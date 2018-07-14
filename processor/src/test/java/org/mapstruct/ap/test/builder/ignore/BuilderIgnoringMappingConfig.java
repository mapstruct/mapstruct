/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builder.ignore;

import org.mapstruct.BeanMapping;
import org.mapstruct.MapperConfig;

/**
 * @author Filip Hrisafov
 */
@MapperConfig
public interface BuilderIgnoringMappingConfig {

    @BeanMapping(ignoreByDefault = true)
    BaseEntity mapBase(BaseDto dto);
}
