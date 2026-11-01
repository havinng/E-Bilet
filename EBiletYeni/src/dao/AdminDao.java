package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import config.DatabaseConnection;
import model.Admin;

public class AdminDao {

    // admin girişi 
    public Admin login(String email, String sifre) {
        String sql = "SELECT * FROM admin WHERE adminMail = ? AND adminSifre = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setString(1, email); 
            statement.setString(2, sifre); 
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Admin adminGiris = new Admin();
                    adminGiris.setId(resultSet.getInt("id"));
                    adminGiris.setAdminMail(resultSet.getString("adminMail"));
                    adminGiris.setAdminSifre(resultSet.getString("adminSifre"));
                    adminGiris.setAdminAd(resultSet.getString("adminAd"));
                    return adminGiris; 
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Giriş hatası: " + e.getMessage());
        }
        
        return null; 
    }
}