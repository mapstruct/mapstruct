/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import java.util.stream.Stream;
import javax.annotation.processing.Generated;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-11-16T12:00:00+0100",
    comments = "version: , compiler: javac, environment: Java 25 (Eclipse Adoptium)"
)
@NullMarked
public class JSpecifyStreamToNonNullElementArrayMapperImpl implements JSpecifyStreamToNonNullElementArrayMapper {

    @Override
    public NullMarkedTargetBean[] mapAll(@Nullable Stream<NullMarkedSourceBean> sources) {
        if ( sources == null ) {
            return new NullMarkedTargetBean[0];
        }

        return sources.filter( nullMarkedSourceBean -> nullMarkedSourceBean != null ).map( nullMarkedSourceBean -> map( nullMarkedSourceBean ) )
        .toArray( NullMarkedTargetBean[]::new );
    }

    @Override
    public NullMarkedTargetBean map(NullMarkedSourceBean source) {

        NullMarkedTargetBean nullMarkedTargetBean = new NullMarkedTargetBean();

        nullMarkedTargetBean.setNonNullByDefault( source.getNonNullByDefault() );
        nullMarkedTargetBean.setExplicitlyNullable( source.getExplicitlyNullable() );

        return nullMarkedTargetBean;
    }
}
