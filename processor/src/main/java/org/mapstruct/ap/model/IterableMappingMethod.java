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
package org.mapstruct.ap.model;

import java.beans.Introspector;
import java.util.Set;

import org.mapstruct.ap.model.common.Parameter;
import org.mapstruct.ap.model.common.Type;
import org.mapstruct.ap.model.source.Method;
import org.mapstruct.ap.util.Strings;

/**
 * A {@link MappingMethod} implemented by a {@link Mapper} class which maps one iterable type to another. The collection
 * elements are mapped either by a {@link TypeConversion} or another mapping method.
 *
 * @author Gunnar Morling
 */
public class IterableMappingMethod extends MappingMethod {

    private final MappingMethodReference elementMappingMethod;
    private final TypeConversion conversion;

    public IterableMappingMethod(Method method, MappingMethodReference elementMappingMethod,
                                 TypeConversion conversion) {
        super( method );
        this.elementMappingMethod = elementMappingMethod;
        this.conversion = conversion;
    }

    public Parameter getSourceParameter() {
        for ( Parameter parameter : getParameters() ) {
            if ( !parameter.isMappingTarget() ) {
                return parameter;
            }
        }

        throw new IllegalStateException( "Method " + this + " has no source parameter." );
    }

    public MappingMethodReference getElementMappingMethod() {
        return elementMappingMethod;
    }

    public TypeConversion getConversion() {
        return conversion;
    }

    @Override
    public Set<Type> getImportTypes() {
        Set<Type> types = super.getImportTypes();

        if ( conversion != null ) {
            types.addAll( conversion.getImportTypes() );
        }

        return types;
    }

    public String getLoopVariableName() {
        return Strings.getSaveVariableName(
            Introspector.decapitalize(
                getSourceParameter().getType()
                    .getTypeParameters()
                    .get( 0 )
                    .getName()
            ), getParameterNames()
        );
    }
}
