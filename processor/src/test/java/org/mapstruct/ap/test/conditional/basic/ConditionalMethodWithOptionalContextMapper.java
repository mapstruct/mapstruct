/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conditional.basic;

import org.mapstruct.Condition;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

/**
 * Reproduces the scenario where two {@link Condition} methods are declared, one with an optional {@link Context}
 * parameter and one without. The context aware method should be used when the context is available, while the plain
 * method is used otherwise, instead of reporting an ambiguity.
 */
@Mapper( nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE )
public interface ConditionalMethodWithOptionalContextMapper {

    ConditionalMethodWithOptionalContextMapper INSTANCE =
        Mappers.getMapper( ConditionalMethodWithOptionalContextMapper.class );

    void updateWithTracker(@MappingTarget Target target, Source source, @Context PropertyChangedTracker tracker);

    void update(@MappingTarget Target target, Source source);

    default <T> T unwrap(Nullable<T> nullable) {
        return nullable.value;
    }

    @Condition
    default <T> boolean isPresent(Nullable<T> nullable) {
        return nullable.isPresent();
    }

    @Condition
    default <T> boolean isPresent(Nullable<T> nullable, @Context PropertyChangedTracker tracker) {
        boolean present = nullable.isPresent();
        if ( tracker != null && present ) {
            tracker.markChanged();
        }
        return present;
    }

    class PropertyChangedTracker {

        private int changedCount;

        public void markChanged() {
            changedCount++;
        }

        public int getChangedCount() {
            return changedCount;
        }
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
