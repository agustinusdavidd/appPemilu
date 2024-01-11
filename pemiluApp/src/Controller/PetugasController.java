package Controller;

import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class PetugasController {

    @FXML
    private ComboBox tpsComboBox;
    @FXML
    private TextField petugasTextField;
    @FXML
    private Button addButon;
    @FXML
    private Button backButton;
    private SceneController sc = new SceneController();

    public void startPetugas(){
        ObservableList<Integer> items = FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        tpsComboBox.setItems(items);
        tpsComboBox.getSelectionModel();
    }

    public void addButtonOnAction(ActionEvent e) {
        int tps = (int) tpsComboBox.getSelectionModel().getSelectedItem();
        String tpsString = String.valueOf(tps);
        String nik = petugasTextField.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Konfirmasi");
        alert.setHeaderText(null);
        alert.setContentText("Apakah Anda yakin ingin menambahkan petugas?");

        // Mendapatkan hasil dari dialog
        ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);

        // Mengecek apakah tombol OK ditekan
        if (result == ButtonType.OK) {
            User u = new User();
            try {
                u.setPetugas(nik, tpsString);
            } catch (Exception exception) {
                System.out.println(exception);
            }
            System.out.println("Petugas ditambahkan");
            petugasTextField.setText("");
        } else {
            System.out.println("Konfirmasi dibatalkan");
        }
    }

    public void backButtonOnAction(ActionEvent e) {
        Node sourceNode = (Node) backButton;
        Stage currentStage = (Stage) sourceNode.getScene().getWindow();

        try {
            sc.switchToAdmin(e);
            currentStage.close();
        } catch (Exception exception) {
            System.out.println(exception);
        }
    }
}
