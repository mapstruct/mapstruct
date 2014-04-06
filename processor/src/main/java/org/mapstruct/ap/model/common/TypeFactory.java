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
import java.util.HashMap;
import java.util.HashSet;
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
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.SimpleElementVisitor6;
import javax.lang.model.util.Types;

import org.mapstruct.ap.prism.MappingTargetPrism;
import org.mapstruct.ap.prism.TargetTypePrism;
import org.mapstruct.ap.util.AnnotationProcessingException;
import org.mapstruct.ap.util.TypeUtilsJDK6Fix;

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
        collectionType = typeUtils.erasure(
            elementUtils.getTypeElement( Collection.class.getCanonicalName() )
                .asType()
        );
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

    public Type getType(TypeElement typeElement) {
        return getType( typeElement.asType() );
    }

    public Type getType(TypeMirror mirror) {
        if ( mirror.getKind() == TypeKind.ERROR ) {
            throw new AnnotationProcessingException( "Encountered erroneous type " + mirror );
        }

        Type implementationType = getImplementationType( mirror );

        boolean isIterableType = TypeUtilsJDK6Fix.isSubType( typeUtils, mirror, iterableType );
        boolean isCollectionType = TypeUtilsJDK6Fix.isSubType( typeUtils, mirror, collectionType );
        boolean isMapType = TypeUtilsJDK6Fix.isSubType( typeUtils, mirror, mapType );

        boolean isEnumType;
        boolean isInterface;
        String name;
        String packageName;
        TypeElement typeElement;
        String qualifiedName;

        DeclaredType declaredType = mirror.getKind() == TypeKind.DECLARED ? (DeclaredType) mirror : null;

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
            name = mirror.toString();
            packageName = null;
            qualifiedName = name;
        }

        return new Type(
            typeUtils, elementUtils,
            mirror,
            typeElement,
            getTypeParameters( mirror ),
            implementationType,
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
     * will be used. <br />
     * Examples: <br />
     * If type represents {@code java.lang.Integer}, it will return the type that represents {@code Class<Integer>}.
     * <br />
     * If type represents {@code int}, it will return the type that represents {@code Class<Integer>}.
     *
     * @param type the type to return the declared class type for
     * @return the type representing {@code Class<type>}.
     */
    public Type classTypeOf(Type type) {
        TypeMirror typeToUse;
        if ( type.isPrimitive() ) {
            typeToUse = typeUtils.boxedClass( (PrimitiveType) type.getTypeMirror() ).asType();
        }
        else {
            typeToUse = type.getTypeMirror();
        }

        return getType( typeUtils.getDeclaredType( elementUtils.getTypeElement( "java.lang.Class" ), typeToUse ) );
    }

    public Parameter getSingleParameter(ExecutableElement method) {
        List<? extends VariableElement> parameters = method.getParameters();

        if ( parameters.size() != 1 ) {
            //TODO: Log error
            return null;
        }

        VariableElement parameter = parameters.get( 0 );

        return new Parameter(
            parameter.getSimpleName().toString(),
            getType( parameter.asType() )
        );
    }

    public List<Parameter> getParameters(ExecutableElement method) {
        List<? extends VariableElement> parameters = method.getParameters();
        List<Parameter> result = new ArrayList<Parameter>( parameters.size() );

        for ( VariableElement parameter : parameters ) {
            result
                .add(
                    new Parameter(
                        parameter.getSimpleName().toString(),
                        getType( parameter.asType() ),
                        MappingTargetPrism.getInstanceOn( parameter ) != null,
                        TargetTypePrism.getInstanceOn( parameter ) != null
                    )
                );
        }

        return result;
    }

    public Type getReturnType(ExecutableElement method) {
        return getType( method.getReturnType() );
    }

    private List<Type> getTypeParameters(TypeMirror mirror) {
        if ( mirror.getKind() != TypeKind.DECLARED ) {
            return java.util.Collections.emptyList();
        }

        DeclaredType declaredType = (DeclaredType) mirror;
        List<Type> typeParameters = new ArrayList<Type>( declaredType.getTypeArguments().size() );

        for ( TypeMirror typeParameter : declaredType.getTypeArguments() ) {
            typeParameters.add( getType( typeParameter ) );
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
                typeUtils.getDeclaredType(
                    implementationType.getTypeElement(),
                    declaredType.getTypeArguments().toArray( new TypeMirror[] { } )
                ),
                implementationType.getTypeElement(),
                getTypeParameters( mirror ),
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

    private boolean isImported(String name, String qualifiedName) {
        String importedType = importedQualifiedTypesBySimpleName.get( name );

        boolean imported = false;
        if ( importedType != null ) {
            if ( importedType.equals( qualifiedName ) ) {
                imported = true;
            }
        }
        else {
            importedQualifiedTypesBySimpleName.put( name, qualifiedName );
            imported = true;
        }
        return imported;
    }

    private static class TypeElementRetrievalVisitor extends SimpleElementVisitor6<TypeElement, Void> {
        @Override
        public TypeElement visitType(TypeElement e, Void p) {
            return e;
        }
    }
}
