/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1933;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author Sjaak Derksen
 */
@Mapper(config = Issue1933Config.class)
public interface Issue1933Mapper {

    Issue1933Mapper INSTANCE = Mappers.getMapper( Issue1933Mapper.class );

    @Mapping(target = "updateCount", source = "updateCount")
    Issue1933Config.Entity map(Issue1933Config.Dto dto);
}
