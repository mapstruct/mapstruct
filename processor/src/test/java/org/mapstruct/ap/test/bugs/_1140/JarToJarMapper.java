package org.mapstruct.ap.test.bugs._1140;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface JarToJarMapper {

    JarToJarMapper INSTANCE = Mappers.getMapper( JarToJarMapper.class );

    EmptyJar mapToEmptyJar(FilledJar filledJar);
}
