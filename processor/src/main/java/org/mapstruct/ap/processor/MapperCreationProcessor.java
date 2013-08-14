/**
 *  Copyright 2012-2013 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.processor;

import java.beans.Introspector;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Messager;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic.Kind;

import org.mapstruct.ap.MapperPrism;
import org.mapstruct.ap.conversion.ConversionProvider;
import org.mapstruct.ap.conversion.Conversions;
import org.mapstruct.ap.conversion.DefaultConversionContext;
import org.mapstruct.ap.model.BeanMappingMethod;
import org.mapstruct.ap.model.DefaultMapperReference;
import org.mapstruct.ap.model.IterableMappingMethod;
import org.mapstruct.ap.model.MapMappingMethod;
import org.mapstruct.ap.model.Mapper;
import org.mapstruct.ap.model.MapperReference;
import org.mapstruct.ap.model.MappingMethod;
import org.mapstruct.ap.model.MappingMethodReference;
import org.mapstruct.ap.model.Options;
import org.mapstruct.ap.model.Parameter;
import org.mapstruct.ap.model.PropertyMapping;
import org.mapstruct.ap.model.ReportingPolicy;
import org.mapstruct.ap.model.Type;
import org.mapstruct.ap.model.TypeConversion;
import org.mapstruct.ap.model.source.Mapping;
import org.mapstruct.ap.model.source.Method;
import org.mapstruct.ap.util.Executables;
import org.mapstruct.ap.util.Filters;
import org.mapstruct.ap.util.Strings;
import org.mapstruct.ap.util.TypeFactory;

/**
 * A {@link ModelElementProcessor} which creates a {@link Mapper} from the given
 * list of {@link Method}s.
 *
 * @author Gunnar Morling
 */
public class MapperCreationProcessor implements ModelElementProcessor<List<Method>, Mapper> {

    private static final String IMPLEMENTATION_SUFFIX = "Impl";

    private Elements elementUtils;
    private Messager messager;
    private Options options;

    private TypeFactory typeFactory;
    private Conversions conversions;
    private Executables executables;
    private Filters filters;

    @Override
    public Mapper process(ProcessorContext context, TypeElement mapperTypeElement, List<Method> sourceModel) {
        this.elementUtils = context.getElementUtils();
        this.messager = context.getMessager();
        this.options = context.getOptions();

        this.typeFactory = context.getTypeFactory();
        this.conversions = new Conversions( elementUtils, typeFactory );
        this.executables = new Executables( typeFactory );
        this.filters = new Filters( executables );

        return getMapper( mapperTypeElement, sourceModel );
    }

    @Override
    public int getPriority() {
        return 1000;
    }

    private Mapper getMapper(TypeElement element, List<Method> methods) {
        ReportingPolicy unmappedTargetPolicy = getEffectiveUnmappedTargetPolicy( element );
        List<MappingMethod> mappingMethods = getMappingMethods( methods, unmappedTargetPolicy );
        List<MapperReference> mapperReferences = getReferencedMappers( element );

        return new Mapper(
            typeFactory,
            elementUtils.getPackageOf( element ).getQualifiedName().toString(),
            element.getSimpleName().toString(),
            element.getSimpleName() + IMPLEMENTATION_SUFFIX,
            mappingMethods,
            mapperReferences,
            options
        );
    }

    /**
     * Returns the effective policy for reporting unmapped target properties. If
     * explicitly set via {@code Mapper}, this value will be returned. Otherwise
     * the value from the corresponding processor option will be returned. If
     * that is not set either, the default value from
     * {@code Mapper#unmappedTargetPolicy()} will be returned.
     *
     * @param element The type declaring the generated mapper type
     *
     * @return The effective policy for reporting unmapped target properties.
     */
    private ReportingPolicy getEffectiveUnmappedTargetPolicy(TypeElement element) {
        MapperPrism mapperPrism = MapperPrism.getInstanceOn( element );
        boolean setViaAnnotation = mapperPrism.values.unmappedTargetPolicy() != null;
        ReportingPolicy annotationValue = ReportingPolicy.valueOf( mapperPrism.unmappedTargetPolicy() );

        if ( setViaAnnotation ||
            options.getUnmappedTargetPolicy() == null ) {
            return annotationValue;
        }
        else {
            return options.getUnmappedTargetPolicy();
        }
    }

    private List<MapperReference> getReferencedMappers(TypeElement element) {
        List<MapperReference> mapperReferences = new LinkedList<MapperReference>();
        MapperPrism mapperPrism = MapperPrism.getInstanceOn( element );

        for ( TypeMirror usedMapper : mapperPrism.uses() ) {
            mapperReferences.add( new DefaultMapperReference( typeFactory.getType( usedMapper ) ) );
        }

        return mapperReferences;
    }

    private List<MappingMethod> getMappingMethods(List<Method> methods, ReportingPolicy unmappedTargetPolicy) {
        List<MappingMethod> mappingMethods = new ArrayList<MappingMethod>();

        for ( Method method : methods ) {
            if ( method.getDeclaringMapper() != null ) {
                continue;
            }

            reportErrorIfNoImplementationTypeIsRegisteredForInterfaceReturnType( method );

            Method reverseMappingMethod = getReverseMappingMethod( methods, method );

            if ( method.isIterableMapping() ) {
                if ( method.getIterableMapping() == null && reverseMappingMethod != null &&
                    reverseMappingMethod.getIterableMapping() != null ) {
                    method.setIterableMapping( reverseMappingMethod.getIterableMapping() );
                }
                mappingMethods.add( getIterableMappingMethod( methods, method ) );
            }
            else if ( method.isMapMapping() ) {
                if ( method.getMapMapping() == null && reverseMappingMethod != null &&
                    reverseMappingMethod.getMapMapping() != null ) {
                    method.setMapMapping( reverseMappingMethod.getMapMapping() );
                }
                mappingMethods.add( getMapMappingMethod( methods, method ) );
            }
            else {
                if ( method.getMappings().isEmpty() ) {
                    if ( reverseMappingMethod != null && !reverseMappingMethod.getMappings().isEmpty() ) {
                        method.setMappings( reverse( reverseMappingMethod.getMappings() ) );
                    }
                }
                MappingMethod beanMappingMethod = getBeanMappingMethod( methods, method, unmappedTargetPolicy );
                if ( beanMappingMethod != null ) {
                    mappingMethods.add( beanMappingMethod );
                }
            }
        }
        return mappingMethods;
    }

    private void reportErrorIfNoImplementationTypeIsRegisteredForInterfaceReturnType(Method method) {
        if ( method.getReturnType().getTypeMirror().getKind() != TypeKind.VOID &&
            method.getReturnType().isInterface() &&
            method.getReturnType().getImplementationType() == null ) {
            messager.printMessage(
                Kind.ERROR,
                String.format(
                    "No implementation type is registered for return type %s.",
                    method.getReturnType()
                ),
                method.getExecutable()
            );
        }
    }

    private Map<String, Mapping> reverse(Map<String, Mapping> mappings) {
        Map<String, Mapping> reversed = new HashMap<String, Mapping>();

        for ( Mapping mapping : mappings.values() ) {
            reversed.put( mapping.getTargetName(), mapping.reverse() );
        }
        return reversed;
    }

    private PropertyMapping getPropertyMapping(List<Method> methods, Method method, ExecutableElement setterMethod,
                                               Parameter parameter) {
        String targetPropertyName = executables.getPropertyName( setterMethod );
        Mapping mapping = method.getMapping( targetPropertyName );
        String dateFormat = mapping != null ? mapping.getDateFormat() : null;
        String sourcePropertyName = mapping != null ? mapping.getSourcePropertyName() : targetPropertyName;
        TypeElement parameterElement = parameter.getType().getTypeElement();
        List<ExecutableElement> sourceGetters = filters.getterMethodsIn(
            elementUtils.getAllMembers( parameterElement )
        );

        for ( ExecutableElement getter : sourceGetters ) {
            Mapping sourceMapping = method.getMappings().get( sourcePropertyName );
            boolean mapsToOtherTarget =
                sourceMapping != null && !sourceMapping.getTargetName().equals( targetPropertyName );
            if ( executables.getPropertyName( getter ).equals( sourcePropertyName ) && !mapsToOtherTarget ) {
                return getPropertyMapping(
                    methods,
                    method,
                    parameter,
                    getter,
                    setterMethod,
                    dateFormat
                );
            }
        }

        return null;
    }

    private MappingMethod getBeanMappingMethod(List<Method> methods, Method method,
                                               ReportingPolicy unmappedTargetPolicy) {
        List<PropertyMapping> propertyMappings = new ArrayList<PropertyMapping>();
        Set<String> mappedTargetProperties = new HashSet<String>();

        if ( !reportErrorIfMappedPropertiesDontExist( method ) ) {
            return null;
        }

        TypeElement resultTypeElement = method.getResultType().getTypeElement();
        List<ExecutableElement> targetSetters = filters.setterMethodsIn(
            elementUtils.getAllMembers( resultTypeElement )
        );

        for ( ExecutableElement setterMethod : targetSetters ) {
            String targetPropertyName = executables.getPropertyName( setterMethod );

            Mapping mapping = method.getMapping( targetPropertyName );

            PropertyMapping propertyMapping = null;
            if ( mapping != null && mapping.getSourceParameterName() != null ) {
                Parameter parameter = method.getSourceParameter( mapping.getSourceParameterName() );
                propertyMapping = getPropertyMapping( methods, method, setterMethod, parameter );
            }

            if ( propertyMapping == null ) {
                for ( Parameter sourceParameter : method.getSourceParameters() ) {
                    PropertyMapping newPropertyMapping = getPropertyMapping(
                        methods,
                        method,
                        setterMethod,
                        sourceParameter
                    );
                    if ( propertyMapping != null && newPropertyMapping != null ) {
                        messager.printMessage(
                            Kind.ERROR,
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

        Set<String> targetProperties = executables.getPropertyNames( targetSetters );

        reportErrorForUnmappedTargetPropertiesIfRequired(
            method,
            unmappedTargetPolicy,
            targetProperties,
            mappedTargetProperties
        );

        return new BeanMappingMethod( method, propertyMappings );
    }

    private void reportErrorForUnmappedTargetPropertiesIfRequired(Method method,
                                                                  ReportingPolicy unmappedTargetPolicy,
                                                                  Set<String> targetProperties,
                                                                  Set<String> mappedTargetProperties) {

        if ( targetProperties.size() > mappedTargetProperties.size() &&
            unmappedTargetPolicy.requiresReport() ) {
            targetProperties.removeAll( mappedTargetProperties );
            messager.printMessage(
                unmappedTargetPolicy.getDiagnosticKind(),
                MessageFormat.format(
                    "Unmapped target {0,choice,1#property|1<properties}: \"{1}\"",
                    targetProperties.size(),
                    Strings.join( targetProperties, ", " )
                ),
                method.getExecutable()
            );
        }
    }

    private Method getReverseMappingMethod(List<Method> rawMethods, Method method) {
        for ( Method oneMethod : rawMethods ) {
            if ( oneMethod.reverses( method ) ) {
                return oneMethod;
            }
        }
        return null;
    }

    private boolean hasSourceProperty(Method method, String propertyName) {
        for ( Parameter parameter : method.getSourceParameters() ) {
            if ( hasProperty( parameter, propertyName ) ) {
                return true;
            }
        }

        return false;
    }

    private boolean hasProperty(Parameter parameter, String propertyName) {
        TypeElement parameterTypeElement = parameter.getType().getTypeElement();
        List<ExecutableElement> getters = filters.setterMethodsIn(
            elementUtils.getAllMembers( parameterTypeElement )
        );

        return executables.getPropertyNames( getters ).contains( propertyName );
    }

    private boolean reportErrorIfMappedPropertiesDontExist(Method method) {
        TypeElement resultTypeElement = method.getResultType().getTypeElement();
        List<ExecutableElement> targetSetters = filters.setterMethodsIn(
            elementUtils.getAllMembers( resultTypeElement )
        );

        Set<String> targetProperties = executables.getPropertyNames( targetSetters );

        boolean foundUnmappedProperty = false;

        for ( Mapping mappedProperty : method.getMappings().values() ) {
            if ( mappedProperty.getSourceParameterName() != null ) {
                Parameter sourceParameter = method.getSourceParameter( mappedProperty.getSourceParameterName() );

                if ( sourceParameter == null ) {
                    messager.printMessage(
                        Kind.ERROR,
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
                    if ( !hasProperty( sourceParameter, mappedProperty.getSourcePropertyName() ) ) {
                        messager.printMessage(
                            Kind.ERROR,
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
            else if ( !hasSourceProperty(
                method,
                mappedProperty.getSourcePropertyName()
            ) ) {
                messager.printMessage(
                    Kind.ERROR,
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
                messager.printMessage(
                    Kind.ERROR,
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

        return !foundUnmappedProperty;
    }

    private PropertyMapping getPropertyMapping(List<Method> methods, Method method, Parameter parameter,
                                               ExecutableElement getterMethod, ExecutableElement setterMethod,
                                               String dateFormat) {
        Type sourceType = executables.retrieveReturnType( getterMethod );
        Type targetType = executables.retrieveSingleParameter( setterMethod ).getType();

        MappingMethodReference propertyMappingMethod = getMappingMethodReference( methods, sourceType, targetType );
        TypeConversion conversion = getConversion(
            sourceType,
            targetType,
            dateFormat,
            parameter.getName() + "." + getterMethod.getSimpleName().toString() + "()"
        );

        PropertyMapping property = new PropertyMapping(
            parameter.getName(),
            executables.getPropertyName( getterMethod ),
            getterMethod.getSimpleName().toString(),
            sourceType,
            executables.getPropertyName( setterMethod ),
            setterMethod.getSimpleName().toString(),
            targetType,
            propertyMappingMethod,
            conversion
        );

        reportErrorIfPropertyCanNotBeMapped(
            method,
            property
        );

        return property;
    }

    private MappingMethod getIterableMappingMethod(List<Method> methods, Method method) {
        Type sourceElementType = method.getSourceParameters().iterator().next().getType().getTypeParameters().get( 0 );
        Type targetElementType = method.getResultType().getTypeParameters().get( 0 );

        TypeConversion conversion = getConversion(
            sourceElementType,
            targetElementType,
            method.getIterableMapping() != null ? method.getIterableMapping().getDateFormat() : null,
            Strings.getSaveVariableName(
                Introspector.decapitalize( sourceElementType.getName() ),
                method.getParameterNames()
            )
        );

        return new IterableMappingMethod(
            method,
            getMappingMethodReference( methods, sourceElementType, targetElementType ),
            conversion
        );
    }

    private MappingMethod getMapMappingMethod(List<Method> methods, Method method) {
        List<Type> sourceTypeParams = method.getSourceParameters().iterator().next().getType().getTypeParameters();
        Type sourceKeyType = sourceTypeParams.get( 0 );
        Type sourceValueType = sourceTypeParams.get( 1 );

        List<Type> resultTypeParams = method.getResultType().getTypeParameters();
        Type targetKeyType = resultTypeParams.get( 0 );
        Type targetValueType = resultTypeParams.get( 1 );

        String keyDateFormat = method.getMapMapping() != null ? method.getMapMapping().getKeyFormat() : null;
        String valueDateFormat = method.getMapMapping() != null ? method.getMapMapping().getValueFormat() : null;

        TypeConversion keyConversion = getConversion( sourceKeyType, targetKeyType, keyDateFormat, "entry.getKey()" );
        TypeConversion valueConversion = getConversion(
            sourceValueType,
            targetValueType,
            valueDateFormat,
            "entry.getValue()"
        );

        MappingMethodReference keyMappingMethod = getMappingMethodReference( methods, sourceKeyType, targetKeyType );
        MappingMethodReference valueMappingMethod = getMappingMethodReference(
            methods,
            sourceValueType,
            targetValueType
        );

        return new MapMappingMethod( method, keyMappingMethod, keyConversion, valueMappingMethod, valueConversion );
    }

    private TypeConversion getConversion(Type sourceType, Type targetType, String dateFormat, String sourceReference) {
        ConversionProvider conversionProvider = conversions.getConversion( sourceType, targetType );

        if ( conversionProvider == null ) {
            return null;
        }

        return conversionProvider.to(
            sourceReference,
            new DefaultConversionContext( typeFactory, targetType, dateFormat )
        );
    }

    private MappingMethodReference getMappingMethodReference(Iterable<Method> methods, Type parameterType,
                                                             Type returnType) {
        for ( Method method : methods ) {
            if ( method.getSourceParameters().size() > 1 ) {
                continue;
            }

            Parameter singleSourceParam = method.getSourceParameters().iterator().next();

            if ( singleSourceParam.getType().equals( parameterType ) && method.getResultType().equals( returnType ) ) {
                return new MappingMethodReference( method );
            }
        }

        return null;
    }

    /**
     * Reports an error if source and target type of the property are different
     * and neither a mapping method nor a conversion exists nor the property is
     * of a collection type with default implementation
     *
     * @param method The mapping method owning the property mapping.
     * @param property The property mapping to check.
     */
    private void reportErrorIfPropertyCanNotBeMapped(Method method, PropertyMapping property) {
        if ( property.getSourceType().equals( property.getTargetType() ) ||
            property.getMappingMethod() != null ||
            property.getConversion() != null ||
            property.getTargetType().getImplementationType() != null ) {
            return;
        }

        messager.printMessage(
            Kind.ERROR,
            String.format(
                "Can't map property \"%s %s\" to \"%s %s\".",
                property.getSourceType().getName(),
                property.getSourceName(),
                property.getTargetType().getName(),
                property.getTargetName()
            ),
            method.getExecutable()
        );
    }
}
