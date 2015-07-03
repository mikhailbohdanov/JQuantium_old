package com.quantium.web.bean.view;

import java.util.ArrayList;

/**
 * Created by Михаил on 19.09.14.
 */
public class Group {
    public static enum Type {
        CONTENT_FULL,
        CONTENT,
        SIDEBAR_LEFT,
        SIDEBAR_RIGHT
    }

    private int groupId;
    private int parentId;
    private Type type;

    private Group sidebar;
    private ArrayList<Module> modules;

    public int getGroupId() {
        return groupId;
    }
    public Group setGroupId(int groupId) {
        this.groupId = groupId;
        return this;
    }

    public int getParentId() {
        return parentId;
    }
    public Group setParentId(int parentId) {
        this.parentId = parentId;
        return this;
    }

    public Type getType() {
        return type;
    }
    public Group setType(Type type) {
        this.type = type;
        return this;
    }
    public Group setType(String type) {
        this.type = Type.valueOf(type);
        return this;
    }

    public Group getSidebar() {
        return sidebar;
    }
    public Group setSidebar(Group sidebar) {
        this.sidebar = sidebar;
        return this;
    }

    public ArrayList<Module> getModules() {
        return modules;
    }
    public Group setModules(ArrayList<Module> modules) {
        this.modules = modules;
        return this;
    }
    public Group addModule(Module module) {
        if (modules == null)
            modules = new ArrayList<Module>();

        modules.add(module);

        return this;
    }
}
