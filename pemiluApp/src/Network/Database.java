package Network;

import java.sql.*;

public class Database {

    public static final String DB = "tubes_pbo";
    public static final String DB_USER = "root";
    public static final String DB_PASSWORD = "";
    public static final String DB_URL = "jdbc:mysql://localhost:3306/" + DB;

    public static Connection conn;
    public static Statement stmt;

    public static void connect() throws SQLException {
        conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
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
