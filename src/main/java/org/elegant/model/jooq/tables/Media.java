/*
 * This file is generated by jOOQ.
 */
package org.elegant.model.jooq.tables;


import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.elegant.model.jooq.Indexes;
import org.elegant.model.jooq.Keys;
import org.elegant.model.jooq.Public;
import org.elegant.model.jooq.tables.records.MediaRecord;
import org.jooq.Field;
import org.jooq.ForeignKey;
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
public class Media extends TableImpl<MediaRecord> {

    private static final long serialVersionUID = 73919604;

    /**
     * The reference instance of <code>PUBLIC.media</code>
     */
    public static final Media MEDIA = new Media();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<MediaRecord> getRecordType() {
        return MediaRecord.class;
    }

    /**
     * The column <code>PUBLIC.media.media_id</code>. 文件id
     */
    public final TableField<MediaRecord, String> MEDIA_ID = createField("media_id", org.jooq.impl.SQLDataType.VARCHAR(100).nullable(false), this, "文件id");

    /**
     * The column <code>PUBLIC.media.content</code>. 文件内容
     */
    public final TableField<MediaRecord, byte[]> CONTENT = createField("content", org.jooq.impl.SQLDataType.BLOB.nullable(false), this, "文件内容");

    /**
     * The column <code>PUBLIC.media.media_type</code>. 文件类型
     */
    public final TableField<MediaRecord, String> MEDIA_TYPE = createField("media_type", org.jooq.impl.SQLDataType.VARCHAR(10).nullable(false), this, "文件类型");

    /**
     * The column <code>PUBLIC.media.file_extension</code>. 文件扩展名
     */
    public final TableField<MediaRecord, String> FILE_EXTENSION = createField("file_extension", org.jooq.impl.SQLDataType.VARCHAR(10).nullable(false), this, "文件扩展名");

    /**
     * Create a <code>PUBLIC.media</code> table reference
     */
    public Media() {
        this(DSL.name("media"), null);
    }

    /**
     * Create an aliased <code>PUBLIC.media</code> table reference
     */
    public Media(String alias) {
        this(DSL.name(alias), MEDIA);
    }

    /**
     * Create an aliased <code>PUBLIC.media</code> table reference
     */
    public Media(Name alias) {
        this(alias, MEDIA);
    }

    private Media(Name alias, Table<MediaRecord> aliased) {
        this(alias, aliased, null);
    }

    private Media(Name alias, Table<MediaRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> Media(Table<O> child, ForeignKey<O, MediaRecord> key) {
        super(child, key, MEDIA);
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
        return Arrays.<Index>asList(Indexes.PRIMARY_KEY_62);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<MediaRecord> getPrimaryKey() {
        return Keys.CONSTRAINT_62;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<MediaRecord>> getKeys() {
        return Arrays.<UniqueKey<MediaRecord>>asList(Keys.CONSTRAINT_62);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Media as(String alias) {
        return new Media(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Media as(Name alias) {
        return new Media(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Media rename(String name) {
        return new Media(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Media rename(Name name) {
        return new Media(name, null);
    }
}