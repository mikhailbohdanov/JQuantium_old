package com.quantium.web.util;

import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Created by FREEMAN on 08.12.14.
 */
public class ValidatorUtils {
    private static final Pattern PHONE                  = Pattern.compile("^(\\+?[0-9]{12}|[0-9]{11,12})$");
    private static final Pattern EMAIL                  = Pattern.compile("^[_A-Za-z0-9-\\\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

    public static boolean validateBy(Pattern pattern, String value) {
        if (pattern == null)
            return false;

        return pattern.matcher(value).matches();
    }
    public static boolean validateBy(String pattern, String value) {
        if (pattern == null)
            return false;

        return Pattern.compile(pattern).matcher(value).matches();
    }

    public static boolean isEmail(String email) {
        return validateBy(EMAIL, email);
    }

    public static boolean isPhone(String phone) {
        return validateBy(PHONE, phone);
    }



}
