/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.constructor.constructorproperties;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.mapstruct.ap.test.constructor.ConstructorProperties;
import org.mapstruct.ap.test.constructor.PersonDto;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

/**
 * @author Filip Hrisafov
 */
@WithClasses({
    ConstructorProperties.class,
    PersonWithConstructorProperties.class,
    PersonDto.class,
    SimpleConstructorPropertiesMapper.class
})
public class SimpleConstructorPropertiesTest {

    @ProcessorTest
    public void mapDefault() {
        PersonDto source = new PersonDto();
        source.setName( "Bob" );
        source.setAge( 30 );
        source.setJob( "Software Engineer" );
        source.setCity( "Zurich" );
        source.setAddress( "Plaza 1" );
        source.setChildren( Arrays.asList( "Alice", "Tom" ) );

        PersonWithConstructorProperties target = SimpleConstructorPropertiesMapper.INSTANCE.map( source );

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

        PersonWithConstructorProperties target = SimpleConstructorPropertiesMapper.INSTANCE.mapWithConstants( source );

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

        PersonWithConstructorProperties target = SimpleConstructorPropertiesMapper.INSTANCE.mapWithExpression( source );

        assertThat( target.getName() ).isEqualTo( "Bob" );
        assertThat( target.getAge() ).isEqualTo( 20 );
        assertThat( target.getJob() ).isEqualTo( "software developer" );
        assertThat( target.getCity() ).isEqualTo( "Zurich" );
        assertThat( target.getAddress() ).isEqualTo( "Plaza 1" );
        assertThat( target.getChildren() ).containsExactly( "Alice", "Tom" );
    }
}
