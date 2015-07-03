package com.quantium.web.bean.sql.elements;

import com.quantium.web.bean.core.StringTemplate;

/**
 * Created by FREEMAN on 28.11.14.
 */
public class Row {
    private String tableName;
    private String rowName;
    private String aliasName;

    public Row(String rowName) {
        this.rowName = rowName;
    }
    public Row(String rowName, String aliasName) {
        this.rowName = rowName;
        this.aliasName = aliasName;
    }
    public Row(String tableName, String rowName, String aliasName) {
        this.tableName = tableName;
        this.rowName = rowName;
        this.aliasName = aliasName;
    }

    public String getTableName() {
        return tableName;
    }

    public String getRowName() {
        return rowName;
    }

    public String getAliasName() {
        return aliasName;
    }

    public String forSelect() {
        StringBuilder out = new StringBuilder();

        if (tableName != null)
            out.append("`").append(tableName).append("`.`").append(rowName).append("`");
        else
            out.append(rowName);

        if (aliasName != null)
            out.append(" AS `").append(aliasName).append("`");

        return out.toString();
    }

    public String forWhere() {
        StringBuilder out = new StringBuilder();

        if (aliasName != null)
            out.append("`").append(aliasName).append("`");
        else {
            if (tableName != null)
                out.append("`").append(tableName).append("`.`").append(rowName).append("`");
            else
                out.append("`").append(rowName).append("`");
        }

        return out.toString();
    }

}
