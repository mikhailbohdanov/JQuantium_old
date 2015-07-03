package com.quantium.web.dao.social;

import com.quantium.web.bean.social.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;

/**
 * Created by FREEMAN on 30.09.14.
 */
@Repository
public class ProfileDAO extends Profile {
    private NamedParameterJdbcTemplate data;
    @Autowired
    public void init(@Qualifier("mainFrame") DataSource dataSource) {
        data = new NamedParameterJdbcTemplate(dataSource);
    }


    public void createProfile(UserProfile user) throws Exception {
        try {
            data.update(
                    USER_PROFILE_CREATE,
                    (new MapSqlParameterSource())
                            .addValue("userId", user.getUserId())
                            .addValue("firstName", user.getFirstName())
                            .addValue("lastName", user.getLastName())
                            .addValue("sex", user.getSex())
                            .addValue("relation", user.getRelation())
                            .addValue("relationUserId", user.getRelationUserId())
                            .addValue("BDate", user.getBDate())
                            .addValue("BDateVisibility", user.getBDateVisibility())
                            .addValue("countryId", user.getCountryId())
                            .addValue("cityId", user.getCityId())
                            .addValue("photoId", user.getPhotoId())
            );
        } catch (DataAccessException e) {
            throw e;
        }
    }



    public UserProfile getProfile(int userId, String userName, boolean full) throws DataAccessException, NullPointerException {
        MapSqlParameterSource map = new MapSqlParameterSource();

        try {
            if (userId > 0){
                map.addValue("userId", userId);

                return data.queryForObject(
                        (full ? GET_PROFILE_BY_ID_FULL : GET_PROFILE_BY_ID_MIN),
                        map,
                        (full ? PROFILE_FULL : PROFILE_MIN)
                );
            } else if (userName != null){
                map.addValue("userName", userName);

                return data.queryForObject(
                        (full ? GET_PROFILE_BY_USERNAME_FULL : GET_PROFILE_BY_USERNAME_MIN),
                        map,
                        (full ? PROFILE_FULL : PROFILE_MIN)
                );
            } else
                throw new NullPointerException("ProfileDAO.getProfile there are no options");
        } catch (DataAccessException e) {
            throw e;
        }
    }

    public List<UserProfile> getProfiles(int[] usersId) throws DataAccessException, NullPointerException {
        if (usersId == null)
            throw new NullPointerException("ProfileDAO.getProfiles there are no options");

        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("usersId", Arrays.asList(usersId));

        try {
            return data.query(
                    GET_PROFILES_BY_ID_MIN,
                    map,
                    PROFILE_MIN
            );
        } catch (DataAccessException e) {
            throw e;
        }
    }


}
