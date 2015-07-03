package com.quantium.web.bean.view;

import java.util.ArrayList;

/**
 * Created by Михаил on 22.08.14.
 */
public class Page {
    private int pageId;
    private String name;
    private String url;

    private ArrayList<PagePassword> passwords;

    public int getPageId() {
        return pageId;
    }
    public Page setPageId(int pageId) {
        this.pageId = pageId;
        return this;
    }

    public String getName() {
        return name;
    }
    public Page setName(String name) {
        this.name = name;
        return this;
    }

    public String getUrl() {
        return url;
    }
    public Page setUrl(String url) {
        this.url = url;
        return this;
    }

    public ArrayList<PagePassword> getPasswords() {
        return passwords;
    }
    public Page setPasswords(ArrayList<PagePassword> passwords) {
        this.passwords = passwords;
        return this;
    }

}
