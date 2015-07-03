package com.quantium.web.dao.social;

import com.quantium.web.bean.core.StringTemplate;
import com.quantium.web.bean.social.UserProfile;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by FREEMAN on 28.01.15.
 */
public interface UserListIO {

    // - Create

    // - Get
    public static final StringTemplate USER_GET_TYPE                = new StringTemplate("SELECT `type` FROM `user_{userId}_list` WHERE `userId` = :userId");


    // - Update

    // - Delete

    // - Mappers
    public static final RowMapper<UserProfile.Owner> USER_TYPE      = new RowMapper<UserProfile.Owner>() {
        @Override
        public UserProfile.Owner mapRow(ResultSet rs, int i) throws SQLException {
            return UserProfile.Owner.valueOf(rs.getString("type"));
        }
    };

}
