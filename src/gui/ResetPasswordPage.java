package gui;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import database.UserDB;

public class ResetPasswordPage extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextField txtUsername;
    private JTextField txtSecurityAnswer;
    private JPasswordField txtNewPassword;
    private JPasswordField txtConfirmPassword;
    private UserDB userDB;

    public ResetPasswordPage() {
        userDB = new UserDB();
        initializeComponents();
    }

    private void initializeComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        setTitle("Şifre Sıfırlama");

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        setContentPane(mainPanel);

        int yOffset = 30;
        int spacing = 40;

        JLabel lblUsername = new JLabel("Kullanıcı Adı veya E-posta:");
        lblUsername.setBounds(50, yOffset, 150, 25);
        mainPanel.add(lblUsername);

        txtUsername = new JTextField();
        txtUsername.setBounds(200, yOffset, 200, 25);
        mainPanel.add(txtUsername);

        yOffset += spacing;
        JLabel lblSecurityAnswer = new JLabel("Güvenlik Sorusu Cevabı:");
        lblSecurityAnswer.setBounds(50, yOffset, 150, 25);
        mainPanel.add(lblSecurityAnswer);

        txtSecurityAnswer = new JTextField();
        txtSecurityAnswer.setBounds(200, yOffset, 200, 25);
        mainPanel.add(txtSecurityAnswer);

        yOffset += spacing;
        JLabel lblNewPassword = new JLabel("Yeni Şifre:");
        lblNewPassword.setBounds(50, yOffset, 150, 25);
        mainPanel.add(lblNewPassword);

        txtNewPassword = new JPasswordField();
        txtNewPassword.setBounds(200, yOffset, 200, 25);
        mainPanel.add(txtNewPassword);

        yOffset += spacing;
        JLabel lblConfirmPassword = new JLabel("Yeni Şifre Tekrar:");
        lblConfirmPassword.setBounds(50, yOffset, 150, 25);
        mainPanel.add(lblConfirmPassword);

        txtConfirmPassword = new JPasswordField();
        txtConfirmPassword.setBounds(200, yOffset, 200, 25);
        mainPanel.add(txtConfirmPassword);

        yOffset += spacing;
        JButton btnReset = new JButton("Şifreyi Sıfırla");
        btnReset.setBounds(200, yOffset, 120, 25);
        mainPanel.add(btnReset);

        yOffset += spacing;
        JLabel lblLogin = new JLabel("Giriş sayfasına dön");
        lblLogin.setBounds(200, yOffset, 200, 25);
        lblLogin.setForeground(Color.BLUE);
        lblLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        mainPanel.add(lblLogin);

        btnReset.addActionListener(e -> resetPassword());
        
        lblLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LoginPage loginPage = new LoginPage();
                loginPage.setVisible(true);
                dispose();
            }
        });
    }

    private void resetPassword() {
        String username = txtUsername.getText();
        String securityAnswer = txtSecurityAnswer.getText();
        String newPassword = new String(txtNewPassword.getPassword());
        String confirmPassword = new String(txtConfirmPassword.getPassword());

        if (username.isEmpty() || securityAnswer.isEmpty() || 
            newPassword.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Lütfen tüm alanları doldurun.",
                "Hata",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this,
                "Şifreler eşleşmiyor.",
                "Hata",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            if (userDB.resetPassword(username, securityAnswer, newPassword)) {
                JOptionPane.showMessageDialog(this,
                    "Şifreniz başarıyla değiştirildi.",
                    "Başarılı",
                    JOptionPane.INFORMATION_MESSAGE);
                
                LoginPage loginPage = new LoginPage();
                loginPage.setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Kullanıcı adı veya güvenlik sorusu cevabı hatalı.",
                    "Hata",
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                "Veritabanı hatası: " + ex.getMessage(),
                "Hata",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}