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
package org.mapstruct.ap.internal.util;

import static javax.lang.model.util.ElementFilter.fieldsIn;
import static javax.lang.model.util.ElementFilter.methodsIn;
import static org.mapstruct.ap.internal.util.workarounds.SpecificCompilerWorkarounds.replaceTypeElementIfNecessary;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.SimpleElementVisitor6;
import javax.lang.model.util.SimpleTypeVisitor6;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.mapstruct.ap.shared.TypeHierarchyErroneousException;
import org.mapstruct.ap.internal.prism.AfterMappingPrism;
import org.mapstruct.ap.internal.prism.BeforeMappingPrism;
import org.mapstruct.ap.internal.util.accessor.Accessor;
import org.mapstruct.ap.internal.util.accessor.ExecutableElementAccessor;
import org.mapstruct.ap.internal.util.accessor.VariableElementAccessor;
import org.mapstruct.ap.spi.AccessorNamingStrategy;
import org.mapstruct.ap.spi.BuilderNamingStrategy;
import org.mapstruct.ap.spi.DefaultAccessorNamingStrategy;
import org.mapstruct.ap.spi.DefaultBuilderNamingStrategy;
import org.mapstruct.ap.spi.MethodType;

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

    private static final AccessorNamingStrategy ACCESSOR_NAMING_STRATEGY = Services.get(
        AccessorNamingStrategy.class, new DefaultAccessorNamingStrategy()
    );

    private static final BuilderNamingStrategy BUILDER_NAMING_STRATEGY = Services.get(
        BuilderNamingStrategy.class, new DefaultBuilderNamingStrategy()
    );

    private Executables() {
    }

    public static boolean isGetterMethod(Accessor method) {
        ExecutableElement executable = method.getExecutable();
        return executable != null && isPublic( method )
            && executable.getParameters().isEmpty()
            && getMethodType( method ) == MethodType.GETTER;
    }

    /**
     * An {@link Accessor} is a field accessor, if it doesn't have an executable element, is public static and it is not
     * static.
     *
     * @param accessor the accessor to ber checked
     *
     * @return {@code true} if the {@code accessor} is for a {@code public} non {@code static} field.
     */
    public static boolean isFieldAccessor(Accessor accessor) {
        ExecutableElement executable = accessor.getExecutable();
        return executable == null && isPublic( accessor ) && isNotStatic( accessor );
    }

    public static boolean isPresenceCheckMethod(Accessor method) {
        if ( !( method instanceof ExecutableElementAccessor ) ) {
            return false;
        }
        ExecutableElement executable = method.getExecutable();
        return executable != null
            && isPublic( method )
            && executable.getParameters().isEmpty()
            && ( executable.getReturnType().getKind() == TypeKind.BOOLEAN ||
            "java.lang.Boolean".equals( getQualifiedName( executable.getReturnType() ) ) )
            && getMethodType( method ) == MethodType.PRESENCE_CHECKER;
    }

    public static boolean isSetterMethod(Accessor method) {
        ExecutableElement executable = method.getExecutable();
        return executable != null
            && isPublic( method )
            && executable.getParameters().size() == 1
            && getMethodType( method ) == MethodType.SETTER;
    }

    public static boolean isAdderMethod(Accessor method) {
        ExecutableElement executable = method.getExecutable();
        return executable != null
            && isPublic( method )
            && executable.getParameters().size() == 1
            && getMethodType( method ) == MethodType.ADDER;
    }

    private static boolean isPublic(Accessor method) {
        return method.getModifiers().contains( Modifier.PUBLIC );
    }

    private static boolean isNotStatic(Accessor accessor) {
        return !accessor.getModifiers().contains( Modifier.STATIC );
    }

    public static boolean isFinal(Accessor accessor) {
        return accessor != null && accessor.getModifiers().contains( Modifier.FINAL );
    }

    public static String getPropertyName(Accessor accessor) {
        final ExecutableElement method = accessor.getExecutable();
        if ( method != null ) {
            if ( accessor.isBuilder() ) {
                return BUILDER_NAMING_STRATEGY.getPropertyName( method );
            }
            else {
                return ACCESSOR_NAMING_STRATEGY.getPropertyName( method );
            }
        }
        else {
            return accessor.getSimpleName().toString();
        }
    }

    public static boolean isDefaultMethod(ExecutableElement method) {
        try {
            return DEFAULT_METHOD != null && Boolean.TRUE.equals( DEFAULT_METHOD.invoke( method ) );
        }
        catch ( IllegalAccessException e ) {
            return false;
        }
        catch ( InvocationTargetException e ) {
            return false;
        }
    }

    /**
     * @param adderMethod the adder method
     * @return the 'element name' to which an adder method applies. If. e.g. an adder method is named
     *         {@code addChild(Child v)}, the element name would be 'Child'.
     */
    public static String getElementNameForAdder(Accessor adderMethod) {
        final ExecutableElement executable = adderMethod.getExecutable();
        if ( executable != null ) {
            if ( adderMethod.isBuilder() ) {
                return BUILDER_NAMING_STRATEGY.getElementName( executable );
            }
            else {
                return ACCESSOR_NAMING_STRATEGY.getElementName( executable );
            }
        }
        return null;
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
    public static List<ExecutableElement> getAllEnclosedExecutableElements(Elements elementUtils, TypeElement element,
        boolean isBuilder) {
        List<ExecutableElement> executables = new ArrayList<ExecutableElement>();
        for ( Accessor accessor : getAllEnclosedAccessors( elementUtils, element, isBuilder ) ) {
            if ( accessor.getExecutable() != null ) {
                executables.add( accessor.getExecutable() );
            }
        }

        return executables;
    }

    /**
     * Finds all executable elements/variable elements within the given type element, including executable/variable
     * elements defined in super classes and implemented interfaces and including the fields in the . Methods defined
     * in {@link java.lang.Object} are ignored, as well as implementations of {@link java.lang.Object#equals(Object)}.
     *
     * @param elementUtils element helper
     * @param element the element to inspect
     * @param builder Whether the retrieved accessors are for a builder type
     *
     * @return the executable elements usable in the type
     */
    public static List<Accessor> getAllEnclosedAccessors(Elements elementUtils, TypeElement element, boolean builder) {
        List<Accessor> enclosedElements = new ArrayList<Accessor>();
        element = replaceTypeElementIfNecessary( elementUtils, element );
        addEnclosedElementsInHierarchy( elementUtils, enclosedElements, element, element, builder );

        return enclosedElements;
    }

    private static void addEnclosedElementsInHierarchy(Elements elementUtils, List<Accessor> alreadyAdded,
        TypeElement element, TypeElement parentType, boolean builder) {
        if ( element != parentType ) { // otherwise the element was already checked for replacement
            element = replaceTypeElementIfNecessary( elementUtils, element );
        }

        if ( element.asType().getKind() == TypeKind.ERROR ) {
            throw new TypeHierarchyErroneousException( element );
        }

        final List<ExecutableElement> methodsToAdd = methodsIn( element.getEnclosedElements() );
        addNotYetOverridden( elementUtils, alreadyAdded, methodsToAdd, parentType, builder );
        addFields( alreadyAdded, fieldsIn( element.getEnclosedElements() ), parentType, builder );

        if ( hasNonObjectSuperclass( element ) ) {
            addEnclosedElementsInHierarchy(
                elementUtils,
                alreadyAdded,
                asTypeElement( element.getSuperclass() ),
                parentType,
                builder
            );
        }

        for ( TypeMirror interfaceType : element.getInterfaces() ) {
            addEnclosedElementsInHierarchy(
                elementUtils,
                alreadyAdded,
                asTypeElement( interfaceType ),
                parentType,
                builder
            );
        }
    }

    /**
     * @param alreadyCollected methods that have already been collected and to which the not-yet-overridden methods will
     *            be added
     * @param methodsToAdd methods to add to alreadyAdded, if they are not yet overridden by an element in the list
     * @param parentType the type for with elements are collected
     */
    private static void addNotYetOverridden(Elements elementUtils, List<Accessor> alreadyCollected,
        List<ExecutableElement> methodsToAdd, TypeElement parentType, boolean isBuilder) {

        List<Accessor> safeToAdd = new ArrayList<Accessor>( methodsToAdd.size() );
        for ( ExecutableElement toAdd : methodsToAdd ) {
            if ( isNotObjectEquals( toAdd )
                && wasNotYetOverridden( elementUtils, alreadyCollected, toAdd, parentType ) ) {
                safeToAdd.add( new ExecutableElementAccessor( toAdd, parentType, isBuilder ) );
            }
        }

        alreadyCollected.addAll( 0, safeToAdd );
    }

    private static void addFields(List<Accessor> alreadyCollected, List<VariableElement> variablesToAdd,
        TypeElement parentType, boolean isBuilder) {
        List<Accessor> safeToAdd = new ArrayList<Accessor>( variablesToAdd.size() );
        for ( VariableElement toAdd : variablesToAdd ) {
            safeToAdd.add( new VariableElementAccessor( toAdd, parentType, isBuilder ) );
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
    private static boolean wasNotYetOverridden(Elements elementUtils, List<Accessor> alreadyCollected,
        ExecutableElement executable, TypeElement parentType) {

        for ( ListIterator<Accessor> it = alreadyCollected.listIterator(); it.hasNext(); ) {
            ExecutableElement executableInSubtype = it.next().getExecutable();
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
     * @param element the type element to check
     *
     * @return {@code true}, iff the type has a super-class that is not java.lang.Object
     */
    private static boolean hasNonObjectSuperclass(TypeElement element) {
        if ( element.getSuperclass().getKind() == TypeKind.ERROR ) {
            throw new TypeHierarchyErroneousException( element );
        }

        return element.getSuperclass().getKind() == TypeKind.DECLARED
            && !asTypeElement( element.getSuperclass() ).getQualifiedName().toString().equals( "java.lang.Object" );
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

    private static MethodType getMethodType(Accessor accessor) {
        final ExecutableElement method = accessor.getExecutable();
        if ( accessor.isBuilder() ) {
            return BUILDER_NAMING_STRATEGY.getMethodType( method );
        }
        else {
            return ACCESSOR_NAMING_STRATEGY.getMethodType( method );
        }
    }
}
