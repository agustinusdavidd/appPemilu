package com.mycompany.pemilu.Model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Calon {
    private int id;
    private String Capres;
    private String Cawapres;
    private String visiMisi;

    public Calon(int id, String Capres, String Cawapres, String visiMisi) {
        this.id = id;
        this.Capres = Capres;
        this.Cawapres = Cawapres;
        this.visiMisi = visiMisi;
    }

    public Calon(int id, User Capres, User Cawapres, String visiMisi) {
        this.id = id;
        setCapres(Capres);
        setCawapres(Cawapres);
        this.visiMisi = visiMisi;
    }
    public Calon(int id, String Capres, User Cawapres, String visiMisi) {
        this.id = id;
        this.Capres = Capres;
        setCawapres(Cawapres);
        this.visiMisi = visiMisi;
    }
    public Calon(int id, User Capres, String Cawapres, String visiMisi) {
        this.id = id;
        setCapres(Capres);
        this.Cawapres = Cawapres;
        this.visiMisi = visiMisi;
    }
    public Calon(String Capres, String Cawapres) {
        this.Capres = Capres;
        this.Cawapres = Cawapres;
    }

    public int getId() {
        return id;
    }
    public User getCapres() throws SQLException {
        return this.Capres == null ? null : User.getByNIK(this.Capres);
    }
    public void setCapres(User capres) {
        this.Capres = capres.getNik();
    }
    public User getCawapres() throws SQLException {
        return this.Cawapres == null ? null : User.getByNIK(this.Cawapres);
    }
    public void setCawapres(User cawapres) {
        this.Cawapres = cawapres.getNik();
    }
    public void setId(int id) {
        this.id = id;
    }

    public static ArrayList<Calon> getAll() throws SQLException {
        DB.connect();
        String sql = "SELECT * FROM calons";
        ArrayList<Calon> calons = new ArrayList<>();
        ResultSet rs = DB.query(sql);
        while (rs.next()) {
            calons.add(new Calon(
                    rs.getInt("id"),
                    rs.getString("capres"),
                    rs.getString("cawapres"),
                    rs.getString("visi_misi")
            ));
        }
        DB.disconnect();
        return calons;
    }
    public static Calon getById(int id) throws SQLException {
        DB.connect();
        PreparedStatement sql = DB.prepareStatement("SELECT * FROM calons " +
                "WHERE id=?");
        sql.setInt(1, id);
        ResultSet rs = DB.query(sql);
        rs.next();
        Calon calon = new Calon(
                    rs.getInt("id"),
                    rs.getString("capres"),
                    rs.getString("cawapres"),
                    rs.getString("visi_misi")
            );
        DB.disconnect();
        return calon;
    }
    public static int create(Calon calon) throws SQLException {
        DB.connect();
        PreparedStatement sql = DB.prepareStatement("INSERT INTO calons(id, capres, cawapres) " +
                "VALUES (?, ?, ?)");
        sql.setInt(1, calon.getId());
        sql.setString(2, calon.Capres);
        sql.setString(3, calon.Cawapres);
        int rs = DB.update(sql);
        DB.disconnect();
        return rs;
    }
    public static int update(int toChange,Calon calon) throws SQLException {
        DB.connect();
        PreparedStatement sql = DB.prepareStatement("UPDATE calons " +
                "SET capres=?, cawapres=? " +
                "WHERE id=?");
        sql.setString(1, calon.getCapres().getNik());
        sql.setString(2, calon.getCawapres().getNik());
        sql.setInt(3, toChange);
        int rs = DB.update(sql);
        DB.disconnect();
        return rs;
    }

    public static void main(String[] args) {
        try {
            Calon.create(new Calon(5, "tes2", "wow", "ini visi misi"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
