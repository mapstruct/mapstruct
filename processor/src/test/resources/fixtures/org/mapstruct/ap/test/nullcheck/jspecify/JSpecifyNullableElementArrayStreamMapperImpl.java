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
    date = "2026-11-15T12:00:00+0100",
    comments = "version: , compiler: javac, environment: Java 25 (Eclipse Adoptium)"
)
@NullMarked
public class JSpecifyNullableElementArrayStreamMapperImpl implements JSpecifyNullableElementArrayStreamMapper {

    @Override
    public Stream<NullMarkedTargetBean> mapAll(@Nullable NullMarkedSourceBean[] sources) {

        return Stream.of( sources ).map( nullMarkedSourceBean -> nullMarkedSourceBean == null ? null : map( nullMarkedSourceBean ) );
    }

    @Override
    public NullMarkedTargetBean map(NullMarkedSourceBean source) {

        NullMarkedTargetBean nullMarkedTargetBean = new NullMarkedTargetBean();

        nullMarkedTargetBean.setNonNullByDefault( source.getNonNullByDefault() );
        nullMarkedTargetBean.setExplicitlyNullable( source.getExplicitlyNullable() );

        return nullMarkedTargetBean;
    }
}
