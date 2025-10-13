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

}
