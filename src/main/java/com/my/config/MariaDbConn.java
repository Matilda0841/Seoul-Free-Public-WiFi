package com.my.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MariaDbConn {

    private final String driver = "org.mariadb.jdbc.Driver";
    private final String ip = "localhost";
    private final String port = "3306";
    private final String name = "wifi_db";
    private final String user = "wifi_user";
    private final String password = "hsh";
    private final String url = "jdbc:mariadb://" + ip + ":" + port + "/" + name;

    public Connection getConn() throws ClassNotFoundException, SQLException {
        Class.forName(this.driver);
        Connection conn = DriverManager.getConnection(this.url, this.user, this.password);;
        return conn;
    }

}
