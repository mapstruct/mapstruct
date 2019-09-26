/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1742;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface Issue1742Mapper {

    void update(@MappingTarget Target target, Source source);
}
