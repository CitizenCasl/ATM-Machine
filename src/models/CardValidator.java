package models;

import java.util.regex.Pattern;

public class CardValidator {
    private static final Pattern CARD_NUMBER_PATTERN = Pattern.compile("\\d{4}-\\d{4}-\\d{4}-\\d{4}");

    public static boolean isValidCardNumber(String cardNumber) {
        return CARD_NUMBER_PATTERN.matcher(cardNumber).matches();
    }

    public static boolean isValidPinCode(String pinCode) {
        return pinCode.length() == 4 && pinCode.chars().allMatch(Character::isDigit);
    }
}
