/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.generics;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper( uses = GenericTypeMapper.class )
public interface ErroneousSourceTargetMapper1 {

    ErroneousSourceTargetMapper1 INSTANCE = Mappers.getMapper( ErroneousSourceTargetMapper1.class );

    ErroneousTarget1 sourceToTarget(ErroneousSource1 source);
}
