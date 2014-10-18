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
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.lang.model.element.ExecutableElement;
import javax.tools.Diagnostic;

import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.ap.model.common.Parameter;
import org.mapstruct.ap.model.common.Type;
import org.mapstruct.ap.model.source.Mapping;
import org.mapstruct.ap.model.source.SourceMethod;
import org.mapstruct.ap.option.ReportingPolicy;
import org.mapstruct.ap.util.Executables;
import org.mapstruct.ap.util.MapperConfig;
import org.mapstruct.ap.util.Strings;

import org.mapstruct.ap.model.PropertyMapping.PropertyMappingBuilder;
import org.mapstruct.ap.model.PropertyMapping.ConstantMappingBuilder;
import org.mapstruct.ap.model.PropertyMapping.JavaExpressionMappingBuilder;
import org.mapstruct.ap.model.source.SourceReference;

/**
 * A {@link MappingMethod} implemented by a {@link Mapper} class which maps one
 bean sourceParameter to another, optionally configured by one or more
 {@link PropertyMapping}s.
 *
 * @author Gunnar Morling
 */
public class BeanMappingMethod extends MappingMethod {

    private final List<PropertyMapping> propertyMappings;
    private final Map<String, List<PropertyMapping>> mappingsByParameter;
    private final List<PropertyMapping> constantMappings;


    private final FactoryMethod factoryMethod;


    public static class Builder {

        private MappingBuilderContext ctx;
        private SourceMethod method;

        private final Map<String, TargetProperty> remainingTargetProperties = new HashMap<String, TargetProperty>();
        private final List<PropertyMapping> propertyMappings = new ArrayList<PropertyMapping>();


        public Builder mappingContext(MappingBuilderContext mappingContext) {
            this.ctx = mappingContext;
            return this;
        }

        public Builder souceMethod(SourceMethod sourceMethod) {
            this.method = sourceMethod;
            return this;
        }

        public BeanMappingMethod build() {

            // init all non ignored targetAccessors
            initTargetPropertyAccessors();

            // map properties with mapping
            boolean mappingErrorOccured = handleDefinedSourceMappings();
            if ( mappingErrorOccured ) {
                return null;
            }

            // map properties without a mapping
            applyPropertyNameBasedMapping();

            // report errors on unmapped properties
            reportErrorForUnmappedTargetPropertiesIfRequired(  );


            FactoryMethod factoryMethod = AssignmentFactory.createFactoryMethod( method.getReturnType(), ctx );
            return new BeanMappingMethod( method, propertyMappings, factoryMethod );
        }


        /**
         * This method builds the list of target accessors.
         */
        private void initTargetPropertyAccessors() {

            // fetch settings from element to implement
            CollectionMappingStrategy cmStrategy = getEffectiveCollectionMappingStrategy();

            // collect all candidate target accessors
            List<ExecutableElement> candidates = new ArrayList<ExecutableElement>();
            candidates.addAll( method.getResultType().getSetters() );
            candidates.addAll( method.getResultType().getAlternativeTargetAccessors() );

            for ( ExecutableElement candidate : candidates ) {

                String targetPropertyName = Executables.getPropertyName( candidate );

                // A target access is in general a setter method on the target object. However, in case of collections,
                // the current target accessor can also be a getter method.
                // The following if block, checks if the target accessor should be overruled by an add method.
                if ( cmStrategy == CollectionMappingStrategy.SETTER_PREFERRED
                    || cmStrategy == CollectionMappingStrategy.ADDER_PREFERRED ) {

                    // first check if there's a setter method.
                    ExecutableElement adderMethod = null;
                    if ( Executables.isSetterMethod( candidate ) ) {
                        Type targetType = ctx.getTypeFactory().getSingleParameter( candidate ).getType();
                        // ok, the current accessor is a setter. So now the strategy determines what to use
                        if ( cmStrategy == CollectionMappingStrategy.ADDER_PREFERRED ) {
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

                remainingTargetProperties.put( targetPropertyName, new TargetProperty(targetPropertyName, candidate ) );
            }
        }

        /**
         * Iterates over all defined mapping methods (@Mappings, @Mapping), either direct or via
         *
         * @InheritInverseConfiguration.
         *
         * If a match is found between a defined source (constant, expression, ignore or source, the mapping is
         * removed from the remain target properties.
         *
         * This method should check if the mappings are defined correctly. When an error occurs, the method continues in
         * search of more problems.
         */
        private boolean handleDefinedSourceMappings() {

            boolean errorOccurred = false;

            Set<String> handledTargets = new HashSet<String>();

            for ( Map.Entry<String, List<Mapping>> entry : method.getMappings().entrySet() ) {
                for ( Mapping mapping : entry.getValue() ) {

                    PropertyMapping propertyMapping = null;

                    // fetch the target property
                    TargetProperty targetProperty = remainingTargetProperties.get( mapping.getTargetName() );
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
                                        .targetAccessor( targetProperty.getAccessor() )
                                        .targetPropertyName( targetProperty.getName() )
                                        .sourceReference( sourceRef )
                                        .qualifiers( mapping.getQualifiers() )
                                        .dateFormat( mapping.getDateFormat() )
                                        .build();
                                handledTargets.add( mapping.getTargetName() );
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
                                .targetAccessor( targetProperty.getAccessor() )
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
                                .targetAccessor( targetProperty.getAccessor() )
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
                // In order to avoid: "Unknown property <> in return sourceParameter" in case of duplicate
                // target mappings
                remainingTargetProperties.remove( handledTarget );
            }

            return errorOccurred;
        }


        /**
         * Iterates over all target properties and all source parameters.
         *
         * When a property name match occurs, the remainder will be checked for duplicates. Matches will
         * be removed from the set of remaining target properties.
         */
        private void applyPropertyNameBasedMapping() {

            Collection<TargetProperty> targetProperties = remainingTargetProperties.values();
            for ( TargetProperty targetProperty : new ArrayList<TargetProperty>( targetProperties ) ) {

                PropertyMapping propertyMapping = null;
                if ( propertyMapping == null ) {
                    for ( Parameter sourceParameter : method.getSourceParameters() ) {

                        PropertyMapping newPropertyMapping = null;
                        for ( ExecutableElement sourceAccessor : sourceParameter.getType().getGetters() ) {
                            String sourcePropertyName = Executables.getPropertyName( sourceAccessor );
                            if ( sourcePropertyName.equals( targetProperty.getName() ) ) {

                                Mapping mapping = method.getSingleMappingByTargetPropertyName( sourcePropertyName );

                                SourceReference sourceRef = new SourceReference.BuilderFromProperty()
                                        .sourceParameter( sourceParameter )
                                        .type( ctx.getTypeFactory().getReturnType( sourceAccessor ) )
                                        .accessor( sourceAccessor )
                                        .name( sourcePropertyName )
                                        .build();

                                newPropertyMapping = new PropertyMappingBuilder()
                                        .mappingContext( ctx )
                                        .souceMethod( method )
                                        .targetAccessor( targetProperty.getAccessor() )
                                        .targetPropertyName( targetProperty.getName() )
                                        .sourceReference( sourceRef )
                                        .qualifiers( mapping != null ? mapping.getQualifiers() : null )
                                        .dateFormat( mapping != null ? mapping.getDateFormat() : null )
                                        .build();
                                break;
                            }
                        }

                        if ( propertyMapping != null && newPropertyMapping != null ) {
                            // TODO improve error message
                            ctx.getMessager().printMessage(
                                    Diagnostic.Kind.ERROR,
                                    "Several possible source properties for target property \""
                                    + targetProperty.getName()
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
                    remainingTargetProperties.remove( targetProperty.getName() );
                }
            }
        }

       /**
         * Returns the effective policy for reporting unmapped getReturnType properties. If explicitly set via
         * {@code Mapper}, this value will be returned. Otherwise the value from the corresponding processor option will
         * be returned. If that is not set either, the default value from {@code Mapper#unmappedTargetPolicy()} will be
         * returned.
         *
         * @param element The sourceParameter declaring the generated mapper sourceParameter
         *
         * @return The effective policy for reporting unmapped getReturnType properties.
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

        private CollectionMappingStrategy getEffectiveCollectionMappingStrategy() {
            MapperConfig mapperSettings = MapperConfig.getInstanceOn( ctx.getMapperTypeElement() );
            return mapperSettings.getCollectionMappingStrategy();
        }



        private void reportErrorForUnmappedTargetPropertiesIfRequired( ) {

            // fetch settings from element to implement
            ReportingPolicy unmappedTargetPolicy = getEffectiveUnmappedTargetPolicy();

            if ( !remainingTargetProperties.isEmpty() && unmappedTargetPolicy.requiresReport() ) {

                ctx.getMessager().printMessage(
                        unmappedTargetPolicy.getDiagnosticKind(),
                        MessageFormat.format(
                                "Unmapped target {0,choice,1#property|1<properties}: \"{1}\"",
                                remainingTargetProperties.size(),
                                Strings.join( remainingTargetProperties.keySet(), ", " )
                        ),
                        method.getExecutable()
                );
            }
        }

    }

    private BeanMappingMethod(SourceMethod method,
                              List<PropertyMapping> propertyMappings,
                              FactoryMethod factoryMethod) {
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

    @Override
    public Set<Type> getImportTypes() {
        Set<Type> types = super.getImportTypes();

        for ( PropertyMapping propertyMapping : propertyMappings ) {
            types.addAll( propertyMapping.getImportTypes() );
        }

        return types;
    }

    public FactoryMethod getFactoryMethod() {
        return this.factoryMethod;
    }


    public static class TargetProperty {

        private final String name;
        private final ExecutableElement accessor;

        public TargetProperty( String name, ExecutableElement accessor ) {
            this.name = name;
            this.accessor = accessor;
        }

        public String getName() {
            return name;
        }

        public ExecutableElement getAccessor() {
            return accessor;
        }
    }

}
