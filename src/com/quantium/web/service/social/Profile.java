package com.quantium.web.service.social;

import com.quantium.web.bean.core.Route;
import com.quantium.web.bean.social.UserProfile;
import com.quantium.web.dao.DAO;
import com.quantium.web.service.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by FREEMAN on 25.02.15.
 */
@Service
public class Profile {
    /* - - - Profile queries - - - */
    private static final String _USER_ROWS                          = "`userId`, `firstName`, `lastName`, `sex`, `relation`, `relationUserId`, `BDate`, `BDateVisibility`, `countryId`, `cityId`, `photoId`";
    public static final String PROFILE_GET_SPECIFIC_BY_ID = "SELECT " + _USER_ROWS + " FROM `social_profiles` WHERE `userId` = :userId";

    /* - - - Profile mappers - - - */
    public static final RowMapper<UserProfile> PROFILE              = new RowMapper<UserProfile>() {
        @Override
        public UserProfile mapRow(ResultSet rs, int i) throws SQLException {
            UserProfile user = new UserProfile();

            user
                    .setUserId(rs.getInt("userId"))
                    .setFirstName(rs.getString("firstName"))
                    .setLastName(rs.getString("lastName"))
                    .setSex(rs.getString("sex"))
                    .setRelation(rs.getString("relation"))
                    .setRelationUserId(rs.getInt("relationUserId"))
                    .setBDate(rs.getDate("BDate"))
                    .setBDateVisibility(rs.getInt("BDateVisibility"))
                    .setCountryId(rs.getInt("countryId"))
                    .setCityId(rs.getInt("cityId"))
                    .setPhotoId(rs.getInt("photoId"));

            return user;
        }
    };

    @Autowired
    private DAO dao;


    public void createUser(UserProfile user) {
        if (user == null)
            return;





    }

    public UserProfile getUser(int userId) {
        try {
            UserProfile user    = dao.getRow(
                    PROFILE_GET_SPECIFIC_BY_ID,
                    (new MapSqlParameterSource())
                            .addValue("userId", userId),
                    PROFILE
            );

            user.setRoute(Services.core.getRoute(Route.Type.USER, userId));

            return user;
        } catch (Exception e) {
            return null;
        }
    }
    public UserProfile getUser(String userName) {

        return null;
    }

}
