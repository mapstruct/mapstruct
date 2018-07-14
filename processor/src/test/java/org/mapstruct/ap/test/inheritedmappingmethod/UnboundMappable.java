/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.inheritedmappingmethod;

public interface UnboundMappable<DTO, ENTITY> {
    ENTITY from(DTO dto);

    DTO to(ENTITY entity);

}
