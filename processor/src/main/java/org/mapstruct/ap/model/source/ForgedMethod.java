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
package org.mapstruct.ap.model.source;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.tools.Diagnostic;
import org.mapstruct.ap.model.common.Accessibility;
import org.mapstruct.ap.model.common.Parameter;
import org.mapstruct.ap.model.common.Type;
import org.mapstruct.ap.util.Strings;

/**
 * This method will be generated in absence of a suitable abstract method to implement.
 *
 * This concerns Iterable- and MapMappingMethods.
 *
 * @author Sjaak Derksen
 */
public class ForgedMethod implements Method {

    private final List<Parameter> parameters;
    private final Type returnType;
    private final String name;
    private final Element positionHintElement;

    public ForgedMethod( Type sourceType, Type targetType, Element positionHintElement ) {
        this.parameters = Arrays.asList( new Parameter("source", sourceType) );
        this.returnType = targetType;

        String fromName = getName( parameters.iterator().next().getType() );
        String toName = getName( returnType );
        name = Strings.decapitalize( fromName + "To" + toName );

        this.positionHintElement = positionHintElement;
    }

    private String getName( Type type ) {
        StringBuilder builder = new StringBuilder( );
        for ( Type typeParam : type.getTypeParameters() ) {
              builder.append( typeParam.getName() );
        }
        builder.append( type.getName() );
        return builder.toString();
    }

    @Override
    public boolean matches( List<Type> sourceTypes, Type targetType ) {

        if ( !targetType.equals( returnType ) ) {
            return false;
        }

        if ( sourceTypes.size() == parameters.size() ) {
            return false;
        }
        for (int i = 0; i < sourceTypes.size(); i++ ) {
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
    public void printMessage( Messager messager, Diagnostic.Kind kind, String message ) {
        messager.printMessage( kind, message, positionHintElement );
    }

    @Override
    public Type getResultType() {
        return returnType;
    }

    @Override
    public List<String> getParameterNames() {
        return Arrays.asList( "source" );
    }

    @Override
    public boolean overridesMethod() {
        return  false;
    }
}
