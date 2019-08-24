/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.injectionstrategy.spring.field;

import org.mapstruct.ComponentModel;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.MapperConfig;

/**
 * @author Filip Hrisafov
 */
@MapperConfig(componentModel = ComponentModel.SPRING, injectionStrategy = InjectionStrategy.FIELD)
public interface FieldSpringConfig {
}
