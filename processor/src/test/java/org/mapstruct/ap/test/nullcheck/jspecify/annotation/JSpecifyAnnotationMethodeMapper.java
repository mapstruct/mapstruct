/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify.annotation;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.NullMarked;
import org.mapstruct.Mapper;
import org.mapstruct.ap.test.nullcheck.jspecify.SimpleSource;
import org.mapstruct.ap.test.nullcheck.jspecify.SimpleTarget;

@Mapper
public interface JSpecifyAnnotationMethodeMapper {

    @NonNull
    SimpleTarget mapToNonNul(@NonNull SimpleSource simpleSource);

    @NullMarked
    SimpleTarget mapToNullMarked(SimpleSource simpleSource);
}
