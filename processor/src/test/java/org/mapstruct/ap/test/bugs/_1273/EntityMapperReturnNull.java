/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1273;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;

@Mapper( nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL )
public interface EntityMapperReturnNull {

    Dto asTarget(Entity entity);

}
