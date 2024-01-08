package com.mycompany.pemilu.Model;

import java.sql.*;

public class DB {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/tubes_pbo";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "";
    private static Connection conn;
    private static Statement stmt;

    public static void connect() throws SQLException {
        conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        stmt = conn.createStatement();
    }
    public static void disconnect() throws SQLException {
        stmt.close();
        conn.close();
    }
    public static PreparedStatement prepareStatement(String sql) throws SQLException {
        return conn.prepareStatement(sql);
    }
    public static ResultSet query(String sql) throws SQLException {
        return stmt.executeQuery(sql);
    }
    public static ResultSet query(PreparedStatement sql) throws SQLException {
        return sql.executeQuery();
    }

    public static int update(PreparedStatement sql) throws SQLException {
        return sql.executeUpdate();
    }

    public static int update(String sql) throws SQLException {
        return stmt.executeUpdate(sql);
    }
}
