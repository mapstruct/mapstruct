/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedbeans.maps;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AutoMapMapper {

    AutoMapMapper INSTANCE = Mappers.getMapper( AutoMapMapper.class );

    AntonymsDictionary entityToDto(AntonymsDictionaryDto antonymsDictionaryDto);

}
