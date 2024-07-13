/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3609;

import org.mapstruct.BeanMapping;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MapperConfig;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.SubclassExhaustiveStrategy;
import org.mapstruct.SubclassMapping;

@Mapper(config = Issue3609Mapper.Config.class)
public abstract class Issue3609Mapper {

    @SubclassMapping(source = CarDto.class, target = Car.class)
    @SubclassMapping(source = TruckDto.class, target = Truck.class)
    @BeanMapping(ignoreUnmappedSourceProperties = { "id" })
    public abstract Vehicle toVehicle(VehicleDto vehicle);

    @InheritInverseConfiguration
    @Mapping(target = "id", ignore = true)
    public abstract VehicleDto toVehicleDto(Vehicle vehicle);

    //CHECKSTYLE:OFF
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
    //CHECKSTYLE:ON

    @MapperConfig(
        unmappedSourcePolicy = ReportingPolicy.ERROR,
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        subclassExhaustiveStrategy = SubclassExhaustiveStrategy.RUNTIME_EXCEPTION
    )
    public static class Config {
    }
}
