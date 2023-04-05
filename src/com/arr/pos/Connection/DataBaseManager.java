package com.arr.pos.Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseManager {
    private final ProxyConnection connection;
    private final PropertiesReader pR = new PropertiesReader();

    public DataBaseManager(){
        try {
            Connection connection = DriverManager.getConnection(
                    this.pR.getMapProperties().get("db.url"),
                    this.pR.getMapProperties().get("db.user"),
                    this.pR.getMapProperties().get("db.password"));
            this.connection = new ProxyConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection(){
        return connection;
    }

    public void close(){
        try {
            connection.reallyClose();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
