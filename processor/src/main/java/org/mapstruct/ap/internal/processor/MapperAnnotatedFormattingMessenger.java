/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.processor;

import java.util.stream.Collectors;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.tools.Diagnostic.Kind;

import org.mapstruct.ap.internal.util.FormattingMessager;
import org.mapstruct.ap.internal.util.Message;
import org.mapstruct.ap.internal.util.TypeUtils;

/**
 * Handles redirection of errors/warnings so that they're shown on the mapper instead of hidden on a superclass.
 *
 * @author Ben Zegveld
 */
public class MapperAnnotatedFormattingMessenger implements FormattingMessager {

    private FormattingMessager delegateMessager;
    private TypeElement mapperTypeElement;
    private TypeUtils typeUtils;

    public MapperAnnotatedFormattingMessenger(FormattingMessager delegateMessager, TypeElement mapperTypeElement,
                                              TypeUtils typeUtils) {
        this.delegateMessager = delegateMessager;
        this.mapperTypeElement = mapperTypeElement;
        this.typeUtils = typeUtils;
    }

    @Override
    public void printMessage(Message msg, Object... args) {
        delegateMessager.printMessage( msg, args );
    }

    @Override
    public void printMessage(Element e, Message msg, Object... args) {
        delegateMessager
                        .printMessage(
                            determineDelegationElement( e ),
                            determineDelegationMessage( e, msg ),
                            determineDelegationArguments( e, msg, args ) );
    }

    @Override
    public void printMessage(Element e, AnnotationMirror a, Message msg, Object... args) {
        delegateMessager
                        .printMessage(
                            determineDelegationElement( e ),
                            a,
                            determineDelegationMessage( e, msg ),
                            determineDelegationArguments( e, msg, args ) );
    }

    @Override
    public void printMessage(Element e, AnnotationMirror a, AnnotationValue v, Message msg, Object... args) {
        delegateMessager
                        .printMessage(
                            determineDelegationElement( e ),
                            a,
                            v,
                            determineDelegationMessage( e, msg ),
                            determineDelegationArguments( e, msg, args ) );
    }

    @Override
    public void note(int level, Message log, Object... args) {
        delegateMessager.note( level, log, args );
    }

    @Override
    public boolean isErroneous() {
        return delegateMessager.isErroneous();
    }

    private Object[] determineDelegationArguments(Element e, Message msg, Object[] args) {
        if ( methodInMapperClass( e ) ) {
            return args;
        }
        String originalMessage = String.format( msg.getDescription(), args );
        return new Object[] { originalMessage, constructMethod( e ), e.getEnclosingElement().getSimpleName() };
    }

    /**
     * ExecutableElement.toString() has different values depending on the compiler. Constructing the method itself
     * manually will ensure that the message is always traceable to it's source.
     */
    private String constructMethod(Element e) {
        if ( e instanceof ExecutableElement ) {
            ExecutableElement ee = (ExecutableElement) e;
            StringBuilder method = new StringBuilder();
            method.append( typeUtils.asElement( ee.getReturnType() ).getSimpleName() );
            method.append( " " );
            method.append( ee.getSimpleName() );
            method.append( "(" );
            method.append( ee.getParameters()
                    .stream()
                    .map( this::parameterToString )
                    .collect( Collectors.joining( ", " ) ) );
            method.append( ")" );
            return method.toString();
        }
        return e.toString();
    }

    private String parameterToString(VariableElement element) {
        return typeUtils.asElement( element.asType() ).getSimpleName() + " " + element.getSimpleName();
    }

    private Message determineDelegationMessage(Element e, Message msg) {
        if ( methodInMapperClass( e ) ) {
            return msg;
        }
        if ( msg.getDiagnosticKind() == Kind.ERROR ) {
            return Message.MESSAGE_MOVED_TO_MAPPER_ERROR;
        }
        return Message.MESSAGE_MOVED_TO_MAPPER_WARNING;
    }

    private Element determineDelegationElement(Element e) {
        return methodInMapperClass( e ) ? e : mapperTypeElement;
    }

    private boolean methodInMapperClass(Element e) {
        return mapperTypeElement == null || e.equals( mapperTypeElement )
            || e.getEnclosingElement().equals( mapperTypeElement );
    }
}
