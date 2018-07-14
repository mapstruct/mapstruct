/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.collection.defaultimplementation;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

/**
 * @author Andreas Gudian
 *
 */
@Mapper
public interface NoSetterMapper {

    NoSetterMapper INSTANCE = Mappers.getMapper( NoSetterMapper.class );

    NoSetterTarget toTarget(NoSetterSource source);

    NoSetterTarget toTargetWithExistingTarget(NoSetterSource source, @MappingTarget NoSetterTarget preExistTarget);
}
