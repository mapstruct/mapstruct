/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.value.exception;

import org.mapstruct.MapperConfig;
import org.mapstruct.ap.test.value.CustomIllegalArgumentException;

/**
 * @author Filip Hrisafov
 */
@MapperConfig(unexpectedValueMappingException = CustomIllegalArgumentException.class)
public interface Config {
}
