/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.frommap;

import java.util.Map;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Christian Kosmowski
 */
@Mapper
public interface MapToBeanTypeCheckMapper {

    MapToBeanTypeCheckMapper INSTANCE = Mappers.getMapper( MapToBeanTypeCheckMapper.class );

    Target toTarget(Map<Integer, String> source);

    class Target {

        private final String value;

        public Target(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

}
