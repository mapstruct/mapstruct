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
package org.mapstruct.ap.model.source;

import java.util.List;
import javax.annotation.processing.Messager;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;

import org.mapstruct.ap.prism.IterableMappingPrism;

/**
 * Represents an iterable mapping as configured via {@code @IterableMapping}.
 *
 * @author Gunnar Morling
 */
public class IterableMapping {

    private final String dateFormat;
    private final List<TypeMirror> qualifiers;
    private final AnnotationMirror mirror;
    private final AnnotationValue dateFormatAnnotationValue;

    public static IterableMapping fromPrism(IterableMappingPrism iterableMapping, ExecutableElement method,
                                            Messager messager) {
        if ( iterableMapping == null ) {
            return null;
        }

        if ( iterableMapping.dateFormat().isEmpty() && iterableMapping.qualifiedBy().isEmpty() ) {
            messager.printMessage(
                Diagnostic.Kind.ERROR,
                "'dateformat' and 'qualifiedBy' are undefined in @IterableMapping, "
                    + "define at least one of them.",
                method
            );
        }

        return new IterableMapping(
            iterableMapping.dateFormat(),
            iterableMapping.qualifiedBy(),
            iterableMapping.mirror,
            iterableMapping.values.dateFormat()
        );
    }

    private IterableMapping(
            String dateFormat,
            List<TypeMirror> qualifiers,
            AnnotationMirror mirror,
            AnnotationValue dateFormatAnnotationValue) {
        this.dateFormat = dateFormat;
        this.qualifiers = qualifiers;
        this.mirror = mirror;
        this.dateFormatAnnotationValue = dateFormatAnnotationValue;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public List<TypeMirror> getQualifiers() {
        return qualifiers;
    }

    public AnnotationMirror getMirror() {
        return mirror;
    }

    public AnnotationValue getDateFormatAnnotationValue() {
        return dateFormatAnnotationValue;
    }
}
