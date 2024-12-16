package database;

import models.Museum;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MuseumDB {
    
    public List<Museum> getAllMuseums() throws SQLException {
        List<Museum> museums = new ArrayList<>();
        String query = "SELECT * FROM Museums";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                museums.add(new Museum(
                    rs.getInt("museum_id"),
                    rs.getInt("city_id"),
                    rs.getString("museum_name"),
                    rs.getDate("event_date"),
                    rs.getString("location"),
                    rs.getBigDecimal("price")
                ));
            }
        }
        return museums;
    }
    
    public List<Museum> getMuseumsSortedByPriceAsc() throws SQLException {
        List<Museum> museums = new ArrayList<>();
        String query = "SELECT * FROM Museums ORDER BY price ASC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                museums.add(new Museum(
                    rs.getInt("museum_id"),
                    rs.getInt("city_id"),
                    rs.getString("museum_name"),
                    rs.getDate("event_date"),
                    rs.getString("location"),
                    rs.getBigDecimal("price")
                ));
            }
        }
        return museums;
    }
    
    public List<Museum> getMuseumsSortedByPriceDesc() throws SQLException {
        List<Museum> museums = new ArrayList<>();
        String query = "SELECT * FROM Museums ORDER BY price DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                museums.add(new Museum(
                    rs.getInt("museum_id"),
                    rs.getInt("city_id"),
                    rs.getString("museum_name"),
                    rs.getDate("event_date"),
                    rs.getString("location"),
                    rs.getBigDecimal("price")
                ));
            }
        }
        return museums;
    }
    
    public boolean deleteMuseum(int museumId) throws SQLException {
        String query = "DELETE FROM Museums WHERE museum_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, museumId);
            return stmt.executeUpdate() > 0;
        }
    }
    
    public boolean addMuseum(String name, int cityId, Date eventDate, String location, BigDecimal price) throws SQLException {
        String query = "INSERT INTO Museums (city_id, museum_name, event_date, location, price) VALUES (?, ?, ?, ?, ?)";
        
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
    
    public Museum getMuseumById(int museumId) throws SQLException {
        String query = "SELECT * FROM Museums WHERE museum_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, museumId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Museum(
                        rs.getInt("museum_id"),
                        rs.getInt("city_id"),
                        rs.getString("museum_name"),
                        rs.getDate("event_date"),
                        rs.getString("location"),
                        rs.getBigDecimal("price")
                    );
                }
            }
        }
        return null;
    }
    
    public Museum getMuseumByName(String museumName) throws SQLException {
        String query = "SELECT * FROM Museums WHERE museum_name = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, museumName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Museum(
                        rs.getInt("museum_id"),
                        rs.getInt("city_id"),
                        rs.getString("museum_name"),
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