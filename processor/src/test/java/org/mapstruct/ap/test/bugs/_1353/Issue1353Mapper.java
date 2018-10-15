/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1353;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author Jeffrey Smyth
 */
@Mapper
public interface Issue1353Mapper {

    Issue1353Mapper INSTANCE = Mappers.getMapper( Issue1353Mapper.class );

    @Mappings ({
        @Mapping (target = "string2 ", source = " source.string1")
    })
    Target sourceToTarget(Source source);
}
