package models;

public abstract class User {
    private int userId;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private int securityQuestionId;
    private String securityAnswer;
    private String email;

    public User(int userId, String firstName, String lastName, String username, 
                String password, int securityQuestionId, String securityAnswer, 
                String email) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.securityQuestionId = securityQuestionId;
        this.securityAnswer = securityAnswer;
        this.email = email;
    }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public int getSecurityQuestionId() { return securityQuestionId; }
    public void setSecurityQuestionId(int securityQuestionId) { this.securityQuestionId = securityQuestionId; }
    
    public String getSecurityAnswer() { return securityAnswer; }
    public void setSecurityAnswer(String securityAnswer) { this.securityAnswer = securityAnswer; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public abstract String getRole();
}