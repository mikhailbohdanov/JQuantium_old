package com.quantium.web.dao.social;

import com.quantium.web.bean.social.UserProfile;
import com.quantium.web.util.UserHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

/**
 * Created by FREEMAN on 28.01.15.
 */
@Repository
public class UserListDAO implements UserListIO {
    private NamedParameterJdbcTemplate data;
    @Autowired
    public void init(@Qualifier("userFrame") DataSource dataSource) {
        data = new NamedParameterJdbcTemplate(dataSource);
    }

    public UserProfile.Owner getUser(int userId) throws Exception {
        try {
            return data.queryForObject(
                    USER_GET_TYPE.toString(UserHelper.getUserId()),
                    (new MapSqlParameterSource())
                            .addValue("userId", userId),
                    USER_TYPE
            );
        } catch (DataAccessException e) {
            throw e;
        }
    }

}
