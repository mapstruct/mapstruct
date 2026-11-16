/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.accessibility;

import org.mapstruct.Mapper;

/**
 * @author Andreas Gudian
 */
@Mapper
interface DefaultSourceTargetMapperIfc {
    Target implicitlyPublicSourceToTarget(Source source);
}
