/**
 *  Copyright 2012-2013 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.util;

import java.beans.Introspector;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeKind;

import org.mapstruct.ap.MappingTargetPrism;
import org.mapstruct.ap.model.Parameter;
import org.mapstruct.ap.model.Type;

/**
 * Provides functionality around {@link ExecutableElement}s.
 *
 * @author Gunnar Morling
 */
public class Executables {

    private final TypeFactory typeFactory;

    public Executables(TypeFactory typeFactory) {
        this.typeFactory = typeFactory;
    }

    public boolean isGetterMethod(ExecutableElement method) {
        return isNonBooleanGetterMethod( method ) || isBooleanGetterMethod( method );
    }

    private boolean isNonBooleanGetterMethod(ExecutableElement method) {
        String name = method.getSimpleName().toString();

        return method.getParameters().isEmpty() &&
            name.startsWith( "get" ) &&
            name.length() > 3 &&
            method.getReturnType().getKind() != TypeKind.VOID;
    }

    private boolean isBooleanGetterMethod(ExecutableElement method) {
        String name = method.getSimpleName().toString();

        return method.getParameters().isEmpty() &&
            name.startsWith( "is" ) &&
            name.length() > 2 &&
            method.getReturnType().getKind() == TypeKind.BOOLEAN;
    }

    public boolean isSetterMethod(ExecutableElement method) {
        String name = method.getSimpleName().toString();

        if ( name.startsWith( "set" ) && name.length() > 3 && method.getParameters()
            .size() == 1 && method.getReturnType().getKind() == TypeKind.VOID ) {
            return true;
        }

        return false;
    }

    public String getPropertyName(ExecutableElement getterOrSetterMethod) {
        if ( isNonBooleanGetterMethod( getterOrSetterMethod ) ) {
            return Introspector.decapitalize(
                getterOrSetterMethod.getSimpleName().toString().substring( 3 )
            );
        }
        else if ( isBooleanGetterMethod( getterOrSetterMethod ) ) {
            return Introspector.decapitalize(
                getterOrSetterMethod.getSimpleName().toString().substring( 2 )
            );
        }
        else if ( isSetterMethod( getterOrSetterMethod ) ) {
            return Introspector.decapitalize(
                getterOrSetterMethod.getSimpleName().toString().substring( 3 )
            );
        }

        throw new IllegalArgumentException( "Executable " + getterOrSetterMethod + " is not getter or setter method." );
    }

    public Set<String> getPropertyNames(List<ExecutableElement> propertyAccessors) {
        Set<String> propertyNames = new HashSet<String>();

        for ( ExecutableElement executableElement : propertyAccessors ) {
            propertyNames.add( getPropertyName( executableElement ) );
        }

        return propertyNames;
    }

    public Parameter retrieveSingleParameter(ExecutableElement method) {
        List<? extends VariableElement> parameters = method.getParameters();

        if ( parameters.size() != 1 ) {
            //TODO: Log error
            return null;
        }

        VariableElement parameter = parameters.get( 0 );

        return new Parameter(
            parameter.getSimpleName().toString(),
            typeFactory.getType( parameter.asType() ),
            false
        );
    }

    public List<Parameter> retrieveParameters(ExecutableElement method) {
        List<? extends VariableElement> parameters = method.getParameters();
        List<Parameter> result = new ArrayList<Parameter>( parameters.size() );

        for ( VariableElement parameter : parameters ) {
            result
                .add(
                    new Parameter(
                        parameter.getSimpleName().toString(),
                        typeFactory.getType( parameter.asType() ),
                        MappingTargetPrism.getInstanceOn( parameter ) != null
                    )
                );
        }

        return result;
    }

    public Type retrieveReturnType(ExecutableElement method) {
        return typeFactory.getType( method.getReturnType() );
    }
}
