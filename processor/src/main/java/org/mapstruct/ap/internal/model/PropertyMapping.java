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
package org.mapstruct.ap.internal.model;

import static org.mapstruct.ap.internal.model.assignment.Assignment.AssignmentType.DIRECT;
import static org.mapstruct.ap.internal.model.assignment.Assignment.AssignmentType.MAPPED_TYPE_CONVERTED;
import static org.mapstruct.ap.internal.model.assignment.Assignment.AssignmentType.TYPE_CONVERTED;
import static org.mapstruct.ap.internal.model.assignment.Assignment.AssignmentType.TYPE_CONVERTED_MAPPED;

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
import org.mapstruct.ap.internal.model.assignment.EnumSetCopyWrapper;
import org.mapstruct.ap.internal.model.assignment.GetterWrapperForCollectionsAndMaps;
import org.mapstruct.ap.internal.model.assignment.NewCollectionOrMapWrapper;
import org.mapstruct.ap.internal.model.assignment.NullCheckWrapper;
import org.mapstruct.ap.internal.model.assignment.SetterWrapper;
import org.mapstruct.ap.internal.model.assignment.SetterWrapperForCollectionsAndMaps;
import org.mapstruct.ap.internal.model.assignment.UpdateNullCheckWrapper;
import org.mapstruct.ap.internal.model.assignment.UpdateWrapper;
import org.mapstruct.ap.internal.model.common.ModelElement;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.ForgedMethod;
import org.mapstruct.ap.internal.model.source.FormattingParameters;
import org.mapstruct.ap.internal.model.source.SelectionParameters;
import org.mapstruct.ap.internal.model.source.SourceMethod;
import org.mapstruct.ap.internal.model.source.PropertyEntry;
import org.mapstruct.ap.internal.model.source.SourceReference;
import static org.mapstruct.ap.internal.util.Collections.first;
import static org.mapstruct.ap.internal.util.Collections.last;
import org.mapstruct.ap.internal.util.Executables;
import org.mapstruct.ap.internal.util.MapperConfiguration;
import org.mapstruct.ap.internal.util.Message;
import org.mapstruct.ap.internal.util.Strings;
import static org.mapstruct.ap.internal.prism.NullValueCheckStrategyPrism.ALWAYS;

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
    private final String targetReadAccessorName;
    private final String localTargetVarName;
    private final Type targetType;
    private final Assignment assignment;
    private final List<String> dependsOn;
    private final Assignment defaultValueAssignment;

    private enum TargetWriteAccessorType {
        GETTER,
        SETTER,
        ADDER;

        public static TargetWriteAccessorType of(ExecutableElement accessor) {
            if ( Executables.isSetterMethod( accessor ) ) {
                return TargetWriteAccessorType.SETTER;
            }
            else if ( Executables.isAdderMethod( accessor ) ) {
                return TargetWriteAccessorType.ADDER;
            }
            else {
                return TargetWriteAccessorType.GETTER;
            }
        }
    }

    @SuppressWarnings("unchecked")
    private static class MappingBuilderBase<T extends MappingBuilderBase<T>> {

        protected MappingBuilderContext ctx;
        protected SourceMethod method;

        protected ExecutableElement targetWriteAccessor;
        protected TargetWriteAccessorType targetWriteAccessorType;
        protected Type targetType;
        protected ExecutableElement targetReadAccessor;
        protected String targetPropertyName;
        protected String localTargetVarName;

        protected List<String> dependsOn;
        protected Set<String> existingVariableNames;

        public T mappingContext(MappingBuilderContext mappingContext) {
            this.ctx = mappingContext;
            return (T) this;
        }

        public T sourceMethod(SourceMethod sourceMethod) {
            this.method = sourceMethod;
            return (T) this;
        }

        public T targetProperty( PropertyEntry targetProp ) {
            this.targetReadAccessor = targetProp.getReadAccessor();
            this.targetWriteAccessor = targetProp.getWriteAccessor();
            this.targetType = targetProp.getType();
            this.targetWriteAccessorType = TargetWriteAccessorType.of( targetWriteAccessor );
            return (T) this;
        }

        public T targetReadAccessor(ExecutableElement targetReadAccessor) {
            this.targetReadAccessor = targetReadAccessor;
            return (T) this;
        }

        public T targetWriteAccessor(ExecutableElement targetWriteAccessor) {
            this.targetWriteAccessor = targetWriteAccessor;
            this.targetWriteAccessorType = TargetWriteAccessorType.of( targetWriteAccessor );
            this.targetType = determineTargetType();

            return (T) this;
        }

        public T localTargetVarName( String localTargetVarName ) {
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
    }

    public static class PropertyMappingBuilder extends MappingBuilderBase<PropertyMappingBuilder> {

        // initial properties
        private String defaultValue;
        private SourceReference sourceReference;
        private FormattingParameters formattingParameters;
        private SelectionParameters selectionParameters;

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
            String sourceElement = getSourceElement();
            Type sourceType = getSourceType();
            SourceRHS sourceRHS;
            if ( targetWriteAccessorType == TargetWriteAccessorType.ADDER && sourceType.isCollectionType() ) {
                // handle adder, if source is collection then use iterator element type as source type.
                // sourceRef becomes a local variable in the itereation.
                sourceType = sourceType.getTypeParameters().get( 0 );
                sourceRHS = new SourceRHS( Executables.getElementNameForAdder( targetWriteAccessor ), sourceType );
            }
            else {
                sourceRHS = getSourceRHS();
            }

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
                sourceElement,
                sourceType,
                targetType,
                targetPropertyName,
                formattingParameters,
                selectionParameters,
                sourceRHS,
                preferUpdateMethods
            );

            // No mapping found. Try to forge a mapping
            if ( assignment == null ) {
                if ( (sourceType.isCollectionType() || sourceType.isArrayType()) && targetType.isIterableType() ) {
                    assignment = forgeIterableMapping( sourceType, targetType, sourceRHS, method.getExecutable() );
                }
                else if ( sourceType.isMapType() && targetType.isMapType() ) {
                    assignment = forgeMapMapping( sourceType, targetType, sourceRHS, method.getExecutable() );
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
                    sourceElement,
                    targetType,
                    targetPropertyName,
                    targetType,
                    getSourceType() /* original source type */
                );
            }

            return new PropertyMapping(
                targetPropertyName,
                sourceReference.getParameter().getName(),
                targetWriteAccessor.getSimpleName().toString(),
                targetReadAccessor != null ? targetReadAccessor.getSimpleName().toString() : null,
                targetType,
                localTargetVarName,
                assignment,
                dependsOn,
                getDefaultValueAssignment()
            );
        }

        private Assignment getDefaultValueAssignment() {
            if ( defaultValue != null
                &&  ( !getSourceType().isPrimitive()  || getSourcePresenceCheckerRef() != null) ) {
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

            if ( targetAccessorType == TargetWriteAccessorType.SETTER ) {
                result = assignToPlainViaSetter( sourceType, targetType, rightHandSide );
            }
            else {
                result = assignToPlainViaAdder( sourceType, rightHandSide );
            }
            return result;

        }

        private Assignment assignToPlainViaSetter(Type sourceType, Type targetType, Assignment rightHandSide) {

            Assignment result;

            if ( rightHandSide.isUpdateMethod() ) {
                if ( targetReadAccessor == null ) {
                    ctx.getMessager().printMessage( method.getExecutable(),
                        Message.PROPERTYMAPPING_NO_READ_ACCESSOR_FOR_TARGET_TYPE,
                        targetPropertyName );
                }
                Assignment factoryMethod = ctx.getMappingResolver().getFactoryMethod( method, targetType, null );
                result = new UpdateWrapper( rightHandSide, method.getThrownTypes(), factoryMethod,
                    targetType );
            }
            else {
                result = new SetterWrapper( rightHandSide, method.getThrownTypes() );
            }

            // if the sourceReference is the bean mapping method parameter itself, no null check is required
            // since this is handled by the BeanMapping
            if ( sourceReference.getPropertyEntries().isEmpty() ) {
                return result;
            }

            // add a null / presence checked when required
            if ( sourceType.isPrimitive() ) {
                if ( getSourcePresenceCheckerRef() != null ) {
                    result = new NullCheckWrapper( result, getSourcePresenceCheckerRef() );
                }
            }
            else {

                if ( result.isUpdateMethod() ) {
                    result = new UpdateNullCheckWrapper( result, getSourcePresenceCheckerRef() );
                }
                else if ( getSourcePresenceCheckerRef() != null ) {
                    result = new NullCheckWrapper( result, getSourcePresenceCheckerRef() );
                }
                else if ( ALWAYS.equals( method.getMapperConfiguration().getNullValueCheckStrategy() ) ) {
                    result = new NullCheckWrapper( result, getSourcePresenceCheckerRef() );
                    useLocalVarWhenNested( result );
                }
                else if ( result.getType() == TYPE_CONVERTED
                    || result.getType() == TYPE_CONVERTED_MAPPED
                    || result.getType() == MAPPED_TYPE_CONVERTED
                    || (result.getType() == DIRECT && targetType.isPrimitive() ) ) {
                    // for primitive types null check is not possible at all, but a conversion needs
                    // a null check.
                    result = new NullCheckWrapper( result, getSourcePresenceCheckerRef() );
                    useLocalVarWhenNested( result );
                }
            }

            return result;
        }

        private void useLocalVarWhenNested(Assignment rightHandSide) {
            if ( sourceReference.getPropertyEntries().size() > 1 ) {
                String name = first( sourceReference.getPropertyEntries() ).getName();
                String safeName = Strings.getSaveVariableName( name, existingVariableNames );
                existingVariableNames.add( safeName );
                rightHandSide.setSourceLocalVarName( safeName );
            }
        }

        private Assignment assignToPlainViaAdder(Type sourceType, Assignment rightHandSide) {

            Assignment result = rightHandSide;

            if ( getSourceType().isCollectionType() ) {
                result = new AdderWrapper(
                    result,
                    method.getThrownTypes(),
                    getSourceRHS().getSourceReference(),
                    sourceType
                );
                result = new NullCheckWrapper( result, getSourcePresenceCheckerRef() );
            }
            else {
                // Possibly adding null to a target collection. So should be surrounded by an null check.
                result = new SetterWrapper( result, method.getThrownTypes() );
                result = new NullCheckWrapper( result, getSourcePresenceCheckerRef() );
            }
            return result;
        }

        private Assignment assignToCollection(Type targetType, TargetWriteAccessorType targetAccessorType,
                                            Assignment rhs) {

            Assignment result = rhs;

            // wrap the setter in the collection / map initializers
            if ( targetAccessorType == TargetWriteAccessorType.SETTER ) {

                // wrap the assignment in a new Map or Collection implementation if this is not done in a
                // mapping method. Note, typeconversons do not apply to collections or maps
                Assignment newCollectionOrMap = null;
                if ( result.getType() == DIRECT ) {
                    Set<Type> implementationTypes;
                    if ( targetType.getImplementationType() != null ) {
                        implementationTypes = targetType.getImplementationType().getImportTypes();
                    }
                    else {
                        implementationTypes = targetType.getImportTypes();
                    }

                    if ( "java.util.EnumSet".equals( targetType.getFullyQualifiedName() ) ) {
                        newCollectionOrMap = new EnumSetCopyWrapper( ctx.getTypeFactory(), result );
                    }
                    else {
                        newCollectionOrMap = new NewCollectionOrMapWrapper( result, implementationTypes );
                    }

                    newCollectionOrMap = new SetterWrapper( newCollectionOrMap, method.getThrownTypes() );
                }
                if ( result.isUpdateMethod() ) {
                    if ( targetReadAccessor == null ) {
                        ctx.getMessager().printMessage( method.getExecutable(),
                            Message.PROPERTYMAPPING_NO_READ_ACCESSOR_FOR_TARGET_TYPE,
                            targetPropertyName );
                    }
                    Assignment factoryMethod = ctx.getMappingResolver().getFactoryMethod( method, targetType, null );
                    result = new UpdateWrapper( result, method.getThrownTypes(), factoryMethod,
                        targetType );
                }
                else {
                    // target accessor is setter, so wrap the setter in setter map/ collection handling
                    result = new SetterWrapperForCollectionsAndMaps(
                        result,
                        newCollectionOrMap,
                        method.getThrownTypes(),
                        getSourcePresenceCheckerRef(),
                        existingVariableNames,
                        targetType
                    );
                }

            }
            else {
                // target accessor is getter, so wrap the setter in getter map/ collection handling
                result = new GetterWrapperForCollectionsAndMaps( result,
                                                                 method.getThrownTypes(),
                                                                 getSourcePresenceCheckerRef(),
                                                                 existingVariableNames,
                                                                 targetType);
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
                existingVariableNames
            );
            return new NullCheckWrapper( assignment, getSourcePresenceCheckerRef() );
        }

        private Type getSourceType() {

            Parameter sourceParam = sourceReference.getParameter();
            List<PropertyEntry> propertyEntries = sourceReference.getPropertyEntries();
            if ( propertyEntries.isEmpty() ) {
                return sourceParam.getType();
            }
            else if ( propertyEntries.size() == 1 ) {
                return last( propertyEntries ).getType();
            }
            else {
                Type sourceType = last( propertyEntries ).getType();
                if ( sourceType.isPrimitive() && !targetType.isPrimitive() ) {
                    // Handle null's. If the forged method needs to be mapped to an object, the forged method must be
                    // able to return null. So in that case primitive types are mapped to their corresponding wrapped
                    // type. The source type becomes the wrapped type in that case.
                    sourceType = ctx.getTypeFactory().getWrappedType( sourceType );
                }
                return sourceType;
            }
        }

        private SourceRHS getSourceRHS() {
            Parameter sourceParam = sourceReference.getParameter();
            List<PropertyEntry> propertyEntries = sourceReference.getPropertyEntries();

            // parameter reference
            if ( propertyEntries.isEmpty() ) {
                return new SourceRHS( sourceParam.getName(), getSourceType() );
            }
            // simple property
            else if ( propertyEntries.size() == 1 ) {
                PropertyEntry propertyEntry = propertyEntries.get( 0 );
                return new SourceRHS( sourceParam.getName()
                    + "." + propertyEntry.getReadAccessor().getSimpleName() + "()", propertyEntry.getType() );
            }
            // nested property given as dot path
            else {
                // copy mapper configuration from the source method, its the same mapper
                MapperConfiguration config = method.getMapperConfiguration();


                // forge a method from the parameter type to the last entry type.
                String forgedName = Strings.joinAndCamelize( sourceReference.getElementNames() );
                forgedName = Strings.getSaveVariableName( forgedName, ctx.getNamesOfMappingsToGenerate() );
                ForgedMethod methodRef = new ForgedMethod(
                    forgedName,
                    sourceReference.getParameter().getType(),
                    getSourceType(),
                    config,
                    method.getExecutable()
                );
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
                return new SourceRHS( forgedName + "( " + sourceParam.getName() + " )", getSourceType() );

            }
        }

        private String getSourceElement() {

            Parameter sourceParam = sourceReference.getParameter();
            List<PropertyEntry> propertyEntries = sourceReference.getPropertyEntries();
            if ( propertyEntries.isEmpty() ) {
                return String.format( "parameter \"%s %s\"", sourceParam.getType(), sourceParam.getName() );
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
                    Strings.join( sourceReference.getElementNames(), "." )
                );
            }
        }

        private String getSourcePresenceCheckerRef() {
            String sourcePresenceChecker = null;
            if ( !sourceReference.getPropertyEntries().isEmpty() ) {
                Parameter sourceParam = sourceReference.getParameter();
                PropertyEntry propertyEntry = first( sourceReference.getPropertyEntries() );
                if ( propertyEntry.getPresenceChecker() != null ) {
                    sourcePresenceChecker = sourceParam.getName()
                        + "." + propertyEntry.getPresenceChecker().getSimpleName() + "()";
                }
            }
            return sourcePresenceChecker;
        }

        private Assignment forgeIterableMapping(Type sourceType, Type targetType, SourceRHS source,
            ExecutableElement element) {

            Assignment assignment = null;
            String name = getName( sourceType, targetType );
            name = Strings.getSaveVariableName( name, ctx.getNamesOfMappingsToGenerate() );

            // copy mapper configuration from the source method, its the same mapper
            MapperConfiguration config = method.getMapperConfiguration();
            ForgedMethod methodRef = new ForgedMethod( name, sourceType, targetType, config, element );
            IterableMappingMethod.Builder builder = new IterableMappingMethod.Builder();

            IterableMappingMethod iterableMappingMethod = builder
                .mappingContext( ctx )
                .method( methodRef )
                .selectionParameters( selectionParameters )
                .build();

            if ( iterableMappingMethod != null ) {
                if ( !ctx.getMappingsToGenerate().contains( iterableMappingMethod ) ) {
                    ctx.getMappingsToGenerate().add( iterableMappingMethod );
                }
                else {
                    String existingName = ctx.getExistingMappingMethod( iterableMappingMethod ).getName();
                    methodRef = new ForgedMethod( existingName, methodRef );
                }

                assignment = new MethodReference( methodRef, null, targetType );
                assignment.setAssignment( source );
            }

            return assignment;
        }

        private Assignment forgeMapMapping(Type sourceType, Type targetType, SourceRHS source,
            ExecutableElement element) {

            Assignment assignment = null;

            String name = getName( sourceType, targetType );
            name = Strings.getSaveVariableName( name, ctx.getNamesOfMappingsToGenerate() );

            // copy mapper configuration from the source method, its the same mapper
            MapperConfiguration config = method.getMapperConfiguration();
            ForgedMethod methodRef = new ForgedMethod( name, sourceType, targetType, config, element );

            MapMappingMethod.Builder builder = new MapMappingMethod.Builder();
            MapMappingMethod mapMappingMethod = builder
                .mappingContext( ctx )
                .method( methodRef )
                .build();

            if ( mapMappingMethod != null ) {
                if ( !ctx.getMappingsToGenerate().contains( mapMappingMethod ) ) {
                    ctx.getMappingsToGenerate().add( mapMappingMethod );
                }
                else {
                    String existingName = ctx.getExistingMappingMethod( mapMappingMethod ).getName();
                    methodRef = new ForgedMethod( existingName, methodRef );
                }
                assignment = new MethodReference( methodRef, null, targetType );
                assignment.setAssignment( source );
            }

            return assignment;
        }

        private String getName(Type sourceType, Type targetType ) {
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
            String mappedElement = "constant '" + constantExpression + "'";
            Type sourceType = ctx.getTypeFactory().getType( String.class );

            Assignment assignment = null;
            if ( !targetType.isEnumType() ) {
                assignment = ctx.getMappingResolver().getTargetAssignment(
                    method,
                    mappedElement,
                    sourceType,
                    targetType,
                    targetPropertyName,
                    formattingParameters,
                    selectionParameters,
                    new SourceRHS( constantExpression, sourceType ),
                    method.getMappingTargetParameter() != null
                );
            }
            else {
                assignment = getEnumAssignment();
            }

            if ( assignment != null ) {

                if ( Executables.isSetterMethod( targetWriteAccessor ) ) {

                    // target accessor is setter, so decorate assignment as setter
                    if ( assignment.isUpdateMethod() ) {
                        if ( targetReadAccessor == null ) {
                            ctx.getMessager().printMessage( method.getExecutable(),
                                Message.CONSTANTMAPPING_NO_READ_ACCESSOR_FOR_TARGET_TYPE,
                                targetPropertyName );
                        }
                        Assignment factoryMethod =
                            ctx.getMappingResolver().getFactoryMethod( method, targetType, null );
                        assignment = new UpdateWrapper( assignment, method.getThrownTypes(), factoryMethod,
                            targetType );
                    }
                    else {
                        assignment = new SetterWrapper( assignment, method.getThrownTypes() );
                    }
                }
                else {

                    // target accessor is getter, so getter map/ collection handling
                    assignment = new GetterWrapperForCollectionsAndMaps( assignment,
                                                                         method.getThrownTypes(),
                                                                         existingVariableNames,
                                                                         targetType);
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
                targetReadAccessor != null ? targetReadAccessor.getSimpleName().toString() : null,
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
                assignment = new SourceRHS( enumExpression, targetType );
                assignment = new EnumConstantWrapper( assignment, targetType );
            }
            else {
                ctx.getMessager().printMessage( method.getExecutable(),
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
            Assignment assignment = new SourceRHS( javaExpression, null );

            if ( Executables.isSetterMethod( targetWriteAccessor ) ) {
                // setter, so wrap in setter
                assignment = new SetterWrapper( assignment, method.getThrownTypes() );
            }
            else {
                // target accessor is getter, so wrap the setter in getter map/ collection handling
                assignment = new GetterWrapperForCollectionsAndMaps( assignment,
                                                                     method.getThrownTypes(),
                                                                     existingVariableNames,
                                                                     targetType);
            }

            return new PropertyMapping(
                targetPropertyName,
                targetWriteAccessor.getSimpleName().toString(),
                targetReadAccessor != null ? targetReadAccessor.getSimpleName().toString() : null,
                targetType,
                localTargetVarName,
                assignment,
                dependsOn,
                null
            );
        }

    }

    // Constructor for creating mappings of constant expressions.
    private PropertyMapping(String name,  String targetWriteAccessorName, String targetReadAccessorName,
                            Type targetType, String localTargetVarName, Assignment propertyAssignment,
                            List<String> dependsOn, Assignment defaultValueAssignment ) {
        this( name, null, targetWriteAccessorName, targetReadAccessorName,
                        targetType, localTargetVarName, propertyAssignment, dependsOn, defaultValueAssignment );
    }

    private PropertyMapping(String name, String sourceBeanName, String targetWriteAccessorName,
                            String targetReadAccessorName, Type targetType, String localTargetVarName,
                            Assignment assignment,
                            List<String> dependsOn, Assignment defaultValueAssignment ) {
        this.name = name;
        this.sourceBeanName = sourceBeanName;
        this.targetWriteAccessorName = targetWriteAccessorName;
        this.targetReadAccessorName = targetReadAccessorName;
        this.targetType = targetType;
        this.localTargetVarName = localTargetVarName;

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
        return targetReadAccessorName;
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
            defaultValueAssignment.getImportTypes() );
    }

    public List<String> getDependsOn() {
        return dependsOn;
    }

    @Override
    public String toString() {
        return "PropertyMapping {"
            + "\n    name='" + name + "\',"
            + "\n    targetWriteAccessorName='" + targetWriteAccessorName + "\',"
            + "\n    targetReadAccessorName='" + targetReadAccessorName + "\',"
            + "\n    targetType=" + targetType + ","
            + "\n    propertyAssignment=" + assignment + ","
            + "\n    defaultValueAssignment=" + defaultValueAssignment + ","
            + "\n    dependsOn=" + dependsOn
            + "\n}";
    }
}
