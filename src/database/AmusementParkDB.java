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
                    rs.getBigDecimal("price"),
                    rs.getInt("capacity")
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
                    rs.getBigDecimal("price"),
                    rs.getInt("capacity")
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
                    rs.getBigDecimal("price"),
                    rs.getInt("capacity")
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
    
    public boolean addAmusementPark(String name, int cityId, Date eventDate, 
            String location, BigDecimal price, int capacity) throws SQLException {
        String query = "INSERT INTO AmusementParks (park_name, city_id, event_date, location, price, capacity, available_seats) " +
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
                        rs.getBigDecimal("price"),
                        rs.getInt("capacity")
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
                        rs.getBigDecimal("price"),
                        rs.getInt("capacity")
                    );
                }
            }
        }
        return null;
    }
    
    public boolean updateAmusementPark(int parkId, String name, int cityId, 
            Date eventDate, String location, BigDecimal price, int capacity) throws SQLException {
        String query = "UPDATE AmusementParks SET park_name = ?, city_id = ?, " +
                       "event_date = ?, location = ?, price = ?, capacity = ? " +
                       "WHERE park_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            TicketDB ticketDB = new TicketDB();
            int bookedSeats = ticketDB.getBookedSeatsCount(7, parkId);

            if (capacity < bookedSeats) {
                throw new SQLException("Yeni kapasite mevcut bilet sayısından az olamaz. Mevcut bilet sayısı: " + bookedSeats);
            }

            stmt.setString(1, name);
            stmt.setInt(2, cityId);
            stmt.setDate(3, eventDate);
            stmt.setString(4, location);
            stmt.setBigDecimal(5, price);
            stmt.setInt(6, capacity);
            stmt.setInt(7, parkId);

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    private AmusementPark mapResultSetToPark(ResultSet rs) throws SQLException {
        return new AmusementPark(
            rs.getInt("park_id"),
            rs.getInt("city_id"),
            rs.getString("park_name"),
            rs.getDate("event_date"),
            rs.getString("location"),
            rs.getBigDecimal("price"),
            rs.getInt("capacity")
        );
    }
}
