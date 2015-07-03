package com.quantium.web.controller;

import javax.annotation.PostConstruct;
import java.util.HashMap;

/**
 * Created by FREEMAN on 23.12.14.
 */
public class Pool {
    private static HashMap<Class, Object> controllers = new HashMap<Class, Object>();

    public static <T> T get(Class<T> _class) {
        if (!controllers.containsKey(_class))
            return null;

        return (T) controllers.get(_class);
    }

    @PostConstruct
    public void init() {
        Pool.controllers.put(this.getClass(), this);
    }

}
