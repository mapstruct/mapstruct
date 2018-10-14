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
public interface FishTankMapperExpression {

    FishTankMapperExpression INSTANCE = Mappers.getMapper( FishTankMapperExpression.class );

    @Mapping(target = "fish.kind", source = "fish.type")
    @Mapping(target = "fish.name", expression = "java(\"Jaws\")")
    @Mapping(target = "plant", ignore = true )
    @Mapping(target = "ornament", ignore = true )
    @Mapping(target = "material", ignore = true)
    @Mapping(target = "quality.report.organisation.name", expression = "java(\"Dunno\")" )
    FishTankDto map( FishTank source  );

}
