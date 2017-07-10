/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
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
import java.util.List;
import java.util.Set;

import javax.lang.model.element.VariableElement;

import org.mapstruct.ap.internal.prism.ContextPrism;
import org.mapstruct.ap.internal.prism.MappingTargetPrism;
import org.mapstruct.ap.internal.prism.TargetTypePrism;
import org.mapstruct.ap.internal.util.Collections;

/**
 * A parameter of a mapping method.
 *
 * @author Gunnar Morling
 */
public class Parameter extends ModelElement {

    private final String name;
    private final String originalName;
    private final Type type;
    private final boolean mappingTarget;
    private final boolean targetType;
    private final boolean mappingContext;

    private Parameter(String name, Type type, boolean mappingTarget, boolean targetType, boolean mappingContext) {
        this.name = name;
        this.originalName = name;
        this.type = type;
        this.mappingTarget = mappingTarget;
        this.targetType = targetType;
        this.mappingContext = mappingContext;
    }

    public Parameter(String name, Type type) {
        this( name, type, false, false, false );
    }

    public String getName() {
        return name;
    }

    public String getOriginalName() {
        return originalName;
    }

    public Type getType() {
        return type;
    }

    public boolean isMappingTarget() {
        return mappingTarget;
    }

    @Override
    public String toString() {
        return ( mappingTarget ? "@MappingTarget " : "" )
            + ( targetType ? "@TargetType " : "" )
            + ( mappingContext ? "@Context " : "" )
            + type.toString() + " " + name;
    }

    @Override
    public Set<Type> getImportTypes() {
        return Collections.asSet( type );
    }

    public boolean isTargetType() {
        return targetType;
    }

    public boolean isMappingContext() {
        return mappingContext;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + ( type != null ? type.hashCode() : 0 );
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }

        Parameter parameter = (Parameter) o;

        if ( name != null ? !name.equals( parameter.name ) : parameter.name != null ) {
            return false;
        }
        return type != null ? type.equals( parameter.type ) : parameter.type == null;

    }

    public static Parameter forElementAndType(VariableElement element, Type parameterType) {
        return new Parameter(
            element.getSimpleName().toString(),
            parameterType,
            MappingTargetPrism.getInstanceOn( element ) != null,
            TargetTypePrism.getInstanceOn( element ) != null,
            ContextPrism.getInstanceOn( element ) != null );
    }

    public static Parameter forForgedMappingTarget(Type parameterType) {
        return new Parameter(
            "mappingTarget",
            parameterType,
            true,
            false,
            false);
    }

    /**
     * @param parameters the parameters to filter
     * @return the parameters from the given list that are considered 'source parameters'
     */
    public static List<Parameter> getSourceParameters(List<Parameter> parameters) {
        List<Parameter> sourceParameters = new ArrayList<Parameter>( parameters.size() );

        for ( Parameter parameter : parameters ) {
            if ( !parameter.isMappingTarget() && !parameter.isTargetType() && !parameter.isMappingContext() ) {
                sourceParameters.add( parameter );
            }
        }

        return sourceParameters;
    }

    /**
     * @param parameters the parameters to filter
     * @return the parameters from the given list that are marked as 'mapping context parameters'
     */
    public static List<Parameter> getContextParameters(List<Parameter> parameters) {
        List<Parameter> contextParameters = new ArrayList<Parameter>( parameters.size() );

        for ( Parameter parameter : parameters ) {
            if ( parameter.isMappingContext() ) {
                contextParameters.add( parameter );
            }
        }

        return contextParameters;
    }

    public static Parameter getMappingTargetParameter(List<Parameter> parameters) {
        for ( Parameter parameter : parameters ) {
            if ( parameter.isMappingTarget() ) {
                return parameter;
            }
        }

        return null;
    }

    public static Parameter getTargetTypeParameter(List<Parameter> parameters) {
        for ( Parameter parameter : parameters ) {
            if ( parameter.isTargetType() ) {
                return parameter;
            }
        }

        return null;
    }
}
