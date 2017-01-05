/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
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
    private static final ThreadLocal<Integer> LEVEL = new ThreadLocal<Integer>();
    private static final ThreadLocal<Map<Object, Object>> MAPPING = new ThreadLocal<Map<Object, Object>>();

    /** Only for test-inspection */
    private static final List<ContextListener> LISTENERS = new CopyOnWriteArrayList<ContextListener>();

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
            MAPPING.set( new IdentityHashMap<Object, Object>() );
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
