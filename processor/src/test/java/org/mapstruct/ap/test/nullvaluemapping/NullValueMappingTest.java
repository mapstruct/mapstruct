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
package org.mapstruct.ap.test.nullvaluemapping;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.test.nullvaluemapping._target.CarDto;
import org.mapstruct.ap.test.nullvaluemapping._target.DriverAndCarDto;
import org.mapstruct.ap.test.nullvaluemapping.source.Car;
import org.mapstruct.ap.test.nullvaluemapping.source.Driver;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Tests for the strategies for mapping {@code null} values, given via {@code NullValueMapping} etc.
 *
 * @author Sjaak Derksen
 */
@IssueKey( "295" )
@WithClasses({
    Car.class,
    Driver.class,
    CarDto.class,
    DriverAndCarDto.class,
    CarMapper.class,
    CarMapperSettingOnMapper.class,
    CentralConfig.class,
    CarMapperSettingOnConfig.class
})
@RunWith(AnnotationProcessorTestRunner.class)
public class NullValueMappingTest {

    @Test
    public void shouldProvideMapperInstance() throws Exception {
        assertThat( CarMapper.INSTANCE ).isNotNull();
    }

    @Test
    public void shouldMapExpressionAndConstantRegardlessNullArg() {
        //given
        Car car = new Car( "Morris", 2 );

        //when
        CarDto carDto1 = CarMapper.INSTANCE.carToCarDto( car );

        //then
        assertThat( carDto1 ).isNotNull();
        assertThat( carDto1.getMake() ).isEqualTo( car.getMake() );
        assertThat( carDto1.getSeatCount() ).isEqualTo( car.getNumberOfSeats() );
        assertThat( carDto1.getModel() ).isEqualTo( "ModelT" );
        assertThat( carDto1.getCatalogId() ).isNotEmpty();

        //when
        CarDto carDto2 = CarMapper.INSTANCE.carToCarDto( null );

        //then
        assertThat( carDto2 ).isNotNull();
        assertThat( carDto2.getMake() ).isNull();
        assertThat( carDto2.getSeatCount() ).isEqualTo( 0 );
        assertThat( carDto2.getModel() ).isEqualTo( "ModelT" );
        assertThat( carDto2.getCatalogId() ).isNotEmpty();
    }

    @Test
    public void shouldMapExpressionAndConstantRegardlessNullArgSeveralSources() {
        //given
        Car car = new Car( "Morris", 2 );

        //when
        CarDto carDto1 = CarMapper.INSTANCE.carToCarDto( car, "ModelT" );

        //then
        assertThat( carDto1 ).isNotNull();
        assertThat( carDto1.getMake() ).isEqualTo( car.getMake() );
        assertThat( carDto1.getSeatCount() ).isEqualTo( car.getNumberOfSeats() );
        assertThat( carDto1.getCatalogId() ).isNotEmpty();

        //when
        CarDto carDto2 = CarMapper.INSTANCE.carToCarDto( null, "ModelT" );

        //then
        assertThat( carDto2 ).isNotNull();
        assertThat( carDto2.getMake() ).isNull();
        assertThat( carDto2.getSeatCount() ).isEqualTo( 0 );
        assertThat( carDto2.getModel() ).isEqualTo( "ModelT" );
        assertThat( carDto2.getCatalogId() ).isNotEmpty();
    }

    @Test
    public void shouldMapIterableWithNullArg() {

        //given
        Car car = new Car( "Morris", 2 );

        //when
        List<CarDto> carDtos1 = CarMapper.INSTANCE.carsToCarDtos( Arrays.asList( car ) );

        //then
        assertThat( carDtos1 ).isNotNull();
        assertThat( carDtos1.size() ).isEqualTo( 1 );

        //when
        List<CarDto> carDtos2 = CarMapper.INSTANCE.carsToCarDtos( null );

        //then
        assertThat( carDtos2 ).isNotNull();
        assertThat( carDtos2.isEmpty() ).isTrue();
    }

    @Test
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void shouldMapMapWithNullArg() {

        //given
        Car car = new Car( "Morris", 2 );
        Map carMap = new HashMap();
        carMap.put( 1, car );

        //when
        Map<Integer, CarDto> carDtoMap1 = CarMapper.INSTANCE.carsToCarDtoMap( carMap );

        //then
        assertThat( carDtoMap1 ).isNotNull();
        assertThat( carDtoMap1.size() ).isEqualTo( 1 );

        //when
        Map<Integer, CarDto> carDtoMap2 = CarMapper.INSTANCE.carsToCarDtoMap( null );

        //then
        assertThat( carDtoMap2 ).isNotNull();
        assertThat( carDtoMap2.isEmpty() ).isTrue();
    }

    @Test
    public void shouldMapExpressionAndConstantRegardlessNullArgOnMapper() {

        //when
        CarDto carDto = CarMapperSettingOnMapper.INSTANCE.carToCarDto( null );

        //then
        assertThat( carDto ).isNotNull();
        assertThat( carDto.getMake() ).isNull();
        assertThat( carDto.getSeatCount() ).isEqualTo( 0 );
        assertThat( carDto.getModel() ).isEqualTo( "ModelT" );
        assertThat( carDto.getCatalogId() ).isNotEmpty();
    }

    @Test
    public void shouldMapIterableWithNullArgOnMapper() {

        //when
        List<CarDto> carDtos = CarMapperSettingOnMapper.INSTANCE.carsToCarDtos( null );

        //then
        assertThat( carDtos ).isNotNull();
        assertThat( carDtos.isEmpty() ).isTrue();
    }

    @Test
    public void shouldMapMapWithNullArgOnMapper() {

        //when
        Map<Integer, CarDto> carDtoMap = CarMapperSettingOnMapper.INSTANCE.carsToCarDtoMap( null );

        //then
        assertThat( carDtoMap ).isNull();
    }

    @Test
    public void shouldMapExpressionAndConstantRegardlessNullArgOnConfig() {

        //when
        CarDto carDto = CarMapperSettingOnConfig.INSTANCE.carToCarDto( null );

        //then
        assertThat( carDto ).isNotNull();
        assertThat( carDto.getMake() ).isNull();
        assertThat( carDto.getSeatCount() ).isEqualTo( 0 );
        assertThat( carDto.getModel() ).isEqualTo( "ModelT" );
        assertThat( carDto.getCatalogId() ).isNotEmpty();
    }

    @Test
    public void shouldMapIterableWithNullArgOnConfig() {

        //when
        List<CarDto> carDtos = CarMapperSettingOnConfig.INSTANCE.carsToCarDtos( null );

        //then
        assertThat( carDtos ).isNotNull();
        assertThat( carDtos.isEmpty() ).isTrue();
    }

    @Test
    public void shouldMapMapWithNullArgOnConfig() {

        //when
        Map<Integer, CarDto> carDtoMap = CarMapperSettingOnConfig.INSTANCE.carsToCarDtoMap( null );

        //then
        assertThat( carDtoMap ).isNull();
    }

    @Test
    public void shouldApplyConfiguredStrategyForMethodWithSeveralSourceParams() {
        //when
        DriverAndCarDto result = CarMapper.INSTANCE.driverAndCarToDto( null, null );

        //then
        assertThat( result ).isNotNull();
        assertThat( result.getMake() ).isNull();
        assertThat( result.getName() ).isNull();

        //when
        result = CarMapper.INSTANCE.driverAndCarToDtoReturningNull( null, null );

        //then
        assertThat( result ).isNull();
    }
}
