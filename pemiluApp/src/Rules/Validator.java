package Rules;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Validator {

    public static validationResult validateNIK(String nik) {
        return new validationResult(!nik.isEmpty() && nik.length() == 16 && validateAge(nik).getStatus());
    }

    public static validationResult validatePassword(String password) {
        return new validationResult(!password.isEmpty() && password.length() > 5);
    }

    public static validationResult validateNama(String nama) {
        return new validationResult(!nama.isEmpty() && nama.length() > 3);
    }

    public static validationResult validateAge(String nik) {

        String tanggal = nik.substring(6,12);

        LocalDate tanggalLahir = LocalDate.parse(tanggal, DateTimeFormatter.ofPattern("ddMMyy"));

        LocalDate now = LocalDate.now();

        int umur = now.getYear() - tanggalLahir.getYear();
        if(now.getMonthValue() < tanggalLahir.getMonthValue() || now.getMonthValue() == tanggalLahir.getMonthValue() && now.getDayOfMonth() < tanggalLahir.getDayOfMonth()){
            umur--;
        }

        return new validationResult(umur >= 17);
    }

    public validationResult validateMatchPassword(String password, String konfirmasiPassword) {

        return new validationResult(password.equals(konfirmasiPassword));

    }
}

