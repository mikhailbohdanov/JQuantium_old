package com.quantium.web.service;

import com.quantium.web.bean.core.Action;
import com.quantium.web.bean.core.Route;
import com.quantium.web.bean.core.StaticNode;
import com.quantium.web.bean.core.config.Config;
import com.quantium.web.bean.core.config.ConfigGroup;
import com.quantium.web.dao.CoreIO;
import com.quantium.web.dao.UtilityDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Created by Михаил on 03.09.14.
 */
@Service
public class CoreService implements CoreIO {

    @Autowired
    private UtilityDAO utilityDAO;

    public int staticNodeCreate(StaticNode staticNode) {
        int nodeId = 0;

        try {
            nodeId = utilityDAO.createRow(
                    STATIC_NODE_CREATE,
                    (new MapSqlParameterSource())
                            .addValue("url", staticNode.getUrl())
            );
        } catch (Exception e) {
            return 0;
        }

        staticNode.setNodeId(nodeId);
        return nodeId;
    }

//    @Cacheable("staticNodes")
    public ArrayList<StaticNode> staticNodeGetList(StaticNode.Level level) {
        try {
            return (ArrayList<StaticNode>) utilityDAO.getRowList(
                    STATIC_NODE_GET_LIST_ALL,
                    (new MapSqlParameterSource())
                            .addValue("level", level.name().toLowerCase()),
                    STATIC_NODE
            );
        } catch (Exception e) {
            return new ArrayList<StaticNode>();
        }
    }

//    @Cacheable("staticNode")
    public StaticNode staticNodeGet(int nodeId) {
        try {
            return utilityDAO.getRow(
                    STATIC_NODE_GET_SPECIFIC,
                    (new MapSqlParameterSource())
                            .addValue("nodeId", nodeId),
                    STATIC_NODE
            );
        } catch (Exception e) {
            return null;
        }
    }




    /* - - - Config groups - - - */
    public int configGroupCreate(ConfigGroup group) {
        int groupId;
        try {
            groupId = utilityDAO.createRow(
                    CONFIG_GROUP_CREATE,
                    (new MapSqlParameterSource())
                            .addValue("name", group.getName())
            );
        } catch (Exception e) {
            return 0;
        }

            group.setGroupId(groupId);
            return groupId;
    }
    //    @Cacheable("configGroupList")
    public ArrayList<ConfigGroup> configGroupGetList() {
        try {
            return (ArrayList<ConfigGroup>) utilityDAO.getRowList(
                    CONFIG_GROUP_GET_LIST_ALL,
                    CONFIG_GROUP
            );
        } catch (Exception e) {
            return new ArrayList<ConfigGroup>();
        }
    }
    //    @Cacheable("configGroupSpecific")
    public ConfigGroup configGroupGet(int groupId) {
        try {
            return utilityDAO.getRow(
                    CONFIG_GROUP_GET_SPECIFIC,
                    (new MapSqlParameterSource())
                            .addValue("groupId", groupId),
                    CONFIG_GROUP
            );
        } catch (Exception e) {
            return null;
        }
    }
    //    @CacheEvict(value = "configGroupSpecific", key = "keyId")
    public boolean configGroupUpdate(ConfigGroup group) {
        try {
            utilityDAO.execSQL(
                    CONFIG_GROUP_UPDATE,
                    (new MapSqlParameterSource())
                            .addValue("groupId", group.getGroupId())
                            .addValue("name", group.getName())
            );
        } catch (Exception e) {
            return false;
        }

        return true;
    }
    public boolean configGroupDelete(ConfigGroup group) {
        return configGroupDelete(group.getGroupId());
    }
    public boolean configGroupDelete(int groupId) {
        try {
            utilityDAO.execSQL(
                    CONFIG_GROUP_DELETE,
                    (new MapSqlParameterSource())
                            .addValue("groupId", groupId)
            );
        } catch (Exception e) {
            return false;
        }

        return true;
    }
    public int configGroupCount() {
        try {
            return utilityDAO.execSQL(
                    CONFIG_GROUP_COUNT,
                    Integer.class
            );
        } catch (Exception e) {
            return 0;
        }
    }

    /* - - - Configs - - - */
    public int configCreate(Config config) {
        int configId;
        try {
            configId = utilityDAO.createRow(
                    CONFIG_CREATE,
                    (new MapSqlParameterSource())
                            .addValue("groupId", config.getGroupId())
                            .addValue("key", config.getKey())
                            .addValue("value", config.getValue())
            );
        } catch (Exception e) {
            return 0;
        }

        config.setConfigId(configId);
        return configId;
    }
    public ArrayList<Config> configGetList() {
        try {
            return (ArrayList<Config>) utilityDAO.getRowList(
                    CONFIG_GET_LIST_ALL,
                    CONFIG
            );
        } catch (Exception e) {
            return new ArrayList<Config>();
        }
    }
    public ArrayList<Config> configGetList(int groupId) {
        try {
            return (ArrayList<Config>) utilityDAO.getRowList(
                    CONFIG_GET_LIST_BY_PARENT,
                    (new MapSqlParameterSource())
                            .addValue("groupId", groupId),
                    CONFIG
            );
        } catch (Exception e) {
            return new ArrayList<Config>();
        }
    }
    public Config configGet(int configId) {
        try {
            return utilityDAO.getRow(
                    CONFIG_GET_SPECIFIC,
                    (new MapSqlParameterSource())
                            .addValue("configId", configId),
                    CONFIG
            );
        } catch (Exception e) {
            return null;
        }
    }
    public boolean configUpdate(Config config) {
        try {
            utilityDAO.execSQL(
                    CONFIG_UPDATE,
                    (new MapSqlParameterSource())
                            .addValue("configId", config.getConfigId())
                            .addValue("groupId", config.getGroupId())
                            .addValue("key", config.getKey())
                            .addValue("value", config.getValue())
            );
        } catch (Exception e) {
            return false;
        }

        return true;
    }
    public boolean configDelete(Config config) {
        return configDelete(config.getConfigId());
    }
    public boolean configDelete(int configId) {
        try {
            utilityDAO.execSQL(
                    CONFIG_DELETE,
                    (new MapSqlParameterSource())
                            .addValue("configId", configId)
            );
        } catch (Exception e) {
            return false;
        }

        return true;
    }
    public int configCount() {
        try {
            return utilityDAO.execSQL(
                    CONFIG_COUNT,
                    Integer.class
            );
        } catch (Exception e) {
            return 0;
        }
    }
    public int configCount(int groupId) {
        try {
            return utilityDAO.execSQL(
                    CONFIG_COUNT_BY_PARENT,
                    (new MapSqlParameterSource())
                            .addValue("groupId", groupId),
                    Integer.class
            );
        } catch (Exception e) {
            return 0;
        }
    }



    /* - - - Routes - - - */
    public Route getRoute(String url) {
        try {
            return utilityDAO.getRow(
                    ROUTE_GET_SPECIFIC,
                    (new MapSqlParameterSource())
                            .addValue("url", url),
                    ROUTE
            );
        } catch (Exception e) {
            return null;
        }
    }



    /* - - - Actions - - - */
    public ArrayList<Action> getActions(String parentName) {
        try {
            return (ArrayList<Action>) utilityDAO.getRowList(
                    ACTION_GET_BY_PARENT.toString(parentName),
                    ACTION
            );
        } catch (Exception e) {
            return null;
        }
    }


}
