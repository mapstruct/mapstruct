/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1720;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(config = SharedConfig.class)
public interface Issue1720Mapper {

    Issue1720Mapper INSTANCE = Mappers.getMapper( Issue1720Mapper.class );

    @InheritConfiguration
    Target map(Source mySource, int value);

}
