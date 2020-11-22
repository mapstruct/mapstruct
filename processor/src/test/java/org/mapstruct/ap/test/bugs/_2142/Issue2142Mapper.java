/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2142;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface Issue2142Mapper {

    Issue2142Mapper INSTANCE = Mappers.getMapper( Issue2142Mapper.class );

    _Target map(Source source);

    // CHECKSTYLE:OFF
    class _Target {
    // CHECKSTYLE:ON
        private final String value;

        public _Target(String value) {
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
    }
}
