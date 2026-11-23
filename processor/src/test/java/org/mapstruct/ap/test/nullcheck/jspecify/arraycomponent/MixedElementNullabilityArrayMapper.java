/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify.arraycomponent;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@NullMarked
@Mapper
public interface MixedElementNullabilityArrayMapper {

    MixedElementNullabilityArrayMapper INSTANCE = Mappers.getMapper( MixedElementNullabilityArrayMapper.class );

    Target map(Source source);

    class Source {

        private final String[] nonNullElements;
        private final @Nullable String[] nullableElements;

        public Source(String[] nonNullElements, @Nullable String[] nullableElements) {
            this.nonNullElements = nonNullElements;
            this.nullableElements = nullableElements;
        }

        public String[] getNonNullElements() {
            return nonNullElements;
        }

        public @Nullable String[] getNullableElements() {
            return nullableElements;
        }
    }

    class Target {

        private @Nullable Integer[] nonNullElements = new Integer[0];
        private @Nullable Integer[] nullableElements = new Integer[0];

        public @Nullable Integer[] getNonNullElements() {
            return nonNullElements;
        }

        public void setNonNullElements(@Nullable Integer[] nonNullElements) {
            this.nonNullElements = nonNullElements;
        }

        public @Nullable Integer[] getNullableElements() {
            return nullableElements;
        }

        public void setNullableElements(@Nullable Integer[] nullableElements) {
            this.nullableElements = nullableElements;
        }
    }
}
