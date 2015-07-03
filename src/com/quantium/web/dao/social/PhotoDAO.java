package com.quantium.web.dao.social;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

/**
 * Created by FREEMAN on 14.10.14.
 */
@Repository
public class PhotoDAO extends Photo {
    private NamedParameterJdbcTemplate data;
    @Autowired
    public void init(@Qualifier("userFrame") DataSource dataSource) {
        data = new NamedParameterJdbcTemplate(dataSource);
    }


}
