package dao.database;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.service.ThriftKsDef;
import me.prettyprint.cassandra.service.template.ColumnFamilyTemplate;
import me.prettyprint.cassandra.service.template.ThriftColumnFamilyTemplate;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.ddl.ColumnFamilyDefinition;
import me.prettyprint.hector.api.ddl.ComparatorType;
import me.prettyprint.hector.api.ddl.KeyspaceDefinition;
import me.prettyprint.hector.api.factory.HFactory;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public abstract class Cassandra {
    private final String CONFIG_PATH = "config/cassandra.json";

    protected final Cluster cluster;
    protected final ColumnFamilyTemplate<String, String> template;
    protected final String KEYSPACE_NAME = "messanger";

    protected Cassandra(String chatColFamily) throws FileNotFoundException {
        Gson gson = new Gson();
        JsonReader reader = null;
        reader = new JsonReader(new FileReader(CONFIG_PATH));
        Map<String, String> config = gson.fromJson(reader, HashMap.class);
        String clusterName = config.get("clusterName");
        String host = config.get("host");
        cluster = HFactory.getOrCreateCluster(clusterName,host);
        this.template = getTemplate(cluster, KEYSPACE_NAME, chatColFamily);
    }


    protected Cassandra(String chatColFamily, String clusterName, String host) throws FileNotFoundException {
        this.cluster = HFactory.getOrCreateCluster(clusterName,host);
        this.template = getTemplate(cluster, KEYSPACE_NAME, chatColFamily);
    }

    private static ColumnFamilyTemplate<String, String> getTemplate(Cluster cluster, String keyspaceName, String chatColFamily) {
        KeyspaceDefinition keyspaceDef = cluster.describeKeyspace(keyspaceName);
        ColumnFamilyDefinition cfDef = HFactory.createColumnFamilyDefinition(keyspaceName, chatColFamily, ComparatorType.BYTESTYPE);
        if (keyspaceDef == null) {
            KeyspaceDefinition newKeyspace = HFactory.createKeyspaceDefinition(keyspaceName, ThriftKsDef.DEF_STRATEGY_CLASS,1, Arrays.asList(cfDef));
            cluster.addKeyspace(newKeyspace, true);

        }
        boolean contains = false;
        for (ColumnFamilyDefinition def : keyspaceDef.getCfDefs()) {
            if (def.getName().equals(chatColFamily)) {
                contains = true;
                break;
            }
        }
        if (!contains) {
            cluster.addColumnFamily(cfDef);
        }
        Keyspace keyspace = HFactory.createKeyspace(keyspaceName, cluster);
        return new ThriftColumnFamilyTemplate<String, String>(keyspace,
                chatColFamily,
                StringSerializer.get(),
                StringSerializer.get());
    }

}
