/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify.sourcecheck;

import java.util.List;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@NullMarked
@Mapper
public interface NonNullResultMethodMapper {

    NonNullResultMethodMapper INSTANCE = Mappers.getMapper( NonNullResultMethodMapper.class );

    @BeanMapping( ignoreByDefault = true )
    @Mapping( target = "name", source = "name", qualifiedByName = "normalize", defaultValue = "default" )
    @Mapping( target = "nick", source = "nick", qualifiedByName = "normalize" )
    @Mapping( target = "numbers", source = "numbers" )
    Target map(Source source);

    List<String> mapNumbers(List<Integer> numbers);

    @Named( "normalize" )
    default String normalize(@Nullable String value) {
        return value == null ? "normalized null" : value.trim();
    }
}
