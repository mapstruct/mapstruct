/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify.collectiontarget;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CollectionTargetNullCheckMapper {

    CollectionTargetNullCheckMapper INSTANCE = Mappers.getMapper( CollectionTargetNullCheckMapper.class );

    CollectionTargetNullCheckTargetBean map(CollectionTargetNullCheckSourceBean source);
}
