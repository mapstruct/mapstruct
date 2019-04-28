/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;

import org.mapstruct.ap.internal.prism.AfterMappingPrism;
import org.mapstruct.ap.internal.prism.BeforeMappingPrism;
import org.mapstruct.ap.spi.TypeHierarchyErroneousException;

import static javax.lang.model.util.ElementFilter.methodsIn;
import static org.mapstruct.ap.internal.util.Types.asTypeElement;
import static org.mapstruct.ap.internal.util.Types.hasNonObjectSuperclass;
import static org.mapstruct.ap.internal.util.workarounds.SpecificCompilerWorkarounds.replaceTypeElementIfNecessary;

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

    public static boolean isDefaultMethod(ExecutableElement method) {
        try {
            return DEFAULT_METHOD != null && Boolean.TRUE.equals( DEFAULT_METHOD.invoke( method ) );
        }
        catch ( IllegalAccessException | InvocationTargetException e ) {
            return false;
        }
    }

    /**
     * Finds all executable elements within the given type element, including executable elements defined in super
     * classes and implemented interfaces. Methods defined in {@link java.lang.Object} are ignored, as well as
     * implementations of {@link java.lang.Object#equals(Object)}.
     *
     * @param elementUtils element helper
     * @param element the element to inspect
     *
     * @return the executable elements usable in the type
     */
    public static List<ExecutableElement> getAllEnclosedExecutableElements(Elements elementUtils, TypeElement element) {
        List<ExecutableElement> enclosedElements = new ArrayList<>();
        element = replaceTypeElementIfNecessary( elementUtils, element );
        addEnclosedElementsInHierarchy( elementUtils, enclosedElements, element, element );

        return enclosedElements;
    }

    private static void addEnclosedElementsInHierarchy(Elements elementUtils, List<ExecutableElement> alreadyAdded,
                                                       TypeElement element, TypeElement parentType) {
        if ( element != parentType ) { // otherwise the element was already checked for replacement
            element = replaceTypeElementIfNecessary( elementUtils, element );
        }

        if ( element.asType().getKind() == TypeKind.ERROR ) {
            throw new TypeHierarchyErroneousException( element );
        }

        addNotYetOverridden( elementUtils, alreadyAdded, methodsIn( element.getEnclosedElements() ), parentType );

        if ( hasNonObjectSuperclass( element ) ) {
            addEnclosedElementsInHierarchy(
                            elementUtils,
                            alreadyAdded,
                            asTypeElement( element.getSuperclass() ),
                            parentType
            );
        }

        for ( TypeMirror interfaceType : element.getInterfaces() ) {
            addEnclosedElementsInHierarchy(
                            elementUtils,
                            alreadyAdded,
                            asTypeElement( interfaceType ),
                            parentType
            );
        }

    }

    /**
     * @param alreadyCollected methods that have already been collected and to which the not-yet-overridden methods will
     *            be added
     * @param methodsToAdd methods to add to alreadyAdded, if they are not yet overridden by an element in the list
     * @param parentType the type for with elements are collected
     */
    private static void addNotYetOverridden(Elements elementUtils, List<ExecutableElement> alreadyCollected,
                                            List<ExecutableElement> methodsToAdd, TypeElement parentType) {
        List<ExecutableElement> safeToAdd = new ArrayList<>( methodsToAdd.size() );
        for ( ExecutableElement toAdd : methodsToAdd ) {
            if ( isNotObjectEquals( toAdd )
                            && wasNotYetOverridden( elementUtils, alreadyCollected, toAdd, parentType ) ) {
                safeToAdd.add( toAdd );
            }
        }

        alreadyCollected.addAll( 0, safeToAdd );
    }

    /**
     * @param executable the executable to check
     *
     * @return {@code true}, iff the executable does not represent {@link java.lang.Object#equals(Object)} or an
     * overridden version of it
     */
    private static boolean isNotObjectEquals(ExecutableElement executable) {
        if ( executable.getSimpleName().contentEquals( "equals" ) && executable.getParameters().size() == 1
                && asTypeElement( executable.getParameters().get( 0 ).asType() ).getQualifiedName().contentEquals(
            "java.lang.Object"
        ) ) {
            return false;
        }
        return true;
    }

    /**
     * @param elementUtils the elementUtils
     * @param alreadyCollected the list of already collected methods of one type hierarchy (order is from sub-types to
     *            super-types)
     * @param executable the method to check
     * @param parentType the type for which elements are collected
     * @return {@code true}, iff the given executable was not yet overridden by a method in the given list.
     */
    private static boolean wasNotYetOverridden(Elements elementUtils, List<ExecutableElement> alreadyCollected,
                                               ExecutableElement executable, TypeElement parentType) {
        for ( ListIterator<ExecutableElement> it = alreadyCollected.listIterator(); it.hasNext(); ) {
            ExecutableElement executableInSubtype = it.next();
            if ( executableInSubtype == null ) {
                continue;
            }
            if ( elementUtils.overrides( executableInSubtype, executable, parentType ) ) {
                return false;
            }
            else if ( elementUtils.overrides( executable, executableInSubtype, parentType ) ) {
                // remove the method from another interface hierarchy that is overridden by the executable to add
                it.remove();
                return true;
            }
        }

        return true;
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
        return AfterMappingPrism.getInstanceOn( executableElement ) != null;
    }

    /**
     * @param executableElement the element to check
     * @return {@code true}, if the executable element is a method annotated with {@code @BeforeMapping}
     */
    public static boolean isBeforeMappingMethod(ExecutableElement executableElement) {
        return BeforeMappingPrism.getInstanceOn( executableElement ) != null;
    }
}
