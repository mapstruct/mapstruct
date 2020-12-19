/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2301;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface Issue2301Mapper {

    Issue2301Mapper INSTANCE = Mappers.getMapper( Issue2301Mapper.class );

    @Mapping(target = "dependantBuildRecords", ignore = true)
    @Mapping(target = "dependantBuildRecord", ignore = true)
    Artifact map(ArtifactDto dto);

    @InheritConfiguration
    void update(@MappingTarget Artifact artifact, ArtifactDto dto);
}
