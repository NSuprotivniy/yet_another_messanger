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
import models.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.*;
import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.literal;

public class CassandraMessage {
    private static final CassandraMessage INSTANCE = new CassandraMessage();
    public static CassandraMessage getInstance() {
        return INSTANCE;
    }

    private CqlSession session;

    private CassandraMessage()  {
        this.session = Cassandra.getSession();
    }

    public String save(Message message) {
        UUID uuid = Uuids.timeBased();
        Insert insert = insertInto( "messages")
                .value("uuid", literal(uuid))
                .value("text", literal(message.getText()))
                .value("creator_uuid", literal(message.getCreatorUUID()))
                .value("chat_uuid", literal(message.getChatUUID()));
        session.execute(insert.build());
        message.setUuid(uuid);
        return uuid.toString();
    }

    public void update(Message message, List<String> fields) {
        Assignment[] assignments = (Assignment[])fields.stream().map(field -> {
            switch (field) {
                case "text": return Assignment.setColumn("text", literal(message.getText()));
                default: return null;
            }
        }).toArray();
        Update updateUsers = QueryBuilder
                .update("messages")
                .set(assignments)
                .whereColumn("uuid").isEqualTo(literal(message.getUuid()));
        session.execute(updateUsers.build());
    }

    public Message get(String uuid, List<String> fields) {
        Select select = selectFrom("messages")
                .columns(fields)
                .whereColumn("uuid").isEqualTo(literal(UUID.fromString(uuid)))
                .allowFiltering();
        ResultSet result = session.execute(select.build());
        Row row = result.all().get(0);
        Message message = new Message().setUuid(UUID.fromString(uuid));
        for (String field : fields) {
            switch (field) {
                case "uuid": message.setUuid(row.getUuid("uuid")); break;
                case "text": message.setText(row.getString("text")); break;
                case "creator_uuid": message.setCreatorUUID(row.getUuid("creator_uuid")); break;
                case "chat_uuid": message.setChatUUID(row.getUuid("chat_uuid")); break;
            }
        }
        return message;
    }

    public List<Message> search(Message message, List<String> searchFields, List<String> fields) {
        List<Relation> relations = searchFields.stream().map(field -> {
            Object value = null;
            switch (field) {
                case "chat_uuid": value = message.getChatUUID(); break;
                case "creator_uuid": value = message.getCreatorUUID(); break;
            }
            return Relation.column(field).isEqualTo(literal(value));
        }).collect(Collectors.toList());
        Select select = selectFrom("messages")
                .columns(fields)
                .where(relations)
                .allowFiltering();
        ResultSet result = session.execute(select.build());
        List<Message> messages = new ArrayList<Message>();
        for (Row row : result) {
            Message newMessage = new Message();
            for (String field : fields) {
                switch (field) {
                    case "uuid": newMessage.setUuid(row.getUuid("uuid")); break;
                    case "text": newMessage.setText(row.getString("text")); break;
                    case "chat_uuid": newMessage.setChatUUID(row.getUuid("chat_uuid")); break;
                    case "creator_uuid": newMessage.setCreatorUUID(row.getUuid("creator_uuid")); break;
                }
            }
            messages.add(newMessage);
        }
        return messages;
    }

    public void delete(String uuid) {
        Delete deleteChat = deleteFrom("messages").whereColumn("uuid").isEqualTo(literal(UUID.fromString(uuid)));
        session.execute(deleteChat.build());
    }
}
