/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.util;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.type.WildcardType;
import javax.lang.model.util.Types;

public class EclipseTypeUtilsDecorator extends AbstractTypeUtilsDecorator {

    private final Types delegate;

    EclipseTypeUtilsDecorator(ProcessingEnvironment processingEnv) {
        super( processingEnv );
        this.delegate = processingEnv.getTypeUtils();
    }

    @Override
    public boolean contains(TypeMirror t1, TypeMirror t2) {
        if ( TypeKind.TYPEVAR == t2.getKind() ) {
            return containsType( t1, ( (TypeVariable) t2 ).getLowerBound() );
        }
        else {
            return containsType( t1, t2 );
        }
    }

    private boolean containsType(TypeMirror t1, TypeMirror t2) {

        boolean result = false;
        if ( TypeKind.DECLARED == t2.getKind() ) {
            if ( TypeKind.WILDCARD == t1.getKind() ) {
                WildcardType wct = (WildcardType) t1;
                if ( wct.getExtendsBound() != null ) {
                    result = isAssignable( t2, wct.getExtendsBound() );
                }
                else if ( wct.getSuperBound() != null ) {
                    result = isAssignable( wct.getSuperBound(), t2 );
                }
                else {
                    result = isAssignable( t2, wct );
                }
            }
        }
        return result;
    }

}
