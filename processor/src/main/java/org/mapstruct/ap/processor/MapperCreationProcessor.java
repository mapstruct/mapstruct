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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.annotation.processing.Messager;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
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
import org.mapstruct.ap.model.MappingBuilderContext;
import org.mapstruct.ap.model.MappingMethod;
import org.mapstruct.ap.model.common.Type;
import org.mapstruct.ap.model.common.TypeFactory;
import org.mapstruct.ap.model.source.SourceMethod;
import org.mapstruct.ap.option.Options;
import org.mapstruct.ap.prism.DecoratedWithPrism;
import org.mapstruct.ap.prism.InheritConfigurationPrism;
import org.mapstruct.ap.prism.InheritInverseConfigurationPrism;
import org.mapstruct.ap.prism.MapperPrism;
import org.mapstruct.ap.processor.creation.MappingResolverImpl;
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
    private MappingBuilderContext mappingContext;

    @Override
    public Mapper process(ProcessorContext context, TypeElement mapperTypeElement, List<SourceMethod> sourceModel) {
        this.elementUtils = context.getElementUtils();
        this.typeUtils = context.getTypeUtils();
        this.messager = context.getMessager();
        this.options = context.getOptions();
        this.typeFactory = context.getTypeFactory();

        List<MapperReference> mapperReferences = initReferencedMappers( mapperTypeElement );

        MappingBuilderContext ctx = new MappingBuilderContext(
            typeFactory,
            elementUtils,
            typeUtils,
            messager,
            options,
            new MappingResolverImpl(
                context.getMessager(),
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
        return getMapper( mapperTypeElement, sourceModel );
    }

    @Override
    public int getPriority() {
        return 1000;
    }

    private List<MapperReference> initReferencedMappers(TypeElement element) {
        List<MapperReference> result = new LinkedList<MapperReference>();
        List<String> variableNames = new LinkedList<String>();

        MapperConfig mapperPrism = MapperConfig.getInstanceOn( element );

        for ( TypeMirror usedMapper : mapperPrism.uses() ) {
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

    private Mapper getMapper(TypeElement element, List<SourceMethod> methods) {
        List<MapperReference> mapperReferences = mappingContext.getMapperReferences();
        List<MappingMethod> mappingMethods = getMappingMethods( methods );
        mappingMethods.addAll( mappingContext.getUsedVirtualMappings() );
        mappingMethods.addAll( mappingContext.getMappingsToGenerate() );

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

    private SortedSet<Type> getExtraImports(TypeElement element) {

        SortedSet<Type> extraImports = new TreeSet<Type>();

        MapperConfig mapperPrism = MapperConfig.getInstanceOn( element );

        for ( TypeMirror extraImport : mapperPrism.imports() ) {
            Type type = typeFactory.getType( extraImport );
            extraImports.add( type );
        }

        return extraImports;
    }

    private List<MappingMethod> getMappingMethods(List<SourceMethod> methods) {
        List<MappingMethod> mappingMethods = new ArrayList<MappingMethod>();

        for ( SourceMethod method : methods ) {
            if ( !method.overridesMethod() ) {
                continue;
            }

            SourceMethod inverseMappingMethod = getInverseMappingMethod( methods, method );
            SourceMethod templateMappingMethod = getTemplateMappingMethod( methods, method );

            boolean hasFactoryMethod = false;
            if ( method.isIterableMapping() ) {

                IterableMappingMethod.Builder builder = new IterableMappingMethod.Builder();
                if ( method.getIterableMapping() == null ) {
                    if ( inverseMappingMethod != null && inverseMappingMethod.getIterableMapping() != null ) {
                        method.setIterableMapping( inverseMappingMethod.getIterableMapping() );
                    }
                    else if ( templateMappingMethod != null && templateMappingMethod.getIterableMapping() != null ) {
                        method.setIterableMapping( templateMappingMethod.getIterableMapping() );
                    }
                }

                String dateFormat = null;
                List<TypeMirror> qualifiers = null;
                if ( method.getIterableMapping() != null ) {
                    dateFormat = method.getIterableMapping().getDateFormat();
                    qualifiers = method.getIterableMapping().getQualifiers();
                }

                IterableMappingMethod iterableMappingMethod = builder
                    .mappingContext( mappingContext )
                    .method( method )
                    .dateFormat( dateFormat )
                    .qualifiers( qualifiers )
                    .build();

                hasFactoryMethod = iterableMappingMethod.getFactoryMethod() != null;
                mappingMethods.add( iterableMappingMethod );
            }
            else if ( method.isMapMapping() ) {

                MapMappingMethod.Builder builder = new MapMappingMethod.Builder();

                if ( method.getMapMapping() == null ) {
                    if ( inverseMappingMethod != null && inverseMappingMethod.getMapMapping() != null ) {
                        method.setMapMapping( inverseMappingMethod.getMapMapping() );
                    }
                    else if ( templateMappingMethod != null && templateMappingMethod.getMapMapping() != null ) {
                        method.setMapMapping( templateMappingMethod.getMapMapping() );
                    }
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

                MapMappingMethod mapMappingMethod = builder
                    .mappingContext( mappingContext )
                    .method( method )
                    .keyDateFormat( keyDateFormat )
                    .valueDateFormat( valueDateFormat )
                    .keyQualifiers( keyQualifiers )
                    .valueQualifiers( valueQualifiers )
                    .build();

                hasFactoryMethod = mapMappingMethod.getFactoryMethod() != null;
                mappingMethods.add( mapMappingMethod );
            }
            else if ( method.isEnumMapping() ) {

                EnumMappingMethod.Builder builder = new EnumMappingMethod.Builder();
                method.mergeWithInverseMappings( inverseMappingMethod, templateMappingMethod );
                MappingMethod enumMappingMethod = builder
                    .mappingContext( mappingContext )
                    .souceMethod( method )
                    .build();

                if ( enumMappingMethod != null ) {
                    mappingMethods.add( enumMappingMethod );
                }
            }
            else {

                BeanMappingMethod.Builder builder = new BeanMappingMethod.Builder();
                method.mergeWithInverseMappings( inverseMappingMethod, templateMappingMethod );
                BeanMappingMethod beanMappingMethod = builder
                    .mappingContext( mappingContext )
                    .souceMethod( method )
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

    /**
     * Returns the configuring inverse method in case the given method is annotated with
     * {@code @InheritInverseConfiguration} and exactly one such configuring method can unambiguously be selected (as
     * per the source/target type and optionally the name given via {@code @InheritInverseConfiguration}).
     */
    private SourceMethod getInverseMappingMethod(List<SourceMethod> rawMethods, SourceMethod method) {
        SourceMethod result = null;
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
                    result = candidates.get( 0 );
                }
                else if ( candidates.get( 0 ).getName().equals( name ) ) {
                    result = candidates.get( 0 );
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
                    result = nameFilteredcandidates.get( 0 );
                }
                else if ( nameFilteredcandidates.size() > 1 ) {
                    reportErrorWhenSeveralNamesMatch( nameFilteredcandidates, method, reversePrism );
                }

                if ( result == null ) {
                    reportErrorWhenAmbigousReverseMapping( candidates, method, reversePrism );
                }
            }

            if ( result != null ) {
                reportErrorIfInverseMethodHasInheritAnnotation( result, method, reversePrism );
            }

        }
        return result;
    }

   /**
     * Returns the configuring forward method in case the given method is annotated with
     * {@code @InheritConfiguration} and exactly one such configuring method can unambiguously be selected (as
     * per the source/target type and optionally the name given via {@code @InheritConfiguration}).
     *
     * The method cannot be marked forward mapping itself (hence 'ohter'). And neither can it contain an
     * {@code @InheritReverseConfiguration}
     */
    private SourceMethod getTemplateMappingMethod(List<SourceMethod> rawMethods, SourceMethod method) {
        SourceMethod result = null;
        InheritConfigurationPrism forwardPrism = InheritConfigurationPrism.getInstanceOn(
            method.getExecutable()
        );

        if (forwardPrism != null) {
            reportErrorWhenInheritForwardAlsoHasInheritReverseMapping( method );

            // method is configured as being reverse method, collect candidates
            List<SourceMethod> candidates = new ArrayList<SourceMethod>();
            for ( SourceMethod oneMethod : rawMethods ) {
                // method must be similar but not equal
                if ( oneMethod.isSimilar( method ) && !( oneMethod.equals( method ) ) ) {
                    candidates.add( oneMethod );
                }
            }

            String name = forwardPrism.name();
            if ( candidates.size() == 1 ) {
                // no ambiguity: if no configuredBy is specified, or configuredBy specified and match
                if ( name.isEmpty() ) {
                    result = candidates.get( 0 );
                }
                else if ( candidates.get( 0 ).getName().equals( name ) ) {
                    result = candidates.get( 0 );
                }
                else {
                    reportErrorWhenNonMatchingName( candidates.get( 0 ), method, forwardPrism );
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
                    result = nameFilteredcandidates.get( 0 );
                }
                else if ( nameFilteredcandidates.size() > 1 ) {
                    reportErrorWhenSeveralNamesMatch( nameFilteredcandidates, method, forwardPrism );
                }

                if ( result == null ) {
                    reportErrorWhenAmbigousMapping( candidates, method, forwardPrism );
                }
            }

            if ( result != null ) {
                reportErrorIfMethodHasAnnotation( result, method, forwardPrism );
            }

        }
        return result;
    }

    private void reportErrorWhenInheritForwardAlsoHasInheritReverseMapping(SourceMethod method) {
        InheritInverseConfigurationPrism reversePrism = InheritInverseConfigurationPrism.getInstanceOn(
                method.getExecutable()
        );
        if ( reversePrism != null ) {
            messager.printMessage(
                Diagnostic.Kind.ERROR,
                "Method cannot be annotated with both a @InheritConfiguration and @InheritInverseConfiguration",
                method.getExecutable(),
                reversePrism.mirror
            );
        }

    }

    private void reportErrorIfInverseMethodHasInheritAnnotation(SourceMethod candidate,
                                                                SourceMethod method,
                                                                InheritInverseConfigurationPrism reversePrism) {

        InheritInverseConfigurationPrism candidatePrism = InheritInverseConfigurationPrism.getInstanceOn(
            candidate.getExecutable()
        );

        if ( candidatePrism != null ) {
            messager.printMessage(
                Diagnostic.Kind.ERROR,
                String.format(
                    "Resolved inverse mapping method %s() should not carry the "
                        + "@InheritInverseConfiguration annotation itself.",
                    candidate.getName()
                ),
                method.getExecutable(),
                reversePrism.mirror
            );
        }

        InheritConfigurationPrism candidateTemplatePrism = InheritConfigurationPrism.getInstanceOn(
            candidate.getExecutable()
        );
        if ( candidateTemplatePrism != null ) {
            messager.printMessage(
                Diagnostic.Kind.ERROR,
                String.format(
                    "Resolved inverse mapping method %s() should not carry the @InheritConfiguration annotation.",
                    candidate.getName()
                ),
                method.getExecutable(),
                reversePrism.mirror
            );
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
            messager.printMessage(
                Diagnostic.Kind.ERROR,
                String.format(
                    "Several matching inverse methods exist: %s(). Specify a name explicitly.",
                    Strings.join( candidateNames, "(), " )
                ),
                method.getExecutable(),
                reversePrism.mirror
            );
        }
        else {
            messager.printMessage(
                Diagnostic.Kind.ERROR,
                String.format(
                    "None of the candidates %s() matches given name: \"%s\".",
                    Strings.join( candidateNames, "(), " ), name
                ),
                method.getExecutable(),
                reversePrism.mirror
            );
        }
    }

    private void reportErrorWhenSeveralNamesMatch(List<SourceMethod> candidates, SourceMethod method,
                                                  InheritInverseConfigurationPrism reversePrism) {

        messager.printMessage(
            Diagnostic.Kind.ERROR,
            String.format(
                "Given name \"%s\" matches several candidate methods: %s().",
                reversePrism.name(), Strings.join( candidates, "(), " )
            ),
            method.getExecutable(),
            reversePrism.mirror
        );
    }

    private void reportErrorWhenNonMatchingName(SourceMethod onlyCandidate, SourceMethod method,
            InheritInverseConfigurationPrism reversePrism) {

        messager.printMessage(
            Diagnostic.Kind.ERROR,
            String.format(
                "Given name \"%s\" does not match the only candidate. Did you mean: \"%s\".",
                reversePrism.name(), onlyCandidate.getName()
            ),
            method.getExecutable(),
            reversePrism.mirror
        );
    }

    private void reportErrorIfMethodHasAnnotation(SourceMethod candidate,
            SourceMethod method,
            InheritConfigurationPrism prism) {

        InheritConfigurationPrism candidatePrism = InheritConfigurationPrism.getInstanceOn(
            candidate.getExecutable()
        );

        if ( candidatePrism != null ) {
            messager.printMessage(
                Diagnostic.Kind.ERROR,
                String.format(
                    "Resolved mapping method %s() should not carry the @InheritConfiguration annotation itself.",
                    candidate.getName()
                ),
                method.getExecutable(),
                prism.mirror
            );
        }

        InheritInverseConfigurationPrism candidateInversePrism = InheritInverseConfigurationPrism.getInstanceOn(
            candidate.getExecutable()
        );

        if ( candidateInversePrism != null ) {
            messager.printMessage(
                Diagnostic.Kind.ERROR,
                String.format(
                    "Resolved mapping method %s() should not carry the @InheritInverseConfiguration annotation.",
                    candidate.getName()
                ),
                method.getExecutable(),
                prism.mirror
            );
        }
    }

    private void reportErrorWhenAmbigousMapping(List<SourceMethod> candidates, SourceMethod method,
                                                       InheritConfigurationPrism prism) {

        List<String> candidateNames = new ArrayList<String>();
        for ( SourceMethod candidate : candidates ) {
            candidateNames.add( candidate.getName() );
        }

        String name = prism.name();
        if ( name.isEmpty() ) {
            messager.printMessage(
                Diagnostic.Kind.ERROR,
                String.format(
                    "Several matching methods exist: %s(). Specify a name explicitly.",
                    Strings.join( candidateNames, "(), " )
                ),
                method.getExecutable(),
                prism.mirror
            );
        }
        else {
            messager.printMessage(
                Diagnostic.Kind.ERROR,
                String.format(
                    "None of the candidates %s() matches given name: \"%s\".",
                    Strings.join( candidateNames, "(), " ), name
                ),
                method.getExecutable(),
                prism.mirror
            );
        }
    }

    private void reportErrorWhenSeveralNamesMatch(List<SourceMethod> candidates, SourceMethod method,
                                                  InheritConfigurationPrism prism) {

        messager.printMessage(
            Diagnostic.Kind.ERROR,
            String.format(
                "Given name \"%s\" matches several candidate methods: %s().",
                prism.name(), Strings.join( candidates, "(), " )
            ),
            method.getExecutable(),
            prism.mirror
        );
    }

    private void reportErrorWhenNonMatchingName(SourceMethod onlyCandidate, SourceMethod method,
                                                InheritConfigurationPrism prims) {

        messager.printMessage(
            Diagnostic.Kind.ERROR,
            String.format(
                "Given name \"%s\" does not match the only candidate. Did you mean: \"%s\".",
                prims.name(), onlyCandidate.getName()
            ),
            method.getExecutable(),
            prims.mirror
        );
    }


}
