package com.quantium.web.dao;

import com.quantium.web.bean.view.PagePassword;
import com.quantium.web.bean.view.PagePasswordUserLink;
import com.quantium.web.bean.security.UserSecurity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Михаил on 02.09.14.
 */
public class Security {

    /* - - - USERS - - - */
//    public static final String USER_CREATE                      = "INSERT INTO `security_users` (`userName`, `password`, `authorities`, `accountNonExpired`, `accountNonLocked`, `credentialsNonExpired`, `enabled`, `email`, `phone`, `emailToken`, `phoneToken`) VALUES (:userName, SHA1(:password), :authorities, :accountNE, :accountNL, :credentialsNE, :enabled, :email, :phone, :emailToken, :phoneToken)";


    private final String _USER_ROWS                             = "`userId`, `userName`, `password`, `authorities`, `accountNonExpired`, `accountNonLocked`, `credentialsNonExpired`, `enabled`, `email`, `phone`, `emailToken`, `phoneToken`, `languageCode`";
    final String GET_USER_BY_EMAIL                              = "SELECT " + _USER_ROWS + " FROM `security_users` WHERE `email` = :email";
    final String GET_USER_BY_PHONE                              = "SELECT " + _USER_ROWS + " FROM `security_users` WHERE `phone` = :phone";
    final String GET_USER_BY_USERNAME                           = "SELECT " + _USER_ROWS + " FROM `security_users` WHERE `userName` = :userName";



    final String GET_PAGE_PASSWORDS                             = "SELECT `id`, `pageId`, `access`, `password` FROM `security_page_passwords` WHERE `pageId` = :pageId";
    final String GET_PAGE_PASSWORD_USERS                        = "SELECT `userId`, `type` FROM `security_page_password_user_link` WHERE `passwordId` = :passwordId";
//
//    protected final RowMapper<UserSecurity> USER                                = new RowMapper<UserSecurity>() {
//        @Override
//        public UserSecurity mapRow(ResultSet rs, int i) throws SQLException {
//            return (new UserSecurity())
//                    .setUserId(rs.getInt("userId"))
//                    .setUserName(rs.getString("userName"))
//                    .setPassword(rs.getString("password"))
//                    .setAuthorities(rs.getString("authorities"))
//                    .setAccountNonExpired(rs.getBoolean("accountNonExpired"))
//                    .setAccountNonLocked(rs.getBoolean("accountNonLocked"))
//                    .setCredentialsNonExpired(rs.getBoolean("credentialsNonExpired"))
//                    .setEnabled(rs.getBoolean("enabled"))
//                    .setEmail(rs.getString("email"))
//                    .setPhone(rs.getString("phone"))
//                    .setEmailToken(rs.getString("emailToken"))
//                    .setPhoneToken(rs.getString("phoneToken"))
//                    .setLanguageCode(rs.getString("languageCode"));
//        }
//    };
//

    protected final RowMapper<PagePassword> PAGE_PASSWORD               = new RowMapper<PagePassword>() {
        @Override
        public PagePassword mapRow(ResultSet rs, int i) throws SQLException {
            return (new PagePassword())
                    .setId(rs.getInt("id"))
                    .setPageId(rs.getInt("pageId"))
                    .setAccess(rs.getString("access"))
                    .setPassword(rs.getString("password"));
        }
    };
    protected final RowMapper<PagePasswordUserLink> PAGE_PASSWORD_USER_LINK = new RowMapper<PagePasswordUserLink>() {
        @Override
        public PagePasswordUserLink mapRow(ResultSet rs, int i) throws SQLException {
            return (new PagePasswordUserLink())
                    .setUserId(rs.getInt("userId"))
                    .setType(rs.getString("type"));
        }
    };

}
