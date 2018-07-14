/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.java8stream.forged;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface StreamMapperNullValueMappingReturnDefault {

    StreamMapperNullValueMappingReturnDefault INSTANCE =
        Mappers.getMapper( StreamMapperNullValueMappingReturnDefault.class );

    Target sourceToTarget(Source source);

    Source targetToSource(Target target);
}
