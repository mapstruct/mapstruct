/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1425;

import org.joda.time.LocalDate;
import org.mapstruct.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;
import org.mapstruct.testutil.WithJoda;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Christian Bandowski
 */
@WithClasses({
    Issue1425Mapper.class,
    Source.class,
    Target.class
})
@IssueKey("1425")
@WithJoda
public class Issue1425Test {

    @ProcessorTest
    public void shouldTestMappingLocalDates() {
        Source source = new Source();
        source.setValue( LocalDate.parse( "2018-04-18" ) );

        Target target = Issue1425Mapper.INSTANCE.map( source );

        assertThat( target ).isNotNull();
        assertThat( target.getValue() ).isEqualTo( "2018-04-18" );
    }
}
