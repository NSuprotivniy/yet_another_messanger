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
import models.Chat;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.*;
import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.literal;
import static java.util.Arrays.asList;

public class CassandraChat {
    private static final CassandraChat INSTANCE = new CassandraChat();
    public static CassandraChat getInstance() {
        return INSTANCE;
    }

    private CqlSession session;

    private CassandraChat()  {
        this.session = Cassandra.getSession();
    }

    public String save(Chat chat) {
        UUID uuid = Uuids.timeBased();
        Insert insert = insertInto( "chats")
                .value("uuid", literal(uuid))
                .value("name", literal(chat.getName()))
                .value("participants_uuids", literal(chat.getParticipantsUUIDs()))
                .value("creator_uuid", literal(chat.getCreatorUUID()));
        session.execute(insert.build());
        return uuid.toString();
    }

    public void update(Chat chat, List<String> fields) {
        Assignment[] assignments = (Assignment[])fields.stream().map(field -> {
            switch (field) {
                case "name": return Assignment.setColumn("name", literal(chat.getName()));
                case "participants_uuids": return Assignment.setColumn("participants_uuids", literal(chat.getParticipantsUUIDs()));
                default: return null;
            }
        }).toArray();
        Update updateUsers = QueryBuilder
                .update("chats")
                .set(assignments)
                .whereColumn("uuid").isEqualTo(literal(chat.getUuid()));
        session.execute(updateUsers.build());
    }

    public Chat get(String uuid, List<String> fields) {
        Select select = selectFrom("chats")
                .columns(fields)
                .whereColumn("uuid").isEqualTo(literal(UUID.fromString(uuid)))
                .allowFiltering();
        ResultSet result = session.execute(select.build());
        Row row = result.all().get(0);
        Chat chat = new Chat().setUuid(uuid);
        for (String field : fields) {
            switch (field) {
                case "name": chat.setName(row.getString("name")); break;
                case "participants_uuids": chat.setParticipantsUUIDs(row.getSet("participants_uuids", String.class)); break;
                case "creator": chat.setName(row.getString("creator_uuid")); break;
            }
        }
        return chat;
    }

    public List<Chat> search(Chat chat, List<String> searchFields, List<String> fields) {
        List<Relation> relations = searchFields.stream().map(field -> {
            Object value = null;
            switch (field) {
                case "name": value = chat.getName(); break;
                case "creator_uuid": value = chat.getCreatorUUID(); break;
            }
            return Relation.column(field).isEqualTo(literal(value));
        }).collect(Collectors.toList());
        Select select = selectFrom("chats")
                .columns(fields)
                .where(relations)
                .allowFiltering();
        ResultSet result = session.execute(select.build());
        List<Chat> chats = new ArrayList<Chat>();
        for (Row row : result) {
            Chat newChat = new Chat();
            for (String field : fields) {
                switch (field) {
                    case "uuid": newChat.setUuid(row.getUuid("uuid").toString()); break;
                    case "name": newChat.setName(row.getString("name")); break;
                    case "participants_uuids": newChat.setParticipantsUUIDs(row.getSet("participants_uuids", String.class)); break;
                    case "creator_uuid": newChat.setCreatorUUID(row.getString("creator_uuid")); break;
                }
            }
            chats.add(newChat);
        }

        return chats;
    }

    public void delete(String uuid) {
        Delete deleteChat = deleteFrom("chats").whereColumn("uuid").isEqualTo(literal(UUID.fromString(uuid)));
        session.execute(deleteChat.build());
    }
}
