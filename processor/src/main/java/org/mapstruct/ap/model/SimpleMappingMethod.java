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
import java.util.Set;

/**
 * A {@link MappingMethod} implemented by a {@link Mapper} class which maps one
 * bean type to another, optionally configured by one or more
 * {@link PropertyMapping}s.
 *
 * @author Gunnar Morling
 */
public class SimpleMappingMethod extends MappingMethod {

    private final List<PropertyMapping> propertyMappings;

    public SimpleMappingMethod(String name, String parameterName, Type sourceType, Type targetType,
                               List<PropertyMapping> propertyMappings) {
        super( name, parameterName, sourceType, targetType );
        this.propertyMappings = propertyMappings;
    }

    public List<PropertyMapping> getPropertyMappings() {
        return propertyMappings;
    }

    @Override
    public Set<Type> getReferencedTypes() {
        Set<Type> types = super.getReferencedTypes();

        for ( PropertyMapping propertyMapping : propertyMappings ) {
            types.add( propertyMapping.getSourceType() );
            types.add( propertyMapping.getTargetType() );
        }

        return types;
    }
}
