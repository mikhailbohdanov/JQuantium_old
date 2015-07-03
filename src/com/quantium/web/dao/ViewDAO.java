package com.quantium.web.dao;

import com.quantium.web.bean.view.ClientPage;
import com.quantium.web.bean.view.Group;
import com.quantium.web.bean.view.Module;
import com.quantium.web.bean.view.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by Михаил on 03.09.14.
 */
@Repository
public class ViewDAO extends View {
    private NamedParameterJdbcTemplate data;
    @Autowired
    public void init(@Qualifier("mainFrame") DataSource dataSource) {
        data = new NamedParameterJdbcTemplate(dataSource);
    }



}
