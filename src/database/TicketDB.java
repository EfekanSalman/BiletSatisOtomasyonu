package database;

import models.Ticket;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

public class TicketDB {
    public List<Ticket> getUserTickets(int userId) throws SQLException {
        List<Ticket> tickets = new ArrayList<>();
        String query = "SELECT * FROM Tickets WHERE user_id = ? ORDER BY purchase_date DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    tickets.add(new Ticket(
                        rs.getInt("ticket_id"),
                        rs.getInt("user_id"),
                        rs.getInt("event_type_id"),
                        rs.getInt("event_id"),
                        rs.getTimestamp("purchase_date"),
                        rs.getBigDecimal("price")
                    ));
                }
            }
        }
        return tickets;
    }

    public boolean purchaseTicket(int userId, int eventTypeId, int eventId, BigDecimal price) throws SQLException {
        String query = "INSERT INTO Tickets (user_id, event_type_id, event_id, purchase_date, price) VALUES (?, ?, ?, NOW(), ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, userId);
            stmt.setInt(2, eventTypeId);
            stmt.setInt(3, eventId);
            stmt.setBigDecimal(4, price);
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Bilet oluşturulamadı, hiçbir satır etkilenmedi.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    System.out.println("Bilet başarıyla oluşturuldu. Bilet ID: " + generatedKeys.getLong(1));
                    return true;
                } else {
                    throw new SQLException("Bilet oluşturuldu ancak ID alınamadı.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Bilet satın alma hatası: " + e.getMessage());
            throw e;
        }
    }

    public boolean cancelTicket(int ticketId) throws SQLException {
        String query = "DELETE FROM Tickets WHERE ticket_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, ticketId);
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Bilet bulunamadı veya silinemedi.");
            }
            
            return true;
        } catch (SQLException e) {
            System.err.println("Bilet iptal hatası: " + e.getMessage());
            throw e;
        }
    }

    public Ticket getTicketById(int ticketId) throws SQLException {
        String query = "SELECT * FROM Tickets WHERE ticket_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, ticketId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Ticket(
                        rs.getInt("ticket_id"),
                        rs.getInt("user_id"),
                        rs.getInt("event_type_id"),
                        rs.getInt("event_id"),
                        rs.getTimestamp("purchase_date"),
                        rs.getBigDecimal("price")
                    );
                }
            }
        }
        return null;
    }
    
    public int getBookedSeatsCount(int eventTypeId, int eventId) throws SQLException {
        String query = "SELECT COUNT(*) as booked_seats FROM Tickets " +
                      "WHERE event_type_id = ? AND event_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, eventTypeId);
            stmt.setInt(2, eventId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("booked_seats");
                }
            }
        }
        return 0;
    }
}