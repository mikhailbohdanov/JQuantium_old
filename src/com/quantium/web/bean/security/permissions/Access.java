package com.quantium.web.bean.security.permissions;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by FREEMAN on 23.02.15.
 */
public class Access {
    private int accessId;
    private String name;

    public int getAccessId() {
        return accessId;
    }
    public Access setAccessId(int accessId) {
        this.accessId = accessId;
        return this;
    }

    public String getName() {
        return name;
    }
    public Access setName(String name) {
        this.name = name;
        return this;
    }
}
