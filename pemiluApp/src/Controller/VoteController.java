package Controller;

import Model.Calon;
import Model.Remember;
import Model.User;
import Model.Vote;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class VoteController {

    @FXML
    private Button resultButton;
    @FXML
    private Button backButton;
    @FXML
    private Label visiMisi1Header;
    @FXML
    private Label visiMisi2Header;
    @FXML
    private Label presiden1Header;
    @FXML
    private Label wakil1Header;
    @FXML
    private Label presiden2Header;
    @FXML
    private Label wakil2Header;
    @FXML
    private Button choose1Button;
    @FXML
    private Button choose2Button;
    @FXML
    private Text isVoteHeader;
    @FXML
    private Label nomor1;
    @FXML
    private Label nomor2;
    @FXML
    private ImageView photo1;
    @FXML
    private ImageView photo2;
    @FXML
    private Label visiMisi1;
    @FXML
    private Label visiMisi2;

    private SceneController sc = new SceneController();
    Remember r = new Remember();
    Vote v = new Vote();
    Boolean sudahMemilih = false;
    User us = new User();

    public void startVote(){
        try {
            User u = User.getByNIK(r.getNikLogin());
            Boolean isVote = u.isSudahMemilih();
            System.out.println(isVote);

            Calon c1 = new Calon(), c2 = new Calon();

            ArrayList<Calon> c = Calon.getAll();
            try {
                for(Calon cs : c){
                    if(cs.getId() == 1){
                        c1 = cs;
                    } else if(cs.getId() == 2) {
                        c2 = cs;
                    }
                }
            } catch (Exception e){
                System.out.println(e.getMessage());
            }

            if(isVote){
                isVoteHeader.setVisible(true);
                nomor1.setVisible(false);
                nomor2.setVisible(false);
                photo1.setVisible(false);
                photo2.setVisible(false);
                visiMisi1.setVisible(false);
                visiMisi2.setVisible(false);
                visiMisi1Header.setVisible(false);
                visiMisi2Header.setVisible(false);
                presiden1Header.setVisible(false);
                presiden2Header.setVisible(false);
                wakil1Header.setVisible(false);
                wakil2Header.setVisible(false);
                choose1Button.setVisible(false);
                choose2Button.setVisible(false);
            } else {
                isVoteHeader.setVisible(false);
                visiMisi1Header.setText(c1.getVisiMisi());
                presiden1Header.setText(c1.getCapres().getNama());
                wakil1Header.setText(c1.getCawapres().getNama());

                visiMisi2Header.setText(c2.getVisiMisi());
                presiden2Header.setText(c2.getCapres().getNama());
                wakil2Header.setText(c2.getCawapres().getNama());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void resultButtonOnAction(ActionEvent e) {
        Node sourceNode = (Node) backButton;
        Stage currentStage = (Stage) sourceNode.getScene().getWindow();

        try {
            sc.switchToResult(e);
            currentStage.close();
        } catch (Exception exception) {
            System.out.println(exception);
        }
    }

    public void backButtonOnAction(ActionEvent e) {
        Node sourceNode = (Node) backButton;
        Stage currentStage = (Stage) sourceNode.getScene().getWindow();

        try {
            sc.switchToDashboard(e);
            currentStage.close();
        } catch (Exception exception) {
            System.out.println(exception);
        }
    }

    public void choose2OnAction(ActionEvent e) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Konfirmasi");
        alert.setHeaderText(null);
        alert.setContentText("Apakah Anda yakin ingin memilih pasangan nomor urut 2?");

        // Mendapatkan hasil dari dialog
        ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);

        // Mengecek apakah tombol OK ditekan
        if (result == ButtonType.OK) {
            alert.setContentText("Pilihan yang sudah dipilih, tidak dapat diubah kembali. Konfirmasi?");
            // Mendapatkan hasil dari dialog
            ButtonType result2 = alert.showAndWait().orElse(ButtonType.CANCEL);

            // Mengecek apakah tombol OK ditekan
            if (result2 == ButtonType.OK) {
                choose1Button.isDisable();
                choose2Button.isDisable();
                try {
                    v.create(r.getNikLogin(), 2, Integer.parseInt(r.getTps()));
                    us.setMemilih(r.getNikLogin(), true);
                } catch (Exception exception) {
                    System.out.println(exception);
                }
//                sudahMemilih = true;
                startVote();
            } else {
                System.out.println("Konfirmasi dibatalkan");
            }
        } else {
            System.out.println("Konfirmasi dibatalkan");
        }
    }

    public void choose1OnAction(ActionEvent e) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Konfirmasi");
        alert.setHeaderText(null);
        alert.setContentText("Apakah Anda yakin ingin memilih pasangan nomor urut 2?");

        // Mendapatkan hasil dari dialog
        ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);

        // Mengecek apakah tombol OK ditekan
        if (result == ButtonType.OK) {
            alert.setContentText("Pilihan yang sudah dipilih, tidak dapat diubah kembali. Konfirmasi?");
            // Mendapatkan hasil dari dialog
            ButtonType result2 = alert.showAndWait().orElse(ButtonType.CANCEL);

            // Mengecek apakah tombol OK ditekan
            if (result2 == ButtonType.OK) {
                try {
                    v.create(r.getNikLogin(), 2, Integer.parseInt(r.getTps()));
                    us.setMemilih(r.getNikLogin(), true);
                } catch (Exception exception) {
                    System.out.println(exception);
                }
                choose1Button.isDisabled();
                choose2Button.isDisabled();
//                sudahMemilih = true;
                startVote();
            } else {
                System.out.println("Konfirmasi dibatalkan");
            }
        } else {
            System.out.println("Konfirmasi dibatalkan");
        }
    }
}
