package com.quantium.web.service.social;

import com.quantium.web.bean.social.UserProfile;
import com.quantium.web.dao.UtilityDAO;
import com.quantium.web.dao.social.UserListDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by FREEMAN on 28.01.15.
 */
@Service
public class UserListService {

    @Autowired
    private UserListDAO userListDAO;

    @Autowired
    private UtilityDAO utilityDAO;

    public UserProfile.Owner userToMe(int userId) {
        try {
            return userListDAO.getUser(userId);
        } catch (Exception e) {}

        return UserProfile.Owner.NOT_FRIEND;
    }



}
