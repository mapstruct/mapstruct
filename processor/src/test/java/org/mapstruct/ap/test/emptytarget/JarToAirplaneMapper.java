/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.emptytarget;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface JarToAirplaneMapper {

    JarToAirplaneMapper INSTANCE = Mappers.getMapper( JarToAirplaneMapper.class );

    AirplaneWithNoAccessors mapToAirplaneWithNoAccessors(FilledJar jar);
}
