package com.quantium.web.dao.social;

import com.quantium.web.bean.social.UserProfile;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by FREEMAN on 30.09.14.
 */
public class Profile {

    public static final String USER_PROFILE_CREATE              = "INSERT INTO `social_profiles` (`userId`, `firstName`, `lastName`, `sex`, `relation`, `relationUserId`, `BDate`, `BDateVisibility`, `countryId`, `cityId`, `photoId`) VALUES (:userId, :firstName, :lastName, :sex, :relation, :relationUserId, :BDate, :BDateVisibility, :countryId, :cityId, :photoId)";



    protected final String GET_PROFILE_BY_ID_FULL       = "SELECT `userId`, `firstName`, `lastName`, `sex`, `relation`, `relationUserId`, `bDate`, `bDateVisibility`, `countryId`, `cityId`, `photoId` FROM `social_profiles` WHERE `userId` = :userId";
    protected final String GET_PROFILE_BY_ID_MIN        = "SELECT `userId`, `firstName`, `lastName`, `photoId` FROM `social_profiles` WHERE `userId` = :userId";

    protected final String GET_PROFILES_BY_ID_MIN       = "SELECT `userId`, `firstName`, `lastName`, `photoId` FROM `social_profiles` WHERE `userId` IN (:usersId)";

    protected final String GET_PROFILE_BY_USERNAME_FULL = "SELECT `s`.`userId`, `s`.`firstName`, `s`.`lastName`, `s`.`sex`, `s`.`relation`, `s`.`relationUserId`, `s`.`bDate`, `s`.`bDateVisibility`, `s`.`countryId`, `s`.`cityId`, `s`.`photoId` FROM `social_profiles` AS `s` LEFT JOIN `security_users` AS `u` ON `s`.`userId` = `u`.`id` WHERE `u`.`userName` = :userName";
    protected final String GET_PROFILE_BY_USERNAME_MIN  = "SELECT `s`.`userId`, `s`.`firstName`, `s`.`lastName`, `s`.`photoId` FROM `social_profiles` AS `s` LEFT JOIN `security_users` AS `u` ON `s`.`userId` = `u`.`id` WHERE `u`.`userName` = :userName";



    protected final RowMapper<UserProfile> PROFILE_FULL  = new RowMapper<UserProfile>() {
        @Override
        public UserProfile mapRow(ResultSet rs, int i) throws SQLException {
            return (new UserProfile())
                    .setUserId(rs.getInt("userId"))
                    .setFirstName(rs.getString("firstName"))
                    .setLastName(rs.getString("lastName"))
                    .setSex(rs.getString("sex"))
                    .setRelation(rs.getString("relation"))
                    .setRelationUserId(rs.getInt("relationUserId"))
                    .setBDate(rs.getDate("bDate"))
                    .setBDateVisibility(rs.getInt("bDateVisibility"))
                    .setCountryId(rs.getInt("countryId"))
                    .setCityId(rs.getInt("cityId"));
        }
    };
    protected final RowMapper<UserProfile> PROFILE_MIN   = new RowMapper<UserProfile>() {
        @Override
        public UserProfile mapRow(ResultSet rs, int i) throws SQLException {
            return (new UserProfile())
                    .setUserId(rs.getInt("userId"))
                    .setFirstName(rs.getString("firstName"))
                    .setLastName(rs.getString("lastName"))
                    .setPhotoId(rs.getInt("photoId"));
        }
    };



}
