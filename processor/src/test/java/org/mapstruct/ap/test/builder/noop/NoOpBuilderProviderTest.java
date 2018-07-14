/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builder.noop;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.spi.NoOpBuilderProvider;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithServiceImplementation;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@RunWith( AnnotationProcessorTestRunner.class )
@IssueKey( "1418" )
@WithServiceImplementation(NoOpBuilderProvider.class)
@WithClasses( {
    Person.class,
    PersonDto.class,
    PersonMapper.class
} )
public class NoOpBuilderProviderTest {

    @Test
    public void shouldNotUseBuilder() {
        Person person = PersonMapper.INSTANCE.map( new PersonDto( "Filip" ) );

        assertThat( person ).isNotNull();
        assertThat( person.getName() ).isEqualTo( "Filip" );
    }
}
