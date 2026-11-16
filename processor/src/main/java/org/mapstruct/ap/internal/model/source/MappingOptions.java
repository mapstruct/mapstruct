/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;

import org.mapstruct.ap.internal.gem.MappingGem;
import org.mapstruct.ap.internal.gem.MappingsGem;
import org.mapstruct.ap.internal.gem.NullValueCheckStrategyGem;
import org.mapstruct.ap.internal.gem.NullValuePropertyMappingStrategyGem;
import org.mapstruct.ap.internal.model.common.FormattingParameters;
import org.mapstruct.ap.internal.util.ElementUtils;
import org.mapstruct.ap.internal.util.FormattingMessager;
import org.mapstruct.ap.internal.util.Message;
import org.mapstruct.ap.internal.util.TypeUtils;
import org.mapstruct.tools.gem.GemValue;

/**
 * Represents a property mapping as configured via {@code @Mapping} (no intermediate state).
 *
 * @author Gunnar Morling
 */
public class MappingOptions extends DelegatingOptions {

    private static final Pattern JAVA_EXPRESSION = Pattern.compile( "^\\s*java\\((.*)\\)\\s*$", Pattern.DOTALL );

    private final String sourceName;
    private final String constant;
    private final String javaExpression;
    private final String defaultJavaExpression;
    private final String conditionJavaExpression;
    private final String targetName;
    private final String defaultValue;
    private final FormattingParameters formattingParameters;
    private final SelectionParameters selectionParameters;

    private final boolean isIgnored;
    private final Set<String> dependsOn;

    private final Element element;
    private final AnnotationValue sourceAnnotationValue;
    private final AnnotationValue targetAnnotationValue;
    private final MappingGem mapping;

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

    public static void addInstances(MappingsGem gem, ExecutableElement method,
                                    BeanMappingOptions beanMappingOptions,
                                    FormattingMessager messager, TypeUtils typeUtils,
                                    Set<MappingOptions> mappings) {

        for ( MappingGem mapping : gem.value().getValue() ) {
            addInstance( mapping, method, beanMappingOptions, messager, typeUtils, mappings );
        }
    }

    public static void addInstance(MappingGem mapping, ExecutableElement method,
                                   BeanMappingOptions beanMappingOptions, FormattingMessager messager,
                                   TypeUtils typeUtils,
                                   Set<MappingOptions> mappings) {

        if ( !isConsistent( mapping, method, messager ) ) {
            return;
        }

        String source = mapping.source().getValue();
        String constant = mapping.constant().getValue();
        String expression = getExpression( mapping, method, messager );
        String defaultExpression = getDefaultExpression( mapping, method, messager );
        String conditionExpression = getConditionExpression( mapping, method, messager );
        String dateFormat = mapping.dateFormat().getValue();
        String numberFormat = mapping.numberFormat().getValue();
        String locale = mapping.locale().getValue();

        String defaultValue = mapping.defaultValue().getValue();

        Set<String> dependsOn = mapping.dependsOn().hasValue() ?
            new LinkedHashSet<>( mapping.dependsOn().getValue() ) :
            Collections.emptySet();

        FormattingParameters formattingParam = new FormattingParameters(
            dateFormat,
            numberFormat,
            mapping.mirror(),
            mapping.dateFormat().getAnnotationValue(),
            method,
            locale
        );
        SelectionParameters selectionParams = new SelectionParameters(
            mapping.qualifiedBy().get(),
            mapping.qualifiedByName().get(),
            mapping.conditionQualifiedBy().get(),
            mapping.conditionQualifiedByName().get(),
            mapping.resultType().getValue(),
            typeUtils
        );

        MappingOptions options = new MappingOptions(
            mapping.target().getValue(),
            method,
            mapping.target().getAnnotationValue(),
            source,
            mapping.source().getAnnotationValue(),
            constant,
            expression,
            defaultExpression,
            conditionExpression,
            defaultValue,
            mapping.ignore().get(),
            formattingParam,
            selectionParams,
            dependsOn,
            mapping,
            null,
            beanMappingOptions
        );

        if ( mappings.contains( options ) ) {
            messager.printMessage( method, Message.PROPERTYMAPPING_DUPLICATE_TARGETS, mapping.target().get() );
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
            null,
            null,
            true,
            null,
            SelectionParameters.empty(),
            Collections.emptySet(),
            null,
            null,
            null
        );
    }

    private static boolean isConsistent(MappingGem gem, ExecutableElement method,
                                        FormattingMessager messager) {

        if ( !gem.target().hasValue() ) {
            messager.printMessage(
                method,
                gem.mirror(),
                gem.target().getAnnotationValue(),
                Message.PROPERTYMAPPING_EMPTY_TARGET
            );
            return false;
        }

        Message message = null;
        if ( gem.source().hasValue() && gem.constant().hasValue() ) {
            message = Message.PROPERTYMAPPING_SOURCE_AND_CONSTANT_BOTH_DEFINED;
        }
        else if ( gem.expression().hasValue() && gem.conditionQualifiedByName().hasValue() ) {
            message = Message.PROPERTYMAPPING_EXPRESSION_AND_CONDITION_QUALIFIED_BY_NAME_BOTH_DEFINED;
        }
        else if ( gem.source().hasValue() && gem.expression().hasValue() ) {
            message = Message.PROPERTYMAPPING_SOURCE_AND_EXPRESSION_BOTH_DEFINED;
        }
        else if ( gem.expression().hasValue() && gem.constant().hasValue() ) {
            message = Message.PROPERTYMAPPING_EXPRESSION_AND_CONSTANT_BOTH_DEFINED;
        }
        else if ( gem.expression().hasValue() && gem.defaultValue().hasValue() ) {
            message = Message.PROPERTYMAPPING_EXPRESSION_AND_DEFAULT_VALUE_BOTH_DEFINED;
        }
        else if ( gem.constant().hasValue() && gem.defaultValue().hasValue() ) {
            message = Message.PROPERTYMAPPING_CONSTANT_AND_DEFAULT_VALUE_BOTH_DEFINED;
        }
        else if ( gem.expression().hasValue() && gem.defaultExpression().hasValue() ) {
            message = Message.PROPERTYMAPPING_EXPRESSION_AND_DEFAULT_EXPRESSION_BOTH_DEFINED;
        }
        else if ( gem.expression().hasValue() && gem.conditionExpression().hasValue() ) {
            message = Message.PROPERTYMAPPING_EXPRESSION_AND_CONDITION_EXPRESSION_BOTH_DEFINED;
        }
        else if ( gem.constant().hasValue() && gem.defaultExpression().hasValue() ) {
            message = Message.PROPERTYMAPPING_CONSTANT_AND_DEFAULT_EXPRESSION_BOTH_DEFINED;
        }
        else if ( gem.constant().hasValue() && gem.conditionExpression().hasValue() ) {
            message = Message.PROPERTYMAPPING_CONSTANT_AND_CONDITION_EXPRESSION_BOTH_DEFINED;
        }
        else if ( gem.defaultValue().hasValue() && gem.defaultExpression().hasValue() ) {
            message = Message.PROPERTYMAPPING_DEFAULT_VALUE_AND_DEFAULT_EXPRESSION_BOTH_DEFINED;
        }
        else if ( gem.expression().hasValue()
            && ( gem.qualifiedByName().hasValue() || gem.qualifiedBy().hasValue() ) ) {
            message = Message.PROPERTYMAPPING_EXPRESSION_AND_QUALIFIER_BOTH_DEFINED;
        }
        else if ( gem.nullValuePropertyMappingStrategy().hasValue() && gem.defaultValue().hasValue() ) {
            message = Message.PROPERTYMAPPING_DEFAULT_VALUE_AND_NVPMS;
        }
        else if ( gem.nullValuePropertyMappingStrategy().hasValue() && gem.constant().hasValue() ) {
            message = Message.PROPERTYMAPPING_CONSTANT_VALUE_AND_NVPMS;
        }
        else if ( gem.nullValuePropertyMappingStrategy().hasValue() && gem.expression().hasValue() ) {
            message = Message.PROPERTYMAPPING_EXPRESSION_VALUE_AND_NVPMS;
        }
        else if ( gem.nullValuePropertyMappingStrategy().hasValue() && gem.defaultExpression().hasValue() ) {
            message = Message.PROPERTYMAPPING_DEFAULT_EXPERSSION_AND_NVPMS;
        }
        else if ( gem.nullValuePropertyMappingStrategy().hasValue()
            && gem.ignore().hasValue() && gem.ignore().getValue() ) {
            message = Message.PROPERTYMAPPING_IGNORE_AND_NVPMS;
        }
        else if ( ".".equals( gem.target().get() ) && gem.ignore().hasValue() && gem.ignore().getValue() ) {
            message = Message.PROPERTYMAPPING_TARGET_THIS_AND_IGNORE;
        }
        else if ( ".".equals( gem.target().get() ) && !gem.source().hasValue() ) {
            message = Message.PROPERTYMAPPING_TARGET_THIS_NO_SOURCE;
        }

        if ( message == null ) {
            return true;
        }
        else {
            messager.printMessage( method, gem.mirror(), message );
            return false;
        }
    }

    @SuppressWarnings("checkstyle:parameternumber")
    private MappingOptions(String targetName,
                           Element element,
                           AnnotationValue targetAnnotationValue,
                           String sourceName,
                           AnnotationValue sourceAnnotationValue,
                           String constant,
                           String javaExpression,
                           String defaultJavaExpression,
                           String conditionJavaExpression,
                           String defaultValue,
                           boolean isIgnored,
                           FormattingParameters formattingParameters,
                           SelectionParameters selectionParameters,
                           Set<String> dependsOn,
                           MappingGem mapping,
                           InheritContext inheritContext,
                           DelegatingOptions next
    ) {
        super( next );
        this.targetName = targetName;
        this.element = element;
        this.targetAnnotationValue = targetAnnotationValue;
        this.sourceName = sourceName;
        this.sourceAnnotationValue = sourceAnnotationValue;
        this.constant = constant;
        this.javaExpression = javaExpression;
        this.defaultJavaExpression = defaultJavaExpression;
        this.conditionJavaExpression = conditionJavaExpression;
        this.defaultValue = defaultValue;
        this.isIgnored = isIgnored;
        this.formattingParameters = formattingParameters;
        this.selectionParameters = selectionParameters;
        this.dependsOn = dependsOn;
        this.mapping = mapping;
        this.inheritContext = inheritContext;
    }

    private static String getExpression(MappingGem mapping, ExecutableElement element,
                                        FormattingMessager messager) {
        if ( !mapping.expression().hasValue() ) {
            return null;
        }

        Matcher javaExpressionMatcher = JAVA_EXPRESSION.matcher( mapping.expression().get() );

        if ( !javaExpressionMatcher.matches() ) {
            messager.printMessage(
                element,
                mapping.mirror(),
                mapping.expression().getAnnotationValue(),
                Message.PROPERTYMAPPING_INVALID_EXPRESSION
            );
            return null;
        }

        return javaExpressionMatcher.group( 1 ).trim();
    }

    private static String getDefaultExpression(MappingGem mapping, ExecutableElement element,
                                        FormattingMessager messager) {
        if ( !mapping.defaultExpression().hasValue() ) {
            return null;
        }

        Matcher javaExpressionMatcher = JAVA_EXPRESSION.matcher( mapping.defaultExpression().get() );

        if ( !javaExpressionMatcher.matches() ) {
            messager.printMessage(
                element,
                mapping.mirror(),
                mapping.defaultExpression().getAnnotationValue(),
                Message.PROPERTYMAPPING_INVALID_DEFAULT_EXPRESSION
            );
            return null;
        }

        return javaExpressionMatcher.group( 1 ).trim();
    }

    private static String getConditionExpression(MappingGem mapping, ExecutableElement element,
                                        FormattingMessager messager) {
        if ( !mapping.conditionExpression().hasValue() ) {
            return null;
        }

        Matcher javaExpressionMatcher = JAVA_EXPRESSION.matcher( mapping.conditionExpression().get() );

        if ( !javaExpressionMatcher.matches() ) {
            messager.printMessage(
                element,
                mapping.mirror(),
                mapping.conditionExpression().getAnnotationValue(),
                Message.PROPERTYMAPPING_INVALID_CONDITION_EXPRESSION
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

    public String getConditionJavaExpression() {
        return conditionJavaExpression;
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
        return Optional.ofNullable( mapping ).map( MappingGem::mirror ).orElse( null );
    }

    public Element getElement() {
        return element;
    }

    public AnnotationValue getDependsOnAnnotationValue() {
        return Optional.ofNullable( mapping )
            .map( MappingGem::dependsOn )
            .map( GemValue::getAnnotationValue )
            .orElse( null );
    }

    public Set<String> getDependsOn() {
        return dependsOn;
    }

    public InheritContext getInheritContext() {
        return inheritContext;
    }

    @Override
    public NullValueCheckStrategyGem getNullValueCheckStrategy() {
        return Optional.ofNullable( mapping ).map( MappingGem::nullValueCheckStrategy )
            .filter( GemValue::hasValue )
            .map( GemValue::getValue )
            .map( NullValueCheckStrategyGem::valueOf )
            .orElse( next().getNullValueCheckStrategy() );
    }

    @Override
    public NullValuePropertyMappingStrategyGem getNullValuePropertyMappingStrategy() {
        return Optional.ofNullable( mapping ).map( MappingGem::nullValuePropertyMappingStrategy )
            .filter( GemValue::hasValue )
            .map( GemValue::getValue )
            .map( NullValuePropertyMappingStrategyGem::valueOf )
            .orElse( next().getNullValuePropertyMappingStrategy() );
    }

    @Override
    public MappingControl getMappingControl(ElementUtils elementUtils) {
        return Optional.ofNullable( mapping ).map( MappingGem::mappingControl )
            .filter( GemValue::hasValue )
            .map( GemValue::getValue )
            .map( mc -> MappingControl.fromTypeMirror( mc, elementUtils ) )
            .orElse( next().getMappingControl( elementUtils ) );
    }

    /**
     *  Mapping can only be inversed if the source was not a constant nor an expression
     *
     * @return true when the above applies
     */
    public boolean canInverse() {
        return constant == null && javaExpression == null;
    }

    public MappingOptions copyForInverseInheritance(SourceMethod templateMethod,
                                                    BeanMappingOptions beanMappingOptions ) {

        MappingOptions mappingOptions = new MappingOptions(
            sourceName != null ? sourceName : targetName,
            templateMethod.getExecutable(),
            targetAnnotationValue,
            sourceName != null ? targetName : null,
            sourceAnnotationValue,
            null, // constant
            null, // expression
            null, // defaultExpression
            null, // conditionExpression
            null,
            isIgnored,
            formattingParameters,
            selectionParameters,
            Collections.emptySet(),
            mapping,
            new InheritContext( true, false, templateMethod ),
            beanMappingOptions
        );
        return mappingOptions;

    }

    /**
     * Creates a copy of this mapping
     *
     * @param templateMethod the template method for the inheritance
     * @param beanMappingOptions the bean mapping options
     *
     * @return the copy
     */
    public MappingOptions copyForForwardInheritance(SourceMethod templateMethod,
                                                    BeanMappingOptions beanMappingOptions ) {
        MappingOptions mappingOptions = new MappingOptions(
            targetName,
            templateMethod.getExecutable(),
            targetAnnotationValue,
            sourceName,
            sourceAnnotationValue,
            constant,
            javaExpression,
            defaultJavaExpression,
            conditionJavaExpression,
            defaultValue,
            isIgnored,
            formattingParameters,
            selectionParameters,
            dependsOn,
            mapping,
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
        return mapping != null;
    }

}

