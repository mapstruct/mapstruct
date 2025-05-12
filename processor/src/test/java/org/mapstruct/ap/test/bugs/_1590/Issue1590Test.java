/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1590;

import java.util.Arrays;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Sjaak Derksen
 */
@WithClasses({
    BookMapper.class,
    BookShelfMapper.class,
    Book.class,
    BookShelf.class
})
@IssueKey("1590")
public class Issue1590Test {

    @ProcessorTest
    public void shouldTestMappingLocalDates() {
        BookShelf source = new BookShelf();
        source.setBooks( Arrays.asList( new Book("Test") ) );

        BookShelf target = BookShelfMapper.INSTANCE.map( source );

        assertThat( target ).isNotNull();
        assertThat( target.getBooks() ).isNotNull();
    }
}
