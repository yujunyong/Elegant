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
import org.elegant.model.jooq.tables.records.BookCoverRecord;
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
public class BookCover extends TableImpl<BookCoverRecord> {

    private static final long serialVersionUID = 633053857;

    /**
     * The reference instance of <code>PUBLIC.book_cover</code>
     */
    public static final BookCover BOOK_COVER = new BookCover();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<BookCoverRecord> getRecordType() {
        return BookCoverRecord.class;
    }

    /**
     * The column <code>PUBLIC.book_cover.book_id</code>. 书本id
     */
    public final TableField<BookCoverRecord, Integer> BOOK_ID = createField("book_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "书本id");

    /**
     * The column <code>PUBLIC.book_cover.cover</code>. 封面二进制数据
     */
    public final TableField<BookCoverRecord, byte[]> COVER = createField("cover", org.jooq.impl.SQLDataType.BLOB.nullable(false), this, "封面二进制数据");

    /**
     * The column <code>PUBLIC.book_cover.image_file_extension</code>. 封面图片扩展名
     */
    public final TableField<BookCoverRecord, String> IMAGE_FILE_EXTENSION = createField("image_file_extension", org.jooq.impl.SQLDataType.VARCHAR(10).nullable(false), this, "封面图片扩展名");

    /**
     * Create a <code>PUBLIC.book_cover</code> table reference
     */
    public BookCover() {
        this(DSL.name("book_cover"), null);
    }

    /**
     * Create an aliased <code>PUBLIC.book_cover</code> table reference
     */
    public BookCover(String alias) {
        this(DSL.name(alias), BOOK_COVER);
    }

    /**
     * Create an aliased <code>PUBLIC.book_cover</code> table reference
     */
    public BookCover(Name alias) {
        this(alias, BOOK_COVER);
    }

    private BookCover(Name alias, Table<BookCoverRecord> aliased) {
        this(alias, aliased, null);
    }

    private BookCover(Name alias, Table<BookCoverRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> BookCover(Table<O> child, ForeignKey<O, BookCoverRecord> key) {
        super(child, key, BOOK_COVER);
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
        return Arrays.<Index>asList(Indexes.PRIMARY_KEY_9);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<BookCoverRecord> getPrimaryKey() {
        return Keys.CONSTRAINT_9;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<BookCoverRecord>> getKeys() {
        return Arrays.<UniqueKey<BookCoverRecord>>asList(Keys.CONSTRAINT_9);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BookCover as(String alias) {
        return new BookCover(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BookCover as(Name alias) {
        return new BookCover(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public BookCover rename(String name) {
        return new BookCover(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public BookCover rename(Name name) {
        return new BookCover(name, null);
    }
}
