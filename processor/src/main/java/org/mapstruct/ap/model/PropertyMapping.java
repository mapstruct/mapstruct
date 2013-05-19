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
public class PropertyMapping {

    private final String sourceReadAccessorName;
    private final String sourceWriteAccessorName;
    private final Type sourceType;
    private final String targetReadAccessorName;
    private final String targetWriteAccessorName;
    private final Type targetType;

    private final MappingMethod mappingMethod;
    private final MappingMethod reverseMappingMethod;
    private final String toConversion;
    private final String fromConversion;

    public PropertyMapping(String sourceReadAccessorName, String sourceWriteAccessorName, Type sourceType, String targetReadAccessorName, String targetWriteAccessorName, Type targetType, MappingMethod mappingMethod, MappingMethod reverseMappingMethod, String toConversion, String fromConversion) {
        this.sourceReadAccessorName = sourceReadAccessorName;
        this.sourceWriteAccessorName = sourceWriteAccessorName;
        this.sourceType = sourceType;
        this.targetReadAccessorName = targetReadAccessorName;
        this.targetWriteAccessorName = targetWriteAccessorName;
        this.targetType = targetType;
        this.mappingMethod = mappingMethod;
        this.reverseMappingMethod = reverseMappingMethod;
        this.toConversion = toConversion;
        this.fromConversion = fromConversion;
    }

    public String getSourceReadAccessorName() {
        return sourceReadAccessorName;
    }

    public String getSourceWriteAccessorName() {
        return sourceWriteAccessorName;
    }

    public Type getSourceType() {
        return sourceType;
    }

    public String getTargetReadAccessorName() {
        return targetReadAccessorName;
    }

    public String getTargetWriteAccessorName() {
        return targetWriteAccessorName;
    }

    public Type getTargetType() {
        return targetType;
    }

    public MappingMethod getMappingMethod() {
        return mappingMethod;
    }

    public MappingMethod getReverseMappingMethod() {
        return reverseMappingMethod;
    }

    public String getToConversion() {
        return toConversion;
    }

    public String getFromConversion() {
        return fromConversion;
    }

    @Override
    public String toString() {
        return "PropertyMapping {" +
            "\n    sourceName='" + sourceReadAccessorName + "/" + sourceWriteAccessorName + "\'," +
            "\n    sourceType=" + sourceType + "," +
            "\n    targetName='" + targetReadAccessorName + "/" + targetWriteAccessorName + "\'," +
            "\n    targetType=" + targetType + "," +
            "\n    mappingMethod=" + mappingMethod + "," +
            "\n    reverseMappingMethod=" + reverseMappingMethod + "," +
            "\n    toConversion='" + toConversion + "\'," +
            "\n    fromConversion='" + fromConversion + "\'," +
            "\n}";
    }
}
