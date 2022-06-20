/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.inheritfromconfig.multiple;

import java.util.Date;

import org.mapstruct.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

@WithClasses({
    BaseDto.class,
    BaseEntity.class,
    CarDto.class,
    CarEntity.class,
    CarMapper.class,
    EntityToDtoMappingConfig.class,
    Car2Dto.class,
    Car2Entity.class,
    CarMapper2.class
})
@IssueKey("1367")
public class CarMapperTest {

    @ProcessorTest
    public void testMapEntityToDto() {
        CarDto dto = CarMapper.MAPPER.mapTo( newCar() );
        assertThat( dto.getDbId() ).isEqualTo( 9L );
        assertThat( dto.getMaker() ).isEqualTo( "Nissan" );
        assertThat( dto.getSeatCount() ).isEqualTo( 5 );
    }

    @ProcessorTest
    public void testMapDtoToEntity() {
        CarEntity car = CarMapper.MAPPER.mapFrom( newCarDto() );
        assertThat( car.getId() ).isEqualTo( 9L );
        assertThat( car.getManufacturer() ).isEqualTo( "Nissan" );
        assertThat( car.getNumberOfSeats() ).isEqualTo( 5 );
        assertThat( car.getLastModifiedBy() ).isEqualTo( "restApiUser" );
        assertThat( car.getCreationDate() ).isNull();
    }

    @ProcessorTest
    public void testForwardMappingShouldTakePrecedence() {
        Car2Dto dto = new Car2Dto();
        dto.setMaker( "mazda" );
        dto.setSeatCount( 5 );

        Car2Entity entity = CarMapper2.MAPPER.mapFrom( dto );
        assertThat( entity.getManufacturer() ).isEqualTo( "ford" );
        assertThat( entity.getNumberOfSeats( ) ).isEqualTo( 0 );

        Car2Entity entity2 = new Car2Entity();
        entity2.setManufacturer( "mazda" );
        entity2.setNumberOfSeats( 5 );

        Car2Dto dto2 = CarMapper2.MAPPER.mapTo( entity2 );
        assertThat( dto2.getMaker() ).isEqualTo( "mazda" );
        assertThat( dto2.getSeatCount() ).isEqualTo( 5 );
    }

    private CarEntity newCar() {
        CarEntity car = new CarEntity();
        car.setId( 9L );
        car.setCreatedBy( "admin" );
        car.setCreationDate( new Date() );
        car.setManufacturer( "Nissan" );
        car.setNumberOfSeats( 5 );
        return car;
    }

    private CarDto newCarDto() {
        CarDto carDto = new CarDto();
        carDto.setDbId( 9L );
        carDto.setMaker( "Nissan" );
        carDto.setSeatCount( 5 );
        return carDto;
    }

}
