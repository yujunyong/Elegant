/*
 * This file is generated by jOOQ.
 */
package org.elegant.model.jooq;


import javax.annotation.Generated;

import org.elegant.model.jooq.tables.Author;
import org.elegant.model.jooq.tables.AuthorBookRelation;
import org.elegant.model.jooq.tables.Book;
import org.elegant.model.jooq.tables.BookCover;
import org.elegant.model.jooq.tables.Directory;
import org.elegant.model.jooq.tables.DirectoryPath;
import org.elegant.model.jooq.tables.Media;
import org.elegant.model.jooq.tables.Property;
import org.elegant.model.jooq.tables.records.AuthorBookRelationRecord;
import org.elegant.model.jooq.tables.records.AuthorRecord;
import org.elegant.model.jooq.tables.records.BookCoverRecord;
import org.elegant.model.jooq.tables.records.BookRecord;
import org.elegant.model.jooq.tables.records.DirectoryPathRecord;
import org.elegant.model.jooq.tables.records.DirectoryRecord;
import org.elegant.model.jooq.tables.records.MediaRecord;
import org.elegant.model.jooq.tables.records.PropertyRecord;
import org.jooq.Identity;
import org.jooq.UniqueKey;
import org.jooq.impl.Internal;


/**
 * A class modelling foreign key relationships and constraints of tables of 
 * the <code>PUBLIC</code> schema.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.5"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // IDENTITY definitions
    // -------------------------------------------------------------------------

    public static final Identity<AuthorRecord, Integer> IDENTITY_AUTHOR = Identities0.IDENTITY_AUTHOR;
    public static final Identity<BookRecord, Integer> IDENTITY_BOOK = Identities0.IDENTITY_BOOK;
    public static final Identity<DirectoryRecord, Integer> IDENTITY_DIRECTORY = Identities0.IDENTITY_DIRECTORY;

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<AuthorRecord> CONSTRAINT_A = UniqueKeys0.CONSTRAINT_A;
    public static final UniqueKey<AuthorBookRelationRecord> CONSTRAINT_6 = UniqueKeys0.CONSTRAINT_6;
    public static final UniqueKey<BookRecord> CONSTRAINT_2 = UniqueKeys0.CONSTRAINT_2;
    public static final UniqueKey<BookCoverRecord> CONSTRAINT_9 = UniqueKeys0.CONSTRAINT_9;
    public static final UniqueKey<DirectoryRecord> CONSTRAINT_C = UniqueKeys0.CONSTRAINT_C;
    public static final UniqueKey<DirectoryPathRecord> CONSTRAINT_4 = UniqueKeys0.CONSTRAINT_4;
    public static final UniqueKey<MediaRecord> CONSTRAINT_62 = UniqueKeys0.CONSTRAINT_62;
    public static final UniqueKey<PropertyRecord> CONSTRAINT_C4 = UniqueKeys0.CONSTRAINT_C4;

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------


    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Identities0 {
        public static Identity<AuthorRecord, Integer> IDENTITY_AUTHOR = Internal.createIdentity(Author.AUTHOR, Author.AUTHOR.AUTHOR_ID);
        public static Identity<BookRecord, Integer> IDENTITY_BOOK = Internal.createIdentity(Book.BOOK, Book.BOOK.BOOK_ID);
        public static Identity<DirectoryRecord, Integer> IDENTITY_DIRECTORY = Internal.createIdentity(Directory.DIRECTORY, Directory.DIRECTORY.DIR_ID);
    }

    private static class UniqueKeys0 {
        public static final UniqueKey<AuthorRecord> CONSTRAINT_A = Internal.createUniqueKey(Author.AUTHOR, "CONSTRAINT_A", Author.AUTHOR.AUTHOR_ID);
        public static final UniqueKey<AuthorBookRelationRecord> CONSTRAINT_6 = Internal.createUniqueKey(AuthorBookRelation.AUTHOR_BOOK_RELATION, "CONSTRAINT_6", AuthorBookRelation.AUTHOR_BOOK_RELATION.BOOK_ID, AuthorBookRelation.AUTHOR_BOOK_RELATION.AUTHOR_ID);
        public static final UniqueKey<BookRecord> CONSTRAINT_2 = Internal.createUniqueKey(Book.BOOK, "CONSTRAINT_2", Book.BOOK.BOOK_ID);
        public static final UniqueKey<BookCoverRecord> CONSTRAINT_9 = Internal.createUniqueKey(BookCover.BOOK_COVER, "CONSTRAINT_9", BookCover.BOOK_COVER.BOOK_ID);
        public static final UniqueKey<DirectoryRecord> CONSTRAINT_C = Internal.createUniqueKey(Directory.DIRECTORY, "CONSTRAINT_C", Directory.DIRECTORY.DIR_ID);
        public static final UniqueKey<DirectoryPathRecord> CONSTRAINT_4 = Internal.createUniqueKey(DirectoryPath.DIRECTORY_PATH, "CONSTRAINT_4", DirectoryPath.DIRECTORY_PATH.ANCESTOR, DirectoryPath.DIRECTORY_PATH.DIR_ID);
        public static final UniqueKey<MediaRecord> CONSTRAINT_62 = Internal.createUniqueKey(Media.MEDIA, "CONSTRAINT_62", Media.MEDIA.MEDIA_ID);
        public static final UniqueKey<PropertyRecord> CONSTRAINT_C4 = Internal.createUniqueKey(Property.PROPERTY, "CONSTRAINT_C4", Property.PROPERTY.PROP_NAME);
    }
}