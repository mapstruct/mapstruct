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
package org.mapstruct.ap.internal.util.workarounds;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;

import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.apt.dispatch.BaseProcessingEnvImpl;
import org.eclipse.jdt.internal.compiler.apt.model.ElementImpl;
import org.eclipse.jdt.internal.compiler.lookup.MethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;

/**
 * Contains the workaround for {@link Types#asMemberOf(DeclaredType, Element)} using Eclipse implementation types.
 * <p>
 * <strong>This class may only be loaded when running within Eclipse</strong>
 *
 * @author Andreas Gudian
 */
final class EclipseAsMemberOfWorkaround {
    private EclipseAsMemberOfWorkaround() {
    }

    /**
     * Eclipse-specific implementation of {@link Types#asMemberOf(DeclaredType, Element)}.
     * <p>
     * Returns {@code null} if the implementation could not determine the result.
     */
    static TypeMirror asMemberOf(ProcessingEnvironment environment, DeclaredType containing,
                                 Element element) {

        ElementImpl elementImpl = tryCast( element, ElementImpl.class );
        BaseProcessingEnvImpl env = tryCast( environment, BaseProcessingEnvImpl.class );

        if ( elementImpl == null || env == null ) {
            return null;
        }

        ReferenceBinding referenceBinding =
            (ReferenceBinding) ( (ElementImpl) environment.getTypeUtils().asElement( containing ) )._binding;

        MethodBinding methodBinding = (MethodBinding) elementImpl._binding;

        // matches in super-classes have priority
        MethodBinding inSuperclassHiearchy = findInSuperclassHierarchy( methodBinding, referenceBinding );

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

        // there can be multiple matches for the same method name from adjacent interface hierarchies.
        Collections.sort( candidatesFromInterfaces, MostSpecificMethodBindingComparator.INSTANCE );

        if ( !candidatesFromInterfaces.isEmpty() ) {
            // return the most specific match
            return env.getFactory().newTypeMirror( candidatesFromInterfaces.get( 0 ) );
        }

        return null;
    }

    private static <T> T tryCast(Object instance, Class<T> type) {
        if ( instance != null && type.isInstance( instance ) ) {
            return type.cast( instance );
        }

        return null;
    }

    private static void collectFromInterfaces(MethodBinding methodBinding, ReferenceBinding typeBinding,
                                              Set<ReferenceBinding> visitedTypes, List<MethodBinding> found) {
        if ( typeBinding == null ) {
            return;
        }

        // also check the interfaces of the superclass hierarchy (the superclasses themselves don't contain a match,
        // we checked that already)
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

    private static MethodBinding findInSuperclassHierarchy(MethodBinding baseMethod, ReferenceBinding typeBinding) {
        while ( typeBinding != null ) {
            MethodBinding matching = findMatchingMethodBinding( baseMethod, typeBinding.methods() );
            if ( matching != null ) {
                return matching;
            }

            typeBinding = typeBinding.superclass();
        }

        return null;
    }

    /**
     * Compares MethodBindings by their signature: the more specific method is considered <em>lower</em>.
     *
     * @author Andreas Gudian
     */
    private static final class MostSpecificMethodBindingComparator implements Comparator<MethodBinding> {
        private static final MostSpecificMethodBindingComparator INSTANCE = new MostSpecificMethodBindingComparator();

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
    }
}
