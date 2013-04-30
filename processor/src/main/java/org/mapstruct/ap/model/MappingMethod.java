/**
 *  Copyright 2012-2013 Gunnar Morling (http://www.gunnarmorling.de/)
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
    private final MappingMethod elementMappingMethod;

    public MappingMethod(Type declaringMapper, String name, String parameterName) {
        this.declaringMapper = declaringMapper;
        this.name = name;
        this.parameterName = parameterName;
        this.elementMappingMethod = null;
    }

    public MappingMethod(Type declaringMapper, String name, String parameterName, MappingMethod elementMappingMethod) {
        this.declaringMapper = declaringMapper;
        this.name = name;
        this.parameterName = parameterName;
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

    @Override
    public String toString() {
        return "MappingMethod {" +
            "\n    name='" + name + "\'," +
            "\n    parameterName='" + parameterName + "\'," +
            "\n    elementMappingMethod=" + elementMappingMethod +
            "\n}";
    }
}
