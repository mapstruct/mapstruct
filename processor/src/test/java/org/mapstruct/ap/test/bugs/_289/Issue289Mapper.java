/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._289;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Issue289Mapper {

    Issue289Mapper INSTANCE = Mappers.getMapper( Issue289Mapper.class );

    TargetWithoutSetter sourceToTargetWithoutSetter(Source source);

    void sourceToTargetWithoutSetter(Source source, @MappingTarget TargetWithoutSetter target);

    TargetWithSetter sourceToTargetWithSetter(Source source);

    TargetElement sourceElementToTargetElement(SourceElement source);

}
