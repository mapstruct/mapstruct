/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.beanmapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;

import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.model.source.MappingOptions;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.util.FormattingMessager;
import org.mapstruct.ap.internal.util.Message;
import org.mapstruct.ap.internal.util.Strings;

import static org.mapstruct.ap.internal.util.Collections.first;

/**
 * This class describes the target side of a property mapping.
 * <p>
 * It contains the target parameter, and all individual (nested) property entries. So consider the following mapping
 * method:
 *
 * <pre>
 * &#64;Mapping(source = "in.propA.propB" target = "propC")
 * TypeB mappingMethod(TypeA in);
 * </pre>
 *
 * Then:
 * <ul>
 * <li>{@code parameter} will describe {@code in}</li>
 * <li>{@code propertyEntries[0]} will describe {@code propA}</li>
 * <li>{@code propertyEntries[1]} will describe {@code propB}</li>
 * </ul>
 *
 * @author Sjaak Derksen
 */
public class TargetReference {

    private final List<String> pathProperties;
    private final Parameter parameter;
    private final List<String> propertyEntries;

    public TargetReference(Parameter parameter, List<String> propertyEntries) {
        this( parameter, propertyEntries, Collections.emptyList() );
    }

    public TargetReference(Parameter parameter, List<String> propertyEntries, List<String> pathProperties) {
        this.pathProperties = pathProperties;
        this.parameter = parameter;
        this.propertyEntries = propertyEntries;
    }

    public List<String> getPathProperties() {
        return pathProperties;
    }

    public List<String> getPropertyEntries() {
        return propertyEntries;
    }

    public List<String> getElementNames() {
        List<String> elementNames = new ArrayList<>();
        if ( parameter != null ) {
            // only relevant for source properties
            elementNames.add( parameter.getName() );
        }
        elementNames.addAll( propertyEntries );
        return elementNames;
    }

    /**
     * returns the property name on the shallowest nesting level
     */
    public String getShallowestPropertyName() {
        if ( propertyEntries.isEmpty() ) {
            return null;
        }
        return first( propertyEntries );
    }

    public boolean isNested() {
        return propertyEntries.size() > 1;
    }

    @Override
    public String toString() {

        String result = "";
        if ( propertyEntries.isEmpty() ) {
            if ( parameter != null ) {
                result = String.format( "parameter \"%s %s\"", parameter.getType(), parameter.getName() );
            }
        }
        else if ( propertyEntries.size() == 1 ) {
            String propertyEntry = propertyEntries.get( 0 );
            result = String.format( "property \"%s\"", propertyEntry );
        }
        else {
            result = String.format(
                "property \"%s\"",
                Strings.join( getElementNames(), "." )
            );
        }
        return result;
    }

    /**
     * Builds a {@link TargetReference} from an {@code @Mappping}.
     */
    public static class Builder {

        private Method method;
        private FormattingMessager messager;
        private TypeFactory typeFactory;
        private Set<String> targetProperties;
        private Type targetType;

        // mapping parameters
        private String targetName = null;
        private MappingOptions mapping;
        private AnnotationMirror annotationMirror = null;
        private AnnotationValue targetAnnotationValue = null;
        private AnnotationValue sourceAnnotationValue = null;

        public Builder messager(FormattingMessager messager) {
            this.messager = messager;
            return this;
        }

        public Builder mapping(MappingOptions mapping) {
            this.mapping = mapping;
            this.targetName = mapping.getTargetName();
            this.annotationMirror = mapping.getMirror();
            this.targetAnnotationValue = mapping.getTargetAnnotationValue();
            this.sourceAnnotationValue = mapping.getSourceAnnotationValue();
            return this;
        }

        public Builder typeFactory(TypeFactory typeFactory) {
            this.typeFactory = typeFactory;
            return this;
        }

        public Builder method(Method method) {
            this.method = method;
            return this;
        }

        public Builder targetProperties(Set<String> targetProperties) {
            this.targetProperties = targetProperties;
            return this;
        }

        public Builder targetType(Type targetType) {
            this.targetType = targetType;
            return this;
        }

        public TargetReference build() {

            Objects.requireNonNull( method );
            Objects.requireNonNull( typeFactory );
            Objects.requireNonNull( messager );
            Objects.requireNonNull( targetType );

            if ( targetName == null ) {
                return null;
            }

            String targetNameTrimmed = targetName.trim();
            if ( !targetName.equals( targetNameTrimmed ) ) {
                messager.printMessage(
                    method.getExecutable(),
                    annotationMirror,
                    targetAnnotationValue,
                    Message.PROPERTYMAPPING_WHITESPACE_TRIMMED,
                    targetName,
                    targetNameTrimmed
                );
            }
            String[] segments = targetNameTrimmed.split( "\\." );
            Parameter parameter = method.getMappingTargetParameter();

            // there can be 4 situations
            // 1. Return type
            // 2. An inverse target reference where the source parameter name is used in the original mapping
            // 3. @MappingTarget, with
            // 4. or without parameter name.
            String[] targetPropertyNames = segments;
            if ( segments.length > 1 ) {
                String firstTargetProperty = targetPropertyNames[0];
                // If the first target property is not within the defined target properties, then check if it matches
                // the source or target parameter
                if ( !targetProperties.contains( firstTargetProperty ) ) {
                    if ( matchesSourceOrTargetParameter( firstTargetProperty, parameter ) ) {
                        targetPropertyNames = Arrays.copyOfRange( segments, 1, segments.length );
                    }
                }
            }


            List<String> entries = new ArrayList<>( Arrays.asList( targetPropertyNames ) );

            return new TargetReference( parameter, entries );
        }

        /**
         * Validates that the {@code segment} is the same as the {@code targetParameter} or the {@code
         * inverseSourceParameter} names
         *
         * @param segment that needs to be checked
         * @param targetParameter the target parameter if it exists
         *
         * @return {@code true} if the segment matches the name of the {@code targetParameter} or the name of the
         * {@code inverseSourceParameter} when this is a inverse {@link TargetReference} is being built, {@code
         * false} otherwise
         */
        private boolean matchesSourceOrTargetParameter(String segment, Parameter targetParameter) {
            boolean matchesTargetParameter = targetParameter != null && targetParameter.getName().equals( segment );
            return matchesTargetParameter || matchesSourceOnInverseSourceParameter( segment );
        }

        /**
         * Needed when we are building from inverse mapping. It is needed, so we can remove the first level if it is
         * needed.
         * E.g. If we have a mapping like:
         * <code>
         * {@literal @}Mapping( target = "letterSignature", source = "dto.signature" )
         * </code>
         * When it is inversed it will look like:
         * <code>
         * {@literal @}Mapping( target = "dto.signature", source = "letterSignature" )
         * </code>
         * The {@code dto} needs to be considered as a possibility for a target name only if a Target Reference for
         * a inverse is created.
         *
         * @param segment that needs to be checked*
         *
         * @return on match when inverse and segment matches the one and only source parameter
         */
        private boolean matchesSourceOnInverseSourceParameter(String segment) {

            boolean result = false;
            MappingOptions.InheritContext inheritContext = mapping.getInheritContext();
            if ( inheritContext != null && inheritContext.isReversed() ) {

                Method templateMethod = inheritContext.getTemplateMethod();
                // there is only source parameter by definition when applying @InheritInverseConfiguration
                Parameter inverseSourceParameter = first( templateMethod.getSourceParameters() );
                result = inverseSourceParameter.getName().equals( segment );
            }
            return result;
        }
    }

    public TargetReference pop() {
        if ( getPropertyEntries().size() > 1 ) {
            List<String> newPathProperties = new ArrayList<>( this.pathProperties );
            newPathProperties.add( getPropertyEntries().get( 0 ) );
            return new TargetReference(
                null,
                getPropertyEntries().subList( 1, getPropertyEntries().size() ),
                newPathProperties
            );
        }
        else {
            return null;
        }
    }

}
