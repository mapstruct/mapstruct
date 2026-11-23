/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.overload;

import org.jspecify.annotations.Nullable;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

/**
 * Two overloads of the same method name {@code map}, each mapping a different source type to an intermediate object
 * type that is in turn converted to a {@code String} target. The {@code @Named} qualifiers are only used so MapStruct
 * can select the right overload per property; both overloads carry the same method name.
 *
 * @author hduelme
 */
@Mapper
public interface OverloadMapper {

    OverloadMapper INSTANCE = Mappers.getMapper( OverloadMapper.class );

    @Named( "mapA" )
    default @Nullable Integer map( @Nullable OverloadA a ) {
        return a == null || a.getValue() == null ? null : a.getValue() * 100;
    }

    @Named( "mapB" )
    default @Nullable Integer map( @Nullable OverloadB b ) {
        return b == null || b.getValue() == null ? null : b.getValue() * 100;
    }

    @Mapping( target = "a", source = "a", qualifiedByName = "mapA" )
    @Mapping( target = "b", source = "b", qualifiedByName = "mapB" )
    OverloadHolderTarget mapHolder( @Nullable OverloadHolderSource source );
}
