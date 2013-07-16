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
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
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
import org.mapstruct.ap.model.PropertyMapping;
import org.mapstruct.ap.model.ReportingPolicy;
import org.mapstruct.ap.model.Type;
import org.mapstruct.ap.model.TypeConversion;
import org.mapstruct.ap.model.source.Mapping;
import org.mapstruct.ap.model.source.Method;
import org.mapstruct.ap.model.source.Parameter;
import org.mapstruct.ap.util.Executables;
import org.mapstruct.ap.util.Filters;
import org.mapstruct.ap.util.Strings;
import org.mapstruct.ap.util.TypeUtil;

/**
 * A {@link ModelElementProcessor} which creates a {@link Mapper} from the given
 * list of {@link Method}s.
 *
 * @author Gunnar Morling
 */
public class MapperCreationProcessor implements ModelElementProcessor<List<Method>, Mapper> {

    private static final String IMPLEMENTATION_SUFFIX = "Impl";

    private Elements elementUtils;
    private Types typeUtils;
    private Messager messager;
    private Options options;

    private TypeUtil typeUtil;
    private Conversions conversions;
    private Executables executables;

    @Override
    public Mapper process(ProcessorContext context, TypeElement mapperTypeElement, List<Method> sourceModel) {
        this.elementUtils = context.getElementUtils();
        this.typeUtils = context.getTypeUtils();
        this.messager = context.getMessager();
        this.options = context.getOptions();

        this.typeUtil = new TypeUtil( context.getElementUtils(), context.getTypeUtils() );
        this.conversions = new Conversions( elementUtils, typeUtils, typeUtil );
        this.executables = new Executables( typeUtil );

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
            mapperReferences.add( new DefaultMapperReference( typeUtil.retrieveType( usedMapper ) ) );
        }

        return mapperReferences;
    }

    private List<MappingMethod> getMappingMethods(List<Method> methods, ReportingPolicy unmappedTargetPolicy) {
        List<MappingMethod> mappingMethods = new ArrayList<MappingMethod>();

        for ( Method method : methods ) {
            if ( method.getDeclaringMapper() != null ) {
                continue;
            }

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
                mappingMethods.add( getBeanMappingMethod( methods, method, unmappedTargetPolicy ) );
            }
        }
        return mappingMethods;
    }

    private Map<String, Mapping> reverse(Map<String, Mapping> mappings) {
        Map<String, Mapping> reversed = new HashMap<String, Mapping>();

        for ( Mapping mapping : mappings.values() ) {
            reversed.put( mapping.getTargetName(), mapping.reverse() );
        }
        return reversed;
    }

    private MappingMethod getBeanMappingMethod(List<Method> methods, Method method,
                                               ReportingPolicy unmappedTargetPolicy) {
        List<PropertyMapping> propertyMappings = new ArrayList<PropertyMapping>();
        Set<String> mappedTargetProperties = new HashSet<String>();

        Map<String, Mapping> mappings = method.getMappings();

        TypeElement resultTypeElement = elementUtils.getTypeElement( method.getResultType().getCanonicalName() );
        TypeElement parameterElement = elementUtils.getTypeElement( method.getSingleSourceType().getCanonicalName() );

        List<ExecutableElement> sourceGetters = Filters.getterMethodsIn(
            elementUtils.getAllMembers( parameterElement )
        );
        List<ExecutableElement> targetSetters = Filters.setterMethodsIn(
            elementUtils.getAllMembers( resultTypeElement )
        );

        Set<String> sourceProperties = executables.getPropertyNames(
            Filters.getterMethodsIn( sourceGetters )
        );
        Set<String> targetProperties = executables.getPropertyNames(
            Filters.setterMethodsIn( targetSetters )
        );

        reportErrorIfMappedPropertiesDontExist( method, sourceProperties, targetProperties );

        for ( ExecutableElement getterMethod : sourceGetters ) {
            String sourcePropertyName = executables.getPropertyName( getterMethod );
            Mapping mapping = mappings.get( sourcePropertyName );
            String dateFormat = mapping != null ? mapping.getDateFormat() : null;

            for ( ExecutableElement setterMethod : targetSetters ) {
                String targetPropertyName = executables.getPropertyName( setterMethod );

                if ( targetPropertyName.equals( mapping != null ? mapping.getTargetName() : sourcePropertyName ) ) {
                    PropertyMapping property = getPropertyMapping(
                        methods,
                        method,
                        getterMethod,
                        setterMethod,
                        dateFormat
                    );

                    propertyMappings.add( property );

                    mappedTargetProperties.add( targetPropertyName );
                }
            }
        }

        reportErrorForUnmappedTargetPropertiesIfRequired(
            method,
            unmappedTargetPolicy,
            targetProperties,
            mappedTargetProperties
        );

        return new BeanMappingMethod(
            method.getName(),
            method.getParameters(),
            method.getSourceParameters(),
            method.getResultType(),
            method.getResultName(),
            method.getReturnType(),
            propertyMappings
        );
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

    private void reportErrorIfMappedPropertiesDontExist(Method method, Set<String> sourceProperties,
                                                        Set<String> targetProperties) {
        for ( Mapping mappedProperty : method.getMappings().values() ) {
            if ( !sourceProperties.contains( mappedProperty.getSourceName() ) ) {
                messager.printMessage(
                    Kind.ERROR,
                    String.format(
                        "Unknown property \"%s\" in parameter type %s.",
                        mappedProperty.getSourceName(),
                        method.getSingleSourceType()
                    ),
                    method.getExecutable(),
                    mappedProperty.getMirror(),
                    mappedProperty.getSourceAnnotationValue()
                );
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
            }
        }
    }

    private PropertyMapping getPropertyMapping(List<Method> methods, Method method, ExecutableElement getterMethod,
                                               ExecutableElement setterMethod, String dateFormat) {
        Type sourceType = executables.retrieveReturnType( getterMethod );
        Type targetType = executables.retrieveSingleParameter( setterMethod ).getType();

        MappingMethodReference propertyMappingMethod = getMappingMethodReference( methods, sourceType, targetType );
        TypeConversion conversion = getConversion(
            sourceType,
            targetType,
            dateFormat,
            method.getSourceParameters().get( 0 ).getName() + "."
                + getterMethod.getSimpleName().toString() + "()"
        );

        PropertyMapping property = new PropertyMapping(
            method.getSourceParameters().get( 0 ).getName(),
            method.getResultName(),
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
        Type sourceElementType = method.getSourceParameters().get( 0 ).getType().getTypeParameters().get( 0 );
        Type targetElementType = method.getResultType().getTypeParameters().get( 0 );

        TypeConversion conversion = getConversion(
            sourceElementType,
            targetElementType,
            method.getIterableMapping() != null ? method.getIterableMapping().getDateFormat() : null,
            Introspector.decapitalize( sourceElementType.getName() )
        );

        return new IterableMappingMethod(
            method.getName(),
            method.getParameters(),
            method.getSourceParameters(),
            method.getResultType(),
            method.getResultName(),
            method.getReturnType(),
            getMappingMethodReference( methods, sourceElementType, targetElementType ),
            conversion
        );
    }

    private MappingMethod getMapMappingMethod(List<Method> methods, Method method) {
        List<Type> sourceTypeParams = method.getSourceParameters().get( 0 ).getType().getTypeParameters();
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

        return new MapMappingMethod(
            method.getName(),
            method.getParameters(),
            method.getSourceParameters(),
            method.getResultType(),
            method.getResultName(),
            method.getReturnType(),
            keyMappingMethod,
            keyConversion,
            valueMappingMethod,
            valueConversion
        );
    }

    private TypeConversion getConversion(Type sourceType, Type targetType, String dateFormat, String sourceReference) {
        ConversionProvider conversionProvider = conversions.getConversion( sourceType, targetType );

        if ( conversionProvider == null ) {
            return null;
        }

        return conversionProvider.to( sourceReference, new DefaultConversionContext( targetType, dateFormat ) );
    }

    private MappingMethodReference getMappingMethodReference(Iterable<Method> methods, Type parameterType,
                                                             Type returnType) {
        for ( Method oneMethod : methods ) {
            Parameter singleSourceParam = oneMethod.getSourceParameters().get( 0 );

            if ( singleSourceParam.getType().equals( parameterType ) &&
                oneMethod.getReturnType().equals( returnType ) ) {
                return new MappingMethodReference(
                    oneMethod.getDeclaringMapper(),
                    oneMethod.getName()
                );
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
            ( property.getTargetType().isCollectionType() &&
                property.getTargetType().getCollectionImplementationType() != null ) ) {
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
