/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2795;

import java.util.Optional;

import org.mapstruct.Condition;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface Issue2795Mapper {

    void update(Source update, @MappingTarget Target destination);

    void update(NestedDto update, @MappingTarget Nested destination);

    static <T> T unwrap(Optional<T> optional) {
        return optional.orElse( null );
    }

    @Condition
    static <T> boolean isNotEmpty(Optional<T> field) {
        return field != null && field.isPresent();
    }

}
