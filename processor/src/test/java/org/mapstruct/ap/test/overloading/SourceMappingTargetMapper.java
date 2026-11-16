/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.overloading;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper( unmappedTargetPolicy = ReportingPolicy.ERROR )
public interface SourceMappingTargetMapper {

    SourceMappingTargetMapper INSTANCE = Mappers.getMapper( SourceMappingTargetMapper.class );

    @Mapping( target = "updatedOnTarget", source = "updatedOn" )
    MappingTarget sourceToTarget(Source source);

    @InheritInverseConfiguration
    Source targetToSourceReversed(MappingTarget source);

}
