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
package org.mapstruct.ap.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.mapstruct.ap.model.source.Parameter;

/**
 * A method implemented or referenced by a {@link Mapper} class.
 *
 * @author Gunnar Morling
 */
public abstract class MappingMethod extends AbstractModelElement {

    private final String name;
    private final List<Parameter> parameters;
    private final List<Parameter> sourceParameters;
    private final Type resultType;
    private final String resultName;
    private final Type returnType;
    private final boolean existingInstanceMapping;

    protected MappingMethod(String name, List<Parameter> parameters, List<Parameter> sourceParameters, Type resultType,
                            String resultName, Type returnType) {
        this.name = name;
        this.parameters = parameters;
        this.sourceParameters = sourceParameters;
        this.resultType = resultType;
        this.resultName = resultName;
        this.returnType = returnType;
        this.existingInstanceMapping =
            ( null != parameters && null != sourceParameters && parameters.size() > sourceParameters.size() );
    }

    public String getName() {
        return name;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public List<Parameter> getSourceParameters() {
        return sourceParameters;
    }

    public Type getResultType() {
        return resultType;
    }

    public String getResultName() {
        return resultName;
    }

    public Type getReturnType() {
        return returnType;
    }

    public boolean isExistingInstanceMapping() {
        return existingInstanceMapping;
    }

    @Override
    public Set<Type> getImportTypes() {
        Set<Type> types = new HashSet<Type>();

        for ( Parameter param : getParameters() ) {
            types.add( param.getType() );
        }

        types.add( getReturnType() );

        return types;
    }

    @Override
    public String toString() {
        return "MappingMethod {" +
            "\n    name='" + name + "\'," +
            "\n    parameters='" + parameters + "\'," +
            "\n}";
    }
}
