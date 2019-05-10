/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.verbose;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ValueMapping {

    ValueMapping INSTANCE = Mappers.getMapper( ValueMapping.class );

    TargetEnum map(SourceEnum source);

    enum TargetEnum { VALUE }

    enum SourceEnum { VALUE }

}
