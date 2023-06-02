/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3296;

import org.mapstruct.AfterMapping;
import org.mapstruct.MapperConfig;
import org.mapstruct.MappingTarget;

@MapperConfig
public interface MapperConfigWithPayloadArgument {

    @AfterMapping
    default void afterMapping(@MappingTarget Entity entity, Payload unused) {
        entity.setName( "AfterMapping called" );
    }
}
