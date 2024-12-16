package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.net.URI;
import java.sql.SQLException;
import java.util.List;
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

        JLabel lblCity = new JLabel("Şehir:");
        cityFilterCombo = new JComboBox<>(new String[]{"Tümü"});
        loadCities();

        JLabel lblPrice = new JLabel("Fiyat:");
        priceFilterCombo = new JComboBox<>(new String[]{"Varsayılan", "Artan", "Azalan"});

        filtersPanel.add(lblCity);
        filtersPanel.add(cityFilterCombo);
        filtersPanel.add(Box.createHorizontalStrut(20));
        filtersPanel.add(lblPrice);
        filtersPanel.add(priceFilterCombo);

        contentPane.add(filtersPanel);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBounds(10, 60, 960, 580);
        contentPane.add(tabbedPane);

        String[] columnNames = {"İsim", "Tarih", "Şehir", "Lokasyon", "Fiyat"};
        concertTableModel = new DefaultTableModel(new Object[][]{}, columnNames);
        museumTableModel = new DefaultTableModel(new Object[][]{}, columnNames);
        festivalTableModel = new DefaultTableModel(new Object[][]{}, columnNames);
        stageTableModel = new DefaultTableModel(new Object[][]{}, columnNames);
        amusementParkTableModel = new DefaultTableModel(new Object[][]{}, columnNames);
        ticketsTableModel = new DefaultTableModel(
            new Object[][]{},
            new String[]{"Bilet No", "Etkinlik", "Tarih", "Şehir", "Lokasyon", "Fiyat"}
        );

        // oluşturulan tabler
        tabbedPane.addTab("Konserler", createEventPanel(concertTableModel, concertTable = new JTable(concertTableModel)));
        tabbedPane.addTab("Müzeler", createEventPanel(museumTableModel, museumTable = new JTable(museumTableModel)));
        tabbedPane.addTab("Festivaller", createEventPanel(festivalTableModel, festivalTable = new JTable(festivalTableModel)));
        tabbedPane.addTab("Sahne", createEventPanel(stageTableModel, stageTable = new JTable(stageTableModel)));
        tabbedPane.addTab("Lunapark", createEventPanel(amusementParkTableModel, amusementParkTable = new JTable(amusementParkTableModel)));
        tabbedPane.addTab("Biletlerim", createTicketsPanel());
        tabbedPane.addTab("SSS", createFAQPanel());
        tabbedPane.addTab("Ayarlar", createSettingsPanel());
        
        // filtreler
        cityFilterCombo.addActionListener(e -> refreshCurrentTab());
        priceFilterCombo.addActionListener(e -> refreshCurrentTab());
        
        tabbedPane.addChangeListener(e -> refreshCurrentTab());
    }

    private JPanel createEventPanel(DefaultTableModel model, JTable table) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton btnBuy = new JButton("Satın Al");
        btnBuy.setBackground(new Color(0, 128, 0)); // Butonun arka plan rengini kırmızı yap
        btnBuy.setForeground(Color.WHITE); // Butonun metnini beyaz yap
        btnBuy.setFocusPainted(false); // Butona tıklandığında odak rengini gizler
        btnBuy.setBorderPainted(false); // Butonun kenar çizgisini gizler
        btnBuy.addActionListener(e -> purchaseTicket(table));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(btnBuy);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createTicketsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        ticketsTable = new JTable(ticketsTableModel);
        JScrollPane scrollPane = new JScrollPane(ticketsTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton btnCancel = new JButton("Bileti İptal Et");
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
            "İstediğiniz etkinliği seçin ve 'Satın Al' butonuna tıklayın. Ödeme işlemini " +
            "tamamladıktan sonra biletiniz 'Biletlerim' sekmesinde görünecektir.");

        addFAQItem(faqContent, "Biletimi iptal edebilir miyim?",
            "Evet, 'Biletlerim' sekmesinden iptal etmek istediğiniz bileti seçip " +
            "'Bileti İptal Et' butonuna tıklayabilirsiniz.");

        addFAQItem(faqContent, "Ödeme yöntemleri nelerdir?",
            "Şu anda sadece kredi/banka kartı ile ödeme kabul edilmektedir. " +
            "Yakında yeni ödeme yöntemleri eklenecektir.");

        addFAQItem(faqContent, "Etkinlik tarihini değiştirebilir miyim?",
            "Hayır, etkinlik tarihleri sabittir. İptal edip yeni tarih için " +
            "bilet almanız gerekmektedir.");

        addFAQItem(faqContent, "Teknik destek nasıl alabilirim?",
            "Aşağıdaki 'Canlı Destek Al' butonuna tıklayarak bizimle görüntülü " +
            "görüşme yapabilirsiniz.");

        JScrollPane scrollPane = new JScrollPane(faqContent);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton btnLiveSupport = new JButton("Canlı Destek Al");
        btnLiveSupport.setBackground(SUCCESS_COLOR);
        btnLiveSupport.setForeground(Color.WHITE);
        btnLiveSupport.setFocusPainted(false);
        btnLiveSupport.addActionListener(e -> openZoomSupport());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        buttonPanel.add(btnLiveSupport);
        panel.add(buttonPanel, BorderLayout.SOUTH);

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
        loadConcerts();
        loadMuseums();
        loadFestivals();
        loadStages();
        loadAmusementParks();
        loadUserTickets();
    }

    private void refreshCurrentTab() {
        JTabbedPane tabbedPane = (JTabbedPane) contentPane.getComponent(2);
        String selectedTab = tabbedPane.getTitleAt(tabbedPane.getSelectedIndex());
        
        switch (selectedTab) {
            case "Konserler": loadConcerts(); break;
            case "Müzeler": loadMuseums(); break;
            case "Festivaller": loadFestivals(); break;
            case "Sahne": loadStages(); break;
            case "Lunapark": loadAmusementParks(); break;
            case "Biletlerim": loadUserTickets(); break;
        }
    }

    private void loadConcerts() {
        try {
            concertTableModel.setRowCount(0);
            ConcertDB concertDB = new ConcertDB();
            List<Concert> concerts;
            
            String priceSort = (String) priceFilterCombo.getSelectedItem();
            if ("Artan".equals(priceSort)) {
                concerts = concertDB.getConcertsSortedByPriceAsc();
            } else if ("Azalan".equals(priceSort)) {
                concerts = concertDB.getConcertsSortedByPriceDesc();
            } else {
                concerts = concertDB.getAllConcerts();
            }

            String selectedCity = (String) cityFilterCombo.getSelectedItem();
            for (Concert concert : concerts) {
                String cityName = CityDB.getCityName(concert.getCityId());
                if ("Tümü".equals(selectedCity) || selectedCity.equals(cityName)) {
                    concertTableModel.addRow(new Object[]{
                        concert.getConcertName(),
                        dateFormat.format(concert.getEventDate()),
                        cityName,
                        concert.getLocation(),
                        concert.getPrice()
                    });
                }
            }
        } catch (SQLException e) {
            showError("Konserler yüklenirken hata oluştu: " + e.getMessage());
        }
    }

    private void loadMuseums() {
        try {
            museumTableModel.setRowCount(0);
            MuseumDB museumDB = new MuseumDB();
            List<Museum> museums;
            
            String priceSort = (String) priceFilterCombo.getSelectedItem();
            if ("Artan".equals(priceSort)) {
                museums = museumDB.getMuseumsSortedByPriceAsc();
            } else if ("Azalan".equals(priceSort)) {
                museums = museumDB.getMuseumsSortedByPriceDesc();
            } else {
                museums = museumDB.getAllMuseums();
            }

            String selectedCity = (String) cityFilterCombo.getSelectedItem();
            for (Museum museum : museums) {
                String cityName = CityDB.getCityName(museum.getCityId());
                if ("Tümü".equals(selectedCity) || selectedCity.equals(cityName)) {
                    museumTableModel.addRow(new Object[]{
                        museum.getMuseumName(),
                        dateFormat.format(museum.getEventDate()),
                        cityName,
                        museum.getLocation(),
                        museum.getPrice()
                    });
                }
            }
        } catch (SQLException e) {
            showError("Müzeler yüklenirken hata oluştu: " + e.getMessage());
        }
    }

    private void loadFestivals() {
        try {
            festivalTableModel.setRowCount(0);
            FestivalDB festivalDB = new FestivalDB();
            List<Festival> festivals;
            
            String priceSort = (String) priceFilterCombo.getSelectedItem();
            if ("Artan".equals(priceSort)) {
                festivals = festivalDB.getFestivalsSortedByPriceAsc();
            } else if ("Azalan".equals(priceSort)) {
                festivals = festivalDB.getFestivalsSortedByPriceDesc();
            } else {
                festivals = festivalDB.getAllFestivals();
            }

            String selectedCity = (String) cityFilterCombo.getSelectedItem();
            for (Festival festival : festivals) {
                String cityName = CityDB.getCityName(festival.getCityId());
                if ("Tümü".equals(selectedCity) || selectedCity.equals(cityName)) {
                    festivalTableModel.addRow(new Object[]{
                        festival.getFestivalType(),
                        dateFormat.format(festival.getEventDate()),
                        cityName,
                        festival.getLocation(),
                        festival.getPrice()
                    });
                }
            }
        } catch (SQLException e) {
            showError("Festivaller yüklenirken hata oluştu: " + e.getMessage());
        }
    }

    private void loadStages() {
        try {
            stageTableModel.setRowCount(0);
            StageDB stageDB = new StageDB();
            List<Stage> stages;
            
            String priceSort = (String) priceFilterCombo.getSelectedItem();
            if ("Artan".equals(priceSort)) {
                stages = stageDB.getStagesSortedByPriceAsc();
            } else if ("Azalan".equals(priceSort)) {
                stages = stageDB.getStagesSortedByPriceDesc();
            } else {
                stages = stageDB.getAllStages();
            }

            String selectedCity = (String) cityFilterCombo.getSelectedItem();
            for (Stage stage : stages) {
                String cityName = CityDB.getCityName(stage.getCityId());
                if ("Tümü".equals(selectedCity) || selectedCity.equals(cityName)) {
                    stageTableModel.addRow(new Object[]{
                        stage.getEventName(),
                        dateFormat.format(stage.getEventDate()),
                        cityName,
                        stage.getLocation(),
                        stage.getPrice()
                    });
                }
            }
        } catch (SQLException e) {
            showError("Sahne gösterileri yüklenirken hata oluştu: " + e.getMessage());
        }
    }

    private void loadAmusementParks() {
        try {
            amusementParkTableModel.setRowCount(0);
            AmusementParkDB parkDB = new AmusementParkDB();
            List<AmusementPark> parks;
            
            String priceSort = (String) priceFilterCombo.getSelectedItem();
            if ("Artan".equals(priceSort)) {
                parks = parkDB.getAmusementParksSortedByPriceAsc();
            } else if ("Azalan".equals(priceSort)) {
                parks = parkDB.getAmusementParksSortedByPriceDesc();
            } else {
                parks = parkDB.getAllAmusementParks();
            }

            String selectedCity = (String) cityFilterCombo.getSelectedItem();
            for (AmusementPark park : parks) {
                String cityName = CityDB.getCityName(park.getCityId());
                if ("Tümü".equals(selectedCity) || selectedCity.equals(cityName)) {
                    amusementParkTableModel.addRow(new Object[]{
                        park.getParkName(),
                        dateFormat.format(park.getEventDate()),
                        cityName,
                        park.getLocation(),
                        park.getPrice()
                    });
                }
            }
        } catch (SQLException e) {
            showError("Lunaparklar yüklenirken hata oluştu: " + e.getMessage());
        }
    }
    
    

    private void loadUserTickets() {
        try {
            ticketsTableModel.setRowCount(0);
            List<Ticket> tickets = ticketDB.getUserTickets(userId);
            
            for (Ticket ticket : tickets) {
                try {
                    String[] eventDetails = getEventDetails(ticket.getEventTypeId(), ticket.getEventId());
                    ticketsTableModel.addRow(new Object[]{
                        ticket.getTicketId(),
                        eventDetails[0], // isim
                        eventDetails[1], // Gün
                        eventDetails[2], // Şehir
                        eventDetails[3], // Lokasyon
                        ticket.getPrice()
                    });
                } catch (SQLException e) {
                    System.err.println("Bilet yükleme hatası" + ticket.getTicketId() + ": " + e.getMessage());
                    continue; // Bir bilet başarısız olsa bile diğer biletleri yüklemeye devam eder
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
            String eventName = (String) table.getValueAt(selectedRow, 0);
            BigDecimal price = (BigDecimal) table.getValueAt(selectedRow, 4);
            
            EventType eventType = getEventTypeFromCurrentTab();
            int eventId = getEventId(eventType, eventName);
            
            int confirm = JOptionPane.showConfirmDialog(this,
                eventName + " etkinliği için bilet satın almak istediğinize emin misiniz?\n" +
                "Fiyat: " + price + " TL",
                "Bilet Satın Al",
                JOptionPane.YES_NO_OPTION);
                
            if (confirm == JOptionPane.YES_OPTION) {
                if (ticketDB.purchaseTicket(userId, eventType.getId(), eventId, price)) {
                    showSuccess("Bilet başarıyla satın alındı!");
                    loadUserTickets();
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