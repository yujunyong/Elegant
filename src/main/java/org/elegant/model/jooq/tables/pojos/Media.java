/*
 * This file is generated by jOOQ.
 */
package org.elegant.model.jooq.tables.pojos;


import java.io.Serializable;

import javax.annotation.Generated;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.5"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Media implements Serializable {

    private static final long serialVersionUID = -289122612;

    private String mediaId;
    private byte[] content;
    private String mediaType;
    private String fileExtension;

    public Media() {}

    public Media(Media value) {
        this.mediaId = value.mediaId;
        this.content = value.content;
        this.mediaType = value.mediaType;
        this.fileExtension = value.fileExtension;
    }

    public Media(
        String mediaId,
        byte[] content,
        String mediaType,
        String fileExtension
    ) {
        this.mediaId = mediaId;
        this.content = content;
        this.mediaType = mediaType;
        this.fileExtension = fileExtension;
    }

    public String getMediaId() {
        return this.mediaId;
    }

    public Media setMediaId(String mediaId) {
        this.mediaId = mediaId;
        return this;
    }

    public byte[] getContent() {
        return this.content;
    }

    public Media setContent(byte... content) {
        this.content = content;
        return this;
    }

    public String getMediaType() {
        return this.mediaType;
    }

    public Media setMediaType(String mediaType) {
        this.mediaType = mediaType;
        return this;
    }

    public String getFileExtension() {
        return this.fileExtension;
    }

    public Media setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Media (");

        sb.append(mediaId);
        sb.append(", ").append("[binary...]");
        sb.append(", ").append(mediaType);
        sb.append(", ").append(fileExtension);

        sb.append(")");
        return sb.toString();
    }
}
