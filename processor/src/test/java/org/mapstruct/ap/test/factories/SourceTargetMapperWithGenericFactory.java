/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.factories;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Andreas Gudian
 *
 */
@Mapper( uses = { GenericFactory.class } )
public interface SourceTargetMapperWithGenericFactory {
    SourceTargetMapperWithGenericFactory INSTANCE = Mappers.getMapper( SourceTargetMapperWithGenericFactory.class );

    Bar1 fromFoo1(Foo1 foo1);
}
