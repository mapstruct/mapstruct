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
public interface MapToBeanNonStringMapAsMultiSourceMapper {

    MapToBeanNonStringMapAsMultiSourceMapper INSTANCE =
        Mappers.getMapper( MapToBeanNonStringMapAsMultiSourceMapper.class );

    Target toTarget(Source source, Map<Integer, String> map);

    class Source {
        private final String value;

        public Source(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    class Target {

        private final String value;
        private final Map<String, String> map;

        public Target(String value, Map<String, String> map) {
            this.value = value;
            this.map = map;
        }

        public String getValue() {
            return value;
        }

        public Map<String, String> getMap() {
            return map;
        }
    }

}
