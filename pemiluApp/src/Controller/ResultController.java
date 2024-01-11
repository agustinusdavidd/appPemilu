package Controller;

import Model.Calon;
import Model.Vote;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ResultController {

    @FXML
    private Button backButton;
    @FXML
    private Label presiden1Header;
    @FXML
    private Label presiden2Header;
    @FXML
    private Label wakil1Header;
    @FXML
    private Label wakil2Header;
    @FXML
    private Label hasil1;
    @FXML
    private Label hasil2;
    private SceneController sc = new SceneController();
    int r1 = 0, r2 = 0;

    public void startResult(){

        try {
            Calon c1 = new Calon(), c2 = new Calon();
            ArrayList<Vote> v = Vote.getAll();

            for(Vote voteResult : v){
                if(voteResult.getId_calon() == 1){
                    r1+=1;
                } else {
                    r2 += 1;
                }
            }

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

            presiden1Header.setText(c1.getCapres().getNama());
            presiden2Header.setText(c2.getCapres().getNama());

            wakil1Header.setText(c1.getCawapres().getNama());
            wakil2Header.setText(c2.getCawapres().getNama());

            hasil1.setText(String.valueOf(r1));
            hasil2.setText(String.valueOf(r2));

        } catch (Exception e) {
            System.out.println(e.getMessage());
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
}
