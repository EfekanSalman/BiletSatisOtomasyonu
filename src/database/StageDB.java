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
                    rs.getBigDecimal("price"),
                    rs.getInt("capacity")
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
                    rs.getBigDecimal("price"),
                    rs.getInt("capacity")
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
                    rs.getBigDecimal("price"),
                    rs.getInt("capacity")
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
    
    public boolean addStage(String name, int cityId, Date eventDate, 
            String location, BigDecimal price, int capacity) throws SQLException {
        String query = "INSERT INTO Stages (event_name, city_id, event_date, location, price, capacity, available_seats) " +
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
                        rs.getBigDecimal("price"),
                        rs.getInt("capacity")
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
                        rs.getBigDecimal("price"),
                        rs.getInt("capacity")
                    );
                }
            }
        }
        return null;
    }
    
    public boolean updateStage(int stageId, String name, int cityId, 
            Date eventDate, String location, BigDecimal price, int capacity) throws SQLException {
        String query = "UPDATE Stages SET event_name = ?, city_id = ?, " +
                       "event_date = ?, location = ?, price = ?, capacity = ? " +
                       "WHERE stage_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            TicketDB ticketDB = new TicketDB();
            int bookedSeats = ticketDB.getBookedSeatsCount(4, stageId);

            if (capacity < bookedSeats) {
                throw new SQLException("Yeni kapasite mevcut bilet sayısından az olamaz. Mevcut bilet sayısı: " + bookedSeats);
            }

            stmt.setString(1, name);
            stmt.setInt(2, cityId);
            stmt.setDate(3, eventDate);
            stmt.setString(4, location);
            stmt.setBigDecimal(5, price);
            stmt.setInt(6, capacity);
            stmt.setInt(7, stageId);

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    private Stage mapResultSetToStage(ResultSet rs) throws SQLException {
        return new Stage(
            rs.getInt("stage_id"),
            rs.getInt("city_id"),
            rs.getString("stage_type"),
            rs.getString("event_name"),
            rs.getDate("event_date"),
            rs.getString("location"),
            rs.getBigDecimal("price"),
            rs.getInt("capacity")
        );
    }
}
