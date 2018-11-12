/*
 * This file is generated by jOOQ.
 */
package org.elegant.model.jooq.tables.daos;


import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.Generated;

import org.elegant.model.jooq.tables.Property;
import org.elegant.model.jooq.tables.records.PropertyRecord;
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
public class PropertyDao extends DAOImpl<PropertyRecord, org.elegant.model.jooq.tables.pojos.Property, String> {

    /**
     * Create a new PropertyDao without any configuration
     */
    public PropertyDao() {
        super(Property.PROPERTY, org.elegant.model.jooq.tables.pojos.Property.class);
    }

    /**
     * Create a new PropertyDao with an attached configuration
     */
    public PropertyDao(Configuration configuration) {
        super(Property.PROPERTY, org.elegant.model.jooq.tables.pojos.Property.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getId(org.elegant.model.jooq.tables.pojos.Property object) {
        return object.getPropName();
    }

    /**
     * Fetch records that have <code>prop_name IN (values)</code>
     */
    public List<org.elegant.model.jooq.tables.pojos.Property> fetchByPropName(String... values) {
        return fetch(Property.PROPERTY.PROP_NAME, values);
    }

    /**
     * Fetch a unique record that has <code>prop_name = value</code>
     */
    public org.elegant.model.jooq.tables.pojos.Property fetchOneByPropName(String value) {
        return fetchOne(Property.PROPERTY.PROP_NAME, value);
    }

    /**
     * Fetch records that have <code>prop_value IN (values)</code>
     */
    public List<org.elegant.model.jooq.tables.pojos.Property> fetchByPropValue(String... values) {
        return fetch(Property.PROPERTY.PROP_VALUE, values);
    }

    /**
     * Fetch records that have <code>create_time IN (values)</code>
     */
    public List<org.elegant.model.jooq.tables.pojos.Property> fetchByCreateTime(LocalDateTime... values) {
        return fetch(Property.PROPERTY.CREATE_TIME, values);
    }

    /**
     * Fetch records that have <code>update_time IN (values)</code>
     */
    public List<org.elegant.model.jooq.tables.pojos.Property> fetchByUpdateTime(LocalDateTime... values) {
        return fetch(Property.PROPERTY.UPDATE_TIME, values);
    }
}