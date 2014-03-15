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

import org.mapstruct.ap.model.ParameterAssignment;
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
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic.Kind;

import org.mapstruct.ap.model.BeanMappingMethod;
import org.mapstruct.ap.model.Decorator;
import org.mapstruct.ap.model.DefaultMapperReference;
import org.mapstruct.ap.model.DelegatingMethod;
import org.mapstruct.ap.model.EnumMappingMethod;
import org.mapstruct.ap.model.IterableMappingMethod;
import org.mapstruct.ap.model.MapMappingMethod;
import org.mapstruct.ap.model.Mapper;
import org.mapstruct.ap.model.MapperReference;
import org.mapstruct.ap.model.MappingMethod;
import org.mapstruct.ap.model.MethodReference;
import org.mapstruct.ap.model.PropertyMapping;
import org.mapstruct.ap.model.common.Parameter;
import org.mapstruct.ap.model.common.Type;
import org.mapstruct.ap.model.common.TypeFactory;
import org.mapstruct.ap.model.source.EnumMapping;
import org.mapstruct.ap.model.source.Mapping;
import org.mapstruct.ap.model.source.SourceMethod;
import org.mapstruct.ap.model.source.selector.MethodSelectors;
import org.mapstruct.ap.option.Options;
import org.mapstruct.ap.option.ReportingPolicy;
import org.mapstruct.ap.prism.DecoratedWithPrism;
import org.mapstruct.ap.prism.MapperPrism;
import org.mapstruct.ap.util.Executables;
import org.mapstruct.ap.util.MapperConfig;
import org.mapstruct.ap.util.Strings;

/**
 * A {@link ModelElementProcessor} which creates a {@link Mapper} from the given
 * list of {@link SourceMethod}s.
 *
 * @author Gunnar Morling
 */
public class MapperCreationProcessor implements ModelElementProcessor<List<SourceMethod>, Mapper> {

    private Elements elementUtils;
    private Types typeUtils;
    private Messager messager;
    private Options options;

    private TypeFactory typeFactory;

    private MappingResolver mappingResolver;



    @Override
    public Mapper process(ProcessorContext context, TypeElement mapperTypeElement, List<SourceMethod> sourceModel) {
        this.elementUtils = context.getElementUtils();
        this.typeUtils = context.getTypeUtils();
        this.messager = context.getMessager();
        this.options = context.getOptions();

        this.typeFactory = context.getTypeFactory();

        this.mappingResolver = new MappingResolver(messager, typeFactory, elementUtils, typeUtils );


        return getMapper( mapperTypeElement, sourceModel );
    }

    @Override
    public int getPriority() {
        return 1000;
    }

    private Mapper getMapper(TypeElement element, List<SourceMethod> methods) {
        ReportingPolicy unmappedTargetPolicy = getEffectiveUnmappedTargetPolicy( element );
        List<MapperReference> mapperReferences = getReferencedMappers( element );
        List<MappingMethod> mappingMethods = getMappingMethods( mapperReferences, methods, unmappedTargetPolicy );
        mappingMethods.addAll( mappingResolver.getVirtualMethodsToGenerate() );

        Mapper mapper = new Mapper.Builder()
            .element( element )
            .mappingMethods( mappingMethods )
            .mapperReferences( mapperReferences )
            .suppressGeneratorTimestamp( options.isSuppressGeneratorTimestamp() )
            .decorator( getDecorator( element, methods ) )
            .typeFactory( typeFactory )
            .elementUtils( elementUtils )
            .build();

        return mapper;
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
    private ReportingPolicy getEffectiveUnmappedTargetPolicy(TypeElement element) {
        MapperConfig mapperSettings = MapperConfig.getInstanceOn( element );
        boolean setViaAnnotation = mapperSettings.isSetUnmappedTargetPolicy();
        ReportingPolicy annotationValue = ReportingPolicy.valueOf( mapperSettings.unmappedTargetPolicy() );

        if ( setViaAnnotation ||
            options.getUnmappedTargetPolicy() == null ) {
            return annotationValue;
        }
        else {
            return options.getUnmappedTargetPolicy();
        }
    }

    private Decorator getDecorator(TypeElement element, List<SourceMethod> methods) {
        DecoratedWithPrism decoratorPrism = DecoratedWithPrism.getInstanceOn( element );

        if ( decoratorPrism == null ) {
            return null;
        }

        TypeElement decoratorElement = (TypeElement) typeUtils.asElement( decoratorPrism.value() );

        if ( !typeUtils.isAssignable( decoratorElement.asType(), element.asType() ) ) {
            messager.printMessage(
                Kind.ERROR,
                String.format( "Specified decorator type is no subtype of the annotated mapper type." ),
                element,
                decoratorPrism.mirror
            );
        }

        List<MappingMethod> mappingMethods = new ArrayList<MappingMethod>( methods.size() );

        for ( SourceMethod mappingMethod : methods ) {
            boolean implementationRequired = true;
            for ( ExecutableElement method : ElementFilter.methodsIn( decoratorElement.getEnclosedElements() ) ) {
                if ( elementUtils.overrides( method, mappingMethod.getExecutable(), decoratorElement ) ) {
                    implementationRequired = false;
                    break;
                }
            }
            Type declaringMapper = mappingMethod.getDeclaringMapper();
            if ( implementationRequired ) {
                if ( ( declaringMapper == null ) || declaringMapper.equals( typeFactory.getType( element ) ) ) {
                    mappingMethods.add( new DelegatingMethod( mappingMethod ) );
                }
            }
        }

        boolean hasDelegateConstructor = false;
        boolean hasDefaultConstructor = false;
        for ( ExecutableElement constructor : ElementFilter.constructorsIn( decoratorElement.getEnclosedElements() ) ) {
            if ( constructor.getParameters().isEmpty() ) {
                hasDefaultConstructor = true;
            }
            else if ( constructor.getParameters().size() == 1 ) {
                if ( typeUtils.isAssignable(
                    element.asType(),
                    constructor.getParameters().iterator().next().asType()
                ) ) {
                    hasDelegateConstructor = true;
                }
            }
        }

        if ( !hasDelegateConstructor && !hasDefaultConstructor ) {
            messager.printMessage(
                Kind.ERROR,
                String.format(
                    "Specified decorator type has no default constructor nor a constructor with a single " +
                        "parameter accepting the decorated mapper type."
                ),
                element,
                decoratorPrism.mirror
            );
        }

        return Decorator.getInstance(
            elementUtils,
            typeFactory,
            element,
            decoratorPrism,
            mappingMethods,
            hasDelegateConstructor,
            options.isSuppressGeneratorTimestamp()
        );
    }

    private List<MapperReference> getReferencedMappers(TypeElement element) {
        List<MapperReference> mapperReferences = new LinkedList<MapperReference>();
        List<String> variableNames = new LinkedList<String>();

        MapperConfig mapperPrism = MapperConfig.getInstanceOn( element );

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

    private List<MappingMethod> getMappingMethods(List<MapperReference> mapperReferences, List<SourceMethod> methods,
                                                  ReportingPolicy unmappedTargetPolicy) {
        List<MappingMethod> mappingMethods = new ArrayList<MappingMethod>();

        for ( SourceMethod method : methods ) {
            if ( !method.requiresImplementation() ) {
                continue;
            }

            SourceMethod reverseMappingMethod = getReverseMappingMethod( methods, method );

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
            else if ( method.isEnumMapping() ) {
                if ( method.getMappings().isEmpty() ) {
                    if ( reverseMappingMethod != null && !reverseMappingMethod.getMappings().isEmpty() ) {
                        method.setMappings( reverse( reverseMappingMethod.getMappings() ) );
                    }
                }

                MappingMethod enumMappingMethod = getEnumMappingMethod( method );

                if ( enumMappingMethod != null ) {
                    mappingMethods.add( enumMappingMethod );
                }
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

    private MethodReference getFactoryMethod(List<MapperReference> mapperReferences, List<SourceMethod> methods,
                                             Type returnType) {
        MethodReference result = null;
        for ( SourceMethod method : methods ) {
            if ( !method.requiresImplementation() && !method.isIterableMapping() && !method.isMapMapping()
                && method.getSourceParameters().size() == 0 ) {

                List<Type> parameterTypes =
                    MethodSelectors.getParameterTypes( typeFactory, method.getParameters(), null, returnType );

                if ( method.matches( parameterTypes, returnType ) ) {
                    if ( result == null ) {
                        MapperReference mapperReference = findMapperReference( mapperReferences, method );
                        result = new MethodReference( method, mapperReference, null );
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

    private void reportErrorIfNoImplementationTypeIsRegisteredForInterfaceReturnType(SourceMethod method) {
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

    private PropertyMapping getPropertyMapping(List<MapperReference> mapperReferences,
                                               List<SourceMethod> methods,
                                               SourceMethod method,
                                               ExecutableElement targetAcessor,
                                               Parameter parameter) {
        String targetPropertyName = Executables.getPropertyName( targetAcessor );
        Mapping mapping = method.getMappingByTargetPropertyName( targetPropertyName );
        String dateFormat = mapping != null ? mapping.getDateFormat() : null;
        String sourcePropertyName = mapping != null ? mapping.getSourcePropertyName() : targetPropertyName;
        List<ExecutableElement> sourceGetters = parameter.getType().getGetters();

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

    private BeanMappingMethod getBeanMappingMethod(List<MapperReference> mapperReferences, List<SourceMethod> methods,
                                                   SourceMethod method, ReportingPolicy unmappedTargetPolicy) {

        List<PropertyMapping> propertyMappings = new ArrayList<PropertyMapping>();
        Set<String> mappedTargetProperties = new HashSet<String>();

        if ( !reportErrorIfMappedPropertiesDontExist( method ) ) {
            return null;
        }

        // collect all target accessors
        List<ExecutableElement> targetAccessors = new ArrayList<ExecutableElement>();
        targetAccessors.addAll( method.getResultType().getSetters() );
        targetAccessors.addAll( method.getResultType().getAlternativeTargetAccessors() );

        for ( ExecutableElement targetAccessor : targetAccessors ) {
            String targetPropertyName = Executables.getPropertyName( targetAccessor );

            Mapping mapping = method.getMappingByTargetPropertyName( targetPropertyName );

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

    private void reportErrorForUnmappedTargetPropertiesIfRequired(SourceMethod method,
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

    private SourceMethod getReverseMappingMethod(List<SourceMethod> rawMethods, SourceMethod method) {
        for ( SourceMethod oneMethod : rawMethods ) {
            if ( oneMethod.reverses( method ) ) {
                return oneMethod;
            }
        }
        return null;
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

    private PropertyMapping getPropertyMapping(List<MapperReference> mapperReferences,
                                               List<SourceMethod> methods,
                                               SourceMethod method,
                                               Parameter parameter,
                                               ExecutableElement sourceAccessor,
                                               ExecutableElement targetAcessor,
                                               String dateFormat) {
        Type sourceType = typeFactory.getReturnType( sourceAccessor );
        Type targetType = null;
        String conversionString = parameter.getName() + "." + sourceAccessor.getSimpleName().toString() + "()";
        if ( Executables.isSetterMethod( targetAcessor ) ) {
            targetType = typeFactory.getSingleParameter( targetAcessor ).getType();
        }
        else if ( Executables.isGetterMethod( targetAcessor ) ) {
            targetType = typeFactory.getReturnType( targetAcessor );
        }

        String targetPropertyName = Executables.getPropertyName( targetAcessor );

        String mappedElement = "property '" + Executables.getPropertyName( sourceAccessor ) + "'";

        ParameterAssignment parameterAssignment = mappingResolver.getParameterAssignment(
            method,
            mappedElement,
            mapperReferences,
            methods,
            sourceType,
            targetType,
            targetPropertyName,
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
            parameterAssignment != null ? parameterAssignment.getMethodReference() : null,
            parameterAssignment != null ? parameterAssignment.getTypeConversion() : null
        );

        if ( !isPropertyMappable( property ) ) {
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
        return property;
    }

    private IterableMappingMethod getIterableMappingMethod(List<MapperReference> mapperReferences,
                                                           List<SourceMethod> methods,
                                                           SourceMethod method) {
        Type sourceElementType = method.getSourceParameters().iterator().next().getType().getTypeParameters().get( 0 );
        Type targetElementType = method.getResultType().getTypeParameters().get( 0 );
        String dateFormat = method.getIterableMapping() != null ? method.getIterableMapping().getDateFormat() : null;
        String conversionStr = Strings.getSaveVariableName( sourceElementType.getName(), method.getParameterNames() );

        ParameterAssignment parameterAssignment = mappingResolver.getParameterAssignment(
            method,
            "collection element",
            mapperReferences,
            methods,
            sourceElementType,
            targetElementType,
            null, // there is no targetPropertyName
            dateFormat,
            conversionStr
        );

        if ( parameterAssignment == null ) {
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
            parameterAssignment != null ? parameterAssignment.getMethodReference() : null,
            parameterAssignment != null ? parameterAssignment.getTypeConversion() : null,
            factoryMethod
        );
    }

    private MapMappingMethod getMapMappingMethod(List<MapperReference> mapperReferences, List<SourceMethod> methods,
                                                 SourceMethod method) {
        List<Type> sourceTypeParams = method.getSourceParameters().iterator().next().getType().getTypeParameters();
        List<Type> resultTypeParams = method.getResultType().getTypeParameters();

        // find mapping method or conversion for key
        Type keySourceType = sourceTypeParams.get( 0 );
        Type keyTargetType = resultTypeParams.get( 0 );
        String keyDateFormat = method.getMapMapping() != null ? method.getMapMapping().getKeyFormat() : null;

        ParameterAssignment parameterAssignmentKey = mappingResolver.getParameterAssignment(
            method,
            "map key",
            mapperReferences,
            methods,
            keySourceType,
            keyTargetType,
            null, // there is no targetPropertyName
            keyDateFormat,
            "entry.getKey()"
        );

        if ( parameterAssignmentKey == null ) {
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

        // find mapping method or conversion for value
        Type valueSourceType = sourceTypeParams.get( 1 );
        Type valueTargetType = resultTypeParams.get( 1 );
        String valueDateFormat = method.getMapMapping() != null ? method.getMapMapping().getValueFormat() : null;

        ParameterAssignment parameterAssignmentValue = mappingResolver.getParameterAssignment(
            method,
            "map value",
            mapperReferences,
            methods,
            valueSourceType,
            valueTargetType,
            null, // there is no targetPropertyName
            valueDateFormat,
            "entry.getValue()"
        );

        if ( parameterAssignmentValue == null ) {
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
                method,
                parameterAssignmentKey != null ? parameterAssignmentKey.getMethodReference() : null,
                parameterAssignmentKey != null ? parameterAssignmentKey.getTypeConversion() : null,
                parameterAssignmentValue != null ? parameterAssignmentValue.getMethodReference() : null,
                parameterAssignmentValue != null ? parameterAssignmentValue.getTypeConversion() : null,
                factoryMethod
        );
    }

    private EnumMappingMethod getEnumMappingMethod(SourceMethod method) {
        if ( !reportErrorIfMappedEnumConstantsDontExist( method ) ||
            !reportErrorIfSourceEnumConstantsWithoutCorrespondingTargetConstantAreNotMapped( method ) ) {
            return null;
        }

        List<EnumMapping> enumMappings = new ArrayList<EnumMapping>();

        List<String> sourceEnumConstants = method.getSourceParameters().iterator().next().getType().getEnumConstants();
        Map<String, List<Mapping>> mappings = method.getMappings();

        for ( String enumConstant : sourceEnumConstants ) {
            List<Mapping> mappedConstants = mappings.get( enumConstant );

            if ( mappedConstants == null ) {
                enumMappings.add( new EnumMapping( enumConstant, enumConstant ) );
            }
            else if ( mappedConstants.size() == 1 ) {
                enumMappings.add( new EnumMapping( enumConstant, mappedConstants.iterator().next().getTargetName() ) );
            }
            else {
                List<String> targetConstants = new ArrayList<String>( mappedConstants.size() );
                for ( Mapping mapping : mappedConstants ) {
                    targetConstants.add( mapping.getTargetName() );
                }
                messager.printMessage(
                    Kind.ERROR,
                    String.format(
                        "One enum constant must not be mapped to more than one target constant, but constant %s is " +
                            "mapped to %s.",
                        enumConstant,
                        Strings.join( targetConstants, ", " )
                    ),
                    method.getExecutable()
                );
            }
        }

        return new EnumMappingMethod( method, enumMappings );
    }

    private boolean reportErrorIfMappedEnumConstantsDontExist(SourceMethod method) {
        // only report errors if this method itself is configured
        if ( method.isConfiguredByReverseMappingMethod() ) {
            return true;
        }

        List<String> sourceEnumConstants = method.getSourceParameters().iterator().next().getType().getEnumConstants();
        List<String> targetEnumConstants = method.getReturnType().getEnumConstants();

        boolean foundIncorrectMapping = false;

        for ( List<Mapping> mappedConstants : method.getMappings().values() ) {
            for ( Mapping mappedConstant : mappedConstants ) {
                if ( mappedConstant.getSourceName() == null ) {
                    messager.printMessage(
                        Kind.ERROR,
                        "A source constant must be specified for mappings of an enum mapping method.",
                        method.getExecutable(),
                        mappedConstant.getMirror()
                    );
                    foundIncorrectMapping = true;
                }
                else if ( !sourceEnumConstants.contains( mappedConstant.getSourceName() ) ) {
                    messager.printMessage(
                        Kind.ERROR,
                        String.format(
                            "Constant %s doesn't exist in enum type %s.",
                            mappedConstant.getSourceName(),
                            method.getSourceParameters().iterator().next().getType()
                        ),
                        method.getExecutable(),
                        mappedConstant.getMirror(),
                        mappedConstant.getSourceAnnotationValue()
                    );
                    foundIncorrectMapping = true;
                }
                if ( mappedConstant.getTargetName() == null ) {
                    messager.printMessage(
                        Kind.ERROR,
                        "A target constant must be specified for mappings of an enum mapping method.",
                        method.getExecutable(),
                        mappedConstant.getMirror()
                    );
                    foundIncorrectMapping = true;
                }
                else if ( !targetEnumConstants.contains( mappedConstant.getTargetName() ) ) {
                    messager.printMessage(
                        Kind.ERROR,
                        String.format(
                            "Constant %s doesn't exist in enum type %s.",
                            mappedConstant.getTargetName(),
                            method.getReturnType()
                        ),
                        method.getExecutable(),
                        mappedConstant.getMirror(),
                        mappedConstant.getTargetAnnotationValue()
                    );
                    foundIncorrectMapping = true;
                }
            }
        }

        return !foundIncorrectMapping;
    }

    private boolean reportErrorIfSourceEnumConstantsWithoutCorrespondingTargetConstantAreNotMapped(
        SourceMethod method) {

        List<String> sourceEnumConstants = method.getSourceParameters().iterator().next().getType().getEnumConstants();
        List<String> targetEnumConstants = method.getReturnType().getEnumConstants();
        Set<String> mappedSourceEnumConstants = method.getMappings().keySet();
        List<String> unmappedSourceEnumConstants = new ArrayList<String>();

        for ( String sourceEnumConstant : sourceEnumConstants ) {
            if ( !targetEnumConstants.contains( sourceEnumConstant ) &&
                !mappedSourceEnumConstants.contains( sourceEnumConstant ) ) {
                unmappedSourceEnumConstants.add( sourceEnumConstant );
            }
        }

        if ( !unmappedSourceEnumConstants.isEmpty() ) {
            messager.printMessage(
                Kind.ERROR,
                String.format(
                    "The following constants from the source enum have no corresponding constant in the target enum " +
                        "and must be be mapped via @Mapping: %s",
                    Strings.join( unmappedSourceEnumConstants, ", " )
                ),
                method.getExecutable()
            );
        }

        return unmappedSourceEnumConstants.isEmpty();
    }

    private MapperReference findMapperReference(List<MapperReference> mapperReferences, SourceMethod method) {
        for ( MapperReference ref : mapperReferences ) {
            if ( ref.getType().equals( method.getDeclaringMapper() ) ) {
                return ref;
            }
        }
        return null;
    }

    /**
     * Whether the specified property can  be mapped from source to target or not. A mapping if possible if one of
     * the following conditions is true:
     * <ul>
     * <li>the source type is assignable to the target type</li>
     * <li>a mapping method exists</li>
     * <li>a built-in conversion exists</li>
     * <li>the property is of a collection or map type and the constructor of the target type (either itself or its
     * implementation type) accepts the source type.</li>
     * </ul>
     *
     * @param property The property mapping to check.
     *
     * @return {@code true} if the specified property can be mapped, {@code false} otherwise.
     */
    private boolean isPropertyMappable(PropertyMapping property) {
        boolean collectionOrMapTargetTypeHasCompatibleConstructor = false;

        if ( property.getSourceType().isCollectionType() && property.getTargetType().isCollectionType() ) {
            collectionOrMapTargetTypeHasCompatibleConstructor = collectionTypeHasCompatibleConstructor(
                property.getSourceType(),
                property.getTargetType().getImplementationType() != null ?
                    property.getTargetType().getImplementationType() : property.getTargetType()
            );
        }

        if ( property.getSourceType().isMapType() && property.getTargetType().isMapType() ) {
            collectionOrMapTargetTypeHasCompatibleConstructor = mapTypeHasCompatibleConstructor(
                property.getSourceType(),
                property.getTargetType().getImplementationType() != null ?
                    property.getTargetType().getImplementationType() : property.getTargetType()
            );
        }

        if ( property.getSourceType().isAssignableTo( property.getTargetType() ) ||
            property.getMappingMethod() != null ||
            property.getConversion() != null ||
            ( ( property.getTargetType().isCollectionType() || property.getTargetType().isMapType() ) &&
                collectionOrMapTargetTypeHasCompatibleConstructor ) ) {
            return true;
        }

        return false;
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
    private boolean collectionTypeHasCompatibleConstructor(Type sourceType, Type targetType) {
        // note (issue #127): actually this should check for the presence of a matching constructor, with help of
        // Types#asMemberOf(); but this method seems to not work correctly in the Eclipse implementation, so instead we
        // just check whether the target type is parameterized in a way that it implicitly should have a constructor
        // which accepts the source type

        TypeMirror sourceElementType = sourceType.getTypeParameters().isEmpty() ?
            typeFactory.getType( Object.class ).getTypeMirror() :
            sourceType.getTypeParameters().get( 0 ).getTypeMirror();

        TypeMirror targetElementType = targetType.getTypeParameters().isEmpty() ?
            typeFactory.getType( Object.class ).getTypeMirror() :
            targetType.getTypeParameters().get( 0 ).getTypeMirror();

        return typeUtils.isAssignable( sourceElementType, targetElementType );
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
    private boolean mapTypeHasCompatibleConstructor(Type sourceType, Type targetType) {
        // note (issue #127): actually this should check for the presence of a matching constructor, with help of
        // Types#asMemberOf(); but this method seems to not work correctly in the Eclipse implementation, so instead we
        // just check whether the target type is parameterized in a way that it implicitly should have a constructor
        // which accepts the source type

        TypeMirror sourceKeyType = null;
        TypeMirror targetKeyType = null;
        TypeMirror sourceValueType = null;
        TypeMirror targetValueType = null;

        if ( sourceType.getTypeParameters().isEmpty() ) {
            sourceKeyType = typeFactory.getType( Object.class ).getTypeMirror();
            sourceValueType = typeFactory.getType( Object.class ).getTypeMirror();
        }
        else {
            sourceKeyType = sourceType.getTypeParameters().get( 0 ).getTypeMirror();
            sourceValueType = sourceType.getTypeParameters().get( 1 ).getTypeMirror();
        }

        if ( targetType.getTypeParameters().isEmpty() ) {
            targetKeyType = typeFactory.getType( Object.class ).getTypeMirror();
            targetValueType = typeFactory.getType( Object.class ).getTypeMirror();
        }
        else {
            targetKeyType = targetType.getTypeParameters().get( 0 ).getTypeMirror();
            targetValueType = targetType.getTypeParameters().get( 1 ).getTypeMirror();
        }

        return typeUtils.isAssignable( sourceKeyType, targetKeyType ) &&
            typeUtils.isAssignable( sourceValueType, targetValueType );
    }
}
