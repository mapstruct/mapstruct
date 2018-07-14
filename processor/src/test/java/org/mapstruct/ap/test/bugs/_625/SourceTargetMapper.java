/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._625;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * @author Andreas Gudian
 *
 */
@Mapper(uses = ObjectFactory.class)
public interface SourceTargetMapper {
    void intoTarget(Source source, @MappingTarget Target target);
}
