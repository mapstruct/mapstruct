/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.collection.adder;

import org.mapstruct.Mapper;
import org.mapstruct.ap.test.collection.adder._target.Target;
import org.mapstruct.ap.test.collection.adder.source.Source;
import org.mapstruct.factory.Mappers;

/**
 * @author Sjaak Derksen
 */
@Mapper(uses = { PetMapper.class })
public interface SourceTargetMapperStrategyDefault {

    SourceTargetMapperStrategyDefault INSTANCE = Mappers.getMapper( SourceTargetMapperStrategyDefault.class );

    Target shouldFallBackToAdder(Source source) throws DogException;

}
