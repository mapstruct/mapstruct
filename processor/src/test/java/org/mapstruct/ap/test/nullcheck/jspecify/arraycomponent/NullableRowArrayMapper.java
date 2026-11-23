/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify.arraycomponent;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@NullMarked
@Mapper
public interface NullableRowArrayMapper {

    NullableRowArrayMapper INSTANCE = Mappers.getMapper( NullableRowArrayMapper.class );

    Integer[] @Nullable [] map(String[] @Nullable [] in);

    Integer[] mapRow(String[] in);

    long[] @Nullable [] mapPrimitive(int[] @Nullable [] in);

    long[] mapPrimitiveRow(int[] in);
}
