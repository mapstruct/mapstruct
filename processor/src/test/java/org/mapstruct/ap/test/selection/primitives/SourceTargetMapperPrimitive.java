/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.primitives;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author Sjaak Derksen
 */
@Mapper(uses = { PrimitiveMapper.class } )
public interface SourceTargetMapperPrimitive {

    SourceTargetMapperPrimitive INSTANCE = Mappers.getMapper( SourceTargetMapperPrimitive.class );

    Target toTarget(Source s);
}
