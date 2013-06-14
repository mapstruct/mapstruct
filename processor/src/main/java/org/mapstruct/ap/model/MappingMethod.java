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

public class MappingMethod {

    private final Type declaringMapper;
    private final String name;
    private final String parameterName;
    private final String targetParameterName;
    private final MappingMethod elementMappingMethod;

    public MappingMethod(Type declaringMapper, String name, String parameterName, String targetParameterName) {
        this.declaringMapper = declaringMapper;
        this.name = name;
        this.parameterName = parameterName;
        this.targetParameterName = targetParameterName;
        this.elementMappingMethod = null;
    }

    public MappingMethod(Type declaringMapper, String name, String parameterName, String targetParameterName,
                         MappingMethod elementMappingMethod) {
        this.declaringMapper = declaringMapper;
        this.name = name;
        this.parameterName = parameterName;
        this.targetParameterName = targetParameterName;
        this.elementMappingMethod = elementMappingMethod;
    }

    public Type getDeclaringMapper() {
        return declaringMapper;
    }

    public String getName() {
        return name;
    }

    public String getParameterName() {
        return parameterName;
    }

    public MappingMethod getElementMappingMethod() {
        return elementMappingMethod;
    }

    public boolean isGenerationRequired() {
        return declaringMapper == null;
    }

    public String getTargetParameterName() {
        return targetParameterName;
    }

    @Override
    public String toString() {
        return "MappingMethod {" +
            "\n    name='" + name + "\'," +
            "\n    parameterName='" + parameterName + "\'," +
            "\n    elementMappingMethod=" + elementMappingMethod +
            "\n}";
    }
}
