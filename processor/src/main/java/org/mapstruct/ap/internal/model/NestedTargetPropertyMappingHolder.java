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
import org.mapstruct.ap.internal.util.Extractor;

import static org.mapstruct.ap.internal.util.Collections.first;

/**
 * This is a helper class that holds the generated {@link PropertyMapping}(s) and all the information associated with
 * it for nested target properties.
 *
 * @author Filip Hrisafov
 */
public class NestedTargetPropertyMappingHolder {

    private static final Extractor<SourceReference, Parameter> SOURCE_PARAM_EXTRACTOR = new
        Extractor<SourceReference, Parameter>() {
            @Override
            public Parameter apply(SourceReference sourceReference) {
                return sourceReference.getParameter();
            }
        };

    private static final Extractor<SourceReference, PropertyEntry> PROPERTY_EXTRACTOR = new
        Extractor<SourceReference, PropertyEntry>() {
            @Override
            public PropertyEntry apply(SourceReference sourceReference) {
                return first( sourceReference.getPropertyEntries() );
            }
        };

    private final List<Parameter> processedSourceParameters;
    private final Set<String> handledTargets;
    private final List<PropertyMapping> propertyMappings;
    private final Map<PropertyEntry, List<Mapping>> unprocessedDefinedTarget;

    public NestedTargetPropertyMappingHolder(
        List<Parameter> processedSourceParameters, Set<String> handledTargets,
        List<PropertyMapping> propertyMappings,
        Map<PropertyEntry, List<Mapping>> unprocessedDefinedTarget) {
        this.processedSourceParameters = processedSourceParameters;
        this.handledTargets = handledTargets;
        this.propertyMappings = propertyMappings;
        this.unprocessedDefinedTarget = unprocessedDefinedTarget;
    }

    /**
     * @return The source parameters that were processed during the generation of the property mappings
     */
    public List<Parameter> getProcessedSourceParameters() {
        return processedSourceParameters;
    }

    /**
     * @return all the targets that were hanled
     */
    public Set<String> getHandledTargets() {
        return handledTargets;
    }

    /**
     * @return the generated property mappings
     */
    public List<PropertyMapping> getPropertyMappings() {
        return propertyMappings;
    }

    /**
     * @return a map of all the unprocessed defined targets that can be applied to name forged base methods
     */
    public Map<PropertyEntry, List<Mapping>> getUnprocessedDefinedTarget() {
        return unprocessedDefinedTarget;
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

            GroupedTargetReferences groupedByTP = groupByTargetReferences( method.getMappingOptions() );
            Map<PropertyEntry, List<Mapping>> unprocessedDefinedTarget = new HashMap<PropertyEntry, List<Mapping>>();

            for ( Map.Entry<PropertyEntry, List<Mapping>> entryByTP : groupedByTP.poppedTargetReferences.entrySet() ) {
                PropertyEntry targetProperty = entryByTP.getKey();
                GroupedBySourceParameters groupedBySourceParam = groupBySourceParameter(
                    entryByTP.getValue(),
                    groupedByTP.singleTargetReferences.get( targetProperty )
                );
                boolean forceUpdateMethod = groupedBySourceParam.groupedBySourceParameter.keySet().size() > 1;

                unprocessedDefinedTarget.put( targetProperty, groupedBySourceParam.notProcessedAppliesToAll );
                for ( Map.Entry<Parameter, List<Mapping>> entryByParam : groupedBySourceParam
                    .groupedBySourceParameter.entrySet() ) {

                    Parameter sourceParameter = entryByParam.getKey();

                    GroupedSourceReferences groupedSourceReferences = groupByPoppedSourceReferences(
                        entryByParam.getValue(),
                        groupedByTP.singleTargetReferences.get( targetProperty )
                    );

                    for ( Map.Entry<PropertyEntry, List<Mapping>> entryBySP : groupedSourceReferences
                        .groupedBySourceReferences
                        .entrySet() ) {
                        PropertyEntry sourceEntry = entryBySP.getKey();
                        MappingOptions sourceMappingOptions = MappingOptions.forMappingsOnly(
                            groupByTargetName( entryBySP.getValue() ),
                            forceUpdateMethod
                        );
                        SourceReference sourceRef = new SourceReference.BuilderFromProperty()
                            .sourceParameter( sourceParameter )
                            .type( sourceEntry.getType() )
                            .readAccessor( sourceEntry.getReadAccessor() )
                            .presenceChecker( sourceEntry.getPresenceChecker() )
                            .name( targetProperty.getName() )
                            .build();

                        PropertyMapping propertyMapping = createPropertyMappingForNestedTarget(
                            sourceMappingOptions,
                            targetProperty,
                            sourceRef,
                            forceUpdateMethod
                        );

                        if ( propertyMapping != null ) {
                            propertyMappings.add( propertyMapping );
                        }

                        handledTargets.add( entryByTP.getKey().getName() );
                    }

                    if ( !groupedSourceReferences.nonNested.isEmpty() ) {
                        MappingOptions nonNestedOptions = MappingOptions.forMappingsOnly(
                            groupByTargetName( groupedSourceReferences.nonNested ),
                            true
                        );
                        SourceReference reference = new SourceReference.BuilderFromProperty()
                            .sourceParameter( sourceParameter )
                            .name( targetProperty.getName() )
                            .build();

                        PropertyMapping propertyMapping = createPropertyMappingForNestedTarget(
                            nonNestedOptions,
                            targetProperty,
                            reference,
                            forceUpdateMethod
                        );

                        if ( propertyMapping != null ) {
                            propertyMappings.add( propertyMapping );
                        }

                        handledTargets.add( entryByTP.getKey().getName() );
                    }

                    unprocessedDefinedTarget.put( targetProperty, groupedSourceReferences.notProcessedAppliesToAll );
                }
            }

            return new NestedTargetPropertyMappingHolder(
                processedSourceParameters,
                handledTargets,
                propertyMappings,
                unprocessedDefinedTarget
            );
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
         * If the target reference cannot be popped it is stored in a different map. That looks like:
         *
         * propertyEntryZ - List ( targetReference4: propertyEntryZ )
         *
         * @param mappingOptions that need to be used to create the {@link GroupedTargetReferences}
         *
         * @return See above
         */
        private GroupedTargetReferences groupByTargetReferences(MappingOptions mappingOptions) {
            Map<String, List<Mapping>> mappings = mappingOptions.getMappings();
            // group all mappings based on the top level name before popping
            Map<PropertyEntry, List<Mapping>> mappingsKeyedByProperty = new HashMap<PropertyEntry, List<Mapping>>();
            Map<PropertyEntry, List<Mapping>> singleTargetReferences = new HashMap<PropertyEntry, List<Mapping>>();
            for ( List<Mapping> mapping : mappings.values() ) {
                PropertyEntry property = first( first( mapping ).getTargetReference().getPropertyEntries() );
                Mapping newMapping = first( mapping ).popTargetReference();
                if ( newMapping != null ) {
                    // group properties on current name.
                    if ( !mappingsKeyedByProperty.containsKey( property ) ) {
                        mappingsKeyedByProperty.put( property, new ArrayList<Mapping>() );
                    }
                    mappingsKeyedByProperty.get( property ).add( newMapping );
                }
                else {
                    if ( !singleTargetReferences.containsKey( property ) ) {
                        singleTargetReferences.put( property, new ArrayList<Mapping>() );
                    }
                    singleTargetReferences.get( property ).add( first( mapping ) );
                }
            }

            return new GroupedTargetReferences( mappingsKeyedByProperty, singleTargetReferences );
        }

        /**
         * Splits the List of Mappings into possibly more Mappings based on each source method parameter type.
         *
         * Note: this method is used for forging nested update methods. For that purpose, the same method with all
         * joined mappings should be generated. See also: NestedTargetPropertiesTest#shouldMapNestedComposedTarget
         *
         * @param mappings that mappings that need to be used for the grouping
         * @param singleTargetReferences a List containing all non-nested mappings for the same grouped target
         * property as the {@code mappings}
         * @return the split mapping options.
         */
        private GroupedBySourceParameters groupBySourceParameter(List<Mapping> mappings,
            List<Mapping> singleTargetReferences) {

            Map<Parameter, List<Mapping>> mappingsKeyedByParameter = new HashMap<Parameter, List<Mapping>>();
            List<Mapping> appliesToAll = new ArrayList<Mapping>();
            for ( Mapping mapping : mappings ) {
                if ( mapping.getSourceReference() != null && mapping.getSourceReference().isValid() ) {
                    Parameter parameter = mapping.getSourceReference().getParameter();
                    if ( !mappingsKeyedByParameter.containsKey( parameter ) ) {
                        mappingsKeyedByParameter.put( parameter, new ArrayList<Mapping>() );
                    }
                    mappingsKeyedByParameter.get( parameter ).add( mapping );
                }
                else {
                    appliesToAll.add( mapping );
                }
            }

            populateWithSingleTargetReferences(
                mappingsKeyedByParameter,
                singleTargetReferences,
                SOURCE_PARAM_EXTRACTOR
            );

            for ( Map.Entry<Parameter, List<Mapping>> entry : mappingsKeyedByParameter.entrySet() ) {
                entry.getValue().addAll( appliesToAll );
            }

            List<Mapping> notProcessAppliesToAll =
                mappingsKeyedByParameter.isEmpty() ? appliesToAll : new ArrayList<Mapping>();

            return new GroupedBySourceParameters( mappingsKeyedByParameter, notProcessAppliesToAll );
        }

        /**
         * Creates a nested grouping by popping the source mappings. See the description of the class to see what is
         * generated.
         *
         * @param mappings the list of {@link Mapping} that needs to be used for grouping on popped source references
         * @param singleTargetReferences the single target references that match the source mappings
         *
         * @return the Grouped Source References
         */
        private GroupedSourceReferences groupByPoppedSourceReferences(List<Mapping> mappings,
            List<Mapping> singleTargetReferences) {
            List<Mapping> nonNested = new ArrayList<Mapping>();
            List<Mapping> appliesToAll = new ArrayList<Mapping>();
            // group all mappings based on the top level name before popping
            Map<PropertyEntry, List<Mapping>> mappingsKeyedByProperty = new HashMap<PropertyEntry, List<Mapping>>();
            for ( Mapping mapping : mappings ) {

                Mapping newMapping = mapping.popSourceReference();
                if ( newMapping != null ) {
                    // group properties on current name.
                    PropertyEntry property = first( mapping.getSourceReference().getPropertyEntries() );
                    if ( !mappingsKeyedByProperty.containsKey( property ) ) {
                        mappingsKeyedByProperty.put( property, new ArrayList<Mapping>() );
                    }
                    mappingsKeyedByProperty.get( property ).add( newMapping );
                }
                //This is an ignore, or some expression, or a default. We apply these to all
                else if ( mapping.getSourceReference() == null ) {
                    appliesToAll.add( mapping );
                }
                else {
                    nonNested.add( mapping );
                }
            }

            populateWithSingleTargetReferences( mappingsKeyedByProperty, singleTargetReferences, PROPERTY_EXTRACTOR );

            for ( Map.Entry<PropertyEntry, List<Mapping>> entry : mappingsKeyedByProperty.entrySet() ) {
                entry.getValue().addAll( appliesToAll );
            }

            List<Mapping> notProcessedAppliesToAll = new ArrayList<Mapping>();
            // If the applied to all were not added to all properties because they were empty, and the non-nested
            // one are not empty, add them to the non-nested ones
            if ( mappingsKeyedByProperty.isEmpty() && !nonNested.isEmpty() ) {
                nonNested.addAll( appliesToAll );
            }
            // Otherwise if the non-nested are empty, just pass them along so they can be processed later on
            else if ( mappingsKeyedByProperty.isEmpty() && nonNested.isEmpty() ) {
                notProcessedAppliesToAll.addAll( appliesToAll );
            }

            return new GroupedSourceReferences(
                mappingsKeyedByProperty,
                nonNested,
                notProcessedAppliesToAll
            );
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

        private PropertyMapping createPropertyMappingForNestedTarget(MappingOptions mappingOptions,
            PropertyEntry targetProperty, SourceReference sourceReference, boolean forceUpdateMethod) {
            PropertyMapping propertyMapping = new PropertyMapping.PropertyMappingBuilder()
                .mappingContext( mappingContext )
                .sourceMethod( method )
                .targetProperty( targetProperty )
                .targetPropertyName( targetProperty.getName() )
                .sourceReference( sourceReference )
                .existingVariableNames( existingVariableNames )
                .dependsOn( mappingOptions.collectNestedDependsOn() )
                .forgeMethodWithMappingOptions( mappingOptions )
                .forceUpdateMethod( forceUpdateMethod )
                .forgedNamedBased( false )
                .build();
            return propertyMapping;
        }

        /**
         * If a single target mapping has a valid {@link SourceReference} and the {@link SourceReference} has more
         * then 0 {@link PropertyEntry} and if the {@code map} does not contain an entry with the extracted key then
         * an entry with the extracted key and an empty list is added.
         *
         * @param map that needs to be populated
         * @param singleTargetReferences to use
         * @param keyExtractor to be used to extract a key
         */
        private <K> void populateWithSingleTargetReferences(Map<K, List<Mapping>> map,
            List<Mapping> singleTargetReferences, Extractor<SourceReference, K> keyExtractor) {
            if ( singleTargetReferences != null ) {
                //This are non nested target references only their property needs to be added as they most probably
                // define it
                for ( Mapping mapping : singleTargetReferences ) {
                    if ( mapping.getSourceReference() != null && mapping.getSourceReference().isValid()
                        && !mapping.getSourceReference().getPropertyEntries().isEmpty() ) {
                        //TODO is this OK? Why there are no propertyEntries? For the Inverse LetterMapper for example
                        K key = keyExtractor.apply( mapping.getSourceReference() );
                        if ( !map.containsKey( key ) ) {
                            map.put( key, new ArrayList<Mapping>() );
                        }
                    }
                }
            }
        }
    }

    private static class GroupedBySourceParameters {
        private final Map<Parameter, List<Mapping>> groupedBySourceParameter;
        private final List<Mapping> notProcessedAppliesToAll;

        private GroupedBySourceParameters(Map<Parameter, List<Mapping>> groupedBySourceParameter,
            List<Mapping> notProcessedAppliesToAll) {
            this.groupedBySourceParameter = groupedBySourceParameter;
            this.notProcessedAppliesToAll = notProcessedAppliesToAll;
        }
    }

    /**
     * The grouped target references. This class contains the poppedTarget references and the single target
     * references (target references that were not nested).
     */
    private static class GroupedTargetReferences {
        private final Map<PropertyEntry, List<Mapping>> poppedTargetReferences;
        private final Map<PropertyEntry, List<Mapping>> singleTargetReferences;

        private GroupedTargetReferences(Map<PropertyEntry, List<Mapping>> poppedTargetReferences,
            Map<PropertyEntry, List<Mapping>> singleTargetReferences) {
            this.poppedTargetReferences = poppedTargetReferences;
            this.singleTargetReferences = singleTargetReferences;
        }
    }

    /**
     * This class is used to group Source references in respected to the nestings that they have.
     *
     * This class contains all groupings by Property Entries if they are nested, or a list of all the other options
     * that could not have been popped.
     *
     * So, take
     *
     * sourceReference 1: propertyEntryX.propertyEntryX1.propertyEntryX1a
     * sourceReference 2: propertyEntryX.propertyEntryX2
     * sourceReference 3: propertyEntryY.propertyY1
     * sourceReference 4: propertyEntryZ
     * sourceReference 5: propertyEntryZ2
     *
     * We will have a map with entries:
     * <pre>
     *
     * propertyEntryX - ( sourceReference1: propertyEntryX1.propertyEntryX1a,
     *                    sourceReference2 propertyEntryX2 )
     * propertyEntryY - ( sourceReference1: propertyEntryY1 )
     *
     * </pre>
     *
     * If non-nested mappings were found they will be located in a List.
     * <pre>
     * sourceReference 4: propertyEntryZ
     * sourceReference 5: propertyEntryZ2
     * </pre>
     *
     * <br />
     * If Mappings that should apply to all were found, but no grouping was found, they will be located in a
     * different list:
     */
    private static class GroupedSourceReferences {

        private final Map<PropertyEntry, List<Mapping>> groupedBySourceReferences;
        private final List<Mapping> nonNested;
        private final List<Mapping> notProcessedAppliesToAll;

        private GroupedSourceReferences(Map<PropertyEntry, List<Mapping>> groupedBySourceReferences,
            List<Mapping> nonNested, List<Mapping> notProcessedAppliesToAll) {
            this.groupedBySourceReferences = groupedBySourceReferences;
            this.nonNested = nonNested;
            this.notProcessedAppliesToAll = notProcessedAppliesToAll;

        }
    }
}
