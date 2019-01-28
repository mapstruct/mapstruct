/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1561;

import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author Sebastian Haberey
 */
@Mapper(collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface Issue1561Mapper {

    Issue1561Mapper
        INSTANCE = Mappers.getMapper( Issue1561Mapper.class );

    @Mapping( target = "nestedTarget.properties", source = "properties")
    Target map(Source source);

    @InheritInverseConfiguration
    Source map(Target target);
}
