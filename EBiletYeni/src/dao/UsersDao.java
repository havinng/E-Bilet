package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import config.DatabaseConnection; 
import model.User;

public class UsersDao {

    // Aynı kullanıcı adı var mı kontrolü
    public boolean kullaniciVarMi(String kullaniciAd) {
        String query = "SELECT COUNT(*) FROM uyeler WHERE kullaniciAd = ?";
        
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, kullaniciAd);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.out.println("Veritabanı sorgu hatası (kullaniciVarMi): " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    //Yeni kullanıcıyı veritabanına kaydetme
    public boolean uyeKaydet(User user) {
        String query = "INSERT INTO uyeler (kullaniciAd, sifre, mail) VALUES (?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, user.getKullaniciAd());
            stmt.setString(2, user.getSifre());
            stmt.setString(3, user.getMail());
            
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            System.out.println("Veritabanına kaydetme hatası (uyeKaydet): " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    //  Giriş Doğrulama Metodu
    public boolean girisKontrol(String kullaniciAdVeyaEmail, String sifre) {
        String query = "SELECT * FROM uyeler WHERE (kullaniciAd = ? OR mail = ?) AND sifre = ?";
        
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, kullaniciAdVeyaEmail);
            stmt.setString(2, kullaniciAdVeyaEmail);
            stmt.setString(3, sifre);
            
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.out.println("Giriş kontrol hatası (girisKontrol): " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public String getSifreByEmail(String email) {
        String query = "SELECT sifre FROM uyeler WHERE mail = ?";
        
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, email);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("sifre");
                }
            }
        } catch (SQLException e) {
            System.out.println("Şifre sorgulama hatası: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
} 
