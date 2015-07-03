package com.quantium.web.bean.view.clientObjects;

import com.quantium.web.bean.view.PageContext;

import java.sql.Timestamp;

/**
 * Created by Михаил on 26.08.14.
 */
public class Video {
    private int videoId;
    private Timestamp created;

    private String title;
    private String text;
    private int serverId;
    private String serverVideoId;
    private int imageId;
    private int views;

    private Image image;

    public int getVideoId() {
        return videoId;
    }
    public Video setVideoId(int videoId) {
        this.videoId = videoId;
        return this;
    }

    public Timestamp getCreated() {
        return created;
    }
    public Video setCreated(Timestamp created) {
        this.created = created;
        return this;
    }

    public String getTitle() {
        return title;
    }
    public Video setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getText() {
        return text;
    }
    public Video setText(String text) {
        this.text = text;
        return this;
    }

    public int getServerId() {
        return serverId;
    }
    public Video setServerId(int serverId) {
        this.serverId = serverId;
        return this;
    }

    public String getServerVideoId() {
        return serverVideoId;
    }
    public Video setServerVideoId(String serverVideoId) {
        this.serverVideoId = serverVideoId;
        return this;
    }

    public int getImageId() {
        return imageId;
    }
    public Video setImageId(int imageId) {
        this.imageId = imageId;
        return this;
    }

    public int getViews() {
        return views;
    }
    public Video setViews(int views) {
        this.views = views;
        return this;
    }

    public Image getImage() {
        return image;
    }
    public Image setImage(Image image) {
        this.image = image;
        return image;
    }

    public String getPlayerUrl() {
        VideoServer vs = PageContext.videoServerGet(serverId);

        return vs.getPlayerUrl().toStringObject(this);
    }
}
