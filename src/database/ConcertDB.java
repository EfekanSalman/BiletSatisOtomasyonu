package database;

import models.Concert;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConcertDB {
    
    public List<Concert> getAllConcerts() throws SQLException {
        List<Concert> concerts = new ArrayList<>();
        String query = "SELECT * FROM Concerts";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                concerts.add(new Concert(
                    rs.getInt("concert_id"),
                    rs.getInt("city_id"),
                    rs.getString("concert_name"),
                    rs.getDate("event_date"),
                    rs.getString("location"),
                    rs.getBigDecimal("price"),
                    rs.getInt("capacity")
                ));
            }
        }
        return concerts;
    }
    
    public List<Concert> getConcertsSortedByPriceAsc() throws SQLException {
        List<Concert> concerts = new ArrayList<>();
        String query = "SELECT * FROM Concerts ORDER BY price ASC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                concerts.add(new Concert(
                    rs.getInt("concert_id"),
                    rs.getInt("city_id"),
                    rs.getString("concert_name"),
                    rs.getDate("event_date"),
                    rs.getString("location"),
                    rs.getBigDecimal("price"),
                    rs.getInt("capacity")
                ));
            }
        }
        return concerts;
    }
    
    public List<Concert> getConcertsSortedByPriceDesc() throws SQLException {
        List<Concert> concerts = new ArrayList<>();
        String query = "SELECT * FROM Concerts ORDER BY price DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                concerts.add(new Concert(
                    rs.getInt("concert_id"),
                    rs.getInt("city_id"),
                    rs.getString("concert_name"),
                    rs.getDate("event_date"),
                    rs.getString("location"),
                    rs.getBigDecimal("price"),
                    rs.getInt("capacity")
                ));
            }
        }
        return concerts;
    }
    
    public boolean deleteConcert(int concertId) throws SQLException {
        String query = "DELETE FROM Concerts WHERE concert_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, concertId);
            return stmt.executeUpdate() > 0;
        }
    }
    
    public boolean addConcert(String name, int cityId, Date eventDate, 
            String location, BigDecimal price, int capacity) throws SQLException {
        String query = "INSERT INTO Concerts (concert_name, city_id, event_date, location, price, capacity, available_seats) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, name);
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
    
    public Concert getConcertById(int concertId) throws SQLException {
        String query = "SELECT * FROM Concerts WHERE concert_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, concertId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Concert(
                        rs.getInt("concert_id"),
                        rs.getInt("city_id"),
                        rs.getString("concert_name"),
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
    
    public Concert getConcertByName(String concertName) throws SQLException {
        String query = "SELECT * FROM Concerts WHERE concert_name = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, concertName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Concert(
                        rs.getInt("concert_id"),
                        rs.getInt("city_id"),
                        rs.getString("concert_name"),
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
    
    public boolean updateConcert(int concertId, String name, int cityId, 
            Date eventDate, String location, BigDecimal price, int capacity) throws SQLException {
        String query = "UPDATE Concerts SET concert_name = ?, city_id = ?, " +
                       "event_date = ?, location = ?, price = ?, capacity = ? " +
                       "WHERE concert_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            TicketDB ticketDB = new TicketDB();
            int bookedSeats = ticketDB.getBookedSeatsCount(2, concertId);

            if (capacity < bookedSeats) {
                throw new SQLException("Yeni kapasite mevcut bilet sayısından az olamaz. Mevcut bilet sayısı: " + bookedSeats);
            }

            stmt.setString(1, name);
            stmt.setInt(2, cityId);
            stmt.setDate(3, eventDate);
            stmt.setString(4, location);
            stmt.setBigDecimal(5, price);
            stmt.setInt(6, capacity);
            stmt.setInt(7, concertId);

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    private Concert mapResultSetToConcert(ResultSet rs) throws SQLException {
        return new Concert(
            rs.getInt("concert_id"),
            rs.getInt("city_id"),
            rs.getString("concert_name"),
            rs.getDate("event_date"),
            rs.getString("location"),
            rs.getBigDecimal("price"),
            rs.getInt("capacity")
        );
    }
}
