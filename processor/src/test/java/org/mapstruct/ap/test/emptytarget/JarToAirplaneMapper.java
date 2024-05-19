package org.mapstruct.ap.test.emptytarget;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface JarToAirplaneMapper {

    JarToAirplaneMapper INSTANCE = Mappers.getMapper( JarToAirplaneMapper.class );

    AirplaneWithNoAccessors mapToAirplaneWithNoAccessors(FilledJar jar);
}
