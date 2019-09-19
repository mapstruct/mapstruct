/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source;

import java.util.List;
import java.util.Optional;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.util.Types;

import org.mapstruct.ap.internal.prism.BeanMappingPrism;
import org.mapstruct.ap.internal.prism.BuilderPrism;
import org.mapstruct.ap.internal.prism.NullValueCheckStrategyPrism;
import org.mapstruct.ap.internal.prism.NullValueMappingStrategyPrism;
import org.mapstruct.ap.internal.prism.NullValuePropertyMappingStrategyPrism;
import org.mapstruct.ap.internal.prism.ReportingPolicyPrism;
import org.mapstruct.ap.internal.util.FormattingMessager;
import org.mapstruct.ap.internal.util.Message;

/**
 * Represents an bean mapping as configured via {@code @BeanMapping}.
 *
 * @author Sjaak Derksen
 */
public class BeanMapping {

    private final SelectionParameters selectionParameters;
    private final NullValueMappingStrategyPrism nullValueMappingStrategy;
    private final NullValueCheckStrategyPrism nullValueCheckStrategy;
    private final ReportingPolicyPrism reportingPolicy;
    private final boolean ignoreByDefault;
    private final List<String> ignoreUnmappedSourceProperties;
    private final BuilderPrism builder;
    private final NullValuePropertyMappingStrategyPrism nullValuePropertyMappingStrategy;

    /**
     * creates a mapping for inheritance. Will set ignoreByDefault to false.
     *
     * @param map
     * @return
     */
    public static BeanMapping forInheritance( BeanMapping map ) {
        return new BeanMapping(
            map.selectionParameters,
            map.nullValueMappingStrategy,
            map.nullValuePropertyMappingStrategy,
            map.nullValueCheckStrategy,
            map.reportingPolicy,
            false,
            map.ignoreUnmappedSourceProperties,
            map.builder
        );
    }

    public static BeanMapping fromPrism(BeanMappingPrism beanMapping, ExecutableElement method,
        FormattingMessager messager, Types typeUtils) {

        if ( beanMapping == null ) {
            return null;
        }

        boolean resultTypeIsDefined = !TypeKind.VOID.equals( beanMapping.resultType().getKind() );

        NullValueMappingStrategyPrism nullValueMappingStrategy =
            null == beanMapping.values.nullValueMappingStrategy()
                            ? null
                            : NullValueMappingStrategyPrism.valueOf( beanMapping.nullValueMappingStrategy() );

        NullValuePropertyMappingStrategyPrism nullValuePropertyMappingStrategy =
            null == beanMapping.values.nullValuePropertyMappingStrategy()
                ? null
                : NullValuePropertyMappingStrategyPrism.valueOf( beanMapping.nullValuePropertyMappingStrategy() );

        NullValueCheckStrategyPrism nullValueCheckStrategy =
            null == beanMapping.values.nullValueCheckStrategy()
                ? null
                : NullValueCheckStrategyPrism.valueOf( beanMapping.nullValueCheckStrategy() );

        boolean ignoreByDefault = beanMapping.ignoreByDefault();
        BuilderPrism builderMapping = null;
        if ( beanMapping.values.builder() != null ) {
            builderMapping = beanMapping.builder();
        }

        if ( !resultTypeIsDefined && beanMapping.qualifiedBy().isEmpty() && beanMapping.qualifiedByName().isEmpty()
            && beanMapping.ignoreUnmappedSourceProperties().isEmpty()
            && ( nullValueMappingStrategy == null ) && ( nullValuePropertyMappingStrategy == null )
            && ( nullValueCheckStrategy == null )  && !ignoreByDefault && builderMapping == null ) {

            messager.printMessage( method, Message.BEANMAPPING_NO_ELEMENTS );
        }

        SelectionParameters cmp = new SelectionParameters(
            beanMapping.qualifiedBy(),
            beanMapping.qualifiedByName(),
            resultTypeIsDefined ? beanMapping.resultType() : null,
            typeUtils
        );

        //TODO Do we want to add the reporting policy to the BeanMapping as well? To give more granular support?
        return new BeanMapping(
            cmp,
            nullValueMappingStrategy,
            nullValuePropertyMappingStrategy,
            nullValueCheckStrategy,
            null,
            ignoreByDefault,
            beanMapping.ignoreUnmappedSourceProperties(),
            builderMapping
        );
    }

    private BeanMapping(SelectionParameters selectionParameters, NullValueMappingStrategyPrism nvms,
                        NullValuePropertyMappingStrategyPrism nvpms, NullValueCheckStrategyPrism nvcs,
                        ReportingPolicyPrism reportingPolicy, boolean ignoreByDefault,
                        List<String> ignoreUnmappedSourceProperties, BuilderPrism builder) {
        this.selectionParameters = selectionParameters;
        this.nullValueMappingStrategy = nvms;
        this.nullValuePropertyMappingStrategy = nvpms;
        this.nullValueCheckStrategy = nvcs;
        this.reportingPolicy = reportingPolicy;
        this.ignoreByDefault = ignoreByDefault;
        this.ignoreUnmappedSourceProperties = ignoreUnmappedSourceProperties;
        this.builder = builder;
    }

    public SelectionParameters getSelectionParameters() {
        return selectionParameters;
    }

    public NullValueMappingStrategyPrism getNullValueMappingStrategy() {
        return nullValueMappingStrategy;
    }

    public NullValuePropertyMappingStrategyPrism getNullValuePropertyMappingStrategy() {
        return nullValuePropertyMappingStrategy;
    }

    public NullValueCheckStrategyPrism getNullValueCheckStrategy() {
        return nullValueCheckStrategy;
    }

    public ReportingPolicyPrism getReportingPolicy() {
        return reportingPolicy;
    }

    public boolean isignoreByDefault() {
        return ignoreByDefault;
    }

    public List<String> getIgnoreUnmappedSourceProperties() {
        return ignoreUnmappedSourceProperties;
    }

    /**
     * derives the builder prism given the options and configuration
     * @param method containing mandatory configuration and the mapping options (optionally containing a beanmapping)
     * @return a BuilderPrism as optional
     */
    public static Optional<BuilderPrism> builderPrismFor(Method method) {
        return method.getMapperConfiguration()
                     .getBuilderPrism( Optional.ofNullable( method.getMappingOptions().getBeanMapping() )
                                               .map( b -> b.builder )
                                               .orElse( null ) );
    }
}
