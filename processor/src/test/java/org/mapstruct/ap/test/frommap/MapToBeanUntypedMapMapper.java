/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.frommap;

import java.util.Map;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Christian Kosmowski
 */
@Mapper
public interface MapToBeanUntypedMapMapper {

    MapToBeanUntypedMapMapper INSTANCE = Mappers.getMapper( MapToBeanUntypedMapMapper.class );

    Target toTarget(Map source);

    class Target {
    }

}
