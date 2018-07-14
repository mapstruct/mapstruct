/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.inheritance.complex;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = { SourceBaseMappingHelper.class })
public interface SourceCompositeTargetCompositeMapper {

    SourceCompositeTargetCompositeMapper INSTANCE = Mappers.getMapper( SourceCompositeTargetCompositeMapper.class );

    TargetComposite sourceToTarget(SourceComposite source);
}
