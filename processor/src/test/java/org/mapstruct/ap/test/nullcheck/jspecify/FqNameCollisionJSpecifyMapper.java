/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.mapstruct.Mapper;
import org.mapstruct.ap.test.nullcheck.jspecify.dupa.Dup;
import org.mapstruct.factory.Mappers;

/**
 * Mapper between two packages that both contain a bean with the simple name {@code Dup}. In the generated
 * code at least one of them must be referenced by its fully qualified name, so a JSpecify types-use
 * annotation on a qualified name is forced, e.g. {@code ...dupb.@NonNull Dup}.
 */
@Mapper
public interface FqNameCollisionJSpecifyMapper {

    FqNameCollisionJSpecifyMapper INSTANCE = Mappers.getMapper( FqNameCollisionJSpecifyMapper.class );

    @NonNull Dup map(org.mapstruct.ap.test.nullcheck.jspecify.dupb.@NonNull Dup source);

    @NullMarked
    org.mapstruct.ap.test.nullcheck.jspecify.dupb.@Nullable Dup map(@Nullable Dup source);
}
