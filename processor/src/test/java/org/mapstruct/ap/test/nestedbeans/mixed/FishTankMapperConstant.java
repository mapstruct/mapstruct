/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedbeans.mixed;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ap.test.nestedbeans.mixed._target.FishTankDto;
import org.mapstruct.ap.test.nestedbeans.mixed.source.FishTank;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author Sjaak Derksen
 */
@Mapper
public interface FishTankMapperConstant {

    FishTankMapperConstant INSTANCE = Mappers.getMapper( FishTankMapperConstant.class );

    @Mapping(target = "fish.kind", source = "fish.type")
    @Mapping(target = "fish.name", constant = "Nemo")
    @Mapping(target = "ornament", ignore = true )
    @Mapping(target = "material.materialType", source = "material")
    @Mapping(target = "material.manufacturer",  constant = "MMM" )
    @Mapping(target = "quality", ignore = true)
    FishTankDto map( FishTank source  );

}
