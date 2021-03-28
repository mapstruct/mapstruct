/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.type.WildcardType;
import org.mapstruct.ap.internal.util.ElementUtils;
import org.mapstruct.ap.internal.util.TypeUtils;

import org.mapstruct.ap.internal.gem.BuilderGem;
import org.mapstruct.ap.internal.util.AnnotationProcessingException;
import org.mapstruct.ap.internal.util.Collections;
import org.mapstruct.ap.internal.util.Extractor;
import org.mapstruct.ap.internal.util.FormattingMessager;
import org.mapstruct.ap.internal.util.JavaStreamConstants;
import org.mapstruct.ap.internal.util.Message;
import org.mapstruct.ap.internal.util.NativeTypes;
import org.mapstruct.ap.internal.util.RoundContext;
import org.mapstruct.ap.internal.util.Strings;
import org.mapstruct.ap.internal.util.accessor.Accessor;
import org.mapstruct.ap.spi.AstModifyingAnnotationProcessor;
import org.mapstruct.ap.spi.BuilderInfo;
import org.mapstruct.ap.spi.MoreThanOneBuilderCreationMethodException;
import org.mapstruct.ap.spi.TypeHierarchyErroneousException;

import static org.mapstruct.ap.internal.model.common.ImplementationType.withDefaultConstructor;
import static org.mapstruct.ap.internal.model.common.ImplementationType.withInitialCapacity;
import static org.mapstruct.ap.internal.model.common.ImplementationType.withLoadFactorAdjustment;

/**
 * Factory creating {@link Type} instances.
 *
 * @author Gunnar Morling
 */
public class TypeFactory {

    private static final Extractor<BuilderInfo, String> BUILDER_INFO_CREATION_METHOD_EXTRACTOR =
        builderInfo -> {
            ExecutableElement builderCreationMethod = builderInfo.getBuilderCreationMethod();

            StringBuilder sb = new StringBuilder( builderCreationMethod.getSimpleName() );

            sb.append( '(' );
            for ( VariableElement parameter : builderCreationMethod.getParameters() ) {
                sb.append( parameter );
            }

            sb.append( ')' );
            return sb.toString();
        };

    private final ElementUtils elementUtils;
    private final TypeUtils typeUtils;
    private final FormattingMessager messager;
    private final RoundContext roundContext;

    private final TypeMirror iterableType;
    private final TypeMirror collectionType;
    private final TypeMirror mapType;
    private final TypeMirror streamType;

    private final Map<String, ImplementationType> implementationTypes = new HashMap<>();
    private final Map<String, String> toBeImportedTypes = new HashMap<>();
    private final Map<String, String> notToBeImportedTypes;

    private final boolean loggingVerbose;

    public TypeFactory(ElementUtils elementUtils, TypeUtils typeUtils, FormattingMessager messager,
                       RoundContext roundContext, Map<String, String> notToBeImportedTypes, boolean loggingVerbose) {
        this.elementUtils = elementUtils;
        this.typeUtils = typeUtils;
        this.messager = messager;
        this.roundContext = roundContext;
        this.notToBeImportedTypes = notToBeImportedTypes;

        iterableType = typeUtils.erasure( elementUtils.getTypeElement( Iterable.class.getCanonicalName() ).asType() );
        collectionType =
            typeUtils.erasure( elementUtils.getTypeElement( Collection.class.getCanonicalName() ).asType() );
        mapType = typeUtils.erasure( elementUtils.getTypeElement( Map.class.getCanonicalName() ).asType() );
        TypeElement streamTypeElement = elementUtils.getTypeElement( JavaStreamConstants.STREAM_FQN );
        streamType = streamTypeElement == null ? null : typeUtils.erasure( streamTypeElement.asType() );

        implementationTypes.put( Iterable.class.getName(), withInitialCapacity( getType( ArrayList.class ) ) );
        implementationTypes.put( Collection.class.getName(), withInitialCapacity( getType( ArrayList.class ) ) );
        implementationTypes.put( List.class.getName(), withInitialCapacity( getType( ArrayList.class ) ) );

        implementationTypes.put( Set.class.getName(), withLoadFactorAdjustment( getType( HashSet.class ) ) );
        implementationTypes.put( SortedSet.class.getName(), withDefaultConstructor( getType( TreeSet.class ) ) );
        implementationTypes.put( NavigableSet.class.getName(), withDefaultConstructor( getType( TreeSet.class ) ) );

        implementationTypes.put( Map.class.getName(), withLoadFactorAdjustment( getType( HashMap.class ) ) );
        implementationTypes.put( SortedMap.class.getName(), withDefaultConstructor( getType( TreeMap.class ) ) );
        implementationTypes.put( NavigableMap.class.getName(), withDefaultConstructor( getType( TreeMap.class ) ) );
        implementationTypes.put(
            ConcurrentMap.class.getName(),
            withLoadFactorAdjustment( getType( ConcurrentHashMap.class ) )
        );
        implementationTypes.put(
            ConcurrentNavigableMap.class.getName(),
            withDefaultConstructor( getType( ConcurrentSkipListMap.class ) )
        );

        this.loggingVerbose = loggingVerbose;
    }

    public Type getTypeForLiteral(Class<?> type) {
        return type.isPrimitive() ? getType( getPrimitiveType( type ), true )
            : getType( type.getCanonicalName(), true );
    }

    public Type getType(Class<?> type) {
        return type.isPrimitive() ? getType( getPrimitiveType( type ) ) : getType( type.getCanonicalName() );
    }

    public Type getType(String canonicalName) {
        return getType( canonicalName, false );
    }

    private Type getType(String canonicalName, boolean isLiteral) {
        TypeElement typeElement = elementUtils.getTypeElement( canonicalName );

        if ( typeElement == null ) {
            throw new AnnotationProcessingException(
                "Couldn't find type " + canonicalName + ". Are you missing a dependency on your classpath?"
            );
        }

        return getType( typeElement, isLiteral );
    }

    /**
     * Determines if the type with the given full qualified name is part of the classpath
     *
     * @param canonicalName Name of the type to be checked for availability
     * @return true if the type with the given full qualified name is part of the classpath.
     */
    public boolean isTypeAvailable(String canonicalName) {
        return null != elementUtils.getTypeElement( canonicalName );
    }

    public Type getWrappedType(Type type ) {
        Type result = type;
        if ( type.isPrimitive() ) {
            PrimitiveType typeMirror = (PrimitiveType) type.getTypeMirror();
            result = getType( typeUtils.boxedClass( typeMirror ) );
        }
        return result;
    }

    public Type getType(TypeElement typeElement) {
        return getType( typeElement.asType(), false );
    }

    private Type getType(TypeElement typeElement, boolean isLiteral) {
        return getType( typeElement.asType(), isLiteral );
    }

    public Type getType(TypeMirror mirror) {
        return getType( mirror, false );
    }

    private Type getType(TypeMirror mirror, boolean isLiteral) {
        if ( !canBeProcessed( mirror ) ) {
            throw new TypeHierarchyErroneousException( mirror );
        }

        ImplementationType implementationType = getImplementationType( mirror );

        boolean isIterableType = typeUtils.isSubtypeErased( mirror, iterableType );
        boolean isCollectionType = typeUtils.isSubtypeErased( mirror, collectionType );
        boolean isMapType = typeUtils.isSubtypeErased( mirror, mapType );
        boolean isStreamType = streamType != null && typeUtils.isSubtypeErased( mirror, streamType );

        boolean isEnumType;
        boolean isInterface;
        String name;
        String packageName;
        String qualifiedName;
        TypeElement typeElement;
        Type componentType;
        Boolean toBeImported = null;

        if ( mirror.getKind() == TypeKind.DECLARED ) {
            DeclaredType declaredType = (DeclaredType) mirror;

            isEnumType = declaredType.asElement().getKind() == ElementKind.ENUM;
            isInterface = declaredType.asElement().getKind() == ElementKind.INTERFACE;
            name = declaredType.asElement().getSimpleName().toString();

            typeElement = (TypeElement) declaredType.asElement();

            if ( typeElement != null ) {
                packageName = elementUtils.getPackageOf( typeElement ).getQualifiedName().toString();
                qualifiedName = typeElement.getQualifiedName().toString();
            }
            else {
                packageName = null;
                qualifiedName = name;
            }

            componentType = null;
        }
        else if ( mirror.getKind() == TypeKind.ARRAY ) {
            TypeMirror componentTypeMirror = getComponentType( mirror );
            StringBuilder builder = new StringBuilder("[]");

            while ( componentTypeMirror.getKind() == TypeKind.ARRAY ) {
                componentTypeMirror = getComponentType( componentTypeMirror );
                builder.append( "[]" );
            }

            if ( componentTypeMirror.getKind() == TypeKind.DECLARED ) {
                DeclaredType declaredType = (DeclaredType) componentTypeMirror;
                TypeElement componentTypeElement = (TypeElement) declaredType.asElement();

                String arraySuffix = builder.toString();
                name = componentTypeElement.getSimpleName().toString() + arraySuffix;
                packageName = elementUtils.getPackageOf( componentTypeElement ).getQualifiedName().toString();
                qualifiedName = componentTypeElement.getQualifiedName().toString() + arraySuffix;
            }
            else if (componentTypeMirror.getKind().isPrimitive()) {
                // When the component type is primitive and is annotated with ElementType.TYPE_USE then
                // the typeMirror#toString returns (@CustomAnnotation :: byte) for the javac compiler
                name = NativeTypes.getName( componentTypeMirror.getKind() ) + builder.toString();
                packageName = null;
                // for primitive types only name (e.g. byte, short..) required as qualified name
                qualifiedName = name;
                toBeImported = false;
            }
            else {
                name = mirror.toString();
                packageName = null;
                qualifiedName = name;
                toBeImported = false;
            }

            isEnumType = false;
            isInterface = false;
            typeElement = null;
            componentType = getType( getComponentType( mirror ) );
        }
        else {
            isEnumType = false;
            isInterface = false;
            // When the component type is primitive and is annotated with ElementType.TYPE_USE then
            // the typeMirror#toString returns (@CustomAnnotation :: byte) for the javac compiler
            name = mirror.getKind().isPrimitive() ? NativeTypes.getName( mirror.getKind() ) : mirror.toString();
            packageName = null;
            qualifiedName = name;
            typeElement = null;
            componentType = null;
            toBeImported = false;
        }

        return new Type(
            typeUtils, elementUtils, this,
            roundContext.getAnnotationProcessorContext().getAccessorNaming(),
            mirror,
            typeElement,
            getTypeParameters( mirror, false ),
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
            isStreamType,
            toBeImportedTypes,
            notToBeImportedTypes,
            toBeImported,
            isLiteral,
            loggingVerbose
        );
    }

    /**
     * Returns the Type that represents the declared Class type of the given type. For primitive types, the boxed class
     * will be used. Examples:
     * <ul>
     * <li>If type represents {@code java.lang.Integer}, it will return the type that represents {@code Class<Integer>}.
     * </li>
     * <li>If type represents {@code int}, it will return the type that represents {@code Class<Integer>}.</li>
     * </ul>
     *
     * @param type the type to return the declared class type for
     * @return the type representing {@code Class<type>}.
     */
    public Type classTypeOf(Type type) {
        TypeMirror typeToUse;
        if ( type.isVoid() ) {
            return null;
        }
        else if ( type.isPrimitive() ) {
            typeToUse = typeUtils.boxedClass( (PrimitiveType) type.getTypeMirror() ).asType();
        }
        else {
            typeToUse = type.getTypeMirror();
        }

        return getType( typeUtils.getDeclaredType( elementUtils.getTypeElement( "java.lang.Class" ), typeToUse ) );
    }

    /**
     * Get the ExecutableType for given method as part of usedMapper. Possibly parameterized types in method declaration
     * will be evaluated to concrete types then.
     *
     * <b>IMPORTANT:</b> This should only be used from the Processors, as they are operating over executable elements.
     * The internals should not be using this function and should not be using the {@link ExecutableElement} directly.
     *
     * @param includingType the type on which's scope the method type shall be evaluated
     * @param method the method
     * @return the ExecutableType representing the method as part of usedMapper
     */
    public ExecutableType getMethodType(DeclaredType includingType, ExecutableElement method) {
        TypeMirror asMemberOf = typeUtils.asMemberOf( includingType, method );
        return (ExecutableType) asMemberOf;
    }

    /**
     * Get the Type for given method as part of usedMapper. Possibly parameterized types in method declaration will be
     * evaluated to concrete types then.
     *
     * @param includingType the type on which's scope the method type shall be evaluated
     * @param method the method
     *
     * @return the ExecutableType representing the method as part of usedMapper
     */
    public TypeMirror getMethodType(DeclaredType includingType, Element method) {
        return typeUtils.asMemberOf( includingType, method );
    }

    public Parameter getSingleParameter(DeclaredType includingType, Accessor method) {
        if ( method.getAccessorType().isFieldAssignment() ) {
            return null;
        }
        ExecutableElement executable = (ExecutableElement) method.getElement();
        List<? extends VariableElement> parameters = executable.getParameters();

        if ( parameters.size() != 1 ) {
            //TODO: Log error
            return null;
        }

        return Collections.first( getParameters( includingType, method ) );
    }

    public List<Parameter> getParameters(DeclaredType includingType, Accessor accessor) {
        ExecutableElement method = (ExecutableElement) accessor.getElement();
        return getParameters( includingType, method );
    }

    public List<Parameter> getParameters(DeclaredType includingType, ExecutableElement method) {
        ExecutableType methodType = getMethodType( includingType, method );
        if ( method == null || methodType.getKind() != TypeKind.EXECUTABLE ) {
            return new ArrayList<>();
        }
        return getParameters( methodType, method );
    }

    public List<Parameter> getParameters(ExecutableType methodType, ExecutableElement method) {
        List<? extends TypeMirror> parameterTypes = methodType.getParameterTypes();
        List<? extends VariableElement> parameters = method.getParameters();
        List<Parameter> result = new ArrayList<>( parameters.size() );

        Iterator<? extends VariableElement> varIt = parameters.iterator();
        Iterator<? extends TypeMirror> typesIt = parameterTypes.iterator();

        while ( varIt.hasNext() ) {
            VariableElement parameter = varIt.next();
            TypeMirror parameterType = typesIt.next();

            Type type = getType( parameterType );

            // if the method has varargs and this is the last parameter
            // we know that this parameter should be used as varargs
            boolean isVarArgs = !varIt.hasNext() && method.isVarArgs();

            result.add( Parameter.forElementAndType( parameter, type, isVarArgs ) );
        }

        return result;
    }

    public Type getReturnType(DeclaredType includingType, Accessor accessor) {
        Type type;
        TypeMirror accessorType = getMethodType( includingType, accessor.getElement() );
        if ( isExecutableType( accessorType ) ) {
            type = getType( ( (ExecutableType) accessorType ).getReturnType() );
        }
        else {
            type = getType( accessorType );
        }

        return type;
    }

    private boolean isExecutableType(TypeMirror accessorType) {
        return accessorType.getKind() == TypeKind.EXECUTABLE;
    }

    public Type getReturnType(ExecutableType method) {
        return getType( method.getReturnType() );
    }

    public List<Type> getThrownTypes(ExecutableType method) {
        return extractTypes( method.getThrownTypes() );
    }

    public List<Type> getThrownTypes(Accessor accessor) {
        if (accessor.getAccessorType().isFieldAssignment()) {
            return new ArrayList<>();
        }
        return extractTypes( ( (ExecutableElement) accessor.getElement() ).getThrownTypes() );
    }

    private List<Type> extractTypes(List<? extends TypeMirror> typeMirrors) {
        Set<Type> types = new HashSet<>( typeMirrors.size() );

        for ( TypeMirror typeMirror : typeMirrors ) {
            types.add( getType( typeMirror ) );
        }

        return new ArrayList<>( types );
    }

    private List<Type> getTypeParameters(TypeMirror mirror, boolean isImplementationType) {
        if ( mirror.getKind() != TypeKind.DECLARED ) {
            return java.util.Collections.emptyList();
        }

        DeclaredType declaredType = (DeclaredType) mirror;
        List<Type> typeParameters = new ArrayList<>( declaredType.getTypeArguments().size() );

        for ( TypeMirror typeParameter : declaredType.getTypeArguments() ) {
            if ( isImplementationType ) {
                typeParameters.add( getType( typeParameter ).getTypeBound() );
            }
            else {
                typeParameters.add( getType( typeParameter ) );
            }
        }

        return typeParameters;
    }

    private TypeMirror getPrimitiveType(Class<?> primitiveType) {
        return primitiveType == byte.class ? typeUtils.getPrimitiveType( TypeKind.BYTE ) :
            primitiveType == short.class ? typeUtils.getPrimitiveType( TypeKind.SHORT ) :
                primitiveType == int.class ? typeUtils.getPrimitiveType( TypeKind.INT ) :
                    primitiveType == long.class ? typeUtils.getPrimitiveType( TypeKind.LONG ) :
                        primitiveType == float.class ? typeUtils.getPrimitiveType( TypeKind.FLOAT ) :
                            primitiveType == double.class ? typeUtils.getPrimitiveType( TypeKind.DOUBLE ) :
                                primitiveType == boolean.class ? typeUtils.getPrimitiveType( TypeKind.BOOLEAN ) :
                                    primitiveType == char.class ? typeUtils.getPrimitiveType( TypeKind.CHAR ) :
                                        typeUtils.getPrimitiveType( TypeKind.VOID );
    }

    private ImplementationType getImplementationType(TypeMirror mirror) {
        if ( mirror.getKind() != TypeKind.DECLARED ) {
            return null;
        }

        DeclaredType declaredType = (DeclaredType) mirror;

        ImplementationType implementation = implementationTypes.get(
            ( (TypeElement) declaredType.asElement() ).getQualifiedName()
                .toString()
        );

        if ( implementation != null ) {
            Type implementationType = implementation.getType();
            Type replacement = new Type(
                typeUtils,
                elementUtils,
                this,
                roundContext.getAnnotationProcessorContext().getAccessorNaming(),
                typeUtils.getDeclaredType(
                    implementationType.getTypeElement(),
                    declaredType.getTypeArguments().toArray( new TypeMirror[] { } )
                ),
                implementationType.getTypeElement(),
                getTypeParameters( mirror, true ),
                null,
                null,
                implementationType.getPackageName(),
                implementationType.getName(),
                implementationType.getFullyQualifiedName(),
                implementationType.isInterface(),
                implementationType.isEnumType(),
                implementationType.isIterableType(),
                implementationType.isCollectionType(),
                implementationType.isMapType(),
                implementationType.isStreamType(),
                toBeImportedTypes,
                notToBeImportedTypes,
                null,
                implementationType.isLiteral(),
                loggingVerbose
            );
            return implementation.createNew( replacement );
        }

        return null;
    }

    private BuilderInfo findBuilder(TypeMirror type, BuilderGem builderGem, boolean report) {
        if ( builderGem != null && builderGem.disableBuilder().get() ) {
            return null;
        }
        try {
            return roundContext.getAnnotationProcessorContext()
                .getBuilderProvider()
                .findBuilderInfo( type );
        }
        catch ( MoreThanOneBuilderCreationMethodException ex ) {
            if ( report ) {
                messager.printMessage(
                        typeUtils.asElement( type ),
                        Message.BUILDER_MORE_THAN_ONE_BUILDER_CREATION_METHOD,
                        type,
                        Strings.join( ex.getBuilderInfo(), ", ", BUILDER_INFO_CREATION_METHOD_EXTRACTOR )
                );
            }
        }

        return null;
    }

    private TypeMirror getComponentType(TypeMirror mirror) {
        if ( mirror.getKind() != TypeKind.ARRAY ) {
            return null;
        }

        ArrayType arrayType = (ArrayType) mirror;
        return arrayType.getComponentType();
    }

    /**
     * creates a void return type
     *
     * @return void type
     */
    public Type createVoidType() {
        return getType( typeUtils.getNoType( TypeKind.VOID ) );
    }

    /**
     * Establishes the type bound:
     * <ol>
     * <li>{@code <? extends Number>}, returns Number</li>
     * <li>{@code <? super Number>}, returns Number</li>
     * <li>{@code <?>}, returns Object</li>
     * <li>{@code <T extends Number>, returns Number}</li>
     * </ol>
     *
     * @param typeMirror the type to return the bound for
     * @return the bound for this parameter
     */
    public TypeMirror getTypeBound(TypeMirror typeMirror) {
        if ( typeMirror.getKind() == TypeKind.WILDCARD ) {
            WildcardType wildCardType = (WildcardType) typeMirror;
            if ( wildCardType.getExtendsBound() != null ) {
                return wildCardType.getExtendsBound();
            }

            if ( wildCardType.getSuperBound() != null ) {
                return wildCardType.getSuperBound();
            }

            String wildCardName = wildCardType.toString();
            if ( "?".equals( wildCardName ) ) {
                return elementUtils.getTypeElement( Object.class.getCanonicalName() ).asType();
            }
        }
        else if ( typeMirror.getKind() == TypeKind.TYPEVAR ) {
            TypeVariable typeVariableType = (TypeVariable) typeMirror;
            if ( typeVariableType.getUpperBound() != null ) {
                return typeVariableType.getUpperBound();
            }
            // lower bounds ( T super Number ) cannot be used for argument parameters, but can be used for
            // method parameters: e.g.  <T super Number> T map (T in);
            if ( typeVariableType.getLowerBound() != null ) {
                return typeVariableType.getLowerBound();
            }
        }

        return typeMirror;
    }

    /**
     * Whether the given type is ready to be processed or not. It can be processed if it is not of kind
     * {@link TypeKind#ERROR} and all {@link AstModifyingAnnotationProcessor}s (if any) indicated that they've fully
     * processed the type.
     */
    private boolean canBeProcessed(TypeMirror type) {
        if ( type.getKind() == TypeKind.ERROR ) {
            return false;
        }

        if ( type.getKind() != TypeKind.DECLARED ) {
            return true;
        }

        if ( roundContext.isReadyForProcessing( type ) ) {
            return true;
        }

        List<AstModifyingAnnotationProcessor> astModifyingAnnotationProcessors = roundContext
                .getAnnotationProcessorContext()
                .getAstModifyingAnnotationProcessors();

        for ( AstModifyingAnnotationProcessor processor : astModifyingAnnotationProcessors ) {
            if ( !processor.isTypeComplete( type ) ) {
                return false;
            }
        }

        roundContext.addTypeReadyForProcessing( type );

        return true;
    }

    public BuilderType builderTypeFor( Type type, BuilderGem builder ) {
        if ( type != null ) {
            BuilderInfo builderInfo = findBuilder( type.getTypeMirror(), builder, true );
            return BuilderType.create( builderInfo, type, this, this.typeUtils );
        }
        return null;
    }

    public Type effectiveResultTypeFor( Type type, BuilderGem builder ) {
        if ( type != null ) {
            BuilderInfo builderInfo = findBuilder( type.getTypeMirror(), builder, false );
            BuilderType builderType = BuilderType.create( builderInfo, type, this, this.typeUtils );
            return builderType != null ? builderType.getBuilder() : type;
        }
        return type;
    }
}
