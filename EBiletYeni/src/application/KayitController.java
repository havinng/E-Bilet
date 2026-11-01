package application;

import dao.UsersDao;
import model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;

public class KayitController {

    @FXML private TextField txtKullaniciAd;
    @FXML private PasswordField txtSifre;
    @FXML private TextField txtMail;
    @FXML private Label lblMesaj;

    private final UsersDao usersDao = new UsersDao();

    @FXML
    public void handleRegister() {
        String kullaniciAd = txtKullaniciAd.getText().trim();
        String sifre = txtSifre.getText().trim();
        String mail = txtMail.getText().trim();

 
        if (kullaniciAd.isEmpty() || sifre.isEmpty() || mail.isEmpty()) {
            mesajGoster("Lütfen tüm alanları doldurunuz!", Color.RED);
            return;
        }

  
        if (usersDao.kullaniciVarMi(kullaniciAd)) {
            mesajGoster("Bu kullanıcı adı zaten alınmış!", Color.RED);
            return;
        }
        User yeniUye = new User(kullaniciAd, sifre, mail);
        boolean basariliMi = usersDao.uyeKaydet(yeniUye);

        if (basariliMi) {
            mesajGoster("Kayıt işlemi başarıyla tamamlandı!", Color.GREEN);
            formuTemizle();
        } else {
            mesajGoster("Sistemsel bir hata oluştu, tekrar deneyiniz.", Color.RED);
        }
    }

    @FXML
    void geriDon(ActionEvent event) {
        try {
            AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("Sample.fxml"));
            
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 600, 500);
            stage.setScene(scene);
            stage.show(); 
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mesajGoster(String mesaj, Color renk) {
        lblMesaj.setText(mesaj);
        lblMesaj.setTextFill(renk);
    }

    private void formuTemizle() {
        txtKullaniciAd.clear();
        txtSifre.clear();
        txtMail.clear();
    }
}