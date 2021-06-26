/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.frommap;

import java.util.Map;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface ObjectMapToBeanWithQualifierMapper {

    ObjectMapToBeanWithQualifierMapper INSTANCE = Mappers.getMapper( ObjectMapToBeanWithQualifierMapper.class );

    @Mapping( target = "car", qualifiedByName = "objectToCar")
    VehiclesDto map(Map<String, Object> vehicles);

    @Named("objectToCar")
    default CarDto objectToCar(Object object) {
        CarDto car = new CarDto();

        if ( object instanceof Car ) {
            car.setBrand( ( (Car) object ).brand );
        }

        return car;
    }

    class VehiclesDto {

        private VehicleDto car;

        public VehicleDto getCar() {
            return car;
        }

        public void setCar(VehicleDto car) {
            this.car = car;
        }
    }

    class VehicleDto {
        private String brand;

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }
    }

    class CarDto extends VehicleDto {

    }

    class Car {

        private final String brand;

        public Car(String brand) {
            this.brand = brand;
        }
    }
}
