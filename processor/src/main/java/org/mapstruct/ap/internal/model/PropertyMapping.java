/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;
import javax.lang.model.element.AnnotationMirror;

import org.mapstruct.ap.internal.gem.BuilderGem;
import org.mapstruct.ap.internal.gem.NullValueCheckStrategyGem;
import org.mapstruct.ap.internal.gem.NullValuePropertyMappingStrategyGem;
import org.mapstruct.ap.internal.model.assignment.AdderWrapper;
import org.mapstruct.ap.internal.model.assignment.ArrayCopyWrapper;
import org.mapstruct.ap.internal.model.assignment.EnumConstantWrapper;
import org.mapstruct.ap.internal.model.assignment.GetterWrapperForCollectionsAndMaps;
import org.mapstruct.ap.internal.model.assignment.SetterWrapper;
import org.mapstruct.ap.internal.model.assignment.StreamAdderWrapper;
import org.mapstruct.ap.internal.model.assignment.UpdateWrapper;
import org.mapstruct.ap.internal.model.beanmapping.MappingReferences;
import org.mapstruct.ap.internal.model.beanmapping.PropertyEntry;
import org.mapstruct.ap.internal.model.beanmapping.SourceReference;
import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.BuilderType;
import org.mapstruct.ap.internal.model.common.FormattingParameters;
import org.mapstruct.ap.internal.model.common.ModelElement;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.PresenceCheck;
import org.mapstruct.ap.internal.model.common.SourceRHS;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.presence.AllPresenceChecksPresenceCheck;
import org.mapstruct.ap.internal.model.presence.JavaExpressionPresenceCheck;
import org.mapstruct.ap.internal.model.presence.NullPresenceCheck;
import org.mapstruct.ap.internal.model.presence.SuffixPresenceCheck;
import org.mapstruct.ap.internal.model.source.DelegatingOptions;
import org.mapstruct.ap.internal.model.source.MappingControl;
import org.mapstruct.ap.internal.model.source.MappingOptions;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.model.source.SelectionParameters;
import org.mapstruct.ap.internal.model.source.selector.SelectionCriteria;
import org.mapstruct.ap.internal.util.Message;
import org.mapstruct.ap.internal.util.NativeTypes;
import org.mapstruct.ap.internal.util.Strings;
import org.mapstruct.ap.internal.util.accessor.Accessor;
import org.mapstruct.ap.internal.util.accessor.AccessorType;
import org.mapstruct.ap.internal.util.accessor.ReadAccessor;

import static org.mapstruct.ap.internal.gem.NullValueCheckStrategyGem.ALWAYS;
import static org.mapstruct.ap.internal.gem.NullValuePropertyMappingStrategyGem.IGNORE;
import static org.mapstruct.ap.internal.gem.NullValuePropertyMappingStrategyGem.SET_TO_DEFAULT;
import static org.mapstruct.ap.internal.gem.NullValuePropertyMappingStrategyGem.SET_TO_NULL;
import static org.mapstruct.ap.internal.model.ForgedMethod.forElementMapping;
import static org.mapstruct.ap.internal.model.ForgedMethod.forParameterMapping;
import static org.mapstruct.ap.internal.model.ForgedMethod.forPropertyMapping;
import static org.mapstruct.ap.internal.model.common.Assignment.AssignmentType.DIRECT;

/**
 * Represents the mapping between a source and target property, e.g. from {@code String Source#foo} to
 * {@code int Target#bar}. Name and type of source and target property can differ. If they have different types, the
 * mapping must either refer to a mapping method or a conversion.
 *
 * @author Gunnar Morling
 */
public class PropertyMapping extends ModelElement {

    private final String name;
    private final String sourcePropertyName;
    private final String sourceBeanName;
    private final String targetWriteAccessorName;
    private final ReadAccessor targetReadAccessorProvider;
    private final Type targetType;
    private final Assignment assignment;
    private final Set<String> dependsOn;
    private final Assignment defaultValueAssignment;
    private final boolean constructorMapping;

    @SuppressWarnings("unchecked")
    private static class MappingBuilderBase<T extends MappingBuilderBase<T>> extends AbstractBaseBuilder<T> {

        protected Accessor targetWriteAccessor;
        protected AccessorType targetWriteAccessorType;
        protected Type targetType;
        protected BuilderType targetBuilderType;
        protected ReadAccessor targetReadAccessor;
        protected String targetPropertyName;
        protected String sourcePropertyName;

        protected Set<String> dependsOn;
        protected Set<String> existingVariableNames;
        protected AnnotationMirror positionHint;

        MappingBuilderBase(Class<T> selfType) {
            super( selfType );
        }

        public T sourceMethod(Method sourceMethod) {
            return super.method( sourceMethod );
        }

        public T target(String targetPropertyName, ReadAccessor targetReadAccessor, Accessor targetWriteAccessor) {
            this.targetPropertyName = targetPropertyName;
            this.targetReadAccessor = targetReadAccessor;
            this.targetWriteAccessor = targetWriteAccessor;
            this.targetType = ctx.getTypeFactory().getType( targetWriteAccessor.getAccessedType() );
            BuilderGem builder = method.getOptions().getBeanMapping().getBuilder();
            this.targetBuilderType = ctx.getTypeFactory().builderTypeFor( this.targetType, builder );
            this.targetWriteAccessorType = targetWriteAccessor.getAccessorType();
            return (T) this;
        }

        T mirror(AnnotationMirror mirror) {
            this.positionHint = mirror;
            return (T) this;
        }

        public T sourcePropertyName(String sourcePropertyName) {
            this.sourcePropertyName = sourcePropertyName;
            return (T) this;
        }

        public T dependsOn(Set<String> dependsOn) {
            this.dependsOn = dependsOn;
            return (T) this;
        }

        public T existingVariableNames(Set<String> existingVariableNames) {
            this.existingVariableNames = existingVariableNames;
            return (T) this;
        }

        protected boolean isFieldAssignment() {
            return targetWriteAccessorType.isFieldAssignment();
        }
    }

    public static class PropertyMappingBuilder extends MappingBuilderBase<PropertyMappingBuilder> {

        // initial properties
        private String defaultValue;
        private String defaultJavaExpression;
        private String conditionJavaExpression;
        private SourceReference sourceReference;
        private SourceRHS rightHandSide;
        private FormattingParameters formattingParameters;
        private SelectionParameters selectionParameters;
        private MappingControl mappingControl;
        private MappingReferences forgeMethodWithMappingReferences;
        private boolean forceUpdateMethod;
        private boolean forgedNamedBased = true;
        private NullValueCheckStrategyGem nvcs;
        private NullValuePropertyMappingStrategyGem nvpms;

        PropertyMappingBuilder() {
            super( PropertyMappingBuilder.class );
        }

        public PropertyMappingBuilder sourceReference(SourceReference sourceReference) {
            this.sourceReference = sourceReference;
            return this;
        }

        public PropertyMappingBuilder selectionParameters(SelectionParameters selectionParameters) {
            this.selectionParameters = selectionParameters;
            return this;
        }

        public PropertyMappingBuilder formattingParameters(FormattingParameters formattingParameters) {
            this.formattingParameters = formattingParameters;
            return this;
        }

        public PropertyMappingBuilder defaultValue(String defaultValue) {
            this.defaultValue = defaultValue;
            return this;
        }

        public PropertyMappingBuilder defaultJavaExpression(String defaultJavaExpression) {
            this.defaultJavaExpression = defaultJavaExpression;
            return this;
        }

        public PropertyMappingBuilder conditionJavaExpression(String conditionJavaExpression) {
            this.conditionJavaExpression = conditionJavaExpression;
            return this;
        }

        public PropertyMappingBuilder forgeMethodWithMappingReferences(MappingReferences mappingReferences) {
            this.forgeMethodWithMappingReferences = mappingReferences;
            return this;
        }

        /**
         * Force the created mapping to use update methods when forging a method.
         *
         * @param forceUpdateMethod whether the mapping should force update method for forged mappings
         * @return the builder for chaining
         */
        public PropertyMappingBuilder forceUpdateMethod(boolean forceUpdateMethod) {
            this.forceUpdateMethod = forceUpdateMethod;
            return this;
        }

        /**
         * @param forgedNamedBased mapping is based on forging
         *
         * @return the builder for chaining
         */
        public PropertyMappingBuilder forgedNamedBased(boolean forgedNamedBased) {
            this.forgedNamedBased = forgedNamedBased;
            return this;
        }

        public PropertyMappingBuilder options(DelegatingOptions options) {
            this.mappingControl = options.getMappingControl( ctx.getElementUtils() );
            this.nvcs = options.getNullValueCheckStrategy();
            if ( method.isUpdateMethod() ) {
                this.nvpms = options.getNullValuePropertyMappingStrategy();
            }
            return this;
        }

        public PropertyMapping build() {

            // handle source
            this.rightHandSide = getSourceRHS( sourceReference );

            ctx.getMessager().note( 2, Message.PROPERTYMAPPING_MAPPING_NOTE, rightHandSide, targetWriteAccessor );

            rightHandSide.setUseElementAsSourceTypeForMatching(
                targetWriteAccessorType == AccessorType.ADDER );

            // all the tricky cases will be excluded for the time being.
            boolean preferUpdateMethods;
            if ( targetWriteAccessorType == AccessorType.ADDER ) {
                preferUpdateMethods = false;
            }
            else {
                preferUpdateMethods = method.getMappingTargetParameter() != null;
            }

            SelectionCriteria criteria = SelectionCriteria.forMappingMethods(
                selectionParameters,
                mappingControl,
                targetPropertyName,
                preferUpdateMethods
            );

            // forge a method instead of resolving one when there are mapping options.
            Assignment assignment = null;
            if ( forgeMethodWithMappingReferences == null ) {
                assignment = ctx.getMappingResolver().getTargetAssignment(
                    method,
                    getForgedMethodHistory( rightHandSide ),
                    targetType,
                    formattingParameters,
                    criteria,
                    rightHandSide,
                    positionHint,
                    this::forge
                );
            }
            else {
                assignment = forge();
            }

            Type sourceType = rightHandSide.getSourceType();
            if ( assignment != null ) {
                ctx.getMessager().note( 2,  Message.PROPERTYMAPPING_SELECT_NOTE,  assignment );
                if ( targetType.isCollectionOrMapType() ) {
                    assignment = assignToCollection( targetType, targetWriteAccessorType, assignment );
                }
                else if ( targetType.isArrayType() && sourceType.isArrayType() && assignment.getType() == DIRECT ) {
                    assignment = assignToArray( targetType, assignment );
                }
                else {
                    assignment = assignToPlain( targetType, targetWriteAccessorType, assignment );
                }
            }
            else {
                reportCannotCreateMapping();
            }

            return new PropertyMapping(
                sourcePropertyName,
                targetPropertyName,
                rightHandSide.getSourceParameterName(),
                targetWriteAccessor.getSimpleName(),
                targetReadAccessor,
                targetType,
                assignment,
                dependsOn,
                getDefaultValueAssignment( assignment ),
                targetWriteAccessorType == AccessorType.PARAMETER
            );
        }

        private Assignment forge( ) {
            Assignment assignment;
            Type sourceType = rightHandSide.getSourceType();
            if ( ( sourceType.isCollectionType() || sourceType.isArrayType()) && targetType.isIterableType()
                    || ( sourceType.isIterableType() && targetType.isCollectionType() ) ) {
                assignment = forgeIterableMapping( sourceType, targetType, rightHandSide );
            }
            else if ( sourceType.isMapType() && targetType.isMapType() ) {
                assignment = forgeMapMapping( sourceType, targetType, rightHandSide );
            }
            else if ( sourceType.isMapType() && !targetType.isMapType()) {
                assignment = forgeMapping( sourceType, targetType.withoutBounds(), rightHandSide );
            }
            else if ( ( sourceType.isIterableType() && targetType.isStreamType() )
                        || ( sourceType.isStreamType() && targetType.isStreamType() )
                        || ( sourceType.isStreamType() && targetType.isIterableType() ) ) {
                assignment = forgeStreamMapping( sourceType, targetType, rightHandSide );
            }
            else {
                assignment = forgeMapping( rightHandSide );
            }
            if ( assignment != null ) {
                ctx.getMessager().note( 2, Message.PROPERTYMAPPING_CREATE_NOTE, assignment );
            }
            return assignment;
        }

        /**
         * Report that a mapping could not be created.
         */
        private void reportCannotCreateMapping() {
            if ( forgeMethodWithMappingReferences != null && ctx.isErroneous() ) {
                // If we arrived here, there is an error it means that we couldn't forge a mapping method
                // so skip the cannot create mapping
                return;
            }
            if ( method instanceof ForgedMethod && ( (ForgedMethod) method ).getHistory() != null ) {
                // The history that is part of the ForgedMethod misses the information from the current right hand
                // side. Therefore we need to extract the most relevant history and use that in the error reporting.
                ForgedMethodHistory history = getForgedMethodHistory( rightHandSide );
                reportCannotCreateMapping(
                    method,
                    positionHint,
                    history.createSourcePropertyErrorMessage(),
                    history.getSourceType(),
                    history.getTargetType(),
                    history.createTargetPropertyName()
                );
            }
            else {
                reportCannotCreateMapping(
                    method,
                    positionHint,
                    rightHandSide.getSourceErrorMessagePart(),
                    rightHandSide.getSourceType(),
                    targetType,
                    targetPropertyName
                );
            }
        }

        private Assignment getDefaultValueAssignment( Assignment rhs ) {
            if ( defaultValue != null
                &&  ( !rhs.getSourceType().isPrimitive() || rhs.getSourcePresenceCheckerReference() != null) ) {
                // cannot check on null source if source is primitive unless it has a presence checker
                PropertyMapping build = new ConstantMappingBuilder()
                    .constantExpression( defaultValue )
                    .formattingParameters( formattingParameters )
                    .selectionParameters( selectionParameters )
                    .dependsOn( dependsOn )
                    .existingVariableNames( existingVariableNames )
                    .mappingContext( ctx )
                    .sourceMethod( method )
                    .target( targetPropertyName, targetReadAccessor, targetWriteAccessor )
                    .build();
                return build.getAssignment();
            }
            if ( defaultJavaExpression != null
                && ( !rhs.getSourceType().isPrimitive() || rhs.getSourcePresenceCheckerReference() != null) ) {
                // cannot check on null source if source is primitive unless it has a presence checker
                PropertyMapping build = new JavaExpressionMappingBuilder()
                    .javaExpression( defaultJavaExpression )
                    .dependsOn( dependsOn )
                    .existingVariableNames( existingVariableNames )
                    .mappingContext( ctx )
                    .sourceMethod( method )
                    .target( targetPropertyName, targetReadAccessor, targetWriteAccessor )
                    .build();
                return build.getAssignment();
            }
            return null;
        }

        private Assignment assignToPlain(Type targetType, AccessorType targetAccessorType,
                                         Assignment rightHandSide) {

            Assignment result;

            if ( targetAccessorType == AccessorType.SETTER || targetAccessorType.isFieldAssignment() ) {
                result = assignToPlainViaSetter( targetType, rightHandSide );
            }
            else {
                result = assignToPlainViaAdder( rightHandSide );
            }
            return result;

        }

        private Assignment assignToPlainViaSetter(Type targetType, Assignment rhs) {

            if ( rhs.isCallingUpdateMethod() ) {
                if ( targetReadAccessor == null ) {
                    ctx.getMessager().printMessage(
                        method.getExecutable(),
                        positionHint,
                        Message.PROPERTYMAPPING_NO_READ_ACCESSOR_FOR_TARGET_TYPE,
                        targetPropertyName
                    );
                }

                Assignment factory = ObjectFactoryMethodResolver
                    .getFactoryMethod( method, targetType, SelectionParameters.forSourceRHS( rightHandSide ), ctx );

                if ( factory == null && targetBuilderType != null) {
                    // If there is no dedicated factory method and the target has a builder we will try to use that
                    MethodReference builderFactoryMethod = ObjectFactoryMethodResolver.getBuilderFactoryMethod(
                        targetType,
                        targetBuilderType
                    );

                    if ( builderFactoryMethod != null ) {

                        MethodReference finisherMethod = BuilderFinisherMethodResolver.getBuilderFinisherMethod(
                            method,
                            targetBuilderType,
                            ctx
                        );

                        if ( finisherMethod != null ) {
                            factory = MethodReference.forMethodChaining( builderFactoryMethod, finisherMethod );
                        }
                    }

                }
                return new UpdateWrapper(
                    rhs,
                    method.getThrownTypes(),
                    factory,
                    isFieldAssignment(),
                    targetType,
                    !rhs.isSourceReferenceParameter(),
                    nvpms == SET_TO_NULL && !targetType.isPrimitive(),
                    nvpms == SET_TO_DEFAULT
                );
            }
            else {
                // If the property mapping has a default value assignment then we have to do a null value check
                boolean includeSourceNullCheck = setterWrapperNeedsSourceNullCheck( rhs, targetType );
                if ( !includeSourceNullCheck ) {
                    // solution for #834 introduced a local var and null check for nested properties always.
                    // however, a local var is not needed if there's no need to check for null.
                    rhs.setSourceLocalVarName( null );
                }
                return new SetterWrapper(
                    rhs,
                    method.getThrownTypes(),
                    isFieldAssignment(),
                    includeSourceNullCheck,
                    includeSourceNullCheck && nvpms == SET_TO_NULL && !targetType.isPrimitive(),
                    nvpms == SET_TO_DEFAULT );
            }
        }

        /**
         * Checks whether the setter wrapper should include a null / presence check or not
         *
         * @param rhs the source right hand side
         * @param targetType the target type
         *
         * @return whether to include a null / presence check or not
         */
        private boolean setterWrapperNeedsSourceNullCheck(Assignment rhs, Type targetType) {
            if ( rhs.getSourceType().isPrimitive() && rhs.getSourcePresenceCheckerReference() == null ) {
                // If the source type is primitive or it doesn't have a presence checker then
                // we shouldn't do a null check
                return false;
            }

            if ( nvcs == ALWAYS ) {
                // NullValueCheckStrategy is ALWAYS -> do a null check
                return true;
            }

            if ( rhs.getSourcePresenceCheckerReference() != null ) {
                // There is an explicit source presence check method -> do a null / presence check
                return true;
            }

            if ( nvpms == SET_TO_DEFAULT || nvpms == IGNORE ) {
                // NullValuePropertyMapping is SET_TO_DEFAULT or IGNORE -> do a null check
                return true;
            }

            if ( rhs.getType().isConverted() ) {
                // A type conversion is applied, so a null check is required
                return true;
            }

            if ( rhs.getType().isDirect() && targetType.isPrimitive() ) {
                // If the type is direct and the target type is primitive (i.e. we are unboxing) then check is needed
                return true;
            }

            if ( hasDefaultValueOrDefaultExpression() ) {
                // If there is default value defined then a check is needed
                return true;
            }

            return false;
        }

        private boolean hasDefaultValueOrDefaultExpression() {
            return defaultValue != null || defaultJavaExpression != null;
        }

        private Assignment assignToPlainViaAdder( Assignment rightHandSide) {

            Assignment result = rightHandSide;

            String adderIteratorName = sourcePropertyName == null ? targetPropertyName : sourcePropertyName;
            if ( result.getSourceType().isIterableType() ) {
                result = new AdderWrapper( result, method.getThrownTypes(), isFieldAssignment(), adderIteratorName );
            }
            else if ( result.getSourceType().isStreamType() ) {
                result = new StreamAdderWrapper(
                    result, method.getThrownTypes(), isFieldAssignment(), adderIteratorName );
            }
            else {
                // Possibly adding null to a target collection. So should be surrounded by an null check.
                // TODO: what triggers this else branch? Should nvcs, nvpms be applied?
                result = new SetterWrapper( result,
                    method.getThrownTypes(),
                    isFieldAssignment(),
                    true,
                    nvpms == SET_TO_NULL && !targetType.isPrimitive(),
                    nvpms == SET_TO_DEFAULT
                );
            }
            return result;
        }

        private Assignment assignToCollection(Type targetType, AccessorType targetAccessorType,
                                            Assignment rhs) {
            return new CollectionAssignmentBuilder()
                .mappingBuilderContext( ctx )
                .method( method )
                .targetReadAccessor( targetReadAccessor )
                .targetType( targetType )
                .targetPropertyName( targetPropertyName )
                .targetAccessorType( targetAccessorType )
                .rightHandSide( rightHandSide )
                .assignment( rhs )
                .nullValueCheckStrategy( hasDefaultValueOrDefaultExpression() ? ALWAYS : nvcs )
                .nullValuePropertyMappingStrategy( nvpms )
                .build();
        }

        private Assignment assignToArray(Type targetType, Assignment rightHandSide) {

            Type arrayType = ctx.getTypeFactory().getType( Arrays.class );
            //TODO init default value
            return new ArrayCopyWrapper(
                rightHandSide,
                targetPropertyName,
                arrayType,
                targetType,
                isFieldAssignment(),
                nvpms == SET_TO_NULL && !targetType.isPrimitive(),
                nvpms == SET_TO_DEFAULT );
        }

        private SourceRHS getSourceRHS( SourceReference sourceReference ) {
            Parameter sourceParam = sourceReference.getParameter();
            PropertyEntry propertyEntry = sourceReference.getDeepestProperty();

            // parameter reference
            if ( propertyEntry == null ) {
                SourceRHS sourceRHS = new SourceRHS(
                    sourceParam.getName(),
                    sourceParam.getType(),
                    existingVariableNames,
                    sourceReference.toString()
                );
                sourceRHS.setSourcePresenceCheckerReference( getSourcePresenceCheckerRef(
                    sourceReference,
                    sourceRHS
                ) );
                return sourceRHS;
            }
            // simple property
            else if ( !sourceReference.isNested() ) {
                String sourceRef = sourceParam.getName() + "." + propertyEntry.getReadAccessor().getReadValueSource();
                SourceRHS sourceRHS = new SourceRHS(
                    sourceParam.getName(),
                    sourceRef,
                    null,
                    propertyEntry.getType(),
                    existingVariableNames,
                    sourceReference.toString()
                );
                sourceRHS.setSourcePresenceCheckerReference( getSourcePresenceCheckerRef(
                    sourceReference,
                    sourceRHS
                ) );
                return sourceRHS;
            }
            // nested property given as dot path
            else {
                Type sourceType = propertyEntry.getType();
                if ( sourceType.isPrimitive() && !targetType.isPrimitive() ) {
                    // Handle null's. If the forged method needs to be mapped to an object, the forged method must be
                    // able to return null. So in that case primitive types are mapped to their corresponding wrapped
                    // type. The source type becomes the wrapped type in that case.
                    sourceType = ctx.getTypeFactory().getWrappedType( sourceType );
                }

                // forge a method from the parameter type to the last entry type.
                String forgedName = Strings.joinAndCamelize( sourceReference.getElementNames() );
                forgedName = Strings.getSafeVariableName( forgedName, ctx.getReservedNames() );
                Type sourceParameterType = sourceReference.getParameter().getType();
                ForgedMethod methodRef = forParameterMapping( forgedName, sourceParameterType, sourceType, method );

                NestedPropertyMappingMethod.Builder builder = new NestedPropertyMappingMethod.Builder();
                NestedPropertyMappingMethod nestedPropertyMapping = builder
                    .method( methodRef )
                    .propertyEntries( sourceReference.getPropertyEntries() )
                    .mappingContext( ctx )
                    .build();

                // add if not yet existing
                if ( !ctx.getMappingsToGenerate().contains( nestedPropertyMapping ) ) {
                    ctx.getMappingsToGenerate().add( nestedPropertyMapping );
                }
                else {
                    forgedName = ctx.getExistingMappingMethod( nestedPropertyMapping ).getName();
                }
                String sourceRef = forgedName + "( " + sourceParam.getName() + " )";
                SourceRHS sourceRhs = new SourceRHS( sourceParam.getName(),
                                                     sourceRef,
                                                     null,
                                                     sourceType,
                                                     existingVariableNames,
                                                     sourceReference.toString()
                );
                sourceRhs.setSourcePresenceCheckerReference( getSourcePresenceCheckerRef(
                    sourceReference,
                    sourceRhs
                ) );

                // create a local variable to which forged method can be assigned.
                String desiredName = propertyEntry.getName();
                sourceRhs.setSourceLocalVarName( sourceRhs.createUniqueVarName( desiredName ) );

                return sourceRhs;

            }
        }

        private PresenceCheck getSourcePresenceCheckerRef(SourceReference sourceReference,
                                                          SourceRHS sourceRHS) {

            if ( conditionJavaExpression != null ) {
                return new JavaExpressionPresenceCheck( conditionJavaExpression );
            }

            SelectionParameters selectionParameters = this.selectionParameters != null ?
                this.selectionParameters.withSourceRHS( sourceRHS ) :
                SelectionParameters.forSourceRHS( sourceRHS );
            PresenceCheck presenceCheck = PresenceCheckMethodResolver.getPresenceCheck(
                method,
                selectionParameters,
                ctx
            );
            if ( presenceCheck != null ) {
                return presenceCheck;
            }

            PresenceCheck sourcePresenceChecker = null;
            if ( !sourceReference.getPropertyEntries().isEmpty() ) {
                Parameter sourceParam = sourceReference.getParameter();
                // TODO is first correct here?? shouldn't it be last since the remainer is checked
                // in the forged method?
                PropertyEntry propertyEntry = sourceReference.getShallowestProperty();
                if ( propertyEntry.getPresenceChecker() != null ) {
                    List<PresenceCheck> presenceChecks = new ArrayList<>();
                    presenceChecks.add( new SuffixPresenceCheck(
                        sourceParam.getName(),
                        propertyEntry.getPresenceChecker().getPresenceCheckSuffix()
                    ) );

                    String variableName = sourceParam.getName() + "."
                        + propertyEntry.getReadAccessor().getReadValueSource();
                    for (int i = 1; i < sourceReference.getPropertyEntries().size(); i++) {
                        PropertyEntry entry = sourceReference.getPropertyEntries().get( i );
                        if (entry.getPresenceChecker() != null && entry.getReadAccessor() != null) {
                            presenceChecks.add( new NullPresenceCheck( variableName ) );
                            presenceChecks.add( new SuffixPresenceCheck(
                                variableName,
                                entry.getPresenceChecker().getPresenceCheckSuffix()
                            ) );
                            variableName = variableName + "." + entry.getReadAccessor().getSimpleName() + "()";
                        }
                        else {
                            break;
                        }
                    }

                    if ( presenceChecks.size() == 1 ) {
                        sourcePresenceChecker = presenceChecks.get( 0 );
                    }
                    else {
                        sourcePresenceChecker = new AllPresenceChecksPresenceCheck( presenceChecks );
                    }
                }
            }
            return sourcePresenceChecker;
        }

        private Assignment forgeStreamMapping(Type sourceType, Type targetType, SourceRHS source) {

            StreamMappingMethod.Builder builder = new StreamMappingMethod.Builder();
            return forgeWithElementMapping( sourceType, targetType, source, builder );
        }

        private Assignment forgeIterableMapping(Type sourceType, Type targetType, SourceRHS source) {

            IterableMappingMethod.Builder builder = new IterableMappingMethod.Builder();
            return forgeWithElementMapping( sourceType, targetType, source, builder );
        }

        private Assignment forgeWithElementMapping(Type sourceType, Type targetType, SourceRHS source,
            ContainerMappingMethodBuilder<?, ? extends ContainerMappingMethod> builder) {

            targetType = targetType.withoutBounds();
            ForgedMethod methodRef = prepareForgedMethod( sourceType, targetType, source, "[]" );

            Supplier<MappingMethod> mappingMethodCreator = () -> builder
                .mappingContext( ctx )
                .method( methodRef )
                .selectionParameters( selectionParameters )
                .callingContextTargetPropertyName( targetPropertyName )
                .positionHint( positionHint )
                .build();

            return getOrCreateForgedAssignment( source, methodRef, mappingMethodCreator );
        }

        private ForgedMethod prepareForgedMethod(Type sourceType, Type targetType, SourceRHS source, String suffix) {
            String name = getName( sourceType, targetType );
            name = Strings.getSafeVariableName( name, ctx.getReservedNames() );

            // copy mapper configuration from the source method, its the same mapper
            ForgedMethodHistory forgedMethodHistory = getForgedMethodHistory( source, suffix );
            return forElementMapping( name, sourceType, targetType, method, forgedMethodHistory, forgedNamedBased );
        }

        private Assignment forgeMapMapping(Type sourceType, Type targetType, SourceRHS source) {

            targetType = targetType.withoutBounds();
            ForgedMethod methodRef = prepareForgedMethod( sourceType, targetType, source, "{}" );

            MapMappingMethod.Builder builder = new MapMappingMethod.Builder();
            Supplier<MappingMethod> mapMappingMethodCreator = () -> builder
                .mappingContext( ctx )
                .method( methodRef )
                .build();

            return getOrCreateForgedAssignment( source, methodRef, mapMappingMethodCreator );
        }

        private Assignment forgeMapping(SourceRHS sourceRHS) {
            Type sourceType;
            if ( targetWriteAccessorType == AccessorType.ADDER ) {
                sourceType = sourceRHS.getSourceTypeForMatching();
            }
            else {
                 sourceType = sourceRHS.getSourceType();
            }
            if ( forgedNamedBased && !canGenerateAutoSubMappingBetween( sourceType, targetType ) ) {
                return null;
            }

            return forgeMapping( sourceType, targetType, sourceRHS );
        }

        private Assignment forgeMapping(Type sourceType, Type targetType, SourceRHS sourceRHS) {

            //Fail fast. If we could not find the method by now, no need to try
            if ( sourceType.isPrimitive() || targetType.isPrimitive() ) {
                return null;
            }

            String name = getName( sourceType, targetType );
            name = Strings.getSafeVariableName( name, ctx.getReservedNames() );

            List<Parameter> parameters = new ArrayList<>( method.getContextParameters() );
            Type returnType;
            // there's only one case for forging a method with mapping options: nested target properties.
            // They should forge an update method only if we set the forceUpdateMethod. This is set to true,
            // because we are forging a Mapping for a method with multiple source parameters.
            // If the target type is enum, then we can't create an update method
            if ( !targetType.isEnumType() && ( method.isUpdateMethod() || forceUpdateMethod )
                && targetWriteAccessorType != AccessorType.ADDER) {
                parameters.add( Parameter.forForgedMappingTarget( targetType ) );
                returnType = ctx.getTypeFactory().createVoidType();
            }
            else {
                returnType = targetType;
            }
            ForgedMethod forgedMethod = forPropertyMapping( name,
                sourceType,
                returnType,
                parameters,
                method,
                getForgedMethodHistory( sourceRHS ),
                forgeMethodWithMappingReferences,
                forgedNamedBased
            );
            return createForgedAssignment( sourceRHS, targetBuilderType, forgedMethod );
        }

        private ForgedMethodHistory getForgedMethodHistory(SourceRHS sourceRHS) {
            return getForgedMethodHistory( sourceRHS, "" );
        }

        private ForgedMethodHistory getForgedMethodHistory(SourceRHS sourceRHS, String suffix) {
            ForgedMethodHistory history = null;
            if ( method instanceof ForgedMethod ) {
                ForgedMethod method = (ForgedMethod) this.method;
                history = method.getHistory();
            }
            return new ForgedMethodHistory( history, getSourceElementName() + suffix,
                targetPropertyName + suffix, sourceRHS.getSourceType(), targetType, true, "property"
            );
        }

        private String getName(Type sourceType, Type targetType) {
            String fromName = getName( sourceType );
            String toName = getName( targetType );
            return Strings.decapitalize( fromName + "To" + toName );
        }

        private String getName(Type type) {
            StringBuilder builder = new StringBuilder();
            for ( Type typeParam : type.getTypeParameters() ) {
                builder.append( typeParam.getIdentification() );
            }
            builder.append( type.getIdentification() );
            return builder.toString();
        }

        private String getSourceElementName() {
            Parameter sourceParam = sourceReference.getParameter();
            List<PropertyEntry> propertyEntries = sourceReference.getPropertyEntries();
            if ( propertyEntries.isEmpty() ) {
                return sourceParam.getName();
            }
            else if ( propertyEntries.size() == 1 ) {
                PropertyEntry propertyEntry = propertyEntries.get( 0 );
                return propertyEntry.getName();
            }
            else {
                return Strings.join( sourceReference.getElementNames(), "." );
            }
        }
    }

    public static class ConstantMappingBuilder extends MappingBuilderBase<ConstantMappingBuilder> {

        private String constantExpression;
        private FormattingParameters formattingParameters;
        private MappingControl mappingControl;
        private SelectionParameters selectionParameters;

        ConstantMappingBuilder() {
            super( ConstantMappingBuilder.class );
        }

        public ConstantMappingBuilder constantExpression(String constantExpression) {
            this.constantExpression = constantExpression;
            return this;
        }

        public ConstantMappingBuilder formattingParameters(FormattingParameters formattingParameters) {
            this.formattingParameters = formattingParameters;
            return this;
        }

        public ConstantMappingBuilder selectionParameters(SelectionParameters selectionParameters) {
            this.selectionParameters = selectionParameters;
            return this;
        }

        public ConstantMappingBuilder options(MappingOptions options) {
            this.mappingControl = options.getMappingControl( ctx.getElementUtils() );
            return this;
        }

        public PropertyMapping build() {
            // source
            String sourceErrorMessagePart = "constant '" + constantExpression + "'";
            String errorMessageDetails = null;

            Class<?> baseForLiteral = null;
            try {
                baseForLiteral = NativeTypes.getLiteral( targetType.getFullyQualifiedName(), constantExpression );
            }
            catch ( IllegalArgumentException ex ) {
                errorMessageDetails = ex.getMessage();
            }

            //  the constant is not a primitive literal, assume it to be a String
            if ( baseForLiteral == null ) {
                constantExpression = "\"" + constantExpression + "\"";
                baseForLiteral = String.class;
            }
            Type sourceType = ctx.getTypeFactory().getTypeForLiteral( baseForLiteral );

            SelectionCriteria criteria = SelectionCriteria.forMappingMethods( selectionParameters,
                mappingControl,
                targetPropertyName,
                            method.getMappingTargetParameter() != null
            );

            Assignment assignment = null;
            if ( !targetType.isEnumType() ) {
                assignment = ctx.getMappingResolver().getTargetAssignment(
                    method,
                    null, // TODO description for constant
                    targetType,
                    formattingParameters,
                    criteria,
                    new SourceRHS( constantExpression, sourceType, existingVariableNames, sourceErrorMessagePart ),
                    positionHint,
                    () -> null
                );
            }
            else {
                assignment = getEnumAssignment();
            }

            if ( assignment != null ) {

                if ( targetWriteAccessor.getAccessorType() == AccessorType.SETTER  ||
                targetWriteAccessor.getAccessorType().isFieldAssignment() ) {

                    // target accessor is setter, so decorate assignment as setter
                    if ( assignment.isCallingUpdateMethod() ) {
                        if ( targetReadAccessor == null ) {
                            ctx.getMessager().printMessage(
                                method.getExecutable(),
                                positionHint,
                                Message.CONSTANTMAPPING_NO_READ_ACCESSOR_FOR_TARGET_TYPE,
                                targetPropertyName
                            );
                        }

                        Assignment factoryMethod =
                            ObjectFactoryMethodResolver.getFactoryMethod( method, targetType, null, ctx );

                        assignment = new UpdateWrapper(
                            assignment,
                            method.getThrownTypes(),
                            factoryMethod,
                            isFieldAssignment(),
                            targetType,
                            false,
                            false,
                            false );
                    }
                    else {
                        assignment = new SetterWrapper( assignment, method.getThrownTypes(), isFieldAssignment() );
                    }
                }
                else {

                    // target accessor is getter, so getter map/ collection handling
                    assignment = new GetterWrapperForCollectionsAndMaps( assignment,
                                                                         method.getThrownTypes(),
                                                                         targetType,
                                                                         isFieldAssignment()
                                                                       );
                }
            }
            else if ( errorMessageDetails == null ) {
                ctx.getMessager().printMessage(
                    method.getExecutable(),
                    positionHint,
                    Message.CONSTANTMAPPING_MAPPING_NOT_FOUND,
                    constantExpression,
                    targetType.describe(),
                    targetPropertyName
                );
            }
            else {
                ctx.getMessager().printMessage(
                    method.getExecutable(),
                    positionHint,
                    Message.CONSTANTMAPPING_MAPPING_NOT_FOUND_WITH_DETAILS,
                    constantExpression,
                    targetType.describe(),
                    targetPropertyName,
                    errorMessageDetails
                );
            }

            return new PropertyMapping(
                targetPropertyName,
                targetWriteAccessor.getSimpleName(),
                targetReadAccessor,
                targetType,
                assignment,
                dependsOn,
                null,
                targetWriteAccessorType == AccessorType.PARAMETER
            );
        }

        private Assignment getEnumAssignment() {
            Assignment assignment = null;
            // String String quotation marks.
            String enumExpression = constantExpression.substring( 1, constantExpression.length() - 1 );
            if ( targetType.getEnumConstants().contains( enumExpression ) ) {
                String sourceErrorMessagePart = "constant '" + constantExpression + "'";
                assignment = new SourceRHS( enumExpression, targetType, existingVariableNames, sourceErrorMessagePart );
                assignment = new EnumConstantWrapper( assignment, targetType );
            }
            else {
                ctx.getMessager().printMessage(
                    method.getExecutable(),
                    positionHint,
                    Message.CONSTANTMAPPING_NON_EXISTING_CONSTANT,
                    constantExpression,
                    targetType.describe(),
                    targetPropertyName
                );
            }
            return assignment;
        }

    }

    public static class JavaExpressionMappingBuilder extends MappingBuilderBase<JavaExpressionMappingBuilder> {

        private String javaExpression;

        JavaExpressionMappingBuilder() {
            super( JavaExpressionMappingBuilder.class );
        }

        public JavaExpressionMappingBuilder javaExpression(String javaExpression) {
            this.javaExpression = javaExpression;
            return this;
        }

        public PropertyMapping build() {
            Assignment assignment = new SourceRHS( javaExpression, null, existingVariableNames, "" );

            if ( targetWriteAccessor.getAccessorType() == AccessorType.SETTER  ||
                            targetWriteAccessor.getAccessorType().isFieldAssignment() ) {
                // setter, so wrap in setter
                assignment = new SetterWrapper( assignment, method.getThrownTypes(), isFieldAssignment() );
            }
            else {
                // target accessor is getter, so wrap the setter in getter map/ collection handling
                assignment = new GetterWrapperForCollectionsAndMaps( assignment,
                                                                     method.getThrownTypes(),
                                                                     targetType,
                                                                     isFieldAssignment()
                                                                   );
            }

            return new PropertyMapping(
                targetPropertyName,
                targetWriteAccessor.getSimpleName(),
                targetReadAccessor,
                targetType,
                assignment,
                dependsOn,
                null,
                targetWriteAccessorType == AccessorType.PARAMETER
            );
        }

    }

    // Constructor for creating mappings of constant expressions.
    private PropertyMapping(String name, String targetWriteAccessorName,
        ReadAccessor targetReadAccessorProvider,
        Type targetType, Assignment propertyAssignment,
        Set<String> dependsOn, Assignment defaultValueAssignment, boolean constructorMapping) {
        this( name, null, null, targetWriteAccessorName, targetReadAccessorProvider,
            targetType, propertyAssignment, dependsOn, defaultValueAssignment,
            constructorMapping
        );
    }

    private PropertyMapping(String sourcePropertyName, String name, String sourceBeanName,
                            String targetWriteAccessorName, ReadAccessor targetReadAccessorProvider, Type targetType,
                            Assignment assignment,
                            Set<String> dependsOn, Assignment defaultValueAssignment, boolean constructorMapping) {
        this.sourcePropertyName = sourcePropertyName;
        this.name = name;
        this.sourceBeanName = sourceBeanName;
        this.targetWriteAccessorName = targetWriteAccessorName;
        this.targetReadAccessorProvider = targetReadAccessorProvider;
        this.targetType = targetType;

        this.assignment = assignment;
        this.dependsOn = dependsOn != null ? dependsOn : Collections.emptySet();
        this.defaultValueAssignment = defaultValueAssignment;
        this.constructorMapping = constructorMapping;
    }

    /**
     * @return the name of this mapping (property name on the target side)
     */
    public String getName() {
        return name;
    }

    public String getSourcePropertyName() {
        return sourcePropertyName;
    }

    public String getSourceBeanName() {
        return sourceBeanName;
    }

    public String getTargetWriteAccessorName() {
        return targetWriteAccessorName;
    }

    public String getTargetReadAccessorName() {
        return targetReadAccessorProvider == null ? null : targetReadAccessorProvider.getReadValueSource();
    }

    public Type getTargetType() {
        return targetType;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public Assignment getDefaultValueAssignment() {
        return defaultValueAssignment;
    }

    public boolean isConstructorMapping() {
        return constructorMapping;
    }

    @Override
    public Set<Type> getImportTypes() {
        if ( defaultValueAssignment == null ) {
            return assignment.getImportTypes();
        }

        return org.mapstruct.ap.internal.util.Collections.asSet(
            assignment.getImportTypes(),
            defaultValueAssignment.getImportTypes()
        );
    }

    public Set<String> getDependsOn() {
        return dependsOn;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 67 * hash + (this.targetType != null ? this.targetType.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if ( this == obj ) {
            return true;
        }
        if ( obj == null ) {
            return false;
        }
        if ( getClass() != obj.getClass() ) {
            return false;
        }
        final PropertyMapping other = (PropertyMapping) obj;
        if ( !Objects.equals( name, other.name ) ) {
            return false;
        }
        if ( !Objects.equals( targetType, other.targetType ) ) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "PropertyMapping {"
            + "\n    name='" + name + "\',"
            + "\n    targetWriteAccessorName='" + targetWriteAccessorName + "\',"
            + "\n    targetReadAccessorName='" + getTargetReadAccessorName() + "\',"
            + "\n    targetType=" + targetType + ","
            + "\n    propertyAssignment=" + assignment + ","
            + "\n    defaultValueAssignment=" + defaultValueAssignment + ","
            + "\n    dependsOn=" + dependsOn
            + "\n}";
    }
}
