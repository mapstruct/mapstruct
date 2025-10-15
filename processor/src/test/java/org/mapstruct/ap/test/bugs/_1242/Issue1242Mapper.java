/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1242;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

/**
 * Test mapper for properly resolving the best fitting factory method
 *
 * @author Andreas Gudian
 */
@Mapper(uses = TargetFactories.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class Issue1242Mapper {
    abstract TargetA toTargetA(SourceA source);

    abstract TargetB toTargetB(SourceB source);

    abstract void mergeA(SourceA source, @MappingTarget TargetA target);

    abstract void mergeB(SourceB source, @MappingTarget TargetB target);
}
