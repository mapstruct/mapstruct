/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conditional.qualifier;

import org.mapstruct.Condition;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Test for overloaded condition methods with different parameters.
 * MapStruct should resolve which condition method to use based on available @Context parameters.
 */
@Mapper
public interface OverloadedConditionMapper {

    OverloadedConditionMapper INSTANCE = Mappers.getMapper(OverloadedConditionMapper.class);

    @Mapping(target = "value", source = "nullable")
    Target mapWithoutContext(Source source);

    @Mapping(target = "value", source = "nullable")
    Target mapWithContext(Source source, @Context PropertyChangedTracker tracker);

    class Source {
        private JsonNullable<String> nullable;

        public JsonNullable<String> getNullable() {
            return nullable;
        }

        public void setNullable(JsonNullable<String> nullable) {
            this.nullable = nullable;
        }
    }

    class Target {
        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    class JsonNullable<T> {
        private T value;
        private boolean present;

        public JsonNullable(T value) {
            this.value = value;
            this.present = value != null;
        }

        public T get() {
            return value;
        }

        public boolean isPresent() {
            return present;
        }
    }

    class PropertyChangedTracker {
        private boolean changed;

        public boolean isChanged() {
            return changed;
        }

        public void markChanged() {
            this.changed = true;
        }
    }

    default <T> T map(JsonNullable<T> nullable) {
        return nullable == null ? null : nullable.get();
    }

    @Condition
    default <T> boolean isPresent(JsonNullable<T> nullable) {
        return nullable != null && nullable.isPresent();
    }

    @Condition
    default <T> boolean isPresent(JsonNullable<T> nullable, @Context PropertyChangedTracker tracker) {
        boolean present = nullable != null && nullable.isPresent();
        if (tracker != null && present) {
            tracker.markChanged();
        }
        return present;
    }
}