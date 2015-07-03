package com.quantium.web.bean.view.clientObjects;

import java.sql.Timestamp;

/**
 * Created by FREEMAN on 02.10.14.
 */
public class Image {
    public static enum FileType {
        JPG,
        PNG,
        GIF,
        BMP
    }

    private int imageId;
    private int nodeId;
    private String destination;
    private String name;
    private FileType fileType;
    private String originalName;
    private Timestamp created;

    public int getImageId() {
        return imageId;
    }
    public Image setImageId(int imageId) {
        this.imageId = imageId;
        return this;
    }

    public int getNodeId() {
        return nodeId;
    }
    public Image setNodeId(int nodeId) {
        this.nodeId = nodeId;
        return this;
    }

    public String getDestination() {
        return destination;
    }
    public Image setDestination(String destination) {
        this.destination = destination;
        return this;
    }

    public String getName() {
        return name;
    }
    public Image setName(String name) {
        this.name = name;
        return this;
    }

    public FileType getFileType() {
        return fileType;
    }
    public Image setFileType(FileType fileType) {
        this.fileType = fileType;
        return this;
    }
    public Image setFileType(String fileType) {
        if (fileType != null)
            this.fileType = FileType.valueOf(fileType);

        return this;
    }

    public String getOriginalName() {
        return originalName;
    }
    public Image setOriginalName(String originalName) {
        this.originalName = originalName;
        return this;
    }

    public Timestamp getCreated() {
        return created;
    }
    public Image setCreated(Timestamp created) {
        this.created = created;
        return this;
    }
}
