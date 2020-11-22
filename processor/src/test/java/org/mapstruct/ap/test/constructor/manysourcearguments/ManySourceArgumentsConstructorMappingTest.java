/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.constructor.manysourcearguments;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.test.constructor.Person;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@RunWith( AnnotationProcessorTestRunner.class )
@WithClasses( {
    ManySourceArgumentsConstructorMapper.class,
    Person.class,
} )
public class ManySourceArgumentsConstructorMappingTest {

    @Test
    public void shouldCorrectlyUseDefaultValueForSourceParameters() {
        Person person = ManySourceArgumentsConstructorMapper.INSTANCE.map( null, "Test Valley" );

        assertThat( person ).isNotNull();
        assertThat( person.getName() ).isEqualTo( "Tester" );
        assertThat( person.getCity() ).isEqualTo( "Test Valley" );

        person = ManySourceArgumentsConstructorMapper.INSTANCE.map( "Other Tester", null );

        assertThat( person ).isNotNull();
        assertThat( person.getName() ).isEqualTo( "Other Tester" );
        assertThat( person.getCity() ).isEqualTo( "Zurich" );
    }
}
