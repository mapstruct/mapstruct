/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._537;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Christian Bandowski
 */
@Mapper(uses = ReferenceMapper.class, config = Issue537MapperConfig.class)
public interface Issue537Mapper {
    Issue537Mapper INSTANCE = Mappers.getMapper( Issue537Mapper.class );

    Target mapDto(Source source);
}
