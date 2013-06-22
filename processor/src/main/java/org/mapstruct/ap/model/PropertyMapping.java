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

/**
 * Represents the mapping between a source and target property, e.g. from
 * {@code String Source#foo} to {@code int Target#bar}. Name and type of source
 * and target property can differ. If they have different types, the mapping
 * must either refer to a mapping method or a conversion.
 *
 * @author Gunnar Morling
 */
public class PropertyMapping extends AbstractModelElement {

    private final String sourceBeanName;
    private final String targetBeanName;
    private final String sourceAccessorName;
    private final Type sourceType;
    private final String targetAccessorName;
    private final Type targetType;

    private final MappingMethodReference mappingMethod;
    private final String conversion;

    public PropertyMapping(String sourceBeanName, String targetBeanName, String sourceAccessorName, Type sourceType,
                           String targetAccessorName, Type targetType, MappingMethodReference mappingMethod,
                           String conversion) {
        this.sourceBeanName = sourceBeanName;
        this.targetBeanName = targetBeanName;
        this.sourceAccessorName = sourceAccessorName;
        this.sourceType = sourceType;
        this.targetAccessorName = targetAccessorName;
        this.targetType = targetType;
        this.mappingMethod = mappingMethod;
        this.conversion = conversion;
    }

    public String getSourceBeanName() {
        return sourceBeanName;
    }

    public String getTargetBeanName() {
        return targetBeanName;
    }

    public String getSourceAccessorName() {
        return sourceAccessorName;
    }

    public Type getSourceType() {
        return sourceType;
    }

    public String getTargetAccessorName() {
        return targetAccessorName;
    }

    public Type getTargetType() {
        return targetType;
    }

    public MappingMethodReference getMappingMethod() {
        return mappingMethod;
    }

    public String getConversion() {
        return conversion;
    }

    @Override
    public String toString() {
        return "PropertyMapping {" +
            "\n    sourceName='" + sourceAccessorName + "\'," +
            "\n    sourceType=" + sourceType + "," +
            "\n    targetName='" + targetAccessorName + "\'," +
            "\n    targetType=" + targetType + "," +
            "\n    mappingMethod=" + mappingMethod + "," +
            "\n    Conversion='" + conversion + "\'," +
            "\n}";
    }
}
