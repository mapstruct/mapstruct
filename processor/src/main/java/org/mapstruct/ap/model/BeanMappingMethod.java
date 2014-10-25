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

/**
 * A {@link MappingMethod} implemented by a {@link Mapper} class which maps one
 * bean type to another, optionally configured by one or more
 * {@link PropertyMapping}s.
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

        public Builder mappingContext(MappingBuilderContext mappingContext) {
            this.ctx = mappingContext;
            return this;
        }

        public Builder souceMethod(SourceMethod sourceMethod) {
            this.method = sourceMethod;
            return this;
        }

        public BeanMappingMethod build() {

            // fetch settings from element to implement
            ReportingPolicy unmappedTargetPolicy = getEffectiveUnmappedTargetPolicy();
            CollectionMappingStrategy cmStrategy = getEffectiveCollectionMappingStrategy();

            List<PropertyMapping> propertyMappings = new ArrayList<PropertyMapping>();
            Set<String> mappedTargetProperties = new HashSet<String>();
            Set<String> ignoredTargetProperties = new HashSet<String>();

            if ( !reportErrorIfMappedPropertiesDontExist() ) {
                return null;
            }

            // collect all target accessors
            List<ExecutableElement> targetAccessors = new ArrayList<ExecutableElement>();
            targetAccessors.addAll( method.getResultType().getSetters() );
            targetAccessors.addAll( method.getResultType().getAlternativeTargetAccessors() );

            for ( ExecutableElement targetAccessor : targetAccessors ) {

                String targetPropertyName = Executables.getPropertyName( targetAccessor );

                Mapping mapping = method.getMappingByTargetPropertyName( targetPropertyName );

                if ( mapping != null && mapping.isIgnored() ) {
                    ignoredTargetProperties.add( targetPropertyName );
                    continue;
                }

                // A target access is in general a setter method on the target object. However, in case of collections,
                // the current target accessor can also be a getter method.
                // The following if block, checks if the target accessor should be overruled by an add method.
                if ( cmStrategy == CollectionMappingStrategy.SETTER_PREFERRED
                    || cmStrategy == CollectionMappingStrategy.ADDER_PREFERRED ) {

                    // first check if there's a setter method.
                    ExecutableElement adderMethod = null;
                    if ( Executables.isSetterMethod( targetAccessor ) ) {
                        Type targetType = ctx.getTypeFactory().getSingleParameter( targetAccessor ).getType();
                        // ok, the current accessor is a setter. So now the strategy determines what to use
                        if ( cmStrategy == CollectionMappingStrategy.ADDER_PREFERRED ) {
                            adderMethod = method.getResultType().getAdderForType( targetType, targetPropertyName );
                        }
                    }
                    else if ( Executables.isGetterMethod( targetAccessor ) ) {
                        // the current accessor is a getter (no setter available). But still, an add method is according
                        // to the above strategy (SETTER_PREFERRED || ADDER_PREFERRED) preferred over the getter.
                        Type targetType = ctx.getTypeFactory().getReturnType( targetAccessor );
                        adderMethod = method.getResultType().getAdderForType( targetType, targetPropertyName );
                    }
                    if ( adderMethod != null ) {
                        // an adder has been found (according strategy) so overrule current choice.
                        targetAccessor = adderMethod;
                    }
                }

                PropertyMapping propertyMapping = null;
                if ( mapping != null ) {

                    if ( mapping.getSourceParameterName() != null ) {
                        // this is a parameterized property, so sourceParameter.property
                        Parameter parameter = method.getSourceParameter( mapping.getSourceParameterName() );

                        PropertyMapping.PropertyMappingBuilder builder = new PropertyMapping.PropertyMappingBuilder();
                        propertyMapping = builder
                            .mappingContext( ctx )
                            .souceMethod( method )
                            .targetAccessor( targetAccessor )
                            .targetPropertyName( targetPropertyName )
                            .parameter( parameter )
                            .build();

                    }
                    else if ( Executables.isSetterMethod( targetAccessor )
                        || Executables.isGetterMethod( targetAccessor ) ) {

                        if ( !mapping.getConstant().isEmpty() ) {
                            // its a constant
                            PropertyMapping.ConstantMappingBuilder builder =
                                new PropertyMapping.ConstantMappingBuilder();
                            propertyMapping = builder
                                .mappingContext( ctx )
                                .sourceMethod( method )
                                .constantExpression( "\"" + mapping.getConstant() + "\"" )
                                .targetAccessor( targetAccessor )
                                .dateFormat( mapping.getDateFormat() )
                                .qualifiers( mapping.getQualifiers() )
                                .build();
                        }

                        else if ( !mapping.getJavaExpression().isEmpty() ) {
                            // its an expression
                            PropertyMapping.JavaExpressionMappingBuilder builder =
                                new PropertyMapping.JavaExpressionMappingBuilder();
                            propertyMapping = builder
                                .mappingContext( ctx )
                                .souceMethod( method )
                                .javaExpression( mapping.getJavaExpression() )
                                .targetAccessor( targetAccessor )
                                .build();
                        }
                    }
                }

                if ( propertyMapping == null ) {
                    for ( Parameter sourceParameter : method.getSourceParameters() ) {
                        PropertyMapping.PropertyMappingBuilder builder = new PropertyMapping.PropertyMappingBuilder();
                        PropertyMapping newPropertyMapping = builder
                            .mappingContext( ctx )
                            .souceMethod( method )
                            .targetAccessor( targetAccessor )
                            .targetPropertyName( targetPropertyName )
                            .parameter( sourceParameter )
                            .build();

                        if ( propertyMapping != null && newPropertyMapping != null ) {
                            ctx.getMessager().printMessage(
                                Diagnostic.Kind.ERROR,
                                "Several possible source properties for target property \"" + targetPropertyName +
                                    "\".",
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
                    mappedTargetProperties.add( targetPropertyName );
                }
            }

            Set<String> targetProperties = Executables.getPropertyNames( targetAccessors );

            reportErrorForUnmappedTargetPropertiesIfRequired(
                method,
                unmappedTargetPolicy,
                targetProperties,
                mappedTargetProperties,
                ignoredTargetProperties
            );
            FactoryMethod factoryMethod = AssignmentFactory.createFactoryMethod( method.getReturnType(), ctx );
            return new BeanMappingMethod( method, propertyMappings, factoryMethod );
        }

        /**
         * Returns the effective policy for reporting unmapped getReturnType properties. If explicitly set via
         * {@code Mapper}, this value will be returned. Otherwise the value from the corresponding processor option will
         * be returned. If that is not set either, the default value from {@code Mapper#unmappedTargetPolicy()} will be
         * returned.
         *
         * @param element The type declaring the generated mapper type
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

        private boolean reportErrorIfMappedPropertiesDontExist() {
            // only report errors if this method itself is configured
            if ( method.isConfiguredByReverseMappingMethod() ) {
                return true;
            }

            // collect all target accessors
            List<ExecutableElement> targetAccessors = new ArrayList<ExecutableElement>();
            targetAccessors.addAll( method.getResultType().getSetters() );
            targetAccessors.addAll( method.getResultType().getAlternativeTargetAccessors() );

            Set<String> targetProperties = Executables.getPropertyNames( targetAccessors );

            boolean foundUnmappedProperty = false;

            for ( List<Mapping> mappedProperties : method.getMappings().values() ) {
                for ( Mapping mappedProperty : mappedProperties ) {
                    if ( mappedProperty.isIgnored() ) {
                        continue;
                    }

                    if ( mappedProperty.getSourceParameterName() != null ) {
                        Parameter sourceParameter = method.getSourceParameter(
                            mappedProperty.getSourceParameterName()
                        );

                        if ( sourceParameter == null ) {
                            ctx.getMessager().printMessage(
                                Diagnostic.Kind.ERROR,
                                String.format(
                                    "Method has no parameter named \"%s\".",
                                    mappedProperty.getSourceParameterName()
                                ),
                                method.getExecutable(),
                                mappedProperty.getMirror(),
                                mappedProperty.getSourceAnnotationValue()
                            );
                            foundUnmappedProperty = true;
                        }
                        else {
                            if ( !hasSourceProperty( sourceParameter, mappedProperty.getSourcePropertyName() ) ) {
                                ctx.getMessager().printMessage(
                                    Diagnostic.Kind.ERROR,
                                    String.format(
                                        "The type of parameter \"%s\" has no property named \"%s\".",
                                        mappedProperty.getSourceParameterName(),
                                        mappedProperty.getSourcePropertyName()
                                    ),
                                    method.getExecutable(),
                                    mappedProperty.getMirror(),
                                    mappedProperty.getSourceAnnotationValue()
                                );
                                foundUnmappedProperty = true;
                            }
                        }

                    }
                    else if ( mappedProperty.getSourcePropertyName() != null
                        && !hasSourceProperty( mappedProperty.getSourcePropertyName() ) ) {
                        ctx.getMessager().printMessage(
                            Diagnostic.Kind.ERROR,
                            String.format(
                                "No property named \"%s\" exists in source parameter(s).",
                                mappedProperty.getSourceName()
                            ),
                            method.getExecutable(),
                            mappedProperty.getMirror(),
                            mappedProperty.getSourceAnnotationValue()
                        );
                        foundUnmappedProperty = true;
                    }
                    if ( !targetProperties.contains( mappedProperty.getTargetName() ) ) {
                        ctx.getMessager().printMessage(
                            Diagnostic.Kind.ERROR,
                            String.format(
                                "Unknown property \"%s\" in return type %s.",
                                mappedProperty.getTargetName(),
                                method.getResultType()
                            ),
                            method.getExecutable(),
                            mappedProperty.getMirror(),
                            mappedProperty.getTargetAnnotationValue()
                        );
                        foundUnmappedProperty = true;
                    }
                }
            }
            return !foundUnmappedProperty;
        }

        private void reportErrorForUnmappedTargetPropertiesIfRequired(SourceMethod method,
                                                                      ReportingPolicy unmappedTargetPolicy,
                                                                      Set<String> targetProperties,
                                                                      Set<String> mappedTargetProperties,
                                                                      Set<String> ignoredTargetProperties) {

            Set<String> unmappedTargetProperties = new HashSet<String>();

            for ( String property : targetProperties ) {
                if ( !mappedTargetProperties.contains( property ) && !ignoredTargetProperties.contains( property ) ) {
                    unmappedTargetProperties.add( property );
                }
            }

            if ( !unmappedTargetProperties.isEmpty() && unmappedTargetPolicy.requiresReport() ) {
                ctx.getMessager().printMessage(
                    unmappedTargetPolicy.getDiagnosticKind(),
                    MessageFormat.format(
                        "Unmapped target {0,choice,1#property|1<properties}: \"{1}\"",
                        unmappedTargetProperties.size(),
                        Strings.join( unmappedTargetProperties, ", " )
                    ),
                    method.getExecutable()
                );
            }
        }

        private boolean hasSourceProperty(String propertyName) {
            for ( Parameter parameter : method.getSourceParameters() ) {
                if ( hasSourceProperty( parameter, propertyName ) ) {
                    return true;
                }
            }

            return false;
        }

        private boolean hasSourceProperty(Parameter parameter, String propertyName) {
            List<ExecutableElement> getters = parameter.getType().getGetters();
            return Executables.getPropertyNames( getters ).contains( propertyName );
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
}
