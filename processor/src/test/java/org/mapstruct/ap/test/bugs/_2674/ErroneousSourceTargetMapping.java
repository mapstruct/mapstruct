/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2674;

import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
interface ErroneousSourceTargetMapping {

    ErroneousSourceTargetMapping INSTANCE = Mappers.getMapper( ErroneousSourceTargetMapping.class );

    @BeforeMapping
    void beforeMappingMethod(Target target, @MappingTarget Source source);

    @AfterMapping
    void afterMappingMethod(Source source, @MappingTarget Target target);

    Target toTarget(Source source);

    Source toSource(Target target);
}
