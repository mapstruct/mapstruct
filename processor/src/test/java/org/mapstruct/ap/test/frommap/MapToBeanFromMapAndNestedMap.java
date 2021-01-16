/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.frommap;

import java.util.HashMap;
import java.util.Map;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Christian Kosmowski
 */
@Mapper
public interface MapToBeanFromMapAndNestedMap {

    MapToBeanFromMapAndNestedMap INSTANCE = Mappers.getMapper( MapToBeanFromMapAndNestedMap.class );

    Target toTarget(Source source);

    NestedTarget toNestedTarget(Map<String, String> nestedMap);

    class Source {

        private Map<String, String> nestedTarget = new HashMap<>(  );

        public Map<String, String> getNestedTarget() {
            return nestedTarget;
        }

        public void setNestedTarget(Map<String, String> nestedTarget) {
            this.nestedTarget = nestedTarget;
        }

        public Source() {
            nestedTarget.put( "stringFromNestedMap", "valueFromNestedMap" );
        }
    }

    class Target {

        private NestedTarget nestedTarget;

        public NestedTarget getNestedTarget() {
            return nestedTarget;
        }

        public void setNestedTarget(NestedTarget nestedTarget) {
            this.nestedTarget = nestedTarget;
        }

    }

    class NestedTarget {
        private String stringFromNestedMap;

        public String getStringFromNestedMap() {
            return stringFromNestedMap;
        }

        public void setStringFromNestedMap(String stringFromNestedMap) {
            this.stringFromNestedMap = stringFromNestedMap;
        }
    }

}
