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
package org.mapstruct.ap.internal.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.lang.model.element.AnnotationMirror;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.DeclaredType;

import org.mapstruct.ap.internal.model.assignment.AdderWrapper;
import org.mapstruct.ap.internal.model.assignment.ArrayCopyWrapper;
import org.mapstruct.ap.internal.model.assignment.EnumConstantWrapper;
import org.mapstruct.ap.internal.model.assignment.GetterWrapperForCollectionsAndMaps;
import org.mapstruct.ap.internal.model.assignment.SetterWrapper;
import org.mapstruct.ap.internal.model.assignment.UpdateWrapper;
import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.FormattingParameters;
import org.mapstruct.ap.internal.model.common.ModelElement;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.SourceRHS;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.ForgedMethod;
import org.mapstruct.ap.internal.model.source.ForgedMethodHistory;
import org.mapstruct.ap.internal.model.source.MappingOptions;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.model.source.ParameterProvidedMethods;
import org.mapstruct.ap.internal.model.source.PropertyEntry;
import org.mapstruct.ap.internal.model.source.SelectionParameters;
import org.mapstruct.ap.internal.model.source.SourceReference;
import org.mapstruct.ap.internal.prism.NullValueCheckStrategyPrism;
import org.mapstruct.ap.internal.prism.NullValueMappingStrategyPrism;
import org.mapstruct.ap.internal.util.AccessorNamingUtils;
import org.mapstruct.ap.internal.util.Executables;
import org.mapstruct.ap.internal.util.MapperConfiguration;
import org.mapstruct.ap.internal.util.Message;
import org.mapstruct.ap.internal.util.Strings;
import org.mapstruct.ap.internal.util.ValueProvider;
import org.mapstruct.ap.internal.util.accessor.Accessor;

import static org.mapstruct.ap.internal.model.common.Assignment.AssignmentType.DIRECT;
import static org.mapstruct.ap.internal.prism.NullValueCheckStrategyPrism.ALWAYS;
import static org.mapstruct.ap.internal.util.Collections.first;
import static org.mapstruct.ap.internal.util.Collections.last;
import org.mapstruct.ap.internal.util.NativeTypes;

/**
 * Represents the mapping between a source and target property, e.g. from {@code String Source#foo} to
 * {@code int Target#bar}. Name and type of source and target property can differ. If they have different types, the
 * mapping must either refer to a mapping method or a conversion.
 *
 * @author Gunnar Morling
 */
public class PropertyMapping extends ModelElement {

    private final String name;
    private final String sourceBeanName;
    private final String targetWriteAccessorName;
    private final ValueProvider targetReadAccessorProvider;
    private final Type targetType;
    private final Assignment assignment;
    private final List<String> dependsOn;
    private final Assignment defaultValueAssignment;

    public enum TargetWriteAccessorType {
        FIELD,
        GETTER,
        SETTER,
        ADDER;

        public static TargetWriteAccessorType of(AccessorNamingUtils accessorNaming, Accessor accessor) {
            if ( accessorNaming.isSetterMethod( accessor ) ) {
                return TargetWriteAccessorType.SETTER;
            }
            else if ( accessorNaming.isAdderMethod( accessor ) ) {
                return TargetWriteAccessorType.ADDER;
            }
            else if ( accessorNaming.isGetterMethod( accessor ) ) {
                return TargetWriteAccessorType.GETTER;
            }
            else {
                return TargetWriteAccessorType.FIELD;
            }
        }

        public static boolean isFieldAssignment(TargetWriteAccessorType accessorType) {
            return accessorType == FIELD;
        }
    }

    @SuppressWarnings("unchecked")
    private static class MappingBuilderBase<T extends MappingBuilderBase<T>> extends AbstractBaseBuilder<T> {

        protected Accessor targetWriteAccessor;
        protected TargetWriteAccessorType targetWriteAccessorType;
        protected Type targetType;
        protected Accessor targetReadAccessor;
        protected String targetPropertyName;

        protected List<String> dependsOn;
        protected Set<String> existingVariableNames;
        protected AnnotationMirror mirror;

        MappingBuilderBase(Class<T> selfType) {
            super( selfType );
        }

        public T sourceMethod(Method sourceMethod) {
            return super.method( sourceMethod );
        }

        public T targetProperty(PropertyEntry targetProp) {
            this.targetReadAccessor = targetProp.getReadAccessor();
            this.targetWriteAccessor = targetProp.getWriteAccessor();
            this.targetType = targetProp.getType();
            this.targetWriteAccessorType = TargetWriteAccessorType.of( ctx.getAccessorNaming(), targetWriteAccessor );
            return (T) this;
        }

        public T targetReadAccessor(Accessor targetReadAccessor) {
            this.targetReadAccessor = targetReadAccessor;
            return (T) this;
        }

        public T targetWriteAccessor(Accessor targetWriteAccessor) {
            this.targetWriteAccessor = targetWriteAccessor;
            this.targetWriteAccessorType = TargetWriteAccessorType.of( ctx.getAccessorNaming(), targetWriteAccessor );
            this.targetType = determineTargetType();

            return (T) this;
        }

        T mirror(AnnotationMirror mirror) {
            this.mirror = mirror;
            return (T) this;
        }

        private Type determineTargetType() {
            // This is a bean mapping method, so we know the result is a declared type
            Type mappingType = method.getResultType();
            if ( !method.isUpdateMethod() ) {
                mappingType = mappingType.getEffectiveType();
            }
            DeclaredType resultType = (DeclaredType) mappingType.getTypeMirror();

            switch ( targetWriteAccessorType ) {
                case ADDER:
                case SETTER:
                    return ctx.getTypeFactory()
                        .getSingleParameter( resultType, targetWriteAccessor )
                        .getType();
                case GETTER:
                case FIELD:
                default:
                    return ctx.getTypeFactory()
                        .getReturnType( resultType, targetWriteAccessor );
            }
        }

        public T targetPropertyName(String targetPropertyName) {
            this.targetPropertyName = targetPropertyName;
            return (T) this;
        }

        public T dependsOn(List<String> dependsOn) {
            this.dependsOn = dependsOn;
            return (T) this;
        }

        public T existingVariableNames(Set<String> existingVariableNames) {
            this.existingVariableNames = existingVariableNames;
            return (T) this;
        }

        protected boolean isFieldAssignment() {
            return targetWriteAccessorType == TargetWriteAccessorType.FIELD;
        }
    }

    public static class PropertyMappingBuilder extends MappingBuilderBase<PropertyMappingBuilder> {

        // initial properties
        private String defaultValue;
        private String defaultJavaExpression;
        private SourceReference sourceReference;
        private SourceRHS rightHandSide;
        private FormattingParameters formattingParameters;
        private SelectionParameters selectionParameters;
        private MappingOptions forgeMethodWithMappingOptions;
        private boolean forceUpdateMethod;
        private boolean forgedNamedBased = true;

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

        public PropertyMappingBuilder forgeMethodWithMappingOptions(MappingOptions mappingOptions) {
            this.forgeMethodWithMappingOptions = mappingOptions;
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

        public PropertyMapping build() {
            // handle source
            this.rightHandSide = getSourceRHS( sourceReference );
            rightHandSide.setUseElementAsSourceTypeForMatching(
                targetWriteAccessorType == TargetWriteAccessorType.ADDER );

            // all the tricky cases will be excluded for the time being.
            boolean preferUpdateMethods;
            if ( targetWriteAccessorType == TargetWriteAccessorType.ADDER ) {
                preferUpdateMethods = false;
            }
            else {
                preferUpdateMethods = method.getMappingTargetParameter() != null;
            }

            // forge a method instead of resolving one when there are mapping options.
            Assignment assignment = null;
            if ( forgeMethodWithMappingOptions == null ) {
                assignment = ctx.getMappingResolver().getTargetAssignment(
                    method,
                    targetType,
                    targetPropertyName,
                    formattingParameters,
                    selectionParameters,
                    rightHandSide,
                    preferUpdateMethods
                );
            }

            Type sourceType = rightHandSide.getSourceType();
            // No mapping found. Try to forge a mapping
            if ( assignment == null ) {
                if ( (sourceType.isCollectionType() || sourceType.isArrayType()) && targetType.isIterableType() ) {
                    assignment = forgeIterableMapping( sourceType, targetType, rightHandSide, method.getExecutable() );
                }
                else if ( sourceType.isMapType() && targetType.isMapType() ) {
                    assignment = forgeMapMapping( sourceType, targetType, rightHandSide, method.getExecutable() );
                }
                else if ( ( sourceType.isIterableType() && targetType.isStreamType() ) ||
                    ( sourceType.isStreamType() && targetType.isStreamType() ) ||
                    ( sourceType.isStreamType() && targetType.isIterableType() ) ) {
                    assignment = forgeStreamMapping( sourceType, targetType, rightHandSide, method.getExecutable() );
                }
                else {
                    assignment = forgeMapping( rightHandSide );
                }
            }

            if ( assignment != null ) {
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
                targetPropertyName,
                rightHandSide.getSourceParameterName(),
                targetWriteAccessor.getSimpleName().toString(),
                ValueProvider.of( targetReadAccessor ),
                targetType,
                assignment,
                dependsOn,
                getDefaultValueAssignment( assignment )
            );
        }

        /**
         * Report that a mapping could not be created.
         */
        private void reportCannotCreateMapping() {
            if ( method instanceof ForgedMethod && ( (ForgedMethod) method ).getHistory() != null ) {
                // The history that is part of the ForgedMethod misses the information from the current right hand
                // side. Therefore we need to extract the most relevant history and use that in the error reporting.
                ForgedMethodHistory history = getForgedMethodHistory( rightHandSide );
                reportCannotCreateMapping(
                    method,
                    history.createSourcePropertyErrorMessage(),
                    history.getSourceType(),
                    history.getTargetType(),
                    history.createTargetPropertyName()
                );
            }
            else {
                reportCannotCreateMapping(
                    method,
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
                    .targetPropertyName( targetPropertyName )
                    .targetReadAccessor( targetReadAccessor )
                    .targetWriteAccessor( targetWriteAccessor )
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
                    .targetPropertyName( targetPropertyName )
                    .targetReadAccessor( targetReadAccessor )
                    .targetWriteAccessor( targetWriteAccessor )
                    .build();
                return build.getAssignment();
            }
            return null;
        }

        private Assignment assignToPlain(Type targetType, TargetWriteAccessorType targetAccessorType,
                                         Assignment rightHandSide) {

            Assignment result;

            if ( targetAccessorType == TargetWriteAccessorType.SETTER ||
                targetAccessorType == TargetWriteAccessorType.FIELD ) {
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
                        Message.PROPERTYMAPPING_NO_READ_ACCESSOR_FOR_TARGET_TYPE,
                        targetPropertyName
                    );
                }

                boolean mapNullToDefault = method.getMapperConfiguration().
                    getNullValueMappingStrategy() == NullValueMappingStrategyPrism.RETURN_DEFAULT;

                Assignment factory = ctx.getMappingResolver()
                    .getFactoryMethod( method, targetType, SelectionParameters.forSourceRHS( rightHandSide ) );
                return new UpdateWrapper( rhs, method.getThrownTypes(), factory, isFieldAssignment(),  targetType,
                    !rhs.isSourceReferenceParameter(), mapNullToDefault );
            }
            else {
                NullValueCheckStrategyPrism nvcs = method.getMapperConfiguration().getNullValueCheckStrategy();
                return new SetterWrapper( rhs, method.getThrownTypes(), nvcs, isFieldAssignment(), targetType );
            }
        }

        private Assignment assignToPlainViaAdder( Assignment rightHandSide) {

            Assignment result = rightHandSide;

            if ( result.getSourceType().isCollectionType() ) {
                result = new AdderWrapper( result, method.getThrownTypes(), isFieldAssignment(), targetPropertyName );
            }
            else {
                // Possibly adding null to a target collection. So should be surrounded by an null check.
                result = new SetterWrapper( result, method.getThrownTypes(), ALWAYS, isFieldAssignment(), targetType );
            }
            return result;
        }

        private Assignment assignToCollection(Type targetType, TargetWriteAccessorType targetAccessorType,
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
                .build();
        }

        private Assignment assignToArray(Type targetType, Assignment rightHandSide) {

            Type arrayType = ctx.getTypeFactory().getType( Arrays.class );
            Assignment assignment = new ArrayCopyWrapper(
                rightHandSide,
                targetPropertyName,
                arrayType,
                targetType,
                isFieldAssignment()
            );
            return assignment;
        }

        private SourceRHS getSourceRHS( SourceReference sourceReference ) {
            Parameter sourceParam = sourceReference.getParameter();
            List<PropertyEntry> propertyEntries = sourceReference.getPropertyEntries();

            // parameter reference
            if ( propertyEntries.isEmpty() ) {
                return new SourceRHS( sourceParam.getName(),
                                      sourceParam.getType(),
                                      existingVariableNames,
                                      sourceReference.toString()
                );
            }
            // simple property
            else if ( propertyEntries.size() == 1 ) {
                PropertyEntry propertyEntry = propertyEntries.get( 0 );
                String sourceRef = sourceParam.getName() + "." + ValueProvider.of( propertyEntry.getReadAccessor() );
                return new SourceRHS( sourceParam.getName(),
                                      sourceRef,
                                      getSourcePresenceCheckerRef( sourceReference ),
                                      propertyEntry.getType(),
                                      existingVariableNames,
                                      sourceReference.toString()
                );
            }
            // nested property given as dot path
            else {
                Type sourceType = last( propertyEntries ).getType();
                if ( sourceType.isPrimitive() && !targetType.isPrimitive() ) {
                    // Handle null's. If the forged method needs to be mapped to an object, the forged method must be
                    // able to return null. So in that case primitive types are mapped to their corresponding wrapped
                    // type. The source type becomes the wrapped type in that case.
                    sourceType = ctx.getTypeFactory().getWrappedType( sourceType );
                }

                // copy mapper configuration from the source method, its the same mapper
                MapperConfiguration config = method.getMapperConfiguration();

                // forge a method from the parameter type to the last entry type.
                String forgedName = Strings.joinAndCamelize( sourceReference.getElementNames() );
                forgedName = Strings.getSaveVariableName( forgedName, ctx.getNamesOfMappingsToGenerate() );
                ForgedMethod methodRef = new ForgedMethod(
                    forgedName,
                    sourceReference.getParameter().getType(),
                    sourceType,
                    config,
                    method.getExecutable(),
                    Collections.<Parameter> emptyList(),
                    ParameterProvidedMethods.empty() );

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
                                                     getSourcePresenceCheckerRef( sourceReference ),
                                                     sourceType,
                                                     existingVariableNames,
                                                     sourceReference.toString()
                );

                // create a local variable to which forged method can be assigned.
                String desiredName = last( sourceReference.getPropertyEntries() ).getName();
                sourceRhs.setSourceLocalVarName( sourceRhs.createLocalVarName( desiredName ) );

                return sourceRhs;

            }
        }

        private String getSourcePresenceCheckerRef( SourceReference sourceReference ) {
            String sourcePresenceChecker = null;
            if ( !sourceReference.getPropertyEntries().isEmpty() ) {
                Parameter sourceParam = sourceReference.getParameter();
                // TODO is first correct here?? shouldn't it be last since the remainer is checked
                // in the forged method?
                PropertyEntry propertyEntry = first( sourceReference.getPropertyEntries() );
                if ( propertyEntry.getPresenceChecker() != null ) {
                    sourcePresenceChecker = sourceParam.getName()
                        + "." + propertyEntry.getPresenceChecker().getSimpleName() + "()";
                }
            }
            return sourcePresenceChecker;
        }

        private Assignment forgeStreamMapping(Type sourceType, Type targetType, SourceRHS source,
                                              ExecutableElement element) {

            StreamMappingMethod.Builder builder = new StreamMappingMethod.Builder();
            return forgeWithElementMapping( sourceType, targetType, source, element, builder );
        }

        private Assignment forgeIterableMapping(Type sourceType, Type targetType, SourceRHS source,
                                                ExecutableElement element) {

            IterableMappingMethod.Builder builder = new IterableMappingMethod.Builder();
            return forgeWithElementMapping( sourceType, targetType, source, element, builder );
        }

        private Assignment forgeWithElementMapping(Type sourceType, Type targetType, SourceRHS source,
            ExecutableElement element, ContainerMappingMethodBuilder<?, ? extends ContainerMappingMethod> builder) {

            targetType = targetType.withoutBounds();
            ForgedMethod methodRef = prepareForgedMethod( sourceType, targetType, source, element, "[]" );

            ContainerMappingMethod iterableMappingMethod = builder
                .mappingContext( ctx )
                .method( methodRef )
                .selectionParameters( selectionParameters )
                .callingContextTargetPropertyName( targetPropertyName )
                .build();

            return createForgedAssignment( source, methodRef, iterableMappingMethod );
        }

        private ForgedMethod prepareForgedMethod(Type sourceType, Type targetType, SourceRHS source,
                                                 ExecutableElement element, String suffix) {
            String name = getName( sourceType, targetType );
            name = Strings.getSaveVariableName( name, ctx.getNamesOfMappingsToGenerate() );

            // copy mapper configuration from the source method, its the same mapper
            MapperConfiguration config = method.getMapperConfiguration();
            return new ForgedMethod(
                name,
                sourceType,
                targetType,
                config,
                element,
                method.getContextParameters(),
                method.getContextProvidedMethods(),
                getForgedMethodHistory( source, suffix ),
                null,
                forgedNamedBased
            );
        }

        private Assignment forgeMapMapping(Type sourceType, Type targetType, SourceRHS source,
                                           ExecutableElement element) {

            targetType = targetType.withoutBounds();
            ForgedMethod methodRef = prepareForgedMethod( sourceType, targetType, source, element, "{}" );

            MapMappingMethod.Builder builder = new MapMappingMethod.Builder();
            MapMappingMethod mapMappingMethod = builder
                .mappingContext( ctx )
                .method( methodRef )
                .build();

            return createForgedAssignment( source, methodRef, mapMappingMethod );
        }

        private Assignment forgeMapping(SourceRHS sourceRHS) {
            Type sourceType = sourceRHS.getSourceType();
            if ( forgedNamedBased && !canGenerateAutoSubMappingBetween( sourceType, targetType ) ) {
                return null;
            }


            //Fail fast. If we could not find the method by now, no need to try
            if ( sourceType.isPrimitive() || targetType.isPrimitive() ) {
                return null;
            }

            String name = getName( sourceType, targetType );
            name = Strings.getSaveVariableName( name, ctx.getNamesOfMappingsToGenerate() );

            List<Parameter> parameters = new ArrayList<Parameter>( method.getContextParameters() );
            Type returnType;
            // there's only one case for forging a method with mapping options: nested target properties.
            // They should forge an update method only if we set the forceUpdateMethod. This is set to true,
            // because we are forging a Mapping for a method with multiple source parameters.
            // If the target type is enum, then we can't create an update method
            if ( !targetType.isEnumType() && ( method.isUpdateMethod() || forceUpdateMethod ) ) {
                parameters.add( Parameter.forForgedMappingTarget( targetType ) );
                returnType = ctx.getTypeFactory().createVoidType();
            }
            else {
                returnType = targetType;
            }
            ForgedMethod forgedMethod = new ForgedMethod(
                name,
                sourceType,
                returnType,
                method.getMapperConfiguration(),
                method.getExecutable(),
                parameters,
                method.getContextProvidedMethods(),
                getForgedMethodHistory( sourceRHS ),
                forgeMethodWithMappingOptions,
                forgedNamedBased
            );
            return createForgedAssignment( sourceRHS, forgedMethod );
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

            Assignment assignment = null;
            if ( !targetType.isEnumType() ) {
                assignment = ctx.getMappingResolver().getTargetAssignment(
                    method,
                    targetType,
                    targetPropertyName,
                    formattingParameters,
                    selectionParameters,
                    new SourceRHS( constantExpression, sourceType, existingVariableNames, sourceErrorMessagePart ),
                    method.getMappingTargetParameter() != null
                );
            }
            else {
                assignment = getEnumAssignment();
            }

            if ( assignment != null ) {

                if ( ctx.getAccessorNaming().isSetterMethod( targetWriteAccessor ) ||
                    Executables.isFieldAccessor( targetWriteAccessor ) ) {

                    // target accessor is setter, so decorate assignment as setter
                    if ( assignment.isCallingUpdateMethod() ) {
                        if ( targetReadAccessor == null ) {
                            ctx.getMessager().printMessage(
                                method.getExecutable(),
                                Message.CONSTANTMAPPING_NO_READ_ACCESSOR_FOR_TARGET_TYPE,
                                targetPropertyName
                            );
                        }

                        boolean mapNullToDefault = method.getMapperConfiguration().
                            getNullValueMappingStrategy() == NullValueMappingStrategyPrism.RETURN_DEFAULT;

                        Assignment factoryMethod =
                            ctx.getMappingResolver().getFactoryMethod( method, targetType, null );

                        assignment = new UpdateWrapper( assignment, method.getThrownTypes(), factoryMethod,
                            isFieldAssignment(), targetType, false, mapNullToDefault );
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
                    mirror,
                    Message.CONSTANTMAPPING_MAPPING_NOT_FOUND,
                    sourceType,
                    constantExpression,
                    targetType,
                    targetPropertyName
                );
            }
            else {
                ctx.getMessager().printMessage(
                    method.getExecutable(),
                    mirror,
                    Message.CONSTANTMAPPING_MAPPING_NOT_FOUND_WITH_DETAILS,
                    sourceType,
                    constantExpression,
                    targetType,
                    targetPropertyName,
                    errorMessageDetails
                );
            }

            return new PropertyMapping(
                targetPropertyName,
                targetWriteAccessor.getSimpleName().toString(),
                ValueProvider.of( targetReadAccessor ),
                targetType,
                assignment,
                dependsOn,
                null
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
                    Message.CONSTANTMAPPING_NON_EXISTING_CONSTANT,
                    constantExpression,
                    targetType,
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

            if ( ctx.getAccessorNaming().isSetterMethod( targetWriteAccessor ) ||
                Executables.isFieldAccessor( targetWriteAccessor ) ) {
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
                targetWriteAccessor.getSimpleName().toString(),
                ValueProvider.of( targetReadAccessor ),
                targetType,
                assignment,
                dependsOn,
                null
            );
        }

    }

    // Constructor for creating mappings of constant expressions.
    private PropertyMapping(String name, String targetWriteAccessorName,
                            ValueProvider targetReadAccessorProvider,
                            Type targetType, Assignment propertyAssignment,
                            List<String> dependsOn, Assignment defaultValueAssignment ) {
        this( name, null, targetWriteAccessorName, targetReadAccessorProvider,
            targetType, propertyAssignment, dependsOn, defaultValueAssignment
        );
    }

    private PropertyMapping(String name, String sourceBeanName, String targetWriteAccessorName,
                            ValueProvider targetReadAccessorProvider, Type targetType,
                            Assignment assignment,
        List<String> dependsOn, Assignment defaultValueAssignment) {
        this.name = name;
        this.sourceBeanName = sourceBeanName;
        this.targetWriteAccessorName = targetWriteAccessorName;
        this.targetReadAccessorProvider = targetReadAccessorProvider;
        this.targetType = targetType;

        this.assignment = assignment;
        this.dependsOn = dependsOn != null ? dependsOn : Collections.<String>emptyList();
        this.defaultValueAssignment = defaultValueAssignment;
    }

    /**
     * @return the name of this mapping (property name on the target side)
     */
    public String getName() {
        return name;
    }

    public String getSourceBeanName() {
        return sourceBeanName;
    }

    public String getTargetWriteAccessorName() {
        return targetWriteAccessorName;
    }

    public String getTargetReadAccessorName() {
        return targetReadAccessorProvider == null ? null : targetReadAccessorProvider.getValue();
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

    @Override
    @SuppressWarnings("unchecked")
    public Set<Type> getImportTypes() {
        if ( defaultValueAssignment == null ) {
            return assignment.getImportTypes();
        }

        return org.mapstruct.ap.internal.util.Collections.asSet(
            assignment.getImportTypes(),
            defaultValueAssignment.getImportTypes()
        );
    }

    public List<String> getDependsOn() {
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
        if ( (this.name == null) ? (other.name != null) : !this.name.equals( other.name ) ) {
            return false;
        }
        if ( this.targetType != other.targetType && (this.targetType == null ||
            !this.targetType.equals( other.targetType )) ) {
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
