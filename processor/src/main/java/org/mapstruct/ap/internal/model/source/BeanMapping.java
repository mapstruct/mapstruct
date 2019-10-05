/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.util.Types;

import org.mapstruct.ap.internal.model.common.TypeFactory;
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

    private final AnnotationMirror mirror;

    /**
     * creates a mapping for inheritance. Will set
     * - ignoreByDefault to false.
     * - resultType to null
     *
     * @return new mapping
     */
    public static BeanMapping forInheritance(BeanMapping beanMapping) {
        return new BeanMapping(
            SelectionParameters.forInheritance( beanMapping.selectionParameters ),
            beanMapping.nullValueMappingStrategy,
            beanMapping.nullValuePropertyMappingStrategy,
            beanMapping.nullValueCheckStrategy,
            beanMapping.reportingPolicy,
            beanMapping.ignoreByDefault,
            beanMapping.ignoreUnmappedSourceProperties,
            beanMapping.builder,
            beanMapping.mirror
        );
    }

    public static class Builder {

        private BeanMappingPrism prism;
        private ExecutableElement method;
        private FormattingMessager messager;
        private Types typeUtils;
        private TypeFactory typeFactory;

        public Builder beanMappingPrism(BeanMappingPrism beanMappingPrism) {
            this.prism = beanMappingPrism;
            return this;
        }

        public Builder method(ExecutableElement method) {
            this.method = method;
            return this;
        }

        public Builder messager(FormattingMessager messager) {
            this.messager = messager;
            return this;
        }

        public Builder typeUtils(Types typeUtils) {
            this.typeUtils = typeUtils;
            return this;
        }

        public Builder typeFactory(TypeFactory typeFactory) {
            this.typeFactory = typeFactory;
            return this;
        }

        public BeanMapping build() {

            if ( prism == null ) {
                return null;
            }

            Objects.requireNonNull( method );
            Objects.requireNonNull( messager );
            Objects.requireNonNull( method );
            Objects.requireNonNull( typeUtils );
            Objects.requireNonNull( typeFactory );

            boolean resultTypeIsDefined = !TypeKind.VOID.equals( prism.resultType().getKind() );

            NullValueMappingStrategyPrism nullValueMappingStrategy =
                null == prism.values.nullValueMappingStrategy()
                    ? null
                    : NullValueMappingStrategyPrism.valueOf( prism.nullValueMappingStrategy() );

            NullValuePropertyMappingStrategyPrism nullValuePropertyMappingStrategy =
                null == prism.values.nullValuePropertyMappingStrategy()
                    ? null
                    :
                    NullValuePropertyMappingStrategyPrism.valueOf( prism.nullValuePropertyMappingStrategy() );

            NullValueCheckStrategyPrism nullValueCheckStrategy =
                null == prism.values.nullValueCheckStrategy()
                    ? null
                    : NullValueCheckStrategyPrism.valueOf( prism.nullValueCheckStrategy() );

            boolean ignoreByDefault = prism.ignoreByDefault();
            BuilderPrism builderMapping = null;
            if ( prism.values.builder() != null ) {
                builderMapping = prism.builder();
            }

            if ( !resultTypeIsDefined && prism.qualifiedBy().isEmpty() &&
                prism.qualifiedByName().isEmpty()
                && prism.ignoreUnmappedSourceProperties().isEmpty()
                && ( nullValueMappingStrategy == null ) && ( nullValuePropertyMappingStrategy == null )
                && ( nullValueCheckStrategy == null ) && !ignoreByDefault && builderMapping == null ) {

                messager.printMessage( method, Message.BEANMAPPING_NO_ELEMENTS );
            }

            SelectionParameters cmp = new SelectionParameters(
                prism.qualifiedBy(),
                prism.qualifiedByName(),
                resultTypeIsDefined ? prism.resultType() : null,
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
                prism.ignoreUnmappedSourceProperties(),
                builderMapping,
                prism.mirror
            );
        }
    }

    private BeanMapping(SelectionParameters selectionParameters, NullValueMappingStrategyPrism nvms,
                        NullValuePropertyMappingStrategyPrism nvpms, NullValueCheckStrategyPrism nvcs,
                        ReportingPolicyPrism reportingPolicy, boolean ignoreByDefault,
                        List<String> ignoreUnmappedSourceProperties, BuilderPrism builder,
                        AnnotationMirror mirror) {
        this.selectionParameters = selectionParameters;
        this.nullValueMappingStrategy = nvms;
        this.nullValuePropertyMappingStrategy = nvpms;
        this.nullValueCheckStrategy = nvcs;
        this.reportingPolicy = reportingPolicy;
        this.ignoreByDefault = ignoreByDefault;
        this.ignoreUnmappedSourceProperties = ignoreUnmappedSourceProperties;
        this.builder = builder;
        this.mirror = mirror;
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

    public AnnotationMirror getMirror() {
        return mirror;
    }

    /**
     * derives the builder prism given the options and configuration
     * @param method containing mandatory configuration and the mapping options (optionally containing a beanmapping)
     * @return null if BuilderPrism not exist
     */
    public static BuilderPrism builderPrismFor(Method method) {
        return method.getMapperConfiguration()
                     .getBuilderPrism( Optional.ofNullable( method.getMappingOptions().getBeanMapping() )
                                               .map( b -> b.builder )
                                               .orElse( null ) );
    }
}
