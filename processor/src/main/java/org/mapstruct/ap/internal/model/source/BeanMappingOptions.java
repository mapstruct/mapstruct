/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import org.mapstruct.ap.internal.gem.BeanMappingGem;
import org.mapstruct.ap.internal.gem.BuilderGem;
import org.mapstruct.ap.internal.gem.NullValueCheckStrategyGem;
import org.mapstruct.ap.internal.gem.NullValueMappingStrategyGem;
import org.mapstruct.ap.internal.gem.NullValuePropertyMappingStrategyGem;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.util.FormattingMessager;
import org.mapstruct.ap.internal.util.Message;
import org.mapstruct.tools.gem.GemValue;

/**
 * Represents an bean mapping as configured via {@code @BeanMapping}.
 *
 * @author Sjaak Derksen
 */
public class BeanMappingOptions extends DelegatingOptions {

    private final SelectionParameters selectionParameters;
    private final BeanMappingGem beanMapping;

    /**
     * creates a mapping for inheritance.
     *
     * @param inheritedOptions Options that should be inherited, could be {@code null}
     * @param existingOptions Existing options, could be {@code null}
     * @return new mapping
     */
    public static BeanMappingOptions forInheritance(BeanMappingOptions inheritedOptions,
                                                    BeanMappingOptions existingOptions) {
        if ( inheritedOptions == null ) {
            return existingOptions;
        }
        if ( existingOptions == null || !existingOptions.hasAnnotation() ) {
            return new BeanMappingOptions(
                SelectionParameters.forInheritance( inheritedOptions.selectionParameters, null ),
                inheritedOptions.beanMapping,
                inheritedOptions
            );
        }

        return new BeanMappingOptions(
            SelectionParameters.forInheritance(
                inheritedOptions.selectionParameters,
                existingOptions.selectionParameters
            ),
            existingOptions.beanMapping,
            inheritedOptions
        );
    }

    public static BeanMappingOptions getInstanceOn(BeanMappingGem beanMapping, MapperOptions mapperOptions,
                                                   ExecutableElement method, FormattingMessager messager,
                                                   Types typeUtils, TypeFactory typeFactory
    ) {
        if ( beanMapping == null || !isConsistent( beanMapping, method, messager ) ) {
            BeanMappingOptions options = new BeanMappingOptions( null, null, mapperOptions );
            return options;
        }

        Objects.requireNonNull( method );
        Objects.requireNonNull( messager );
        Objects.requireNonNull( method );
        Objects.requireNonNull( typeUtils );
        Objects.requireNonNull( typeFactory );

        SelectionParameters selectionParameters = new SelectionParameters(
            beanMapping.qualifiedBy().get(),
            beanMapping.qualifiedByName().get(),
            beanMapping.resultType().getValue(),
            typeUtils
        );

        //TODO Do we want to add the reporting policy to the BeanMapping as well? To give more granular support?
        BeanMappingOptions options = new BeanMappingOptions(
            selectionParameters,
            beanMapping,
            mapperOptions
        );
        return options;
    }

    private static boolean isConsistent(BeanMappingGem gem, ExecutableElement method,
                                        FormattingMessager messager) {
        if ( !gem.resultType().hasValue()
            && !gem.qualifiedBy().hasValue()
            && !gem.qualifiedByName().hasValue()
            && !gem.ignoreUnmappedSourceProperties().hasValue()
            && !gem.nullValueCheckStrategy().hasValue()
            && !gem.nullValuePropertyMappingStrategy().hasValue()
            && !gem.nullValueMappingStrategy().hasValue()
            && !gem.ignoreByDefault().hasValue()
            && !gem.builder().hasValue() ) {

            messager.printMessage( method, Message.BEANMAPPING_NO_ELEMENTS );
            return false;
        }
        return true;
    }

    private BeanMappingOptions(SelectionParameters selectionParameters,
                               BeanMappingGem beanMapping,
                               DelegatingOptions next) {
        super( next );
        this.selectionParameters = selectionParameters;
        this.beanMapping = beanMapping;
    }

    // @Mapping, @BeanMapping

    @Override
    public NullValueCheckStrategyGem getNullValueCheckStrategy() {
        return Optional.ofNullable( beanMapping ).map( BeanMappingGem::nullValueCheckStrategy )
            .filter( GemValue::hasValue )
            .map( GemValue::getValue )
            .map( NullValueCheckStrategyGem::valueOf )
            .orElse( next().getNullValueCheckStrategy() );
    }

    @Override
    public NullValuePropertyMappingStrategyGem getNullValuePropertyMappingStrategy() {
        return Optional.ofNullable( beanMapping ).map( BeanMappingGem::nullValuePropertyMappingStrategy )
            .filter( GemValue::hasValue )
            .map( GemValue::getValue )
            .map( NullValuePropertyMappingStrategyGem::valueOf )
            .orElse( next().getNullValuePropertyMappingStrategy() );
    }

    @Override
    public NullValueMappingStrategyGem getNullValueMappingStrategy() {
        return Optional.ofNullable( beanMapping ).map( BeanMappingGem::nullValueMappingStrategy )
            .filter( GemValue::hasValue )
            .map( GemValue::getValue )
            .map( NullValueMappingStrategyGem::valueOf )
            .orElse( next().getNullValueMappingStrategy() );
    }

    @Override
    public BuilderGem getBuilder() {
        return Optional.ofNullable( beanMapping ).map( BeanMappingGem::builder )
            .filter( GemValue::hasValue )
            .map( GemValue::getValue )
            .orElse( next().getBuilder() );
    }

    @Override
    public MappingControl getMappingControl(Elements elementUtils) {
        return Optional.ofNullable( beanMapping ).map( BeanMappingGem::mappingControl )
            .filter( GemValue::hasValue )
            .map( GemValue::getValue )
            .map( mc -> MappingControl.fromTypeMirror( mc, elementUtils ) )
            .orElse( next().getMappingControl( elementUtils ) );
    }

    // @BeanMapping specific

    public SelectionParameters getSelectionParameters() {
        return selectionParameters;
    }

    public boolean isignoreByDefault() {
        return Optional.ofNullable( beanMapping ).map( BeanMappingGem::ignoreByDefault )
            .map( GemValue::get )
            .orElse( false );
    }

    public List<String> getIgnoreUnmappedSourceProperties() {
        return StreamSupport.stream( spliterator(), false )
            .filter( opt -> opt instanceof BeanMappingOptions )
            .map( opt -> (BeanMappingOptions) opt )
            .filter( opt -> opt.beanMapping != null )
            .map( opt -> opt.beanMapping )
            .map( BeanMappingGem::ignoreUnmappedSourceProperties )
            .filter( GemValue::hasValue )
            .flatMap( gv -> gv.getValue().stream() )
            .distinct()
            .collect( Collectors.toList() );
    }

    public AnnotationMirror getMirror() {
        return Optional.ofNullable( beanMapping ).map( BeanMappingGem::mirror ).orElse( null );
    }

    @Override
    public boolean hasAnnotation() {
        return beanMapping != null;
    }
}
