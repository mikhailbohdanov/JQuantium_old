package com.quantium.web.errors.controllers;

/**
 * Author FREEMAN
 * Created 13.11.14
 */
public class ProfileErrors {
    private static int i                                                = 0;

    public static final String _NAME                                    = "profile";


    // - Main errors


    // - First name errors
    public static final long FIRST_NAME_EMPTY                           = 1 << i++;
    public static final long FIRST_NAME_INCORRECT                       = 1 << i++;

    // - Last name errors
    public static final long LAST_NAME_EMPTY                            = 1 << i++;
    public static final long LAST_NAME_INCORRECT                        = 1 << i++;

    // - Profile errors
    public static final long PROFILE_NOT_FOUND                          = 1 << i++;


}
