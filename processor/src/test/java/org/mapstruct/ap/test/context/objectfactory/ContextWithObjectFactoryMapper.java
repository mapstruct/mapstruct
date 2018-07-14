/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.context.objectfactory;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author Sjaak Derksen
 */
@Mapper
public interface ContextWithObjectFactoryMapper {

    ContextWithObjectFactoryMapper INSTANCE = Mappers.getMapper( ContextWithObjectFactoryMapper.class );

    Valve map(ValveDto dto, @Context ContextObjectFactory factory);

}
