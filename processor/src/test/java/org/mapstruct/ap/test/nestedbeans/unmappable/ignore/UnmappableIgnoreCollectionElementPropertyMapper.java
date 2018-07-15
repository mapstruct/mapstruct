/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedbeans.unmappable.ignore;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.ap.test.nestedbeans.unmappable.BaseCollectionElementPropertyMapper;
import org.mapstruct.ap.test.nestedbeans.unmappable.RoofTypeMapper;

@Mapper(uses = RoofTypeMapper.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class UnmappableIgnoreCollectionElementPropertyMapper extends BaseCollectionElementPropertyMapper {
}
