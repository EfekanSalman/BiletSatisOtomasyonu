package database;

import models.AmusementPark;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AmusementParkDB {
    
    public List<AmusementPark> getAllAmusementParks() throws SQLException {
        List<AmusementPark> parks = new ArrayList<>();
        String query = "SELECT * FROM AmusementParks";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                parks.add(new AmusementPark(
                    rs.getInt("park_id"),
                    rs.getInt("city_id"),
                    rs.getString("park_name"),
                    rs.getDate("event_date"),
                    rs.getString("location"),
                    rs.getBigDecimal("price")
                ));
            }
        }
        return parks;
    }
    
    public List<AmusementPark> getAmusementParksSortedByPriceAsc() throws SQLException {
        List<AmusementPark> parks = new ArrayList<>();
        String query = "SELECT * FROM AmusementParks ORDER BY price ASC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                parks.add(new AmusementPark(
                    rs.getInt("park_id"),
                    rs.getInt("city_id"),
                    rs.getString("park_name"),
                    rs.getDate("event_date"),
                    rs.getString("location"),
                    rs.getBigDecimal("price")
                ));
            }
        }
        return parks;
    }
    
    public List<AmusementPark> getAmusementParksSortedByPriceDesc() throws SQLException {
        List<AmusementPark> parks = new ArrayList<>();
        String query = "SELECT * FROM AmusementParks ORDER BY price DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                parks.add(new AmusementPark(
                    rs.getInt("park_id"),
                    rs.getInt("city_id"),
                    rs.getString("park_name"),
                    rs.getDate("event_date"),
                    rs.getString("location"),
                    rs.getBigDecimal("price")
                ));
            }
        }
        return parks;
    }
    
    public boolean deleteAmusementPark(int parkId) throws SQLException {
        String query = "DELETE FROM AmusementParks WHERE park_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, parkId);
            return stmt.executeUpdate() > 0;
        }
    }
    
    public boolean addAmusementPark(String name, int cityId, Date eventDate, String location, BigDecimal price) throws SQLException {
        String query = "INSERT INTO AmusementParks (city_id, park_name, event_date, location, price) VALUES (?, ?, ?, ?, ?)";
        
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
    
    public AmusementPark getAmusementParkById(int parkId) throws SQLException {
        String query = "SELECT * FROM AmusementParks WHERE park_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, parkId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new AmusementPark(
                        rs.getInt("park_id"),
                        rs.getInt("city_id"),
                        rs.getString("park_name"),
                        rs.getDate("event_date"),
                        rs.getString("location"),
                        rs.getBigDecimal("price")
                    );
                }
            }
        }
        return null;
    }
    
    public AmusementPark getAmusementParkByName(String parkName) throws SQLException {
        String query = "SELECT * FROM AmusementParks WHERE park_name = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, parkName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new AmusementPark(
                        rs.getInt("park_id"),
                        rs.getInt("city_id"),
                        rs.getString("park_name"),
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