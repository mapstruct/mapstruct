/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3943;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface Issue3943ErroneousMapper {

    Issue3943ErroneousMapper INSTANCE = Mappers.getMapper( Issue3943ErroneousMapper.class );

    Target map(int somethingElse);

    class Target {

        private final int value;

        public Target(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
