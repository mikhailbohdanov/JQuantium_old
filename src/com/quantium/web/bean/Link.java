package com.quantium.web.bean;

/**
 * Created by Михаил on 20.09.14.
 */
public class Link {
    private int parentId;
    private int childId;
    private int order;
    private boolean enable;

    public int getParentId() {
        return parentId;
    }
    public Link setParentId(int parentId) {
        this.parentId = parentId;
        return this;
    }

    public int getChildId() {
        return childId;
    }
    public Link setChildId(int childId) {
        this.childId = childId;
        return this;
    }

    public int getOrder() {
        return order;
    }
    public Link setOrder(int order) {
        this.order = order;
        return this;
    }

    public boolean isEnable() {
        return enable;
    }
    public Link setEnable(boolean enable) {
        this.enable = enable;
        return this;
    }
}
