package com.quantium.web.service;

import com.quantium.web.bean.core.StringTemplate;
import com.quantium.web.bean.view.ClientPage;
import com.quantium.web.bean.view.Group;
import com.quantium.web.bean.view.Module;
import com.quantium.web.bean.view.Page;
import com.quantium.web.dao.DAO;
import com.quantium.web.errors.exceptions.view.PageNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by FREEMAN on 25.02.15.
 */
@Service
public class View {
    private static final ArrayList<Page> pages              = new ArrayList<Page>();
    private static final TreeMap<String, Page> pagesByUrl   = new TreeMap<String, Page>();
    private static final HashMap<Integer, Page> pagesById   = new HashMap<Integer, Page>();

    // - - - Pages management
    public static void clearPages() {
        pages.clear();
        pagesById.clear();
        pagesByUrl.clear();
    }
    public static void addPage(Page page) {
        updatePage(page);

        pages.add(page);
        pagesById.put(page.getPageId(), page);
        pagesByUrl.put(page.getUrl(), page);
    }
    public static Page getPage(String url) throws PageNotFoundException {
        if (url != null && !pagesByUrl.containsKey(url)) {
            Page page = Services.view.getPageByUrl(url);

            if (page == null)
                throw new PageNotFoundException();

            addPage(page);
            return page;
        } else if (url != null)
            return pagesByUrl.get(url);
        else
            throw new PageNotFoundException();
    }
    public static Page getPage(int pageId) throws PageNotFoundException {
        if (pageId > 0 && !pagesById.containsKey(pageId)) {
            Page page = Services.view.getPageById(pageId);

            if (page == null)
                throw new PageNotFoundException();

            addPage(page);
            return page;
        } else if (pageId > 0)
            return pagesById.get(pageId);
        else
            throw new PageNotFoundException();
    }
    public static Page updatePage(Page page) {
        return page
                .setPasswords(MainServices.securityService.getPagePasswords(page.getPageId()));
    }
    public static void removePage(Page page) {
        pages.remove(page);
        pagesById.remove(page.getPageId());
        pagesByUrl.remove(page.getUrl());
    }

    @Autowired
    private DAO dao;

    public void init() {
        clearPages();
        for (Page page : getPageList())
            addPage(page);

    }

    // - - - PAGES
    public static final String _PAGE_FIELDS                         = "`pageId`, `name`, `url`";
    public static final String PAGE_GET_LIST                        = "SELECT " + _PAGE_FIELDS + " FROM `view_pages`";
    public static final String PAGE_GET_SPECIFIC_BY_URL             = "SELECT " + _PAGE_FIELDS + " FROM `view_pages` WHERE `url` = :url";
    public static final String PAGE_GET_SPECIFIC_BY_ID              = "SELECT " + _PAGE_FIELDS + " FROM `view_pages` WHERE `pageId` = :pageId";

    public static final StringTemplate CLIENT_PAGE_GET_SPECIFIC     = new StringTemplate("SELECT `pageId`, `title`, `keywords`, `description` FROM `view_pages_{languageCode}` WHERE `pageId` = :pageId");

    public static final String GROUP_GET_LIST_OF_PAGE               = "SELECT `G`.`groupId`, `G`.`type` FROM `view_groups` AS `G` JOIN `view_pages_group_link` AS `L` ON `G`.`groupId` = `L`.`childId` WHERE `L`.`parentId` = :pageId ORDER BY `L`.`order`";

    public static final StringTemplate MODULE_GET_SPECIFIC          = new StringTemplate("SELECT `M`.*, `ML`.* FROM `view_modules` AS `M` JOIN `view_modules_{languageCode}` AS `ML` WHERE `M`.`moduleId` = :moduleId");
    public static final StringTemplate MODULE_GET_LIST_OF_PAGE      = new StringTemplate("SELECT `M`.*, `ML`.*, `GML`.`parentId` AS `groupId`, `GML`.`order` FROM `view_modules` AS `M` JOIN `view_modules_{languageCode}` AS `ML` ON `M`.`moduleId` = `ML`.`moduleId` JOIN `view_groups_module_link` AS `GML` ON `M`.`moduleId` = `GML`.`childId` JOIN `view_pages_group_link` AS `PGL` ON `GML`.`parentId` = `PGL`.`childId` WHERE `PGL`.`parentId` = :pageId ORDER BY `PGL`.`order`, `GML`.`order`");//TODO must get only need rows

    public static final RowMapper<Page> PAGE                        = new RowMapper<Page>() {
        @Override
        public Page mapRow(ResultSet rs, int i) throws SQLException {
            Page page = new Page();

            page
                    .setPageId(rs.getInt("pageId"))
                    .setName(rs.getString("name"))
                    .setUrl(rs.getString("url"));

            return page;
        }
    };
    public static final RowMapper<ClientPage> CLIENT_PAGE           = new RowMapper<ClientPage>() {
        @Override
        public ClientPage mapRow(ResultSet rs, int i) throws SQLException {
            ClientPage page = new ClientPage();

            page
                    .setPageId(rs.getInt("pageId"))
                    .setTitle(rs.getString("title"))
                    .setKeywords(rs.getString("keywords"))
                    .setDescriptions(rs.getString("description"));

            return page;
        }
    };
    public static final RowMapper<Group> GROUP                      = new RowMapper<Group>() {
        @Override
        public Group mapRow(ResultSet rs, int i) throws SQLException {
            Group group = new Group();

            group
                    .setGroupId(rs.getInt("groupId"))
                    .setType(rs.getString("type"));

            return group;
        }
    };
    public static final RowMapper<Module> MODULE_LIST               = new RowMapper<Module>() {
        @Override
        public Module mapRow(ResultSet rs, int i) throws SQLException {
            Module module = new Module();

            module
                    .setModuleId(rs.getInt("moduleId"))
                    .setType(rs.getString("type"))
                    .setHeader(rs.getBoolean("header"))
                    .setHeaderMenuId(rs.getInt("headerMenuId"))
                    .setDefaultLink(rs.getString("defaultLink"))
                    .setElementsPerPage(rs.getInt("elementsPerPage"))
                    .setTemplate(rs.getString("template"))
                    .setHeaderTitle(rs.getString("headerTitle"))
                    .setGroupId(rs.getInt("groupId"))
                    .setOrder(rs.getInt("order"));

            return module;
        }
    };
    public static final RowMapper<Module> MODULE_SPECIFIC           = new RowMapper<Module>() {
        @Override
        public Module mapRow(ResultSet rs, int i) throws SQLException {
            Module module = new Module();

            module
                    .setModuleId(rs.getInt("moduleId"))
                    .setType(rs.getString("type"))
                    .setHeader(rs.getBoolean("header"))
                    .setHeaderMenuId(rs.getInt("headerMenuId"))
                    .setDefaultLink(rs.getString("defaultLink"))
                    .setElementsPerPage(rs.getInt("elementsPerPage"))
                    .setTemplate(rs.getString("template"))
                    .setHeaderTitle(rs.getString("headerTitle"));

            return module;
        }
    };

//    @Cacheable("pageList")
    public ArrayList<Page> getPageList() {
        try {
            return (ArrayList<Page>) dao.getRowList(
                    PAGE_GET_LIST,
                    null,
                    PAGE
            );
        } catch (Exception e) {
            return new ArrayList<Page>();
        }
    }
    public Page getPageByUrl(String url) {
        try {
            return dao.getRow(
                    PAGE_GET_SPECIFIC_BY_URL,
                    (new MapSqlParameterSource())
                            .addValue("url", url),
                    PAGE
            );
        } catch (Exception e) {
            return null;
        }
    }
    public Page getPageById(int pageId) {
        try {
            return dao.getRow(
                    PAGE_GET_SPECIFIC_BY_ID,
                    (new MapSqlParameterSource())
                            .addValue("pageId", pageId),
                    PAGE
            );
        } catch (Exception e) {
            return null;
        }
    }

//    @Cacheable("clientPageSpecific")
    public ClientPage getClientPage(int pageId, String languageCode) {
        ClientPage page     = null;

        try {
            page = dao.getRow(
                    CLIENT_PAGE_GET_SPECIFIC.toString(languageCode),
                    (new MapSqlParameterSource())
                            .addValue("pageId", pageId),
                    CLIENT_PAGE
            );
        } catch (Exception e) {
            return null;
        }

        try {
            page.setGroups((ArrayList<Group>) dao.getRowList(
                    GROUP_GET_LIST_OF_PAGE,
                    (new MapSqlParameterSource())
                            .addValue("pageId", pageId),
                    GROUP
            ));
        } catch (Exception e) {
            page.setGroups(new ArrayList<Group>());
        }

        ArrayList<Module> modules;
        try {
            modules = (ArrayList<Module>) dao.getRowList(
                    MODULE_GET_LIST_OF_PAGE.toString(languageCode),
                    (new MapSqlParameterSource())
                            .addValue("pageId", pageId),
                    MODULE_LIST
            );
        } catch (Exception e) {
            modules = new ArrayList<Module>();
        }

        HashMap<Integer, Group> groups = page.getGroupsMap();
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

        return page;
    }

    public Module getModule(int moduleId, String languageCode) {
        try {
            return dao.getRow(
                    MODULE_GET_SPECIFIC.toString(languageCode),
                    (new MapSqlParameterSource())
                            .addValue("moduleId", moduleId),
                    MODULE_SPECIFIC
            );
        } catch (Exception e) {
            return null;
        }
    }




}
