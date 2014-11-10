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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.processing.Messager;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import javax.tools.Diagnostic.Kind;

import org.mapstruct.ap.model.common.TypeFactory;
import org.mapstruct.ap.prism.MappingPrism;
import org.mapstruct.ap.prism.MappingsPrism;
import org.mapstruct.ap.util.Executables;

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
    private final List<TypeMirror> qualifiers;
    private final boolean isIgnored;

    private final AnnotationMirror mirror;
    private final AnnotationValue sourceAnnotationValue;
    private final AnnotationValue targetAnnotationValue;
    private SourceReference sourceReference;

    public static Map<String, List<Mapping>> fromMappingsPrism(MappingsPrism mappingsAnnotation,
                                                               ExecutableElement method,
                                                               Messager messager) {
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
                    messager.printMessage(
                        Kind.ERROR,
                        "Target property \"" + mappingPrism.target() + "\" must not be mapped more than once.",
                        method
                    );
                }
            }
        }

        return mappings;
    }

    private static boolean isEnumType(TypeMirror mirror) {
        return mirror.getKind() == TypeKind.DECLARED &&
            ( (DeclaredType) mirror ).asElement().getKind() == ElementKind.ENUM;
    }


    public static Mapping fromMappingPrism(MappingPrism mappingPrism, ExecutableElement element, Messager messager) {

        if ( mappingPrism.target().isEmpty() ) {
            messager.printMessage(
                Diagnostic.Kind.ERROR,
                "Target must not be empty in @Mapping",
                element,
                mappingPrism.mirror,
                mappingPrism.values.target()
            );
            return null;
        }

        if ( !mappingPrism.source().isEmpty() && !mappingPrism.constant().isEmpty() ) {
            messager.printMessage(
                Diagnostic.Kind.ERROR,
                "Source and constant are both defined in @Mapping, either define a source or a constant",
                element
            );
            return null;
        }
        else if ( !mappingPrism.source().isEmpty() && !mappingPrism.expression().isEmpty() ) {
            messager.printMessage(
                Diagnostic.Kind.ERROR,
                "Source and expression are both defined in @Mapping, either define a source or an expression",
                element
            );
            return null;
        }
        else if ( !mappingPrism.expression().isEmpty() && !mappingPrism.constant().isEmpty() ) {
            messager.printMessage(
                Diagnostic.Kind.ERROR,
                "Expression and constant are both defined in @Mapping, either define an expression or a constant",
                element
            );
            return null;
        }

        String source = mappingPrism.source().isEmpty() ? null : mappingPrism.source();
        String constant = mappingPrism.constant().isEmpty() ? null : mappingPrism.constant();
        String expression = getExpression( mappingPrism, element, messager );
        String dateFormat = mappingPrism.dateFormat().isEmpty() ? null : mappingPrism.dateFormat();

        return new Mapping(
            source,
            constant,
            expression,
            mappingPrism.target(),
            dateFormat,
            mappingPrism.qualifiedBy(),
            mappingPrism.ignore(),
            mappingPrism.mirror,
            mappingPrism.values.source(),
            mappingPrism.values.target()
        );
    }

    private static String getExpression(MappingPrism mappingPrism, ExecutableElement element, Messager messager) {
        if ( mappingPrism.expression().isEmpty() ) {
            return null;
        }

        Matcher javaExpressionMatcher = JAVA_EXPRESSION.matcher( mappingPrism.expression() );

        if ( !javaExpressionMatcher.matches() ) {
            messager.printMessage(
                Diagnostic.Kind.ERROR,
                "Value must be given in the form \"java(<EXPRESSION>)\"",
                element,
                mappingPrism.mirror,
                mappingPrism.values.expression()
            );
            return null;
        }

        return javaExpressionMatcher.group( 1 ).trim();
    }

    private Mapping(String sourceName, String constant, String javaExpression, String targetName,
                    String dateFormat, List<TypeMirror> qualifiers,
                    boolean isIgnored, AnnotationMirror mirror,
                    AnnotationValue sourceAnnotationValue, AnnotationValue targetAnnotationValue) {
        this.sourceName = sourceName;
        this.constant = constant;
        this.javaExpression = javaExpression;
        this.targetName = targetName;
        this.dateFormat = dateFormat;
        this.qualifiers = qualifiers;
        this.isIgnored = isIgnored;
        this.mirror = mirror;
        this.sourceAnnotationValue = sourceAnnotationValue;
        this.targetAnnotationValue = targetAnnotationValue;
    }

    public void init(SourceMethod method, Messager messager, TypeFactory typeFactory) {

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

    public SourceReference getSourceReference() {
        return sourceReference;
    }

    private boolean hasPropertyInReverseMethod( String name, SourceMethod method ) {
        boolean match = false;
        for ( ExecutableElement getter : method.getResultType().getGetters() ) {
            if ( Executables.getPropertyName( getter ).equals( name ) ) {
                match = true;
                break;
            }
        }
        for ( ExecutableElement getter : method.getResultType().getAlternativeTargetAccessors() ) {
            if ( Executables.getPropertyName( getter ).equals( name ) ) {
                match = true;
                break;
            }
        }
        return match;
    }

    public Mapping reverse(SourceMethod method, Messager messager, TypeFactory typeFactory) {

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
        if (sourceReference != null && sourceReference.getPropertyEntries().size() > 1 ) {
            return null;
        }

        // should generate error when parameter
        if (sourceReference != null && sourceReference.getPropertyEntries().isEmpty() ) {
            // parameter mapping only, apparantly the @InheritReverseConfiguration is intentional
            // but erroneous. Lets raise an error to warn.
            messager.printMessage(
                    Diagnostic.Kind.ERROR,
                    String.format( "Parameter %s cannot be reversed", sourceReference.getParameter() ),
                    method.getExecutable()
            );
            return null;
        }

        Mapping reverse = new Mapping(
            sourceName != null ? targetName : null,
            null, // constant
            null, // expression
            sourceName != null ? sourceName : targetName,
            dateFormat,
            qualifiers,
            isIgnored,
            mirror,
            sourceAnnotationValue,
            targetAnnotationValue
        );

        reverse.init( method, messager, typeFactory );
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
