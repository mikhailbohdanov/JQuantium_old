package com.quantium.web.dao;

import com.quantium.web.bean.core.StringTemplate;

/**
 * Created by FREEMAN on 17.12.14.
 */
public interface UtilityIO {

    /* - - - INTEGRATED - - - */

    public static final StringTemplate COUNT_VALUES                         = new StringTemplate("SELECT COUNT(*) FROM `{tableName}` WHERE `{rowName}` = :value");
    public static final StringTemplate COUNT_ALL_VALUES                     = new StringTemplate("SELECT COUNT(*) FROM `{tableName}`");




    /* - - - Messages - - - */

    public static final StringTemplate FAILED_COUNT                         = new StringTemplate("Failed to get count of elements in the table: \"{tableName}\" by column: \"{columnName} with value: \"{value}\"");
    public static final StringTemplate FAILED_COUNT_ALL                     = new StringTemplate("Failed to get count of elements in the table: \"{tableName}\"");



}
