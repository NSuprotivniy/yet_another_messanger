package dao.database;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.core.metadata.schema.ClusteringOrder;
import com.datastax.oss.driver.api.core.uuid.Uuids;
import com.datastax.oss.driver.api.querybuilder.Literal;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.delete.Delete;
import com.datastax.oss.driver.api.querybuilder.insert.Insert;
import com.datastax.oss.driver.api.querybuilder.relation.Relation;
import com.datastax.oss.driver.api.querybuilder.select.Select;
import com.datastax.oss.driver.api.querybuilder.term.Term;
import com.datastax.oss.driver.api.querybuilder.update.Assignment;
import com.datastax.oss.driver.api.querybuilder.update.Update;
import models.Chat;
import models.File;

import java.time.LocalTime;
import java.util.*;

import static java.util.Arrays.asList;

import java.util.stream.Collectors;

import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.*;

public class CassandraFile {
    private static final CassandraFile INSTANCE = new CassandraFile();
    public static CassandraFile getInstance() {
        return INSTANCE;
    }
    private CqlSession session  = Cassandra.getSession();
    private CassandraChat cassandraChat = CassandraChat.getInstance();

    public String save(File file) {
        UUID uuid = Uuids.timeBased();
        long createdAt = System.currentTimeMillis();
        Insert insert = insertInto( "files")
                .value("uuid", literal(uuid))
                .value("name", literal(file.getName()))
                .value("body", literal(file.getBody()))
                .value("creator_uuid", literal(file.getCreatorUUID()))
                .value("chat_uuid", literal(file.getChatUUID()))
                .value("created_at", literal(createdAt));
        session.execute(insert.build());
        file.setUuid(uuid);
        file.setCreatedAt(createdAt);
        return uuid.toString();

    }

    public void update(File message, List<String> fields) {
        Assignment[] assignments = (Assignment[])fields.stream().map(field -> {
            switch (field) {
                case "name": return Assignment.setColumn("name", literal(message.getName()));
                default: return null;
            }
        }).toArray();
        Update updateUsers = QueryBuilder
                .update("files")
                .set(assignments)
                .whereColumn("uuid").isEqualTo(literal(message.getUuid()));
        session.execute(updateUsers.build());
    }

    public File get(String uuid, List<String> fields) {return get(UUID.fromString(uuid), fields);}

    public File get(UUID uuid, List<String> fields) {
        Select select = selectFrom("files")
                .columns(fields)
                .whereColumn("uuid").isEqualTo(literal(uuid))
                .allowFiltering();
        ResultSet result = session.execute(select.build());
        Row row = result.one();
        File file = new File().setUuid(uuid);
        for (String field : fields) {
            switch (field) {
                case "uuid": file.setUuid(row.getUuid("uuid").toString()); break;
                case "name": file.setName(row.getString("name")); break;
                case "body": file.setBody(row.getString("body")); break;
                case "creator_uuid": file.setCreatorUUID(row.getUuid("creator_uuid")); break;
                case "chat_uuid": file.setCreatorUUID(row.getUuid("chat_uuid")); break;
                case "created_at": file.setCreatedAt(row.getInstant("created_at").toEpochMilli()); break;
            }
        }
        return file;
    }

    public List<File> search(File chat, List<String> searchFields, List<String> fields) {
        List<Relation> relations = searchFields.stream().map(field -> {
            Object value = null;
            switch (field) {
                case "name": value = chat.getName(); break;
                case "creator_uuid": value = chat.getCreatorUUID(); break;
                case "chat_uuid": value = chat.getChatUUID(); break;
            }
            return Relation.column(field).isEqualTo(literal(value));
        }).collect(Collectors.toList());
        Select select = selectFrom("files")
                .columns(fields)
                .where(relations)
                .allowFiltering();
        ResultSet result = session.execute(select.build());
        List<File> files = new ArrayList<File>();
        for (Row row : result) {
            File newFile = new File();
            for (String field : fields) {
                switch (field) {
                    case "uuid": newFile.setUuid(row.getUuid("uuid").toString()); break;
                    case "name": newFile.setName(row.getString("name")); break;
                    case "body": newFile.setBody(row.getString("body")); break;
                    case "creator_uuid": newFile.setCreatorUUID(row.getUuid("creator_uuid")); break;
                    case "chat_uuid": newFile.setChatUUID(row.getUuid("chat_uuid")); break;
                    case "created_at": newFile.setCreatedAt(row.getInstant("created_at").toEpochMilli()); break;
                }
            }
            files.add(newFile);
        }

        return files;
    }

    public List<File> getAll(String userUUID, List<String> fields) {
//        List<Chat> chats = cassandraChat.getByParticipantUUIDs(asList(userUUID), asList("uuid"));
//        List<Term> chatsUUIDs = chats.stream().map(Chat::getUuid).map(uuid -> literal(uuid)).collect(Collectors.toList());
//        Select select = selectFrom("files")
//                .columns(fields)
//                .whereColumn("chat_uuid").in(chatsUUIDs)
//                .allowFiltering();
//        ResultSet result = session.execute(select.build());
//        Set<File> files = new HashSet<>();
//        for (Row row : result) {
//            File newFile = new File();
//            for (String field : fields) {
//                switch (field) {
//                    case "uuid": newFile.setUuid(row.getUuid("uuid").toString()); break;
//                    case "name": newFile.setName(row.getString("name")); break;
//                    case "body": newFile.setBody(row.getString("body")); break;
//                    case "creator_uuid": newFile.setCreatorUUID(row.getUuid("creator_uuid")); break;
//                    case "chat_uuid": newFile.setChatUUID(row.getUuid("chat_uuid")); break;
//                    case "created_at": newFile.setCreatedAt(row.getInstant("created_at").toEpochMilli()); break;
//                }
//            }
//            files.add(newFile);
//        }
//        files.addAll(search(new File().setCreatorUUID(userUUID), asList("creator_uuid"), fields));
//        return new ArrayList<>(files);
        return search(new File().setCreatorUUID(userUUID), asList("creator_uuid"), fields);
    }

    public void delete(String uuid) {
        Delete deleteChat = deleteFrom("files").whereColumn("uuid").isEqualTo(literal(UUID.fromString(uuid)));
        session.execute(deleteChat.build());
    }
}
