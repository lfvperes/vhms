package com.vhms.utils;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

public final class ValidationUtils {
    private ValidationUtils() {}

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^[1-9]{2}9{1}[0-9]{8}$");

    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean isValidPhone(String phone) {
        // validation for a Brazilian mobile phone number
        return phone != null && PHONE_PATTERN.matcher(phone).matches();
    }

    /**
     * Checks if a date-time string is in the ISO format "yyyy-MM-dd'T'HH:mm" and is a valid calendar date and time.
     * @param dateTimeString The date-time string to check.
     * @return true if the format is valid and the date is real, false otherwise.
     */
    public static boolean isValidDateTimeFormat(String dateTimeString) {
        if (dateTimeString == null) {
            return false;
        }
        try {
            // This will parse strings like "2024-07-29T15:30"
            // It will throw an exception for invalid times like "2024-07-29T25:00"
            LocalDateTime.parse(dateTimeString);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

}
