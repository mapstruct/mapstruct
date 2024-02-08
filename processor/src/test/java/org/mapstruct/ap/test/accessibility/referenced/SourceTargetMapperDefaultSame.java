/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.accessibility.referenced;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author Sjaak Derksen
 */
@Mapper(uses = ReferencedMapperDefaultSame.class, unmappedSourcePolicy = ReportingPolicy.WARN)
public interface SourceTargetMapperDefaultSame {

    SourceTargetMapperDefaultSame INSTANCE = Mappers.getMapper( SourceTargetMapperDefaultSame.class );

    @Mapping(target = "referencedTarget", source = "referencedSource")
    Target toTarget(Source source);
}
