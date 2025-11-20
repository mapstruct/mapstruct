/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.optional.nullvalue;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.ap.test.nestedproperties.simple._target.TargetObject;
import org.mapstruct.ap.test.nestedproperties.simple.source.SourceRoot;
import org.mapstruct.factory.Mappers;

/**
 * @author Dennis Melzer
 */
@Mapper(
    nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
    injectionStrategy = InjectionStrategy.CONSTRUCTOR, unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface SimpleConstructorMapper {

    SimpleConstructorMapper MAPPER = Mappers.getMapper( SimpleConstructorMapper.class );

    TargetObject toTargetObject(SourceRoot sourceRoot);

}
