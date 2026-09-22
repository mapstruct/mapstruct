/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import javax.annotation.processing.Generated;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-14T23:55:43+0200",
    comments = "version: , compiler: javac, environment: Java 25 (Eclipse Adoptium)"
)
@NullMarked
public class JSpecifyNonNullReturnArrayMapperImpl implements JSpecifyNonNullReturnArrayMapper {

    @Override
    public NullMarkedTargetBean[] mapAll(@Nullable NullMarkedSourceBean[] sources) {
        if ( sources == null ) {
            return new NullMarkedTargetBean[0];
        }

        NullMarkedTargetBean[] nullMarkedTargetBeanTmp = new NullMarkedTargetBean[sources.length];
        int i = 0;
        for ( NullMarkedSourceBean nullMarkedSourceBean : sources ) {
            nullMarkedTargetBeanTmp[i] = map( nullMarkedSourceBean );
            i++;
        }

        return nullMarkedTargetBeanTmp;
    }

    @Override
    public NullMarkedTargetBean map(NullMarkedSourceBean source) {

        NullMarkedTargetBean nullMarkedTargetBean = new NullMarkedTargetBean();

        nullMarkedTargetBean.setNonNullByDefault( source.getNonNullByDefault() );
        nullMarkedTargetBean.setExplicitlyNullable( source.getExplicitlyNullable() );

        return nullMarkedTargetBean;
    }
}
