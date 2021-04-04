/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conditional.basic;

import java.util.Collection;
import java.util.List;

import org.mapstruct.Condition;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface ConditionalMethodForCollectionMapper {

    ConditionalMethodForCollectionMapper INSTANCE = Mappers.getMapper( ConditionalMethodForCollectionMapper.class );

    AuthorDto map(Author author);

    @Condition
    default <T> boolean isNotEmpty(Collection<T> collection) {
        return collection != null && !collection.isEmpty();
    }

    class Author {
        private List<Book> books;

        public List<Book> getBooks() {
            return books;
        }

        public boolean hasBooks() {
            return false;
        }

        public void setBooks(List<Book> books) {
            this.books = books;
        }
    }

    class Book {
        private final String name;

        public Book(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    class AuthorDto {
        private List<BookDto> books;

        public List<BookDto> getBooks() {
            return books;
        }

        public void setBooks(List<BookDto> books) {
            this.books = books;
        }
    }

    class BookDto {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
