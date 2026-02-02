/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.erroneous.typemismatch;

import org.mapstruct.Mapper;
import org.mapstruct.TargetType;

@Mapper
public interface ErroneousMapper {

    Target sourceToTarget(Source source);

    Source targetToSource(Target target);

    long sourceToLong(Source source);

    Target sourceToTargetWithMappingTargetType(Source source, @TargetType Class<?> clazz);
}
