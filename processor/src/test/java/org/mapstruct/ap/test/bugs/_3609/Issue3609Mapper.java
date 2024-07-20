/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3609;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.SubclassMapping;

@Mapper(unmappedSourcePolicy = ReportingPolicy.ERROR)
public abstract class Issue3609Mapper {

    @SubclassMapping(source = CarDto.class, target = Car.class)
    @BeanMapping(ignoreUnmappedSourceProperties = "id")
    public abstract Vehicle toVehicle(VehicleDto vehicle);

    //CHECKSTYLE:OFF
    public static class Vehicle {
        public int price;
    }

    public static class Car extends Vehicle {
        public int seats;

        public Car(int price, int seats) {
            this.price = price;
            this.seats = seats;
        }
    }

    public static class VehicleDto {
        public int id;
        public int price;
    }

    public static class CarDto extends VehicleDto {
        public int seats;
    }
    //CHECKSTYLE:ON
}
