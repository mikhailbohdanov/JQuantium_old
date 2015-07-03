package com.quantium.web.bean.core.config;

/**
 * Created by FREEMAN on 26.11.14.
 */
public class Config {
    private int configId;
    private int groupId;
    private String key;
    private Object value;

    public int getConfigId() {
        return configId;
    }
    public Config setConfigId(int configId) {
        this.configId = configId;
        return this;
    }

    public int getGroupId() {
        return groupId;
    }
    public Config setGroupId(int groupId) {
        this.groupId = groupId;
        return this;
    }

    public String getKey() {
        return key;
    }
    public Config setKey(String key) {
        this.key = key;
        return this;
    }

    public Object getValue() {
        return value;
    }
    public Config setValue(Object value) {
        this.value = value;
        return this;
    }
}

