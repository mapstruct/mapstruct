/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.util.workarounds;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import org.mapstruct.ap.internal.version.VersionInformation;

/**
 * Contains workarounds for various quirks in specific compilers.
 *
 * @author Sjaak Derksen
 * @author Andreas Gudian
 */
public class SpecificCompilerWorkarounds {
    private SpecificCompilerWorkarounds() {
    }

    /**
     * Tests whether one type is assignable to another, checking for VOID first.
     *
     * @param types the type utils
     * @param t1 the first type
     * @param t2 the second type
     * @return {@code true} if and only if the first type is assignable to the second
     * @throws IllegalArgumentException if given an executable or package type
     */
    static boolean isAssignable(Types types, TypeMirror t1, TypeMirror t2) {
        if ( t1.getKind() == TypeKind.VOID ) {
            return false;
        }

        return types.isAssignable( t1, t2 );
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
    static boolean isSubtype(Types types, TypeMirror t1, TypeMirror t2) {
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
    static TypeMirror erasure(Types types, TypeMirror t) {
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

    /**
     * Workaround for Bugs in the Eclipse implementation of {@link Types#asMemberOf(DeclaredType, Element)}.
     *
     * @see <a href="https://bugs.eclipse.org/bugs/show_bug.cgi?id=382590">Eclipse Bug 382590 (fixed in Eclipse 4.6)</a>
     * @see <a href="https://bugs.eclipse.org/bugs/show_bug.cgi?id=481555">Eclipse Bug 481555</a>
     */
    static TypeMirror asMemberOf(Types typeUtils, ProcessingEnvironment env, VersionInformation versionInformation,
                                 DeclaredType containing, Element element) {
        return typeUtils.asMemberOf( containing, element );
    }
}
