package application;

import dao.SeferDao;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Sefer;

public class AdminController {

    @FXML private TableView<Sefer> table_seferler;
    @FXML private TableColumn<Sefer, Integer> col_id;
    @FXML private TableColumn<Sefer, String> col_kalkis;
    @FXML private TableColumn<Sefer, Double> col_fiyat;
    @FXML private TableColumn<Sefer, Integer> col_koltuk;

    @FXML private TextField txt_kalkis;
    @FXML private TextField txt_fiyat;
    @FXML private TextField txt_koltuk;

    private SeferDao seferDao = new SeferDao();

    @FXML
    public void initialize() { 
      
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_kalkis.setCellValueFactory(new PropertyValueFactory<>("kalkis"));
        col_fiyat.setCellValueFactory(new PropertyValueFactory<>("fiyat"));
        col_koltuk.setCellValueFactory(new PropertyValueFactory<>("koltukSayisi"));
   
        tabloyuYenile();
   
        table_seferler.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txt_kalkis.setText(newSelection.getKalkis());
                txt_fiyat.setText(String.valueOf(newSelection.getFiyat()));
                txt_koltuk.setText(String.valueOf(newSelection.getKoltukSayisi()));
            }
        });
    }

    @FXML
    public void handleTabloSecim() {
    }

    private void tabloyuYenile() {
        ObservableList<Sefer> list = seferDao.getTumSeferler();
        if (list != null) {
            table_seferler.setItems(list);
        }
    }

    @FXML
    public void handleEkle() {
        if (!txt_kalkis.getText().isEmpty() && !txt_fiyat.getText().isEmpty() && !txt_koltuk.getText().isEmpty()) 
         	{
            seferDao.seferEkle(txt_kalkis.getText(), 
                               Double.parseDouble(txt_fiyat.getText()), 
                               Integer.parseInt(txt_koltuk.getText()));        
            tabloyuYenile(); kutulariTemizle();
        }
    }

    @FXML
    public void handleGuncelle() {
        Sefer secilen = table_seferler.getSelectionModel().getSelectedItem();
        if (secilen != null) {
            seferDao.seferGuncelle(secilen.getId(), 
                                   txt_kalkis.getText(), 
                                   Double.parseDouble(txt_fiyat.getText()), 
                                   Integer.parseInt(txt_koltuk.getText()));  
            tabloyuYenile(); kutulariTemizle();
        }
    }
    @FXML
    public void handleSil() {
        Sefer secilen = table_seferler.getSelectionModel().getSelectedItem();
        if (secilen != null) {
            seferDao.seferSil(secilen.getId());
            tabloyuYenile();  kutulariTemizle();          
        }
    }

    private void kutulariTemizle() {
        txt_kalkis.clear();
        txt_fiyat.clear();
        txt_koltuk.clear();
        table_seferler.getSelectionModel().clearSelection();
    }
}