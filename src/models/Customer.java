package models;

public class Customer extends User {
    private String cardNumber;
    private String cvv;

    public Customer(int userId, String firstName, String lastName, String username, 
                   String password, int securityQuestionId, String securityAnswer, 
                   String email, String cardNumber, String cvv) {
        super(userId, firstName, lastName, username, password, securityQuestionId, 
              securityAnswer, email);
        this.cardNumber = cardNumber;
        this.cvv = cvv;
    }

    @Override
    public String getRole() {
        return "Customer";
    }

    public String getCardNumber() { return cardNumber; }
    public void setCardNumber(String cardNumber) { this.cardNumber = cardNumber; }
    
    public String getCvv() { return cvv; }
    public void setCvv(String cvv) { this.cvv = cvv; }
}