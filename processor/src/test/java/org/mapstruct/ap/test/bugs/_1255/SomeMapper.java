/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1255;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author Sjaak Derksen
 */
@Mapper(config = SomeMapperConfig.class)
public interface SomeMapper {

    SomeMapper INSTANCE = Mappers.getMapper( SomeMapper.class );

    SomeA toSomeA(SomeB source);

    SomeB toSomeB(SomeA source);
}
