/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1590;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

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
@RunWith(AnnotationProcessorTestRunner.class)
@IssueKey("1590")
public class Issue1590Test {

    @Test
    public void shouldTestMappingLocalDates() {
        BookShelf source = new BookShelf();
        source.setBooks( Arrays.asList( new Book() ) );

        BookShelf target = BookShelfMapper.INSTANCE.map( source );

        assertThat( target ).isNotNull();
        assertThat( target.getBooks() ).isNotNull();
    }
}
