package gui;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import database.UserDB;
import models.User;
import models.Admin;
import models.Customer;

public class LoginPage extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private UserDB userDB;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                LoginPage frame = new LoginPage();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public LoginPage() {
        userDB = new UserDB();
        initializeComponents();
    }

    private void initializeComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        setTitle("Giriş Yap");
        setResizable(false);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        setContentPane(mainPanel);

        JLabel lblTitle = new JLabel("Etkinlik Bilet Sistemi");
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setBounds(0, 20, 450, 30);
        mainPanel.add(lblTitle);

        JLabel lblUsername = new JLabel("Kullanıcı Adı veya E-posta:");
        lblUsername.setBounds(50, 70, 150, 25);
        mainPanel.add(lblUsername);

        txtUsername = new JTextField();
        txtUsername.setBounds(200, 70, 200, 25);
        mainPanel.add(txtUsername);

        JLabel lblPassword = new JLabel("Şifre:");
        lblPassword.setBounds(50, 110, 150, 25);
        mainPanel.add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(200, 110, 200, 25);
        mainPanel.add(txtPassword);

        JButton btnLogin = new JButton("Giriş Yap");
        btnLogin.setBounds(200, 150, 100, 30);
        btnLogin.setBackground(new Color(70, 130, 180));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        mainPanel.add(btnLogin);

        JLabel lblRegister = new JLabel("Hesabınız yok mu? Kayıt olun");
        lblRegister.setBounds(200, 190, 200, 25);
        lblRegister.setForeground(new Color(70, 130, 180));
        lblRegister.setCursor(new Cursor(Cursor.HAND_CURSOR));
        mainPanel.add(lblRegister);

        JLabel lblForgotPassword = new JLabel("Şifremi Unuttum");
        lblForgotPassword.setBounds(200, 220, 200, 25);
        lblForgotPassword.setForeground(new Color(70, 130, 180));
        lblForgotPassword.setCursor(new Cursor(Cursor.HAND_CURSOR));
        mainPanel.add(lblForgotPassword);

        btnLogin.addActionListener(e -> login());
        
        lblRegister.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RegisterPage registerPage = new RegisterPage();
                registerPage.setVisible(true);
                dispose();
            }
        });

        lblForgotPassword.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ResetPasswordPage resetPage = new ResetPasswordPage();
                resetPage.setVisible(true);
                dispose();
            }
        });

        setLocationRelativeTo(null);
    }

    private void login() {
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            showError("Lütfen tüm alanları doldurun.");
            return;
        }

        try {
            User user = userDB.login(username, password);
            if (user != null) {
                String fullName = user.getFirstName() + " " + user.getLastName();
                if (user instanceof Admin) {
                    AdminPage adminPage = new AdminPage(user.getUserId());
                    adminPage.setVisible(true);
                } else if (user instanceof Customer) {
                    MainPage mainPage = new MainPage(user.getUserId(), fullName);
                    mainPage.setVisible(true);
                }
                dispose();
            } else {
                showError("Kullanıcı adı veya şifre hatalı.");
            }
        } catch (SQLException ex) {
            showError("Giriş yapılırken bir hata oluştu: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this,
            message,
            "Hata",
            JOptionPane.ERROR_MESSAGE);
    }
}