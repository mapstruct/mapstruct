/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedbeans.unmappable.warn;

import org.mapstruct.Mapper;
import org.mapstruct.ap.test.nestedbeans.unmappable.BaseValuePropertyMapper;
import org.mapstruct.ap.test.nestedbeans.unmappable.RoofTypeMapper;

@Mapper(uses = RoofTypeMapper.class)
public abstract class UnmappableWarnValuePropertyMapper extends BaseValuePropertyMapper {
}
