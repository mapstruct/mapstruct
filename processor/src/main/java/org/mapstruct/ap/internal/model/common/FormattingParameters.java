/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.common;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;

/**
 *
 * @author Sjaak Derksen
 */
public class FormattingParameters {

    public static final FormattingParameters EMPTY = new FormattingParameters( null, null, null, null, null, null );

    private final String date;
    private final String number;
    private final AnnotationMirror mirror;
    private final AnnotationValue dateAnnotationValue;
    private final Element element;
    private final String locale;

    public FormattingParameters(String date, String number, AnnotationMirror mirror,
        AnnotationValue dateAnnotationValue, Element element, String locale) {
        this.date = date;
        this.number = number;
        this.mirror = mirror;
        this.dateAnnotationValue = dateAnnotationValue;
        this.element = element;
        this.locale = locale;
    }

    public String getDate() {
        return date;
    }

    public String getNumber() {
        return number;
    }

    public AnnotationMirror getMirror() {
        return mirror;
    }

    public AnnotationValue getDateAnnotationValue() {
        return dateAnnotationValue;
    }

    public Element getElement() {
        return element;
    }

    public String getLocale() {
        return locale;
    }
}
