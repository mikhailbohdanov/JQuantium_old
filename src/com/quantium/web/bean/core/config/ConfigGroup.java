package com.quantium.web.bean.core.config;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by FREEMAN on 26.11.14.
 */
public class ConfigGroup {
    private int groupId;
    private String name;

    private boolean deleted;

    private HashMap<String, Config> configs = new HashMap<String, Config>();

    public int getGroupId() {
        return groupId;
    }
    public ConfigGroup setGroupId(int groupId) {
        this.groupId = groupId;
        return this;
    }

    public String getName() {
        return name;
    }
    public ConfigGroup setName(String name) {
        this.name = name;
        return this;
    }

    public ConfigGroup setConfigs(ArrayList<Config> configs) {
        for (Config config : configs)
            if (config.getGroupId() == groupId)
                this.configs.put(config.getKey(), config);

        return this;
    }

    public boolean hasConfig(String key) {
        return configs.containsKey(key);
    }

    public Config getConfig(String key) {
        return configs.get(key);
    }
}
