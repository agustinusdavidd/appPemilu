package Model;

public class Remember {

    private static String nikLogin;
    private static String nama;

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
}
