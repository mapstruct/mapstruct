/**
 *  Copyright 2012-2016 Gunnar Morling (http://www.gunnarmorling.de/)
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
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.DeclaredType;

import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.prism.CollectionMappingStrategyPrism;
import org.mapstruct.ap.internal.util.FormattingMessager;
import org.mapstruct.ap.internal.util.Message;
import org.mapstruct.ap.internal.util.Strings;

import static org.mapstruct.ap.internal.util.Collections.first;
import org.mapstruct.ap.internal.util.Executables;

/**
 * This class describes the source side of a property mapping.
 * <p>
 * It contains the source parameter, and all individual (nested) property entries. So consider the following mapping
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
 * After building, {@link #isValid()} will return true when when no problems are detected during building.
 *
 * @author Sjaak Derksen
 */
public class NestedReference {

    private final Parameter parameter;
    private final List<PropertyEntry> propertyEntries;
    private final boolean isValid;

    /**
     * Builds a {@link NestedReference} from an {@code @Mappping}.
     */
    public static class BuilderFromSourceMapping {

        private Mapping mapping;
        private SourceMethod method;
        private FormattingMessager messager;
        private TypeFactory typeFactory;

        public BuilderFromSourceMapping messager(FormattingMessager messager) {
            this.messager = messager;
            return this;
        }

        public BuilderFromSourceMapping mapping(Mapping mapping) {
            this.mapping = mapping;
            return this;
        }

        public BuilderFromSourceMapping method(SourceMethod method) {
            this.method = method;
            return this;
        }

        public BuilderFromSourceMapping typeFactory(TypeFactory typeFactory) {
            this.typeFactory = typeFactory;
            return this;
        }

        public NestedReference build() {

            String sourceName = mapping.getSourceName();

            if ( sourceName == null ) {
                return null;
            }

            boolean isValid = true;
            boolean foundEntryMatch;

            String[] sourcePropertyNames = new String[0];
            String[] segments = sourceName.split( "\\." );
            Parameter parameter = null;

            List<PropertyEntry> entries = new ArrayList<PropertyEntry>();

            if ( method.getSourceParameters().size() > 1 ) {

                // parameterName is mandatory for multiple source parameters
                if ( segments.length > 0 ) {
                    String sourceParameterName = segments[0];
                    parameter = method.getSourceParameter( sourceParameterName );
                    if ( parameter == null ) {
                        reportMappingError( Message.PROPERTYMAPPING_INVALID_PARAMETER_NAME, sourceParameterName );
                        isValid = false;
                    }
                }
                if ( segments.length > 1 && parameter != null ) {
                    sourcePropertyNames = Arrays.copyOfRange( segments, 1, segments.length );
                    entries = getSourceEntries( parameter.getType(), sourcePropertyNames );
                    foundEntryMatch = (entries.size() == sourcePropertyNames.length);
                }
                else {
                    // its only a parameter, no property
                    foundEntryMatch = true;
                }

            }
            else {

                // parameter name is not mandatory for single source parameter
                sourcePropertyNames = segments;
                parameter = method.getSourceParameters().get( 0 );
                entries = getSourceEntries( parameter.getType(), sourcePropertyNames );
                foundEntryMatch = (entries.size() == sourcePropertyNames.length);

                if ( !foundEntryMatch ) {
                    //Lets see if the expression contains the parameterName, so parameterName.propName1.propName2
                    if ( parameter.getName().equals( segments[0] ) ) {
                        sourcePropertyNames = Arrays.copyOfRange( segments, 1, segments.length );
                        entries = getSourceEntries( parameter.getType(), sourcePropertyNames );
                        foundEntryMatch = (entries.size() == sourcePropertyNames.length);
                    }
                    else {
                        // segment[0] cannot be attributed to the parameter name.
                        parameter = null;
                    }
                }
            }

            if ( !foundEntryMatch ) {

                if ( parameter != null ) {
                    reportMappingError( Message.PROPERTYMAPPING_NO_PROPERTY_IN_PARAMETER, parameter.getName(),
                        Strings.join( Arrays.asList( sourcePropertyNames ), "." ) );
                }
                else {
                    reportMappingError( Message.PROPERTYMAPPING_INVALID_PROPERTY_NAME, mapping.getSourceName() );
                }
                isValid = false;
            }

            return new NestedReference( parameter, entries, isValid );
        }

        private List<PropertyEntry> getSourceEntries(Type type, String[] entryNames) {
            List<PropertyEntry> sourceEntries = new ArrayList<PropertyEntry>();
            Type newType = type;
            for ( int i = 0; i < entryNames.length; i++ ) {

                // initialize
                boolean matchFound = false;
                Map<String, ExecutableElement> sourceReadAccessors = newType.getPropertyReadAccessors();

                // search through the read accessors
                for ( Map.Entry<String, ExecutableElement> getter : sourceReadAccessors.entrySet() ) {

                    // match
                    if ( getter.getKey().equals( entryNames[i] ) ) {

                        newType =  typeFactory.getReturnType(
                            (DeclaredType) newType.getTypeMirror(),
                            getter.getValue()
                        );

                        // search existing entries
                        String[] fullName = Arrays.copyOfRange( entryNames, 0, i + 1 );
                        PropertyEntry existingPropertyEntry = searchForExistingPropertyEntry( fullName );

                        // make a new entry when none exists
                        if ( existingPropertyEntry != null ) {
                            sourceEntries.add( existingPropertyEntry );
                        }
                        else {
                            sourceEntries.add( new PropertyEntry( fullName, getter.getValue(), newType ) );
                        }
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

        /**
         * Searches if a property entry has been created before for this specific bean mapping method.
         *
         * @param fullName the full name of the property.
         * @return
         */
        private PropertyEntry searchForExistingPropertyEntry(String[] fullName) {
            PropertyEntry existingPropertyEntry = null;
            for ( String entry : method.getMappingOptions().getMappings().keySet() ) {
                for ( Mapping existingMapping : method.getMappingOptions().getMappings().get( entry ) ) {
                    if ( existingMapping.getSourceReference() != null ) {
                        NestedReference sourceRef = existingMapping.getSourceReference();
                        for ( PropertyEntry propertyEntry : sourceRef.getPropertyEntries() ) {
                            if ( Arrays.deepEquals( fullName, propertyEntry.fullName ) ) {
                                existingPropertyEntry = propertyEntry;
                                break;
                            }
                        }
                    }
                }
            }
            return existingPropertyEntry;
        }

        private void reportMappingError(Message msg, Object... objects) {
            messager.printMessage( method.getExecutable(), mapping.getMirror(), mapping.getSourceAnnotationValue(),
                msg, objects );
        }
    }

    /**
     * Builds a {@link NestedReference} from an {@code @Mappping}.
     */
    public static class BuilderFromTargetMapping {

        private Mapping mapping;
        private SourceMethod method;
        private FormattingMessager messager;
        private TypeFactory typeFactory;
        private boolean isReverse;

        public BuilderFromTargetMapping messager(FormattingMessager messager) {
            this.messager = messager;
            return this;
        }

        public BuilderFromTargetMapping mapping(Mapping mapping) {
            this.mapping = mapping;
            return this;
        }

        public BuilderFromTargetMapping method(SourceMethod method) {
            this.method = method;
            return this;
        }

        public BuilderFromTargetMapping typeFactory(TypeFactory typeFactory) {
            this.typeFactory = typeFactory;
            return this;
        }

        public BuilderFromTargetMapping isReverse(boolean isReverse) {
            this.isReverse = isReverse;
            return this;
        }

        public NestedReference build() {

            String targetName = mapping.getTargetName();

            if ( targetName == null ) {
                return null;
            }

            boolean isValid = true;
            boolean foundEntryMatch = false;

            String[] segments = targetName.split( "\\." );
            Parameter parameter = method.getMappingTargetParameter();

            // check returntype
            Type resultType = method.getResultType();
            String[] targetPropertyNames = segments;
            List<PropertyEntry> entries = getTargetEntries( resultType, targetPropertyNames );
            foundEntryMatch = (entries.size() == targetPropertyNames.length);
            if ( !foundEntryMatch ) {
                targetPropertyNames = Arrays.copyOfRange( segments, 1, segments.length );
                entries = getTargetEntries( resultType, targetPropertyNames );
                foundEntryMatch = (entries.size() == targetPropertyNames.length);
            }

            // @MappingTarget (update) can possibly introduce a parameter name in the mapping..
            // starts to get hairy in case of reversing..
            // situation with parameter name in name and without..

//            else {

                // try option with parameter name and without
                // TODO ignore parameter name completely when reverse
//                if ( segments.length == 1 ) {
//                    String mappingTargetParameterName = segments[0];
//                    if ( parameter.getName().equals( segments[0] ) ) {
//                        foundEntryMatch = true;
//                    }
//                    else {
//                        reportMappingError( Message.PROPERTYMAPPING_INVALID_PARAMETER_NAME,
//                            mappingTargetParameterName );
//                        isValid = false;
//                    }
//                }
//                else if ( segments.length > 1 ) {
//                    entries = getTargetEntries( parameter.getType(), segments );
//                    foundEntryMatch = (entries.size() == targetPropertyNames.length);
//                    if ( !foundEntryMatch ) {
//                        targetPropertyNames = Arrays.copyOfRange( segments, 1, segments.length );
//                        entries = getTargetEntries( parameter.getType(), targetPropertyNames );
//                        foundEntryMatch = (entries.size() == targetPropertyNames.length);
//                    }
//                }
//            }
//
            if ( !foundEntryMatch ) {

//                if ( parameter != null ) {
//                    reportMappingError( Message.PROPERTYMAPPING_NO_PROPERTY_IN_PARAMETER, parameter.getName(),
//                        Strings.join( Arrays.asList( targetPropertyNames ), "." ) );
//                }
//                else {
//                    reportMappingError( Message.PROPERTYMAPPING_INVALID_PROPERTY_NAME, mapping.getSourceName() );
//                }
                isValid = false;
            }

            return new NestedReference( parameter, entries, isValid );
        }

        private List<PropertyEntry> getTargetEntries(Type type, String[] entryNames) {

            // initialize
            CollectionMappingStrategyPrism cms = method.getMapperConfiguration().getCollectionMappingStrategy();
            List<PropertyEntry> targetEntries = new ArrayList<PropertyEntry>();
            Type newType = type;

            // iterate, establish for each entry the target write accessors. Other than setter is only allowed for
            // last entry
            for ( int i = 0; i < entryNames.length; i++ ) {

                boolean matchFound = false;
                Set<Map.Entry<String, ExecutableElement>> targetWriteAccessorEntries =
                    newType.getPropertyWriteAccessors( cms ).entrySet();

                // iterate over target write accessors entries  find existing or create new property entry.
                for ( Map.Entry<String, ExecutableElement> targetWriteAccessorEntry : targetWriteAccessorEntries ) {

                    if ( targetWriteAccessorEntry.getKey().equals( entryNames[i] ) ) {

                        // there is a match, determine the accessor
                        ExecutableElement targetWriteAccessor = targetWriteAccessorEntry.getValue();

                        if ( (i == entryNames.length - 1 )
                            || (Executables.isSetterMethod( targetWriteAccessor ) ) ) {
                            // only intermediate nested properties when they are a true setter
                            // the last may be other accessor (setter / getter / adder).

                            if ( Executables.isGetterMethod( targetWriteAccessor ) ) {
                                newType = typeFactory.getReturnType(
                                    (DeclaredType) newType.getTypeMirror(),
                                    targetWriteAccessor );
                            }
                            else {
                                newType = typeFactory.getSingleParameter(
                                    (DeclaredType) newType.getTypeMirror(),
                                    targetWriteAccessor ).getType();
                            }
                            String[] fullName = Arrays.copyOfRange( entryNames, 0, i + 1 );

                            // check if an entry alread exists, otherwise create
                            PropertyEntry existingPropertyEntry = searchForExistingPropertyEntry( fullName );
                            if ( existingPropertyEntry != null ) {
                                targetEntries.add( existingPropertyEntry );
                            }
                            else {
                                targetEntries.add( new PropertyEntry( fullName, targetWriteAccessor, newType ) );
                            }
                            matchFound = true;
                            break;
                        }
                    }
                }
                if ( !matchFound ) {
                    break;
                }
            }
            return targetEntries;
        }

        /**
         * Searches if a property entry has been created before for this specific bean mapping method.
         *
         * @param fullName the full name of the property.
         * @return
         */
        private PropertyEntry searchForExistingPropertyEntry(String[] fullName) {
            PropertyEntry existingPropertyEntry = null;
            for ( String entry : method.getMappingOptions().getMappings().keySet() ) {
                for ( Mapping existingMapping : method.getMappingOptions().getMappings().get( entry ) ) {
                    if ( existingMapping.getTargetReference() != null ) {
                        NestedReference targetRef = existingMapping.getTargetReference();
                        for ( PropertyEntry propertyEntry : targetRef.getPropertyEntries() ) {
                            if ( Arrays.deepEquals( fullName, propertyEntry.fullName ) ) {
                                existingPropertyEntry = propertyEntry;
                                break;
                            }
                        }
                    }
                }
            }
            return existingPropertyEntry;
        }

        private void reportMappingError(Message msg, Object... objects) {
            messager.printMessage( method.getExecutable(), mapping.getMirror(), mapping.getSourceAnnotationValue(),
                msg, objects );
        }
    }

    /**
     * Builds a {@link NestedReference} from a property.
     */
    public static class BuilderFromProperty {

        private String name;
        private ExecutableElement accessor;
        private Type type;
        private Parameter sourceParameter;

        public BuilderFromProperty name(String name) {
            this.name = name;
            return this;
        }

        public BuilderFromProperty accessor(ExecutableElement accessor) {
            this.accessor = accessor;
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

        public NestedReference build() {
            List<PropertyEntry> sourcePropertyEntries = new ArrayList<PropertyEntry>();
            if ( accessor != null ) {
                sourcePropertyEntries.add( new PropertyEntry( name, accessor, type ) );
            }
            return new NestedReference( sourceParameter, sourcePropertyEntries, true );
        }
    }

    private NestedReference(Parameter sourceParameter, List<PropertyEntry> sourcePropertyEntries, boolean isValid) {
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
        List<String> elementName = new ArrayList<String>();
        if ( parameter != null ) {
            // only relevant for source properties
            elementName.add( parameter.getName() );
        }
        for ( PropertyEntry propertyEntry : propertyEntries ) {
            elementName.add( propertyEntry.getName() );
        }
        return elementName;
    }

    /**
     * A PropertyEntry contains information on the name, accessor and return type of a property.
     *
     * It can be shared between several nested properties. For example
     *
     * bean
     *
     * nestedMapping1 = "x.y1.z1" nestedMapping2 = "x.y1.z2" nestedMapping3 = "x.y2.z3"
     *
     * has property entries x, y1, y2, z1, z2, z3.
     */
    public static class PropertyEntry {

        private final String[] fullName;
        private final ExecutableElement accessor;
        private final Type type;

        public PropertyEntry(String[] fullName, ExecutableElement accessor, Type type) {
            this.fullName = fullName;
            this.accessor = accessor;
            this.type = type;
        }

        public PropertyEntry(String name, ExecutableElement accessor, Type type) {
            this.fullName = new String[]{name};
            this.accessor = accessor;
            this.type = type;
        }

        public String getName() {
            return fullName[fullName.length - 1];
        }

        public ExecutableElement getAccessor() {
            return accessor;
        }

        public Type getType() {
            return type;
        }

    }

    /**
     * Creates a copy of this reference, which is adapted to the given method
     *
     * @param method the method to create the copy for
     * @return the copy
     */
    public NestedReference copyForInheritanceTo(SourceMethod method) {
        List<Parameter> replacementParamCandidates = new ArrayList<Parameter>();
        for ( Parameter sourceParam : method.getSourceParameters() ) {
            if ( sourceParam.getType().isAssignableTo( parameter.getType() ) ) {
                replacementParamCandidates.add( sourceParam );
            }
        }

        Parameter replacement = parameter;
        if ( replacementParamCandidates.size() == 1 ) {
            replacement = first( replacementParamCandidates );
        }

        return new NestedReference( replacement, propertyEntries, isValid );
    }
}
