/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3390;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
    nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_NULL
)
public interface Issue3390Mapper {

    Issue3390Mapper INSTANCE = Mappers.getMapper( Issue3390Mapper.class );

    void update(@MappingTarget Target target, Source source);

    class Source {
        private final List<String> property;

        Source(List<String> property) {
            this.property = property;
        }

        public List<String> getProperty() {
            return property;
        }
    }

    class Target {
        private List<String> property;

        Target(List<String> property) {
            this.property = property;
        }

        public List<String> getProperty() {
            return property;
        }

        public void setProperty(List<String> property) {
            this.property = property;
        }
    }
}
