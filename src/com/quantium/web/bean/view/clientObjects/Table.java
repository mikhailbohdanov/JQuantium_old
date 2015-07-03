package com.quantium.web.bean.view.clientObjects;

import com.quantium.web.bean.Url;
import com.quantium.web.controller.CONFIG;

/**
 * Created by FREEMAN on 19.02.15.
 */
public class Table implements CONFIG {
    private Url url;
    private String active;
    public boolean activeDesc;

    public Table(Url url, String activeParameter, boolean activeDesc) {
        this.url = url;
        this.active = activeParameter;
        this.activeDesc = activeDesc;
    }

    public String getActive() {
        return active;
    }
    public boolean isActiveDesc() {
        return activeDesc;
    }

    public boolean getDefaultDESC() {
        return LIST_DEFAULT_DESC;
    }

    public String get(String columnName) {
        return url.toString(LIST_ORDER_COLUMN, columnName, "desc", String.valueOf(active.equals(columnName) ? !activeDesc : LIST_DEFAULT_DESC));
    }
}
