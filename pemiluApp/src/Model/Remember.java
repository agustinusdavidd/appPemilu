package Model;

public class Remember {

    private static String nikLogin;
    private static String nama;
    private static Boolean isInvited;
    public static String tps;
    public static Boolean isAdmin;

    public Remember(String n, String nm){
        this.nikLogin = n;
        this.nama = nm;
    }
    public Remember(){};
    public static String getNama() {
        return nama;
    }
    public static String getNikLogin() {
        return nikLogin;
    }
    public static void setIsInvited(Boolean b){
        isInvited = b;
    }
    public static Boolean getIsInvited() { return  isInvited; }
    public static void setTps(String t) {
        tps = t;
    }
    public static String getTps(){ return tps; }
    public static void setIsAdmin(Boolean a) {
        isAdmin = a;
    }
    public static Boolean getIsAdmin(){ return isAdmin; }
}
