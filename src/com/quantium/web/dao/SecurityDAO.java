package com.quantium.web.dao;

import com.quantium.web.bean.view.PagePassword;
import com.quantium.web.bean.view.PagePasswordUserLink;
import com.quantium.web.bean.security.UserSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Михаил on 01.09.14.
 */
@Repository
public class SecurityDAO extends Security implements SecurityIO {
    private NamedParameterJdbcTemplate data;
    @Autowired
    public void init(@Qualifier("mainFrame") DataSource dataSource) {
        data = new NamedParameterJdbcTemplate(dataSource);
    }

    public int createUser(UserSecurity user) throws Exception {
        try {
            KeyHolder key = new GeneratedKeyHolder();

            data.update(
                    USER_CREATE,
                    (new MapSqlParameterSource())
                            .addValue("userName", user.getUserName())
                            .addValue("password", user.getPassword())
                            .addValue("authorities", user.getAuthoritiesAsString())
                            .addValue("accessId", user.getRoleId())
                            .addValue("accountNE", user.isAccountNonExpired())
                            .addValue("accountNL", user.isAccountNonLocked())
                            .addValue("credentialsNE", user.isCredentialsNonExpired())
                            .addValue("enabled", user.isEnabled())
                            .addValue("email", user.getEmail())
                            .addValue("phone", user.getPhone()),
                    key
            );

            return key.getKey().intValue();
        } catch (DataAccessException e) {
            throw new Exception("Cannot create new Security record for user", e);
        }
    }

//    public UserSecurity getUserById(int userId) throws Exception {
//        return data.queryForObject(
//
//        )
//    }
    public UserSecurity getUserByEmail(String email) throws Exception {
        try {
            return data.queryForObject(
                    USER_GET_BY_EMAIL,
                    (new MapSqlParameterSource())
                        .addValue("email", email),
                    USER
            );
        } catch (Exception e) {
            throw new Exception("User profile not found by email: " + email, e);
        }
    }
    public UserSecurity getUserByPhone(String phone) throws Exception {
        try {
            return data.queryForObject(
                    GET_USER_BY_PHONE,
                    (new MapSqlParameterSource())
                            .addValue("phone", phone),
                    USER
            );
        } catch (Exception e) {
            throw new Exception("User profile not found by phone: " + phone, e);
        }
    }
    public UserSecurity getUserByUserName(String userName) throws Exception {
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("userName", userName);

        try {
            return data.queryForObject(
                    USER_GET_BY_USERNAME,
                    map,
                    USER
            );
        } catch (Exception e) {
            throw new Exception("User profile not found by userName: " + userName, e);
        }
    }

    public void updateUser(UserSecurity user) throws Exception {
        MapSqlParameterSource map = new MapSqlParameterSource();

        map
                .addValue("userName", user.getUserName())
                .addValue("password", user.getPassword())
                .addValue("authorities", user.getAuthoritiesAsString())
                .addValue("accessId", user.getRoleId())
                .addValue("accountNE", user.isAccountNonExpired())
                .addValue("accountNL", user.isAccountNonLocked())
                .addValue("credentialsNE", user.isCredentialsNonExpired())
                .addValue("enabled", user.isEnabled())
                .addValue("email", user.getEmail())
                .addValue("phone", user.getPhone())
                .addValue("languageCode", user.getLanguageCode());

        try {
            data.update(
                    USER_UPDATE,
                    map
            );
        } catch (DataAccessException e) {
            throw new Exception(e);
        }
    }



    public List<PagePassword> getPagePasswords(int pageId) throws Exception {
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("pageId", pageId);

        try {
            return data.query(
                    GET_PAGE_PASSWORDS,
                    map,
                    PAGE_PASSWORD
            );
        } catch (DataAccessException e) {
            throw new Exception("SecurityDAO.getPagePasswords(pageId) some problem", e);
        }
    }

    public Map<Integer, PagePasswordUserLink.Type> getPagePasswordUsers(int passwordId) throws Exception {
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("passwordId", passwordId);

        try {
            List<PagePasswordUserLink> links = data.query(
                    GET_PAGE_PASSWORD_USERS,
                    map,
                    PAGE_PASSWORD_USER_LINK
            );

            HashMap<Integer, PagePasswordUserLink.Type> out = new HashMap<Integer, PagePasswordUserLink.Type>();
            for (PagePasswordUserLink link : links)
                out.put(link.getUserId(), link.getType());

            return out;
        } catch (DataAccessException e) {
            throw new Exception("SecurityDAO.getPagePasswordUsers(passwordId) some problem", e);
        }
    }

}
