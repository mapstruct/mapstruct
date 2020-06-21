/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2121;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface Issue2121Mapper {

    Issue2121Mapper INSTANCE = Mappers.getMapper( Issue2121Mapper.class );

    Target map(Source source);

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

        private final SourceEnum value;

        public Source(SourceEnum value) {
            this.value = value;
        }

        public SourceEnum getValue() {
            return value;
        }
    }

    enum SourceEnum {
        VALUE1,
        VALUE2
    }
}
