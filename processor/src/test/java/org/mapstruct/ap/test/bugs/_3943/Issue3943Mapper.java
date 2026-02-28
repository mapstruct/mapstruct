/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3943;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Issue3943Mapper {

    Issue3943Mapper INSTANCE = Mappers.getMapper( Issue3943Mapper.class );

    TargetWithMatchingProperty mapImplicitly(int value);

//    TargetWithMatchingProperty mapImplicitlyWithoutMatchingProperty(int somethingElse);

    @Mapping(target = "value", source = "value")
    TargetWithMatchingProperty mapWithMatchingProperty(int value);

    @Mapping(target = "nonMatchingProperty", source = "value")
    TargetWithoutMatchingProperty mapWithoutMatchingProperty(int value);

    class TargetWithMatchingProperty {

        private final int value;

        public TargetWithMatchingProperty(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    class TargetWithoutMatchingProperty {

        private final int nonMatchingProperty;

        public TargetWithoutMatchingProperty(int nonMatchingProperty) {
            this.nonMatchingProperty = nonMatchingProperty;
        }

        public int getNonMatchingProperty() {
            return nonMatchingProperty;
        }
    }
}
