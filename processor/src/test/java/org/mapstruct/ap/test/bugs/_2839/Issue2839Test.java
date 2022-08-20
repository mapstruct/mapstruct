/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2839;

import java.util.Collections;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author Hakan Özkan
 */
@IssueKey("2839")
@WithClasses({
    Car.class,
    CarDto.class,
    CarMapper.class,
    Id.class,
    Issue2839Exception.class,
})
public class Issue2839Test {

    @ProcessorTest
    void shouldCompile() {
        CarDto car1 = new CarDto(
            "carId",
            Collections.singletonList( "seatId" ),
            Collections.singletonList( "tireId" )
        );
        assertThatThrownBy( () -> CarMapper.MAPPER.toEntity( car1 ) )
            .isExactlyInstanceOf( RuntimeException.class )
            .getCause()
            .isInstanceOf( Issue2839Exception.class )
            .hasMessage( "For id seatId" );

        CarDto car2 = new CarDto( "carId", Collections.emptyList(), Collections.singletonList( "tireId" ) );
        assertThatThrownBy( () -> CarMapper.MAPPER.toEntity( car2 ) )
            .isExactlyInstanceOf( RuntimeException.class )
            .getCause()
            .isInstanceOf( Issue2839Exception.class )
            .hasMessage( "For id tireId" );

        CarDto car3 = new CarDto( "carId", Collections.emptyList(), Collections.emptyList() );
        assertThatThrownBy( () -> CarMapper.MAPPER.toEntity( car3 ) )
            .isExactlyInstanceOf( RuntimeException.class )
            .getCause()
            .isInstanceOf( Issue2839Exception.class )
            .hasMessage( "For id carId" );
    }
}
