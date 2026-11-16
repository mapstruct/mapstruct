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

@Mapper( typeConversionPolicy = ReportingPolicy.ERROR )
public interface ErroneousKitchenDrawerMapper6 {

    @BeanMapping( ignoreByDefault = true )
    @Mapping( target = "drawerId", source = "drawerId" )
    RegularKitchenDrawerEntity map( OversizedKitchenDrawerDto dto );

}
