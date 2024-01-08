package Controller;

import Network.Database;
import Rules.Validator;
import Rules.validationResult;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class RegisterController {

    @FXML
    private Button loginButton;
    @FXML
    private Label statusLabel;
    @FXML
    private Button registerButton;
    @FXML
    private Button closeButton;
    @FXML
    private TextField nikTextField;
    @FXML
    private TextField namaTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;
    private Validator validator = new Validator();


    private SceneController sc = new SceneController();


    public void registerButtonOnAction(ActionEvent e) {

        String nik = nikTextField.getText();
        String nama = namaTextField.getText();
        String password = passwordField.getText();
        String konfirmasiPassword = confirmPasswordField.getText();

        validationResult isNIKValid = validator.validateNIK(nik);
        validationResult isNamaValid = validator.validateNama(nama);
        validationResult isPasswordValid = validator.validatePassword(password);
        validationResult isPasswordMatch = validator.validateMatchPassword(password, konfirmasiPassword);

        System.out.println(isNIKValid.getStatus() + "" + isNamaValid.getStatus() + "" + isPasswordValid.getStatus() + "" + isPasswordMatch.getStatus());

        Node sourceNode = (Node) registerButton;
        Stage currentStage = (Stage) sourceNode.getScene().getWindow();

        Alert.AlertType type = Alert.AlertType.WARNING;
        Alert alert = new Alert(type, "");

        alert.initModality(Modality.APPLICATION_MODAL);

        if(isNIKValid.getStatus() && isNamaValid.getStatus() && isPasswordValid.getStatus() && isPasswordMatch.getStatus()){
            String hashedPassword = hashPassword(password);
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");

                Connection connection = DriverManager.getConnection(Database.DB_URL, Database.DB_USER, Database.DB_PASSWORD);

                // Prepare the SQL statement
                String query = "INSERT INTO users (nama, nik, password, isAdmin, sudahMemilih) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
                    preparedStatement.setString(1, nama);
                    preparedStatement.setString(2, nik);
                    preparedStatement.setString(3, hashedPassword);
                    preparedStatement.setBoolean(4, false); // Set it to true if the user is an admin, otherwise false
                    preparedStatement.setBoolean(5, false); // Set it based on your application logic

                    int rowsAffected = preparedStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        sc.switchToLogin(e);
                        currentStage.close();
                    }
                } catch (Exception exception) {
                    alert.getDialogPane().setContentText(exception.getMessage());
                    alert.getDialogPane().setHeaderText("Perhatian");
                    alert.showAndWait();

                    System.out.println("Register Failed");
                }
            } catch (Exception exception){
                alert.getDialogPane().setContentText(exception.getMessage());
                alert.getDialogPane().setHeaderText("Perhatian");
                alert.showAndWait();

                System.out.println("Register Failed");
            }
        }

    }

    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public void loginButtonOnAction(ActionEvent e) {

        Node sourceNode = (Node) registerButton;
        Stage currentStage = (Stage) sourceNode.getScene().getWindow();

        try {
            sc.switchToLogin(e);
            currentStage.close();

        } catch (Exception exception) {
            System.out.println(exception);
        }
    }

    public void closeButtonOnAction(ActionEvent e) {
        Platform.exit();
    }
}
