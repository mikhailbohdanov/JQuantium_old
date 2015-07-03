package com.quantium.web.bean.view;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by FREEMAN on 26.01.15.
 */
public class Menu {
    public static final Comparator<MenuItem> ITEM_COMPARATOR    = new Comparator<MenuItem>() {
        @Override
        public int compare(MenuItem o1, MenuItem o2) {
            int order1 = o1.getOrder(), order2 = o2.getOrder();

            if (order1 > order2)
                return 1;
            else if (order1 < order2)
                return -1;
            else
                return 0;
        }
    };

    private int menuId;
    private String name;

    ArrayList<MenuItem> items;

    public int getMenuId() {
        return menuId;
    }
    public Menu setMenuId(int menuId) {
        this.menuId = menuId;
        return this;
    }

    public String getName() {
        return name;
    }
    public Menu setName(String name) {
        this.name = name;
        return this;
    }

    public ArrayList<MenuItem> getItems() {
        return items;
    }
    public Menu setItems(ArrayList<MenuItem> items) {
        this.items = items;
        items.sort(ITEM_COMPARATOR);

        return this;
    }
}
