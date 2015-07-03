package com.quantium.web.bean.security.permissions;

/**
 * Created by FREEMAN on 23.02.15.
 */
public class AccessValue {
    private int valueId;
    private int accessId;
    private String name;

    public int getValueId() {
        return valueId;
    }
    public AccessValue setValueId(int valueId) {
        this.valueId = valueId;
        return this;
    }

    public int getAccessId() {
        return accessId;
    }
    public AccessValue setAccessId(int accessId) {
        this.accessId = accessId;
        return this;
    }

    public String getName() {
        return name;
    }
    public AccessValue setName(String name) {
        this.name = name;
        return this;
    }
}
