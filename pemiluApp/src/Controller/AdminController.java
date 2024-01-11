package Controller;

import Model.Remember;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AdminController {

    @FXML
    private Text nameHeader;
    @FXML
    private Button inputButton;
    @FXML
    private Button petugasButton;
    @FXML
    private Button undanganButton;
    @FXML
    private Button backButton;
    Remember r = new Remember();
    private SceneController sc = new SceneController();

    public void startAdmin(){
        nameHeader.setText("Selamat Datang, " + r.getNama());
    }

    public void inputButtonOnAction(ActionEvent e){
        Node sourceNode = (Node) backButton;
        Stage currentStage = (Stage) sourceNode.getScene().getWindow();

        try {
            sc.switchToInput(e);
            currentStage.close();
        } catch (Exception exception) {
            System.out.println(exception);
        }
    }

    public void petugasButtonOnAction(ActionEvent e){
        Node sourceNode = (Node) backButton;
        Stage currentStage = (Stage) sourceNode.getScene().getWindow();

        try {
            sc.switchToPetugas(e);
            currentStage.close();
        } catch (Exception exception) {
            System.out.println(exception);
        }
    }

    public void undanganButtonOnAction(ActionEvent e){
        Node sourceNode = (Node) backButton;
        Stage currentStage = (Stage) sourceNode.getScene().getWindow();

        try {
            sc.switchToUndangan(e);
            currentStage.close();
        } catch (Exception exception) {
            System.out.println(exception);
        }
    }

    public void backButtonOnAction(ActionEvent e){
        Node sourceNode = (Node) backButton;
        Stage currentStage = (Stage) sourceNode.getScene().getWindow();

        try {
            sc.switchToDashboard(e);
            currentStage.close();
        } catch (Exception exception) {
            System.out.println(exception);
        }
    }

}
