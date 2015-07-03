package com.quantium.web.service;

import com.quantium.web.bean.core.Response;
import com.quantium.web.bean.security.UserSecurity;
import com.quantium.web.bean.security.UserToken;
import com.quantium.web.bean.social.UserProfile;
import com.quantium.web.dao.DAO;
import com.quantium.web.service.social.Profile;
import com.quantium.web.util.Primitives;
import com.quantium.web.util.UserHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by FREEMAN on 25.02.15.
 */
@Service
public class Users {

    @Autowired
    private DAO dao;

    @Qualifier("security")
    @Autowired
    private Security security;

    @Autowired
    private Profile profile;

    public UserSecurity createUser(String userName, String password, String passwordConfirm, String email, String phone, String firstName, String lastName, boolean autoPassword, Response response) {
        UserHelper.validateUserName(userName, response);
        UserHelper.validateEmail(email, response);
        UserHelper.validatePhone(phone, response);

        if (!autoPassword)
            UserHelper.validatePassword(password, passwordConfirm, response);

        UserHelper.validateName(firstName, lastName, response);

        if (response.hasError())
            return null;

        UserSecurity userSecurity   = new UserSecurity();

        boolean enabled = true;
        String emailToken = null, phoneToken = null, passwordToken = null;


        if (ConfigService.getBoolean("SIGNUP", "email.token")) {
            emailToken      = Primitives.randomString(ConfigService.getInt("SIGNUP", "email.token.length"));
            enabled         = false;
        }

        if (ConfigService.getBoolean("SIGNUP", "phone.token")) {
            phoneToken      = Primitives.randomString(ConfigService.getInt("SIGNUP", "phone.token.length"));
            enabled         = false;
        }

        if (autoPassword) {
            passwordToken   = String.valueOf(Primitives.randomInt(ConfigService.getInt("SIGNUP", "password.autoToken.length")));
            enabled         = false;
        }

        userSecurity
                .setAuthorities("ROLE_USER")
                .setAccountNonLocked(true)
                .setAccountNonExpired(true)
                .setCredentialsNonExpired(true)
                .setEnabled(enabled)

                .setUserName(userName)
                .setEmail(email)
                .setPhone(phone)
                .setPassword(password);

        int userId = security.createUser(userSecurity);

        UserProfile userProfile = new UserProfile();

        userProfile
                .setUserId(userId)
                .setFirstName(firstName)
                .setLastName(lastName);

        profile.createUser(userProfile);

        if (emailToken != null)
            security.createUserToken(
                    userId,
                    UserToken.Type.EMAIL
            );

        if (phoneToken != null)
            security.createUserToken(
                    userId,
                    UserToken.Type.PHONE
            );

        if (passwordToken != null)
            security.createUserToken(
                    userId,
                    UserToken.Type.PASSWORD
            );

        //TODO call create tables for user in random selected data node
//        ActionService.call("social.user.create");

        userSecurity.setProfile(userProfile);

        return userSecurity;
    }

    public UserSecurity getUser(int userId) {
        if (userId == 0)
            return null;

        UserSecurity userSecurity;
        UserProfile userProfile;

        userSecurity    = security.getUser(userId);

        if (userSecurity == null)
            return null;

        userProfile     = profile.getUser(userId);

        if (userProfile == null)
            return null;

        userSecurity.setProfile(userProfile);

        return userSecurity;
    }
    public UserSecurity getUser(String userName) {
        if (userName == null || userName.isEmpty())
            return null;

        UserSecurity userSecurity;
        UserProfile userProfile;

        userSecurity    = security.getUser(userName);

        if (userSecurity == null)
            return null;

        userProfile     = profile.getUser(userSecurity.getUserId());

        if (userProfile == null)
            return null;

        userSecurity.setProfile(userProfile);

        return userSecurity;
    }



}
