/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.collection.adder;

import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ap.test.collection.adder._target.TargetWithoutSetter;
import org.mapstruct.ap.test.collection.adder.source.Source;
import org.mapstruct.factory.Mappers;

/**
 * @author Sjaak Derksen
 */
@Mapper(collectionMappingStrategy = CollectionMappingStrategy.SETTER_PREFERRED,
    uses = { PetMapper.class }
)
public interface SourceTargetMapperStrategySetterPreferred {

    SourceTargetMapperStrategySetterPreferred INSTANCE =
        Mappers.getMapper( SourceTargetMapperStrategySetterPreferred.class );

    TargetWithoutSetter toTargetDontUseAdder(Source source) throws DogException;

}
