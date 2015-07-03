package com.quantium.web.service;

import com.quantium.web.service.social.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by FREEMAN on 25.02.15.
 */
@Service
public class Social {

    public static Profile profile;
    @Autowired
    public void setProfile(Profile profile) {
        Social.profile = profile;
    }


}
