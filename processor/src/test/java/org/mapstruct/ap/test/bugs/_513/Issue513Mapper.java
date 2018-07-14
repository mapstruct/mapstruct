/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._513;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Issue513Mapper {

    Issue513Mapper INSTANCE = Mappers.getMapper( Issue513Mapper.class );

    Target map(Source source) throws MappingException, MappingValueException, MappingKeyException;

    TargetElement mapElement(SourceElement source) throws MappingException;

    TargetKey mapKey(SourceKey source) throws MappingException, MappingKeyException;

    TargetValue mapValue(SourceValue source) throws MappingException, MappingValueException;
}
