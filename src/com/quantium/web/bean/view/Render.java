package com.quantium.web.bean.view;

import java.util.HashMap;

/**
 * Created by FREEMAN on 21.10.14.
 */
public class Render {
    public static enum UAType {

    }

    private int id;
    private String name;
    private UAType uaType;
    private String fullPath;
    private String ajaxPath;
    private HashMap<String, String> headers;//TODO

    public int getId() {
        return id;
    }
    public Render setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }
    public Render setName(String name) {
        this.name = name;
        return this;
    }

    public UAType getUaType() {
        return uaType;
    }
    public Render setUaType(UAType uaType) {
        this.uaType = uaType;
        return this;
    }

    public String getFullPath() {
        return fullPath;
    }
    public Render setFullPath(String fullPath) {
        this.fullPath = fullPath;
        return this;
    }

    public String getAjaxPath() {
        return ajaxPath;
    }
    public Render setAjaxPath(String ajaxPath) {
        this.ajaxPath = ajaxPath;
        return this;
    }
}
