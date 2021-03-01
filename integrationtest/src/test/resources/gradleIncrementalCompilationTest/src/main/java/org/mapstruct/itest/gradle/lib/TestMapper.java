/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.gradle.lib;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import org.mapstruct.itest.gradle.model.Target;
import org.mapstruct.itest.gradle.model.Source;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TestMapper {
    @Mapping(target = "field", source = "value")
    public Target toTarget(Source source);
}
