/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3565;

import java.util.Optional;

import org.mapstruct.Condition;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface Issue3565Mapper {

    Issue3565Mapper INSTANCE = Mappers.getMapper( Issue3565Mapper.class );

    default <T> T mapFromOptional(Optional<T> value) {
        return value.orElse( (T) null );
    }

    @Condition
    default boolean isOptionalPresent(Optional<?> value) {
        return value.isPresent();
    }

    Target map(Source source);

    class Source {

        private final Boolean condition;

        public Source(Boolean condition) {
            this.condition = condition;
        }

        public Optional<Boolean> getCondition() {
            return Optional.ofNullable( this.condition );
        }
    }

    class Target {
        private String condition;

        public Optional<String> getCondition() {
            return Optional.ofNullable( this.condition );
        }

        public void setCondition(String condition) {
            this.condition = condition;
        }
    }
}
