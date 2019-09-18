/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1790;

import org.mapstruct.MapperConfig;
import org.mapstruct.NullValueCheckStrategy;

/**
 * @author Filip Hrisafov
 */
@MapperConfig(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface Issue1790Config {
}
