/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

import org.mapstruct.ap.spi.AccessorNamingStrategy;
import org.mapstruct.ap.spi.AstModifyingAnnotationProcessor;
import org.mapstruct.ap.spi.BuilderProvider;
import org.mapstruct.ap.spi.DefaultAccessorNamingStrategy;
import org.mapstruct.ap.spi.DefaultBuilderProvider;
import org.mapstruct.ap.spi.ImmutablesAccessorNamingStrategy;
import org.mapstruct.ap.spi.ImmutablesBuilderProvider;

/**
 * Keeps contextual data in the scope of the entire annotation processor ("application scope").
 *
 * @author Gunnar Morling
 */
public class AnnotationProcessorContext {

    private List<AstModifyingAnnotationProcessor> astModifyingAnnotationProcessors;

    private BuilderProvider builderProvider;
    private AccessorNamingStrategy accessorNamingStrategy;
    private boolean initialized;

    private AccessorNamingUtils accessorNaming;
    private Elements elementUtils;

    public AnnotationProcessorContext(Elements elementUtils) {
        astModifyingAnnotationProcessors = java.util.Collections.unmodifiableList(
                findAstModifyingAnnotationProcessors() );
        this.elementUtils = elementUtils;
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
        TypeElement immutableElement = elementUtils.getTypeElement( ImmutablesConstants.IMMUTABLE_FQN );
        if ( immutableElement == null ) {
            defaultAccessorNamingStrategy = new DefaultAccessorNamingStrategy();
            defaultBuilderProvider = new DefaultBuilderProvider();
        }
        else {
            defaultAccessorNamingStrategy = new ImmutablesAccessorNamingStrategy();
            defaultBuilderProvider = new ImmutablesBuilderProvider();
        }
        this.accessorNamingStrategy = Services.get( AccessorNamingStrategy.class, defaultAccessorNamingStrategy );
        this.builderProvider = Services.get( BuilderProvider.class, defaultBuilderProvider );
        this.accessorNaming = new AccessorNamingUtils( this.accessorNamingStrategy );
        this.initialized = true;
    }

    private static List<AstModifyingAnnotationProcessor> findAstModifyingAnnotationProcessors() {
        List<AstModifyingAnnotationProcessor> processors = new ArrayList<>();

        ServiceLoader<AstModifyingAnnotationProcessor> loader = ServiceLoader.load(
                AstModifyingAnnotationProcessor.class, AnnotationProcessorContext.class.getClassLoader()
        );

        for ( Iterator<AstModifyingAnnotationProcessor> it = loader.iterator(); it.hasNext(); ) {
            processors.add( it.next() );
        }

        return processors;
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
