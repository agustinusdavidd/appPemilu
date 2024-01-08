package Controller;

import Network.Database;
import Rules.Validator;
import Rules.validationResult;
import UI.Dashboard;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class LoginController {

    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;
    @FXML
    private Button closeButton;
    @FXML
    private Label statusLabel;
    @FXML
    private TextField nikTextField;
    @FXML
    private PasswordField passwordField;

    private Boolean loginStatus = false;

    private Validator validator = new Validator();
    private SceneController sc = new SceneController();

    public void loginButtonOnAction(ActionEvent e) {

        Node sourceNode = (Node) loginButton;
        Stage currentStage = (Stage) sourceNode.getScene().getWindow();

        String nik = nikTextField.getText();
        String password = passwordField.getText();

        validationResult isNIKValid = validator.validateNIK(nik);
        validationResult isPasswordValid = validator.validatePassword(password);

        Alert.AlertType type = Alert.AlertType.WARNING;
        Alert alert = new Alert(type, "");

        alert.initModality(Modality.APPLICATION_MODAL);

        if(isNIKValid.getStatus() && isPasswordValid.getStatus()){
            try {
                Connection conn = DriverManager.getConnection(Database.DB_URL, Database.DB_USER, Database.DB_PASSWORD);
                String query = "SELECT * FROM users WHERE nik = ?";

                try (PreparedStatement ps = conn.prepareStatement(query)) {
                    ps.setString(1, nik);
                    ResultSet rs = ps.executeQuery();

                    if(rs.next()){
                        String hashedPasswordDB = rs.getString("password");
                        String username = rs.getString("nama");

                        if(BCrypt.checkpw(password, hashedPasswordDB)) {
                            loginStatus = true;

                            Dashboard d = new Dashboard();
                            d.setVisible(true);
                            d.setLocationRelativeTo(null);

                            currentStage.close();
                        }
                    }
                } catch (Exception exception) {
                    alert.getDialogPane().setContentText(exception.getMessage());
                    alert.getDialogPane().setHeaderText("Perhatian");
                    alert.showAndWait();

                    System.out.println("Login Failed");
                }

            } catch (Exception exception){
                alert.getDialogPane().setContentText(exception.getMessage());
                alert.getDialogPane().setHeaderText("Perhatian");
                alert.showAndWait();

                System.out.println("Login Failed");
            }
        }

        if(loginStatus) {
            statusLabel.setText("Login Success");
            statusLabel.setTextFill(Color.GREEN);
        } else {
            statusLabel.setText("Login Failed");
            statusLabel.setTextFill(Color.RED);
        }

    }
    public void registerButtonOnAction(ActionEvent e) {

        Node sourceNode = (Node) loginButton;
        Stage currentStage = (Stage) sourceNode.getScene().getWindow();

        try {
            sc.switchToRegister(e);
            currentStage.close();

        } catch (Exception exception) {
            System.out.println(exception);
        }

    }
    public void closeButtonOnAction(ActionEvent e) {
        Platform.exit();
    }

}
