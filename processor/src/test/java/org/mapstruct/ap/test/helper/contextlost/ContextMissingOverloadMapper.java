/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.helper.contextlost;

import org.jspecify.annotations.Nullable;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Pins MapStruct's general behavior for {@code @Context} when the outer mapping method does
 * <b>not</b> declare a {@code @Context} parameter: the overload {@code toInt( ContextLostInput, StringBuilder )}
 * requires a context parameter that cannot be bound, so it is not selected at all; the plain overload
 * {@code toInt( ContextLostInput )} is used instead.
 */
@Mapper
public interface ContextMissingOverloadMapper {

    ContextMissingOverloadMapper INSTANCE = Mappers.getMapper( ContextMissingOverloadMapper.class );

    @Nullable
    default Integer toInt( @Nullable ContextLostInput a ) {
        if ( a == null ) {
            return null;
        }
        return 7;
    }

    @Nullable
    default Integer toInt( @Nullable ContextLostInput a, @Context StringBuilder log ) {
        if ( a == null ) {
            return null;
        }
        log.append( "t" );
        return 42;
    }

    @Mapping( target = "number", source = "input" )
    ContextLostTargetBean map( ContextLostSourceBean h );
}
