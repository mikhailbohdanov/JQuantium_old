package com.quantium.web.dao;

import com.quantium.web.bean.core.Action;
import com.quantium.web.bean.core.Route;
import com.quantium.web.bean.core.StaticNode;
import com.quantium.web.bean.core.StringTemplate;
import com.quantium.web.bean.core.config.Config;
import com.quantium.web.bean.core.config.ConfigGroup;
import com.quantium.web.bean.core._language.Language;
import com.quantium.web.bean.core._language.LanguageKey;
import com.quantium.web.bean.core._language.LanguageValue;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Михаил on 03.09.14.
 */
public interface CoreIO {

    /* - - - STATIC NODES - - - */

    // - Create
    public static final String STATIC_NODE_CREATE                           = "INSERT INTO `core_static_nodes` (`url`) VALUES (:url)";

    // - Get
    public static final String STATIC_NODE_GET_LIST_ALL                     = "SELECT `nodeId`, `url`, `level` FROM `core_static_nodes` WHERE `level` = :level";
    public static final String STATIC_NODE_GET_SPECIFIC                     = "SELECT `nodeId`, `url`, `level` FROM `core_static_nodes` WHERE `nodeId` = :level";
    public static final String STATIC_NODE_COUNT                            = "SELECT COUNT(*) FROM `core_static_nodes`";

    // - Update
    public static final String STATIC_NODE_UPDATE                           = "UPDATE `core_static_nodes` SET `url` = :url, `level` = :level WHERE `nodeId` = :nodeId";

    // - Delete
    public static final String STATIC_NODE_DELETE                           = "UPDATE `core_static_nodes` SET `deleted` = 1 WHERE `nodeId` = :nodeId";
    public static final String STATIC_NODE_RESTORE                          = "UPDATE `core_static_nodes` SET `deleted` = 0 WHERE `nodeId` = :nodeId";

    // - Mappers
    public static final RowMapper<StaticNode> STATIC_NODE                   = new RowMapper<StaticNode>() {
        @Override
        public StaticNode mapRow(ResultSet rs, int i) throws SQLException {
            return (new StaticNode())
                    .setNodeId(rs.getInt("nodeId"))
                    .setUrl(rs.getString("url"))
                    .setLevel(rs.getString("level"));
        }
    };



    /* - - - CONFIG GROUPS - - - */

    // - Create
    public static final String CONFIG_GROUP_CREATE                          = "INSERT INTO `core_config_groups` (`name`) VALUES (:groupName)";

    // - Get
    public static final String CONFIG_GROUP_GET_LIST_ALL                    = "SELECT `groupId`, `name` FROM `core_config_groups`";
    public static final String CONFIG_GROUP_GET_SPECIFIC                    = "SELECT `groupId`, `name` FROM `core_config_groups` WHERE `groupId` = :groupId";
    public static final String CONFIG_GROUP_COUNT                           = "SELECT COUNT(*) FROM `core_config_groups`";

    // - Update
    public static final String CONFIG_GROUP_UPDATE                          = "UPDATE `core_config_groups` SET `name` = :groupName WHERE `groupId` = :groupId";

    // - Delete
    public static final String CONFIG_GROUP_DELETE                          = "DELETE FROM `core_config_groups` WHERE `groupId` = :groupId";
    public static final String CONFIG_GROUP_RESTORE                         = "UPDATE `core_config_groups` SET `deleted` = 0 WHERE `groupId` = :groupId";

    // - Mappers
    public static final RowMapper<ConfigGroup> CONFIG_GROUP                 = new RowMapper<ConfigGroup>() {
        @Override
        public ConfigGroup mapRow(ResultSet rs, int i) throws SQLException {
            return (new ConfigGroup())
                    .setGroupId(rs.getInt("groupId"))
                    .setName(rs.getString("name"));
        }
    };



    /* - - - CONFIGS - - - */

    // - Create
    public static final String CONFIG_CREATE                                = "INSERT INTO `core_configs` (`groupId`, `key`, `value`) VALUES (:groupId, :key, :value)";

    // - Get
    public static final String CONFIG_GET_LIST_ALL                          = "SELECT `configId`, `groupId`, `key`, `value` FROM `core_configs`";
    public static final String CONFIG_GET_LIST_BY_PARENT                    = "SELECT `configId`, `groupId`, `key`, `value` FROM `core_configs` WHERE `groupId` = :groupId";
    public static final String CONFIG_GET_SPECIFIC                          = "SELECT `configId`, `groupId`, `key`, `value` FROM `core_configs` WHERE `configId` = :configId";
    public static final String CONFIG_COUNT                                 = "SELECT COUNT(*) FROM `core_configs`";
    public static final String CONFIG_COUNT_BY_PARENT                       = "SELECT COUNT(*) FROM `core_configs` WHERE `groupId` = :groupId";

    // - Update
    public static final String CONFIG_UPDATE                                = "UPDATE `core_configs` SET `groupId` = :groupId, `key` = :key, `value` = :value WHERE `configId` = :configId";

    // - Delete
    public static final String CONFIG_DELETE                                = "DELETE FROM `core_configs` WHERE `configId` = :configId";
    public static final String CONFIG_RESTORE                               = "UPDATE `core_configs` SET `deleted` = 0 WHERE `configId` = :configId";

    // - Mappers
    public static final RowMapper<Config> CONFIG                            = new RowMapper<Config>() {
        @Override
        public Config mapRow(ResultSet rs, int i) throws SQLException {
            return (new Config())
                    .setConfigId(rs.getInt("configId"))
                    .setGroupId(rs.getInt("groupId"))
                    .setKey(rs.getString("key"))
                    .setValue(rs.getString("value"));
        }
    };



    /* - - - LANGUAGES - - - */

    // - Create
    public static final String LANGUAGE_CREATE                              = "INSERT INTO `core_languages` (`code`, `name`) VALUES (:code, :name)";

    // - Get
    public static final String LANGUAGE_GET_LIST_ALL                        = "SELECT `languageId`, `code`, `name`, `enabled` FROM `core_languages` WHERE `enabled` = 1";
    public static final String LANGUAGE_GET_SPECIFIC                        = "SELECT `languageId`, `code`, `name`, `enabled` FROM `core_languages` WHERE `languageId` = :languageId";
    public static final String LANGUAGE_COUNT                               = "SELECT COUNT(*) FROM `core_languages`";

    // - Update
    public static final String LANGUAGE_UPDATE                              = "UPDATE `core_languages` SET `code` = :code, `name` = :name, `enabled` = :enabled WHERE `languageId` = :languageId";

    // - Delete
    public static final String LANGUAGE_DELETE                              = "DELETE FROM `core_languages` WHERE `languageId` = :languageId";

    // - Mappers
    public static final RowMapper<Language> LANGUAGE                        = new RowMapper<Language>() {
        @Override
        public Language mapRow(ResultSet rs, int i) throws SQLException {
            return (new Language())
                    .setLanguageId(rs.getInt("languageId"))
                    .setCode(rs.getString("code"))
                    .setName(rs.getString("name"))
                    .setEnabled(rs.getBoolean("enabled"));
        }
    };



    /* - - - LANGUAGE KEYS - - - */

    // - Create
    public static final String LANGUAGE_KEY_CREATE                          = "INSERT INTO `core_languages_keys` (`key`, `info`) VALUES (:key, :info)";

    // - Get
    public static final String LANGUAGE_KEY_GET_LIST_ALL                    = "SELECT `keyId`, `key`, `info` FROM `core_languages_keys`";
    public static final String LANGUAGE_KEY_GET_SPECIFIC                    = "SELECT `keyId`, `key`, `info` FROM `core_languages_keys` WHERE `keyId` = :keyId";
    public static final String LANGUAGE_KEY_COUNT                           = "SELECT COUNT(*) FROM `core_languages_keys` WHERE";

    // - Update
    public static final String LANGUAGE_KEY_UPDATE                          = "UPDATE `core_languages_keys` SET `key` = :key, `info` = :info WHERE `keyId` = :keyId";

    // - Delete
    public static final String LANGUAGE_KEY_DELETE                          = "DELETE FROM `core_languages_keys` WHERE `keyId` = :keyId";
    public static final String LANGUAGE_KEY_RESTORE                         = "UPDATE `core_languages_keys` SET `deleted` = 0 WHERE `keyId` = :keyId";

    // - Mappers
    public static final RowMapper<LanguageKey> LANGUAGE_KEY                 = new RowMapper<LanguageKey>() {
        @Override
        public LanguageKey mapRow(ResultSet rs, int i) throws SQLException {
            return (new LanguageKey())
                    .setKeyId(rs.getInt("keyId"))
                    .setKey(rs.getString("key"))
                    .setInfo(rs.getString("info"));
        }
    };



    /* - - - LANGUAGE VALUES - - - */

    // - Create
    public static final String LANGUAGE_VALUE_CREATE                        = "INSERT INTO `core_languages_values` (`languageId`, `keyId`, `value`) VALUES (:languageId, :keyId, :value)";

    // - Get
    public static final String LANGUAGE_VALUE_GET_LIST_ALL                  = "SELECT `valueId`, `languageId`, `keyId`, `value` FROM `core_languages_values`";
    public static final String LANGUAGE_VALUE_GET_LIST_BY_LANGUAGE          = "SELECT `valueId`, `languageId`, `keyId`, `value` FROM `core_languages_values` WHERE `languageId` = :languageId";
    public static final String LANGUAGE_VALUE_COUNT                         = "SELECT COUNT(*) FROM `core_languages_values`";
    public static final String LANGUAGE_VALUE_GET_SPECIFIC                  = "SELECT `valueId`, `languageId`, `keyId`, `value` FROM `core_languages_values` WHERE `valueId` = :valueId";
    public static final String LANGUAGE_VALUE_GET_SPECIFIC_BY_L_K           = "SELECT `valueId`, `languageId`, `keyId`, `value` FROM `core_languages_values` WHERE `languageId` = :languageId AND `keyId` = :keyId";

    // - Update
    public static final String LANGUAGE_VALUE_UPDATE                        = "UPDATE `core_languages_values` SET `languageId` = :languageId, `keyId` = :keyId, `value` = :value WHERE `valueId` = :valueId";

    // - Delete
    public static final String LANGUAGE_VALUE_DELETE                        = "UPDATE FROM `core_languages_values` WHERE `valueId` = :valueId";
    public static final String LANGUAGE_VALUE_RESTORE                       = "UPDATE `core_languages_values` SET `deleted` = 0 WHERE `valueId` = :valueId";

    // - Mappers
    public static final RowMapper<LanguageValue> LANGUAGE_VALUE             = new RowMapper<LanguageValue>() {
        @Override
        public LanguageValue mapRow(ResultSet rs, int i) throws SQLException {
            return (new LanguageValue())
                    .setValueId(rs.getInt("valueId"))
                    .setKeyId(rs.getInt("keyId"))
                    .setValue(rs.getString("value"));
        }
    };



    /* - - - ROUTES - - - */

    // - Create

    // - Get
    public static final String ROUTE_GET_SPECIFIC                           = "SELECT * FROM `core_routes` WHERE `url` = :url";

    // - Update

    // - Delete

    // - Mappers
    public static final RowMapper<Route> ROUTE                              = new RowMapper<Route>() {
        @Override
        public Route mapRow(ResultSet rs, int i) throws SQLException {
            return (new Route())
                    .setRouteId(rs.getInt("routeId"))
                    .setType(rs.getString("type"))
                    .setUrl(rs.getString("url"))
                    .setOwnerId(rs.getInt("ownerId"));
        }
    };



    /* - - - ACTIONS - - - */

    // - Create

    // - Get
    public static final StringTemplate ACTION_GET_BY_PARENT                 = new StringTemplate("SELECT * FROM `core_actions` WHERE `name` LIKE '{parentName}%'");

    // - Update

    // - Delete

    // - Mappers
    public static final RowMapper<Action> ACTION                            = new RowMapper<Action>() {
        @Override
        public Action mapRow(ResultSet rs, int i) throws SQLException {
            Action action = new Action();

            action
                    .setActionId(rs.getInt("actionId"))
                    .setName(rs.getString("name"))
                    .setType(rs.getString("type"))
                    .setObjectClass(rs.getString("objectClass"))
                    .setAction(rs.getString("action"));

            return action;
        }
    };

}
