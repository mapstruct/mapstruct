/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.collection.forged;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper( nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT )
public interface CollectionMapperNullValueMappingReturnDefault {

    CollectionMapperNullValueMappingReturnDefault INSTANCE =
        Mappers.getMapper( CollectionMapperNullValueMappingReturnDefault.class );

    Target sourceToTarget( Source source );

    Source targetToSource( Target target );
}
