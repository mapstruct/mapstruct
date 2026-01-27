/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.collection.remover;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Jarle Sætre
 */
@Mapper
public interface MapperWithRemoverInTarget {

    MapperWithRemoverInTarget INSTANCE = Mappers.getMapper( MapperWithRemoverInTarget.class );

    TargetWithRemover map(Source source);

}
