package gui;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import database.*;
import models.*;

public class AddEventDialog extends JDialog {
    private static final long serialVersionUID = 1L;
    private JTextField txtName;
    private JComboBox<String> comboCity;
    private JTextField txtDate;
    private JTextField txtLocation;
    private JTextField txtPrice;
    private JTextField txtCapacity;
    private String eventType;
    private CityDB cityDB;
    private boolean success = false;

    public AddEventDialog(JFrame parent, String eventType) {
        super(parent, "Yeni " + eventType + " Ekle", true);
        this.eventType = eventType;
        this.cityDB = new CityDB();
        initializeComponents();
        loadCities();
    }

    private void initializeComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel(getNameLabel()), gbc);

        gbc.gridx = 1;
        txtName = new JTextField(20);
        add(txtName, gbc);

        // City
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Şehir:"), gbc);

        gbc.gridx = 1;
        comboCity = new JComboBox<>();
        add(comboCity, gbc);

        // Date
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Tarih (GG/AA/YYYY):"), gbc);

        gbc.gridx = 1;
        txtDate = new JTextField(20);
        add(txtDate, gbc);

        // Location
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Lokasyon:"), gbc);

        gbc.gridx = 1;
        txtLocation = new JTextField(20);
        add(txtLocation, gbc);

        // Price
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("Fiyat:"), gbc);

        gbc.gridx = 1;
        txtPrice = new JTextField(20);
        add(txtPrice, gbc);

        // Capacity
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(new JLabel("Kapasite:"), gbc);

        gbc.gridx = 1;
        txtCapacity = new JTextField(20);
        add(txtCapacity, gbc);

        // Buttons
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton btnSave = new JButton("Kaydet");
        btnSave.setBackground(new Color(40, 167, 69));
        btnSave.setForeground(Color.WHITE);
        btnSave.addActionListener(e -> save());

        JButton btnCancel = new JButton("İptal");
        btnCancel.setBackground(new Color(108, 117, 125));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.addActionListener(e -> dispose());

        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);
        add(buttonPanel, gbc);

        pack();
        setLocationRelativeTo(getParent());
    }

    private String getNameLabel() {
        switch (eventType) {
            case "Konser":
                return "Konser Adı:";
            case "Müze":
                return "Müze Adı:";
            case "Festival":
                return "Festival Türü:";
            case "Sahne":
                return "Etkinlik Adı:";
            case "Lunapark":
                return "Park Adı:";
            default:
                return "İsim:";
        }
    }

    private void loadCities() {
        try {
            List<City> cities = cityDB.getAllCities();
            for (City city : cities) {
                comboCity.addItem(city.getCityName());
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                "Şehirler yüklenirken hata oluştu: " + e.getMessage(),
                "Hata",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void save() {
        if (!validateInput()) {
            return;
        }

        try {
            String name = txtName.getText();
            String cityName = (String) comboCity.getSelectedItem();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date utilDate = dateFormat.parse(txtDate.getText());
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            String location = txtLocation.getText();
            BigDecimal price = new BigDecimal(txtPrice.getText());
            int capacity = Integer.parseInt(txtCapacity.getText());

            // Get city ID
            City city = cityDB.getCityByName(cityName);
            if (city == null) {
                throw new Exception("Şehir bulunamadı");
            }

            boolean result = false;
            switch (eventType) {
                case "Konser":
                    result = new ConcertDB().addConcert(name, city.getCityId(), sqlDate, location, price, capacity);
                    break;
                case "Müze":
                    result = new MuseumDB().addMuseum(name, city.getCityId(), sqlDate, location, price, capacity);
                    break;
                case "Festival":
                    result = new FestivalDB().addFestival(name, city.getCityId(), sqlDate, location, price, capacity);
                    break;
                case "Sahne":
                    result = new StageDB().addStage(name, city.getCityId(), sqlDate, location, price, capacity);
                    break;
                case "Lunapark":
                    result = new AmusementParkDB().addAmusementPark(name, city.getCityId(), sqlDate, location, price, capacity);
                    break;
            }

            if (result) {
                success = true;
                JOptionPane.showMessageDialog(this,
                    "Etkinlik başarıyla eklendi",
                    "Başarılı",
                    JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this,
                "Geçersiz tarih formatı. Lütfen GG/AA/YYYY formatında girin.",
                "Hata",
                JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                "Geçersiz sayısal değer. Lütfen fiyat ve kapasiteyi doğru formatta girin.",
                "Hata",
                JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                "Veritabanı hatası: " + e.getMessage(),
                "Hata",
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Hata: " + e.getMessage(),
                "Hata",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validateInput() {
        if (txtName.getText().isEmpty() || txtDate.getText().isEmpty() ||
            txtLocation.getText().isEmpty() || txtPrice.getText().isEmpty() ||
            txtCapacity.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Lütfen tüm alanları doldurun",
                "Hata",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            new BigDecimal(txtPrice.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                "Geçersiz fiyat formatı",
                "Hata",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            int capacity = Integer.parseInt(txtCapacity.getText());
            if (capacity <= 0) {
                JOptionPane.showMessageDialog(this,
                    "Kapasite 0'dan büyük olmalıdır",
                    "Hata",
                    JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                "Geçersiz kapasite değeri",
                "Hata",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    public boolean isSuccess() {
        return success;
    }
}
