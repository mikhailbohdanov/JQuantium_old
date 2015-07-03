package com.quantium.web.service.common;

import com.quantium.web.bean.core.Route;
import com.quantium.web.errors.exceptions.view.PageNotFoundException;
import com.quantium.web.service.Core;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

/**
 * Created by user on 10.05.2015.
 */
public class Router {
    private static final ArrayList<Route> routeList         = new ArrayList<>();


//    private static final ArrayList<Route> routes            = new ArrayList<Route>();
//
//    // - - - Routes management
//    public static void clearRoutes() {
//        routes.clear();
//    }
//    public static void addRoute(Route route) {
//        routes.add(route);
//    }
//    public static Route findRoute(String url) throws PageNotFoundException {
//        if (url == null)
//            throw new PageNotFoundException();
//
//        for (Route route : routes)
//            if (route.matchUrl(url))
//                return route;
//
//        throw new PageNotFoundException();
//    }
//    public static void removeRoute(Route route) {
//        routes.remove(route);
//    }
//



    private static Core core;
    @Autowired
    public void setCore(Core core) {
        Router.core = core;
    }
}
