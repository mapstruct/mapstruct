/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._375;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Issue375Mapper {

    Issue375Mapper INSTANCE = Mappers.getMapper( Issue375Mapper.class );

    Target mapTo(Source string);
}
