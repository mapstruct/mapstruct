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
    date = "2026-09-09T23:20:27+0200",
    comments = "version: , compiler: javac, environment: Java 21.0.5 (Eclipse Adoptium)"
)
public class JSpecifyInnerClassMapperImpl implements JSpecifyInnerClassMapper {

    @Override
    public InnerClassTargetBean.@NonNull Inner mapNonNull(InnerClassSourceBean.@NonNull Inner source) {

        InnerClassTargetBean.Inner inner = new InnerClassTargetBean.Inner();

        inner.setName( source.getName() );

        return inner;
    }

    @Override
    @NullMarked
    public InnerClassTargetBean.@Nullable Inner mapNullable(InnerClassSourceBean.@Nullable Inner source) {
        if ( source == null ) {
            return null;
        }

        InnerClassTargetBean.Inner inner = new InnerClassTargetBean.Inner();

        inner.setName( source.getName() );

        return inner;
    }
}
