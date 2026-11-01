package application;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class BiletOnayController {
    @FXML private Label lblRuzergah, lblKoltuk, lblYolcuTipi, lblUcret;
    @FXML private TextField txtAd, txtSoyad, txtTC;

    // Verileri diğer sayfadan alacak metot
    public void setBiletDetay(String guzergah, int koltukNo, double ucret, boolean bebekli) {
        lblRuzergah.setText(guzergah);
        lblKoltuk.setText(String.valueOf(koltukNo));
        lblUcret.setText(ucret + " TL");
        lblYolcuTipi.setText(bebekli ? "Bebekli Yolcu" : "Standart");
    }

    @FXML
    public void handleOnayla() {
        if(txtAd.getText().isEmpty() || txtTC.getText().length() != 11) {
            System.out.println("Lütfen bilgileri eksiksiz doldurun!");
        } else {
            System.out.println("Bilet başarıyla onaylandı: " + txtAd.getText() + " " + txtSoyad.getText());
        }
    }
}	