/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.beanmapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.type.DeclaredType;

import org.mapstruct.ap.internal.model.common.BuilderType;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.model.source.MappingOptions;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.gem.BuilderGem;
import org.mapstruct.ap.internal.gem.CollectionMappingStrategyGem;
import org.mapstruct.ap.internal.util.FormattingMessager;
import org.mapstruct.ap.internal.util.Message;
import org.mapstruct.ap.internal.util.Strings;
import org.mapstruct.ap.internal.util.accessor.Accessor;
import org.mapstruct.ap.internal.util.accessor.AccessorType;

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
 * After building, {@link #isValid()} will return true when when no problems are detected during building.
 *
 * @author Sjaak Derksen
 */
public class TargetReference extends AbstractReference {

    /**
     * Builds a {@link TargetReference} from an {@code @Mappping}.
     */
    public static class Builder {

        private Method method;
        private FormattingMessager messager;
        private TypeFactory typeFactory;

        // mapping parameters
        private boolean isReversed = false;
        private boolean isIgnored = false;
        private String targetName = null;
        private AnnotationMirror annotationMirror = null;
        private AnnotationValue targetAnnotationValue = null;
        private AnnotationValue sourceAnnotationValue = null;
        private Method templateMethod = null;
        /**
         * During {@link #getTargetEntries(Type, String[])} an error can occur. However, we are invoking
         * that multiple times because the first entry can also be the name of the parameter. Therefore we keep
         * the error message until the end when we can report it.
         */
        private MappingErrorMessage errorMessage;

        public Builder messager(FormattingMessager messager) {
            this.messager = messager;
            return this;
        }

        public Builder mapping(MappingOptions mapping) {
            if ( mapping.getInheritContext() != null ) {
                this.isReversed = mapping.getInheritContext().isReversed();
                this.templateMethod = mapping.getInheritContext().getTemplateMethod();
            }
            this.isIgnored = mapping.isIgnored();
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

        public TargetReference build() {

            Objects.requireNonNull( method );
            Objects.requireNonNull( typeFactory );
            Objects.requireNonNull( messager );

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

            boolean foundEntryMatch;
            Type resultType = typeBasedOnMethod( method.getResultType() );

            // there can be 4 situations
            // 1. Return type
            // 2. An inverse target reference where the source parameter name is used in the original mapping
            // 3. @MappingTarget, with
            // 4. or without parameter name.
            String[] targetPropertyNames = segments;
            List<PropertyEntry> entries = getTargetEntries( resultType, targetPropertyNames );
            foundEntryMatch = (entries.size() == targetPropertyNames.length);
            if ( !foundEntryMatch && segments.length > 1
                && matchesSourceOrTargetParameter( segments[0], parameter, isReversed ) ) {
                targetPropertyNames = Arrays.copyOfRange( segments, 1, segments.length );
                entries = getTargetEntries( resultType, targetPropertyNames );
                foundEntryMatch = (entries.size() == targetPropertyNames.length);
            }

            if ( !foundEntryMatch && errorMessage != null && !isReversed ) {
                // This is called only for reporting errors
                errorMessage.report( );
            }

            // foundEntryMatch = isValid, errors are handled here, and the BeanMapping uses that to ignore
            // the creation of mapping for invalid TargetReferences
            return new TargetReference( parameter, entries, foundEntryMatch );
        }

        private List<PropertyEntry> getTargetEntries(Type type, String[] entryNames) {

            // initialize
            CollectionMappingStrategyGem cms = method.getOptions().getMapper().getCollectionMappingStrategy();
            List<PropertyEntry> targetEntries = new ArrayList<>();
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
                    || isWriteAccessorNotValidWhenLast( targetWriteAccessor, targetReadAccessor, isIgnored, isLast ) ) {
                    // there should always be a write accessor (except for the last when the mapping is ignored and
                    // there is a read accessor) and there should be read accessor mandatory for all but the last
                    setErrorMessage( targetWriteAccessor, targetReadAccessor, entryNames, i, nextType );
                    break;
                }

                if ( isLast || ( targetWriteAccessor.getAccessorType() == AccessorType.SETTER  ||
                                targetWriteAccessor.getAccessorType() == AccessorType.FIELD ) ) {
                    // only intermediate nested properties when they are a true setter or field accessor
                    // the last may be other readAccessor (setter / getter / adder).

                    nextType = findNextType( nextType, targetWriteAccessor, targetReadAccessor );

                    // check if an entry alread exists, otherwise create
                    String[] fullName = Arrays.copyOfRange( entryNames, 0, i + 1 );
                    BuilderType builderType;
                    PropertyEntry propertyEntry = null;
                    if ( method.isUpdateMethod() ) {
                        propertyEntry = PropertyEntry.forTargetReference( fullName,
                                        targetReadAccessor,
                                        targetWriteAccessor,
                                        nextType,
                                        null
                        );
                    }
                    else {
                        BuilderGem builder = method.getOptions().getBeanMapping().getBuilder();
                        builderType = typeFactory.builderTypeFor( nextType, builder );
                        propertyEntry = PropertyEntry.forTargetReference( fullName,
                                        targetReadAccessor,
                                        targetWriteAccessor,
                                        nextType,
                                        builderType
                        );

                    }
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
            if ( toUse.getAccessorType() == AccessorType.GETTER  || toUse.getAccessorType() == AccessorType.FIELD ) {
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
                errorMessage = new NoPropertyErrorMessage( this, entryNames, index, nextType );
            }
            else if ( targetWriteAccessor == null ) {
                errorMessage = new NoWriteAccessorErrorMessage(this );
            }
            else {
                //TODO there is no read accessor. What should we do here?
                errorMessage = new NoPropertyErrorMessage( this, entryNames, index, nextType );
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
                BuilderGem builder = method.getOptions().getBeanMapping().getBuilder();
                return typeFactory.effectiveResultTypeFor( type, builder );
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
         * @param isIgnored true when ignored
         * @param isLast whether or not this is the last write accessor in the entry chain
         *
         * @return {@code true} if the write accessor is not valid, {@code false} otherwise. See description for more
         * information
         */
        private static boolean isWriteAccessorNotValidWhenLast(Accessor writeAccessor, Accessor readAccessor,
            boolean isIgnored, boolean isLast) {
            return writeAccessor == null && isLast && ( readAccessor == null || !isIgnored );
        }

        /**
         * Validates that the {@code segment} is the same as the {@code targetParameter} or the {@code
         * inverseSourceParameter} names
         *
         * @param segment that needs to be checked
         * @param targetParameter the target parameter if it exists

         * @param isInverse whether a inverse {@link TargetReference} is being built
         *
         * @return {@code true} if the segment matches the name of the {@code targetParameter} or the name of the
         * {@code inverseSourceParameter} when this is a inverse {@link TargetReference} is being built, {@code
         * false} otherwise
         */
        private boolean matchesSourceOrTargetParameter(String segment, Parameter targetParameter, boolean isInverse) {
            boolean matchesTargetParameter = targetParameter != null && targetParameter.getName().equals( segment );
            return matchesTargetParameter || matchesSourceOnInverseSourceParameter( segment, isInverse );
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
         * @param isInverse whether a inverse {@link TargetReference} is being built
         *
         * @return on match when inverse and segment matches the one and only source parameter
         */
        private boolean matchesSourceOnInverseSourceParameter( String segment, boolean isInverse ) {
            boolean result = false;
            if ( isInverse ) {
                // there is only source parameter by definition when applying @InheritInverseConfiguration
                Parameter inverseSourceParameter = first( templateMethod.getSourceParameters() );
                result = inverseSourceParameter.getName().equals( segment );
            }
            return result;
        }
    }

    private TargetReference(Parameter sourceParameter, List<PropertyEntry> targetPropertyEntries, boolean isValid) {
        super( sourceParameter, targetPropertyEntries, isValid );
    }

    public TargetReference pop() {
        if ( getPropertyEntries().size() > 1 ) {
            List<PropertyEntry> newPropertyEntries = new ArrayList<>( getPropertyEntries().size() - 1 );
            for ( PropertyEntry propertyEntry : getPropertyEntries() ) {
                PropertyEntry newPropertyEntry = propertyEntry.pop();
                if ( newPropertyEntry != null ) {
                    newPropertyEntries.add( newPropertyEntry );
                }
            }
            return new TargetReference( null, newPropertyEntries, isValid() );
        }
        else {
            return null;
        }
    }

    private abstract static class MappingErrorMessage {
        private final Builder builder;

        private MappingErrorMessage(Builder builder) {
            this.builder = builder;
        }

        abstract void report();

        protected void printErrorMessage(Message message, Object... args) {
            Object[] errorArgs = new Object[args.length + 2];
            errorArgs[0] = builder.targetName;
            errorArgs[1] = builder.method.getResultType();
            System.arraycopy( args, 0, errorArgs, 2, args.length );
            AnnotationMirror annotationMirror = builder.annotationMirror;
            builder.messager.printMessage( builder.method.getExecutable(),
                annotationMirror,
                builder.sourceAnnotationValue,
                message,
                errorArgs
            );
        }
    }

    private static class NoWriteAccessorErrorMessage extends MappingErrorMessage {

        private NoWriteAccessorErrorMessage(Builder builder) {
            super( builder );
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

        private NoPropertyErrorMessage(Builder builder, String[] entryNames, int index,
                                       Type nextType) {
            super( builder );
            this.entryNames = entryNames;
            this.index = index;
            this.nextType = nextType;
        }

        @Override
        public void report() {

            Set<String> readAccessors = nextType.getPropertyReadAccessors().keySet();
            String mostSimilarProperty = Strings.getMostSimilarWord( entryNames[index], readAccessors );

            List<String> elements = new ArrayList<>( Arrays.asList( entryNames ).subList( 0, index ) );
            elements.add( mostSimilarProperty );

            printErrorMessage( Message.BEANMAPPING_UNKNOWN_PROPERTY_IN_RESULTTYPE, Strings.join( elements, "." ) );
        }
    }

}
