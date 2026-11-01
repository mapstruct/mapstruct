/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-05T14:55:49+0200",
    comments = "version: , compiler: javac, environment: Java 25 (Eclipse Adoptium)"
)
@NullMarked
public class JSpecifyNullableSourceIterableMapperImpl implements JSpecifyNullableSourceIterableMapper {

    @Override
    public @Nullable List<NullMarkedTargetBean> mapAll(@Nullable List<NullMarkedSourceBean> sources) {
        if ( sources == null ) {
            return null;
        }

        List<NullMarkedTargetBean> list = new ArrayList<>( sources.size() );
        for ( NullMarkedSourceBean nullMarkedSourceBean : sources ) {
            if ( nullMarkedSourceBean != null ) {
                list.add( map( nullMarkedSourceBean ) );
            }
        }

        return list;
    }

    @Override
    public NullMarkedTargetBean map(NullMarkedSourceBean source) {

        NullMarkedTargetBean nullMarkedTargetBean = new NullMarkedTargetBean();

        nullMarkedTargetBean.setNonNullByDefault( source.getNonNullByDefault() );
        nullMarkedTargetBean.setExplicitlyNullable( source.getExplicitlyNullable() );

        return nullMarkedTargetBean;
    }
}
