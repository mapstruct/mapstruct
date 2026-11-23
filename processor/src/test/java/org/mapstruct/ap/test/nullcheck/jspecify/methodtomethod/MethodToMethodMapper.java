/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify.methodtomethod;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MethodToMethodMapper {

    MethodToMethodMapper INSTANCE = Mappers.getMapper( MethodToMethodMapper.class );

    @Nullable
    default MyWrapper wrap( @Nullable String s ) {
        return s == null ? null : new MyWrapper( s );
    }

    default MyTargetValue unwrap( @NonNull MyWrapper in ) {
        return new MyTargetValue( in.getValue(), 42 );
    }

    @Mapping( target = "value", source = "name" )
    MethodToMethodTargetBean map( MethodToMethodSourceBean source );
}
