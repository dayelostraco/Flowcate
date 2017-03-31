package com.notiflowcate.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private static final Pattern emailPattern = Pattern.compile(EMAIL_PATTERN);

    /**
     * Validates the passed in Email address as a {@link String} against the EMAIL_PATTERN Regex.
     *
     * @param email {@link String}
     * @return Boolean true if valid, false if invalid
     */
    public static boolean validate(final String email) {

        Matcher matcher = emailPattern.matcher(email);
        return matcher.matches();
    }
}
