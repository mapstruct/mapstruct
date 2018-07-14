/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builder.multiple;

import org.mapstruct.Builder;
import org.mapstruct.MapperConfig;

/**
 * @author Filip Hrisafov
 */
@MapperConfig(builder = @Builder(buildMethod = "create"))
public interface BuilderMapperConfig {
}
