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
    date = "2026-10-27T12:18:04+0100",
    comments = "version: , compiler: javac, environment: Java 21.0.12.1 (Eclipse Adoptium)"
)
@NullMarked
public class JSpecifyNullableElementArrayMapperImpl implements JSpecifyNullableElementArrayMapper {

    @Override
    public @Nullable NullMarkedTargetBean[] map(@Nullable NullMarkedSourceBean[] sources) {

        NullMarkedTargetBean[] nullMarkedTargetBeanTmp = new NullMarkedTargetBean[sources.length];
        int i = 0;
        for ( NullMarkedSourceBean nullMarkedSourceBean : sources ) {
            if ( nullMarkedSourceBean != null ) {
                nullMarkedTargetBeanTmp[i] = map( nullMarkedSourceBean );
            }
            i++;
        }

        return nullMarkedTargetBeanTmp;
    }

    @Override
    public NullMarkedTargetBean[] @Nullable [] map2dim(NullMarkedSourceBean[] @Nullable [] sourceBeans) {

        NullMarkedTargetBean[][] nullMarkedTargetBeanArrayTmp = new NullMarkedTargetBean[sourceBeans.length][];
        int i = 0;
        for ( NullMarkedSourceBean[] nullMarkedSourceBeanArray : sourceBeans ) {
            nullMarkedTargetBeanArrayTmp[i] = map( nullMarkedSourceBeanArray );
            i++;
        }

        return nullMarkedTargetBeanArrayTmp;
    }

    @Override
    public NullMarkedTargetBean map(NullMarkedSourceBean source) {

        NullMarkedTargetBean nullMarkedTargetBean = new NullMarkedTargetBean();

        nullMarkedTargetBean.setNonNullByDefault( source.getNonNullByDefault() );
        nullMarkedTargetBean.setExplicitlyNullable( source.getExplicitlyNullable() );

        return nullMarkedTargetBean;
    }
}
