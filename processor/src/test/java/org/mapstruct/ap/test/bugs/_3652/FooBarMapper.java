/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */

package org.mapstruct.ap.test.bugs._3652;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(config = FooBarConfig.class)
public interface FooBarMapper {

    FooBarMapper INSTANCE = Mappers.getMapper( FooBarMapper.class );

    Bar toBar(Foo foo);

    Foo toFoo(Bar bar);

}
