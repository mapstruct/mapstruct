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

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.DeclaredType;

import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.prism.CollectionMappingStrategyPrism;
import org.mapstruct.ap.internal.util.FormattingMessager;
import org.mapstruct.ap.internal.util.Message;

import org.mapstruct.ap.internal.util.Executables;

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
 * After building, {@link #isValid()} will return true when when no problems are detected during building.
 *
 * @author Sjaak Derksen
 */
public class TargetReference {

    private final Parameter parameter;
    private final List<PropertyEntry> propertyEntries;
    private final boolean isValid;


    /**
     * Builds a {@link TargetReference} from an {@code @Mappping}.
     */
    public static class BuilderFromTargetMapping {

        private Mapping mapping;
        private SourceMethod method;
        private FormattingMessager messager;
        private TypeFactory typeFactory;

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
            return this;
        }

        public TargetReference build() {

            String targetName = mapping.getTargetName();

            if ( targetName == null ) {
                return null;
            }


            String[] segments = targetName.split( "\\." );
            Parameter parameter = method.getMappingTargetParameter();

            boolean foundEntryMatch;
            Type resultType = method.getResultType();

            // there can be 3 situations
            // 1. Return type
            // 2. @MappingTarget, with
            // 3. or without parameter name.
            String[] targetPropertyNames = segments;
            List<PropertyEntry> entries = getTargetEntries( resultType, targetPropertyNames );
            foundEntryMatch = (entries.size() == targetPropertyNames.length);
            if ( !foundEntryMatch && segments.length > 1 ) {
                targetPropertyNames = Arrays.copyOfRange( segments, 1, segments.length );
                entries = getTargetEntries( resultType, targetPropertyNames );
                foundEntryMatch = (entries.size() == targetPropertyNames.length);
            }

            // foundEntryMatch = isValid, errors are handled in the BeanMapping, where the error context is known
            return new TargetReference( parameter, entries, foundEntryMatch );
        }

        private List<PropertyEntry> getTargetEntries(Type type, String[] entryNames) {

            // initialize
            CollectionMappingStrategyPrism cms = method.getMapperConfiguration().getCollectionMappingStrategy();
            List<PropertyEntry> targetEntries = new ArrayList<PropertyEntry>();
            Type nextType = type;

            // iterate, establish for each entry the target write accessors. Other than setter is only allowed for
            // last entry
            for ( int i = 0; i < entryNames.length; i++ ) {

                ExecutableElement targetReadAccessor = nextType.getPropertyReadAccessors().get( entryNames[i] );
                ExecutableElement targetWriteAccessor = nextType.getPropertyWriteAccessors( cms ).get( entryNames[i] );
                if ( targetWriteAccessor == null || ( i < entryNames.length - 1 && targetReadAccessor == null) ) {
                    // there should always be a write accessor and there should be read accessor mandatory for all
                    // but the last
                    // TODO error handling when full fledged target mapping is in place.
                    break;
                }

                if ( (i == entryNames.length - 1) || (Executables.isSetterMethod( targetWriteAccessor ) ) ) {
                    // only intermediate nested properties when they are a true setter
                    // the last may be other readAccessor (setter / getter / adder).

                    if ( Executables.isGetterMethod( targetWriteAccessor ) ) {
                        nextType = typeFactory.getReturnType(
                            (DeclaredType) nextType.getTypeMirror(),
                            targetWriteAccessor );
                    }
                    else {
                        nextType = typeFactory.getSingleParameter(
                            (DeclaredType) nextType.getTypeMirror(),
                            targetWriteAccessor ).getType();
                    }

                    // check if an entry alread exists, otherwise create
                    String[] fullName = Arrays.copyOfRange( entryNames, 0, i + 1 );
                    PropertyEntry propertyEntry = PropertyEntry.forTargetReference( fullName, targetReadAccessor,
                        targetWriteAccessor, nextType );
                    targetEntries.add( propertyEntry );
                    }

                }

            return targetEntries;
        }

        private void reportMappingError(Message msg, Object... objects) {
            messager.printMessage( method.getExecutable(), mapping.getMirror(), mapping.getSourceAnnotationValue(),
                msg, objects );
        }
    }


    private TargetReference(Parameter sourceParameter, List<PropertyEntry> sourcePropertyEntries, boolean isValid) {
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
}
