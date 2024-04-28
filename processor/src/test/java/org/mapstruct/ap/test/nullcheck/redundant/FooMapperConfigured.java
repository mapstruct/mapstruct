/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.redundant;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(
    nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT
)
public interface FooMapperConfigured {

    FooMapperConfigured INSTANCE = Mappers.getMapper( FooMapperConfigured.class );

    void updateFoo(FooSource input, @MappingTarget FooTarget toUpdate, boolean baz);

    void updateFoo(FooSource input, @MappingTarget FooTarget toUpdate);
}
