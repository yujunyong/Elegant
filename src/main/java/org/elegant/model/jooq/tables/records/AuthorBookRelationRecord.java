/*
 * This file is generated by jOOQ.
 */
package org.elegant.model.jooq.tables.records;


import java.time.LocalDateTime;

import javax.annotation.Generated;

import org.elegant.model.jooq.tables.AuthorBookRelation;
import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.UpdatableRecordImpl;


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
public class AuthorBookRelationRecord extends UpdatableRecordImpl<AuthorBookRelationRecord> implements Record4<Integer, Integer, LocalDateTime, LocalDateTime> {

    private static final long serialVersionUID = 280080997;

    /**
     * Setter for <code>PUBLIC.author_book_relation.book_id</code>. 书本id
     */
    public AuthorBookRelationRecord setBookId(Integer value) {
        set(0, value);
        return this;
    }

    /**
     * Getter for <code>PUBLIC.author_book_relation.book_id</code>. 书本id
     */
    public Integer getBookId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>PUBLIC.author_book_relation.author_id</code>. 作者id
     */
    public AuthorBookRelationRecord setAuthorId(Integer value) {
        set(1, value);
        return this;
    }

    /**
     * Getter for <code>PUBLIC.author_book_relation.author_id</code>. 作者id
     */
    public Integer getAuthorId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>PUBLIC.author_book_relation.create_time</code>. 创建时间
     */
    public AuthorBookRelationRecord setCreateTime(LocalDateTime value) {
        set(2, value);
        return this;
    }

    /**
     * Getter for <code>PUBLIC.author_book_relation.create_time</code>. 创建时间
     */
    public LocalDateTime getCreateTime() {
        return (LocalDateTime) get(2);
    }

    /**
     * Setter for <code>PUBLIC.author_book_relation.update_time</code>.
     */
    public AuthorBookRelationRecord setUpdateTime(LocalDateTime value) {
        set(3, value);
        return this;
    }

    /**
     * Getter for <code>PUBLIC.author_book_relation.update_time</code>.
     */
    public LocalDateTime getUpdateTime() {
        return (LocalDateTime) get(3);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record2<Integer, Integer> key() {
        return (Record2) super.key();
    }

    // -------------------------------------------------------------------------
    // Record4 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row4<Integer, Integer, LocalDateTime, LocalDateTime> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row4<Integer, Integer, LocalDateTime, LocalDateTime> valuesRow() {
        return (Row4) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return AuthorBookRelation.AUTHOR_BOOK_RELATION.BOOK_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return AuthorBookRelation.AUTHOR_BOOK_RELATION.AUTHOR_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<LocalDateTime> field3() {
        return AuthorBookRelation.AUTHOR_BOOK_RELATION.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<LocalDateTime> field4() {
        return AuthorBookRelation.AUTHOR_BOOK_RELATION.UPDATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component1() {
        return getBookId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component2() {
        return getAuthorId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalDateTime component3() {
        return getCreateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalDateTime component4() {
        return getUpdateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getBookId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value2() {
        return getAuthorId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalDateTime value3() {
        return getCreateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalDateTime value4() {
        return getUpdateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AuthorBookRelationRecord value1(Integer value) {
        setBookId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AuthorBookRelationRecord value2(Integer value) {
        setAuthorId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AuthorBookRelationRecord value3(LocalDateTime value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AuthorBookRelationRecord value4(LocalDateTime value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AuthorBookRelationRecord values(Integer value1, Integer value2, LocalDateTime value3, LocalDateTime value4) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached AuthorBookRelationRecord
     */
    public AuthorBookRelationRecord() {
        super(AuthorBookRelation.AUTHOR_BOOK_RELATION);
    }

    /**
     * Create a detached, initialised AuthorBookRelationRecord
     */
    public AuthorBookRelationRecord(Integer bookId, Integer authorId, LocalDateTime createTime, LocalDateTime updateTime) {
        super(AuthorBookRelation.AUTHOR_BOOK_RELATION);

        set(0, bookId);
        set(1, authorId);
        set(2, createTime);
        set(3, updateTime);
    }
}
