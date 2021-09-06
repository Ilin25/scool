package ru.ilin.school.dao;

import ru.ilin.school.dao.Impl.DataBaseRuntimeException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DBConnector {

    private final String url;
    private final String user;
    private final String password;

    public DBConnector(String fileConfigName) {
        ResourceBundle resource = ResourceBundle.getBundle(fileConfigName);
        this.url = resource.getString("url");
        this.user = resource.getString("user");
        this.password = resource.getString("password");
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new DataBaseRuntimeException(e);
        }
    }
}
