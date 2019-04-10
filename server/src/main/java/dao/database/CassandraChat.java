package dao.database;

import me.prettyprint.cassandra.service.template.ColumnFamilyResult;
import me.prettyprint.cassandra.service.template.ColumnFamilyUpdater;
import me.prettyprint.cassandra.utils.TimeUUIDUtils;
import me.prettyprint.hector.api.exceptions.HectorException;
import models.Chat;

import java.io.FileNotFoundException;

public class CassandraChat extends Cassandra {
    private static final CassandraChat INSTANCE;
    static {
        try {
            INSTANCE  = new CassandraChat();
        } catch (FileNotFoundException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static CassandraChat getInstance() {
        return INSTANCE;
    }

    private final static String CHAT_COL_FAMILY = "chats";

    private CassandraChat() throws FileNotFoundException {
        super(CHAT_COL_FAMILY);
    }

    private CassandraChat(String clusterName, String host) throws FileNotFoundException {
        super(CHAT_COL_FAMILY, clusterName, host);
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
