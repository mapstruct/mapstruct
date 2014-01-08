/**
 *  Copyright 2012-2014 Gunnar Morling (http://www.gunnarmorling.de/)
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.mapstruct.ap.model.source.Method;

/**
 * A {@link MappingMethod} implemented by a {@link Mapper} class which maps one
 * bean type to another, optionally configured by one or more
 * {@link PropertyMapping}s.
 *
 * @author Gunnar Morling
 */
public class BeanMappingMethod extends MappingMethod {

    private final List<PropertyMapping> propertyMappings;

    public BeanMappingMethod(Method method, List<PropertyMapping> propertyMappings) {
        super( method );
        this.propertyMappings = propertyMappings;
    }

    public List<PropertyMapping> getPropertyMappings() {
        return propertyMappings;
    }

    public Map<String, List<PropertyMapping>> getPropertyMappingsByParameter() {
        Map<String, List<PropertyMapping>> mappingsByParameter = new HashMap<String, List<PropertyMapping>>();

        for ( Parameter sourceParameter : getSourceParameters() ) {
            ArrayList<PropertyMapping> mappingsOfParameter = new ArrayList<PropertyMapping>();
            mappingsByParameter.put( sourceParameter.getName(), mappingsOfParameter );
            for ( PropertyMapping mapping : propertyMappings ) {
                if ( mapping.getSourceBeanName().equals( sourceParameter.getName() ) ) {
                    mappingsOfParameter.add( mapping );
                }
            }
        }

        return mappingsByParameter;
    }

    @Override
    public Set<Type> getImportTypes() {
        Set<Type> types = super.getImportTypes();

        for ( PropertyMapping propertyMapping : propertyMappings ) {
            types.addAll( propertyMapping.getImportTypes() );
        }

        return types;
    }
}
