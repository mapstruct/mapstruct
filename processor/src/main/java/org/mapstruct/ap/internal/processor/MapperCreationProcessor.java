/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.processor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
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
import org.mapstruct.ap.internal.model.ContainerMappingMethod;
import org.mapstruct.ap.internal.model.ContainerMappingMethodBuilder;
import org.mapstruct.ap.internal.model.Decorator;
import org.mapstruct.ap.internal.model.DefaultMapperReference;
import org.mapstruct.ap.internal.model.DelegatingMethod;
import org.mapstruct.ap.internal.model.Field;
import org.mapstruct.ap.internal.model.IterableMappingMethod;
import org.mapstruct.ap.internal.model.MapMappingMethod;
import org.mapstruct.ap.internal.model.Mapper;
import org.mapstruct.ap.internal.model.MapperReference;
import org.mapstruct.ap.internal.model.MappingBuilderContext;
import org.mapstruct.ap.internal.model.MappingMethod;
import org.mapstruct.ap.internal.model.StreamMappingMethod;
import org.mapstruct.ap.internal.model.SupportingConstructorFragment;
import org.mapstruct.ap.internal.model.ValueMappingMethod;
import org.mapstruct.ap.internal.model.common.FormattingParameters;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.model.source.MapperOptions;
import org.mapstruct.ap.internal.model.source.MappingMethodOptions;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.model.source.SelectionParameters;
import org.mapstruct.ap.internal.model.source.SourceMethod;
import org.mapstruct.ap.internal.option.Options;
import org.mapstruct.ap.internal.gem.BuilderGem;
import org.mapstruct.ap.internal.gem.DecoratedWithGem;
import org.mapstruct.ap.internal.gem.InheritConfigurationGem;
import org.mapstruct.ap.internal.gem.InheritInverseConfigurationGem;
import org.mapstruct.ap.internal.gem.MapperGem;
import org.mapstruct.ap.internal.gem.MappingInheritanceStrategyGem;
import org.mapstruct.ap.internal.gem.NullValueMappingStrategyGem;
import org.mapstruct.ap.internal.processor.creation.MappingResolverImpl;
import org.mapstruct.ap.internal.util.AccessorNamingUtils;
import org.mapstruct.ap.internal.util.FormattingMessager;
import org.mapstruct.ap.internal.util.Message;
import org.mapstruct.ap.internal.util.Strings;
import org.mapstruct.ap.internal.version.VersionInformation;

import static org.mapstruct.ap.internal.model.SupportingConstructorFragment.addAllFragmentsIn;
import static org.mapstruct.ap.internal.model.SupportingField.addAllFieldsIn;
import static org.mapstruct.ap.internal.util.Collections.first;
import static org.mapstruct.ap.internal.util.Collections.join;

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
    private AccessorNamingUtils accessorNaming;
    private MappingBuilderContext mappingContext;

    @Override
    public Mapper process(ProcessorContext context, TypeElement mapperTypeElement, List<SourceMethod> sourceModel) {
        this.elementUtils = context.getElementUtils();
        this.typeUtils = context.getTypeUtils();
        this.messager = context.getMessager();
        this.options = context.getOptions();
        this.versionInformation = context.getVersionInformation();
        this.typeFactory = context.getTypeFactory();
        this.accessorNaming = context.getAccessorNaming();

        MapperOptions mapperOptions = MapperOptions.getInstanceOn( mapperTypeElement, context.getOptions() );
        List<MapperReference> mapperReferences = initReferencedMappers( mapperTypeElement, mapperOptions );

        MappingBuilderContext ctx = new MappingBuilderContext(
            typeFactory,
            elementUtils,
            typeUtils,
            messager,
            accessorNaming,
            options,
            new MappingResolverImpl(
                messager,
                elementUtils,
                typeUtils,
                typeFactory,
                new ArrayList<>( sourceModel ),
                mapperReferences
            ),
            mapperTypeElement,
            //sourceModel is passed only to fetch the after/before mapping methods in lifecycleCallbackFactory;
            //Consider removing those methods directly into MappingBuilderContext.
            Collections.unmodifiableList( sourceModel ),
            mapperReferences
        );
        this.mappingContext = ctx;
        return getMapper( mapperTypeElement, mapperOptions, sourceModel );
    }

    @Override
    public int getPriority() {
        return 1000;
    }

    private List<MapperReference> initReferencedMappers(TypeElement element, MapperOptions mapperAnnotation) {
        List<MapperReference> result = new LinkedList<>();
        List<String> variableNames = new LinkedList<>();

        for ( TypeMirror usedMapper : mapperAnnotation.uses() ) {
            DefaultMapperReference mapperReference = DefaultMapperReference.getInstance(
                typeFactory.getType( usedMapper ),
                MapperGem.instanceOn( typeUtils.asElement( usedMapper ) ) != null,
                typeFactory,
                variableNames
            );

            result.add( mapperReference );
            variableNames.add( mapperReference.getVariableName() );
        }

        return result;
    }

    private Mapper getMapper(TypeElement element, MapperOptions mapperOptions, List<SourceMethod> methods) {

        List<MappingMethod> mappingMethods = getMappingMethods( mapperOptions, methods );
        mappingMethods.addAll( mappingContext.getUsedSupportedMappings() );
        mappingMethods.addAll( mappingContext.getMappingsToGenerate() );

        // handle fields
        List<Field> fields = new ArrayList<>( mappingContext.getMapperReferences() );
        Set<Field> supportingFieldSet = new LinkedHashSet<>();
        addAllFieldsIn( mappingContext.getUsedSupportedMappings(), supportingFieldSet );
        fields.addAll( supportingFieldSet );

        // handle constructorfragments
        Set<SupportingConstructorFragment> constructorFragments = new LinkedHashSet<>();
        addAllFragmentsIn( mappingContext.getUsedSupportedMappings(), constructorFragments );

        Mapper mapper = new Mapper.Builder()
            .element( element )
            .methods( mappingMethods )
            .fields( fields )
            .constructorFragments(  constructorFragments )
            .options( options )
            .versionInformation( versionInformation )
            .decorator( getDecorator( element, methods, mapperOptions.implementationName(),
                mapperOptions.implementationPackage(), getExtraImports( element, mapperOptions ) ) )
            .typeFactory( typeFactory )
            .elementUtils( elementUtils )
            .extraImports( getExtraImports( element, mapperOptions ) )
            .implName( mapperOptions.implementationName() )
            .implPackage( mapperOptions.implementationPackage() )
            .build();

        if ( !mappingContext.getForgedMethodsUnderCreation().isEmpty() ) {
            messager.printMessage( element, Message.GENERAL_NOT_ALL_FORGED_CREATED,
                mappingContext.getForgedMethodsUnderCreation().keySet() );
        }

        return mapper;
    }

    private Decorator getDecorator(TypeElement element, List<SourceMethod> methods, String implName,
                                   String implPackage, SortedSet<Type> extraImports) {
        DecoratedWithGem decoratedWith = DecoratedWithGem.instanceOn( element );

        if ( decoratedWith == null ) {
            return null;
        }

        TypeElement decoratorElement = (TypeElement) typeUtils.asElement( decoratedWith.value().get() );

        if ( !typeUtils.isAssignable( decoratorElement.asType(), element.asType() ) ) {
            messager.printMessage( element, decoratedWith.mirror(), Message.DECORATOR_NO_SUBTYPE );
        }

        List<MappingMethod> mappingMethods = new ArrayList<>( methods.size() );

        for ( SourceMethod mappingMethod : methods ) {
            boolean implementationRequired = true;
            for ( ExecutableElement method : ElementFilter.methodsIn( decoratorElement.getEnclosedElements() ) ) {
                if ( elementUtils.overrides( method, mappingMethod.getExecutable(), decoratorElement ) ) {
                    implementationRequired = false;
                    break;
                }
            }
            Type declaringMapper = mappingMethod.getDeclaringMapper();
            if ( implementationRequired && !( mappingMethod.isDefault() || mappingMethod.isStatic() ) ) {
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
            messager.printMessage( element, decoratedWith.mirror(), Message.DECORATOR_CONSTRUCTOR );
        }

        Decorator decorator = new Decorator.Builder()
            .elementUtils( elementUtils )
            .typeFactory( typeFactory )
            .mapperElement( element )
            .decoratedWith( decoratedWith )
            .methods( mappingMethods )
            .hasDelegateConstructor( hasDelegateConstructor )
            .options( options )
            .versionInformation( versionInformation )
            .implName( implName )
            .implPackage( implPackage )
            .extraImports( extraImports )
            .build();

        return decorator;
    }

    private SortedSet<Type> getExtraImports(TypeElement element,  MapperOptions mapperOptions) {
        SortedSet<Type> extraImports = new TreeSet<>();


        for ( TypeMirror extraImport : mapperOptions.imports() ) {
            Type type = typeFactory.getType( extraImport );
            extraImports.add( type );
        }

        // Add original package if a dest package has been set
        if ( !"default".equals( mapperOptions.implementationPackage() ) ) {
            extraImports.add( typeFactory.getType( element ) );
        }

        return extraImports;
    }

    private List<MappingMethod> getMappingMethods(MapperOptions mapperAnnotation, List<SourceMethod> methods) {
        List<MappingMethod> mappingMethods = new ArrayList<>();

        for ( SourceMethod method : methods ) {
            if ( !method.overridesMethod() ) {
                continue;
            }

            mergeInheritedOptions( method, mapperAnnotation, methods, new ArrayList<>() );

            MappingMethodOptions mappingOptions = method.getOptions();

            boolean hasFactoryMethod = false;

            if ( method.isIterableMapping() ) {
                this.messager.note( 1, Message.ITERABLEMAPPING_CREATE_NOTE, method );


                IterableMappingMethod iterableMappingMethod = createWithElementMappingMethod(
                    method,
                    mappingOptions,
                    new IterableMappingMethod.Builder()
                );

                hasFactoryMethod = iterableMappingMethod.getFactoryMethod() != null;
                mappingMethods.add( iterableMappingMethod );
            }
            else if ( method.isMapMapping() ) {

                MapMappingMethod.Builder builder = new MapMappingMethod.Builder();

                SelectionParameters keySelectionParameters = null;
                FormattingParameters keyFormattingParameters = null;
                SelectionParameters valueSelectionParameters = null;
                FormattingParameters valueFormattingParameters = null;
                NullValueMappingStrategyGem nullValueMappingStrategy = null;

                if ( mappingOptions.getMapMapping() != null ) {
                    keySelectionParameters = mappingOptions.getMapMapping().getKeySelectionParameters();
                    keyFormattingParameters = mappingOptions.getMapMapping().getKeyFormattingParameters();
                    valueSelectionParameters = mappingOptions.getMapMapping().getValueSelectionParameters();
                    valueFormattingParameters = mappingOptions.getMapMapping().getValueFormattingParameters();
                    nullValueMappingStrategy = mappingOptions.getMapMapping().getNullValueMappingStrategy();
                }

                this.messager.note( 1, Message.MAPMAPPING_CREATE_NOTE, method );
                MapMappingMethod mapMappingMethod = builder
                    .mappingContext( mappingContext )
                    .method( method )
                    .keyFormattingParameters( keyFormattingParameters )
                    .keySelectionParameters( keySelectionParameters )
                    .valueFormattingParameters( valueFormattingParameters )
                    .valueSelectionParameters( valueSelectionParameters )
                    .nullValueMappingStrategy( nullValueMappingStrategy )
                    .build();

                hasFactoryMethod = mapMappingMethod.getFactoryMethod() != null;
                mappingMethods.add( mapMappingMethod );
            }
            else if ( method.isValueMapping() ) {
                // prefer value mappings over enum mapping
                this.messager.note( 1, Message.VALUEMAPPING_CREATE_NOTE, method );
                ValueMappingMethod valueMappingMethod = new ValueMappingMethod.Builder()
                    .mappingContext( mappingContext )
                    .method( method )
                    .valueMappings( mappingOptions.getValueMappings() )
                    .build();
                mappingMethods.add( valueMappingMethod );
            }
            else if ( method.isRemovedEnumMapping() ) {
                messager.printMessage(
                    method.getExecutable(),
                    Message.ENUMMAPPING_REMOVED
                );
            }
            else if ( method.isStreamMapping() ) {
                this.messager.note( 1, Message.STREAMMAPPING_CREATE_NOTE, method );
                StreamMappingMethod streamMappingMethod = createWithElementMappingMethod(
                    method,
                    mappingOptions,
                    new StreamMappingMethod.Builder()
                );

                // If we do StreamMapping that means that internally there is a way to generate the result type
                hasFactoryMethod =
                    streamMappingMethod.getFactoryMethod() != null || method.getResultType().isStreamType();
                mappingMethods.add( streamMappingMethod );
            }
            else {
                this.messager.note( 1, Message.BEANMAPPING_CREATE_NOTE, method );
                BuilderGem builder = method.getOptions().getBeanMapping().getBuilder();
                BeanMappingMethod.Builder beanMappingBuilder = new BeanMappingMethod.Builder();
                BeanMappingMethod beanMappingMethod = beanMappingBuilder
                    .mappingContext( mappingContext )
                    .sourceMethod( method )
                    .returnTypeBuilder( typeFactory.builderTypeFor( method.getReturnType(), builder ) )
                    .build();

                if ( beanMappingMethod != null ) {
                    // We can consider that the bean mapping method can always be constructed. If there is a problem
                    // it would have been reported in its build
                    hasFactoryMethod = true;
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

    private <M extends ContainerMappingMethod> M createWithElementMappingMethod(SourceMethod method,
             MappingMethodOptions mappingMethodOptions, ContainerMappingMethodBuilder<?, M> builder) {

        FormattingParameters formattingParameters = null;
        SelectionParameters selectionParameters = null;

        if ( mappingMethodOptions.getIterableMapping() != null ) {
            formattingParameters = mappingMethodOptions.getIterableMapping().getFormattingParameters();
            selectionParameters = mappingMethodOptions.getIterableMapping().getSelectionParameters();
        }

        return builder
            .mappingContext( mappingContext )
            .method( method )
            .formattingParameters( formattingParameters )
            .selectionParameters( selectionParameters )
            .build();
    }

    private void mergeInheritedOptions(SourceMethod method, MapperOptions mapperConfig,
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

        MappingMethodOptions mappingOptions = method.getOptions();
        List<SourceMethod> applicableReversePrototypeMethods = method.getApplicableReversePrototypeMethods();

        SourceMethod inverseTemplateMethod =
            getInverseTemplateMethod( join( availableMethods, applicableReversePrototypeMethods ),
                method,
                initializingMethods,
                mapperConfig );

        List<SourceMethod> applicablePrototypeMethods = method.getApplicablePrototypeMethods();

        SourceMethod forwardTemplateMethod =
            getForwardTemplateMethod(
                join( availableMethods, applicablePrototypeMethods ),
                method,
                initializingMethods,
                mapperConfig );

        // apply defined (@InheritConfiguration, @InheritInverseConfiguration) mappings
        if ( forwardTemplateMethod != null ) {
            mappingOptions.applyInheritedOptions( forwardTemplateMethod, false, method );
        }
        if ( inverseTemplateMethod != null ) {
            mappingOptions.applyInheritedOptions( inverseTemplateMethod, true, method );
        }

        // apply auto inherited options
        MappingInheritanceStrategyGem inheritanceStrategy = mapperConfig.getMappingInheritanceStrategy();
        if ( inheritanceStrategy.isAutoInherit() ) {

            // but.. there should not be an @InheritedConfiguration
            if ( forwardTemplateMethod == null && inheritanceStrategy.isApplyForward() ) {
                if ( applicablePrototypeMethods.size() == 1 ) {
                    mappingOptions.applyInheritedOptions(
                        first( applicablePrototypeMethods ),
                        false,
                        method
                    );
                }
                else if ( applicablePrototypeMethods.size() > 1 ) {
                    messager.printMessage(
                        method.getExecutable(),
                        Message.INHERITCONFIGURATION_MULTIPLE_PROTOTYPE_METHODS_MATCH,
                        Strings.join( applicablePrototypeMethods, ", " ) );
                }
            }

            // or no @InheritInverseConfiguration
            if ( inverseTemplateMethod == null && inheritanceStrategy.isApplyReverse() ) {
                if ( applicableReversePrototypeMethods.size() == 1 ) {
                    mappingOptions.applyInheritedOptions(
                        first( applicableReversePrototypeMethods ),
                        true,
                        method
                    );
                }
                else if ( applicableReversePrototypeMethods.size() > 1 ) {
                    messager.printMessage(
                        method.getExecutable(),
                        Message.INHERITINVERSECONFIGURATION_MULTIPLE_PROTOTYPE_METHODS_MATCH,
                        Strings.join( applicableReversePrototypeMethods, ", " ) );
                }
            }
        }

        // @BeanMapping( ignoreByDefault = true )
        if ( mappingOptions.getBeanMapping() != null && mappingOptions.getBeanMapping().isignoreByDefault() ) {
            mappingOptions.applyIgnoreAll( method, typeFactory );
        }

        mappingOptions.markAsFullyInitialized();
    }

    private void reportErrorIfNoImplementationTypeIsRegisteredForInterfaceReturnType(Method method) {
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
    private SourceMethod getInverseTemplateMethod(List<SourceMethod> rawMethods, SourceMethod method,
                                                  List<SourceMethod> initializingMethods,
                                                  MapperOptions mapperConfig) {
        SourceMethod resultMethod = null;
        InheritInverseConfigurationGem inverseConfiguration =
            InheritInverseConfigurationGem.instanceOn( method.getExecutable() );

        if ( inverseConfiguration != null ) {

            // method is configured as being inverse method, collect candidates
            List<SourceMethod> candidates = new ArrayList<>();
            for ( SourceMethod oneMethod : rawMethods ) {
                if ( method.inverses( oneMethod ) ) {
                    candidates.add( oneMethod );
                }
            }

            String name = inverseConfiguration.name().get();
            if ( candidates.size() == 1 ) {
                // no ambiguity: if no configuredBy is specified, or configuredBy specified and match
                if ( name.isEmpty() ) {
                    resultMethod = candidates.get( 0 );
                }
                else if ( candidates.get( 0 ).getName().equals( name ) ) {
                    resultMethod = candidates.get( 0 );
                }
                else {
                    reportErrorWhenNonMatchingName( candidates.get( 0 ), method, inverseConfiguration );
                }
            }
            else if ( candidates.size() > 1 ) {
                // ambiguity: find a matching method that matches configuredBy

                List<SourceMethod> nameFilteredcandidates = new ArrayList<>();
                for ( SourceMethod candidate : candidates ) {
                    if ( candidate.getName().equals( name ) ) {
                        nameFilteredcandidates.add( candidate );
                    }
                }

                if ( nameFilteredcandidates.size() == 1 ) {
                    resultMethod = nameFilteredcandidates.get( 0 );
                }
                else if ( nameFilteredcandidates.size() > 1 ) {
                    reportErrorWhenSeveralNamesMatch( nameFilteredcandidates, method, inverseConfiguration );
                }
                else {
                    reportErrorWhenAmbigousReverseMapping( candidates, method, inverseConfiguration );
                }
            }
        }

        return extractInitializedOptions( resultMethod, rawMethods, mapperConfig, initializingMethods );
    }

    private SourceMethod extractInitializedOptions(SourceMethod resultMethod,
                                                     List<SourceMethod> rawMethods,
                                                     MapperOptions mapperConfig,
                                                     List<SourceMethod> initializingMethods) {
        if ( resultMethod != null ) {
            if ( !resultMethod.getOptions().isFullyInitialized() ) {
                mergeInheritedOptions( resultMethod, mapperConfig, rawMethods, initializingMethods );
            }

            return resultMethod;
        }

        return null;
    }

    /**
     * Returns the configuring forward method's options in case the given method is annotated with
     * {@code @InheritConfiguration} and exactly one such configuring method can unambiguously be selected (as per the
     * source/target type and optionally the name given via {@code @InheritConfiguration}). The method cannot be marked
     * forward mapping itself (hence 'other'). And neither can it contain an {@code @InheritReverseConfiguration}
     */
    private SourceMethod getForwardTemplateMethod(List<SourceMethod> rawMethods, SourceMethod method,
                                                  List<SourceMethod> initializingMethods,
                                                  MapperOptions mapperConfig) {
        SourceMethod resultMethod = null;
        InheritConfigurationGem inheritConfiguration =
            InheritConfigurationGem.instanceOn( method.getExecutable() );

        if ( inheritConfiguration != null ) {

            List<SourceMethod> candidates = new ArrayList<>();
            for ( SourceMethod oneMethod : rawMethods ) {
                // method must be similar but not equal
                if ( method.canInheritFrom( oneMethod ) && !( oneMethod.equals( method ) ) ) {
                    candidates.add( oneMethod );
                }
            }

            String name = inheritConfiguration.name().get();
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
                    reportErrorWhenNonMatchingName( sourceMethod, method, inheritConfiguration );
                }
            }
            else if ( candidates.size() > 1 ) {
                // ambiguity: find a matching method that matches configuredBy

                List<SourceMethod> nameFilteredcandidates = new ArrayList<>();
                for ( SourceMethod candidate : candidates ) {
                    if ( candidate.getName().equals( name ) ) {
                        nameFilteredcandidates.add( candidate );
                    }
                }

                if ( nameFilteredcandidates.size() == 1 ) {
                    resultMethod = first( nameFilteredcandidates );
                }
                else if ( nameFilteredcandidates.size() > 1 ) {
                    reportErrorWhenSeveralNamesMatch( nameFilteredcandidates, method, inheritConfiguration );
                }
                else {
                    reportErrorWhenAmbigousMapping( candidates, method, inheritConfiguration );
                }
            }
        }

        return extractInitializedOptions( resultMethod, rawMethods, mapperConfig, initializingMethods );
    }

    private void reportErrorWhenAmbigousReverseMapping(List<SourceMethod> candidates, SourceMethod method,
                   InheritInverseConfigurationGem inverseGem) {

        List<String> candidateNames = new ArrayList<>();
        for ( SourceMethod candidate : candidates ) {
            candidateNames.add( candidate.getName() );
        }

        String name = inverseGem.name().get();
        if ( name.isEmpty() ) {
            messager.printMessage( method.getExecutable(),
                inverseGem.mirror(),
                Message.INHERITINVERSECONFIGURATION_DUPLICATES,
                Strings.join( candidateNames, "(), " )

            );
        }
        else {
            messager.printMessage( method.getExecutable(),
                inverseGem.mirror(),
                Message.INHERITINVERSECONFIGURATION_INVALID_NAME,
                Strings.join( candidateNames, "(), " ),
                name

            );
        }
    }

    private void reportErrorWhenSeveralNamesMatch(List<SourceMethod> candidates, SourceMethod method,
          InheritInverseConfigurationGem inverseGem) {

        messager.printMessage( method.getExecutable(),
            inverseGem.mirror(),
            Message.INHERITINVERSECONFIGURATION_DUPLICATE_MATCHES,
            inverseGem.name().get(),
            Strings.join( candidates, ", " )

        );
    }

    private void reportErrorWhenNonMatchingName(SourceMethod onlyCandidate, SourceMethod method,
                                            InheritInverseConfigurationGem inverseGem) {

        messager.printMessage( method.getExecutable(),
            inverseGem.mirror(),
            Message.INHERITINVERSECONFIGURATION_NO_NAME_MATCH,
            inverseGem.name().get(),
            onlyCandidate.getName()
        );
    }

    private void reportErrorWhenAmbigousMapping(List<SourceMethod> candidates, SourceMethod method,
                                                InheritConfigurationGem gem) {

        List<String> candidateNames = new ArrayList<>();
        for ( SourceMethod candidate : candidates ) {
            candidateNames.add( candidate.getName() );
        }

        String name = gem.name().get();
        if ( name.isEmpty() ) {
            messager.printMessage( method.getExecutable(),
                gem.mirror(),
                Message.INHERITCONFIGURATION_DUPLICATES,
                Strings.join( candidateNames, "(), " )
            );
        }
        else {
            messager.printMessage(
                method.getExecutable(),
                gem.mirror(),
                Message.INHERITCONFIGURATION_INVALIDNAME,
                Strings.join( candidateNames, "(), " ),
                name
            );
        }
    }

    private void reportErrorWhenSeveralNamesMatch(List<SourceMethod> candidates, SourceMethod method,
                                                  InheritConfigurationGem gem) {

        messager.printMessage(
            method.getExecutable(),
            gem.mirror(),
            Message.INHERITCONFIGURATION_DUPLICATE_MATCHES,
            gem.name().get(),
            Strings.join( candidates, ", " )
        );
    }

    private void reportErrorWhenNonMatchingName(SourceMethod onlyCandidate, SourceMethod method,
                                                InheritConfigurationGem gem) {

        messager.printMessage(
            method.getExecutable(),
            gem.mirror(),
            Message.INHERITCONFIGURATION_NO_NAME_MATCH,
            gem.name().get(),
            onlyCandidate.getName()
        );
    }
}
