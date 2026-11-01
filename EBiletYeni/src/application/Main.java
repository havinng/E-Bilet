package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
        	
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("./Sample.fxml"));
            Parent root = loader.load();
            
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            
            primaryStage.setScene(scene);
            primaryStage.setTitle("Kullanıcı Giriş Paneli");
            primaryStage.show();
            
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("Başlangıç ekranı yüklenirken hata oluştu: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}