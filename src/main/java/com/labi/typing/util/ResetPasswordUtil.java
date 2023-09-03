package com.labi.typing.util;

import java.security.SecureRandom;

public class ResetPasswordUtil {

    private static final String LETTERS_LOWER_CASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String LETTERS_UPPER_CASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMBERS = "0123456789";
    private static final String SPECIAL_CHARACTERS = "!@#$%";

    private static final String ALL_CHARACTERS =
            LETTERS_LOWER_CASE + LETTERS_UPPER_CASE + NUMBERS + SPECIAL_CHARACTERS;

    private static final SecureRandom random = new SecureRandom();

    public static String generateRandomPassword(int length) {
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(ALL_CHARACTERS.length());
            char randomChar = ALL_CHARACTERS.charAt(randomIndex);
            password.append(randomChar);
        }

        return password.toString();
    }
}
