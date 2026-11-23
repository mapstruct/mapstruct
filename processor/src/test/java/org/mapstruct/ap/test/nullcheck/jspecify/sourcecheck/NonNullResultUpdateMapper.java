/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify.sourcecheck;

import org.jspecify.annotations.NullMarked;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@NullMarked
@Mapper
public interface NonNullResultUpdateMapper {

    NonNullResultUpdateMapper INSTANCE = Mappers.getMapper( NonNullResultUpdateMapper.class );

    @BeanMapping( ignoreByDefault = true )
    @Mapping( target = "child", source = "child" )
    Target update(Source source, @MappingTarget Target target);

    Target.Child updateChild(Source.Child source, @MappingTarget Target.Child target);
}
