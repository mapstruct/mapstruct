/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.constructor.defaultannotated;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.mapstruct.ap.test.constructor.Default;
import org.mapstruct.ap.test.constructor.PersonDto;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

/**
 * @author Filip Hrisafov
 */
@WithClasses({
    Default.class,
    PersonWithDefaultAnnotatedConstructor.class,
    PersonDto.class,
    SimpleDefaultAnnotatedConstructorMapper.class
})
public class SimpleDefaultAnnotatedConstructorTest {

    @ProcessorTest
    public void mapDefault() {
        PersonDto source = new PersonDto();
        source.setName( "Bob" );
        source.setAge( 30 );
        source.setJob( "Software Engineer" );
        source.setCity( "Zurich" );
        source.setAddress( "Plaza 1" );
        source.setChildren( Arrays.asList( "Alice", "Tom" ) );

        PersonWithDefaultAnnotatedConstructor target = SimpleDefaultAnnotatedConstructorMapper.INSTANCE.map( source );

        assertThat( target.getName() ).isEqualTo( "Bob" );
        assertThat( target.getAge() ).isEqualTo( 30 );
    }

    @ProcessorTest
    public void mapWithConstants() {
        PersonDto source = new PersonDto();
        source.setName( "Bob" );
        source.setAge( 30 );
        source.setJob( "Software Engineer" );
        source.setCity( "Zurich" );
        source.setAddress( "Plaza 1" );
        source.setChildren( Arrays.asList( "Alice", "Tom" ) );

        PersonWithDefaultAnnotatedConstructor target =
            SimpleDefaultAnnotatedConstructorMapper.INSTANCE.mapWithConstants( source );

        assertThat( target.getName() ).isEqualTo( "Bob" );
        assertThat( target.getAge() ).isEqualTo( 25 );
    }

    @ProcessorTest
    public void mapWithExpressions() {
        PersonDto source = new PersonDto();
        source.setName( "Bob" );
        source.setAge( 30 );
        source.setJob( "Software Engineer" );
        source.setCity( "Zurich" );
        source.setAddress( "Plaza 1" );
        source.setChildren( Arrays.asList( "Alice", "Tom" ) );

        PersonWithDefaultAnnotatedConstructor target =
            SimpleDefaultAnnotatedConstructorMapper.INSTANCE.mapWithExpression( source );

        assertThat( target.getName() ).isEqualTo( "Bob" );
        assertThat( target.getAge() ).isEqualTo( 20 );
    }
}
