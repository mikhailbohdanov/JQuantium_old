package com.quantium.web.bean.view;

import java.util.HashMap;

/**
 * Created by Михаил on 17.09.14.
 */
public class PagePassword {
    public static enum Access {
        ALL,
        AUTHORITY,
        ONLY
    }

    private int id;
    private int pageId;
    private Access access;
    private String password;

    private HashMap<Integer, PagePasswordUserLink.Type> users;

    public int getId() {
        return id;
    }
    public PagePassword setId(int id) {
        this.id = id;
        return this;
    }

    public int getPageId() {
        return pageId;
    }
    public PagePassword setPageId(int pageId) {
        this.pageId = pageId;
        return this;
    }

    public Access getAccess() {
        return access;
    }
    public PagePassword setAccess(Access access) {
        this.access = access;
        return this;
    }
    public PagePassword setAccess(String access) {
        this.access = Access.valueOf(access);
        return this;
    }

    public String getPassword() {
        return password;
    }
    public PagePassword setPassword(String password) {
        this.password = password;
        return this;
    }

    public PagePasswordUserLink.Type userAccess(int userId) {
        if (users.containsKey(userId))
            return users.get(userId);
        else
            return null;
    }
    public boolean hasUser(int userId) {
        return users.containsKey(userId);
    }
    public PagePassword addUser(int userId, PagePasswordUserLink.Type type) {
        users.put(userId, type);
        return this;
    }
}
