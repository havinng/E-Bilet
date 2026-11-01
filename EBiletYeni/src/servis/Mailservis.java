package servis;

import java.util.Properties; 
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mailservis {

    public static void sifreGonder(String aliciEmail, String sifre) {
        final String kullanici = "havingonul6@gmail.com";            
        final String sifreApp = "lpisllybfwzokfvn"; 

        // SMTP 
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.ssl.protocols", "TLSv1.2"); // 
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        

        Session session = Session.getInstance(prop, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(kullanici, sifreApp);
            }
        });

        try {
            // Mail 
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(kullanici));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(aliciEmail));
            message.setSubject("Şifre Hatırlatma Servisi");
            message.setText("Merhaba,\n\nSistemimizdeki kayıtlı şifreniz: " + sifre + "\n\nİyi günler dileriz.");
      
            Transport.send(message);
            System.out.println("Mail başarıyla gönderildi: " + aliciEmail);

        } catch (MessagingException e) {
            System.out.println("Mail gönderim hatası: " + e.getMessage());
            e.printStackTrace();
        }
    }
}