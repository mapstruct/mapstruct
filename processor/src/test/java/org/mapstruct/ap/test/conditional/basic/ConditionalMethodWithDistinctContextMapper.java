/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conditional.basic;

import org.mapstruct.Condition;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Two overloaded {@link Condition} methods that bind the same number of parameters, but each requires a distinct
 * {@link Context}. When only one of the contexts is available, the matching overload is selected without ambiguity.
 */
@Mapper
public interface ConditionalMethodWithDistinctContextMapper {

    ConditionalMethodWithDistinctContextMapper INSTANCE =
        Mappers.getMapper( ConditionalMethodWithDistinctContextMapper.class );

    Target map(Source source, @Context FirstContext first);

    default <T> T unwrap(Nullable<T> nullable) {
        return nullable.value;
    }

    @Condition
    default <T> boolean isPresent(Nullable<T> nullable, @Context FirstContext first) {
        first.markChecked();
        return nullable.isPresent();
    }

    @Condition
    default <T> boolean isPresent(Nullable<T> nullable, @Context SecondContext second) {
        return nullable.isPresent();
    }

    class FirstContext {

        private int checkedCount;

        public void markChecked() {
            checkedCount++;
        }

        public int getCheckedCount() {
            return checkedCount;
        }
    }

    class SecondContext {
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
        protected String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
