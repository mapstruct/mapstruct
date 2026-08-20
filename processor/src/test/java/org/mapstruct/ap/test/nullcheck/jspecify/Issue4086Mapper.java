/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Issue4086Mapper {

    Issue4086Mapper INSTANCE = Mappers.getMapper( Issue4086Mapper.class );

    @Mapping(target = "payload", source = "value", qualifiedByName = "mapValue")
    Target map(Source source);

    @Named("mapValue")
    default Target.Nested mapValue(@Nullable String value) {
        return value == null ? new Target.Nested( "default" ) : new Target.Nested( value );
    }

    class Source {

        private String value;

        public @Nullable String getValue() {
            return value;
        }
    }

    class Target {

        private final Nested payload;

        public Target(@NonNull Nested payload) {
            this.payload = payload;
        }

        public Nested getPayload() {
            return payload;
        }

        static class Nested {

            private final String value;

            Nested(String value) {
                this.value = value;
            }

            String getValue() {
                return value;
            }
        }
    }
}
