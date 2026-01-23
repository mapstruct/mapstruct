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
    Target map(int value);

    class Target {

        private final long value;

        public Target(long value) {
            this.value = value;
        }

        public long getValue() {
            return value;
        }
    }
}
