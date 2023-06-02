/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3296;

import org.mapstruct.Mapper;

@Mapper( config = MapperConfigWithoutPayloadArgument.class )
public interface MapperNotExtendingConfig {
    Entity toEntity(Payload payload);
}
