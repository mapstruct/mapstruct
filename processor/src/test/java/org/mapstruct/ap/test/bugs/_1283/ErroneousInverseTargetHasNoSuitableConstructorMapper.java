/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1283;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface ErroneousInverseTargetHasNoSuitableConstructorMapper {

    @Mapping(target = "target", source = "source")
    Target fromSource(Source source);

    @InheritInverseConfiguration
    Source fromTarget(Target target);
}
