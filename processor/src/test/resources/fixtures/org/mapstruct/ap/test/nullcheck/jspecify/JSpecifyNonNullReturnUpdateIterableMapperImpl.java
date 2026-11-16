/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-05T15:01:46+0200",
    comments = "version: , compiler: javac, environment: Java 25 (Eclipse Adoptium)"
)
public class JSpecifyNonNullReturnUpdateIterableMapperImpl implements JSpecifyNonNullReturnUpdateIterableMapper {

    @Override
    public List<NullMarkedTargetBean> mapAll(List<NullMarkedSourceBean> sources, List<NullMarkedTargetBean> target) {
        if ( sources == null ) {
            return target;
        }

        target.clear();
        for ( NullMarkedSourceBean nullMarkedSourceBean : sources ) {
            target.add( map( nullMarkedSourceBean ) );
        }

        return target;
    }

    @Override
    public NullMarkedTargetBean map(NullMarkedSourceBean source) {

        NullMarkedTargetBean nullMarkedTargetBean = new NullMarkedTargetBean();

        nullMarkedTargetBean.setNonNullByDefault( source.getNonNullByDefault() );
        nullMarkedTargetBean.setExplicitlyNullable( source.getExplicitlyNullable() );

        return nullMarkedTargetBean;
    }
}
