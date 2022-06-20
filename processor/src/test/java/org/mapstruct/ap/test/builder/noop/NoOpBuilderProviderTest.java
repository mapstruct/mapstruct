/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builder.noop;

import org.mapstruct.ap.spi.NoOpBuilderProvider;
import org.mapstruct.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;
import org.mapstruct.testutil.WithServiceImplementation;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@IssueKey( "1418" )
@WithServiceImplementation(NoOpBuilderProvider.class)
@WithClasses( {
    Person.class,
    PersonDto.class,
    PersonMapper.class
} )
public class NoOpBuilderProviderTest {

    @ProcessorTest
    public void shouldNotUseBuilder() {
        Person person = PersonMapper.INSTANCE.map( new PersonDto( "Filip" ) );

        assertThat( person ).isNotNull();
        assertThat( person.getName() ).isEqualTo( "Filip" );
    }
}
