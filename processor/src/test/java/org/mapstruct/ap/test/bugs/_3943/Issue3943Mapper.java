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

    @Mapping(target = "value", source = "value")
    TargetWithMatchingProperty mapWithMatchingProperty(int value);

    @Mapping(target = "nonMatchingProperty", source = "value")
    TargetWithoutMatchingProperty mapWithoutMatchingProperty(int value);

    class TargetWithMatchingProperty {

        private final long value;

        public TargetWithMatchingProperty(long value) {
            this.value = value;
        }

        public long getValue() {
            return value;
        }
    }

    class TargetWithoutMatchingProperty {

        private final long nonMatchingProperty;

        public TargetWithoutMatchingProperty(long nonMatchingProperty) {
            this.nonMatchingProperty = nonMatchingProperty;
        }

        public long getNonMatchingProperty() {
            return nonMatchingProperty;
        }
    }
}
