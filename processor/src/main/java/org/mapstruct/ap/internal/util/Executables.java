/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;

import org.mapstruct.ap.internal.gem.AfterMappingGem;
import org.mapstruct.ap.internal.gem.BeforeMappingGem;
import org.mapstruct.ap.internal.util.accessor.Accessor;

/**
 * Provides functionality around {@link ExecutableElement}s.
 *
 * @author Gunnar Morling
 */
public class Executables {

    private static final Method DEFAULT_METHOD;

    static {
        Method method;
        try {
            method = ExecutableElement.class.getMethod( "isDefault" );
        }
        catch ( NoSuchMethodException e ) {
            method = null;
        }
        DEFAULT_METHOD = method;
    }

    private Executables() {
    }

    static boolean isPublicNotStatic(ExecutableElement method) {
        return isPublic( method ) && isNotStatic( method );
    }

    static boolean isPublic(ExecutableElement method) {
        return method.getModifiers().contains( Modifier.PUBLIC );
    }

    private static boolean isNotStatic(ExecutableElement method) {
        return !method.getModifiers().contains( Modifier.STATIC );
    }

    public static boolean isFinal(Accessor accessor) {
        return accessor != null && accessor.getModifiers().contains( Modifier.FINAL );
    }

    public static boolean isDefaultMethod(ExecutableElement method) {
        try {
            return DEFAULT_METHOD != null && Boolean.TRUE.equals( DEFAULT_METHOD.invoke( method ) );
        }
        catch ( IllegalAccessException | InvocationTargetException e ) {
            return false;
        }
    }

    /**
     * @param executableElement the element to check
     * @return {@code true}, if the executable element is a method annotated with {@code @BeforeMapping} or
     *         {@code @AfterMapping}
     */
    public static boolean isLifecycleCallbackMethod(ExecutableElement executableElement) {
        return isBeforeMappingMethod( executableElement ) || isAfterMappingMethod( executableElement );
    }

    /**
     * @param executableElement the element to check
     * @return {@code true}, if the executable element is a method annotated with {@code @AfterMapping}
     */
    public static boolean isAfterMappingMethod(ExecutableElement executableElement) {
        return AfterMappingGem.instanceOn( executableElement ) != null;
    }

    /**
     * @param executableElement the element to check
     * @return {@code true}, if the executable element is a method annotated with {@code @BeforeMapping}
     */
    public static boolean isBeforeMappingMethod(ExecutableElement executableElement) {
        return BeforeMappingGem.instanceOn( executableElement ) != null;
    }
}
