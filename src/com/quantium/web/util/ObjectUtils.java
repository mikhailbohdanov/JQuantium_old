package com.quantium.web.util;

import com.quantium.web.dao.annotation.Row;
import org.json.simple.parser.JSONParser;

import javax.servlet.ServletRequest;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Author FREEMAN
 * Created 14.11.14
 */
public class ObjectUtils {
    private final static Map<Class<?>, Class<?>> classes        = new HashMap<Class<?>, Class<?>>();
    static {
        classes.put(boolean.class, Boolean.class);
        classes.put(byte.class, Byte.class);
        classes.put(short.class, Short.class);
        classes.put(char.class, Character.class);
        classes.put(int.class, Integer.class);
        classes.put(long.class, Long.class);
        classes.put(float.class, Float.class);
        classes.put(double.class, Double.class);
    }

    public static final JSONParser JSON_PARSER                  = new JSONParser();

    public static <T> T getFromRequest(Class<T> _class, ServletRequest request) {
        Object object;

        try {
            object = _class.newInstance();
        } catch (Exception e){
            return null;
        }

        return getFromRequest((T) object, request);
    }
    public static <T> T getFromRequest(T obj, ServletRequest request) {
        if (obj == null || request == null)
            return null;

        Class _class    = obj.getClass();
        Field[] fields  = _class.getDeclaredFields();

        Row row;
        String value;
        Class __class;
        for (Field field : fields) {
            row = field.getDeclaredAnnotation(Row.class);

            if (row == null)
                continue;

            if ((value = request.getParameter(row.name())) != null) {
                field.setAccessible(true);

                try {
                    __class = field.getType();

                    if (classes.containsKey(__class)) {
                        field.set(
                                obj,
                                classes.get(__class).getConstructor(String.class).newInstance(value)
                        );
                    } else
                        field.set(obj, field.getType().cast(value));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return obj;
    }


}
