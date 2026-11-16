/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3110;

import org.mapstruct.EnumMapping;
import org.mapstruct.Mapper;

@Mapper
public interface Issue3110Mapper {
    enum SourceEnum {
        FOO, BAR
    }

    enum TargetEnum {
        FOO, BAR
    }

    class CustomCheckedException extends Exception {
        public CustomCheckedException(String message) {
            super( message );
        }
    }

    @EnumMapping(unexpectedValueMappingException = CustomCheckedException.class)
    TargetEnum map(SourceEnum sourceEnum) throws CustomCheckedException;
}
