/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.unmappedtarget;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ErroneousStrictSourceTargetMapper {

    ErroneousStrictSourceTargetMapper INSTANCE = Mappers.getMapper( ErroneousStrictSourceTargetMapper.class );

    Target sourceToTarget(Source source);

    Source targetToSource(Target target);
}
