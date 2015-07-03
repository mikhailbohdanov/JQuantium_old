package com.quantium.web.bean.core.dao;

/**
 * Created by user on 13.05.2015.
 */
public class RowInstance {
    private String name;

    private int length;
    private boolean unsigned;
    private boolean autoIncrement;
    private boolean notNull;
    private String defaultValue;
    private boolean primary;
    private String unique;

    public RowInstance(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getLength() {
        return length;
    }
    public RowInstance setLength(int length) {
        this.length = length;
        return this;
    }

    public boolean isUnsigned() {
        return unsigned;
    }
    public RowInstance setUnsigned(boolean unsigned) {
        this.unsigned = unsigned;
        return this;
    }

    public boolean isAutoIncrement() {
        return autoIncrement;
    }
    public RowInstance setAutoIncrement(boolean autoIncrement) {
        this.autoIncrement = autoIncrement;
        return this;
    }

    public boolean isNotNull() {
        return notNull;
    }
    public RowInstance setNotNull(boolean notNull) {
        this.notNull = notNull;
        return this;
    }

    public String getDefaultValue() {
        return defaultValue;
    }
    public RowInstance setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    public boolean isPrimary() {
        return primary;
    }
    public RowInstance setPrimary(boolean primary) {
        this.primary = primary;
        return this;
    }

    public String getUnique() {
        return unique;
    }
    public RowInstance setUnique(String unique) {
        this.unique = unique;
        return this;
    }
}
