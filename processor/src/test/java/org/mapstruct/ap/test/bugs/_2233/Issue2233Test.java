/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2233;

import java.util.Collections;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

/**
 * @author Filip Hrisafov
 */
@IssueKey("2233")
@RunWith(AnnotationProcessorTestRunner.class)
@WithClasses( {
    Program.class,
    ProgramAggregate.class,
    ProgramDto.class,
    ProgramMapper.class,
    ProgramResponseDto.class,
} )
public class Issue2233Test {

    @Test
    public void shouldCorrectlyMapFromOptionalToCollection() {
        ProgramResponseDto response = ProgramMapper.INSTANCE.map( new ProgramAggregate( Collections.singleton(
            new Program(
                "Optional Mapping",
                "123"
            ) ) ) );

        assertThat( response ).isNotNull();
        assertThat( response.getPrograms() ).isPresent();
        assertThat( response.getPrograms().get() )
            .extracting( ProgramDto::getName, ProgramDto::getNumber )
            .containsExactly(
                tuple( Optional.of( "Optional Mapping" ), Optional.of( "123" ) )
            );
    }
}
