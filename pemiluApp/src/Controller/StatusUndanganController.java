package Controller;

import Model.Remember;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class StatusUndanganController {

    @FXML
    private Text isInvitedHeader;
    @FXML
    private Text namaPemilihHeader;
    @FXML
    private Text lokasiTPSHeader;
    @FXML
    private Text hormat;
    @FXML
    private Text pembukaan;
    @FXML
    private Text tanggal;
    @FXML
    private Text tanggal2;
    @FXML
    private Text tempat;
    @FXML
    private Text alamat;
    @FXML
    private Text alamat2;
    @FXML
    private Text jam;
    @FXML
    private Text jam2;
    @FXML
    private Text closing;
    @FXML
    private Text closing2;
    @FXML
    private ImageView qr;
    @FXML
    private Button closeButton;
    Remember r = new Remember();
    private SceneController sc = new SceneController();

    public void startStatus(){
        if (r.getIsInvited()){
            namaPemilihHeader.setText(r.getNama());
            lokasiTPSHeader.setText("TPS " + r.getTps());

            isInvitedHeader.setText("Undangan");
            hormat.setText("Yth.");
            pembukaan.setText("Anda diundang untuk mengikuti Pemilihan Umum yang akan dilaksanakan pada :");
            tanggal.setText("Hari/Tanggal");
            tanggal2.setText("Jumat/14 Februari 2024");
            tempat.setText("Tempat");
            alamat.setText("Alamat");
            alamat2.setText("Jl. Komunikasi Nomor 1, Bojongsoang");
            jam.setText("Jam");
            jam2.setText("09.00 WIB - 18.00 WIB");
            closing.setText("Ayo, gunakan hak pilih anda, untuk Indonesia yang lebih baik.");
            closing2.setText("1$hjsfo2fd-2mknoenm-34$jo#@no-ADmkv)(d");
            qr.setVisible(true);
        } else {
            isInvitedHeader.setText("Anda Belum Menerima Undangan");
            hormat.setText("");
            namaPemilihHeader.setText("");
            lokasiTPSHeader.setText("");
            pembukaan.setText("Anda saat ini belum menerima undangan untuk mengikuti Pemilihan Umum. Apabila anda layak untuk melakukan pemilihan umum namun belum menerima surat undangan, mohon menunggu petugas kami untuk mengirimkan surat undangan kepada anda paling lambat 10 Februari 2024.");
            tanggal.setText("");
            tanggal2.setText("");
            tempat.setText("");
            alamat.setText("");
            alamat2.setText("");
            jam.setText("");
            jam2.setText("");
            closing.setText("");
            closing2.setText("");
            qr.setVisible(false);
        }
    }

    public void closeButtonOnAction(ActionEvent e) {
        Node sourceNode = (Node) closeButton;
        Stage currentStage = (Stage) sourceNode.getScene().getWindow();

        try {
            sc.switchToDashboard(e);
            currentStage.close();
        } catch (Exception exception) {
            System.out.println(exception);
        }
    }
}
