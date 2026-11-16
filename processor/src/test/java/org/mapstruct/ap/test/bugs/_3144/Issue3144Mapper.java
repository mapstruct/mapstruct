/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3144;

import java.util.Map;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface Issue3144Mapper {

    Issue3144Mapper INSTANCE = Mappers.getMapper( Issue3144Mapper.class );

    @Mapping(target = "map", source = "sourceMap")
    Target mapExplicitDefined(Map<String, String> sourceMap);

    @Mapping(target = "map", ignore = true)
    Target map(Map<String, String> sourceMap);

    Target mapMultiParameters(Source source, Map<String, String> map);

    @Mapping(target = "value", source = "map.testValue")
    Target mapMultiParametersDefinedMapping(Source source, Map<String, String> map);

    class Source {
        private final String sourceValue;

        public Source(String sourceValue) {
            this.sourceValue = sourceValue;
        }

        public String getSourceValue() {
            return sourceValue;
        }
    }

    class Target {
        private String value;
        private Map<String, String> map;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public Map<String, String> getMap() {
            return map;
        }

        public void setMap(Map<String, String> map) {
            this.map = map;
        }
    }
}
