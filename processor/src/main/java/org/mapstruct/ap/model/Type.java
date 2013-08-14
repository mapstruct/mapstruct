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
package org.mapstruct.ap.model;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.SimpleElementVisitor6;
import javax.lang.model.util.Types;

import org.mapstruct.ap.util.TypeFactory;

/**
 * Represents the type of a bean property, parameter etc. Each type corresponds to a {@link TypeMirror}, i.e. there are
 * different instances for e.g. {@code Set<String>} and {@code Set<Integer>}.
 * <p>
 * Allows for a unified handling of declared and primitive types and usage within templates. Instances are obtained
 * through {@link TypeFactory}.
 *
 * @author Gunnar Morling
 */
public class Type extends AbstractModelElement implements Comparable<Type> {

    private final String packageName;
    private final String name;
    private final String qualifiedName;
    private final List<Type> typeParameters;
    private final boolean isInterface;
    private final boolean isEnumType;
    private final boolean isIterableType;
    private final boolean isMapType;
    private final Type implementationType;
    private final TypeMirror typeMirror;
    private final Types typeUtils;
    private final TypeElement typeElement;

    public Type(TypeMirror typeMirror, List<Type> typeParameters, Type implementationType, boolean isIterableType,
                boolean isMapType,
                Types typeUtils, Elements elementUtils) {
        this.typeMirror = typeMirror;
        this.implementationType = implementationType;
        this.typeParameters = typeParameters;
        this.isIterableType = isIterableType;
        this.isMapType = isMapType;
        this.typeUtils = typeUtils;

        DeclaredType declaredType = typeMirror.getKind() == TypeKind.DECLARED ? (DeclaredType) typeMirror : null;

        if ( declaredType != null ) {
            isEnumType = declaredType.asElement().getKind() == ElementKind.ENUM;
            isInterface = declaredType.asElement().getKind() == ElementKind.INTERFACE;
            name = declaredType.asElement().getSimpleName().toString();

            typeElement = declaredType.asElement().accept( new TypeElementRetrievalVisitor(), null );

            if ( typeElement != null ) {
                packageName = elementUtils.getPackageOf( typeElement ).getQualifiedName().toString();
                qualifiedName = typeElement.getQualifiedName().toString();
            }
            else {
                packageName = null;
                qualifiedName = name;
            }
        }
        else {
            isEnumType = false;
            isInterface = false;
            typeElement = null;
            name = typeMirror.toString();
            packageName = null;
            qualifiedName = name;
        }
    }

    public TypeMirror getTypeMirror() {
        return typeMirror;
    }

    public TypeElement getTypeElement() {
        return typeElement;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getName() {
        return name;
    }

    public List<Type> getTypeParameters() {
        return typeParameters;
    }

    public boolean isPrimitive() {
        return typeMirror.getKind().isPrimitive();
    }

    public boolean isInterface() {
        return isInterface;
    }

    public boolean isEnumType() {
        return isEnumType;
    }

    public Type getImplementationType() {
        return implementationType;
    }

    public boolean isIterableType() {
        return isIterableType;
    }

    public boolean isMapType() {
        return isMapType;
    }

    public String getFullyQualifiedName() {
        return qualifiedName;
    }

    @Override
    public Set<Type> getImportTypes() {
        return implementationType != null ? org.mapstruct.ap.util.Collections.<Type>asSet( implementationType ) :
            Collections.<Type>emptySet();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( name == null ) ? 0 : name.hashCode() );
        result = prime * result + ( ( packageName == null ) ? 0 : packageName.hashCode() );
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if ( this == obj ) {
            return true;
        }
        if ( obj == null ) {
            return false;
        }
        if ( getClass() != obj.getClass() ) {
            return false;
        }
        Type other = (Type) obj;

        return typeUtils.isSameType( typeMirror, other.typeMirror );
    }

    @Override
    public int compareTo(Type o) {
        return getFullyQualifiedName().compareTo( o.getFullyQualifiedName() );
    }

    @Override
    public String toString() {
        return typeMirror.toString();
    }

    private static class TypeElementRetrievalVisitor extends SimpleElementVisitor6<TypeElement, Void> {
        @Override
        public TypeElement visitType(TypeElement e, Void p) {
            return e;
        }
    }
}
