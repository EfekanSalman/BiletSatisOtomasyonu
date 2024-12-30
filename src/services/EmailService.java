package services;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class EmailService {
    private static final String FROM_EMAIL = "efekansalman@gmail.com"; // Gmail adresiniz
    private static final String PASSWORD = "uxed qqlt nmmo juwlr"; // Gmail App Password
    
    private static Properties getEmailProperties() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.debug", "true"); 
        return props;
    }
    
    private static Session getEmailSession() {
        return Session.getInstance(getEmailProperties(), new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM_EMAIL, PASSWORD);
            }
        });
    }
    
    public static void sendWelcomeEmail(String toEmail, String firstName) {
        try {
            Session session = getEmailSession();
            Message message = new MimeMessage(session);
            
            message.setFrom(new InternetAddress(FROM_EMAIL, "Etkinlik Bilet Sistemi"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Hoş Geldiniz - Etkinlik Bilet Sistemi");
            
            String content = String.format(
                "Merhaba %s,\n\n" +
                "Etkinlik Bilet Sistemine hoş geldiniz! Hesabınız başarıyla oluşturuldu.\n\n" +
                "İyi eğlenceler!\n" +
                "Etkinlik Bilet Sistemi Ekibi",
                firstName
            );
            
            message.setText(content);
            
            try {
                Transport.send(message);
                System.out.println("E-posta başarıyla gönderildi: " + toEmail);
            } catch (MessagingException e) {
                System.err.println("E-posta gönderimi başarısız: " + e.getMessage());
                e.printStackTrace();
                throw e;
            }
        } catch (Exception e) {
            System.err.println("E-posta hazırlama hatası: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public static void sendPasswordResetEmail(String toEmail, String newPassword) {
        try {
            Session session = getEmailSession();
            Message message = new MimeMessage(session);
            
            message.setFrom(new InternetAddress(FROM_EMAIL, "Etkinlik Bilet Sistemi"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Şifre Sıfırlama - Etkinlik Bilet Sistemi");
            
            String content = String.format(
                "Merhaba,\n\n" +
                "Şifreniz başarıyla sıfırlandı. Yeni şifreniz: %s\n\n" +
                "Güvenliğiniz için lütfen giriş yaptıktan sonra şifrenizi değiştirin.\n\n" +
                "Etkinlik Bilet Sistemi Ekibi",
                newPassword
            );
            
            message.setText(content);
            
            try {
                Transport.send(message);
                System.out.println("Şifre sıfırlama e-postası gönderildi: " + toEmail);
            } catch (MessagingException e) {
                System.err.println("E-posta gönderimi başarısız: " + e.getMessage());
                e.printStackTrace();
                throw e;
            }
        } catch (Exception e) {
            System.err.println("E-posta hazırlama hatası: " + e.getMessage());
            e.printStackTrace();
        }
    }
}