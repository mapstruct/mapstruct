/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.context;

import org.mapstruct.BeforeMapping;
import org.mapstruct.Context;
import org.mapstruct.MappingTarget;
import org.mapstruct.TargetType;

/**
 * @author Andreas Gudian
 */
public class CycleContextLifecycleMethods {

    @BeforeMapping
    public <T> T getInstance(Object source, @TargetType Class<T> type, @Context CycleContext cycleContext) {
        return cycleContext.getMappedInstance( source, type );
    }

    @BeforeMapping
    public void setInstance(Object source, @MappingTarget Object target, @Context CycleContext cycleContext) {
        cycleContext.storeMappedInstance( source, target );
    }
}
