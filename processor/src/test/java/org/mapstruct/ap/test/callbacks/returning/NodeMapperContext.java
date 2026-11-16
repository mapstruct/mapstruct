/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.callbacks.returning;

import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.TargetType;

/**
 * @author Pascal Grün
 */
public class NodeMapperContext {
    private static final ThreadLocal<Integer> LEVEL = new ThreadLocal<>();
    private static final ThreadLocal<Map<Object, Object>> MAPPING = new ThreadLocal<>();

    /** Only for test-inspection */
    private static final List<ContextListener> LISTENERS = new CopyOnWriteArrayList<>();

    private NodeMapperContext() {
        // Only allow static access
    }

    @BeforeMapping
    @SuppressWarnings( "unchecked" )
    public static <T> T getInstance(Object source, @TargetType Class<T> type) {
        fireMethodCalled( LEVEL.get(), "getInstance", source, null );
        Map<Object, Object> mapping = MAPPING.get();
        if ( mapping == null ) {
            return null;
        }
        else {
            return (T) mapping.get( source );
        }
    }

    @BeforeMapping
    public static void setInstance(Object source, @MappingTarget Object target) {
        Integer level = LEVEL.get();
        fireMethodCalled( level, "setInstance", source, target );
        if ( level == null ) {
            LEVEL.set( 1 );
            MAPPING.set( new IdentityHashMap<>() );
        }
        else {
            LEVEL.set( level + 1 );
        }
        MAPPING.get().put( source, target );
    }

    @AfterMapping
    public static void cleanup() {
        Integer level = LEVEL.get();
        fireMethodCalled( level, "cleanup", null, null );
        if ( level == 1 ) {
            MAPPING.set( null );
            LEVEL.set( null );
        }
        else {
            LEVEL.set( level - 1 );
        }
    }

    /**
     * Only for test-inspection
     */
    static void addContextListener(ContextListener contextListener) {
        LISTENERS.add( contextListener );
    }

    /**
     * Only for test-inspection
     */
    static void removeContextListener(ContextListener contextListener) {
        LISTENERS.remove( contextListener );
    }

    /**
     * Only for test-inspection
     */
    private static void fireMethodCalled(Integer level, String method, Object source, Object target) {
        for ( ContextListener contextListener : LISTENERS ) {
            contextListener.methodCalled( level, method, source, target );
        }
    }

    /**
     * Only for test-inspection
     */
    interface ContextListener {
        void methodCalled(Integer level, String method, Object source, Object target);
    }
}
