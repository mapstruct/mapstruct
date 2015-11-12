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
package org.mapstruct.ap.internal.model.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
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
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.WildcardType;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import org.mapstruct.ap.internal.prism.CollectionMappingStrategyPrism;
import org.mapstruct.ap.internal.util.Executables;
import org.mapstruct.ap.internal.util.Filters;
import org.mapstruct.ap.internal.util.Nouns;

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
    private final TypeFactory typeFactory;

    private final TypeMirror typeMirror;
    private final TypeElement typeElement;
    private final List<Type> typeParameters;

    private final Type implementationType;
    private final Type componentType;

    private final String packageName;
    private final String name;
    private final String qualifiedName;

    private final boolean isInterface;
    private final boolean isEnumType;
    private final boolean isIterableType;
    private final boolean isCollectionType;
    private final boolean isMapType;
    private final boolean isImported;
    private final boolean isVoid;

    private final List<String> enumConstants;

    private Map<String, ExecutableElement> getters = null;

    private List<ExecutableElement> allExecutables = null;
    private List<ExecutableElement> setters = null;
    private List<ExecutableElement> adders = null;
    private List<ExecutableElement> alternativeTargetAccessors = null;


    private Type boundingBase = null;


    //CHECKSTYLE:OFF
    public Type(Types typeUtils, Elements elementUtils, TypeFactory typeFactory,
                TypeMirror typeMirror, TypeElement typeElement,
                List<Type> typeParameters, Type implementationType, Type componentType,
                String packageName, String name, String qualifiedName,
                boolean isInterface, boolean isEnumType, boolean isIterableType,
                boolean isCollectionType, boolean isMapType, boolean isImported) {

        this.typeUtils = typeUtils;
        this.elementUtils = elementUtils;
        this.typeFactory = typeFactory;

        this.typeMirror = typeMirror;
        this.typeElement = typeElement;
        this.typeParameters = typeParameters;
        this.componentType = componentType;
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
        this.isVoid = typeMirror.getKind() == TypeKind.VOID;

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

    public Type getComponentType() {
        return componentType;
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

    public boolean isVoid() {
        return isVoid;
    }

    /**
     * @return this type's enum constants in case it is an enum, an empty list otherwise.
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
     * type, {@code null} otherwise.
     */
    public Type getImplementationType() {
        return implementationType;
    }

    /**
     * Whether this type is a sub-type of {@link Iterable} or an array type.
     *
     * @return {@code true} if this type is a sub-type of {@link Iterable} or an array type, {@code false} otherwise.
     */
    public boolean isIterableType() {
        return isIterableType || isArrayType();
    }

    public boolean isCollectionType() {
        return isCollectionType;
    }

    public boolean isMapType() {
        return isMapType;
    }

    public boolean isCollectionOrMapType() {
        return isCollectionType || isMapType;
    }

    public boolean isArrayType() {
        return componentType != null;
    }

    public boolean isTypeVar() {
        return (typeMirror.getKind() == TypeKind.TYPEVAR);
    }

    public boolean isWildCardSuperBound() {
        boolean result = false;
        if ( typeMirror.getKind() == TypeKind.WILDCARD ) {
            WildcardType wildcardType = (WildcardType) typeMirror;
            result = wildcardType.getSuperBound() != null;
        }
        return result;
    }

    public boolean isWildCardExtendsBound() {
        boolean result = false;
        if ( typeMirror.getKind() == TypeKind.WILDCARD ) {
            WildcardType wildcardType = (WildcardType) typeMirror;
            result = wildcardType.getExtendsBound() != null;
        }
        return result;
    }


    public String getFullyQualifiedName() {
        return qualifiedName;
    }

    /**
     * @return The name of this type as to be used within import statements.
     */
    public String getImportName() {
        return isArrayType() ? qualifiedName.substring( 0, qualifiedName.length() - 2 ) : qualifiedName;
    }

    @Override
    public Set<Type> getImportTypes() {
        Set<Type> result = new HashSet<Type>();

        result.add( this );

        if ( componentType != null ) {
            result.addAll( componentType.getImportTypes() );
        }

        for ( Type parameter : typeParameters ) {
            result.addAll( parameter.getImportTypes() );
        }

        return result;
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
            typeFactory,
            typeUtils.erasure( typeMirror ),
            typeElement,
            typeParameters,
            implementationType,
            componentType,
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
     * getPropertyReadAccessors
     *
     * @return an unmodifiable map of all read accessors (including 'is' for booleans), indexed by property name
     */
    public Map<String, ExecutableElement> getPropertyReadAccessors() {
        if ( getters == null ) {
            List<ExecutableElement> getterList = Filters.getterMethodsIn( getAllExecutables() );
            Map<String, ExecutableElement> modifiableGetters = new LinkedHashMap<String, ExecutableElement>();
            for (ExecutableElement getter : getterList) {
                modifiableGetters.put( Executables.getPropertyName( getter ), getter );
            }
            getters = Collections.unmodifiableMap( modifiableGetters );
        }
        return getters;
    }

    /**
     * getPropertyWriteAccessors returns a map of the write accessors according to the CollectionMappingStrategy. These
     * accessors include:
     * <ul>
     * <li>setters, the obvious candidate :-), {@link #getSetters() }</li>
     * <li>getters, for collections that do not have a setter, e.g. for JAXB generated collection attributes
     * {@link #getPropertyReadAccessors() }</li>
     * <li>adders, typically for from table generated entities, {@link #getAdders() }</li>
     * </ul>
     *
     * @param cmStrategy collection mapping strategy
     * @return an unmodifiable map of all write accessors indexed by property name
     */
    public Map<String, ExecutableElement> getPropertyWriteAccessors( CollectionMappingStrategyPrism cmStrategy ) {

        // collect all candidate target accessors
        List<ExecutableElement> candidates = new ArrayList<ExecutableElement>();
        candidates.addAll( getSetters() );
        candidates.addAll( getAlternativeTargetAccessors() );

        Map<String, ExecutableElement> result = new LinkedHashMap<String, ExecutableElement>();

        for ( ExecutableElement candidate : candidates ) {
            String targetPropertyName = Executables.getPropertyName( candidate );

            // A target access is in general a setter method on the target object. However, in case of collections,
            // the current target accessor can also be a getter method.
            // The following if block, checks if the target accessor should be overruled by an add method.
            if ( cmStrategy == CollectionMappingStrategyPrism.SETTER_PREFERRED
                    || cmStrategy == CollectionMappingStrategyPrism.ADDER_PREFERRED ) {

                // first check if there's a setter method.
                ExecutableElement adderMethod = null;
                if ( Executables.isSetterMethod( candidate ) ) {
                    Type targetType = typeFactory.getSingleParameter( typeElement, candidate ).getType();
                    // ok, the current accessor is a setter. So now the strategy determines what to use
                    if ( cmStrategy == CollectionMappingStrategyPrism.ADDER_PREFERRED ) {
                        adderMethod = getAdderForType( targetType, targetPropertyName );
                    }
                }
                else if ( Executables.isGetterMethod( candidate ) ) {
                        // the current accessor is a getter (no setter available). But still, an add method is according
                    // to the above strategy (SETTER_PREFERRED || ADDER_PREFERRED) preferred over the getter.
                    Type targetType = typeFactory.getReturnType( typeFactory.getMethodType( typeElement, candidate ) );
                    adderMethod = getAdderForType( targetType, targetPropertyName );
                }
                if ( adderMethod != null ) {
                    // an adder has been found (according strategy) so overrule current choice.
                    candidate = adderMethod;
                }
            }

            result.put( targetPropertyName, candidate );
        }
        return result;
    }

    private List<ExecutableElement> getAllExecutables() {
        if ( allExecutables == null ) {
            allExecutables = Executables.getAllEnclosedExecutableElements( elementUtils, typeElement );
        }

        return allExecutables;
    }

    /**
     * Tries to find an addMethod in this type for given collection property in this type.
     *
     * Matching occurs on:
     * <ol>
     * <li>The generic type parameter type of the collection should match the adder method argument</li>
     * <li>When there are more candidates, property name is made singular (as good as is possible). This routine
     * looks for a matching add method name.</li>
     * <li>The singularization rules of Dali are used to make a property name singular. This routine
     * looks for a matching add method name.</li>
     * </ol>
     *
     * @param collectionProperty property type (assumed collection) to find  the adder method for
     * @param pluralPropertyName the property name (assumed plural)
     *
     * @return corresponding adder method for getter when present
     */
    private ExecutableElement getAdderForType(Type collectionProperty, String pluralPropertyName) {

        List<ExecutableElement> candidates = new ArrayList<ExecutableElement>();
        if ( collectionProperty.isCollectionType ) {

            // this is a collection, so this can be done always
            if ( !collectionProperty.getTypeParameters().isEmpty() ) {
                // there's only one type arg to a collection
                TypeMirror typeArg = collectionProperty.getTypeParameters().get( 0 ).getTypeMirror();
                // now, look for a method that
                // 1) starts with add,
                // 2) and has typeArg as one and only arg
                List<ExecutableElement> adderList = getAdders();
                for ( ExecutableElement adder : adderList ) {
                    VariableElement arg = adder.getParameters().get( 0 );
                    if ( arg.asType().equals( typeArg ) ) {
                        candidates.add( adder );
                    }
                }
            }
        }
        if ( candidates.isEmpty() ) {
            return null;
        }
        else if ( candidates.size() == 1 ) {
            return candidates.get( 0 );
        }
        else {
            for ( ExecutableElement candidate : candidates ) {
                String elementName = Executables.getElementNameForAdder( candidate );
                if ( elementName.equals( Nouns.singularize( pluralPropertyName ) ) ) {
                    return candidate;
                }
            }
        }

        return null;
    }

    /**
     * getSetters
     *
     * @return an unmodifiable list of all setters
     */
    private List<ExecutableElement> getSetters() {
        if ( setters == null ) {
            setters = Collections.unmodifiableList( Filters.setterMethodsIn( getAllExecutables() ) );
        }
        return setters;
    }

    /**
     * Alternative accessors could be a getter for a collection / map. By means of the
     * {@link Collection#addAll(Collection) } or {@link Map#putAll(Map)} this getter can still be used as
     * targetAccessor. JAXB XJC tool generates such constructs. This method can be extended when new cases come along.
     * getAdders
     *
     * @return an unmodifiable list of all adders
     */
    private List<ExecutableElement> getAdders() {
        if ( adders == null ) {
            adders = Collections.unmodifiableList( Filters.adderMethodsIn( getAllExecutables() ) );
        }
        return adders;
    }

    /**
     * Alternative accessors could be a getter for a collection. By means of the
     * {@link java.util.Collection#addAll(java.util.Collection) } this getter can still
     * be used as targetAccessor. JAXB XJC tool generates such constructs.
     *
     * This method can be extended when new cases come along.
     *
     * @return an unmodifiable list of alternative target accessors.
     */
    private List<ExecutableElement> getAlternativeTargetAccessors() {

        if ( alternativeTargetAccessors == null ) {

            List<ExecutableElement> result = new ArrayList<ExecutableElement>();
            List<ExecutableElement> setterMethods = getSetters();
            List<ExecutableElement> getterMethods =
                new ArrayList<ExecutableElement>( getPropertyReadAccessors().values() );

            // there could be a getter method for a list/map that is not present as setter.
            // a getter could substitute the setter in that case and act as setter.
            // (assuming it is initialized)
            for ( ExecutableElement getterMethod : getterMethods ) {
                if ( isCollectionOrMap( getterMethod ) &&
                    !correspondingSetterMethodExists( getterMethod, setterMethods ) ) {
                    result.add( getterMethod );
                }
            }

            alternativeTargetAccessors = Collections.unmodifiableList( result );
        }
        return alternativeTargetAccessors;
    }

    private boolean correspondingSetterMethodExists(ExecutableElement getterMethod,
                                                    List<ExecutableElement> setterMethods) {
        String getterPropertyName = Executables.getPropertyName( getterMethod );

        for ( ExecutableElement setterMethod : setterMethods ) {
            String setterPropertyName = Executables.getPropertyName( setterMethod );
            if ( getterPropertyName.equals( setterPropertyName ) ) {
                return true;
            }
        }

        return false;
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
        return typeUtils.isSubtype( candidate, classType );
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
     * @param type the type declaring the method
     * @param method the method to check
     * @return Whether this type can access the given method declared on the given type.
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

    /**
     * @return A valid Java expression most suitable for representing null - useful for dealing with primitives from
     *         FTL.
     */
    public String getNull() {
        if ( !isPrimitive() || isArrayType() ) {
            return "null";
        }
        if ( "boolean".equals( getName() ) ) {
            return "false";
        }
        if ( "byte".equals( getName() ) ) {
            return "0";
        }
        if ( "char".equals( getName() ) ) {
            return "'\\u0000'";
        }
        if ( "double".equals( getName() ) ) {
            return "0.0";
        }
        if ( "float".equals( getName() ) ) {
            return "0.0f";
        }
        if ( "int".equals( getName() ) ) {
            return "0";
        }
        if ( "long".equals( getName() ) ) {
            return "0L";
        }
        if ( "short".equals( getName() ) ) {
            return "0";
        }
        throw new UnsupportedOperationException( getName() );
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


    /**
     *
     * @return an identification that can be used as part in a forged method name.
     */
    public String getIdentification() {
        if ( isArrayType() ) {
            return componentType.getName() + "Array";
        }
        else {
            return getTypeBound().getName();
        }
    }

    /**
     * Establishes the type bound:
     * <ol>
     * <li>{@code<? extends Number>}, returns Number</li>
     * <li>{@code<? super Number>}, returns Number</li>
     * <li>{@code<?>}, returns Object</li>
     * <li>{@code<T extends Number>, returns Number}</li>
     * </ol>
     * @return the bound for this parameter
     */
    public Type getTypeBound() {
        if ( boundingBase != null ) {
            return boundingBase;
        }

        boundingBase = typeFactory.getType( typeFactory.getTypeBound( getTypeMirror() ) );

        return boundingBase;
    }
}
