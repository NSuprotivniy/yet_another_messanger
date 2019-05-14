package dao.database;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.core.uuid.Uuids;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.delete.Delete;
import com.datastax.oss.driver.api.querybuilder.insert.Insert;
import com.datastax.oss.driver.api.querybuilder.select.Select;
import com.datastax.oss.driver.api.querybuilder.update.Assignment;
import com.datastax.oss.driver.api.querybuilder.update.Update;
import models.User;

import java.util.List;
import java.util.UUID;

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
                .value("salt", literal(user.getSalt()));
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
                .whereColumn("uuid").isEqualTo(literal(UUID.fromString(user.getUuid())));
        session.execute(updateUsers.build());
    }

    public User get(String uuid, List<String> fields) {
        Select select = selectFrom("users")
                .columns(fields)
                .whereColumn("uuid").isEqualTo(literal(UUID.fromString(uuid)))
                .allowFiltering();
        ResultSet result = session.execute(select.build());
        if (result.all().size() == 0) {
            return null;
        }
        Row row = result.all().get(0);
        User user = new User().setUuid(uuid);
        for (String field : fields) {
            switch (field) {
                case "name": user.setName(row.getString("name"));
                case "email": user.setEmail(row.getString("email"));
                case "password_digest": user.setPasswordDigest(row.getString("password_digest"));
                case "salt": user.setSalt(row.getString("salt"));
            }
        }
        return user;
    }

    public void delete(String uuid) {
        Delete deleteUser = deleteFrom("users").whereColumn("uuid").isEqualTo(literal(UUID.fromString(uuid)));
        session.execute(deleteUser.build());
    }
}
