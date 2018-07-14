/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.collection.erroneous;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ap.test.NoProperties;
import org.mapstruct.ap.test.WithProperties;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author Sjaak Derksen
 */
@Mapper
public interface ErroneousCollectionNoElementMappingFound {

    ErroneousCollectionNoElementMappingFound INSTANCE =
        Mappers.getMapper( ErroneousCollectionNoElementMappingFound.class );

    List<NoProperties> map(List<WithProperties> source);

}
