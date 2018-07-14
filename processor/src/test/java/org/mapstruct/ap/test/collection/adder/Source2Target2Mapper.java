/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.collection.adder;

import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ap.test.collection.adder._target.Target2;
import org.mapstruct.ap.test.collection.adder.source.Source2;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author Sjaak Derksen
 */
@Mapper( collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED )
public abstract class Source2Target2Mapper {

    public static final Source2Target2Mapper INSTANCE = Mappers.getMapper( Source2Target2Mapper.class );

    public abstract Target2 toTarget( Source2 source );

}
