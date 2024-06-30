/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3609;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(config = Issue3609Mapper.Config.class)
public abstract class Issue3609Mapper {

    public static Issue3609Mapper INSTANCE = Mappers.getMapper( Issue3609Mapper.class );

    @SubclassMapping(source = CarDto.class, target = Car.class)
    @SubclassMapping(source = TruckDto.class, target = Truck.class)
    @BeanMapping(ignoreUnmappedSourceProperties = { "id" })
    public abstract Vehicle toVehicle(VehicleDto vehicle);

    @InheritInverseConfiguration
    @Mapping(target = "id", ignore = true)
    public abstract VehicleDto toVehicleDto(Vehicle vehicle);

    static class Vehicle {
        public int price;
    }

    static class Car extends Vehicle {
        public int seats;

        public Car(int price, int seats) {
            this.price = price;
            this.seats = seats;
        }
    }

    static class Truck extends Vehicle {
        public int capacity;
    }

    static class VehicleDto {
        public int id;
        public int price;
    }

    static class CarDto extends VehicleDto {
        public int seats;
    }

    static class TruckDto extends VehicleDto {
        public int capacity;
    }

    @MapperConfig(
        unmappedSourcePolicy = ReportingPolicy.ERROR,
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        subclassExhaustiveStrategy = SubclassExhaustiveStrategy.RUNTIME_EXCEPTION
    )
    public static class Config {
    }
}
