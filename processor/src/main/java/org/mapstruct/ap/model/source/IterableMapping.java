/**
 *  Copyright 2012-2013 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.model.source;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;

import org.mapstruct.ap.IterableMappingPrism;

/**
 * Represents an iterable mapping as configured via {@code @IterableMapping}.
 *
 * @author Gunnar Morling
 */
public class IterableMapping {

    private final String dateFormat;
    private final AnnotationMirror mirror;
    private final AnnotationValue dateFormatAnnotationValue;

    public static IterableMapping fromIterableMappingPrism(IterableMappingPrism iterableMapping) {
        return new IterableMapping(
            iterableMapping.dateFormat(),
            iterableMapping.mirror,
            iterableMapping.values.dateFormat()
        );
    }

    private IterableMapping(String dateFormat, AnnotationMirror mirror, AnnotationValue dateFormatAnnotationValue) {
        this.dateFormat = dateFormat;
        this.mirror = mirror;
        this.dateFormatAnnotationValue = dateFormatAnnotationValue;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public AnnotationMirror getMirror() {
        return mirror;
    }

    public AnnotationValue getDateFormatAnnotationValue() {
        return dateFormatAnnotationValue;
    }
}
