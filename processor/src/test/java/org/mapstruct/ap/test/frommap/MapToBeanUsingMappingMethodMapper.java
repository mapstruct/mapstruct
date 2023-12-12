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
    @Mapping(target = "normalIntWithDots", source = "source.number.with.dots")
    Target toTarget(Map<String, Integer> source);

    default String mapIntegerToString( Integer input ) {
        return "converted_" + input;
    }

    class Target {

        private String normalInt;
        private String normalIntWithDots;

        public String getNormalInt() {
            return normalInt;
        }

        public void setNormalInt(String normalInt) {
            this.normalInt = normalInt;
        }

        public String getNormalIntWithDots() {
            return normalIntWithDots;
        }

        public void setNormalIntWithDots(String normalIntWithDots) {
            this.normalIntWithDots = normalIntWithDots;
        }
    }

}
