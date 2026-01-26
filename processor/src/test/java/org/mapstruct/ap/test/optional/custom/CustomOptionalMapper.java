/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.optional.custom;

import java.util.Optional;

import org.mapstruct.Condition;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
@SuppressWarnings("OptionalAssignedToNull")
public interface CustomOptionalMapper {

    CustomOptionalMapper INSTANCE = Mappers.getMapper( CustomOptionalMapper.class );

    Target map(Source source);

    void update(@MappingTarget Target target, Source source);

    @Condition
    default <T> boolean isPresent(Optional<T> optional) {
        return optional != null;
    }

    default <E> E unwrapFromOptional(Optional<E> optional) {
        return optional == null
            ? null
            : optional.orElse( null );
    }

    class Target {
        private String value = "initial";

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    class Source {
        private final Optional<String> value;

        public Source(Optional<String> value) {
            this.value = value;
        }

        public Optional<String> getValue() {
            return value;
        }
    }
}
