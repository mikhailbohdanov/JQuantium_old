package com.quantium.web.service;

import com.quantium.web.bean.core.Route;
import com.quantium.web.bean.core.StaticNode;
import com.quantium.web.bean.core.localization.Language;
import com.quantium.web.bean.core.localization.LanguageKey;
import com.quantium.web.bean.core.localization.LanguageValue;
import com.quantium.web.dao.DAO;
import com.quantium.web.service.common.Localization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by FREEMAN on 26.02.15.
 */
@Service
public class Core {

    @Autowired
    private DAO dao;

    @Autowired
    private Util util;

    public void init() {
        // - Router
        //TODO
//        clearRoutes();
//        for (Route route : getRouteList(Route.Type.PAGE_PATTERN))
//            addRoute(route);

        // - Languages
        Localization.clearLanguages();

        Localization.setKeys(languageKeyList());

        for (Language language : languageGetList()) {
            Localization.addLanguage(language);
            language.setValues(languageValueGetList(language.getLanguageId()));
        }
    }



    /* - - - Static nodes - - - */
    //TODO
    public static final String STATIC_NODE_GET_LIST             = "SELECT `nodeId`, `url`, `level` FROM `core_static_nodes` WHERE `level` = :level";

    public static final RowMapper<StaticNode> STATIC_NODE       = new RowMapper<StaticNode>() {
        @Override
        public StaticNode mapRow(ResultSet rs, int i) throws SQLException {
            StaticNode node = new StaticNode();

            node
                    .setNodeId(rs.getInt("nodeId"))
                    .setUrl(rs.getString("url"))
                    .setLevel(rs.getString("level"));

            return node;
        }
    };

    public int createStaticNode(StaticNode node) {


        return 0;
    }
    public ArrayList<StaticNode> getStaticNodeList(StaticNode.Level level) {
        try {
            return (ArrayList<StaticNode>) dao.getRowList(
                    STATIC_NODE_GET_LIST,
                    (new MapSqlParameterSource())
                            .addValue("level", level.name()),
                    STATIC_NODE
            );
        } catch (Exception e) {
            return (ArrayList) Collections.emptyList();
        }
    }




    /* - - - Route - - - */
    private static final String _ROUTE_ROWS                     = "`routeId`, `type`, `url`, `ownerId`, `parameters`";
    public static final String ROUTE_GET_LIST_BY_TYPE           = "SELECT " + _ROUTE_ROWS + " FROM `core_routes` WHERE `type` = :type";
    public static final String ROUTE_GET_SPECIFIC_BY_URL        = "SELECT " + _ROUTE_ROWS + " FROM `core_routes` WHERE `url` = :url";
    public static final String ROUTE_GET_SPECIFIC_BY_ID         = "SELECT " + _ROUTE_ROWS + " FROM `core_routes` WHERE `routeId` = :routeId";
    public static final String ROUTE_GET_SPECIFIC_BY_TYPE_OWNER = "SELECT " + _ROUTE_ROWS + " FROM `core_routes` WHERE `type` = :type AND `ownerId` = :ownerId";
    public static final RowMapper<Route> ROUTE                  = new RowMapper<Route>() {
        @Override
        public Route mapRow(ResultSet rs, int i) throws SQLException {
            Route route = new Route();

            route
                    .setRouteId(rs.getInt("routeId"))
                    .setType(rs.getString("type"))
                    .setUrl(rs.getString("url"))
                    .setOwnerId(rs.getInt("ownerId"))
                    .setParameters(rs.getString("parameters"));

            return route;
        }
    };

    public ArrayList<Route> getRouteList() {
        return null;
    }
    public ArrayList<Route> getRouteList(Route.Type type) {
        try {
            return (ArrayList<Route>) dao.getRowList(
                    ROUTE_GET_LIST_BY_TYPE,
                    (new MapSqlParameterSource())
                            .addValue("type", type.name()),
                    ROUTE
            );
        } catch (Exception e) {
            return (ArrayList) Collections.emptyList();
        }
    }

    public Route getRoute(int routeId) {
        try {
            return dao.getRow(
                    ROUTE_GET_SPECIFIC_BY_ID,
                    (new MapSqlParameterSource())
                            .addValue("routeId", routeId),
                    ROUTE
            );
        } catch (Exception e) {
            return null;
        }
    }
    public Route getRoute(String url) {
        try {
            return dao.getRow(
                    ROUTE_GET_SPECIFIC_BY_URL,
                    (new MapSqlParameterSource())
                            .addValue("url", url),
                    ROUTE
            );
        } catch (Exception e) {
            return null;
        }
    }
    public Route getRoute(Route.Type type, int ownerId) {
        try {
            return dao.getRow(
                    ROUTE_GET_SPECIFIC_BY_TYPE_OWNER,
                    (new MapSqlParameterSource())
                            .addValue("type", type.name())
                            .addValue("ownerId", ownerId),
                    ROUTE
            );
        } catch (Exception e) {
            return null;
        }
    }



    /* - - - Languages - - - */
    public static final RowMapper<Language> LANGUAGE            = new RowMapper<Language>() {
        @Override
        public Language mapRow(ResultSet rs, int i) throws SQLException {
            return new Language(rs.getInt("languageId"), rs.getString("code"), rs.getString("name"), rs.getBoolean("enabled"));
        }
    };

    public Language languageCreate(String code, String name, boolean enabled) {
        Language language   = new Language(code, name, enabled);

        if (languageCreate(language) > 0)
            return language;
        else
            return null;
    }
    public int languageCreate(Language language) {
        int languageId  = util.createRow(language);

        if (languageId > 0) {
            ArrayList<LanguageKey> keyList  = languageKeyList();

            String prefix   = language.getCode() + ":";
            LanguageValue value;
            for (LanguageKey key : keyList) {
                value   = languageValueCreate(languageId, key.getKeyId(), prefix + key.getKey());
                language.addValue(value);
            }

            Localization.addLanguage(language);

            //TODO must auto create every table
            ActionService.call("core.language.create", language);
        }

        return languageId;
    }

    public ArrayList<Language> languageGetList() {
        return (ArrayList<Language>) util.getRowList("core_languages", LANGUAGE);
    }
    public Language languageGet(int languageId) {
        return util.getRow("core_languages", "languageId", languageId, LANGUAGE);
    }

    public void languageUpdate(int languageId, String name, String code, boolean enabled) {
        Language languageById   = Localization.getLanguage(languageId);

        languageById
                .setName(name)
                .setCode(code)
                .setEnabled(enabled);

        util.updateRow(languageById);
    }

    public void languageDelete(int languageId) {
        Language language   = Localization.getLanguage(languageId);

        Localization.removeLanguage(languageId);
        util.removeFrom("core_languages", "languageId", languageId);
        util.removeFrom("core_languages_values", "languageId", languageId);
        ActionService.call("core.language.delete", language);
    }

    /* - - - LanguageKey - - - */
    public static final RowMapper<LanguageKey> LANGUAGE_KEY     = new RowMapper<LanguageKey>() {
        @Override
        public LanguageKey mapRow(ResultSet rs, int i) throws SQLException {
            return new LanguageKey(
                    rs.getInt("keyId"),
                    rs.getString("key"),
                    rs.getString("info")
            );
        }
    };

    public LanguageKey languageKeyCreate(String key, String info) {
        LanguageKey _key    = new LanguageKey(key, info);

        _key
                .setKey(key)
                .setInfo(info);

        if (languageKeyCreate(_key) > 0)
            return _key;
        else
            return null;
    }
    public int languageKeyCreate(LanguageKey key) {
        int keyId   = util.createRow(key);

        if (keyId > 0) {
            Localization.addKey(key);

            for (Language language : Localization.getLanguages())
                language.addValue(languageValueCreate(language.getLanguageId(), keyId, language.getCode() + ":" + key.getKey()));
        }

        return keyId;
    }


    public ArrayList<LanguageKey> languageKeyList() {
        return (ArrayList<LanguageKey>) util.getRowList("core_languages_keys", LANGUAGE_KEY);
    }

    /* - - - LanguageValue - - - */
    public static final RowMapper<LanguageValue> LANGUAGE_VALUE = new RowMapper<LanguageValue>() {
        @Override
        public LanguageValue mapRow(ResultSet rs, int i) throws SQLException {
            return new LanguageValue(rs.getInt("languageId"), rs.getInt("keyId"), rs.getString("value"));
        }
    };

    public LanguageValue languageValueCreate(int languageId, int keyId, String value) {
        LanguageValue _value    = new LanguageValue(languageId, keyId, value);

        if (languageValueCreate(_value) > 0)
            return _value;
        else
            return null;
    }
    public int languageValueCreate(LanguageValue value) {
        return util.createRow(value);
    }

    public ArrayList<LanguageValue> languageValueGetList(int languageId) {
        return (ArrayList<LanguageValue>) util.getRowList("core_languages_values", "languageId", languageId, LANGUAGE_VALUE);
    }



}
