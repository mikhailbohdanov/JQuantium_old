package com.quantium.web.dao;

import com.quantium.web.bean.core.StringTemplate;
import com.quantium.web.bean.security.UserSecurity;
import com.quantium.web.bean.social.UserProfile;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by FREEMAN on 19.02.15.
 */
public interface UserIO {

    // - - - ADMIN USERS - - - */

    // - Create

    // - Get
    public static final StringTemplate _USER_GET_LIST_WITH_ORDER                    = new StringTemplate("SELECT `security_users`.`userId`, `security_users`.`userName`, `security_users`.`email`, `security_users`.`phone`, `social_profiles`.`firstName`, `social_profiles`.`lastName` FROM `quantium`.`security_users` LEFT JOIN `quantium`.`social_profiles` ON (`security_users`.`userId` = `social_profiles`.`userId`) ORDER BY `{tableName}`.`{columnName}` {order} LIMIT :offset, :limit");
    public static final String _USER_GET_COUNT                                      = "SELECT COUNT(*) FROM `security_users`";

    // - Update

    // - Delete

    // - Mappers
    public static final RowMapper<UserSecurity> USER                                = new RowMapper<UserSecurity>() {
        @Override
        public UserSecurity mapRow(ResultSet rs, int i) throws SQLException {
            UserSecurity user = new UserSecurity();

            user
                    .setUserId(rs.getInt("userId"))
                    .setUserName(rs.getString("userName"))
                    .setEmail(rs.getString("email"))
                    .setPhone(rs.getString("phone"));

            UserProfile _user = new UserProfile();

            _user
                    .setFirstName(rs.getString("firstName"))
                    .setLastName(rs.getString("lastName"));

            user.setProfile(_user);

            return user;
        }
    };

}
