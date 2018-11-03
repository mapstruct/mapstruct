/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1576.java8;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Issue1576Mapper {

    Issue1576Mapper INSTANCE = Mappers.getMapper( Issue1576Mapper.class );

    Target map( Source source );
}
