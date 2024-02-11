/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedbeans.unmappable.warn;

import static org.mapstruct.ReportingPolicy.IGNORE;
import static org.mapstruct.ReportingPolicy.WARN;

import org.mapstruct.Mapper;
import org.mapstruct.ap.test.nestedbeans.unmappable.BaseDeepNestingMapper;
import org.mapstruct.ap.test.nestedbeans.unmappable.RoofTypeMapper;

@Mapper( uses = RoofTypeMapper.class, unmappedTargetPolicy = WARN, unmappedSourcePolicy = IGNORE )
public abstract class UnmappableTargetWarnDeepNestingMapper extends BaseDeepNestingMapper {
}
