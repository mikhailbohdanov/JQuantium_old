package com.quantium.web.bean.social;

import java.sql.Timestamp;

/**
 * Created by FREEMAN on 22.10.14.
 */
public class Friend {
    public enum Type {
        FRIEND,
        NOT_FRIEND,
        MY_REQUEST,
        ME_REQUEST
    }

    private int id;
    private int userId;
    private Type type;
    private Timestamp created;
    private Timestamp modified;


}
