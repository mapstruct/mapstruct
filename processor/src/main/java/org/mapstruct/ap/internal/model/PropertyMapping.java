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
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.DeclaredType;

import org.mapstruct.ap.internal.model.assignment.AdderWrapper;
import org.mapstruct.ap.internal.model.assignment.ArrayCopyWrapper;
import org.mapstruct.ap.internal.model.assignment.Assignment;
import org.mapstruct.ap.internal.model.assignment.EnumConstantWrapper;
import org.mapstruct.ap.internal.model.assignment.GetterWrapperForCollectionsAndMaps;
import org.mapstruct.ap.internal.model.assignment.SetterWrapper;
import org.mapstruct.ap.internal.model.assignment.SetterWrapperForCollectionsAndMaps;
import org.mapstruct.ap.internal.model.assignment.UpdateWrapper;
import org.mapstruct.ap.internal.model.common.ModelElement;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.ParameterBinding;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.ForgedMethod;
import org.mapstruct.ap.internal.model.source.ForgedMethodHistory;
import org.mapstruct.ap.internal.model.source.FormattingParameters;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.model.source.PropertyEntry;
import org.mapstruct.ap.internal.model.source.SelectionParameters;
import org.mapstruct.ap.internal.model.source.SourceReference;
import org.mapstruct.ap.internal.prism.NullValueCheckStrategyPrism;
import org.mapstruct.ap.internal.util.Executables;
import org.mapstruct.ap.internal.util.MapperConfiguration;
import org.mapstruct.ap.internal.util.Message;
import org.mapstruct.ap.internal.util.Strings;
import org.mapstruct.ap.internal.util.ValueProvider;
import org.mapstruct.ap.internal.util.accessor.Accessor;

import static org.mapstruct.ap.internal.model.assignment.Assignment.AssignmentType.DIRECT;
import static org.mapstruct.ap.internal.prism.NullValueCheckStrategyPrism.ALWAYS;
import static org.mapstruct.ap.internal.util.Collections.first;
import static org.mapstruct.ap.internal.util.Collections.last;

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
    private final String localTargetVarName;
    private final Type targetType;
    private final Assignment assignment;
    private final List<String> dependsOn;
    private final Assignment defaultValueAssignment;
    private List<ForgedMethod> forgedMethods = new ArrayList<ForgedMethod>();

    private enum TargetWriteAccessorType {
        FIELD,
        GETTER,
        SETTER,
        ADDER;

        public static TargetWriteAccessorType of(Accessor accessor) {
            if ( Executables.isSetterMethod( accessor ) ) {
                return TargetWriteAccessorType.SETTER;
            }
            else if ( Executables.isAdderMethod( accessor ) ) {
                return TargetWriteAccessorType.ADDER;
            }
            else if ( Executables.isGetterMethod( accessor ) ) {
                return TargetWriteAccessorType.GETTER;
            }
            else {
                return TargetWriteAccessorType.FIELD;
            }
        }
    }

    @SuppressWarnings("unchecked")
    private static class MappingBuilderBase<T extends MappingBuilderBase<T>> {

        protected MappingBuilderContext ctx;
        protected Method method;

        protected Accessor targetWriteAccessor;
        protected TargetWriteAccessorType targetWriteAccessorType;
        protected Type targetType;
        protected Accessor targetReadAccessor;
        protected TargetWriteAccessorType targetReadAccessorType;
        protected String targetPropertyName;
        protected String localTargetVarName;

        protected List<String> dependsOn;
        protected Set<String> existingVariableNames;

        public T mappingContext(MappingBuilderContext mappingContext) {
            this.ctx = mappingContext;
            return (T) this;
        }

        public T sourceMethod(Method sourceMethod) {
            this.method = sourceMethod;
            return (T) this;
        }

        public T targetProperty(PropertyEntry targetProp) {
            this.targetReadAccessor = targetProp.getReadAccessor();
            this.targetWriteAccessor = targetProp.getWriteAccessor();
            this.targetType = targetProp.getType();
            this.targetWriteAccessorType = TargetWriteAccessorType.of( targetWriteAccessor );
            if ( targetReadAccessor != null ) {
                this.targetReadAccessorType = TargetWriteAccessorType.of( targetReadAccessor );
            }
            return (T) this;
        }

        public T targetReadAccessor(Accessor targetReadAccessor) {
            this.targetReadAccessor = targetReadAccessor;
            if ( targetReadAccessor != null ) {
                this.targetReadAccessorType = TargetWriteAccessorType.of( targetReadAccessor );
            }
            return (T) this;
        }

        public T targetWriteAccessor(Accessor targetWriteAccessor) {
            this.targetWriteAccessor = targetWriteAccessor;
            this.targetWriteAccessorType = TargetWriteAccessorType.of( targetWriteAccessor );
            this.targetType = determineTargetType();

            return (T) this;
        }

        public T localTargetVarName(String localTargetVarName) {
            this.localTargetVarName = localTargetVarName;
            return (T) this;
        }

        private Type determineTargetType() {
            // This is a bean mapping method, so we know the result is a declared type
            DeclaredType resultType = (DeclaredType) method.getResultType().getTypeMirror();

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
        private SourceReference sourceReference;
        private SourceRHS rightHandSide;
        private FormattingParameters formattingParameters;
        private SelectionParameters selectionParameters;
        private List<ForgedMethod> forgedMethods = new ArrayList<ForgedMethod>();

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

            Assignment assignment = ctx.getMappingResolver().getTargetAssignment(
                method,
                targetType,
                targetPropertyName,
                formattingParameters,
                selectionParameters,
                rightHandSide,
                preferUpdateMethods
            );

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
                    assignment = assignToPlain( sourceType, targetType, targetWriteAccessorType, assignment );
                }
            }
            else {
                ctx.getMessager().printMessage(
                    method.getExecutable(),
                    Message.PROPERTYMAPPING_MAPPING_NOT_FOUND,
                    rightHandSide.getSourceErrorMessagePart(),
                    targetType,
                    targetPropertyName,
                    targetType,
                    rightHandSide.getSourceType() /* original source type */
                );
            }

            return new PropertyMapping(
                targetPropertyName,
                rightHandSide.getSourceParameterName(),
                targetWriteAccessor.getSimpleName().toString(),
                ValueProvider.of( targetReadAccessor ),
                targetType,
                localTargetVarName,
                assignment,
                dependsOn,
                getDefaultValueAssignment( assignment ),
                forgedMethods
            );
        }

        private Assignment getDefaultValueAssignment( Assignment rhs ) {
            if ( defaultValue != null
                &&  ( !rhs.getSourceType().isPrimitive() || rhs.getSourcePresenceCheckerReference() != null) ) {
                // cannot check on null source if source is primitive unless it has a presenche checker
                PropertyMapping build = new ConstantMappingBuilder()
                    .constantExpression( '"' + defaultValue + '"' )
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
            return null;
        }

        private Assignment assignToPlain(Type sourceType, Type targetType, TargetWriteAccessorType targetAccessorType,
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
                Assignment factory = ctx.getMappingResolver().getFactoryMethod( method, targetType, null );
                return new UpdateWrapper( rhs, method.getThrownTypes(), factory, isFieldAssignment(),  targetType,
                    true );
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

            Assignment result = rhs;

            if ( targetAccessorType == TargetWriteAccessorType.SETTER ||
                targetAccessorType == TargetWriteAccessorType.FIELD ) {

                if ( result.isCallingUpdateMethod() ) {
                    // call to an update method
                    if ( targetReadAccessor == null ) {
                        ctx.getMessager().printMessage(
                            method.getExecutable(),
                            Message.PROPERTYMAPPING_NO_READ_ACCESSOR_FOR_TARGET_TYPE,
                            targetPropertyName
                        );
                    }
                    Assignment factoryMethod = ctx.getMappingResolver().getFactoryMethod( method, targetType, null );
                    result = new UpdateWrapper(
                        result,
                        method.getThrownTypes(),
                        factoryMethod,
                        isFieldAssignment(),
                        targetType,
                        true
                    );
                }
                else {
                    // target accessor is setter, so wrap the setter in setter map/ collection handling
                    result = new SetterWrapperForCollectionsAndMaps(
                        result,
                        method.getThrownTypes(),
                        targetType,
                        method.getMapperConfiguration().getNullValueCheckStrategy(),
                        ctx.getTypeFactory(),
                        targetWriteAccessorType == TargetWriteAccessorType.FIELD
                    );
                }
            }
            else {
                // target accessor is getter, so wrap the setter in getter map/ collection handling
                result = new GetterWrapperForCollectionsAndMaps( result,
                                                                 method.getThrownTypes(),
                                                                 targetType,
                                                                 isFieldAssignment()
                                                               );
            }

            return result;
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
                    method.getContextParameters() );

                NestedPropertyMappingMethod.Builder builder = new NestedPropertyMappingMethod.Builder();
                NestedPropertyMappingMethod nestedPropertyMapping = builder
                    .method( methodRef )
                    .propertyEntries( sourceReference.getPropertyEntries() )
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

            ForgedMethod methodRef = prepareForgedMethod( sourceType, targetType, source, element );

            ContainerMappingMethod iterableMappingMethod = builder
                .mappingContext( ctx )
                .method( methodRef )
                .selectionParameters( selectionParameters )
                .callingContextTargetPropertyName( targetPropertyName )
                .build();

            return getForgedAssignment( source, methodRef, iterableMappingMethod );
        }

        private Assignment getForgedAssignment(SourceRHS source, ForgedMethod methodRef,
                                               MappingMethod mappingMethod) {
            Assignment assignment = null;
            if ( mappingMethod != null ) {
                if ( !ctx.getMappingsToGenerate().contains( mappingMethod ) ) {
                    ctx.getMappingsToGenerate().add( mappingMethod );
                }
                else {
                    String existingName = ctx.getExistingMappingMethod( mappingMethod ).getName();
                    methodRef = new ForgedMethod( existingName, methodRef );
                }

                assignment = new MethodReference(
                    methodRef,
                    null,
                    ParameterBinding.fromParameters( methodRef.getParameters() )
                );
                assignment.setAssignment( source );
                forgedMethods.addAll( mappingMethod.getForgedMethods() );
            }

            return assignment;
        }

        private ForgedMethod prepareForgedMethod(Type sourceType, Type targetType, SourceRHS source,
                                                 ExecutableElement element) {
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
                new ForgedMethodHistory( getForgedMethodHistory( source ),
                    source.getSourceErrorMessagePart(),
                    targetPropertyName,
                    source.getSourceType(),
                    targetType
                )
            );
        }

        private Assignment forgeMapMapping(Type sourceType, Type targetType, SourceRHS source,
                                           ExecutableElement element) {

            ForgedMethod methodRef = prepareForgedMethod( sourceType, targetType, source, element );

            MapMappingMethod.Builder builder = new MapMappingMethod.Builder();
            MapMappingMethod mapMappingMethod = builder
                .mappingContext( ctx )
                .method( methodRef )
                .build();

            return getForgedAssignment( source, methodRef, mapMappingMethod );
        }

        private Assignment forgeMapping(SourceRHS sourceRHS) {

            Type sourceType = sourceRHS.getSourceType();

            //Fail fast. If we could not find the method by now, no need to try
            if ( sourceType.isPrimitive() || targetType.isPrimitive() ) {
                return null;
            }
            String name = getName( sourceType, targetType );
            ForgedMethod forgedMethod = new ForgedMethod(
                name,
                sourceType,
                targetType,
                method.getMapperConfiguration(),
                method.getExecutable(),
                method.getContextParameters(),
                getForgedMethodHistory( sourceRHS )
            );

            Assignment assignment = new MethodReference(
                forgedMethod,
                null,
                ParameterBinding.fromParameters( forgedMethod.getParameters() ) );

            assignment.setAssignment( sourceRHS );

            this.forgedMethods.add( forgedMethod );

            return assignment;
        }

        private ForgedMethodHistory getForgedMethodHistory(SourceRHS sourceRHS) {
            ForgedMethodHistory history = null;
            if ( method instanceof ForgedMethod ) {
                ForgedMethod method = (ForgedMethod) this.method;
                history = method.getHistory();
            }
            return new ForgedMethodHistory( history, sourceRHS.getSourceErrorMessagePart(),
                targetPropertyName, sourceRHS.getSourceType(), targetType
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

    }

    public static class ConstantMappingBuilder extends MappingBuilderBase<ConstantMappingBuilder> {

        private String constantExpression;
        private FormattingParameters formattingParameters;
        private SelectionParameters selectionParameters;

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
            Type sourceType = ctx.getTypeFactory().getType( String.class );

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

                if ( Executables.isSetterMethod( targetWriteAccessor ) ||
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
                        Assignment factoryMethod =
                            ctx.getMappingResolver().getFactoryMethod( method, targetType, null );
                        assignment = new UpdateWrapper( assignment, method.getThrownTypes(), factoryMethod,
                            isFieldAssignment(), targetType, false );
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
            else {
                ctx.getMessager().printMessage(
                    method.getExecutable(),
                    Message.CONSTANTMAPPING_MAPPING_NOT_FOUND,
                    sourceType,
                    constantExpression,
                    targetType,
                    targetPropertyName
                );
            }

            return new PropertyMapping(
                targetPropertyName,
                targetWriteAccessor.getSimpleName().toString(),
                ValueProvider.of( targetReadAccessor ),
                targetType,
                localTargetVarName,
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

        public JavaExpressionMappingBuilder javaExpression(String javaExpression) {
            this.javaExpression = javaExpression;
            return this;
        }

        public PropertyMapping build() {
            Assignment assignment = new SourceRHS( javaExpression, null, existingVariableNames, "" );

            if ( Executables.isSetterMethod( targetWriteAccessor ) ||
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
                localTargetVarName,
                assignment,
                dependsOn,
                null
            );
        }

    }

    // Constructor for creating mappings of constant expressions.
    private PropertyMapping(String name, String targetWriteAccessorName,
                            ValueProvider targetReadAccessorProvider,
                            Type targetType, String localTargetVarName, Assignment propertyAssignment,
                            List<String> dependsOn, Assignment defaultValueAssignment ) {
        this( name, null, targetWriteAccessorName, targetReadAccessorProvider,
                        targetType, localTargetVarName, propertyAssignment, dependsOn, defaultValueAssignment,
            Collections.<ForgedMethod>emptyList() );
    }

    private PropertyMapping(String name, String sourceBeanName, String targetWriteAccessorName,
                            ValueProvider targetReadAccessorProvider, Type targetType, String localTargetVarName,
                            Assignment assignment,
                            List<String> dependsOn, Assignment defaultValueAssignment,
                            List<ForgedMethod> forgedMethods) {
        this.name = name;
        this.sourceBeanName = sourceBeanName;
        this.targetWriteAccessorName = targetWriteAccessorName;
        this.targetReadAccessorProvider = targetReadAccessorProvider;
        this.targetType = targetType;
        this.localTargetVarName = localTargetVarName;

        this.assignment = assignment;
        this.dependsOn = dependsOn != null ? dependsOn : Collections.<String>emptyList();
        this.defaultValueAssignment = defaultValueAssignment;
        this.forgedMethods = forgedMethods;
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

    public String getLocalTargetVarName() {
        return localTargetVarName;
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

    public List<ForgedMethod> getForgedMethods() {
        return forgedMethods;
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
