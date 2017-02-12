/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.internal.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.source.Mapping;
import org.mapstruct.ap.internal.model.source.MappingOptions;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.model.source.PropertyEntry;
import org.mapstruct.ap.internal.model.source.SourceReference;

import static org.mapstruct.ap.internal.util.Collections.first;

/**
 * This is a helper class that holds the generated {@link PropertyMapping}(s) and all the information associated with
 * it for nested target properties.
 *
 * @author Filip Hrisafov
 */
public class NestedTargetPropertyMappingHolder {

    private final List<Parameter> processedSourceParameters;
    private final Set<String> handledTargets;
    private final List<PropertyMapping> propertyMappings;

    public NestedTargetPropertyMappingHolder(
        List<Parameter> processedSourceParameters, Set<String> handledTargets,
        List<PropertyMapping> propertyMappings) {
        this.processedSourceParameters = processedSourceParameters;
        this.handledTargets = handledTargets;
        this.propertyMappings = propertyMappings;
    }

    /**
     * @return The source parameters that were processed during the generation of the property mappings
     */
    public List<Parameter> getProcessedSourceParameters() {
        return processedSourceParameters;
    }

    /**
     *
     * @return all the targets that were hanled
     */
    public Set<String> getHandledTargets() {
        return handledTargets;
    }

    /**
     *
     * @return the generated property mappings
     */
    public List<PropertyMapping> getPropertyMappings() {
        return propertyMappings;
    }

    public static class Builder {

        private Method method;
        private MappingBuilderContext mappingContext;
        private Set<String> existingVariableNames;

        public Builder method(Method method) {
            this.method = method;
            return this;
        }

        public Builder mappingContext(MappingBuilderContext mappingContext) {
            this.mappingContext = mappingContext;
            return this;
        }

        public Builder existingVariableNames(Set<String> existingVariableNames) {
            this.existingVariableNames = existingVariableNames;
            return this;
        }

        public NestedTargetPropertyMappingHolder build() {
            List<Parameter> processedSourceParameters = new ArrayList<Parameter>();
            Set<String> handledTargets = new HashSet<String>();
            List<PropertyMapping> propertyMappings = new ArrayList<PropertyMapping>();

            Map<PropertyEntry, List<Mapping>> groupedByTP = groupByPoppedTargetReferences( method.getMappingOptions() );

            for ( Map.Entry<PropertyEntry, List<Mapping>> entryByTP : groupedByTP.entrySet() ) {
                Map<Parameter, List<Mapping>> groupedBySourceParam = groupBySourceParameter( entryByTP.getValue() );
                boolean forceUpdateMethod = groupedBySourceParam.keySet().size() > 1;
                for ( Map.Entry<Parameter, List<Mapping>> entryByParam : groupedBySourceParam.entrySet() ) {

                    SourceReference sourceRef = new SourceReference.BuilderFromProperty()
                        .sourceParameter( entryByParam.getKey() )
                        .name( entryByTP.getKey().getName() )
                        .build();
                    MappingOptions mappingOptions = MappingOptions.forMappingsOnly(
                        groupByTargetName( entryByParam.getValue() )
                    );

                    PropertyMapping propertyMapping = new PropertyMapping.PropertyMappingBuilder()
                        .mappingContext( mappingContext )
                        .sourceMethod( method )
                        .targetProperty( entryByTP.getKey() )
                        .targetPropertyName( entryByTP.getKey().getName() )
                        .sourceReference( sourceRef )
                        .existingVariableNames( existingVariableNames )
                        .dependsOn( mappingOptions.collectNestedDependsOn() )
                        .forgeMethodWithMappingOptions( mappingOptions )
                        .forceUpdateMethod( forceUpdateMethod )
                        .build();
                    processedSourceParameters.add( sourceRef.getParameter() );

                    if ( propertyMapping != null ) {
                        propertyMappings.add( propertyMapping );
                    }
                }
                handledTargets.add( entryByTP.getKey().getName() );
            }

            return new NestedTargetPropertyMappingHolder( processedSourceParameters, handledTargets, propertyMappings );
        }

        /**
         * The target references are popped. The {@code List<}{@link Mapping}{@code >} are keyed on the unique first
         * entries of the target references.
         *
         * So, take
         *
         * targetReference 1: propertyEntryX.propertyEntryX1.propertyEntryX1a
         * targetReference 2: propertyEntryX.propertyEntryX2
         * targetReference 3: propertyEntryY.propertyY1
         * targetReference 4: propertyEntryZ
         *
         * will be popped and grouped into entries:
         *
         * propertyEntryX - List ( targetReference1: propertyEntryX1.propertyEntryX1a,
         * targetReference2: propertyEntryX2 )
         * propertyEntryY - List ( targetReference1: propertyEntryY1 )
         *
         * The key will be the former top level property, the MappingOptions will contain the remainders.
         *
         * So, 2 cloned new MappingOptions with popped targetReferences. Also Note that the not nested targetReference4
         * disappeared.
         *
         * @return See above
         */
        public Map<PropertyEntry, List<Mapping>> groupByPoppedTargetReferences(MappingOptions mappingOptions) {
            Map<String, List<Mapping>> mappings = mappingOptions.getMappings();
            // group all mappings based on the top level name before popping
            Map<PropertyEntry, List<Mapping>> mappingsKeyedByProperty = new HashMap<PropertyEntry, List<Mapping>>();
            for ( List<Mapping> mapping : mappings.values() ) {
                Mapping newMapping = first( mapping ).popTargetReference();
                if ( newMapping != null ) {
                    // group properties on current name.
                    PropertyEntry property = first( first( mapping ).getTargetReference().getPropertyEntries() );
                    if ( !mappingsKeyedByProperty.containsKey( property ) ) {
                        mappingsKeyedByProperty.put( property, new ArrayList<Mapping>() );
                    }
                    mappingsKeyedByProperty.get( property ).add( newMapping );
                }
            }

            return mappingsKeyedByProperty;
        }

        /**
         * Splits the List of Mappings into possibly more Mappings based on each source method parameter type.
         *
         * Note: this method is used for forging nested update methods. For that purpose, the same method with all
         * joined mappings should be generated. See also: NestedTargetPropertiesTest#shouldMapNestedComposedTarget
         *
         * @return the split mapping options.
         */
        public Map<Parameter, List<Mapping>> groupBySourceParameter(List<Mapping> mappings) {

            Map<Parameter, List<Mapping>> mappingsKeyedByParameter = new HashMap<Parameter, List<Mapping>>();
            for ( Mapping mapping : mappings ) {
                if ( mapping.getSourceReference() != null && mapping.getSourceReference().isValid() ) {
                    Parameter parameter = mapping.getSourceReference().getParameter();
                    if ( !mappingsKeyedByParameter.containsKey( parameter ) ) {
                        mappingsKeyedByParameter.put( parameter, new ArrayList<Mapping>() );
                    }
                    mappingsKeyedByParameter.get( parameter ).add( mapping );
                }
            }

            return mappingsKeyedByParameter;
        }

        private Map<String, List<Mapping>> groupByTargetName(List<Mapping> mappingList) {
            Map<String, List<Mapping>> result = new HashMap<String, List<Mapping>>();
            for ( Mapping mapping : mappingList ) {
                if ( !result.containsKey( mapping.getTargetName() ) ) {
                    result.put( mapping.getTargetName(), new ArrayList<Mapping>() );
                }
                result.get( mapping.getTargetName() ).add( mapping );
            }
            return result;
        }
    }
}
