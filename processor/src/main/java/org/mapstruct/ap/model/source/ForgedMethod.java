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
package org.mapstruct.ap.model.source;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.lang.model.element.ExecutableElement;

import org.mapstruct.ap.model.common.Accessibility;
import org.mapstruct.ap.model.common.Parameter;
import org.mapstruct.ap.model.common.Type;
import org.mapstruct.ap.util.Strings;

/**
 * This method will be generated in absence of a suitable abstract method to implement.
 *
 * @author Sjaak Derksen
 */
public class ForgedMethod implements Method {

    private final List<Parameter> parameters;
    private final Type returnType;
    private final String name;
    private final ExecutableElement positionHintElement;

     /**
     * Creates a new forged method with the given name.
     *
     * @param name the (unique name) for this method
     * @param sourceType the source type
     * @param targetType the target type.
     * @param positionHintElement element used to for reference to the position in the source file.
     */
    public ForgedMethod(String name, Type sourceType, Type targetType, ExecutableElement positionHintElement) {
        String sourceParamName = Strings.decapitalize( sourceType.getName().replace( "[]", "" ) );
        String sourceParamSafeName = Strings.getSaveVariableName( sourceParamName );
        this.parameters = Arrays.asList( new Parameter( sourceParamSafeName, sourceType ) );
        this.returnType = targetType;
        this.name = name;
        this.positionHintElement = positionHintElement;
    }

    /**
     * creates a new ForgedMethod with the same arguments but with a new name
     * @param name the new name
     * @param forgedMethod existing forge method
     */
    public ForgedMethod(String name, ForgedMethod forgedMethod) {
        this.parameters = forgedMethod.parameters;
        this.returnType = forgedMethod.returnType;
        this.positionHintElement = forgedMethod.positionHintElement;
        this.name = name;
    }

    @Override
    public boolean matches(List<Type> sourceTypes, Type targetType) {

        if ( !targetType.equals( returnType ) ) {
            return false;
        }

        if ( sourceTypes.size() == parameters.size() ) {
            return false;
        }
        for ( int i = 0; i < sourceTypes.size(); i++ ) {
            if ( !sourceTypes.get( i ).equals( parameters.get( i ).getType() ) ) {
                return false;
            }
        }

        return true;
    }

    @Override
    public Type getDeclaringMapper() {
        return null;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<Parameter> getParameters() {
        return parameters;
    }

    @Override
    public List<Parameter> getSourceParameters() {
        return parameters;
    }

    @Override
    public Parameter getTargetParameter() {
        return null;
    }

    @Override
    public Accessibility getAccessibility() {
        return Accessibility.PROTECTED;
    }

    @Override
    public Type getReturnType() {
        return returnType;
    }

    @Override
    public List<Type> getThrownTypes() {
        return Collections.<Type>emptyList();
    }

    @Override
    public Type getResultType() {
        return returnType;
    }

    @Override
    public List<String> getParameterNames() {
        List<String> parameterNames = new ArrayList<String>();
        for ( Parameter parameter : getParameters() ) {
            parameterNames.add( parameter.getName() );
        }
        return parameterNames;
    }

    @Override
    public boolean overridesMethod() {
        return false;
    }

    @Override
    public ExecutableElement getExecutable() {
        return positionHintElement;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder( returnType.toString() );
        sb.append( " " );

        sb.append( getName() ).append( "(" ).append( Strings.join( parameters, ", " ) ).append( ")" );

        return sb.toString();
    }
}
