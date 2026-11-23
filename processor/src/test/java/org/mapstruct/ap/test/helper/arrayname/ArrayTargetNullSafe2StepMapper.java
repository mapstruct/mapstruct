/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.helper.arrayname;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@NullMarked
@Mapper
public interface ArrayTargetNullSafe2StepMapper {

    ArrayTargetNullSafe2StepMapper INSTANCE = Mappers.getMapper( ArrayTargetNullSafe2StepMapper.class );

    @Mapping( target = "values", source = "input" )
    Target map(Source source);

    default @Nullable Middle toMiddle(Input input) {
        return input.value == null ? null : new Middle( input.value );
    }

    default String[] toArray(Middle middle) {
        return middle.value.split( "," );
    }

    class Input {
        private final @Nullable String value;

        public Input(@Nullable String value) {
            this.value = value;
        }
    }

    class Middle {
        private final String value;

        public Middle(String value) {
            this.value = value;
        }
    }

    class Source {
        private final Input input;

        public Source(Input input) {
            this.input = input;
        }

        public Input getInput() {
            return input;
        }
    }

    class Target {
        private String @Nullable [] values;

        public String @Nullable [] getValues() {
            return values;
        }

        public void setValues(String @Nullable [] values) {
            this.values = values;
        }
    }
}
