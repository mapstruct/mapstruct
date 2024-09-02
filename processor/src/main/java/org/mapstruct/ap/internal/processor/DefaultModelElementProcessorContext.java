/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.processor;

import java.util.Map;
import java.util.stream.IntStream;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic.Kind;

import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.option.Options;
import org.mapstruct.ap.internal.processor.ModelElementProcessor.ProcessorContext;
import org.mapstruct.ap.internal.util.AccessorNamingUtils;
import org.mapstruct.ap.internal.util.ElementUtils;
import org.mapstruct.ap.internal.util.FormattingMessager;
import org.mapstruct.ap.internal.util.Message;
import org.mapstruct.ap.internal.util.RoundContext;
import org.mapstruct.ap.internal.util.TypeUtils;
import org.mapstruct.ap.internal.version.VersionInformation;
import org.mapstruct.ap.spi.EnumMappingStrategy;
import org.mapstruct.ap.spi.EnumTransformationStrategy;

/**
 * Default implementation of the processor context.
 *
 * @author Gunnar Morling
 */
public class DefaultModelElementProcessorContext implements ProcessorContext {

    private final ProcessingEnvironment processingEnvironment;
    private final DelegatingMessager messager;
    private final Options options;
    private final TypeFactory typeFactory;
    private final VersionInformation versionInformation;
    private final TypeUtils delegatingTypes;
    private final ElementUtils delegatingElements;
    private final AccessorNamingUtils accessorNaming;
    private final RoundContext roundContext;

    public DefaultModelElementProcessorContext(ProcessingEnvironment processingEnvironment, Options options,
            RoundContext roundContext, Map<String, String> notToBeImported, TypeElement mapperElement) {

        this.processingEnvironment = processingEnvironment;
        this.messager = new DelegatingMessager( processingEnvironment.getMessager(), options.isVerbose() );
        this.accessorNaming = roundContext.getAnnotationProcessorContext().getAccessorNaming();
        this.versionInformation = DefaultVersionInformation.fromProcessingEnvironment( processingEnvironment );
        this.delegatingTypes = TypeUtils.create( processingEnvironment, versionInformation );
        this.delegatingElements = ElementUtils.create( processingEnvironment, versionInformation, mapperElement );
        this.roundContext = roundContext;
        this.typeFactory = new TypeFactory(
            delegatingElements,
            delegatingTypes,
            messager,
            roundContext,
            notToBeImported,
            options.isVerbose(),
            versionInformation
        );
        this.options = options;
    }

    @Override
    public Filer getFiler() {
        return processingEnvironment.getFiler();
    }

    @Override
    public TypeUtils getTypeUtils() {
        return delegatingTypes;
    }

    @Override
    public ElementUtils getElementUtils() {
        return delegatingElements;
    }

    @Override
    public TypeFactory getTypeFactory() {
        return typeFactory;
    }

    @Override
    public FormattingMessager getMessager() {
        return messager;
    }

    @Override
    public AccessorNamingUtils getAccessorNaming() {
        return accessorNaming;
    }

    @Override
    public Map<String, EnumTransformationStrategy> getEnumTransformationStrategies() {
        return roundContext.getAnnotationProcessorContext().getEnumTransformationStrategies();
    }

    @Override
    public EnumMappingStrategy getEnumMappingStrategy() {
        return roundContext.getAnnotationProcessorContext().getEnumMappingStrategy();
    }

    @Override
    public Options getOptions() {
        return options;
    }

    @Override
    public VersionInformation getVersionInformation() {
        return versionInformation;
    }

    @Override
    public boolean isErroneous() {
        return messager.isErroneous();
    }

    private static final class DelegatingMessager implements FormattingMessager {

        private final Messager delegate;
        private boolean isErroneous = false;
        private final boolean verbose;

        DelegatingMessager(Messager delegate, boolean verbose) {
            this.delegate = delegate;
            this.verbose = verbose;
        }

        @Override
        public void printMessage(Message msg, Object... args) {
            String message = String.format( msg.getDescription(), args );
            delegate.printMessage( msg.getDiagnosticKind(), message );
            if ( msg.getDiagnosticKind() == Kind.ERROR ) {
                isErroneous = true;
            }
        }

        @Override
        public void printMessage(Element e, Message msg, Object... args) {
            String message = String.format( msg.getDescription(), args );
            delegate.printMessage( msg.getDiagnosticKind(), message, e );
            if ( msg.getDiagnosticKind() == Kind.ERROR ) {
                isErroneous = true;
            }
        }

        @Override
        public void printMessage(Element e, AnnotationMirror a, Message msg, Object... args) {
            if ( a == null ) {
                printMessage( e, msg, args );
            }
            else {
                String message = String.format( msg.getDescription(), args );
                delegate.printMessage( msg.getDiagnosticKind(), message, e, a );
                if ( msg.getDiagnosticKind() == Kind.ERROR ) {
                    isErroneous = true;
                }
            }
        }

        @Override
        public void printMessage(Element e, AnnotationMirror a, AnnotationValue v, Message msg,
                                 Object... args) {
            String message = String.format( msg.getDescription(), args );
            delegate.printMessage( msg.getDiagnosticKind(), message, e, a, v );
            if ( msg.getDiagnosticKind() == Kind.ERROR ) {
                isErroneous = true;
            }
        }

        public void note( int level, Message msg, Object... args ) {
            if ( verbose ) {
                StringBuilder builder = new StringBuilder();
                IntStream.range( 0, level ).mapToObj( i -> "-" ).forEach( builder::append );
                builder.append( " MapStruct: " ).append( String.format( msg.getDescription(), args ) );
                delegate.printMessage( Kind.NOTE, builder.toString() );
            }
        }

        @Override
        public boolean isErroneous() {
            return isErroneous;
        }

    }
}
