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

/**
 *
 * @author Sjaak Derksen
 */
@Mapper( typeConversionPolicy = ReportingPolicy.WARN )
public interface KitchenDrawerMapper2 {

    KitchenDrawerMapper2 INSTANCE = Mappers.getMapper( KitchenDrawerMapper2.class );

    @BeanMapping( ignoreByDefault = true )
    @Mapping( target = "numberOfKnifes", source = "numberOfKnifes" )
    RegularKitchenDrawerEntity map( OversizedKitchenDrawerDto dto );

}
