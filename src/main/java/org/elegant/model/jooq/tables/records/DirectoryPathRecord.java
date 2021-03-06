/*
 * This file is generated by jOOQ.
 */
package org.elegant.model.jooq.tables.records;


import java.time.LocalDateTime;

import javax.annotation.Generated;

import org.elegant.model.jooq.tables.DirectoryPath;
import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Record5;
import org.jooq.Row5;
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
public class DirectoryPathRecord extends UpdatableRecordImpl<DirectoryPathRecord> implements Record5<Integer, Integer, Integer, LocalDateTime, LocalDateTime> {

    private static final long serialVersionUID = 1128205559;

    /**
     * Setter for <code>PUBLIC.directory_path.ancestor</code>. 祖先目录id
     */
    public DirectoryPathRecord setAncestor(Integer value) {
        set(0, value);
        return this;
    }

    /**
     * Getter for <code>PUBLIC.directory_path.ancestor</code>. 祖先目录id
     */
    public Integer getAncestor() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>PUBLIC.directory_path.dir_id</code>. 后代目录id
     */
    public DirectoryPathRecord setDirId(Integer value) {
        set(1, value);
        return this;
    }

    /**
     * Getter for <code>PUBLIC.directory_path.dir_id</code>. 后代目录id
     */
    public Integer getDirId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>PUBLIC.directory_path.path_length</code>. 目录的深度
     */
    public DirectoryPathRecord setPathLength(Integer value) {
        set(2, value);
        return this;
    }

    /**
     * Getter for <code>PUBLIC.directory_path.path_length</code>. 目录的深度
     */
    public Integer getPathLength() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>PUBLIC.directory_path.create_time</code>. 创建时间
     */
    public DirectoryPathRecord setCreateTime(LocalDateTime value) {
        set(3, value);
        return this;
    }

    /**
     * Getter for <code>PUBLIC.directory_path.create_time</code>. 创建时间
     */
    public LocalDateTime getCreateTime() {
        return (LocalDateTime) get(3);
    }

    /**
     * Setter for <code>PUBLIC.directory_path.update_time</code>. 更新时间
     */
    public DirectoryPathRecord setUpdateTime(LocalDateTime value) {
        set(4, value);
        return this;
    }

    /**
     * Getter for <code>PUBLIC.directory_path.update_time</code>. 更新时间
     */
    public LocalDateTime getUpdateTime() {
        return (LocalDateTime) get(4);
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
    // Record5 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row5<Integer, Integer, Integer, LocalDateTime, LocalDateTime> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row5<Integer, Integer, Integer, LocalDateTime, LocalDateTime> valuesRow() {
        return (Row5) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return DirectoryPath.DIRECTORY_PATH.ANCESTOR;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return DirectoryPath.DIRECTORY_PATH.DIR_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return DirectoryPath.DIRECTORY_PATH.PATH_LENGTH;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<LocalDateTime> field4() {
        return DirectoryPath.DIRECTORY_PATH.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<LocalDateTime> field5() {
        return DirectoryPath.DIRECTORY_PATH.UPDATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component1() {
        return getAncestor();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component2() {
        return getDirId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component3() {
        return getPathLength();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalDateTime component4() {
        return getCreateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalDateTime component5() {
        return getUpdateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getAncestor();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value2() {
        return getDirId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value3() {
        return getPathLength();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalDateTime value4() {
        return getCreateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalDateTime value5() {
        return getUpdateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DirectoryPathRecord value1(Integer value) {
        setAncestor(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DirectoryPathRecord value2(Integer value) {
        setDirId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DirectoryPathRecord value3(Integer value) {
        setPathLength(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DirectoryPathRecord value4(LocalDateTime value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DirectoryPathRecord value5(LocalDateTime value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DirectoryPathRecord values(Integer value1, Integer value2, Integer value3, LocalDateTime value4, LocalDateTime value5) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached DirectoryPathRecord
     */
    public DirectoryPathRecord() {
        super(DirectoryPath.DIRECTORY_PATH);
    }

    /**
     * Create a detached, initialised DirectoryPathRecord
     */
    public DirectoryPathRecord(Integer ancestor, Integer dirId, Integer pathLength, LocalDateTime createTime, LocalDateTime updateTime) {
        super(DirectoryPath.DIRECTORY_PATH);

        set(0, ancestor);
        set(1, dirId);
        set(2, pathLength);
        set(3, createTime);
        set(4, updateTime);
    }
}
