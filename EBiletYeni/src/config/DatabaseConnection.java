package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/kutuphane?useSSL=false&serverTimezone=UTC&useUnicode=true&characterEncoding=UTF-8";
            
    private static final String DB_USER = "root"; 
    private static final String DB_PASSWORD = ""; 

    private static Connection connection;
 
    private DatabaseConnection() {}  
    
    public static Connection getConnection() { 
        try {
            if (connection == null || connection.isClosed()) {            
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                System.out.println("MySQL veritabanına bağlantı başarılı."); 
            }
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL Sürücüsü bulunamadı!");
            e.printStackTrace();
        } catch (SQLException e) { 
            System.err.println("MySQL veritabanına bağlantı hatası!"); 
            e.printStackTrace(); 
        }
        return connection; 
    }
    public static void closeConnection() {
        if (connection != null) { 
            try {
                if (!connection.isClosed()) {
                    connection.close(); 
                    System.out.println("MySQL veritabanı bağlantısı kapatıldı."); 
                }
            } catch (SQLException e) { 
                System.err.println("Bağlantı kapatma hatası!"); 
                e.printStackTrace(); 
            }
        }
    }
}