/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.redundant;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FooMapper {

    FooMapper INSTANCE = Mappers.getMapper( FooMapper.class );

    void updateFoo(FooSource input, @MappingTarget FooTarget toUpdate, boolean baz);

    void updateFoo(FooSource input, @MappingTarget FooTarget toUpdate, boolean baz, int bay);

    void updateFoo(FooSource input, @MappingTarget FooTarget toUpdate);

    FooTarget getUpdatedFooTarget(FooSource input, @MappingTarget FooTarget toUpdate);

    FooTarget getUpdatedFooTarget(FooSource input, @MappingTarget FooTarget toUpdate, boolean baz);

    FooTarget map(FooSource source);

    FooTarget map(FooSourceNested source);

    @Mapping(source = "input.nested.bar", target = "bar")
    FooTarget map(FooSourceNested input, @MappingTarget FooTarget toUpdate, boolean baz);
}
