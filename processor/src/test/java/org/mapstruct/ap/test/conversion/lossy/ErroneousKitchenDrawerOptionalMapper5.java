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
public interface ErroneousKitchenDrawerOptionalMapper5 {

    ErroneousKitchenDrawerOptionalMapper5 INSTANCE = Mappers.getMapper( ErroneousKitchenDrawerOptionalMapper5.class );

    @BeanMapping( ignoreByDefault = true )
    @Mapping( target = "length", source = "length" )
    RegularKitchenDrawerEntity map( OversizedKitchenDrawerOptionalDto dto );

}
