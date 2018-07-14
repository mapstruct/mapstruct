/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._971;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author Filip Hrisafov
 */
@Mapper
public interface Issue971CollectionMapper {

    Issue971CollectionMapper INSTANCE = Mappers.getMapper( Issue971CollectionMapper.class );

    CollectionSource mapTargetToSource(CollectionTarget source);

}
