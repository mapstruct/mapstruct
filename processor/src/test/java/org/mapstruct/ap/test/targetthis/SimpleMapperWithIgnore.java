/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.targetthis;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author Sjaak Derksen
 */
@Mapper
public interface SimpleMapperWithIgnore {
    SimpleMapperWithIgnore INSTANCE = Mappers.getMapper( SimpleMapperWithIgnore.class );

    @Mapping( target = ".", source = "item" )
    @Mapping( target = "id", ignore = true )
    CustomerItem map(CustomerDTO customer);

}
