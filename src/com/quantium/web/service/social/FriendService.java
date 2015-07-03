package com.quantium.web.service.social;

import com.quantium.web.dao.social.FriendDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by FREEMAN on 22.10.14.
 */
@Service
public class FriendService {

    @Autowired
    private FriendDAO friendDAO;

    public boolean createUserTable(int serverId, int userId) {


        return false;
    }
}
