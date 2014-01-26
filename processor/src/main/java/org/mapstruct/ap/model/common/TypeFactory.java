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

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import org.mapstruct.ap.prism.MappingTargetPrism;
import org.mapstruct.ap.util.AnnotationProcessingException;

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

        boolean isIterableType = typeUtils.isSubtype(
            mirror,
            iterableType
        );
        boolean isCollectionType = typeUtils.isSubtype(
            mirror,
            collectionType
        );
        boolean isMapType = typeUtils.isSubtype(
            mirror,
            mapType
        );

        return new Type(
            mirror,
            getTypeParameters( mirror ),
            implementationType,
            isIterableType,
            isCollectionType,
            isMapType,
            typeUtils,
            elementUtils
        );
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
            getType( parameter.asType() ),
            false
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
                        MappingTargetPrism.getInstanceOn( parameter ) != null
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
                typeUtils.getDeclaredType(
                    implementationType.getTypeElement(),
                    declaredType.getTypeArguments().toArray( new TypeMirror[] { } )
                ),
                getTypeParameters( mirror ),
                null,
                implementationType.isIterableType(),
                implementationType.isCollectionType(),
                implementationType.isMapType(),
                typeUtils,
                elementUtils
            );
        }

        return null;
    }
}
