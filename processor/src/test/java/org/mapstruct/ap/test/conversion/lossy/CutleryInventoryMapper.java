/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.lossy;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper( typeConversionPolicy = ReportingPolicy.ERROR )
public interface CutleryInventoryMapper {

    CutleryInventoryMapper INSTANCE = Mappers.getMapper( CutleryInventoryMapper.class );

    CutleryInventoryEntity map( CutleryInventoryDto in );
}
