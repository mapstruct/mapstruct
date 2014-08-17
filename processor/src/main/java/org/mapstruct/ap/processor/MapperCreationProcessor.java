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
import java.util.SortedSet;
import java.util.TreeSet;
import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic.Kind;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.ap.model.Assignment;
import static org.mapstruct.ap.model.Assignment.AssignmentType.DIRECT;
import static org.mapstruct.ap.model.Assignment.AssignmentType.TYPE_CONVERTED;
import static org.mapstruct.ap.model.Assignment.AssignmentType.TYPE_CONVERTED_MAPPED;
import org.mapstruct.ap.model.BeanMappingMethod;
import org.mapstruct.ap.model.Decorator;
import org.mapstruct.ap.model.DefaultMapperReference;
import org.mapstruct.ap.model.DelegatingMethod;
import org.mapstruct.ap.model.EnumMappingMethod;
import org.mapstruct.ap.model.FactoryMethod;
import org.mapstruct.ap.model.IterableMappingMethod;
import org.mapstruct.ap.model.MapMappingMethod;
import org.mapstruct.ap.model.Mapper;
import org.mapstruct.ap.model.MapperReference;
import org.mapstruct.ap.model.MappingMethod;
import org.mapstruct.ap.model.PropertyMapping;
import org.mapstruct.ap.model.assignment.AdderWrapper;
import org.mapstruct.ap.model.assignment.AssignmentFactory;
import org.mapstruct.ap.model.assignment.GetterCollectionOrMapWrapper;
import org.mapstruct.ap.model.assignment.LocalVarWrapper;
import org.mapstruct.ap.model.assignment.NewCollectionOrMapWrapper;
import org.mapstruct.ap.model.assignment.NullCheckWrapper;
import org.mapstruct.ap.model.assignment.SetterCollectionOrMapWrapper;
import org.mapstruct.ap.model.assignment.SetterWrapper;
import org.mapstruct.ap.model.common.Parameter;
import org.mapstruct.ap.model.common.Type;
import org.mapstruct.ap.model.common.TypeFactory;
import org.mapstruct.ap.model.source.EnumMapping;
import org.mapstruct.ap.model.source.ForgedMethod;
import org.mapstruct.ap.model.source.Mapping;
import org.mapstruct.ap.model.source.Method;
import org.mapstruct.ap.model.source.SourceMethod;
import org.mapstruct.ap.model.source.selector.MethodSelectors;
import org.mapstruct.ap.option.Options;
import org.mapstruct.ap.option.ReportingPolicy;
import org.mapstruct.ap.prism.DecoratedWithPrism;
import org.mapstruct.ap.prism.MapperPrism;
import org.mapstruct.ap.processor.creation.MappingResolver;
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

    private enum TargetAccessorType {
        GETTER,
        SETTER,
        ADDER
    }

    private Elements elementUtils;
    private Types typeUtils;
    private Messager messager;
    private Options options;
    private TypeFactory typeFactory;
    private MappingResolver mappingResolver;
    private final List<MappingMethod> mappingsToGenerate = new ArrayList<MappingMethod>();

    @Override
    public Mapper process(ProcessorContext context, TypeElement mapperTypeElement, List<SourceMethod> sourceModel) {
        this.elementUtils = context.getElementUtils();
        this.typeUtils = context.getTypeUtils();
        this.messager = context.getMessager();
        this.options = context.getOptions();
        this.typeFactory = context.getTypeFactory();
        this.mappingResolver = new MappingResolver( messager, typeFactory, elementUtils, typeUtils );

        return getMapper( mapperTypeElement, sourceModel );
    }

    @Override
    public int getPriority() {
        return 1000;
    }

    private Mapper getMapper(TypeElement element, List<SourceMethod> methods) {
        List<MapperReference> mapperReferences = getReferencedMappers( element );
        List<MappingMethod> mappingMethods = getMappingMethods( mapperReferences, methods, element );
        mappingMethods.addAll( mappingResolver.getVirtualMethodsToGenerate() );
        mappingMethods.addAll( mappingsToGenerate );

        Mapper mapper = new Mapper.Builder()
            .element( element )
            .mappingMethods( mappingMethods )
            .mapperReferences( mapperReferences )
            .suppressGeneratorTimestamp( options.isSuppressGeneratorTimestamp() )
            .decorator( getDecorator( element, methods ) )
            .typeFactory( typeFactory )
            .elementUtils( elementUtils )
            .extraImports( getExtraImports( element ) )
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

    private CollectionMappingStrategy getEffectiveCollectionMappingStrategy(TypeElement element) {
        MapperConfig mapperSettings = MapperConfig.getInstanceOn( element );
        return mapperSettings.getCollectionMappingStrategy();
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

    private SortedSet<Type> getExtraImports(TypeElement element) {

        SortedSet<Type> extraImports = new TreeSet<Type>();

        MapperConfig mapperPrism = MapperConfig.getInstanceOn( element );

        for ( TypeMirror extraImport : mapperPrism.imports() ) {
            Type type = typeFactory.getType( extraImport );
            extraImports.add( type );
        }

        return extraImports;
    }

    private List<MappingMethod> getMappingMethods(List<MapperReference> mapperReferences, List<SourceMethod> methods,
                                                  TypeElement element) {
        List<MappingMethod> mappingMethods = new ArrayList<MappingMethod>();

        for ( SourceMethod method : methods ) {
            if ( !method.overridesMethod() ) {
                continue;
            }

            SourceMethod reverseMappingMethod = getReverseMappingMethod( methods, method );

            boolean hasFactoryMethod = false;
            if ( method.isIterableMapping() ) {
                if ( method.getIterableMapping() == null && reverseMappingMethod != null &&
                    reverseMappingMethod.getIterableMapping() != null ) {
                    method.setIterableMapping( reverseMappingMethod.getIterableMapping() );
                }

                String dateFormat = null;
                List<TypeMirror> qualifiers = null;
                if ( method.getIterableMapping() != null ) {
                    dateFormat = method.getIterableMapping().getDateFormat();
                    qualifiers = method.getIterableMapping().getQualifiers();
                }

                IterableMappingMethod iterableMappingMethod = getIterableMappingMethod(
                        mapperReferences,
                        methods,
                        method,
                        dateFormat,
                        qualifiers
                );
                hasFactoryMethod = iterableMappingMethod.getFactoryMethod() != null;
                mappingMethods.add( iterableMappingMethod );
            }
            else if ( method.isMapMapping() ) {
                if ( method.getMapMapping() == null && reverseMappingMethod != null &&
                    reverseMappingMethod.getMapMapping() != null ) {
                    method.setMapMapping( reverseMappingMethod.getMapMapping() );
                }
                String keyDateFormat = null;
                String valueDateFormat = null;
                List<TypeMirror> keyQualifiers = null;
                List<TypeMirror> valueQualifiers = null;
                if ( method.getMapMapping() != null ) {
                    keyDateFormat = method.getMapMapping().getKeyFormat();
                    valueDateFormat = method.getMapMapping().getValueFormat();
                    keyQualifiers = method.getMapMapping().getKeyQualifiers();
                    valueQualifiers = method.getMapMapping().getValueQualifiers();
                }

                MapMappingMethod mapMappingMethod = getMapMappingMethod(
                        mapperReferences,
                        methods,
                        method,
                        keyDateFormat,
                        valueDateFormat,
                        keyQualifiers,
                        valueQualifiers
                );
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
                    element
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

    private FactoryMethod getFactoryMethod(List<MapperReference> mapperReferences, List<SourceMethod> methods,
                                           Type returnType) {
        FactoryMethod result = null;
        for ( SourceMethod method : methods ) {
            if ( !method.overridesMethod() && !method.isIterableMapping() && !method.isMapMapping()
                && method.getSourceParameters().isEmpty() ) {

                List<Type> parameterTypes =
                    MethodSelectors.getParameterTypes( typeFactory, method.getParameters(), null, returnType );

                if ( method.matches( parameterTypes, returnType ) ) {
                    if ( result == null ) {
                        MapperReference mapperReference = findMapperReference( mapperReferences, method );
                        result = AssignmentFactory.createFactory( method, mapperReference );
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

                Mapping reverseMapping = mapping.reverse();
                if ( reverseMapping != null ) {
                    if ( !reversed.containsKey( mapping.getTargetName() ) ) {
                        reversed.put( mapping.getTargetName(), new ArrayList<Mapping>() );
                    }
                    reversed.get( mapping.getTargetName() ).add( reverseMapping );
                }
            }
        }
        return reversed;
    }

    private PropertyMapping getPropertyMapping(List<MapperReference> mapperReferences,
                                               List<SourceMethod> methods,
                                               SourceMethod method,
                                               ExecutableElement targetAccessor,
                                               String targetPropertyName,
                                               Parameter parameter) {

        // check if there's a mapping defined
        Mapping mapping = method.getMappingByTargetPropertyName( targetPropertyName );
        String dateFormat = null;
        List<TypeMirror> qualifiers = null;
        String sourcePropertyName;
        if ( mapping != null ) {
            dateFormat = mapping.getDateFormat();
            qualifiers = mapping.getQualifiers();
            sourcePropertyName = mapping.getSourcePropertyName();
        }
        else {
            sourcePropertyName = targetPropertyName;
        }

        List<ExecutableElement> sourceGetters = parameter.getType().getGetters();

        // then iterate over source accessors (assuming the source is a bean)
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
                            targetAccessor,
                            targetPropertyName,
                            dateFormat,
                            qualifiers
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
                    targetAccessor,
                    targetPropertyName,
                    dateFormat,
                    qualifiers
                );
            }
        }
        return null;
    }

    private BeanMappingMethod getBeanMappingMethod(List<MapperReference> mapperReferences, List<SourceMethod> methods,
                                                   SourceMethod method, TypeElement element) {

        // fetch settings from element to implement
        ReportingPolicy unmappedTargetPolicy = getEffectiveUnmappedTargetPolicy( element );
        CollectionMappingStrategy cmStrategy = getEffectiveCollectionMappingStrategy( element );

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
                    Type targetType = typeFactory.getSingleParameter( targetAccessor ).getType();
                    // ok, the current accessor is a setter. So now the strategy determines what to use
                    if ( cmStrategy.equals( CollectionMappingStrategy.ADDER_PREFERRED ) ) {
                        adderMethod = method.getResultType().getAdderForType( targetType, targetPropertyName );
                    }
                }
                else if ( Executables.isGetterMethod( targetAccessor ) ) {
                    // the current accessor is a getter (no setter available). But still, an add method is according
                    // to the above strategy (SETTER_PREFERRED || ADDER_PREFERRED) preferred over the getter.
                    Type targetType = typeFactory.getReturnType( targetAccessor );
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
                    propertyMapping = getPropertyMapping(
                            mapperReferences,
                            methods,
                            method,
                            targetAccessor,
                            targetPropertyName,
                            parameter
                    );
                }
                else if ( Executables.isSetterMethod( targetAccessor ) ) {

                    if ( !mapping.getConstant().isEmpty() ) {
                        // its a constant
                        propertyMapping = getConstantMapping(
                                mapperReferences,
                                methods,
                                method,
                                "\"" + mapping.getConstant() + "\"",
                                targetAccessor,
                                mapping.getDateFormat(),
                                mapping.getQualifiers()
                        );
                    }

                    else if ( !mapping.getJavaExpression().isEmpty() ) {
                        // its an expression
                        propertyMapping = getJavaExpressionMapping(
                                method,
                                mapping.getJavaExpression(),
                                targetAccessor
                        );
                    }
                }
            }

            if ( propertyMapping == null ) {
                for ( Parameter sourceParameter : method.getSourceParameters() ) {
                    PropertyMapping newPropertyMapping = getPropertyMapping(
                        mapperReferences,
                        methods,
                        method,
                        targetAccessor,
                        targetPropertyName,
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
            mappedTargetProperties,
            ignoredTargetProperties
        );

        FactoryMethod factoryMethod = getFactoryMethod( mapperReferences, methods, method.getReturnType() );
        return new BeanMappingMethod( method, propertyMappings, factoryMethod );
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
            messager.printMessage(
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
                if ( mappedProperty.isIgnored() ) {
                    continue;
                }

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
                else if ( mappedProperty.getConstant().isEmpty() &&
                    mappedProperty.getJavaExpression().isEmpty() &&
                    !hasSourceProperty( method, mappedProperty.getSourcePropertyName() ) ) {
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
                                               ExecutableElement targetAccessor,
                                               String targetPropertyName,
                                               String dateFormat,
                                               List<TypeMirror> qualifiers) {

        Type sourceType;
        Type targetType;
        TargetAccessorType targetAccessorType;
        String sourceReference = parameter.getName() + "." + sourceAccessor.getSimpleName().toString() + "()";
        String iteratorReference = null;
        boolean sourceIsCollection = false;
        if ( Executables.isSetterMethod( targetAccessor ) ) {
            sourceType = typeFactory.getReturnType( sourceAccessor );
            targetType = typeFactory.getSingleParameter( targetAccessor ).getType();
            targetAccessorType = TargetAccessorType.SETTER;
        }
        else if ( Executables.isAdderMethod( targetAccessor ) ) {
            sourceType = typeFactory.getReturnType( sourceAccessor );
            if ( sourceType.isCollectionType() ) {
                sourceIsCollection = true;
                sourceType = sourceType.getTypeParameters().get( 0 );
                iteratorReference = Executables.getElementNameForAdder( targetAccessor );
            }
            targetType = typeFactory.getSingleParameter( targetAccessor ).getType();
            targetAccessorType = TargetAccessorType.ADDER;
        }
        else {
            sourceType = typeFactory.getReturnType( sourceAccessor );
            targetType = typeFactory.getReturnType( targetAccessor );
            targetAccessorType = TargetAccessorType.GETTER;
        }
        String sourcePropertyName = Executables.getPropertyName( sourceAccessor );
        String mappedElement = "property '" + sourcePropertyName + "'";

        Assignment assignment = mappingResolver.getTargetAssignment(
            method,
            mappedElement,
            mapperReferences,
            methods,
            sourceType,
            targetType,
            targetPropertyName,
            dateFormat,
            qualifiers,
            iteratorReference != null ? iteratorReference : sourceReference
        );

        if ( assignment == null ) {
            assignment = forgeMapping(
                    mapperReferences,
                    methods,
                    sourceType,
                    targetType,
                    sourceReference,
                    method.getExecutable()
            );
        }

        if ( assignment != null ) {

            if ( targetType.isCollectionOrMapType() ) {

                // wrap the setter in the collection / map initializers
                if ( targetAccessorType == TargetAccessorType.SETTER ) {

                    // wrap the assignment in a new Map or Collection implementation if this is not done in a mapping
                    // method. Note, typeconversons do not apply to collections or maps
                    Assignment newCollectionOrMap = null;
                    if ( assignment.getType() == DIRECT ) {
                        newCollectionOrMap = new NewCollectionOrMapWrapper( assignment, targetType.getImportTypes() );
                        newCollectionOrMap = new SetterWrapper( newCollectionOrMap, method.getThrownTypes() );
                    }

                    // wrap the assignment in the setter method
                    assignment = new SetterWrapper( assignment, method.getThrownTypes() );

                    // target accessor is setter, so wrap the setter in setter map/ collection handling
                    assignment = new SetterCollectionOrMapWrapper(
                        assignment,
                        targetAccessor.getSimpleName().toString(),
                        newCollectionOrMap
                    );
                }
                else {
                    // wrap the assignment in the setter method
                    assignment = new SetterWrapper( assignment, method.getThrownTypes() );

                    // target accessor is getter, so wrap the setter in getter map/ collection handling
                    assignment = new GetterCollectionOrMapWrapper( assignment );
                }

                // For collections and maps include a null check, when the assignment type is DIRECT.
                // for mapping methods (builtin / custom), the mapping method is responsible for the
                // null check. Typeconversions do not apply to collections and maps.
                if ( assignment.getType() == DIRECT ) {
                    assignment = new NullCheckWrapper( assignment );
                }
            }
            else {
                if ( targetAccessorType == TargetAccessorType.SETTER ) {
                    assignment = new SetterWrapper( assignment, method.getThrownTypes() );
                    if ( !sourceType.isPrimitive() &&
                        ( assignment.getType() == TYPE_CONVERTED ||
                            assignment.getType() == TYPE_CONVERTED_MAPPED ||
                            assignment.getType() == DIRECT && targetType.isPrimitive() ) ) {
                        // for primitive types null check is not possible at all, but a conversion needs
                        // a null check.
                        assignment = new NullCheckWrapper( assignment );
                    }
                }
                else {
                    // TargetAccessorType must be ADDER
                    if ( sourceIsCollection ) {
                        assignment =
                            new AdderWrapper( assignment, method.getThrownTypes(), sourceReference, sourceType );
                    }
                    else {
                        // Possibly adding null to a target collection. So should be surrounded by an null check.
                        assignment = new SetterWrapper( assignment, method.getThrownTypes() );
                        assignment = new NullCheckWrapper( assignment );
                    }
                }
            }
        }
        else {
            messager.printMessage(
                Kind.ERROR,
                String.format(
                    "Can't map property \"%s %s\" to \"%s %s\".",
                    sourceType,
                    sourcePropertyName,
                    targetType,
                    targetPropertyName
                ),
                method.getExecutable()
            );
        }
        return new PropertyMapping(
            parameter.getName(),
            targetAccessor.getSimpleName().toString(),
            targetType,
            assignment
        );
    }

    /**
     * Creates a {@link PropertyMapping} representing the mapping of the given constant expression into the target
     * property.
     */
    private PropertyMapping getConstantMapping(List<MapperReference> mapperReferences,
                                               List<SourceMethod> methods,
                                               SourceMethod method,
                                               String constantExpression,
                                               ExecutableElement targetAccessor,
                                               String dateFormat,
                                               List<TypeMirror> qualifiers) {

        // source
        String mappedElement = "constant '" + constantExpression + "'";
        Type sourceType = typeFactory.getType( String.class );

        // target
        Type targetType = typeFactory.getSingleParameter( targetAccessor ).getType();
        String targetPropertyName = Executables.getPropertyName( targetAccessor );

        Assignment assignment = mappingResolver.getTargetAssignment(
            method,
            mappedElement,
            mapperReferences,
            methods,
            sourceType,
            targetType,
            targetPropertyName,
            dateFormat,
            qualifiers,
            constantExpression
        );

        if ( assignment != null ) {

            // target accessor is setter, so decorate assignment as setter
            assignment = new SetterWrapper( assignment, method.getThrownTypes() );
        }
        else {
            messager.printMessage(
                Kind.ERROR,
                String.format(
                    "Can't map \"%s %s\" to \"%s %s\".",
                    sourceType,
                    constantExpression,
                    targetType,
                    targetPropertyName
                ),
                method.getExecutable()
            );
        }

        return new PropertyMapping( targetAccessor.getSimpleName().toString(), targetType, assignment );
    }

    /**
     * Creates a {@link PropertyMapping} representing the mapping of the given java expression into the target
     * property.
     */
    private PropertyMapping getJavaExpressionMapping(SourceMethod method,
                                                     String javaExpression,
                                                     ExecutableElement targetAcessor) {

        Type targetType = typeFactory.getSingleParameter( targetAcessor ).getType();
        Assignment assignment = AssignmentFactory.createSimple( javaExpression );
        assignment = new SetterWrapper( assignment, method.getThrownTypes() );
        return new PropertyMapping( targetAcessor.getSimpleName().toString(), targetType, assignment );
    }

    private IterableMappingMethod getIterableMappingMethod(List<MapperReference> mapperReferences,
                                                           List<SourceMethod> methods,
                                                           Method method,
                                                           String dateFormat,
                                                           List<TypeMirror> qualifiers) {
        Type sourceElementType = method.getSourceParameters().iterator().next().getType().getTypeParameters().get( 0 );
        Type targetElementType = method.getResultType().getTypeParameters().get( 0 );
        String conversionStr = Strings.getSaveVariableName( sourceElementType.getName(), method.getParameterNames() );

        Assignment assignment = mappingResolver.getTargetAssignment(
            method,
            "collection element",
            mapperReferences,
            methods,
            sourceElementType,
            targetElementType,
            null, // there is no targetPropertyName
            dateFormat,
            qualifiers,
            conversionStr
        );

        if ( assignment == null ) {
            String message = String.format(
                    "Can't create implementation of method %s. Found no method nor built-in conversion for mapping "
                    + "source element type into target element type.",
                    method
            );
            method.printMessage( messager, Kind.ERROR, message );
        }

        // target accessor is setter, so decorate assignment as setter
        assignment = new SetterWrapper( assignment, method.getThrownTypes() );

        FactoryMethod factoryMethod = getFactoryMethod( mapperReferences, methods, method.getReturnType() );
        return new IterableMappingMethod( method, assignment, factoryMethod );
    }

    private MapMappingMethod getMapMappingMethod(List<MapperReference> mapperReferences,
                                                 List<SourceMethod> methods,
                                                 Method method,
                                                 String keyDateFormat,
                                                 String valueDateFormat,
                                                 List<TypeMirror> keyQualifiers,
                                                 List<TypeMirror> valueQualifiers     ) {
        List<Type> sourceTypeParams = method.getSourceParameters().iterator().next().getType().getTypeParameters();
        List<Type> resultTypeParams = method.getResultType().getTypeParameters();

        // find mapping method or conversion for key
        Type keySourceType = sourceTypeParams.get( 0 );
        Type keyTargetType = resultTypeParams.get( 0 );

        Assignment keyAssignment = mappingResolver.getTargetAssignment(
            method,
            "map key",
            mapperReferences,
            methods,
            keySourceType,
            keyTargetType,
            null, // there is no targetPropertyName
            keyDateFormat,
            keyQualifiers,
            "entry.getKey()"
        );

        if ( keyAssignment == null ) {
            String message = String.format( "Can't create implementation of method %s. Found no method nor built-in "
                    + "conversion for mapping source key type to target key type.", method );
            method.printMessage( messager, Kind.ERROR, message );
        }

        // find mapping method or conversion for value
        Type valueSourceType = sourceTypeParams.get( 1 );
        Type valueTargetType = resultTypeParams.get( 1 );

        Assignment valueAssignment = mappingResolver.getTargetAssignment(
            method,
            "map value",
            mapperReferences,
            methods,
            valueSourceType,
            valueTargetType,
            null, // there is no targetPropertyName
            valueDateFormat,
            valueQualifiers,
            "entry.getValue()"
        );

        if ( valueAssignment == null ) {
            String message = String.format( "Can't create implementation of method %s. Found no method nor built-in "
                    + "conversion for mapping source value type to target value type.", method );
            method.printMessage( messager, Kind.ERROR, message );
        }

        FactoryMethod factoryMethod = getFactoryMethod( mapperReferences, methods, method.getReturnType() );

        keyAssignment = new LocalVarWrapper( keyAssignment, method.getThrownTypes() );
        valueAssignment = new LocalVarWrapper( valueAssignment, method.getThrownTypes() );

        return new MapMappingMethod( method, keyAssignment, valueAssignment, factoryMethod );
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

    public Assignment forgeMapping( List<MapperReference> mapperReferences,
                                    List<SourceMethod> methods,
                                    Type sourceType,
                                    Type targetType,
                                    String sourceReference,
                                    Element element) {
        Assignment assignment = null;
        if ( sourceType.isCollectionType() && targetType.isCollectionType() ) {

            ForgedMethod methodToGenerate
                    = new ForgedMethod( sourceType, targetType, element );
            IterableMappingMethod iterableMappingMethod
                    = getIterableMappingMethod( mapperReferences, methods, methodToGenerate, null, null );
            if ( !mappingsToGenerate.contains( iterableMappingMethod ) ) {
                mappingsToGenerate.add( iterableMappingMethod );
            }
            assignment = AssignmentFactory.createMethodReference( methodToGenerate, null, targetType );
            assignment.setAssignment( AssignmentFactory.createSimple( sourceReference ) );

        }
        else if ( sourceType.isMapType() && targetType.isMapType() ) {

            ForgedMethod methodToGenerate
                    = new ForgedMethod( sourceType, targetType, element );
            MapMappingMethod mapMappingMethod
                    = getMapMappingMethod( mapperReferences, methods, methodToGenerate, null, null, null, null );
            if ( !mappingsToGenerate.contains( mapMappingMethod ) ) {
                mappingsToGenerate.add( mapMappingMethod );
            }
            assignment = AssignmentFactory.createMethodReference( methodToGenerate, null, targetType );
            assignment.setAssignment( AssignmentFactory.createSimple( sourceReference ) );
        }
        return assignment;
    }
}
