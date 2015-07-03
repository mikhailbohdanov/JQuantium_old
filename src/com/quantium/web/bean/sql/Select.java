package com.quantium.web.bean.sql;

import com.quantium.web.bean.core.StringTemplate;
import com.quantium.web.bean.sql.elements.*;
import com.quantium.web.util.Primitives;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by FREEMAN on 27.11.14.
 */
public class Select {
    protected HashMap<String, ArrayList<Row>> rows  = new HashMap<String, ArrayList<Row>>();
    protected HashMap<String, String> joins         = new HashMap<String, String>();
    protected HashMap<String, String> wheres        = new HashMap<String, String>();
    protected HashMap<String, String> orders        = new HashMap<String, String>();
    protected HashMap<String, String> groups        = new HashMap<String, String>();
    protected HashMap<String, String> limits        = new HashMap<String, String>();

    protected String tableName;

    private HashMap<String, Instance> instances     = new HashMap<String, Instance>();

    public String getTableName() {
        return tableName;
    }
    public Select setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public Instance newInstance(String groupName) {
        return newInstance(groupName, groupName, groupName, groupName, groupName, groupName);
    }
    public Instance newInstance(String rows, String join, String where, String order, String group, String limit) {
        StringBuilder _hash  = new StringBuilder();

        _hash.append(rows).append(join).append(where).append(order).append(group).append(limit);

        String hash = _hash.toString();

        Instance instance;
        if (instances.containsKey(hash))
            instance = instances.get(hash);
        else {
            instance = new Instance(this).group(rows, join, where, order, group, limit);
            instances.put(hash, instance);
        }

        return instance;
    }

    public Group group(String groupName) {
        return new Group(this, groupName);
    }

    public class Instance {
        private ArrayList<Row> rows = null;
        private String join         = null;
        private String where        = null;
        private String order        = null;
        private String group        = null;
        private String limit        = null;

        private Select parent       = null;

        private boolean modified        = true;
        private StringTemplate cache    = null;

        private Instance(Select parent) {
            this.parent = parent;
        }

        private Instance group(String rows, String join, String where, String order, String group, String limit) {
            if (rows != null && parent.rows.containsKey(rows))
                this.rows = parent.rows.get(rows);

            if (join != null && parent.joins.containsKey(join))
                this.join = parent.joins.get(join);

            if (where != null && parent.wheres.containsKey(where))
                this.where = parent.wheres.get(where);

            if (order != null && parent.orders.containsKey(order))
                this.order = parent.orders.get(order);

            if (group != null && parent.groups.containsKey(group))
                this.group = parent.groups.get(group);

            if (limit != null && parent.limits.containsKey(limit))
                this.limit = parent.limits.get(limit);

            this.modified = true;

            return this;
        }

        public Instance setModified(boolean modified) {
            this.modified = modified;
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

        public <T> T mapObject(Class<T> obj, ResultSet rs) {
            T out = null;

            try {
                out = obj.newInstance();
            } catch (Exception e) {
                return null;
            }

            String name;
            for (Row row : rows) {
                if ((name = row.getAliasName()) == null)
                    name = row.getRowName();

                Field field;
                try {
                    field = obj.getDeclaredField(name);
                } catch (NoSuchFieldException e) {
                    continue;
                }

                try {
                    field.set(out, rs.getObject(name, field.getType()));
                } catch (Exception e) {}
            }

            return out;
        }

        private void generate() {
            if (modified){
                StringBuilder out = new StringBuilder();

                out.append("SELECT ");

                if (rows != null) {
                    ArrayList<String> rows = new ArrayList<String>();

                    for (Row row : this.rows)
                        rows.add(row.forSelect());

                    out.append(Primitives.slice(", ", rows));
                } else
                    out.append("*");

                out.append(" FROM ").append("`").append(parent.tableName).append("`");

                if (join != null)
                    out.append(join);

                if (where != null)
                    out.append(" WHERE ").append(where);

                if (order != null)
                    out.append(" ORDER BY ").append(order);

                if (group != null)
                    out.append(" GROUP BY ").append(group);

                if (limit != null)
                    out.append(" LIMIT ").append(limit);

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

        private Select parent           = null;
        private String groupName        = null;

        private Group() {}
        public Group(Select parent, String groupName) {
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

        public Group setJoin(String join) {
            this.join = join;
            return this;
        }

        public Group setWhere(String where) {
            this.where = where;
            return this;
        }

        public Group setOrder(String order) {
            this.order = order;
            return this;
        }

        public Group setGroup(String group) {
            this.group = group;
            return this;
        }

        public Group setLimit(String limit) {
            this.limit = limit;
            return this;
        }

        public Select end() {
            if (rows != null) {
                if (!parent.rows.containsKey(groupName))
                    parent.rows.put(groupName, rows);
                else
                    parent.rows.replace(groupName, rows);
            } else if (parent.rows.containsKey(groupName))
                parent.rows.remove(groupName);

            if (join != null) {
                if (!parent.joins.containsKey(groupName))
                    parent.joins.put(groupName, join);
                else
                    parent.joins.replace(groupName, join);
            } else if (parent.joins.containsKey(groupName))
                parent.joins.remove(groupName);

            if (where != null) {
                if (!parent.wheres.containsKey(groupName))
                    parent.wheres.put(groupName, where);
                else
                    parent.wheres.replace(groupName, where);
            } else if (parent.wheres.containsKey(groupName))
                parent.wheres.remove(groupName);

            if (order != null) {
                if (!parent.orders.containsKey(groupName))
                    parent.orders.put(groupName, order);
                else
                    parent.orders.replace(groupName, order);
            } else if (parent.orders.containsKey(groupName))
                parent.orders.remove(groupName);

            if (group != null) {
                if (!parent.groups.containsKey(groupName))
                    parent.groups.put(groupName, group);
                else
                    parent.groups.replace(groupName, group);
            } else if (parent.groups.containsKey(groupName))
                parent.groups.remove(groupName);

            if (limit != null) {
                if (!parent.limits.containsKey(groupName))
                    parent.limits.put(groupName, limit);
                else
                    parent.limits.replace(groupName, limit);
            } else if (parent.limits.containsKey(groupName))
                parent.limits.remove(groupName);

            return parent;
        }
    }
}
