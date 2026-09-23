/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2932;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Seonwoo Jung
 */
@IssueKey("2932")
@WithClasses({
    Issue2932Mapper.class,
    Pair.class,
    PairDto.class,
    Record.class,
    RecordDto.class
})
class Issue2932Test {

    @ProcessorTest
    void shouldUseBuilderOfGenericTarget() {
        Record<String> target = Issue2932Mapper.INSTANCE.map( new RecordDto( "answer", "42" ) );

        assertThat( target.getName() ).isEqualTo( "answer" );
        assertThat( target.getValue() ).isEqualTo( "42" );
    }

    @ProcessorTest
    void shouldUseConstructorCreatedBuilderOfGenericTarget() {
        Pair<String, Integer> target = Issue2932Mapper.INSTANCE.map( new PairDto( "answer", 42 ) );

        assertThat( target.getKey() ).isEqualTo( "answer" );
        assertThat( target.getValue() ).isEqualTo( 42 );
    }
}
