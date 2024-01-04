/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.mappingcontrol;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.control.DeepClone;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CloningBeanMappingMapper {

    CloningBeanMappingMapper INSTANCE = Mappers.getMapper( CloningBeanMappingMapper.class );

    @BeanMapping(mappingControl = DeepClone.class)
    FridgeDTO clone(FridgeDTO in);

}
