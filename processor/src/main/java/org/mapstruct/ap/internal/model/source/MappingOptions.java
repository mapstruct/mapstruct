/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.util.Types;

import org.mapstruct.ap.internal.model.common.FormattingParameters;
import org.mapstruct.ap.internal.prism.MappingPrism;
import org.mapstruct.ap.internal.prism.MappingsPrism;
import org.mapstruct.ap.internal.prism.NullValueCheckStrategyPrism;
import org.mapstruct.ap.internal.prism.NullValuePropertyMappingStrategyPrism;
import org.mapstruct.ap.internal.util.FormattingMessager;
import org.mapstruct.ap.internal.util.Message;

/**
 * Represents a property mapping as configured via {@code @Mapping} (no intermediate state).
 *
 * @author Gunnar Morling
 */
public class MappingOptions extends DelegatingOptions {

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

    private final AnnotationValue sourceAnnotationValue;
    private final AnnotationValue targetAnnotationValue;
    private final MappingPrism prism;

    private final InheritContext inheritContext;

    public static class InheritContext {

        private final boolean isReversed;
        private final boolean isForwarded;
        private final Method templateMethod;

        public InheritContext(boolean isReversed, boolean isForwarded, Method templateMethod) {
            this.isReversed = isReversed;
            this.isForwarded = isForwarded;
            this.templateMethod = templateMethod;
        }

        public boolean isReversed() {
            return isReversed;
        }

        public boolean isForwarded() {
            return isForwarded;
        }

        public Method getTemplateMethod() {
            return templateMethod;
        }
    }

    public static Set<String> getMappingTargetNamesBy(Predicate<MappingOptions> predicate,
                                                      Set<MappingOptions> mappings) {
        return mappings.stream()
            .filter( predicate )
            .map( MappingOptions::getTargetName )
            .collect( Collectors.toCollection( LinkedHashSet::new ) );
    }

    public static void addInstances(MappingsPrism prism, ExecutableElement method,
                                    BeanMappingOptions beanMappingOptions,
                                    FormattingMessager messager, Types typeUtils,
                                    Set<MappingOptions> mappings) {

        for ( MappingPrism mappingPrism : prism.value() ) {
            addInstance( mappingPrism, method, beanMappingOptions, messager, typeUtils, mappings );
        }
    }

    public static void addInstance(MappingPrism prism, ExecutableElement method, BeanMappingOptions beanMappingOptions,
                                   FormattingMessager messager, Types typeUtils, Set<MappingOptions> mappings) {

        if ( !isConsistent( prism, method, messager ) ) {
            return;
        }

        String source = prism.source().isEmpty() ? null : prism.source();
        String constant = prism.values.constant() == null ? null : prism.constant();
        String expression = getExpression( prism, method, messager );
        String defaultExpression = getDefaultExpression( prism, method, messager );
        String dateFormat = prism.values.dateFormat() == null ? null : prism.dateFormat();
        String numberFormat = prism.values.numberFormat() == null ? null : prism.numberFormat();
        String defaultValue = prism.values.defaultValue() == null ? null : prism.defaultValue();

        boolean resultTypeIsDefined = prism.values.resultType() != null;
        Set<String> dependsOn = prism.dependsOn() != null ?
            new LinkedHashSet( prism.dependsOn() ) :
            Collections.emptySet();

        FormattingParameters formattingParam = new FormattingParameters(
            dateFormat,
            numberFormat,
            prism.mirror,
            prism.values.dateFormat(),
            method
        );
        SelectionParameters selectionParams = new SelectionParameters(
            prism.qualifiedBy(),
            prism.qualifiedByName(),
            resultTypeIsDefined ? prism.resultType() : null,
            typeUtils
        );

        MappingOptions options = new MappingOptions(
            prism.target(),
            prism.values.target(),
            source,
            prism.values.source(),
            constant,
            expression,
            defaultExpression,
            defaultValue,
            prism.ignore(),
            formattingParam,
            selectionParams,
            dependsOn,
            prism,
            null,
            beanMappingOptions
        );

        if ( mappings.contains( options ) ) {
            messager.printMessage( method, Message.PROPERTYMAPPING_DUPLICATE_TARGETS, prism.target() );
        }
        else {
            mappings.add( options );
        }
    }

   public static MappingOptions forIgnore(String targetName) {
        return new MappingOptions(
            targetName,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            true,
            null,
            null,
            Collections.emptySet(),
            null,
            null,
            null
        );
    }

    private static boolean isConsistent(MappingPrism prism, ExecutableElement method,
                                        FormattingMessager messager) {

        if ( prism.target().isEmpty() ) {
            messager.printMessage(
                method,
                prism.mirror,
                prism.values.target(),
                Message.PROPERTYMAPPING_EMPTY_TARGET
            );
            return false;
        }

        Message message = null;
        if ( !prism.source().isEmpty() && prism.values.constant() != null ) {
            message = Message.PROPERTYMAPPING_SOURCE_AND_CONSTANT_BOTH_DEFINED;
        }
        else if ( !prism.source().isEmpty() && prism.values.expression() != null ) {
            message = Message.PROPERTYMAPPING_SOURCE_AND_EXPRESSION_BOTH_DEFINED;
        }
        else if ( prism.values.expression() != null && prism.values.constant() != null ) {
            message = Message.PROPERTYMAPPING_EXPRESSION_AND_CONSTANT_BOTH_DEFINED;
        }
        else if ( prism.values.expression() != null && prism.values.defaultValue() != null ) {
            message = Message.PROPERTYMAPPING_EXPRESSION_AND_DEFAULT_VALUE_BOTH_DEFINED;
        }
        else if ( prism.values.constant() != null && prism.values.defaultValue() != null ) {
            message = Message.PROPERTYMAPPING_CONSTANT_AND_DEFAULT_VALUE_BOTH_DEFINED;
        }
        else if ( prism.values.expression() != null && prism.values.defaultExpression() != null ) {
            message = Message.PROPERTYMAPPING_EXPRESSION_AND_DEFAULT_EXPRESSION_BOTH_DEFINED;
        }
        else if ( prism.values.constant() != null && prism.values.defaultExpression() != null ) {
            message = Message.PROPERTYMAPPING_CONSTANT_AND_DEFAULT_EXPRESSION_BOTH_DEFINED;
        }
        else if ( prism.values.defaultValue() != null && prism.values.defaultExpression() != null ) {
            message = Message.PROPERTYMAPPING_DEFAULT_VALUE_AND_DEFAULT_EXPRESSION_BOTH_DEFINED;
        }
        else if ( prism.values.expression() != null
            && ( prism.values.qualifiedByName() != null || prism.values.qualifiedBy() != null ) ) {
            message = Message.PROPERTYMAPPING_EXPRESSION_AND_QUALIFIER_BOTH_DEFINED;
        }
        else if ( prism.values.nullValuePropertyMappingStrategy() != null
            && prism.values.defaultValue() != null ) {
            message = Message.PROPERTYMAPPING_DEFAULT_VALUE_AND_NVPMS;
        }
        else if ( prism.values.nullValuePropertyMappingStrategy() != null
            && prism.values.constant() != null ) {
            message = Message.PROPERTYMAPPING_CONSTANT_VALUE_AND_NVPMS;
        }
        else if ( prism.values.nullValuePropertyMappingStrategy() != null
            && prism.values.expression() != null ) {
            message = Message.PROPERTYMAPPING_EXPRESSION_VALUE_AND_NVPMS;
        }
        else if ( prism.values.nullValuePropertyMappingStrategy() != null
            && prism.values.defaultExpression() != null ) {
            message = Message.PROPERTYMAPPING_DEFAULT_EXPERSSION_AND_NVPMS;
        }
        else if ( prism.values.nullValuePropertyMappingStrategy() != null
            && prism.ignore() != null && prism.ignore() ) {
            message = Message.PROPERTYMAPPING_IGNORE_AND_NVPMS;
        }

        if ( message == null ) {
            return true;
        }
        else {
            messager.printMessage( method, prism.mirror, message );
            return false;
        }
    }

    @SuppressWarnings("checkstyle:parameternumber")
    private MappingOptions(String targetName,
                           AnnotationValue targetAnnotationValue,
                           String sourceName,
                           AnnotationValue sourceAnnotationValue,
                           String constant,
                           String javaExpression,
                           String defaultJavaExpression,
                           String defaultValue,
                           boolean isIgnored,
                           FormattingParameters formattingParameters,
                           SelectionParameters selectionParameters,
                           Set<String> dependsOn,
                           MappingPrism prism,
                           InheritContext inheritContext,
                           DelegatingOptions next
    ) {
        super( next );
        this.targetName = targetName;
        this.targetAnnotationValue = targetAnnotationValue;
        this.sourceName = sourceName;
        this.sourceAnnotationValue = sourceAnnotationValue;
        this.constant = constant;
        this.javaExpression = javaExpression;
        this.defaultJavaExpression = defaultJavaExpression;
        this.defaultValue = defaultValue;
        this.isIgnored = isIgnored;
        this.formattingParameters = formattingParameters;
        this.selectionParameters = selectionParameters;
        this.dependsOn = dependsOn;
        this.prism = prism;
        this.inheritContext = inheritContext;
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

    public String getTargetName() {
        return targetName;
    }

    public AnnotationValue getTargetAnnotationValue() {
        return targetAnnotationValue;
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

    public AnnotationValue getSourceAnnotationValue() {
        return sourceAnnotationValue;
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
        return prism == null ? null : prism.mirror;
    }

    public AnnotationValue getDependsOnAnnotationValue() {
        return prism == null ? null : prism.values.dependsOn();
    }

    public Set<String> getDependsOn() {
        return dependsOn;
    }

    public InheritContext getInheritContext() {
        return inheritContext;
    }

    @Override
    public NullValueCheckStrategyPrism getNullValueCheckStrategy() {
        return null == prism || null == prism.values.nullValueCheckStrategy() ?
            next().getNullValueCheckStrategy()
            : NullValueCheckStrategyPrism.valueOf( prism.nullValueCheckStrategy() );
    }

    @Override
    public NullValuePropertyMappingStrategyPrism getNullValuePropertyMappingStrategy() {
        return null == prism || null == prism.values.nullValuePropertyMappingStrategy() ?
            next().getNullValuePropertyMappingStrategy()
            : NullValuePropertyMappingStrategyPrism.valueOf( prism.nullValuePropertyMappingStrategy() );
    }

    /**
     *  mapping can only be inversed if the source was not a constant nor an expression nor a nested property
     *  and the mapping is not a 'target-source-ignore' mapping
     *
     * @return true when the above applies
     */
    public boolean canInverse() {
        return constant == null && javaExpression == null && !( isIgnored && sourceName == null );
    }

    public MappingOptions copyForInverseInheritance(SourceMethod templateMethod,
                                                    BeanMappingOptions beanMappingOptions ) {

        MappingOptions mappingOptions = new MappingOptions(
            sourceName != null ? sourceName : targetName,
            targetAnnotationValue,
            sourceName != null ? targetName : null,
            sourceAnnotationValue,
            null, // constant
            null, // expression
            null, // defaultExpression
            null,
            isIgnored,
            formattingParameters,
            selectionParameters,
            Collections.emptySet(),
            prism,
            new InheritContext( true, false, templateMethod ),
            beanMappingOptions
        );
        return mappingOptions;

    }

    /**
     * Creates a copy of this mapping
     *
     * @return the copy
     */
    public MappingOptions copyForForwardInheritance(SourceMethod templateMethod,
                                                    BeanMappingOptions beanMappingOptions ) {
        MappingOptions mappingOptions = new MappingOptions(
            targetName,
            targetAnnotationValue,
            sourceName,
            sourceAnnotationValue,
            constant,
            javaExpression,
            defaultJavaExpression,
            defaultValue,
            isIgnored,
            formattingParameters,
            selectionParameters,
            dependsOn,
            prism,
            new InheritContext( false, true, templateMethod ),
            beanMappingOptions
        );
        return mappingOptions;
    }

    @Override
    public boolean equals(Object o) {
        if ( this == o ) {
            return true;
        }
        if ( ".".equals( this.targetName ) ) {
            // target this will never be equal to any other target this or any other.
            return false;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }
        MappingOptions mapping = (MappingOptions) o;
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

    @Override
    public boolean hasAnnotation() {
        return prism != null;
    }

}

