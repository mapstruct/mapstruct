package org.mapstruct.ap.internal.processor;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import org.mapstruct.ap.internal.util.FormattingMessager;
import org.mapstruct.ap.internal.util.Message;

public class MapperAnnotatedFormattingMessenger implements FormattingMessager {

    private FormattingMessager delegateMessager;
    private TypeElement mapperTypeElement;
    private Element messageElement;

    public MapperAnnotatedFormattingMessenger(FormattingMessager delegateMessager, TypeElement mapperTypeElement) {
        this.delegateMessager = delegateMessager;
        this.mapperTypeElement = mapperTypeElement;
    }

    @Override
    public void printMessage(Message msg, Object... args) {
        delegateMessager.printMessage( msg, args );
    }

    @Override
    public void printMessage(Element e, Message msg, Object... args) {
        if (methodInMapperClass( e )) {

            delegateMessager
                            .printMessage(
                                mapperTypeElement,
                                Message.MESSAGE_MOVED_TO_MAPPER,
                                message,
                                messageElement,
                                mapperTypeElement.getSimpleName() );
        }
        else {
            delegateMessager.printMessage( e, msg, args );
        }
    }

    @Override
    public void printMessage(Element e, AnnotationMirror a, Message msg, Object... args) {
        delegateMessager.printMessage( e, a, msg, args );
    }

    @Override
    public void printMessage(Element e, AnnotationMirror a, AnnotationValue v, Message msg, Object... args) {
        delegateMessager.printMessage( e, a, v, msg, args );
    }

    @Override
    public void note(int level, Message log, Object... args) {
        delegateMessager.note( level, log, args );
    }

    @Override
    public boolean isErroneous() {
        return delegateMessager.isErroneous();
    }

    @Override
    public String messagePreProcessing(String message) {
        if ( methodInMapperClass( messageElement ) ) {
            return message;
        }
        return String
                     .format(
                         Message.MESSAGE_MOVED_TO_MAPPER.getDescription(),
                         message,
                         messageElement,
                         mapperTypeElement.getSimpleName() );
    }

    private boolean methodInMapperClass(Element e) {
        return mapperTypeElement == null || e.getEnclosingElement().equals( mapperTypeElement );
    }
}
