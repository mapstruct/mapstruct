/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source;

import static org.mapstruct.ap.internal.model.source.PropertyEntry.forSourceReference;
import static org.mapstruct.ap.internal.util.Collections.first;
import static org.mapstruct.ap.internal.util.Collections.last;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.lang.model.type.DeclaredType;

import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.util.Extractor;
import org.mapstruct.ap.internal.util.FormattingMessager;
import org.mapstruct.ap.internal.util.Message;
import org.mapstruct.ap.internal.util.Strings;
import org.mapstruct.ap.internal.util.accessor.Accessor;

/**
 * This class describes the source side of a property mapping.
 * <p>
 * It contains the source parameter, and all individual (nested) property entries. So consider the following
 * mapping method:
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
 * After building, {@link #isValid()} will return true when when no problems are detected during building.
 *
 * @author Sjaak Derksen
 */
public class SourceReference {

    private final Parameter parameter;
    private final List<PropertyEntry> propertyEntries;
    private final boolean isValid;

    /**
     * Builds a {@link SourceReference} from an {@code @Mappping}.
     */
    public static class BuilderFromMapping {

        private Mapping mapping;
        private Method method;
        private FormattingMessager messager;
        private TypeFactory typeFactory;

        public BuilderFromMapping messager(FormattingMessager messager) {
            this.messager = messager;
            return this;
        }

        public BuilderFromMapping mapping(Mapping mapping) {
            this.mapping = mapping;
            return this;
        }

        public BuilderFromMapping method(Method method) {
            this.method = method;
            return this;
        }

        public BuilderFromMapping typeFactory(TypeFactory typeFactory) {
            this.typeFactory = typeFactory;
            return this;
        }

        public SourceReference build() {

            String sourceName = mapping.getSourceName();
            if ( sourceName == null ) {
                return null;
            }

            String sourceNameTrimmed = sourceName.trim();
            if ( !sourceName.equals( sourceNameTrimmed ) ) {
                messager.printMessage(
                    method.getExecutable(),
                    mapping.getMirror(),
                    mapping.getSourceAnnotationValue(),
                    Message.PROPERTYMAPPING_WHITESPACE_TRIMMED,
                    sourceName,
                    sourceNameTrimmed
                );
            }

            String[] segments = sourceNameTrimmed.split( "\\." );

            // start with an invalid source reference
            SourceReference result = new SourceReference( null, new ArrayList<>(  ), false );
            if ( method.getSourceParameters().size() > 1 ) {
                Parameter parameter = fetchMatchingParameterFromFirstSegment( segments );
                if ( parameter != null ) {
                    result = buildFromMultipleSourceParameters( segments, parameter );
                }
            }
            else {
                Parameter parameter = method.getSourceParameters().get( 0 );
                result = buildFromSingleSourceParameters( segments, parameter );
            }
            return result;

        }

        /**
         * When there is only one source parameters, the first segment name of the property may, or may not match
         * the parameter name to avoid ambiguity
         *
         * consider: {@code Target map( Source1 source1 )}
         * entries in an @Mapping#source can be "source1.propx" or just "propx" to be valid
         *
         * @param segments the segments of @Mapping#source
         * @param parameter the one and only  parameter
         * @return the source reference
         */
        private SourceReference buildFromSingleSourceParameters(String[] segments, Parameter parameter) {

            boolean foundEntryMatch;

            String[] propertyNames = segments;
            List<PropertyEntry> entries = matchWithSourceAccessorTypes( parameter.getType(), propertyNames );
            foundEntryMatch = ( entries.size() == propertyNames.length );

            if ( !foundEntryMatch ) {
                //Lets see if the expression contains the parameterName, so parameterName.propName1.propName2
                if ( getSourceParameterFromMethodOrTemplate( segments[0] ) != null ) {
                    propertyNames = Arrays.copyOfRange( segments, 1, segments.length );
                    entries = matchWithSourceAccessorTypes( parameter.getType(), propertyNames );
                    foundEntryMatch = ( entries.size() == propertyNames.length );
                }
                else {
                    // segment[0] cannot be attributed to the parameter name.
                    parameter = null;
                }
            }

            if ( !foundEntryMatch ) {
                reportErrorOnNoMatch( parameter, propertyNames, entries );
            }

            return new SourceReference( parameter, entries, foundEntryMatch );
        }

        /**
         * When there are more than one source parameters, the first segment name of the property
         * needs to match the parameter name to avoid ambiguity
         *
         * @param segments the segments of @Mapping#source
         * @param parameter the relevant source parameter
         * @return the source reference
         */
        private SourceReference buildFromMultipleSourceParameters(String[] segments, Parameter parameter) {

            boolean foundEntryMatch;

            String[] propertyNames = new String[0];
            List<PropertyEntry> entries = new ArrayList<>();

            if ( segments.length > 1 && parameter != null ) {
                propertyNames = Arrays.copyOfRange( segments, 1, segments.length );
                entries = matchWithSourceAccessorTypes( parameter.getType(), propertyNames );
                foundEntryMatch = ( entries.size() == propertyNames.length );
            }
            else {
                // its only a parameter, no property
                foundEntryMatch = true;
            }

            if ( !foundEntryMatch ) {
                reportErrorOnNoMatch( parameter, propertyNames, entries );
            }

            return new SourceReference( parameter, entries, foundEntryMatch );
        }

        /**
         * When there are more than one source parameters, the first segment name of the propery
         * needs to match the parameter name to avoid ambiguity
         *
         * consider: {@code Target map( Source1 source1, Source2 source2 )}
         * entries in an @Mapping#source need to be "source1.propx" or "source2.propy.propz" to be valid
         *
         * @param segments the segments of @Mapping#source
         * @return parameter that matches with first segment of @Mapping#source
        */
        private Parameter fetchMatchingParameterFromFirstSegment(String[] segments ) {
            Parameter parameter = null;
            if ( segments.length > 0 ) {
                String parameterName = segments[0];
                parameter = getSourceParameterFromMethodOrTemplate( parameterName );
                if ( parameter == null ) {
                    reportMappingError(
                        Message.PROPERTYMAPPING_INVALID_PARAMETER_NAME,
                        parameterName,
                        Strings.join(
                            method.getSourceParameters(),
                            ", ",
                            new Extractor<Parameter, String>() {
                                @Override
                                public String apply(Parameter parameter) {
                                    return parameter.getName();
                                }
                            }
                        )
                    );
                }
            }
            return parameter;
        }

        private Parameter getSourceParameterFromMethodOrTemplate(String parameterName ) {

            Parameter result = null;
            if ( mapping.getInheritContext() != null && mapping.getInheritContext().isForwarded() ) {
                Method templateMethod = mapping.getInheritContext().getInheritedFromMethod();
                Parameter parameter = Parameter.getSourceParameter( templateMethod.getParameters(), parameterName );
                if ( parameter != null ) {
                    result = method.getSourceParameters()
                                   .stream()
                                   .filter( p -> p.getType().isAssignableTo( parameter.getType() ) )
                                   .collect( Collectors.reducing( (a, b) -> null ) )
                                   .orElse( null );
                }
            }
            else {
                result = Parameter.getSourceParameter( method.getParameters(), parameterName );
            }
            return result;
        }

        private void reportErrorOnNoMatch( Parameter parameter, String[] propertyNames, List<PropertyEntry> entries) {
            if ( parameter != null ) {
                reportMappingError( Message.PROPERTYMAPPING_NO_PROPERTY_IN_PARAMETER, parameter.getName(),
                    Strings.join( Arrays.asList( propertyNames ), "." )
                );
            }
            else {
                int notFoundPropertyIndex = 0;
                Type sourceType = method.getParameters().get( 0 ).getType();
                if ( !entries.isEmpty() ) {
                    notFoundPropertyIndex = entries.size();
                    sourceType = last( entries ).getType();
                }
                String mostSimilarWord = Strings.getMostSimilarWord(
                    propertyNames[notFoundPropertyIndex],
                    sourceType.getPropertyReadAccessors().keySet()
                );
                List<String> elements = new ArrayList<>(
                    Arrays.asList( propertyNames ).subList( 0, notFoundPropertyIndex )
                );
                elements.add( mostSimilarWord );
                reportMappingError(
                    Message.PROPERTYMAPPING_INVALID_PROPERTY_NAME, mapping.getSourceName(),
                    Strings.join( elements, "." )
                );
            }
        }

        private List<PropertyEntry> matchWithSourceAccessorTypes(Type type, String[] entryNames) {
            List<PropertyEntry> sourceEntries = new ArrayList<>();
            Type newType = type;
            for ( String entryName : entryNames ) {
                boolean matchFound = false;
                Map<String, Accessor> sourceReadAccessors = newType.getPropertyReadAccessors();
                Map<String, Accessor> sourcePresenceCheckers = newType.getPropertyPresenceCheckers();

                for ( Map.Entry<String, Accessor> getter : sourceReadAccessors.entrySet() ) {
                    if ( getter.getKey().equals( entryName ) ) {
                        newType = typeFactory.getReturnType(
                            (DeclaredType) newType.getTypeMirror(),
                            getter.getValue()
                        );
                        sourceEntries.add( forSourceReference( entryName, getter.getValue(),
                            sourcePresenceCheckers.get( entryName ), newType
                        ) );
                        matchFound = true;
                        break;
                    }
                }
                if ( !matchFound ) {
                    break;
                }
            }
            return sourceEntries;
        }

        private void reportMappingError(Message msg, Object... objects) {
            messager.printMessage( method.getExecutable(), mapping.getMirror(), mapping.getSourceAnnotationValue(),
                msg, objects
            );
        }
    }

    /**
     * Builds a {@link SourceReference} from a property.
     */
    public static class BuilderFromProperty {

        private String name;
        private Accessor readAccessor;
        private Accessor presenceChecker;
        private Type type;
        private Parameter sourceParameter;

        public BuilderFromProperty name(String name) {
            this.name = name;
            return this;
        }

        public BuilderFromProperty readAccessor(Accessor readAccessor) {
            this.readAccessor = readAccessor;
            return this;
        }

        public BuilderFromProperty presenceChecker(Accessor presenceChecker) {
            this.presenceChecker = presenceChecker;
            return this;
        }

        public BuilderFromProperty type(Type type) {
            this.type = type;
            return this;
        }

        public BuilderFromProperty sourceParameter(Parameter sourceParameter) {
            this.sourceParameter = sourceParameter;
            return this;
        }

        public SourceReference build() {
            List<PropertyEntry> sourcePropertyEntries = new ArrayList<>();
            if ( readAccessor != null ) {
                sourcePropertyEntries.add( forSourceReference( name, readAccessor, presenceChecker, type ) );
            }
            return new SourceReference( sourceParameter, sourcePropertyEntries, true );
        }
    }

    /**
     * Builds a {@link SourceReference} from a property.
     */
    public static class BuilderFromSourceReference {

        private Parameter sourceParameter;
        private SourceReference sourceReference;

        public BuilderFromSourceReference sourceReference(SourceReference sourceReference) {
            this.sourceReference = sourceReference;
            return this;
        }

        public BuilderFromSourceReference sourceParameter(Parameter sourceParameter) {
            this.sourceParameter = sourceParameter;
            return this;
        }

        public SourceReference build() {
            return new SourceReference( sourceParameter, sourceReference.propertyEntries, true );
        }
    }

    private SourceReference(Parameter sourceParameter, List<PropertyEntry> sourcePropertyEntries, boolean isValid) {
        this.parameter = sourceParameter;
        this.propertyEntries = sourcePropertyEntries;
        this.isValid = isValid;
    }

    public Parameter getParameter() {
        return parameter;
    }

    public List<PropertyEntry> getPropertyEntries() {
        return propertyEntries;
    }

    public boolean isValid() {
        return isValid;
    }

    public List<String> getElementNames() {
        List<String> sourceName = new ArrayList<>();
        sourceName.add( parameter.getName() );
        for ( PropertyEntry propertyEntry : propertyEntries ) {
            sourceName.add( propertyEntry.getName() );
        }
        return sourceName;
    }

    /**
     * Creates a copy of this reference, which is adapted to the given method
     *
     * @param method the method to create the copy for
     * @return the copy
     */
    public SourceReference copyForInheritanceTo(SourceMethod method) {
        List<Parameter> replacementParamCandidates = new ArrayList<>();
        for ( Parameter sourceParam : method.getSourceParameters() ) {
            if ( parameter != null && sourceParam.getType().isAssignableTo( parameter.getType() ) ) {
                replacementParamCandidates.add( sourceParam );
            }
        }

        Parameter replacement = parameter;
        if ( replacementParamCandidates.size() == 1 ) {
            replacement = first( replacementParamCandidates );
        }

        return new SourceReference( replacement, propertyEntries, isValid );
    }

    public SourceReference pop() {
        if ( propertyEntries.size() > 1 ) {
            List<PropertyEntry> newPropertyEntries =
                new ArrayList<>( propertyEntries.subList( 1, propertyEntries.size() ) );
            return new SourceReference( parameter, newPropertyEntries, isValid );
        }
        else {
            return null;
        }
    }

    @Override
    public String toString() {

        if ( propertyEntries.isEmpty() ) {
            return String.format( "parameter \"%s %s\"", getParameter().getType(), getParameter().getName() );
        }
        else if ( propertyEntries.size() == 1 ) {
            PropertyEntry propertyEntry = propertyEntries.get( 0 );
            return String.format( "property \"%s %s\"", propertyEntry.getType(), propertyEntry.getName() );
        }
        else {
            PropertyEntry lastPropertyEntry = propertyEntries.get( propertyEntries.size() - 1 );
            return String.format(
                "property \"%s %s\"",
                lastPropertyEntry.getType(),
                Strings.join( getElementNames(), "." )
            );
        }
    }

}
