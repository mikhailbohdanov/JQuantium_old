package com.quantium.web.errors.controllers;

/**
 * Author FREEMAN
 * Created 12.11.14
 */
public abstract class SecurityErrors {
    private static int i                                                = 0;

    public static final String _NAME                                    = "security";


    // - Main errors
    public static final long SECURITY_UNKNOWN_PROBLEM                   = 1 << i++;

    // - Authenticate errors
    public static final long SECURITY_AUTHENTICATED                     = 1 << i++;
    public static final long SECURITY_NOT_AUTHENTICATED                 = 1 << i++;
    public static final long SECURITY_AUTHENTICATE_MISS_PARAMS          = 1 << i++;
    public static final long SECURITY_AUTHENTICATE_INVALID              = 1 << i++;
    public static final long SECURITY_OUT_TOKEN_MISS                    = 1 << i++;
    public static final long SECURITY_OUT_TOKEN_INVALID                 = 1 << i++;

    // - Login
    public static final long LOGIN_EMPTY                                = 1 << i++;

    // - UserName errors
    public static final long USERNAME_EMPTY                             = 1 << i++;
    public static final long USERNAME_INCORRECT                         = 1 << i++;
    public static final long USERNAME_MIN_LENGTH                        = 1 << i++;
    public static final long USERNAME_MAX_LENGTH                        = 1 << i++;
    public static final long USERNAME_EXISTS                            = 1 << i++;
    public static final long USERNAME_NOT_EXISTS                        = 1 << i++;

    // - Email errors
    public static final long EMAIL_EMPTY                                = 1 << i++;
    public static final long EMAIL_INCORRECT                            = 1 << i++;
    public static final long EMAIL_EXISTS                               = 1 << i++;
    public static final long EMAIL_NOT_EXISTS                           = 1 << i++;
    public static final long EMAIL_NOT_CONFIRMED                        = 1 << i++;
    public static final long EMAIL_CODE_INCORRECT                       = 1 << i++;

    // - Phone errors
    public static final long PHONE_EMPTY                                = 1 << i++;
    public static final long PHONE_INCORRECT                            = 1 << i++;
    public static final long PHONE_EXISTS                               = 1 << i++;
    public static final long PHONE_NOT_EXISTS                           = 1 << i++;
    public static final long PHONE_NOT_CONFIRMED                        = 1 << i++;
    public static final long PHONE_CODE_INCORRECT                       = 1 << i++;

    // - Password errors
    public static final long PASSWORD_EMPTY                             = 1 << i++;
    public static final long PASSWORD_SMALL                             = 1 << i++;
    public static final long PASSWORD_BIG                               = 1 << i++;
    public static final long PASSWORD_EASY                              = 1 << i++;
    public static final long PASSWORD_NOT_CONFIRMED                     = 1 << i++;


}
