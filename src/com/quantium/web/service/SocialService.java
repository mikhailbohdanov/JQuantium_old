package com.quantium.web.service;

import com.quantium.web.bean.social.Friend;
import com.quantium.web.service.social.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by FREEMAN on 29.09.14.
 */

@Service
public class SocialService {

    public static ProfileService profileService;
    @Autowired
    public void setProfileService(ProfileService profileService) {
        SocialService.profileService = profileService;
    }

    public static FriendService friendService;
    @Autowired
    public void setFriendService(FriendService friendService) {
        SocialService.friendService = friendService;
    }

    public static MessageService messageService;
    @Autowired
    public void setMessageService(MessageService messageService) {
        SocialService.messageService = messageService;
    }

    public static AlbumPhotoService albumPhotoService;
    @Autowired
    public void setPhotoService(AlbumPhotoService albumPhotoService) {
        SocialService.albumPhotoService = albumPhotoService;
    }
    public static UserListService userListService;
    @Autowired
    public void setUserListService(UserListService userListService) {
        SocialService.userListService = userListService;
    }


    public static boolean createUserTables(int serverId, int userId) {
        profileService.createUserTable(serverId, userId);
        friendService.createUserTable(serverId, userId);
        messageService.createUserTable(serverId, userId);
        albumPhotoService.createUserTable(serverId, userId);

        return true;
    }




}
