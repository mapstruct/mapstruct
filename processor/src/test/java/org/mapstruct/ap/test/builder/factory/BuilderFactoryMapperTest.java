/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builder.factory;

import static org.assertj.core.api.Assertions.assertThat;

import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

/**
 * @author Filip Hrisafov
 */
@WithClasses({
    BuilderFactoryMapper.class,
    BuilderImplicitFactoryMapper.class,
    Person.class,
    PersonDto.class
})
public class BuilderFactoryMapperTest {

    @ProcessorTest
    public void shouldUseBuilderFactory() {
        Person person = BuilderFactoryMapper.INSTANCE.map( new PersonDto( "Filip" ) );

        assertThat( person.getName() ).isEqualTo( "Filip" );
        assertThat( person.getSource() ).isEqualTo( "Factory with @ObjectFactory" );
    }

    @ProcessorTest
    public void shouldUseImplicitBuilderFactory() {
        Person person = BuilderImplicitFactoryMapper.INSTANCE.map( new PersonDto( "Filip" ) );

        assertThat( person.getName() ).isEqualTo( "Filip" );
        assertThat( person.getSource() ).isEqualTo( "Implicit Factory" );
    }
}
