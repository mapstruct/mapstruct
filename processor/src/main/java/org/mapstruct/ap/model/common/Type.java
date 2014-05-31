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
package org.mapstruct.ap.model.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import org.mapstruct.ap.util.Executables;
import org.mapstruct.ap.util.Filters;
import org.mapstruct.ap.util.TypeUtilsJDK6Fix;

/**
 * Represents (a reference to) the type of a bean property, parameter etc. Types are managed per generated source file.
 * Each type corresponds to a {@link TypeMirror}, i.e. there are different instances for e.g. {@code Set<String>} and
 * {@code Set<Integer>}.
 * <p>
 * Allows for a unified handling of declared and primitive types and usage within templates. Instances are obtained
 * through {@link TypeFactory}.
 *
 * @author Gunnar Morling
 */
public class Type extends ModelElement implements Comparable<Type> {

    private final Types typeUtils;
    private final Elements elementUtils;

    private final TypeMirror typeMirror;
    private final TypeElement typeElement;
    private final List<Type> typeParameters;

    private final Type implementationType;

    private final String packageName;
    private final String name;
    private final String qualifiedName;

    private final boolean isInterface;
    private final boolean isEnumType;
    private final boolean isIterableType;
    private final boolean isCollectionType;
    private final boolean isMapType;
    private final boolean isImported;
    private final List<String> enumConstants;

    private List<ExecutableElement> getters = null;
    private List<ExecutableElement> setters = null;
    private List<ExecutableElement> alternativeTargetAccessors = null;

    //CHECKSTYLE:OFF
    public Type(Types typeUtils, Elements elementUtils, TypeMirror typeMirror, TypeElement typeElement,
                List<Type> typeParameters, Type implementationType, String packageName, String name,
                String qualifiedName, boolean isInterface, boolean isEnumType, boolean isIterableType,
                boolean isCollectionType, boolean isMapType, boolean isImported) {

        this.typeUtils = typeUtils;
        this.elementUtils = elementUtils;

        this.typeMirror = typeMirror;
        this.typeElement = typeElement;
        this.typeParameters = typeParameters;
        this.implementationType = implementationType;

        this.packageName = packageName;
        this.name = name;
        this.qualifiedName = qualifiedName;

        this.isInterface = isInterface;
        this.isEnumType = isEnumType;
        this.isIterableType = isIterableType;
        this.isCollectionType = isCollectionType;
        this.isMapType = isMapType;
        this.isImported = isImported;

        if ( isEnumType ) {
            enumConstants = new ArrayList<String>();

            for ( Element element : typeElement.getEnclosedElements() ) {
                // #162: The check for visibility shouldn't be required, but the Eclipse compiler implementation
                // exposes non-enum members otherwise
                if ( element.getKind() == ElementKind.ENUM_CONSTANT &&
                    element.getModifiers().contains( Modifier.PUBLIC ) ) {
                    enumConstants.add( element.getSimpleName().toString() );
                }
            }
        }
        else {
            enumConstants = Collections.emptyList();
        }
    }
    //CHECKSTYLE:ON

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
     * Returns this type's enum constants in case it is an enum, an empty list otherwise.
     */
    public List<String> getEnumConstants() {
        return enumConstants;
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

    public boolean isMapType() {
        return isMapType;
    }

    public String getFullyQualifiedName() {
        return qualifiedName;
    }

    @Override
    public Set<Type> getImportTypes() {
        return implementationType != null ? Collections.singleton( implementationType ) : Collections.<Type>emptySet();
    }

    /**
     * Whether this type is imported by means of an import statement in the currently generated source file (meaning it
     * can be referenced in the generated source using its simple name) or not (meaning it has to be referenced using
     * the fully-qualified name).
     *
     * @return {@code true} if the type is imported, {@code false} otherwise.
     */
    public boolean isImported() {
        return isImported;
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

    public Type erasure() {
        return new Type(
            typeUtils,
            elementUtils,
            typeUtils.erasure( typeMirror ),
            typeElement,
            typeParameters,
            implementationType,
            packageName,
            name,
            qualifiedName,
            isInterface,
            isEnumType,
            isIterableType,
            isCollectionType,
            isMapType,
            isImported
        );
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
     * getGetters
     *
     * @return an unmodifiable list of all getters (including 'is' for booleans).
     */
    public List<ExecutableElement> getGetters() {
        if ( getters == null ) {
            List<? extends Element> members = elementUtils.getAllMembers( typeElement );
            getters = Collections.unmodifiableList( Filters.getterMethodsIn( members ) );
        }
        return getters;
    }

    /**
     * getSetters
     *
     * @return an unmodifiable list of all setters
     */
    public List<ExecutableElement> getSetters() {
        if ( setters == null ) {
            List<? extends Element> members = elementUtils.getAllMembers( typeElement );
            setters = Collections.unmodifiableList( Filters.setterMethodsIn( members ) );
        }
        return setters;
    }

    /**
     * Alternative accessors could be a getter for a collection / map. By means of the
     * {@link Collection#addAll(Collection) } or {@link Map#putAll(Map)} this getter can still be used as
     * targetAccessor. JAXB XJC tool generates such constructs. This method can be extended when new cases come along.
     *
     * @return an unmodifiable list of alternative target accessors.
     */
    public List<ExecutableElement> getAlternativeTargetAccessors() {
        if ( alternativeTargetAccessors == null ) {

            List<ExecutableElement> result = new ArrayList<ExecutableElement>();
            List<ExecutableElement> setterMethods = getSetters();
            List<ExecutableElement> getterMethods = getGetters();

            // there could be a getter method for a list/map that is not present as setter.
            // a getter could substitute the setter in that case and act as setter.
            // (assuming it is initialized)
            for ( ExecutableElement getterMethod : getterMethods ) {
                if ( hasNoSetterMethod( getterMethod, setterMethods ) && isCollectionOrMap( getterMethod ) ) {
                    result.add( getterMethod );
                }
            }

            alternativeTargetAccessors = Collections.unmodifiableList( result );
        }
        return alternativeTargetAccessors;
    }

    private boolean hasNoSetterMethod(ExecutableElement getterMethod, List<ExecutableElement> setterMethods) {
        String getterPropertyName = Executables.getPropertyName( getterMethod );
        for ( ExecutableElement setterMethod : setterMethods ) {
            String setterPropertyName = Executables.getPropertyName( setterMethod );
            if ( getterPropertyName.equals( setterPropertyName ) ) {
                return false;
            }
        }
        return true;
    }

    private boolean isCollectionOrMap(ExecutableElement getterMethod) {
        return isCollection( getterMethod.getReturnType() ) || isMap( getterMethod.getReturnType() );
    }

    private boolean isCollection(TypeMirror candidate) {
        return isSubType( candidate, Collection.class );
    }

    private boolean isMap(TypeMirror candidate) {
        return isSubType( candidate, Map.class );
    }

    private boolean isSubType(TypeMirror candidate, Class<?> clazz) {
        String className = clazz.getCanonicalName();
        TypeMirror classType = typeUtils.erasure( elementUtils.getTypeElement( className ).asType() );
        return TypeUtilsJDK6Fix.isSubType( typeUtils, candidate, classType );
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

    /**
     * Whether this type can access the given method declared on the given type.
     */
    public boolean canAccess(Type type, ExecutableElement method) {
        if ( method.getModifiers().contains( Modifier.PRIVATE ) ) {
            return false;
        }
        else if ( method.getModifiers().contains( Modifier.PROTECTED ) ) {
            return isAssignableTo( type ) || getPackageName().equals( type.getPackageName() );
        }
        else if ( !method.getModifiers().contains( Modifier.PUBLIC ) ) {
            // default
            return getPackageName().equals( type.getPackageName() );
        }
        // public
        return true;
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
}
