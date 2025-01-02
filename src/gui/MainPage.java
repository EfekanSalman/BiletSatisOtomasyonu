package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.net.URI;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import database.*;
import models.*;
import java.text.SimpleDateFormat;
import database.UserDB;

public class MainPage extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private DefaultTableModel concertTableModel;
    private DefaultTableModel museumTableModel;
    private DefaultTableModel festivalTableModel;
    private DefaultTableModel stageTableModel;
    private DefaultTableModel amusementParkTableModel;
    private DefaultTableModel ticketsTableModel;
    private JTable concertTable;
    private JTable museumTable;
    private JTable festivalTable;
    private JTable stageTable;
    private JTable amusementParkTable;
    private JTable ticketsTable;
    private SimpleDateFormat dateFormat;
    private int userId;
    private String userName;
    private CityDB cityDB;
    private TicketDB ticketDB;
    private JComboBox<String> cityFilterCombo;
    private JComboBox<String> priceFilterCombo;
    private DefaultTableModel eventsTableModel;
    private JTable eventsTable;
    private JComboBox<String> eventTypeCombo;
    private static final Color PRIMARY_COLOR = new Color(70, 130, 180);
    private static final Color DANGER_COLOR = new Color(220, 53, 69);
    private static final Color SUCCESS_COLOR = new Color(40, 167, 69);
    private static final Color WARNING_COLOR = new Color(255, 193, 7);
    private UserDB userDB;
    


    public MainPage(int userId, String userName) {
        this.userId = userId;
        this.userName = userName;
        initializeDatabases();
        initializeComponents();
        loadAllData();
    }

    private void initializeDatabases() {
        cityDB = new CityDB();
        ticketDB = new TicketDB();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        JPanel filtersPanel = new JPanel();
        filtersPanel.setBounds(330, 20, 600, 30);
        filtersPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
    }

    private void initializeComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 700);
        setTitle("Etkinlik Bilet Sistemi");
        userDB = new UserDB(); // UserDB'yi başlatma
        
        

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblWelcome = new JLabel("Hoşgeldiniz, " + userName);
        lblWelcome.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblWelcome.setBounds(20, 20, 300, 30);
        contentPane.add(lblWelcome);

        JPanel filtersPanel = new JPanel();
        filtersPanel.setBounds(330, 20, 600, 30);
        filtersPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JLabel lblEventType = new JLabel("Etkinlik Türü:");
        eventTypeCombo = new JComboBox<>(new String[]{
            "Tümü", "Konserler", "Müzeler", "Festivaller", "Sahne", "Lunapark"
        });

        JLabel lblCity = new JLabel("Şehir:");
        cityFilterCombo = new JComboBox<>(new String[]{"Tümü"});
        loadCities();

        JLabel lblPrice = new JLabel("Fiyat:");
        priceFilterCombo = new JComboBox<>(new String[]{"Varsayılan", "Artan", "Azalan"});

        filtersPanel.add(lblEventType);
        filtersPanel.add(eventTypeCombo);
        filtersPanel.add(Box.createHorizontalStrut(20));
        filtersPanel.add(lblCity);
        filtersPanel.add(cityFilterCombo);
        filtersPanel.add(Box.createHorizontalStrut(20));
        filtersPanel.add(lblPrice);
        filtersPanel.add(priceFilterCombo);

        contentPane.add(filtersPanel);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBounds(10, 60, 960, 580);
        contentPane.add(tabbedPane);

        String[] columnNames = {"Tür", "İsim", "Tarih", "Şehir", "Lokasyon", "Fiyat"};
        eventsTableModel = new DefaultTableModel(new Object[][]{}, columnNames);
        eventsTable = new JTable(eventsTableModel);

        tabbedPane.addTab("Etkinlikler", createEventsPanel());
        tabbedPane.addTab("Biletlerim", createTicketsPanel());
        tabbedPane.addTab("Profilim", createProfilePanel());
        tabbedPane.addTab("SSS", createFAQPanel());
        tabbedPane.addTab("Ayarlar", createSettingsPanel());


        eventTypeCombo.addActionListener(e -> loadFilteredEvents());
        cityFilterCombo.addActionListener(e -> loadFilteredEvents());
        priceFilterCombo.addActionListener(e -> loadFilteredEvents());
    }
    
    
    private JPanel createEventsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        String[] columnNames = {"Tür", "İsim", "Tarih", "Şehir", "Lokasyon", "Fiyat", "Kapasite", "Kalan Bilet"};
        eventsTableModel = new DefaultTableModel(new Object[][]{}, columnNames);
        eventsTable = new JTable(eventsTableModel);

        JScrollPane scrollPane = new JScrollPane(eventsTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton btnBuy = new JButton("Satın Al");
        btnBuy.setBackground(SUCCESS_COLOR);
        btnBuy.setForeground(Color.WHITE);
        btnBuy.setFocusPainted(false);
        btnBuy.setBorderPainted(false);
        btnBuy.addActionListener(e -> purchaseTicket(eventsTable));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(btnBuy);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }


    private void loadFilteredEvents() {
        eventsTableModel.setRowCount(0);
        String selectedEventType = (String) eventTypeCombo.getSelectedItem();
        String selectedCity = (String) cityFilterCombo.getSelectedItem();
        String priceSort = (String) priceFilterCombo.getSelectedItem();

        try {
            // Konserler
            if ("Tümü".equals(selectedEventType) || "Konserler".equals(selectedEventType)) {
                loadFilteredConcerts(selectedCity, priceSort);
            }

            // Müzeler
            if ("Tümü".equals(selectedEventType) || "Müzeler".equals(selectedEventType)) {
                loadFilteredMuseums(selectedCity, priceSort);
            }

            // Festivaller
            if ("Tümü".equals(selectedEventType) || "Festivaller".equals(selectedEventType)) {
                loadFilteredFestivals(selectedCity, priceSort);
            }

            // Sahne
            if ("Tümü".equals(selectedEventType) || "Sahne".equals(selectedEventType)) {
                loadFilteredStages(selectedCity, priceSort);
            }

            // Lunapark
            if ("Tümü".equals(selectedEventType) || "Lunapark".equals(selectedEventType)) {
                loadFilteredAmusementParks(selectedCity, priceSort);
            }
        } catch (SQLException e) {
            showError("Etkinlikler yüklenirken hata oluştu: " + e.getMessage());
        }
    }

    private void loadFilteredConcerts(String selectedCity, String priceSort) throws SQLException {
        ConcertDB concertDB = new ConcertDB();
        List<Concert> concerts;

        if ("Artan".equals(priceSort)) {
            concerts = concertDB.getConcertsSortedByPriceAsc();
        } else if ("Azalan".equals(priceSort)) {
            concerts = concertDB.getConcertsSortedByPriceDesc();
        } else {
            concerts = concertDB.getAllConcerts();
        }

        for (Concert concert : concerts) {
            String cityName = CityDB.getCityName(concert.getCityId());
            if ("Tümü".equals(selectedCity) || selectedCity.equals(cityName)) {
                int bookedSeats = ticketDB.getBookedSeatsCount(2, concert.getId());
                int remainingSeats = concert.getCapacity() - bookedSeats;
                
                eventsTableModel.addRow(new Object[]{
                    "Konser",
                    concert.getConcertName(),
                    dateFormat.format(concert.getEventDate()),
                    cityName,
                    concert.getLocation(),
                    concert.getPrice(),
                    concert.getCapacity(),
                    remainingSeats
                });
            }
        }
    }
    
    
    private void loadFilteredMuseums(String selectedCity, String priceSort) throws SQLException {
        MuseumDB museumDB = new MuseumDB();
        List<Museum> museums;

        if ("Artan".equals(priceSort)) {
            museums = museumDB.getMuseumsSortedByPriceAsc();
        } else if ("Azalan".equals(priceSort)) {
            museums = museumDB.getMuseumsSortedByPriceDesc();
        } else {
            museums = museumDB.getAllMuseums();
        }

        for (Museum museum : museums) {
            String cityName = CityDB.getCityName(museum.getCityId());
            if ("Tümü".equals(selectedCity) || selectedCity.equals(cityName)) {
                int bookedSeats = ticketDB.getBookedSeatsCount(6, museum.getId());
                int remainingSeats = museum.getCapacity() - bookedSeats;
                
                eventsTableModel.addRow(new Object[]{
                    "Müze",
                    museum.getMuseumName(),
                    dateFormat.format(museum.getEventDate()),
                    cityName,
                    museum.getLocation(),
                    museum.getPrice(),
                    museum.getCapacity(),
                    remainingSeats
                });
            }
        }
    }

    private void loadFilteredFestivals(String selectedCity, String priceSort) throws SQLException {
        FestivalDB festivalDB = new FestivalDB();
        List<Festival> festivals;

        if ("Artan".equals(priceSort)) {
            festivals = festivalDB.getFestivalsSortedByPriceAsc();
        } else if ("Azalan".equals(priceSort)) {
            festivals = festivalDB.getFestivalsSortedByPriceDesc();
        } else {
            festivals = festivalDB.getAllFestivals();
        }

        for (Festival festival : festivals) {
            String cityName = CityDB.getCityName(festival.getCityId());
            if ("Tümü".equals(selectedCity) || selectedCity.equals(cityName)) {
                int bookedSeats = ticketDB.getBookedSeatsCount(3, festival.getId());
                int remainingSeats = festival.getCapacity() - bookedSeats;
                
                eventsTableModel.addRow(new Object[]{
                    "Festival",
                    festival.getFestivalType(),
                    dateFormat.format(festival.getEventDate()),
                    cityName,
                    festival.getLocation(),
                    festival.getPrice(),
                    festival.getCapacity(),
                    remainingSeats
                });
            }
        }
    }

    private void loadFilteredStages(String selectedCity, String priceSort) throws SQLException {
        StageDB stageDB = new StageDB();
        List<Stage> stages;

        if ("Artan".equals(priceSort)) {
            stages = stageDB.getStagesSortedByPriceAsc();
        } else if ("Azalan".equals(priceSort)) {
            stages = stageDB.getStagesSortedByPriceDesc();
        } else {
            stages = stageDB.getAllStages();
        }

        for (Stage stage : stages) {
            String cityName = CityDB.getCityName(stage.getCityId());
            if ("Tümü".equals(selectedCity) || selectedCity.equals(cityName)) {
                int bookedSeats = ticketDB.getBookedSeatsCount(4, stage.getId());
                int remainingSeats = stage.getCapacity() - bookedSeats;
                
                eventsTableModel.addRow(new Object[]{
                    "Sahne",
                    stage.getEventName(),
                    dateFormat.format(stage.getEventDate()),
                    cityName,
                    stage.getLocation(),
                    stage.getPrice(),
                    stage.getCapacity(),
                    remainingSeats
                });
            }
        }
    }

    private void loadFilteredAmusementParks(String selectedCity, String priceSort) throws SQLException {
        AmusementParkDB parkDB = new AmusementParkDB();
        List<AmusementPark> parks;

        if ("Artan".equals(priceSort)) {
            parks = parkDB.getAmusementParksSortedByPriceAsc();
        } else if ("Azalan".equals(priceSort)) {
            parks = parkDB.getAmusementParksSortedByPriceDesc();
        } else {
            parks = parkDB.getAllAmusementParks();
        }

        for (AmusementPark park : parks) {
            String cityName = CityDB.getCityName(park.getCityId());
            if ("Tümü".equals(selectedCity) || selectedCity.equals(cityName)) {
                int bookedSeats = ticketDB.getBookedSeatsCount(7, park.getId());
                int remainingSeats = park.getCapacity() - bookedSeats;
                
                eventsTableModel.addRow(new Object[]{
                    "Lunapark",
                    park.getParkName(),
                    dateFormat.format(park.getEventDate()),
                    cityName,
                    park.getLocation(),
                    park.getPrice(),
                    park.getCapacity(),
                    remainingSeats
                });
            }
        }
     }  
    
    
    private JPanel createTicketsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        String[] columnNames = {"Bilet No", "Etkinlik", "Tarih", "Şehir", "Lokasyon", "Fiyat", "Bilet Adedi"};
        ticketsTableModel = new DefaultTableModel(new Object[][]{}, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        ticketsTable = new JTable(ticketsTableModel);

        // Tablo ayarları
        ticketsTable.getColumnModel().getColumn(0).setPreferredWidth(70);  // Bilet No
        ticketsTable.getColumnModel().getColumn(1).setPreferredWidth(200); // Etkinlik
        ticketsTable.getColumnModel().getColumn(2).setPreferredWidth(100); // Tarih
        ticketsTable.getColumnModel().getColumn(3).setPreferredWidth(100); // Şehir
        ticketsTable.getColumnModel().getColumn(4).setPreferredWidth(150); // Lokasyon
        ticketsTable.getColumnModel().getColumn(5).setPreferredWidth(80);  // Fiyat
        ticketsTable.getColumnModel().getColumn(6).setPreferredWidth(80);  // Bilet Adedi

        JScrollPane scrollPane = new JScrollPane(ticketsTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton btnCancel = new JButton("Bileti İptal Et");
        btnCancel.setBackground(DANGER_COLOR);
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setFocusPainted(false);
        btnCancel.setBorderPainted(false);
        btnCancel.addActionListener(e -> cancelTicket());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(btnCancel);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createFAQPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel faqContent = new JPanel();
        faqContent.setLayout(new BoxLayout(faqContent, BoxLayout.Y_AXIS));
        faqContent.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        addFAQItem(faqContent, "Bilet nasıl satın alabilirim?",
            "1. 'Etkinlikler' sekmesinden istediğiniz etkinliği seçin\n" +
            "2. Etkinlik türü, şehir ve fiyat filtrelerini kullanarak arama yapabilirsiniz\n" +
            "3. İstediğiniz etkinliği seçtikten sonra 'Satın Al' butonuna tıklayın\n" +
            "4. Onay verdikten sonra biletiniz 'Biletlerim' sekmesinde görünecektir");

        addFAQItem(faqContent, "Biletimi iptal edebilir miyim?",
            "Evet, 'Biletlerim' sekmesinden iptal etmek istediğiniz bileti seçip " +
            "'Bileti İptal Et' butonuna tıklayabilirsiniz. İptal edilen biletler için " +
            "ödeme iadesi 3-5 iş günü içinde yapılacaktır.");

        addFAQItem(faqContent, "Ödeme yöntemleri nelerdir?",
            "Şu anda sadece kredi/banka kartı ile ödeme kabul edilmektedir. ");

        addFAQItem(faqContent, "Etkinlik tarihini değiştirebilir miyim?",
            "Hayır, etkinlik tarihleri sabittir. Tarih değişikliği için:\n" +
            "1. Mevcut biletinizi iptal edin\n" +
            "2. İstediğiniz yeni tarih için bilet alın\n\n" +
            "Not: Bazı özel etkinliklerde tarih değişikliği mümkün olabilir, " +
            "bunun için müşteri hizmetleri ile iletişime geçin.");

        addFAQItem(faqContent, "Teknik destek nasıl alabilirim?",
            "Aşağıdaki yöntemlerle bize ulaşabilirsiniz:\n" +
            "1. 'Canlı Destek Al' butonu ile görüntülü görüşme yapabilirsiniz\n" +
            "2. E-posta: destek@bilet.com\n" +
            "3. Telefon: 0850 123 45 67\n" +
            "4. Sosyal Medya: @biletsistemi");

        JScrollPane scrollPane = new JScrollPane(faqContent);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton btnLiveSupport = new JButton("Canlı Destek Al");
        btnLiveSupport.setBackground(SUCCESS_COLOR);
        btnLiveSupport.setForeground(Color.WHITE);
        btnLiveSupport.setFocusPainted(false);
        btnLiveSupport.setBorderPainted(false);
        btnLiveSupport.addActionListener(e -> openZoomSupport());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        buttonPanel.add(btnLiveSupport);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }
    
    

    private JPanel createProfilePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Ad:"), gbc);

        gbc.gridx = 1;
        JLabel lblFirstName = new JLabel();
        formPanel.add(lblFirstName, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Soyad:"), gbc);

        gbc.gridx = 1;
        JLabel lblLastName = new JLabel();
        formPanel.add(lblLastName, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Kullanıcı Adı:"), gbc);

        gbc.gridx = 1;
        JLabel lblUsername = new JLabel();
        formPanel.add(lblUsername, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("E-posta:"), gbc);

        gbc.gridx = 1;
        JLabel lblEmail = new JLabel();
        formPanel.add(lblEmail, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(new JLabel("Kart Numarası:"), gbc);

        gbc.gridx = 1;
        JLabel lblCardNumber = new JLabel();
        formPanel.add(lblCardNumber, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(new JLabel("CVV:"), gbc);

        gbc.gridx = 1;
        JLabel lblCVV = new JLabel();
        formPanel.add(lblCVV, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton btnUpdate = new JButton("Bilgileri Güncelle");
        btnUpdate.setBackground(SUCCESS_COLOR);
        btnUpdate.setForeground(Color.WHITE);
        btnUpdate.setFocusPainted(false);
        formPanel.add(btnUpdate, gbc);

        try {
            Customer customer = (Customer) userDB.getUserById(userId);
            if (customer != null) {
                lblFirstName.setText(customer.getFirstName());
                lblLastName.setText(customer.getLastName());
                lblUsername.setText(customer.getUsername());
                lblEmail.setText(customer.getEmail());
                String cardNumber = customer.getCardNumber();
                lblCardNumber.setText("**** **** **** " + cardNumber.substring(cardNumber.length() - 4));
                lblCVV.setText("***");
            }
        } catch (SQLException e) {
            showError("Kullanıcı bilgileri yüklenirken hata oluştu: " + e.getMessage());
        }

        btnUpdate.addActionListener(e -> {
            UpdateProfileDialog dialog = new UpdateProfileDialog(this, userId);
            dialog.setVisible(true);
            if (dialog.isSuccess()) {
                try {
                    Customer customer = (Customer) userDB.getUserById(userId);
                    if (customer != null) {
                        lblFirstName.setText(customer.getFirstName());
                        lblLastName.setText(customer.getLastName());
                        lblUsername.setText(customer.getUsername());
                        lblEmail.setText(customer.getEmail());
                        String cardNumber = customer.getCardNumber();
                        lblCardNumber.setText("**** **** **** " + cardNumber.substring(cardNumber.length() - 4));
                        lblCVV.setText("***");
                        
                        userName = customer.getFirstName() + " " + customer.getLastName();
                    }
                } catch (SQLException ex) {
                    showError("Profil bilgileri güncellenirken hata oluştu: " + ex.getMessage());
                }
            }
        });

        panel.add(formPanel, BorderLayout.CENTER);
        return panel;
    }
    

    private void addFAQItem(JPanel panel, String question, String answer) {
        JPanel itemPanel = new JPanel();
        itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));
        itemPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY),
            BorderFactory.createEmptyBorder(10, 0, 10, 0)
        ));
        itemPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblQuestion = new JLabel(question);
        lblQuestion.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblQuestion.setForeground(PRIMARY_COLOR);

        JTextArea txtAnswer = new JTextArea(answer);
        txtAnswer.setWrapStyleWord(true);
        txtAnswer.setLineWrap(true);
        txtAnswer.setOpaque(false);
        txtAnswer.setEditable(false);
        txtAnswer.setFont(new Font("Tahoma", Font.PLAIN, 12));
        txtAnswer.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        itemPanel.add(lblQuestion);
        itemPanel.add(Box.createVerticalStrut(5));
        itemPanel.add(txtAnswer);
        panel.add(itemPanel);
        panel.add(Box.createVerticalStrut(10));
    }

    private JPanel createSettingsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton btnLogout = new JButton("Oturumu Kapat");
        btnLogout.setBackground(WARNING_COLOR);
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setFocusPainted(false);
        btnLogout.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLogout.setMaximumSize(new Dimension(200, 40));
        btnLogout.addActionListener(e -> logout());

        JButton btnDeleteAccount = new JButton("Hesabı Sil");
        btnDeleteAccount.setBackground(DANGER_COLOR);
        btnDeleteAccount.setForeground(Color.WHITE);
        btnDeleteAccount.setFocusPainted(false);
        btnDeleteAccount.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnDeleteAccount.setMaximumSize(new Dimension(200, 40));
        btnDeleteAccount.addActionListener(e -> deleteAccount());

        buttonPanel.add(Box.createVerticalGlue());
        buttonPanel.add(btnLogout);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(btnDeleteAccount);
        buttonPanel.add(Box.createVerticalGlue());

        panel.add(buttonPanel, BorderLayout.CENTER);
        return panel;
    }

    private void openZoomSupport() {
        try {
            Desktop.getDesktop().browse(new URI("https://zoom.us/j/id")); //buraya kendi id koy!
        } catch (Exception ex) {
            showError("Zoom bağlantısı açılırken hata oluştu: " + ex.getMessage());
        }
    }

    private void logout() {
        int response = JOptionPane.showConfirmDialog(this,
            "Oturumu kapatmak istediğinize emin misiniz?",
            "Oturumu Kapat",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);

        if (response == JOptionPane.YES_OPTION) {
            LoginPage loginPage = new LoginPage();
            loginPage.setVisible(true);
            dispose();
        }
    }

    private void deleteAccount() {
        int response = JOptionPane.showConfirmDialog(this,
            "Hesabınızı silmek istediğinize emin misiniz?\nBu işlem geri alınamaz!",
            "Hesabı Sil",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);

        if (response == JOptionPane.YES_OPTION) {
            try {
                if (userDB.deleteUser(userId)) {
                    JOptionPane.showMessageDialog(this,
                        "Hesabınız başarıyla silindi.",
                        "Başarılı",
                        JOptionPane.INFORMATION_MESSAGE);
                    LoginPage loginPage = new LoginPage();
                    loginPage.setVisible(true);
                    dispose();
                }
            } catch (SQLException ex) {
                showError("Hesap silinirken hata oluştu: " + ex.getMessage());
            }
        }
    }

    private void loadCities() {
        try {
            List<City> cities = cityDB.getAllCities();
            for (City city : cities) {
                cityFilterCombo.addItem(city.getCityName());
            }
        } catch (SQLException e) {
            showError("Şehirler yüklenirken hata oluştu: " + e.getMessage());
        }
    }

    private void loadAllData() {
        loadUserTickets();
    }



    

    private void loadUserTickets() {
        try {
            ticketsTableModel.setRowCount(0);
            List<Ticket> tickets = ticketDB.getUserTickets(userId);       
            Map<String, List<Ticket>> ticketGroups = new HashMap<>();
            Map<String, String[]> eventDetailsMap = new HashMap<>();
            
            for (Ticket ticket : tickets) {
                try {
                    String[] eventDetails = getEventDetails(ticket.getEventTypeId(), ticket.getEventId());
                    String eventKey = ticket.getEventTypeId() + "-" + ticket.getEventId();
                    
                    ticketGroups.computeIfAbsent(eventKey, k -> new ArrayList<>()).add(ticket);
                    eventDetailsMap.put(eventKey, eventDetails);
                } catch (SQLException e) {
                    System.err.println("Bilet yükleme hatası " + ticket.getTicketId() + ": " + e.getMessage());
                }
            }
            
            for (Map.Entry<String, List<Ticket>> entry : ticketGroups.entrySet()) {
                String eventKey = entry.getKey();
                List<Ticket> eventTickets = entry.getValue();
                String[] eventDetails = eventDetailsMap.get(eventKey);
                
                if (eventDetails != null) {
                    ticketsTableModel.addRow(new Object[]{
                        eventTickets.get(0).getTicketId(), // bilet numarasını göster
                        eventDetails[0], // Etkinlik ismi
                        eventDetails[1], // Gün
                        eventDetails[2], // Şehir
                        eventDetails[3], // Lokasyon
                        eventTickets.get(0).getPrice(), // Fiyat
                        eventTickets.size() // Bilet sayısı
                    });
                }
            }
        } catch (SQLException e) {
            showError("Biletler yüklenirken hata oluştu: " + e.getMessage());
        }
    }

    private String[] getEventDetails(int eventTypeId, int eventId) throws SQLException {
        String[] details = new String[4]; // [isim, tarih, şehir, konum]
        
        try {
            switch (eventTypeId) {
                case 2: // Konser
                    ConcertDB concertDB = new ConcertDB();
                    Concert concert = concertDB.getConcertById(eventId);
                    if (concert != null) {
                        details[0] = concert.getConcertName();
                        details[1] = dateFormat.format(concert.getEventDate());
                        details[2] = CityDB.getCityName(concert.getCityId());
                        details[3] = concert.getLocation();
                    }
                    break;
                    
                case 3: // Festival
                    FestivalDB festivalDB = new FestivalDB();
                    Festival festival = festivalDB.getFestivalById(eventId);
                    if (festival != null) {
                        details[0] = festival.getFestivalType();
                        details[1] = dateFormat.format(festival.getEventDate());
                        details[2] = CityDB.getCityName(festival.getCityId());
                        details[3] = festival.getLocation();
                    }
                    break;
                    
                case 4: // Sahne
                    StageDB stageDB = new StageDB();
                    Stage stage = stageDB.getStageById(eventId);
                    if (stage != null) {
                        details[0] = stage.getEventName();
                        details[1] = dateFormat.format(stage.getEventDate());
                        details[2] = CityDB.getCityName(stage.getCityId());
                        details[3] = stage.getLocation();
                    }
                    break;
                    
                case 6: // Müze
                    MuseumDB museumDB = new MuseumDB();
                    Museum museum = museumDB.getMuseumById(eventId);
                    if (museum != null) {
                        details[0] = museum.getMuseumName();
                        details[1] = dateFormat.format(museum.getEventDate());
                        details[2] = CityDB.getCityName(museum.getCityId());
                        details[3] = museum.getLocation();
                    }
                    break;
                    
                case 7: // Eğlence parkı
                    AmusementParkDB parkDB = new AmusementParkDB();
                    AmusementPark park = parkDB.getAmusementParkById(eventId);
                    if (park != null) {
                        details[0] = park.getParkName();
                        details[1] = dateFormat.format(park.getEventDate());
                        details[2] = CityDB.getCityName(park.getCityId());
                        details[3] = park.getLocation();
                    }
                    break;
                    
                default:
                    throw new IllegalArgumentException("Unknown event type ID: " + eventTypeId);
            }
            
            if (details[0] == null) {
                throw new SQLException("Bilinmeyen etkinlik türü kimliği: " + eventTypeId + " kimliği: " + eventId);
            }
            
            return details;
        } catch (SQLException e) {
            System.err.println("Olay detayları alınırken hata oluştu: " + e.getMessage());
            throw e;
        }
    }


    private void purchaseTicket(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            showError("Lütfen bir etkinlik seçin");
            return;
        }

        try {
            String eventType = (String) table.getValueAt(selectedRow, 0);
            String eventName = (String) table.getValueAt(selectedRow, 1);
            BigDecimal price = (BigDecimal) table.getValueAt(selectedRow, 5);
            int capacity = (int) table.getValueAt(selectedRow, 6);
            int remainingTickets = (int) table.getValueAt(selectedRow, 7);

            if (remainingTickets <= 0) {
                showError("Bu etkinlik için bilet kalmamıştır!");
                return;
            }

            SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1, 1, remainingTickets, 1);
            JSpinner quantitySpinner = new JSpinner(spinnerModel);

            JPanel panel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);

            gbc.gridx = 0;
            gbc.gridy = 0;
            panel.add(new JLabel("Etkinlik:"), gbc);

            gbc.gridx = 1;
            panel.add(new JLabel(eventName), gbc);

            gbc.gridx = 0;
            gbc.gridy = 1;
            panel.add(new JLabel("Bilet Adedi:"), gbc);

            gbc.gridx = 1;
            panel.add(quantitySpinner, gbc);

            gbc.gridx = 0;
            gbc.gridy = 2;
            panel.add(new JLabel("Bilet Fiyatı:"), gbc);

            gbc.gridx = 1;
            panel.add(new JLabel(price + " TL"), gbc);

            gbc.gridx = 0;
            gbc.gridy = 3;
            gbc.gridwidth = 2;
            JLabel totalLabel = new JLabel("Toplam: " + price + " TL");
            panel.add(totalLabel, gbc);

            quantitySpinner.addChangeListener(e -> {
                int quantity = (int) quantitySpinner.getValue();
                BigDecimal total = price.multiply(new BigDecimal(quantity));
                totalLabel.setText("Toplam: " + total + " TL");
            });

            int result = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Bilet Satın Al",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
            );

            if (result == JOptionPane.OK_OPTION) {
                int quantity = (int) quantitySpinner.getValue();
                BigDecimal total = price.multiply(new BigDecimal(quantity));
                
                int confirm = JOptionPane.showConfirmDialog(
                    this,
                    String.format("%s etkinliği için %d adet bilet satın almak istediğinize emin misiniz?%nToplam Tutar: %s TL", 
                        eventName, quantity, total),
                    "Onay",
                    JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    EventType eventTypeEnum = EventType.fromDisplayName(eventType);
                    int eventId = getEventId(eventTypeEnum, eventName);
                    boolean allPurchasesSuccessful = true;
                    for (int i = 0; i < quantity; i++) {
                        if (!ticketDB.purchaseTicket(userId, eventTypeEnum.getId(), eventId, price)) {
                            allPurchasesSuccessful = false;
                            break;
                        }
                    }

                    if (allPurchasesSuccessful) {
                        showSuccess(String.format("%d adet bilet başarıyla satın alındı!", quantity));
                        loadUserTickets();
                        loadFilteredEvents();
                    } else {
                        showError("Bilet satın alınırken bir hata oluştu!");
                    }
                }
            }
        } catch (SQLException e) {
            showError("Bilet satın alınırken hata oluştu: " + e.getMessage());
        }
    }

    private void cancelTicket() {
        int selectedRow = ticketsTable.getSelectedRow();
        if (selectedRow == -1) {
            showError("Lütfen iptal edilecek bileti seçin");
            return;
        }

        try {
            int ticketId = (int) ticketsTable.getValueAt(selectedRow, 0);
            int totalTickets = (int) ticketsTable.getValueAt(selectedRow, 6);
            
            if (totalTickets == 1) {
                int confirm = JOptionPane.showConfirmDialog(this,
                    "Bileti iptal etmek istediğinize emin misiniz?",
                    "Bilet İptali",
                    JOptionPane.YES_NO_OPTION);
                    
                if (confirm == JOptionPane.YES_OPTION) {
                    if (ticketDB.cancelTicket(ticketId)) {
                        showSuccess("Bilet başarıyla iptal edildi!");
                        loadUserTickets();
                    }
                }
                return;
            }

            SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1, 1, totalTickets, 1);
            JSpinner quantitySpinner = new JSpinner(spinnerModel);

            JPanel panel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);

            gbc.gridx = 0;
            gbc.gridy = 0;
            panel.add(new JLabel("İptal edilecek bilet sayısı:"), gbc);

            gbc.gridx = 1;
            panel.add(quantitySpinner, gbc);

            int result = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Bilet İptali",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
            );

            if (result == JOptionPane.OK_OPTION) {
                int quantity = (int) quantitySpinner.getValue();
                
                int confirm = JOptionPane.showConfirmDialog(this,
                    String.format("%d adet bileti iptal etmek istediğinize emin misiniz?", quantity),
                    "Bilet İptali",
                    JOptionPane.YES_NO_OPTION);
                    
                if (confirm == JOptionPane.YES_OPTION) {
                    boolean allCancellationsSuccessful = true;
                    for (int i = 0; i < quantity; i++) {
                        if (!ticketDB.cancelTicket(ticketId + i)) {
                            allCancellationsSuccessful = false;
                            break;
                        }
                    }

                    if (allCancellationsSuccessful) {
                        showSuccess(String.format("%d adet bilet başarıyla iptal edildi!", quantity));
                        loadUserTickets();
                    } else {
                        showError("Biletler iptal edilirken bir hata oluştu!");
                    }
                }
            }
        } catch (SQLException e) {
            showError("Bilet iptal edilirken hata oluştu: " + e.getMessage());
        }
    }


    private EventType getEventTypeFromCurrentTab() {
        JTabbedPane tabbedPane = (JTabbedPane) contentPane.getComponent(2);
        String selectedTab = tabbedPane.getTitleAt(tabbedPane.getSelectedIndex());
        return EventType.fromDisplayName(selectedTab);
    }

    private int getEventId(EventType eventType, String eventName) throws SQLException {
        switch (eventType) {
            case CONCERT:
                return new ConcertDB().getConcertByName(eventName).getId();
            case MUSEUM:
                return new MuseumDB().getMuseumByName(eventName).getId();
            case FESTIVAL:
                return new FestivalDB().getFestivalByName(eventName).getId();
            case STAGE:
                return new StageDB().getStageByName(eventName).getId();
            case AMUSEMENT_PARK:
                return new AmusementParkDB().getAmusementParkByName(eventName).getId();
            default:
                throw new IllegalArgumentException("Unknown event type: " + eventType);
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Hata", JOptionPane.ERROR_MESSAGE);
    }

    private void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, message, "Başarılı", JOptionPane.INFORMATION_MESSAGE);
    }
}
