/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.generics;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper( uses = GenericTypeMapper.class )
public interface ErroneousSourceTargetMapper4 {

    ErroneousSourceTargetMapper4 INSTANCE = Mappers.getMapper( ErroneousSourceTargetMapper4.class );

    ErroneousTarget4 sourceToTarget(ErroneousSource4 source);
}
