package gui;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import database.UserDB;
import models.Customer;
import utils.ValidationUtils;
import services.EmailService;

public class RegisterPage extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextField txtFirstName;
    private JTextField txtLastName;
    private JTextField txtUsername;
    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JPasswordField txtConfirmPassword;
    private JComboBox<String> comboSecurityQuestion;
    private JTextField txtSecurityAnswer;
    private JTextField txtCardNumber;
    private JTextField txtCVV;
    private UserDB userDB;

    public RegisterPage() {
        userDB = new UserDB();
        initializeComponents();
    }

    private void initializeComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 600);
        setTitle("Kayıt Ol");

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        setContentPane(mainPanel);

        int yOffset = 30;
        int spacing = 40;

        JLabel lblFirstName = new JLabel("Ad:");
        lblFirstName.setBounds(50, yOffset, 150, 25);
        mainPanel.add(lblFirstName);

        txtFirstName = new JTextField();
        txtFirstName.setBounds(200, yOffset, 200, 25);
        mainPanel.add(txtFirstName);

        yOffset += spacing;
        JLabel lblLastName = new JLabel("Soyad:");
        lblLastName.setBounds(50, yOffset, 150, 25);
        mainPanel.add(lblLastName);

        txtLastName = new JTextField();
        txtLastName.setBounds(200, yOffset, 200, 25);
        mainPanel.add(txtLastName);

        yOffset += spacing;
        JLabel lblUsername = new JLabel("Kullanıcı Adı:");
        lblUsername.setBounds(50, yOffset, 150, 25);
        mainPanel.add(lblUsername);

        txtUsername = new JTextField();
        txtUsername.setBounds(200, yOffset, 200, 25);
        mainPanel.add(txtUsername);

        yOffset += spacing;
        JLabel lblEmail = new JLabel("E-posta:");
        lblEmail.setBounds(50, yOffset, 150, 25);
        mainPanel.add(lblEmail);

        txtEmail = new JTextField();
        txtEmail.setBounds(200, yOffset, 200, 25);
        mainPanel.add(txtEmail);

        yOffset += spacing;
        JLabel lblPassword = new JLabel("Şifre:");
        lblPassword.setBounds(50, yOffset, 150, 25);
        mainPanel.add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(200, yOffset, 200, 25);
        mainPanel.add(txtPassword);

        yOffset += spacing;
        JLabel lblConfirmPassword = new JLabel("Şifre Tekrar:");
        lblConfirmPassword.setBounds(50, yOffset, 150, 25);
        mainPanel.add(lblConfirmPassword);

        txtConfirmPassword = new JPasswordField();
        txtConfirmPassword.setBounds(200, yOffset, 200, 25);
        mainPanel.add(txtConfirmPassword);

        yOffset += spacing;
        JLabel lblSecurityQuestion = new JLabel("Güvenlik Sorusu:");
        lblSecurityQuestion.setBounds(50, yOffset, 150, 25);
        mainPanel.add(lblSecurityQuestion);

        comboSecurityQuestion = new JComboBox<>(new String[]{
            "Annenizin kızlık soyadı nedir?",
            "En sevdiğiniz renk nedir?",
            "İlk okulunuzun adı nedir?",
            "En sevdiğiniz hayvan nedir?"
        });
        comboSecurityQuestion.setBounds(200, yOffset, 200, 25);
        mainPanel.add(comboSecurityQuestion);

        yOffset += spacing;
        JLabel lblSecurityAnswer = new JLabel("Güvenlik Cevabı:");
        lblSecurityAnswer.setBounds(50, yOffset, 150, 25);
        mainPanel.add(lblSecurityAnswer);

        txtSecurityAnswer = new JTextField();
        txtSecurityAnswer.setBounds(200, yOffset, 200, 25);
        mainPanel.add(txtSecurityAnswer);

        yOffset += spacing;
        JLabel lblCardNumber = new JLabel("Kart Numarası:");
        lblCardNumber.setBounds(50, yOffset, 150, 25);
        mainPanel.add(lblCardNumber);

        txtCardNumber = new JTextField();
        txtCardNumber.setBounds(200, yOffset, 200, 25);
        mainPanel.add(txtCardNumber);

        yOffset += spacing;
        JLabel lblCVV = new JLabel("CVV:");
        lblCVV.setBounds(50, yOffset, 150, 25);
        mainPanel.add(lblCVV);

        txtCVV = new JTextField();
        txtCVV.setBounds(200, yOffset, 200, 25);
        mainPanel.add(txtCVV);

        yOffset += spacing;
        JButton btnRegister = new JButton("Kayıt Ol");
        btnRegister.setBounds(200, yOffset, 100, 25);
        mainPanel.add(btnRegister);

        yOffset += spacing;
        JLabel lblLogin = new JLabel("Zaten hesabınız var mı? Giriş yapın");
        lblLogin.setBounds(200, yOffset, 200, 25);
        lblLogin.setForeground(Color.BLUE);
        lblLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        mainPanel.add(lblLogin);

        btnRegister.addActionListener(e -> register());
        
        lblLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LoginPage loginPage = new LoginPage();
                loginPage.setVisible(true);
                dispose();
            }
        });
    }

    private void register() {
        if (!validateInput()) {
            return;
        }

        try {
            if (userDB.isUsernameExists(txtUsername.getText())) {
                JOptionPane.showMessageDialog(this,
                    "Bu kullanıcı adı zaten kullanılıyor.",
                    "Hata",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (userDB.isEmailExists(txtEmail.getText())) {
                JOptionPane.showMessageDialog(this,
                    "Bu e-posta adresi zaten kullanılıyor.",
                    "Hata",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            Customer customer = new Customer(
                0, 
                txtFirstName.getText(),
                txtLastName.getText(),
                txtUsername.getText(),
                new String(txtPassword.getPassword()),
                comboSecurityQuestion.getSelectedIndex() + 1,
                txtSecurityAnswer.getText(),
                txtEmail.getText(),
                txtCardNumber.getText(),
                txtCVV.getText()
            );

            if (userDB.register(customer)) {
                JOptionPane.showMessageDialog(this,
                    "Kayıt başarıyla tamamlandı.",
                    "Başarılı",
                    JOptionPane.INFORMATION_MESSAGE);
                
                LoginPage loginPage = new LoginPage();
                loginPage.setVisible(true);
                dispose();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                "Veritabanı hatası: " + ex.getMessage(),
                "Hata",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validateInput() {
        if (txtFirstName.getText().isEmpty() || txtLastName.getText().isEmpty() ||
            txtUsername.getText().isEmpty() || txtEmail.getText().isEmpty() ||
            txtPassword.getPassword().length == 0 || txtConfirmPassword.getPassword().length == 0 ||
            txtSecurityAnswer.getText().isEmpty() || txtCardNumber.getText().isEmpty() ||
            txtCVV.getText().isEmpty()) {
            
            JOptionPane.showMessageDialog(this,
                "Lütfen tüm alanları doldurun.",
                "Hata",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!new String(txtPassword.getPassword()).equals(new String(txtConfirmPassword.getPassword()))) {
            JOptionPane.showMessageDialog(this,
                "Şifreler eşleşmiyor.",
                "Hata",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (txtCardNumber.getText().length() != 16) {
            JOptionPane.showMessageDialog(this,
                "Kart numarası 16 haneli olmalıdır.",
                "Hata",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (txtCVV.getText().length() != 3) {
            JOptionPane.showMessageDialog(this,
                "CVV 3 haneli olmalıdır.",
                "Hata",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }
}