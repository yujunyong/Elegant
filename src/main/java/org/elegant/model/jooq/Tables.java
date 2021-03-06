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


/**
 * Convenience access to all tables in PUBLIC
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.5"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Tables {

    /**
     * The table <code>PUBLIC.author</code>.
     */
    public static final Author AUTHOR = org.elegant.model.jooq.tables.Author.AUTHOR;

    /**
     * The table <code>PUBLIC.author_book_relation</code>.
     */
    public static final AuthorBookRelation AUTHOR_BOOK_RELATION = org.elegant.model.jooq.tables.AuthorBookRelation.AUTHOR_BOOK_RELATION;

    /**
     * The table <code>PUBLIC.book</code>.
     */
    public static final Book BOOK = org.elegant.model.jooq.tables.Book.BOOK;

    /**
     * The table <code>PUBLIC.book_cover</code>.
     */
    public static final BookCover BOOK_COVER = org.elegant.model.jooq.tables.BookCover.BOOK_COVER;

    /**
     * The table <code>PUBLIC.directory</code>.
     */
    public static final Directory DIRECTORY = org.elegant.model.jooq.tables.Directory.DIRECTORY;

    /**
     * The table <code>PUBLIC.directory_path</code>.
     */
    public static final DirectoryPath DIRECTORY_PATH = org.elegant.model.jooq.tables.DirectoryPath.DIRECTORY_PATH;

    /**
     * The table <code>PUBLIC.media</code>.
     */
    public static final Media MEDIA = org.elegant.model.jooq.tables.Media.MEDIA;

    /**
     * The table <code>PUBLIC.property</code>.
     */
    public static final Property PROPERTY = org.elegant.model.jooq.tables.Property.PROPERTY;
}
