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

@Mapper
public interface MapToBeanFromMapWithSource {

    MapToBeanFromMapWithSource INSTANCE = Mappers.getMapper( MapToBeanFromMapWithSource.class );

    @Mapping(target = "targetName", source = "sourceA.name")
    Target toTarget(Map<String, Source> source);

    @Mapping(target = "targetName", source = "source.sourceA.name")
    Target toTargetWithLeadingParameterName(Map<String, Source> source);

    class Source {

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    class Target {

        String targetName;

        public String getTargetName() {
            return targetName;
        }

        public void setTargetName(String targetName) {
            this.targetName = targetName;
        }
    }

}
