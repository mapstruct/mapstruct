/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.util.Types;

import org.mapstruct.annotations.GemValue;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.gem.BeanMappingGem;
import org.mapstruct.ap.internal.gem.BuilderGem;
import org.mapstruct.ap.internal.gem.NullValueCheckStrategyGem;
import org.mapstruct.ap.internal.gem.NullValueMappingStrategyGem;
import org.mapstruct.ap.internal.gem.NullValuePropertyMappingStrategyGem;
import org.mapstruct.ap.internal.util.FormattingMessager;
import org.mapstruct.ap.internal.util.Message;

/**
 * Represents an bean mapping as configured via {@code @BeanMapping}.
 *
 * @author Sjaak Derksen
 */
public class BeanMappingOptions extends DelegatingOptions {

    private final SelectionParameters selectionParameters;
    private final Optional<BeanMappingGem.BeanMapping> beanMapping;

    /**
     * creates a mapping for inheritance. Will set
     *
     * @return new mapping
     */
    public static BeanMappingOptions forInheritance(BeanMappingOptions beanMapping) {
        BeanMappingOptions options =  new BeanMappingOptions(
            SelectionParameters.forInheritance( beanMapping.selectionParameters ),
            beanMapping.beanMapping,
            beanMapping
        );
        return options;
    }

    public static BeanMappingOptions getInstanceOn(BeanMappingGem.BeanMapping beanMapping, MapperOptions mapperOptions,
                                                   ExecutableElement method, FormattingMessager messager,
                                                   Types typeUtils, TypeFactory typeFactory
    ) {
        if ( beanMapping == null || !isConsistent( beanMapping, method, messager ) ) {
            BeanMappingOptions options = new BeanMappingOptions( null, Optional.empty(), mapperOptions );
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
            Optional.ofNullable( beanMapping ),
            mapperOptions
        );
        return options;
    }

    private static boolean isConsistent(BeanMappingGem.BeanMapping gem, ExecutableElement method,
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
                               Optional<BeanMappingGem.BeanMapping> beanMapping,
                               DelegatingOptions next) {
        super( next );
        this.selectionParameters = selectionParameters;
        this.beanMapping = beanMapping;
    }

    // @Mapping, @BeanMapping

    @Override
    public NullValueCheckStrategyGem getNullValueCheckStrategy() {
        return beanMapping.map( BeanMappingGem.BeanMapping::nullValueCheckStrategy )
            .filter( GemValue::hasValue )
            .map( GemValue::getValue )
            .map( NullValueCheckStrategyGem::valueOf )
            .orElse( next().getNullValueCheckStrategy() );
    }

    @Override
    public NullValuePropertyMappingStrategyGem getNullValuePropertyMappingStrategy() {
        return beanMapping.map( BeanMappingGem.BeanMapping::nullValuePropertyMappingStrategy )
            .filter( GemValue::hasValue )
            .map( GemValue::getValue )
            .map( NullValuePropertyMappingStrategyGem::valueOf )
            .orElse( next().getNullValuePropertyMappingStrategy() );
    }

    @Override
    public NullValueMappingStrategyGem getNullValueMappingStrategy() {
        return beanMapping.map( BeanMappingGem.BeanMapping::nullValueMappingStrategy )
            .filter( GemValue::hasValue )
            .map( GemValue::getValue )
            .map( NullValueMappingStrategyGem::valueOf )
            .orElse( next().getNullValueMappingStrategy() );
    }

    @Override
    public BuilderGem.Builder getBuilder() {
        return beanMapping.map( BeanMappingGem.BeanMapping::builder )
            .filter( GemValue::hasValue )
            .map( GemValue::getValue )
            .orElse( next().getBuilder() );
    }

    // @BeanMapping specific

    public SelectionParameters getSelectionParameters() {
        return selectionParameters;
    }

    public boolean isignoreByDefault() {
        return beanMapping.map( BeanMappingGem.BeanMapping::ignoreByDefault )
            .map( GemValue::get )
            .orElse( false );
    }

    public List<String> getIgnoreUnmappedSourceProperties() {
        return beanMapping.map( BeanMappingGem.BeanMapping::ignoreUnmappedSourceProperties )
            .map( GemValue::get )
            .orElse( Collections.emptyList() );
    }

    public AnnotationMirror getMirror() {
        return beanMapping.map( BeanMappingGem.BeanMapping::mirror ).orElse( null );
    }

    @Override
    public boolean hasAnnotation() {
        return beanMapping.isPresent();
    }
}
