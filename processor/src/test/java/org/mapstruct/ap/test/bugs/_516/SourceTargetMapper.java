/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._516;

import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


/**
 *
 * @author Sjaak Derksen
 */
@Mapper( collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED )
public interface SourceTargetMapper {

    SourceTargetMapper STM = Mappers.getMapper( SourceTargetMapper.class );

    Target map(Source source);

}
