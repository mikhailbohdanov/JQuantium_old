package com.quantium.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by FREEMAN on 24.02.15.
 */
@Service
public class Services {

    public static Util util;
    @Autowired
    public void setUtil(Util util) {
        Services.util   = util;
    }

    public static Core core;
    @Autowired
    public void setCore(Core core) {
        Services.core = core;
    }

    public static Users users;
    @Autowired
    public void setUsers(Users users) {
        Services.users = users;
    }

    public static Security security;
    @Autowired
    public void setSecurity(@Qualifier("security") Security security) {
        Services.security = security;
    }

    public static View view;
    @Autowired
    public void setView(View view) {
        Services.view = view;
    }

    public static Media media;
    @Autowired
    public void setMedia(Media media) {
        Services.media = media;
    }


    public static Social social;
    @Autowired
    public void setSocial(Social social) {
        Services.social = social;
    }


}
