package application;

import java.net.URL;
import java.util.ResourceBundle;
import dao.SeferDao;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ListCell;
import model.Admin;
import model.Sefer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
public class SeferSecimController implements Initializable {

    @FXML private Label labelHosgeldin;
    @FXML private Label labelId;
    @FXML private Label labelAd;
    @FXML private Label labelMail;
    
    @FXML private ComboBox<Sefer> seferSecim_cb;
    @FXML private ListView<Sefer> seferListesi_lv;

    private Admin loginedAdmin;
    private SeferDao seferDao = new SeferDao();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        var seferler = seferDao.getTumSeferler();
        
        // 1. ListView hücre tasarımı
        seferListesi_lv.setCellFactory(lv -> new ListCell<Sefer>() {
            @Override
            protected void updateItem(Sefer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getKalkis() + " - " + item.getFiyat() + " TL");
                }
            }
        });

        // 2. ComboBox hücre tasarımı
        seferSecim_cb.setCellFactory(lv -> new ListCell<Sefer>() {
            @Override
            protected void updateItem(Sefer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getKalkis() + " - " + item.getFiyat() + " TL");
                }
            }
        });

        // 3. ComboBox kapalı haldeki görünümü
        seferSecim_cb.setButtonCell(new ListCell<Sefer>() {
            @Override
            protected void updateItem(Sefer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getKalkis());
                }
            }
        });

        seferSecim_cb.setItems(seferler);
        seferListesi_lv.setItems(seferler);

        // Seçim dinleyicisi
        seferListesi_lv.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                System.out.println("Seçilen Rota: " + newVal.getKalkis());
            }
        });
    }

    @FXML
    public void handleKoltukSecim() {
        System.out.println("-> Butona basıldı, handleKoltukSecim çalıştı!");

        // Hem ListView'dan hem de ComboBox'tan seçimi kontrol et
        Sefer secilen = seferListesi_lv.getSelectionModel().getSelectedItem();
        if (secilen == null) {
            secilen = seferSecim_cb.getSelectionModel().getSelectedItem();
        }
        
        if (secilen != null) {
            System.out.println("-> Seçilen sefer: " + secilen.getKalkis());
            try {
                // FXML yükleyicisi
                FXMLLoader loader = new FXMLLoader(getClass().getResource("KoltukSecim.fxml"));
                Parent root = loader.load();
                
                // Kontrolcüye seçilen sefer bilgisini gönderiyoruz
                KoltukSecimController controller = loader.getController();
                controller.setSefer(secilen);
                
                // Sahne değişimi
                Stage stage = (Stage) seferListesi_lv.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
                System.out.println("-> Sahne değiştirme kodları başarıyla tamamlandı!");
                
            } catch (IOException e) {
                System.out.println("!!! FXML Yükleme Hatası Yakalandı !!!");
                e.printStackTrace();
            }
        } else {
            System.out.println("-> HATA: Henüz ne listeden ne de combobox'tan seçim yapılmadı!");
        }       
    }           

    public void setUser(Admin admin) {
        this.loginedAdmin = admin;
        if (loginedAdmin != null) {
            if (labelHosgeldin != null) labelHosgeldin.setText("Hoşgeldin, " + loginedAdmin.getAdminAd());
            if (labelId != null) labelId.setText("ID: " + loginedAdmin.getId());
            if (labelAd != null) labelAd.setText("Ad: " + loginedAdmin.getAdminAd());
            if (labelMail != null) labelMail.setText("Mail: " + loginedAdmin.getAdminMail());
        }
    }
}