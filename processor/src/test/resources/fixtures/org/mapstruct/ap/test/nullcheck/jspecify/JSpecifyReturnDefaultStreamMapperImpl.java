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
    date = "2026-09-13T22:36:22+0200",
    comments = "version: , compiler: javac, environment: Java 21.0.5 (Eclipse Adoptium)"
)
@NullMarked
public class JSpecifyReturnDefaultStreamMapperImpl implements JSpecifyReturnDefaultStreamMapper {

    @Override
    public Stream<NullMarkedTargetBean> mapAll(@Nullable Stream<NullMarkedSourceBean> sources) {
        if ( sources == null ) {
            return Stream.empty();
        }

        return sources.filter( resultName -> resultName != null ).map( nullMarkedSourceBean -> map( nullMarkedSourceBean ) );
    }

    @Override
    public NullMarkedTargetBean map(NullMarkedSourceBean source) {

        NullMarkedTargetBean nullMarkedTargetBean = new NullMarkedTargetBean();

        nullMarkedTargetBean.setNonNullByDefault( source.getNonNullByDefault() );
        nullMarkedTargetBean.setExplicitlyNullable( source.getExplicitlyNullable() );

        return nullMarkedTargetBean;
    }
}
