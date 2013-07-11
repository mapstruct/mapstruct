/**
 *  Copyright 2012-2013 Gunnar Morling (http://www.gunnarmorling.de/)
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
    private final TypeMirror collectionType;
    private final TypeMirror iterableType;

    public TypeUtil(Elements elementUtils, Types typeUtils) {
        this.elementUtils = elementUtils;
        this.typeUtils = typeUtils;
        collectionType = elementUtils.getTypeElement( Collection.class.getCanonicalName() ).asType();
        iterableType = elementUtils.getTypeElement( Iterable.class.getCanonicalName() ).asType();
    }

    public Type getType(DeclaredType type) {
        List<Type> typeParameters = new ArrayList<Type>();

        boolean isIterableType = isIterableType( type );
        for ( TypeMirror mirror : type.getTypeArguments() ) {
            typeParameters.add( retrieveType( mirror ) );
        }

        return new Type(
            elementUtils.getPackageOf( type.asElement() ).toString(),
            type.asElement().getSimpleName().toString(),
            type.asElement().getKind() == ElementKind.ENUM,
            isCollectionType( type ),
            isIterableType,
            typeParameters
        );
    }

    private boolean isIterableType(DeclaredType type) {
        return typeUtils.isAssignable( typeUtils.erasure( type ), typeUtils.erasure( iterableType ) );
    }

    private boolean isCollectionType(DeclaredType type) {
        return typeUtils.isAssignable( typeUtils.erasure( type ), typeUtils.erasure( collectionType ) );
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
