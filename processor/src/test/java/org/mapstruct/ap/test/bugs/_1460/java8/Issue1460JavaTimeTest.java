/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1460.java8;

import java.time.LocalDate;

import org.mapstruct.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Christian Bandowski
 */
@WithClasses({
    Issue1460JavaTimeMapper.class,
    Source.class,
    Target.class
})
@IssueKey("1460")
public class Issue1460JavaTimeTest {

    @ProcessorTest
    public void shouldTestMappingLocalDates() {
        String dateAsString = "2018-04-26";

        Source source = new Source();
        source.setStringToJavaLocalDate( dateAsString );

        Target target = Issue1460JavaTimeMapper.INSTANCE.map( source );

        assertThat( target ).isNotNull();
        assertThat( target.getStringToJavaLocalDate() ).isEqualTo( LocalDate.parse( dateAsString ) );
    }
}
