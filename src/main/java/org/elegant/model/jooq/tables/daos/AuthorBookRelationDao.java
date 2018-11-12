/*
 * This file is generated by jOOQ.
 */
package org.elegant.model.jooq.tables.daos;


import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.Generated;

import org.elegant.model.jooq.tables.AuthorBookRelation;
import org.elegant.model.jooq.tables.records.AuthorBookRelationRecord;
import org.jooq.Configuration;
import org.jooq.Record2;
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
public class AuthorBookRelationDao extends DAOImpl<AuthorBookRelationRecord, org.elegant.model.jooq.tables.pojos.AuthorBookRelation, Record2<Integer, Integer>> {

    /**
     * Create a new AuthorBookRelationDao without any configuration
     */
    public AuthorBookRelationDao() {
        super(AuthorBookRelation.AUTHOR_BOOK_RELATION, org.elegant.model.jooq.tables.pojos.AuthorBookRelation.class);
    }

    /**
     * Create a new AuthorBookRelationDao with an attached configuration
     */
    public AuthorBookRelationDao(Configuration configuration) {
        super(AuthorBookRelation.AUTHOR_BOOK_RELATION, org.elegant.model.jooq.tables.pojos.AuthorBookRelation.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Record2<Integer, Integer> getId(org.elegant.model.jooq.tables.pojos.AuthorBookRelation object) {
        return compositeKeyRecord(object.getBookId(), object.getAuthorId());
    }

    /**
     * Fetch records that have <code>book_id IN (values)</code>
     */
    public List<org.elegant.model.jooq.tables.pojos.AuthorBookRelation> fetchByBookId(Integer... values) {
        return fetch(AuthorBookRelation.AUTHOR_BOOK_RELATION.BOOK_ID, values);
    }

    /**
     * Fetch records that have <code>author_id IN (values)</code>
     */
    public List<org.elegant.model.jooq.tables.pojos.AuthorBookRelation> fetchByAuthorId(Integer... values) {
        return fetch(AuthorBookRelation.AUTHOR_BOOK_RELATION.AUTHOR_ID, values);
    }

    /**
     * Fetch records that have <code>create_time IN (values)</code>
     */
    public List<org.elegant.model.jooq.tables.pojos.AuthorBookRelation> fetchByCreateTime(LocalDateTime... values) {
        return fetch(AuthorBookRelation.AUTHOR_BOOK_RELATION.CREATE_TIME, values);
    }

    /**
     * Fetch records that have <code>update_time IN (values)</code>
     */
    public List<org.elegant.model.jooq.tables.pojos.AuthorBookRelation> fetchByUpdateTime(LocalDateTime... values) {
        return fetch(AuthorBookRelation.AUTHOR_BOOK_RELATION.UPDATE_TIME, values);
    }
}