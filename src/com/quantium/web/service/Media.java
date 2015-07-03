package com.quantium.web.service;

import com.quantium.web.bean.core.StringTemplate;
import com.quantium.web.bean.view.Pagination;
import com.quantium.web.bean.view.clientObjects.Image;
import com.quantium.web.bean.view.clientObjects.Post;
import com.quantium.web.bean.view.clientObjects.Video;
import com.quantium.web.bean.view.clientObjects.VideoServer;
import com.quantium.web.dao.DAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by FREEMAN on 27.02.15.
 */
@Service
public class Media {
    /* - - - Video - - - */

    /* - - - Post - - - */
    public static final StringTemplate POST_GET_LIST_OF_MODULE              = new StringTemplate("");
    public static final StringTemplate POST_GET_SPECIFIC_BY_ID              = new StringTemplate("");
    public static final StringTemplate POST_GET_SPECIFIC_BY_URL             = new StringTemplate("");

    /* - - - Image - - - */



    public static final RowMapper<Post> POST_PREVIEW                = new RowMapper<Post>() {
        @Override
        public Post mapRow(ResultSet rs, int i) throws SQLException {
            Post post = new Post();



            return post;
        }
    };
    public static final RowMapper<Post> POST_VIEW                   = new RowMapper<Post>() {
        @Override
        public Post mapRow(ResultSet rs, int i) throws SQLException {
            Post post = new Post();



            return post;
        }
    };

    public static final RowMapper<Image> IMAGE_PREVIEW              = new RowMapper<Image>() {
        @Override
        public Image mapRow(ResultSet rs, int i) throws SQLException {
            Image image = new Image();



            return image;
        }
    };


    @Autowired
    private DAO dao;

    @Autowired
    private Util util;

    /* - - - Videos - - - */
    public static final StringTemplate VIDEO_VIEW_UP                        = new StringTemplate("UPDATE `media_videos_{languageCode}` SET `views` = `views` + 1 WHERE `videoId` = :videoId");

    public static final StringTemplate VIDEO_GET_COUNT_OF_MODULE            = new StringTemplate("SELECT COUNT(*) FROM `view_modules_items_link` AS `MIL` JOIN `media_videos_tags_link` AS `VTL` ON `MIL`.`childId` = `VTL`.`parentId` JOIN `media_videos` AS `V` ON `VTL`.`childId` = `V`.`videoId` JOIN `media_videos_{languageCode}` AS `VL` ON `V`.`videoId` = `VL`.`videoId` WHERE `MIL`.`parentId` = :moduleId");
    public static final StringTemplate VIDEO_GET_LIST_OF_MODULE             = new StringTemplate("SELECT `V`.`videoId`, `VL`.`title` AS `videoTitle`, `VI`.`imageId`, `VI`.`nodeId` AS `imageNodeId`, `VI`.`destination` AS `imageDestination`, `VI`.`name` AS `imageName`, `VI`.`fileType` AS `imageFileType` FROM `view_modules_items_link` AS `MIL` JOIN `media_videos_tags_link` AS `VTL` ON `MIL`.`childId` = `VTL`.`parentId` JOIN `media_videos` AS `V` ON `VTL`.`childId` = `V`.`videoId` JOIN `media_videos_{languageCode}` AS `VL` ON `V`.`videoId` = `VL`.`videoId` LEFT JOIN `media_images` AS `VI` ON `VL`.`imageId` = `VI`.`imageId` WHERE `MIL`.`parentId` = :moduleId ORDER BY `VTL`.`order` DESC LIMIT :offset, :limit");
    public static final StringTemplate VIDEO_GET_SPECIFIC                   = new StringTemplate("SELECT `V`.`videoId`, `VL`.`title` AS `videoTitle`, `VL`.`text` AS `videoText`, `VL`.`serverId` AS `videoServerId`, `VL`.`serverVideoId` AS `videoServerVideoId`, `VL`.`views` AS `videoViews` FROM `media_videos` AS `V` JOIN `media_videos_{languageCode}` AS `VL` ON `V`.`videoId` = `VL`.`videoId` LEFT JOIN `media_images` AS `VI` ON `VL`.`imageId` = `VI`.`imageId` WHERE `V`.`videoId` = :videoId");

    public static final RowMapper<Video> VIDEO_PREVIEW              = new RowMapper<Video>() {
        @Override
        public Video mapRow(ResultSet rs, int i) throws SQLException {
            Video video = new Video();

            video
                    .setVideoId(rs.getInt("videoId"))
                    .setTitle(rs.getString("videoTitle"))
                    .setImage(new Image())
                    .setImageId(rs.getInt("imageId"))
                    .setNodeId(rs.getInt("imageNodeId"))
                    .setDestination(rs.getString("imageDestination"))
                    .setName(rs.getString("imageName"))
                    .setFileType(rs.getString("imageFileType"));

            return video;
        }
    };
    public static final RowMapper<Video> VIDEO_VIEW                 = new RowMapper<Video>() {
        @Override
        public Video mapRow(ResultSet rs, int i) throws SQLException {
            Video video = new Video();

            video
                    .setVideoId(rs.getInt("videoId"))
                    .setTitle(rs.getString("videoTitle"))
                    .setText(rs.getString("videoText"))
                    .setServerId(rs.getInt("videoServerId"))
                    .setServerVideoId(rs.getString("videoServerVideoId"))
                    .setViews(rs.getInt("videoViews"));

            return video;
        }
    };

    public int videoCountOfModule(int moduleId, String languageCode) {
        try {
            return dao.exec(
                    VIDEO_GET_COUNT_OF_MODULE.toString(languageCode),
                    (new MapSqlParameterSource())
                            .addValue("moduleId", moduleId),
                    Integer.class
            );
        } catch (Exception e) {
            return 0;
        }
    }
    public ArrayList<Video> videoListOfModule(int moduleId, Pagination pagination, String languageCode) {
        try {
            return (ArrayList<Video>) dao.getRowList(
                    VIDEO_GET_LIST_OF_MODULE.toString(languageCode),
                    (new MapSqlParameterSource())
                            .addValue("moduleId", moduleId)
                            .addValue("offset", pagination.getOffset())
                            .addValue("limit", pagination.getPerPage()),
                    VIDEO_PREVIEW
            );
        } catch (Exception e) {
            return null;
        }
    }
    public Video videoGet(int videoId, String languageCode) {
        try {
            return dao.getRow(
                    VIDEO_GET_SPECIFIC.toString(languageCode),
                    (new MapSqlParameterSource())
                            .addValue("videoId", videoId),
                    VIDEO_VIEW
            );
        } catch (Exception e) {
            return null;
        }
    }
    public void videoViewUp(int videoId, String languageCode) {
        try {
            dao.exec(
                    VIDEO_VIEW_UP.toString(languageCode),
                    (new MapSqlParameterSource())
                            .addValue("videoId", videoId)
            );
        } catch (Exception e) {}
    }

    /* - - - Video servers - - - */
    public static final RowMapper<VideoServer> VIDEO_SERVER         = new RowMapper<VideoServer>() {
        @Override
        public VideoServer mapRow(ResultSet rs, int i) throws SQLException {
            VideoServer vs = new VideoServer();

            vs
                    .setServerId(rs.getInt("serverId"))
                    .setName(rs.getString("name"))
                    .setPlayerUrl(rs.getString("playerUrlPattern"))
                    .setImageUrl(rs.getString("imageUrlPattern"))
                    .setLinks(rs.getString("linkPatterns"));

            return vs;
        }
    };
    public ArrayList<VideoServer> videoServerGetList() {
        return (ArrayList<VideoServer>) util.getRowList("media_videos_servers", VIDEO_SERVER);
    }

}
