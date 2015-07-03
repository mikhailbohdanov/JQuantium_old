package com.quantium.web.util;

import com.quantium.web.bean.core.Response;
import com.quantium.web.bean.core.Route;
import com.quantium.web.bean.security.UserSecurity;
import com.quantium.web.errors.controllers.ProfileErrors;
import com.quantium.web.errors.controllers.SecurityErrors;
import com.quantium.web.service.ConfigService;
import com.quantium.web.service.MainServices;
import com.quantium.web.service.Services;
import com.quantium.web.service.Social;
import com.quantium.web.util.validators.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserHelper {
    private static AuthenticationManager manager;
    @Autowired
    public void setAuthenticationManager(@Qualifier("authentication-manager") AuthenticationManager manager) {
        UserHelper.manager = manager;
    }

    public static boolean validateUserName(String userName, Response response) {
        int tmp;

        if (ConfigService.getBoolean("USER", "userName"))
            if (userName == null || userName.isEmpty()) {// - validate on empty value
                response.addError(SecurityErrors._NAME, SecurityErrors.USERNAME_EMPTY);
                return false;
            } else if ((tmp = ConfigService.getInt("USER", "userName.length.min")) > 0 && tmp > userName.length()) {// - validate on min length
                response.addError(SecurityErrors._NAME, SecurityErrors.USERNAME_MIN_LENGTH);
                return false;
            } else if ((tmp = ConfigService.getInt("USER", "userName.length.max")) > 0 && tmp < userName.length()) {// - validate on max length
                response.addError(SecurityErrors._NAME,SecurityErrors.USERNAME_MAX_LENGTH);
                return false;
            } else if ((ConfigService.getBoolean("USER", "userName.pattern.useDefault") && !ValidatorUtils.validateBy(Profile.USERNAME, userName)) || (!ConfigService.getBoolean("USER", "userName.pattern.useDefault") && !ValidatorUtils.validateBy(ConfigService.getString("USER", "userName.pattern"), userName))) {// - validate on userName by default or custom pattern
                response.addError(SecurityErrors._NAME, SecurityErrors.USERNAME_INCORRECT);
                return false;
            } else if (Services.security.hasUserName(userName)) {// - verify on exists in database
                response.addError(SecurityErrors._NAME, SecurityErrors.USERNAME_EXISTS);
                return false;
            }

        return true;
    }
    public static boolean validateEmail(String email, Response response) {
        if (ConfigService.getBoolean("USER", "email"))
            if (email == null || email.isEmpty()) {// - validate on empty value
                response.addError(SecurityErrors._NAME, SecurityErrors.EMAIL_EMPTY);
                return false;
            } else if (!ValidatorUtils.isEmail(email)) {// - validate on pattern
                response.addError(SecurityErrors._NAME, SecurityErrors.EMAIL_INCORRECT);
                return false;
            } else if (Services.security.hasEmail(email)) {// - verify on exists in database
                response.addError(SecurityErrors._NAME, SecurityErrors.EMAIL_EXISTS);
                return false;
            }

        return true;
    }
    public static boolean validatePhone(String phone, Response response) {
        if (ConfigService.getBoolean("USER", "phone"))
            if (phone == null || phone.isEmpty()) {// - validate on empty value
                response.addError(SecurityErrors._NAME, SecurityErrors.PHONE_EMPTY);
                return false;
            } else if (!ValidatorUtils.isPhone(phone)) {// - validate on pattern
                response.addError(SecurityErrors._NAME, SecurityErrors.PHONE_INCORRECT);
                return false;
            } else if (Services.security.hasPhone(phone)) {// - verify on exists in database
                response.addError(SecurityErrors._NAME, SecurityErrors.PHONE_EXISTS);
                return false;
            }

        return true;
    }
    public static boolean validatePassword(String password, String passwordConfirm, Response response) {
        int tmp;

        if (password == null || password.isEmpty()) {// - validate on empty value
            response.addError(SecurityErrors._NAME, SecurityErrors.PASSWORD_EMPTY);
            return false;
        } else if ((tmp = ConfigService.getInt("USER", "password.length.min")) > 0 && password.length() < tmp) {// - validate on password length
            response.addError(SecurityErrors._NAME, SecurityErrors.PASSWORD_SMALL);
            return false;
        } else if ((tmp = ConfigService.getInt("USER", "password.length,max")) > 0 && password.length() > tmp) {// - validate on password length
            response.addError(SecurityErrors._NAME, SecurityErrors.PASSWORD_BIG);
            return false;
        } else if (!password.equals(passwordConfirm)) {// - validate on confirm password
            response.addError(SecurityErrors._NAME, SecurityErrors.PASSWORD_NOT_CONFIRMED);
            return false;
        }

        return true;
    }
    public static boolean validateName(String firstName, String lastName, Response response) {
        boolean error = true;

        if (ConfigService.getBoolean("USER", "names")) {
            if (firstName == null || firstName.isEmpty()) {
                response.addError(ProfileErrors._NAME, ProfileErrors.FIRST_NAME_EMPTY);
                error = false;
            } else if (!ValidatorUtils.validateBy(Profile.NAME, firstName)) {
                response.addError(ProfileErrors._NAME, ProfileErrors.FIRST_NAME_INCORRECT);
                error = false;
            }

            if (lastName == null || lastName.isEmpty()) {
                response.addError(ProfileErrors._NAME, ProfileErrors.LAST_NAME_EMPTY);
                error = false;
            } else if (!ValidatorUtils.validateBy(Profile.NAME, lastName)) {
                response.addError(ProfileErrors._NAME, ProfileErrors.LAST_NAME_INCORRECT);
                error = false;
            }
        }

        return error;
    }

    public static UserSecurity loginUser(String login, String password, Response response) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(login, password);

        try {
            Authentication auth = manager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
        } catch (Exception e) {
            response.addError(SecurityErrors._NAME, SecurityErrors.SECURITY_AUTHENTICATE_INVALID);
            return null;
        }

        UserSecurity user = UserHelper.getMe();

        Services.security.getAccessManager(user);
        user.setProfile(Social.profile.getUser(user.getUserId()));

        return user;
    }
    public static boolean logoutUser(Response response) {
        try {
            SecurityContextHolder.clearContext();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isAuthorized() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            return authentication != null && !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
        } catch (Exception e) {
            return false;
        }
    }

    public static UserSecurity getMe() {
        try {
            return ((UserSecurity) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        } catch (Exception e) {
            return null;
        }
    }

    public static int getUserId() {
        try {
            return UserHelper.getMe().getUserId();
        } catch (Exception e) {
            return -1;
        }
    }

}
