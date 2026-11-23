/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.helper.reuse;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Both properties are mapped with the same two-step method chain. The configured number formats are not part of the
 * generated helper body, so a single helper must serve both mappings.
 */
@Mapper
public interface ReuseMapper {

    ReuseMapper INSTANCE = Mappers.getMapper( ReuseMapper.class );

    @Nullable
    default Wrapper wrap( @Nullable String s ) {
        return s == null ? null : new Wrapper( s );
    }

    default Value unwrap( @NonNull Wrapper in ) {
        return new Value( in.getValue(), 42 );
    }

    @Mapping( target = "valueA", source = "name", numberFormat = "#.00" )
    @Mapping( target = "valueB", source = "name", numberFormat = "#,###" )
    ReuseTarget map( ReuseSource source );
}
