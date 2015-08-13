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
package org.mapstruct.ap.internal.model.source;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.prism.CollectionMappingStrategyPrism;
import org.mapstruct.ap.internal.prism.MappingPrism;
import org.mapstruct.ap.internal.prism.MappingsPrism;
import org.mapstruct.ap.internal.util.FormattingMessager;
import org.mapstruct.ap.internal.util.Message;

/**
 * Represents a property mapping as configured via {@code @Mapping}.
 *
 * @author Gunnar Morling
 */
public class Mapping {

    private static final Pattern JAVA_EXPRESSION = Pattern.compile( "^java\\((.*)\\)$" );

    private final String sourceName;
    private final String constant;
    private final String javaExpression;
    private final String targetName;
    private final String dateFormat;
    private final String defaultValue;
    private final List<TypeMirror> qualifiers;
    private final TypeMirror resultType;
    private final boolean isIgnored;
    private final List<String> dependsOn;

    private final AnnotationMirror mirror;
    private final AnnotationValue sourceAnnotationValue;
    private final AnnotationValue targetAnnotationValue;
    private final AnnotationValue dependsOnAnnotationValue;

    private SourceReference sourceReference;

    public static Map<String, List<Mapping>> fromMappingsPrism(MappingsPrism mappingsAnnotation,
                                                               ExecutableElement method,
                                                               FormattingMessager messager) {
        Map<String, List<Mapping>> mappings = new HashMap<String, List<Mapping>>();

        for ( MappingPrism mappingPrism : mappingsAnnotation.value() ) {
            Mapping mapping = fromMappingPrism( mappingPrism, method, messager );
            if ( mapping != null ) {
                List<Mapping> mappingsOfProperty = mappings.get( mappingPrism.target() );
                if ( mappingsOfProperty == null ) {
                    mappingsOfProperty = new ArrayList<Mapping>();
                    mappings.put( mappingPrism.target(), mappingsOfProperty );
                }

                mappingsOfProperty.add( mapping );

                if ( mappingsOfProperty.size() > 1 && !isEnumType( method.getReturnType() ) ) {
                    messager.printMessage( method, Message.PROPERTYMAPPING_DUPLICATE_TARGETS, mappingPrism.target() );
                }
            }
        }

        return mappings;
    }

    public static Mapping fromMappingPrism(MappingPrism mappingPrism, ExecutableElement element,
                                           FormattingMessager messager) {


        if ( mappingPrism.target().isEmpty() ) {
            messager.printMessage(
                element,
                mappingPrism.mirror,
                mappingPrism.values.target(),
                Message.PROPERTYMAPPING_EMPTY_TARGET
            );
            return null;
        }

        if ( !mappingPrism.source().isEmpty() && mappingPrism.values.constant() != null ) {
            messager.printMessage( element, Message.PROPERTYMAPPING_SOURCE_AND_CONSTANT_BOTH_DEFINED );
            return null;
        }
        else if ( !mappingPrism.source().isEmpty() && mappingPrism.values.expression() != null ) {
            messager.printMessage( element, Message.PROPERTYMAPPING_SOURCE_AND_EXPRESSION_BOTH_DEFINED );
            return null;
        }
        else if ( mappingPrism.values.expression() != null && mappingPrism.values.constant() != null ) {
            messager.printMessage( element, Message.PROPERTYMAPPING_EXPRESSION_AND_CONSTANT_BOTH_DEFINED );
            return null;
        }
        else if ( mappingPrism.values.expression() != null && mappingPrism.values.defaultValue() != null ) {
            messager.printMessage( element, Message.PROPERTYMAPPING_EXPRESSION_AND_DEFAULT_VALUE_BOTH_DEFINED );
            return null;
        }
        else if ( mappingPrism.values.constant() != null && mappingPrism.values.defaultValue() != null ) {
            messager.printMessage( element, Message.PROPERTYMAPPING_CONSTANT_AND_DEFAULT_VALUE_BOTH_DEFINED );
            return null;
        }

        String source = mappingPrism.source().isEmpty() ? null : mappingPrism.source();
        String constant = mappingPrism.values.constant() == null ? null : mappingPrism.constant();
        String expression = getExpression( mappingPrism, element, messager );
        String dateFormat = mappingPrism.values.dateFormat() == null ? null : mappingPrism.dateFormat();
        String defaultValue = mappingPrism.values.defaultValue() == null ? null : mappingPrism.defaultValue();

        boolean resultTypeIsDefined = mappingPrism.values.resultType() != null;
        TypeMirror resultType = resultTypeIsDefined ? mappingPrism.resultType() : null;
        List<String> dependsOn =
            mappingPrism.dependsOn() != null ? mappingPrism.dependsOn() : Collections.<String>emptyList();

        return new Mapping(
            source,
            constant,
            expression,
            mappingPrism.target(),
            dateFormat,
            defaultValue,
            mappingPrism.qualifiedBy(),
            mappingPrism.ignore(),
            mappingPrism.mirror,
            mappingPrism.values.source(),
            mappingPrism.values.target(),
            mappingPrism.values.dependsOn(),
            resultType,
            dependsOn
        );
    }

    @SuppressWarnings("checkstyle:parameternumber")
    private Mapping(String sourceName, String constant, String javaExpression, String targetName,
                    String dateFormat, String defaultValue, List<TypeMirror> qualifiers,
                    boolean isIgnored, AnnotationMirror mirror,
                    AnnotationValue sourceAnnotationValue, AnnotationValue targetAnnotationValue,
                    AnnotationValue dependsOnAnnotationValue,
                    TypeMirror resultType, List<String> dependsOn) {
        this.sourceName = sourceName;
        this.constant = constant;
        this.javaExpression = javaExpression;
        this.targetName = targetName;
        this.dateFormat = dateFormat;
        this.defaultValue = defaultValue;
        this.qualifiers = qualifiers;
        this.isIgnored = isIgnored;
        this.mirror = mirror;
        this.sourceAnnotationValue = sourceAnnotationValue;
        this.targetAnnotationValue = targetAnnotationValue;
        this.dependsOnAnnotationValue = dependsOnAnnotationValue;
        this.resultType = resultType;
        this.dependsOn = dependsOn;
    }

    private static String getExpression(MappingPrism mappingPrism, ExecutableElement element,
                                        FormattingMessager messager) {
        if ( mappingPrism.expression().isEmpty() ) {
            return null;
        }

        Matcher javaExpressionMatcher = JAVA_EXPRESSION.matcher( mappingPrism.expression() );

        if ( !javaExpressionMatcher.matches() ) {
            messager.printMessage(
                element, mappingPrism.mirror, mappingPrism.values.expression(),
                Message.PROPERTYMAPPING_INVALID_EXPRESSION
            );
            return null;
        }

        return javaExpressionMatcher.group( 1 ).trim();
    }

    private static boolean isEnumType(TypeMirror mirror) {
        return mirror.getKind() == TypeKind.DECLARED &&
            ( (DeclaredType) mirror ).asElement().getKind() == ElementKind.ENUM;
    }

    public void init(SourceMethod method, FormattingMessager messager, TypeFactory typeFactory) {

        if ( !method.isEnumMapping() ) {
            sourceReference = new SourceReference.BuilderFromMapping()
                .mapping( this )
                .method( method )
                .messager( messager )
                .typeFactory( typeFactory )
                .build();
        }
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

    public String getConstant() {
        return constant;
    }

    public String getJavaExpression() {
        return javaExpression;
    }

    public String getTargetName() {
        return targetName;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public List<TypeMirror> getQualifiers() {
        return qualifiers;
    }

    public boolean isIgnored() {
        return isIgnored;
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

    public AnnotationValue getDependsOnAnnotationValue() {
        return dependsOnAnnotationValue;
    }

    public SourceReference getSourceReference() {
        return sourceReference;
    }

    public TypeMirror getResultType() {
        return resultType;
    }

    public List<String> getDependsOn() {
        return dependsOn;
    }

    private boolean hasPropertyInReverseMethod(String name, SourceMethod method) {
        CollectionMappingStrategyPrism cms = method.getMapperConfiguration().getCollectionMappingStrategy();
        return method.getResultType().getPropertyWriteAccessors( cms ).containsKey( name );
    }

    public Mapping reverse(SourceMethod method, FormattingMessager messager, TypeFactory typeFactory) {

        // mapping can only be reversed if the source was not a constant nor an expression nor a nested property
        if ( constant != null || javaExpression != null ) {
            return null;
        }

        // should only ignore a property when 1) there is a sourceName defined or 2) there's a name match
        if ( isIgnored ) {
            if ( sourceName == null && !hasPropertyInReverseMethod( targetName, method ) ) {
                return null;
            }
        }

        // should not reverse a nested property
        if ( sourceReference != null && sourceReference.getPropertyEntries().size() > 1 ) {
            return null;
        }

        // should generate error when parameter
        if ( sourceReference != null && sourceReference.getPropertyEntries().isEmpty() ) {
            // parameter mapping only, apparently the @InheritReverseConfiguration is intentional
            // but erroneous. Lets raise an error to warn.
            messager.printMessage(
                method.getExecutable(), Message.PROPERTYMAPPING_REVERSAL_PROBLEM,
                sourceReference.getParameter()
            );
            return null;
        }

        Mapping reverse = new Mapping(
            sourceName != null ? targetName : null,
            null, // constant
            null, // expression
            sourceName != null ? sourceName : targetName,
            dateFormat,
            null,
            qualifiers,
            isIgnored,
            mirror,
            sourceAnnotationValue,
            targetAnnotationValue,
            dependsOnAnnotationValue,
            null,
            Collections.<String>emptyList()
        );

        reverse.init( method, messager, typeFactory );
        return reverse;
    }

    /**
     * Creates a copy of this mapping, which is adapted to the given method
     *
     * @param method the method to create the copy for
     * @return the copy
     */
    public Mapping copyForInheritanceTo(SourceMethod method) {
        Mapping mapping = new Mapping(
            sourceName,
            constant,
            javaExpression,
            targetName,
            dateFormat,
            defaultValue,
            qualifiers,
            isIgnored,
            mirror,
            sourceAnnotationValue,
            targetAnnotationValue,
            dependsOnAnnotationValue,
            resultType,
            dependsOn
        );

        if ( sourceReference != null ) {
            mapping.sourceReference = sourceReference.copyForInheritanceTo( method );
        }

        return mapping;
    }

    @Override
    public String toString() {
        return "Mapping {" +
            "\n    sourceName='" + sourceName + "\'," +
            "\n    targetName='" + targetName + "\'," +
            "\n}";
    }
}
