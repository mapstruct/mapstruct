/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.lossy;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper( typeConversionPolicy = ReportingPolicy.WARN, uses = VerySpecialNumberMapper.class )
public interface KitchenDrawerOptionalMapper6 {

    KitchenDrawerOptionalMapper6 INSTANCE = Mappers.getMapper( KitchenDrawerOptionalMapper6.class );

    @BeanMapping( ignoreByDefault = true )
    @Mapping( target = "height", source = "height" )
    RegularKitchenDrawerEntity map( OversizedKitchenDrawerOptionalDto dto );

}
