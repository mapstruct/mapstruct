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

import java.util.List;

public class BeanMapping {

    private final Type sourceType;
    private final Type targetType;
    private final List<PropertyMapping> propertyMappings;
    private final MappingMethod mappingMethod;
    private final MappingMethod reverseMappingMethod;
    private final boolean isIterableMapping;
    private final String toConversion;
    private final String fromConversion;

    public BeanMapping(Type sourceType, Type targetType, List<PropertyMapping> propertyMappings, MappingMethod mappingMethod,
                       MappingMethod reverseMappingMethod, String toConversion, String fromConversion) {
        this.sourceType = sourceType;
        this.targetType = targetType;
        this.propertyMappings = propertyMappings;
        this.mappingMethod = mappingMethod;
        this.reverseMappingMethod = reverseMappingMethod;
        this.isIterableMapping = sourceType.isIterableType() && targetType.isIterableType();
        this.toConversion = toConversion;
        this.fromConversion = fromConversion;
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

    public MappingMethod getMappingMethod() {
        return mappingMethod;
    }

    public MappingMethod getReverseMappingMethod() {
        return reverseMappingMethod;
    }

    public boolean isIterableMapping() {
        return isIterableMapping;
    }

    public String getToConversion() {
        return toConversion;
    }

    public String getFromConversion() {
        return fromConversion;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder( "BeanMapping {" );

        sb.append( "\n    sourceType=" + sourceType + ',' );
        sb.append( "\n    targetType=" + targetType + ',' );

        sb.append( "\n    propertyMappings=[\n" );

        for ( PropertyMapping propertyMapping : propertyMappings ) {
            sb.append( "        " + propertyMapping.toString().replaceAll( "\n", "\n        " ) );
        }
        sb.append( "\n    ]" );

        sb.append( "\n    mappingMethod=" + mappingMethod.toString().replaceAll( "\n", "\n    " ) + ',' );
        sb.append( "\n    reverseMappingMethod=" + reverseMappingMethod + ',' );
        sb.append( "\n    toConversion=" + toConversion + ',' );
        sb.append( "\n    fromConversion=" + fromConversion + ',' );
        sb.append( "\n    isIterableMapping=" + isIterableMapping );
        sb.append( "\n}" );

        return sb.toString();
    }
}
