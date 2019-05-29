package dao.database;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.core.uuid.Uuids;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.delete.Delete;
import com.datastax.oss.driver.api.querybuilder.insert.Insert;
import com.datastax.oss.driver.api.querybuilder.relation.Relation;
import com.datastax.oss.driver.api.querybuilder.select.Select;
import com.datastax.oss.driver.api.querybuilder.update.Assignment;
import com.datastax.oss.driver.api.querybuilder.update.Update;
import models.User;

import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.*;
import static java.util.Arrays.asList;

public class CassandraUser {
    private static final CassandraUser INSTANCE = new CassandraUser();
    public static CassandraUser getInstance() {
        return INSTANCE;
    }
    private CqlSession session;
    public CassandraUser() {
        this.session = Cassandra.getSession();
    }

    public String save(User user) {
        UUID uuid = Uuids.timeBased();
        Insert insert = insertInto( "users")
                .value("uuid", literal(uuid))
                .value("name", literal(user.getName()))
                .value("email", literal(user.getEmail()))
                .value("password_digest", literal(user.getPasswordDigest()))
                .value("salt", literal(user.getSalt()))
                .value("created_at", literal(System.currentTimeMillis()));
        session.execute(insert.build());
        return uuid.toString();
    }

    public void update(User user, List<String> fields) {
        Assignment[] assignments = (Assignment[])fields.stream().map(field -> {
            switch (field) {
                case "name": return Assignment.setColumn("name", literal(user.getName()));
                case "email": return Assignment.setColumn("email", literal(user.getEmail()));
                case "password_digest": return Assignment.setColumn("password_digest", literal(user.getPasswordDigest()));
                case "salt": return Assignment.setColumn("salt", literal(user.getSalt()));
                default: return null;
            }
        }).toArray();
        Update updateUsers = QueryBuilder
                .update("users")
                .set(assignments)
                .whereColumn("uuid").isEqualTo(literal(user.getUuid()));
        session.execute(updateUsers.build());
    }

    public User get(String uuid, List<String> fields) { return get(UUID.fromString(uuid), fields); }
    public User get(UUID uuid, List<String> fields) {
        Select select = selectFrom("users")
                .columns(fields)
                .whereColumn("uuid").isEqualTo(literal(uuid))
                .allowFiltering();
        ResultSet result = session.execute(select.build());
        Row row =  result.one();
        if (row == null) {
            return null;
        }
        User user = new User().setUuid(uuid);
        for (String field : fields) {
            switch (field) {
                case "name": user.setName(row.getString("name")); break;
                case "email": user.setEmail(row.getString("email")); break;
                case "password_digest": user.setPasswordDigest(row.getString("password_digest")); break;
                case "salt": user.setSalt(row.getString("salt")); break;
                case "created_at": user.setCreatedAt(row.getInstant("created_at").toEpochMilli()); break;
            }
        }
        return user;
    }

    public User searchOne(User user, List<String> searchFields, List<String> fields) {
        List<Relation> relations = searchFields.stream().map(field -> {
            Object value = null;
            switch (field) {
                case "name": value = user.getName(); break;
                case "email": value = user.getEmail(); break;
                case "password_digest": value = user.getPasswordDigest(); break;
                case "salt": value = user.getSalt(); break;
            }
            return Relation.column(field).isEqualTo(literal(value));
        }).collect(Collectors.toList());
        Select select = selectFrom("users")
                .columns(fields)
                .where(relations)
                .allowFiltering();
        ResultSet result = session.execute(select.build());
        Row row = result.one();
        if (row == null) {
            return null;
        }
        for (String field : fields) {
            switch (field) {
                case "uuid": user.setUuid(row.getUuid("uuid").toString()); break;
                case "name": user.setName(row.getString("name")); break;
                case "email": user.setEmail(row.getString("email")); break;
                case "password_digest": user.setPasswordDigest(row.getString("password_digest")); break;
                case "salt": user.setSalt(row.getString("salt")); break;
                case "created_at": user.setCreatedAt(row.getInstant("created_at").toEpochMilli()); break;
            }
        }
        return user;
    }

    public void delete(String uuid) {
        Delete deleteUser = deleteFrom("users").whereColumn("uuid").isEqualTo(literal(UUID.fromString(uuid)));
        session.execute(deleteUser.build());
    }

    public boolean addContact(String uuid, User contact) {
        Update update = QueryBuilder.update("users")
                .appendSetElement("contacts_uuids", literal(contact.getUuid()))
                .whereColumn("uuid").isEqualTo(literal(UUID.fromString(uuid)));
        ResultSet result = session.execute(update.build());
        return result.wasApplied();
    }

    public boolean removeContact(String uuid, User contact) {
        Update update = QueryBuilder.update("users")
                .removeSetElement("contacts_uuids", literal(contact.getUuid()))
                .whereColumn("uuid").isEqualTo(literal(UUID.fromString(uuid)));
        ResultSet result = session.execute(update.build());
        return result.wasApplied();
    }

    public List<User> getContacts(String uuid, List<String> fields) {
        Select select = selectFrom("users")
                .columns("contacts_uuids")
                .whereColumn("uuid").isEqualTo(literal(UUID.fromString(uuid)))
                .allowFiltering();
        Set<UUID> contactsUUIDs = session.execute(select.build()).one().getSet("contacts_uuids", UUID.class);
        List<User> users = new ArrayList<User>();
        for (UUID contactUUID : contactsUUIDs) {
            users.add(get(contactUUID.toString(), fields));
        }
        return users;
    }
}
