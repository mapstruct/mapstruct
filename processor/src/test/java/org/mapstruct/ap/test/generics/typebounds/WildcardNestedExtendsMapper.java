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
public abstract class WildcardNestedExtendsMapper {
    public static final WildcardNestedExtendsMapper INSTANCE = Mappers.getMapper( WildcardNestedExtendsMapper.class );

    @Mapping( target = "object", source = "contained.object" )
    public abstract Target map(SourceContainer source);

    String mapToString(Wildcard rawData) {
        return rawData.getContents();
    }
}
