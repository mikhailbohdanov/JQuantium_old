package com.quantium.web.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

/**
 * Author FREEMAN
 * Created 12.11.14
 */
@Repository
public class UtilityDAO implements UtilityIO {
    private NamedParameterJdbcTemplate data;
    @Autowired
    public void init(@Qualifier("mainFrame") DataSource dataSource) {
        data = new NamedParameterJdbcTemplate(dataSource);
    }

    public boolean hasValue(String tableName, String rowName, String value) throws Exception {
        return 0 < countValues(tableName, rowName, value);
    }

    public int countValues(String tableName) throws Exception {
        try {
            return data.queryForObject(
                    COUNT_ALL_VALUES.toString(tableName),
                    (new MapSqlParameterSource()),
                    Integer.class
            );
        } catch (DataAccessException e) {
            throw new Exception(FAILED_COUNT_ALL.toString(tableName), e);
        }
    }
    public int countValues(String tableName, String rowName, String value) throws Exception {
        try {
            return data.queryForObject(
                    COUNT_VALUES.toString(tableName, rowName),
                    (new MapSqlParameterSource())
                            .addValue("value", value),
                    Integer.class
            );
        } catch (DataAccessException e) {
            throw new Exception(FAILED_COUNT.toString(tableName, rowName, value), e);
        }
    }

    public int createRow(String sql, MapSqlParameterSource map) throws Exception {
        try {
            KeyHolder key = new GeneratedKeyHolder();

            data.update(sql, map, key);

            return key.getKey().intValue();
        } catch (Exception e) {
            throw new Exception("", e);//TODO add message of exception
        }
    }

    public <T> List<T> getRowList(String sql, RowMapper<T> mapper) throws Exception {
        return getRowList(sql, null, mapper);
    }
    public <T> List<T> getRowList(String sql, MapSqlParameterSource map, RowMapper<T> mapper) throws Exception {
        try {
            if (map != null)
                return data.query(sql, map, mapper);
            else
                return data.query(sql, mapper);
        } catch (Exception e) {
            throw new Exception("", e);//TODO add message of exception
        }
    }

    public <T> T getRow(String sql, RowMapper<T> mapper) throws Exception {
        return getRow(sql, null, mapper);
    }
    public <T> T getRow(String sql, MapSqlParameterSource map, RowMapper<T> mapper) throws Exception {
        try {
            return data.queryForObject(
                    sql,
                    map,
                    mapper
            );
        } catch (DataAccessException e) {
            throw new Exception("", e);//TODO add message of exception
        }
    }

    public void execSQL(String sql) throws Exception {
        try {
            data.update(
                    sql,
                    new MapSqlParameterSource()
            );
        } catch (DataAccessException e) {
            throw new Exception("", e);//TODO add message of exception
        }
    }
    public void execSQL(String sql, MapSqlParameterSource map) throws Exception {
        try {
            data.update(
                    sql,
                    map
            );
        } catch (DataAccessException e) {
            throw new Exception("", e);//TODO add message of exception
        }
    }

    public <T> T execSQL(String sql, Class<T> c) throws Exception {
        return execSQL(sql, new MapSqlParameterSource(), c);
    }
    public <T> T execSQL(String sql, MapSqlParameterSource map, Class<T> c) throws Exception {
        try {
            return data.queryForObject(
                    sql,
                    map,
                    c
            );
        } catch (DataAccessException e) {
            return null;

        }
    }

}
