package com.quantium.web.service;

import com.quantium.web.bean.view.*;
import com.quantium.web.dao.UtilityDAO;
import com.quantium.web.dao.ViewDAO;
import com.quantium.web.dao.ViewIO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Михаил on 03.09.14.
 */
@Service
public class ViewService implements ViewIO {

    @Autowired
    private ViewDAO viewDAO;

    @Autowired
    private UtilityDAO utilityDAO;

    @Cacheable(value = "pageList")
    public ArrayList<Page> getPages() {
        try {
            return (ArrayList<Page>) utilityDAO.getRowList(
                    PAGE_GET_LIST_ALL,
                    PAGE
            );
        } catch (Exception e) {
            return new ArrayList<Page>();
        }
    }

//    @Cacheable("clientPage")
    public ClientPage getClientPage(int pageId, String languageCode) {
        ClientPage clientPage = null;

        try {
            clientPage = utilityDAO.getRow(
                    CLIENT_PAGE_SPECIFIC.toString(languageCode),
                    (new MapSqlParameterSource())
                            .addValue("pageId", pageId),
                    CLIENT_PAGE
            );
        } catch (Exception e) {
            return null;
        }

        try {
            clientPage.setGroups((ArrayList<Group>) utilityDAO.getRowList(
                    GROUP_GET_LIST_OF_PAGE,
                    (new MapSqlParameterSource())
                            .addValue("pageId", pageId),
                    GROUP
            ));
        } catch (Exception e) {
            clientPage.setGroups(new ArrayList<Group>());
        }

        ArrayList<Module> modules;
        try {
            modules = (ArrayList<Module>) utilityDAO.getRowList(
                    MODULE_GET_LIST_OF_PAGE.toString(languageCode),
                    (new MapSqlParameterSource())
                            .addValue("pageId", pageId),
                    MODULE
            );
        } catch (Exception e) {
            modules = new ArrayList<Module>();
        }

        HashMap<Integer, Group> groups = clientPage.getGroupsMap();
        int groupId;
        Group group;
        Group sidebar;
        Group.Type type;

        for (Map.Entry<Integer, Group> entry : groups.entrySet()) {
            groupId     = entry.getKey();
            group       = entry.getValue();
            type        = group.getType();

            sidebar     = group.getSidebar();

            for (Module module : modules) {
                if (module.getGroupId() == groupId) {
                    if ((type == Group.Type.CONTENT || type == Group.Type.CONTENT_FULL) && module.getOrder() > 0)
                        group.addModule(module);
                    else if ((type == Group.Type.SIDEBAR_LEFT || type == Group.Type.SIDEBAR_RIGHT) && module.getOrder() < 0) {
                        if (sidebar == null)
                            group.setSidebar(sidebar = new Group());

                        sidebar.addModule(module);
                    } else
                        group.addModule(module);
                }
            }
        }

        return clientPage;
    }


//    @Cacheable("menuById")
    public Menu getMenu(int menuId, String language) {
        Menu menu;

        try {
            menu = utilityDAO.getRow(
                    MENU_GET_SPECIFIC_BY_ID,
                    (new MapSqlParameterSource())
                            .addValue("menuId", menuId),
                    MENU
            );
        } catch (Exception e) {
            return null;
        }

        return fillMenu(menu, language);
    }
//    @Cacheable("menuByName")
    public Menu getMenu(String menuName, String language) {
        Menu menu;

        try {
            menu = utilityDAO.getRow(
                    MENU_GET_SPECIFIC_BY_NAME,
                    (new MapSqlParameterSource())
                            .addValue("name", menuName),
                    MENU
            );
        } catch (Exception e) {
            return null;
        }

        return fillMenu(menu, language);
    }

    private Menu fillMenu(Menu menu, String language) {
        ArrayList<MenuItem> items;
        try {
            items = (ArrayList<MenuItem>) utilityDAO.getRowList(
                    MENU_ITEM_LIST_OF_MENU.toString(language),
                    (new MapSqlParameterSource())
                            .addValue("menuId", menu.getMenuId()),
                    MENU_ITEM
            );
        } catch (Exception e) {
            return menu;
        }

        return menu.setItems(iterateMenuItem(0, items));
    }
    private ArrayList<MenuItem> iterateMenuItem(int parentId, ArrayList<MenuItem> items) {
        ArrayList<MenuItem> _items = new ArrayList<MenuItem>();

        MenuItem item;
        Iterator<MenuItem> it = items.iterator();
        while (it.hasNext()) {
            item = it.next();

            if (parentId == item.getParentId() && item.getItemId() != item.getParentId()) {
                _items.add(item);
                item.setChildren(iterateMenuItem(item.getItemId(), items));
            }
        }

        return _items;
    }

}
