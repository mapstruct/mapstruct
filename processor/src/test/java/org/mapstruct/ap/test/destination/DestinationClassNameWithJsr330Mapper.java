/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.destination;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * @author Christophe Labouisse on 27/05/2015.
 */
@Mapper(implementationName = "<CLASS_NAME>Jsr330Impl", componentModel = MappingConstants.ComponentModel.JSR330)
public interface DestinationClassNameWithJsr330Mapper {
    Target map(Integer source);
}
