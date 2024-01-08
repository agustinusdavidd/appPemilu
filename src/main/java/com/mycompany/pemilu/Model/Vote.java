package com.mycompany.pemilu.Model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Vote {
    private int id;
    private String nik_user;
    private int id_calon;
    private int id_tps;
    private User user = null;
    private Calon calon = null;
    private TPS tps = null;

    protected Vote(int id, String nik_user, int id_calon, int id_tps) {
        this.id = id;
        this.nik_user = nik_user;
        this.id_calon = id_calon;
        this.id_tps = id_tps;
    }

    protected Vote(int id, User user, Calon calon, TPS tps) {
        this.user = user;
        this.calon = calon;
        this.tps = tps;
        this.id = id;
        this.nik_user = user.getNik();
        this.id_calon = calon.getId();
        this.id_tps = tps.getId();
    }

    public Vote(User user, Calon calon, TPS tps) {
        this.user = user;
        this.nik_user = user.getNik();
        this.calon = calon;
        this.id_calon = calon.getId();
        this.tps = tps;
        this.id_tps = tps.getId();
    }

    public Vote(String nik_user, int id_calon, int id_tps) {
        this.nik_user = nik_user;
        this.id_calon = id_calon;
        this.id_tps = id_tps;
    }

    public int getId() {
        return id;
    }

    protected void setId(int id) {
        this.id = id;
    }

    public User getUser() throws SQLException {
        if (user == null) {
            this.user = User.getByNIK(nik_user);
        }
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        this.nik_user = user.getNik();
    }

    public Calon getCalon() throws SQLException {
        if (calon == null) {
            this.calon = Calon.getById(id_calon);
        }
        return calon;
    }

    public void setCalon(Calon calon) {
        this.calon = calon;
        this.id_calon = calon.getId();
    }

    public TPS getTps() throws SQLException {
        if (tps == null) {
            this.tps = TPS.getById(id_tps);
        }
        return tps;
    }

    public void setTps(TPS tps) {
        this.tps = tps;
        this.id_tps = tps.getId();
    }

    public static int create(String nik_user, int id_calon, int id_tps) throws SQLException {
        DB.connect();
        PreparedStatement sql = DB.prepareStatement(
                "INSERT INTO votes(`nik_user`, `id_calon`,`id_tps`) " +
                        "VALUES (?,?,?)"
        );
        sql.setString(1, nik_user);
        sql.setInt(2, id_calon);
        sql.setInt(3, id_tps);
        int rs = DB.update(sql);
        DB.disconnect();
        return rs;
    }

    public static ArrayList<Vote> getAll() throws SQLException {
        DB.connect();
        String sql = "SELECT * FROM votes";
        ResultSet rs = DB.query(sql);
        ArrayList<Vote> allVotes = new ArrayList<>();
        while (rs.next()) {
            allVotes.add(new Vote(
                    rs.getInt("votes.id"),
                    rs.getString("votes.nik_user"),
                    rs.getInt("votes.id_calon"),
                    rs.getInt("votes.id_tps")
            ));
        }
        DB.disconnect();
        return allVotes;
    }

    public static Vote getUserVote(String nik_user) throws SQLException {
        DB.connect();
        PreparedStatement sql = DB.prepareStatement("SELECT * FROM votes WHERE `nik_user`=?");
        sql.setString(1, nik_user);
        ResultSet rs = DB.query(sql);
        rs.next();
        Vote vote = new Vote(
                rs.getInt("votes.id"),
                rs.getString("votes.nik_user"),
                rs.getInt("votes.id_calon"),
                rs.getInt("votes.id_tps")
        );
        DB.disconnect();
        return vote;
    }

    public static Vote getUserVote(User user) throws SQLException {
        return getUserVote(user.getNik());
    }

    public static ArrayList<Vote> getAllTPSVotes(int id_tps) throws SQLException {
        DB.connect();
        PreparedStatement sql = DB.prepareStatement("SELECT * FROM votes WHERE `id_tps`=?");
        sql.setInt(1, id_tps);
        ResultSet rs = DB.query(sql);
        ArrayList<Vote> allVotes = new ArrayList<>();
        while (rs.next()) {
            allVotes.add(new Vote(
                    rs.getInt("votes.id"),
                    rs.getString("votes.nik_user"),
                    rs.getInt("votes.id_calon"),
                    rs.getInt("votes.id_tps")
            ));
        }
        DB.disconnect();
        return allVotes;
    }

    public static ArrayList<Vote> getAllTPSVotes(TPS tps) throws SQLException {
        return getAllTPSVotes(tps.getId());
    }

    public static ArrayList<Vote> getAllCalonVotes(int id_calon) throws SQLException {
        DB.connect();
        PreparedStatement sql = DB.prepareStatement("SELECT * FROM votes WHERE `id_calon`=?");
        sql.setInt(1, id_calon);
        ResultSet rs = DB.query(sql);
        ArrayList<Vote> allVotes = new ArrayList<>();
        while (rs.next()) {
            allVotes.add(new Vote(
                    rs.getInt("votes.id"),
                    rs.getString("votes.nik_user"),
                    rs.getInt("votes.id_calon"),
                    rs.getInt("votes.id_tps")
            ));
        }
        DB.disconnect();
        return allVotes;
    }

    public static ArrayList<Vote> getAllCalonVotes(Calon calon) throws SQLException {
        return getAllCalonVotes(calon.getId());
    }

    public static int update(int id, Vote newVote) throws SQLException {
        DB.connect();
        PreparedStatement sql = DB.prepareStatement(
                "UPDATE `votes` SET `nik_user`=?,`id_calon`=?,`id_tps`=? WHERE `id`=?"
        );
        sql.setString(1, newVote.getUser().getNik());
        sql.setInt(2, newVote.getCalon().getId());
        sql.setInt(3, newVote.getTps().getId());
        sql.setInt(4, id);
        int rs = DB.update(sql);
        DB.disconnect();
        return rs;
    }

    public static int delete(int id) throws SQLException {
        DB.connect();
        PreparedStatement sql = DB.prepareStatement(
                "DELETE FROM `votes` WHERE `id`=?"
        );
        sql.setInt(1,id);
        int rs = DB.update(sql);
        DB.disconnect();
        return rs;
    }

    public static int delete(Vote vote) throws SQLException {
        return delete(vote.getId());
    }

}
