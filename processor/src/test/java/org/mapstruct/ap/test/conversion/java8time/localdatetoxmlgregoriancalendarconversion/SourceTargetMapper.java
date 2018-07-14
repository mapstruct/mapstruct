/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.java8time.localdatetoxmlgregoriancalendarconversion;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Andreas Gudian
 */
@Mapper
public interface SourceTargetMapper {

    SourceTargetMapper INSTANCE = Mappers.getMapper( SourceTargetMapper.class );

    Target toTarget(Source source);

    Source toSource(Target target);
}
