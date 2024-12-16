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

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel(getNameLabel()), gbc);

        gbc.gridx = 1;
        txtName = new JTextField(20);
        add(txtName, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Şehir:"), gbc);

        gbc.gridx = 1;
        comboCity = new JComboBox<>();
        add(comboCity, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Tarih (GG/AA/YYYY):"), gbc);

        gbc.gridx = 1;
        txtDate = new JTextField(20);
        add(txtDate, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Lokasyon:"), gbc);

        gbc.gridx = 1;
        txtLocation = new JTextField(20);
        add(txtLocation, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("Fiyat:"), gbc);

        gbc.gridx = 1;
        txtPrice = new JTextField(20);
        add(txtPrice, gbc);

        JPanel buttonPanel = new JPanel();
        JButton btnSave = new JButton("Kaydet");
        JButton btnCancel = new JButton("İptal");

        btnSave.addActionListener(e -> save());
        btnCancel.addActionListener(e -> dispose());

        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
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

            // City id al
            City city = cityDB.getCityByName(cityName);
            if (city == null) {
                throw new Exception("Şehir bulunamadı");
            }

            boolean result = false;
            switch (eventType) {
                case "Konser":
                    result = new ConcertDB().addConcert(name, city.getCityId(), sqlDate, location, price);
                    break;
                case "Müze":
                    result = new MuseumDB().addMuseum(name, city.getCityId(), sqlDate, location, price);
                    break;
                case "Festival":
                    result = new FestivalDB().addFestival(name, city.getCityId(), sqlDate, location, price);
                    break;
                case "Sahne":
                    result = new StageDB().addStage(name, city.getCityId(), sqlDate, location, price);
                    break;
                case "Lunapark":
                    result = new AmusementParkDB().addAmusementPark(name, city.getCityId(), sqlDate, location, price);
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
            txtLocation.getText().isEmpty() || txtPrice.getText().isEmpty()) {
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

        return true;
    }

    public boolean isSuccess() {
        return success;
    }
}