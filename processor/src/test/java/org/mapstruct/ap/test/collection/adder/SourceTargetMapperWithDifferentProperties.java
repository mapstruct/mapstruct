/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.collection.adder;

import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ap.test.collection.adder._target.TargetWithAnimals;
import org.mapstruct.ap.test.collection.adder.source.SourceWithPets;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper(collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface SourceTargetMapperWithDifferentProperties {

    SourceTargetMapperWithDifferentProperties INSTANCE =
        Mappers.getMapper( SourceTargetMapperWithDifferentProperties.class );

    @Mapping(target = "animals", source = "pets")
    TargetWithAnimals map(SourceWithPets source);
}
