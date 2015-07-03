package com.quantium.web.util.dao;

import com.quantium.web.bean.core.StringTemplate;
import com.quantium.web.bean.core.dao.RowInstance;
import com.quantium.web.bean.core.dao.TableInstance;
import com.quantium.web.dao.annotation.Index;
import com.quantium.web.dao.annotation.Row;
import com.quantium.web.dao.annotation.Table;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by user on 13.05.2015.
 */
public class DAOHelper {
    private static final HashMap<Class, TableInstance> tableList = new HashMap<>();

    private static void _table(Class _class) {
        Annotation _a;
        Table _table = null;
        TableInstance table;

        _a      = _class.getAnnotation(Table.class);

        if (_a != null)
            _table  = (Table) _a;

        if (_table != null) {
            table   = new TableInstance(new StringTemplate(_table.value()), _table.engine(), _table.charset(), _table.autoIncrement());

            table.setRowList(_row(_class));

            tableList.put(_class, table);
        }
    }
    private static ArrayList<RowInstance> _row(Class _class) {
        Annotation _a;
        String name;
        Row _row    = null;

        ArrayList<RowInstance> rows     = new ArrayList<>();

        for (Field field : _class.getDeclaredFields()) {
            _a      = field.getAnnotation(Row.class);

            if (_a == null)
                continue;

            _row        = (Row) _a;

            name        = _row.name();

            if (name == null || name.isEmpty())
                name    = field.getName();

            RowInstance row = new RowInstance(name);



            rows.add(row);
        }

        return rows;
    }

    public static TableInstance getTable(Object object) {
        if (object != null)
            return getTable(object.getClass());

        return null;
    }
    public static TableInstance getTable(Class _class) {
        if (_class != null) {
            if (!tableList.containsKey(_class))
                _table(_class);

            return tableList.get(_class);
        }

        return null;
    }

}
