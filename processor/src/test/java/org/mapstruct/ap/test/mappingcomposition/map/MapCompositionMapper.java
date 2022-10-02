/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.mappingcomposition.map;

import java.util.Map;

import org.mapstruct.Mapper;

/**
 * @author orange add
 */
@Mapper
public interface MapCompositionMapper {

    @ToMap
    Map<String, String> stringIntegerMapToStringStringMap(Map<String, Integer> source);
}
