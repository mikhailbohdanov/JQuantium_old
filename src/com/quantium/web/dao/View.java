package com.quantium.web.dao;

import com.quantium.web.bean.core.StringTemplate;
import com.quantium.web.bean.view.ClientPage;
import com.quantium.web.bean.view.Group;
import com.quantium.web.bean.view.Module;
import com.quantium.web.bean.view.Page;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Михаил on 03.09.14.
 */
public class View {

    public static final String PAGES_GET_LIST                   = "SELECT `pageId`, `name`, `url`, `priority` FROM `view_pages`";
    public static final StringTemplate PAGES_GET_LANG_LIST      = new StringTemplate("SELECT `pageId`, `title`, `keywords`, `description` FROM `view_pages_{lang}`");



//    protected final String

    protected final String GET_PAGES                    = "SELECT `pageId`, `url`, `priority` FROM `view_pages`";

    protected final String GET_PAGE_GROUPS              = "SELECT `id`, `type` FROM `view_groups` LEFT JOIN `view_page_group_link` ON `view_groups`.`id` = `view_page_group_link`.`groupId` WHERE `view_page_group_link`.`pageId` = :pageId";
    protected final String GET_GROUP_GROUPS             = "SELECT `id`, `type` FROM `view_groups` WHERE `groupId` = :groupId";

    protected final String GET_GROUP_MODULES            = "SELECT ";


    public static final RowMapper<Page> PAGE                = new RowMapper<Page>() {
        @Override
        public Page mapRow(ResultSet rs, int i) throws SQLException {
            return (new Page())
                    .setPageId(rs.getInt("pageId"))
                    .setName(rs.getString("name"))
                    .setUrl(rs.getString("url"));
        }
    };
    public static final RowMapper<ClientPage> CLIENT_PAGE   = new RowMapper<ClientPage>() {
        @Override
        public ClientPage mapRow(ResultSet rs, int i) throws SQLException {
            return (new ClientPage())
                    .setPageId(rs.getInt("pageId"))
                    .setTitle(rs.getString("title"))
                    .setKeywords(rs.getString("keywords"))
                    .setDescriptions(rs.getString("description"));
        }
    };


    protected final RowMapper<Group> GROUP              = new RowMapper<Group>() {
        @Override
        public Group mapRow(ResultSet rs, int i) throws SQLException {
            return (new Group())
                    .setGroupId(rs.getInt("id"))
                    .setType(rs.getString("type"));
        }
    };

    protected final RowMapper<Module> MODULE            = new RowMapper<Module>() {
        @Override
        public Module mapRow(ResultSet resultSet, int i) throws SQLException {
            return (new Module());

                //TODO заполнить
        }
    };

}
