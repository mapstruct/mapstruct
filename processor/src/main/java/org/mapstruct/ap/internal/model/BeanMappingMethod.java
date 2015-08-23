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
package org.mapstruct.ap.internal.model;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;

import org.mapstruct.ap.internal.model.PropertyMapping.ConstantMappingBuilder;
import org.mapstruct.ap.internal.model.PropertyMapping.JavaExpressionMappingBuilder;
import org.mapstruct.ap.internal.model.PropertyMapping.PropertyMappingBuilder;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.dependency.GraphAnalyzer;
import org.mapstruct.ap.internal.model.dependency.GraphAnalyzer.GraphAnalyzerBuilder;
import org.mapstruct.ap.internal.model.source.Mapping;
import org.mapstruct.ap.internal.model.source.SourceMethod;
import org.mapstruct.ap.internal.model.source.SourceReference;
import org.mapstruct.ap.internal.option.ReportingPolicy;
import org.mapstruct.ap.internal.prism.BeanMappingPrism;
import org.mapstruct.ap.internal.prism.CollectionMappingStrategyPrism;
import org.mapstruct.ap.internal.prism.NullValueMappingStrategyPrism;
import org.mapstruct.ap.internal.util.Executables;
import org.mapstruct.ap.internal.util.MapperConfiguration;
import org.mapstruct.ap.internal.util.Message;
import org.mapstruct.ap.internal.util.Strings;

/**
 * A {@link MappingMethod} implemented by a {@link Mapper} class which maps one bean type to another, optionally
 * configured by one or more {@link PropertyMapping}s.
 *
 * @author Gunnar Morling
 */
public class BeanMappingMethod extends MappingMethod {

    private final List<PropertyMapping> propertyMappings;
    private final Map<String, List<PropertyMapping>> mappingsByParameter;
    private final List<PropertyMapping> constantMappings;
    private final MethodReference factoryMethod;
    private final boolean mapNullToDefault;
    private final Type resultType;

    public static class Builder {

        private MappingBuilderContext ctx;
        private SourceMethod method;
        private Map<String, ExecutableElement> unprocessedTargetProperties;
        private Set<String> targetProperties;
        private final List<PropertyMapping> propertyMappings = new ArrayList<PropertyMapping>();
        private final Set<Parameter> unprocessedSourceParameters = new HashSet<Parameter>();
        private List<TypeMirror> qualifiers;
        private NullValueMappingStrategyPrism nullValueMappingStrategy;
        private TypeMirror resultTypeMirror;
        private final Set<String> existingVariableNames = new HashSet<String>();

        public Builder mappingContext(MappingBuilderContext mappingContext) {
            this.ctx = mappingContext;
            return this;
        }

        public Builder souceMethod(SourceMethod sourceMethod) {
            this.method = sourceMethod;
            CollectionMappingStrategyPrism cms = sourceMethod.getMapperConfiguration().getCollectionMappingStrategy();
            Map<String, ExecutableElement> accessors = method.getResultType().getPropertyWriteAccessors( cms );
            this.targetProperties = accessors.keySet();
            this.unprocessedTargetProperties = new LinkedHashMap<String, ExecutableElement>( accessors );
            for ( Parameter sourceParameter : method.getSourceParameters() ) {
                unprocessedSourceParameters.add( sourceParameter );
            }
            existingVariableNames.addAll( method.getParameterNames() );
            return this;
        }

        public Builder qualifiers(List<TypeMirror> qualifiers) {
            this.qualifiers = qualifiers;
            return this;
        }

        public Builder nullValueMappingStrategy(NullValueMappingStrategyPrism nullValueMappingStrategy) {
            this.nullValueMappingStrategy = nullValueMappingStrategy;
            return this;
        }

        public Builder resultType(TypeMirror resultType) {
            this.resultTypeMirror = resultType;
            return this;
        }

        public BeanMappingMethod build() {
            // map properties with mapping
            boolean mappingErrorOccured = handleDefinedSourceMappings();
            if ( mappingErrorOccured ) {
                return null;
            }

            // map properties without a mapping
            applyPropertyNameBasedMapping();

            // map parameters without a mapping
            applyParameterNameBasedMapping();

            // report errors on unmapped properties
            reportErrorForUnmappedTargetPropertiesIfRequired();

            // mapNullToDefault
            boolean mapNullToDefault = method.getMapperConfiguration().isMapToDefault( nullValueMappingStrategy );


            BeanMappingPrism beanMappingPrism = BeanMappingPrism.getInstanceOn( method.getExecutable() );

            MethodReference factoryMethod = null;
            if ( !method.isUpdateMethod() ) {
                factoryMethod = ctx.getMappingResolver().getFactoryMethod(
                    method,
                    method.getResultType(),
                    qualifiers,
                    resultTypeMirror );
            }

            // if there's no factory method, try the resultType in the @BeanMapping
            Type resultType = null;
            if ( factoryMethod == null ) {
                if ( resultTypeMirror != null ) {
                    resultType = ctx.getTypeFactory().getType( resultTypeMirror );
                    if ( !resultType.isAssignableTo( method.getResultType() ) ) {
                        ctx.getMessager().printMessage(
                            method.getExecutable(),
                            beanMappingPrism.mirror,
                            Message.BEANMAPPING_NOT_ASSIGNABLE, resultType, method.getResultType()
                        );
                    }
                }
            }

            sortPropertyMappingsByDependencies();

            List<LifecycleCallbackMethodReference> beforeMappingMethods =
                LifecycleCallbackFactory.beforeMappingMethods( method, qualifiers, ctx );
            List<LifecycleCallbackMethodReference> afterMappingMethods =
                LifecycleCallbackFactory.afterMappingMethods( method, qualifiers, ctx );

            return new BeanMappingMethod(
                method,
                propertyMappings,
                factoryMethod,
                mapNullToDefault,
                resultType,
                existingVariableNames,
                beforeMappingMethods,
                afterMappingMethods
            );
        }

        /**
         * Sources the given mappings as per the dependency relationships given via {@code dependsOn()}. If a cycle is
         * detected, an error is reported.
         */
        private void sortPropertyMappingsByDependencies() {
            GraphAnalyzerBuilder graphAnalyzerBuilder = GraphAnalyzer.builder();

            for ( PropertyMapping propertyMapping : propertyMappings ) {
                graphAnalyzerBuilder.withNode( propertyMapping.getName(), propertyMapping.getDependsOn() );
            }

            final GraphAnalyzer graphAnalyzer = graphAnalyzerBuilder.build();

            if ( !graphAnalyzer.getCycles().isEmpty() ) {
                Set<String> cycles = new HashSet<String>();
                for ( List<String> cycle : graphAnalyzer.getCycles() ) {
                    cycles.add( Strings.join( cycle, " -> " ) );
                }

                ctx.getMessager().printMessage(
                    method.getExecutable(),
                    Message.BEANMAPPING_CYCLE_BETWEEN_PROPERTIES, Strings.join( cycles, ", " )
                );
            }
            else {
                Collections.sort(
                    propertyMappings, new Comparator<PropertyMapping>() {

                        @Override
                        public int compare(PropertyMapping o1, PropertyMapping o2) {
                            if ( graphAnalyzer.getAllDescendants( o1.getName() ).contains( o2.getName() ) ) {
                                return 1;
                            }
                            else if ( graphAnalyzer.getAllDescendants( o2.getName() ).contains( o1.getName() ) ) {
                                return -1;
                            }
                            else {
                                return 0;
                            }
                        }
                    }
                );
            }
        }

        /**
         * Iterates over all defined mapping methods ({@code @Mapping(s)}), either directly given or inherited from the
         * inverse mapping method.
         * <p>
         * If a match is found between a defined source (constant, expression, ignore or source) the mapping is removed
         * from the remaining target properties.
         * <p>
         * It is furthermore checked whether the given mappings are correct. When an error occurs, the method continues
         * in search of more problems.
         */
        private boolean handleDefinedSourceMappings() {
            boolean errorOccurred = false;

            Set<String> handledTargets = new HashSet<String>();

            for ( Map.Entry<String, List<Mapping>> entry : method.getMappingOptions().getMappings().entrySet() ) {
                for ( Mapping mapping : entry.getValue() ) {

                    PropertyMapping propertyMapping = null;

                    // fetch the target property
                    ExecutableElement targetWriteAccessor = unprocessedTargetProperties.get( mapping.getTargetName() );
                    if ( targetWriteAccessor == null ) {
                        ctx.getMessager().printMessage(
                            method.getExecutable(),
                            mapping.getMirror(),
                            mapping.getSourceAnnotationValue(),
                            Message.BEANMAPPING_UNKNOWN_PROPERTY_IN_RETURNTYPE,
                            mapping.getTargetName()
                        );
                        errorOccurred = true;
                    }

                    // unknown properties given via dependsOn()?
                    for ( String dependency : mapping.getDependsOn() ) {
                        if ( !targetProperties.contains( dependency ) ) {
                            ctx.getMessager().printMessage(
                                method.getExecutable(),
                                mapping.getMirror(),
                                mapping.getDependsOnAnnotationValue(),
                                Message.BEANMAPPING_UNKNOWN_PROPERTY_IN_DEPENDS_ON,
                                dependency
                            );
                            errorOccurred = true;
                        }
                    }

                    // check the mapping options
                    // its an ignored property mapping
                    if ( mapping.isIgnored() ) {
                        propertyMapping = null;
                        handledTargets.add( mapping.getTargetName() );
                    }

                    // its a plain-old property mapping
                    else if ( mapping.getSourceName() != null ) {

                        // determine source parameter
                        SourceReference sourceRef = mapping.getSourceReference();
                        if ( sourceRef.isValid() ) {

                            if ( targetWriteAccessor != null ) {

                                // targetProperty == null can occur: we arrived here because we want as many errors
                                // as possible before we stop analysing
                                propertyMapping = new PropertyMappingBuilder()
                                    .mappingContext( ctx )
                                    .sourceMethod( method )
                                    .targetWriteAccessor( targetWriteAccessor )
                                    .targetReadAccessor( getTargetPropertyReadAccessor( mapping.getTargetName() ) )
                                    .targetPropertyName( mapping.getTargetName() )
                                    .sourceReference( sourceRef )
                                    .qualifiers( mapping.getQualifiers() )
                                    .resultType( mapping.getResultType() )
                                    .dateFormat( mapping.getDateFormat() )
                                    .existingVariableNames( existingVariableNames )
                                    .dependsOn( mapping.getDependsOn() )
                                    .defaultValue( mapping.getDefaultValue() )
                                    .build();
                                handledTargets.add( mapping.getTargetName() );
                                unprocessedSourceParameters.remove( sourceRef.getParameter() );
                            }
                        }
                        else {
                            errorOccurred = true;
                        }
                    }

                    // its a constant
                    else if ( mapping.getConstant() != null && targetWriteAccessor != null ) {

                        propertyMapping = new ConstantMappingBuilder()
                            .mappingContext( ctx )
                            .sourceMethod( method )
                            .constantExpression( "\"" + mapping.getConstant() + "\"" )
                            .targetWriteAccessor( targetWriteAccessor )
                            .targetReadAccessor( getTargetPropertyReadAccessor( mapping.getTargetName() ) )
                            .targetPropertyName( mapping.getTargetName() )
                            .dateFormat( mapping.getDateFormat() )
                            .qualifiers( mapping.getQualifiers() )
                            .resultType( mapping.getResultType() )
                            .existingVariableNames( existingVariableNames )
                            .dependsOn( mapping.getDependsOn() )
                            .build();
                        handledTargets.add( mapping.getTargetName() );
                    }

                    // its an expression
                    else if ( mapping.getJavaExpression() != null && targetWriteAccessor != null ) {

                        propertyMapping = new JavaExpressionMappingBuilder()
                            .mappingContext( ctx )
                            .sourceMethod( method )
                            .javaExpression( mapping.getJavaExpression() )
                            .existingVariableNames( existingVariableNames )
                            .targetWriteAccessor( targetWriteAccessor )
                            .targetReadAccessor( targetWriteAccessor )
                            .targetPropertyName( mapping.getTargetName() )
                            .dependsOn( mapping.getDependsOn() )
                            .build();
                        handledTargets.add( mapping.getTargetName() );
                    }

                    // remaining are the mappings without a 'source' so, 'only' a date format or qualifiers

                    if ( propertyMapping != null ) {
                        propertyMappings.add( propertyMapping );
                    }
                }
            }

            for ( String handledTarget : handledTargets ) {
                // In order to avoid: "Unknown property foo in return type" in case of duplicate
                // target mappings
                unprocessedTargetProperties.remove( handledTarget );
            }

            return errorOccurred;
        }

        /**
         * Iterates over all target properties and all source parameters.
         * <p>
         * When a property name match occurs, the remainder will be checked for duplicates. Matches will be removed from
         * the set of remaining target properties.
         */
        private void applyPropertyNameBasedMapping() {
            Iterator<Entry<String, ExecutableElement>> targetProperties =
                unprocessedTargetProperties.entrySet().iterator();

            // usually there should be only one getter; only for Boolean there may be two: isFoo() and getFoo()
            List<ExecutableElement> candidates = new ArrayList<ExecutableElement>( 2 );

            while ( targetProperties.hasNext() ) {
                Entry<String, ExecutableElement> targetProperty = targetProperties.next();

                PropertyMapping propertyMapping = null;

                if ( propertyMapping == null ) {

                    for ( Parameter sourceParameter : method.getSourceParameters() ) {

                        if ( sourceParameter.getType().isPrimitive() ) {
                            continue;
                        }

                        Collection<ExecutableElement> sourceReadAccessors =
                            sourceParameter.getType().getPropertyReadAccessors().values();
                        for ( ExecutableElement sourceReadAccessor : sourceReadAccessors ) {
                            String sourcePropertyName = Executables.getPropertyName( sourceReadAccessor );

                            if ( sourcePropertyName.equals( targetProperty.getKey() ) ) {
                                candidates.add( sourceReadAccessor );
                            }
                        }

                        PropertyMapping newPropertyMapping = null;
                        ExecutableElement sourceAccessor = getSourceAccessor( targetProperty.getKey(), candidates );
                        if ( sourceAccessor != null ) {
                            Mapping mapping = method.getSingleMappingByTargetPropertyName( targetProperty.getKey() );

                            TypeElement sourceType = sourceParameter.getType().getTypeElement();

                            SourceReference sourceRef = new SourceReference.BuilderFromProperty()
                                .sourceParameter( sourceParameter )
                                .type( ctx.getTypeFactory().getReturnType( sourceType, sourceAccessor ) )
                                .accessor( sourceAccessor )
                                .name( targetProperty.getKey() )
                                .build();

                            newPropertyMapping = new PropertyMappingBuilder()
                                .mappingContext( ctx )
                                .sourceMethod( method )
                                .targetWriteAccessor( targetProperty.getValue() )
                                .targetReadAccessor( getTargetPropertyReadAccessor( targetProperty.getKey() ) )
                                .targetPropertyName( targetProperty.getKey() )
                                .sourceReference( sourceRef )
                                .qualifiers( mapping != null ? mapping.getQualifiers() : null )
                                .resultType( mapping != null ? mapping.getResultType() : null )
                                .dateFormat( mapping != null ? mapping.getDateFormat() : null )
                                .defaultValue( mapping != null ? mapping.getDefaultValue() : null )
                                .existingVariableNames( existingVariableNames )
                                .dependsOn( mapping != null ? mapping.getDependsOn() : Collections.<String>emptyList() )
                                .build();

                            unprocessedSourceParameters.remove( sourceParameter );

                        }
                        // candidates are handled
                        candidates.clear();

                        if ( propertyMapping != null && newPropertyMapping != null ) {
                            // TODO improve error message
                            ctx.getMessager().printMessage(
                                method.getExecutable(),
                                Message.BEANMAPPING_SEVERAL_POSSIBLE_SOURCES,
                                targetProperty.getKey()
                            );
                            break;
                        }
                        else if ( newPropertyMapping != null ) {
                            propertyMapping = newPropertyMapping;
                        }
                    }
                }

                if ( propertyMapping != null ) {
                    propertyMappings.add( propertyMapping );
                    targetProperties.remove();
                }
            }
        }

        private void applyParameterNameBasedMapping() {

            Iterator<Entry<String, ExecutableElement>> targetProperties =
                unprocessedTargetProperties.entrySet().iterator();

            while ( targetProperties.hasNext() ) {

                Entry<String, ExecutableElement> targetProperty = targetProperties.next();

                Iterator<Parameter> sourceParameters = unprocessedSourceParameters.iterator();

                while ( sourceParameters.hasNext() ) {

                    Parameter sourceParameter = sourceParameters.next();
                    if ( sourceParameter.getName().equals( targetProperty.getKey() ) ) {
                        Mapping mapping = method.getSingleMappingByTargetPropertyName( targetProperty.getKey() );

                        SourceReference sourceRef = new SourceReference.BuilderFromProperty()
                            .sourceParameter( sourceParameter )
                            .name( targetProperty.getKey() )
                            .build();

                        PropertyMapping propertyMapping = new PropertyMappingBuilder()
                            .mappingContext( ctx )
                            .sourceMethod( method )
                            .targetWriteAccessor( targetProperty.getValue() )
                            .targetReadAccessor( getTargetPropertyReadAccessor( targetProperty.getKey() ) )
                            .targetPropertyName( targetProperty.getKey() )
                            .sourceReference( sourceRef )
                            .qualifiers( mapping != null ? mapping.getQualifiers() : null )
                            .resultType( mapping != null ? mapping.getResultType() : null )
                            .dateFormat( mapping != null ? mapping.getDateFormat() : null )
                            .existingVariableNames( existingVariableNames )
                            .dependsOn( mapping != null ? mapping.getDependsOn() : Collections.<String>emptyList() )
                            .build();

                        propertyMappings.add( propertyMapping );
                        targetProperties.remove();
                        sourceParameters.remove();
                    }
                }
            }
        }

        private ExecutableElement getSourceAccessor(String sourcePropertyName, List<ExecutableElement> candidates) {
            if ( candidates.isEmpty() ) {
                return null;
            }
            else if ( candidates.size() == 1 ) {
                return candidates.get( 0 );
            }
            // can only be the case for Booleans: isFoo() and getFoo(); The latter is preferred then
            else if ( candidates.size() == 2 ) {
                if ( candidates.get( 0 ).getSimpleName().toString().startsWith( "get" ) ) {
                    return candidates.get( 0 );
                }
                else {
                    return candidates.get( 1 );
                }
            }
            // Should never really happen
            else {
                ctx.getMessager().printMessage(
                    method.getExecutable(),
                    Message.BEANMAPPING_SEVERAL_POSSIBLE_TARGET_ACCESSORS,
                    sourcePropertyName
                );

                return null;
            }
        }

        private ExecutableElement getTargetPropertyReadAccessor( String propertyName ) {
            return method.getResultType().getPropertyReadAccessors().get( propertyName );
        }

        /**
         * Returns the effective policy for reporting unmapped getReturnType properties. If explicitly set via
         * {@code Mapper}, this value will be returned. Otherwise the value from the corresponding processor option will
         * be returned. If that is not set either, the default value from {@code Mapper#unmappedTargetPolicy()} will be
         * returned.
         *
         * @return The effective policy for reporting unmapped target properties.
         */
        private ReportingPolicy getEffectiveUnmappedTargetPolicy() {
            MapperConfiguration mapperSettings = MapperConfiguration.getInstanceOn( ctx.getMapperTypeElement() );
            boolean setViaAnnotation = mapperSettings.isSetUnmappedTargetPolicy();
            ReportingPolicy annotationValue = ReportingPolicy.valueOf( mapperSettings.unmappedTargetPolicy() );

            if ( setViaAnnotation
                || ctx.getOptions().getUnmappedTargetPolicy() == null ) {
                return annotationValue;
            }
            else {
                return ctx.getOptions().getUnmappedTargetPolicy();
            }
        }

        private void reportErrorForUnmappedTargetPropertiesIfRequired() {

            // fetch settings from element to implement
            ReportingPolicy unmappedTargetPolicy = getEffectiveUnmappedTargetPolicy();

            if ( !unprocessedTargetProperties.isEmpty() && unmappedTargetPolicy.requiresReport() ) {

                Message msg = unmappedTargetPolicy.getDiagnosticKind() == Diagnostic.Kind.ERROR ?
                    Message.BEANMAPPING_UNMAPPED_TARGETS_ERROR : Message.BEANMAPPING_UNMAPPED_TARGETS_WARNING;

                ctx.getMessager().printMessage(
                    method.getExecutable(),
                    msg,
                    MessageFormat.format(
                        "{0,choice,1#property|1<properties}: \"{1}\"",
                        unprocessedTargetProperties.size(),
                        Strings.join( unprocessedTargetProperties.keySet(), ", " )
                    )
                );
            }
        }
    }

    private BeanMappingMethod(SourceMethod method,
                              List<PropertyMapping> propertyMappings,
                              MethodReference factoryMethod,
                              boolean mapNullToDefault,
                              Type resultType,
                              Collection<String> existingVariableNames,
                              List<LifecycleCallbackMethodReference> beforeMappingReferences,
                              List<LifecycleCallbackMethodReference> afterMappingReferences) {
        super( method, existingVariableNames, beforeMappingReferences, afterMappingReferences );
        this.propertyMappings = propertyMappings;

        // intialize constant mappings as all mappings, but take out the ones that can be contributed to a
        // parameter mapping.
        this.mappingsByParameter = new HashMap<String, List<PropertyMapping>>();
        this.constantMappings = new ArrayList<PropertyMapping>( propertyMappings );
        for ( Parameter sourceParameter : getSourceParameters() ) {
            ArrayList<PropertyMapping> mappingsOfParameter = new ArrayList<PropertyMapping>();
            mappingsByParameter.put( sourceParameter.getName(), mappingsOfParameter );
            for ( PropertyMapping mapping : propertyMappings ) {
                if ( sourceParameter.getName().equals( mapping.getSourceBeanName() ) ) {
                    mappingsOfParameter.add( mapping );
                    constantMappings.remove( mapping );
                }
            }
        }
        this.factoryMethod = factoryMethod;
        this.mapNullToDefault = mapNullToDefault;
        this.resultType = resultType;
    }

    public List<PropertyMapping> getPropertyMappings() {
        return propertyMappings;
    }

    public List<PropertyMapping> getConstantMappings() {
        return constantMappings;
    }

    public Map<String, List<PropertyMapping>> getPropertyMappingsByParameter() {
        return mappingsByParameter;
    }

    public boolean isMapNullToDefault() {
        return mapNullToDefault;
    }

    @Override
    public Type getResultType() {
        if ( resultType == null ) {
            return super.getResultType();
        }
        else {
            return resultType;
        }
    }

    @Override
    public Set<Type> getImportTypes() {
        Set<Type> types = super.getImportTypes();

        for ( PropertyMapping propertyMapping : propertyMappings ) {
            types.addAll( propertyMapping.getImportTypes() );
        }

        return types;
    }

    public List<Parameter> getSourceParametersExcludingPrimitives() {
        List<Parameter> sourceParameters = new ArrayList<Parameter>();
        for ( Parameter sourceParam : getSourceParameters() ) {
            if ( !sourceParam.getType().isPrimitive() ) {
                sourceParameters.add( sourceParam );
            }
        }

        return sourceParameters;
    }

    public List<Parameter> getSourcePrimitiveParameters() {
        List<Parameter> sourceParameters = new ArrayList<Parameter>();
        for ( Parameter sourceParam : getSourceParameters() ) {
            if ( sourceParam.getType().isPrimitive() ) {
                sourceParameters.add( sourceParam );
            }
        }
        return sourceParameters;
    }


    public MethodReference getFactoryMethod() {
        return this.factoryMethod;
    }
}
