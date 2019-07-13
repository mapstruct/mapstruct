/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;

import org.mapstruct.ap.internal.model.common.FormattingParameters;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.prism.MappingPrism;
import org.mapstruct.ap.internal.prism.MappingsPrism;
import org.mapstruct.ap.internal.prism.NullValueCheckStrategyPrism;
import org.mapstruct.ap.internal.prism.NullValuePropertyMappingStrategyPrism;
import org.mapstruct.ap.internal.util.AccessorNamingUtils;
import org.mapstruct.ap.internal.util.FormattingMessager;
import org.mapstruct.ap.internal.util.Message;
import org.mapstruct.ap.internal.util.Strings;

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
    private final String defaultJavaExpression;
    private final String targetName;
    private final String defaultValue;
    private final FormattingParameters formattingParameters;
    private final SelectionParameters selectionParameters;

    private final boolean isIgnored;
    private final Set<String> dependsOn;

    private final AnnotationMirror mirror;
    private final AnnotationValue sourceAnnotationValue;
    private final AnnotationValue targetAnnotationValue;
    private final AnnotationValue dependsOnAnnotationValue;
    private final NullValueCheckStrategyPrism nullValueCheckStrategy;
    private final NullValuePropertyMappingStrategyPrism nullValuePropertyMappingStrategy;

    private SourceReference sourceReference;
    private TargetReference targetReference;

    public static Set<String> getMappingTargetNamesBy(Predicate<Mapping> predicate, Set<Mapping> mappings) {
        return mappings.stream().filter( predicate ).map( Mapping::getTargetName ).collect( Collectors.toSet() );
    }

    public static Set<Mapping> fromMappingsPrism(MappingsPrism mappingsAnnotation, ExecutableElement method,
                                                 FormattingMessager messager, Types typeUtils) {
        Set<Mapping> mappings = new LinkedHashSet<>();

        for ( MappingPrism mappingPrism : mappingsAnnotation.value() ) {
            Mapping mapping = fromMappingPrism( mappingPrism, method, messager, typeUtils );
            if ( mapping != null ) {
                if ( !mappings.add( mapping ) ) {
                    messager.printMessage( method, Message.PROPERTYMAPPING_DUPLICATE_TARGETS, mappingPrism.target() );
                }
            }
        }

        return mappings;
    }

    public static Mapping fromMappingPrism(MappingPrism mappingPrism, ExecutableElement element,
        FormattingMessager messager, Types typeUtils) {

        if (!isConsistent( mappingPrism, element, messager  ) ) {
            return null;
        }

        String source = mappingPrism.source().isEmpty() ? null : mappingPrism.source();
        String constant = mappingPrism.values.constant() == null ? null : mappingPrism.constant();
        String expression = getExpression( mappingPrism, element, messager );
        String defaultExpression = getDefaultExpression( mappingPrism, element, messager );
        String dateFormat = mappingPrism.values.dateFormat() == null ? null : mappingPrism.dateFormat();
        String numberFormat = mappingPrism.values.numberFormat() == null ? null : mappingPrism.numberFormat();
        String defaultValue = mappingPrism.values.defaultValue() == null ? null : mappingPrism.defaultValue();

        boolean resultTypeIsDefined = mappingPrism.values.resultType() != null;
        Set<String> dependsOn = mappingPrism.dependsOn() != null ?
            new LinkedHashSet( mappingPrism.dependsOn() ) :
            Collections.<String>emptySet();

        FormattingParameters formattingParam = new FormattingParameters(
            dateFormat,
            numberFormat,
            mappingPrism.mirror,
            mappingPrism.values.dateFormat(),
            element
        );
        SelectionParameters selectionParams = new SelectionParameters(
            mappingPrism.qualifiedBy(),
            mappingPrism.qualifiedByName(),
            resultTypeIsDefined ? mappingPrism.resultType() : null,
            typeUtils
        );

        NullValueCheckStrategyPrism nullValueCheckStrategy =
            null == mappingPrism.values.nullValueCheckStrategy()
                ? null
                : NullValueCheckStrategyPrism.valueOf( mappingPrism.nullValueCheckStrategy() );

        NullValuePropertyMappingStrategyPrism nullValuePropertyMappingStrategy =
            null == mappingPrism.values.nullValuePropertyMappingStrategy()
                ? null
                : NullValuePropertyMappingStrategyPrism.valueOf( mappingPrism.nullValuePropertyMappingStrategy() );

        return new Mapping(
            source,
            constant,
            expression,
            defaultExpression,
            mappingPrism.target(),
            defaultValue,
            mappingPrism.ignore(),
            mappingPrism.mirror,
            mappingPrism.values.source(),
            mappingPrism.values.target(),
            formattingParam,
            selectionParams,
            mappingPrism.values.dependsOn(),
            dependsOn,
            nullValueCheckStrategy,
            nullValuePropertyMappingStrategy
        );
    }

   public static Mapping forIgnore( String targetName) {
        return new Mapping(
            null,
            null,
            null,
            null,
            targetName,
            null,
            true,
            null,
            null,
            null,
            null,
            null,
            null,
            new HashSet<>(),
            null,
            null
        );
    }

    private static boolean isConsistent(MappingPrism mappingPrism, ExecutableElement element,
                                        FormattingMessager messager) {

        if ( mappingPrism.target().isEmpty() ) {
            messager.printMessage(
                element,
                mappingPrism.mirror,
                mappingPrism.values.target(),
                Message.PROPERTYMAPPING_EMPTY_TARGET
            );
            return false;
        }

        if ( !mappingPrism.source().isEmpty() && mappingPrism.values.constant() != null ) {
            messager.printMessage(
                element,
                mappingPrism.mirror,
                Message.PROPERTYMAPPING_SOURCE_AND_CONSTANT_BOTH_DEFINED
            );
            return false;
        }
        else if ( !mappingPrism.source().isEmpty() && mappingPrism.values.expression() != null ) {
            messager.printMessage(
                element,
                mappingPrism.mirror,
                Message.PROPERTYMAPPING_SOURCE_AND_EXPRESSION_BOTH_DEFINED
            );
            return false;
        }
        else if ( mappingPrism.values.expression() != null && mappingPrism.values.constant() != null ) {
            messager.printMessage(
                element,
                mappingPrism.mirror,
                Message.PROPERTYMAPPING_EXPRESSION_AND_CONSTANT_BOTH_DEFINED
            );
            return false;
        }
        else if ( mappingPrism.values.expression() != null && mappingPrism.values.defaultValue() != null ) {
            messager.printMessage(
                element,
                mappingPrism.mirror,
                Message.PROPERTYMAPPING_EXPRESSION_AND_DEFAULT_VALUE_BOTH_DEFINED
            );
            return false;
        }
        else if ( mappingPrism.values.constant() != null && mappingPrism.values.defaultValue() != null ) {
            messager.printMessage(
                element,
                mappingPrism.mirror,
                Message.PROPERTYMAPPING_CONSTANT_AND_DEFAULT_VALUE_BOTH_DEFINED
            );
            return false;
        }
        else if ( mappingPrism.values.expression() != null && mappingPrism.values.defaultExpression() != null ) {
            messager.printMessage(
                element,
                mappingPrism.mirror,
                Message.PROPERTYMAPPING_EXPRESSION_AND_DEFAULT_EXPRESSION_BOTH_DEFINED
            );
            return false;
        }
        else if ( mappingPrism.values.constant() != null && mappingPrism.values.defaultExpression() != null ) {
            messager.printMessage(
                element,
                mappingPrism.mirror,
                Message.PROPERTYMAPPING_CONSTANT_AND_DEFAULT_EXPRESSION_BOTH_DEFINED
            );
            return false;
        }
        else if ( mappingPrism.values.defaultValue() != null && mappingPrism.values.defaultExpression() != null ) {
            messager.printMessage(
                element,
                mappingPrism.mirror,
                Message.PROPERTYMAPPING_DEFAULT_VALUE_AND_DEFAULT_EXPRESSION_BOTH_DEFINED
            );
            return false;
        }
        else if ( mappingPrism.values.nullValuePropertyMappingStrategy() != null
            && mappingPrism.values.defaultValue() != null ) {
            messager.printMessage(
                element,
                mappingPrism.mirror,
                Message.PROPERTYMAPPING_DEFAULT_VALUE_AND_NVPMS
            );
            return false;
        }
        else if ( mappingPrism.values.nullValuePropertyMappingStrategy() != null
            && mappingPrism.values.constant() != null ) {
            messager.printMessage(
                element,
                mappingPrism.mirror,
                Message.PROPERTYMAPPING_CONSTANT_VALUE_AND_NVPMS
            );
            return false;
        }
        else if ( mappingPrism.values.nullValuePropertyMappingStrategy() != null
            && mappingPrism.values.expression() != null ) {
            messager.printMessage(
                element,
                mappingPrism.mirror,
                Message.PROPERTYMAPPING_EXPRESSION_VALUE_AND_NVPMS
            );
            return false;
        }
        else if ( mappingPrism.values.nullValuePropertyMappingStrategy() != null
            && mappingPrism.values.defaultExpression() != null ) {
            messager.printMessage(
                element,
                mappingPrism.mirror,
                Message.PROPERTYMAPPING_DEFAULT_EXPERSSION_AND_NVPMS
            );
            return false;
        }
        else if ( mappingPrism.values.nullValuePropertyMappingStrategy() != null
            && mappingPrism.ignore() != null && mappingPrism.ignore() ) {
            messager.printMessage(
                element,
                mappingPrism.mirror,
                Message.PROPERTYMAPPING_IGNORE_AND_NVPMS
            );
            return false;
        }
        return true;
    }

    @SuppressWarnings("checkstyle:parameternumber")
    private Mapping( String sourceName, String constant, String javaExpression, String defaultJavaExpression,
                     String targetName, String defaultValue, boolean isIgnored, AnnotationMirror mirror,
                     AnnotationValue sourceAnnotationValue,  AnnotationValue targetAnnotationValue,
                     FormattingParameters formattingParameters, SelectionParameters selectionParameters,
                     AnnotationValue dependsOnAnnotationValue, Set<String> dependsOn,
                     NullValueCheckStrategyPrism nullValueCheckStrategy,
                     NullValuePropertyMappingStrategyPrism nullValuePropertyMappingStrategy ) {
        this.sourceName = sourceName;
        this.constant = constant;
        this.javaExpression = javaExpression;
        this.defaultJavaExpression = defaultJavaExpression;
        this.targetName = targetName;
        this.defaultValue = defaultValue;
        this.isIgnored = isIgnored;
        this.mirror = mirror;
        this.sourceAnnotationValue = sourceAnnotationValue;
        this.targetAnnotationValue = targetAnnotationValue;
        this.formattingParameters = formattingParameters;
        this.selectionParameters = selectionParameters;
        this.dependsOnAnnotationValue = dependsOnAnnotationValue;
        this.dependsOn = dependsOn;
        this.nullValueCheckStrategy = nullValueCheckStrategy;
        this.nullValuePropertyMappingStrategy = nullValuePropertyMappingStrategy;
    }

    private Mapping( Mapping mapping, TargetReference targetReference ) {
        this.sourceName = mapping.sourceName;
        this.constant = mapping.constant;
        this.javaExpression = mapping.javaExpression;
        this.defaultJavaExpression = mapping.defaultJavaExpression;
        this.targetName = Strings.join( targetReference.getElementNames(), "." );
        this.defaultValue = mapping.defaultValue;
        this.isIgnored = mapping.isIgnored;
        this.mirror = mapping.mirror;
        this.sourceAnnotationValue = mapping.sourceAnnotationValue;
        this.targetAnnotationValue = mapping.targetAnnotationValue;
        this.formattingParameters = mapping.formattingParameters;
        this.selectionParameters = mapping.selectionParameters;
        this.dependsOnAnnotationValue = mapping.dependsOnAnnotationValue;
        this.dependsOn = mapping.dependsOn;
        this.sourceReference = mapping.sourceReference;
        this.targetReference = targetReference;
        this.nullValueCheckStrategy = mapping.nullValueCheckStrategy;
        this.nullValuePropertyMappingStrategy = mapping.nullValuePropertyMappingStrategy;
    }

    private Mapping( Mapping mapping, SourceReference sourceReference ) {
        this.sourceName = Strings.join( sourceReference.getElementNames(), "." );
        this.constant = mapping.constant;
        this.javaExpression = mapping.javaExpression;
        this.defaultJavaExpression = mapping.defaultJavaExpression;
        this.targetName = mapping.targetName;
        this.defaultValue = mapping.defaultValue;
        this.isIgnored = mapping.isIgnored;
        this.mirror = mapping.mirror;
        this.sourceAnnotationValue = mapping.sourceAnnotationValue;
        this.targetAnnotationValue = mapping.targetAnnotationValue;
        this.formattingParameters = mapping.formattingParameters;
        this.selectionParameters = mapping.selectionParameters;
        this.dependsOnAnnotationValue = mapping.dependsOnAnnotationValue;
        this.dependsOn = mapping.dependsOn;
        this.sourceReference = sourceReference;
        this.targetReference = mapping.targetReference;
        this.nullValueCheckStrategy = mapping.nullValueCheckStrategy;
        this.nullValuePropertyMappingStrategy = mapping.nullValuePropertyMappingStrategy;
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

    private static String getDefaultExpression(MappingPrism mappingPrism, ExecutableElement element,
                                        FormattingMessager messager) {
        if ( mappingPrism.defaultExpression().isEmpty() ) {
            return null;
        }

        Matcher javaExpressionMatcher = JAVA_EXPRESSION.matcher( mappingPrism.defaultExpression() );

        if ( !javaExpressionMatcher.matches() ) {
            messager.printMessage(
                element, mappingPrism.mirror, mappingPrism.values.defaultExpression(),
                Message.PROPERTYMAPPING_INVALID_DEFAULT_EXPRESSION
            );
            return null;
        }

        return javaExpressionMatcher.group( 1 ).trim();
    }

    private static boolean isEnumType(TypeMirror mirror) {
        return mirror.getKind() == TypeKind.DECLARED &&
            ( (DeclaredType) mirror ).asElement().getKind() == ElementKind.ENUM;
    }

    public void init(SourceMethod method, FormattingMessager messager, TypeFactory typeFactory,
                     AccessorNamingUtils accessorNaming) {
        init( method, messager, typeFactory, accessorNaming, false, null );
    }

    /**
     * Initialize the Mapping.
     *
     * @param method the source method that the mapping belongs to
     * @param messager the messager that can be used for outputting messages
     * @param typeFactory the type factory
     * @param accessorNaming the accessor naming utils
     * @param isReverse whether the init is for a reverse mapping
     * @param reverseSourceParameter the source parameter from the revers mapping
     */
    private void init(SourceMethod method, FormattingMessager messager, TypeFactory typeFactory,
                      AccessorNamingUtils accessorNaming, boolean isReverse,
                      Parameter reverseSourceParameter) {

        sourceReference = new SourceReference.BuilderFromMapping()
            .mapping( this )
            .method( method )
            .messager( messager )
            .typeFactory( typeFactory )
            .build();

        targetReference = new TargetReference.BuilderFromTargetMapping()
            .mapping( this )
            .isReverse( isReverse )
            .method( method )
            .messager( messager )
            .typeFactory( typeFactory )
            .accessorNaming( accessorNaming )
            .reverseSourceParameter( reverseSourceParameter )
            .build();

    }

    /**
     * Initializes the mapping with a new source parameter.
     *
     * @param sourceParameter sets the source parameter that acts as a basis for this mapping
     */
    public void init( Parameter sourceParameter ) {
        if ( sourceReference != null ) {
            SourceReference oldSourceReference = sourceReference;
            sourceReference = new SourceReference.BuilderFromSourceReference()
                .sourceParameter( sourceParameter )
                .sourceReference( oldSourceReference )
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

    public String getDefaultJavaExpression() {
        return defaultJavaExpression;
    }

    public String getTargetName() {
        return targetName;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public FormattingParameters getFormattingParameters() {
        return formattingParameters;
    }

    public SelectionParameters getSelectionParameters() {
        return selectionParameters;
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

    public TargetReference getTargetReference() {
        return targetReference;
    }

    public NullValueCheckStrategyPrism getNullValueCheckStrategy() {
        return nullValueCheckStrategy;
    }

    public NullValuePropertyMappingStrategyPrism getNullValuePropertyMappingStrategy() {
        return nullValuePropertyMappingStrategy;
    }

    public Mapping popTargetReference() {
        if ( targetReference != null ) {
            TargetReference newTargetReference = targetReference.pop();
            if (newTargetReference != null ) {
                return new Mapping(this, newTargetReference );
            }
        }
        return null;
    }

    public Mapping popSourceReference() {
        if ( sourceReference != null ) {
            SourceReference newSourceReference = sourceReference.pop();
            if (newSourceReference != null ) {
                return new Mapping(this, newSourceReference );
            }
        }
        return null;
    }

    public Set<String> getDependsOn() {
        return dependsOn;
    }

    public Mapping reverse(SourceMethod method, FormattingMessager messager, TypeFactory typeFactory,
                           AccessorNamingUtils accessorNaming) {

        // mapping can only be reversed if the source was not a constant nor an expression nor a nested property
        // and the mapping is not a 'target-source-ignore' mapping
        if ( constant != null || javaExpression != null || ( isIgnored && sourceName == null ) ) {
            return null;
        }

        Mapping reverse = new Mapping(
            sourceName != null ? targetName : null,
            null, // constant
            null, // expression
            null, // defaultExpression
            sourceName != null ? sourceName : targetName,
            null,
            isIgnored,
            mirror,
            sourceAnnotationValue,
            targetAnnotationValue,
            formattingParameters,
            selectionParameters,
            dependsOnAnnotationValue,
            Collections.<String>emptySet(),
            nullValueCheckStrategy,
            nullValuePropertyMappingStrategy
        );

        reverse.init(
            method,
            messager,
            typeFactory,
            accessorNaming,
            true,
            sourceReference != null ? sourceReference.getParameter() : null
        );

        // check if the reverse mapping has indeed a write accessor, otherwise the mapping cannot be reversed
        if ( !reverse.targetReference.isValid() ) {
            return null;
        }

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
            defaultJavaExpression,
            targetName,
            defaultValue,
            isIgnored,
            mirror,
            sourceAnnotationValue,
            targetAnnotationValue,
            formattingParameters,
            selectionParameters,
            dependsOnAnnotationValue,
            dependsOn,
            nullValueCheckStrategy,
            nullValuePropertyMappingStrategy
        );

        if ( sourceReference != null ) {
            mapping.sourceReference = sourceReference.copyForInheritanceTo( method );
        }

        // TODO... must something be done here? Andreas?
        mapping.targetReference = targetReference;

        return mapping;
    }

    @Override
    public boolean equals(Object o) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }
        Mapping mapping = (Mapping) o;
        return targetName.equals( mapping.targetName );
    }

    @Override
    public int hashCode() {
        return Objects.hash( targetName );
    }

    @Override
    public String toString() {
        return "Mapping {" +
            "\n    sourceName='" + sourceName + "\'," +
            "\n    targetName='" + targetName + "\'," +
            "\n}";
    }

}

