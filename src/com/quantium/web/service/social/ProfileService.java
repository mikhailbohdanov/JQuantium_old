package com.quantium.web.service.social;

import com.quantium.web.bean.social.UserProfile;
import com.quantium.web.dao.social.ProfileDAO;
import com.quantium.web.service.MainServices;
import com.quantium.web.service.SocialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FREEMAN on 30.09.14.
 */
@Service
public class ProfileService {

    @Autowired
    private ProfileDAO profileDAO;

    public boolean createUserTable(int serverId, int userId) {


        return false;
    }

    public boolean createProfile(UserProfile user) {
        try {
            profileDAO.createProfile(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public UserProfile getProfile(int userId) {
        return getProfile(userId, false);
    }
    public UserProfile getProfile(int userId, boolean links) {
        UserProfile user = null;

        try {
            user = profileDAO.getProfile(userId, null, true);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return user;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return user;
        }

        if (links)
            getProfileLinks(user);

        return user;
    }

    public UserProfile getProfile(String userName) {
        return getProfile(userName, false);
    }
    public UserProfile getProfile(String userName, boolean links) {
        UserProfile user = null;

        try {
            user = profileDAO.getProfile(0, userName, true);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return user;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return user;
        }

        if (links)
            getProfileLinks(user);

        return user;
    }

    public UserProfile getProfileMin(int userId) {
        try {
            return profileDAO.getProfile(userId, null, false);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<UserProfile> getProfilesMin(int[] usersId) {
        try {
            return (ArrayList<UserProfile>) profileDAO.getProfiles(usersId);
        } catch (Exception e) {
            return new ArrayList<UserProfile>();
        }
    }
    public ArrayList<UserProfile> getProfilesMin(List<Integer> usersId) {
        return null;
    }

    public UserProfile getProfileLinks(UserProfile user) {

        if (user.getRelationUserId() > 0)
            user.setRelationUser(getProfileMin(user.getRelationUserId()));

        if (user.getPhotoId() > 0)
            user.setPhoto(SocialService.albumPhotoService.getPhoto(user.getPhotoId(), user.getUserId()));

        if (user.getCountryId() > 0)
            user.setCountry(MainServices.locationService.getCountry(user.getCountryId()));

        if (user.getCityId() > 0)
            user.setCity(MainServices.locationService.getCity(user.getCityId()));

        return user;
    }



}
