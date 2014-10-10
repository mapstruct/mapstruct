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
package org.mapstruct.ap.processor.creation;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.lang.model.element.ExecutableElement;
import javax.tools.Diagnostic;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.ap.model.BeanMappingMethod;
import org.mapstruct.ap.model.FactoryMethod;
import org.mapstruct.ap.model.PropertyMapping;
import org.mapstruct.ap.model.common.Parameter;
import org.mapstruct.ap.model.common.Type;
import org.mapstruct.ap.model.source.Mapping;
import org.mapstruct.ap.model.source.SourceMethod;
import org.mapstruct.ap.option.ReportingPolicy;
import org.mapstruct.ap.util.Executables;
import org.mapstruct.ap.util.MapperConfig;
import org.mapstruct.ap.util.Strings;

/**
 * This class is responsible for building bean mapping methods.
 *
 * @author Sjaak Derksen
 */
public class BeanMappingBuilder extends MappingBuilder {

    public BeanMappingBuilder( MappingContext ctx ) {
        super( ctx );
    }

    public BeanMappingMethod getBeanMappingMethod( SourceMethod method ) {

        // fetch settings from element to implement
        ReportingPolicy unmappedTargetPolicy = getEffectiveUnmappedTargetPolicy( );
        CollectionMappingStrategy cmStrategy = getEffectiveCollectionMappingStrategy( );

        List<PropertyMapping> propertyMappings = new ArrayList<PropertyMapping>();
        Set<String> mappedTargetProperties = new HashSet<String>();
        Set<String> ignoredTargetProperties = new HashSet<String>();

        if ( !reportErrorIfMappedPropertiesDontExist( method ) ) {
            return null;
        }

        // collect all target accessors
        List<ExecutableElement> targetAccessors = new ArrayList<ExecutableElement>();
        targetAccessors.addAll( method.getResultType().getSetters() );
        targetAccessors.addAll( method.getResultType().getAlternativeTargetAccessors() );

        for ( ExecutableElement targetAccessor : targetAccessors ) {

            PropertyMappingBuilder propertyMappingBuilder = new PropertyMappingBuilder( getMappingContext() );

            String targetPropertyName = Executables.getPropertyName( targetAccessor );

            Mapping mapping = method.getMappingByTargetPropertyName( targetPropertyName );

            if ( mapping != null && mapping.isIgnored() ) {
                ignoredTargetProperties.add( targetPropertyName );
                continue;
            }

            // A target access is in general a setter method on the target object. However, in case of collections,
            // the current target accessor can also be a getter method.
            //
            // The following if block, checks if the target accessor should be overruled by an add method.
            if ( cmStrategy.equals( CollectionMappingStrategy.SETTER_PREFERRED ) ||
                cmStrategy.equals( CollectionMappingStrategy.ADDER_PREFERRED ) ) {

                // first check if there's a setter method.
                ExecutableElement adderMethod = null;
                if ( Executables.isSetterMethod( targetAccessor ) ) {
                    Type targetType = getTypeFactory().getSingleParameter( targetAccessor ).getType();
                    // ok, the current accessor is a setter. So now the strategy determines what to use
                    if ( cmStrategy.equals( CollectionMappingStrategy.ADDER_PREFERRED ) ) {
                        adderMethod = method.getResultType().getAdderForType( targetType, targetPropertyName );
                    }
                }
                else if ( Executables.isGetterMethod( targetAccessor ) ) {
                    // the current accessor is a getter (no setter available). But still, an add method is according
                    // to the above strategy (SETTER_PREFERRED || ADDER_PREFERRED) preferred over the getter.
                    Type targetType = getTypeFactory().getReturnType( targetAccessor );
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
                    propertyMapping = propertyMappingBuilder.buildPropertyMappingForParameter(
                            method,
                            targetAccessor,
                            targetPropertyName,
                            parameter
                    );
                }
                else if ( Executables.isSetterMethod( targetAccessor )  ||
                          Executables.isGetterMethod( targetAccessor ) ) {

                    if ( !mapping.getConstant().isEmpty() ) {
                        // its a constant
                        propertyMapping = propertyMappingBuilder.buildConstantMapping(
                                method,
                                "\"" + mapping.getConstant() + "\"",
                                targetAccessor,
                                mapping.getDateFormat(),
                                mapping.getQualifiers()
                        );
                    }

                    else if ( !mapping.getJavaExpression().isEmpty() ) {
                        // its an expression
                        propertyMapping = propertyMappingBuilder.buildJavaExpressionMapping(
                                method,
                                mapping.getJavaExpression(),
                                targetAccessor
                        );
                    }
                }
            }

            if ( propertyMapping == null ) {
                for ( Parameter sourceParameter : method.getSourceParameters() ) {
                    PropertyMapping newPropertyMapping = propertyMappingBuilder.buildPropertyMappingForParameter(
                        method,
                        targetAccessor,
                        targetPropertyName,
                        sourceParameter
                    );
                    if ( propertyMapping != null && newPropertyMapping != null ) {
                        getMessager().printMessage(
                            Diagnostic.Kind.ERROR,
                            "Several possible source properties for target property \"" + targetPropertyName + "\".",
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

        FactoryMethod factoryMethod = getFactoryMethod( method.getReturnType() );
        return new BeanMappingMethod( method, propertyMappings, factoryMethod );
    }


   /**
     * Returns the effective policy for reporting unmapped getReturnType properties. If
     * explicitly set via {@code Mapper}, this value will be returned. Otherwise
     * the value from the corresponding processor option will be returned. If
     * that is not set either, the default value from
     * {@code Mapper#unmappedTargetPolicy()} will be returned.
     *
     * @param element The type declaring the generated mapper type
     *
     * @return The effective policy for reporting unmapped getReturnType properties.
     */
    private ReportingPolicy getEffectiveUnmappedTargetPolicy( ) {
        MapperConfig mapperSettings = MapperConfig.getInstanceOn( getMapperTypeElement() );
        boolean setViaAnnotation = mapperSettings.isSetUnmappedTargetPolicy();
        ReportingPolicy annotationValue = ReportingPolicy.valueOf( mapperSettings.unmappedTargetPolicy() );

        if ( setViaAnnotation ||
            getOptions().getUnmappedTargetPolicy() == null ) {
            return annotationValue;
        }
        else {
            return getOptions().getUnmappedTargetPolicy();
        }
    }

    private CollectionMappingStrategy getEffectiveCollectionMappingStrategy( ) {
        MapperConfig mapperSettings = MapperConfig.getInstanceOn( getMapperTypeElement());
        return mapperSettings.getCollectionMappingStrategy();
    }

  private boolean reportErrorIfMappedPropertiesDontExist(SourceMethod method) {
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
                    Parameter sourceParameter = method.getSourceParameter( mappedProperty.getSourceParameterName() );

                    if ( sourceParameter == null ) {
                        getMessager().printMessage(
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
                            getMessager().printMessage(
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
                else if ( mappedProperty.getConstant().isEmpty() &&
                    mappedProperty.getJavaExpression().isEmpty() &&
                    !hasSourceProperty( method, mappedProperty.getSourcePropertyName() ) ) {
                    getMessager().printMessage(
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
                    getMessager().printMessage(
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
            getMessager().printMessage(
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

    private boolean hasSourceProperty(SourceMethod method, String propertyName) {
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
