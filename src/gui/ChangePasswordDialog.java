package gui;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import database.UserDB;

public class ChangePasswordDialog extends JDialog {
    private JPasswordField txtCurrentPassword;
    private JPasswordField txtNewPassword;
    private JPasswordField txtConfirmPassword;
    private UserDB userDB;
    private int userId;

    public ChangePasswordDialog(JDialog parent, int userId) {
        super(parent, "Şifre Değiştir", true);
        this.userId = userId;
        this.userDB = new UserDB();
        initializeComponents();
        setLocationRelativeTo(parent);
    }

    private void initializeComponents() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(350, 250);
        setResizable(false);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(new JLabel("Mevcut Şifre:"), gbc);

        gbc.gridx = 1;
        txtCurrentPassword = new JPasswordField(20);
        mainPanel.add(txtCurrentPassword, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(new JLabel("Yeni Şifre:"), gbc);

        gbc.gridx = 1;
        txtNewPassword = new JPasswordField(20);
        mainPanel.add(txtNewPassword, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(new JLabel("Yeni Şifre Tekrar:"), gbc);

        gbc.gridx = 1;
        txtConfirmPassword = new JPasswordField(20);
        mainPanel.add(txtConfirmPassword, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton btnSave = new JButton("Şifreyi Değiştir");
        btnSave.setBackground(new Color(40, 167, 69));
        btnSave.setForeground(Color.WHITE);
        btnSave.addActionListener(e -> changePassword());

        JButton btnCancel = new JButton("İptal");
        btnCancel.setBackground(new Color(108, 117, 125));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.addActionListener(e -> dispose());

        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);
        mainPanel.add(buttonPanel, gbc);

        setContentPane(mainPanel);
    }

    private void changePassword() {
        String currentPassword = new String(txtCurrentPassword.getPassword());
        String newPassword = new String(txtNewPassword.getPassword());
        String confirmPassword = new String(txtConfirmPassword.getPassword());

        // Validate input
        if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            showError("Lütfen tüm alanları doldurun.");
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            showError("Yeni şifreler eşleşmiyor.");
            return;
        }

        try {
            if (userDB.changePassword(userId, currentPassword, newPassword)) {
                JOptionPane.showMessageDialog(this,
                    "Şifreniz başarıyla değiştirildi.",
                    "Başarılı",
                    JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                showError("Mevcut şifre hatalı.");
            }
        } catch (SQLException ex) {
            showError("Şifre değiştirilirken hata oluştu: " + ex.getMessage());
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this,
            message,
            "Hata",
            JOptionPane.ERROR_MESSAGE);
    }
}