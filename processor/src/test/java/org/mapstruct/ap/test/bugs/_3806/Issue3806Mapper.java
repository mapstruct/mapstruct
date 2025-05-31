/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3806;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface Issue3806Mapper {

    Issue3806Mapper INSTANCE = Mappers.getMapper( Issue3806Mapper.class );

    void update(@MappingTarget Target target, Target source);

    class Target {

        private final Collection<String> authors;
        private final Map<String, String> booksByAuthor;

        protected Collection<String> books;
        protected Map<String, String> booksByPublisher;

        public Target(Collection<String> authors, Map<String, String> booksByAuthor) {
            this.authors = authors != null ? new ArrayList<>( authors ) : null;
            this.booksByAuthor = booksByAuthor != null ? new HashMap<>( booksByAuthor ) : null;
        }

        public Collection<String> getAuthors() {
            return authors;
        }

        public Map<String, String> getBooksByAuthor() {
            return booksByAuthor;
        }

        public Collection<String> getBooks() {
            return books;
        }

        public void setBooks(Collection<String> books) {
            this.books = books;
        }

        public Map<String, String> getBooksByPublisher() {
            return booksByPublisher;
        }

        public void setBooksByPublisher(Map<String, String> booksByPublisher) {
            this.booksByPublisher = booksByPublisher;
        }
    }

}
