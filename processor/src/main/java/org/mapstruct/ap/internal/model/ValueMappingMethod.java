/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;

import org.mapstruct.ap.internal.gem.BeanMappingGem;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.model.source.SelectionParameters;
import org.mapstruct.ap.internal.model.source.ValueMappingOptions;
import org.mapstruct.ap.internal.util.Message;
import org.mapstruct.ap.internal.util.Strings;

import static org.mapstruct.ap.internal.gem.MappingConstantsGem.ANY_REMAINING;
import static org.mapstruct.ap.internal.gem.MappingConstantsGem.ANY_UNMAPPED;
import static org.mapstruct.ap.internal.gem.MappingConstantsGem.NULL;
import static org.mapstruct.ap.internal.util.Collections.first;

/**
 * A {@link ValueMappingMethod} which maps one value type to another, optionally configured by one or more
 * {@link ValueMappingOptions}s. For now, only enum-to-enum mapping is supported.
 *
 * @author Sjaak Derksen
 */
public class ValueMappingMethod extends MappingMethod {

    private final List<MappingEntry> valueMappings;
    private final String defaultTarget;
    private final String nullTarget;
    private final boolean throwIllegalArgumentException;
    private final boolean overridden;

    public static class Builder {

        private Method method;
        private MappingBuilderContext ctx;
        private ValueMappings valueMappings;

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

        public ValueMappingMethod build( ) {

            // initialize all relevant parameters
            List<MappingEntry> mappingEntries = new ArrayList<>();

            Type sourceType = first( method.getSourceParameters() ).getType();
            Type targetType = method.getResultType();

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

            // finally return a mapping
            return new ValueMappingMethod( method,
                mappingEntries,
                valueMappings.nullValueTarget,
                valueMappings.defaultTargetValue,
                !valueMappings.hasDefaultValue,
                beforeMappingMethods,
                afterMappingMethods
            );
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
                String target =
                    NULL.equals( valueMapping.getTarget() ) ? null : valueMapping.getTarget();
                mappings.add( new MappingEntry( valueMapping.getSource(), target ) );
                unmappedSourceConstants.remove( valueMapping.getSource() );
            }

            if ( valueMappings.nullTarget == null ) {
                // If no null value target is defined, use from SPI
                valueMappings.nullValueTarget = targetType.getDefaultEnumValue();
            }

            // Ask SPI for any enum values that should always be ignored
            for ( String unmappedSourceConstant : new ArrayList<>( unmappedSourceConstants ) ) {
                if ( sourceType.isMapToNull( unmappedSourceConstant ) ) {
                    mappings.add( new MappingEntry( unmappedSourceConstant, null ) );
                    unmappedSourceConstants.remove( unmappedSourceConstant );
                }
            }

            // add mappings based on name
            if ( !valueMappings.hasMapAnyUnmapped ) {

                // get all target constants
                List<String> targetConstants = method.getReturnType().getEnumConstants();
                List<String> mappedTargetConstants = targetConstants.stream()
                    .map( e -> targetType.getMappedEnumValue( e ) )
                    .collect( Collectors.toList() );
                for ( String sourceConstant : new ArrayList<>( unmappedSourceConstants ) ) {
                    String mappedSourceConstant = sourceType.getMappedEnumValue( sourceConstant );
                    for ( int i = 0; i < mappedTargetConstants.size(); i++ ) {

                        String currentTargetConstant = mappedTargetConstants.get( i );
                        if ( currentTargetConstant != null &&
                            currentTargetConstant.equals( mappedSourceConstant ) ) {
                            if ( sourceConstant.equals( mappedSourceConstant ) ) {
                                // The standard enum value
                                mappings.add( new MappingEntry( sourceConstant, targetConstants.get( i ) ) );
                            }
                            else {
                                // The mapped enum value
                                mappings.add( new MappingEntry(
                                    sourceConstant,
                                    currentTargetConstant
                                ) );
                            }
                            unmappedSourceConstants.remove( sourceConstant );
                        }

                    }
                }


                if ( valueMappings.defaultTarget == null && !unmappedSourceConstants.isEmpty() ) {
                    String sourceErrorMessage = "source";
                    String targetErrorMessage = "target";
                    if ( method instanceof ForgedMethod && ( (ForgedMethod) method ).getHistory() != null ) {
                        ForgedMethodHistory history = ( (ForgedMethod) method ).getHistory();
                        sourceErrorMessage = history.createSourcePropertyErrorMessage();
                        targetErrorMessage =
                            "\"" + history.getTargetType().toString() + " " + history.createTargetPropertyName() + "\"";
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

             // Start to fill the mappings with the defined valuemappings
             for ( ValueMappingOptions valueMapping : valueMappings.regularValueMappings ) {
                 String target =
                     NULL.equals( valueMapping.getTarget() ) ? null : valueMapping.getTarget();
                 mappings.add( new MappingEntry( valueMapping.getSource(), target ) );
                 unmappedSourceConstants.remove( valueMapping.getSource() );
             }

             // Ask SPI for any enum values that should always be ignored
             for ( String unmappedSourceConstant : new ArrayList<>( unmappedSourceConstants ) ) {
                 if ( sourceType.isMapToNull( unmappedSourceConstant ) ) {
                     mappings.add( new MappingEntry( unmappedSourceConstant, null ) );
                     unmappedSourceConstants.remove( unmappedSourceConstant );
                 }
             }

             // add mappings based on name
             if ( !valueMappings.hasMapAnyUnmapped ) {

                 // all remaining constants are mapped
                 for ( String sourceConstant : unmappedSourceConstants ) {
                     mappings.add( new MappingEntry(
                         sourceConstant,
                         sourceType.getMappedEnumValue( sourceConstant )
                     ) );
                 }
             }
             return mappings;
        }

        private List<MappingEntry> stringToEnumMapping(Method method, Type targetType ) {

            List<MappingEntry> mappings = new ArrayList<>();
            List<String> unmappedTargetConstants = new ArrayList<>( targetType.getEnumConstants() );
            boolean targetErrorOccurred = !reportErrorIfMappedTargetEnumConstantsDontExist( method, targetType );
            boolean mandatoryMissing = !reportErrorIfAnyRemainingOrAnyUnMappedMissing( method );
            if ( targetErrorOccurred || mandatoryMissing ) {
                return mappings;
            }

            if ( valueMappings.nullTarget == null ) {
                // If no null value target is defined, use from SPI
                valueMappings.nullValueTarget = targetType.getDefaultEnumValue();
            }

            // Start to fill the mappings with the defined valuemappings
            for ( ValueMappingOptions valueMapping : valueMappings.regularValueMappings ) {
                String target =
                    NULL.equals( valueMapping.getTarget() ) ? null : valueMapping.getTarget();
                mappings.add( new MappingEntry( valueMapping.getSource(), target ) );
                unmappedTargetConstants.remove( valueMapping.getSource() );
            }

            // add mappings based on name
            if ( !valueMappings.hasMapAnyUnmapped ) {
                List<String> mappedTargetConstants = unmappedTargetConstants.stream()
                    .map( e -> targetType.getMappedEnumValue( e ) )
                    .collect( Collectors.toList() );
                for ( int i = 0; i < mappedTargetConstants.size(); i++ ) {
                    mappings.add( new MappingEntry( mappedTargetConstants.get( i ),
                        unmappedTargetConstants.get( i ) ) );
                }
                unmappedTargetConstants.clear(); // All empty
            }
            return mappings;
        }

        private SelectionParameters getSelectionParameters(Method method, Types typeUtils) {
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

                if ( !sourceEnumConstants.contains( mappedConstant.getSource() ) ) {
                    ctx.getMessager().printMessage( method.getExecutable(),
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

        private boolean reportErrorIfAnyRemainingOrAnyUnMappedMissing(Method method) {
            boolean foundIncorrectMapping = false;

            if ( !( valueMappings.hasMapAnyUnmapped || valueMappings.hasMapAnyRemaining ) ) {
                ctx.getMessager().printMessage(
                    method.getExecutable(),
                    Message.VALUEMAPPING_ANY_REMAINING_OR_UNMAPPED_MISSING
                );
                foundIncorrectMapping = true;
            }
            return !foundIncorrectMapping;
        }

        private boolean reportErrorIfMappedTargetEnumConstantsDontExist(Method method, Type targetType) {
            List<String> targetEnumConstants = targetType.getEnumConstants();

            boolean foundIncorrectMapping = false;

            for ( ValueMappingOptions mappedConstant : valueMappings.regularValueMappings ) {
                if ( !NULL.equals( mappedConstant.getTarget() )
                    && !targetEnumConstants.contains( mappedConstant.getTarget() ) ) {
                    ctx.getMessager().printMessage( method.getExecutable(),
                        mappedConstant.getMirror(),
                        mappedConstant.getTargetAnnotationValue(),
                        Message.VALUEMAPPING_NON_EXISTING_CONSTANT,
                        mappedConstant.getTarget(),
                        method.getReturnType()
                    );
                    foundIncorrectMapping = true;
                }
            }

            if ( valueMappings.defaultTarget != null && !NULL.equals( valueMappings.defaultTarget.getTarget() )
                && !targetEnumConstants.contains( valueMappings.defaultTarget.getTarget() ) ) {
                ctx.getMessager().printMessage( method.getExecutable(),
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

            return !foundIncorrectMapping;
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

        ValueMappings(List<ValueMappingOptions> valueMappings) {

            for ( ValueMappingOptions valueMapping : valueMappings ) {
                if ( ANY_REMAINING.equals( valueMapping.getSource() ) ) {
                    defaultTarget = valueMapping;
                    defaultTargetValue = getValue( defaultTarget );
                    hasDefaultValue = true;
                    hasMapAnyRemaining = true;
                }
                else if ( ANY_UNMAPPED.equals( valueMapping.getSource() ) ) {
                    defaultTarget = valueMapping;
                    defaultTargetValue = getValue( defaultTarget );
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
            }
        }

        String getValue(ValueMappingOptions valueMapping) {
            return NULL.equals( valueMapping.getTarget() ) ? null : valueMapping.getTarget();
        }
    }

    private ValueMappingMethod(Method method, List<MappingEntry> enumMappings, String nullTarget, String defaultTarget,
        boolean throwIllegalArgumentException, List<LifecycleCallbackMethodReference> beforeMappingMethods,
        List<LifecycleCallbackMethodReference> afterMappingMethods) {
        super( method, beforeMappingMethods, afterMappingMethods );
        this.valueMappings = enumMappings;
        this.nullTarget = nullTarget;
        this.defaultTarget = defaultTarget;
        this.throwIllegalArgumentException = throwIllegalArgumentException;
        this.overridden = method.overridesMethod();
    }

    public List<MappingEntry> getValueMappings() {
        return valueMappings;
    }

    public String getDefaultTarget() {
        return defaultTarget;
    }

    public String getNullTarget() {
        return nullTarget;
    }

    public boolean isThrowIllegalArgumentException() {
        return throwIllegalArgumentException;
    }

    public Parameter getSourceParameter() {
        return first( getSourceParameters() );
    }

    public boolean isOverridden() {
        return overridden;
    }

    public static class MappingEntry {
        private final String source;
        private final String target;

        MappingEntry( String source, String target ) {
            this.source = source;
            this.target = target;
        }

        public String getSource() {
            return source;
        }

        public String getTarget() {
            return target;
        }
    }
}
