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
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import org.mapstruct.ap.internal.prism.MappingTargetPrism;
import org.mapstruct.ap.internal.prism.TargetTypePrism;
import org.mapstruct.ap.internal.util.AnnotationProcessingException;
import org.mapstruct.ap.internal.util.Collections;

import static org.mapstruct.ap.internal.util.workarounds.SpecificCompilerWorkarounds.replaceTypeElementIfNecessary;

/**
 * Factory creating {@link Type} instances.
 *
 * @author Gunnar Morling
 */
public class TypeFactory {

    private final Elements elementUtils;
    private final Types typeUtils;

    private final TypeMirror iterableType;
    private final TypeMirror collectionType;
    private final TypeMirror mapType;

    private final Map<String, Type> implementationTypes = new HashMap<String, Type>();
    private final Map<String, String> importedQualifiedTypesBySimpleName = new HashMap<String, String>();

    public TypeFactory(Elements elementUtils, Types typeUtils) {
        this.elementUtils = elementUtils;
        this.typeUtils = typeUtils;

        iterableType = typeUtils.erasure( elementUtils.getTypeElement( Iterable.class.getCanonicalName() ).asType() );
        collectionType =
            typeUtils.erasure( elementUtils.getTypeElement( Collection.class.getCanonicalName() ).asType() );
        mapType = typeUtils.erasure( elementUtils.getTypeElement( Map.class.getCanonicalName() ).asType() );

        implementationTypes.put( Iterable.class.getName(), getType( ArrayList.class ) );
        implementationTypes.put( Collection.class.getName(), getType( ArrayList.class ) );
        implementationTypes.put( List.class.getName(), getType( ArrayList.class ) );

        implementationTypes.put( Set.class.getName(), getType( HashSet.class ) );
        implementationTypes.put( SortedSet.class.getName(), getType( TreeSet.class ) );
        implementationTypes.put( NavigableSet.class.getName(), getType( TreeSet.class ) );

        implementationTypes.put( Map.class.getName(), getType( HashMap.class ) );
        implementationTypes.put( SortedMap.class.getName(), getType( TreeMap.class ) );
        implementationTypes.put( NavigableMap.class.getName(), getType( TreeMap.class ) );
        implementationTypes.put( ConcurrentMap.class.getName(), getType( ConcurrentHashMap.class ) );
        implementationTypes.put( ConcurrentNavigableMap.class.getName(), getType( ConcurrentSkipListMap.class ) );
    }

    public Type getType(Class<?> type) {
        return type.isPrimitive() ? getType( getPrimitiveType( type ) ) : getType( type.getCanonicalName() );
    }

    public Type getType(String canonicalName) {
        TypeElement typeElement = elementUtils.getTypeElement( canonicalName );

        if ( typeElement == null ) {
            throw new AnnotationProcessingException(
                "Couldn't find type " + canonicalName + ". Are you missing a dependency on your classpath?"
            );
        }

        return getType( typeElement );
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

    public Type getType(TypeElement typeElement) {
        return getType( typeElement.asType() );
    }

    public Type getType(TypeMirror mirror) {
        if ( mirror.getKind() == TypeKind.ERROR ) {
            throw new AnnotationProcessingException( "Encountered erroneous type " + mirror );
        }

        Type implementationType = getImplementationType( mirror );

        boolean isIterableType = typeUtils.isSubtype( mirror, iterableType );
        boolean isCollectionType = typeUtils.isSubtype( mirror, collectionType );
        boolean isMapType = typeUtils.isSubtype( mirror, mapType );

        boolean isEnumType;
        boolean isInterface;
        String name;
        String packageName;
        String qualifiedName;
        TypeElement typeElement;
        Type componentType;


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

            if ( componentTypeMirror.getKind() == TypeKind.DECLARED ) {
                DeclaredType declaredType = (DeclaredType) componentTypeMirror;
                TypeElement componentTypeElement = (TypeElement) declaredType.asElement();

                name = componentTypeElement.getSimpleName().toString() + "[]";
                packageName = elementUtils.getPackageOf( componentTypeElement ).getQualifiedName().toString();
                qualifiedName = componentTypeElement.getQualifiedName().toString() + "[]";
            }
            else {
                name = mirror.toString();
                packageName = null;
                qualifiedName = name;
            }

            isEnumType = false;
            isInterface = false;
            typeElement = null;
            componentType = getType( componentTypeMirror );
        }
        else {
            isEnumType = false;
            isInterface = false;
            name = mirror.toString();
            packageName = null;
            qualifiedName = name;
            typeElement = null;
            componentType = null;
        }

        return new Type(
            typeUtils, elementUtils, this,
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
            isImported( name, qualifiedName )
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
     * @param includingType the type on which's scope the method type shall be evaluated
     * @param method the method
     * @return the ExecutableType representing the method as part of usedMapper
     */
    public ExecutableType getMethodType(TypeElement includingType, ExecutableElement method) {
        DeclaredType asType = (DeclaredType) replaceTypeElementIfNecessary( elementUtils, includingType ).asType();
        TypeMirror asMemberOf = typeUtils.asMemberOf( asType, method );
        return (ExecutableType) asMemberOf;
    }

    public Parameter getSingleParameter(TypeElement includingType, ExecutableElement method) {
        List<? extends VariableElement> parameters = method.getParameters();

        if ( parameters.size() != 1 ) {
            //TODO: Log error
            return null;
        }

        return Collections.first( getParameters( includingType, method ) );
    }

    public List<Parameter> getParameters(TypeElement includingType, ExecutableElement method) {
        return getParameters( getMethodType( includingType, method ), method );
    }

    public List<Parameter> getParameters(ExecutableType methodType, ExecutableElement method) {
        List<? extends TypeMirror> parameterTypes = methodType.getParameterTypes();
        List<? extends VariableElement> parameters = method.getParameters();
        List<Parameter> result = new ArrayList<Parameter>( parameters.size() );

        Iterator<? extends VariableElement> varIt = parameters.iterator();
        Iterator<? extends TypeMirror> typesIt = parameterTypes.iterator();

        for ( ; varIt.hasNext(); ) {
            VariableElement parameter = varIt.next();
            TypeMirror parameterType = typesIt.next();

            result.add( new Parameter(
                parameter.getSimpleName().toString(),
                getType( parameterType ),
                MappingTargetPrism.getInstanceOn( parameter ) != null,
                TargetTypePrism.getInstanceOn( parameter ) != null ) );
        }

        return result;
    }

    public Type getReturnType(TypeElement includingType, ExecutableElement method) {
        return getReturnType( getMethodType( includingType, method ) );
    }

    public Type getReturnType(ExecutableType method) {
        return getType( method.getReturnType() );
    }

    public List<Type> getThrownTypes(TypeElement includingType, ExecutableElement method) {
        return getThrownTypes( getMethodType( includingType, method ) );
    }

    public List<Type> getThrownTypes(ExecutableType method) {
        List<Type> thrownTypes = new ArrayList<Type>();
        for ( TypeMirror exceptionType : method.getThrownTypes() ) {
            thrownTypes.add( getType( exceptionType ) );
        }
        return thrownTypes;
    }

    private List<Type> getTypeParameters(TypeMirror mirror, boolean isImplementationType) {
        if ( mirror.getKind() != TypeKind.DECLARED ) {
            return java.util.Collections.emptyList();
        }

        DeclaredType declaredType = (DeclaredType) mirror;
        List<Type> typeParameters = new ArrayList<Type>( declaredType.getTypeArguments().size() );

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

    private Type getImplementationType(TypeMirror mirror) {
        if ( mirror.getKind() != TypeKind.DECLARED ) {
            return null;
        }

        DeclaredType declaredType = (DeclaredType) mirror;

        Type implementationType = implementationTypes.get(
            ( (TypeElement) declaredType.asElement() ).getQualifiedName()
                .toString()
        );

        if ( implementationType != null ) {
            return new Type(
                typeUtils,
                elementUtils,
                this,
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
                isImported( implementationType.getName(), implementationType.getFullyQualifiedName() )
            );
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

    private boolean isImported(String name, String qualifiedName) {
        String trimmedName = TypeFactory.trimSimpleClassName( name );
        String importedType = importedQualifiedTypesBySimpleName.get( trimmedName );

        boolean imported = false;
        if ( importedType != null ) {
            if ( importedType.equals( qualifiedName ) ) {
                imported = true;
            }
        }
        else {
            importedQualifiedTypesBySimpleName.put( trimmedName, qualifiedName );
            imported = true;
        }
        return imported;
    }

    /**
     * Converts any collection type, e.g. {@code List<T>} to {@code Collection<T>} and any map type, e.g.
     * {@code HashMap<K,V>} to {@code Map<K,V>}.
     *
     * @param collectionOrMap any collection or map type
     * @return the type representing {@code Collection<T>} or {@code Map<K,V>}, if the argument type is a subtype of
     *         {@code Collection<T>} or of {@code Map<K,V>} respectively.
     */
    public Type asCollectionOrMap(Type collectionOrMap) {
        List<Type> originalParameters = collectionOrMap.getTypeParameters();
        TypeMirror[] originalParameterMirrors = new TypeMirror[originalParameters.size()];
        int i = 0;
        for ( Type param : originalParameters ) {
            originalParameterMirrors[i++] = param.getTypeMirror();
        }

        if ( collectionOrMap.isCollectionType()
            && !"java.util.Collection".equals( collectionOrMap.getFullyQualifiedName() ) ) {
            return getType( typeUtils.getDeclaredType(
                elementUtils.getTypeElement( "java.util.Collection" ),
                originalParameterMirrors ) );
        }
        else if ( collectionOrMap.isMapType()
            && !"java.util.Map".equals( collectionOrMap.getFullyQualifiedName() ) ) {
            return getType( typeUtils.getDeclaredType(
                elementUtils.getTypeElement( "java.util.Map" ),
                originalParameterMirrors ) );
        }

        return collectionOrMap;
    }

    /**
     * Establishes the type bound:
     * <ol>
     * <li>{@code<? extends Number>}, returns Number</li>
     * <li>{@code<? super Number>}, returns Number</li>
     * <li>{@code<?>}, returns Object</li>
     * <li>{@code<T extends Number>, returns Number}</li>
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
            // Lowerbounds intentionally left out: Type variables otherwise have a lower bound of NullType.
        }

        return typeMirror;
    }

    static String trimSimpleClassName(String className) {
        if ( className == null ) {
            return null;
        }
        String trimmedClassName = className;
        while ( trimmedClassName.endsWith( "[]" ) ) {
            trimmedClassName = trimmedClassName.substring( 0, trimmedClassName.length() - 2 );
        }
        return trimmedClassName;
    }

}
