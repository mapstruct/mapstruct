/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.inheritedmappingmethod;

import org.mapstruct.ap.test.inheritedmappingmethod._target.CarDto;
import org.mapstruct.ap.test.inheritedmappingmethod._target.FastCarDto;
import org.mapstruct.ap.test.inheritedmappingmethod.source.Car;
import org.mapstruct.ap.test.inheritedmappingmethod.source.FastCar;
import org.mapstruct.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

@IssueKey( "274" )
@WithClasses({
 Car.class, CarDto.class, UnboundMappable.class, CarMapper.class, //
    FastCar.class, FastCarDto.class, BoundMappable.class, FastCarMapper.class
})
public class InheritedMappingMethodTest {

    @ProcessorTest
    public void shouldProvideUnboundedMapperInstance() {
        UnboundMappable<CarDto, Car> instance = CarMapper.INSTANCE;
        assertThat( instance ).isNotNull();
    }

    @ProcessorTest
    public void shouldMapUsingUnboundedInheretedMappingMethod() {
        // given
        CarDto bikeDto = new CarDto();
        bikeDto.setHorsepower( 130 );
        // when
        UnboundMappable<CarDto, Car> instance = CarMapper.INSTANCE;
        Car bike = instance.from( bikeDto );

        // then
        assertThat( bike ).isNotNull();
        assertThat( bike.getHorsepower() ).isEqualTo( 130 );
    }

    @ProcessorTest
    public void shouldProvideBoundedMapperInstance() {
        BoundMappable<? extends CarDto, ? extends Car> instance = FastCarMapper.INSTANCE;
        assertThat( instance ).isNotNull();
    }

    @ProcessorTest
    public void shouldMapUsingBoundedInheretedMappingMethod() {
        // given
        FastCarDto bikeDto = new FastCarDto();
        bikeDto.setHorsepower( 130 );
        bikeDto.setCoolnessFactor( 243 );

        // when
        BoundMappable<FastCarDto, FastCar> instance = FastCarMapper.INSTANCE;
        FastCar bike = instance.from( bikeDto );

        // then
        assertThat( bike ).isNotNull();
        assertThat( bike.getHorsepower() ).isEqualTo( 130 );
        assertThat( bike.getCoolnessFactor() ).isEqualTo( 243 );
    }

}
