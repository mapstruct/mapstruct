/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2541;

import java.util.Optional;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Issue2541Mapper {

    Issue2541Mapper INSTANCE = Mappers.getMapper( Issue2541Mapper.class );

    Target map(Source source);

    default <T> Optional<T> toOptional(@Nullable T value) {
        return Optional.ofNullable( value );
    }

    class Target {
        private Optional<String> value;

        public Optional<String> getValue() {
            return value;
        }

        public void setValue(Optional<String> value) {
            this.value = value;
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
