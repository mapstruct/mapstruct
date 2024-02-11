/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedbeans.unmappable.erroneous;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.ap.test.nestedbeans.unmappable.BaseDeepListMapper;
import org.mapstruct.ap.test.nestedbeans.unmappable.RoofTypeMapper;

@Mapper( uses = RoofTypeMapper.class, unmappedTargetPolicy = ReportingPolicy.ERROR )
public abstract class UnmappableTargetDeepListMapper extends BaseDeepListMapper {
}
