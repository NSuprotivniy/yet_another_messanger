package dao.database;

import me.prettyprint.cassandra.service.template.ColumnFamilyResult;
import me.prettyprint.cassandra.service.template.ColumnFamilyUpdater;
import me.prettyprint.cassandra.utils.TimeUUIDUtils;
import me.prettyprint.hector.api.exceptions.HectorException;
import models.User;

import java.io.FileNotFoundException;

public class CassandraUser extends Cassandra {
    private static final CassandraUser INSTANCE;
    static {
        try {
            INSTANCE  = new CassandraUser();
        } catch (FileNotFoundException e) {
            throw new ExceptionInInitializerError(e);
        }
    }
    public static CassandraUser getInstance() {
        return INSTANCE;
    }

    private final static String CHAT_COL_FAMILY = "users";

    public CassandraUser() throws FileNotFoundException {
        super(CHAT_COL_FAMILY);
    }

    public CassandraUser(String clusterName, String host) throws FileNotFoundException {
        super(CHAT_COL_FAMILY, clusterName, host);
    }
    public String update(User user) {
        try {
            String uuid = TimeUUIDUtils.getUniqueTimeUUIDinMillis().toString();
            ColumnFamilyUpdater<String, String> updater = template.createUpdater(uuid);
            updater.setString("name", user.getName());
            updater.setString("email", user.getEmail());
            updater.setString("password_digest", user.getPasswordDigest());
            updater.setLong("timestamp", System.currentTimeMillis());
            template.update(updater);
            return uuid;
        } catch (HectorException e) {
            e.printStackTrace();
            return null;
        }
    }

    public User get(String uuid) {
        try {
            ColumnFamilyResult<String, String> res = template.queryColumns(uuid);
            String name = res.getString("name");
            String email = res.getString("email");
            User user = new User(name, email);
            return user;
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
