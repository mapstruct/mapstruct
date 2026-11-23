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

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-09-12T09:06:04+0200",
    comments = "version: , compiler: javac, environment: Java 21.0.5 (Eclipse Adoptium)"
)
public class DeepNestedJSpecifyMapperImpl implements DeepNestedJSpecifyMapper {

    @Override
    public DeepNestedTargetBean.Middle.@NonNull Inner mapNonNull(DeepNestedSourceBean.Middle.@NonNull Inner source) {

        DeepNestedTargetBean.Middle.Inner inner = new DeepNestedTargetBean.Middle.Inner();

        inner.setName( source.getName() );

        return inner;
    }

    @Override
    @NullMarked
    public DeepNestedTargetBean.Middle.@Nullable Inner mapNullable(DeepNestedSourceBean.Middle.@Nullable Inner source) {
        if ( source == null ) {
            return null;
        }

        DeepNestedTargetBean.Middle.Inner inner = new DeepNestedTargetBean.Middle.Inner();

        inner.setName( source.getName() );

        return inner;
    }
}
