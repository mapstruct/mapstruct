/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.java8stream.erroneous;

import java.util.List;
import java.util.stream.Stream;

import org.mapstruct.Mapper;
import org.mapstruct.ap.test.NoProperties;
import org.mapstruct.ap.test.WithProperties;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface ErroneousStreamToListNoElementMappingFound {

    ErroneousStreamToListNoElementMappingFound INSTANCE =
        Mappers.getMapper( ErroneousStreamToListNoElementMappingFound.class );

    List<NoProperties> mapStreamToCollection(Stream<WithProperties> source);
}
