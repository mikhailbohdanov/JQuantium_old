package com.quantium.web.bean.core.dao;

import com.quantium.web.bean.core.StringTemplate;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by user on 13.05.2015.
 */
public class TableInstance {
    private StringTemplate tableName;
    private String engine;
    private String charset;
    private int autoIncrement;

    private ArrayList<RowInstance> rowList;
    private HashMap<String, RowInstance> rowByName;

    private TableInstance() {
        rowByName   = new HashMap<>();
    }

    public TableInstance(StringTemplate tableName, String engine, String charset, int autoIncrement) {
        this();

        this.tableName = tableName;
        this.engine = engine;
        this.charset = charset;
        this.autoIncrement = autoIncrement;
    }

    public StringTemplate getTableName() {
        return tableName;
    }

    public String getEngine() {
        return engine;
    }

    public String getCharset() {
        return charset;
    }

    public int getAutoIncrement() {
        return autoIncrement;
    }

    public ArrayList<RowInstance> getRowList() {
        return rowList;
    }
    public TableInstance setRowList(ArrayList<RowInstance> rowList) {
        this.rowList = rowList;

        for (RowInstance row : rowList)
            rowByName.put(row.getName(), row);

        return this;
    }
}
