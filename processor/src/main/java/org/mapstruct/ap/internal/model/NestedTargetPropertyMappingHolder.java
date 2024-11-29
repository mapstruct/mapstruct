/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import org.mapstruct.ap.internal.model.beanmapping.MappingReference;
import org.mapstruct.ap.internal.model.beanmapping.MappingReferences;
import org.mapstruct.ap.internal.model.beanmapping.PropertyEntry;
import org.mapstruct.ap.internal.model.beanmapping.SourceReference;
import org.mapstruct.ap.internal.model.beanmapping.TargetReference;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.util.accessor.ReadAccessor;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.MappingOptions;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.util.Message;
import org.mapstruct.ap.internal.util.Strings;
import org.mapstruct.ap.internal.util.accessor.Accessor;

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
    private final Map<String, Set<MappingReference>> unprocessedDefinedTarget;
    private final boolean errorOccurred;

    public NestedTargetPropertyMappingHolder(
        List<Parameter> processedSourceParameters, Set<String> handledTargets,
        List<PropertyMapping> propertyMappings,
        Map<String, Set<MappingReference>> unprocessedDefinedTarget, boolean errorOccurred) {
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
     * @return all the targets that were handled
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
    public Map<String, Set<MappingReference>> getUnprocessedDefinedTarget() {
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
        private MappingReferences mappingReferences;
        private MappingBuilderContext mappingContext;
        private Set<String> existingVariableNames;
        private List<PropertyMapping> propertyMappings;
        private Set<String> handledTargets;
        private Map<String, Accessor> targetPropertiesWriteAccessors;
        private Type targetType;
        private boolean errorOccurred;

        public Builder mappingReferences(MappingReferences mappingReferences) {
            this.mappingReferences = mappingReferences;
            return this;
        }

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

        public Builder targetPropertiesWriteAccessors(Map<String, Accessor> targetPropertiesWriteAccessors) {
            this.targetPropertiesWriteAccessors = targetPropertiesWriteAccessors;
            return this;
        }

        public Builder targetPropertyType(Type targetType) {
            this.targetType = targetType;
            return this;
        }

        public NestedTargetPropertyMappingHolder build() {
            List<Parameter> processedSourceParameters = new ArrayList<>();
            handledTargets = new HashSet<>();
            propertyMappings = new ArrayList<>();

            // first we group by the first property in the target properties and for each of those
            // properties we get the new mappings as if the first property did not exist.
            GroupedTargetReferences groupedByTP = groupByTargetReferences( );
            Map<String, Set<MappingReference>> unprocessedDefinedTarget = new LinkedHashMap<>();

            for ( Map.Entry<String, Set<MappingReference>> entryByTP :
                groupedByTP.poppedTargetReferences.entrySet() ) {
                String targetProperty = entryByTP.getKey();
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
                for ( Map.Entry<Parameter, Set<MappingReference>> entryByParam : groupedBySourceParam
                    .groupedBySourceParameter.entrySet() ) {

                    Parameter sourceParameter = entryByParam.getKey();

                    // Lastly we need to group by the source references. This will allow us to actually create
                    // the next mappings by popping source elements
                    GroupedSourceReferences groupedSourceReferences = groupByPoppedSourceReferences(
                        entryByParam,
                        groupedByTP.singleTargetReferences.get( targetProperty )
                    );

                    // We need an update method in the when one of the following is satisfied:
                    // 1) Multiple source parameters for the target reference
                    // 2) Multiple source references for the target reference
                    // The reason for this is that multiple sources have effect on the target.
                    // See Issue1828Test for more info.
                    boolean forceUpdateMethod =
                        multipleSourceParametersForTP || groupedSourceReferences.groupedBySourceReferences.size() > 1;

                    boolean forceUpdateMethodOrNonNestedReferencesPresent =
                        forceUpdateMethod || !groupedSourceReferences.nonNested.isEmpty();
                    // For all the groupedBySourceReferences we need to create property mappings
                    // from the Mappings and not restrict on the defined mappings (allow to forge name based mapping)
                    // if we have composite methods i.e. more then 2 parameters then we have to force a creation
                    // of an update method in our generation
                    for ( Map.Entry<PropertyEntry, Set<MappingReference>> entryBySP : groupedSourceReferences
                        .groupedBySourceReferences
                        .entrySet() ) {
                        PropertyEntry sourceEntry = entryBySP.getKey();
                        // If there are multiple source parameters that are mapped to the target reference
                        // then we restrict the mapping only to the defined mappings. And we create MappingOptions
                        // for forged methods (which means that any unmapped target properties are ignored)
                        // MappingOptions for forged methods is also created if we have something like this:
                        //@Mappings({
                        //    @Mapping(target = "vehicleInfo", source = "vehicleTypeInfo"),
                        //    @Mapping(target = "vehicleInfo.images", source = "images")
                        //})
                        // See Issue1269Test, Issue1247Test, AutomappingAndNestedTest for more info as well
                        MappingReferences sourceMappingRefs = new MappingReferences( entryBySP.getValue(),
                            multipleSourceParametersForTP,
                            forceUpdateMethodOrNonNestedReferencesPresent
                        );

                        SourceReference sourceRef = new SourceReference.BuilderFromProperty()
                            .sourceParameter( sourceParameter )
                            .type( sourceEntry.getType() )
                            .readAccessor( sourceEntry.getReadAccessor() )
                            .presenceChecker( sourceEntry.getPresenceChecker() )
                            .name( targetProperty )
                            .build();

                        // If we have multiple source parameters that are mapped to the target reference, or
                        // parts of the nested properties are mapped to different sources (see comment above as well)
                        // we would force an update method
                        PropertyMapping propertyMapping = createPropertyMappingForNestedTarget(
                            sourceMappingRefs,
                            targetProperty,
                            sourceRef,
                            forceUpdateMethodOrNonNestedReferencesPresent
                        );

                        if ( propertyMapping != null ) {
                            propertyMappings.add( propertyMapping );
                        }

                        handledTargets.add( entryByTP.getKey() );
                    }

                    // For the nonNested mappings (asymmetric) Mappings we also forge mappings
                    // However, here we do not forge name based mappings and we only
                    // do update on the defined Mappings.
                    if ( !groupedSourceReferences.nonNested.isEmpty() ) {
                        MappingReferences mappingReferences =
                            new MappingReferences( groupedSourceReferences.nonNested, true );
                        SourceReference reference = new SourceReference.BuilderFromProperty()
                            .sourceParameter( sourceParameter )
                            .name( targetProperty )
                            .build();

                        boolean forceUpdateMethodForNonNested =
                            multipleSourceParametersForTP ||
                                !groupedSourceReferences.groupedBySourceReferences.isEmpty();
                        // If an update method is forced or there are groupedBySourceReferences then we must create
                        // an update method. The reason is that they might be for the same reference and we should
                        // use update for it
                        PropertyMapping propertyMapping = createPropertyMappingForNestedTarget(
                            mappingReferences,
                            targetProperty,
                            reference,
                            forceUpdateMethodForNonNested
                        );

                        if ( propertyMapping != null ) {
                            propertyMappings.add( propertyMapping );
                        }

                        handledTargets.add( entryByTP.getKey() );
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
                errorOccurred
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
        private void handleSourceParameterMappings(Set<MappingReference> sourceParameterMappings,
                                                   String targetProperty, Parameter sourceParameter,
                                                   boolean forceUpdateMethod) {
            if ( !sourceParameterMappings.isEmpty() ) {
                // The source parameter mappings have no mappings, the source name is actually the parameter itself
                MappingReferences nonNestedRefs =
                    new MappingReferences( Collections.emptySet(), false, true );
                SourceReference reference = new SourceReference.BuilderFromProperty()
                    .sourceParameter( sourceParameter )
                    .name( targetProperty )
                    .build();

                PropertyMapping propertyMapping = createPropertyMappingForNestedTarget(
                    nonNestedRefs,
                    targetProperty,
                    reference,
                    forceUpdateMethod
                );

                if ( propertyMapping != null ) {
                    propertyMappings.add( propertyMapping );
                }

                handledTargets.add( targetProperty );
            }
        }

        /**
         * The target references are popped. The {@code List<}{@link MappingOptions}{@code >} are keyed on the unique
         * first entries of the target references.
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
         * @return See above
         */
        private GroupedTargetReferences groupByTargetReferences( ) {
            // group all mappings based on the top level name before popping
            Map<String, Set<MappingReference>> mappingsKeyedByProperty = new LinkedHashMap<>();
            Map<String, Set<MappingReference>> singleTargetReferences = new LinkedHashMap<>();
            for ( MappingReference mapping : mappingReferences.getMappingReferences() )  {
                TargetReference targetReference = mapping.getTargetReference();
                List<String> propertyEntries = targetReference.getPropertyEntries();
                if ( propertyEntries.isEmpty() ) {
                    // This can happen if the target property is target = ".",
                    // this usually happens when doing a reverse mapping
                    continue;
                }
                String property = first( propertyEntries );
                MappingReference newMapping = mapping.popTargetReference();
                if ( newMapping != null ) {
                    // group properties on current name.
                    mappingsKeyedByProperty.computeIfAbsent( property, propertyEntry -> new LinkedHashSet<>() )
                        .add( newMapping );
                }
                else {
                    singleTargetReferences.computeIfAbsent( property, propertyEntry -> new LinkedHashSet<>() )
                        .add( mapping );
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
        private GroupedBySourceParameters groupBySourceParameter(Set<MappingReference> mappings,
            Set<MappingReference> singleTargetReferences) {

            Map<Parameter, Set<MappingReference>> mappingsKeyedByParameter = new LinkedHashMap<>();
            Set<MappingReference> appliesToAll = new LinkedHashSet<>();
            for ( MappingReference mapping : mappings ) {
                if ( mapping.getSourceReference() != null && mapping.getSourceReference().isValid() ) {
                    Parameter parameter = mapping.getSourceReference().getParameter();
                    mappingsKeyedByParameter.computeIfAbsent( parameter, key -> new LinkedHashSet<>() )
                        .add( mapping );
                }
                else {
                    appliesToAll.add( mapping );
                }
            }

            populateWithSingleTargetReferences(
                mappingsKeyedByParameter,
                singleTargetReferences,
                SourceReference::getParameter
            );

            for ( Map.Entry<Parameter, Set<MappingReference>> entry : mappingsKeyedByParameter.entrySet() ) {
                entry.getValue().addAll( appliesToAll );
            }

            Set<MappingReference> notProcessAppliesToAll =
                mappingsKeyedByParameter.isEmpty() ? appliesToAll : new LinkedHashSet<>();

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
         * @param entryByParam the entry of a {@link Parameter} and it's associated {@link MappingOptions}(s) that need
         *                     to be used for grouping on popped source references
         * @param singleTargetReferences the single target references that match the source mappings
         *
         * @return the Grouped Source References
         */
        private GroupedSourceReferences groupByPoppedSourceReferences(
            Map.Entry<Parameter, Set<MappingReference>> entryByParam,
            Set<MappingReference> singleTargetReferences) {
            Set<MappingReference> mappings = entryByParam.getValue();
            Set<MappingReference> nonNested = new LinkedHashSet<>();
            Set<MappingReference> appliesToAll = new LinkedHashSet<>();
            Set<MappingReference> sourceParameterMappings = new LinkedHashSet<>();
            // group all mappings based on the top level name before popping
            Map<PropertyEntry, Set<MappingReference>> mappingsKeyedByProperty
                = new LinkedHashMap<>();
            for ( MappingReference mapping : mappings ) {
                MappingReference newMapping = mapping.popSourceReference();
                if ( newMapping != null ) {
                    // group properties on current name.
                    PropertyEntry property = first( mapping.getSourceReference().getPropertyEntries() );
                    mappingsKeyedByProperty.computeIfAbsent( property, propertyEntry -> new LinkedHashSet<>() )
                        .add( newMapping );
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
            Set<MappingReference> singleTargetReferencesToUse =
                extractSingleTargetReferencesToUseAndPopulateSourceParameterMappings(
                    singleTargetReferences,
                    sourceParameterMappings,
                    hasNoMappings,
                    sourceParameter
                );

            populateWithSingleTargetReferences(
                mappingsKeyedByProperty,
                singleTargetReferencesToUse,
                sourceReference -> sourceReference.getPropertyEntries().isEmpty() ? null :
                    first( sourceReference.getPropertyEntries() )
            );

            for ( Map.Entry<PropertyEntry, Set<MappingReference>> entry : mappingsKeyedByProperty.entrySet() ) {
                entry.getValue().addAll( appliesToAll );
            }

            Set<MappingReference> notProcessedAppliesToAll = new LinkedHashSet<>();
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
         * @return a set with valid single target references
         */
        private Set<MappingReference> extractSingleTargetReferencesToUseAndPopulateSourceParameterMappings(
            Set<MappingReference> singleTargetReferences, Set<MappingReference> sourceParameterMappings,
            boolean hasNoMappings, Parameter sourceParameter) {
            Set<MappingReference> singleTargetReferencesToUse = null;
            if ( singleTargetReferences != null ) {
                singleTargetReferencesToUse = new LinkedHashSet<>( singleTargetReferences.size() );
                for ( MappingReference mapping : singleTargetReferences ) {
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

        private PropertyMapping createPropertyMappingForNestedTarget(MappingReferences mappingReferences,
                                                                     String targetPropertyName,
                                                                     SourceReference sourceReference,
                                                                     boolean forceUpdateMethod) {

            Accessor targetWriteAccessor = targetPropertiesWriteAccessors.get( targetPropertyName );
            ReadAccessor targetReadAccessor = targetType.getReadAccessor(
                targetPropertyName,
                method.getSourceParameters().size() == 1
            );
            if ( targetWriteAccessor == null ) {
                Set<String> readAccessors = targetType.getPropertyReadAccessors().keySet();
                String mostSimilarProperty = Strings.getMostSimilarWord( targetPropertyName, readAccessors );

                for ( MappingReference mappingReference : mappingReferences.getMappingReferences() ) {
                    MappingOptions mapping = mappingReference.getMapping();
                    List<String> pathProperties = new ArrayList<>( mappingReference.getTargetReference()
                        .getPathProperties() );
                    if ( !pathProperties.isEmpty() ) {
                        pathProperties.set( pathProperties.size() - 1, mostSimilarProperty );
                    }

                    mappingContext.getMessager()
                        .printMessage(
                            mapping.getElement(),
                            mapping.getMirror(),
                            mapping.getTargetAnnotationValue(),
                            Message.BEANMAPPING_UNKNOWN_PROPERTY_IN_TYPE,
                            targetPropertyName,
                            targetType.describe(),
                            mapping.getTargetName(),
                            Strings.join( pathProperties, "." )
                        );
                }

                errorOccurred = true;
                return null;
            }

            return new PropertyMapping.PropertyMappingBuilder()
                .mappingContext( mappingContext )
                .sourceMethod( method )
                .target( targetPropertyName, targetReadAccessor, targetWriteAccessor )
                .sourceReference( sourceReference )
                .existingVariableNames( existingVariableNames )
                .dependsOn( mappingReferences.collectNestedDependsOn() )
                .forgeMethodWithMappingReferences( mappingReferences )
                .forceUpdateMethod( forceUpdateMethod )
                .forgedNamedBased( false )
                .options( method.getOptions().getBeanMapping() )
                .build();
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
        private <K> void populateWithSingleTargetReferences(Map<K, Set<MappingReference>> map,
            Set<MappingReference> singleTargetReferences, Function<SourceReference, K> keyExtractor) {
            if ( singleTargetReferences != null ) {
                //This are non nested target references only their property needs to be added as they most probably
                // define it
                for ( MappingReference mapping : singleTargetReferences ) {
                    if ( mapping.getSourceReference() != null && mapping.getSourceReference().isValid() ) {
                        K key = keyExtractor.apply( mapping.getSourceReference() );
                        if ( key != null ) {
                            map.computeIfAbsent( key, keyValue -> new LinkedHashSet<>() );
                        }
                    }
                }
            }
        }
    }

    private static class GroupedBySourceParameters {
        private final Map<Parameter, Set<MappingReference>> groupedBySourceParameter;
        private final Set<MappingReference> notProcessedAppliesToAll;

        private GroupedBySourceParameters(Map<Parameter, Set<MappingReference>> groupedBySourceParameter,
            Set<MappingReference> notProcessedAppliesToAll) {
            this.groupedBySourceParameter = groupedBySourceParameter;
            this.notProcessedAppliesToAll = notProcessedAppliesToAll;
        }
    }

    /**
     * The grouped target references. This class contains the poppedTarget references and the single target
     * references (target references that were not nested).
     */
    private static class GroupedTargetReferences {
        private final Map<String, Set<MappingReference>> poppedTargetReferences;
        private final Map<String, Set<MappingReference>> singleTargetReferences;

        private GroupedTargetReferences(Map<String, Set<MappingReference>> poppedTargetReferences,
            Map<String, Set<MappingReference>> singleTargetReferences) {
            this.poppedTargetReferences = poppedTargetReferences;
            this.singleTargetReferences = singleTargetReferences;
        }

        @Override
        public String toString() {
            return "GroupedTargetReferences{" + "poppedTargetReferences=" + poppedTargetReferences
                + ", singleTargetReferences=" + singleTargetReferences + "}";
        }
    }

    /**
     * This class is used to group Source references in respected to the nesting that they have.
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

        private final Map<PropertyEntry, Set<MappingReference>> groupedBySourceReferences;
        private final Set<MappingReference> nonNested;
        private final Set<MappingReference> notProcessedAppliesToAll;
        private final Set<MappingReference> sourceParameterMappings;

        private GroupedSourceReferences(Map<PropertyEntry, Set<MappingReference>> groupedBySourceReferences,
            Set<MappingReference> nonNested, Set<MappingReference> notProcessedAppliesToAll,
            Set<MappingReference> sourceParameterMappings) {
            this.groupedBySourceReferences = groupedBySourceReferences;
            this.nonNested = nonNested;
            this.notProcessedAppliesToAll = notProcessedAppliesToAll;
            this.sourceParameterMappings = sourceParameterMappings;

        }

        @Override
        public String toString() {
            return "GroupedSourceReferences{" + "groupedBySourceReferences=" + groupedBySourceReferences
                + ", nonNested=" + nonNested + ", notProcessedAppliesToAll=" + notProcessedAppliesToAll
                + ", sourceParameterMappings=" + sourceParameterMappings + '}';
        }
    }
}
