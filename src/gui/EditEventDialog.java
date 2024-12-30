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

public class EditEventDialog extends JDialog {
    private static final long serialVersionUID = 1L;
    private JTextField txtName;
    private JComboBox<String> comboCity;
    private JTextField txtDate;
    private JTextField txtLocation;
    private JTextField txtPrice;
    private JTextField txtCapacity;
    private String eventType;
    private CityDB cityDB;
    private int eventId;
    private boolean success = false;
    private SimpleDateFormat dateFormat;

    public EditEventDialog(JFrame parent, String eventType, int eventId) {
        super(parent, eventType + " Düzenle", true);
        this.eventType = eventType;
        this.eventId = eventId;
        this.cityDB = new CityDB();
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        initializeComponents();
        loadCities();
        loadEventData();
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

        gbc.gridx = 0;
        gbc.gridy = 5;
        add(new JLabel("Kapasite:"), gbc);

        gbc.gridx = 1;
        txtCapacity = new JTextField(20);
        add(txtCapacity, gbc);

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
            case "Konser": return "Konser Adı:";
            case "Müze": return "Müze Adı:";
            case "Festival": return "Festival Türü:";
            case "Sahne": return "Etkinlik Adı:";
            case "Lunapark": return "Park Adı:";
            default: return "İsim:";
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

    private void loadEventData() {
        try {
            switch (eventType) {
                case "Konser":
                    loadConcertData();
                    break;
                case "Müze":
                    loadMuseumData();
                    break;
                case "Festival":
                    loadFestivalData();
                    break;
                case "Sahne":
                    loadStageData();
                    break;
                case "Lunapark":
                    loadAmusementParkData();
                    break;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                "Etkinlik bilgileri yüklenirken hata oluştu: " + e.getMessage(),
                "Hata",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadConcertData() throws SQLException {
        Concert concert = new ConcertDB().getConcertById(eventId);
        if (concert != null) {
            txtName.setText(concert.getConcertName());
            comboCity.setSelectedItem(CityDB.getCityName(concert.getCityId()));
            txtDate.setText(dateFormat.format(concert.getEventDate()));
            txtLocation.setText(concert.getLocation());
            txtPrice.setText(concert.getPrice().toString());
            txtCapacity.setText(String.valueOf(concert.getCapacity()));
        }
    }


    private void loadMuseumData() throws SQLException {
        Museum museum = new MuseumDB().getMuseumById(eventId);
        if (museum != null) {
            txtName.setText(museum.getMuseumName());
            comboCity.setSelectedItem(CityDB.getCityName(museum.getCityId()));
            txtDate.setText(dateFormat.format(museum.getEventDate()));
            txtLocation.setText(museum.getLocation());
            txtPrice.setText(museum.getPrice().toString());
            txtCapacity.setText(String.valueOf(museum.getCapacity()));
        }
    }
    
    private void loadFestivalData() throws SQLException {
        Festival festival = new FestivalDB().getFestivalById(eventId);
        if (festival != null) {
            txtName.setText(festival.getFestivalType());
            comboCity.setSelectedItem(CityDB.getCityName(festival.getCityId()));
            txtDate.setText(dateFormat.format(festival.getEventDate()));
            txtLocation.setText(festival.getLocation());
            txtPrice.setText(festival.getPrice().toString());
            txtCapacity.setText(String.valueOf(festival.getCapacity()));
        }
    }

    private void loadStageData() throws SQLException {
        Stage stage = new StageDB().getStageById(eventId);
        if (stage != null) {
            txtName.setText(stage.getEventName());
            comboCity.setSelectedItem(CityDB.getCityName(stage.getCityId()));
            txtDate.setText(dateFormat.format(stage.getEventDate()));
            txtLocation.setText(stage.getLocation());
            txtPrice.setText(stage.getPrice().toString());
            txtCapacity.setText(String.valueOf(stage.getCapacity()));
        }
    }

    private void loadAmusementParkData() throws SQLException {
        AmusementPark park = new AmusementParkDB().getAmusementParkById(eventId);
        if (park != null) {
            txtName.setText(park.getParkName());
            comboCity.setSelectedItem(CityDB.getCityName(park.getCityId()));
            txtDate.setText(dateFormat.format(park.getEventDate()));
            txtLocation.setText(park.getLocation());
            txtPrice.setText(park.getPrice().toString());
            txtCapacity.setText(String.valueOf(park.getCapacity()));
        }
    }

    private void save() {
        if (!validateInput()) {
            return;
        }

        try {
            String name = txtName.getText();
            String cityName = (String) comboCity.getSelectedItem();
            java.util.Date utilDate = dateFormat.parse(txtDate.getText());
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            String location = txtLocation.getText();
            BigDecimal price = new BigDecimal(txtPrice.getText());
            int capacity = Integer.parseInt(txtCapacity.getText());

            City city = cityDB.getCityByName(cityName);
            if (city == null) {
                throw new Exception("Şehir bulunamadı");
            }

            boolean result = false;
            switch (eventType) {
                case "Konser":
                    result = new ConcertDB().updateConcert(eventId, name, city.getCityId(), sqlDate, location, price, capacity);
                    break;
                case "Müze":
                    result = new MuseumDB().updateMuseum(eventId, name, city.getCityId(), sqlDate, location, price, capacity);
                    break;
                case "Festival":
                    result = new FestivalDB().updateFestival(eventId, name, city.getCityId(), sqlDate, location, price, capacity);
                    break;
                case "Sahne":
                    result = new StageDB().updateStage(eventId, name, city.getCityId(), sqlDate, location, price, capacity);
                    break;
                case "Lunapark":
                    result = new AmusementParkDB().updateAmusementPark(eventId, name, city.getCityId(), sqlDate, location, price, capacity);
                    break;
            }

            if (result) {
                success = true;
                JOptionPane.showMessageDialog(this,
                    "Etkinlik başarıyla güncellendi",
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