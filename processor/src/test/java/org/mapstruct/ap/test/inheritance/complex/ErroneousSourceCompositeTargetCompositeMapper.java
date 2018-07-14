/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.inheritance.complex;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = { AdditionalMappingHelper.class })
public interface ErroneousSourceCompositeTargetCompositeMapper {

    ErroneousSourceCompositeTargetCompositeMapper INSTANCE =
        Mappers.getMapper( ErroneousSourceCompositeTargetCompositeMapper.class );

    TargetComposite sourceToTarget(SourceComposite source);

    Iterable<Number> intListToNumberIterable(List<Integer> source);
}
