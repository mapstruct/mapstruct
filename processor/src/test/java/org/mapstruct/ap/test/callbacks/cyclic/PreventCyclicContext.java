/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.callbacks.cyclic;

import java.util.IdentityHashMap;
import java.util.Map;

import org.mapstruct.BeforeMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.TargetType;

/**
 * @author Ben Zegveld
 */
public class PreventCyclicContext {

    private Map<Object, Object> sourceTargetMapping = new IdentityHashMap<>();

    @BeforeMapping
    void storeMapping(Object source, @MappingTarget Object target) {
        sourceTargetMapping.put( source, target );
    }

    @BeforeMapping
    @SuppressWarnings( "unchecked" )
    <T> T retrieveMapping(Object source, @TargetType Class<T> target) {
        Object object = sourceTargetMapping.get( source );
        if ( target.isInstance( object ) ) {
            return (T) object;
        }
        return null;
    }
}
