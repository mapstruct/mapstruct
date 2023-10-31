/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3400;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Issue3400Mapper {

    Issue3400Mapper INSTANCE = Mappers.getMapper( Issue3400Mapper.class );

    @Mapping(target = "longField", source = "longField", defaultValue = "42L")
    ObjectTo map(ObjectFrom from);
}
