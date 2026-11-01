package application;

import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import dao.UsersDao;
import dao.AdminDao;
import model.Admin;
import servis.Mailservis;

public class SampleController {

    @FXML private TextField email_tf;
    @FXML private PasswordField sifre_pf;

    private final UsersDao usersDao = new UsersDao();
    private final AdminDao adminDao = new AdminDao();

    @FXML
    void handleGirisYap(ActionEvent event) {
        String girdi = email_tf.getText().trim();
        String sifre = sifre_pf.getText().trim();

        if (girdi.isEmpty() || sifre.isEmpty()) {
            System.out.println("Hata: Lütfen tüm alanları doldurunuz!");
            return;
        }

        Admin girisYapanAdmin = adminDao.login(girdi, sifre);
        if (girisYapanAdmin != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminPanel.fxml"));
                Parent adminKoku = loader.load();
                
                Stage mevcutSahne = (Stage) ((Node) event.getSource()).getScene().getWindow();
                mevcutSahne.setScene(new Scene(adminKoku, 800, 600)); 
                mevcutSahne.setTitle("Admin Yönetim Paneli");
                mevcutSahne.show();
                
                System.out.println("Admin Paneline Başarıyla Yönlendirildi.");
            } catch (Exception e) {
                System.err.println("Admin paneli yüklenirken FXML hatası oluştu! Dosya adını ve yolunu kontrol edin.");
                e.printStackTrace();
            }
        } 

        else if (usersDao.girisKontrol(girdi, sifre)) {
            try {
                // Kullanıcı başarılı girdiğinde SeferSecim.fxml açılıyor
                FXMLLoader loader = new FXMLLoader(getClass().getResource("SeferSecim.fxml"));
                Parent seferKoku = loader.load(); 
                
                Stage mevcutSahne = (Stage) ((Node) event.getSource()).getScene().getWindow();
                mevcutSahne.setScene(new Scene(seferKoku, 600, 500));
                mevcutSahne.setTitle("Sefer ve Saat Seçim Ekranı");
                mevcutSahne.show();
                
                System.out.println("Kullanıcı Sefer Seçim Ekranına Yönlendirildi.");
            } catch (Exception e) {
                System.err.println("Sefer seçim ekranı yüklenirken FXML hatası oluştu!");
                e.printStackTrace();
            }
        } else {
            System.out.println("Kullanıcı adı veya Şifre hatalı!");
        }
    }

    // 2. Şifremi Unuttum İşlemi
    @FXML
    private void handleSifremiUnuttum(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Şifremi Unuttum");
        dialog.setHeaderText("Kayıtlı e-posta adresinizi girin:");
        dialog.setContentText("E-posta:");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(email -> {
            UsersDao dao = new UsersDao();
            String sifre = dao.getSifreByEmail(email);

            if (sifre != null) {
                Mailservis.sifreGonder(email, sifre);
                showAlert("Başarılı", "Şifreniz e-posta adresinize gönderildi.");
            } else {
                showAlert("Hata", "Bu e-posta sistemde kayıtlı değil.");
            }
        });
    }

    // Yardımcı metot
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // 3. Kayıt Ekranına Gitme
    @FXML
    void handleKayitEkraninaGit(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("kayit.fxml"));
            Parent kayitKoku = loader.load(); 
            Stage mevcutSahne = (Stage) ((Node) event.getSource()).getScene().getWindow();
            mevcutSahne.setScene(new Scene(kayitKoku, 600, 500));
            mevcutSahne.setTitle("Yeni Yolcu Kayıt Ekranı");
            mevcutSahne.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}