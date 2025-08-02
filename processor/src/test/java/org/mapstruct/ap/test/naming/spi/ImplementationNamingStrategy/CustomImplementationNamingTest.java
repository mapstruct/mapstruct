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

@WithClasses( { MyObject.class, MyObjectDto.class, MyObjectMapper.class } )
@WithServiceImplementation( CustomImplementationNamingStrategy.class )
public class CustomImplementationNamingTest {
    @ProcessorTest
    public void shouldGenerateCustomNameForImplementation() {
        MyObjectMapper instance = MyObjectMapper.INSTANCE;
        MyObject myObject = new MyObject( "Gavriil" );
        MyObjectDto myObjectDto = instance.toDto( myObject );

        assertThat( instance ).isInstanceOf( MyObjectMapper.class );
        assertThat( instance.getClass().getSimpleName() ).isEqualTo( new CustomImplementationNamingStrategy().generateImplementationName( MyObjectMapper.class.getSimpleName(), null ) );
        assertThat( myObjectDto.getName() ).isEqualTo( myObject.getName() );

    }
}

