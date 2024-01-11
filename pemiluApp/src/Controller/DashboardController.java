package Controller;

import Model.Remember;
import Model.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.*;

public class DashboardController {

    @FXML
    private Text welcomeHeader;
    @FXML
    private Text isMemilihHeader;
    @FXML
    private Text tpsHeader;
    @FXML
    private Button voteButton;
    @FXML
    private Button resultButton;
    @FXML
    private Button undanganButton;
    @FXML
    private Button adminButton;
    @FXML
    private Button logoutButton;
    Remember r = new Remember();
    private String nik = r.getNikLogin();
    private String nama = r.getNama();
    Alert.AlertType type = Alert.AlertType.WARNING;
    Alert alert = new Alert(type, "");
    private SceneController sc = new SceneController();

    public void startDashboard() {
        // check if is admin
        try {
            User user = User.getByNIK(nik);
            r.setIsAdmin(user.isAdmin());

            if(!r.getIsAdmin()){
                adminButton.setVisible(false);
            }
        } catch (Exception e) {
            alert.initModality(Modality.APPLICATION_MODAL);

            alert.getDialogPane().setContentText(e.getMessage());
            alert.getDialogPane().setHeaderText("Perhatian");
            alert.showAndWait();
        }
        // set header
        setHeader();

    }

    public void voteButtonOnAction(ActionEvent e){
        Node sourceNode = (Node) voteButton;
        Stage currentStage = (Stage) sourceNode.getScene().getWindow();

        try {
            sc.switchToVote(e);
            currentStage.close();

        } catch (Exception exception) {
            System.out.println(exception);
        }
    }

    public void resultButtonOnAction(ActionEvent e) {
        Node sourceNode = (Node) voteButton;
        Stage currentStage = (Stage) sourceNode.getScene().getWindow();

        try {
            sc.switchToResult(e);
            currentStage.close();

        } catch (Exception exception) {
            System.out.println(exception);
        }
    }

    public void undanganButtonOnAction(ActionEvent e) {
        Node sourceNode = (Node) voteButton;
        Stage currentStage = (Stage) sourceNode.getScene().getWindow();

        try {
            sc.switchToStatusUndangan(e);
            currentStage.close();
        } catch (Exception exception) {
            System.out.println(exception);
        }
    }

    public void logoutButtonOnAction(ActionEvent e) {
        Platform.exit();
    }

    public void adminButtonOnAction(ActionEvent e) {
        Node sourceNode = (Node) adminButton;
        Stage currentStage = (Stage) sourceNode.getScene().getWindow();

        if(r.getIsAdmin()){
            try {
                sc.switchToAdmin(e);
                currentStage.close();
            } catch (Exception exception) {
                System.out.println(exception);
            }
        }
    }

    public void setHeader() {
        welcomeHeader.setText("Selamat Datang, " + nama);

        try {
            User user = User.getByNIK(nik);
            r.setIsInvited(user.isInvited());

            // dapatkan value tps
            String tps = String.valueOf(user.getTPS().getId());
            r.setTps(tps);

            // check apakah sudah memilih
            Boolean isMemilih = user.isSudahMemilih();

            if(user.isInvited()){
                tpsHeader.setText("Anda Terdaftar di TPS " + tps);
            } else {
                tpsHeader.setText("Anda Belum Mendapatkan Surat Undangan");
            }

            if(isMemilih){
                isMemilihHeader.setText("Terima Kasih Sudah Menggunakan Hak Pilih Anda");
            } else {
                isMemilihHeader.setText("Anda Belum Menggunakan Hak Pilih Anda");
            }


        } catch (Exception e) {
            alert.initModality(Modality.APPLICATION_MODAL);

            alert.getDialogPane().setContentText(e.getMessage());
            alert.getDialogPane().setHeaderText("Perhatian");
            alert.showAndWait();
        }
    }
}
