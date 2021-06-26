/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.frommap;

import java.util.Map;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author Christian Kosmowski
 */
@Mapper(unmappedSourcePolicy = ReportingPolicy.ERROR)
public interface MapToBeanImplicitUnmappedSourcePolicyMapper {

    MapToBeanImplicitUnmappedSourcePolicyMapper INSTANCE =
        Mappers.getMapper( MapToBeanImplicitUnmappedSourcePolicyMapper.class );

    Target toTarget(Map<String, String> source);

    class Target {

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
