/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2117;

import java.nio.file.AccessMode;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface Issue2117Mapper {

    Issue2117Mapper INSTANCE = Mappers.getMapper( Issue2117Mapper.class );

    @Mapping(target = "accessMode", source = "accessMode")
    Target toTarget(AccessMode accessMode, String otherSource);

    class Target {
        // CHECKSTYLE:OFF
        public AccessMode accessMode;
        // CHECKSTYLE ON
    }
}
