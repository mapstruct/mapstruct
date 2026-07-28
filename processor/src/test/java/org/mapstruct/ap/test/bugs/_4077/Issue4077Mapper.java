/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._4077;

import org.jspecify.annotations.NullMarked;
import org.mapstruct.Mapper;

@Mapper
@NullMarked
public interface Issue4077Mapper {
    Target map(Source source);

    Target.Nested mapNested(Source.Nested source);
}
