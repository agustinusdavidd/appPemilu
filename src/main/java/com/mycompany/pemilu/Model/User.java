package com.mycompany.pemilu.Model;

import javax.xml.transform.Result;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;

public class User {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/tubes_pbo";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "";
    private static Connection conn;
    private static Statement stmt;
    private String nik;
    private String nama;
    private String passwordHash;
    private Calon pilihan;
    private boolean isAdmin;
    private boolean sudahMemilih;

    public User(String nik, String nama, String password,Calon pilihan, boolean isAdmin, boolean sudahMemilih) {
        this.nik = nik;
        this.nama = nama;
        this.passwordHash = hash256(password);
        this.pilihan = pilihan;
        this.isAdmin = isAdmin;
        this.sudahMemilih = sudahMemilih;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String password) {
        this.passwordHash = hash256(password);
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean isSudahMemilih() {
        return sudahMemilih;
    }

    public void setSudahMemilih(boolean sudahMemilih) {
        this.sudahMemilih = sudahMemilih;
    }

    public Calon getPilihan() {
        return pilihan;
    }

    public void setPilihan(Calon pilihan) {
        this.pilihan = pilihan;
    }

    private String hash256(String pass) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = md.digest(pass.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder(2 * encodedHash.length);
            for (int i = 0; i < encodedHash.length; i++) {
                String hex = Integer.toHexString(0xff & encodedHash[i]);
                if(hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException ex) {
            System.out.println(ex.getMessage());
            return "";
        }
    }

    public static ArrayList<User> getAll() throws SQLException {
        connect();
        String sql = "SELECT * " +
                "FROM users " +
                "LEFT JOIN calons " +
                "ON users.pilihan = calons.id";
        ResultSet rs = stmt.executeQuery(sql);
        ArrayList<User> allUser = new ArrayList<>();
        while (rs.next()) {
            allUser.add(new User(
                    rs.getString("users.nik"),
                    rs.getString("users.nama"),
                    rs.getString("users.password"),
                    (rs.getString("users.pilihan")) == null ?
                            null : new Calon(rs.getInt("calons.id")),
                    rs.getBoolean("users.isAdmin"),
                    rs.getBoolean("users.sudahMemilih")
            ));
        }
        disconnect();
        return allUser;
    }

    public static ArrayList<User> getWhere(String sqlWhere) throws SQLException {
        connect();
        String sql = "SELECT * " +
                "FROM users " +
                "LEFT JOIN calons " +
                "ON users.pilihan = calons.id " +
                "WHERE "+ sqlWhere;
        ResultSet rs = stmt.executeQuery(sql);
        ArrayList<User> allUser = new ArrayList<>();
        while (rs.next()) {
            allUser.add(new User(
                    rs.getString("users.nik"),
                    rs.getString("users.nama"),
                    rs.getString("users.password"),
                    (rs.getString("users.pilihan")) == null ?
                            null : new Calon(rs.getInt("calons.id")),
                    rs.getBoolean("users.isAdmin"),
                    rs.getBoolean("users.sudahMemilih")
            ));
        }
        disconnect();
        return allUser;
    }

    private static void connect() throws SQLException {
        conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        stmt = conn.createStatement();
    }
    private static void disconnect() throws SQLException {
        stmt.close();
        conn.close();
    }

    public static User getByNIK(String nik) throws SQLException {
        connect();
        String sql = "SELECT * " +
                "FROM users " +
                "LEFT JOIN calons " +
                "ON users.pilihan = calons.id " +
                "WHERE users.nik = '"+nik+"'";
        ResultSet rs = stmt.executeQuery(sql);
        rs.next();
        User user = new User(
                rs.getString("users.nik"),
                rs.getString("users.nama"),
                rs.getString("users.password"),
                (rs.getString("users.pilihan")) == null ?
                        null : new Calon(rs.getInt("calons.id")),
                rs.getBoolean("users.isAdmin"),
                rs.getBoolean("users.sudahMemilih")
        );
        disconnect();
        return user;
    }

    public static int delete(String nik) throws SQLException {
        connect();
        String sql = "DELETE FROM users " +
                "WHERE users.nik = '"+nik+"'";
        int rs = stmt.executeUpdate(sql);
        disconnect();
        return rs;
    }

    public static int create(User user) throws SQLException {
        connect();
        PreparedStatement sql = conn.prepareStatement(
                "INSERT INTO users " +
                "VALUES(?, ?, ?, ?, ?, ?)");
        sql.setString(1, user.getNik());
        sql.setString(2, user.getNama());
        sql.setString(3, user.getPasswordHash());
        if (user.pilihan == null) {
            sql.setNull(4, Types.INTEGER);
        } else {
            sql.setInt(4, user.getPilihan().getId());
        }
        sql.setBoolean(5, user.isAdmin());
        sql.setBoolean(6, user.isSudahMemilih());
        int rs = sql.executeUpdate();
        disconnect();
        return rs;
    }

    public static int update(String oldNik,User user) throws SQLException {
        connect();
        PreparedStatement sql = conn.prepareStatement(
                "UPDATE users " +
                        "SET 'nik'=?, " +
                        "'nama'=?, " +
                        "'password'=?, " +
                        "'pilihan'=?, " +
                        "'isAdmin'=?, " +
                        "'sudahMemilih'=?, " +
                        "WHERE 'nik'=?"
        );
        sql.setString(1, user.getNik());
        sql.setString(2, user.getNama());
        sql.setString(3, user.getPasswordHash());

        if (user.pilihan == null) {
            sql.setNull(4, Types.INTEGER);
        } else {
            sql.setInt(4, user.getPilihan().getId());
        }

        sql.setBoolean(5, user.isAdmin());
        sql.setBoolean(6, user.isSudahMemilih());
        sql.setString(7,oldNik);
        int rs = sql.executeUpdate();
        disconnect();
        return rs;
    }

    public static void main(String[] args) {
        try {
            create(new User(
                    "wow",
                    "ini",
                    "user",
                    null,
                    false,
                    false
            ));
            User user = getByNIK("wow");
            System.out.println(user.getPasswordHash());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}
