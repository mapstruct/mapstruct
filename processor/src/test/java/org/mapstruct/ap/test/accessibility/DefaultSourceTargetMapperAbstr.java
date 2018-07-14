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
abstract class DefaultSourceTargetMapperAbstr {
    abstract Target defaultSourceToTarget(Source source);

    protected abstract Target protectedSourceToTarget(Source source);

    public abstract Target publicSourceToTarget(Source source);
}
