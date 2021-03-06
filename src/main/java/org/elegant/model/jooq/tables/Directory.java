/*
 * This file is generated by jOOQ.
 */
package org.elegant.model.jooq.tables;


import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.elegant.model.jooq.Indexes;
import org.elegant.model.jooq.Keys;
import org.elegant.model.jooq.Public;
import org.elegant.model.jooq.tables.records.DirectoryRecord;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


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
public class Directory extends TableImpl<DirectoryRecord> {

    private static final long serialVersionUID = -761857842;

    /**
     * The reference instance of <code>PUBLIC.directory</code>
     */
    public static final Directory DIRECTORY = new Directory();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<DirectoryRecord> getRecordType() {
        return DirectoryRecord.class;
    }

    /**
     * The column <code>PUBLIC.directory.dir_id</code>. 目录id
     */
    public final TableField<DirectoryRecord, Integer> DIR_ID = createField("dir_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "目录id");

    /**
     * The column <code>PUBLIC.directory.name</code>. 目录名称
     */
    public final TableField<DirectoryRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR(100).nullable(false), this, "目录名称");

    /**
     * The column <code>PUBLIC.directory.create_time</code>. 创建时间
     */
    public final TableField<DirectoryRecord, LocalDateTime> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.LOCALDATETIME.defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP()", org.jooq.impl.SQLDataType.LOCALDATETIME)), this, "创建时间");

    /**
     * The column <code>PUBLIC.directory.update_time</code>. 更新时间
     */
    public final TableField<DirectoryRecord, LocalDateTime> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.LOCALDATETIME.defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP()", org.jooq.impl.SQLDataType.LOCALDATETIME)), this, "更新时间");

    /**
     * Create a <code>PUBLIC.directory</code> table reference
     */
    public Directory() {
        this(DSL.name("directory"), null);
    }

    /**
     * Create an aliased <code>PUBLIC.directory</code> table reference
     */
    public Directory(String alias) {
        this(DSL.name(alias), DIRECTORY);
    }

    /**
     * Create an aliased <code>PUBLIC.directory</code> table reference
     */
    public Directory(Name alias) {
        this(alias, DIRECTORY);
    }

    private Directory(Name alias, Table<DirectoryRecord> aliased) {
        this(alias, aliased, null);
    }

    private Directory(Name alias, Table<DirectoryRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> Directory(Table<O> child, ForeignKey<O, DirectoryRecord> key) {
        super(child, key, DIRECTORY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.PRIMARY_KEY_C);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<DirectoryRecord, Integer> getIdentity() {
        return Keys.IDENTITY_DIRECTORY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<DirectoryRecord> getPrimaryKey() {
        return Keys.CONSTRAINT_C;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<DirectoryRecord>> getKeys() {
        return Arrays.<UniqueKey<DirectoryRecord>>asList(Keys.CONSTRAINT_C);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Directory as(String alias) {
        return new Directory(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Directory as(Name alias) {
        return new Directory(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Directory rename(String name) {
        return new Directory(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Directory rename(Name name) {
        return new Directory(name, null);
    }
}
