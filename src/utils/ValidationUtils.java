package utils;

import java.util.regex.Pattern;

public class ValidationUtils {
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@(.+)$"
    );
    
    private static final Pattern CARD_NUMBER_PATTERN = Pattern.compile(
        "^[0-9]{16}$"
    );
    
    private static final Pattern CVV_PATTERN = Pattern.compile(
        "^[0-9]{3}$"
    );
    
    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }
    
    public static boolean isValidCardNumber(String cardNumber) {
        return cardNumber != null && CARD_NUMBER_PATTERN.matcher(cardNumber).matches();
    }
    
    public static boolean isValidCVV(String cvv) {
        return cvv != null && CVV_PATTERN.matcher(cvv).matches();
    }
}