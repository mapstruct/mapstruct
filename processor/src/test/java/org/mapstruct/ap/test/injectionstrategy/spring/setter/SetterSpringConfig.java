/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.injectionstrategy.spring.setter;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.MapperConfig;
import org.mapstruct.MappingConstants;

/**
 * @author Lucas Resch
 */
@MapperConfig(componentModel = MappingConstants.ComponentModel.SPRING, injectionStrategy = InjectionStrategy.SETTER)
public interface SetterSpringConfig {
}
