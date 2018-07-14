/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.collection.erroneous;

import java.util.Map;

import org.mapstruct.Mapper;
import org.mapstruct.ap.test.NoProperties;
import org.mapstruct.ap.test.WithProperties;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author Sjaak Derksen
 */
@Mapper
public interface ErroneousCollectionNoValueMappingFound {

    ErroneousCollectionNoValueMappingFound INSTANCE =
        Mappers.getMapper( ErroneousCollectionNoValueMappingFound.class );

    Map<String, NoProperties> map(Map<String, WithProperties> source);

}
