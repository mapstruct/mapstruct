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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.source.Mapping;
import org.mapstruct.ap.internal.model.source.MappingOptions;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.model.source.PropertyEntry;
import org.mapstruct.ap.internal.model.source.SourceReference;
import org.mapstruct.ap.internal.model.source.TargetReference;
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
                return sourceReference.getPropertyEntries().isEmpty() ? null :
                    first( sourceReference.getPropertyEntries() );
            }
        };

    private final List<Parameter> processedSourceParameters;
    private final Set<String> handledTargets;
    private final List<PropertyMapping> propertyMappings;
    private final Map<PropertyEntry, List<Mapping>> unprocessedDefinedTarget;
    private final boolean errorOccurred;

    public NestedTargetPropertyMappingHolder(
        List<Parameter> processedSourceParameters, Set<String> handledTargets,
        List<PropertyMapping> propertyMappings,
        Map<PropertyEntry, List<Mapping>> unprocessedDefinedTarget, boolean errorOccurred) {
        this.processedSourceParameters = processedSourceParameters;
        this.handledTargets = handledTargets;
        this.propertyMappings = propertyMappings;
        this.unprocessedDefinedTarget = unprocessedDefinedTarget;
        this.errorOccurred = errorOccurred;
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

    /**
     * @return {@code true} if an error occurred during the creation of the nested mappings
     */
    public boolean hasErrorOccurred() {
        return errorOccurred;
    }

    public static class Builder {

        private Method method;
        private MappingBuilderContext mappingContext;
        private Set<String> existingVariableNames;
        private List<PropertyMapping> propertyMappings;
        private Set<String> handledTargets;

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
            handledTargets = new HashSet<String>();
            propertyMappings = new ArrayList<PropertyMapping>();

            // first we group by the first property in the target properties and for each of those
            // properties we get the new mappings as if the first property did not exist.
            GroupedTargetReferences groupedByTP = groupByTargetReferences( method.getMappingOptions() );
            Map<PropertyEntry, List<Mapping>> unprocessedDefinedTarget
                = new LinkedHashMap<PropertyEntry, List<Mapping>>();

            for ( Map.Entry<PropertyEntry, List<Mapping>> entryByTP : groupedByTP.poppedTargetReferences.entrySet() ) {
                PropertyEntry targetProperty = entryByTP.getKey();
                //Now we are grouping the already popped mappings by the source parameter(s) of the method
                GroupedBySourceParameters groupedBySourceParam = groupBySourceParameter(
                    entryByTP.getValue(),
                    groupedByTP.singleTargetReferences.get( targetProperty )
                );
                boolean multipleSourceParametersForTP =
                    groupedBySourceParam.groupedBySourceParameter.keySet().size() > 1;

                // All not processed mappings that should have been applied to all are part of the unprocessed
                // defined targets
                unprocessedDefinedTarget.put( targetProperty, groupedBySourceParam.notProcessedAppliesToAll );
                for ( Map.Entry<Parameter, List<Mapping>> entryByParam : groupedBySourceParam
                    .groupedBySourceParameter.entrySet() ) {

                    Parameter sourceParameter = entryByParam.getKey();

                    // Lastly we need to group by the source references. This will allow us to actually create
                    // the next mappings by popping source elements
                    GroupedSourceReferences groupedSourceReferences = groupByPoppedSourceReferences(
                        entryByParam,
                        groupedByTP.singleTargetReferences.get( targetProperty )
                    );

                    // For all the groupedBySourceReferences we need to create property mappings
                    // from the Mappings and not restrict on the defined mappings (allow to forge name based mapping)
                    // if we have composite methods i.e. more then 2 parameters then we have to force a creation
                    // of an update method in our generation
                    for ( Map.Entry<PropertyEntry, List<Mapping>> entryBySP : groupedSourceReferences
                        .groupedBySourceReferences
                        .entrySet() ) {
                        PropertyEntry sourceEntry = entryBySP.getKey();
                        boolean forceUpdateMethodOrNonNestedReferencesPresent =
                            multipleSourceParametersForTP || !groupedSourceReferences.nonNested.isEmpty();
                        // If there are multiple source parameters that are mapped to the target reference
                        // then we restrict the mapping only to the defined mappings. And we create MappingOptions
                        // for forged methods (which means that any unmapped target properties are ignored)
                        // MappingOptions for forged methods is also created if we have something like this:
                        //@Mappings({
                        //    @Mapping(target = "vehicleInfo", source = "vehicleTypeInfo"),
                        //    @Mapping(target = "vehicleInfo.images", source = "images")
                        //})
                        // See Issue1269Test, Issue1247Test, AutomappingAndNestedTest for more info as well
                        MappingOptions sourceMappingOptions = MappingOptions.forMappingsOnly(
                            groupByTargetName( entryBySP.getValue() ),
                            multipleSourceParametersForTP,
                            forceUpdateMethodOrNonNestedReferencesPresent
                        );
                        SourceReference sourceRef = new SourceReference.BuilderFromProperty()
                            .sourceParameter( sourceParameter )
                            .type( sourceEntry.getType() )
                            .readAccessor( sourceEntry.getReadAccessor() )
                            .presenceChecker( sourceEntry.getPresenceChecker() )
                            .name( targetProperty.getName() )
                            .build();

                        // If we have multiple source parameters that are mapped to the target reference, or
                        // parts of the nested properties are mapped to different sources (see comment above as well)
                        // we would force an update method
                        PropertyMapping propertyMapping = createPropertyMappingForNestedTarget(
                            sourceMappingOptions,
                            targetProperty,
                            sourceRef,
                            forceUpdateMethodOrNonNestedReferencesPresent
                        );

                        if ( propertyMapping != null ) {
                            propertyMappings.add( propertyMapping );
                        }

                        handledTargets.add( entryByTP.getKey().getName() );
                    }

                    // For the nonNested mappings (assymetric) Mappings we also forge mappings
                    // However, here we do not forge name based mappings and we only
                    // do update on the defined Mappings.
                    if ( !groupedSourceReferences.nonNested.isEmpty() ) {
                        MappingOptions nonNestedOptions = MappingOptions.forMappingsOnly(
                            groupByTargetName( groupedSourceReferences.nonNested ),
                            true
                        );
                        SourceReference reference = new SourceReference.BuilderFromProperty()
                            .sourceParameter( sourceParameter )
                            .name( targetProperty.getName() )
                            .build();

                        boolean forceUpdateMethodForNonNested =
                            multipleSourceParametersForTP ||
                                !groupedSourceReferences.groupedBySourceReferences.isEmpty();
                        // If an update method is forced or there are groupedBySourceReferences then we must create
                        // an update method. The reason is that they might be for the same reference and we should
                        // use update for it
                        PropertyMapping propertyMapping = createPropertyMappingForNestedTarget(
                            nonNestedOptions,
                            targetProperty,
                            reference,
                            forceUpdateMethodForNonNested
                        );

                        if ( propertyMapping != null ) {
                            propertyMappings.add( propertyMapping );
                        }

                        handledTargets.add( entryByTP.getKey().getName() );
                    }

                    handleSourceParameterMappings(
                        groupedSourceReferences.sourceParameterMappings,
                        targetProperty,
                        sourceParameter,
                        multipleSourceParametersForTP
                    );

                    unprocessedDefinedTarget.put( targetProperty, groupedSourceReferences.notProcessedAppliesToAll );
                }
            }

            return new NestedTargetPropertyMappingHolder(
                processedSourceParameters,
                handledTargets,
                propertyMappings,
                unprocessedDefinedTarget,
                groupedByTP.errorOccurred
            );
        }

        /**
         * Handle the {@link PropertyMapping} creation for source parameter mappings.
         *
         * @param sourceParameterMappings the source parameter mappings for which property mapping should be done
         * @param targetProperty the target property that is being mapped
         * @param sourceParameter the source parameter that is used
         * @param forceUpdateMethod whether we need to force an update method
         */
        private void handleSourceParameterMappings(List<Mapping> sourceParameterMappings, PropertyEntry targetProperty,
            Parameter sourceParameter, boolean forceUpdateMethod) {
            if ( !sourceParameterMappings.isEmpty() ) {
                // The source parameter mappings have no mappings, the source name is actually the parameter itself
                MappingOptions nonNestedOptions = MappingOptions.forMappingsOnly(
                    new HashMap<String, List<Mapping>>(),
                    false,
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

                handledTargets.add( targetProperty.getName() );
            }
        }

        /**
         * The target references are popped. The {@code List<}{@link Mapping}{@code >} are keyed on the unique first
         * entries of the target references.
         *
         * <p>
         * <p>
         * Group all target references by their first property and for each such mapping use a new one where the
         * first property will be removed from it. If a {@link org.mapstruct.Mapping} cannot be popped, i.e. it
         * contains a non nested target property just keep it as is (this is usually needed to control how an
         * intermediary level can be mapped).
         *
         * <p>
         * <p>
         * We start with the following mappings:
         *
         * <pre>
         * {@literal @}Mapping(target = "fish.kind", source = "fish.type"),
         * {@literal @}Mapping(target = "fish.name", ignore = true),
         * {@literal @}Mapping(target = "ornament", ignore = true ),
         * {@literal @}Mapping(target = "material.materialType", source = "material"),
         * {@literal @}Mapping(target = "document", source = "report"),
         * {@literal @}Mapping(target = "document.organisation.name", source = "report.organisationName")
         * </pre>
         *
         * We will get this:
         *
         * <pre>
         * // All target references are popped and grouped by their first property
         * GroupedTargetReferences.poppedTargetReferences {
         *     fish:     {@literal @}Mapping(target = "kind", source = "fish.type"),
         *               {@literal @}Mapping(target = "name", ignore = true);
         *     material: {@literal @}Mapping(target = "materialType", source = "material");
         *     document: {@literal @}Mapping(target = "organization.name", source = "report.organizationName");
         * }
         *
         * //This references are not nested and we they stay as they were
         * GroupedTargetReferences.singleTargetReferences {
         *     document: {@literal @}Mapping(target = "document", source = "report");
         *     ornament: {@literal @}Mapping(target = "ornament", ignore = true );
         * }
         * </pre>
         *
         * @param mappingOptions that need to be used to create the {@link GroupedTargetReferences}
         *
         * @return See above
         */
        private GroupedTargetReferences groupByTargetReferences(MappingOptions mappingOptions) {
            Map<String, List<Mapping>> mappings = mappingOptions.getMappings();
            // group all mappings based on the top level name before popping
            Map<PropertyEntry, List<Mapping>> mappingsKeyedByProperty
                = new LinkedHashMap<PropertyEntry, List<Mapping>>();
            Map<PropertyEntry, List<Mapping>> singleTargetReferences
                = new LinkedHashMap<PropertyEntry, List<Mapping>>();
            boolean errorOccurred = false;
            for ( List<Mapping> mapping : mappings.values() ) {
                Mapping firstMapping = first( mapping );
                TargetReference targetReference = firstMapping.getTargetReference();
                if ( !targetReference.isValid() ) {
                    errorOccurred = true;
                    continue;
                }
                PropertyEntry property = first( targetReference.getPropertyEntries() );
                Mapping newMapping = firstMapping.popTargetReference();
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
                    singleTargetReferences.get( property ).add( firstMapping );
                }
            }

            return new GroupedTargetReferences( mappingsKeyedByProperty, singleTargetReferences, errorOccurred );
        }

        /**
         * Splits the List of Mappings into possibly more Mappings based on each source method parameter type.
         *
         * Note: this method is used for forging nested update methods. For that purpose, the same method with all
         * joined mappings should be generated. See also: NestedTargetPropertiesTest#shouldMapNestedComposedTarget
         *
         * Mappings:
         * <pre>
         * {@literal @}Mapping(target = "organization.name", source = "report.organizationName");
         * </pre>
         *
         * singleTargetReferences:
         * <pre>
         * {@literal @}Mapping(target = "document", source = "report");
         * </pre>
         *
         * We assume that all properties belong to the same source parameter (fish). We are getting this in return:
         *
         * <pre>
         * GroupedBySourceParameters.groupedBySourceParameter {
         *     fish: {@literal @}Mapping(target = "organization.name", source = "report.organizationName");
         * }
         *
         * GroupedBySourceParameters.notProcessedAppliesToAll {} //empty
         *
         * </pre>
         *
         * Notice how the {@code singleTargetReferences} are missing. They are used for situations when there are
         * mappings without source. Such as:
         * Mappings:
         * <pre>
         * {@literal @}Mapping(target = "organization.name", expression="java(\"Dunno\")");
         * </pre>
         *
         * singleTargetReferences:
         * <pre>
         * {@literal @}Mapping(target = "document", source = "report");
         * </pre>
         *
         * The mappings have no source reference so we cannot extract the source parameter from them. When mappings
         * have no source properties then we apply those to all of them. In this case we have a single target
         * reference that can provide a source parameter. So we will get:
         * <pre>
         * GroupedBySourceParameters.groupedBySourceParameter {
         *     fish: {@literal @}Mapping(target = "organization.name", expression="java(\"Dunno\")");
         * }
         *
         * GroupedBySourceParameters.notProcessedAppliesToAll {} //empty
         * </pre>
         *
         * <p>
         * See also how the {@code singleTargetReferences} are not part of the mappings. They are used <b>only</b> to
         * make sure that their source parameter is taken into consideration in the next step.
         *
         * <p>
         * The {@code notProcessedAppliesToAll} contains all Mappings that should have been applied to all but have not
         * because there were no other mappings that we could have used to pass them along. These
         * {@link org.mapstruct.Mapping}(s) are used later on for normal mapping.
         *
         * <p>
         * If we only had:
         * <pre>
         * {@literal @}Mapping(target = "document", source = "report");
         * {@literal @}Mapping(target = "ornament", ignore = true );
         * </pre>
         *
         * Then we only would have had:
         * <pre>
         * GroupedBySourceParameters.notProcessedAppliesToAll {
         * {@literal @}Mapping(target = "document", source = "report");
         * {@literal @}Mapping(target = "ornament", ignore = true );
         * }
         * </pre>
         *
         * These mappings will be part of the {@code GroupedBySourceParameters.notProcessedAppliesToAll} and are
         * used to be passed to the normal defined mapping.
         *
         *
         * @param mappings that mappings that need to be used for the grouping
         * @param singleTargetReferences a List containing all non-nested mappings for the same grouped target
         * property as the {@code mappings}
         * @return the split mapping options.
         */
        private GroupedBySourceParameters groupBySourceParameter(List<Mapping> mappings,
            List<Mapping> singleTargetReferences) {

            Map<Parameter, List<Mapping>> mappingsKeyedByParameter = new LinkedHashMap<Parameter, List<Mapping>>();
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
         * Mappings:
         * <pre>
         * {@literal @}Mapping(target = "organization.name", source = "report.organizationName");
         * </pre>
         *
         * singleTargetReferences:
         * <pre>
         * {@literal @}Mapping(target = "document", source = "report");
         * </pre>
         *
         * And we get:
         *
         * <pre>
         * GroupedSourceReferences.groupedBySourceReferences {
         *     report: {@literal @}Mapping(target = "organization.name", source = "organizationName");
         * }
         * </pre>
         *
         *
         *
         * @param entryByParam the entry of a {@link Parameter} and it's associated {@link Mapping}(s) that need to
         * be used for grouping on popped source references
         * @param singleTargetReferences the single target references that match the source mappings
         *
         * @return the Grouped Source References
         */
        private GroupedSourceReferences groupByPoppedSourceReferences(Map.Entry<Parameter, List<Mapping>> entryByParam,
            List<Mapping> singleTargetReferences) {
            List<Mapping> mappings = entryByParam.getValue();
            List<Mapping> nonNested = new ArrayList<Mapping>();
            List<Mapping> appliesToAll = new ArrayList<Mapping>();
            List<Mapping> sourceParameterMappings = new ArrayList<Mapping>();
            // group all mappings based on the top level name before popping
            Map<PropertyEntry, List<Mapping>> mappingsKeyedByProperty
                = new LinkedHashMap<PropertyEntry, List<Mapping>>();
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

            // We consider that there were no mappings if there are no mappingsKeyedByProperty
            // and no nonNested. appliesToAll Mappings are mappings that have no source reference and need to be
            // applied to everything.
            boolean hasNoMappings = mappingsKeyedByProperty.isEmpty() && nonNested.isEmpty();
            Parameter sourceParameter = entryByParam.getKey();
            List<Mapping> singleTargetReferencesToUse =
                extractSingleTargetReferencesToUseAndPopulateSourceParameterMappings(
                    singleTargetReferences,
                    sourceParameterMappings,
                    hasNoMappings,
                    sourceParameter
                );

            populateWithSingleTargetReferences(
                mappingsKeyedByProperty,
                singleTargetReferencesToUse,
                PROPERTY_EXTRACTOR
            );

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
                notProcessedAppliesToAll,
                sourceParameterMappings
            );
        }

        /**
         * Extracts all relevant single target references and populates the {@code sourceParameterMappings} if needed.
         * A relevant single target reference mapping is a mapping that has a valid source reference and is for
         * the {@code sourceParameter}. If there are no mappings i.e. {@code hasNoMappings = true} and the source
         * reference in the mapping has no property entries then add that to the {@code sourceParameterMappings}
         * (mappings like this have found themselves here because there is a mapping method with multiple parameters
         * and that are using the same sub-path in the target properties).
         *
         * @param singleTargetReferences All the single target references for a target property
         * @param sourceParameterMappings a List that needs to be populated with valid mappings when {@code
         * hasNoMappings = true} and there are no property entries in the source reference
         * @param hasNoMappings parameter indicating whether there were any extracted mappings for this target property
         * @param sourceParameter the source parameter for which the grouping is being done
         *
         * @return a list with valid single target references
         */
        private List<Mapping> extractSingleTargetReferencesToUseAndPopulateSourceParameterMappings(
            List<Mapping> singleTargetReferences, List<Mapping> sourceParameterMappings, boolean hasNoMappings,
            Parameter sourceParameter) {
            List<Mapping> singleTargetReferencesToUse = null;
            if ( singleTargetReferences != null ) {
                singleTargetReferencesToUse = new ArrayList<Mapping>( singleTargetReferences.size() );
                for ( Mapping mapping : singleTargetReferences ) {
                    if ( mapping.getSourceReference() == null || !mapping.getSourceReference().isValid() ||
                        !sourceParameter.equals( mapping.getSourceReference().getParameter() ) ) {
                        // If the mapping has no sourceReference, it is not valid or it does not have the same source
                        // parameter then we need to ignore it. When a mapping method has multiple parameters it can
                        // happen that different parameters somehow have same path in the nesting
                        continue;
                    }
                    if ( hasNoMappings && mapping.getSourceReference().getPropertyEntries().isEmpty() ) {
                        // If there were no mappings for this source parameter and there are no property entries
                        // that means that this could be for a mapping method with multiple parameters.
                        // We have to consider and map this separately
                        sourceParameterMappings.add( mapping );
                    }
                    else {
                        singleTargetReferencesToUse.add( mapping );
                    }
                }
            }
            return singleTargetReferencesToUse;
        }

        private Map<String, List<Mapping>> groupByTargetName(List<Mapping> mappingList) {
            Map<String, List<Mapping>> result = new LinkedHashMap<String, List<Mapping>>();
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
                    if ( mapping.getSourceReference() != null && mapping.getSourceReference().isValid() ) {
                        K key = keyExtractor.apply( mapping.getSourceReference() );
                        if ( key != null && !map.containsKey( key ) ) {
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
        private final boolean errorOccurred;

        private GroupedTargetReferences(Map<PropertyEntry, List<Mapping>> poppedTargetReferences,
            Map<PropertyEntry, List<Mapping>> singleTargetReferences, boolean errorOccurred) {
            this.poppedTargetReferences = poppedTargetReferences;
            this.singleTargetReferences = singleTargetReferences;
            this.errorOccurred = errorOccurred;
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
     * <p>
     * If Mappings that should apply to all were found, but no grouping was found, they will be located in a
     * different list:
     */
    private static class GroupedSourceReferences {

        private final Map<PropertyEntry, List<Mapping>> groupedBySourceReferences;
        private final List<Mapping> nonNested;
        private final List<Mapping> notProcessedAppliesToAll;
        private final List<Mapping> sourceParameterMappings;

        private GroupedSourceReferences(Map<PropertyEntry, List<Mapping>> groupedBySourceReferences,
            List<Mapping> nonNested, List<Mapping> notProcessedAppliesToAll,
            List<Mapping> sourceParameterMappings) {
            this.groupedBySourceReferences = groupedBySourceReferences;
            this.nonNested = nonNested;
            this.notProcessedAppliesToAll = notProcessedAppliesToAll;
            this.sourceParameterMappings = sourceParameterMappings;

        }
    }
}
