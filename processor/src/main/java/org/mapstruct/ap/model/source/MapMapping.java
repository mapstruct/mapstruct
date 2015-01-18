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
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;

import org.mapstruct.ap.prism.MapMappingPrism;

/**
 * Represents a map mapping as configured via {@code @MapMapping}.
 *
 * @author Gunnar Morling
 */
public class MapMapping {

    private final String keyFormat;
    private final List<TypeMirror> keyQualifiers;
    private final String valueFormat;
    private final List<TypeMirror> valueQualifiers;
    private final AnnotationMirror mirror;

    public static MapMapping fromPrism(MapMappingPrism mapMapping, ExecutableElement method, Messager messager) {
        if ( mapMapping == null ) {
            return null;
        }

        if ( mapMapping.keyDateFormat().isEmpty()
            && mapMapping.keyQualifiedBy().isEmpty()
            && mapMapping.valueDateFormat().isEmpty()
            && mapMapping.valueQualifiedBy().isEmpty() ) {
            messager.printMessage(
                Diagnostic.Kind.ERROR,
                "'keyDateFormat', 'keyQualifiedBy', 'valueDateFormat' and 'valueQualfiedBy' are all undefined in "
                    + "@MapMapping, define at least one of them.",
                method
            );
        }


        return new MapMapping(
            mapMapping.keyDateFormat(),
            mapMapping.keyQualifiedBy(),
            mapMapping.valueDateFormat(),
            mapMapping.valueQualifiedBy(),
            mapMapping.mirror
        );
    }

    private MapMapping(
            String keyFormat,
            List<TypeMirror> keyQualifiers,
            String valueFormat,
            List<TypeMirror> valueQualifiers,
            AnnotationMirror mirror) {
        this.keyFormat = keyFormat;
        this.keyQualifiers = keyQualifiers;
        this.valueFormat = valueFormat;
        this.valueQualifiers = valueQualifiers;
        this.mirror = mirror;
    }

    public String getKeyFormat() {
        return keyFormat;
    }

    public List<TypeMirror>  getKeyQualifiers() {
        return keyQualifiers;
    }

    public String getValueFormat() {
        return valueFormat;
    }

    public List<TypeMirror>  getValueQualifiers() {
        return valueQualifiers;
    }

    public AnnotationMirror getMirror() {
        return mirror;
    }
}
