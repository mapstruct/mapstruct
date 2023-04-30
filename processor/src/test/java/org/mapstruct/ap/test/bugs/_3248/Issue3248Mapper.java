/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3248;

import org.mapstruct.BeanMapping;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author Filip Hrisafov
 */
@Mapper(unmappedSourcePolicy = ReportingPolicy.ERROR)
public interface Issue3248Mapper {

    @BeanMapping(ignoreUnmappedSourceProperties = "otherValue")
    Target map(Source source);

    @InheritConfiguration
    Target secondMap(Source source);

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

        public Source(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public String getOtherValue() {
            return value;
        }
    }
}
