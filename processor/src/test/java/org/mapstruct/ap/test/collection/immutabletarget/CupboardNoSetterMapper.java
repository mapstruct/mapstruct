/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.collection.immutabletarget;

import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author Sjaak Derksen
 */
@Mapper( collectionMappingStrategy = CollectionMappingStrategy.TARGET_IMMUTABLE )
public interface CupboardNoSetterMapper {

    CupboardNoSetterMapper INSTANCE = Mappers.getMapper( CupboardNoSetterMapper.class );

    void map( CupboardDto in, @MappingTarget CupboardEntityOnlyGetter out );
}
