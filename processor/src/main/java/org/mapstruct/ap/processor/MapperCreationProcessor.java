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
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import org.mapstruct.ap.MapperPrism;
import org.mapstruct.ap.conversion.Conversion;
import org.mapstruct.ap.conversion.Conversions;
import org.mapstruct.ap.model.BeanMappingMethod;
import org.mapstruct.ap.model.DefaultMapperReference;
import org.mapstruct.ap.model.IterableMappingMethod;
import org.mapstruct.ap.model.Mapper;
import org.mapstruct.ap.model.MapperReference;
import org.mapstruct.ap.model.MappingMethod;
import org.mapstruct.ap.model.MappingMethodReference;
import org.mapstruct.ap.model.Options;
import org.mapstruct.ap.model.PropertyMapping;
import org.mapstruct.ap.model.ReportingPolicy;
import org.mapstruct.ap.model.Type;
import org.mapstruct.ap.model.source.Mapping;
import org.mapstruct.ap.model.source.Method;
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

    private boolean isErroneous = false;

    @Override
    public Mapper process(ProcessorContext context, TypeElement mapperTypeElement, List<Method> sourceElement) {
        this.elementUtils = context.getElementUtils();
        this.typeUtils = context.getTypeUtils();
        this.messager = context.getMessager();
        this.options = context.getOptions();

        this.typeUtil = new TypeUtil( context.getElementUtils(), context.getTypeUtils() );
        this.conversions = new Conversions( elementUtils, typeUtils, typeUtil );
        this.executables = new Executables( typeUtil );

        return getMapper( mapperTypeElement, sourceElement );
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
            options,
            isErroneous
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

            if ( method.getMappings().isEmpty() ) {
                Method reverseMappingMethod = getReverseMappingMethod( methods, method );
                if ( reverseMappingMethod != null && !reverseMappingMethod.getMappings().isEmpty() ) {
                    method.setMappings( reverse( reverseMappingMethod.getMappings() ) );
                }
            }

            if ( method.isIterableMapping() ) {
                mappingMethods.add( getIterableMappingMethod( methods, method ) );
            }
            else {
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

        TypeElement returnTypeElement = (TypeElement) typeUtils.asElement( method.getExecutable().getReturnType() );
        TypeElement parameterElement = (TypeElement) typeUtils.asElement(
            method.getExecutable()
                .getParameters()
                .get( 0 )
                .asType()
        );

        List<ExecutableElement> sourceGetters = Filters.getterMethodsIn(
            elementUtils.getAllMembers( parameterElement )
        );
        List<ExecutableElement> targetSetters = Filters.setterMethodsIn(
            elementUtils.getAllMembers( returnTypeElement )
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

            for ( ExecutableElement setterMethod : targetSetters ) {
                String targetPropertyName = executables.getPropertyName( setterMethod );

                if ( targetPropertyName.equals( mapping != null ? mapping.getTargetName() : sourcePropertyName ) ) {
                    PropertyMapping property = getPropertyMapping(
                        methods,
                        method,
                        getterMethod,
                        setterMethod
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
            method.getParameterName(),
            method.getSourceType(),
            method.getTargetType(),
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
            printMessage(
                unmappedTargetPolicy,
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
                printMessage(
                    ReportingPolicy.ERROR,
                    String.format(
                        "Unknown property \"%s\" in parameter type %s.",
                        mappedProperty.getSourceName(),
                        method.getSourceType()
                    ), method.getExecutable(), mappedProperty.getMirror(), mappedProperty.getSourceAnnotationValue()
                );
            }
            if ( !targetProperties.contains( mappedProperty.getTargetName() ) ) {
                printMessage(
                    ReportingPolicy.ERROR,
                    String.format(
                        "Unknown property \"%s\" in return type %s.",
                        mappedProperty.getTargetName(),
                        method.getTargetType()
                    ), method.getExecutable(), mappedProperty.getMirror(), mappedProperty.getTargetAnnotationValue()
                );
            }
        }
    }

    private PropertyMapping getPropertyMapping(List<Method> methods, Method method, ExecutableElement getterMethod,
                                               ExecutableElement setterMethod) {
        Type sourceType = executables.retrieveReturnType( getterMethod );
        Type targetType = executables.retrieveParameter( setterMethod ).getType();

        MappingMethodReference propertyMappingMethod = getMappingMethodReference( methods, sourceType, targetType );
        Conversion conversion = conversions.getConversion(
            sourceType,
            targetType
        );

        PropertyMapping property = new PropertyMapping(
            method.getParameterName(),
            Introspector.decapitalize( method.getTargetType().getName() ),
            executables.getPropertyName( getterMethod ),
            getterMethod.getSimpleName().toString(),
            sourceType,
            executables.getPropertyName( setterMethod ),
            setterMethod.getSimpleName().toString(),
            targetType,
            propertyMappingMethod,
            conversion != null ? conversion.to(
                method.getParameterName() + "." + getterMethod.getSimpleName().toString() + "()",
                targetType
            ) : null
        );

        reportErrorIfPropertyCanNotBeMapped(
            method,
            property
        );

        return property;
    }

    private MappingMethod getIterableMappingMethod(List<Method> methods, Method method) {
        String toConversionString = getIterableConversionString(
            conversions,
            method.getSourceType().getElementType(),
            method.getTargetType().getElementType(),
            true
        );

        return new IterableMappingMethod(
            method.getName(),
            method.getParameterName(),
            method.getSourceType(),
            method.getTargetType(),
            getMappingMethodReference(
                methods,
                method.getSourceType().getElementType(),
                method.getTargetType().getElementType()
            ),
            toConversionString
        );
    }

    private String getIterableConversionString(Conversions conversions, Type sourceElementType, Type targetElementType,
                                               boolean isToConversion) {
        Conversion conversion = conversions.getConversion( sourceElementType, targetElementType );

        if ( conversion == null ) {
            return null;
        }

        return conversion.to(
            Introspector.decapitalize( sourceElementType.getName() ),
            targetElementType
        );
    }

    private MappingMethodReference getMappingMethodReference(Iterable<Method> methods, Type parameterType,
                                                             Type returnType) {
        for ( Method oneMethod : methods ) {
            if ( oneMethod.getSourceType().equals( parameterType ) && oneMethod.getTargetType().equals( returnType ) ) {
                return new MappingMethodReference(
                    oneMethod.getDeclaringMapper(),
                    oneMethod.getName(),
                    oneMethod.getParameterName(),
                    oneMethod.getSourceType(),
                    oneMethod.getTargetType()
                );
            }
        }

        return null;
    }

    private void reportErrorIfPropertyCanNotBeMapped(Method method, PropertyMapping property) {
        if ( property.getSourceType().equals( property.getTargetType() ) ) {
            return;
        }

        //no mapping method nor conversion nor collection with default implementation
        if ( !(
            property.getMappingMethod() != null ||
                property.getConversion() != null ||
                ( property.getTargetType().isCollectionType() && property.getTargetType()
                    .getCollectionImplementationType() != null ) ) ) {

            printMessage(
                ReportingPolicy.ERROR,
                String.format(
                    "Can't map property \"%s %s\" to \"%s %s\".",
                    property.getSourceType(),
                    property.getSourceName(),
                    property.getTargetType(),
                    property.getTargetName()
                ),
                method.getExecutable()
            );
        }
    }

    private void printMessage(ReportingPolicy reportingPolicy, String message, Element element) {
        messager.printMessage( reportingPolicy.getDiagnosticKind(), message, element );
        if ( reportingPolicy.failsBuild() ) {
            isErroneous = true;
        }
    }

    private void printMessage(ReportingPolicy reportingPolicy, String message, Element element,
                              AnnotationMirror annotationMirror, AnnotationValue annotationValue) {
        messager
            .printMessage(
                reportingPolicy.getDiagnosticKind(),
                message,
                element,
                annotationMirror,
                annotationValue
            );
        if ( reportingPolicy.failsBuild() ) {
            isErroneous = true;
        }
    }
}
