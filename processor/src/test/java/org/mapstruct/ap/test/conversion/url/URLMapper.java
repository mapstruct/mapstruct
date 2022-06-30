/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.url;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.net.MalformedURLException;

@Mapper
public interface URLMapper {

    URLMapper INSTANCE = Mappers.getMapper( URLMapper.class );

    Target sourceToTarget(Source source);

    Source targetToSource(Target target);

    Source targetToSourceWithMalformedURLException(Target target) throws MalformedURLException;
}
