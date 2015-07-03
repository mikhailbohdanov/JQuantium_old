package com.quantium.web.bean.sql;

import com.quantium.web.bean.core.StringTemplate;
import com.quantium.web.bean.sql.elements.Row;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by FREEMAN on 27.11.14.
 */
public class Insert {
    protected HashMap<String, ArrayList<Row>> rows          = new HashMap<String, ArrayList<Row>>();

    protected String tableName;

    public String getTableName() {
        return tableName;
    }
    public Insert setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public Instance newInstance() {
        return new Instance(this);
    }
    public Instance newInstance(String groupName) {
        return new Instance(this, groupName);
    }

    public Group group(String groupName) {
        return new Group(this, groupName);
    }

    public class Instance {
        public ArrayList<Row> row       = null;

        public Insert parent    = null;

        private boolean modified        = true;
        private StringTemplate cache    = null;

        private Instance(Insert parent) {
            this.parent = parent;
        }
        private Instance(Insert parent, String groupName) {
            this.parent = parent;
            group(groupName);
        }

        public Instance group(String groupName) {
            return this
                    .rows(groupName);
        }
        public Instance rows(String groupName) {
            if (groupName != null && rows.containsKey(groupName))
                row = rows.get(groupName);
            else
                row = null;

            modified = true;
            return this;
        }

        @Override
        public String toString() {
            generate();
            return cache.toString();
        }

        public String toString(Map<String, Object> map) {
            generate();

            SQLUtils.escapeAll(map);

            if (map != null)
                return cache.toString(map);
            else
                return cache.toString();
        }


        private void generate() {
            if (modified){
                StringBuilder out = new StringBuilder();

                out
                        .append("INSERT INTO `")
                        .append(parent.tableName)
                        .append("` (")
                        .append(row);



                cache = new StringTemplate(out.toString());
                modified = false;
            }
        }
    }

    public class Group {
        private ArrayList<Row> rows     = null;
        private String join             = null;
        private String where            = null;
        private String order            = null;
        private String group            = null;
        private String limit            = null;

        private Insert parent           = null;
        private String groupName        = null;

        private Group() {}
        public Group(Insert parent, String groupName) {
            this.parent = parent;
            this.groupName = groupName;
        }

        public Group addRow(String rowName) {
            if (rows == null)
                rows = new ArrayList<Row>();

            rows.add(new Row(rowName));
            return this;
        }
        public Group addRow(String rowName, String aliasName) {
            if (rows == null)
                rows = new ArrayList<Row>();

            rows.add(new Row(rowName, aliasName));
            return this;
        }
        public Group addRow(String tableName, String rowName, String aliasName) {
            if (rows == null)
                rows = new ArrayList<Row>();

            rows.add(new Row(tableName, rowName, aliasName));
            return this;
        }

        public Insert end() {
            if (rows != null) {
                if (!parent.rows.containsKey(groupName))
                    parent.rows.put(groupName, rows);
                else
                    parent.rows.replace(groupName, rows);
            } else if (parent.rows.containsKey(groupName))
                parent.rows.remove(groupName);

            return parent;
        }
    }

}
