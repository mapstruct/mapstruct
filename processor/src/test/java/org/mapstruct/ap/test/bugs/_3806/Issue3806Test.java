/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3806;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

@WithClasses(Issue3806Mapper.class)
@IssueKey("3806")
class Issue3806Test {

    @ProcessorTest
    void shouldNotClearGetterOnlyCollectionsInUpdateMapping() {
        Map<String, String> booksByAuthor = new HashMap<>();
        booksByAuthor.put( "author1", "book1" );
        booksByAuthor.put( "author2", "book2" );
        List<String> authors = new ArrayList<>();
        authors.add( "author1" );
        authors.add( "author2" );

        List<String> books = new ArrayList<>();
        books.add( "book1" );
        books.add( "book2" );
        Map<String, String> booksByPublisher = new HashMap<>();
        booksByPublisher.put( "publisher1", "book1" );
        booksByPublisher.put( "publisher2", "book2" );
        Issue3806Mapper.Target target = new Issue3806Mapper.Target( authors, booksByAuthor );
        target.setBooks( books );
        target.setBooksByPublisher( booksByPublisher );

        Issue3806Mapper.Target source = new Issue3806Mapper.Target( null, null );
        Issue3806Mapper.INSTANCE.update( target, source );

        assertThat( target.getAuthors() ).containsExactly( "author1", "author2" );
        assertThat( target.getBooksByAuthor() )
            .containsOnly(
                entry( "author1", "book1" ),
                entry( "author2", "book2" )
            );

        assertThat( target.getBooks() ).containsExactly( "book1", "book2" );
        assertThat( target.getBooksByPublisher() )
            .containsOnly(
                entry( "publisher1", "book1" ),
                entry( "publisher2", "book2" )
            );

        booksByAuthor = new HashMap<>();
        booksByAuthor.put( "author3", "book3" );
        authors = new ArrayList<>();
        authors.add( "author3" );

        books = new ArrayList<>();
        books.add( "book3" );
        booksByPublisher = new HashMap<>();
        booksByPublisher.put( "publisher3", "book3" );
        source = new Issue3806Mapper.Target( authors, booksByAuthor );
        source.setBooks( books );
        source.setBooksByPublisher( booksByPublisher );
        Issue3806Mapper.INSTANCE.update( target, source );

        assertThat( target.getAuthors() ).containsExactly( "author3" );
        assertThat( target.getBooksByAuthor() )
            .containsOnly(
                entry( "author3", "book3" )
            );

        assertThat( target.getBooks() ).containsExactly( "book3" );
        assertThat( target.getBooksByPublisher() )
            .containsOnly(
                entry( "publisher3", "book3" )
            );
    }
}
