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
 * Like {@link ContextLostMapper}, but the {@code @Context} parameter of the user method is declared at the
 * first position instead of the last one.
 */
@Mapper
public interface ContextFirstPositionMapper {

    ContextFirstPositionMapper INSTANCE = Mappers.getMapper( ContextFirstPositionMapper.class );

    @Nullable
    default Integer toInt( @Context StringBuilder log, @Nullable ContextLostInput a ) {
        if ( a == null ) {
            return null;
        }
        log.append( "t" );
        return 42;
    }

    @Mapping( target = "number", source = "input" )
    ContextLostTargetBean map( ContextLostSourceBean h, @Context StringBuilder log );
}
