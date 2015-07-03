package com.quantium.web.bean.core;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by FREEMAN on 10.02.15.
 */
public class StringTemplate {
    protected static final String keyStart      = "{";
    protected static final String keyEnd        = "}";
    protected static final int keyStartLength   = keyStart.length();
    protected static final int keyEndLength     = keyEnd.length();
    protected static final Pattern keyPattern   = Pattern.compile("[a-zA-Z0-9]+");

    protected String original;
    protected LinkedHashMap<String, Integer> keys;
    protected StringBuilder pattern;

    protected boolean isPattern                 = false;

    protected StringTemplate() {}

    public StringTemplate(String original) {
        if (original == null)
            original            = "";

        this.original           = original;

        StringBuilder _pattern  = new StringBuilder(original);
        LinkedHashMap<String, Integer> _keys    = new LinkedHashMap<String, Integer>();

        String key;
        int _start, _end, _offset = 0;
        while ((_start = _pattern.indexOf(keyStart, _offset)) >= 0) {
            _end = _pattern.indexOf(keyEnd, _start + keyStartLength);
            key = _pattern.substring(_start + keyStartLength, _end);

            if (keyPattern.matcher(key).matches()) {
                _keys.put(key, _start);
                _pattern.replace(_start, _end + keyEndLength, "");
            } else
                _offset = _end;
        }

        if (_keys.size() > 0) {
            this.isPattern  = true;
            this.keys       = _keys;
            this.pattern    = _pattern;
        }
    }

    public String getOriginal() {
        return original;
    }

    public String toString() {
        if (!isPattern)
            return original;

        return pattern.toString();
    }
    public String toStringObject(Object object) {
        if (!isPattern)
            return original;

        if (object == null)
            return pattern.toString();

        StringBuilder out   = new StringBuilder(pattern);

        Class _class    = object.getClass();

        Field field;
        String key, value;
        int offset  = 0;
        for (Map.Entry<String, Integer> entry : this.keys.entrySet()) {
            key    = entry.getKey();

            try {
                field   = _class.getDeclaredField(key);

                field.setAccessible(true);
                value   = String.valueOf(field.get(object));
                field.setAccessible(false);

                out.insert(entry.getValue() + offset, value);
                offset += value.length();
            } catch (NoSuchFieldException e) {} catch (IllegalAccessException e) {}
        }

        return out.toString();
    }
    public String toString(Map keys) {
        if (!isPattern)
            return original;

        if (keys == null)
            return pattern.toString();

        StringBuilder out = new StringBuilder(pattern);

        int offset = 0;
        String key;
        Object _data;
        for (Map.Entry<String, Integer> entry : this.keys.entrySet())
            if (keys.containsKey(key = entry.getKey())) {
                _data = keys.get(key);

                out.insert(entry.getValue() + offset, _data);

                offset += (String.valueOf(_data)).length();
            }

        return out.toString();
    }
    public String toString(String... elements) {
        if (!isPattern)
            return original;

        if (elements == null)
            return pattern.toString();

        StringBuilder out = new StringBuilder(pattern);

        int offset = 0;
        int i = 0;
        Object _data;
        for (Map.Entry<String, Integer> entry : keys.entrySet()) {
            _data = elements[i++];

            out.insert(entry.getValue() + offset, _data);

            offset += String.valueOf(_data).length();

            if (i >= elements.length)
                i = 0;
        }

        return out.toString();
    }
    public String toString(Integer... elements) {
        if (!isPattern)
            return original;

        if (elements == null)
            return pattern.toString();

        String[] _elements = new String[elements.length];

        for (int i = 0; i < elements.length; i++)
            _elements[i] = String.valueOf(elements[i]);

        return toString(_elements);
    }

}
