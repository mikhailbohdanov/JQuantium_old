package com.quantium.web.service.social;

import com.quantium.web.bean.view.clientObjects.Image;
import com.quantium.web.dao.social.PhotoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by FREEMAN on 14.10.14.
 */
@Service
public class AlbumPhotoService {

    @Autowired
    private PhotoDAO photoDAO;


    public boolean createUserTable(int serverId, int userId) {


        return false;
    }



    public Image getPhoto(int photoId, int userId) {



        return null;
    }
}
