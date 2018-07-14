/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.generics;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper( uses = GenericTypeMapper.class )
public interface ErroneousSourceTargetMapper6 {

    ErroneousSourceTargetMapper6 INSTANCE = Mappers.getMapper( ErroneousSourceTargetMapper6.class );

    ErroneousTarget6 sourceToTarget(ErroneousSource6 source);
}
