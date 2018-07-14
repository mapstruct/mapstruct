/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.injectionstrategy.spring.constructor;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.MapperConfig;

/**
 * @author Filip Hrisafov
 */
@MapperConfig(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ConstructorSpringConfig {
}
