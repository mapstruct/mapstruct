/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1608;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@IssueKey("1608")
@WithClasses({
    Issue1608Mapper.class,
    Book.class,
    BookDto.class
})
public class Issue1608Test {

    @ProcessorTest
    public void shouldCorrectlyUseFluentSettersStartingWithIs() {

        Book book = new Book();
        book.setIsbn( "978-3-16-148410-0" );
        book.setIssueYear( 2018 );
        BookDto bookDto = Issue1608Mapper.INSTANCE.map( book );

        assertThat( bookDto.getIsbn() ).isEqualTo( "978-3-16-148410-0" );
        assertThat( bookDto.getIssueYear() ).isEqualTo( 2018 );
    }
}
