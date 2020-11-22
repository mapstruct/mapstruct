/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2197;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface Issue2197Mapper {

    Issue2197Mapper INSTANCE = Mappers.getMapper( Issue2197Mapper.class );

    _0Target map(Source source);

    // CHECKSTYLE:OFF
    class _0Target {
    // CHECKSTYLE:ON
        private final String value;

        public _0Target(String value) {
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
