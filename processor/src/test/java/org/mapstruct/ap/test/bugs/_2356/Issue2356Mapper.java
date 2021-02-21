/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2356;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface Issue2356Mapper {

    Issue2356Mapper INSTANCE = Mappers.getMapper( Issue2356Mapper.class );

    class Car {
        //CHECKSTYLE:OFF
        public String brand;
        public String model;
        public String modelInternational;
        //CHECKSTYLE:ON
    }

    class CarDTO {
        //CHECKSTYLE:OFF
        public String brand;
        public String modelName;
        //CHECKSTYLE:ON
    }

    // When using InheritInverseConfiguration the mapping from in to modelName should be ignored
    // and shouldn't lead to a compile error.
    @Mapping(target = "modelName", source = "in")
    CarDTO map(Car in);

    default String mapToModel(Car in) {
        return in.modelInternational == null ? in.model : in.modelInternational;
    }

    @InheritInverseConfiguration
    @Mapping(target = "model", source = "modelName")
    @Mapping(target = "modelInternational", ignore = true)
    Car map(CarDTO in);
}
