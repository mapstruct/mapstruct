/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.context;

import java.util.IdentityHashMap;
import java.util.Map;

import org.mapstruct.Context;

/**
 * A type to be used as {@link Context} parameter to track cycles in graphs
 *
 * @author Andreas Gudian
 */
public class CycleContext {
    private Map<Object, Object> knownInstances = new IdentityHashMap<Object, Object>();

    @SuppressWarnings("unchecked")
    public <T> T getMappedInstance(Object source, Class<T> targetType) {
        return (T) knownInstances.get( source );
    }

    public void storeMappedInstance(Object source, Object target) {
        knownInstances.put( source, target );
    }
}
