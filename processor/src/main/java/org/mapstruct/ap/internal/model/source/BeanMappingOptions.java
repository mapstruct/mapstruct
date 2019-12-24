/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
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
import org.mapstruct.ap.internal.util.FormattingMessager;
import org.mapstruct.ap.internal.util.Message;

/**
 * Represents an bean mapping as configured via {@code @BeanMapping}.
 *
 * @author Sjaak Derksen
 */
public class BeanMappingOptions extends DelegatingOptions {

    private final SelectionParameters selectionParameters;
    private final BeanMappingPrism prism;

    /**
     * creates a mapping for inheritance. Will set
     *
     * @return new mapping
     */
    public static BeanMappingOptions forInheritance(BeanMappingOptions beanMapping) {
        BeanMappingOptions options =  new BeanMappingOptions(
            SelectionParameters.forInheritance( beanMapping.selectionParameters ),
            beanMapping.prism,
            beanMapping
        );
        return options;
    }

    public static BeanMappingOptions getInstanceOn(BeanMappingPrism prism, MapperOptions mapperOptions,
                                                   ExecutableElement method, FormattingMessager messager,
                                                   Types typeUtils, TypeFactory typeFactory
    ) {
        if ( prism == null || !isConsistent( prism, method, messager ) ) {
            BeanMappingOptions options = new BeanMappingOptions( null, null, mapperOptions );
            return options;
        }

        Objects.requireNonNull( method );
        Objects.requireNonNull( messager );
        Objects.requireNonNull( method );
        Objects.requireNonNull( typeUtils );
        Objects.requireNonNull( typeFactory );

        SelectionParameters selectionParameters = new SelectionParameters(
            prism.qualifiedBy(),
            prism.qualifiedByName(),
            TypeKind.VOID != prism.resultType().getKind() ? prism.resultType() : null,
            typeUtils
        );

        //TODO Do we want to add the reporting policy to the BeanMapping as well? To give more granular support?
        BeanMappingOptions options = new BeanMappingOptions( selectionParameters, prism, mapperOptions );
        return options;
    }

    private static boolean isConsistent(BeanMappingPrism prism, ExecutableElement method,
                                        FormattingMessager messager) {
        if ( TypeKind.VOID == prism.resultType().getKind()
            && prism.qualifiedBy().isEmpty()
            && prism.qualifiedByName().isEmpty()
            && prism.ignoreUnmappedSourceProperties().isEmpty()
            && null == prism.values.nullValueCheckStrategy()
            && null == prism.values.nullValuePropertyMappingStrategy()
            && null == prism.values.nullValueMappingStrategy()
            && null == prism.values.ignoreByDefault()
            && null == prism.values.builder() ) {

            messager.printMessage( method, Message.BEANMAPPING_NO_ELEMENTS );
            return false;
        }
        return true;
    }

    private BeanMappingOptions(SelectionParameters selectionParameters, BeanMappingPrism prism,
                               DelegatingOptions next) {
        super( next );
        this.selectionParameters = selectionParameters;
        this.prism = prism;
    }

    // @Mapping, @BeanMapping

    @Override
    public NullValueCheckStrategyPrism getNullValueCheckStrategy() {
        return null == prism || null == prism.values.nullValueCheckStrategy() ?
            next().getNullValueCheckStrategy()
            : NullValueCheckStrategyPrism.valueOf( prism.nullValueCheckStrategy() );
    }

    @Override
    public NullValuePropertyMappingStrategyPrism getNullValuePropertyMappingStrategy() {
        return null == prism || null == prism.values.nullValuePropertyMappingStrategy() ?
            next().getNullValuePropertyMappingStrategy()
            : NullValuePropertyMappingStrategyPrism.valueOf( prism.nullValuePropertyMappingStrategy() );
    }

    @Override
    public NullValueMappingStrategyPrism getNullValueMappingStrategy() {
        return null == prism || null == prism.values.nullValueMappingStrategy() ?
            next().getNullValueMappingStrategy()
            : NullValueMappingStrategyPrism.valueOf( prism.nullValueMappingStrategy() );
    }

    @Override
    public BuilderPrism getBuilderPrism() {
        return null == prism || null == prism.values.builder() ? next().getBuilderPrism() : prism.builder();
    }

    // @BeanMapping specific

    public SelectionParameters getSelectionParameters() {
        return selectionParameters;
    }

    public boolean isignoreByDefault() {
        return null == prism ? false : prism.ignoreByDefault();
    }

    public List<String> getIgnoreUnmappedSourceProperties() {
        return null == prism ? Collections.emptyList() : prism.ignoreUnmappedSourceProperties();
    }

    public AnnotationMirror getMirror() {
        return null == prism ? null : prism.mirror;
    }

    @Override
    public boolean hasAnnotation() {
        return prism != null;
    }
}
