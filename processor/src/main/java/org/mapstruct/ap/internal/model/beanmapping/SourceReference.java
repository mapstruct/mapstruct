/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.beanmapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.type.DeclaredType;

import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.model.source.MappingOptions;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.util.FormattingMessager;
import org.mapstruct.ap.internal.util.Message;
import org.mapstruct.ap.internal.util.Strings;
import org.mapstruct.ap.internal.util.accessor.PresenceCheckAccessor;
import org.mapstruct.ap.internal.util.accessor.ReadAccessor;

import static org.mapstruct.ap.internal.model.beanmapping.PropertyEntry.forSourceReference;
import static org.mapstruct.ap.internal.util.Collections.last;

/**
 * This class describes the source side of a property mapping.
 * <p>
 * It contains the source parameter, and all individual (nested) property entries. So consider the following
 * mapping method:
 *
 * <pre>
 * &#64;Mapping(target = "propC", source = "in.propA.propB")
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
 * After building, {@link #isValid()} will return true when no problems are detected during building.
 *
 * @author Sjaak Derksen
 * @author Filip Hrisafov
 */
public class SourceReference extends AbstractReference {

    /**
     * Builds a {@link SourceReference} from an {@code @Mappping}.
     */
    public static class BuilderFromMapping {

        private Method method;
        private FormattingMessager messager = null;
        private TypeFactory typeFactory;

        private boolean isForwarded = false;
        private Method templateMethod = null;
        private String sourceName;
        private AnnotationMirror annotationMirror;
        private AnnotationValue sourceAnnotationValue;

        public BuilderFromMapping messager(FormattingMessager messager) {
            this.messager = messager;
            return this;
        }

        public BuilderFromMapping mapping(MappingOptions mapping) {
            this.sourceName = mapping.getSourceName();
            this.annotationMirror = mapping.getMirror();
            this.sourceAnnotationValue = mapping.getSourceAnnotationValue();
            if ( mapping.getInheritContext() != null ) {
                isForwarded = mapping.getInheritContext().isForwarded();
                templateMethod = mapping.getInheritContext().getTemplateMethod();
            }
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

        public BuilderFromMapping sourceName(String sourceName) {
            this.sourceName = sourceName;
            return this;
        }

        public SourceReference build() {

            if ( sourceName == null ) {
                return null;
            }

            Objects.requireNonNull( messager );

            String sourceNameTrimmed = sourceName.trim();
            if ( !sourceName.equals( sourceNameTrimmed ) ) {
                messager.printMessage(
                    method.getExecutable(),
                    annotationMirror,
                    sourceAnnotationValue,
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
         * entries in a @Mapping#source can be "source1.propx" or just "propx" to be valid
         *
         * @param segments the segments of @Mapping#source
         * @param parameter the one and only  parameter
         * @return the source reference
         */
        private SourceReference buildFromSingleSourceParameters(String[] segments, Parameter parameter) {
            boolean foundEntryMatch;

            boolean allowedMapToBean = false;
            if ( segments.length > 0 ) {
                if ( parameter.getType().isMapType() ) {
                    // When the parameter type is a map and the parameter name matches the first segment
                    // then the first segment should not be treated as a property of the map
                    allowedMapToBean = !segments[0].equals( parameter.getName() );
                }
            }
            String[] propertyNames = segments;
            List<PropertyEntry> entries = matchWithSourceAccessorTypes(
                parameter.getType(),
                propertyNames,
                allowedMapToBean
            );
            foundEntryMatch = ( entries.size() == propertyNames.length );

            if ( !foundEntryMatch ) {
                //Lets see if the expression contains the parameterName, so parameterName.propName1.propName2
                if ( getSourceParameterFromMethodOrTemplate( segments[0] ) != null ) {
                    propertyNames = Arrays.copyOfRange( segments, 1, segments.length );
                    entries = matchWithSourceAccessorTypes( parameter.getType(), propertyNames, true );
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
                entries = matchWithSourceAccessorTypes( parameter.getType(), propertyNames, true );
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
         * When there are more than one source parameters, the first segment name of the property
         * needs to match the parameter name to avoid ambiguity
         *
         * consider: {@code Target map( Source1 source1, Source2 source2 )}
         * entries in a @Mapping#source need to be "source1.propx" or "source2.propy.propz" to be valid
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
                        Strings.join( method.getSourceParameters(), ", ", Parameter::getName )
                    );
                }
            }
            return parameter;
        }

        private Parameter getSourceParameterFromMethodOrTemplate(String parameterName ) {

            Parameter result = null;
            if ( isForwarded ) {
                Parameter parameter = Parameter.getSourceParameter( templateMethod.getParameters(), parameterName );
                if ( parameter != null ) {

                    // When forward inheriting we should find the matching source parameter by type
                    // If there are multiple parameters of the same type
                    // then we fallback to match the parameter name to the current method source parameters
                    for ( Parameter sourceParameter : method.getSourceParameters() ) {
                        if ( sourceParameter.getType().isAssignableTo( parameter.getType() ) ) {
                            if ( result == null ) {
                                result = sourceParameter;
                            }
                            else {
                                // When we reach here it means that we found a second source parameter
                                // that has the same type, then fallback to the matching source parameter
                                // in the current method
                                result = Parameter.getSourceParameter( method.getParameters(), parameterName );
                                break;
                            }
                        }
                    }
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

                Set<String> readProperties = sourceType.getPropertyReadAccessors().keySet();

                if ( !readProperties.isEmpty() ) {
                    String mostSimilarWord = Strings.getMostSimilarWord(
                        propertyNames[notFoundPropertyIndex],
                        readProperties
                    );

                    List<String> elements = new ArrayList<>(
                        Arrays.asList( propertyNames ).subList( 0, notFoundPropertyIndex )
                    );
                    elements.add( mostSimilarWord );
                    reportMappingError(
                        Message.PROPERTYMAPPING_INVALID_PROPERTY_NAME, sourceName, Strings.join( elements, "." )
                    );
                }
                else {
                    reportMappingError(
                        Message.PROPERTYMAPPING_INVALID_PROPERTY_NAME_SOURCE_HAS_NO_PROPERTIES,
                        sourceName,
                        sourceType.describe()
                    );
                }
            }
        }

        private List<PropertyEntry> matchWithSourceAccessorTypes(Type type, String[] entryNames,
                                                                 boolean allowedMapToBean) {
            List<PropertyEntry> sourceEntries = new ArrayList<>();
            Type newType = type;
            for ( int i = 0; i < entryNames.length; i++ ) {
                boolean matchFound = false;
                Type noBoundsType = newType.withoutBounds();
                ReadAccessor readAccessor = noBoundsType.getReadAccessor( entryNames[i], i > 0 || allowedMapToBean );
                if ( readAccessor != null ) {
                    PresenceCheckAccessor presenceChecker = noBoundsType.getPresenceChecker( entryNames[i] );
                    newType = typeFactory.getReturnType(
                        (DeclaredType) noBoundsType.getTypeMirror(),
                        readAccessor
                    );
                    sourceEntries.add( forSourceReference(
                        Arrays.copyOf( entryNames, i + 1 ),
                        readAccessor,
                        presenceChecker,
                        newType
                    ) );
                    matchFound = true;
                }
                if ( !matchFound ) {
                    break;
                }
            }
            return sourceEntries;
        }

        private void reportMappingError(Message msg, Object... objects) {
            messager.printMessage( method.getExecutable(), annotationMirror, sourceAnnotationValue, msg, objects );
        }
    }

    /**
     * Builds a {@link SourceReference} from a property.
     */
    public static class BuilderFromProperty {

        private String name;
        private ReadAccessor readAccessor;
        private PresenceCheckAccessor presenceChecker;
        private Type type;
        private Parameter sourceParameter;

        public BuilderFromProperty name(String name) {
            this.name = name;
            return this;
        }

        public BuilderFromProperty readAccessor(ReadAccessor readAccessor) {
            this.readAccessor = readAccessor;
            return this;
        }

        public BuilderFromProperty presenceChecker(PresenceCheckAccessor presenceChecker) {
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
                sourcePropertyEntries.add( forSourceReference(
                    new String[] { name },
                    readAccessor,
                    presenceChecker,
                    type
                ) );
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
            return new SourceReference( sourceParameter, sourceReference.getPropertyEntries(), true );
        }
    }

    private SourceReference(Parameter sourceParameter, List<PropertyEntry> sourcePropertyEntries, boolean isValid) {
        super( sourceParameter, sourcePropertyEntries, isValid );
    }

    public SourceReference pop() {
        if ( getPropertyEntries().size() > 1 ) {
            List<PropertyEntry> newPropertyEntries =
                new ArrayList<>( getPropertyEntries().subList( 1, getPropertyEntries().size() ) );
            return new SourceReference( getParameter(), newPropertyEntries, isValid() );
        }
        else {
            return null;
        }
    }

    public List<SourceReference> push(TypeFactory typeFactory, FormattingMessager messager, Method method ) {
        List<SourceReference> result = new ArrayList<>();
        PropertyEntry deepestProperty = getDeepestProperty();
        if ( deepestProperty != null ) {
            Type type = deepestProperty.getType();
            Map<String, ReadAccessor> newDeepestReadAccessors = type.getPropertyReadAccessors();
            String parameterName = getParameter().getName();
            String deepestPropertyFullName = deepestProperty.getFullName();
            for ( Map.Entry<String, ReadAccessor> newDeepestReadAccessorEntry : newDeepestReadAccessors.entrySet() ) {
                // Always include the parameter name in the new full name.
                // Otherwise multi source parameters might be reported incorrectly
                String newFullName =
                    parameterName + "." + deepestPropertyFullName + "." + newDeepestReadAccessorEntry.getKey();
                SourceReference sourceReference = new BuilderFromMapping()
                    .sourceName( newFullName )
                    .method( method )
                    .messager( messager )
                    .typeFactory( typeFactory )
                    .build();
                result.add( sourceReference );
            }
        }
        return result;
    }

}
