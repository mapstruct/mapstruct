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

@Mapper( typeConversionPolicy = ReportingPolicy.ERROR )
public interface ErroneousKitchenDrawerOptionalMapper4 {

    ErroneousKitchenDrawerOptionalMapper4 INSTANCE = Mappers.getMapper( ErroneousKitchenDrawerOptionalMapper4.class );

    @BeanMapping( ignoreByDefault = true )
    @Mapping( target = "depth", source = "depth" )
    RegularKitchenDrawerEntity map( OversizedKitchenDrawerOptionalDto dto );

}
