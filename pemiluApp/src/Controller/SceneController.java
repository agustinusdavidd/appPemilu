package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.io.IOException;

public class SceneController {

    Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    double width = screenSize.getWidth();
    double height = screenSize.getHeight();

    private Stage stage;
    private Parent root;
    private FXMLLoader loader;

    public void switchToRegister(ActionEvent e) throws IOException {
        loader = new FXMLLoader(getClass().getResource("/UI/Register.fxml"));

        showStage(e, loader);
    }

    public void switchToLogin(ActionEvent e) throws IOException {
        loader = new FXMLLoader(getClass().getResource("/UI/Login.fxml"));

        showStage(e, loader);
    }

    public void switchToDashboard(ActionEvent e) throws IOException {
        loader = new FXMLLoader(getClass().getResource("/UI/Dashboard.fxml"));
        Parent root = loader.load();

        DashboardController dc = loader.getController();
        dc.startDashboard();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));

        stage.show();
    }

    public void switchToVote(ActionEvent e) throws IOException {
        loader = new FXMLLoader(getClass().getResource("/UI/Vote.fxml"));
        Parent root = loader.load();

        VoteController vc = loader.getController();
        vc.startVote();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.initStyle(StageStyle.UNDECORATED);

        stage.show();
    }

    public void switchToResult(ActionEvent e) throws IOException {
        loader = new FXMLLoader(getClass().getResource("/UI/Result.fxml"));

        Parent root = loader.load();

        ResultController rc = loader.getController();
        rc.startResult();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.initStyle(StageStyle.UNDECORATED);

        stage.show();
    }

    public void switchToStatusUndangan(ActionEvent e) throws IOException {
        loader = new FXMLLoader(getClass().getResource("/UI/StatusUndangan.fxml"));
        Parent root = loader.load();

        StatusUndanganController su = loader.getController();
        su.startStatus();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.initStyle(StageStyle.UNDECORATED);

        stage.show();
    }

    public void switchToAdmin(ActionEvent e) throws IOException {
        loader = new FXMLLoader(getClass().getResource("/UI/AdminControl.fxml"));
        Parent root = loader.load();

        AdminController ac = loader.getController();
        ac.startAdmin();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));

        stage.show();
    }

    public void switchToInput(ActionEvent e) throws IOException {
        loader = new FXMLLoader(getClass().getResource("/UI/InputPaslon.fxml"));
        Parent root = loader.load();

        InputController ic = loader.getController();
        ic.startInput();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));

        stage.show();
    }
    public void switchToPetugas(ActionEvent e) throws IOException {
        loader = new FXMLLoader(getClass().getResource("/UI/InputPetugas.fxml"));
        Parent root = loader.load();

        PetugasController pc = loader.getController();
        pc.startPetugas();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));

        stage.show();
    }

    public void switchToUndangan(ActionEvent e) throws IOException {
        loader = new FXMLLoader(getClass().getResource("/UI/KirimUndangan.fxml"));
        Parent root = loader.load();

        UndanganController uc = loader.getController();
        uc.startUndangan();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));

        stage.show();
    }
    public void showStage(ActionEvent e, FXMLLoader loader) throws IOException{
        root = loader.load();

        stage = new Stage();
        stage.setScene(new Scene(root));
        stage.initStyle(StageStyle.UNDECORATED);

        stage.show();
    }

    public void showStageTitled(ActionEvent e, FXMLLoader loader) throws IOException{
        root = loader.load();

        stage = new Stage();
        stage.setScene(new Scene(root));

        stage.show();
    }
}
