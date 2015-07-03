package com.quantium.web.bean.security;

import java.sql.Timestamp;

/**
 * Created by FREEMAN on 25.02.15.
 */
public class UserToken {
    public static enum Type {
        PHONE,
        EMAIL,
        PASSWORD
    }

    private int tokenId;
    private int userId;
    private Type type;
    private String token;
    private Timestamp expired;

    public int getTokenId() {
        return tokenId;
    }
    public UserToken setTokenId(int tokenId) {
        this.tokenId = tokenId;
        return this;
    }

    public int getUserId() {
        return userId;
    }
    public UserToken setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public Type getType() {
        return type;
    }
    public UserToken setType(Type type) {
        this.type = type;
        return this;
    }
    public UserToken setType(String type) {
        this.type = Type.valueOf(type);
        return this;
    }

    public String getToken() {
        return token;
    }
    public UserToken setToken(String token) {
        this.token = token;
        return this;
    }

    public Timestamp getExpired() {
        return expired;
    }
    public UserToken setExpired(Timestamp expired) {
        this.expired = expired;
        return this;
    }
}
