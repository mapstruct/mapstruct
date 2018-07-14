/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.generics;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper( uses = GenericTypeMapper.class )
public interface ErroneousSourceTargetMapper3 {

    ErroneousSourceTargetMapper3 INSTANCE = Mappers.getMapper( ErroneousSourceTargetMapper3.class );

    ErroneousTarget3 sourceToTarget(ErroneousSource3 source);
}
