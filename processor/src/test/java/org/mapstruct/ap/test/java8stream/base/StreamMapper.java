/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.java8stream.base;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface StreamMapper {

    StreamMapper INSTANCE = Mappers.getMapper( StreamMapper.class );

    @Mappings( {
        @Mapping(target = "targetStream", source = "stream"),
        @Mapping(target = "targetElements", source = "sourceElements")
    } )
    Target map(Source source);

    @InheritInverseConfiguration
    Source map(Target target);

    SourceElement mapElement(TargetElement targetElement) throws MyCustomException;

    @InheritInverseConfiguration
    TargetElement mapElement(SourceElement sourceElement);

    @InheritInverseConfiguration
    Source updateSource(Target target, @MappingTarget Source source) throws MyCustomException;
}
