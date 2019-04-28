/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.util;

import java.util.LinkedList;
import java.util.List;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;

import org.mapstruct.ap.internal.util.accessor.Accessor;
import org.mapstruct.ap.internal.util.accessor.ExecutableElementAccessor;
import org.mapstruct.ap.internal.util.accessor.VariableElementAccessor;

import static org.mapstruct.ap.internal.util.Collections.first;
import static org.mapstruct.ap.internal.util.accessor.AccessorType.ADDER;
import static org.mapstruct.ap.internal.util.accessor.AccessorType.GETTER;
import static org.mapstruct.ap.internal.util.accessor.AccessorType.PRESENCE_CHECKER;
import static org.mapstruct.ap.internal.util.accessor.AccessorType.SETTER;

/**
 * Filter methods for working with {@link Element} collections.
 *
 * @author Gunnar Morling
 */
public class Filters {

    private final AccessorNamingUtils accessorNaming;
    private final Types typeUtils;
    private final TypeMirror typeMirror;

    public Filters(AccessorNamingUtils accessorNaming, Types typeUtils, TypeMirror typeMirror) {
        this.accessorNaming = accessorNaming;
        this.typeUtils = typeUtils;
        this.typeMirror = typeMirror;
    }

    public  List<Accessor> getterMethodsIn(List<ExecutableElement> elements) {
        List<Accessor> getterMethods = new LinkedList<>();

        for ( ExecutableElement method : elements ) {
            if ( accessorNaming.isGetterMethod( method ) ) {
                getterMethods.add( new ExecutableElementAccessor( method, getReturnType( method ), GETTER ) );
            }
        }

        return getterMethods;
    }

    public  List<Accessor> fieldsIn(List<VariableElement> accessors) {
        List<Accessor> fieldAccessors = new LinkedList<>();

        for ( VariableElement accessor : accessors ) {
            if ( Fields.isFieldAccessor( accessor ) ) {
                fieldAccessors.add( new VariableElementAccessor( accessor ) );
            }
        }

        return fieldAccessors;
    }

    public List<Accessor> presenceCheckMethodsIn(List<ExecutableElement> elements) {
        List<Accessor> presenceCheckMethods = new LinkedList<>();

        for ( ExecutableElement method : elements ) {
            if ( accessorNaming.isPresenceCheckMethod( method ) ) {
                presenceCheckMethods.add( new ExecutableElementAccessor(
                                method,
                                getReturnType( method ),
                                PRESENCE_CHECKER
                ) );
            }
        }

        return presenceCheckMethods;
    }

    public  List<Accessor> setterMethodsIn(List<ExecutableElement> elements) {
        List<Accessor> setterMethods = new LinkedList<>();

        for ( ExecutableElement method : elements ) {
            if ( accessorNaming.isSetterMethod( method ) ) {
                setterMethods.add( new ExecutableElementAccessor( method, getFirstParameter( method ), SETTER ) );
            }
        }
        return setterMethods;
    }

    public  List<Accessor> adderMethodsIn( List<ExecutableElement> elements) {
        List<Accessor> adderMethods = new LinkedList<>();

        for ( ExecutableElement method : elements ) {
            if ( accessorNaming.isAdderMethod( method ) ) {
                adderMethods.add( new ExecutableElementAccessor( method, getFirstParameter( method ), ADDER ) );
            }
        }

        return adderMethods;
    }

    private TypeMirror getReturnType(ExecutableElement executableElement) {
        return getWithinContext( executableElement ).getReturnType();
    }

    private TypeMirror getFirstParameter(ExecutableElement executableElement) {
        return first( getWithinContext( executableElement ).getParameterTypes() );
    }

    private ExecutableType getWithinContext( ExecutableElement executableElement ) {
        return (ExecutableType) typeUtils.asMemberOf( (DeclaredType) typeMirror, executableElement );
    }
}
