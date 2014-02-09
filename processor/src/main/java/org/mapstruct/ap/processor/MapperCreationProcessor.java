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
package org.mapstruct.ap.processor;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic.Kind;

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
import org.mapstruct.ap.model.MethodReference;
import org.mapstruct.ap.model.PropertyMapping;
import org.mapstruct.ap.model.TypeConversion;
import org.mapstruct.ap.model.common.Parameter;
import org.mapstruct.ap.model.common.Type;
import org.mapstruct.ap.model.common.TypeFactory;
import org.mapstruct.ap.model.source.Mapping;
import org.mapstruct.ap.model.source.Method;
import org.mapstruct.ap.model.source.MethodMatcher;
import org.mapstruct.ap.option.Options;
import org.mapstruct.ap.option.ReportingPolicy;
import org.mapstruct.ap.prism.MapperPrism;
import org.mapstruct.ap.util.Executables;
import org.mapstruct.ap.util.Filters;
import org.mapstruct.ap.util.Strings;

/**
 * A {@link ModelElementProcessor} which creates a {@link Mapper} from the given
 * list of {@link Method}s.
 *
 * @author Gunnar Morling
 */
public class MapperCreationProcessor implements ModelElementProcessor<List<Method>, Mapper> {

    private Elements elementUtils;
    private Types typeUtils;
    private Messager messager;
    private Options options;

    private TypeFactory typeFactory;
    private Conversions conversions;

    @Override
    public Mapper process(ProcessorContext context, TypeElement mapperTypeElement, List<Method> sourceModel) {
        this.elementUtils = context.getElementUtils();
        this.typeUtils = context.getTypeUtils();
        this.messager = context.getMessager();
        this.options = context.getOptions();

        this.typeFactory = context.getTypeFactory();
        this.conversions = new Conversions( elementUtils, typeFactory );

        return getMapper( mapperTypeElement, sourceModel );
    }

    @Override
    public int getPriority() {
        return 1000;
    }

    private Mapper getMapper(TypeElement element, List<Method> methods) {
        ReportingPolicy unmappedTargetPolicy = getEffectiveUnmappedTargetPolicy( element );
        List<MapperReference> mapperReferences = getReferencedMappers( element );
        List<MappingMethod> mappingMethods = getMappingMethods( mapperReferences, methods, unmappedTargetPolicy );

        return new Mapper.Builder()
            .element( element )
            .mappingMethods( mappingMethods )
            .mapperReferences( mapperReferences )
            .suppressGeneratorTimestamp( options.isSuppressGeneratorTimestamp() )
            .typeFactory( typeFactory )
            .elementUtils( elementUtils )
            .build();
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
        List<String> variableNames = new LinkedList<String>();

        MapperPrism mapperPrism = MapperPrism.getInstanceOn( element );

        for ( TypeMirror usedMapper : mapperPrism.uses() ) {
            DefaultMapperReference mapperReference = DefaultMapperReference.getInstance(
                typeFactory.getType( usedMapper ),
                MapperPrism.getInstanceOn( typeUtils.asElement( usedMapper ) ) != null,
                typeFactory,
                variableNames
            );

            mapperReferences.add( mapperReference );
            variableNames.add( mapperReference.getVariableName() );
        }

        return mapperReferences;
    }

    private List<MappingMethod> getMappingMethods(List<MapperReference> mapperReferences, List<Method> methods,
                                                  ReportingPolicy unmappedTargetPolicy) {
        List<MappingMethod> mappingMethods = new ArrayList<MappingMethod>();

        for ( Method method : methods ) {
            if ( !method.requiresImplementation() ) {
                continue;
            }


            Method reverseMappingMethod = getReverseMappingMethod( methods, method );

            boolean hasFactoryMethod = false;
            if ( method.isIterableMapping() ) {
                if ( method.getIterableMapping() == null && reverseMappingMethod != null &&
                    reverseMappingMethod.getIterableMapping() != null ) {
                    method.setIterableMapping( reverseMappingMethod.getIterableMapping() );
                }
                IterableMappingMethod iterableMappingMethod
                    = getIterableMappingMethod( mapperReferences, methods, method );
                hasFactoryMethod = iterableMappingMethod.getFactoryMethod() != null;
                mappingMethods.add( iterableMappingMethod );
            }
            else if ( method.isMapMapping() ) {
                if ( method.getMapMapping() == null && reverseMappingMethod != null &&
                    reverseMappingMethod.getMapMapping() != null ) {
                    method.setMapMapping( reverseMappingMethod.getMapMapping() );
                }
                MapMappingMethod mapMappingMethod = getMapMappingMethod( mapperReferences, methods, method );
                hasFactoryMethod = mapMappingMethod.getFactoryMethod() != null;
                mappingMethods.add( mapMappingMethod );
            }
            else {
                if ( method.getMappings().isEmpty() ) {
                    if ( reverseMappingMethod != null && !reverseMappingMethod.getMappings().isEmpty() ) {
                        method.setMappings( reverse( reverseMappingMethod.getMappings() ) );
                    }
                }
                BeanMappingMethod beanMappingMethod = getBeanMappingMethod(
                    mapperReferences,
                    methods,
                    method,
                    unmappedTargetPolicy
                );
                if ( beanMappingMethod != null ) {
                    hasFactoryMethod = beanMappingMethod.getFactoryMethod() != null;
                    mappingMethods.add( beanMappingMethod );
                }
            }

            if ( !hasFactoryMethod ) {
                // A factory method  is allowed to return an interface type and hence, the generated
                // implementation as well. The check below must only be executed if there's no factory
                // method that could be responsible.
                reportErrorIfNoImplementationTypeIsRegisteredForInterfaceReturnType( method );
            }
        }
        return mappingMethods;
    }

    private MethodReference getFactoryMethod(List<MapperReference> mapperReferences, List<Method> methods,
                                             Type returnType) {
        MethodReference result = null;
        for ( Method method : methods ) {
            if ( !method.requiresImplementation() && !method.isIterableMapping() && !method.isMapMapping()
                && method.getMappings().isEmpty() && method.getParameters().isEmpty() ) {
                if ( method.getReturnType().equals( returnType ) ) {
                    if ( result == null ) {
                        MapperReference mapperReference = null;
                        for ( MapperReference ref : mapperReferences ) {
                            if ( ref.getMapperType().equals( method.getDeclaringMapper() ) ) {
                                mapperReference = ref;
                                break;
                            }
                        }

                        result = new MethodReference( method, mapperReference );
                    }
                    else {
                        messager.printMessage(
                            Kind.ERROR,
                            String.format(
                                "Ambiguous factory methods: \"%s\" conflicts with \"%s\".",
                                result,
                                method
                            ),
                            method.getExecutable()
                        );
                    }
                }
            }
        }
        return result;
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

    private Map<String, List<Mapping>> reverse(Map<String, List<Mapping>> mappings) {
        Map<String, List<Mapping>> reversed = new HashMap<String, List<Mapping>>();

        for ( List<Mapping> mappingList : mappings.values() ) {
            for ( Mapping mapping : mappingList ) {
                if ( !reversed.containsKey( mapping.getTargetName() ) ) {
                    reversed.put( mapping.getTargetName(), new ArrayList<Mapping>() );
                }
                reversed.get( mapping.getTargetName() ).add( mapping.reverse() );
            }
        }
        return reversed;
    }

    private PropertyMapping getPropertyMapping(List<MapperReference> mapperReferences, List<Method> methods,
                                               Method method, ExecutableElement targetAcessor, Parameter parameter) {
        String targetPropertyName = Executables.getPropertyName( targetAcessor );
        Mapping mapping = method.getMapping( targetPropertyName );
        String dateFormat = mapping != null ? mapping.getDateFormat() : null;
        String sourcePropertyName = mapping != null ? mapping.getSourcePropertyName() : targetPropertyName;
        TypeElement parameterElement = parameter.getType().getTypeElement();
        List<ExecutableElement> sourceGetters = Filters.getterMethodsIn(
            elementUtils.getAllMembers( parameterElement )
        );

        for ( ExecutableElement sourceAccessor : sourceGetters ) {

            List<Mapping> sourceMappings = method.getMappings().get( sourcePropertyName );
            if ( method.getMappings().containsKey( sourcePropertyName ) ) {
                for ( Mapping sourceMapping : sourceMappings ) {
                    boolean mapsToOtherTarget = !sourceMapping.getTargetName().equals( targetPropertyName );
                    if ( Executables.getPropertyName( sourceAccessor ).equals( sourcePropertyName ) &&
                        !mapsToOtherTarget ) {
                        return getPropertyMapping(
                            mapperReferences,
                            methods,
                            method,
                            parameter,
                            sourceAccessor,
                            targetAcessor,
                            dateFormat
                        );
                    }
                }
            }
            else if ( Executables.getPropertyName( sourceAccessor ).equals( sourcePropertyName ) ) {
                return getPropertyMapping(
                    mapperReferences,
                    methods,
                    method,
                    parameter,
                    sourceAccessor,
                    targetAcessor,
                    dateFormat
                );
            }
        }

        return null;
    }

    private BeanMappingMethod getBeanMappingMethod(List<MapperReference> mapperReferences, List<Method> methods,
                                                   Method method, ReportingPolicy unmappedTargetPolicy) {
        List<PropertyMapping> propertyMappings = new ArrayList<PropertyMapping>();
        Set<String> mappedTargetProperties = new HashSet<String>();

        if ( !reportErrorIfMappedPropertiesDontExist( method ) ) {
            return null;
        }

        TypeElement resultTypeElement = method.getResultType().getTypeElement();
        List<ExecutableElement> targetAccessors = Filters.setterMethodsIn(
            elementUtils.getAllMembers( resultTypeElement )
        );
        targetAccessors.addAll(
            alternativeTargetAccessorMethodsIn(
                elementUtils.getAllMembers( resultTypeElement )
            )
        );

        for ( ExecutableElement targetAccessor : targetAccessors ) {
            String targetPropertyName = Executables.getPropertyName( targetAccessor );

            Mapping mapping = method.getMapping( targetPropertyName );

            PropertyMapping propertyMapping = null;
            if ( mapping != null && mapping.getSourceParameterName() != null ) {
                Parameter parameter = method.getSourceParameter( mapping.getSourceParameterName() );
                propertyMapping = getPropertyMapping( mapperReferences, methods, method, targetAccessor, parameter );
            }

            if ( propertyMapping == null ) {
                for ( Parameter sourceParameter : method.getSourceParameters() ) {
                    PropertyMapping newPropertyMapping = getPropertyMapping(
                        mapperReferences,
                        methods,
                        method,
                        targetAccessor,
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

        Set<String> targetProperties = Executables.getPropertyNames( targetAccessors );

        reportErrorForUnmappedTargetPropertiesIfRequired(
            method,
            unmappedTargetPolicy,
            targetProperties,
            mappedTargetProperties
        );

        MethodReference factoryMethod = getFactoryMethod( mapperReferences, methods, method.getReturnType() );
        return new BeanMappingMethod( method, propertyMappings, factoryMethod );
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
            if ( hasSourceProperty( parameter, propertyName ) ) {
                return true;
            }
        }

        return false;
    }

    private boolean hasSourceProperty(Parameter parameter, String propertyName) {
        TypeElement parameterTypeElement = parameter.getType().getTypeElement();
        List<ExecutableElement> getters = Filters.getterMethodsIn(
            elementUtils.getAllMembers( parameterTypeElement )
        );

        return Executables.getPropertyNames( getters ).contains( propertyName );
    }

    private boolean reportErrorIfMappedPropertiesDontExist(Method method) {
        // only report errors if this method itself is configured
        if ( method.isConfiguredByReverseMappingMethod() ) {
            return true;
        }

        TypeElement resultTypeElement = method.getResultType().getTypeElement();
        List<ExecutableElement> targetAccessors = Filters.setterMethodsIn(
            elementUtils.getAllMembers( resultTypeElement )
        );
        targetAccessors.addAll(
            alternativeTargetAccessorMethodsIn(
                elementUtils.getAllMembers( resultTypeElement )
            )
        );
        Set<String> targetProperties = Executables.getPropertyNames( targetAccessors );

        boolean foundUnmappedProperty = false;

        for ( List<Mapping> mappedProperties : method.getMappings().values() ) {
            for ( Mapping mappedProperty : mappedProperties ) {
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
                        if ( !hasSourceProperty( sourceParameter, mappedProperty.getSourcePropertyName() ) ) {
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
        }
        return !foundUnmappedProperty;
    }

    private PropertyMapping getPropertyMapping(List<MapperReference> mapperReferences, List<Method> methods,
                                               Method method, Parameter parameter, ExecutableElement sourceAccessor,
                                               ExecutableElement targetAcessor, String dateFormat) {
        Type sourceType = typeFactory.getReturnType( sourceAccessor );
        Type targetType = null;
        String conversionString = null;
        conversionString = parameter.getName() + "." + sourceAccessor.getSimpleName().toString() + "()";

        if ( Executables.isSetterMethod( targetAcessor ) ) {
            targetType = typeFactory.getSingleParameter( targetAcessor ).getType();
        }
        else if ( Executables.isGetterMethod( targetAcessor ) ) {
            targetType = typeFactory.getReturnType( targetAcessor );
        }

        MethodReference propertyMappingMethod = getMappingMethodReference(
            method,
            "property '" + Executables.getPropertyName( sourceAccessor ) + "'",
            mapperReferences,
            methods,
            sourceType,
            targetType
        );
        TypeConversion conversion = getConversion(
            sourceType,
            targetType,
            dateFormat,
            conversionString
        );

        PropertyMapping property = new PropertyMapping(
            parameter.getName(),
            Executables.getPropertyName( sourceAccessor ),
            sourceAccessor.getSimpleName().toString(),
            sourceType,
            Executables.getPropertyName( targetAcessor ),
            targetAcessor.getSimpleName().toString(),
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

    private IterableMappingMethod getIterableMappingMethod(List<MapperReference> mapperReferences, List<Method> methods,
                                                           Method method) {
        Type sourceElementType = method.getSourceParameters().iterator().next().getType().getTypeParameters().get( 0 );
        Type targetElementType = method.getResultType().getTypeParameters().get( 0 );

        TypeConversion conversion = getConversion(
            sourceElementType,
            targetElementType,
            method.getIterableMapping() != null ? method.getIterableMapping().getDateFormat() : null,
            Strings.getSaveVariableName(
                sourceElementType.getName(),
                method.getParameterNames()
            )
        );

        MethodReference elementMappingMethod =
            getMappingMethodReference(
                method,
                "collection element",
                mapperReferences,
                methods,
                sourceElementType,
                targetElementType
            );

        if ( !sourceElementType.isAssignableTo( targetElementType ) && conversion == null &&
            elementMappingMethod == null ) {
            messager.printMessage(
                Kind.ERROR,
                String.format(
                    "Can't create implementation of method %s. Found no method nor built-in conversion for mapping "
                        + "source element type into target element type.",
                    method
                ),
                method.getExecutable()
            );
        }

        MethodReference factoryMethod = getFactoryMethod( mapperReferences, methods, method.getReturnType() );
        return new IterableMappingMethod(
            method,
            elementMappingMethod,
            conversion,
            factoryMethod
        );
    }

    private MapMappingMethod getMapMappingMethod(List<MapperReference> mapperReferences, List<Method> methods,
                                                 Method method) {
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

        MethodReference keyMappingMethod = getMappingMethodReference(
            method,
            "map key",
            mapperReferences,
            methods,
            sourceKeyType,
            targetKeyType
        );
        MethodReference valueMappingMethod = getMappingMethodReference(
            method,
            "map value",
            mapperReferences,
            methods,
            sourceValueType,
            targetValueType
        );

        if ( !sourceKeyType.isAssignableTo( targetKeyType ) && keyConversion == null && keyMappingMethod == null ) {
            messager.printMessage(
                Kind.ERROR,
                String.format(
                    "Can't create implementation of method %s. Found no method nor built-in conversion for mapping "
                        + "source key type to target key type.",
                    method
                ),
                method.getExecutable()
            );
        }

        if ( !sourceValueType.isAssignableTo( targetValueType ) && valueConversion == null &&
            valueMappingMethod == null ) {
            messager.printMessage(
                Kind.ERROR,
                String.format(
                    "Can't create implementation of method %s. Found no method nor built-in conversion for mapping "
                        + "source value type to target value type.",
                    method
                ),
                method.getExecutable()
            );
        }

        MethodReference factoryMethod = getFactoryMethod( mapperReferences, methods, method.getReturnType() );
        return new MapMappingMethod(
            method, keyMappingMethod, keyConversion, valueMappingMethod, valueConversion,
            factoryMethod
        );
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

    private MethodReference getMappingMethodReference(Method mappingMethod, String mappedElement,
                                                      List<MapperReference> mapperReferences,
                                                      Iterable<Method> methods, Type parameterType,
                                                      Type returnType) {
        List<Method> candidatesWithMathingTargetType = new ArrayList<Method>();

        for ( Method method : methods ) {
            if ( method.getSourceParameters().size() != 1 ) {
                continue;
            }

            MethodMatcher m = new MethodMatcher( typeUtils, method, returnType, parameterType );
            if ( m.matches() ) {
                candidatesWithMathingTargetType.add( method );
            }
        }

        List<Method> candidatesWithBestMatchingSourceType = new ArrayList<Method>();
        int bestMatchingSourceTypeDistance = Integer.MAX_VALUE;

        // find the methods with the minimum distance regarding source parameter type
        for ( Method method : candidatesWithMathingTargetType ) {
            Parameter singleSourceParam = method.getSourceParameters().iterator().next();

            int sourceTypeDistance = parameterType.distanceTo( singleSourceParam.getType() );
            bestMatchingSourceTypeDistance =
                addToCandidateListIfMinimal(
                    candidatesWithBestMatchingSourceType,
                    bestMatchingSourceTypeDistance,
                    method,
                    sourceTypeDistance
                );
        }

        if ( candidatesWithBestMatchingSourceType.isEmpty() ) {
            return null;
        }

        // print a warning if we find more than one method with minimum source type distance
        if ( candidatesWithBestMatchingSourceType.size() > 1 ) {
            messager.printMessage(
                Kind.ERROR,
                String.format(
                    "Ambiguous mapping methods found for mapping " + mappedElement + " from %s to %s: %s.",
                    parameterType,
                    returnType,
                    Strings.join( candidatesWithBestMatchingSourceType, ", " )
                ),
                mappingMethod.getExecutable()
            );
        }

        Method method = candidatesWithBestMatchingSourceType.get( 0 );
        MapperReference mapperReference = null;

        for ( MapperReference ref : mapperReferences ) {
            if ( ref.getMapperType().equals( method.getDeclaringMapper() ) ) {
                mapperReference = ref;
                break;
            }
        }

        return new MethodReference( method, mapperReference );
    }

    private int addToCandidateListIfMinimal(List<Method> candidatesWithBestMathingType, int bestMatchingTypeDistance,
                                            Method method, int currentTypeDistance) {
        if ( currentTypeDistance == bestMatchingTypeDistance ) {
            candidatesWithBestMathingType.add( method );
        }
        else if ( currentTypeDistance < bestMatchingTypeDistance ) {
            bestMatchingTypeDistance = currentTypeDistance;

            candidatesWithBestMathingType.clear();
            candidatesWithBestMathingType.add( method );
        }
        return bestMatchingTypeDistance;
    }

    /**
     * Reports an error if source the property can't be mapped from source to target. A mapping if possible if one of
     * the following conditions is true:
     * <ul>
     * <li>the source type is assignable to the target type</li>
     * <li>a mapping method exists</li>
     * <li>a built-in conversion exists</li>
     * <li>the property is of a collection or map type and the constructor of the target type (either itself or its
     * implementation type) accepts the source type.</li>
     * </ul>
     *
     * @param method The mapping method owning the property mapping.
     * @param property The property mapping to check.
     */
    private void reportErrorIfPropertyCanNotBeMapped(Method method, PropertyMapping property) {
        boolean collectionOrMapTargetTypeHasCompatibleConstructor = false;

        if ( property.getTargetType().isCollectionType() || property.getTargetType().isMapType() ) {
            if ( property.getTargetType().getImplementationType() != null ) {
                collectionOrMapTargetTypeHasCompatibleConstructor = hasCompatibleConstructor(
                    property.getSourceType(),
                    property.getTargetType().getImplementationType()
                );
            }
            else {
                collectionOrMapTargetTypeHasCompatibleConstructor = hasCompatibleConstructor(
                    property.getSourceType(),
                    property.getTargetType()
                );
            }
        }

        if ( property.getSourceType().isAssignableTo( property.getTargetType() ) ||
            property.getMappingMethod() != null ||
            property.getConversion() != null ||
            ( ( property.getTargetType().isCollectionType() || property.getTargetType().isMapType() ) &&
                collectionOrMapTargetTypeHasCompatibleConstructor ) ) {
            return;
        }

        messager.printMessage(
            Kind.ERROR,
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

    /**
     * Whether the given target type has a single-argument constructor which accepts the given source type.
     *
     * @param sourceType the source type
     * @param targetType the target type
     *
     * @return {@code true} if the target type has a constructor accepting the given source type, {@code false}
     *         otherwise.
     */
    private boolean hasCompatibleConstructor(Type sourceType, Type targetType) {
        List<ExecutableElement> targetTypeConstructors = ElementFilter.constructorsIn(
            targetType.getTypeElement()
                .getEnclosedElements()
        );

        for ( ExecutableElement constructor : targetTypeConstructors ) {
            if ( constructor.getParameters().size() != 1 ) {
                continue;
            }

            //get the constructor resolved against the type arguments of specific target type
            ExecutableType typedConstructor = (ExecutableType) typeUtils.asMemberOf(
                (DeclaredType) targetType.getTypeMirror(), constructor
            );

            if ( typeUtils.isAssignable(
                sourceType.getTypeMirror(),
                typedConstructor.getParameterTypes().iterator().next()
            ) ) {
                return true;
            }
        }

        return false;
    }

    /**
     * A getter could be an alternative target-accessor if a setter is not available, and the
     * target is a collection.
     *
     * Provided such a getter is initialized lazy by the target class, e.g. in generated JAXB beans.
     *
     * @param elements
     *
     * @return
     */
    private List<ExecutableElement> alternativeTargetAccessorMethodsIn(Iterable<? extends Element> elements) {
        List<ExecutableElement> setterMethods = Filters.setterMethodsIn( elements );
        List<ExecutableElement> getterMethods = Filters.getterMethodsIn( elements );
        List<ExecutableElement> alternativeTargetAccessorsMethods = new LinkedList<ExecutableElement>();

        if ( getterMethods.size() > setterMethods.size() ) {
            // there could be a getter method for a list that is not present as setter.
            // a getter could substitute the setter in that case and act as setter.
            // (assuming it is initialized)
            for ( ExecutableElement getterMethod : getterMethods ) {
                boolean matchFound = false;
                String getterPropertyName = Executables.getPropertyName( getterMethod );
                for ( ExecutableElement setterMethod : setterMethods ) {
                    String setterPropertyName = Executables.getPropertyName( setterMethod );
                    if ( getterPropertyName.equals( setterPropertyName ) ) {
                        matchFound = true;
                        break;
                    }
                }
                if ( !matchFound && typeFactory.getReturnType( getterMethod ).isCollectionType() ) {
                    alternativeTargetAccessorsMethods.add( getterMethod );
                }
            }
        }

        return alternativeTargetAccessorsMethods;
    }
}
