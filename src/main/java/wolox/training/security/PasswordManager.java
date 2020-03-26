package wolox.training.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordManager {
    public static String encode(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

    public static boolean validate(String value, String encodedPassword) {
        return new BCryptPasswordEncoder().matches(value, encodedPassword);
    }
}
