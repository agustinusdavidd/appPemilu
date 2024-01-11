package Model;

import Network.Database;

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
    public Calon(String Capres, String Cawapres, String visiMisi) {
        this.Capres = Capres;
        this.Cawapres = Cawapres;
        this.visiMisi = visiMisi;
    }

    public Calon() {}

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
    public String getVisiMisi() throws SQLException {
        return this.visiMisi == null ? null : visiMisi;
    }

    public static ArrayList<Calon> getAll() throws SQLException {
        Database.connect();
        String sql = "SELECT * FROM calons";
        ArrayList<Calon> calons = new ArrayList<>();
        ResultSet rs = Database.query(sql);
        while (rs.next()) {
            calons.add(new Calon(
                    rs.getInt("id"),
                    rs.getString("capres"),
                    rs.getString("cawapres"),
                    rs.getString("visi_misi")
            ));
        }
        Database.disconnect();
        return calons;
    }
    public static Calon getById(int id) throws SQLException {
        Database.connect();
        PreparedStatement sql = Database.prepareStatement("SELECT * FROM calons " +
                "WHERE id=?");
        sql.setInt(1, id);
        ResultSet rs = Database.query(sql);
        rs.next();
        Calon calon = new Calon(
                rs.getInt("id"),
                rs.getString("capres"),
                rs.getString("cawapres"),
                rs.getString("visi_misi")
        );
        Database.disconnect();
        return calon;
    }
    public static void create(Calon calon) throws SQLException {
        Database.connect();
        PreparedStatement sql = Database.prepareStatement("INSERT INTO calons(id, capres, cawapres, visi_misi) " +
                "VALUES (?, ?, ?, ?)");
        sql.setInt(1, calon.getId());
        sql.setString(2, calon.Capres);
        sql.setString(3, calon.Cawapres);
        sql.setString(4, calon.visiMisi);
        int rs = Database.update(sql);
        Database.disconnect();

        if(rs > 1) {
            throw new SQLException("Something went wrong");
        } else {
            System.out.println("Creating object in table Calon");
        }
    }
    public static int update(int toChange,Calon calon) throws SQLException {
        Database.connect();
        PreparedStatement sql = Database.prepareStatement("UPDATE calons " +
                "SET capres=?, cawapres=? " +
                "WHERE id=?");
        sql.setString(1, calon.getCapres().getNik());
        sql.setString(2, calon.getCawapres().getNik());
        sql.setInt(3, toChange);
        int rs = Database.update(sql);
        Database.disconnect();
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
