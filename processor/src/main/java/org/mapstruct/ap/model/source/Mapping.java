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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.processing.Messager;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.tools.Diagnostic;

import org.mapstruct.ap.prism.MappingPrism;
import org.mapstruct.ap.prism.MappingsPrism;
import org.mapstruct.ap.util.AnnotationProcessingException;

/**
 * Represents a property mapping as configured via {@code @Mapping}.
 *
 * @author Gunnar Morling
 */
public class Mapping {

    private final String sourceName;
    private final String sourceParameterName;
    private final String sourcePropertyName;
    private final String expression;
    private final String targetName;
    private final String dateFormat;
    private final AnnotationMirror mirror;
    private final AnnotationValue sourceAnnotationValue;
    private final AnnotationValue targetAnnotationValue;

    public static Map<String, List<Mapping>> fromMappingsPrism(MappingsPrism mappingsAnnotation, Element element,
                                                               Messager messager) {
        Map<String, List<Mapping>> mappings = new HashMap<String, List<Mapping>>();

        for ( MappingPrism mappingPrism : mappingsAnnotation.value() ) {
            if ( !mappings.containsKey( mappingPrism.source() ) ) {
                mappings.put( mappingPrism.source(), new ArrayList<Mapping>() );
            }
            Mapping mapping = fromMappingPrism( mappingPrism, element, messager );
            if ( mapping != null ) {
                mappings.get( mappingPrism.source() ).add( mapping );
            }
        }

        return mappings;
    }

    public static Mapping fromMappingPrism(MappingPrism mappingPrism, Element element, Messager messager) {
        String[] sourceNameParts = getSourceNameParts(
            mappingPrism.source(),
            element,
            mappingPrism.mirror,
            mappingPrism.values.source()
        );

        if ( mappingPrism.source().isEmpty() && mappingPrism.expression().isEmpty() ) {
            messager.printMessage(
                Diagnostic.Kind.ERROR,
                "Either define a source or an expression in a Mapping",
                element
            );
            return null;
        }
        else if ( !mappingPrism.source().isEmpty() && !mappingPrism.expression().isEmpty() ) {
            messager.printMessage(
                Diagnostic.Kind.ERROR,
                "Source and expression are both defined in Mapping, either define a source or an expression",
                element
            );
            return null;
        }

        return new Mapping(
            mappingPrism.source(),
            sourceNameParts != null ? sourceNameParts[0] : null,
            sourceNameParts != null ? sourceNameParts[1] : mappingPrism.source(),
            mappingPrism.expression(),
            mappingPrism.target(),
            mappingPrism.dateFormat(),
            mappingPrism.mirror,
            mappingPrism.values.source(),
            mappingPrism.values.target()
        );
    }

    private static String[] getSourceNameParts(String sourceName, Element element, AnnotationMirror annotationMirror,
                                               AnnotationValue annotationValue) {
        if ( !sourceName.contains( "." ) ) {
            return null;
        }

        String[] parts = sourceName.split( "\\." );
        if ( parts.length != 2 ) {
            throw new AnnotationProcessingException(
                "Mapping of nested attributes not supported yet.",
                element,
                annotationMirror,
                annotationValue
            );
        }

        return parts;
    }

    private Mapping(String sourceName, String sourceParameterName, String sourcePropertyName, String expression,
                    String targetName, String dateFormat, AnnotationMirror mirror,
                    AnnotationValue sourceAnnotationValue, AnnotationValue targetAnnotationValue) {
        this.sourceName = sourceName;
        this.sourceParameterName = sourceParameterName;
        this.sourcePropertyName = sourcePropertyName;
        this.expression = expression;
        this.targetName = targetName.equals( "" ) ? sourceName : targetName;
        this.dateFormat = dateFormat;
        this.mirror = mirror;
        this.sourceAnnotationValue = sourceAnnotationValue;
        this.targetAnnotationValue = targetAnnotationValue;
    }

    /**
     * Returns the complete source name of this mapping, either a qualified (e.g. {@code parameter1.foo}) or
     * unqualified (e.g. {@code foo}) property reference.
     *
     * @return The complete source name of this mapping.
     */
    public String getSourceName() {
        return sourceName;
    }

    /**
     * Returns the unqualified name of the source property (i.e. without the parameter name if given) of this mapping.
     *
     * @return The unqualified name of the source property of this mapping.
     */
    public String getSourcePropertyName() {
        return sourcePropertyName;
    }

    /**
     * Returns the name of the source parameter of this mapping if the source name is qualified.
     *
     * @return The name of the source parameter of this mapping if given, {@code null} otherwise.
     */
    public String getSourceParameterName() {
        return sourceParameterName;
    }

    public String getExpression() {
        return expression;
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
        Mapping reverse = null;
        if ( expression != null ) {
            /* mapping can only be reversed if the source was not a constant */
            reverse = new Mapping(
                targetName,
                null,
                targetName,
                expression,
                sourceName,
                dateFormat,
                mirror,
                sourceAnnotationValue,
                targetAnnotationValue
            );
        }
        return reverse;
    }

    @Override
    public String toString() {
        return "Mapping {" +
            "\n    sourceName='" + sourceName + "\'," +
            "\n    targetName='" + targetName + "\'," +
            "\n}";
    }
}
