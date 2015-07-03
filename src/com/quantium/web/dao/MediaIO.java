package com.quantium.web.dao;

import com.quantium.web.bean.core.StringTemplate;
import com.quantium.web.bean.view.clientObjects.Image;
import com.quantium.web.bean.view.clientObjects.Video;
import com.quantium.web.bean.view.clientObjects.VideoServer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by FREEMAN on 26.01.15.
 */
public interface MediaIO {

    /* - - - VIDEO SERVERS - - - */

    // - Create

    // - Get
    public static final String VIDEO_SERVER_GET_LIST                        = "SELECT `serverId`, `name`, `playerUrlPattern` AS `playerUrl`, `imageUrlPattern` AS `imageUrl`, `linkPatterns` AS `links` FROM `media_videos_servers`";
    public static final String VIDEO_SERVER_GET_SPECIFIC                    = "SELECT `serverId`, `name`, `playerUrlPattern` AS `playerUrl`, `imageUrlPattern` AS `imageUrl`, `linkPatterns` AS `links` FROM `media_videos_servers` WHERE `serverId` = :serverId";

    // - Update

    // - Delete

    // - Mappers
    public static final RowMapper<VideoServer> VIDEO_SERVER                 = new RowMapper<VideoServer>() {
        @Override
        public VideoServer mapRow(ResultSet rs, int i) throws SQLException {
            VideoServer server = new VideoServer();

            server.setServerId(rs.getInt("serverId"));
            server.setName(rs.getString("name"));
            server.setPlayerUrl(rs.getString("playerUrl"));
            server.setImageUrl(rs.getString("imageUrl"));
            server.setLinks(rs.getString("links"));

            return server;
        }
    };

    /* - - - VIDEOS - - - */

    // - Create

    // - Get
    public static final StringTemplate VIDEO_COUNT_OF_MODULE                = new StringTemplate("SELECT COUNT(*) FROM `media_videos` AS `V` JOIN `media_videos_tags_link` AS `VTL` ON `VTL`.`elementId` = `V`.`videoId` JOIN `media_videos_{langCode}` AS `VL` ON `V`.`videoId` = `VL`.`videoId` JOIN `view_modules_items_link` AS `MIL` ON `MIL`.`ownerId` = `VTL`.`tagId` WHERE `MIL`.`moduleId` = :moduleId");
    public static final StringTemplate VIDEO_GET_PREVIEW_LIST_OF_MODULE     = new StringTemplate("SELECT `V`.`videoId`, `VL`.`title` AS `videoTitle`, `VI`.`imageId`, `VI`.`nodeId` AS `imageNodeId`, `VI`.`destination` AS `imageDestination`, `VI`.`name` AS `imageName`, `VI`.`fileType` AS `imageFileType`FROM `media_videos` AS `V` JOIN `media_videos_tags_link` AS `VTL` ON `VTL`.`elementId` = `V`.`videoId` JOIN `media_videos_{langCode}` AS `VL` ON `V`.`videoId` = `VL`.`videoId` LEFT JOIN `media_images` AS `VI` ON `VL`.`imageId` = `VI`.`imageId` JOIN `view_modules_items_link` AS `MIL` ON `MIL`.`ownerId` = `VTL`.`tagId` WHERE `MIL`.`moduleId` = :moduleId ORDER BY `VTL`.`ORDER` DESC LIMIT :offset, :limit");
    public static final StringTemplate VIDEO_GET_VIEW                       = new StringTemplate("SELECT `V`.`videoId`, `VL`.`title`, `VL`.`text`, `VL`.`serverId`, `VL`.`serverVideoId`, `VL`.`views` FROM `media_videos` AS `V` INNER JOIN `media_videos_{langCode}` AS `VL` ON `V`.`videoId` = `VL`.`videoId` WHERE `V`.`videoId` = :videoId");

    // - Update
    public static final StringTemplate VIDEO_UPDATE_VIEWS                   = new StringTemplate("UPDATE `media_videos_{langCode} SET `views` = `views` + 1 WHERE `videoId` = :videoId");

    // - Delete

    // - Mappers
    public static final RowMapper<Video> VIDEO_PREVIEW                      = new RowMapper<Video>() {
        @Override
        public Video mapRow(ResultSet rs, int i) throws SQLException {
            Video video     = new Video();

            video.setVideoId(rs.getInt("videoId"));
            video.setTitle(rs.getString("videoTitle"));

            if (rs.getInt("imageId") > 0) {
                Image image     = new Image();

                image.setImageId(rs.getInt("imageId"));
                image.setNodeId(rs.getInt("imageNodeId"));
                image.setDestination(rs.getString("imageDestination"));
                image.setName(rs.getString("imageName"));
                image.setFileType(rs.getString("imageFileType"));

                video.setImage(image);
            }

            return video;
        }
    };
    public static final RowMapper<Video> VIDEO_VIEW                     = new RowMapper<Video>() {
        @Override
        public Video mapRow(ResultSet rs, int i) throws SQLException {
            Video video     = new Video();

            video.setVideoId(rs.getInt("videoId"));
            video.setTitle(rs.getString("title"));
            video.setText(rs.getString("text"));

            video.setServerId(rs.getInt("serverId"));
            video.setServerVideoId(rs.getString("serverVideoId"));

            video.setViews(rs.getInt("views"));

            return video;
        }
    };



    /* - - - POSTS - - - */

    // - Create

    // - Get
    public static final StringTemplate POSTS_COUNT_OF_MODULE                = new StringTemplate("SELECT COUNT(*) FROM `media_videos` AS `V` JOIN `media_videos_tags_link` AS `VTL` ON `VTL`.`elementId` = `V`.`videoId` JOIN `media_videos_{langCode}` AS `VL` ON `V`.`videoId` = `VL`.`videoId` JOIN `view_modules_items_link` AS `MIL` ON `MIL`.`ownerId` = `VTL`.`tagId` WHERE `MIL`.`moduleId` = :moduleId");
    public static final StringTemplate POSTS_GET_PREVIEW_LIST_OF_MODULE     = new StringTemplate("SELECT `V`.`videoId`, `VL`.`title` AS `videoTitle`, `VI`.`imageId`, `VI`.`nodeId` AS `imageNodeId`, `VI`.`destination` AS `imageDestination`, `VI`.`name` AS `imageName`, `VI`.`fileType` AS `imageFileType`FROM `media_videos` AS `V` JOIN `media_videos_tags_link` AS `VTL` ON `VTL`.`elementId` = `V`.`videoId` JOIN `media_videos_{langCode}` AS `VL` ON `V`.`videoId` = `VL`.`videoId` LEFT JOIN `media_images` AS `VI` ON `VL`.`imageId` = `VI`.`imageId` JOIN `view_modules_items_link` AS `MIL` ON `MIL`.`ownerId` = `VTL`.`tagId` WHERE `MIL`.`moduleId` = :moduleId ORDER BY `VTL`.`ORDER` DESC LIMIT :offset, :limit");
    public static final StringTemplate POSTS_GET_VIEW_LIST_OF_MODULE        = new StringTemplate("SELECT `V`.`videoId`, `VL`.`title` AS `videoTitle`, `VL`.`text` AS `videoText`, `VL`.`serverId` AS `videoServerId`, `VL`.`serverVideoId` AS `videoServerVideoId`, `VL`.`views` AS `videoViews`, `VI`.`imageId`, `VI`.`nodeId` AS `imageNodeId`, `VI`.`destination` AS `imageDestination`, `VI`.`name` AS `imageName`, `VI`.`fileType` AS `imageFileType`FROM `media_videos` AS `V` JOIN `media_videos_tags_link` AS `VTL` ON `VTL`.`elementId` = `V`.`videoId` JOIN `media_videos_{langCode}` AS `VL` ON `V`.`videoId` = `VL`.`videoId` LEFT JOIN `media_images` AS `VI` ON `VL`.`imageId` = `VI`.`imageId` JOIN `view_modules_items_link` AS `MIL` ON `MIL`.`ownerId` = `VTL`.`tagId` WHERE `MIL`.`moduleId` = :moduleId ORDER BY `VTL`.`ORDER` DESC LIMIT :offset, :limit");

    // - Update

    // - Delete

    // - Mappers

}
