/*
 * This file is generated by jOOQ.
 */
package org.elegant.model.jooq.tables.daos;


import java.util.List;

import javax.annotation.Generated;

import org.elegant.model.jooq.tables.Media;
import org.elegant.model.jooq.tables.records.MediaRecord;
import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


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
public class MediaDao extends DAOImpl<MediaRecord, org.elegant.model.jooq.tables.pojos.Media, String> {

    /**
     * Create a new MediaDao without any configuration
     */
    public MediaDao() {
        super(Media.MEDIA, org.elegant.model.jooq.tables.pojos.Media.class);
    }

    /**
     * Create a new MediaDao with an attached configuration
     */
    public MediaDao(Configuration configuration) {
        super(Media.MEDIA, org.elegant.model.jooq.tables.pojos.Media.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getId(org.elegant.model.jooq.tables.pojos.Media object) {
        return object.getMediaId();
    }

    /**
     * Fetch records that have <code>media_id IN (values)</code>
     */
    public List<org.elegant.model.jooq.tables.pojos.Media> fetchByMediaId(String... values) {
        return fetch(Media.MEDIA.MEDIA_ID, values);
    }

    /**
     * Fetch a unique record that has <code>media_id = value</code>
     */
    public org.elegant.model.jooq.tables.pojos.Media fetchOneByMediaId(String value) {
        return fetchOne(Media.MEDIA.MEDIA_ID, value);
    }

    /**
     * Fetch records that have <code>content IN (values)</code>
     */
    public List<org.elegant.model.jooq.tables.pojos.Media> fetchByContent(byte[]... values) {
        return fetch(Media.MEDIA.CONTENT, values);
    }

    /**
     * Fetch records that have <code>media_type IN (values)</code>
     */
    public List<org.elegant.model.jooq.tables.pojos.Media> fetchByMediaType(String... values) {
        return fetch(Media.MEDIA.MEDIA_TYPE, values);
    }

    /**
     * Fetch records that have <code>file_extension IN (values)</code>
     */
    public List<org.elegant.model.jooq.tables.pojos.Media> fetchByFileExtension(String... values) {
        return fetch(Media.MEDIA.FILE_EXTENSION, values);
    }
}
