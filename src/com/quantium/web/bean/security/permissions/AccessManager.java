package com.quantium.web.bean.security.permissions;

import java.util.*;

/**
 * Created by FREEMAN on 23.02.15.
 */
public class AccessManager {
    private HashMap<String, HashSet<String>> access      = new HashMap<String, HashSet<String>>();

    public boolean has(String accessName, String accessValue) {
        return access.containsKey(accessName) && access.get(accessName).contains(accessValue);
    }
    public boolean hasAny(String accessName) {
        return access.containsKey(accessName) && access.get(accessName).size() > 0;
    }
    public boolean hasAny(String accessName, String... values) {
        HashSet<String> _values;

        if (access.containsKey(accessName)) {
            _values = access.get(accessName);

            for (String value : values)
                if (_values.contains(value))
                    return true;
        }

        return false;
    }
    public boolean hasAll(String accessName, String... values) {
        return access.containsKey(accessName) && access.get(accessName).containsAll(Arrays.asList(values));
    }

    public AccessManager addAccess(String accessName, HashSet<String> values) {
        access.put(accessName, values);
        return this;
    }
}
