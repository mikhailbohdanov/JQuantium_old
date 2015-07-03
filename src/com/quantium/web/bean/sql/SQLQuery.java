package com.quantium.web.bean.sql;

import com.quantium.web.bean.core.StringTemplate;

import java.util.Map;

/**
 * Created by FREEMAN on 05.12.14.
 */
public class SQLQuery extends StringTemplate {
    public SQLQuery(String data) {
        super(data);
    }

    @Override
    public String toString(Map map) {
        SQLUtils.escapeAll(map);
        return super.toString(map);
    }
}
