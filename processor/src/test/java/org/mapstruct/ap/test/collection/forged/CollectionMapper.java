/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.collection.forged;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CollectionMapper {

    CollectionMapper INSTANCE = Mappers.getMapper( CollectionMapper.class );

    Target sourceToTarget(Source source);

    Source targetToSource(Target target);
}
