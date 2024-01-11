package Controller;

import Model.Calon;
import Model.Remember;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class UndanganController {

    @FXML
    private ComboBox tpsComboBox;
    @FXML
    private TextField nikTextField;
    @FXML
    private Button addButon;
    @FXML
    private Button backButton;
    private SceneController sc = new SceneController();

    public void startUndangan(){
        ObservableList<Integer> items = FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        tpsComboBox.setItems(items);
        tpsComboBox.getSelectionModel();
    }

    public void addButtonOnAction(ActionEvent e) {
        int tps = (int) tpsComboBox.getSelectionModel().getSelectedItem();
        String tpsString = String.valueOf(tps);
        String nik = nikTextField.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Konfirmasi");
        alert.setHeaderText(null);
        alert.setContentText("Apakah Anda yakin ingin mengirimkan undangan?");

        // Mendapatkan hasil dari dialog
        ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);

        // Mengecek apakah tombol OK ditekan
        if (result == ButtonType.OK) {
            User u = new User();
            try {
                u.kirimUndangan(nik, tpsString);
            } catch (Exception exception) {
                System.out.println(exception);
            }
            System.out.println("Undangan Terkirim");
            nikTextField.setText("");
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
