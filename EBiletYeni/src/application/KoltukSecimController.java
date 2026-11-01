package application;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.CheckBox;
import model.Sefer;

public class KoltukSecimController implements Initializable {

    @FXML private Label labelSecilenSefer;
    @FXML private Label labelToplamUcret;
    @FXML private CheckBox chkBebekli;

    @FXML private Button k1, k2, k3, k4, k5, k6, k7, k8, k9, k10;
    @FXML private Button k11, k12, k13, k14, k15, k16, k17, k18, k19, k20;
    @FXML private Button k21, k22, k23, k24, k25, k26, k27, k28, k29, k30;
    @FXML private Button k31, k32, k33, k34, k35, k36, k37, k38, k39, k40;

    private Map<Integer, Button> koltukButonlari = new HashMap<>();
    
    private Sefer aktifSefer;
    private Button sonSecilenButon = null;
    private int secilenKoltukNo = -1;
    private double biletFiyati = 0.0;
    
    private boolean isBebekli = false; 

    // Stil Tanımlamaları
    private final String STIL_BOS_GRI = "-fx-background-color: #cbd5e1; -fx-text-fill: #334155; -fx-font-weight: bold; -fx-background-radius: 8; -fx-cursor: hand;";
    private final String STIL_DOLU_KIRMIZI = "-fx-background-color: #fca5a5; -fx-text-fill: #991b1b; -fx-font-weight: bold; -fx-background-radius: 8;";
    private final String STIL_BEBEK_SARI = "-fx-background-color: #fef08a; -fx-text-fill: #854d0e; -fx-font-weight: bold; -fx-background-radius: 8;";
    private final String STIL_SECILEN_YESIL = "-fx-background-color: #10b981; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8; -fx-effect: dropshadow(gaussian, rgba(16,185,129,0.4), 8, 0, 0, 2);";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (int i = 1; i <= 40; i++) {
            try {
                String butonId = "k" + i;
                java.lang.reflect.Field field = getClass().getDeclaredField(butonId);
                Button btn = (Button) field.get(this);
                
                if (btn != null) {
                    final int koltukNo = i;
                    koltukButonlari.put(koltukNo, btn);
                    btn.setOnAction(event -> koltukTiklandi(koltukNo, btn));
                }
            } catch (Exception e) {
                System.out.println("Buton haritalama uyarısı: k" + i + " bulunamadı.");
            }
        }
    }

    public void setSefer(Sefer sefer) {
        this.aktifSefer = sefer;
        if (aktifSefer != null) {
            // Sadece kalkış değil, varsa tüm güzergah bilgisini yazdırmak daha doğrudur
            if (labelSecilenSefer != null) {
                labelSecilenSefer.setText("Seçilen Sefer: " + aktifSefer.getKalkis());
            }
            this.biletFiyati = aktifSefer.getFiyat();
            if (labelToplamUcret != null) labelToplamUcret.setText("0 TL");
            koltukDurumlariniYukle();
        }
    }

    private void koltukDurumlariniYukle() {
        for (int i = 1; i <= 40; i++) {
            Button btn = koltukButonlari.get(i);
            if (btn == null) continue;

            if (i == 2 || i == 4 || i == 7 || i == 10 || i == 15) {
                btn.setStyle(STIL_DOLU_KIRMIZI);
                btn.setDisable(true); 
            } else if (i == 17 || i == 18) {
                btn.setStyle(STIL_BEBEK_SARI);
                btn.setDisable(true); 
            } else {
                btn.setStyle(STIL_BOS_GRI);
                btn.setDisable(false);
            }
        }
    }

    private void koltukTiklandi(int koltukNo, Button tiklananButon) {
        // Durum 1: Zaten seçili olan koltuğa tekrar tıklandı (Seçim İptali)
        if (secilenKoltukNo == koltukNo) {
            tiklananButon.setStyle(STIL_BOS_GRI);
            secilenKoltukNo = -1;
            sonSecilenButon = null;
            this.isBebekli = false;
            if (labelToplamUcret != null) labelToplamUcret.setText("0 TL");
            
            if (chkBebekli != null) {
                chkBebekli.setSelected(false);
                chkBebekli.setVisible(false);
            }
            return;
        }

        // Durum 2: Eski seçilen koltuğu temizle (Eğer varsa)
        if (sonSecilenButon != null) {
            sonSecilenButon.setStyle(STIL_BOS_GRI);
        }

        // Durum 3: Yeni koltuğu seç ve işaretle
        this.secilenKoltukNo = koltukNo;
        this.sonSecilenButon = tiklananButon;
        tiklananButon.setStyle(STIL_SECILEN_YESIL);
        
        // Yeni koltuğa geçildiğinde bebekli seçimini sıfırla (Mantıksal koruma)
        this.isBebekli = false;
        if (chkBebekli != null) {
            chkBebekli.setSelected(false); 
            chkBebekli.setVisible(true);
        }

        if (labelToplamUcret != null) labelToplamUcret.setText(biletFiyati + " TL");
    }

    @FXML
    public void handleBebekliSecim() {
        // chkBebekli için NullPointerException koruması eklendi
        if (sonSecilenButon != null && secilenKoltukNo != -1 && chkBebekli != null) {
            if (chkBebekli.isSelected()) {
                sonSecilenButon.setStyle(STIL_BEBEK_SARI);
                this.isBebekli = true;
                System.out.println("Koltuk " + secilenKoltukNo + " için bebekli yolcu bildirimi yapıldı.");
            } else {
                sonSecilenButon.setStyle(STIL_SECILEN_YESIL);
                this.isBebekli = false;
                System.out.println("Koltuk " + secilenKoltukNo + " normal seçime döndürüldü.");
            }
        }
    }

    @FXML
    public void handleDevamEt() {
        if (secilenKoltukNo != -1) {
            System.out.println("--- BİLET SATIN ALMA DETAYI ---");
            System.out.println("Seçilen Koltuk: " + secilenKoltukNo);
            System.out.println("Bebekli mi?: " + (this.isBebekli ? "EVET" : "HAYIR"));
            System.out.println("Ödenecek Ücret: " + biletFiyati + " TL");
            
            try {
              
                javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("BiletOnay.fxml"));
                javafx.scene.Parent root = loader.load();
                
                // 2. Diğer sayfanın kontrolcüsüne ulaşıp seçilen verileri gönderiyoruz
                BiletOnayController controller = loader.getController();
                String guzergah = (aktifSefer != null) ? aktifSefer.getKalkis() : "Belirsiz Güzergah";
                controller.setBiletDetay(guzergah, secilenKoltukNo, biletFiyati, isBebekli);
                
             
                javafx.scene.Scene mevcutSahne = null;
                if (labelToplamUcret != null) {
                    mevcutSahne = labelToplamUcret.getScene();
                } else if (chkBebekli != null) {
                    mevcutSahne = chkBebekli.getScene();
                }
                
                if (mevcutSahne != null) {
                    javafx.stage.Window mevcutPencere = mevcutSahne.getWindow();
                    javafx.stage.Stage stage = (javafx.stage.Stage) mevcutPencere;
                    
                 
                    javafx.scene.Scene yeniSahne = new javafx.scene.Scene(root);
                    stage.setScene(yeniSahne);
                    System.out.println("BiletOnay ekranına başarıyla geçiş yapıldı.");
                } else {
                    System.err.println("Hata: Mevcut sahne hiyerarşisi bulunamadı!");
                }
                
            } catch (Exception e) {
                System.err.println("Sayfa geçişi sırasında hata oluştu: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("Lütfen ilerlemek için bir koltuk seçimi yapın!");
        }
    }
}