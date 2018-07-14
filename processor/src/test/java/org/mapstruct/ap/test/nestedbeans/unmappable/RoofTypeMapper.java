/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedbeans.unmappable;

import org.mapstruct.Mapper;
import org.mapstruct.ValueMapping;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface RoofTypeMapper {

    @ValueMapping( source = "NORMAL", target = "STANDARD")
    ExternalRoofType map(RoofType type);
}
