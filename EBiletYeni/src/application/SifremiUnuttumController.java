package application;

import dao.UsersDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import servis.Mailservis;

public class SifremiUnuttumController {

    @FXML
    private TextField txtEmail;

    @FXML
    private Label lblDurum;

    @FXML
    void handleGonder(ActionEvent event) {

        String email = txtEmail.getText();

      
        if(email.isEmpty()) {

            lblDurum.setText("Lütfen e-posta giriniz.");
            return;
        }

        UsersDao dao = new UsersDao();

      
        String sifre = dao.getSifreByEmail(email);

       
        if(sifre != null) {

          
            Mailservis.sifreGonder(email, sifre);

            lblDurum.setText("Şifre mail olarak gönderildi.");

        } else {

            lblDurum.setText("Bu e-posta kayıtlı değil.");
        }
    }
}