/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.inheritedmappingmethod;

import org.mapstruct.ap.test.inheritedmappingmethod._target.CarDto;
import org.mapstruct.ap.test.inheritedmappingmethod.source.Car;

public interface BoundMappable<DTO extends CarDto, ENTITY extends Car> {
    ENTITY from(DTO dto);

    DTO to(ENTITY entity);

}
