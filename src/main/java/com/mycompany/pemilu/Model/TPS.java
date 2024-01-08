package com.mycompany.pemilu.Model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TPS {
    private int id;
    private String lokasi;
    private String nik_panitia;
    private User panitia = null;

    protected TPS(int id, String lokasi, String nik_panitia) {
        this.id = id;
        this.lokasi = lokasi;
        this.nik_panitia = nik_panitia;
    }

    protected TPS(int id, String lokasi, User panitia) {
        this.id = id;
        this.lokasi = lokasi;
        this.nik_panitia = panitia.getNik();
        this.panitia = panitia;
    }

    public TPS(String lokasi, User panitia) {
        this.lokasi = lokasi;
        this.nik_panitia = panitia.getNik();
        this.panitia = panitia;
    }
    public TPS(String lokasi, String nik_panitia) {
        this.lokasi = lokasi;
        this.nik_panitia = nik_panitia;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public User getPanitia() {
        if (panitia == null) {
            try {
                panitia = User.getByNIK(nik_panitia);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return panitia;
    }

    public void setPanitia(User panitia) {
        this.panitia = panitia;
    }

    public void setPanitia(String nik_panitia) {
        try {
            setPanitia(User.getByNIK(nik_panitia));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static int create(TPS tps) throws SQLException {
        DB.connect();
        PreparedStatement sql = DB.prepareStatement(
                "INSERT INTO TPS(`lokasi`, `nik_panitia`) " +
                        "VALUES(?, ?)"
        );
        sql.setString(1, tps.getLokasi());
        sql.setString(2, tps.nik_panitia);
        int rs = DB.update(sql);
        DB.disconnect();
        return rs;
    }

    public static ArrayList<TPS> getAll() throws SQLException {
        DB.connect();
        ResultSet rs = DB.query("SELECT * FROM TPS");
        ArrayList<TPS> allTPS = new ArrayList<>();
        while (rs.next()) {
            allTPS.add(new TPS(
                    rs.getInt("TPS.id"),
                    rs.getString("TPS.lokasi"),
                    rs.getString("TPS.nik_panitia")
            ));
        }
        DB.disconnect();
        return allTPS;
    }

    public static TPS getById(int id) throws SQLException {
        DB.connect();
        PreparedStatement sql = DB.prepareStatement("SELECT * FROM TPS WHERE `id`=?");
        sql.setInt(1, id);
        ResultSet rs = DB.query(sql);
        rs.next();
        TPS tps = new TPS(
                rs.getInt("TPS.id"),
                rs.getString("TPS.lokasi"),
                rs.getString("TPS.nik_panitia")
        );
        DB.disconnect();
        return tps;
    }

    public static TPS getByLokasi(String lokasi) throws SQLException {
        DB.connect();
        PreparedStatement sql = DB.prepareStatement("SELECT * FROM TPS WHERE `lokasi`=?");
        sql.setString(1, lokasi);
        ResultSet rs = DB.query(sql);
        rs.next();
        TPS tps = new TPS(
                rs.getInt("TPS.id"),
                rs.getString("TPS.lokasi"),
                rs.getString("TPS.nik_panitia")
        );
        DB.disconnect();
        return tps;
    }

    public static ArrayList<TPS> getByPanitia(String nik_panitia) throws SQLException {
        DB.connect();
        PreparedStatement sql = DB.prepareStatement("SELECT * FROM TPS WHERE `nik_panitia`=?");
        sql.setString(1, nik_panitia);
        ResultSet rs = DB.query(sql);
        ArrayList<TPS> allTPS = new ArrayList<>();
        while (rs.next()) {
            allTPS.add(new TPS(
                    rs.getInt("TPS.id"),
                    rs.getString("TPS.lokasi"),
                    rs.getString("TPS.nik_panitia")
            ));
        }
        DB.disconnect();
        return allTPS;
    }

    public static ArrayList<TPS> getByPanitia(User panitia) throws SQLException {
        return TPS.getByPanitia(panitia.getNik());
    }

    public static int update(int id, TPS tps) throws SQLException {
        DB.connect();
        PreparedStatement sql = DB.prepareStatement(
                "UPDATE `tps` SET " +
                        "`lokasi`=?," +
                        "`nik_panitia`=? " +
                        "WHERE id = id"
        );
        sql.setString(1, tps.getLokasi());
        sql.setString(2, tps.getPanitia().getNik());
        int rs = DB.update(sql);
        DB.disconnect();
        return rs;
    }

    public static int delete(int id) throws SQLException {
        DB.connect();
        PreparedStatement sql = DB.prepareStatement("DELETE FROM TPS " +
                "WHERE `id`=?");
        sql.setInt(1,id);
        int rs = DB.update(sql);
        DB.disconnect();
        return rs;
    }
    public static int delete(TPS tps) throws SQLException {
        return delete(tps.getId());
    }

    public static void main(String[] args) {
        try {
            TPS tps = new TPS("bandung", "");
            TPS.create(tps);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
