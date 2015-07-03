package com.quantium.web.dao;

/**
 * Author FREEMAN
 * Created 12.11.14
 */
public abstract class Utility {
    final String HAS_VALUE(String tableName, String rowName) {
        return "SELECT COUNT(*) FROM `" + tableName + "` WHERE `" + rowName + "` = :value";
    }

}
