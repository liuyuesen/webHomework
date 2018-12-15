package com.example.demo.tool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class DBManager {
    private static final String url = "jdbc:mysql://45.76.65.63:3306/library_manager?useSSL=true&characterEncoding=utf-8";
    private static final String name = "com.mysql.jdbc.Driver";
    private static final String username = "library_mysql_manager";
    private static final String password = "library_123456";
    public Connection connection = null;
    public PreparedStatement preparedStatement = null;

    public DBManager(String sql) throws Exception {
        Class.forName(name);
        connection = DriverManager.getConnection(url, username, password);
        preparedStatement = connection.prepareStatement(sql);
    }

    public void close() {
        try {
            this.connection.close();
            this.preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
