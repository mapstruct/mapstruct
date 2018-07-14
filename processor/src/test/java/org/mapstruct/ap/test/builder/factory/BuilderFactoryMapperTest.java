/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builder.factory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@RunWith(AnnotationProcessorTestRunner.class)
@WithClasses({
    BuilderFactoryMapper.class,
    BuilderImplicitFactoryMapper.class,
    Person.class,
    PersonDto.class
})
public class BuilderFactoryMapperTest {

    @Test
    public void shouldUseBuilderFactory() {
        Person person = BuilderFactoryMapper.INSTANCE.map( new PersonDto( "Filip" ) );

        assertThat( person.getName() ).isEqualTo( "Filip" );
        assertThat( person.getSource() ).isEqualTo( "Factory with @ObjectFactory" );
    }

    @Test
    public void shouldUseImplicitBuilderFactory() {
        Person person = BuilderImplicitFactoryMapper.INSTANCE.map( new PersonDto( "Filip" ) );

        assertThat( person.getName() ).isEqualTo( "Filip" );
        assertThat( person.getSource() ).isEqualTo( "Implicit Factory" );
    }
}
