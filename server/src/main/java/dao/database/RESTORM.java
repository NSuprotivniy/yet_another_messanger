package dao.database;

import models.Model;

public interface RESTORM {
    public String update(String name);
    public Model get(String uuid);
    public boolean delete(String uuid);
}
