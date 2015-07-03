package com.quantium.web.bean.sql;

import com.quantium.web.util.Primitives;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

/**
 * Created by FREEMAN on 03.12.14.
 */
public class SQLUtils {

    //TODO finish this function
    public static Object escape(Object object) {
        if (object instanceof Byte
                || object instanceof Short
                || object instanceof Integer
                || object instanceof Long
                || object instanceof Float
                || object instanceof Double
                || object instanceof Boolean) {
            return object;
        } else if (object instanceof String
                || object instanceof Time
                || object instanceof Date
                || object instanceof Timestamp) {
            return "'" + Primitives.replaceAll(String.valueOf(object), "'", "\'") + "'";
        }

        return object;
    }

    public static Map<String, Object> escapeAll(Map<String, Object> map) {
        if (map != null)
            for (Map.Entry<String, Object> entry : map.entrySet())
                entry.setValue(escape(entry.getValue()));

        return map;
    }

}
