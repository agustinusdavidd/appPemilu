package Controller;

import Model.Calon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class InputController {

    @FXML
    private TextField nikPresidenTextField;
    @FXML
    private TextField nikWakilTextField;
    @FXML
    private TextField visiMisiTextField;
    @FXML
    private ComboBox nomorComboBox;
    @FXML
    private Button addButton;
    @FXML
    private Button backButton;
    private SceneController sc = new SceneController();

    public void startInput(){
        ObservableList<Integer> items = FXCollections.observableArrayList(1, 2, 3, 4, 5);
        nomorComboBox.setItems(items);
        nomorComboBox.getSelectionModel();
    }
    public void addButtonOnAction(ActionEvent e) {

        String nikPresiden = nikPresidenTextField.getText();
        String nikWakilPresiden = nikWakilTextField.getText();
        String visiMisi = visiMisiTextField.getText();
        int id = (int) nomorComboBox.getSelectionModel().getSelectedItem();

        System.out.println("nik : " + nikPresiden + " " + nikWakilPresiden + " \nvisi misi : " + visiMisi + "\nid " + id);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Konfirmasi");
        alert.setHeaderText(null);
        alert.setContentText("Apakah Anda yakin ingin menambahkan?");

        // Mendapatkan hasil dari dialog
        ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);

        // Mengecek apakah tombol OK ditekan
        if (result == ButtonType.OK) {
            Calon pasangan = new Calon(id, nikPresiden, nikWakilPresiden, visiMisi);
            try {
                pasangan.create(pasangan);

                nikPresidenTextField.setText("");
                nikWakilTextField.setText("");
                visiMisiTextField.setText("");
            } catch (Exception exception) {
                System.out.println(exception);
            }
            System.out.println("Pasangan Ditambahkan");
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
