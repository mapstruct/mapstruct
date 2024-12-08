/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.util;

import javax.lang.model.element.Modifier;
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
public class Fields {

    private Fields() {
    }

    public static boolean isFieldAccessor(VariableElement method) {
        return isPublic( method ) && isNotStatic( method );
    }

    static boolean isPublic(VariableElement method) {
        return method.getModifiers().contains( Modifier.PUBLIC );
    }

    private static boolean isNotStatic(VariableElement method) {
        return !method.getModifiers().contains( Modifier.STATIC );
    }

    private static TypeElement asTypeElement(TypeMirror mirror) {
        return (TypeElement) ( (DeclaredType) mirror ).asElement();
    }

    private static boolean hasNonObjectSuperclass(TypeElement element) {
        if ( element.getSuperclass().getKind() == TypeKind.ERROR ) {
            throw new TypeHierarchyErroneousException( element );
        }

        return element.getSuperclass().getKind() == TypeKind.DECLARED
            && !"java.lang.Object".equals (asTypeElement( element.getSuperclass() ).getQualifiedName().toString() );
    }
}
