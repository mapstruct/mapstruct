/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1899;

import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


/**
 * @author Ruslan Mikhalev
 */
@Mapper(
    collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED
)
public interface Issue1899Mapper {

    Issue1899Mapper STM = Mappers.getMapper( Issue1899Mapper.class );

    TargetWithTwoLists map(SourceWithTwoLists sourceWithListWithTwoLists);

}
