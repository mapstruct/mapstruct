/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.constructor.kotlin;

import kotlin.Metadata;
import org.mapstruct.ap.test.constructor.PersonDto;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

@WithClasses({
    Metadata.class,
    PersonDto.class,
    PersonDataClass.class,
    KotlinDataClassConstructorMapper.class
})
public class KotlinDataClassConstructorTest {

    @ProcessorTest
    public void shouldMap() {
        PersonDto source = new PersonDto();
        source.setName( "Bob" );
        source.setAge( 30 );
        source.setJob( "Software Engineer" );

        PersonDataClass target = KotlinDataClassConstructorMapper.INSTANCE.map( source );

        assertThat( target.getName() ).isEqualTo( "Bob" );
        assertThat( target.getAge() ).isEqualTo( 30 );
        assertThat( target.getJob() ).isEqualTo( "Software Engineer" );
    }
}
