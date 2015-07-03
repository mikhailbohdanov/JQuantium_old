package com.quantium.web.service;

import com.quantium.web.bean.core.config.Config;
import com.quantium.web.bean.core.config.ConfigGroup;
import com.quantium.web.util.ObjectUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by FREEMAN on 26.11.14.
 */
@Service
public class ConfigService {
    private static HashMap<String, ConfigGroup> configGroupsByName  = new HashMap<String, ConfigGroup>();
    private static HashMap<Integer, ConfigGroup> configGroupsById   = new HashMap<Integer, ConfigGroup>();

    // - Set config groups
    public static void setConfigGroups(ArrayList<ConfigGroup> configGroups) {
        for (ConfigGroup configGroup : configGroups) {
            configGroupsByName.put(configGroup.getName(), configGroup);
            configGroupsById.put(configGroup.getGroupId(), configGroup);
        }
    }

    // - Set configs
    public static void setConfigs(ArrayList<Config> configs) {
        for (Map.Entry<Integer, ConfigGroup> entry : configGroupsById.entrySet())
            entry.getValue().setConfigs(configs);
    }

    // - Get config group
    public static ConfigGroup getConfigGroup(String name) {
        if (!configGroupsByName.containsKey(name))
            return null;

        return configGroupsByName.get(name);
    }
    public static ConfigGroup getConfigGroup(int groupId) {
        if (!configGroupsById.containsKey(groupId))
            return null;

        return configGroupsById.get(groupId);
    }

    // - - - Get config
    public static Config getConfig(String parent, String key) {
        ConfigGroup group = getConfigGroup(parent);

        if (group == null || !group.hasConfig(key))
            return null;

        return group.getConfig(key);
    }
    public static byte getByte(String parent, String key) {
        String _config = getString(parent, key);

        if (_config != null)
            return Byte.parseByte(_config);

        return 0;
    }
    public static short getShort(String parent, String key) {
        String _config = getString(parent, key);

        if (_config != null)
            return Short.parseShort(_config);

        return 0;
    }
    public static int getInt(String parent, String key) {
        String _config = getString(parent, key);

        if (_config != null)
            return Integer.parseInt(_config);

        return 0;
    }
    public static long getLong(String parent, String key) {
        String _config = getString(parent, key);

        if (_config != null)
            return Long.parseLong(_config);

        return 0L;
    }
    public static float getFloat(String parent, String key) {
        String _config = getString(parent, key);

        if (_config != null)
            return Float.parseFloat(_config);

        return 0.0f;
    }
    public static double getDouble(String parent, String key) {
        String _config = getString(parent, key);

        if (_config != null)
            return Double.parseDouble(_config);

        return 0.0d;
    }
    public static boolean getBoolean(String parent, String key) {
        String _config = getString(parent, key);

        if (_config != null)
            return Boolean.valueOf(_config);

        return false;
    }
    public static String getString(String parent, String key) {
        Config _config = getConfig(parent, key);

        if (_config != null)
            return String.valueOf(_config.getValue());
        else
            return null;
    }
    public static JSONObject getJSONObject(String parent, String key) {
        Config _config = getConfig(parent, key);

        if (_config != null)
            try {
                return (JSONObject) ObjectUtils.JSON_PARSER.parse((String) _config.getValue());
            } catch (ParseException ignored) {}
        return null;
    }
    public static JSONArray getJSONArray(String parent, String key) {
        Config _config = getConfig(parent, key);

        if (_config != null)
            try {
                return (JSONArray) ObjectUtils.JSON_PARSER.parse((String) _config.getValue());
            } catch (ParseException ignored) {}
        return null;
    }
    public static Object getObject(String parent, String key) {
        Config _config = getConfig(parent, key);

        if (_config != null)
            return _config.getValue();
        return null;
    }


    // - Create config group
    public static boolean createConfigGroup(String name) {
        if (configGroupsByName.containsKey(name))//TODO
            return false;

        return false;
    }

    private static CoreService coreService;

    @Autowired
    public void setCoreService(CoreService coreService) {
        ConfigService.coreService = coreService;
    }

    @PostConstruct
    public void init() {
        setConfigGroups(coreService.configGroupGetList());
        setConfigs(coreService.configGetList());
    }
}
