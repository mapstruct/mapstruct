/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2949;

import org.mapstruct.BeanMapping;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper(unmappedSourcePolicy = ReportingPolicy.ERROR)
public interface Issue2949Mapper {

    Issue2949Mapper INSTANCE = Mappers.getMapper( Issue2949Mapper.class );

    @Mapping( target = "property1", ignore = true)
    @InheritInverseConfiguration
    Source toSource(Target target);

    @BeanMapping(ignoreUnmappedSourceProperties = { "property1" })
    Target toTarget(Source source);

    class Target {
        private final String value;

        public Target(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    class Source {
        private final String value;
        private final String property1;

        public Source(String value, String property1) {
            this.value = value;
            this.property1 = property1;
        }

        public String getValue() {
            return value;
        }

        public String getProperty1() {
            return property1;
        }
    }
}
