/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

import org.mapstruct.ap.internal.gem.BeanMappingGem;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.EnumMappingOptions;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.model.source.SelectionParameters;
import org.mapstruct.ap.internal.model.source.ValueMappingOptions;
import org.mapstruct.ap.internal.util.Message;
import org.mapstruct.ap.internal.util.Strings;
import org.mapstruct.ap.internal.util.TypeUtils;
import org.mapstruct.ap.spi.EnumTransformationStrategy;

import static org.mapstruct.ap.internal.gem.MappingConstantsGem.ANY_REMAINING;
import static org.mapstruct.ap.internal.gem.MappingConstantsGem.ANY_UNMAPPED;
import static org.mapstruct.ap.internal.gem.MappingConstantsGem.NULL;
import static org.mapstruct.ap.internal.gem.MappingConstantsGem.THROW_EXCEPTION;
import static org.mapstruct.ap.internal.util.Collections.first;

/**
 * A {@link ValueMappingMethod} which maps one value type to another, optionally configured by one or more
 * {@link ValueMappingOptions}s. For now, only enum-to-enum mapping is supported.
 *
 * @author Sjaak Derksen
 */
public class ValueMappingMethod extends MappingMethod {

    private final List<Annotation> annotations;
    private final List<MappingEntry> valueMappings;
    private final MappingEntry defaultTarget;
    private final MappingEntry nullTarget;

    private final Type unexpectedValueMappingException;

    private final boolean overridden;

    public static class Builder {

        private Method method;
        private MappingBuilderContext ctx;
        private ValueMappings valueMappings;
        private EnumMappingOptions enumMapping;
        private EnumTransformationStrategyInvoker enumTransformationInvoker;
        private boolean enumTransformationIllegalReported = false;

        public Builder mappingContext(MappingBuilderContext mappingContext) {
            this.ctx = mappingContext;
            return this;
        }

        public Builder method(Method sourceMethod) {
            this.method = sourceMethod;
            return this;
        }

        public Builder valueMappings(List<ValueMappingOptions> valueMappings) {
            this.valueMappings = new ValueMappings( valueMappings );
            return this;
        }

        public Builder enumMapping(EnumMappingOptions enumMapping) {
            this.enumMapping = enumMapping;
            return this;
        }

        public ValueMappingMethod build() {

            if ( !enumMapping.isValid() ) {
                return null;
            }

            initializeEnumTransformationStrategy();

            // initialize all relevant parameters
            List<MappingEntry> mappingEntries = new ArrayList<>();

            Type sourceType = first( method.getSourceParameters() ).getType();
            Type targetType = method.getResultType();

            if ( targetType.isEnumType() && valueMappings.nullTarget == null ) {
                // If null target is not set it means that the user has not explicitly defined a mapping for null
                valueMappings.nullValueTarget = ctx.getEnumMappingStrategy()
                    .getDefaultNullEnumConstant( targetType.getTypeElement() );
            }

            // enum-to-enum
            if ( sourceType.isEnumType() && targetType.isEnumType() ) {
                mappingEntries.addAll( enumToEnumMapping( method, sourceType, targetType ) );
            }
            else if ( sourceType.isEnumType() && targetType.isString() ) {
                mappingEntries.addAll( enumToStringMapping( method, sourceType ) );
            }
            else if ( sourceType.isString() && targetType.isEnumType() ) {
                mappingEntries.addAll( stringToEnumMapping( method, targetType ) );
            }

            // do before / after lifecycle mappings
            SelectionParameters selectionParameters = getSelectionParameters( method, ctx.getTypeUtils() );
            Set<String> existingVariables = new HashSet<>( method.getParameterNames() );
            List<LifecycleCallbackMethodReference> beforeMappingMethods =
                LifecycleMethodResolver.beforeMappingMethods( method, selectionParameters, ctx, existingVariables );
            List<LifecycleCallbackMethodReference> afterMappingMethods =
                LifecycleMethodResolver.afterMappingMethods( method, selectionParameters, ctx, existingVariables );
            List<Annotation> annotations;
            if ( method instanceof ForgedMethod ) {
                annotations = Collections.emptyList();
            }
            else {
                annotations = new ArrayList<>();
                AdditionalAnnotationsBuilder additionalAnnotationsBuilder =
                    new AdditionalAnnotationsBuilder(
                        ctx.getElementUtils(),
                        ctx.getTypeFactory(),
                        ctx.getMessager() );

                annotations.addAll( additionalAnnotationsBuilder.getProcessedAnnotations( method.getExecutable() ) );
            }
            // finally return a mapping
            return new ValueMappingMethod( method,
                annotations,
                mappingEntries,
                valueMappings.nullValueTarget,
                valueMappings.defaultTargetValue,
                determineUnexpectedValueMappingException(),
                beforeMappingMethods,
                afterMappingMethods
            );
        }

        private void initializeEnumTransformationStrategy() {
            if ( !enumMapping.hasNameTransformationStrategy() ) {
                enumTransformationInvoker = EnumTransformationStrategyInvoker.DEFAULT;
            }
            else {
                Map<String, EnumTransformationStrategy> enumTransformationStrategies =
                    ctx.getEnumTransformationStrategies();

                String nameTransformationStrategy = enumMapping.getNameTransformationStrategy();
                if ( enumTransformationStrategies.containsKey( nameTransformationStrategy ) ) {
                    enumTransformationInvoker = new EnumTransformationStrategyInvoker( enumTransformationStrategies.get(
                        nameTransformationStrategy ), enumMapping.getNameTransformationConfiguration() );
                }
            }

        }

        private String transform(String source) {
            try {
                return enumTransformationInvoker.transform( source );
            }
            catch ( IllegalArgumentException ex ) {
                if ( !enumTransformationIllegalReported ) {
                    enumTransformationIllegalReported = true;
                    ctx.getMessager().printMessage(
                        method.getExecutable(),
                        enumMapping.getMirror(),
                        Message.ENUMMAPPING_ILLEGAL_TRANSFORMATION,
                        enumTransformationInvoker.transformationStrategy.getStrategyName(),
                        ex.getMessage()
                    );
                }
                return source;
            }
        }

        private List<MappingEntry> enumToEnumMapping(Method method, Type sourceType, Type targetType ) {

            List<MappingEntry> mappings = new ArrayList<>();
            List<String> unmappedSourceConstants = new ArrayList<>( sourceType.getEnumConstants() );
            boolean sourceErrorOccurred = !reportErrorIfMappedSourceEnumConstantsDontExist( method, sourceType );
            boolean targetErrorOccurred = !reportErrorIfMappedTargetEnumConstantsDontExist( method, targetType );
            if ( sourceErrorOccurred || targetErrorOccurred ) {
                return mappings;
            }

            // Start to fill the mappings with the defined value mappings
            for ( ValueMappingOptions valueMapping : valueMappings.regularValueMappings ) {
                mappings.add( new MappingEntry( valueMapping.getSource(), valueMapping.getTarget() ) );
                unmappedSourceConstants.remove( valueMapping.getSource() );
            }

            // add mappings based on name
            if ( !valueMappings.hasMapAnyUnmapped ) {

                // We store the target constants in a map in order to support inherited inverse mapping
                // When using a nameTransformationStrategy the transformation should be done on the target enum
                // instead of the source enum
                Map<String, String> targetConstants = new LinkedHashMap<>();

                boolean enumMappingInverse = enumMapping.isInverse();
                TypeElement targetTypeElement = method.getReturnType().getTypeElement();
                for ( String targetEnumConstant : method.getReturnType().getEnumConstants() ) {
                    String targetNameEnum = getEnumConstant( targetTypeElement, targetEnumConstant );
                    if ( enumMappingInverse ) {
                        // If the mapping is inverse we have to change the target enum constant
                        targetConstants.put(
                            transform( targetNameEnum ),
                            targetEnumConstant
                        );
                    }
                    else {
                        targetConstants.put( targetNameEnum, targetEnumConstant );
                    }
                }

                TypeElement sourceTypeElement = sourceType.getTypeElement();
                for ( String sourceConstant : new ArrayList<>( unmappedSourceConstants ) ) {
                    String sourceNameConstant = getEnumConstant( sourceTypeElement, sourceConstant );
                    String targetConstant;
                    if ( !enumMappingInverse ) {
                        targetConstant = transform( sourceNameConstant );
                    }
                    else {
                        targetConstant = sourceNameConstant;
                    }

                    if ( targetConstants.containsKey( targetConstant ) ) {
                        mappings.add( new MappingEntry( sourceConstant, targetConstants.get( targetConstant ) ) );
                        unmappedSourceConstants.remove( sourceConstant );
                    }
                    else if ( NULL.equals( targetConstant ) ) {
                        mappings.add( new MappingEntry( sourceConstant, null ) );
                        unmappedSourceConstants.remove( sourceConstant );
                    }
                }

                if ( valueMappings.defaultTarget == null && !unmappedSourceConstants.isEmpty() ) {
                    String sourceErrorMessage = "source";
                    String targetErrorMessage = "target";
                    if ( method instanceof ForgedMethod && ( (ForgedMethod) method ).getHistory() != null ) {
                        ForgedMethodHistory history = ( (ForgedMethod) method ).getHistory();
                        sourceErrorMessage = history.createSourcePropertyErrorMessage();
                        targetErrorMessage =
                            "\"" + history.getTargetType().describe() + " " + history.createTargetPropertyName() + "\"";
                    }
                    // all sources should now be matched, there's no default to fall back to, so if sources remain,
                    // we have an issue.
                    ctx.getMessager().printMessage( method.getExecutable(),
                        Message.VALUEMAPPING_UNMAPPED_SOURCES,
                        sourceErrorMessage,
                        targetErrorMessage,
                        Strings.join( unmappedSourceConstants, ", " )
                    );

                }
            }
            return mappings;
        }

         private List<MappingEntry> enumToStringMapping(Method method, Type sourceType ) {

            List<MappingEntry> mappings = new ArrayList<>();
            List<String> unmappedSourceConstants = new ArrayList<>( sourceType.getEnumConstants() );
            boolean sourceErrorOccurred = !reportErrorIfMappedSourceEnumConstantsDontExist( method, sourceType );
            boolean anyRemainingUsedError = !reportErrorIfSourceEnumConstantsContainsAnyRemaining( method );
            if ( sourceErrorOccurred || anyRemainingUsedError ) {
                return mappings;
            }

            // Start to fill the mappings with the defined valueMappings
            for ( ValueMappingOptions valueMapping : valueMappings.regularValueMappings ) {
                mappings.add( new MappingEntry( valueMapping.getSource(), valueMapping.getTarget() ) );
                unmappedSourceConstants.remove( valueMapping.getSource() );
            }

            // add mappings based on name
            if ( !valueMappings.hasMapAnyUnmapped ) {

                TypeElement sourceTypeElement = sourceType.getTypeElement();
                // all remaining constants are mapped
                for ( String sourceConstant : unmappedSourceConstants ) {
                    String sourceNameConstant = getEnumConstant( sourceTypeElement, sourceConstant );
                    String targetConstant = transform( sourceNameConstant );
                    mappings.add( new MappingEntry( sourceConstant, targetConstant ) );
                }
            }
            return mappings;
        }

        private List<MappingEntry> stringToEnumMapping(Method method, Type targetType ) {

            List<MappingEntry> mappings = new ArrayList<>();
            List<String> unmappedSourceConstants = new ArrayList<>( targetType.getEnumConstants() );
            boolean sourceErrorOccurred = !reportErrorIfMappedTargetEnumConstantsDontExist( method, targetType );
            reportWarningIfAnyRemainingOrAnyUnMappedMissing( method );
            if ( sourceErrorOccurred ) {
                return mappings;
            }
            Set<String> mappedSources = new LinkedHashSet<>();

            // Start to fill the mappings with the defined value mappings
            for ( ValueMappingOptions valueMapping : valueMappings.regularValueMappings ) {
                mappedSources.add( valueMapping.getSource() );
                mappings.add( new MappingEntry( valueMapping.getSource(), valueMapping.getTarget() ) );
                unmappedSourceConstants.remove( valueMapping.getSource() );
            }

            // add mappings based on name
            if ( !valueMappings.hasMapAnyUnmapped ) {
                mappedSources.add( NULL );
                TypeElement targetTypeElement = targetType.getTypeElement();
                // all remaining constants are mapped
                for ( String sourceConstant : unmappedSourceConstants ) {
                    String sourceNameConstant = getEnumConstant( targetTypeElement, sourceConstant );
                    String stringConstant = transform( sourceNameConstant );
                    if ( !mappedSources.contains( stringConstant ) ) {
                        mappings.add( new MappingEntry( stringConstant, sourceConstant ) );
                    }
                }
            }
            return mappings;
        }

        private String getEnumConstant(TypeElement typeElement, String enumConstant) {
            return ctx.getEnumMappingStrategy().getEnumConstant( typeElement, enumConstant );
        }

        private SelectionParameters getSelectionParameters(Method method, TypeUtils typeUtils) {
            BeanMappingGem beanMapping = BeanMappingGem.instanceOn( method.getExecutable() );
            if ( beanMapping != null ) {
                List<TypeMirror> qualifiers = beanMapping.qualifiedBy().get();
                List<String> qualifyingNames = beanMapping.qualifiedByName().get();
                TypeMirror resultType = beanMapping.resultType().get();
                return new SelectionParameters( qualifiers, qualifyingNames, resultType, typeUtils );
            }
            return null;
        }

        private boolean reportErrorIfMappedSourceEnumConstantsDontExist(Method method, Type sourceType) {
            List<String> sourceEnumConstants = sourceType.getEnumConstants();

            boolean foundIncorrectMapping = false;

            for ( ValueMappingOptions mappedConstant : valueMappings.regularValueMappings ) {

                if ( !enumMapping.isInverse() && THROW_EXCEPTION.equals( mappedConstant.getSource() ) ) {
                    ctx.getMessager().printMessage(
                        method.getExecutable(),
                        mappedConstant.getMirror(),
                        mappedConstant.getSourceAnnotationValue(),
                        Message.VALUEMAPPING_THROW_EXCEPTION_SOURCE
                    );
                    foundIncorrectMapping = true;
                }
                else if ( !sourceEnumConstants.contains( mappedConstant.getSource() ) ) {

                    ctx.getMessager().printMessage(
                        method.getExecutable(),
                        mappedConstant.getMirror(),
                        mappedConstant.getSourceAnnotationValue(),
                        Message.VALUEMAPPING_NON_EXISTING_CONSTANT,
                        mappedConstant.getSource(),
                        sourceType
                    );
                    foundIncorrectMapping = true;
                }
            }
            return !foundIncorrectMapping;
        }

        private boolean reportErrorIfSourceEnumConstantsContainsAnyRemaining(Method method) {
            boolean foundIncorrectMapping = false;

            if ( valueMappings.hasMapAnyRemaining ) {
                ctx.getMessager().printMessage(
                    method.getExecutable(),
                    valueMappings.defaultTarget.getMirror(),
                    valueMappings.defaultTarget.getSourceAnnotationValue(),
                    Message.VALUEMAPPING_ANY_REMAINING_FOR_NON_ENUM,
                    method.getResultType()
                );
                foundIncorrectMapping = true;
            }
            return !foundIncorrectMapping;
        }

        private void reportWarningIfAnyRemainingOrAnyUnMappedMissing(Method method) {

            if ( !( valueMappings.hasMapAnyUnmapped || valueMappings.hasMapAnyRemaining ) ) {
                ctx.getMessager().printMessage(
                    method.getExecutable(),
                    Message.VALUEMAPPING_ANY_REMAINING_OR_UNMAPPED_MISSING
                );
            }
        }

        private boolean reportErrorIfMappedTargetEnumConstantsDontExist(Method method, Type targetType) {
            List<String> targetEnumConstants = targetType.getEnumConstants();

            boolean foundIncorrectMapping = false;

            for ( ValueMappingOptions mappedConstant : valueMappings.regularValueMappings ) {
                if ( !NULL.equals( mappedConstant.getTarget() )
                    && !THROW_EXCEPTION.equals( mappedConstant.getTarget() )
                    && !targetEnumConstants.contains( mappedConstant.getTarget() ) ) {
                    ctx.getMessager().printMessage(
                        method.getExecutable(),
                        mappedConstant.getMirror(),
                        mappedConstant.getTargetAnnotationValue(),
                        Message.VALUEMAPPING_NON_EXISTING_CONSTANT,
                        mappedConstant.getTarget(),
                        method.getReturnType()
                    );
                    foundIncorrectMapping = true;
                }
            }

            if ( valueMappings.defaultTarget != null
                && !THROW_EXCEPTION.equals( valueMappings.defaultTarget.getTarget() )
                && !NULL.equals( valueMappings.defaultTarget.getTarget() )
                && !targetEnumConstants.contains( valueMappings.defaultTarget.getTarget() ) ) {
                ctx.getMessager().printMessage(
                    method.getExecutable(),
                    valueMappings.defaultTarget.getMirror(),
                    valueMappings.defaultTarget.getTargetAnnotationValue(),
                    Message.VALUEMAPPING_NON_EXISTING_CONSTANT,
                    valueMappings.defaultTarget.getTarget(),
                    method.getReturnType()
                );
                foundIncorrectMapping = true;
            }

            if ( valueMappings.nullTarget != null && NULL.equals( valueMappings.nullTarget.getTarget() )
                && !targetEnumConstants.contains( valueMappings.nullTarget.getTarget() ) ) {
                ctx.getMessager().printMessage( method.getExecutable(),
                    valueMappings.nullTarget.getMirror(),
                    valueMappings.nullTarget.getTargetAnnotationValue(),
                    Message.VALUEMAPPING_NON_EXISTING_CONSTANT,
                    valueMappings.nullTarget.getTarget(),
                    method.getReturnType()
                );
                foundIncorrectMapping = true;
            }
            else if ( valueMappings.nullTarget == null && valueMappings.nullValueTarget != null
                && !valueMappings.nullValueTarget.equals( THROW_EXCEPTION )
                && !targetEnumConstants.contains( valueMappings.nullValueTarget ) ) {
                // if there is no nullTarget, but nullValueTarget has a value it means that there was an SPI
                // which returned an enum for the target enum
                ctx.getMessager().printMessage(
                    method.getExecutable(),
                    Message.VALUEMAPPING_NON_EXISTING_CONSTANT_FROM_SPI,
                    valueMappings.nullValueTarget,
                    method.getReturnType(),
                    ctx.getEnumMappingStrategy()
                );
            }

            return !foundIncorrectMapping;
        }

        private Type determineUnexpectedValueMappingException() {
            TypeMirror unexpectedValueMappingException = enumMapping.getUnexpectedValueMappingException();
            if ( unexpectedValueMappingException != null ) {
                return ctx.getTypeFactory().getType( unexpectedValueMappingException );
            }

            return ctx.getTypeFactory()
                .getType( ctx.getEnumMappingStrategy().getUnexpectedValueMappingExceptionType() );
        }
    }

    private static class EnumTransformationStrategyInvoker {

        private static final EnumTransformationStrategyInvoker DEFAULT = new EnumTransformationStrategyInvoker(
            null,
            null
        );

        private final EnumTransformationStrategy transformationStrategy;
        private final String configuration;

        private EnumTransformationStrategyInvoker(
            EnumTransformationStrategy transformationStrategy, String configuration) {
            this.transformationStrategy = transformationStrategy;
            this.configuration = configuration;
        }

        private String transform(String source) {
            if ( transformationStrategy == null ) {
                return source;
            }

            return transformationStrategy.transform( source, configuration );
        }
    }

    private static class ValueMappings {

        List<ValueMappingOptions> regularValueMappings = new ArrayList<>();
        ValueMappingOptions defaultTarget = null;
        String defaultTargetValue = null;
        ValueMappingOptions nullTarget = null;
        String nullValueTarget = null;
        boolean hasMapAnyUnmapped = false;
        boolean hasMapAnyRemaining = false;
        boolean hasDefaultValue = false;
        boolean hasAtLeastOneExceptionValue = false;

        ValueMappings(List<ValueMappingOptions> valueMappings) {

            for ( ValueMappingOptions valueMapping : valueMappings ) {
                if ( ANY_REMAINING.equals( valueMapping.getSource() ) ) {
                    defaultTarget = valueMapping;
                    defaultTargetValue = defaultTarget.getTarget();
                    hasDefaultValue = true;
                    hasMapAnyRemaining = true;
                }
                else if ( ANY_UNMAPPED.equals( valueMapping.getSource() ) ) {
                    defaultTarget = valueMapping;
                    defaultTargetValue = defaultTarget.getTarget();
                    hasDefaultValue = true;
                    hasMapAnyUnmapped = true;
                }
                else if ( NULL.equals( valueMapping.getSource() ) ) {
                    nullTarget = valueMapping;
                    nullValueTarget = getValue( nullTarget );
                }
                else {
                    regularValueMappings.add( valueMapping );
                }

                if ( THROW_EXCEPTION.equals( valueMapping.getTarget() ) ) {
                    hasAtLeastOneExceptionValue = true;
                }
            }
        }

        String getValue(ValueMappingOptions valueMapping) {
            return NULL.equals( valueMapping.getTarget() ) ? null : valueMapping.getTarget();
        }
    }

    private ValueMappingMethod(Method method,
                               List<Annotation> annotations,
                               List<MappingEntry> enumMappings,
                               String nullTarget,
                               String defaultTarget,
                               Type unexpectedValueMappingException,
                               List<LifecycleCallbackMethodReference> beforeMappingMethods,
                               List<LifecycleCallbackMethodReference> afterMappingMethods) {
        super( method, beforeMappingMethods, afterMappingMethods );
        this.valueMappings = enumMappings;
        this.nullTarget = new MappingEntry( null, nullTarget );
        this.defaultTarget = new MappingEntry( null, defaultTarget != null ? defaultTarget : THROW_EXCEPTION);
        this.unexpectedValueMappingException = unexpectedValueMappingException;
        this.overridden = method.overridesMethod();
        this.annotations = annotations;
    }

    @Override
    public Set<Type> getImportTypes() {
        Set<Type> importTypes = super.getImportTypes();

        if ( unexpectedValueMappingException != null && !unexpectedValueMappingException.isJavaLangType() ) {
            if ( defaultTarget.isTargetAsException() || nullTarget.isTargetAsException() ||
                hasMappingWithTargetAsException() ) {
                importTypes.addAll( unexpectedValueMappingException.getImportTypes() );
            }
        }
        for ( Annotation annotation : annotations ) {
            importTypes.addAll( annotation.getImportTypes() );
        }
        return importTypes;
    }

    protected boolean hasMappingWithTargetAsException() {
        return getValueMappings()
            .stream()
            .anyMatch( MappingEntry::isTargetAsException );
    }

    public List<MappingEntry> getValueMappings() {
        return valueMappings;
    }

    public MappingEntry getDefaultTarget() {
        return defaultTarget;
    }

    public MappingEntry getNullTarget() {
        return nullTarget;
    }

    public Type getUnexpectedValueMappingException() {
        return unexpectedValueMappingException;
    }

    public Parameter getSourceParameter() {
        return first( getSourceParameters() );
    }

    public boolean isOverridden() {
        return overridden;
    }

    public List<Annotation> getAnnotations() {
        return annotations;
    }

    public static class MappingEntry {
        private final String source;
        private final String target;
        private boolean targetAsException = false;

        MappingEntry(String source, String target) {
            this.source = source;
            if ( !NULL.equals( target ) ) {
                this.target = target;
                if ( THROW_EXCEPTION.equals( target ) ) {
                    this.targetAsException = true;
                }
            }
            else {
                this.target = null;
            }
        }

        public boolean isTargetAsException() {
            return targetAsException;
        }

        public String getSource() {
            return source;
        }

        public String getTarget() {
            return target;
        }
    }
}
