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
package org.mapstruct.ap.internal.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.apt.dispatch.BaseProcessingEnvImpl;
import org.eclipse.jdt.internal.compiler.apt.model.DeclaredTypeImpl;
import org.eclipse.jdt.internal.compiler.apt.model.ElementImpl;
import org.eclipse.jdt.internal.compiler.apt.model.TypeMirrorImpl;
import org.eclipse.jdt.internal.compiler.apt.model.TypesImpl;
import org.eclipse.jdt.internal.compiler.lookup.MethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;

/**
 * Contains workarounds for various quirks in specific compilers.
 *
 * @author Sjaak Derksen
 * @author Andreas Gudian
 */
public class SpecificCompilerWorkarounds {

    private SpecificCompilerWorkarounds() { }

  /**
     * Tests whether one type is assignable to another.
     *
     * <p>
     * Work-around for a bug most likely related to problem solved with {@link #isSubType}
     *
     * @param types the type utils
     * @param t1 the first type
     * @param t2 the second type
     * @return {@code true} if and only if the first type is assignable to the second
     * @throws IllegalArgumentException if given an executable or package type
     */
    public static boolean isAssignable(Types types, TypeMirror t1, TypeMirror t2) {
        if ( t1.getKind() == TypeKind.VOID ) {
            return false;
        }

        return types.isAssignable( erasure( types, t1 ), erasure( types, t2 ) );
    }

    /**
     * Tests whether one type is a subtype of another. Any type is considered to be a subtype of itself. Also see <a
     * href="http://docs.oracle.com/javase/specs/jls/se8/html/jls-4.html">JLS section 4.10, Subtyping</a>.
     * <p>
     * Work-around for a bug related to sub-typing in the Eclipse JSR 269 implementation.
     *
     * @param types the type utils
     * @param t1 the first type
     * @param t2 the second type
     * @return {@code true} if and only if the first type is a subtype of the second
     * @throws IllegalArgumentException if given an executable or package type
     */
    public static boolean isSubType(Types types, TypeMirror t1, TypeMirror t2) {
        if ( t1.getKind() == TypeKind.VOID ) {
            return false;
        }

        return types.isSubtype( erasure( types, t1 ), erasure( types, t2 ) );
    }

    /**
     * Returns the erasure of a type.
     * <p>
     * Performs an additional test on the given type to check if it is not void. Calling
     * {@link Types#erasure(TypeMirror)} with a void kind type will create a ClassCastException in Eclipse JDT. See the
     * JLS, section 4.6 Type Erasure, for reference.
     *
     * @param types the type utils
     * @param t the type to be erased
     * @return the erasure of the given type
     * @throws IllegalArgumentException if given a package type
     */
    public static TypeMirror erasure(Types types, TypeMirror t) {
        if ( t.getKind() == TypeKind.VOID || t.getKind() == TypeKind.NULL ) {
            return t;
        }
        else {
            return types.erasure( t );
        }
    }

    /**
     * When running during Eclipse Incremental Compilation, we might get a TypeElement that has an UnresolvedTypeBinding
     * and which is not automatically resolved. In that case, getEnclosedElements returns an empty list. We take that as
     * a hint to check if the TypeElement resolved by FQN might have any enclosed elements and, if so, return the
     * resolved element.
     *
     * @param elementUtils element utils
     * @param element the original element
     * @return the element freshly resolved using the qualified name, if the original element did not return any
     *         enclosed elements, whereas the resolved element does return enclosed elements.
     */
    public static TypeElement replaceTypeElementIfNecessary(Elements elementUtils, TypeElement element) {
        if ( element.getEnclosedElements().isEmpty() ) {
            TypeElement resolvedByName = elementUtils.getTypeElement( element.getQualifiedName() );
            if ( resolvedByName != null && !resolvedByName.getEnclosedElements().isEmpty() ) {
                return resolvedByName;
            }
        }
        return element;
    }

    public static TypeMirror asMemberOf(DeclaredType containing, Element element, Types typeUtils,
                                        Elements elementUtils) {
        try {
            // quick and dirty, until implementation below is more sound
            return typeUtils.asMemberOf( containing, element );
        }
        catch ( IllegalArgumentException e ) {
            TypeMirror eclipseWorkaround =
                EclipseEnvironment.asMemberOf( containing, element, typeUtils, elementUtils );

            if ( null != eclipseWorkaround ) {
                return eclipseWorkaround;
            }

            throw e;
        }
    }

    private static final class EclipseEnvironment {
        public static TypeMirror asMemberOf(DeclaredType containing, Element element, Types typeUtils,
                                            Elements elementUtils) {
            if ( typeUtils instanceof TypesImpl && containing instanceof DeclaredTypeImpl
                && element instanceof ElementImpl ) {
                try {
                    ElementImpl elementImpl = (ElementImpl) element;

                    Field field = TypesImpl.class.getDeclaredField( "_env" );
                    field.setAccessible( true );
                    BaseProcessingEnvImpl env = (BaseProcessingEnvImpl) field.get( typeUtils );

                    // TODO can some of the reflection here be avoided?
                    Method bindingGetter = TypeMirrorImpl.class.getDeclaredMethod( "binding" );
                    bindingGetter.setAccessible( true );
                    ReferenceBinding referenceBinding = (ReferenceBinding) bindingGetter.invoke( containing );

                    MethodBinding methodBinding = (MethodBinding) elementImpl._binding;
                    // matches in super-classes have priority
                    MethodBinding inSuperclassHiearchy =
                        findInSuperclassHierarchy( methodBinding, env, referenceBinding );
                    if ( inSuperclassHiearchy != null ) {
                        return env.getFactory().newTypeMirror( inSuperclassHiearchy );
                    }

                    // if nothing was found, traverse the interfaces and collect all candidate methods that match
                    List<MethodBinding> candidatesFromInterfaces = new ArrayList<MethodBinding>();
                    collectFromInterfaces(
                        methodBinding,
                        referenceBinding,
                        new HashSet<ReferenceBinding>(),
                        candidatesFromInterfaces );

                    java.util.Collections.sort( candidatesFromInterfaces, new Comparator<MethodBinding>() {
                        @Override
                        public int compare(MethodBinding first, MethodBinding second) {
                            boolean firstParamsAssignableFromSecond =
                                first.areParametersCompatibleWith( second.parameters );
                            boolean secondParamsAssignableFromFirst =
                                second.areParametersCompatibleWith( first.parameters );

                            if ( firstParamsAssignableFromSecond != secondParamsAssignableFromFirst ) {
                                return firstParamsAssignableFromSecond ? 1 : -1;
                            }

                            if ( TypeBinding.equalsEquals( first.returnType, second.returnType ) ) {
                                return 0;
                            }

                            boolean firstReturnTypeAssignableFromSecond =
                                second.returnType.isCompatibleWith( first.returnType );

                            return firstReturnTypeAssignableFromSecond ? 1 : -1;
                        }
                    } );

                    if ( !candidatesFromInterfaces.isEmpty() ) {
                        return env.getFactory().newTypeMirror( candidatesFromInterfaces.get( 0 ) );
                    }
                }
                catch ( Exception e2 ) {
                    throw new RuntimeException( "Fallback implementation of asMemberOf didn't work for "
                        + element + " in " + containing, e2 );
                }
            }

            return null;
        }

        /**
         * @param methodBinding
         * @param typeBinding
         * @param found
         * @return
         */
        private static void collectFromInterfaces(MethodBinding methodBinding, ReferenceBinding typeBinding,
                                                  Set<ReferenceBinding> visitedTypes, List<MethodBinding> found) {
            if ( typeBinding == null ) {
                return;
            }

            // also check the interfaces of the superclass hierarchy (the superclasses themselves don't contain a match,
            // we
            // checked that already)
            collectFromInterfaces( methodBinding, typeBinding.superclass(), visitedTypes, found );

            for ( ReferenceBinding ifc : typeBinding.superInterfaces() ) {
                if ( visitedTypes.contains( ifc ) ) {
                    continue;
                }

                visitedTypes.add( ifc );

                // finding a match in one interface
                MethodBinding f = findMatchingMethodBinding( methodBinding, ifc.methods() );

                if ( f == null ) {
                    collectFromInterfaces( methodBinding, ifc, visitedTypes, found );
                }
                else {
                    // no need for recursion if we found a candidate in this type already
                    found.add( f );
                }
            }
        }

        /**
         * @param baseMethod binding to compare against
         * @param methods the candidate methods
         * @return The method from the list of candidates that matches the name and original/erasure of
         *         {@code methodBinding}, or {@code null} if none was found.
         */
        private static MethodBinding findMatchingMethodBinding(MethodBinding baseMethod, MethodBinding[] methods) {
            for ( MethodBinding method : methods ) {
                if ( CharOperation.equals( method.selector, baseMethod.selector )
                    && ( method.original() == baseMethod || method.areParameterErasuresEqual( baseMethod ) ) ) {
                    return method;
                }
            }

            return null;
        }

        private static MethodBinding findInSuperclassHierarchy(MethodBinding baseMethod, BaseProcessingEnvImpl env,
                                                               ReferenceBinding typeBinding) {
            while ( typeBinding != null ) {

                MethodBinding matching = findMatchingMethodBinding( baseMethod, typeBinding.methods() );
                if ( matching != null ) {
                    return matching;
                }

                typeBinding = typeBinding.superclass();
            }

            return null;
        }
    }
}
