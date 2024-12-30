package database;

import models.Festival;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FestivalDB {
    
    public List<Festival> getAllFestivals() throws SQLException {
        List<Festival> festivals = new ArrayList<>();
        String query = "SELECT * FROM Festivals";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                festivals.add(new Festival(
                    rs.getInt("festival_id"),
                    rs.getInt("city_id"),
                    rs.getString("festival_type"),
                    rs.getDate("event_date"),
                    rs.getString("location"),
                    rs.getBigDecimal("price"),
                    rs.getInt("capacity")
                ));
            }
        }
        return festivals;
    }
    
    public List<Festival> getFestivalsSortedByPriceAsc() throws SQLException {
        List<Festival> festivals = new ArrayList<>();
        String query = "SELECT * FROM Festivals ORDER BY price ASC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                festivals.add(new Festival(
                    rs.getInt("festival_id"),
                    rs.getInt("city_id"),
                    rs.getString("festival_type"),
                    rs.getDate("event_date"),
                    rs.getString("location"),
                    rs.getBigDecimal("price"),
                    rs.getInt("capacity")
                ));
            }
        }
        return festivals;
    }
    
    public List<Festival> getFestivalsSortedByPriceDesc() throws SQLException {
        List<Festival> festivals = new ArrayList<>();
        String query = "SELECT * FROM Festivals ORDER BY price DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                festivals.add(new Festival(
                    rs.getInt("festival_id"),
                    rs.getInt("city_id"),
                    rs.getString("festival_type"),
                    rs.getDate("event_date"),
                    rs.getString("location"),
                    rs.getBigDecimal("price"),
                    rs.getInt("capacity")
                ));
            }
        }
        return festivals;
    }
    
    public boolean deleteFestival(int festivalId) throws SQLException {
        String query = "DELETE FROM Festivals WHERE festival_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, festivalId);
            return stmt.executeUpdate() > 0;
        }
    }
    
    public boolean addFestival(String festivalType, int cityId, Date eventDate, 
            String location, BigDecimal price, int capacity) throws SQLException {
        String query = "INSERT INTO Festivals (festival_type, city_id, event_date, location, price, capacity, available_seats) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, festivalType);
            stmt.setInt(2, cityId);
            stmt.setDate(3, eventDate);
            stmt.setString(4, location);
            stmt.setBigDecimal(5, price);
            stmt.setInt(6, capacity);
            stmt.setInt(7, capacity); 

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    public Festival getFestivalById(int festivalId) throws SQLException {
        String query = "SELECT * FROM Festivals WHERE festival_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, festivalId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Festival(
                        rs.getInt("festival_id"),
                        rs.getInt("city_id"),
                        rs.getString("festival_type"),
                        rs.getDate("event_date"),
                        rs.getString("location"),
                        rs.getBigDecimal("price"),
                        rs.getInt("capacity")
                    );
                }
            }
        }
        return null;
    }
    
    public Festival getFestivalByName(String festivalType) throws SQLException {
        String query = "SELECT * FROM Festivals WHERE festival_type = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, festivalType);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Festival(
                        rs.getInt("festival_id"),
                        rs.getInt("city_id"),
                        rs.getString("festival_type"),
                        rs.getDate("event_date"),
                        rs.getString("location"),
                        rs.getBigDecimal("price"),
                        rs.getInt("capacity")
                    );
                }
            }
        }
        return null;
    }
    
    public boolean updateFestival(int festivalId, String festivalType, int cityId, 
            Date eventDate, String location, BigDecimal price, int capacity) throws SQLException {
        String query = "UPDATE Festivals SET festival_type = ?, city_id = ?, " +
                       "event_date = ?, location = ?, price = ?, capacity = ? " +
                       "WHERE festival_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            TicketDB ticketDB = new TicketDB();
            int bookedSeats = ticketDB.getBookedSeatsCount(3, festivalId);

            if (capacity < bookedSeats) {
                throw new SQLException("Yeni kapasite mevcut bilet sayısından az olamaz. Mevcut bilet sayısı: " + bookedSeats);
            }

            stmt.setString(1, festivalType);
            stmt.setInt(2, cityId);
            stmt.setDate(3, eventDate);
            stmt.setString(4, location);
            stmt.setBigDecimal(5, price);
            stmt.setInt(6, capacity);
            stmt.setInt(7, festivalId);

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    private Festival mapResultSetToFestival(ResultSet rs) throws SQLException {
        return new Festival(
            rs.getInt("festival_id"),
            rs.getInt("city_id"),
            rs.getString("festival_type"),
            rs.getDate("event_date"),
            rs.getString("location"),
            rs.getBigDecimal("price"),
            rs.getInt("capacity")
        );
    }
}
