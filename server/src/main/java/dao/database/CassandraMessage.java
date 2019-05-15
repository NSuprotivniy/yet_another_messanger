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
import models.Message;

import java.util.List;
import java.util.UUID;

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
        Message message = new Message().setUuid(uuid);
        for (String field : fields) {
            switch (field) {
                case "text": message.setText(row.getString("text")); break;
                case "creator_uuid": message.setText(row.getString("creator_uuid")); break;
                case "chat_uuid": message.setText(row.getString("chat_uuid")); break;
            }
        }
        return message;
    }

    public void delete(String uuid) {
        Delete deleteChat = deleteFrom("messages").whereColumn("uuid").isEqualTo(literal(UUID.fromString(uuid)));
        session.execute(deleteChat.build());
    }
}
