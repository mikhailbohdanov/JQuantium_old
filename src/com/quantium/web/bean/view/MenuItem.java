package com.quantium.web.bean.view;

import com.quantium.web.controller.PageGenerator;
import com.quantium.web.errors.exceptions.view.PageNotFoundException;
import com.quantium.web.service.View;

import java.util.ArrayList;

/**
 * Created by FREEMAN on 11.02.15.
 */
public class MenuItem {
    private int itemId;
    private int menuId;
    private int parentId;
    private int pageId;
    private String parameters;
    private String value;
    private int order;

    private ArrayList<MenuItem> children;

    public int getItemId() {
        return itemId;
    }
    public MenuItem setItemId(int itemId) {
        this.itemId = itemId;
        return this;
    }

    public int getMenuId() {
        return menuId;
    }
    public MenuItem setMenuId(int menuId) {
        this.menuId = menuId;
        return this;
    }

    public int getParentId() {
        return parentId;
    }
    public MenuItem setParentId(int parentId) {
        this.parentId = parentId;
        return this;
    }

    public int getPageId() {
        return pageId;
    }
    public MenuItem setPageId(int pageId) {
        this.pageId = pageId;
        return this;
    }

    public String getParameters() {
        return parameters;
    }
    public MenuItem setParameters(String parameters) {
        this.parameters = parameters;
        return this;
    }

    public String getHref() {
        StringBuilder out = new StringBuilder();

        if (pageId > 0)
            try {
                out.append(View.getPage(pageId).getUrl());
            } catch (PageNotFoundException e) {}

        if (parameters != null && !parameters.isEmpty())
            out.append("?").append(parameters);

        return out.toString();
    }

    public boolean isLink() {
        return !(pageId == 0 && (parameters == null || parameters.isEmpty()));
    }
    public boolean isCurrentPage(PageContext PC) {
        int tmp;

        if (pageId == 0 && (parameters == null || parameters.isEmpty()))
            return false;

        if (pageId == 0 || PC.getPage() == null || (tmp = PC.getPage().getPageId()) == 0 || tmp != pageId)
            return false;

        if (parameters == null || parameters.isEmpty())
            return true;
        else
            return PC.getUrl().matchSearch(parameters);
    }

    public String getValue() {
        if (value != null)
            return value;

        return getHref();
    }
    public MenuItem setValue(String value) {
        this.value = value;
        return this;
    }

    public int getOrder() {
        return order;
    }
    public MenuItem setOrder(int order) {
        this.order = order;
        return this;
    }

    public ArrayList<MenuItem> getChildren() {
        return children;
    }
    public MenuItem setChildren(ArrayList<MenuItem> children) {
        this.children = children;
        children.sort(Menu.ITEM_COMPARATOR);

        return this;
    }
}
