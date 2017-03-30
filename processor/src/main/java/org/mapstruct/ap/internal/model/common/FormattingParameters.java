/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.internal.model.common;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;

/**
 *
 * @author Sjaak Derksen
 */
public class FormattingParameters {

    public static final FormattingParameters EMPTY = new FormattingParameters( null, null, null, null, null );

    private final String date;
    private final String number;
    private final AnnotationMirror mirror;
    private final AnnotationValue dateAnnotationValue;
    private final Element element;

    public FormattingParameters(String date, String number, AnnotationMirror mirror,
        AnnotationValue dateAnnotationValue, Element element) {
        this.date = date;
        this.number = number;
        this.mirror = mirror;
        this.dateAnnotationValue = dateAnnotationValue;
        this.element = element;
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
}
