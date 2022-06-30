/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builder.ignore;

import static org.assertj.core.api.Assertions.assertThat;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

/**
 * @author Filip Hrisafov
 */
@IssueKey("1452")
@WithClasses({
    BaseDto.class,
    BaseEntity.class,
    BuilderIgnoringMapper.class,
    BuilderIgnoringMappingConfig.class,
    Person.class,
    PersonDto.class
})
public class BuilderIgnoringTest {

    @ProcessorTest
    @IssueKey( "1933" )
    public void shouldIgnoreBase() {
        PersonDto source = new PersonDto();
        source.setId( 100L );
        source.setName( "John" );
        source.setLastName( "Doe" );

        Person target = BuilderIgnoringMapper.INSTANCE.mapWithIgnoringBase( source );

        assertThat( target.getId() ).isNull();
        assertThat( target.getName() ).isNull();
        assertThat( target.getLastName() ).isEqualTo( "Doe" );
    }

    @ProcessorTest
    public void shouldMapOnlyExplicit() {
        PersonDto source = new PersonDto();
        source.setId( 100L );
        source.setName( "John" );
        source.setLastName( "Doe" );

        Person target = BuilderIgnoringMapper.INSTANCE.mapOnlyWithExplicit( source );

        assertThat( target.getId() ).isNull();
        assertThat( target.getName() ).isEqualTo( "John" );
        assertThat( target.getLastName() ).isNull();
    }

    @ProcessorTest
    public void shouldMapAll() {
        PersonDto source = new PersonDto();
        source.setId( 100L );
        source.setName( "John" );
        source.setLastName( "Doe" );

        Person target = BuilderIgnoringMapper.INSTANCE.mapAll( source );

        assertThat( target.getId() ).isEqualTo( 100L );
        assertThat( target.getName() ).isEqualTo( "John" );
        assertThat( target.getLastName() ).isEqualTo( "Doe" );
    }
}
