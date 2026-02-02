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
public interface MapToBeanFromMapWithKeyContainingDotMapper {

    MapToBeanFromMapWithKeyContainingDotMapper INSTANCE =
        Mappers.getMapper( MapToBeanFromMapWithKeyContainingDotMapper.class );

    @Mapping(target = "someValue", source = "some\\.value")
    Target toTargetDirect(Map<String, String> source);

    @Mapping(target = "someValue", source = "source.some\\.value")
    Target toTargetWithLeadingParameterName(Map<String, String> source);

    class Target {

        private String someValue;

        public String getSomeValue() {
            return someValue;
        }

        public void setSomeValue(String someValue) {
            this.someValue = someValue;
        }
    }

}
