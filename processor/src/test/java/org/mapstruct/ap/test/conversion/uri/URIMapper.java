/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.uri;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface URIMapper {

    URIMapper INSTANCE = Mappers.getMapper( URIMapper.class );

    URITarget sourceToTarget(URISource source);

    URISource targetToSource(URITarget target);
}
