/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.destination;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Andreas Gudian
 */
@Mapper(implementationName = "My<CLASS_NAME>CustomImpl")
public abstract class AbstractDestinationClassNameMapper {
    public static final AbstractDestinationClassNameMapper INSTANCE =
        Mappers.getMapper( AbstractDestinationClassNameMapper.class );

    public abstract Target map(Integer source);
}
