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
import org.elegant.model.jooq.tables.records.AuthorBookRelationRecord;
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
public class AuthorBookRelation extends TableImpl<AuthorBookRelationRecord> {

    private static final long serialVersionUID = -725692641;

    /**
     * The reference instance of <code>PUBLIC.author_book_relation</code>
     */
    public static final AuthorBookRelation AUTHOR_BOOK_RELATION = new AuthorBookRelation();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<AuthorBookRelationRecord> getRecordType() {
        return AuthorBookRelationRecord.class;
    }

    /**
     * The column <code>PUBLIC.author_book_relation.book_id</code>. 书本id
     */
    public final TableField<AuthorBookRelationRecord, Integer> BOOK_ID = createField("book_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "书本id");

    /**
     * The column <code>PUBLIC.author_book_relation.author_id</code>. 作者id
     */
    public final TableField<AuthorBookRelationRecord, Integer> AUTHOR_ID = createField("author_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "作者id");

    /**
     * The column <code>PUBLIC.author_book_relation.create_time</code>. 创建时间
     */
    public final TableField<AuthorBookRelationRecord, LocalDateTime> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.LOCALDATETIME.defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP()", org.jooq.impl.SQLDataType.LOCALDATETIME)), this, "创建时间");

    /**
     * The column <code>PUBLIC.author_book_relation.update_time</code>.
     */
    public final TableField<AuthorBookRelationRecord, LocalDateTime> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.LOCALDATETIME.defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP()", org.jooq.impl.SQLDataType.LOCALDATETIME)), this, "");

    /**
     * Create a <code>PUBLIC.author_book_relation</code> table reference
     */
    public AuthorBookRelation() {
        this(DSL.name("author_book_relation"), null);
    }

    /**
     * Create an aliased <code>PUBLIC.author_book_relation</code> table reference
     */
    public AuthorBookRelation(String alias) {
        this(DSL.name(alias), AUTHOR_BOOK_RELATION);
    }

    /**
     * Create an aliased <code>PUBLIC.author_book_relation</code> table reference
     */
    public AuthorBookRelation(Name alias) {
        this(alias, AUTHOR_BOOK_RELATION);
    }

    private AuthorBookRelation(Name alias, Table<AuthorBookRelationRecord> aliased) {
        this(alias, aliased, null);
    }

    private AuthorBookRelation(Name alias, Table<AuthorBookRelationRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> AuthorBookRelation(Table<O> child, ForeignKey<O, AuthorBookRelationRecord> key) {
        super(child, key, AUTHOR_BOOK_RELATION);
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
        return Arrays.<Index>asList(Indexes.PRIMARY_KEY_6F);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<AuthorBookRelationRecord> getPrimaryKey() {
        return Keys.CONSTRAINT_6;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<AuthorBookRelationRecord>> getKeys() {
        return Arrays.<UniqueKey<AuthorBookRelationRecord>>asList(Keys.CONSTRAINT_6);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AuthorBookRelation as(String alias) {
        return new AuthorBookRelation(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AuthorBookRelation as(Name alias) {
        return new AuthorBookRelation(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public AuthorBookRelation rename(String name) {
        return new AuthorBookRelation(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public AuthorBookRelation rename(Name name) {
        return new AuthorBookRelation(name, null);
    }
}
