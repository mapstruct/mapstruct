/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2177;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface Issue2177Mapper {

    Issue2177Mapper INSTANCE = Mappers.getMapper( Issue2177Mapper.class );

    Target<String> map(Source source);

    class Source {
        private final String value;

        public Source(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    class Target<T> {
        private final T value;

        public Target(T value) {
            this.value = value;
        }

        public T getValue() {
            return value;
        }
    }
}
