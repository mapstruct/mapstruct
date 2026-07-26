/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.naming.spi.ImplementationNamingStrategy;

import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithServiceImplementation;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomImplementationNamingTest {

    @ProcessorTest
    @WithClasses( { MyObject.class, MyObjectDto.class, SimpleObjectMapper.class } )
    @WithServiceImplementation( CustomImplementationNamingStrategy.class )
    public void shouldGenerateCustomNameForImplementation() {
        SimpleObjectMapper instance = SimpleObjectMapper.INSTANCE;
        MyObject myObject = new MyObject( "Gavriil" );
        MyObjectDto myObjectDto = instance.toDto( myObject );

        assertThat( instance ).isInstanceOf( SimpleObjectMapper.class );
        String expectedName = new CustomImplementationNamingStrategy()
                .generateMapperImplementationName( SimpleObjectMapper.class.getSimpleName(),
                        SimpleObjectMapper.class.getSimpleName() + "Impl" );
        assertThat( instance.getClass().getSimpleName() ).isEqualTo( expectedName );
        assertThat( myObjectDto.getName() ).isEqualTo( myObject.getName() );

    }

    @WithClasses( { MapperDecorator.class, MyObject.class, MyObjectDto.class, DecoratedMapper.class } )
    @ProcessorTest
    @WithServiceImplementation( CustomImplementationNamingStrategy.class )
    public void shouldGenerateCustomNameForDecoratorImplementation() {
        DecoratedMapper instance = DecoratedMapper.INSTANCE;
        MyObject myObject = new MyObject( "Gavriil" );
        MyObjectDto myObjectDto = instance.toDto( myObject );

        assertThat( instance ).isInstanceOf( DecoratedMapper.class );
        String simpleName = DecoratedMapper.class.getSimpleName();
        String expectedName = new CustomImplementationNamingStrategy()
                .generateDecoratorImplementationName( simpleName, simpleName + "Impl" );
        assertThat( instance.getClass().getSimpleName() ).isEqualTo( expectedName );
        assertThat( myObjectDto.getName() ).isEqualTo( myObject.getName() );

    }

}
