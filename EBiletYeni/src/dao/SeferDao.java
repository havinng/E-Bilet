package dao;

import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import config.DatabaseConnection;
import model.Sefer; 

public class SeferDao {
    
    public ObservableList<Sefer> getTumSeferler() {
        ObservableList<Sefer> seferListesi = FXCollections.observableArrayList();
        String sql = "SELECT * FROM seferler"; 

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                int id = rs.getInt("id"); 
                // Veritabanındaki seferAd'ı alıp, bizim kalkış bilgimiz yapıyoruz
                String kalkis = rs.getString("seferAd"); 
                double fiyat = rs.getDouble("fiyat");
                int koltukSayisi = rs.getInt("koltukSayisi"); 

                Sefer sefer = new Sefer(id, kalkis, fiyat, koltukSayisi);
                seferListesi.add(sefer);
            }
        } catch (SQLException e) {
            System.err.println("Veritabanı bağlantı hatası: " + e.getMessage());
        }
        return seferListesi;
    }
    
    
    
 // SEFER EKLEME
    public void seferEkle(String kalkis, double fiyat, int koltuk) {
        String sql = "INSERT INTO seferler (seferAd, fiyat, koltukSayisi) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, kalkis);
            ps.setDouble(2, fiyat);
            ps.setInt(3, koltuk);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    // SEFER SİLME
    public void seferSil(int id) {
        String sql = "DELETE FROM seferler WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    // SEFER GÜNCELLEME
    public void seferGuncelle(int id, String kalkis, double fiyat, int koltuk) {
        String sql = "UPDATE seferler SET seferAd = ?, fiyat = ?, koltukSayisi = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, kalkis);
            ps.setDouble(2, fiyat);
            ps.setInt(3, koltuk);
            ps.setInt(4, id);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
}
