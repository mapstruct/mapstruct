/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify.builtintobuiltIn;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BuiltInToBuiltInMapper {

    BuiltInToBuiltInMapper INSTANCE = Mappers.getMapper( BuiltInToBuiltInMapper.class );

    @Mapping( target = "dateTime", source = "elem" )
    BuiltInToBuiltInTargetBean map( BuiltInToBuiltInSourceBean source );
}
