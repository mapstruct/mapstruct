/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
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

import static org.mapstruct.ap.internal.util.Collections.first;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.lang.model.element.ExecutableElement;

import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.util.FormattingMessager;
import org.mapstruct.ap.internal.util.Message;
import org.mapstruct.ap.internal.util.Strings;

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
        private SourceMethod method;
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

        public BuilderFromMapping method(SourceMethod method) {
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
                    foundEntryMatch = ( entries.size() == sourcePropertyNames.length );
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
                foundEntryMatch = ( entries.size() == sourcePropertyNames.length );

                if ( !foundEntryMatch ) {
                    //Lets see if the expression contains the parameterName, so parameterName.propName1.propName2
                    if ( parameter.getName().equals( segments[0] ) ) {
                        sourcePropertyNames = Arrays.copyOfRange( segments, 1, segments.length );
                        entries = getSourceEntries( parameter.getType(), sourcePropertyNames );
                        foundEntryMatch = ( entries.size() == sourcePropertyNames.length );
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

            return new SourceReference( parameter, entries, isValid );
        }

        private List<PropertyEntry> getSourceEntries(Type type, String[] entryNames) {
            List<PropertyEntry> sourceEntries = new ArrayList<PropertyEntry>();
            Type newType = type;
            for ( String entryName : entryNames ) {
                boolean matchFound = false;
                Map<String, ExecutableElement> sourceReadAccessors = newType.getPropertyReadAccessors();
                for (  Map.Entry<String, ExecutableElement> getter : sourceReadAccessors.entrySet() ) {
                    if ( getter.getKey().equals( entryName ) ) {
                        newType = typeFactory.getReturnType( newType.getTypeElement(), getter.getValue() );
                        sourceEntries.add( new PropertyEntry( entryName, getter.getValue(), newType ) );
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
                                   msg, objects );
        }
    }

    /**
     * Builds a {@link SourceReference} from a property.
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

        public SourceReference build() {
            List<PropertyEntry> sourcePropertyEntries = new ArrayList<PropertyEntry>();
            if ( accessor != null ) {
                sourcePropertyEntries.add( new PropertyEntry( name, accessor, type ) );
            }
            return new SourceReference( sourceParameter, sourcePropertyEntries, true );
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
        List<String> sourceName = new ArrayList<String>();
        sourceName.add( parameter.getName() );
        for ( PropertyEntry propertyEntry : propertyEntries ) {
            sourceName.add( propertyEntry.getName() );
        }
        return sourceName;
    }

    /**
     * A PropertyEntry contains information on the name, accessor and return type of a property.
     */
    public static class PropertyEntry {

        private final String name;
        private final ExecutableElement accessor;
        private final Type type;

        public PropertyEntry(String name, ExecutableElement accessor, Type type) {
            this.name = name;
            this.accessor = accessor;
            this.type = type;
        }

        public String getName() {
            return name;
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
    public SourceReference copyForInheritanceTo(SourceMethod method) {
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

        return new SourceReference( replacement, propertyEntries, isValid );
    }
}
