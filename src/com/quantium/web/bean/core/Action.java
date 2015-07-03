package com.quantium.web.bean.core;

/**
 * Created by FREEMAN on 10.02.15.
 */
public class Action {
    public static enum Type {
        CALL_SQL,
    }

    private int actionId;
    private String name;
    private Type type;
    private String objectClass;
    private String action;

    public int getActionId() {
        return actionId;
    }
    public Action setActionId(int actionId) {
        this.actionId = actionId;
        return this;
    }

    public String getName() {
        return name;
    }
    public Action setName(String name) {
        this.name = name;
        return this;
    }

    public Type getType() {
        return type;
    }
    public Action setType(Type type) {
        this.type = type;
        return this;
    }
    public Action setType(String type) {
        this.type = Type.valueOf(type);
        return this;
    }

    public String getObjectClass() {
        return objectClass;
    }
    public Action setObjectClass(String objectClass) {
        this.objectClass = objectClass;
        return this;
    }

    public String getAction() {
        return action;
    }
    public Action setAction(String action) {
        this.action = action;
        return this;
    }
}
