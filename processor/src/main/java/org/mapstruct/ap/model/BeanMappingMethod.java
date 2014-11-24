/**
 *  Copyright 2012-2014 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.model;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.lang.model.element.ExecutableElement;
import javax.tools.Diagnostic;

import org.mapstruct.ap.model.PropertyMapping.ConstantMappingBuilder;
import org.mapstruct.ap.model.PropertyMapping.JavaExpressionMappingBuilder;
import org.mapstruct.ap.model.PropertyMapping.PropertyMappingBuilder;
import org.mapstruct.ap.model.common.Parameter;
import org.mapstruct.ap.model.common.Type;
import org.mapstruct.ap.model.source.Mapping;
import org.mapstruct.ap.model.source.SourceMethod;
import org.mapstruct.ap.model.source.SourceReference;
import org.mapstruct.ap.option.ReportingPolicy;
import org.mapstruct.ap.prism.NullValueMappingPrism;
import org.mapstruct.ap.prism.CollectionMappingStrategyPrism;
import org.mapstruct.ap.util.Executables;
import org.mapstruct.ap.util.MapperConfig;
import org.mapstruct.ap.util.Strings;

/**
 * A {@link MappingMethod} implemented by a {@link Mapper} class which maps one bean type to another, optionally
 * configured by one or more {@link PropertyMapping}s.
 *
 * @author Gunnar Morling
 * @author Sjaak Derksen
 */
public class BeanMappingMethod extends MappingMethod {

    private final List<PropertyMapping> propertyMappings;
    private final Map<String, List<PropertyMapping>> mappingsByParameter;
    private final List<PropertyMapping> constantMappings;
    private final MethodReference factoryMethod;
    private final boolean mapNullToDefault;

    public static class Builder {

        private MappingBuilderContext ctx;
        private SourceMethod method;
        private Map<String, ExecutableElement> unprocessedTargetProperties;
        private final List<PropertyMapping> propertyMappings = new ArrayList<PropertyMapping>();
        private final Set<Parameter> unprocessedSourceParameters = new HashSet<Parameter>();

        public Builder mappingContext(MappingBuilderContext mappingContext) {
            this.ctx = mappingContext;
            return this;
        }

        public Builder souceMethod(SourceMethod sourceMethod) {
            this.method = sourceMethod;
            this.unprocessedTargetProperties = initTargetPropertyAccessors();
            for ( Parameter sourceParameter : method.getSourceParameters() ) {
                unprocessedSourceParameters.add( sourceParameter );
            }
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
            NullValueMappingPrism prism = NullValueMappingPrism.getInstanceOn( method.getExecutable() );
            boolean mapNullToDefault =
                    MapperConfig.getInstanceOn( ctx.getMapperTypeElement() ).isMapToDefault( prism );

            MethodReference factoryMethod = AssignmentFactory.createFactoryMethod( method.getReturnType(), ctx );
            return new BeanMappingMethod( method, propertyMappings, factoryMethod, mapNullToDefault );
        }

        /**
         * This method builds the list of target accessors.
         */
        private Map<String, ExecutableElement> initTargetPropertyAccessors() {
            // fetch settings from element to implement
            CollectionMappingStrategyPrism cmStrategy = getEffectiveCollectionMappingStrategy();

            // collect all candidate target accessors
            List<ExecutableElement> candidates = new ArrayList<ExecutableElement>();
            candidates.addAll( method.getResultType().getSetters() );
            candidates.addAll( method.getResultType().getAlternativeTargetAccessors() );

            Map<String, ExecutableElement> targetProperties = new HashMap<String, ExecutableElement>();

            for ( ExecutableElement candidate : candidates ) {
                String targetPropertyName = Executables.getPropertyName( candidate );

                // A target access is in general a setter method on the target object. However, in case of collections,
                // the current target accessor can also be a getter method.
                // The following if block, checks if the target accessor should be overruled by an add method.
                if ( cmStrategy == CollectionMappingStrategyPrism.SETTER_PREFERRED
                    || cmStrategy == CollectionMappingStrategyPrism.ADDER_PREFERRED ) {

                    // first check if there's a setter method.
                    ExecutableElement adderMethod = null;
                    if ( Executables.isSetterMethod( candidate ) ) {
                        Type targetType = ctx.getTypeFactory().getSingleParameter( candidate ).getType();
                        // ok, the current accessor is a setter. So now the strategy determines what to use
                        if ( cmStrategy == CollectionMappingStrategyPrism.ADDER_PREFERRED ) {
                            adderMethod = method.getResultType().getAdderForType( targetType, targetPropertyName );
                        }
                    }
                    else if ( Executables.isGetterMethod( candidate ) ) {
                        // the current accessor is a getter (no setter available). But still, an add method is according
                        // to the above strategy (SETTER_PREFERRED || ADDER_PREFERRED) preferred over the getter.
                        Type targetType = ctx.getTypeFactory().getReturnType( candidate );
                        adderMethod = method.getResultType().getAdderForType( targetType, targetPropertyName );
                    }
                    if ( adderMethod != null ) {
                        // an adder has been found (according strategy) so overrule current choice.
                        candidate = adderMethod;
                    }
                }

                targetProperties.put( targetPropertyName, candidate );
            }

            return targetProperties;
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

            for ( Map.Entry<String, List<Mapping>> entry : method.getMappings().entrySet() ) {
                for ( Mapping mapping : entry.getValue() ) {

                    PropertyMapping propertyMapping = null;

                    // fetch the target property
                    ExecutableElement targetProperty = unprocessedTargetProperties.get( mapping.getTargetName() );
                    if ( targetProperty == null ) {
                        ctx.getMessager().printMessage(
                                Diagnostic.Kind.ERROR,
                                String.format( "Unknown property \"%s\" in return type.",
                                        mapping.getTargetName()
                                ),
                                method.getExecutable(),
                                mapping.getMirror(),
                                mapping.getSourceAnnotationValue()
                        );
                        errorOccurred = true;
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

                            if ( targetProperty != null ) {

                                // targetProperty == null can occur: we arrived here because we want as many errors
                                // as possible before we stop analysing
                                propertyMapping = new PropertyMappingBuilder()
                                        .mappingContext( ctx )
                                        .souceMethod( method )
                                        .targetAccessor( targetProperty )
                                        .targetPropertyName( mapping.getTargetName() )
                                        .sourceReference( sourceRef )
                                        .qualifiers( mapping.getQualifiers() )
                                        .dateFormat( mapping.getDateFormat() )
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
                    else if ( mapping.getConstant() != null && targetProperty != null ) {

                        propertyMapping = new ConstantMappingBuilder()
                                .mappingContext( ctx )
                                .sourceMethod( method )
                                .constantExpression( "\"" + mapping.getConstant() + "\"" )
                                .targetAccessor( targetProperty )
                                .dateFormat( mapping.getDateFormat() )
                                .qualifiers( mapping.getQualifiers() )
                                .build();
                        handledTargets.add( mapping.getTargetName() );
                    }

                    // its an expression
                    else if ( mapping.getJavaExpression() != null && targetProperty != null ) {

                        propertyMapping = new JavaExpressionMappingBuilder()
                                .mappingContext( ctx )
                                .souceMethod( method )
                                .javaExpression( mapping.getJavaExpression() )
                                .targetAccessor( targetProperty )
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

                        for ( ExecutableElement sourceAccessor : sourceParameter.getType().getGetters() ) {
                            String sourcePropertyName = Executables.getPropertyName( sourceAccessor );

                            if ( sourcePropertyName.equals( targetProperty.getKey() ) ) {
                                candidates.add( sourceAccessor );
                            }
                        }

                        PropertyMapping newPropertyMapping = null;
                        ExecutableElement sourceAccessor = getSourceAccessor( targetProperty.getKey(), candidates );
                        if ( sourceAccessor != null ) {
                            Mapping mapping = method.getSingleMappingByTargetPropertyName( targetProperty.getKey() );

                            SourceReference sourceRef = new SourceReference.BuilderFromProperty()
                                    .sourceParameter( sourceParameter )
                                    .type( ctx.getTypeFactory().getReturnType( sourceAccessor ) )
                                    .accessor( sourceAccessor )
                                    .name( targetProperty.getKey() )
                                    .build();

                            newPropertyMapping = new PropertyMappingBuilder()
                                    .mappingContext( ctx )
                                    .souceMethod( method )
                                    .targetAccessor( targetProperty.getValue() )
                                    .targetPropertyName( targetProperty.getKey() )
                                    .sourceReference( sourceRef )
                                    .qualifiers( mapping != null ? mapping.getQualifiers() : null )
                                    .dateFormat( mapping != null ? mapping.getDateFormat() : null )
                                    .build();

                            unprocessedSourceParameters.remove( sourceParameter );

                        }
                        // candidates are handled
                        candidates.clear();

                        if ( propertyMapping != null && newPropertyMapping != null ) {
                            // TODO improve error message
                            ctx.getMessager().printMessage(
                                    Diagnostic.Kind.ERROR,
                                    "Several possible source properties for target property \""
                                    + targetProperty.getKey()
                                    + "\".",
                                    method.getExecutable()
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
                                .souceMethod( method )
                                .targetAccessor( targetProperty.getValue() )
                                .targetPropertyName( targetProperty.getKey() )
                                .sourceReference( sourceRef )
                                .qualifiers( mapping != null ? mapping.getQualifiers() : null )
                                .dateFormat( mapping != null ? mapping.getDateFormat() : null )
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
                    Diagnostic.Kind.ERROR,
                    String.format( "Found several matching getters for property \"%s\"", sourcePropertyName ),
                    method.getExecutable()
                );

                return null;
            }
        }

       /**
         * Returns the effective policy for reporting unmapped getReturnType properties. If explicitly set via
         * {@code Mapper}, this value will be returned. Otherwise the value from the corresponding processor option will
         * be returned. If that is not set either, the default value from {@code Mapper#unmappedTargetPolicy()} will be
         * returned.
         *
         * @param element The type declaring the generated mapper type
         *
         * @return The effective policy for reporting unmapped target properties.
         */
        private ReportingPolicy getEffectiveUnmappedTargetPolicy() {
            MapperConfig mapperSettings = MapperConfig.getInstanceOn( ctx.getMapperTypeElement() );
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

        private CollectionMappingStrategyPrism getEffectiveCollectionMappingStrategy() {
            MapperConfig mapperSettings = MapperConfig.getInstanceOn( ctx.getMapperTypeElement() );
            return mapperSettings.getCollectionMappingStrategy();
        }



        private void reportErrorForUnmappedTargetPropertiesIfRequired( ) {

            // fetch settings from element to implement
            ReportingPolicy unmappedTargetPolicy = getEffectiveUnmappedTargetPolicy();

            if ( !unprocessedTargetProperties.isEmpty() && unmappedTargetPolicy.requiresReport() ) {

                ctx.getMessager().printMessage(
                        unmappedTargetPolicy.getDiagnosticKind(),
                        MessageFormat.format(
                                "Unmapped target {0,choice,1#property|1<properties}: \"{1}\"",
                                unprocessedTargetProperties.size(),
                                Strings.join( unprocessedTargetProperties.keySet(), ", " )
                        ),
                        method.getExecutable()
                );
            }
        }
    }

    private BeanMappingMethod(SourceMethod method,
                              List<PropertyMapping> propertyMappings,
                              MethodReference factoryMethod,
                              boolean mapNullToDefault ) {
        super( method );
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
            if (!sourceParam.getType().isPrimitive()) {
                sourceParameters.add( sourceParam );
            }
        }

        return sourceParameters;
    }

    public List<Parameter> getSourcePrimitiveParameters() {
        List<Parameter> sourceParameters = new ArrayList<Parameter>();
        for ( Parameter sourceParam : getSourceParameters() ) {
            if (sourceParam.getType().isPrimitive()) {
                sourceParameters.add( sourceParam );
            }
        }
        return sourceParameters;
    }


    public MethodReference getFactoryMethod() {
        return this.factoryMethod;
    }
}
