/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.injectionstrategy.jsr330.field;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.MapperConfig;

/**
 * @author Filip Hrisafov
 */
@MapperConfig(componentModel = "jsr330", injectionStrategy = InjectionStrategy.FIELD)
public interface FieldJsr330Config {
}
