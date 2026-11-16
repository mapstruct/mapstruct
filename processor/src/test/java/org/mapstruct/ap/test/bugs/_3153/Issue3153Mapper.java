/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3153;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ValueMapping;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
interface Issue3153Mapper {

    Issue3153Mapper INSTANCE = Mappers.getMapper( Issue3153Mapper.class );

    @ValueMapping(source = " PR", target = "PR")
    @ValueMapping(source = "  PR", target = "PR")
    @ValueMapping(source = "   PR", target = "PR")
    @ValueMapping(source = MappingConstants.ANY_REMAINING, target = MappingConstants.NULL)
    Target mapToEnum(String value);

    @ValueMapping(source = "PR", target = "   PR")
    String mapFromEnum(Target value);

    enum Target {
        PR,
    }
}
