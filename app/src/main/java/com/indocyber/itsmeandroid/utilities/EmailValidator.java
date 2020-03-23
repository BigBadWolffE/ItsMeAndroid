package com.indocyber.itsmeandroid.utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {
    private static final String EMAIL_PATTERN = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,500}" +
            "\\@" +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,500}" +
            "(" +
            "\\." +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,500}" +
            ")+";

    /**
     * method for validate email address
     *
     * @param email String
     * @return boolean, true = valid
     */
    public static boolean validate(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();

    }
}
