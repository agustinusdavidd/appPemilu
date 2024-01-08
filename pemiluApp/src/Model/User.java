package Model;

import Network.Database;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;

public class User {

    private static final String Database_URL = "jDatabasec:mysql://localhost:3306/tubes_pbo";
    private static final String Database_USER = "root";
    private static final String Database_PASS = "";
    private static Connection conn;
    private static Statement stmt;
    private String nik;
    private String nama;
    private String passwordHash;
    private Calon pilihan;
    private boolean isAdmin;
    private boolean sudahMemilih;
    private boolean isInvited;
    private int id_tps;

    public User(String nik, String nama, String password, Calon pilihan, int id_tps, boolean isAdmin, boolean sudahMemilih) {
        this.nik = nik;
        this.nama = nama;
        this.passwordHash = hash256(password);
        this.pilihan = pilihan;
        this.isAdmin = isAdmin;
        this.sudahMemilih = sudahMemilih;
        this.id_tps = id_tps;
    }
    public User(String nik, String nama, String password, Calon pilihan, boolean isAdmin, boolean sudahMemilih, boolean isInvited) {
        this.nik = nik;
        this.nama = nama;
        this.passwordHash = hash256(password);
        this.pilihan = pilihan;
        this.isAdmin = isAdmin;
        this.sudahMemilih = sudahMemilih;
        this.isInvited = isInvited;
    }

    private User(String nik, String nama, String password, Calon pilihan, int id_tps, boolean isAdmin, boolean sudahMemilih, boolean isInvited) {
        this.nik = nik;
        this.nama = nama;
        this.passwordHash = password;
        this.pilihan = pilihan;
        this.id_tps = id_tps;
        this.isAdmin = isAdmin;
        this.sudahMemilih = sudahMemilih;
        this.isInvited = isInvited;
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

    public boolean isInvited() {
        return isInvited;
    }

    public void setInvited(boolean invited) {
        isInvited = invited;
    }

    public TPS getTPS() throws SQLException {
        return id_tps == -1 ? null : TPS.getById(id_tps);
    }

    public void setTPS(TPS tps) {
        this.id_tps = tps.getId();
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
        Database.connect();
        String sql = "SELECT * " +
                "FROM users " +
                "LEFT JOIN calons " +
                "ON users.pilihan = calons.id";
        ResultSet rs = Database.query(sql);
        ArrayList<User> allUser = new ArrayList<>();
        while (rs.next()) {
            allUser.add(new User(
                    rs.getString("users.nik"),
                    rs.getString("users.nama"),
                    rs.getString("users.password"),
                    (rs.getString("users.pilihan")) == null ?
                            null : new Calon(
                            rs.getInt("calons.id"),
                            rs.getString("calons.capres"),
                            rs.getString("calons.cawapres"),
                            rs.getString("calons.visi_misi")
                    ),
                    rs.getInt("users.id_tps"),
                    rs.getBoolean("users.isAdmin"),
                    rs.getBoolean("users.sudahMemilih"),
                    rs.getBoolean("users.isInvited")
            ));
        }
        Database.disconnect();
        return allUser;
    }

    public static ArrayList<User> getWhere(String sqlWhere) throws SQLException {
        Database.connect();
        String sql = "SELECT * " +
                "FROM users " +
                "LEFT JOIN calons " +
                "ON users.pilihan = calons.id " +
                "WHERE "+ sqlWhere;
        ResultSet rs = Database.query(sql);
        ArrayList<User> allUser = new ArrayList<>();
        while (rs.next()) {
            allUser.add(new User(
                    rs.getString("users.nik"),
                    rs.getString("users.nama"),
                    rs.getString("users.password"),
                    (rs.getString("users.pilihan")) == null ?
                            null : new Calon(
                            rs.getInt("calons.id"),
                            rs.getString("calons.capres"),
                            rs.getString("calons.cawapres"),
                            rs.getString("calons.visi_misi")

                    ),
                    rs.getInt("users.id_tps"),
                    rs.getBoolean("users.isAdmin"),
                    rs.getBoolean("users.sudahMemilih"),
                    rs.getBoolean("users.isInvited")
            ));
        }
        Database.disconnect();
        return allUser;
    }

    public static User getByNIK(String nik) throws SQLException {
        Database.connect();
        String sql = "SELECT * " +
                "FROM users " +
                "LEFT JOIN calons " +
                "ON users.pilihan = calons.id " +
                "WHERE users.nik = '"+nik+"'";
        ResultSet rs = Database.query(sql);
        rs.next();
        User user = new User(
                rs.getString("users.nik"),
                rs.getString("users.nama"),
                rs.getString("users.password"),
                (rs.getString("users.pilihan")) == null ?
                        null : new Calon(
                        rs.getInt("calons.id"),
                        rs.getString("calons.capres"),
                        rs.getString("calons.cawapres"),
                        rs.getString("calons.visi_misi")
                ),
                rs.getInt("users.id_tps"),
                rs.getBoolean("users.isAdmin"),
                rs.getBoolean("users.sudahMemilih"),
                rs.getBoolean("users.isInvited")
        );
        Database.disconnect();
        return user;
    }

    public static int delete(String nik) throws SQLException {
        Database.connect();
        String sql = "DELETE FROM users " +
                "WHERE users.nik = '"+nik+"'";
        int rs = Database.update(sql);
        Database.disconnect();
        return rs;
    }

    public static int create(User user) throws SQLException {
        Database.connect();

        PreparedStatement sql = Database.prepareStatement(
                "INSERT INTO users " +
                        "VALUES(?, ?, ?, ?, ?, ?, ?, ?)");
        sql.setString(1, user.getNik());
        sql.setString(2, user.getNama());
        sql.setString(3, user.getPasswordHash());
        if (user.pilihan == null) {
            sql.setNull(4, Types.INTEGER);
        } else {
            sql.setInt(4, user.getPilihan().getId());
        }
        sql.setInt(5, user.id_tps);
        sql.setBoolean(6, user.isAdmin());
        sql.setBoolean(7, user.isInvited());
        sql.setBoolean(8, user.isSudahMemilih());

        int rs = Database.update(sql);
        Database.disconnect();
        return rs;
    }

    public static int update(String oldNik,User user) throws SQLException {
        Database.connect();
        PreparedStatement sql = Database.prepareStatement(
                "UPDATE users " +
                        "SET " +
                        "`nik`=?, " +
                        "`nama`=?, " +
                        "`password`=?, " +
                        "`pilihan`=?, " +
                        "`id_tps`=?, " +
                        "`isAdmin`=?, " +
                        "`sudahMemilih`=?, " +
                        "`isInvited`=? " +
                        "WHERE `nik`=?"
        );
        sql.setString(1, user.getNik());
        sql.setString(2, user.getNama());
        sql.setString(3, user.getPasswordHash());

        if (user.pilihan == null) {
            sql.setNull(4, Types.INTEGER);
        } else {
            sql.setInt(4, user.getPilihan().getId());
        }
        sql.setInt(5, user.getTPS() == null ? -1 : user.id_tps);
        sql.setBoolean(6, user.isAdmin());
        sql.setBoolean(7, user.isSudahMemilih());
        sql.setBoolean(8, user.isInvited());
        sql.setString(9,oldNik);
        System.out.println(sql);
        int rs = Database.update(sql);
        Database.disconnect();
        return rs;
    }

    public static void main(String[] args) {
        try {
            User user = getByNIK("tes2");
            user.id_tps = 1;
            User.update(user.getNik(), user);
            System.out.println(getByNIK(user.getNik()).id_tps);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}