/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
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
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.WildcardType;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import org.mapstruct.ap.internal.prism.CollectionMappingStrategyPrism;
import org.mapstruct.ap.internal.util.AccessorNamingUtils;
import org.mapstruct.ap.internal.util.Executables;
import org.mapstruct.ap.internal.util.Filters;
import org.mapstruct.ap.internal.util.Nouns;
import org.mapstruct.ap.internal.util.accessor.Accessor;
import org.mapstruct.ap.internal.util.accessor.ExecutableElementAccessor;
import org.mapstruct.ap.spi.BuilderInfo;

import static org.mapstruct.ap.internal.util.Collections.first;
import org.mapstruct.ap.internal.util.NativeTypes;

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
    private final AccessorNamingUtils accessorNaming;

    private final TypeMirror typeMirror;
    private final TypeElement typeElement;
    private final List<Type> typeParameters;

    private final ImplementationType implementationType;
    private final Type componentType;
    private final BuilderType builderType;

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
    private final boolean isStream;
    private final boolean isLiteral;

    private final List<String> enumConstants;

    private Map<String, Accessor> readAccessors = null;
    private Map<String, ExecutableElementAccessor> presenceCheckers = null;

    private List<Accessor> allAccessors = null;
    private List<Accessor> setters = null;
    private List<Accessor> adders = null;
    private List<Accessor> alternativeTargetAccessors = null;

    private Type boundingBase = null;

    private Boolean hasEmptyAccessibleContructor;

    //CHECKSTYLE:OFF
    public Type(Types typeUtils, Elements elementUtils, TypeFactory typeFactory,
                AccessorNamingUtils accessorNaming,
                TypeMirror typeMirror, TypeElement typeElement,
                List<Type> typeParameters, ImplementationType implementationType, Type componentType,
                BuilderInfo builderInfo,
                String packageName, String name, String qualifiedName,
                boolean isInterface, boolean isEnumType, boolean isIterableType,
                boolean isCollectionType, boolean isMapType, boolean isStreamType, boolean isImported,
                boolean isLiteral ) {

        this.typeUtils = typeUtils;
        this.elementUtils = elementUtils;
        this.typeFactory = typeFactory;
        this.accessorNaming = accessorNaming;

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
        this.isStream = isStreamType;
        this.isImported = isImported;
        this.isVoid = typeMirror.getKind() == TypeKind.VOID;
        this.isLiteral = isLiteral;

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

        this.builderType = BuilderType.create( builderInfo, this, this.typeFactory, this.typeUtils );
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

    /**
     * String that could be used in generated code to reference to this {@link Type}.
     *
     * @return Just the name if this {@link Type} will be imported, otherwise the fully-qualified name.
     */
    public String getReferenceName() {
        return isImported ? name : qualifiedName;
    }

    public List<Type> getTypeParameters() {
        return typeParameters;
    }

    public Type getComponentType() {
        return componentType;
    }

    public BuilderType getBuilderType() {
        return builderType;
    }

    /**
     * The effective type that should be used when searching for getters / setters, creating new types etc
     * @return the effective type for mappings
     */
    public Type getEffectiveType() {
        return builderType != null ? builderType.getBuilder() : this;
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

    public boolean isAbstract() {
        return typeElement != null && typeElement.getModifiers().contains( Modifier.ABSTRACT );
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
        return implementationType != null ? implementationType.getType() : null;
    }

    public ImplementationType getImplementation() {
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

    /**
     * Whether this type is a sub-type of{@link Iterable}, {@link java.util.stream.Stream} or an array type
     *
     * @return {@code true} if this type is a sub-type of{@link Iterable}, {@link java.util.stream.Stream} or
     * an array type, {@code false} otherwise
     */
    public boolean isIterableOrStreamType() {
        return isIterableType() || isStreamType();
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

    /**
     * Whether this type is a sub-type of {@link java.util.stream.Stream}.
     *
     * @return {@code true} it this type is a sub-type of {@link java.util.stream.Stream}, {@code false otherwise}
     */
    public boolean isStreamType() {
        return isStream;
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
        return isArrayType() ? TypeFactory.trimSimpleClassName( qualifiedName ) : qualifiedName;
    }

    @Override
    public Set<Type> getImportTypes() {
        Set<Type> result = new HashSet<Type>();

        if ( getTypeMirror().getKind() == TypeKind.DECLARED ) {
            result.add( this );
        }

        if ( componentType != null ) {
            result.addAll( componentType.getImportTypes() );
        }

        for ( Type parameter : typeParameters ) {
            result.addAll( parameter.getImportTypes() );
        }

        if ( ( isWildCardExtendsBound() || isWildCardSuperBound() ) && getTypeBound() != null ) {
            result.addAll( getTypeBound().getImportTypes() );
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
            accessorNaming,
            typeUtils.erasure( typeMirror ),
            typeElement,
            typeParameters,
            implementationType,
            componentType,
            builderType == null ? null : builderType.asBuilderInfo(),
            packageName,
            name,
            qualifiedName,
            isInterface,
            isEnumType,
            isIterableType,
            isCollectionType,
            isMapType,
            isStream,
            isImported,
            isLiteral
        );
    }

    public Type withoutBounds() {
        if ( typeParameters.isEmpty() ) {
            return this;
        }

        List<Type> bounds = new ArrayList<Type>( typeParameters.size() );
        List<TypeMirror> mirrors = new ArrayList<TypeMirror>( typeParameters.size() );
        for ( Type typeParameter : typeParameters ) {
            bounds.add( typeParameter.getTypeBound() );
            mirrors.add( typeParameter.getTypeBound().getTypeMirror() );
        }

        DeclaredType declaredType = typeUtils.getDeclaredType(
            typeElement,
            mirrors.toArray( new TypeMirror[] {} )
        );
        return new Type(
            typeUtils,
            elementUtils,
            typeFactory,
            accessorNaming,
            declaredType,
            (TypeElement) declaredType.asElement(),
            bounds,
            implementationType,
            componentType,
            builderType == null ? null : builderType.asBuilderInfo(),
            packageName,
            name,
            qualifiedName,
            isInterface,
            isEnumType,
            isIterableType,
            isCollectionType,
            isMapType,
            isStream,
            isImported,
            isLiteral
        );
    }

    /**
     * Whether this type is assignable to the given other type.
     *
     * @param other The other type.
     *
     * @return {@code true} if and only if this type is assignable to the given other type.
     */
    // TODO This doesn't yet take super wild card types into account;
    // e.g. Number wouldn't be assignable to ? super Number atm. (is there any practical use case)
    public boolean isAssignableTo(Type other) {
        if ( equals( other ) ) {
            return true;
        }

        TypeMirror typeMirrorToMatch = isWildCardExtendsBound() ? getTypeBound().typeMirror : typeMirror;

        return typeUtils.isAssignable( typeMirrorToMatch, other.typeMirror );
    }

    /**
     * getPropertyReadAccessors
     *
     * @return an unmodifiable map of all read accessors (including 'is' for booleans), indexed by property name
     */
    public Map<String, Accessor> getPropertyReadAccessors() {
        if ( readAccessors == null ) {
            List<Accessor> getterList = Filters.getterMethodsIn( accessorNaming, getAllAccessors() );
            Map<String, Accessor> modifiableGetters = new LinkedHashMap<String, Accessor>();
            for ( Accessor getter : getterList ) {
                String propertyName = accessorNaming.getPropertyName( getter );
                if ( modifiableGetters.containsKey( propertyName ) ) {
                    // In the DefaultAccessorNamingStrategy, this can only be the case for Booleans: isFoo() and
                    // getFoo(); The latter is preferred.
                    if ( !getter.getSimpleName().toString().startsWith( "is" ) ) {
                        modifiableGetters.put( accessorNaming.getPropertyName( getter ), getter );
                    }

                }
                else {
                    modifiableGetters.put( accessorNaming.getPropertyName( getter ), getter );
                }
            }

            List<Accessor> fieldsList = Filters.fieldsIn( getAllAccessors() );
            for ( Accessor field : fieldsList ) {
                String propertyName = accessorNaming.getPropertyName( field );
                if ( !modifiableGetters.containsKey( propertyName ) ) {
                    // If there was no getter or is method for booleans, then resort to the field.
                    // If a field was already added do not add it again.
                    modifiableGetters.put( propertyName, field );
                }
            }
            readAccessors = Collections.unmodifiableMap( modifiableGetters );
        }
        return readAccessors;
    }

    /**
     * getPropertyPresenceCheckers
     *
     * @return an unmodifiable map of all presence checkers, indexed by property name
     */
    public Map<String, ExecutableElementAccessor> getPropertyPresenceCheckers() {
        if ( presenceCheckers == null ) {
            List<ExecutableElementAccessor> checkerList = Filters.presenceCheckMethodsIn(
                accessorNaming,
                getAllAccessors()
            );
            Map<String, ExecutableElementAccessor> modifiableCheckers = new LinkedHashMap<String,
                ExecutableElementAccessor>();
            for ( ExecutableElementAccessor checker : checkerList ) {
                modifiableCheckers.put( accessorNaming.getPropertyName( checker ), checker );
            }
            presenceCheckers = Collections.unmodifiableMap( modifiableCheckers );
        }
        return presenceCheckers;
    }

    /**
     * getPropertyWriteAccessors returns a map of the write accessors according to the CollectionMappingStrategy. These
     * accessors include:
     * <ul>
     * <li>setters, the obvious candidate :-), {@link #getSetters() }</li>
     * <li>readAccessors, for collections that do not have a setter, e.g. for JAXB generated collection attributes
     * {@link #getPropertyReadAccessors() }</li>
     * <li>adders, typically for from table generated entities, {@link #getAdders() }</li>
     * </ul>
     *
     * @param cmStrategy collection mapping strategy
     * @return an unmodifiable map of all write accessors indexed by property name
     */
    public Map<String, Accessor> getPropertyWriteAccessors( CollectionMappingStrategyPrism cmStrategy ) {
        // collect all candidate target accessors
        List<Accessor> candidates = new ArrayList<Accessor>( getSetters() );
        candidates.addAll( getAlternativeTargetAccessors() );

        Map<String, Accessor> result = new LinkedHashMap<String, Accessor>();

        for ( Accessor candidate : candidates ) {
            String targetPropertyName = accessorNaming.getPropertyName( candidate );

            Accessor readAccessor = getPropertyReadAccessors().get( targetPropertyName );

            Type preferredType = determinePreferredType( readAccessor );
            Type targetType = determineTargetType( candidate );

            // A target access is in general a setter method on the target object. However, in case of collections,
            // the current target accessor can also be a getter method.
            // The following if block, checks if the target accessor should be overruled by an add method.
            if ( cmStrategy == CollectionMappingStrategyPrism.SETTER_PREFERRED
                || cmStrategy == CollectionMappingStrategyPrism.ADDER_PREFERRED
                || cmStrategy == CollectionMappingStrategyPrism.TARGET_IMMUTABLE ) {

                // first check if there's a setter method.
                Accessor adderMethod = null;
                if ( accessorNaming.isSetterMethod( candidate )
                    // ok, the current accessor is a setter. So now the strategy determines what to use
                    && cmStrategy == CollectionMappingStrategyPrism.ADDER_PREFERRED ) {
                    adderMethod = getAdderForType( targetType, targetPropertyName );
                }
                else if ( accessorNaming.isGetterMethod( candidate ) ) {
                    // the current accessor is a getter (no setter available). But still, an add method is according
                    // to the above strategy (SETTER_PREFERRED || ADDER_PREFERRED) preferred over the getter.
                    adderMethod = getAdderForType( targetType, targetPropertyName );
                }
                if ( adderMethod != null ) {
                    // an adder has been found (according strategy) so overrule current choice.
                    candidate = adderMethod;
                }
            }
            else if ( Executables.isFieldAccessor( candidate ) && ( Executables.isFinal( candidate ) ||
                result.containsKey( targetPropertyName ) ) ) {
                // if the candidate is a field and a mapping already exists, then use that one, skip it.
                continue;
            }

            Accessor previousCandidate = result.get( targetPropertyName );
            if ( previousCandidate == null || preferredType == null || ( targetType != null
                && typeUtils.isAssignable( preferredType.getTypeMirror(), targetType.getTypeMirror() ) ) ) {
                result.put( targetPropertyName, candidate );
            }
        }

        return result;
    }

    private Type determinePreferredType(Accessor readAccessor) {
        if ( readAccessor != null ) {
            return typeFactory.getReturnType( (DeclaredType) typeMirror, readAccessor );
        }
        return null;
    }

    private Type determineTargetType(Accessor candidate) {
        Parameter parameter = typeFactory.getSingleParameter( (DeclaredType) typeMirror, candidate );
        if ( parameter != null ) {
            return parameter.getType();
        }
        else if ( accessorNaming.isGetterMethod( candidate ) || Executables.isFieldAccessor( candidate ) ) {
            return typeFactory.getReturnType( (DeclaredType) typeMirror, candidate );
        }
        return null;
    }

    private List<Accessor> getAllAccessors() {
        if ( allAccessors == null ) {
            allAccessors = Executables.getAllEnclosedAccessors( elementUtils, typeElement );
        }

        return allAccessors;
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
    private Accessor getAdderForType(Type collectionProperty, String pluralPropertyName) {

        List<Accessor> candidates = new ArrayList<Accessor>();
        if ( collectionProperty.isCollectionType ) {

            // this is a collection, so this can be done always
            TypeMirror typeArg = first( collectionProperty.determineTypeArguments( Iterable.class ) ).getTypeBound()
                .getTypeMirror();
            // now, look for a method that
            // 1) starts with add,
            // 2) and has typeArg as one and only arg
            List<Accessor> adderList = getAdders();
            for ( Accessor adder : adderList ) {
                ExecutableElement executable = adder.getExecutable();
                if ( executable == null ) {
                    // it should not be null, but to be safe
                    continue;
                }
                VariableElement arg = executable.getParameters().get( 0 );
                if ( typeUtils.isSameType( arg.asType(), typeArg ) ) {
                    candidates.add( adder );
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
            for ( Accessor candidate : candidates ) {
                String elementName = accessorNaming.getElementNameForAdder( candidate );
                if ( elementName != null && elementName.equals( Nouns.singularize( pluralPropertyName ) ) ) {
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
    private List<Accessor> getSetters() {
        if ( setters == null ) {
            setters = Collections.unmodifiableList( Filters.setterMethodsIn( accessorNaming, getAllAccessors() ) );
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
    private List<Accessor> getAdders() {
        if ( adders == null ) {
            adders = Collections.unmodifiableList( Filters.adderMethodsIn( accessorNaming, getAllAccessors() ) );
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
    private List<Accessor> getAlternativeTargetAccessors() {

        if ( alternativeTargetAccessors == null ) {

            List<Accessor> result = new ArrayList<Accessor>();
            List<Accessor> setterMethods = getSetters();
            List<Accessor> readAccessors =
                new ArrayList<Accessor>( getPropertyReadAccessors().values() );
            // All the fields are also alternative accessors
            readAccessors.addAll( Filters.fieldsIn( getAllAccessors() ) );

            // there could be a read accessor (field or  method) for a list/map that is not present as setter.
            // an accessor could substitute the setter in that case and act as setter.
            // (assuming it is initialized)
            for ( Accessor readAccessor : readAccessors ) {
                if ( isCollectionOrMap( readAccessor ) &&
                    !correspondingSetterMethodExists( readAccessor, setterMethods ) ) {
                    result.add( readAccessor );
                }
                else if ( Executables.isFieldAccessor( readAccessor ) &&
                    !correspondingSetterMethodExists( readAccessor, setterMethods ) ) {
                    result.add( readAccessor );
                }
            }

            alternativeTargetAccessors = Collections.unmodifiableList( result );
        }
        return alternativeTargetAccessors;
    }

    private boolean correspondingSetterMethodExists(Accessor getterMethod,
                                                    List<Accessor> setterMethods) {
        String getterPropertyName = accessorNaming.getPropertyName( getterMethod );

        for ( Accessor setterMethod : setterMethods ) {
            String setterPropertyName = accessorNaming.getPropertyName( setterMethod );
            if ( getterPropertyName.equals( setterPropertyName ) ) {
                return true;
            }
        }

        return false;
    }

    private boolean isCollectionOrMap(Accessor getterMethod) {
        return isCollection( getterMethod.getAccessedType() ) || isMap( getterMethod.getAccessedType() );
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
            //"'\u0000'" would have been better, but depends on platform encoding
                return "0";
        }
        if ( "double".equals( getName() ) ) {
            return "0.0d";
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
        // javadoc typemirror: "Types should be compared using the utility methods in Types. There is no guarantee
        // that any particular type will always be represented by the same object." This is true when the objects
        // are in another jar than the mapper. So the qualfiedName is a better candidate.
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((packageName == null) ? 0 : packageName.hashCode());
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
     * <li>{@code <? extends Number>}, returns Number</li>
     * <li>{@code <? super Number>}, returns Number</li>
     * <li>{@code <?>}, returns Object</li>
     * <li>{@code <T extends Number>, returns Number}</li>
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

    public boolean hasEmptyAccessibleContructor() {

        if ( this.hasEmptyAccessibleContructor == null ) {
            hasEmptyAccessibleContructor = false;
            List<ExecutableElement> constructors = ElementFilter.constructorsIn( typeElement.getEnclosedElements() );
            for ( ExecutableElement constructor : constructors ) {
                if ( !constructor.getModifiers().contains( Modifier.PRIVATE )
                    && constructor.getParameters().isEmpty() ) {
                    hasEmptyAccessibleContructor = true;
                    break;
                }
            }
        }
        return hasEmptyAccessibleContructor;
    }

    /**
     * Searches for the given superclass and collects all type arguments for the given class
     *
     * @param superclass the superclass or interface the generic type arguments are searched for
     * @return a list of type arguments or null, if superclass was not found
     */
    public List<Type> determineTypeArguments(Class<?> superclass) {
        if ( qualifiedName.equals( superclass.getName() ) ) {
            return getTypeParameters();
        }

        List<? extends TypeMirror> directSupertypes = typeUtils.directSupertypes( typeMirror );
        for ( TypeMirror supertypemirror : directSupertypes ) {
            Type supertype = typeFactory.getType( supertypemirror );
            List<Type> supertypeTypeArguments = supertype.determineTypeArguments( superclass );
            if ( supertypeTypeArguments != null ) {
                return supertypeTypeArguments;
            }
        }

        return null;
    }

    /**
     * All primitive types and their corresponding boxed types are considered native.
     * @return true when native.
     */
    public boolean isNative() {
        return NativeTypes.isNative( qualifiedName );
    }

    public boolean isLiteral() {
        return isLiteral;
    }

}
