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

import java.util.List;

public class MappingMethod extends AbstractModelElement {

    private final Type declaringMapper;
    private final String name;
    private final String parameterName;
    private final Type sourceType;
    private final Type targetType;
    private final List<PropertyMapping> propertyMappings;
    private final MappingMethod elementMappingMethod;
    private final boolean isIterableMapping;
    private final String toConversion;

    public MappingMethod(Type declaringMapper, String name, String parameterName, Type sourceType, Type targetType) {
        this.declaringMapper = declaringMapper;
        this.name = name;
        this.parameterName = parameterName;
        this.sourceType = sourceType;
        this.targetType = targetType;
        this.propertyMappings = null;
        this.elementMappingMethod = null;
        this.isIterableMapping = false;
        this.toConversion = null;
    }

    public MappingMethod(Type declaringMapper, String name, String parameterName, Type sourceType, Type targetType,
                         List<PropertyMapping> propertyMappings, MappingMethod elementMappingMethod,
                         String toConversion) {
        this.declaringMapper = declaringMapper;
        this.name = name;
        this.parameterName = parameterName;
        this.sourceType = sourceType;
        this.targetType = targetType;
        this.propertyMappings = propertyMappings;
        this.elementMappingMethod = elementMappingMethod;
        this.isIterableMapping = sourceType.isIterableType() && targetType.isIterableType();
        this.toConversion = toConversion;
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

    public Type getSourceType() {
        return sourceType;
    }

    public Type getTargetType() {
        return targetType;
    }

    public List<PropertyMapping> getPropertyMappings() {
        return propertyMappings;
    }

    public MappingMethod getElementMappingMethod() {
        return elementMappingMethod;
    }

    public boolean isIterableMapping() {
        return isIterableMapping;
    }

    public String getToConversion() {
        return toConversion;
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
