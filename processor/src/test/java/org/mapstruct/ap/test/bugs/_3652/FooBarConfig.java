/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */

package org.mapstruct.ap.test.bugs._3652;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.MapperConfig;
import org.mapstruct.Mapping;
import org.mapstruct.MappingInheritanceStrategy;

@MapperConfig(mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG)
public interface FooBarConfig {

    @Mapping(target = "doesNotExistInFoo", ignore = true)
    @Mapping(target = "secret", ignore = true)
    Bar toBar(Foo foo);

    @InheritInverseConfiguration(name = "toBar")
    Foo toFoo(Bar bar);

}
