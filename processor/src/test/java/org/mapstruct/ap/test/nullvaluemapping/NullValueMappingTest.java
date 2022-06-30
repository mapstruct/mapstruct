/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullvaluemapping;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mapstruct.ap.test.nullvaluemapping._target.CarDto;
import org.mapstruct.ap.test.nullvaluemapping._target.DriverAndCarDto;
import org.mapstruct.ap.test.nullvaluemapping.source.Car;
import org.mapstruct.ap.test.nullvaluemapping.source.Driver;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

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
    CarMapperIterableSettingOnMapper.class,
    CarMapperMapSettingOnMapper.class,
    CentralConfig.class,
    CarMapperSettingOnConfig.class,
    CentralIterableMappingConfig.class,
    CarMapperIterableSettingOnConfig.class,
    CentralMapMappingConfig.class,
    CarMapperMapSettingOnConfig.class,
})
public class NullValueMappingTest {

    @ProcessorTest
    public void shouldProvideMapperInstance() {
        assertThat( CarMapper.INSTANCE ).isNotNull();
    }

    @ProcessorTest
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

    @ProcessorTest
    public void shouldMapExpressionAndConstantRegardlessNullArgSeveralSources() {
        //given
        Car car = new Car( "Morris", 2 );

        //when
        CarDto carDto1 = CarMapper.INSTANCE.carToCarDto( car, "ModelT" );

        //then
        assertThat( carDto1 ).isNotNull();
        assertThat( carDto1.getMake() ).isEqualTo( car.getMake() );
        assertThat( carDto1.getModel() ).isEqualTo( "ModelT" );
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

    @ProcessorTest
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

    @ProcessorTest
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

    @ProcessorTest
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

    @ProcessorTest
    public void shouldMapIterableWithNullArgOnMapper() {

        //when
        List<CarDto> carDtos = CarMapperSettingOnMapper.INSTANCE.carsToCarDtos( null );

        //then
        assertThat( carDtos ).isEmpty();
    }

    @ProcessorTest
    public void shouldMapMapWithNullArgOnMapper() {

        //when
        Map<Integer, CarDto> carDtoMap = CarMapperSettingOnMapper.INSTANCE.carsToCarDtoMap( null );

        //then
        assertThat( carDtoMap ).isNull();
    }

    @ProcessorTest
    public void shouldMapExpressionAndConstantRegardlessOfIterableNullArgOnMapper() {

        //when
        CarDto carDto = CarMapperIterableSettingOnMapper.INSTANCE.carToCarDto( null );

        //then
        assertThat( carDto ).isNotNull();
        assertThat( carDto.getMake() ).isNull();
        assertThat( carDto.getSeatCount() ).isEqualTo( 0 );
        assertThat( carDto.getModel() ).isEqualTo( "ModelT" );
        assertThat( carDto.getCatalogId() ).isNotEmpty();
    }

    @ProcessorTest
    public void shouldMapIterableToNullWithIterableNullArgOnMapper() {

        //when
        List<CarDto> carDtos = CarMapperIterableSettingOnMapper.INSTANCE.carsToCarDtos( null );

        //then
        assertThat( carDtos ).isNull();
    }

    @ProcessorTest
    public void shouldMapMapRegardlessOfIterableNullArgOnMapper() {

        //when
        Map<Integer, CarDto> carDtoMap = CarMapperIterableSettingOnMapper.INSTANCE.carsToCarDtoMap( null );

        //then
        assertThat( carDtoMap ).isEmpty();
    }

    @ProcessorTest
    public void shouldMapExpressionAndConstantRegardlessMapNullArgOnMapper() {

        //when
        CarDto carDto = CarMapperMapSettingOnMapper.INSTANCE.carToCarDto( null );

        //then
        assertThat( carDto ).isNotNull();
        assertThat( carDto.getMake() ).isNull();
        assertThat( carDto.getSeatCount() ).isEqualTo( 0 );
        assertThat( carDto.getModel() ).isEqualTo( "ModelT" );
        assertThat( carDto.getCatalogId() ).isNotEmpty();
    }

    @ProcessorTest
    public void shouldMapIterableRegardlessOfMapNullArgOnMapper() {

        //when
        List<CarDto> carDtos = CarMapperMapSettingOnMapper.INSTANCE.carsToCarDtos( null );

        //then
        assertThat( carDtos ).isEmpty();
    }

    @ProcessorTest
    public void shouldMapMapToWithMapNullArgOnMapper() {

        //when
        Map<Integer, CarDto> carDtoMap = CarMapperMapSettingOnMapper.INSTANCE.carsToCarDtoMap( null );

        //then
        assertThat( carDtoMap ).isNull();
    }

    @ProcessorTest
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

    @ProcessorTest
    public void shouldMapIterableWithNullArgOnConfig() {

        //when
        List<CarDto> carDtos = CarMapperSettingOnConfig.INSTANCE.carsToCarDtos( null );

        //then
        assertThat( carDtos ).isEmpty();
    }

    @ProcessorTest
    public void shouldMapMapWithNullArgOnConfig() {

        //when
        Map<Integer, CarDto> carDtoMap = CarMapperSettingOnConfig.INSTANCE.carsToCarDtoMap( null );

        //then
        assertThat( carDtoMap ).isNull();
    }

    @ProcessorTest
    public void shouldMapExpressionAndConstantRegardlessOfIterableNullArgOnConfig() {

        //when
        CarDto carDto = CarMapperIterableSettingOnConfig.INSTANCE.carToCarDto( null );

        //then
        assertThat( carDto ).isNotNull();
        assertThat( carDto.getMake() ).isNull();
        assertThat( carDto.getSeatCount() ).isEqualTo( 0 );
        assertThat( carDto.getModel() ).isEqualTo( "ModelT" );
        assertThat( carDto.getCatalogId() ).isNotEmpty();
    }

    @ProcessorTest
    public void shouldMapIterableToNullWithIterableNullArgOnConfig() {

        //when
        List<CarDto> carDtos = CarMapperIterableSettingOnConfig.INSTANCE.carsToCarDtos( null );

        //then
        assertThat( carDtos ).isNull();
    }

    @ProcessorTest
    public void shouldMapMapRegardlessOfIterableNullArgOnConfig() {

        //when
        Map<Integer, CarDto> carDtoMap = CarMapperIterableSettingOnConfig.INSTANCE.carsToCarDtoMap( null );

        //then
        assertThat( carDtoMap ).isEmpty();
    }

    @ProcessorTest
    public void shouldMapExpressionAndConstantRegardlessOfMapNullArgOnConfig() {

        //when
        CarDto carDto = CarMapperMapSettingOnConfig.INSTANCE.carToCarDto( null );

        //then
        assertThat( carDto ).isNotNull();
        assertThat( carDto.getMake() ).isNull();
        assertThat( carDto.getSeatCount() ).isEqualTo( 0 );
        assertThat( carDto.getModel() ).isEqualTo( "ModelT" );
        assertThat( carDto.getCatalogId() ).isNotEmpty();
    }

    @ProcessorTest
    public void shouldMapIterableRegardlessOfMapNullArgOnConfig() {

        //when
        List<CarDto> carDtos = CarMapperMapSettingOnConfig.INSTANCE.carsToCarDtos( null );

        //then
        assertThat( carDtos ).isEmpty();
    }

    @ProcessorTest
    public void shouldMapMapToNullWithMapNullArgOnConfig() {

        //when
        Map<Integer, CarDto> carDtoMap = CarMapperMapSettingOnConfig.INSTANCE.carsToCarDtoMap( null );

        //then
        assertThat( carDtoMap ).isNull();
    }

    @ProcessorTest
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
