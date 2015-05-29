/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
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
