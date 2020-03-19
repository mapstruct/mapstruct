/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.util;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

import javax.annotation.processing.Messager;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

import org.mapstruct.ap.spi.AccessorNamingStrategy;
import org.mapstruct.ap.spi.AstModifyingAnnotationProcessor;
import org.mapstruct.ap.spi.BuilderProvider;
import org.mapstruct.ap.spi.DefaultAccessorNamingStrategy;
import org.mapstruct.ap.spi.DefaultBuilderProvider;
import org.mapstruct.ap.spi.DefaultEnumConstantNamingStrategy;
import org.mapstruct.ap.spi.EnumConstantNamingStrategy;
import org.mapstruct.ap.spi.FreeBuilderAccessorNamingStrategy;
import org.mapstruct.ap.spi.ImmutablesAccessorNamingStrategy;
import org.mapstruct.ap.spi.ImmutablesBuilderProvider;
import org.mapstruct.ap.spi.MapStructProcessingEnvironment;

/**
 * Keeps contextual data in the scope of the entire annotation processor ("application scope").
 *
 * @author Gunnar Morling
 */
public class AnnotationProcessorContext implements MapStructProcessingEnvironment {

    private List<AstModifyingAnnotationProcessor> astModifyingAnnotationProcessors;

    private BuilderProvider builderProvider;
    private AccessorNamingStrategy accessorNamingStrategy;
    private EnumConstantNamingStrategy enumConstantNamingStrategy;
    private boolean initialized;

    private AccessorNamingUtils accessorNaming;
    private ValueMappingUtils valueMappingUtils;
    private Elements elementUtils;
    private Types typeUtils;
    private Messager messager;
    private boolean verbose;

    public AnnotationProcessorContext(Elements elementUtils, Types typeUtils, Messager messager, boolean verbose) {
        astModifyingAnnotationProcessors = java.util.Collections.unmodifiableList(
            findAstModifyingAnnotationProcessors() );
        this.elementUtils = elementUtils;
        this.typeUtils = typeUtils;
        this.messager = messager;
        this.verbose = verbose;
    }

    /**
     * Method for initializing the context with the SPIs. The reason why we do this is due to the fact that
     * when custom SPI implementations are done and users don't set {@code proc:none} then our processor
     * would be triggered. And this context will always get initialized and the SPI won't be found. However,
     * if this is lazily evaluated it won't be a problem, as in the SPI implementation module there won't be any
     * processing done.
     */
    private void initialize() {
        if ( initialized ) {
            return;
        }

        AccessorNamingStrategy defaultAccessorNamingStrategy;
        BuilderProvider defaultBuilderProvider;
        EnumConstantNamingStrategy defaultEnumConstantNamingStrategy = new DefaultEnumConstantNamingStrategy();

        if ( elementUtils.getTypeElement( ImmutablesConstants.IMMUTABLE_FQN ) != null ) {
            defaultAccessorNamingStrategy = new ImmutablesAccessorNamingStrategy();
            defaultBuilderProvider = new ImmutablesBuilderProvider();
            if ( verbose ) {
                messager.printMessage( Diagnostic.Kind.NOTE, "MapStruct: Immutables found on classpath" );
            }
        }
        else if ( elementUtils.getTypeElement( FreeBuilderConstants.FREE_BUILDER_FQN ) != null ) {
            defaultAccessorNamingStrategy = new FreeBuilderAccessorNamingStrategy();
            defaultBuilderProvider = new DefaultBuilderProvider();
            if ( verbose ) {
                messager.printMessage( Diagnostic.Kind.NOTE, "MapStruct: Freebuilder found on classpath" );
            }
        }
        else {
            defaultAccessorNamingStrategy = new DefaultAccessorNamingStrategy();
            defaultBuilderProvider = new DefaultBuilderProvider();
        }
        this.accessorNamingStrategy = Services.get( AccessorNamingStrategy.class, defaultAccessorNamingStrategy );
        this.accessorNamingStrategy.init( this );
        if ( verbose ) {
            messager.printMessage(
                Diagnostic.Kind.NOTE,
                "MapStruct: Using accessor naming strategy: "
                    + this.accessorNamingStrategy.getClass().getCanonicalName()
            );
        }
        this.builderProvider = Services.get( BuilderProvider.class, defaultBuilderProvider );
        this.builderProvider.init( this );
        if ( verbose ) {
            messager.printMessage(
                Diagnostic.Kind.NOTE,
                "MapStruct: Using builder provider: " + this.builderProvider.getClass().getCanonicalName()
            );
        }
        this.accessorNaming = new AccessorNamingUtils( this.accessorNamingStrategy );
        this.enumConstantNamingStrategy = Services.get( EnumConstantNamingStrategy.class,
            defaultEnumConstantNamingStrategy
        );
        this.enumConstantNamingStrategy.init( this );
        if ( verbose ) {
            messager.printMessage(
                Diagnostic.Kind.NOTE,
                "MapStruct: Using enum value mapping strategy: "
                    + this.enumConstantNamingStrategy.getClass().getCanonicalName()
            );
        }
        this.valueMappingUtils = new ValueMappingUtils( enumConstantNamingStrategy );
        this.initialized = true;
    }

    private static List<AstModifyingAnnotationProcessor> findAstModifyingAnnotationProcessors() {
        List<AstModifyingAnnotationProcessor> processors = new ArrayList<>();

        ServiceLoader<AstModifyingAnnotationProcessor> loader = ServiceLoader.load(
                AstModifyingAnnotationProcessor.class, AnnotationProcessorContext.class.getClassLoader()
        );

        for ( AstModifyingAnnotationProcessor astModifyingAnnotationProcessor : loader ) {
            processors.add( astModifyingAnnotationProcessor );
        }

        return processors;
    }

    @Override
    public Elements getElementUtils() {
        return elementUtils;
    }

    @Override
    public Types getTypeUtils() {
        return typeUtils;
    }

    public List<AstModifyingAnnotationProcessor> getAstModifyingAnnotationProcessors() {
        return astModifyingAnnotationProcessors;
    }

    public AccessorNamingUtils getAccessorNaming() {
        initialize();
        return accessorNaming;
    }

    public AccessorNamingStrategy getAccessorNamingStrategy() {
        initialize();
        return accessorNamingStrategy;
    }

    public BuilderProvider getBuilderProvider() {
        initialize();
        return builderProvider;
    }

    public ValueMappingUtils getValueMappingUtils() {
        initialize();
        return valueMappingUtils;
    }
}
