package com.quantium.web.bean.view;

/**
 * Created by Михаил on 17.09.14.
 */
public class PagePasswordUserLink {
    public static enum Type {
        NONE,
        USER,
        MODERATOR,
        ADMIN
    }

    private int id;
    private int passwordId;
    private int userId;
    private Type type;



    public int getId() {
        return id;
    }
    public PagePasswordUserLink setId(int id) {
        this.id = id;
        return this;
    }

    public int getPasswordId() {
        return passwordId;
    }
    public PagePasswordUserLink setPasswordId(int passwordId) {
        this.passwordId = passwordId;
        return this;
    }

    public int getUserId() {
        return userId;
    }
    public PagePasswordUserLink setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public Type getType() {
        return type;
    }
    public PagePasswordUserLink setType(Type type) {
        this.type = type;
        return this;
    }
    public PagePasswordUserLink setType(String type) {
        this.type = Type.valueOf(type);
        return this;
    }
}
