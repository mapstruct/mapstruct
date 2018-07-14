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
public interface CupboardMapper {

    CupboardMapper INSTANCE = Mappers.getMapper( CupboardMapper.class );

    void map( CupboardDto in, @MappingTarget CupboardEntity out );
}
