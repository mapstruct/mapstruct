/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.constructor.simple;

import java.util.Arrays;

import org.mapstruct.ap.test.constructor.Person;
import org.mapstruct.ap.test.constructor.PersonDto;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@WithClasses({
    Person.class,
    PersonDto.class,
    SimpleConstructorMapper.class
})
public class SimpleConstructorTest {

    @ProcessorTest
    public void mapDefault() {
        PersonDto source = new PersonDto();
        source.setName( "Bob" );
        source.setAge( 30 );
        source.setJob( "Software Engineer" );
        source.setCity( "Zurich" );
        source.setAddress( "Plaza 1" );
        source.setChildren( Arrays.asList( "Alice", "Tom" ) );

        Person target = SimpleConstructorMapper.INSTANCE.map( source );

        assertThat( target.getName() ).isEqualTo( "Bob" );
        assertThat( target.getAge() ).isEqualTo( 30 );
        assertThat( target.getJob() ).isEqualTo( "Software Engineer" );
        assertThat( target.getCity() ).isEqualTo( "Zurich" );
        assertThat( target.getAddress() ).isEqualTo( "Plaza 1" );
        assertThat( target.getChildren() ).containsExactly( "Alice", "Tom" );
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

        Person target = SimpleConstructorMapper.INSTANCE.mapWithConstants( source );

        assertThat( target.getName() ).isEqualTo( "Bob" );
        assertThat( target.getAge() ).isEqualTo( 25 );
        assertThat( target.getJob() ).isEqualTo( "Software Developer" );
        assertThat( target.getCity() ).isEqualTo( "Zurich" );
        assertThat( target.getAddress() ).isEqualTo( "Plaza 1" );
        assertThat( target.getChildren() ).containsExactly( "Alice", "Tom" );
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

        Person target = SimpleConstructorMapper.INSTANCE.mapWithExpression( source );

        assertThat( target.getName() ).isEqualTo( "Bob" );
        assertThat( target.getAge() ).isEqualTo( 20 );
        assertThat( target.getJob() ).isEqualTo( "software developer" );
        assertThat( target.getCity() ).isEqualTo( "Zurich" );
        assertThat( target.getAddress() ).isEqualTo( "Plaza 1" );
        assertThat( target.getChildren() ).containsExactly( "Alice", "Tom" );
    }
}
