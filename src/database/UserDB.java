package database;

import models.User;
import models.Customer;
import models.Admin;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDB {
    
    public User login(String usernameOrEmail, String password) throws SQLException {
        String query = "SELECT * FROM Users WHERE (username = ? OR email = ?) AND password = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, usernameOrEmail);
            stmt.setString(2, usernameOrEmail);
            stmt.setString(3, password);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    if (rs.getString("role").equals("Admin")) {
                        return new Admin(
                            rs.getInt("user_id"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getInt("security_question_id"),
                            rs.getString("security_answer"),
                            rs.getString("email"),
                            rs.getString("role")
                        );
                    } else {
                        return new Customer(
                            rs.getInt("user_id"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getInt("security_question_id"),
                            rs.getString("security_answer"),
                            rs.getString("email"),
                            rs.getString("card_number"),
                            rs.getString("cvv")
                        );
                    }
                }
            }
        }
        return null;
    }
    
    public boolean register(Customer customer) throws SQLException {
        String query = "INSERT INTO Users (first_name, last_name, username, password, " +
                      "security_question_id, security_answer, email, card_number, cvv, role) " +
                      "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, 'Customer')";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, customer.getFirstName());
            stmt.setString(2, customer.getLastName());
            stmt.setString(3, customer.getUsername());
            stmt.setString(4, customer.getPassword());
            stmt.setInt(5, customer.getSecurityQuestionId());
            stmt.setString(6, customer.getSecurityAnswer());
            stmt.setString(7, customer.getEmail());
            stmt.setString(8, customer.getCardNumber());
            stmt.setString(9, customer.getCvv());
            
            return stmt.executeUpdate() > 0;
        }
    }
    
    public boolean resetPassword(String usernameOrEmail, String securityAnswer, String newPassword) throws SQLException {
        String query = "UPDATE Users SET password = ? WHERE (username = ? OR email = ?) AND security_answer = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, newPassword);
            stmt.setString(2, usernameOrEmail);
            stmt.setString(3, usernameOrEmail);
            stmt.setString(4, securityAnswer);
            
            return stmt.executeUpdate() > 0;
        }
    }
    
    public boolean isUsernameExists(String username) throws SQLException {
        String query = "SELECT COUNT(*) FROM Users WHERE username = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
    
    public boolean isEmailExists(String email) throws SQLException {
        String query = "SELECT COUNT(*) FROM Users WHERE email = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
    
    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM Users";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                User user;
                if (rs.getString("role").equals("Admin")) {
                    user = new Admin(
                        rs.getInt("user_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getInt("security_question_id"),
                        rs.getString("security_answer"),
                        rs.getString("email"),
                        rs.getString("role")
                    );
                } else {
                    user = new Customer(
                        rs.getInt("user_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getInt("security_question_id"),
                        rs.getString("security_answer"),
                        rs.getString("email"),
                        rs.getString("card_number"),
                        rs.getString("cvv")
                    );
                }
                users.add(user);
            }
        }
        return users;
    }

    public boolean updateUserRole(int userId, String newRole) throws SQLException {
        String query = "UPDATE Users SET role = ? WHERE user_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, newRole);
            stmt.setInt(2, userId);
            
            return stmt.executeUpdate() > 0;
        }
    }
    
    public boolean deleteUser(int userId) throws SQLException {
        Connection conn = null;
        PreparedStatement deleteTicketsStmt = null;
        PreparedStatement deleteUserStmt = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);
            
            // Önce kullanıcıyla ilişkili tüm biletleri silinir
            String deleteTicketsQuery = "DELETE FROM Tickets WHERE user_id = ?";
            deleteTicketsStmt = conn.prepareStatement(deleteTicketsQuery);
            deleteTicketsStmt.setInt(1, userId);
            deleteTicketsStmt.executeUpdate();
            
            // Sonra kullanıcıyı silinir
            String deleteUserQuery = "DELETE FROM Users WHERE user_id = ?";
            deleteUserStmt = conn.prepareStatement(deleteUserQuery);
            deleteUserStmt.setInt(1, userId);
            
            int affectedRows = deleteUserStmt.executeUpdate();
            
            // Her şey başarılı olursa işlemi gerçekleşir
            conn.commit();
            
            return affectedRows > 0;
            
        } catch (SQLException e) {
            // Bir hata varsa, işlemi geri alır
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    throw new SQLException("İşlemi geri alma hatası: " + ex.getMessage());
                }
            }
            throw new SQLException("Kullanıcı silinirken hata oluştu: " + e.getMessage());
            
        } finally {
            if (deleteTicketsStmt != null) {
                deleteTicketsStmt.close();
            }
            if (deleteUserStmt != null) {
                deleteUserStmt.close();
            }
            if (conn != null) {
                conn.setAutoCommit(true); 
                conn.close();
            }
        }
    }
    
    public boolean updateUserProfile(Customer customer) throws SQLException {
        String query = "UPDATE Users SET first_name = ?, last_name = ?, username = ?, " +
                      "email = ?, card_number = ?, cvv = ? WHERE user_id = ?";
                      
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, customer.getFirstName());
            stmt.setString(2, customer.getLastName());
            stmt.setString(3, customer.getUsername());
            stmt.setString(4, customer.getEmail());
            stmt.setString(5, customer.getCardNumber());
            stmt.setString(6, customer.getCvv());
            stmt.setInt(7, customer.getUserId());
            
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }
    
    public User getUserById(int userId) throws SQLException {
        String query = "SELECT * FROM Users WHERE user_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, userId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String role = rs.getString("role");
                    if ("Customer".equals(role)) {
                        return new Customer(
                            rs.getInt("user_id"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getInt("security_question_id"),
                            rs.getString("security_answer"),
                            rs.getString("email"),
                            rs.getString("card_number"),
                            rs.getString("cvv")
                        );
                    } else if ("Admin".equals(role)) {
                        return new Admin(
                            rs.getInt("user_id"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getInt("security_question_id"),
                            rs.getString("security_answer"),
                            rs.getString("email"),
                            rs.getString("admin_level")
                        );
                    }
                }
            }
        }
        return null;
    }
    
    public boolean changePassword(int userId, String currentPassword, String newPassword) throws SQLException {
        String query = "SELECT password FROM Users WHERE user_id = ? AND password = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, userId);
            stmt.setString(2, currentPassword);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    return false; 
                }
            }
        }

        query = "UPDATE Users SET password = ? WHERE user_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, newPassword);
            stmt.setInt(2, userId);
            
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }
    
}