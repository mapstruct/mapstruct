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
public abstract class WildcardNestedInheritedExtendsMapper {
    public static final WildcardNestedInheritedExtendsMapper INSTANCE =
        Mappers.getMapper( WildcardNestedInheritedExtendsMapper.class );

    @Mapping( target = "object", source = "contained.object" )
    public abstract Target map(SourceContainerInherited<?> source);

    String mapToString(WildcardedInterface rawData) {
        return rawData.getContents();
    }
}
