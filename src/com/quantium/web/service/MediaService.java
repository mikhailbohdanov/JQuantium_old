package com.quantium.web.service;

import com.quantium.web.bean.view.clientObjects.Video;
import com.quantium.web.bean.view.clientObjects.VideoServer;
import com.quantium.web.dao.MediaIO;
import com.quantium.web.dao.UtilityDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by FREEMAN on 26.01.15.
 */
@Service
public class MediaService implements MediaIO {
    private static final HashMap<Integer, VideoServer> videoServers     = new HashMap<Integer, VideoServer>();

    public static VideoServer getVideoServer(int serverId) {
        if (videoServers.containsKey(serverId))
            return videoServers.get(serverId);

        VideoServer server = MainServices.mediaService.videoServerGet(serverId);

        if (server != null)
            videoServers.put(serverId, server);

        return server;
    }

    @Autowired
    private UtilityDAO utilityDAO;

    @PostConstruct
    public void init() {
        ArrayList<VideoServer> servers = videoServerGetList();

        for (VideoServer server : servers)
            videoServers.put(server.getServerId(), server);
    }

    /* - - - VIDEO SERVERS - - - */
    public VideoServer videoServerByLink(String link) {
        VideoServer videoServer;

        for (Map.Entry<Integer, VideoServer> entry : videoServers.entrySet()) {
            videoServer = entry.getValue();

            if (videoServer.matchLink(link))
                return videoServer;
        }

        return null;
    }
    public VideoServer videoServerGet(int serverId) {
        try {
            return utilityDAO.getRow(
                    VIDEO_SERVER_GET_SPECIFIC,
                    (new MapSqlParameterSource())
                            .addValue("serverId", serverId),
                    VIDEO_SERVER
            );
        } catch (Exception e) {
            return null;
        }
    }
    public ArrayList<VideoServer> videoServerGetList() {
        try {
            return (ArrayList<VideoServer>) utilityDAO.getRowList(
                    VIDEO_SERVER_GET_LIST,
                    VIDEO_SERVER
            );
        } catch (Exception e) {
            return new ArrayList<VideoServer>();
        }
    }

    /* - - - VIDEOS - - - */
    public int videoCountOfModule(int moduleId, String language) {
        try {
            return utilityDAO.execSQL(
                    VIDEO_COUNT_OF_MODULE.toString(language),
                    (new MapSqlParameterSource())
                            .addValue("moduleId", moduleId),
                    Integer.class
            );
        } catch (Exception e) {
            return 0;
        }
    }
    public ArrayList<Video> videoGetPreviewListOfModule(int moduleId, int offset, int limit, String language) {
        try {
             return (ArrayList<Video>) utilityDAO.getRowList(
                     VIDEO_GET_PREVIEW_LIST_OF_MODULE.toString(language),
                    (new MapSqlParameterSource())
                            .addValue("moduleId", moduleId)
                            .addValue("offset", offset)
                            .addValue("limit", limit),
                    VIDEO_PREVIEW
            );
        } catch (Exception e) {
            return new ArrayList<Video>();
        }
    }
    public Video videoGetView(int videoId, String language) {


        try {
            return utilityDAO.getRow(
                    VIDEO_GET_VIEW.toString(language),
                    (new MapSqlParameterSource())
                            .addValue("videoId", videoId),
                    VIDEO_VIEW
            );
        } catch (Exception e) {
            return null;
        }
    }
    public boolean videoUpdateViews(int videoId, String language) {
        try {
            utilityDAO.execSQL(
                    VIDEO_UPDATE_VIEWS.toString(language),
                    (new MapSqlParameterSource())
                            .addValue("videoId", videoId)
            );
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    /* - - - POSTS - - - */
    public int postCountOfModule(int moduleId, String language) {
        try {
            return utilityDAO.execSQL(
                    VIDEO_COUNT_OF_MODULE.toString(language),
                    (new MapSqlParameterSource())
                            .addValue("moduleId", moduleId),
                    Integer.class
            );
        } catch (Exception e) {
            return 0;
        }
    }

}
