package application;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import dao.AdminDao;  // Paket ismin doğru
import model.Admin;   // Paket ismin doğru

public class KullaniciPanelController {

    @FXML
    private Button girisButton;

    @FXML
    private TextField email_tf;

    @FXML
    private PasswordField sifre_pf;

    @FXML
    void handleGiris(ActionEvent event) {
        AdminDao adminDao = new AdminDao();
        String email = email_tf.getText();
        String sifre = sifre_pf.getText();
        
        Admin girisYapanAdmin = adminDao.login(email, sifre);

        if (girisYapanAdmin != null) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Başarılı Giriş");
            alert.setHeaderText(null);
            alert.setContentText("Giriş başarılı! Hoş geldiniz, " + girisYapanAdmin.getAdminMail());
            alert.showAndWait(); 

            try {
                // 1. Projendeki fxml dosyasının gerçek adı: SeferSecim.fxml
                FXMLLoader loader = new FXMLLoader(getClass().getResource("SeferSecim.fxml"));
                Parent root = loader.load();
                
                // 2. Projendeki asıl hedef controller sınıfı: SeferSecimController
                // (Eğer bu sınıf henüz oluşturulmadıysa src/application altına "SeferSecimController" adında bir class açmalısın)
                SeferSecimController controller = loader.getController();
                
                // 3. Veriyi sonraki sayfaya paslıyoruz
                controller.setUser(girisYapanAdmin);
                
                // Sahne yükleme işlemleri
                Stage stage = (Stage) girisButton.getScene().getWindow();
                stage.setScene(new Scene(root, 600, 500)); 
                stage.setTitle("Sefer ve Saat Seçim Ekranı");
                stage.show();
                
            } catch (IOException e) {
                System.out.println("Hata: SeferSecim.fxml dosyası yüklenemedi!");
                e.printStackTrace();
            }
            
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Başarısız Giriş");
            alert.setHeaderText(null);
            alert.setContentText("Giriş başarısız! Kullanıcı adı veya şifre yanlış.");
            alert.showAndWait();
        }
    }
}