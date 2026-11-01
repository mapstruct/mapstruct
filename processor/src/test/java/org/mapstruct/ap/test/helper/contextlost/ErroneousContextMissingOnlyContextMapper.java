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
 * Pins MapStruct's general behavior for {@code @Context} when the only user method covering the mapping
 * requires a context parameter that is not available on the outer mapping method: the method is not
 * selected at all, so the 2-step mapping cannot be generated: resolving the explicit mapping fails with
 * an error, and no code referencing a missing context parameter is generated.
 */
@Mapper
public interface ErroneousContextMissingOnlyContextMapper {

    ErroneousContextMissingOnlyContextMapper INSTANCE =
        Mappers.getMapper( ErroneousContextMissingOnlyContextMapper.class );

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
