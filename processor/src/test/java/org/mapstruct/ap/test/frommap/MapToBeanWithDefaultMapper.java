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
public interface MapToBeanWithDefaultMapper {

    MapToBeanWithDefaultMapper INSTANCE = Mappers.getMapper( MapToBeanWithDefaultMapper.class );

    @Mapping(target = "normalInt", source = "number", defaultValue = "4711")
    Target toTarget(Map<String, Integer> source);

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
