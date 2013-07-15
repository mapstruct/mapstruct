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

import java.beans.Introspector;
import java.util.HashSet;
import java.util.Set;

import org.mapstruct.ap.util.Strings;

/**
 * A method implemented or referenced by a {@link Mapper} class.
 *
 * @author Gunnar Morling
 */
public abstract class MappingMethod extends AbstractModelElement {

    private final String name;
    private final String parameterName;
    private final Type sourceType;
    private final Type targetType;

    protected MappingMethod(String name, String parameterName, Type sourceType, Type targetType) {
        this.name = name;
        this.parameterName = parameterName;
        this.sourceType = sourceType;
        this.targetType = targetType;
    }

    public String getName() {
        return name;
    }

    public String getParameterName() {
        return parameterName;
    }

    public Type getSourceType() {
        return sourceType;
    }

    public Type getTargetType() {
        return targetType;
    }

    @Override
    public Set<Type> getImportTypes() {
        Set<Type> types = new HashSet<Type>();
        types.add( getSourceType() );
        types.add( getTargetType() );

        return types;
    }

    public String getReturnValueName() {
        return Strings.getSaveVariableName(
            Introspector.decapitalize( getTargetType().getName() ),
            getParameterName()
        );
    }

    @Override
    public String toString() {
        return "MappingMethod {" +
            "\n    name='" + name + "\'," +
            "\n    parameterName='" + parameterName + "\'," +
            "\n}";
    }
}
