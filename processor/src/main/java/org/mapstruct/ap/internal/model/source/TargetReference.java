/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
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
import java.util.Set;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.type.DeclaredType;

import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.prism.CollectionMappingStrategyPrism;
import org.mapstruct.ap.internal.util.AccessorNamingUtils;
import org.mapstruct.ap.internal.util.Executables;
import org.mapstruct.ap.internal.util.FormattingMessager;
import org.mapstruct.ap.internal.util.Message;
import org.mapstruct.ap.internal.util.Strings;
import org.mapstruct.ap.internal.util.accessor.Accessor;

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
        private AccessorNamingUtils accessorNaming;
        private boolean isReverse;
        /**
         * Needed when we are building from reverse mapping. It is needed, so we can remove the first level if it is
         * needed.
         * E.g. If we have a mapping like:
         * <code>
         * {@literal @}Mapping( target = "letterSignature", source = "dto.signature" )
         * </code>
         *
         * When it is reversed it will look like:
         * <code>
         * {@literal @}Mapping( target = "dto.signature", source = "letterSignature" )
         * </code>
         *
         * The {@code dto} needs to be considered as a possibility for a target name only if a Target Reference for
         * a reverse is created.
         */
        private Parameter reverseSourceParameter;
        /**
         * During {@link #getTargetEntries(Type, String[])} an error can occur. However, we are invoking
         * that multiple times because the first entry can also be the name of the parameter. Therefore we keep
         * the error message until the end when we can report it.
         */
        private MappingErrorMessage errorMessage;

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

        public BuilderFromTargetMapping accessorNaming(AccessorNamingUtils accessorNaming) {
            this.accessorNaming = accessorNaming;
            return this;
        }

        public BuilderFromTargetMapping isReverse(boolean isReverse) {
            this.isReverse = isReverse;
            return this;
        }

        public BuilderFromTargetMapping reverseSourceParameter(Parameter reverseSourceParameter) {
            this.reverseSourceParameter = reverseSourceParameter;
            return this;
        }

        public TargetReference build() {

            String targetName = mapping.getTargetName();

            if ( targetName == null ) {
                return null;
            }

            String targetNameTrimmed = targetName.trim();
            if ( !targetName.equals( targetNameTrimmed ) ) {
                messager.printMessage(
                    method.getExecutable(),
                    mapping.getMirror(),
                    mapping.getTargetAnnotationValue(),
                    Message.PROPERTYMAPPING_WHITESPACE_TRIMMED,
                    targetName,
                    targetNameTrimmed
                );
            }
            String[] segments = targetNameTrimmed.split( "\\." );
            Parameter parameter = method.getMappingTargetParameter();

            boolean foundEntryMatch;
            Type resultType = method.getResultType();
            resultType = typeBasedOnMethod( resultType );

            // there can be 4 situations
            // 1. Return type
            // 2. A reverse target reference where the source parameter name is used in the original mapping
            // 3. @MappingTarget, with
            // 4. or without parameter name.
            String[] targetPropertyNames = segments;
            List<PropertyEntry> entries = getTargetEntries( resultType, targetPropertyNames );
            foundEntryMatch = (entries.size() == targetPropertyNames.length);
            if ( !foundEntryMatch && segments.length > 1
                && matchesSourceOrTargetParameter( segments[0], parameter, reverseSourceParameter, isReverse ) ) {
                targetPropertyNames = Arrays.copyOfRange( segments, 1, segments.length );
                entries = getTargetEntries( resultType, targetPropertyNames );
                foundEntryMatch = (entries.size() == targetPropertyNames.length);
            }

            if ( !foundEntryMatch && errorMessage != null && !isReverse ) {
                // This is called only for reporting errors
                errorMessage.report( );
            }

            // foundEntryMatch = isValid, errors are handled here, and the BeanMapping uses that to ignore
            // the creation of mapping for invalid TargetReferences
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

                Type mappingType = typeBasedOnMethod( nextType );
                Accessor targetReadAccessor = mappingType.getPropertyReadAccessors().get( entryNames[i] );
                Accessor targetWriteAccessor = mappingType.getPropertyWriteAccessors( cms ).get( entryNames[i] );
                boolean isLast = i == entryNames.length - 1;
                boolean isNotLast = i < entryNames.length - 1;
                if ( isWriteAccessorNotValidWhenNotLast( targetWriteAccessor, isNotLast )
                    || isWriteAccessorNotValidWhenLast( targetWriteAccessor, targetReadAccessor, mapping, isLast ) ) {
                    // there should always be a write accessor (except for the last when the mapping is ignored and
                    // there is a read accessor) and there should be read accessor mandatory for all but the last
                    setErrorMessage( targetWriteAccessor, targetReadAccessor, entryNames, i, nextType );
                    break;
                }

                if ( isLast || ( accessorNaming.isSetterMethod( targetWriteAccessor )
                    || Executables.isFieldAccessor( targetWriteAccessor ) ) ) {
                    // only intermediate nested properties when they are a true setter or field accessor
                    // the last may be other readAccessor (setter / getter / adder).

                    nextType = findNextType( nextType, targetWriteAccessor, targetReadAccessor );

                    // check if an entry alread exists, otherwise create
                    String[] fullName = Arrays.copyOfRange( entryNames, 0, i + 1 );
                    PropertyEntry propertyEntry = PropertyEntry.forTargetReference( fullName, targetReadAccessor,
                        targetWriteAccessor, nextType );
                    targetEntries.add( propertyEntry );
                }

            }

            return targetEntries;
        }

        /**
         * Finds the next type based on the initial type.
         *
         * @param initial for which a next type should be found
         * @param targetWriteAccessor the write accessor
         * @param targetReadAccessor the read accessor
         * @return the next type that should be used for finding a property entry
         */
        private Type findNextType(Type initial, Accessor targetWriteAccessor, Accessor targetReadAccessor) {
            Type nextType;
            Accessor toUse = targetWriteAccessor != null ? targetWriteAccessor : targetReadAccessor;
            if ( accessorNaming.isGetterMethod( toUse ) ||
                Executables.isFieldAccessor( toUse ) ) {
                nextType = typeFactory.getReturnType(
                    (DeclaredType) typeBasedOnMethod( initial ).getTypeMirror(),
                    toUse
                );
            }
            else {
                nextType = typeFactory.getSingleParameter(
                    (DeclaredType) typeBasedOnMethod( initial ).getTypeMirror(),
                    toUse
                ).getType();
            }
            return nextType;
        }

        private void setErrorMessage(Accessor targetWriteAccessor, Accessor targetReadAccessor, String[] entryNames,
                                     int index, Type nextType) {
            if ( targetWriteAccessor == null && targetReadAccessor == null ) {
                errorMessage = new NoPropertyErrorMessage( mapping, method, messager, entryNames, index, nextType );
            }
            else if ( targetWriteAccessor == null ) {
                errorMessage = new NoWriteAccessorErrorMessage( mapping, method, messager );
            }
            else {
                //TODO there is no read accessor. What should we do here?
                errorMessage = new NoPropertyErrorMessage( mapping, method, messager, entryNames, index, nextType );
            }
        }

        /**
         * When we are in an update method, i.e. source parameter with {@code @MappingTarget} then the type should
         * be itself, otherwise, we always get the effective type. The reason is that when doing updates we always
         * search for setters and getters within the updating type.
         */
        private Type typeBasedOnMethod(Type type) {
            if ( method.isUpdateMethod() ) {
                return type;
            }
            else {
                return type.getEffectiveType();
            }
        }

        /**
         * A write accessor is not valid if it is {@code null} and it is not last. i.e. for nested target mappings
         * there must be a write accessor for all entries except the last one.
         *
         * @param accessor that needs to be checked
         * @param isNotLast whether or not this is the last write accessor in the entry chain
         *
         * @return {@code true} if the accessor is not valid, {@code false} otherwise
         */
        private static boolean isWriteAccessorNotValidWhenNotLast(Accessor accessor, boolean isNotLast) {
            return accessor == null && isNotLast;
        }

        /**
         * For a last accessor to be valid, a read accessor should exist and the mapping should be ignored. All other
         * cases represent an invalid write accessor. This method will evaluate to {@code true} if the following is
         * {@code true}:
         * <ul>
         * <li>{@code writeAccessor} is {@code null}</li>
         * <li>It is for the last entry</li>
         * <li>A read accessor does not exist, or the mapping is not ignored</li>
         * </ul>
         *
         * @param writeAccessor that needs to be checked
         * @param readAccessor that is used
         * @param mapping that is used
         * @param isLast whether or not this is the last write accessor in the entry chain
         *
         * @return {@code true} if the write accessor is not valid, {@code false} otherwise. See description for more
         * information
         */
        private static boolean isWriteAccessorNotValidWhenLast(Accessor writeAccessor, Accessor readAccessor,
            Mapping mapping, boolean isLast) {
            return writeAccessor == null && isLast && ( readAccessor == null || !mapping.isIgnored() );
        }

        /**
         * Validates that the {@code segment} is the same as the {@code targetParameter} or the {@code
         * reverseSourceParameter} names
         *
         * @param segment that needs to be checked
         * @param targetParameter the target parameter if it exists
         * @param reverseSourceParameter the reverse source parameter if it exists
         * @param isReverse whether a reverse {@link TargetReference} is being built
         *
         * @return {@code true} if the segment matches the name of the {@code targetParameter} or the name of the
         * {@code reverseSourceParameter} when this is a reverse {@link TargetReference} is being built, {@code
         * false} otherwise
         */
        private static boolean matchesSourceOrTargetParameter(String segment, Parameter targetParameter,
            Parameter reverseSourceParameter, boolean isReverse) {
            boolean matchesTargetParameter =
                targetParameter != null && targetParameter.getName().equals( segment );
            return matchesTargetParameter
                || isReverse && reverseSourceParameter != null && reverseSourceParameter.getName().equals( segment );
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
        List<String> elementNames = new ArrayList<String>();
        if ( parameter != null ) {
            // only relevant for source properties
            elementNames.add( parameter.getName() );
        }
        for ( PropertyEntry propertyEntry : propertyEntries ) {
            elementNames.add( propertyEntry.getName() );
        }
        return elementNames;
    }

    public TargetReference pop() {
        if ( propertyEntries.size() > 1 ) {
            List<PropertyEntry> newPropertyEntries = new ArrayList<PropertyEntry>( propertyEntries.size() - 1 );
            for ( PropertyEntry propertyEntry : propertyEntries ) {
                PropertyEntry newPropertyEntry = propertyEntry.pop();
                if ( newPropertyEntry != null ) {
                    newPropertyEntries.add( newPropertyEntry );
                }
            }
            return new TargetReference( null, newPropertyEntries, isValid );
        }
        else {
            return null;
        }
    }

    private abstract static class MappingErrorMessage {
        private final Mapping mapping;
        private final SourceMethod method;
        private final FormattingMessager messager;

        private MappingErrorMessage(Mapping mapping, SourceMethod method, FormattingMessager messager) {
            this.mapping = mapping;
            this.method = method;
            this.messager = messager;
        }

        abstract void report();

        protected void printErrorMessage(Message message, Object... args) {
            Object[] errorArgs = new Object[args.length + 2];
            errorArgs[0] = mapping.getTargetName();
            errorArgs[1] = method.getResultType();
            System.arraycopy( args, 0, errorArgs, 2, args.length );
            AnnotationMirror annotationMirror = mapping.getMirror();
            messager.printMessage( method.getExecutable(), annotationMirror, mapping.getSourceAnnotationValue(),
                message, errorArgs
            );
        }
    }

    private static class NoWriteAccessorErrorMessage extends MappingErrorMessage {

        private NoWriteAccessorErrorMessage(Mapping mapping, SourceMethod method, FormattingMessager messager) {
            super( mapping, method, messager );
        }

        @Override
        public void report() {
            printErrorMessage( Message.BEANMAPPING_PROPERTY_HAS_NO_WRITE_ACCESSOR_IN_RESULTTYPE );
        }
    }

    private static class NoPropertyErrorMessage extends MappingErrorMessage {

        private final String[] entryNames;
        private final int index;
        private final Type nextType;

        private NoPropertyErrorMessage(Mapping mapping, SourceMethod method, FormattingMessager messager,
                                       String[] entryNames, int index, Type nextType) {
            super( mapping, method, messager );
            this.entryNames = entryNames;
            this.index = index;
            this.nextType = nextType;
        }

        @Override
        public void report() {

            Set<String> readAccessors = nextType.getPropertyReadAccessors().keySet();
            String mostSimilarProperty = Strings.getMostSimilarWord( entryNames[index], readAccessors );

            List<String> elements = new ArrayList<String>( Arrays.asList( entryNames ).subList( 0, index ) );
            elements.add( mostSimilarProperty );

            printErrorMessage( Message.BEANMAPPING_UNKNOWN_PROPERTY_IN_RESULTTYPE, Strings.join( elements, "." ) );
        }
    }

}
