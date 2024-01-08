package Rules;

public class Validator {

    public static validationResult validateNIK(String nik) {
        return new validationResult(!nik.isEmpty() && nik.length() == 16);
    }

    public static validationResult validatePassword(String password) {
        return new validationResult(!password.isEmpty() && password.length() > 5);
    }

}

