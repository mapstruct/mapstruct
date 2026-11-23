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
 * The context parameter of the mapping method has the same name as the source parameter of the method used in the
 * first step of the null-safe two-step helper.
 */
@Mapper
public interface ContextNamedAsSourceParameterMapper {

    ContextNamedAsSourceParameterMapper INSTANCE = Mappers.getMapper( ContextNamedAsSourceParameterMapper.class );

    @Nullable
    default Integer toInt( @Nullable ContextLostInput a, @Context StringBuilder log ) {
        if ( a == null ) {
            return null;
        }
        log.append( "t" );
        return 42;
    }

    @Mapping( target = "number", source = "input" )
    ContextLostTargetBean map( ContextLostSourceBean h, @Context StringBuilder a );
}
