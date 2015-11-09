/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.internal.processor;

import static org.mapstruct.ap.internal.prism.MappingInheritanceStrategyPrism.AUTO_INHERIT_FROM_CONFIG;
import static org.mapstruct.ap.internal.util.Collections.first;
import static org.mapstruct.ap.internal.util.Collections.join;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import org.mapstruct.ap.internal.model.BeanMappingMethod;
import org.mapstruct.ap.internal.model.Decorator;
import org.mapstruct.ap.internal.model.DefaultMapperReference;
import org.mapstruct.ap.internal.model.DelegatingMethod;
import org.mapstruct.ap.internal.model.EnumMappingMethod;
import org.mapstruct.ap.internal.model.IterableMappingMethod;
import org.mapstruct.ap.internal.model.MapMappingMethod;
import org.mapstruct.ap.internal.model.Mapper;
import org.mapstruct.ap.internal.model.MapperReference;
import org.mapstruct.ap.internal.model.MappingBuilderContext;
import org.mapstruct.ap.internal.model.MappingMethod;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.model.source.MappingOptions;
import org.mapstruct.ap.internal.model.source.SourceMethod;
import org.mapstruct.ap.internal.option.Options;
import org.mapstruct.ap.internal.prism.DecoratedWithPrism;
import org.mapstruct.ap.internal.prism.InheritConfigurationPrism;
import org.mapstruct.ap.internal.prism.InheritInverseConfigurationPrism;
import org.mapstruct.ap.internal.prism.MapperPrism;
import org.mapstruct.ap.internal.prism.NullValueMappingStrategyPrism;
import org.mapstruct.ap.internal.processor.creation.MappingResolverImpl;
import org.mapstruct.ap.internal.util.FormattingMessager;
import org.mapstruct.ap.internal.util.MapperConfiguration;
import org.mapstruct.ap.internal.util.Message;
import org.mapstruct.ap.internal.util.Strings;
import org.mapstruct.ap.internal.version.VersionInformation;

/**
 * A {@link ModelElementProcessor} which creates a {@link Mapper} from the given
 * list of {@link SourceMethod}s.
 *
 * @author Gunnar Morling
 */
public class MapperCreationProcessor implements ModelElementProcessor<List<SourceMethod>, Mapper> {

    private Elements elementUtils;
    private Types typeUtils;
    private FormattingMessager messager;
    private Options options;
    private VersionInformation versionInformation;
    private TypeFactory typeFactory;
    private MappingBuilderContext mappingContext;

    @Override
    public Mapper process(ProcessorContext context, TypeElement mapperTypeElement, List<SourceMethod> sourceModel) {
        this.elementUtils = context.getElementUtils();
        this.typeUtils = context.getTypeUtils();
        this.messager = context.getMessager();
        this.options = context.getOptions();
        this.versionInformation = context.getVersionInformation();
        this.typeFactory = context.getTypeFactory();

        MapperConfiguration mapperConfig = MapperConfiguration.getInstanceOn( mapperTypeElement );
        List<MapperReference> mapperReferences = initReferencedMappers( mapperTypeElement, mapperConfig );

        MappingBuilderContext ctx = new MappingBuilderContext(
            typeFactory,
            elementUtils,
            typeUtils,
            messager,
            options,
            new MappingResolverImpl(
                messager,
                elementUtils,
                typeUtils,
                typeFactory,
                sourceModel,
                mapperReferences
            ),
            mapperTypeElement,
            sourceModel,
            mapperReferences
        );
        this.mappingContext = ctx;
        return getMapper( mapperTypeElement, mapperConfig, sourceModel );
    }

    @Override
    public int getPriority() {
        return 1000;
    }

    private List<MapperReference> initReferencedMappers(TypeElement element, MapperConfiguration mapperConfig) {
        List<MapperReference> result = new LinkedList<MapperReference>();
        List<String> variableNames = new LinkedList<String>();

        for ( TypeMirror usedMapper : mapperConfig.uses() ) {
            DefaultMapperReference mapperReference = DefaultMapperReference.getInstance(
                typeFactory.getType( usedMapper ),
                MapperPrism.getInstanceOn( typeUtils.asElement( usedMapper ) ) != null,
                typeFactory,
                variableNames
            );

            result.add( mapperReference );
            variableNames.add( mapperReference.getVariableName() );
        }

        return result;
    }

    private Mapper getMapper(TypeElement element, MapperConfiguration mapperConfig, List<SourceMethod> methods) {
        List<MapperReference> mapperReferences = mappingContext.getMapperReferences();
        List<MappingMethod> mappingMethods = getMappingMethods( mapperConfig, methods );
        mappingMethods.addAll( mappingContext.getUsedVirtualMappings() );
        mappingMethods.addAll( mappingContext.getMappingsToGenerate() );

        Mapper mapper = new Mapper.Builder()
            .element( element )
            .mappingMethods( mappingMethods )
            .mapperReferences( mapperReferences )
            .options( options )
            .versionInformation( versionInformation )
            .decorator( getDecorator( element, methods, mapperConfig.implementationName(),
                        mapperConfig.implementationPackage() ) )
            .typeFactory( typeFactory )
            .elementUtils( elementUtils )
            .extraImports( getExtraImports( element ) )
            .implName( mapperConfig.implementationName() )
            .implPackage( mapperConfig.implementationPackage() )
            .build();

        return mapper;
    }

    private Decorator getDecorator(TypeElement element, List<SourceMethod> methods, String implName,
                                   String implPackage) {
        DecoratedWithPrism decoratorPrism = DecoratedWithPrism.getInstanceOn( element );

        if ( decoratorPrism == null ) {
            return null;
        }

        TypeElement decoratorElement = (TypeElement) typeUtils.asElement( decoratorPrism.value() );

        if ( !typeUtils.isAssignable( decoratorElement.asType(), element.asType() ) ) {
            messager.printMessage( element, decoratorPrism.mirror, Message.DECORATOR_NO_SUBTYPE );
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
            if ( implementationRequired && !( mappingMethod.isDefault() || mappingMethod.isStatic()) ) {
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
                    first( constructor.getParameters() ).asType()
                ) ) {
                    hasDelegateConstructor = true;
                }
            }
        }

        if ( !hasDelegateConstructor && !hasDefaultConstructor ) {
            messager.printMessage( element, decoratorPrism.mirror, Message.DECORATOR_CONSTRUCTOR );
        }

        Decorator decorator = new Decorator.Builder()
             .elementUtils( elementUtils )
             .typeFactory( typeFactory )
             .mapperElement( element )
             .decoratorPrism( decoratorPrism )
             .methods( mappingMethods )
             .hasDelegateConstructor( hasDelegateConstructor )
             .options( options )
             .versionInformation( versionInformation )
            .implName( implName )
            .implPackage( implPackage )
            .extraImports( getExtraImports( element ) )
             .build();

        return decorator;
    }

    private SortedSet<Type> getExtraImports(TypeElement element) {
        SortedSet<Type> extraImports = new TreeSet<Type>();

        MapperConfiguration mapperConfiguration = MapperConfiguration.getInstanceOn( element );

        for ( TypeMirror extraImport : mapperConfiguration.imports() ) {
            Type type = typeFactory.getType( extraImport );
            extraImports.add( type );
        }

        // Add original package if a dest package has been set
        if ( !"default".equals( mapperConfiguration.implementationPackage() ) ) {
            extraImports.add( typeFactory.getType( element ) );
        }

        return extraImports;
    }

    private List<MappingMethod> getMappingMethods(MapperConfiguration mapperConfig, List<SourceMethod> methods) {
        List<MappingMethod> mappingMethods = new ArrayList<MappingMethod>();

        for ( SourceMethod method : methods ) {
            if ( !method.overridesMethod() ) {
                continue;
            }

            mergeInheritedOptions( method, mapperConfig, methods, new ArrayList<SourceMethod>() );

            MappingOptions mappingOptions = method.getMappingOptions();

            boolean hasFactoryMethod = false;

            if ( method.isIterableMapping() ) {

                IterableMappingMethod.Builder builder = new IterableMappingMethod.Builder();

                String dateFormat = null;
                List<TypeMirror> qualifiers = null;
                TypeMirror qualifyingElementTargetType = null;
                NullValueMappingStrategyPrism nullValueMappingStrategy = null;

                if ( mappingOptions.getIterableMapping() != null ) {
                    dateFormat = mappingOptions.getIterableMapping().getDateFormat();
                    qualifiers = mappingOptions.getIterableMapping().getQualifiers();
                    qualifyingElementTargetType = mappingOptions.getIterableMapping().getQualifyingElementTargetType();
                    nullValueMappingStrategy = mappingOptions.getIterableMapping().getNullValueMappingStrategy();
                }

                IterableMappingMethod iterableMappingMethod = builder
                    .mappingContext( mappingContext )
                    .method( method )
                    .dateFormat( dateFormat )
                    .qualifiers( qualifiers )
                    .qualifyingElementTargetType( qualifyingElementTargetType )
                    .nullValueMappingStrategy( nullValueMappingStrategy )
                    .build();

                hasFactoryMethod = iterableMappingMethod.getFactoryMethod() != null;
                mappingMethods.add( iterableMappingMethod );
            }
            else if ( method.isMapMapping() ) {

                MapMappingMethod.Builder builder = new MapMappingMethod.Builder();

                String keyDateFormat = null;
                String valueDateFormat = null;
                List<TypeMirror> keyQualifiers = null;
                List<TypeMirror> valueQualifiers = null;
                TypeMirror keyQualifyingTargetType = null;
                TypeMirror valueQualifyingTargetType = null;
                NullValueMappingStrategyPrism nullValueMappingStrategy = null;

                if ( mappingOptions.getMapMapping() != null ) {
                    keyDateFormat = mappingOptions.getMapMapping().getKeyFormat();
                    valueDateFormat = mappingOptions.getMapMapping().getValueFormat();
                    keyQualifiers = mappingOptions.getMapMapping().getKeyQualifiers();
                    valueQualifiers = mappingOptions.getMapMapping().getValueQualifiers();
                    keyQualifyingTargetType = mappingOptions.getMapMapping().getKeyQualifyingTargetType();
                    valueQualifyingTargetType = mappingOptions.getMapMapping().getValueQualifyingTargetType();
                    nullValueMappingStrategy = mappingOptions.getMapMapping().getNullValueMappingStrategy();
                }

                MapMappingMethod mapMappingMethod = builder
                    .mappingContext( mappingContext )
                    .method( method )
                    .keyDateFormat( keyDateFormat )
                    .valueDateFormat( valueDateFormat )
                    .keyQualifiers( keyQualifiers )
                    .valueQualifiers( valueQualifiers )
                    .keyQualifyingTargetType( keyQualifyingTargetType )
                    .valueQualifyingTargetType( valueQualifyingTargetType )
                    .nullValueMappingStrategy( nullValueMappingStrategy )
                    .build();

                hasFactoryMethod = mapMappingMethod.getFactoryMethod() != null;
                mappingMethods.add( mapMappingMethod );
            }
            else if ( method.isEnumMapping() ) {

                EnumMappingMethod.Builder builder = new EnumMappingMethod.Builder();
                MappingMethod enumMappingMethod = builder
                    .mappingContext( mappingContext )
                    .souceMethod( method )
                    .build();

                if ( enumMappingMethod != null ) {
                    mappingMethods.add( enumMappingMethod );
                }
            }
            else {

                NullValueMappingStrategyPrism nullValueMappingStrategy = null;
                TypeMirror resultType = null;
                List<TypeMirror> qualifiers = null;

                if ( mappingOptions.getBeanMapping() != null ) {
                    nullValueMappingStrategy = mappingOptions.getBeanMapping().getNullValueMappingStrategy();
                    resultType = mappingOptions.getBeanMapping().getResultType();
                    qualifiers = mappingOptions.getBeanMapping().getQualifiers();
                }
                BeanMappingMethod.Builder builder = new BeanMappingMethod.Builder();
                BeanMappingMethod beanMappingMethod = builder
                    .mappingContext( mappingContext )
                    .souceMethod( method )
                    .nullValueMappingStrategy( nullValueMappingStrategy )
                    .qualifiers( qualifiers )
                    .resultType( resultType )
                    .build();

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

    private void mergeInheritedOptions(SourceMethod method, MapperConfiguration mapperConfig,
                                       List<SourceMethod> availableMethods, List<SourceMethod> initializingMethods) {
        if ( initializingMethods.contains( method ) ) {
            // cycle detected

            initializingMethods.add( method );

            messager.printMessage(
                method.getExecutable(),
                Message.INHERITCONFIGURATION_CYCLE,
                Strings.join( initializingMethods, " -> " ) );
            return;
        }

        initializingMethods.add( method );

        MappingOptions mappingOptions = method.getMappingOptions();
        List<SourceMethod> applicablePrototypeMethods = method.getApplicablePrototypeMethods();

        MappingOptions inverseMappingOptions =
            getInverseMappingOptions( availableMethods, method, initializingMethods, mapperConfig );

        MappingOptions templateMappingOptions =
            getTemplateMappingOptions(
                join( availableMethods, applicablePrototypeMethods ),
                method,
                initializingMethods,
                mapperConfig );

        if ( templateMappingOptions != null ) {
            mappingOptions.applyInheritedOptions( templateMappingOptions, false, method, messager, typeFactory );
        }
        else if ( inverseMappingOptions != null ) {
            mappingOptions.applyInheritedOptions( inverseMappingOptions, true, method, messager, typeFactory );
        }
        else if ( mapperConfig.getMappingInheritanceStrategy() == AUTO_INHERIT_FROM_CONFIG ) {
            if ( applicablePrototypeMethods.size() == 1 ) {
                mappingOptions.applyInheritedOptions(
                    first( applicablePrototypeMethods ).getMappingOptions(),
                    false,
                    method,
                    messager,
                    typeFactory );
            }
            else if ( applicablePrototypeMethods.size() > 1 ) {
                messager.printMessage(
                    method.getExecutable(),
                    Message.INHERITCONFIGURATION_MULTIPLE_PROTOTYPE_METHODS_MATCH,
                    Strings.join( applicablePrototypeMethods, ", " ) );
            }
        }

        mappingOptions.markAsFullyInitialized();
    }

    private void reportErrorIfNoImplementationTypeIsRegisteredForInterfaceReturnType(SourceMethod method) {
        if ( method.getReturnType().getTypeMirror().getKind() != TypeKind.VOID &&
            method.getReturnType().isInterface() &&
            method.getReturnType().getImplementationType() == null ) {
            messager.printMessage( method.getExecutable(), Message.GENERAL_NO_IMPLEMENTATION, method.getReturnType() );
        }
    }

    /**
     * Returns the configuring inverse method's options in case the given method is annotated with
     * {@code @InheritInverseConfiguration} and exactly one such configuring method can unambiguously be selected (as
     * per the source/target type and optionally the name given via {@code @InheritInverseConfiguration}).
     */
    private MappingOptions getInverseMappingOptions(List<SourceMethod> rawMethods, SourceMethod method,
                                                    List<SourceMethod> initializingMethods,
                                                    MapperConfiguration mapperConfig) {
        SourceMethod resultMethod = null;
        InheritInverseConfigurationPrism reversePrism = InheritInverseConfigurationPrism.getInstanceOn(
            method.getExecutable()
        );

        if ( reversePrism != null ) {

            // method is configured as being reverse method, collect candidates
            List<SourceMethod> candidates = new ArrayList<SourceMethod>();
            for ( SourceMethod oneMethod : rawMethods ) {
                if ( oneMethod.reverses( method ) ) {
                    candidates.add( oneMethod );
                }
            }

            String name = reversePrism.name();
            if ( candidates.size() == 1 ) {
                // no ambiguity: if no configuredBy is specified, or configuredBy specified and match
                if ( name.isEmpty() ) {
                    resultMethod = candidates.get( 0 );
                }
                else if ( candidates.get( 0 ).getName().equals( name ) ) {
                    resultMethod = candidates.get( 0 );
                }
                else {
                    reportErrorWhenNonMatchingName( candidates.get( 0 ), method, reversePrism );
                }
            }
            else if ( candidates.size() > 1 ) {
                // ambiguity: find a matching method that matches configuredBy

                List<SourceMethod> nameFilteredcandidates = new ArrayList<SourceMethod>();
                for ( SourceMethod candidate : candidates ) {
                    if ( candidate.getName().equals( name ) ) {
                        nameFilteredcandidates.add( candidate );
                    }
                }

                if ( nameFilteredcandidates.size() == 1 ) {
                    resultMethod = nameFilteredcandidates.get( 0 );
                }
                else if ( nameFilteredcandidates.size() > 1 ) {
                    reportErrorWhenSeveralNamesMatch( nameFilteredcandidates, method, reversePrism );
                }
                else {
                    reportErrorWhenAmbigousReverseMapping( candidates, method, reversePrism );
                }
            }
        }

        return extractInitializedOptions( resultMethod, rawMethods, mapperConfig, initializingMethods );
    }

    private MappingOptions extractInitializedOptions(SourceMethod resultMethod,
                                                     List<SourceMethod> rawMethods,
                                                     MapperConfiguration mapperConfig,
                                                     List<SourceMethod> initializingMethods) {
        if ( resultMethod != null ) {
            if ( !resultMethod.getMappingOptions().isFullyInitialized() ) {
                mergeInheritedOptions( resultMethod, mapperConfig, rawMethods, initializingMethods );
            }

            return resultMethod.getMappingOptions();
        }

        return null;
    }

    /**
     * Returns the configuring forward method's options in case the given method is annotated with
     * {@code @InheritConfiguration} and exactly one such configuring method can unambiguously be selected (as per the
     * source/target type and optionally the name given via {@code @InheritConfiguration}). The method cannot be marked
     * forward mapping itself (hence 'ohter'). And neither can it contain an {@code @InheritReverseConfiguration}
     */
    private MappingOptions getTemplateMappingOptions(List<SourceMethod> rawMethods, SourceMethod method,
                                                     List<SourceMethod> initializingMethods,
                                                     MapperConfiguration mapperConfig) {
        SourceMethod resultMethod = null;
        InheritConfigurationPrism forwardPrism = InheritConfigurationPrism.getInstanceOn(
            method.getExecutable()
        );

        if (forwardPrism != null) {
            reportErrorWhenInheritForwardAlsoHasInheritReverseMapping( method );

            List<SourceMethod> candidates = new ArrayList<SourceMethod>();
            for ( SourceMethod oneMethod : rawMethods ) {
                // method must be similar but not equal
                if ( method.canInheritFrom( oneMethod ) && !( oneMethod.equals( method ) ) ) {
                    candidates.add( oneMethod );
                }
            }

            String name = forwardPrism.name();
            if ( candidates.size() == 1 ) {
                // no ambiguity: if no configuredBy is specified, or configuredBy specified and match
                SourceMethod sourceMethod = first( candidates );
                if ( name.isEmpty() ) {
                    resultMethod = sourceMethod;
                }
                else if ( sourceMethod.getName().equals( name ) ) {
                    resultMethod = sourceMethod;
                }
                else {
                    reportErrorWhenNonMatchingName( sourceMethod, method, forwardPrism );
                }
            }
            else if ( candidates.size() > 1 ) {
                // ambiguity: find a matching method that matches configuredBy

                List<SourceMethod> nameFilteredcandidates = new ArrayList<SourceMethod>();
                for ( SourceMethod candidate : candidates ) {
                    if ( candidate.getName().equals( name ) ) {
                        nameFilteredcandidates.add( candidate );
                    }
                }

                if ( nameFilteredcandidates.size() == 1 ) {
                    resultMethod = first( nameFilteredcandidates );
                }
                else if ( nameFilteredcandidates.size() > 1 ) {
                    reportErrorWhenSeveralNamesMatch( nameFilteredcandidates, method, forwardPrism );
                }
                else {
                    reportErrorWhenAmbigousMapping( candidates, method, forwardPrism );
                }
            }
        }

        return extractInitializedOptions( resultMethod, rawMethods, mapperConfig, initializingMethods );
    }

    private void reportErrorWhenInheritForwardAlsoHasInheritReverseMapping(SourceMethod method) {
        InheritInverseConfigurationPrism reversePrism = InheritInverseConfigurationPrism.getInstanceOn(
                method.getExecutable()
        );
        if ( reversePrism != null ) {
            messager.printMessage( method.getExecutable(), reversePrism.mirror, Message.INHERITCONFIGURATION_BOTH );
        }

    }

    private void reportErrorWhenAmbigousReverseMapping(List<SourceMethod> candidates, SourceMethod method,
                                                       InheritInverseConfigurationPrism reversePrism) {

        List<String> candidateNames = new ArrayList<String>();
        for ( SourceMethod candidate : candidates ) {
            candidateNames.add( candidate.getName() );
        }

        String name = reversePrism.name();
        if ( name.isEmpty() ) {
            messager.printMessage( method.getExecutable(),
                reversePrism.mirror,
                Message.INHERITINVERSECONFIGURATION_DUPLICATES,
                Strings.join( candidateNames, "(), " )

            );
        }
        else {
            messager.printMessage( method.getExecutable(),
                reversePrism.mirror,
                Message.INHERITINVERSECONFIGURATION_INVALID_NAME,
                Strings.join( candidateNames, "(), " ),
                name

            );
        }
    }

    private void reportErrorWhenSeveralNamesMatch(List<SourceMethod> candidates, SourceMethod method,
                                                  InheritInverseConfigurationPrism reversePrism) {

        messager.printMessage( method.getExecutable(),
            reversePrism.mirror,
            Message.INHERITINVERSECONFIGURATION_DUPLICATE_MATCHES,
            reversePrism.name(),
            Strings.join( candidates, ", " )

        );
    }

    private void reportErrorWhenNonMatchingName(SourceMethod onlyCandidate, SourceMethod method,
            InheritInverseConfigurationPrism reversePrism) {

        messager.printMessage( method.getExecutable(),
            reversePrism.mirror,
            Message.INHERITINVERSECONFIGURATION_NO_NAME_MATCH,
            reversePrism.name(),
            onlyCandidate.getName()
        );
    }

    private void reportErrorWhenAmbigousMapping(List<SourceMethod> candidates, SourceMethod method,
                                                       InheritConfigurationPrism prism) {

        List<String> candidateNames = new ArrayList<String>();
        for ( SourceMethod candidate : candidates ) {
            candidateNames.add( candidate.getName() );
        }

        String name = prism.name();
        if ( name.isEmpty() ) {
            messager.printMessage( method.getExecutable(),
                prism.mirror,
                Message.INHERITCONFIGURATION_DUPLICATES,
                Strings.join( candidateNames, "(), " )
            );
        }
        else {
            messager.printMessage( method.getExecutable(),
                prism.mirror,
                Message.INHERITCONFIGURATION_INVALIDNAME,
                Strings.join( candidateNames, "(), " ),
                name
            );
        }
    }

    private void reportErrorWhenSeveralNamesMatch(List<SourceMethod> candidates, SourceMethod method,
                                                  InheritConfigurationPrism prism) {

        messager.printMessage( method.getExecutable(),
            prism.mirror,
            Message.INHERITCONFIGURATION_DUPLICATE_MATCHES,
            prism.name(),
            Strings.join( candidates, ", " )
        );
    }

    private void reportErrorWhenNonMatchingName(SourceMethod onlyCandidate, SourceMethod method,
                                                InheritConfigurationPrism prims) {

        messager.printMessage( method.getExecutable(),
            prims.mirror,
            Message.INHERITCONFIGURATION_NO_NAME_MATCH,
            prims.name(),
            onlyCandidate.getName()
        );
    }
}
