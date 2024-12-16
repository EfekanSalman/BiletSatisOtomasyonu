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
                    rs.getBigDecimal("price")
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
                    rs.getBigDecimal("price")
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
                    rs.getBigDecimal("price")
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
    
    public boolean addConcert(String name, int cityId, Date eventDate, String location, BigDecimal price) throws SQLException {
        String query = "INSERT INTO Concerts (city_id, concert_name, event_date, location, price) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, cityId);
            stmt.setString(2, name);
            stmt.setDate(3, eventDate);
            stmt.setString(4, location);
            stmt.setBigDecimal(5, price);
            
            return stmt.executeUpdate() > 0;
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
                        rs.getBigDecimal("price")
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
                        rs.getBigDecimal("price")
                    );
                }
            }
        }
        return null;
    }
}