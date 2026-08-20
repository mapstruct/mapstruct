/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import org.jspecify.annotations.NullMarked;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = JSpecifySimpleReusedMethodUses.class)
@NullMarked
public interface JSpecifyVerboseReusedMethodMapper {

    @Mapping(target = "value", source = "value", qualifiedByName = "map")
    JSpecifySimpleReusedMethodTarget map(JSpecifySimpleReusedMethodSource source);
}
