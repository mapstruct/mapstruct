/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.common;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.NestingKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.IntersectionType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.type.WildcardType;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.SimpleTypeVisitor8;

import org.mapstruct.ap.internal.gem.CollectionMappingStrategyGem;
import org.mapstruct.ap.internal.util.AccessorNamingUtils;
import org.mapstruct.ap.internal.util.ElementUtils;
import org.mapstruct.ap.internal.util.Executables;
import org.mapstruct.ap.internal.util.Filters;
import org.mapstruct.ap.internal.util.JavaStreamConstants;
import org.mapstruct.ap.internal.util.NativeTypes;
import org.mapstruct.ap.internal.util.Nouns;
import org.mapstruct.ap.internal.util.TypeUtils;
import org.mapstruct.ap.internal.util.accessor.Accessor;
import org.mapstruct.ap.internal.util.accessor.AccessorType;
import org.mapstruct.ap.internal.util.accessor.ElementAccessor;
import org.mapstruct.ap.internal.util.accessor.MapValueAccessor;
import org.mapstruct.ap.internal.util.accessor.PresenceCheckAccessor;
import org.mapstruct.ap.internal.util.accessor.ReadAccessor;

import static java.util.Collections.emptyList;
import static org.mapstruct.ap.internal.util.Collections.first;

/**
 * Represents (a reference to) the type of a bean property, parameter etc. Types are managed per generated source file.
 * Each type corresponds to a {@link TypeMirror}, i.e. there are different instances for e.g. {@code Set<String>} and
 * {@code Set<Integer>}.
 * <p>
 * Allows for a unified handling of declared and primitive types and usage within templates. Instances are obtained
 * through {@link TypeFactory}.
 *
 * @author Gunnar Morling
 * @author Filip Hrisafov
 */
public class Type extends ModelElement implements Comparable<Type> {
    private static final Method SEALED_PERMITTED_SUBCLASSES_METHOD;

    static {
        Method permittedSubclassesMethod;
        try {
            permittedSubclassesMethod = TypeElement.class.getMethod( "getPermittedSubclasses" );
        }
        catch ( NoSuchMethodException e ) {
            permittedSubclassesMethod = null;
        }
        SEALED_PERMITTED_SUBCLASSES_METHOD = permittedSubclassesMethod;
    }

    private final TypeUtils typeUtils;
    private final ElementUtils elementUtils;
    private final TypeFactory typeFactory;
    private final AccessorNamingUtils accessorNaming;

    private final TypeMirror typeMirror;
    private final TypeElement typeElement;
    private final List<Type> typeParameters;

    private final ImplementationType implementationType;
    private final Type componentType;
    private final Type topLevelType;

    private final String packageName;
    private final String name;
    private final String nameWithTopLevelTypeName;
    private final String qualifiedName;

    private final boolean isInterface;
    private final boolean isEnumType;
    private final boolean isIterableType;
    private final boolean isCollectionType;
    private final boolean isMapType;
    private final boolean isVoid;
    private final boolean isStream;
    private final boolean isLiteral;

    private final boolean loggingVerbose;

    private final List<String> enumConstants;

    private final Map<String, String> toBeImportedTypes;
    private final Map<String, String> notToBeImportedTypes;
    private Boolean isToBeImported;

    private Map<String, ReadAccessor> readAccessors = null;
    private Map<String, PresenceCheckAccessor> presenceCheckers = null;

    private List<ExecutableElement> allMethods = null;
    private List<VariableElement> allFields = null;
    private List<Element> recordComponents = null;

    private List<Accessor> setters = null;
    private List<Accessor> adders = null;
    private List<Accessor> alternativeTargetAccessors = null;

    private Type boundingBase = null;
    private List<Type> boundTypes = null;

    private Type boxedEquivalent = null;

    private Boolean hasAccessibleConstructor;

    private final Filters filters;

    //CHECKSTYLE:OFF
    public Type(TypeUtils typeUtils, ElementUtils elementUtils, TypeFactory typeFactory,
                AccessorNamingUtils accessorNaming,
                TypeMirror typeMirror, TypeElement typeElement,
                List<Type> typeParameters, ImplementationType implementationType, Type componentType,
                String packageName, String name, String qualifiedName,
                boolean isInterface, boolean isEnumType, boolean isIterableType,
                boolean isCollectionType, boolean isMapType, boolean isStreamType,
                Map<String, String> toBeImportedTypes,
                Map<String, String> notToBeImportedTypes,
                Boolean isToBeImported,
                boolean isLiteral, boolean loggingVerbose) {

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
        this.isVoid = typeMirror.getKind() == TypeKind.VOID;
        this.isLiteral = isLiteral;

        if ( isEnumType ) {
            enumConstants = new ArrayList<>();

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

        this.isToBeImported = isToBeImported;
        this.toBeImportedTypes = toBeImportedTypes;
        this.notToBeImportedTypes = notToBeImportedTypes;
        this.filters = new Filters( accessorNaming, typeUtils, typeMirror );

        this.loggingVerbose = loggingVerbose;

        TypeElement typeElementForTopLevel;
        if ( Boolean.TRUE.equals( isToBeImported ) ) {
            // If the is to be imported is explicitly set to true then we shouldn't look for the top level type
            typeElementForTopLevel = null;
        }
        else {
            // The top level type for an array type is the top level type of the component type
            typeElementForTopLevel =
                this.componentType == null ? this.typeElement : this.componentType.getTypeElement();
        }
        this.topLevelType = topLevelType( typeElementForTopLevel, this.typeFactory );
        this.nameWithTopLevelTypeName = nameWithTopLevelTypeName( typeElementForTopLevel, this.name );
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
     * Returns a String that could be used in generated code to reference to this {@link Type}.<br>
     *  <p>
     * The first time a name is referred-to it will be marked as to be imported. For instance
     * {@code LocalDateTime} can be one of {@code java.time.LocalDateTime} and {@code org.joda.LocalDateTime})
     * <p>
     * If the {@code java.time} variant is referred to first, the {@code java.time.LocalDateTime} will be imported
     * and the {@code org.joda} variant will be referred to with its FQN.
     * <p>
     * If the type is nested and its top level type is to be imported
     * then the name including its top level type will be returned.
     *
     * @return Just the name if this {@link Type} will be imported, the name up to the top level {@link Type}
     * (if the top level type is important, otherwise the fully-qualified name.
     */
    public String createReferenceName() {
        if ( isToBeImported() ) {
            // isToBeImported() returns true for arrays.
            // Therefore, we need to check the top level type when creating the reference
            if ( isTopLevelTypeToBeImported() ) {
                return nameWithTopLevelTypeName != null ? nameWithTopLevelTypeName : name;
            }

            return name;
        }

        if ( shouldUseSimpleName() ) {
            return name;
        }

        if ( isTopLevelTypeToBeImported() && nameWithTopLevelTypeName != null) {
            return nameWithTopLevelTypeName;
        }

        return qualifiedName;
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

    public boolean isAbstract() {
        return typeElement != null && typeElement.getModifiers().contains( Modifier.ABSTRACT );
    }

    public boolean isString() {
        return String.class.getName().equals( getFullyQualifiedName() );
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

    private boolean hasStringMapSignature() {
        if ( isMapType() ) {
            List<Type> typeParameters = getTypeParameters();
            if ( typeParameters.size() == 2 && typeParameters.get( 0 ).isString() ) {
                return true;
            }
        }

        return false;
    }

    public boolean isCollectionOrMapType() {
        return isCollectionType || isMapType;
    }

    public boolean isArrayType() {
        return componentType != null;
    }

    private boolean isType(Class<?> type) {
        return type.getName().equals( getFullyQualifiedName() );
    }

    private boolean isOptionalType() {
        return isType( Optional.class ) || isType( OptionalInt.class ) || isType( OptionalDouble.class ) ||
            isType( OptionalLong.class );
    }

    public boolean isTypeVar() {
        return (typeMirror.getKind() == TypeKind.TYPEVAR);
    }

    public boolean isIntersection() {
        return typeMirror.getKind() == TypeKind.INTERSECTION;
    }

    public boolean isJavaLangType() {
        return packageName != null && packageName.startsWith( "java." );
    }

    public boolean isRecord() {
        return typeElement.getKind().name().equals( "RECORD" );
    }

    /**
     * Whether this type is a sub-type of {@link java.util.stream.Stream}.
     *
     * @return {@code true} it this type is a sub-type of {@link java.util.stream.Stream}, {@code false otherwise}
     */
    public boolean isStreamType() {
        return isStream;
    }

    /**
     * A wild card type can have two types of bounds (mutual exclusive): extends and super.
     *
     * @return true if the bound has a wild card super bound (e.g. ? super Number)
     */
    public boolean hasSuperBound() {
        boolean result = false;
        if ( typeMirror.getKind() == TypeKind.WILDCARD ) {
            WildcardType wildcardType = (WildcardType) typeMirror;
            result = wildcardType.getSuperBound() != null;
        }
        return result;
    }

    /**
     * A wild card type can have two types of bounds (mutual exclusive): extends and super.
     *
     * @return true if the bound has a wild card super bound (e.g. ? extends Number)
     */
    public boolean hasExtendsBound() {
        boolean result = false;
        if ( typeMirror.getKind() == TypeKind.WILDCARD ) {
            WildcardType wildcardType = (WildcardType) typeMirror;
            result = wildcardType.getExtendsBound() != null;
        }
        return result;
    }

    /**
     * A type variable type can have two types of bounds (mutual exclusive): lower and upper.
     *
     * Note that its use is only permitted on a definition (not on the place where its used). For instance:
     * {@code<T super Number> T map( T in)}
     *
     * @return true if the bound has a type variable lower bound (e.g. T super Number)
     */
    public boolean hasLowerBound() {
        boolean result = false;
        if ( typeMirror.getKind() == TypeKind.TYPEVAR ) {
            TypeVariable typeVarType = (TypeVariable) typeMirror;
            result = typeVarType.getLowerBound() != null;
        }
        return result;
    }

    /**
     * A type variable type can have two types of bounds (mutual exclusive): lower and upper.
     *
     * Note that its use is only permitted on a definition  (not on the place where its used). For instance:
     * {@code><T extends Number> T map( T in)}
     *
     * @return true if the bound has a type variable upper bound (e.g. T extends Number)
     */
    public boolean hasUpperBound() {
        boolean result = false;
        if ( typeMirror.getKind() == TypeKind.TYPEVAR ) {
            TypeVariable typeVarType = (TypeVariable) typeMirror;
            result = typeVarType.getUpperBound() != null;
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
        return isArrayType() ? trimSimpleClassName( qualifiedName ) : qualifiedName;
    }

    @Override
    public Set<Type> getImportTypes() {
        Set<Type> result = new HashSet<>();

        if ( getTypeMirror().getKind() == TypeKind.DECLARED ) {
            result.add( this );
        }

        if ( componentType != null ) {
            result.addAll( componentType.getImportTypes() );
        }

        if ( topLevelType != null ) {
            result.addAll( topLevelType.getImportTypes() );
        }

        for ( Type parameter : typeParameters ) {
            result.addAll( parameter.getImportTypes() );
        }

        if ( ( hasExtendsBound() || hasSuperBound() ) && getTypeBound() != null ) {
            result.addAll( getTypeBound().getImportTypes() );
        }

        return result;
    }

    protected boolean isTopLevelTypeToBeImported() {
        return topLevelType != null && topLevelType.isToBeImported();
    }

    /**
     * Whether this type is to be imported by means of an import statement in the currently generated source file
     * (it can be referenced in the generated source using its simple name) or not (referenced using the FQN).
     *
     * @return {@code true} if the type is imported, {@code false} otherwise.
     */
    public boolean isToBeImported() {
        if ( isToBeImported == null ) {
            String trimmedName = trimSimpleClassName( name );
            if ( notToBeImportedTypes.containsKey( trimmedName ) ) {
                isToBeImported = false;
                return isToBeImported;
            }
            String trimmedQualifiedName = trimSimpleClassName( qualifiedName );
            String importedType = toBeImportedTypes.get( trimmedName );

            isToBeImported = false;
            if ( importedType != null ) {
                if ( importedType.equals( trimmedQualifiedName ) ) {
                    isToBeImported = true;
                }
            }
            else if ( typeElement == null || !typeElement.getNestingKind().isNested() ) {
                toBeImportedTypes.put( trimmedName, trimmedQualifiedName );
                isToBeImported = true;
            }
        }
        return isToBeImported;
    }

    private boolean shouldUseSimpleName() {
        // Using trimSimpleClassName since the same is used in the isToBeImported()
        // to check whether notToBeImportedTypes contains it
        String trimmedName = trimSimpleClassName( name );
        String fqn = notToBeImportedTypes.get( trimmedName );
        return trimSimpleClassName( this.qualifiedName ).equals( fqn );
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
            packageName,
            name,
            qualifiedName,
            isInterface,
            isEnumType,
            isIterableType,
            isCollectionType,
            isMapType,
            isStream,
            toBeImportedTypes,
            notToBeImportedTypes,
            isToBeImported,
            isLiteral,
            loggingVerbose
        );
    }

    public Type withoutBounds() {
        if ( typeParameters.isEmpty() ) {
            return this;
        }

        List<Type> bounds = new ArrayList<>( typeParameters.size() );
        List<TypeMirror> mirrors = new ArrayList<>( typeParameters.size() );
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
            packageName,
            name,
            qualifiedName,
            isInterface,
            isEnumType,
            isIterableType,
            isCollectionType,
            isMapType,
            isStream,
            toBeImportedTypes,
            notToBeImportedTypes,
            isToBeImported,
            isLiteral,
            loggingVerbose
        );
    }

    private Type replaceGeneric(Type oldGenericType, Type newType) {
        if ( !typeParameters.contains( oldGenericType ) || newType == null ) {
            return this;
        }
        newType = newType.getBoxedEquivalent();
        TypeMirror[] replacedTypeMirrors = new TypeMirror[typeParameters.size()];
        for ( int i = 0; i < typeParameters.size(); i++ ) {
            Type typeParameter = typeParameters.get( i );
            replacedTypeMirrors[i] =
                typeParameter.equals( oldGenericType ) ? newType.typeMirror : typeParameter.typeMirror;
        }

        return typeFactory.getType( typeUtils.getDeclaredType( typeElement, replacedTypeMirrors ) );
    }

    /**
     * Whether this type is assignable to the given other type, considering the "extends / upper bounds"
     * as well.
     *
     * @param other The other type.
     *
     * @return {@code true} if and only if this type is assignable to the given other type.
     */
    public boolean isAssignableTo(Type other) {
        TypeMirror otherMirror = other.typeMirror;
        if ( otherMirror.getKind() == TypeKind.WILDCARD ) {
            otherMirror = typeUtils.erasure( other.typeMirror );
        }
        if ( TypeKind.WILDCARD == typeMirror.getKind() ) {
            return typeUtils.contains( typeMirror, otherMirror );
        }
        return typeUtils.isAssignable( typeMirror, otherMirror );
    }

    /**
     * Whether this type is raw assignable to the given other type. We can't make a verdict on typevars,
     * they need to be resolved first.
     *
     * @param other The other type.
     *
     * @return {@code true} if and only if this type is assignable to the given other type.
     */
    public boolean isRawAssignableTo(Type other) {
        if ( isTypeVar() || other.isTypeVar() ) {
            return true;
        }
        if ( equals( other ) ) {
            return true;
        }
        return typeUtils.isAssignable( typeUtils.erasure( typeMirror ), typeUtils.erasure( other.typeMirror ) );
    }

    /**
     * removes any bounds from this type.
     * @return the raw type
     */
    public Type asRawType() {
        if ( getTypeBound() != null ) {
            return typeFactory.getType( typeUtils.erasure( typeMirror ) );
        }
        else {
            return this;
        }
    }

    public ReadAccessor getReadAccessor(String propertyName, boolean allowedMapToBean) {
        if ( allowedMapToBean && hasStringMapSignature() ) {
            ExecutableElement getMethod = getAllMethods()
                .stream()
                .filter( m -> m.getSimpleName().contentEquals( "get" ) )
                .filter( m -> m.getParameters().size() == 1 )
                .findAny()
                .orElse( null );
            return new MapValueAccessor( getMethod, typeParameters.get( 1 ).getTypeMirror(), propertyName );
        }

        Map<String, ReadAccessor> readAccessors = getPropertyReadAccessors();

        return readAccessors.get( propertyName );
    }

    public PresenceCheckAccessor getPresenceChecker(String propertyName) {
        if ( hasStringMapSignature() ) {
            return PresenceCheckAccessor.mapContainsKey( propertyName );
        }

        Map<String, PresenceCheckAccessor> presenceCheckers = getPropertyPresenceCheckers();
        return presenceCheckers.get( propertyName );
    }

    /**
     * getPropertyReadAccessors
     *
     * @return an unmodifiable map of all read accessors (including 'is' for booleans), indexed by property name
     */
    public Map<String, ReadAccessor> getPropertyReadAccessors() {
        if ( readAccessors == null ) {

            Map<String, ReadAccessor> recordAccessors = filters.recordAccessorsIn( getRecordComponents() );
            Map<String, ReadAccessor> modifiableGetters = new LinkedHashMap<>(recordAccessors);

            List<ReadAccessor> getterList = filters.getterMethodsIn( getAllMethods() );
            for ( ReadAccessor getter : getterList ) {
                String simpleName = getter.getSimpleName();
                if ( recordAccessors.containsKey( simpleName ) ) {
                    // If there is already a record accessor that contains the simple name
                    // then it means that the getter is actually a record component.
                    // In that case we need to ignore it.
                    // e.g. record component named isActive.
                    // The DefaultAccessorNamingStrategy will return active as property name,
                    // but the property name is isActive, since it is a record
                    continue;
                }
                String propertyName = getPropertyName( getter );

                if ( recordAccessors.containsKey( propertyName ) ) {
                    // If there is already a record accessor, the property needs to be ignored
                    continue;
                }
                if ( modifiableGetters.containsKey( propertyName ) ) {
                    // In the DefaultAccessorNamingStrategy, this can only be the case for Booleans: isFoo() and
                    // getFoo(); The latter is preferred.
                    if ( !simpleName.startsWith( "is" ) ) {
                        modifiableGetters.put( propertyName, getter );
                    }

                }
                else {
                    modifiableGetters.put( propertyName, getter );
                }
            }

            List<ReadAccessor> fieldsList = filters.fieldsIn( getAllFields(), ReadAccessor::fromField );
            for ( ReadAccessor field : fieldsList ) {
                String propertyName = getPropertyName( field );
                // If there was no getter or is method for booleans, then resort to the field.
                // If a field was already added do not add it again.
                modifiableGetters.putIfAbsent( propertyName, field );
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
    public Map<String, PresenceCheckAccessor> getPropertyPresenceCheckers() {
        if ( presenceCheckers == null ) {
            List<ExecutableElement> checkerList = filters.presenceCheckMethodsIn( getAllMethods() );
            Map<String, PresenceCheckAccessor> modifiableCheckers = new LinkedHashMap<>();
            for ( ExecutableElement checker : checkerList ) {
                modifiableCheckers.put(
                    getPropertyName( checker ),
                    PresenceCheckAccessor.methodInvocation( checker )
                );
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
    public Map<String, Accessor> getPropertyWriteAccessors( CollectionMappingStrategyGem cmStrategy ) {
        if ( isRecord() ) {
            // Records do not have setters, so we return an empty map
            return Collections.emptyMap();
        }
        // collect all candidate target accessors
        List<Accessor> candidates = new ArrayList<>( getSetters() );
        candidates.addAll( getAlternativeTargetAccessors() );

        Map<String, Accessor> result = new LinkedHashMap<>();

        for ( Accessor candidate : candidates ) {
            String targetPropertyName = getPropertyName( candidate );

            Accessor readAccessor = getPropertyReadAccessors().get( targetPropertyName );

            Type preferredType = determinePreferredType( readAccessor );
            Type targetType = determineTargetType( candidate );

            // A target access is in general a setter method on the target object. However, in case of collections,
            // the current target accessor can also be a getter method.
            // The following if block, checks if the target accessor should be overruled by an add method.
            if ( cmStrategy == CollectionMappingStrategyGem.SETTER_PREFERRED
                || cmStrategy == CollectionMappingStrategyGem.ADDER_PREFERRED
                || cmStrategy == CollectionMappingStrategyGem.TARGET_IMMUTABLE ) {

                // first check if there's a setter method.
                Accessor adderMethod = null;
                if ( candidate.getAccessorType() == AccessorType.SETTER
                    // ok, the current accessor is a setter. So now the strategy determines what to use
                    && cmStrategy == CollectionMappingStrategyGem.ADDER_PREFERRED ) {
                    adderMethod = getAdderForType( targetType, targetPropertyName );
                }
                else if ( candidate.getAccessorType() == AccessorType.GETTER ) {
                    // the current accessor is a getter (no setter available). But still, an add method is according
                    // to the above strategy (SETTER_PREFERRED || ADDER_PREFERRED) preferred over the getter.
                    adderMethod = getAdderForType( targetType, targetPropertyName );
                }
                if ( adderMethod != null ) {
                    // an adder has been found (according strategy) so overrule current choice.
                    candidate = adderMethod;
                }

            }
            else if ( candidate.getAccessorType() == AccessorType.FIELD  && ( Executables.isFinal( candidate ) ||
                result.containsKey( targetPropertyName ) ) ) {
                // if the candidate is a field and a mapping already exists, then use that one, skip it.
                continue;
            }

            if ( candidate.getAccessorType() == AccessorType.GETTER ) {
                // When the candidate is a getter then it can't be used in the following cases:
                // 1. The collection mapping strategy is target immutable
                // 2. The target type is a stream (streams are immutable)
                if ( cmStrategy == CollectionMappingStrategyGem.TARGET_IMMUTABLE ||
                    targetType != null && targetType.isStreamType() ) {
                    continue;
                }
            }

            Accessor previousCandidate = result.get( targetPropertyName );
            if ( previousCandidate == null || preferredType == null || ( targetType != null
                && typeUtils.isAssignable( preferredType.getTypeMirror(), targetType.getTypeMirror() ) ) ) {
                result.put( targetPropertyName, candidate );
            }
        }

        return result;
    }

    public List<Element> getRecordComponents() {
        if ( recordComponents == null ) {
            recordComponents = nullSafeTypeElementListConversion( filters::recordComponentsIn );
        }

        return recordComponents;
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
        else if ( candidate.getAccessorType() == AccessorType.GETTER
                        || candidate.getAccessorType().isFieldAssignment() ) {
            return typeFactory.getReturnType( (DeclaredType) typeMirror, candidate );
        }
        return null;
    }

    private List<ExecutableElement> getAllMethods() {
        if ( allMethods == null ) {
            allMethods = nullSafeTypeElementListConversion( elementUtils::getAllEnclosedExecutableElements );
        }

        return allMethods;
    }

    private List<VariableElement> getAllFields() {
        if ( allFields == null ) {
            allFields = nullSafeTypeElementListConversion( elementUtils::getAllEnclosedFields );
        }

        return allFields;
    }

    private <T> List<T> nullSafeTypeElementListConversion(Function<TypeElement, List<T>> conversionFunction) {
        if ( typeElement != null ) {
            return conversionFunction.apply( typeElement );
        }

        return Collections.emptyList();
    }

    private String getPropertyName(Accessor accessor ) {
        Element accessorElement = accessor.getElement();
        if ( accessorElement instanceof ExecutableElement ) {
            return getPropertyName( (ExecutableElement) accessorElement );
        }
        else {
            return accessor.getSimpleName();
        }
    }

    private String getPropertyName(ExecutableElement element) {
        return accessorNaming.getPropertyName( element );
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

        List<Accessor> candidates;

        if ( collectionProperty.isCollectionType() ) {
            candidates = getAccessorCandidates( collectionProperty, Iterable.class );
        }
        else if ( collectionProperty.isStreamType() ) {
            candidates = getAccessorCandidates( collectionProperty, Stream.class );
        }
        else {
            return null;
        }

        if ( candidates.isEmpty() ) {
            return null;
        }

        if ( candidates.size() == 1 ) {
            return candidates.get( 0 );
        }

        for ( Accessor candidate : candidates ) {
            String elementName = accessorNaming.getElementNameForAdder( candidate );
            if ( elementName != null && elementName.equals( Nouns.singularize( pluralPropertyName ) ) ) {
                return candidate;
            }
        }

        return null;
    }

    /**
     * Returns all accessor candidates that start with "add" and have exactly one argument
     * whose type matches the collection or stream property's type argument.
     *
     * @param property the collection or stream property
     * @param superclass the superclass to use for type argument lookup
     *
     * @return accessor candidates
     */
    private List<Accessor> getAccessorCandidates(Type property, Class<?> superclass) {
        TypeMirror typeArg = first( property.determineTypeArguments( superclass ) ).getTypeBound().getTypeMirror();
        // now, look for a method that
        // 1) starts with add,
        // 2) and has typeArg as one and only arg
        List<Accessor> adderList = getAdders();
        List<Accessor> candidateList = new ArrayList<>();
        for ( Accessor adder : adderList ) {
            TypeMirror adderParameterType = determineTargetType( adder ).getTypeMirror();
            if ( typeUtils.isSameType( boxed( adderParameterType ), boxed( typeArg ) ) ) {
                candidateList.add( adder );
            }
        }
        return candidateList;
    }

    private TypeMirror boxed(TypeMirror possiblePrimitive) {
        if ( possiblePrimitive.getKind().isPrimitive() ) {
            return typeUtils.boxedClass( (PrimitiveType) possiblePrimitive ).asType();
        }
        else {
            return possiblePrimitive;
        }
    }

    /**
     * getSetters
     *
     * @return an unmodifiable list of all setters
     */
    private List<Accessor> getSetters() {
        if ( setters == null ) {
            setters = Collections.unmodifiableList( filters.setterMethodsIn( getAllMethods() ) );
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
            adders = Collections.unmodifiableList( filters.adderMethodsIn( getAllMethods() ) );
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
        if ( alternativeTargetAccessors != null ) {
            return alternativeTargetAccessors;
        }

        if ( isRecord() ) {
            alternativeTargetAccessors = Collections.emptyList();
        }

        if ( alternativeTargetAccessors == null ) {

            List<Accessor> result = new ArrayList<>();
            List<Accessor> setterMethods = getSetters();
            List<Accessor> readAccessors = new ArrayList<>( getPropertyReadAccessors().values() );
            // All the fields are also alternative accessors
            readAccessors.addAll( filters.fieldsIn( getAllFields(), ElementAccessor::new ) );

            // there could be a read accessor (field or  method) for a list/map that is not present as setter.
            // an accessor could substitute the setter in that case and act as setter.
            // (assuming it is initialized)
            for ( Accessor readAccessor : readAccessors ) {
                if ( isCollectionOrMapOrStream( readAccessor ) &&
                    !correspondingSetterMethodExists( readAccessor, setterMethods ) ) {
                    result.add( readAccessor );
                }
                else if ( readAccessor.getAccessorType() == AccessorType.FIELD &&
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
        String getterPropertyName = getPropertyName( getterMethod );

        for ( Accessor setterMethod : setterMethods ) {
            String setterPropertyName = getPropertyName( setterMethod );
            if ( getterPropertyName.equals( setterPropertyName ) ) {
                return true;
            }
        }

        return false;
    }

    private boolean isCollectionOrMapOrStream(Accessor getterMethod) {
        return isCollection( getterMethod.getAccessedType() ) || isMap( getterMethod.getAccessedType() ) ||
            isStream( getterMethod.getAccessedType() );
    }

    private boolean isCollection(TypeMirror candidate) {
        return isSubType( candidate, Collection.class );
    }

    private boolean isStream(TypeMirror candidate) {
        TypeElement streamTypeElement = elementUtils.getTypeElement( JavaStreamConstants.STREAM_FQN );
        TypeMirror streamType = streamTypeElement == null ? null : typeUtils.erasure( streamTypeElement.asType() );
        return streamType != null && typeUtils.isSubtypeErased( candidate, streamType );
    }

    private boolean isMap(TypeMirror candidate) {
        return isSubType( candidate, Map.class );
    }

    private boolean isSubType(TypeMirror candidate, Class<?> clazz) {
        String className = clazz.getCanonicalName();
        TypeMirror classType = typeUtils.erasure( elementUtils.getTypeElement( className ).asType() );
        return typeUtils.isSubtypeErased( candidate, classType );
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
        if ( isOptionalType() ) {
            return createReferenceName() + ".empty()";
        }

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

    public String getSensibleDefault() {
        if ( isPrimitive() ) {
            return getNull();
        }
        else if ( "String".equals( getName() ) ) {
            return "\"\"";
        }
        else {
            if ( isNative() ) {
                // must be boxed, since primitive is already checked
                return typeFactory.getType( typeUtils.unboxedType( typeMirror ) ).getNull();
            }
        }
        return null;
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

        if ( this.isWildCardBoundByTypeVar() && other.isWildCardBoundByTypeVar() ) {
            return  ( this.hasExtendsBound() == this.hasExtendsBound()
                || this.hasSuperBound() == this.hasSuperBound() )
                && typeUtils.isSameType( getTypeBound().getTypeMirror(), other.getTypeBound().getTypeMirror() );
        }
        else {
            return typeUtils.isSameType( typeMirror, other.typeMirror );
        }
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
     * @return a string representation of the type for use in messages
     */
    public String describe() {
        if ( loggingVerbose ) {
            return toString();
        }
        else {
            // name allows for inner classes
            String name = getFullyQualifiedName().replaceFirst( "^" + getPackageName() + ".", "" );
            List<Type> typeParams = getTypeParameters();
            if ( typeParams.isEmpty() ) {
                return name;
            }
            else {
                String params = typeParams.stream().map( Type::describe ).collect( Collectors.joining( "," ) );
                return String.format( "%s<%s>", name, params );
            }
        }
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

    public List<Type> getTypeBounds() {
        if ( this.boundTypes != null ) {
            return boundTypes;
        }
        Type bound = getTypeBound();
        if ( bound == null ) {
            this.boundTypes = Collections.emptyList();
        }
        else if ( !bound.isIntersection() ) {
            this.boundTypes = Collections.singletonList( bound );
        }
        else {
            List<? extends TypeMirror> bounds = ( (IntersectionType) bound.typeMirror ).getBounds();
            this.boundTypes = new ArrayList<>( bounds.size() );
            for ( TypeMirror mirror : bounds ) {
                boundTypes.add( typeFactory.getType( mirror ) );
            }
        }

        return this.boundTypes;

    }

    public boolean hasAccessibleConstructor() {
        if ( hasAccessibleConstructor == null ) {
            hasAccessibleConstructor = false;
            List<ExecutableElement> constructors = ElementFilter.constructorsIn( typeElement.getEnclosedElements() );
            for ( ExecutableElement constructor : constructors ) {
                if ( !constructor.getModifiers().contains( Modifier.PRIVATE ) ) {
                    hasAccessibleConstructor = true;
                    break;
                }
            }
        }
        return hasAccessibleConstructor;
    }

    /**
     * Returns the direct supertypes of a type.  The interface types, if any,
     * will appear last in the list.
     *
     * @return the direct supertypes, or an empty list if none
     */
    public List<Type> getDirectSuperTypes() {
        return typeUtils.directSupertypes( typeMirror )
            .stream()
            .map( typeFactory::getType )
            .collect( Collectors.toList() );
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

    /**
     * Steps through the declaredType in order to find a match for this typeVar Type. It aligns with
     * the provided parameterized type where this typeVar type is used.<br>
     * <br>
     * For example:<pre>
     * {@code
     * this: T
     * declaredType: JAXBElement<String>
     * parameterizedType: JAXBElement<T>
     * result: String
     *
     *
     * this: T, T[] or ? extends T,
     * declaredType: E.g. Callable<? extends T>
     * parameterizedType: Callable<BigDecimal>
     * return: BigDecimal
     * }
     * </pre>
     *
     * @param declared the type
     * @param parameterized the parameterized type
     *
     * @return - the same type when this is not a type var in the broadest sense (T, T[], or ? extends T)<br>
     *         - the matching parameter in the parameterized type when this is a type var when found<br>
     *         - null in all other cases
     */
    public ResolvedPair resolveParameterToType(Type declared, Type parameterized) {
        if ( isTypeVar() || isArrayTypeVar() || isWildCardBoundByTypeVar() ) {
            TypeVarMatcher typeVarMatcher = new TypeVarMatcher( typeFactory, typeUtils, this );
            return typeVarMatcher.visit( parameterized.getTypeMirror(), declared );
        }
        return new ResolvedPair( this, this );
    }

    /**
     * Resolves generic types using the declared and parameterized types as input.<br>
     * <br>
     * For example:
     * <pre>
     * {@code
     * this: T
     * declaredType: JAXBElement<T>
     * parameterizedType: JAXBElement<Integer>
     * result: Integer
     *
     * this: List<T>
     * declaredType: JAXBElement<T>
     * parameterizedType: JAXBElement<Integer>
     * result: List<Integer>
     *
     * this: List<? extends T>
     * declaredType: JAXBElement<? extends T>
     * parameterizedType: JAXBElement<BigDecimal>
     * result: List<BigDecimal>
     *
     * this: List<Optional<T>>
     * declaredType: JAXBElement<T>
     * parameterizedType: JAXBElement<BigDecimal>
     * result: List<Optional<BigDecimal>>
     * }
     * </pre>
     * It also works for partial matching.<br>
     * <br>
     * For example:
     * <pre>
     * {@code
     * this: Map<K, V>
     * declaredType: JAXBElement<K>
     * parameterizedType: JAXBElement<BigDecimal>
     * result: Map<BigDecimal, V>
     * }
     * </pre>
     * It also works with multiple parameters at both sides.<br>
     * <br>
     * For example when reversing Key/Value for a Map:
     * <pre>
     * {@code
     * this: Map<KEY, VALUE>
     * declaredType: HashMap<VALUE, KEY>
     * parameterizedType: HashMap<BigDecimal, String>
     * result: Map<String, BigDecimal>
     * }
     * </pre>
     *
     * Mismatch result examples:
     * <pre>
     * {@code
     * this: T
     * declaredType: JAXBElement<Y>
     * parameterizedType: JAXBElement<Integer>
     * result: null
     *
     * this: List<T>
     * declaredType: JAXBElement<Y>
     * parameterizedType: JAXBElement<Integer>
     * result: List<T>
     * }
     * </pre>
     *
     * @param declared the type
     * @param parameterized the parameterized type
     *
     * @return - the result of {@link #resolveParameterToType(Type, Type)} when this type itself is a type var.<br>
     *         - the type but then with the matching type parameters replaced.<br>
     *         - the same type when this type does not contain matching type parameters.
     */
    public Type resolveGenericTypeParameters(Type declared, Type parameterized) {
        if ( isTypeVar() || isArrayTypeVar() || isWildCardBoundByTypeVar() ) {
            return resolveParameterToType( declared, parameterized ).getMatch();
        }
        Type resultType = this;
        for ( Type generic : getTypeParameters() ) {
            if ( generic.isTypeVar() || generic.isArrayTypeVar() || generic.isWildCardBoundByTypeVar() ) {
                ResolvedPair resolveParameterToType = generic.resolveParameterToType( declared, parameterized );
                resultType = resultType.replaceGeneric( generic, resolveParameterToType.getMatch() );
            }
            else {
                Type replacementType = generic.resolveParameterToType( declared, parameterized ).getMatch();
                resultType = resultType.replaceGeneric( generic, replacementType );
            }
        }
        return resultType;
    }

    public boolean isWildCardBoundByTypeVar() {
        return ( hasExtendsBound() || hasSuperBound() ) && getTypeBound().isTypeVar();
    }

    public boolean isArrayTypeVar() {
        return  isArrayType() && getComponentType().isTypeVar();
    }

    private static class TypeVarMatcher extends SimpleTypeVisitor8<ResolvedPair, Type> {

        private final TypeFactory typeFactory;
        private final Type typeToMatch;
        private final TypeUtils types;

        /**
         * @param typeFactory factory
         * @param types type utils
         * @param typeToMatch the typeVar or wildcard with typeVar bound
         */
        TypeVarMatcher(TypeFactory typeFactory, TypeUtils types, Type typeToMatch) {
            super( new ResolvedPair( typeToMatch, null ) );
            this.typeFactory = typeFactory;
            this.typeToMatch = typeToMatch;
            this.types = types;
        }

        @Override
        public ResolvedPair visitTypeVariable(TypeVariable parameterized, Type declared) {
            if ( typeToMatch.isTypeVar() && types.isSameType( parameterized, typeToMatch.getTypeMirror() ) ) {
                return new ResolvedPair(  typeFactory.getType( parameterized ), declared );
            }
            return super.DEFAULT_VALUE;
        }

        /**
         * If ? extends SomeTime equals the boundary set in typeVarToMatch (NOTE: you can't compare the wildcard itself)
         * then return a result;
          */
        @Override
        public ResolvedPair visitWildcard(WildcardType parameterized, Type declared) {
            if ( typeToMatch.hasExtendsBound() && parameterized.getExtendsBound() != null
                && types.isSameType( typeToMatch.getTypeBound().getTypeMirror(), parameterized.getExtendsBound() ) ) {
                return new ResolvedPair( typeToMatch, declared);
            }
            else if ( typeToMatch.hasSuperBound() && parameterized.getSuperBound() != null
                && types.isSameType( typeToMatch.getTypeBound().getTypeMirror(), parameterized.getSuperBound() ) ) {
                return new ResolvedPair( typeToMatch, declared);
            }
            if ( parameterized.getExtendsBound() != null ) {
                ResolvedPair match = visit( parameterized.getExtendsBound(), declared );
                if ( match.match != null ) {
                    return new ResolvedPair( typeFactory.getType( parameterized ), declared );
                }
            }
            else if (parameterized.getSuperBound() != null ) {
                ResolvedPair match = visit( parameterized.getSuperBound(), declared );
                if ( match.match != null ) {
                    return new ResolvedPair( typeFactory.getType( parameterized ), declared );
                }

            }
            return super.DEFAULT_VALUE;
        }

        @Override
        public ResolvedPair visitArray(ArrayType parameterized, Type declared) {
            if ( types.isSameType( parameterized.getComponentType(), typeToMatch.getTypeMirror() ) ) {
                return new ResolvedPair( typeFactory.getType( parameterized ), declared );
            }
            if ( declared.isArrayType() ) {
                return visit( parameterized.getComponentType(), declared.getComponentType() );
            }
            return super.DEFAULT_VALUE;
        }

        @Override
        public ResolvedPair visitDeclared(DeclaredType parameterized, Type declared) {

            List<ResolvedPair> results = new ArrayList<>(  );
            if ( parameterized.getTypeArguments().isEmpty() ) {
                return super.DEFAULT_VALUE;
            }
            else if ( types.isSameType( types.erasure( parameterized ), types.erasure( declared.getTypeMirror() ) ) ) {
                // We can't assume that the type args are the same
                // e.g. List<T> is assignable to Object
                if ( parameterized.getTypeArguments().size() != declared.getTypeParameters().size() ) {
                    return super.visitDeclared( parameterized, declared );
                }

                // only possible to compare parameters when the types are exactly the same
                for ( int i = 0; i < parameterized.getTypeArguments().size(); i++ ) {
                    TypeMirror parameterizedTypeArg = parameterized.getTypeArguments().get( i );
                    Type declaredTypeArg = declared.getTypeParameters().get( i );
                    ResolvedPair result = visit( parameterizedTypeArg, declaredTypeArg );
                    if ( result != super.DEFAULT_VALUE ) {
                        results.add( result );
                    }
                }
            }
            else {
                // Also check whether the implemented interfaces are parameterized
                for ( Type declaredSuperType : declared.getDirectSuperTypes() ) {
                    if ( Object.class.getName().equals( declaredSuperType.getFullyQualifiedName() ) ) {
                        continue;
                    }
                    ResolvedPair result = visitDeclared( parameterized, declaredSuperType );
                    if ( result != super.DEFAULT_VALUE  ) {
                        results.add( result );
                    }
                }

                for ( TypeMirror parameterizedSuper : types.directSupertypes( parameterized ) ) {
                    if ( isJavaLangObject( parameterizedSuper ) ) {
                        continue;
                    }
                    ResolvedPair result = visitDeclared( (DeclaredType) parameterizedSuper, declared );
                    if ( result != super.DEFAULT_VALUE  ) {
                        results.add( result );
                    }
                }
            }
            if ( results.isEmpty() ) {
                return super.DEFAULT_VALUE;
            }
            else {
                return results.stream().allMatch( results.get( 0 )::equals ) ? results.get( 0 ) : super.DEFAULT_VALUE;
            }
        }

        private boolean isJavaLangObject(TypeMirror type) {
            if ( type instanceof DeclaredType ) {
                return ( (TypeElement) ( (DeclaredType) type ).asElement() ).getQualifiedName()
                                                                            .contentEquals( Object.class.getName() );
            }
            return false;
        }
    }

    /**
     * Reflects any Resolved Pair, examples are
     * T, String
     * ? extends T, BigDecimal
     * T[], Integer[]
     */
    public static class ResolvedPair {

        public ResolvedPair(Type parameter, Type match) {
            this.parameter = parameter;
            this.match = match;
        }

        /**
         * parameter, e.g. T, ? extends T or T[]
         */
        private Type parameter;

        /**
         * match, e.g. String, BigDecimal, Integer[]
         */
        private Type match;

        public Type getParameter() {
            return parameter;
        }

        public Type getMatch() {
            return match;
        }

        @Override
        public boolean equals(Object o) {
            if ( this == o ) {
                return true;
            }
            if ( o == null || getClass() != o.getClass() ) {
                return false;
            }
            ResolvedPair that = (ResolvedPair) o;
            return Objects.equals( parameter, that.parameter ) && Objects.equals( match, that.match );
        }

        @Override
        public int hashCode() {
            return Objects.hash( parameter );
        }
    }

    /**
     * Gets the boxed equivalent type if the type is primitive, int will return Integer
     *
     * @return boxed equivalent
     */
    public Type getBoxedEquivalent() {
        if ( boxedEquivalent != null ) {
            return boxedEquivalent;
        }
        else if ( isPrimitive() ) {
            boxedEquivalent = typeFactory.getType( typeUtils.boxedClass( (PrimitiveType) typeMirror ) );
            return boxedEquivalent;
        }
        return this;
    }

    /**
     * It strips all the {@code []} from the {@code className}.
     *
     * E.g.
     * <pre>
     *     trimSimpleClassName("String[][][]") -> "String"
     *     trimSimpleClassName("String[]") -> "String"
     * </pre>
     *
     * @param className that needs to be trimmed
     *
     * @return the trimmed {@code className}, or {@code null} if the {@code className} was {@code null}
     */
    private String trimSimpleClassName(String className) {
        if ( className == null ) {
            return null;
        }
        String trimmedClassName = className;
        while ( trimmedClassName.endsWith( "[]" ) ) {
            trimmedClassName = trimmedClassName.substring( 0, trimmedClassName.length() - 2 );
        }
        return trimmedClassName;
    }

    private static String nameWithTopLevelTypeName(TypeElement element, String name) {
        if ( element == null ) {
            return null;
        }
        if ( !element.getNestingKind().isNested() ) {
            return name;
        }

        Deque<CharSequence> elements = new ArrayDeque<>();
        elements.addFirst( name );
        Element parent = element.getEnclosingElement();
        while ( parent != null && parent.getKind() != ElementKind.PACKAGE ) {
            elements.addFirst( parent.getSimpleName() );
            parent = parent.getEnclosingElement();
        }

        return String.join( ".", elements );
    }

    private static Type topLevelType(TypeElement typeElement, TypeFactory typeFactory) {
        if ( typeElement == null || typeElement.getNestingKind() == NestingKind.TOP_LEVEL ) {
            return null;
        }

        Element parent = typeElement.getEnclosingElement();
        while ( parent != null ) {
            if ( parent.getEnclosingElement() != null &&
                parent.getEnclosingElement().getKind() == ElementKind.PACKAGE ) {
                break;
            }
            parent = parent.getEnclosingElement();
        }
        return parent == null ? null : typeFactory.getType( parent.asType() );
    }

    public boolean isEnumSet() {
        return "java.util.EnumSet".equals( getFullyQualifiedName() );
    }

    /**
     * return true if this type is a java 17+ sealed class
     */
    public boolean isSealed() {
        return typeElement.getModifiers().stream().map( Modifier::name ).anyMatch( "SEALED"::equals );
    }

    /**
     * return the list of permitted TypeMirrors for the java 17+ sealed class
     */
    @SuppressWarnings( "unchecked" )
    public List<? extends TypeMirror> getPermittedSubclasses() {
        if (SEALED_PERMITTED_SUBCLASSES_METHOD == null) {
            return emptyList();
        }
        try {
            return (List<? extends TypeMirror>) SEALED_PERMITTED_SUBCLASSES_METHOD.invoke( typeElement );
        }
        catch ( IllegalAccessException | IllegalArgumentException | InvocationTargetException e ) {
            return emptyList();
        }
    }

}
