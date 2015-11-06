/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.mapstruct.ap.test.inheritedmappingmethod;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.test.inheritedmappingmethod._target.CarDto;
import org.mapstruct.ap.test.inheritedmappingmethod._target.FastCarDto;
import org.mapstruct.ap.test.inheritedmappingmethod.source.Car;
import org.mapstruct.ap.test.inheritedmappingmethod.source.FastCar;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.fest.assertions.Assertions.assertThat;

@IssueKey( "274" )
@WithClasses({
 Car.class, CarDto.class, UnboundMappable.class, CarMapper.class, //
    FastCar.class, FastCarDto.class, BoundMappable.class, FastCarMapper.class
})
@RunWith(AnnotationProcessorTestRunner.class)
public class InheritedMappingMethodTest {

    @Test
    public void shouldProvideUnboundedMapperInstance() {
        UnboundMappable<CarDto, Car> instance = CarMapper.INSTANCE;
        assertThat( instance ).isNotNull();
    }

    @Test
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

    @Test
    public void shouldProvideBoundedMapperInstance() {
        BoundMappable<? extends CarDto, ? extends Car> instance = FastCarMapper.INSTANCE;
        assertThat( instance ).isNotNull();
    }

    @Test
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
