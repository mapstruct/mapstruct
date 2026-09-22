/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify.builtintomethod;

import org.jspecify.annotations.NonNull;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BuiltInMethodMapper {

    BuiltInMethodMapper INSTANCE = Mappers.getMapper( BuiltInMethodMapper.class );

    default int toLength( @NonNull String in ) {
        return in.length();
    }

    @Mapping( target = "length", source = "elem" )
    BuiltInMethodTargetBean map( BuiltInMethodSourceBean source );
}
