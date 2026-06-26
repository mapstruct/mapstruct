/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._4000;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Issue4000Mapper {

    Issue4000Mapper INSTANCE = Mappers.getMapper( Issue4000Mapper.class );

    Target map(Source source);
}
