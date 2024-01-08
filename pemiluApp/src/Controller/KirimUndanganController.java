package Controller;

public class KirimUndanganController {

    private int nomorTps;
    private String nik;

    public KirimUndanganController(){};

    public void setTps(int t) throws Exception {
        if(t < 1 || t > 10){
            throw new Exception("Nomor TPS Keliru");
        } else  {
            this.nomorTps = t;
        }
    }

    public void setNik(String n) throws Exception {
        if(n.length()<16){
            throw new Exception("NIK terlalu pendek");
        } else if (isContainLetter(n)){
            throw new Exception("NIK harus berupa huruf");
        } else if (n.length()>16){
            throw new Exception("NIK terlalu panjang");
        } else {
            this.nik = n;
        }

    }

    private boolean isContainLetter(String n) {
        for (char c : n.toCharArray()){
            if(Character.isLetter(c)){
                return true;
            }
        }
        return false;
    }

}
