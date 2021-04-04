/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conditional.basic;

import org.mapstruct.Condition;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface OptionalLikeConditionalMapper {

    OptionalLikeConditionalMapper INSTANCE = Mappers.getMapper( OptionalLikeConditionalMapper.class );

    Target map(Source source);

    default <T> T map(Nullable<T> nullable) {
        return nullable.value;
    }

    @Condition
    default <T> boolean isPresent(Nullable<T> nullable) {
        return nullable.isPresent();
    }

    class Nullable<T> {

        private final T value;
        private final boolean present;

        private Nullable(T value, boolean present) {
            this.value = value;
            this.present = present;
        }

        public boolean isPresent() {
            return present;
        }

        public static <T> Nullable<T> undefined() {
            return new Nullable<>( null, false );
        }

        public static <T> Nullable<T> ofNullable(T value) {
            return new Nullable<>( value, true );
        }
    }

    class Source {
        protected final Nullable<String> value;

        public Source(Nullable<String> value) {
            this.value = value;
        }

        public Nullable<String> getValue() {
            return value;
        }
    }

    class Target {
        protected String value = "initial";

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
