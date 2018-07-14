/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1170;

import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ap.test.bugs._1170._target.Target;
import org.mapstruct.ap.test.bugs._1170.source.Source;
import org.mapstruct.factory.Mappers;

/**
 * @author Cornelius Dirmeier
 */
@Mapper(collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
    uses = { PetMapper.class })
public interface AdderSourceTargetMapper {

    AdderSourceTargetMapper INSTANCE = Mappers.getMapper( AdderSourceTargetMapper.class );

    Target toTarget(Source source);

    Source toSource(Target source);

}
