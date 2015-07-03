package com.quantium.web.bean.view.clientObjects;

import com.quantium.web.bean.core.StringPattern;
import com.quantium.web.bean.core.StringTemplate;

import java.util.ArrayList;

/**
 * Created by FREEMAN on 29.01.15.
 */
public class VideoServer {
    private int serverId;
    private String name;
    private StringTemplate playerUrl;
    private StringTemplate imageUrl;
    private ArrayList<StringPattern> links;

    public int getServerId() {
        return serverId;
    }
    public VideoServer setServerId(int serverId) {
        this.serverId = serverId;
        return this;
    }

    public String getName() {
        return name;
    }
    public VideoServer setName(String name) {
        this.name = name;
        return this;
    }

    public StringTemplate getPlayerUrl() {
        return playerUrl;
    }
    public VideoServer setPlayerUrl(StringTemplate playerUrl) {
        this.playerUrl = playerUrl;
        return this;
    }
    public VideoServer setPlayerUrl(String playerUrl) {
        this.playerUrl = new StringTemplate(playerUrl);
        return this;
    }

    public StringTemplate getImageUrl() {
        return imageUrl;
    }
    public String getImageUrl(String videoServerId) {
        return imageUrl.toStringObject(videoServerId);
    }
    public VideoServer setImageUrl(StringTemplate imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }
    public VideoServer setImageUrl(String imageUrl) {
        this.imageUrl = new StringTemplate(imageUrl);
        return this;
    }

    public ArrayList<StringPattern> getLinks() {
        return links;
    }
    public boolean matchLink(String link) {
        if (links == null)
            return false;

        for (StringPattern pattern : links)
            if (pattern.match(link))
                return true;

        return false;
    }
    public String getVideoServerId(String link) {
        if (links == null)
            return null;

        for (StringPattern pattern : links)
            if (pattern.match(link))
                return pattern.getValues(link).get("videoServerId");

        return null;
    }
    public VideoServer setLinks(String links) {
        this.links = new ArrayList<StringPattern>();

        for (final String link : links.split(";"))
            this.links.add(new StringPattern(link));

        return this;
    }
}
