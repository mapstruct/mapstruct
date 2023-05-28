/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.frommap;

import java.util.Map;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author Christian Kosmowski
 */
@Mapper
public interface MapToBeanUsingMappingMethodMapper {

    MapToBeanUsingMappingMethodMapper INSTANCE = Mappers.getMapper( MapToBeanUsingMappingMethodMapper.class );

    @Mapping(target = "normalInt", source = "source.number")
    Target toTarget(Map<String, Integer> source);

    default String mapIntegerToString( Integer input ) {
        return "converted_" + input;
    }

    class Target {

        private String normalInt;

        public String getNormalInt() {
            return normalInt;
        }

        public void setNormalInt(String normalInt) {
            this.normalInt = normalInt;
        }
    }

}
