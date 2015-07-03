package com.quantium.web.dao;

import com.quantium.web.bean.core.StringTemplate;
import com.quantium.web.bean.view.*;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by FREEMAN on 15.01.15.
 */
public interface ViewIO {

    /* - - - PAGES - - - */

    // - Create
    public static final String PAGE_CREATE                                  = "INSERT INTO `view_pages` (`pageId`, `name`, `url`, `priority`) VALUES (:pageId, :name, :url, :priority)";

    // - Get
    String _PAGE_FIELDS                                                     = "`pageId`, `name`, `url`, `priority`";
    public static final String PAGE_GET_LIST_ALL                            = "SELECT " + _PAGE_FIELDS + " FROM `view_pages` WHERE `deleted` = 0";
    public static final String PAGE_GET_SPECIFIC                            = "SELECT " + _PAGE_FIELDS + " FROM `view_pages` WHERE `pageId` = :pageId AND `deleted` = 0";
    public static final String PAGE_COUNT                                   = "SELECT COUNT(*) FROM `view_pages` WHERE `deleted` = 0";

    // - Update
    public static final String PAGE_UPDATE                                  = "UPDATE `view_pages` SET `url` = :url, `level` = :level WHERE `nodeId` = :nodeId";

    // - Delete
    public static final String PAGE_DELETE                                  = "UPDATE `core_static_nodes` SET `deleted` = 1 WHERE `nodeId` = :nodeId";
    public static final String PAGE_RESTORE                                 = "UPDATE `core_static_nodes` SET `deleted` = 0 WHERE `nodeId` = :nodeId";

    // - Mappers
    public static final RowMapper<Page> PAGE                                = new RowMapper<Page>() {
        @Override
        public Page mapRow(ResultSet rs, int i) throws SQLException {
            return (new Page())
                    .setPageId(rs.getInt("pageId"))
                    .setName(rs.getString("name"))
                    .setUrl(rs.getString("url"));
        }
    };



    /* - - - CLIENT PAGES - - - */

    // - Create

    // - Get
    public static final StringTemplate CLIENT_PAGE_SPECIFIC                 = new StringTemplate("SELECT `pageId`, `title`, `keywords`, `description` FROM `view_pages_{langCode}` WHERE `pageId` = :pageId");

    // - Update

    // - Delete

    // - Mappers
    public static final RowMapper<ClientPage> CLIENT_PAGE                   = new RowMapper<ClientPage>() {
        @Override
        public ClientPage mapRow(ResultSet rs, int i) throws SQLException {
            return (new ClientPage())
                    .setPageId(rs.getInt("pageId"))
                    .setTitle(rs.getString("title"))
                    .setKeywords(rs.getString("keywords"))
                    .setDescriptions(rs.getString("description"));
        }
    };



    /* - - - GROUPS - - - */

    // - Create

    // - Get
    public static final String GROUP_GET_LIST_OF_PAGE                       = "SELECT `G`.`groupId`, `G`.`type` FROM `view_groups` AS `G` JOIN `view_pages_group_link` AS `L` ON `G`.`groupId` = `L`.`groupId` WHERE `L`.`pageId` = :pageId AND `G`.`deleted` = 0 ORDER BY `L`.`order`";

    // - Update

    // - Delete

    // - Mappers
    public static final RowMapper<Group> GROUP                              = new RowMapper<Group>() {
        @Override
        public Group mapRow(ResultSet rs, int i) throws SQLException {
            return (new Group())
                    .setGroupId(rs.getInt("groupId"))
                    .setType(rs.getString("type"));
        }
    };



    /* - - - MODULES - - - */

    // - Create

    // - Get
    public static final StringTemplate MODULE_GET_LIST_OF_PAGE              = new StringTemplate("SELECT `M`.*, `ML`.*, `GML`.`groupId`, `GML`.`order` FROM `view_modules` AS `M` JOIN `view_modules_{langCode}` AS `ML` ON `M`.`moduleId` = `ML`.`moduleId` JOIN `view_groups_module_link` AS `GML` ON `M`.`moduleId` = `GML`.`moduleId` JOIN `view_pages_group_link` AS `PGL` ON `GML`.`groupId` = `PGL`.`groupId` WHERE `PGL`.`pageId` = :pageId ORDER BY `PGL`.`order`, `GML`.`order`");

    // - Update

    // - Delete

    // - Mappers
    public static final RowMapper<Module> MODULE                            = new RowMapper<Module>() {
        @Override
        public Module mapRow(ResultSet rs, int i) throws SQLException {
            return (new Module())
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
        }
    };



    /* - - - MENUS - - - */

    // - Create

    // - Get
    public static final String MENU_GET_SPECIFIC_BY_ID                      = "SELECT * FROM `view_menus` WHERE `menuId` = :menuId";
    public static final String MENU_GET_SPECIFIC_BY_NAME                    = "SELECT * FROM `view_menus` WHERE `name` = :name";
    public static final StringTemplate MENU_ITEM_LIST_OF_MENU               = new StringTemplate("SELECT `M`.`itemId`, `M`.`menuId`, `M`.`parentId`, `M`.`pageId`, `M`.`parameters`, `ML`.`value`, `M`.`order` FROM `view_menus_items` AS `M` LEFT JOIN `view_menus_items_{langCode}` AS `ML` ON `M`.`itemId` = `ML`.`itemId` WHERE `M`.`menuId` = :menuId");

    // - Update

    // - Delete

    // - Mappers
    public static final RowMapper<Menu> MENU                                = new RowMapper<Menu>() {
        @Override
        public Menu mapRow(ResultSet rs, int i) throws SQLException {
            Menu menu = new Menu();

            menu
                    .setMenuId(rs.getInt("menuId"))
                    .setName(rs.getString("name"));

            return menu;
        }
    };
    public static final RowMapper<MenuItem> MENU_ITEM                       = new RowMapper<MenuItem>() {
        @Override
        public MenuItem mapRow(ResultSet rs, int i) throws SQLException {
            MenuItem item = new MenuItem();

            item
                    .setItemId(rs.getInt("itemId"))
                    .setMenuId(rs.getInt("menuId"))
                    .setParentId(rs.getInt("parentId"))
                    .setPageId(rs.getInt("pageId"))
                    .setParameters(rs.getString("parameters"))
                    .setValue(rs.getString("value"))
                    .setOrder(rs.getInt("order"));

            return item;
        }
    };


}
