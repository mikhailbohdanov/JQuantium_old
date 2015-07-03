package com.quantium.web.service;

import com.quantium.web.bean.security.permissions.Access;
import com.quantium.web.bean.security.permissions.AccessManager;
import com.quantium.web.bean.security.permissions.AccessValue;
import com.quantium.web.bean.view.PagePassword;
import com.quantium.web.bean.view.PagePasswordUserLink;
import com.quantium.web.bean.security.UserSecurity;
import com.quantium.web.dao.SecurityDAO;
import com.quantium.web.dao.SecurityIO;
import com.quantium.web.dao.UtilityDAO;
import com.quantium.web.util.ValidatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Михаил on 01.09.14.
 */
@Service
public class SecurityService implements SecurityIO {

    @Autowired
    private SecurityDAO securityDAO;

    @Autowired
    private UtilityDAO utilityDAO;

    public int createUser(UserSecurity user) {
        try {
            int userId = securityDAO.createUser(user);

            user.setUserId(userId);

            return userId;
        } catch (Exception e) {
            return 0;
        }
    }

    public UserSecurity getSecurityUser(String userName, boolean includeAll) {
        UserSecurity user = null;
        try {
            if (ValidatorUtils.isEmail(userName))
                user = securityDAO.getUserByEmail(userName);
            else if (ValidatorUtils.isPhone(userName))
                user = securityDAO.getUserByPhone(userName);
            else
                user = securityDAO.getUserByUserName(userName);
        } catch (Exception e) {
            return null;
        }

        if (includeAll && user != null)
            user.setProfile(SocialService.profileService.getProfile(user.getUserId()));

        if (includeAll && user != null)
            user.setAccessManager(getAccessManager(user));

        return user;
    }
    public UserSecurity getSecurityUser(int userId) {



        return null;
    }

    public boolean updateSecurityUser(UserSecurity user) {
        try {
            securityDAO.updateUser(user);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public boolean hasUserName(String userName) {
        try {
            return utilityDAO.hasValue("security_users", "userName", userName);
        } catch (Exception e) {
            return false;
        }
    }
    public boolean hasEmail(String email) {
        try {
            return utilityDAO.hasValue("security_users", "email", email);
        } catch (Exception e) {
            return false;
        }
    }
    public boolean hasPhone(String phone) {
        try {
            return utilityDAO.hasValue("security_users", "phone", phone);
        } catch (Exception e) {
            return false;
        }
    }

    public AccessManager getAccessManager(UserSecurity user) {
        ArrayList<AccessValue> values = null;
        try {
            values = (ArrayList<AccessValue>) utilityDAO.getRowList(
                    ACCESS_FOR_USER,
                    (new MapSqlParameterSource())
                            .addValue("roleId", user.getRoleId())
                            .addValue("userId", user.getUserId()),
                    ACCESS_VALUE
            );
        } catch (Exception e) {}

        AccessManager manager = new AccessManager();

        if (values != null) {
            ArrayList<Integer> accessIds = new ArrayList<Integer>();

            ArrayList<Access> accesses = null;
            try {
                accesses = (ArrayList<Access>) utilityDAO.getRowList(
                        ACCESS_GET_LIST,
                        ACCESS
                );
            } catch (Exception e) {}

            if (accesses != null) {
                int accessId;
                HashSet<String> _values;

                for (Access access : accesses) {
                    accessId    = access.getAccessId();

                    _values = new HashSet<String>();
                    for (AccessValue value : values)
                        if (accessId == value.getAccessId())
                            _values.add(value.getName());

                    manager.addAccess(access.getName(), _values);
                }
            }
        }

        return manager;
    }

//    @Cacheable("pagePasswords")
    public ArrayList<PagePassword> getPagePasswords(int pageId) {
        try {
            ArrayList<PagePassword> passwords = (ArrayList) securityDAO.getPagePasswords(pageId);

//            for (PagePassword password : passwords)
//                password.setUsers(getPagePasswordUsers(password.getId()));

            return passwords;
        } catch (Exception e) {
            return new ArrayList<PagePassword>();
        }
    }

//    @Cacheable("pagePasswordUsers")
    public HashMap<Integer, PagePasswordUserLink.Type> getPagePasswordUsers(int passwordId) {
        try {
            return (HashMap<Integer, PagePasswordUserLink.Type>) securityDAO.getPagePasswordUsers(passwordId);
        } catch (Exception e) {
            return new HashMap<Integer, PagePasswordUserLink.Type>();
        }
    }




}
