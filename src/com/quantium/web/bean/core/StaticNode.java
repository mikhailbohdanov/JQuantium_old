package com.quantium.web.bean.core;

/**
 * Created by Михаил on 23.08.14.
 */
public class StaticNode {
    public static enum Level {
        DEVELOP,
        PRODUCTION,
        TEST
    }

    private int nodeId;
    private String url;
    private Level level;

    public int getNodeId() {
        return nodeId;
    }
    public StaticNode setNodeId(int nodeId) {
        this.nodeId = nodeId;
        return this;
    }

    public String getUrl() {
        return url;
    }
    public StaticNode setUrl(String url) {
        this.url = url;
        return this;
    }

    public Level getLevel() {
        return level;
    }
    public StaticNode setLevel(Level level) {
        this.level = level;
        return this;
    }
    public StaticNode setLevel(String level) {
        this.level = Level.valueOf(level.toUpperCase());
        return this;
    }
}
