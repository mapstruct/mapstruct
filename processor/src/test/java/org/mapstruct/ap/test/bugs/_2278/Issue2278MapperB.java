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
 * ReproducerB
 *
 */
@Mapper
public interface Issue2278MapperB {

    Issue2278MapperB INSTANCE = Mappers.getMapper( Issue2278MapperB.class );

    // id mapping is cros-linked
    @Mapping( target = "amount", source = "price" )
    @Mapping( target = "detailsDTO.brand", source = "details.type" )
    @Mapping( target = "detailsDTO.id1",  source = "details.id2" )
    @Mapping( target = "detailsDTO.id2",  source = "details.id1" )
    CarDTO map(Car in);

    // inherit inverse, but undo cross-link in one sweep
    @InheritInverseConfiguration // inherits all
    @Mapping( target = "details", source = "detailsDTO" ) // resets everything on details <> detailsDto
    @Mapping( target = "details.type", source = "detailsDTO.brand" )  // so this needs to be redone
    Car map2(CarDTO in);

    class Car {
        //CHECKSTYLE:OFF
        public float price;
        public Details details;
        //CHECKSTYLE:ON
    }

    class CarDTO {
        //CHECKSTYLE:OFF
        public float amount;
        public DetailsDTO detailsDTO;
        //CHECKSTYLE:ON
    }

    class Details {
        //CHECKSTYLE:OFF
        public String type;
        public String id1;
        public String id2;
        //CHECKSTYLE:ON
    }

    class DetailsDTO {
        //CHECKSTYLE:OFF
        public String brand;
        public String id1;
        public String id2;
        //CHECKSTYLE:ON
    }

}
