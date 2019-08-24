/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.destination;

import org.mapstruct.ComponentModel;
import org.mapstruct.Mapper;

/**
 * @author Christophe Labouisse on 27/05/2015.
 */
@Mapper(implementationName = "<CLASS_NAME>Jsr330Impl", componentModel = ComponentModel.JSR330)
public interface DestinationClassNameWithJsr330Mapper {
    String intToString(Integer source);
}
