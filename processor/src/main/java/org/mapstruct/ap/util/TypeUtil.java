/**
 *  Copyright 2012-2013 Gunnar Morling (http://www.gunnarmorling.de/)
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

import java.util.Collection;
import javax.lang.model.element.ElementKind;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import org.mapstruct.ap.model.Type;

public class TypeUtil {

    private final Elements elementUtils;
    private final Types typeUtils;
    private TypeMirror collectionType;

    public TypeUtil(Elements elementUtils, Types typeUtils) {
        this.elementUtils = elementUtils;
        this.typeUtils = typeUtils;
        collectionType = elementUtils.getTypeElement( Collection.class.getCanonicalName() ).asType();
    }

    public Type getType(DeclaredType type) {
        Type elementType = null;

        if ( isIterableType( type ) && !type.getTypeArguments().isEmpty() ) {
            elementType = retrieveType( type.getTypeArguments().iterator().next() );
        }

        return new Type(
            elementUtils.getPackageOf( type.asElement() ).toString(),
            type.asElement().getSimpleName().toString(),
            elementType,
            type.asElement().getKind() == ElementKind.ENUM,
            typeUtils.isAssignable( typeUtils.erasure( type ), typeUtils.erasure( collectionType ) )
        );
    }

    private boolean isIterableType(DeclaredType type) {
        TypeMirror iterableType = typeUtils.getDeclaredType( elementUtils.getTypeElement( Iterable.class.getCanonicalName() ) );
        return typeUtils.isSubtype( type, iterableType );
    }

    public Type retrieveType(TypeMirror mirror) {
        if ( mirror == null ) {
            return null;
        }
        else if ( mirror.getKind() == TypeKind.DECLARED ) {
            return getType( ( (DeclaredType) mirror ) );
        }
        else {
            return new Type( mirror.toString() );
        }
    }
}
