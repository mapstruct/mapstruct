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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.source.MappingOptions;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.model.source.PropertyEntry;
import org.mapstruct.ap.internal.model.source.SourceReference;

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

    public List<Parameter> getProcessedSourceParameters() {
        return processedSourceParameters;
    }

    public Set<String> getHandledTargets() {
        return handledTargets;
    }

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

            Map<PropertyEntry, MappingOptions> optionsByNestedTarget =
                method.getMappingOptions().groupByPoppedTargetReferences();
            for ( Map.Entry<PropertyEntry, MappingOptions> entryByTP : optionsByNestedTarget.entrySet() ) {

                Map<Parameter, MappingOptions> optionsBySourceParam = entryByTP.getValue().groupBySourceParameter();
                boolean forceUpdateMethod = optionsBySourceParam.keySet().size() > 1;
                for ( Map.Entry<Parameter, MappingOptions> entryByParam : optionsBySourceParam.entrySet() ) {

                    SourceReference sourceRef = new SourceReference.BuilderFromProperty()
                        .sourceParameter( entryByParam.getKey() )
                        .name( entryByTP.getKey().getName() )
                        .build();

                    PropertyMapping propertyMapping = new PropertyMapping.PropertyMappingBuilder()
                        .mappingContext( mappingContext )
                        .sourceMethod( method )
                        .targetProperty( entryByTP.getKey() )
                        .targetPropertyName( entryByTP.getKey().getName() )
                        .sourceReference( sourceRef )
                        .existingVariableNames( existingVariableNames )
                        .dependsOn( entryByParam.getValue().collectNestedDependsOn() )
                        .forgeMethodWithMappingOptions( entryByParam.getValue() )
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
    }
}
