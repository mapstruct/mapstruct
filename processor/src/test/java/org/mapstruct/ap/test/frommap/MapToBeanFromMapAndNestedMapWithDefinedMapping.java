/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.frommap;

import java.util.HashMap;
import java.util.Map;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface MapToBeanFromMapAndNestedMapWithDefinedMapping {

    MapToBeanFromMapAndNestedMapWithDefinedMapping INSTANCE = Mappers.getMapper(
        MapToBeanFromMapAndNestedMapWithDefinedMapping.class );

    @Mapping(target = "nested", source = "nestedTarget.nested")
    Target toTarget(Source source);

    class Source {

        private Map<String, String> nestedTarget = new HashMap<>();

        public Map<String, String> getNestedTarget() {
            return nestedTarget;
        }

        public void setNestedTarget(Map<String, String> nestedTarget) {
            this.nestedTarget = nestedTarget;
        }

        public Source() {
            nestedTarget.put( "nested", "valueFromNestedMap" );
        }
    }

    class Target {

        private String nested;

        public String getNested() {
            return nested;
        }

        public void setNested(String nested) {
            this.nested = nested;
        }
    }

}
