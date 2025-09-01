/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.generics.typebounds;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author Ben Zegveld
 */
@Mapper
public abstract class WildcardConditionalExtendsMapper {
    public static final WildcardConditionalExtendsMapper INSTANCE =
        Mappers.getMapper( WildcardConditionalExtendsMapper.class );

    @Mapping( target = "object", source = "object.contents" )
    public abstract Target map(Source<?> action);
}
