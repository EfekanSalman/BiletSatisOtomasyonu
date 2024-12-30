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
                    rs.getBigDecimal("price"),
                    rs.getInt("capacity")
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
                    rs.getBigDecimal("price"),
                    rs.getInt("capacity")
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
                    rs.getBigDecimal("price"),
                    rs.getInt("capacity")
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
    
    public boolean addMuseum(String name, int cityId, Date eventDate, 
            String location, BigDecimal price, int capacity) throws SQLException {
        String query = "INSERT INTO Museums (museum_name, city_id, event_date, location, price, capacity, available_seats) " +
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
                        rs.getBigDecimal("price"),
                        rs.getInt("capacity")
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
                        rs.getBigDecimal("price"),
                        rs.getInt("capacity")
                    );
                }
            }
        }
        return null;
    }
    
    public boolean updateMuseum(int museumId, String name, int cityId, 
            Date eventDate, String location, BigDecimal price, int capacity) throws SQLException {
        String query = "UPDATE Museums SET museum_name = ?, city_id = ?, " +
                       "event_date = ?, location = ?, price = ?, capacity = ? " +
                       "WHERE museum_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            TicketDB ticketDB = new TicketDB();
            int bookedSeats = ticketDB.getBookedSeatsCount(6, museumId);

            if (capacity < bookedSeats) {
                throw new SQLException("Yeni kapasite mevcut bilet sayısından az olamaz. Mevcut bilet sayısı: " + bookedSeats);
            }

            stmt.setString(1, name);
            stmt.setInt(2, cityId);
            stmt.setDate(3, eventDate);
            stmt.setString(4, location);
            stmt.setBigDecimal(5, price);
            stmt.setInt(6, capacity);
            stmt.setInt(7, museumId);

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    private Museum mapResultSetToMuseum(ResultSet rs) throws SQLException {
        return new Museum(
            rs.getInt("museum_id"),
            rs.getInt("city_id"),
            rs.getString("museum_name"),
            rs.getDate("event_date"),
            rs.getString("location"),
            rs.getBigDecimal("price"),
            rs.getInt("capacity")
        );
    }
}
