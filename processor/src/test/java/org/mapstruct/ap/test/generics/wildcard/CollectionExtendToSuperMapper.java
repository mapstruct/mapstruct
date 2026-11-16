/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.generics.wildcard;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CollectionExtendToSuperMapper {

    CollectionExtendToSuperMapper INSTANCE = Mappers.getMapper( CollectionExtendToSuperMapper.class );

    CollectionSuperTypes toSuper(CollectionExtendTypes extendTypes);
}
