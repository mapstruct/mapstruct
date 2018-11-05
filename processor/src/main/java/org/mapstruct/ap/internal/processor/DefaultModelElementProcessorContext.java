/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.processor;

import java.util.Map;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic.Kind;

import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.option.Options;
import org.mapstruct.ap.internal.processor.ModelElementProcessor.ProcessorContext;
import org.mapstruct.ap.internal.util.AccessorNamingUtils;
import org.mapstruct.ap.internal.util.FormattingMessager;
import org.mapstruct.ap.internal.util.Message;
import org.mapstruct.ap.internal.util.RoundContext;
import org.mapstruct.ap.internal.util.workarounds.TypesDecorator;
import org.mapstruct.ap.internal.version.VersionInformation;

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
    private final Types delegatingTypes;
    private final AccessorNamingUtils accessorNaming;

    public DefaultModelElementProcessorContext(ProcessingEnvironment processingEnvironment, Options options,
            RoundContext roundContext, Map<String, String> notToBeImported) {

        this.processingEnvironment = processingEnvironment;
        this.messager = new DelegatingMessager( processingEnvironment.getMessager() );
        this.accessorNaming = roundContext.getAnnotationProcessorContext().getAccessorNaming();
        this.versionInformation = DefaultVersionInformation.fromProcessingEnvironment( processingEnvironment );
        this.delegatingTypes = new TypesDecorator( processingEnvironment, versionInformation );
        this.typeFactory = new TypeFactory(
            processingEnvironment.getElementUtils(),
            delegatingTypes,
            messager,
            roundContext,
            notToBeImported
        );
        this.options = options;
    }

    @Override
    public Filer getFiler() {
        return processingEnvironment.getFiler();
    }

    @Override
    public Types getTypeUtils() {
        return delegatingTypes;
    }

    @Override
    public Elements getElementUtils() {
        return processingEnvironment.getElementUtils();
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

        DelegatingMessager(Messager delegate) {
            this.delegate = delegate;
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

        public boolean isErroneous() {
            return isErroneous;
        }

    }
}
