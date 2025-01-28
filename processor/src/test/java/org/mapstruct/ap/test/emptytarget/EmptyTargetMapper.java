/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.emptytarget;

import org.mapstruct.Mapper;

@Mapper
public interface EmptyTargetMapper {

    TargetWithNoSetters mapToTargetWithSetters(Source source);

    EmptyTarget mapToEmptyTarget(Source source);

    Target mapToTarget(Source source);
}
