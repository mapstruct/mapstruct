/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedbeans.unmappable.erroneous;

import static org.mapstruct.ReportingPolicy.ERROR;
import static org.mapstruct.ReportingPolicy.IGNORE;

import org.mapstruct.Mapper;
import org.mapstruct.ap.test.nestedbeans.unmappable.BaseDeepListMapper;
import org.mapstruct.ap.test.nestedbeans.unmappable.RoofTypeMapper;

@Mapper( uses = RoofTypeMapper.class, unmappedTargetPolicy = IGNORE, unmappedSourcePolicy = ERROR )
public abstract class UnmappableSourceDeepListMapper extends BaseDeepListMapper {
}
