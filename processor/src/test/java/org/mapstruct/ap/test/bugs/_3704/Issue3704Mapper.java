/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3704;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Issue3704Mapper {

    Issue3704Mapper INSTANCE = Mappers.getMapper( Issue3704Mapper.class );

    Target map(Source source);
}
