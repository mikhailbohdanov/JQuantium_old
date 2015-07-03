package com.quantium.web.dao;

import com.quantium.web.bean.security.UserSecurity;
import com.quantium.web.bean.security.permissions.Access;
import com.quantium.web.bean.security.permissions.AccessValue;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by FREEMAN on 30.01.15.
 */
public interface SecurityIO {

    /* - - - USERS - - - */

    // - Create
    public static final String USER_CREATE                              = "INSERT INTO `security_users` (`userName`, `password`, `authorities`, `roleId`, `accountNonExpired`, `accountNonLocked`, `credentialsNonExpired`, `enabled`, `email`, `phone`, `languageCode`) VALUES (:userName, :password, :authorities, :roleId, :accountNE, :accountNL, :credentialsNE, :enabled, :email, :phone, :emailToken)";

    // - Get
    public static final String _USER_ROWS                               = "`userId`, `userName`, `password`, `authorities`, `roleId`, `accountNonExpired`, `accountNonLocked`, `credentialsNonExpired`, `enabled`, `email`, `phone`, `languageCode`";
    public static final String USER_GET_BY_EMAIL                        = "SELECT " + _USER_ROWS + " FROM `security_users` WHERE `email` = :email";
    public static final String USER_GET_BY_PHONE                        = "SELECT " + _USER_ROWS + " FROM `security_users` WHERE `phone` = :phone";
    public static final String USER_GET_BY_USERNAME                     = "SELECT " + _USER_ROWS + " FROM `security_users` WHERE `userName` = :userName";

    public static final String _ACCESS_ROWS                             = "`AV`.`valueId`, `AV`.`accessId`, `AV`.`name`";
    public static final String ACCESS_FOR_USER                          = "SELECT " + _ACCESS_ROWS + " FROM `security_permit_access_values` AS `AV` JOIN `security_permit_roles_access_link` AS `RAL` ON `AV`.`valueId` = `RAL`.`childId` WHERE `RAL`.`parentId` = :roleId UNION SELECT " + _ACCESS_ROWS + " FROM `security_permit_access_values` AS `AV` JOIN `security_permit_users_access_link` AS `UAL` ON `AV`.`valueId` = `UAL`.`childId` WHERE `UAL`.`parentId` = :userId";
    public static final String ACCESS_GET_LIST                          = "SELECT * FROM `security_permit_access` WHERE `accessId`";

    // - Update
    public static final String USER_UPDATE                              = "UPDATE `security_users` SET `userName` = :userName, `password` = :password, `authorities` = :authorities, `roleId` = :roleId, `accountNonExpired` = :accountNE, `accountNonLocked` = :accountNL, `credentialsNonExpired` = :credentialsNE, `enabled` = :enabled, `email` = :email, `phone` = :phone, `languageCode` = :languageCode WHERE `userId` = :userId";

    // - Delete

    // - Mappers

    public static final RowMapper<UserSecurity> USER                    = new RowMapper<UserSecurity>() {
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
    public static final RowMapper<AccessValue> ACCESS_VALUE             = new RowMapper<AccessValue>() {
        @Override
        public AccessValue mapRow(ResultSet rs, int i) throws SQLException {
            AccessValue value = new AccessValue();

            return value;
        }
    };
    public static final RowMapper<Access> ACCESS                        = new RowMapper<Access>() {
        @Override
        public Access mapRow(ResultSet rs, int i) throws SQLException {
            Access access = new Access();

            access
                    .setAccessId(rs.getInt("accessId"))
                    .setName(rs.getString("name"));

            return access;
        }
    };




}
