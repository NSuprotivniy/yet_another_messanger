package dao.database;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.schema.CreateKeyspace;
import com.datastax.oss.driver.api.querybuilder.schema.CreateTable;

import java.net.InetSocketAddress;

import static com.datastax.oss.driver.api.querybuilder.SchemaBuilder.*;
import static com.datastax.oss.driver.api.querybuilder.SchemaBuilder.createIndex;

public class CassandraInit {
    private static void createKS(String keyspace, String datacenter, String ip, int port) {
        try(CqlSession session = CqlSession.builder()
                .withLocalDatacenter(datacenter)
                .addContactPoint(InetSocketAddress.createUnresolved(ip, port))
                .build()) {
            CreateKeyspace createKs = createKeyspace(keyspace).ifNotExists().withSimpleStrategy(1);
            session.execute(createKs.build());
        }
    }

    private static void createSchema(String keyspace, String datacenter, String ip, int port) {
        try(CqlSession session = CqlSession.builder()
                .withLocalDatacenter(datacenter)
                .addContactPoint(InetSocketAddress.createUnresolved(ip, port))
                .withKeyspace(keyspace)
                .build()) {
            CreateTable createTable =
                    createTable("users").ifNotExists()
                            .withPartitionKey("uuid", DataTypes.UUID)
                            .withColumn("name", DataTypes.TEXT)
                            .withColumn("email", DataTypes.TEXT)
                            .withColumn("password_digest", DataTypes.TEXT)
                            .withColumn("salt", DataTypes.TEXT);

            session.execute(createTable.build());

            createTable =
                    createTable("chats").ifNotExists()
                            .withPartitionKey("uuid", DataTypes.UUID)
                            .withColumn("name", DataTypes.TEXT)
                            .withColumn("participants_uuids", DataTypes.listOf(DataTypes.UUID))
                            .withColumn("creator_uuid", DataTypes.UUID);

            session.execute(createTable.build());


            createTable =
                    createTable("messages").ifNotExists()
                            .withPartitionKey("uuid", DataTypes.UUID)
                            .withColumn("text", DataTypes.TEXT)
                            .withColumn("creator_uuid", DataTypes.UUID)
                            .withColumn("chat_uuid", DataTypes.UUID);
            session.execute(createTable.build());

            createIndex().ifNotExists().onTable("chats").andColumnKeys("creator");
            createIndex().ifNotExists().onTable("chats").andColumnKeys("participants");
            createIndex().ifNotExists().onTable("messages").andColumnKeys("user_uuid");
            createIndex().ifNotExists().onTable("messages").andColumnKeys("chat_uuid");
        }
    }

    public static void main(String[] args) {
        createKS(args[0],  args[1], args[2], Integer.parseInt(args[3]));
        createSchema(args[0],  args[1], args[2], Integer.parseInt(args[3]));
    }
}
