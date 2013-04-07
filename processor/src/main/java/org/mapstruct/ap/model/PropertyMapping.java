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

public class PropertyMapping {

    private final String sourceName;
    private final Type sourceType;
    private final String targetName;
    private final Type targetType;
    private final MappingMethod mappingMethod;
    private final MappingMethod reverseMappingMethod;
    private final String toConversion;
    private final String fromConversion;

    public PropertyMapping(String sourceName, Type sourceType, String targetName, Type targetType, MappingMethod mappingMethod, MappingMethod reverseMappingMethod, String toConversion, String fromConversion) {
        this.sourceName = sourceName;
        this.sourceType = sourceType;
        this.targetName = targetName;
        this.targetType = targetType;
        this.mappingMethod = mappingMethod;
        this.reverseMappingMethod = reverseMappingMethod;
        this.toConversion = toConversion;
        this.fromConversion = fromConversion;
    }

    public String getSourceName() {
        return sourceName;
    }

    public Type getSourceType() {
        return sourceType;
    }

    public String getTargetName() {
        return targetName;
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
}
