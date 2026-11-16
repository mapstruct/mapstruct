/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.injectionstrategy.jakarta.setter;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.MapperConfig;
import org.mapstruct.MappingConstants;

/**
 * @author Filip Hrisafov
 */
@MapperConfig(componentModel = MappingConstants.ComponentModel.JAKARTA,
    injectionStrategy = InjectionStrategy.SETTER)
public interface SetterJakartaConfig {
}
