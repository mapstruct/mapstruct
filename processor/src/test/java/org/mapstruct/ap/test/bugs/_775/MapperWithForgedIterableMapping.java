/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._775;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Andreas Gudian
 */
@Mapper
public abstract class MapperWithForgedIterableMapping {
    public static final MapperWithForgedIterableMapping INSTANCE =
        Mappers.getMapper( MapperWithForgedIterableMapping.class );

    public abstract IterableContainer toContainerWithIterable(ListContainer source);

}
