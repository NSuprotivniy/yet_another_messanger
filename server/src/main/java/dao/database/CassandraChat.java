package dao.database;

import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.service.ThriftKsDef;
import me.prettyprint.cassandra.service.template.ColumnFamilyResult;
import me.prettyprint.cassandra.service.template.ColumnFamilyTemplate;
import me.prettyprint.cassandra.service.template.ColumnFamilyUpdater;
import me.prettyprint.cassandra.service.template.ThriftColumnFamilyTemplate;
import me.prettyprint.cassandra.utils.TimeUUIDUtils;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.ddl.ColumnFamilyDefinition;
import me.prettyprint.hector.api.ddl.ComparatorType;
import me.prettyprint.hector.api.ddl.KeyspaceDefinition;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.factory.HFactory;
import models.Chat;

import java.util.Arrays;

public class CassandraChat {
    private final Cluster cluster;
    private final ColumnFamilyTemplate<String, String> template;
    private final String KEYSPACE_NAME = "messanger";
    private final String CHAT_COL_FAMILY = "chats";


    public CassandraChat(String clusterName, String host) {
        cluster = HFactory.getOrCreateCluster(clusterName,host);
        KeyspaceDefinition keyspaceDef = cluster.describeKeyspace(KEYSPACE_NAME);
        if (keyspaceDef == null) {
            ColumnFamilyDefinition cfDef = HFactory.createColumnFamilyDefinition(KEYSPACE_NAME,
                    CHAT_COL_FAMILY,
                    ComparatorType.BYTESTYPE);
            KeyspaceDefinition newKeyspace = HFactory.createKeyspaceDefinition(KEYSPACE_NAME,
                    ThriftKsDef.DEF_STRATEGY_CLASS,
                    1,
                    Arrays.asList(cfDef));
            cluster.addKeyspace(newKeyspace, true);
        }
        Keyspace keyspace = HFactory.createKeyspace(KEYSPACE_NAME, cluster);
        this.template = new ThriftColumnFamilyTemplate<String, String>(keyspace,
                CHAT_COL_FAMILY,
                StringSerializer.get(),
                StringSerializer.get());
    }

    public String update(Chat chat) {
        try {
            String uuid = TimeUUIDUtils.getUniqueTimeUUIDinMillis().toString();
            ColumnFamilyUpdater<String, String> updater = template.createUpdater(uuid);
            updater.setString("name", chat.getName());
            updater.setLong("timestamp", System.currentTimeMillis());
            template.update(updater);
            return uuid;
        } catch (HectorException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Chat get(String uuid) {
        try {
            ColumnFamilyResult<String, String> res = template.queryColumns(uuid);
            String name = res.getString("name");
            Chat chat = new Chat(name);
            return chat;
        } catch (HectorException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean delete(String uuid) {
        try {
            template.deleteRow(uuid);
            return true;
        } catch (HectorException e) {
            e.printStackTrace();
            return false;
        }
    }
}
