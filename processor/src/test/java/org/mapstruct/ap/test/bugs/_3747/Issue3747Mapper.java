/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3747;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;

/**
 * @author Filip Hrisafov
 */
@Mapper(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface Issue3747Mapper {

    Target map(Source source);

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

        public Target(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
