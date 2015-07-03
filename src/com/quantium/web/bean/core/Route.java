package com.quantium.web.bean.core;

import java.util.HashMap;

/**
 * Created by FREEMAN on 26.01.15.
 */
public class Route {
    public static enum Type {
        PAGE,
        PAGE_PATTERN,
        USER,
        GROUP
    }

    private int routeId;
    private Type type;
    private String url;
    private int ownerId;
    private String parameters;

    private StringPattern urlPattern;

    public int getRouteId() {
        return routeId;
    }
    public Route setRouteId(int routeId) {
        this.routeId = routeId;
        return this;
    }

    public Type getType() {
        return type;
    }
    public Route setType(Type type) {
        this.type = type;
        return this;
    }
    public Route setType(String type) {
        this.type = Type.valueOf(type);
        return this;
    }

    public String getUrl() {
        return url;
    }
    public Route setUrl(String url) {
        this.url = url;
        urlPattern = new StringPattern(url);
        return this;
    }
    public boolean matchUrl(String url) {
        return urlPattern.match(url);
    }
    public HashMap<String, String> getParams(String url) {
        return urlPattern.getValues(url);
    }

    public int getOwnerId() {
        return ownerId;
    }
    public Route setOwnerId(int ownerId) {
        this.ownerId = ownerId;
        return this;
    }

    public String getParameters() {
        return parameters;
    }
    public Route setParameters(String parameters) {
        this.parameters = parameters;
        return this;
    }
}
