/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.optional.builder;

import java.util.Optional;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface SimpleOptionalBuilderMapper {

    SimpleOptionalBuilderMapper INSTANCE = Mappers.getMapper( SimpleOptionalBuilderMapper.class );

    Optional<Target> map(Source source);

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

        private Target(TargetBuilder builder) {
            this.value = builder.value;
        }

        public String getValue() {
            return value;
        }

        public static TargetBuilder builder() {
            return new TargetBuilder();
        }

    }

    class TargetBuilder {

        private String value;

        public TargetBuilder value(String value) {
            this.value = value;
            return this;
        }

        public Target build() {
            return new Target(this);
        }

    }
}
