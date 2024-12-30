package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import database.*;
import models.*;

public class AdminPage extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private int adminId;
    private UserDB userDB;
    private ConcertDB concertDB;
    private MuseumDB museumDB;
    private FestivalDB festivalDB;
    private StageDB stageDB;
    private AmusementParkDB amusementParkDB;
    private JTable userTable;
    private JTable eventTable;
    private DefaultTableModel userTableModel;
    private DefaultTableModel eventTableModel;
    private JComboBox<String> eventTypeCombo;
    private JComboBox<String> userRoleCombo;
    private TicketDB ticketDB;

    public AdminPage(int adminId) {
        this.adminId = adminId;
        initializeDatabases();
        initializeComponents();
        loadData();
    }

    private void initializeDatabases() {
        userDB = new UserDB();
        concertDB = new ConcertDB();
        museumDB = new MuseumDB();
        festivalDB = new FestivalDB();
        stageDB = new StageDB();
        amusementParkDB = new AmusementParkDB();
        ticketDB = new TicketDB();
    }

    private void initializeComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 700);
        setTitle("Admin Panel");
        
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblWelcome = new JLabel("Admin Panel");
        lblWelcome.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblWelcome.setBounds(20, 20, 200, 30);
        contentPane.add(lblWelcome);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBounds(10, 60, 960, 580);
        contentPane.add(tabbedPane);

        JPanel usersPanel = new JPanel();
        tabbedPane.addTab("Kullanıcılar", null, usersPanel, null);
        usersPanel.setLayout(null);

        JScrollPane userScrollPane = new JScrollPane();
        userScrollPane.setBounds(10, 50, 920, 400);
        usersPanel.add(userScrollPane);

        userTableModel = new DefaultTableModel(
            new Object[][] {},
            new String[] {"ID", "Ad", "Soyad", "Kullanıcı Adı", "E-posta", "Rol"}
        );
        userTable = new JTable(userTableModel);
        userScrollPane.setViewportView(userTable);

        JLabel lblRole = new JLabel("Rol Değiştir:");
        lblRole.setBounds(10, 460, 80, 25);
        usersPanel.add(lblRole);

        userRoleCombo = new JComboBox<>(new String[]{"Customer", "Admin"});
        userRoleCombo.setBounds(100, 460, 120, 25);
        usersPanel.add(userRoleCombo);

        JButton btnUpdateRole = new JButton("Rolü Güncelle");
        btnUpdateRole.setBounds(230, 460, 120, 25);
        usersPanel.add(btnUpdateRole);
        
        JButton btnDeleteUser = new JButton("Kullanıcıyı Sil");
        btnDeleteUser.setBounds(360, 460, 120, 25);
        btnDeleteUser.setBackground(new Color(220, 53, 69));
        btnDeleteUser.setForeground(Color.WHITE);
        btnDeleteUser.setFocusPainted(false);
        usersPanel.add(btnDeleteUser);
        
        btnDeleteUser.addActionListener(e -> deleteSelectedUser());

        JPanel eventsPanel = new JPanel();
        tabbedPane.addTab("Etkinlikler", null, eventsPanel, null);
        eventsPanel.setLayout(null);

        JLabel lblEventType = new JLabel("Etkinlik Türü:");
        lblEventType.setBounds(10, 10, 80, 25);
        eventsPanel.add(lblEventType);

        eventTypeCombo = new JComboBox<>(new String[]{
            "Konser", "Müze", "Festival", "Sahne", "Lunapark"
        });
        eventTypeCombo.setBounds(100, 10, 120, 25);
        eventsPanel.add(eventTypeCombo);

        JButton btnAddEvent = new JButton("Yeni Etkinlik Ekle");
        btnAddEvent.setBounds(230, 10, 140, 25);
        eventsPanel.add(btnAddEvent);

        JScrollPane eventScrollPane = new JScrollPane();
        eventScrollPane.setBounds(10, 50, 920, 400);
        eventsPanel.add(eventScrollPane);

        eventTableModel = new DefaultTableModel(
                new Object[][] {},
                new String[] {"ID", "İsim", "Şehir", "Tarih", "Lokasyon", "Fiyat", "Kapasite", "Kalan Bilet"}
            );

        eventTable = new JTable(eventTableModel);
        eventScrollPane.setViewportView(eventTable);
        
        JButton btnEditEvent = new JButton("Etkinliği Düzenle");
        btnEditEvent.setBounds(140, 460, 140, 25);
        eventsPanel.add(btnEditEvent);

        btnEditEvent.addActionListener(e -> editSelectedEvent());

        JButton btnDeleteEvent = new JButton("Etkinliği Sil");
        btnDeleteEvent.setBounds(10, 460, 120, 25);
        eventsPanel.add(btnDeleteEvent);

        btnUpdateRole.addActionListener(e -> updateUserRole());
        btnAddEvent.addActionListener(e -> showAddEventDialog());
        btnDeleteEvent.addActionListener(e -> deleteSelectedEvent());
        eventTypeCombo.addActionListener(e -> loadEventsByType());
    }

    private void loadData() {
        loadUsers();
        loadEventsByType();
    }

    private void loadUsers() {
        try {
            userTableModel.setRowCount(0);
            List<User> users = userDB.getAllUsers();
            for (User user : users) {
                userTableModel.addRow(new Object[]{
                    user.getUserId(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getRole()
                });
            }
        } catch (SQLException e) {
            showError("Kullanıcılar yüklenirken hata oluştu: " + e.getMessage());
        }
    }

    private void loadEventsByType() {
        try {
            eventTableModel.setRowCount(0);
            String selectedType = (String) eventTypeCombo.getSelectedItem();
            
            switch (selectedType) {
                case "Konser":
                    loadConcerts();
                    break;
                case "Müze":
                    loadMuseums();
                    break;
                case "Festival":
                    loadFestivals();
                    break;
                case "Sahne":
                    loadStages();
                    break;
                case "Lunapark":
                    loadAmusementParks();
                    break;
            }
        } catch (SQLException e) {
            showError("Etkinlikler yüklenirken hata oluştu: " + e.getMessage());
        }
    }

    private void loadConcerts() throws SQLException {
        List<Concert> concerts = concertDB.getAllConcerts();
        for (Concert concert : concerts) {
            int bookedSeats = ticketDB.getBookedSeatsCount(2, concert.getId());
            int remainingSeats = concert.getCapacity() - bookedSeats;
            eventTableModel.addRow(new Object[]{
                concert.getId(),
                concert.getConcertName(),
                CityDB.getCityName(concert.getCityId()),
                concert.getEventDate(),
                concert.getLocation(),
                concert.getPrice(),
                concert.getCapacity(),
                remainingSeats
            });
        }
    }


    private void loadMuseums() throws SQLException {
        List<Museum> museums = museumDB.getAllMuseums();
        for (Museum museum : museums) {
            int bookedSeats = ticketDB.getBookedSeatsCount(6, museum.getId());
            int remainingSeats = museum.getCapacity() - bookedSeats;
            eventTableModel.addRow(new Object[]{
                museum.getId(),
                museum.getMuseumName(),
                CityDB.getCityName(museum.getCityId()),
                museum.getEventDate(),
                museum.getLocation(),
                museum.getPrice(),
                museum.getCapacity(),
                remainingSeats
            });
        }
    }

    private void loadFestivals() throws SQLException {
        List<Festival> festivals = festivalDB.getAllFestivals();
        for (Festival festival : festivals) {
            int bookedSeats = ticketDB.getBookedSeatsCount(3, festival.getId());
            int remainingSeats = festival.getCapacity() - bookedSeats;
            eventTableModel.addRow(new Object[]{
                festival.getId(),
                festival.getFestivalType(),
                CityDB.getCityName(festival.getCityId()),
                festival.getEventDate(),
                festival.getLocation(),
                festival.getPrice(),
                festival.getCapacity(),
                remainingSeats
            });
        }
    }

    private void loadStages() throws SQLException {
        List<Stage> stages = stageDB.getAllStages();
        for (Stage stage : stages) {
            int bookedSeats = ticketDB.getBookedSeatsCount(4, stage.getId());
            int remainingSeats = stage.getCapacity() - bookedSeats;
            eventTableModel.addRow(new Object[]{
                stage.getId(),
                stage.getEventName(),
                CityDB.getCityName(stage.getCityId()),
                stage.getEventDate(),
                stage.getLocation(),
                stage.getPrice(),
                stage.getCapacity(),
                remainingSeats
            });
        }
    }

    private void loadAmusementParks() throws SQLException {
        List<AmusementPark> parks = amusementParkDB.getAllAmusementParks();
        for (AmusementPark park : parks) {
            int bookedSeats = ticketDB.getBookedSeatsCount(7, park.getId());
            int remainingSeats = park.getCapacity() - bookedSeats;
            eventTableModel.addRow(new Object[]{
                park.getId(),
                park.getParkName(),
                CityDB.getCityName(park.getCityId()),
                park.getEventDate(),
                park.getLocation(),
                park.getPrice(),
                park.getCapacity(),
                remainingSeats
            });
        }
    }

    private void updateUserRole() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow == -1) {
            showError("Lütfen bir kullanıcı seçin");
            return;
        }

        int userId = (int) userTable.getValueAt(selectedRow, 0);
        String newRole = (String) userRoleCombo.getSelectedItem();

        try {
            userDB.updateUserRole(userId, newRole);
            loadUsers();
            JOptionPane.showMessageDialog(this, 
                "Kullanıcı rolü başarıyla güncellendi", 
                "Başarılı", 
                JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            showError("Rol güncellenirken hata oluştu: " + e.getMessage());
        }
    }

    private void showAddEventDialog() {
        String selectedType = (String) eventTypeCombo.getSelectedItem();
        AddEventDialog dialog = new AddEventDialog(this, selectedType);
        dialog.setVisible(true);
        
        if (dialog.isSuccess()) {
            loadEventsByType();
        }
    }

    private void deleteSelectedEvent() {
        int selectedRow = eventTable.getSelectedRow();
        if (selectedRow == -1) {
            showError("Lütfen bir etkinlik seçin");
            return;
        }

        int eventId = (int) eventTable.getValueAt(selectedRow, 0);
        String selectedType = (String) eventTypeCombo.getSelectedItem();

        try {
            boolean success = false;
            switch (selectedType) {
                case "Konser":
                    success = concertDB.deleteConcert(eventId);
                    break;
                case "Müze":
                    success = museumDB.deleteMuseum(eventId);
                    break;
                case "Festival":
                    success = festivalDB.deleteFestival(eventId);
                    break;
                case "Sahne":
                    success = stageDB.deleteStage(eventId);
                    break;
                case "Lunapark":
                    success = amusementParkDB.deleteAmusementPark(eventId);
                    break;
            }

            if (success) {
                loadEventsByType();
                JOptionPane.showMessageDialog(this, 
                    "Etkinlik başarıyla silindi", 
                    "Başarılı", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            showError("Etkinlik silinirken hata oluştu: " + e.getMessage());
        }
    }
    
    private void editSelectedEvent() {
        int selectedRow = eventTable.getSelectedRow();
        if (selectedRow == -1) {
            showError("Lütfen bir etkinlik seçin");
            return;
        }

        int eventId = (int) eventTable.getValueAt(selectedRow, 0);
        String selectedType = (String) eventTypeCombo.getSelectedItem();

        EditEventDialog dialog = new EditEventDialog(this, selectedType, eventId);
        dialog.setVisible(true);
        
        if (dialog.isSuccess()) {
            loadEventsByType();
        }
    }
    
    private void deleteSelectedUser() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow == -1) {
            showError("Lütfen bir kullanıcı seçin");
            return;
        }

        int userId = (int) userTable.getValueAt(selectedRow, 0);
        String username = (String) userTable.getValueAt(selectedRow, 3);

        // admin kendi hesabıbı silemez
        if (userId == this.adminId) {
            showError("Kendi hesabınızı silemezsiniz!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
            username + " kullanıcısını silmek istediğinize emin misiniz?\n" +
            "Bu işlem geri alınamaz!",
            "Kullanıcı Silme Onayı",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                if (userDB.deleteUser(userId)) {
                    loadUsers(); // Silinndikten sonra ekranı yeniden göster
                    JOptionPane.showMessageDialog(this,
                        "Kullanıcı başarıyla silindi",
                        "Başarılı",
                        JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (SQLException ex) {
                showError("Kullanıcı silinirken hata oluştu: " + ex.getMessage());
            }
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this,
            message,
            "Hata",
            JOptionPane.ERROR_MESSAGE);
    }
}