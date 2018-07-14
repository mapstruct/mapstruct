/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.java8;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

/**
 * Test for generation of Java8 based mapper implementations.
 *
 * @author Christian Schuster
 */
public class Java8MapperTest {

    @Test
    public void shouldMapWithRepeatedMappingAnnotation() {
        Java8Mapper mapper = Java8Mapper.INSTANCE;

        Source source = new Source();
        source.setFirstName( "firstname" );
        source.setLastName( "lastname" );

        Target target = mapper.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getGivenName() ).isEqualTo( source.getFirstName() );
        assertThat( target.getSurname() ).isEqualTo( source.getLastName() );
    }
}
