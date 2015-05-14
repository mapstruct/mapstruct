/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.util;

import java.beans.Introspector;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.SimpleElementVisitor6;
import javax.lang.model.util.SimpleTypeVisitor6;

import static javax.lang.model.util.ElementFilter.methodsIn;
import static org.mapstruct.ap.util.SpecificCompilerWorkarounds.replaceTypeElementIfNecessary;

/**
 * Provides functionality around {@link ExecutableElement}s.
 *
 * @author Gunnar Morling
 */
public class Executables {

    private Executables() {
    }

    public static boolean isGetterMethod(ExecutableElement method) {
        return isPublic( method ) && ( isNonBooleanGetterMethod( method ) || isBooleanGetterMethod( method ) );
    }

    private static boolean isNonBooleanGetterMethod(ExecutableElement method) {
        String name = method.getSimpleName().toString();

        return method.getParameters().isEmpty() &&
            name.startsWith( "get" ) &&
            name.length() > 3 &&
            method.getReturnType().getKind() != TypeKind.VOID;
    }

    private static boolean isBooleanGetterMethod(ExecutableElement method) {
        String name = method.getSimpleName().toString();
        boolean returnTypeIsBoolean = method.getReturnType().getKind() == TypeKind.BOOLEAN ||
            "java.lang.Boolean".equals( getQualifiedName( method.getReturnType() ) );

        return method.getParameters().isEmpty() &&
            name.startsWith( "is" ) &&
            name.length() > 2 &&
            returnTypeIsBoolean;
    }

    public static boolean isSetterMethod(ExecutableElement method) {
        String name = method.getSimpleName().toString();

        return isPublic( method ) &&
            name.startsWith( "set" ) &&
            name.length() > 3 &&
            method.getParameters().size() == 1;
    }

    public static boolean isAdderMethod(ExecutableElement method) {
        String name = method.getSimpleName().toString();

        return isPublic( method ) &&
            name.startsWith( "add" ) && name.length() > 3 &&
            method.getParameters().size() == 1;

    }

    private static boolean isPublic(ExecutableElement method) {
        return method.getModifiers().contains( Modifier.PUBLIC );
    }

    public static String getPropertyName(ExecutableElement getterOrSetterMethod) {
        if ( isNonBooleanGetterMethod( getterOrSetterMethod ) ) {
            return Introspector.decapitalize(
                getterOrSetterMethod.getSimpleName().toString().substring( 3 )
            );
        }
        else if ( isBooleanGetterMethod( getterOrSetterMethod ) ) {
            return Introspector.decapitalize(
                getterOrSetterMethod.getSimpleName().toString().substring( 2 )
            );
        }
        else if ( isSetterMethod( getterOrSetterMethod ) ) {
            return Introspector.decapitalize(
                getterOrSetterMethod.getSimpleName().toString().substring( 3 )
            );
        }

        throw new IllegalArgumentException( "Executable " + getterOrSetterMethod + " is not getter or setter method." );
    }

    /**
     * @param adderMethod the adder method
     * @return the 'element name' to which an adder method applies. If. e.g. an adder method is named
     *         {@code addChild(Child v)}, the element name would be 'Child'.
     */
    public static String getElementNameForAdder(ExecutableElement adderMethod) {
        if ( isAdderMethod( adderMethod ) ) {
            return Introspector.decapitalize(
                adderMethod.getSimpleName().toString().substring( 3 )
            );
        }

        throw new IllegalArgumentException( "Executable " + adderMethod + " is not an adder method." );
    }

    public static Set<String> getPropertyNames(List<ExecutableElement> propertyAccessors) {
        Set<String> propertyNames = new HashSet<String>();

        for ( ExecutableElement executableElement : propertyAccessors ) {
            propertyNames.add( getPropertyName( executableElement ) );
        }

        return propertyNames;
    }

    private static String getQualifiedName(TypeMirror type) {
        DeclaredType declaredType = type.accept(
            new SimpleTypeVisitor6<DeclaredType, Void>() {
                @Override
                public DeclaredType visitDeclared(DeclaredType t, Void p) {
                    return t;
                }
            },
            null
        );

        if ( declaredType == null ) {
            return null;
        }

        TypeElement typeElement = declaredType.asElement().accept(
            new SimpleElementVisitor6<TypeElement, Void>() {
                @Override
                public TypeElement visitType(TypeElement e, Void p) {
                    return e;
                }
            },
            null
        );

        return typeElement != null ? typeElement.getQualifiedName().toString() : null;
    }

    /**
     * @param mirror the type mirror
     *
     * @return the corresponding type element
     */
    private static TypeElement asTypeElement(TypeMirror mirror) {
        return (TypeElement) ( (DeclaredType) mirror ).asElement();
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
        List<ExecutableElement> enclosedElements = new ArrayList<ExecutableElement>();
        element = replaceTypeElementIfNecessary( elementUtils, element );
        addEnclosedElementsInHierarchy( elementUtils, enclosedElements, element, element );

        return enclosedElements;
    }

    private static void addEnclosedElementsInHierarchy(Elements elementUtils, List<ExecutableElement> alreadyAdded,
                                                       TypeElement element, TypeElement parentType) {
        if ( element != parentType ) { // otherwise the element was already checked for replacement
            element = replaceTypeElementIfNecessary( elementUtils, element );
        }

        addNotYetOverridden( elementUtils, alreadyAdded, methodsIn( element.getEnclosedElements() ), parentType );

        if ( hasNonObjectSuperclass( element ) ) {
            addEnclosedElementsInHierarchy(
                elementUtils,
                alreadyAdded,
                asTypeElement( element.getSuperclass() ),
                parentType );
        }

        for ( TypeMirror interfaceType : element.getInterfaces() ) {
            addEnclosedElementsInHierarchy(
                elementUtils,
                alreadyAdded,
                asTypeElement( interfaceType ),
                parentType );
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
        List<ExecutableElement> safeToAdd = new ArrayList<ExecutableElement>( methodsToAdd.size() );
        for ( ExecutableElement toAdd : methodsToAdd ) {
            if ( isNotObjectEquals( toAdd )
               && wasNotYetOverridden( elementUtils, alreadyCollected, toAdd, parentType ) ) {
                safeToAdd.add( toAdd );
            }
        }

        alreadyCollected.addAll( safeToAdd );
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
     * @param alreadyAdded the list of already collected methods of one type hierarchy (order is from sub-types to
     *            super-types)
     * @param executable the method to check
     * @param parentType the type for which elements are collected
     * @return {@code true}, iff the given executable was not yet overridden by a method in the given list.
     */
    private static boolean wasNotYetOverridden(Elements elementUtils, List<ExecutableElement> alreadyAdded,
                                               ExecutableElement executable, TypeElement parentType) {
        for ( ExecutableElement executableInSubtype : alreadyAdded ) {
            if ( elementUtils.overrides( executableInSubtype, executable, parentType ) ) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param element the type element to check
     *
     * @return {@code true}, iff the type has a super-class that is not java.lang.Object
     */
    private static boolean hasNonObjectSuperclass(TypeElement element) {
        return element.getSuperclass().getKind() == TypeKind.DECLARED
            && asTypeElement( element.getSuperclass() ).getSuperclass().getKind() == TypeKind.DECLARED;
    }
}
