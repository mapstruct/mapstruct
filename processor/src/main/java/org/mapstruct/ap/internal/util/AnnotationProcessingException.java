/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.util;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;

/**
 * Indicates an error during annotation processing. Should only be thrown in non-recoverable situations such as errors
 * due to incomplete compilations etc. Expected errors to be propagated to the user of the annotation processor should
 * be raised using the {@link FormattingMessager} API instead.
 *
 * @author Gunnar Morling
 */
@SuppressWarnings("serial")
public class AnnotationProcessingException extends RuntimeException {

    private final Element element;
    private final AnnotationMirror annotationMirror;
    private final AnnotationValue annotationValue;

    public AnnotationProcessingException(String message) {
        this( message, null, null, null );
    }

    public AnnotationProcessingException(String message, Element element) {
        this( message, element, null, null );
    }

    public AnnotationProcessingException(String message, Element element, AnnotationMirror annotationMirror) {
        this( message, element, annotationMirror, null );
    }

    public AnnotationProcessingException(String message, Element element, AnnotationMirror annotationMirror,
                                         AnnotationValue annotationValue) {
        super( message );
        this.element = element;
        this.annotationMirror = annotationMirror;
        this.annotationValue = annotationValue;
    }

    public Element getElement() {
        return element;
    }

    public AnnotationMirror getAnnotationMirror() {
        return annotationMirror;
    }

    public AnnotationValue getAnnotationValue() {
        return annotationValue;
    }
}
