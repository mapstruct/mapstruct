/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.references;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Andreas Gudian
 *
 */
@Mapper
public interface FooMapper {
    FooMapper INSTANCE = Mappers.getMapper( FooMapper.class );

    Bar fooToBar(Foo source);
}
