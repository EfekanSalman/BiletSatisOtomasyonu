package database;

import models.Stage;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StageDB {
    
    public List<Stage> getAllStages() throws SQLException {
        List<Stage> stages = new ArrayList<>();
        String query = "SELECT * FROM Stages";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                stages.add(new Stage(
                    rs.getInt("stage_id"),
                    rs.getInt("city_id"),
                    rs.getString("stage_type"),
                    rs.getString("event_name"),
                    rs.getDate("event_date"),
                    rs.getString("location"),
                    rs.getBigDecimal("price")
                ));
            }
        }
        return stages;
    }
    
    public List<Stage> getStagesSortedByPriceAsc() throws SQLException {
        List<Stage> stages = new ArrayList<>();
        String query = "SELECT * FROM Stages ORDER BY price ASC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                stages.add(new Stage(
                    rs.getInt("stage_id"),
                    rs.getInt("city_id"),
                    rs.getString("stage_type"),
                    rs.getString("event_name"),
                    rs.getDate("event_date"),
                    rs.getString("location"),
                    rs.getBigDecimal("price")
                ));
            }
        }
        return stages;
    }
    
    public List<Stage> getStagesSortedByPriceDesc() throws SQLException {
        List<Stage> stages = new ArrayList<>();
        String query = "SELECT * FROM Stages ORDER BY price DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                stages.add(new Stage(
                    rs.getInt("stage_id"),
                    rs.getInt("city_id"),
                    rs.getString("stage_type"),
                    rs.getString("event_name"),
                    rs.getDate("event_date"),
                    rs.getString("location"),
                    rs.getBigDecimal("price")
                ));
            }
        }
        return stages;
    }
    
    public boolean deleteStage(int stageId) throws SQLException {
        String query = "DELETE FROM Stages WHERE stage_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, stageId);
            return stmt.executeUpdate() > 0;
        }
    }
    
    public boolean addStage(String name, int cityId, Date eventDate, String location, BigDecimal price) throws SQLException {
        String query = "INSERT INTO Stages (city_id, stage_type, event_name, event_date, location, price) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, cityId);
            stmt.setString(2, "Regular"); 
            stmt.setString(3, name);
            stmt.setDate(4, eventDate);
            stmt.setString(5, location);
            stmt.setBigDecimal(6, price);
            
            return stmt.executeUpdate() > 0;
        }
    }
    
    public Stage getStageById(int stageId) throws SQLException {
        String query = "SELECT * FROM Stages WHERE stage_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, stageId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Stage(
                        rs.getInt("stage_id"),
                        rs.getInt("city_id"),
                        rs.getString("stage_type"),
                        rs.getString("event_name"),
                        rs.getDate("event_date"),
                        rs.getString("location"),
                        rs.getBigDecimal("price")
                    );
                }
            }
        }
        return null;
    }
    
    public Stage getStageByName(String eventName) throws SQLException {
        String query = "SELECT * FROM Stages WHERE event_name = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, eventName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Stage(
                        rs.getInt("stage_id"),
                        rs.getInt("city_id"),
                        rs.getString("stage_type"),
                        rs.getString("event_name"),
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