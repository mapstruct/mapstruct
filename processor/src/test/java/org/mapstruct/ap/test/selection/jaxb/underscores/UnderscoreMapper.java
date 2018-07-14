/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.jaxb.underscores;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper( uses = { ObjectFactory.class } )
public interface UnderscoreMapper {
    UnderscoreMapper INSTANCE = Mappers.getMapper( UnderscoreMapper.class );

    SubType map(UnderscoreType underscoreType);
}
