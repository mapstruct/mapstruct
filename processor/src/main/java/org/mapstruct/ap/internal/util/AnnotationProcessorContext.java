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
import javax.tools.Diagnostic.Kind;

import org.mapstruct.ap.spi.AccessorNamingStrategy;
import org.mapstruct.ap.spi.AstModifyingAnnotationProcessor;
import org.mapstruct.ap.spi.BuilderProvider;
import org.mapstruct.ap.spi.DefaultAccessorNamingStrategy;
import org.mapstruct.ap.spi.DefaultBuilderProvider;
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
    private boolean initialized;

    private AccessorNamingUtils accessorNaming;
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
        this.initialized = true;
    }

    protected List<AstModifyingAnnotationProcessor> findAstModifyingAnnotationProcessors() {
        List<AstModifyingAnnotationProcessor> processors = new ArrayList<>();

        ServiceLoader<AstModifyingAnnotationProcessor> loader = ServiceLoader.load(
                AstModifyingAnnotationProcessor.class, AnnotationProcessorContext.class.getClassLoader()
        );

        for ( AstModifyingAnnotationProcessor astModifyingAnnotationProcessor : loader ) {
            processors.add( astModifyingAnnotationProcessor );
        }

        return processors;
    }

    protected boolean hasMoreAnnotationProcessors(Iterator<AstModifyingAnnotationProcessor> it) {
        try {
            return it.hasNext();
        }
        catch ( Throwable t ) {
            if ( messager != null ) {
                messager.printMessage( Kind.ERROR,
                        "MapStruct: Could not load a configured AstModifyingAnnotationProcesor. "
                        + "Will try to skip it." );
            }

            /*
             * Will give it one more try before giving up
             * on any other processors might still be in the lazy iterator
             */

            try {
                it.next();
            }
            catch ( Throwable t2 ) { }

            try {
                return it.hasNext();
            }
            catch ( Throwable t2 ) {
                if ( messager != null ) {
                    messager.printMessage( Kind.ERROR,
                            "MapStruct: Could not skip conflicting AstModifyingAnnotationProcesor."
                            + " Will stop processing now." );
                }
                return false;
            }
        }
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
}
