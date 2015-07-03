package com.quantium.web.bean.core;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

/**
 * Author FREEMAN
 * Created 18.11.14
 */
public abstract class AutoUpdate<T> {
    protected boolean modified = false;


    public abstract T update();

    public T updateMe() {
        this.update();


        return null;
    }
}
