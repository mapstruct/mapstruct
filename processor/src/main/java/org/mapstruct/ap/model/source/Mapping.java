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

import java.util.HashMap;
import java.util.Map;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;

import org.mapstruct.ap.MappingPrism;
import org.mapstruct.ap.MappingsPrism;

/**
 * Represents a property mapping as configured via {@code @Mapping}.
 *
 * @author Gunnar Morling
 */
public class Mapping {

    private final String sourceName;
    private final String targetName;
    private final String dateFormat;
    private final AnnotationMirror mirror;
    private final AnnotationValue sourceAnnotationValue;
    private final AnnotationValue targetAnnotationValue;

    public static Map<String, Mapping> fromMappingsPrism(MappingsPrism mappingsAnnotation) {
        Map<String, Mapping> mappings = new HashMap<String, Mapping>();

        for ( MappingPrism mapping : mappingsAnnotation.value() ) {
            mappings.put( mapping.source(), fromMappingPrism( mapping ) );
        }

        return mappings;
    }

    public static Mapping fromMappingPrism(MappingPrism mapping) {
        return new Mapping(
            mapping.source(),
            mapping.target(),
            mapping.dateFormat(),
            mapping.mirror,
            mapping.values.source(),
            mapping.values.target()
        );
    }

    private Mapping(String sourceName, String targetName, String dateFormat, AnnotationMirror mirror,
                    AnnotationValue sourceAnnotationValue, AnnotationValue targetAnnotationValue) {
        this.sourceName = sourceName;
        this.targetName = targetName.equals( "" ) ? sourceName : targetName;
        this.dateFormat = dateFormat;
        this.mirror = mirror;
        this.sourceAnnotationValue = sourceAnnotationValue;
        this.targetAnnotationValue = targetAnnotationValue;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getTargetName() {
        return targetName;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public AnnotationMirror getMirror() {
        return mirror;
    }

    public AnnotationValue getSourceAnnotationValue() {
        return sourceAnnotationValue;
    }

    public AnnotationValue getTargetAnnotationValue() {
        return targetAnnotationValue;
    }

    public Mapping reverse() {
        return new Mapping( targetName, sourceName, dateFormat, mirror, sourceAnnotationValue, targetAnnotationValue );
    }

    @Override
    public String toString() {
        return "Mapping {" +
            "\n    sourceName='" + sourceName + "\'," +
            "\n    targetName='" + targetName + "\'," +
            "\n}";
    }
}
