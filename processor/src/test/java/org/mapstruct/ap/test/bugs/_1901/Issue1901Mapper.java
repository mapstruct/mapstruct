/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1901;

import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


/**
 * @author Ruslan Mikhalev
 */
@Mapper(
    collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED
)
public interface Issue1901Mapper {

    Issue1901Mapper STM = Mappers.getMapper( Issue1901Mapper.class );

    TargetWithTwoListsAndAdders map(SourceWithTwoLists sourceWithTwoLists);

}
