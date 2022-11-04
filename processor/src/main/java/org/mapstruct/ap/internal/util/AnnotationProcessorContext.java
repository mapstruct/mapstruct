/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
import org.mapstruct.ap.spi.DefaultEnumMappingStrategy;
import org.mapstruct.ap.spi.EnumMappingStrategy;
import org.mapstruct.ap.spi.EnumTransformationStrategy;
import org.mapstruct.ap.spi.FreeBuilderAccessorNamingStrategy;
import org.mapstruct.ap.spi.ImmutablesAccessorNamingStrategy;
import org.mapstruct.ap.spi.ImmutablesBuilderProvider;
import org.mapstruct.ap.spi.MapStructProcessingEnvironment;
import org.mapstruct.ap.spi.NoOpBuilderProvider;

/**
 * Keeps contextual data in the scope of the entire annotation processor ("application scope").
 *
 * @author Gunnar Morling
 */
public class AnnotationProcessorContext implements MapStructProcessingEnvironment {

    private List<AstModifyingAnnotationProcessor> astModifyingAnnotationProcessors;

    private BuilderProvider builderProvider;
    private AccessorNamingStrategy accessorNamingStrategy;
    private EnumMappingStrategy enumMappingStrategy;
    private boolean initialized;
    private Map<String, EnumTransformationStrategy> enumTransformationStrategies;

    private AccessorNamingUtils accessorNaming;
    private Elements elementUtils;
    private Types typeUtils;
    private Messager messager;
    private boolean disableBuilder;
    private boolean verbose;

    private Map<String, String> options;

    public AnnotationProcessorContext(Elements elementUtils, Types typeUtils, Messager messager, boolean disableBuilder,
                                      boolean verbose, Map<String, String> options) {
        astModifyingAnnotationProcessors = java.util.Collections.unmodifiableList(
            findAstModifyingAnnotationProcessors( messager ) );
        this.elementUtils = elementUtils;
        this.typeUtils = typeUtils;
        this.messager = messager;
        this.disableBuilder = disableBuilder;
        this.verbose = verbose;
        this.options = java.util.Collections.unmodifiableMap( options );
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
        this.builderProvider = this.disableBuilder ?
            new NoOpBuilderProvider() :
            Services.get( BuilderProvider.class, defaultBuilderProvider );
        this.builderProvider.init( this );
        if ( verbose ) {
            messager.printMessage(
                Diagnostic.Kind.NOTE,
                "MapStruct: Using builder provider: " + this.builderProvider.getClass().getCanonicalName()
            );
        }
        this.accessorNaming = new AccessorNamingUtils( this.accessorNamingStrategy );

        this.enumMappingStrategy = Services.get( EnumMappingStrategy.class, new DefaultEnumMappingStrategy() );
        this.enumMappingStrategy.init( this );
        if ( verbose ) {
            messager.printMessage(
                Diagnostic.Kind.NOTE,
                "MapStruct: Using enum naming strategy: "
                    + this.enumMappingStrategy.getClass().getCanonicalName()
            );
        }

        this.enumTransformationStrategies = new LinkedHashMap<>();
        ServiceLoader<EnumTransformationStrategy> transformationStrategiesLoader = ServiceLoader.load(
            EnumTransformationStrategy.class,
            AnnotationProcessorContext.class.getClassLoader()
        );

        for ( EnumTransformationStrategy transformationStrategy : transformationStrategiesLoader ) {
            String transformationStrategyName = transformationStrategy.getStrategyName();
            if ( enumTransformationStrategies.containsKey( transformationStrategyName ) ) {
                throw new IllegalStateException(
                    "Multiple EnumTransformationStrategies are using the same ma,e. Found: " +
                        enumTransformationStrategies.get( transformationStrategyName ) + " and " +
                        transformationStrategy + " for name " + transformationStrategyName );
            }

            transformationStrategy.init( this );
            enumTransformationStrategies.put( transformationStrategyName, transformationStrategy );
        }


        this.initialized = true;
    }

    private static List<AstModifyingAnnotationProcessor> findAstModifyingAnnotationProcessors(Messager messager) {
        List<AstModifyingAnnotationProcessor> processors = new ArrayList<>();

        ServiceLoader<AstModifyingAnnotationProcessor> loader = ServiceLoader.load(
                AstModifyingAnnotationProcessor.class, AnnotationProcessorContext.class.getClassLoader()
        );

        // Lombok packages an AstModifyingAnnotationProcessor as part of their jar
        // this leads to problems within Eclipse when lombok is used as an agent
        // Therefore we are wrapping this into an iterator that can handle exceptions by ignoring
        // the faulty processor
        Iterator<AstModifyingAnnotationProcessor> loaderIterator = new FaultyDelegatingIterator(
            messager,
            loader.iterator()
        );

        while ( loaderIterator.hasNext() ) {
            AstModifyingAnnotationProcessor processor = loaderIterator.next();
            if ( processor != null ) {
                processors.add( processor );
            }
        }

        return processors;
    }

    private static class FaultyDelegatingIterator implements Iterator<AstModifyingAnnotationProcessor> {

        private final Messager messager;
        private final Iterator<AstModifyingAnnotationProcessor> delegate;

        private FaultyDelegatingIterator(Messager messager,
            Iterator<AstModifyingAnnotationProcessor> delegate) {
            this.messager = messager;
            this.delegate = delegate;
        }

        @Override
        public boolean hasNext() {
            // Check the delegate maximum of 5 times
            // before returning false
            int failures = 5;
            while ( failures > 0 ) {
                try {
                    return delegate.hasNext();
                }
                catch ( Throwable t ) {
                    failures--;
                    logFailure( t );
                }
            }

            return false;
        }

        @Override
        public AstModifyingAnnotationProcessor next() {
            try {
                return delegate.next();
            }
            catch ( Throwable t ) {
                logFailure( t );
                return null;
            }
        }

        private void logFailure(Throwable t) {
            StringWriter sw = new StringWriter();
            t.printStackTrace( new PrintWriter( sw ) );

            String reportableStacktrace = sw.toString().replace( System.lineSeparator(), "  " );

            messager.printMessage(
                Diagnostic.Kind.WARNING,
                "Failed to read AstModifyingAnnotationProcessor. Reading next processor. Reason: " +
                    reportableStacktrace
            );
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

    public EnumMappingStrategy getEnumMappingStrategy() {
        initialize();
        return enumMappingStrategy;
    }

    public BuilderProvider getBuilderProvider() {
        initialize();
        return builderProvider;
    }

    public Map<String, EnumTransformationStrategy> getEnumTransformationStrategies() {
        initialize();
        return enumTransformationStrategies;
    }

    public Map<String, String> getOptions() {
        return this.options;
    }
}
