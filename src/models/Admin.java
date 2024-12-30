package models;

public class Admin extends User {
    private String adminLevel;

    public Admin(int userId, String firstName, String lastName, String username, 
                String password, int securityQuestionId, String securityAnswer, 
                String email, String adminLevel) {
        super(userId, firstName, lastName, username, password, securityQuestionId, 
              securityAnswer, email);
        this.adminLevel = adminLevel;
    }

    @Override
    public String getRole() {
        return "Admin";
    }

    public String getAdminLevel() { return adminLevel; }
    public void setAdminLevel(String adminLevel) { this.adminLevel = adminLevel; }
    
    public boolean canManageUsers() {
        return "SUPER".equals(adminLevel) || "FULL".equals(adminLevel);
    }
    
    public boolean canManageEvents() {
        return true; 
    }
}