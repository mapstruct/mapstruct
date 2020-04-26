/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.constructor.mixed;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.test.constructor.PersonDto;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@WithClasses({
    PersonMixed.class,
    PersonDto.class,
    ConstructorMixedWithSettersMapper.class
})
@RunWith(AnnotationProcessorTestRunner.class)
public class ConstructorMixedWithSettersTest {

    @Test
    public void mapDefault() {
        PersonDto source = new PersonDto();
        source.setName( "Bob" );
        source.setAge( 30 );
        source.setJob( "Software Engineer" );
        source.setCity( "Zurich" );
        source.setAddress( "Plaza 1" );
        source.setChildren( Arrays.asList( "Alice", "Tom" ) );

        PersonMixed target = ConstructorMixedWithSettersMapper.INSTANCE.map( source );

        assertThat( target.getName() ).isEqualTo( "Bob" );
        assertThat( target.getAge() ).isEqualTo( 30 );
        assertThat( target.getJob() ).isEqualTo( "Software Engineer" );
        assertThat( target.getCity() ).isEqualTo( "Zurich" );
        assertThat( target.getAddress() ).isEqualTo( "Plaza 1" );
        assertThat( target.getChildren() ).containsExactly( "Alice", "Tom" );
    }
}
