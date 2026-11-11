/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.constructor.deprecated;

import java.util.List;

import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * A type with a deprecated parameterless constructor next to the constructor that is declared for its properties has
 * to be mapped through the declared constructor.
 * <p>
 * Without that, the compilation fails with
 * {@code No target bean properties found: can't map Collection element "AddressDto addressDto" to
 * "AddressEntity addressEntity"}, which is what Kotlin JPA entities started to run into with Kotlin 2.3.0.
 *
 * @author Oleg Babichev
 * @see <a href="https://github.com/mapstruct/mapstruct/issues/3960">#3960</a>
 */
@WithClasses({
    AddressDto.class,
    AddressEntity.class,
    AddressEntityMapper.class
})
class DeprecatedConstructorTest {

    @ProcessorTest
    void shouldMapThroughTheDeclaredConstructor() {
        List<AddressEntity> entities = AddressEntityMapper.INSTANCE.map(
            List.of( new AddressDto( "Sesame Street", "New York" ) )
        );

        assertThat( entities ).hasSize( 1 );
        assertThat( entities.get( 0 ).getStreet() ).isEqualTo( "Sesame Street" );
        assertThat( entities.get( 0 ).getCity() ).isEqualTo( "New York" );
    }
}
