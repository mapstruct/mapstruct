/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2278;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * ReproducerA
 *
 */
@Mapper
public interface Issue2278MapperA {

    Issue2278MapperA INSTANCE = Mappers.getMapper( Issue2278MapperA.class );

    @Mapping( target = "detailsDTO", source = "details" )
    @Mapping( target = "detailsDTO.fuelType", ignore = true )
    CarDTO map(Car in);

    // checkout the Issue2278ReferenceMapper, the @InheritInverseConfiguration
    // is de-facto @Mapping( target = "details", source = "detailsDTO" )
    @InheritInverseConfiguration
    @Mapping( target = "details.model", ignore = true )
    @Mapping( target = "details.type", constant = "gto")
    @Mapping( target = "details.fuel", source = "detailsDTO.fuelType")
    Car map(CarDTO in);

    class Car {
        //CHECKSTYLE:OFF
        public Details details;
        //CHECKSTYLE:ON
    }

    class CarDTO {
        //CHECKSTYLE:OFF
        public DetailsDTO detailsDTO;
        //CHECKSTYLE:ON
    }

    class Details {
        //CHECKSTYLE:OFF
        public String brand;
        public String model;
        public String type;
        public String fuel;
        //CHECKSTYLE:ON
    }

    class DetailsDTO {
        //CHECKSTYLE:OFF
        public String brand;
        public String fuelType;
        //CHECKSTYLE:ON
    }

}
