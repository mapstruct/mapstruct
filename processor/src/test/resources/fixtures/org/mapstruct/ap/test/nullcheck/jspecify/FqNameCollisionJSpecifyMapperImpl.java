/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import javax.annotation.processing.Generated;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.mapstruct.ap.test.nullcheck.jspecify.dupa.Dup;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-09-12T09:06:04+0200",
    comments = "version: , compiler: javac, environment: Java 21.0.5 (Eclipse Adoptium)"
)
public class FqNameCollisionJSpecifyMapperImpl implements FqNameCollisionJSpecifyMapper {

    @Override
    public @NonNull Dup map(org.mapstruct.ap.test.nullcheck.jspecify.dupb.@NonNull Dup source) {

        Dup dup = new Dup();

        dup.setName( source.getName() );

        return dup;
    }

    @Override
    @NullMarked
    public org.mapstruct.ap.test.nullcheck.jspecify.dupb.@Nullable Dup map(@Nullable Dup source) {
        if ( source == null ) {
            return null;
        }

        org.mapstruct.ap.test.nullcheck.jspecify.dupb.Dup dup = new org.mapstruct.ap.test.nullcheck.jspecify.dupb.Dup();

        dup.setName( source.getName() );

        return dup;
    }
}
