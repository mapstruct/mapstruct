/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.injectionstrategy.dagger2.constructor;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.MapperConfig;
import org.mapstruct.MappingConstants;

@MapperConfig(componentModel = MappingConstants.ComponentModel.DAGGER2,
    injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ConstructorDagger2Config {
}
