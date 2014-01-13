/**
 *  Copyright 2012-2014 Gunnar Morling (http://www.gunnarmorling.de/)
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
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Name;
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
    private final boolean isCollectionType;
    private final boolean isListType;
    private final boolean isMapType;
    private final Type implementationType;
    private final TypeMirror typeMirror;
    private final Types typeUtils;
    private final TypeElement typeElement;

    public Type(TypeMirror typeMirror, List<Type> typeParameters, Type implementationType, boolean isIterableType,
                boolean isCollectionType, boolean isListType, boolean isMapType, Types typeUtils,
                Elements elementUtils) {
        this.typeMirror = typeMirror;
        this.implementationType = implementationType;
        this.typeParameters = typeParameters;
        this.isIterableType = isIterableType;
        this.isCollectionType = isCollectionType;
        this.isListType = isListType;
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

    /**
     * Returns the implementation type to be instantiated in case this type is an interface iterable, collection or map
     * type. The type will have the correct type arguments, so if this type e.g. represents {@code Set<String>}, the
     * implementation type is {@code HashSet<String>}.
     *
     * @return The implementation type to be instantiated in case this type is an interface iterable, collection or map
     *         type, {@code null} otherwise.
     */
    public Type getImplementationType() {
        return implementationType;
    }

    public boolean isIterableType() {
        return isIterableType;
    }

    public boolean isCollectionType() {
        return isCollectionType;
    }

    public boolean isListType() {
        return isListType;
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

    /**
     * @param annotationTypeName the fully qualified name of the annotation type
     *
     * @return true, if the type is annotated with an annotation of the specified type (super-types are not inspected)
     */
    public boolean isAnnotatedWith(String annotationTypeName) {
        List<? extends AnnotationMirror> annotationMirrors = typeElement.getAnnotationMirrors();

        for ( AnnotationMirror mirror : annotationMirrors ) {
            Name mirrorAnnotationName = ( (TypeElement) mirror.getAnnotationType().asElement() ).getQualifiedName();
            if ( mirrorAnnotationName.contentEquals( annotationTypeName ) ) {
                return true;
            }
        }

        return false;
    }

    /**
     * Whether this type is assignable to the given other type.
     *
     * @param other The other type.
     *
     * @return {@code true} if and only if this type is assignable to the given other type.
     */
    // TODO This doesn't yet take wild card types into account; e.g. ? extends Integer wouldn't be assignable to Number
    // atm.
    public boolean isAssignableTo(Type other) {
        if ( equals( other ) ) {
            return true;
        }

        return typeUtils.isAssignable( typeMirror, other.typeMirror );
    }

    /**
     * Returns the length of the shortest path in the type hierarchy between this type and the specified other type.
     * Returns {@code -1} if this type is not assignable to the other type. Returns {@code 0} if this type is equal to
     * the other type. Returns {@code 1}, if the other type is a direct super type of this type, and so on.
     *
     * @param assignableOther the other type
     *
     * @return the length of the shortest path in the type hierarchy between this type and the specified other type
     */
    public int distanceTo(Type assignableOther) {
        return distanceTo( typeMirror, assignableOther.typeMirror );
    }

    private int distanceTo(TypeMirror base, TypeMirror targetType) {
        if ( typeUtils.isSameType( base, targetType ) ) {
            return 0;
        }

        if ( !typeUtils.isAssignable( base, targetType ) ) {
            return -1;
        }

        List<? extends TypeMirror> directSupertypes = typeUtils.directSupertypes( base );
        int minDistanceOfSuperToTargetType = Integer.MAX_VALUE;
        for ( TypeMirror type : directSupertypes ) {
            int distanceToTargetType = distanceTo( type, targetType );
            if ( distanceToTargetType >= 0 ) {
                minDistanceOfSuperToTargetType = Math.min( minDistanceOfSuperToTargetType, distanceToTargetType );
            }
        }

        return 1 + minDistanceOfSuperToTargetType;
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
