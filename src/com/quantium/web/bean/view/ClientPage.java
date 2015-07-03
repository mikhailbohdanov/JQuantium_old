package com.quantium.web.bean.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by FREEMAN on 16.12.14.
 */
public class ClientPage {
    private int pageId;
    private String title;
    private String keywords;
    private String descriptions;

    private ArrayList<Group> groups;
    private HashMap<Integer, Group> groupsById  = new HashMap<Integer, Group>();

    private ArrayList<Menu> menus;
    private HashMap<Integer, Menu> menusById    = new HashMap<Integer, Menu>();
    private HashMap<String, Menu> menusByName   = new HashMap<String, Menu>();
    private HashSet<Integer> menuPool           = new HashSet<Integer>();

    public int getPageId() {
        return pageId;
    }
    public ClientPage setPageId(int pageId) {
        this.pageId = pageId;
        return this;
    }

    public String getTitle() {
        return title;
    }
    public ClientPage setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getKeywords() {
        return keywords;
    }
    public ClientPage setKeywords(String keywords) {
        this.keywords = keywords;
        return this;
    }

    public String getDescriptions() {
        return descriptions;
    }
    public ClientPage setDescriptions(String descriptions) {
        this.descriptions = descriptions;
        return this;
    }

    public ArrayList<Group> getGroups() {
        return groups;
    }
    public ClientPage setGroups(ArrayList<Group> groups) {
        this.groups = groups;

        if (this.groups != null) {
            this.groupsById.clear();

            for (Group group : this.groups)
                this.groupsById.put(group.getGroupId(), group);
        }

        return this;
    }
    public HashMap<Integer, Group> getGroupsMap() {
        return groupsById;
    }
    public Group getGroup(int groupId) {
        return groupsById.get(groupId);
    }

    public ClientPage setMenus(ArrayList<Menu> menus) {
        this.menus = menus;

        if (this.menus != null) {
            this.menusById.clear();
            this.menusByName.clear();

            for (Menu menu : this.menus) {
                this.menusById.put(menu.getMenuId(), menu);
                this.menusByName.put(menu.getName(), menu);
            }
        }

        return this;
    }
    public Menu getMenu(int menuId) {
        return menusById.get(menuId);
    }
    public Menu getMenu(String name) {
        return menusByName.get(name);
    }

    public ClientPage addMenuPool(int menuId) {
        this.menuPool.add(menuId);
        return this;
    }
    public HashSet<Integer> getMenuPool() {
        return menuPool;
    }

}
