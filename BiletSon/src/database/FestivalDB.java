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
                    rs.getBigDecimal("price")
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
                    rs.getBigDecimal("price")
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
                    rs.getBigDecimal("price")
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
    
    public boolean addFestival(String type, int cityId, Date eventDate, String location, BigDecimal price) throws SQLException {
        String query = "INSERT INTO Festivals (city_id, festival_type, event_date, location, price) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, cityId);
            stmt.setString(2, type);
            stmt.setDate(3, eventDate);
            stmt.setString(4, location);
            stmt.setBigDecimal(5, price);
            
            return stmt.executeUpdate() > 0;
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
                        rs.getBigDecimal("price")
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
                        rs.getBigDecimal("price")
                    );
                }
            }
        }
        return null;
    }
}