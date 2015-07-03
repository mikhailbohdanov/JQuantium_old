package com.quantium.web.util.validators;

import java.util.regex.Pattern;

/**
 * Created by FREEMAN on 08.12.14.
 */
public class Profile {
    public static final Pattern USERNAME                = Pattern.compile("^[^0-9-.][_A-Za-z0-9-.]+$");


    public static final Pattern NAME                    = Pattern.compile("^([A-Za-zА-Яа-я]+\\s*)*$");


    public static String normalizePhone(String phone) {
        return phone;
    }
}
