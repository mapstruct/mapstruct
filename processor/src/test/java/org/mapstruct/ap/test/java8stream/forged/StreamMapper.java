/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.java8stream.forged;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StreamMapper {

    StreamMapper INSTANCE = Mappers.getMapper( StreamMapper.class );

    Target sourceToTarget(Source source);

    Source targetToSource(Target target);
}
