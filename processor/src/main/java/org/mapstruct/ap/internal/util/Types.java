/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.util;

import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

import org.mapstruct.ap.spi.TypeHierarchyErroneousException;

/**
 * Provides functionality around {@link VariableElement}s.
 *
 * @author Sjaak Derksen
 */
public class Types {

    private Types() {
    }

    /**
     * @param element the type element
     * @return returns true if the element has a superclass not equal to {@link java.lang.Object}
     */
    public static boolean hasNonObjectSuperclass(TypeElement element) {
        if ( element.getSuperclass().getKind() == TypeKind.ERROR ) {
            throw new TypeHierarchyErroneousException( element );
        }

        return element.getSuperclass().getKind() == TypeKind.DECLARED
                && !asTypeElement( element.getSuperclass() ).getQualifiedName().toString().equals( "java.lang.Object" );
    }

    /**
     *
     * @param mirror the mirror
     * @return the {@link TypeElement} for the provided mirror.
     */
    public static TypeElement asTypeElement(TypeMirror mirror) {
        return (TypeElement) ( (DeclaredType) mirror ).asElement();
    }
}
