package com.quantium.web.service;

import com.quantium.web.bean.core.StringTemplate;
import com.quantium.web.dao.DAO;
import com.quantium.web.dao.annotation.Index;
import com.quantium.web.dao.annotation.Row;
import com.quantium.web.dao.annotation.Table;
import com.quantium.web.util.Primitives;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.Time;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by FREEMAN on 24.02.15.
 */
@Service
public class Util {
    public static String generateCreate(Object obj, MapSqlParameterSource map) {//TODO must generate without empty values (if index has value, must use it)
        if (obj == null || map == null)
            return null;

        Class _class    = obj.getClass();
        Annotation _ann;
        String tableName = null, indexRow = null, _row;
        LinkedHashMap<String, Object> rows;

        _ann            = _class.getAnnotation(Table.class);
        if (_ann != null)
            tableName   = ((Table) _ann).value();

        if (tableName == null)
            return null;

        rows            = new LinkedHashMap<String, Object>();
        for (Field field : _class.getDeclaredFields()) {
            _ann        = field.getAnnotation(Row.class);

            if (_ann == null || field.getDeclaredAnnotation(Index.class) != null)
                continue;

            _row        = ((Row) _ann).name();

            try {
                field.setAccessible(true);
                rows.put(_row, field.get(obj));
            } catch (IllegalAccessException e) {}
        }

        if (rows.size() == 0)
            return null;

        StringBuilder out   = new StringBuilder();
        int length          = rows.size();

        out.append("INSERT INTO `").append(tableName).append("` (");

        String key;
        StringBuilder keys      = new StringBuilder();
        StringBuilder values    = new StringBuilder();
        for (Map.Entry<String, Object> entry : rows.entrySet()) {
            key     = entry.getKey();

            keys.append("`").append(key).append("`");
            values.append(":").append(key);

            map.addValue(key, entry.getValue());

            if (length-- > 1) {
                keys.append(", ");
                values.append(", ");
            }
        }

        out.append(keys).append(") VALUES (").append(values).append(")");

        return out.toString();
    }

    public static String generateUpdate(Object obj, MapSqlParameterSource map) {
        if (obj == null || map == null)
            return null;

        Class _class    = obj.getClass();
        Annotation _ann;
        String tableName = null, indexRow = null, _row;
        HashMap<String, Object> rows;

        _ann            = _class.getAnnotation(Table.class);
        if (_ann != null)
            tableName   = ((Table) _ann).value();

        if (tableName == null)
            return null;

        rows            = new HashMap<String, Object>();
        for (Field field : _class.getDeclaredFields()) {
            _ann        = field.getAnnotation(Row.class);

            if (_ann == null)
                continue;

            _row        = ((Row) _ann).name();

            try {
                field.setAccessible(true);
                rows.put(_row, field.get(obj));
            } catch (IllegalAccessException e) {}

            if (field.getDeclaredAnnotation(Index.class) != null)
                indexRow    = _row;
        }

        if (indexRow == null || rows.size() == 0)
            return null;

        StringBuilder out   = new StringBuilder();
        int length          = rows.size() - 1;

        out.append("UPDATE `").append(tableName).append("` SET ");

        for (Map.Entry<String, Object> entry : rows.entrySet()) {
            _row    = entry.getKey();
            map.addValue(_row, entry.getValue());

            if (!_row.equals(indexRow)) {
                out.append("`").append(_row).append("` = :").append(_row);

                if (length-- > 1)
                    out.append(", ");
            }
        }

        out.append(" WHERE `").append(indexRow).append("` = :").append(indexRow);

        return out.toString();
    }

    public static String generateCreateTable(Class _class) {
        if (_class == null)
            return null;

        Annotation _ann;
        Table table;
        Row row;

        StringBuilder builder;
        String name, tableName, tmpStr;
        int tmpInt;
        ArrayList<String> rows, indexes, tmpALS;
        HashMap<String, ArrayList<String>> unique;

        _ann            = _class.getAnnotation(Table.class);
        if (_ann == null)
            return null;

        table           = (Table) _ann;
        tableName       = table.value();

        rows            = new ArrayList<String>();
        indexes         = new ArrayList<String>();
        unique          = new HashMap<String, ArrayList<String>>();
        for (Field field : _class.getDeclaredFields()) {
            _ann        = field.getAnnotation(Row.class);

            if (_ann == null)
                continue;

            builder     = new StringBuilder();

            row         = (Row) _ann;
            name        = row.name();

            builder
                    .append("`")
                    .append(name)
                    .append("`");

            _class      = field.getType();

            if (boolean.class == _class || Boolean.class == _class)
                builder
                        .append(" tinyint(1)");
            else if (int.class == _class || Integer.class == _class) {
                builder
                        .append(" int(")
                        .append(row.length())
                        .append(")");

                if (row.unsigned())
                    builder.append(" unsigned");

                if (row.notNull())
                    builder.append(" NOT");

                builder.append(" NULL");

                if (row.autoIncrement())
                    builder.append(" AUTO_INCREMENT");
            } else if (String.class == _class) {
                tmpInt  = row.length();

                if (tmpInt < 4096)
                    builder.append(" varchar(").append(tmpInt).append(")");
                else
                    builder.append(" text");

                if (row.notNull())
                    builder.append(" NOT");

                builder.append(" NULL");
            } else if (Timestamp.class == _class) {
                builder
                        .append(" timestamp");

                if (row.notNull())
                    builder.append(" NOT");

                builder.append(" NULL");
            } else if (Date.class == _class) {
                builder
                        .append(" date");

                if (row.notNull())
                    builder.append(" NOT");

                builder.append(" NULL");
            } else if (Time.class == _class) {
                builder
                        .append(" time");

                if (row.notNull())
                    builder.append(" NOT");

                builder.append(" NULL");
            } else if (_class.isEnum()) {
                builder
                        .append("enum(")
                        .append(Primitives.slice(", ", _class.getEnumConstants(), "'", "'"))
                        .append(")");

                if (row.notNull())
                    builder.append(" NOT");

                builder.append(" NULL");
            }

            if (!row.defaultValue().isEmpty())
                builder
                        .append(" DEFAULT '")
                        .append(row.defaultValue())
                        .append("'");

            rows.add(builder.toString());

            if (row.primary() || field.getAnnotation(Index.class) != null)
                indexes.add(name);

            if (!(tmpStr = row.unique()).isEmpty()) {
                if (unique.containsKey(tmpStr))
                    tmpALS  = unique.get(tmpStr);
                else {
                    tmpALS  = new ArrayList<String>();
                    unique.put(tmpStr, tmpALS);
                }

                tmpALS.add(name);
            }
        }

        builder     = new StringBuilder();
        builder
                .append("CREATE TABLE `")
                .append(tableName)
                .append("` (")
                .append(Primitives.slice(", ", rows));

        builder
                .append(", PRIMARY KEY (").append(Primitives.slice(", ", indexes, "`", "`")).append(")");

        if ((tmpInt = unique.size()) > 0) {
            builder
                    .append(", ");

            for (Map.Entry<String, ArrayList<String>> entry : unique.entrySet()) {
                builder
                        .append("UNIQUE KEY `")
                        .append(entry.getKey())
                        .append("` (")
                        .append(Primitives.slice(", ", entry.getValue(), "`", "`"))
                        .append(")");

                if (--tmpInt > 0)
                    builder.append(",");
            }
        }

        builder
                .append(") ENGINE=")
                .append(table.engine())
                .append(" DEFAULT CHARSET=")
                .append(table.charset());

        return builder.toString();
    }



    /* - - - Count rows - - - */
    public static final StringTemplate COUNT_IN_TABLE               = new StringTemplate("SELECT COUNT(*) FROM `{tableName}`");
    public static final StringTemplate COUNT_IN_TABLE_BY_ROW        = new StringTemplate("SELECT COUNT(*) FROM `{tableName}` WHERE `{rowName}` = :value");

    /* - - - Get rows - - - */
    public static final StringTemplate GET_ROWS_ALL                 = new StringTemplate("SELECT * FROM `{tableName}`");
    public static final StringTemplate GET_ROW_BY_CONDITION         = new StringTemplate("SELECT * FROM `{tableName}` WHERE `{rowName}` = :value");

    /* - - - Delete rows - - - */
    public static final StringTemplate DELETE_IN_TABLE              = new StringTemplate("DELETE FROM `{tableName}` WHERE `{rowName}` = :value");

    @Autowired
    private DAO dao;


    public int count(String tableName) {
        if (tableName != null)
            try {
                return dao.exec(
                        COUNT_IN_TABLE.toString(tableName),
                        null,
                        Integer.class
                );
            } catch (Exception e) {}

        return 0;
    }
    public int count(String tableName, String rowName, Object value) {
        if (tableName != null && rowName != null && value != null)
            try {
                return dao.exec(
                        COUNT_IN_TABLE_BY_ROW.toString(tableName, rowName),
                        (new MapSqlParameterSource())
                                .addValue("value", value),
                        Integer.class
                );
            } catch (Exception e) {}

        return 0;
    }

    public boolean hasValue(String tableName, String rowName, Object value) {
        return count(tableName, rowName, value) > 0;
    }

    public int[] createRowList(Object... objects) {
        if (objects == null || objects.length == 0)
            return null;

        int[] ids   = new int[objects.length];
        int current = 0;

        for (Object object : objects)
            ids[current++]  = createRow(object);

        return ids;
    }
    public int createRow(Object object) {
        if (object == null)
            return 0;

        MapSqlParameterSource map   = new MapSqlParameterSource();

        String query    = generateCreate(object, map);

        int objectId    = 0;
        try {
            objectId    = dao.createRow(
                    query,
                    map
            );
        } catch (Exception e) {
            return 0;
        }

        //TODO must set indexID to object

        return objectId;
    }

    public <T> List<T> getRowList(String tableName, RowMapper<T> mapper) {
        try {
            return dao.getRowList(
                    GET_ROWS_ALL.toString(tableName),
                    new MapSqlParameterSource(),
                    mapper
            );
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
    public <T> List<T> getRowList(String tableName, String rowName, Object value, RowMapper<T> mapper) {
        try {
            return dao.getRowList(
                    GET_ROW_BY_CONDITION.toString(tableName, rowName),
                    (new MapSqlParameterSource())
                            .addValue("value", value),
                    mapper
            );
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
    public <T> T getRow(String tableName, String rowName, Object value, RowMapper<T> mapper) {
        try {
            return dao.getRow(
                    GET_ROW_BY_CONDITION.toString(tableName, rowName),
                    (new MapSqlParameterSource())
                            .addValue("value", value),
                    mapper
            );
        } catch (Exception e) {
            return null;
        }
    }

    public void updateRowList(Object... objects) {
        if (objects == null || objects.length == 0)
            return;

        for (Object object : objects)
            updateRow(object);
    }
    public void updateRow(Object object) {
        if (object == null)
            return;

        MapSqlParameterSource map   = new MapSqlParameterSource();

        String query    = generateUpdate(object, map);

        try {
            dao.exec(
                    query,
                    map
            );
        } catch (Exception e) {}
    }

    public void removeFrom(String tableName, String rowName, Object value) {
        if (tableName == null || rowName == null || value == null)
            return;

        try {
            dao.exec(
                    DELETE_IN_TABLE.toString(tableName, rowName),
                    (new MapSqlParameterSource())
                            .addValue("value", value)
            );
        } catch (Exception e) {}
    }

    public void createTable(Class _class, Object object) {
        if (_class == null)
            return;

        StringTemplate query    = new StringTemplate(generateCreateTable(_class));

        query.toStringObject(object);
        try {
            dao.exec(
                    query.toStringObject(object),
                    new MapSqlParameterSource()
            );
        } catch (Exception e) {}
    }
}
