package com.quantium.web.service;

import com.quantium.web.bean.security.UserSecurity;
import com.quantium.web.bean.security.UserToken;
import com.quantium.web.bean.security.permissions.Access;
import com.quantium.web.bean.security.permissions.AccessManager;
import com.quantium.web.bean.security.permissions.AccessValue;
import com.quantium.web.dao.DAO;
import com.quantium.web.util.Primitives;
import com.quantium.web.util.ValidatorUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by FREEMAN on 24.02.15.
 */
@Service("security")
public class Security implements UserDetailsService {
    /* - - - User queries - - - */
    private static final String _USER_ROWS                  = "`userId`, `userName`, `password`, `authorities`, `roleId`, `accountNonExpired`, `accountNonLocked`, `credentialsNonExpired`, `enabled`, `email`, `phone`, `languageCode`";
    public static final String USER_GET_BY_ID               = "SELECT " + _USER_ROWS + " FROM `security_users` WHERE `userId` = :userId";
    public static final String USER_GET_BY_USERNAME         = "SELECT " + _USER_ROWS + " FROM `security_users` WHERE `userName` = :userName";
    public static final String USER_GET_BY_EMAIL            = "SELECT " + _USER_ROWS + " FROM `security_users` WHERE `email` = :email";
    public static final String USER_GET_BY_PHONE            = "SELECT " + _USER_ROWS + " FROM `security_users` WHERE `phone` = :phone";

    /* - - - User mappers - - - */
    public static final RowMapper<UserSecurity> USER        = new RowMapper<UserSecurity>() {
        @Override
        public UserSecurity mapRow(ResultSet rs, int i) throws SQLException {
            UserSecurity user = new UserSecurity();

            user
                    .setUserId(rs.getInt("userId"))
                    .setUserName(rs.getString("userName"))
                    .setPassword(rs.getString("password"))
                    .setAuthorities(rs.getString("authorities"))
                    .setRoleId(rs.getInt("roleId"))
                    .setAccountNonExpired(rs.getBoolean("accountNonExpired"))
                    .setAccountNonLocked(rs.getBoolean("accountNonLocked"))
                    .setCredentialsNonExpired(rs.getBoolean("credentialsNonExpired"))
                    .setEnabled(rs.getBoolean("enabled"))
                    .setEmail(rs.getString("email"))
                    .setPhone(rs.getString("phone"))
                    .setLanguageCode(rs.getString("languageCode"));

            return user;
        }
    };


    /* - - - Access manager queries - - - */
    private static final String _ACCESS_MANAGER_ROWS        = "`AV`.`valueId`, `AV`.`accessId`, `AV`.`name`";
    public static final String ACCESS_MANAGER_FOR_USER      = "SELECT " + _ACCESS_MANAGER_ROWS + " FROM `security_permit_access_values` AS `AV` JOIN `security_permit_roles_access_link` AS `RAL` ON `AV`.`valueId` = `RAL`.`childId` WHERE `RAL`.`parentId` = :roleId UNION SELECT " + _ACCESS_MANAGER_ROWS + " FROM `security_permit_access_values` AS `AV` JOIN `security_permit_users_access_link` AS `UAL` ON `AV`.`valueId` = `UAL`.`childId` WHERE `UAL`.`parentId` = :userId";
    public static final String ACCESS_GET_LIST              = "SELECT * FROM `security_permit_access` WHERE `accessId`";

    /* - - - Access manager mappers - - - */
    public static final RowMapper<AccessValue> ACCESS_VALUE = new RowMapper<AccessValue>() {
        @Override
        public AccessValue mapRow(ResultSet rs, int i) throws SQLException {
            AccessValue value = new AccessValue();

            value
                    .setValueId(rs.getInt("valueId"))
                    .setAccessId(rs.getInt("accessId"))
                    .setName(rs.getString("name"));

            return value;
        }
    };
    public static final RowMapper<Access> ACCESS            = new RowMapper<Access>() {
        @Override
        public Access mapRow(ResultSet rs, int i) throws SQLException {
            Access access = new Access();

            access
                    .setAccessId(rs.getInt("accessId"))
                    .setName(rs.getString("name"));

            return access;
        }
    };

    @Autowired
    private DAO dao;


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        return getUser(userName);
    }

    public boolean hasUserName(String userName) {
        return Services.util.hasValue("security_users", "userName", userName);
    }
    public boolean hasEmail(String email) {
        return Services.util.hasValue("security_users", "email", email);
    }
    public boolean hasPhone(String phone) {
        return Services.util.hasValue("security_users", "phone", phone);
    }

    public int createUser(UserSecurity user) {
        if (user == null)
            return 0;

        int userId = Services.util.createRow(user);

        return userId;
    }

    public UserSecurity getUser(int userId) {
        try {
            return dao.getRow(
                    USER_GET_BY_ID,
                    (new MapSqlParameterSource())
                            .addValue("userId", userId),
                    USER
            );
        } catch (Exception e) {
            return null;
        }
    }
    public UserSecurity getUser(String userName) {
        String query;
        MapSqlParameterSource map = new MapSqlParameterSource();

        if (ValidatorUtils.isEmail(userName)) {
            query   = USER_GET_BY_EMAIL;
            map.addValue("email", userName);
        } else if (ValidatorUtils.isPhone(userName)) {
            query   = USER_GET_BY_PHONE;
            map.addValue("phone", userName);
        } else {
            query   = USER_GET_BY_USERNAME;
            map.addValue("userName", userName);
        }

        try {
            return dao.getRow(
                    query,
                    map,
                    USER
            );
        } catch (Exception e) {
            return null;
        }
    }

    public int createUserToken(int userId, UserToken.Type type) {
        if (userId == 0 || type == null)
            return 0;

        UserToken token = new UserToken();

        token
                .setUserId(userId)
                .setType(type);

        switch (type) {
            case EMAIL:
                token.setToken(Primitives.randomString());
                break;
            case PHONE:
                token.setToken(String.valueOf(Primitives.randomInt(4)));
                break;
            case PASSWORD:
                token.setToken(String.valueOf(Primitives.randomInt(8)));
                break;
        }

        int tokenId = Services.util.createRow(token);

        return tokenId;
    }


    public AccessManager getAccessManager(UserSecurity user) {
        if (user == null)
            return null;

        ArrayList<AccessValue> values = null;
        try {
            values = (ArrayList<AccessValue>) dao.getRowList(
                    ACCESS_MANAGER_FOR_USER,
                    (new MapSqlParameterSource())
                            .addValue("roleId", user.getRoleId())
                            .addValue("userId", user.getUserId()),
                    ACCESS_VALUE
            );
        } catch (Exception e) {
            return null;
        }

        ArrayList<Access> accesses = null;
        try {
            accesses = (ArrayList<Access>) dao.getRowList(
                    ACCESS_GET_LIST,
                    null,
                    ACCESS
            );
        } catch (Exception e) {
            return null;
        }

        AccessManager manager = new AccessManager();

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

        user.setAccessManager(manager);

        return manager;
    }

}
