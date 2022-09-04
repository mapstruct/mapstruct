/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2937;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@IssueKey("2937")
@WithClasses({
    Issue2937Mapper.class,
})
class Issue2937Test {

    @ProcessorTest
    void shouldCorrectlyUseConditionalForAdder() {
        List<String> sourceNames = new ArrayList<>();
        sourceNames.add( "Tester 1" );
        Issue2937Mapper.Source source = new Issue2937Mapper.Source( sourceNames );
        Issue2937Mapper.Target target = Issue2937Mapper.INSTANCE.map( source );

        assertThat( target.getNames() ).isEmpty();

        sourceNames.add( "Tester 2" );

        target = Issue2937Mapper.INSTANCE.map( source );

        assertThat( target.getNames() )
            .containsExactly( "Tester 1", "Tester 2" );
    }
}
