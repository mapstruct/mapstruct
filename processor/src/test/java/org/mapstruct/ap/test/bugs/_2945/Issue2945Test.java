/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2945;

import org.mapstruct.ap.test.bugs._2945._target.EnumHolder;
import org.mapstruct.ap.test.bugs._2945._target.Target;
import org.mapstruct.ap.test.bugs._2945.source.Source;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@IssueKey("2945")
@WithClasses({
    EnumHolder.class,
    Issue2945Mapper.class,
    Source.class,
    Target.class,
})
class Issue2945Test {

    @ProcessorTest
    void shouldCompile() {
        Target target = Issue2945Mapper.INSTANCE.map( new Source( "VALUE_1" ) );

        assertThat( target.getProperty() ).isEqualTo( EnumHolder.Property.VALUE_1 );
    }
}
