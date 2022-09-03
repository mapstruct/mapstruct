/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.TypeMirror;

import org.mapstruct.ap.internal.util.accessor.Accessor;
import org.mapstruct.ap.internal.util.accessor.ExecutableElementAccessor;
import org.mapstruct.ap.internal.util.accessor.ReadAccessor;

import static org.mapstruct.ap.internal.util.Collections.first;
import static org.mapstruct.ap.internal.util.accessor.AccessorType.ADDER;
import static org.mapstruct.ap.internal.util.accessor.AccessorType.SETTER;

/**
 * Filter methods for working with {@link Element} collections.
 *
 * @author Gunnar Morling
 * @author Filip Hrisafov
 */
public class Filters {

    private static final Method RECORD_COMPONENTS_METHOD;
    private static final Method RECORD_COMPONENT_ACCESSOR_METHOD;

    static {
        Method recordComponentsMethod;
        Method recordComponentAccessorMethod;
        try {
            recordComponentsMethod = TypeElement.class.getMethod( "getRecordComponents" );
            recordComponentAccessorMethod = Class.forName( "javax.lang.model.element.RecordComponentElement" )
                .getMethod( "getAccessor" );
        }
        catch ( NoSuchMethodException | ClassNotFoundException e ) {
            recordComponentsMethod = null;
            recordComponentAccessorMethod = null;
        }
        RECORD_COMPONENTS_METHOD = recordComponentsMethod;
        RECORD_COMPONENT_ACCESSOR_METHOD = recordComponentAccessorMethod;
    }

    private final AccessorNamingUtils accessorNaming;
    private final TypeUtils typeUtils;
    private final TypeMirror typeMirror;

    public Filters(AccessorNamingUtils accessorNaming, TypeUtils typeUtils, TypeMirror typeMirror) {
        this.accessorNaming = accessorNaming;
        this.typeUtils = typeUtils;
        this.typeMirror = typeMirror;
    }

    public List<ReadAccessor> getterMethodsIn(List<ExecutableElement> elements) {
        return elements.stream()
            .filter( accessorNaming::isGetterMethod )
            .map( method ->  ReadAccessor.fromGetter( method, getReturnType( method ) ) )
            .collect( Collectors.toCollection( LinkedList::new ) );
    }

    @SuppressWarnings("unchecked")
    public List<Element> recordComponentsIn(TypeElement typeElement) {
        if ( RECORD_COMPONENTS_METHOD == null ) {
            return java.util.Collections.emptyList();
        }

        try {
            return (List<Element>) RECORD_COMPONENTS_METHOD.invoke( typeElement );
        }
        catch ( IllegalAccessException | InvocationTargetException e ) {
            return java.util.Collections.emptyList();
        }
    }

    public Map<String, ReadAccessor> recordAccessorsIn(Collection<Element> recordComponents) {
        if ( RECORD_COMPONENT_ACCESSOR_METHOD == null ) {
            return java.util.Collections.emptyMap();
        }
        try {
            Map<String, ReadAccessor> recordAccessors = new LinkedHashMap<>();
            for ( Element recordComponent : recordComponents ) {
                ExecutableElement recordExecutableElement =
                    (ExecutableElement) RECORD_COMPONENT_ACCESSOR_METHOD.invoke( recordComponent );
                recordAccessors.put(
                    recordComponent.getSimpleName().toString(),
                    ReadAccessor.fromGetter( recordExecutableElement, getReturnType( recordExecutableElement ) )
                );
            }

            return recordAccessors;
        }
        catch ( IllegalAccessException | InvocationTargetException e ) {
            return java.util.Collections.emptyMap();
        }
    }

    private TypeMirror getReturnType(ExecutableElement executableElement) {
        return getWithinContext( executableElement ).getReturnType();
    }

    public <T> List<T> fieldsIn(List<VariableElement> accessors, Function<VariableElement, T> creator) {
        return accessors.stream()
            .filter( Fields::isFieldAccessor )
            .map( creator )
            .collect( Collectors.toCollection( LinkedList::new ) );
    }

    public List<ExecutableElement> presenceCheckMethodsIn(List<ExecutableElement> elements) {
        return elements.stream()
            .filter( accessorNaming::isPresenceCheckMethod )
            .collect( Collectors.toCollection( LinkedList::new ) );
    }

    public List<Accessor> setterMethodsIn(List<ExecutableElement> elements) {
        return elements.stream()
            .filter( accessorNaming::isSetterMethod )
            .map( method ->  new ExecutableElementAccessor( method, getFirstParameter( method ), SETTER ) )
            .collect( Collectors.toCollection( LinkedList::new ) );
    }

    private TypeMirror getFirstParameter(ExecutableElement executableElement) {
        return first( getWithinContext( executableElement ).getParameterTypes() );
    }

    private ExecutableType getWithinContext( ExecutableElement executableElement ) {
        return (ExecutableType) typeUtils.asMemberOf( (DeclaredType) typeMirror, executableElement );
    }

    public List<Accessor> adderMethodsIn(List<ExecutableElement> elements) {
        return elements.stream()
            .filter( accessorNaming::isAdderMethod )
            .map( method -> new ExecutableElementAccessor( method, getFirstParameter( method ), ADDER ) )
            .collect( Collectors.toCollection( LinkedList::new ) );
    }
}
