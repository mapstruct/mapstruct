/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builder.immutables;

import org.immutables.value.Value;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests that {@link org.mapstruct.ap.spi.ImmutablesBuilderProvider} correctly redirects MapStruct from a
 * {@code @Value.Immutable}-annotated interface to the Immutables-generated {@code ImmutableXxx} implementation
 * and uses its nested {@code Builder}.
 */
@WithClasses({
    Value.class,
    Person.class,
    ImmutablePerson.class,
    PersonDto.class,
    PersonMapper.class
})
class ImmutablesValueImmutableTest {

    @ProcessorTest
    void shouldMapDtoToImmutablesValueObject() {
        Person person = PersonMapper.INSTANCE.toPerson( new PersonDto( "Bob", 33 ) );

        assertThat( person.getName() ).isEqualTo( "Bob" );
        assertThat( person.getAge() ).isEqualTo( 33 );
    }
}
