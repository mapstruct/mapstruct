/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2743;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author Filip Hrisafov
 */
@Mapper(unmappedSourcePolicy = ReportingPolicy.ERROR)
public interface Issue2743Mapper {

    @BeanMapping(ignoreUnmappedSourceProperties = { "number" })
    Target map(Source source);

    class Source {

        private final int number = 10;
        private final NestedSource nested;

        public Source(NestedSource nested) {
            this.nested = nested;
        }

        public int getNumber() {
            return number;
        }

        public NestedSource getNested() {
            return nested;
        }
    }

    class NestedSource {
        private final String value;

        public NestedSource(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    class Target {

        private final NestedTarget nested;

        public Target(NestedTarget nested) {
            this.nested = nested;
        }

        public NestedTarget getNested() {
            return nested;
        }
    }

    class NestedTarget {
        private final String value;

        public NestedTarget(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
