/**
 *  Copyright 2012-2014 Gunnar Morling (http://www.gunnarmorling.de/)
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

import org.mapstruct.ap.MapMappingPrism;

/**
 * Represents a map mapping as configured via {@code @MapMapping}.
 *
 * @author Gunnar Morling
 */
public class MapMapping {

    private final String keyFormat;
    private final String valueFormat;
    private final AnnotationMirror mirror;

    public static MapMapping fromPrism(MapMappingPrism mapMapping) {
        if ( mapMapping == null ) {
            return null;
        }

        return new MapMapping(
            mapMapping.keyDateFormat(),
            mapMapping.valueDateFormat(),
            mapMapping.mirror
        );
    }

    private MapMapping(String keyFormat, String valueFormat, AnnotationMirror mirror) {
        this.keyFormat = keyFormat;
        this.valueFormat = valueFormat;
        this.mirror = mirror;
    }

    public String getKeyFormat() {
        return keyFormat;
    }

    public String getValueFormat() {
        return valueFormat;
    }

    public AnnotationMirror getMirror() {
        return mirror;
    }
}
