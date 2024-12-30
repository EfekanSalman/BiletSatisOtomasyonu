package gui;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import database.UserDB;
import models.Customer;

public class UpdateProfileDialog extends JDialog {
    private JTextField txtFirstName;
    private JTextField txtLastName;
    private JTextField txtUsername;
    private JTextField txtEmail;
    private JTextField txtCardNumber;
    private JTextField txtCVV;
    private JPasswordField txtCurrentPassword;
    private JPasswordField txtNewPassword;
    private JPasswordField txtConfirmPassword;
    private JCheckBox chkChangePassword;
    private UserDB userDB;
    private int userId;
    private boolean success;

    public UpdateProfileDialog(JFrame parent, int userId) {
        super(parent, "Profil Güncelle", true);
        this.userId = userId;
        this.userDB = new UserDB();
        initializeComponents();
        loadUserData();
        setLocationRelativeTo(parent);
    }

    private void initializeComponents() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(400, 600);
        setResizable(false);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(new JLabel("Ad:"), gbc);

        gbc.gridx = 1;
        txtFirstName = new JTextField(20);
        mainPanel.add(txtFirstName, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(new JLabel("Soyad:"), gbc);

        gbc.gridx = 1;
        txtLastName = new JTextField(20);
        mainPanel.add(txtLastName, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(new JLabel("Kullanıcı Adı:"), gbc);

        gbc.gridx = 1;
        txtUsername = new JTextField(20);
        mainPanel.add(txtUsername, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(new JLabel("E-posta:"), gbc);

        gbc.gridx = 1;
        txtEmail = new JTextField(20);
        mainPanel.add(txtEmail, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        mainPanel.add(new JLabel("Kart Numarası:"), gbc);

        gbc.gridx = 1;
        txtCardNumber = new JTextField(20);
        mainPanel.add(txtCardNumber, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        mainPanel.add(new JLabel("CVV:"), gbc);

        gbc.gridx = 1;
        txtCVV = new JTextField(3);
        mainPanel.add(txtCVV, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        chkChangePassword = new JCheckBox("Şifreyi Değiştir");
        mainPanel.add(chkChangePassword, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 1;
        JLabel lblCurrentPassword = new JLabel("Mevcut Şifre:");
        mainPanel.add(lblCurrentPassword, gbc);

        gbc.gridx = 1;
        txtCurrentPassword = new JPasswordField(20);
        txtCurrentPassword.setEnabled(false);
        mainPanel.add(txtCurrentPassword, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        JLabel lblNewPassword = new JLabel("Yeni Şifre:");
        mainPanel.add(lblNewPassword, gbc);

        gbc.gridx = 1;
        txtNewPassword = new JPasswordField(20);
        txtNewPassword.setEnabled(false);
        mainPanel.add(txtNewPassword, gbc);

        gbc.gridx = 0;
        gbc.gridy = 9;
        JLabel lblConfirmPassword = new JLabel("Yeni Şifre Tekrar:");
        mainPanel.add(lblConfirmPassword, gbc);

        gbc.gridx = 1;
        txtConfirmPassword = new JPasswordField(20);
        txtConfirmPassword.setEnabled(false);
        mainPanel.add(txtConfirmPassword, gbc);

        chkChangePassword.addActionListener(e -> {
            boolean enabled = chkChangePassword.isSelected();
            txtCurrentPassword.setEnabled(enabled);
            txtNewPassword.setEnabled(enabled);
            txtConfirmPassword.setEnabled(enabled);
            if (!enabled) {
                txtCurrentPassword.setText("");
                txtNewPassword.setText("");
                txtConfirmPassword.setText("");
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton btnSave = new JButton("Kaydet");
        btnSave.setBackground(new Color(40, 167, 69));
        btnSave.setForeground(Color.WHITE);
        btnSave.addActionListener(e -> saveChanges());

        JButton btnCancel = new JButton("İptal");
        btnCancel.setBackground(new Color(108, 117, 125));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.addActionListener(e -> dispose());

        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);
        mainPanel.add(buttonPanel, gbc);

        setContentPane(mainPanel);
    }

    private void loadUserData() {
        try {
            Customer customer = (Customer) userDB.getUserById(userId);
            if (customer != null) {
                txtFirstName.setText(customer.getFirstName());
                txtLastName.setText(customer.getLastName());
                txtUsername.setText(customer.getUsername());
                txtEmail.setText(customer.getEmail());
                txtCardNumber.setText(customer.getCardNumber());
                txtCVV.setText(customer.getCvv());
            }
        } catch (SQLException e) {
            showError("Kullanıcı bilgileri yüklenirken hata oluştu: " + e.getMessage());
        }
    }

    private void saveChanges() {
        if (!validateInput()) {
            return;
        }

        try {
            Customer customer = new Customer(
                userId,
                txtFirstName.getText(),
                txtLastName.getText(),
                txtUsername.getText(),
                "", 
                0, 
                "", 
                txtEmail.getText(),
                txtCardNumber.getText(),
                txtCVV.getText()
            );

            boolean updateSuccess = userDB.updateUserProfile(customer);
            if (chkChangePassword.isSelected()) {
                String currentPassword = new String(txtCurrentPassword.getPassword());
                String newPassword = new String(txtNewPassword.getPassword());
                
                if (!userDB.changePassword(userId, currentPassword, newPassword)) {
                    showError("Mevcut şifre hatalı.");
                    return;
                }
            }

            if (updateSuccess) {
                success = true;
                JOptionPane.showMessageDialog(this,
                    "Profil başarıyla güncellendi!",
                    "Başarılı",
                    JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }
        } catch (SQLException ex) {
            showError("Profil güncellenirken hata oluştu: " + ex.getMessage());
        }
    }

    private boolean validateInput() {
        if (txtFirstName.getText().isEmpty() || txtLastName.getText().isEmpty() ||
            txtUsername.getText().isEmpty() || txtEmail.getText().isEmpty() ||
            txtCardNumber.getText().isEmpty() || txtCVV.getText().isEmpty()) {
            showError("Lütfen tüm alanları doldurun.");
            return false;
        }

        if (chkChangePassword.isSelected()) {
            String currentPassword = new String(txtCurrentPassword.getPassword());
            String newPassword = new String(txtNewPassword.getPassword());
            String confirmPassword = new String(txtConfirmPassword.getPassword());

            if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                showError("Şifre değişikliği için tüm şifre alanlarını doldurun.");
                return false;
            }

            if (!newPassword.equals(confirmPassword)) {
                showError("Yeni şifreler eşleşmiyor.");
                return false;
            }
        }

        if (txtCardNumber.getText().length() != 16) {
            showError("Kart numarası 16 haneli olmalıdır.");
            return false;
        }

        if (txtCVV.getText().length() != 3) {
            showError("CVV 3 haneli olmalıdır.");
            return false;
        }

        return true;
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this,
            message,
            "Hata",
            JOptionPane.ERROR_MESSAGE);
    }

    public boolean isSuccess() {
        return success;
    }
}