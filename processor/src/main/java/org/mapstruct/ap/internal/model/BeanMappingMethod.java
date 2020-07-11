/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;

import org.mapstruct.ap.internal.gem.CollectionMappingStrategyGem;
import org.mapstruct.ap.internal.gem.ReportingPolicyGem;
import org.mapstruct.ap.internal.model.PropertyMapping.ConstantMappingBuilder;
import org.mapstruct.ap.internal.model.PropertyMapping.JavaExpressionMappingBuilder;
import org.mapstruct.ap.internal.model.PropertyMapping.PropertyMappingBuilder;
import org.mapstruct.ap.internal.model.beanmapping.MappingReference;
import org.mapstruct.ap.internal.model.beanmapping.MappingReferences;
import org.mapstruct.ap.internal.model.beanmapping.SourceReference;
import org.mapstruct.ap.internal.model.beanmapping.TargetReference;
import org.mapstruct.ap.internal.model.common.BuilderType;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.ParameterBinding;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.dependency.GraphAnalyzer;
import org.mapstruct.ap.internal.model.dependency.GraphAnalyzer.GraphAnalyzerBuilder;
import org.mapstruct.ap.internal.model.source.BeanMappingOptions;
import org.mapstruct.ap.internal.model.source.MappingOptions;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.model.source.SelectionParameters;
import org.mapstruct.ap.internal.model.source.SourceMethod;
import org.mapstruct.ap.internal.model.source.selector.SelectedMethod;
import org.mapstruct.ap.internal.util.Message;
import org.mapstruct.ap.internal.util.Strings;
import org.mapstruct.ap.internal.util.accessor.Accessor;
import org.mapstruct.ap.internal.util.accessor.AccessorType;
import org.mapstruct.ap.internal.util.accessor.ParameterElementAccessor;

import static org.mapstruct.ap.internal.model.beanmapping.MappingReferences.forSourceMethod;
import static org.mapstruct.ap.internal.util.Collections.first;
import static org.mapstruct.ap.internal.util.Message.BEANMAPPING_ABSTRACT;
import static org.mapstruct.ap.internal.util.Message.BEANMAPPING_NOT_ASSIGNABLE;
import static org.mapstruct.ap.internal.util.Message.GENERAL_ABSTRACT_RETURN_TYPE;
import static org.mapstruct.ap.internal.util.Message.GENERAL_AMBIGIOUS_CONSTRUCTORS;
import static org.mapstruct.ap.internal.util.Message.GENERAL_CONSTRUCTOR_PROPERTIES_NOT_MATCHING_PARAMETERS;

/**
 * A {@link MappingMethod} implemented by a {@link Mapper} class which maps one bean type to another, optionally
 * configured by one or more {@link PropertyMapping}s.
 *
 * @author Gunnar Morling
 */
public class BeanMappingMethod extends NormalTypeMappingMethod {

    private final List<PropertyMapping> propertyMappings;
    private final Map<String, List<PropertyMapping>> mappingsByParameter;
    private final Map<String, List<PropertyMapping>> constructorMappingsByParameter;
    private final List<PropertyMapping> constantMappings;
    private final List<PropertyMapping> constructorConstantMappings;
    private final Type returnTypeToConstruct;
    private final BuilderType returnTypeBuilder;
    private final MethodReference finalizerMethod;

    public static class Builder {

        private MappingBuilderContext ctx;
        private Method method;

        /* returnType to construct can have a builder */
        private BuilderType returnTypeBuilder;
        private Map<String, Accessor> unprocessedConstructorProperties;
        private Map<String, Accessor> unprocessedTargetProperties;
        private Map<String, Accessor> unprocessedSourceProperties;
        private Set<String> targetProperties;
        private final List<PropertyMapping> propertyMappings = new ArrayList<>();
        private final Set<Parameter> unprocessedSourceParameters = new HashSet<>();
        private final Set<String> existingVariableNames = new HashSet<>();
        private final Map<String, Set<MappingReference>> unprocessedDefinedTargets = new LinkedHashMap<>();

        private MappingReferences mappingReferences;
        private MethodReference factoryMethod;
        private boolean hasFactoryMethod;

        public Builder mappingContext(MappingBuilderContext mappingContext) {
            this.ctx = mappingContext;
            return this;
        }

        public Builder returnTypeBuilder( BuilderType returnTypeBuilder ) {
            this.returnTypeBuilder = returnTypeBuilder;
            return this;
        }

        public Builder sourceMethod(SourceMethod sourceMethod) {
            this.method = sourceMethod;
            return this;
        }

        public Builder forgedMethod(ForgedMethod forgedMethod) {
            this.method = forgedMethod;
            mappingReferences = forgedMethod.getMappingReferences();
            Parameter sourceParameter = first( Parameter.getSourceParameters( forgedMethod.getParameters() ) );
            for ( MappingReference mappingReference: mappingReferences.getMappingReferences() ) {
                SourceReference sourceReference = mappingReference.getSourceReference();
                if ( sourceReference != null ) {
                    mappingReference.setSourceReference( new SourceReference.BuilderFromSourceReference()
                        .sourceParameter( sourceParameter )
                        .sourceReference( sourceReference )
                        .build() );
                }
            }
            return this;
        }

        public BeanMappingMethod build() {

            BeanMappingOptions beanMapping = method.getOptions().getBeanMapping();
            SelectionParameters selectionParameters = beanMapping != null ? beanMapping.getSelectionParameters() : null;

            /* the return type that needs to be constructed (new or factorized), so for instance: */
            /*  1) the return type of a non-update method */
            /*  2) or the implementation type that needs to be used when the return type is abstract */
            /*  3) or the builder whenever the return type is immutable */
            Type returnTypeToConstruct = null;

            // determine which return type to construct
            boolean cannotConstructReturnType = false;
            if ( !method.getReturnType().isVoid() ) {
                Type returnTypeImpl = getReturnTypeToConstructFromSelectionParameters( selectionParameters );
                if ( returnTypeImpl != null ) {
                    initializeFactoryMethod( returnTypeImpl, selectionParameters );
                    if ( factoryMethod != null || canResultTypeFromBeanMappingBeConstructed( returnTypeImpl ) ) {
                        returnTypeToConstruct = returnTypeImpl;
                    }
                    else {
                        cannotConstructReturnType = true;
                    }
                }
                else if ( isBuilderRequired() ) {
                    returnTypeImpl = returnTypeBuilder.getBuilder();
                    initializeFactoryMethod( returnTypeImpl, selectionParameters );
                    if ( factoryMethod != null || canReturnTypeBeConstructed( returnTypeImpl ) ) {
                        returnTypeToConstruct = returnTypeImpl;
                    }
                    else {
                        cannotConstructReturnType = true;
                    }
                }
                else if ( !method.isUpdateMethod() ) {
                    returnTypeImpl = method.getReturnType();
                    initializeFactoryMethod( returnTypeImpl, selectionParameters );
                    if ( factoryMethod != null || canReturnTypeBeConstructed( returnTypeImpl ) ) {
                        returnTypeToConstruct = returnTypeImpl;
                    }
                    else {
                        cannotConstructReturnType = true;
                    }
                }
            }

            if ( cannotConstructReturnType ) {
                // If the return type cannot be constructed then no need to try to create mappings
                return null;
            }

            /* the type that needs to be used in the mapping process as target */
            Type resultTypeToMap = returnTypeToConstruct == null ? method.getResultType() : returnTypeToConstruct;

            existingVariableNames.addAll( method.getParameterNames() );

            CollectionMappingStrategyGem cms = this.method.getOptions().getMapper().getCollectionMappingStrategy();

            // determine accessors
            Map<String, Accessor> accessors = resultTypeToMap.getPropertyWriteAccessors( cms );
            this.targetProperties = new LinkedHashSet<>( accessors.keySet() );

            this.unprocessedTargetProperties = new LinkedHashMap<>( accessors );

            if ( !method.isUpdateMethod() && !hasFactoryMethod ) {
                ConstructorAccessor constructorAccessor = getConstructorAccessor( resultTypeToMap );
                if ( constructorAccessor != null ) {

                    this.unprocessedConstructorProperties = constructorAccessor.constructorAccessors;

                    factoryMethod = MethodReference.forConstructorInvocation(
                        resultTypeToMap,
                        constructorAccessor.parameterBindings
                    );

                }
                else {
                    this.unprocessedConstructorProperties = new LinkedHashMap<>();
                }

                this.targetProperties.addAll( this.unprocessedConstructorProperties.keySet() );
                this.unprocessedTargetProperties.putAll( this.unprocessedConstructorProperties );
            }
            else {
                unprocessedConstructorProperties = new LinkedHashMap<>();
            }

            this.unprocessedSourceProperties = new LinkedHashMap<>();
            for ( Parameter sourceParameter : method.getSourceParameters() ) {
                unprocessedSourceParameters.add( sourceParameter );

                if ( sourceParameter.getType().isPrimitive() || sourceParameter.getType().isArrayType() ) {
                    continue;
                }
                Map<String, Accessor> readAccessors = sourceParameter.getType().getPropertyReadAccessors();

                for ( Entry<String, Accessor> entry : readAccessors.entrySet() ) {
                    unprocessedSourceProperties.put( entry.getKey(), entry.getValue() );
                }
            }

            // get bean mapping (when specified as annotation )
            if ( beanMapping != null ) {
                for ( String ignoreUnmapped : beanMapping.getIgnoreUnmappedSourceProperties() ) {
                    unprocessedSourceProperties.remove( ignoreUnmapped );
                }
            }

            initializeMappingReferencesIfNeeded( resultTypeToMap );

            // map properties with mapping
            boolean mappingErrorOccurred = handleDefinedMappings( resultTypeToMap );
            if ( mappingErrorOccurred ) {
                return null;
            }

            if ( !mappingReferences.isRestrictToDefinedMappings() ) {

                // apply name based mapping from a source reference
                applyTargetThisMapping();

                // map properties without a mapping
                applyPropertyNameBasedMapping();

                // map parameters without a mapping
                applyParameterNameBasedMapping();
            }

            // Process the unprocessed defined targets
            handleUnprocessedDefinedTargets();

            // Initialize unprocessed constructor properties
            handleUnmappedConstructorProperties();

            // report errors on unmapped properties
            reportErrorForUnmappedTargetPropertiesIfRequired();
            reportErrorForUnmappedSourcePropertiesIfRequired();

            // mapNullToDefault
            boolean mapNullToDefault = method.getOptions()
                .getBeanMapping()
                .getNullValueMappingStrategy()
                .isReturnDefault();

            // sort
            sortPropertyMappingsByDependencies();

            // before / after mappings
            List<LifecycleCallbackMethodReference> beforeMappingMethods = LifecycleMethodResolver.beforeMappingMethods(
                            method,
                            resultTypeToMap,
                            selectionParameters,
                            ctx,
                            existingVariableNames
            );
            List<LifecycleCallbackMethodReference> afterMappingMethods = LifecycleMethodResolver.afterMappingMethods(
                            method,
                            resultTypeToMap,
                            selectionParameters,
                            ctx,
                            existingVariableNames
            );

            if (factoryMethod != null && method instanceof ForgedMethod ) {
                ( (ForgedMethod) method ).addThrownTypes( factoryMethod.getThrownTypes() );
            }

            MethodReference finalizeMethod = null;

            if ( shouldCallFinalizerMethod( returnTypeToConstruct ) ) {
                finalizeMethod = getFinalizerMethod();
            }

            return new BeanMappingMethod(
                method,
                existingVariableNames,
                propertyMappings,
                factoryMethod,
                mapNullToDefault,
                returnTypeToConstruct,
                returnTypeBuilder,
                beforeMappingMethods,
                afterMappingMethods,
                finalizeMethod
            );
        }

        private void initializeMappingReferencesIfNeeded(Type resultTypeToMap) {
            if ( mappingReferences == null && method instanceof SourceMethod ) {
                Set<String> readAndWriteTargetProperties = new HashSet<>( unprocessedTargetProperties.keySet() );
                readAndWriteTargetProperties.addAll( resultTypeToMap.getPropertyReadAccessors().keySet() );
                mappingReferences = forSourceMethod(
                    (SourceMethod) method,
                    resultTypeToMap,
                    readAndWriteTargetProperties,
                    ctx.getMessager(),
                    ctx.getTypeFactory()
                );
            }
        }

        /**
         * @return builder is required when there is a returnTypeBuilder and the mapping method is not update method.
         * However, builder is also required when there is a returnTypeBuilder, the mapping target is the builder and
         * builder is not assignable to the return type (so without building).
         */
        private boolean isBuilderRequired() {
            return returnTypeBuilder != null
                    && ( !method.isUpdateMethod() || !method.isMappingTargetAssignableToReturnType() );
        }

        private boolean shouldCallFinalizerMethod(Type returnTypeToConstruct ) {
            if ( returnTypeToConstruct == null ) {
                return false;
            }
            else if ( returnTypeToConstruct.isAssignableTo( method.getReturnType() ) ) {
                // If the mapping type can be assigned to the return type then we
                // don't need a finalizer method
                return false;
            }

            return returnTypeBuilder != null;
        }

        private MethodReference getFinalizerMethod() {
            return BuilderFinisherMethodResolver.getBuilderFinisherMethod(
                method,
                returnTypeBuilder,
                ctx
            );
        }

        /**
         * If there were nested defined targets that have not been handled. Then we need to process them at the end.
         */
        private void handleUnprocessedDefinedTargets() {
            Iterator<Entry<String, Set<MappingReference>>> iterator = unprocessedDefinedTargets.entrySet().iterator();

            // For each of the unprocessed defined targets forge a mapping for each of the
            // method source parameters. The generated mappings are not going to use forged name based mappings.
            while ( iterator.hasNext() ) {
                Entry<String, Set<MappingReference>> entry = iterator.next();
                String propertyName = entry.getKey();
                if ( !unprocessedTargetProperties.containsKey( propertyName ) ) {
                    continue;
                }
                List<Parameter> sourceParameters = method.getSourceParameters();
                boolean forceUpdateMethod = sourceParameters.size() > 1;
                for ( Parameter sourceParameter : sourceParameters ) {
                    SourceReference reference = new SourceReference.BuilderFromProperty()
                        .sourceParameter( sourceParameter )
                        .name( propertyName )
                        .build();

                    Accessor targetPropertyReadAccessor =
                        method.getResultType().getPropertyReadAccessors().get( propertyName );
                    MappingReferences mappingRefs = extractMappingReferences( propertyName, true );
                    PropertyMapping propertyMapping = new PropertyMappingBuilder()
                        .mappingContext( ctx )
                        .sourceMethod( method )
                        .target(
                            propertyName,
                            targetPropertyReadAccessor,
                            unprocessedTargetProperties.get( propertyName )
                        )
                        .sourceReference( reference )
                        .existingVariableNames( existingVariableNames )
                        .dependsOn( mappingRefs.collectNestedDependsOn() )
                        .forgeMethodWithMappingReferences( mappingRefs )
                        .forceUpdateMethod( forceUpdateMethod )
                        .forgedNamedBased( false )
                        .build();

                    if ( propertyMapping != null ) {
                        unprocessedTargetProperties.remove( propertyName );
                        unprocessedConstructorProperties.remove( propertyName );
                        unprocessedSourceProperties.remove( propertyName );
                        iterator.remove();
                        propertyMappings.add( propertyMapping );
                        // If we found a mapping for the unprocessed property then stop
                        break;
                    }
                }
            }
        }

        private void handleUnmappedConstructorProperties() {
            for ( Entry<String, Accessor> entry : unprocessedConstructorProperties.entrySet() ) {
                Accessor accessor = entry.getValue();
                Type accessedType = ctx.getTypeFactory()
                    .getType( accessor.getAccessedType() );
                String targetPropertyName = entry.getKey();

                propertyMappings.add( new JavaExpressionMappingBuilder()
                    .mappingContext( ctx )
                    .sourceMethod( method )
                    .javaExpression( accessedType.getNull() )
                    .existingVariableNames( existingVariableNames )
                    .target( targetPropertyName, null, accessor )
                    .dependsOn( Collections.emptySet() )
                    .mirror( null )
                    .build()
                );
            }

            unprocessedConstructorProperties.clear();
        }

        /**
         * Sources the given mappings as per the dependency relationships given via {@code dependsOn()}. If a cycle is
         * detected, an error is reported.
         */
        private void sortPropertyMappingsByDependencies() {
            GraphAnalyzerBuilder graphAnalyzerBuilder = GraphAnalyzer.builder();

            for ( PropertyMapping propertyMapping : propertyMappings ) {
                graphAnalyzerBuilder.withNode( propertyMapping.getName(), propertyMapping.getDependsOn() );
            }

            final GraphAnalyzer graphAnalyzer = graphAnalyzerBuilder.build();

            if ( !graphAnalyzer.getCycles().isEmpty() ) {
                Set<String> cycles = new HashSet<>();
                for ( List<String> cycle : graphAnalyzer.getCycles() ) {
                    cycles.add( Strings.join( cycle, " -> " ) );
                }

                ctx.getMessager().printMessage(
                    method.getExecutable(),
                    Message.BEANMAPPING_CYCLE_BETWEEN_PROPERTIES, Strings.join( cycles, ", " )
                );
            }
            else {
                propertyMappings.sort( Comparator.comparingInt( propertyMapping ->
                    graphAnalyzer.getTraversalSequence( propertyMapping.getName() ) ) );
            }
        }

        private Type getReturnTypeToConstructFromSelectionParameters(SelectionParameters selectionParams) {
            // resultType only applies to method that actually has @BeanMapping annotation, never to forged methods
            if ( !( method instanceof ForgedMethod )
                && selectionParams != null
                && selectionParams.getResultType() != null ) {
                return ctx.getTypeFactory().getType( selectionParams.getResultType() );
            }
            return null;
        }

        private boolean canResultTypeFromBeanMappingBeConstructed(Type resultType) {

            boolean error = true;
            if ( resultType.isAbstract() ) {
                ctx.getMessager().printMessage(
                    method.getExecutable(),
                    method.getOptions().getBeanMapping().getMirror(),
                    BEANMAPPING_ABSTRACT,
                    resultType,
                    method.getResultType()
                );
                error = false;
            }
            else if ( !resultType.isAssignableTo( method.getResultType() ) ) {
                ctx.getMessager().printMessage(
                    method.getExecutable(),
                    method.getOptions().getBeanMapping().getMirror(),
                    BEANMAPPING_NOT_ASSIGNABLE,
                    resultType,
                    method.getResultType()
                );
                error = false;
            }
            else if ( !resultType.hasAccessibleConstructor() ) {
                ctx.getMessager().printMessage(
                    method.getExecutable(),
                    method.getOptions().getBeanMapping().getMirror(),
                    Message.GENERAL_NO_SUITABLE_CONSTRUCTOR,
                    resultType
                );
                error = false;
            }
            return error;
        }

        private boolean canReturnTypeBeConstructed(Type returnType) {
            boolean error = true;
            if ( returnType.isAbstract() ) {
                ctx.getMessager().printMessage(
                    method.getExecutable(),
                    GENERAL_ABSTRACT_RETURN_TYPE,
                    returnType
                );
                error = false;
            }
            else if ( !returnType.hasAccessibleConstructor() ) {
                ctx.getMessager().printMessage(
                    method.getExecutable(),
                    Message.GENERAL_NO_SUITABLE_CONSTRUCTOR,
                    returnType
                );
                error = false;
            }
            return error;
        }

        /**
         * Find a factory method for a return type or for a builder.
         * @param returnTypeImpl the return type implementation to construct
         * @param @selectionParameters
         * @return
         */
        private void initializeFactoryMethod(Type returnTypeImpl, SelectionParameters selectionParameters) {
            List<SelectedMethod<SourceMethod>> matchingFactoryMethods =
                ObjectFactoryMethodResolver.getMatchingFactoryMethods(
                    method,
                    returnTypeImpl,
                    selectionParameters,
                    ctx
                );

            if ( matchingFactoryMethods.isEmpty() ) {
                if ( factoryMethod == null && returnTypeBuilder != null ) {
                    factoryMethod = ObjectFactoryMethodResolver.getBuilderFactoryMethod( method, returnTypeBuilder );
                    hasFactoryMethod = factoryMethod != null;
                }
            }
            else if ( matchingFactoryMethods.size() == 1 ) {
                factoryMethod = ObjectFactoryMethodResolver.getFactoryMethodReference(
                    method,
                    first( matchingFactoryMethods ),
                    ctx
                );
                hasFactoryMethod = true;
            }
            else {
                ctx.getMessager().printMessage(
                    method.getExecutable(),
                    Message.GENERAL_AMBIGIOUS_FACTORY_METHOD,
                    returnTypeImpl,
                    Strings.join( matchingFactoryMethods, ", " )
                );
                hasFactoryMethod = true;
            }
        }

        private ConstructorAccessor getConstructorAccessor(Type type) {

            if ( type.isRecord() ) {
                // If the type is a record then just get the record components and use then
                List<Element> recordComponents = type.getRecordComponents();
                List<ParameterBinding> parameterBindings = new ArrayList<>( recordComponents.size() );
                Map<String, Accessor> constructorAccessors = new LinkedHashMap<>();
                for ( Element recordComponent : recordComponents ) {
                    String parameterName = recordComponent.getSimpleName().toString();
                    Accessor accessor = createConstructorAccessor( recordComponent, parameterName );
                    constructorAccessors.put(
                        parameterName,
                        accessor
                    );

                    parameterBindings.add( ParameterBinding.fromTypeAndName(
                        ctx.getTypeFactory().getType( recordComponent.asType() ),
                        accessor.getSimpleName()
                    ) );
                }
                return new ConstructorAccessor( parameterBindings, constructorAccessors );
            }

            List<ExecutableElement> constructors = ElementFilter.constructorsIn( type.getTypeElement()
                .getEnclosedElements() );

            ExecutableElement defaultConstructor = null;
            List<ExecutableElement> accessibleConstructors = new ArrayList<>( constructors.size() );

            for ( ExecutableElement constructor : constructors ) {
                if ( constructor.getModifiers().contains( Modifier.PRIVATE ) ) {
                    continue;
                }

                if ( constructor.getParameters().isEmpty() ) {
                    defaultConstructor = constructor;
                }
                else {
                    accessibleConstructors.add( constructor );
                }
            }

            if ( defaultConstructor != null ) {
                return null;
            }

            if ( accessibleConstructors.isEmpty() ) {
                return null;
            }

            ExecutableElement constructor = null;
            if ( accessibleConstructors.size() > 1 ) {

                for ( ExecutableElement accessibleConstructor : accessibleConstructors ) {
                    for ( AnnotationMirror annotationMirror : accessibleConstructor.getAnnotationMirrors() ) {
                        if ( annotationMirror.getAnnotationType()
                            .asElement()
                            .getSimpleName()
                            .contentEquals( "Default" ) ) {
                            constructor = accessibleConstructor;
                            break;
                        }
                    }
                }

                if ( constructor == null ) {
                    ctx.getMessager().printMessage(
                        method.getExecutable(),
                        GENERAL_AMBIGIOUS_CONSTRUCTORS,
                        type,
                        Strings.join( constructors, ", " )
                    );
                    return null;
                }
            }
            else {
                constructor = accessibleConstructors.get( 0 );
            }

            List<Parameter> constructorParameters = ctx.getTypeFactory()
                .getParameters( (DeclaredType) type.getTypeMirror(), constructor );


            List<String> constructorProperties = null;
            for ( AnnotationMirror annotationMirror : constructor.getAnnotationMirrors() ) {
                if ( annotationMirror.getAnnotationType()
                    .asElement()
                    .getSimpleName()
                    .contentEquals( "ConstructorProperties" ) ) {
                    for ( Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : annotationMirror
                        .getElementValues()
                        .entrySet() ) {
                        if ( entry.getKey().getSimpleName().contentEquals( "value" ) ) {
                            constructorProperties = getArrayValues( entry.getValue() );
                            break;
                        }
                    }
                    break;
                }
            }

            if ( constructorProperties == null ) {
                Map<String, Accessor> constructorAccessors = new LinkedHashMap<>();
                List<ParameterBinding> parameterBindings = new ArrayList<>( constructorParameters.size() );
                for ( Parameter constructorParameter : constructorParameters ) {
                    String parameterName = constructorParameter.getName();
                    Element parameterElement = constructorParameter.getElement();
                    Accessor constructorAccessor = createConstructorAccessor( parameterElement, parameterName );
                    constructorAccessors.put(
                        parameterName,
                        constructorAccessor
                    );
                    parameterBindings.add( ParameterBinding.fromTypeAndName(
                        constructorParameter.getType(),
                        constructorAccessor.getSimpleName()
                    ) );
                }

                return new ConstructorAccessor( parameterBindings, constructorAccessors );
            }
            else if ( constructorProperties.size() != constructorParameters.size() ) {
                ctx.getMessager().printMessage(
                    method.getExecutable(),
                    GENERAL_CONSTRUCTOR_PROPERTIES_NOT_MATCHING_PARAMETERS,
                    type
                );
                return null;
            }
            else {
                Map<String, Accessor> constructorAccessors = new LinkedHashMap<>();
                List<ParameterBinding> parameterBindings = new ArrayList<>( constructorProperties.size() );
                for ( int i = 0; i < constructorProperties.size(); i++ ) {
                    String parameterName = constructorProperties.get( i );
                    Parameter constructorParameter = constructorParameters.get( i );
                    Element parameterElement = constructorParameter.getElement();
                    Accessor constructorAccessor = createConstructorAccessor( parameterElement, parameterName );
                    constructorAccessors.put(
                        parameterName,
                        constructorAccessor
                    );
                    parameterBindings.add( ParameterBinding.fromTypeAndName(
                        constructorParameter.getType(),
                        constructorAccessor.getSimpleName()
                    ) );
                }

                return new ConstructorAccessor( parameterBindings, constructorAccessors );
            }
        }

        private Accessor createConstructorAccessor(Element element, String parameterName) {
            String safeParameterName = Strings.getSafeVariableName(
                parameterName,
                existingVariableNames
            );
            existingVariableNames.add( safeParameterName );
            return new ParameterElementAccessor( element, safeParameterName );
        }

        private List<String> getArrayValues(AnnotationValue av) {

            if ( av.getValue() instanceof List ) {
                List<String> result = new ArrayList<>();
                for ( AnnotationValue v : getValueAsList( av ) ) {
                    Object value = v.getValue();
                    if ( value instanceof String ) {
                        result.add( (String) value );
                    }
                    else {
                        return null;
                    }
                }
                return result;
            }
            else {
                return null;
            }
        }

        @SuppressWarnings("unchecked")
        private List<AnnotationValue> getValueAsList(AnnotationValue av) {
            return (List<AnnotationValue>) av.getValue();
        }

        /**
         * Iterates over all defined mapping methods ({@code @Mapping(s)}), either directly given or inherited from the
         * inverse mapping method.
         * <p>
         * If a match is found between a defined source (constant, expression, ignore or source) the mapping is removed
         * from the remaining target properties.
         * <p>
         * It is furthermore checked whether the given mappings are correct. When an error occurs, the method continues
         * in search of more problems.
         *
         * @param resultTypeToMap the type in which the defined target properties are defined
         */
        private boolean handleDefinedMappings(Type resultTypeToMap) {

            boolean errorOccurred = false;
            Set<String> handledTargets = new HashSet<>();

            // first we have to handle nested target mappings
            if ( mappingReferences.hasNestedTargetReferences() ) {
                errorOccurred = handleDefinedNestedTargetMapping( handledTargets, resultTypeToMap );
            }

            for ( MappingReference mapping : mappingReferences.getMappingReferences() ) {
                if ( mapping.isValid() ) {
                    String target = mapping.getTargetReference().getShallowestPropertyName();
                    if ( !handledTargets.contains( target ) ) {
                        if ( handleDefinedMapping( mapping, resultTypeToMap, handledTargets ) ) {
                            errorOccurred = true;
                        }
                    }
                    if ( mapping.getSourceReference() != null ) {
                        String source = mapping.getSourceReference().getShallowestPropertyName();
                        if ( source != null ) {
                            unprocessedSourceProperties.remove( source );
                        }
                    }
                }
                else {
                    errorOccurred = true;
                }
            }

            // remove the remaining name based properties
            for ( String handledTarget : handledTargets ) {
                unprocessedTargetProperties.remove( handledTarget );
                unprocessedConstructorProperties.remove( handledTarget );
                unprocessedDefinedTargets.remove( handledTarget );
            }

            return errorOccurred;
        }

        private boolean handleDefinedNestedTargetMapping(Set<String> handledTargets, Type resultTypeToMap) {

            NestedTargetPropertyMappingHolder holder = new NestedTargetPropertyMappingHolder.Builder()
                .mappingContext( ctx )
                .method( method )
                .targetPropertiesWriteAccessors( unprocessedTargetProperties )
                .targetPropertyType( resultTypeToMap )
                .mappingReferences( mappingReferences )
                .existingVariableNames( existingVariableNames )
                .build();

            unprocessedSourceParameters.removeAll( holder.getProcessedSourceParameters() );
            propertyMappings.addAll( holder.getPropertyMappings() );
            handledTargets.addAll( holder.getHandledTargets() );
            // Store all the unprocessed defined targets.
            for ( Entry<String, Set<MappingReference>> entry : holder.getUnprocessedDefinedTarget()
                                                                            .entrySet() ) {
                if ( entry.getValue().isEmpty() ) {
                    continue;
                }
                unprocessedDefinedTargets.put( entry.getKey(), entry.getValue() );
            }
            return holder.hasErrorOccurred();
        }

        private boolean handleDefinedMapping(MappingReference mappingRef, Type resultTypeToMap,
            Set<String> handledTargets) {
            boolean errorOccured = false;

            PropertyMapping propertyMapping = null;

            TargetReference targetRef = mappingRef.getTargetReference();
            MappingOptions mapping = mappingRef.getMapping();

            // unknown properties given via dependsOn()?
            for ( String dependency : mapping.getDependsOn() ) {
                if ( !targetProperties.contains( dependency ) ) {
                    ctx.getMessager().printMessage(
                        method.getExecutable(),
                        mapping.getMirror(),
                        mapping.getDependsOnAnnotationValue(),
                        Message.BEANMAPPING_UNKNOWN_PROPERTY_IN_DEPENDS_ON,
                        dependency
                    );
                    errorOccured = true;
                }
            }

            String targetPropertyName = first( targetRef.getPropertyEntries() );

            // check if source / expression / constant are not somehow handled already
            if ( unprocessedDefinedTargets.containsKey( targetPropertyName ) ) {
                return false;
            }

            Accessor targetWriteAccessor = unprocessedTargetProperties.get( targetPropertyName );
            Accessor targetReadAccessor = resultTypeToMap.getPropertyReadAccessors().get( targetPropertyName );

            if ( targetWriteAccessor == null ) {
                if ( targetReadAccessor == null ) {
                    Set<String> readAccessors = resultTypeToMap.getPropertyReadAccessors().keySet();
                    String mostSimilarProperty = Strings.getMostSimilarWord( targetPropertyName, readAccessors );

                    Message msg;
                    Object[] args;

                    if ( targetRef.getPathProperties().isEmpty() ) {
                        msg = Message.BEANMAPPING_UNKNOWN_PROPERTY_IN_RESULTTYPE;
                        args = new Object[] {
                            targetPropertyName,
                            resultTypeToMap,
                            mostSimilarProperty
                        };
                    }
                    else {
                        List<String> pathProperties = new ArrayList<>( targetRef.getPathProperties() );
                        pathProperties.add( mostSimilarProperty );
                        msg = Message.BEANMAPPING_UNKNOWN_PROPERTY_IN_TYPE;
                        args = new Object[] {
                            targetPropertyName,
                            resultTypeToMap,
                            mapping.getTargetName(),
                            Strings.join( pathProperties, "." )
                        };
                    }

                    ctx.getMessager()
                        .printMessage(
                            mapping.getElement(),
                            mapping.getMirror(),
                            mapping.getTargetAnnotationValue(),
                            msg,
                            args
                        );
                    return true;
                }
                else if ( mapping.getInheritContext() != null && mapping.getInheritContext().isReversed() ) {
                    // read only reversed mappings are implicitly ignored
                    return false;
                }
                else if ( !mapping.isIgnored() ) {
                    // report an error for read only mappings
                    Message msg;
                    Object[] args;

                    if ( Objects.equals( targetPropertyName, mapping.getTargetName() ) ) {
                        msg = Message.BEANMAPPING_PROPERTY_HAS_NO_WRITE_ACCESSOR_IN_RESULTTYPE;
                        args = new Object[] {
                            mapping.getTargetName(),
                            resultTypeToMap
                        };
                    }
                    else {
                        msg = Message.BEANMAPPING_PROPERTY_HAS_NO_WRITE_ACCESSOR_IN_TYPE;
                        args = new Object[] {
                            targetPropertyName,
                            resultTypeToMap,
                            mapping.getTargetName()
                        };
                    }
                    ctx.getMessager()
                        .printMessage(
                            mapping.getElement(),
                            mapping.getMirror(),
                            mapping.getTargetAnnotationValue(),
                            msg,
                            args
                        );
                    return true;
                }
            }

            // check the mapping options
            // its an ignored property mapping
            if ( mapping.isIgnored() ) {
                if ( targetWriteAccessor != null && targetWriteAccessor.getAccessorType() == AccessorType.PARAMETER ) {
                    // Even though the property is ignored this is a constructor parameter.
                    // Therefore we have to initialize it
                    Type accessedType = ctx.getTypeFactory()
                        .getType( targetWriteAccessor.getAccessedType() );

                    propertyMapping = new JavaExpressionMappingBuilder()
                        .mappingContext( ctx )
                        .sourceMethod( method )
                        .javaExpression( accessedType.getNull() )
                        .existingVariableNames( existingVariableNames )
                        .target( targetPropertyName, targetReadAccessor, targetWriteAccessor )
                        .dependsOn( mapping.getDependsOn() )
                        .mirror( mapping.getMirror() )
                        .build();
                }
                handledTargets.add( targetPropertyName );
            }

            // its a constant
            // if we have an unprocessed target that means that it most probably is nested and we should
            // not generated any mapping for it now. Eventually it will be done though
            else if ( mapping.getConstant() != null ) {

                propertyMapping = new ConstantMappingBuilder()
                    .mappingContext( ctx )
                    .sourceMethod( method )
                    .constantExpression( mapping.getConstant() )
                    .target( targetPropertyName, targetReadAccessor, targetWriteAccessor )
                    .formattingParameters( mapping.getFormattingParameters() )
                    .selectionParameters( mapping.getSelectionParameters() )
                    .options( mapping )
                    .existingVariableNames( existingVariableNames )
                    .dependsOn( mapping.getDependsOn() )
                    .mirror( mapping.getMirror() )
                    .build();
                handledTargets.add( targetPropertyName );
            }

            // its an expression
            // if we have an unprocessed target that means that it most probably is nested and we should
            // not generated any mapping for it now. Eventually it will be done though
            else if ( mapping.getJavaExpression() != null ) {

                propertyMapping = new JavaExpressionMappingBuilder()
                    .mappingContext( ctx )
                    .sourceMethod( method )
                    .javaExpression( mapping.getJavaExpression() )
                    .existingVariableNames( existingVariableNames )
                    .target( targetPropertyName, targetReadAccessor, targetWriteAccessor )
                    .dependsOn( mapping.getDependsOn() )
                    .mirror( mapping.getMirror() )
                    .build();
                handledTargets.add( targetPropertyName );
            }
            // its a plain-old property mapping
            else  {

                // determine source parameter
                SourceReference sourceRef = mappingRef.getSourceReference();
                if ( sourceRef == null && method.getSourceParameters().size() == 1 ) {
                    sourceRef = getSourceRef( method.getSourceParameters().get( 0 ), targetPropertyName );
                }

                if ( sourceRef != null ) {
                    // sourceRef == null is not considered an error here
                    if ( sourceRef.isValid() ) {

                        // targetProperty == null can occur: we arrived here because we want as many errors
                        // as possible before we stop analysing
                        propertyMapping = new PropertyMappingBuilder()
                            .mappingContext( ctx )
                            .sourceMethod( method )
                            .target( targetPropertyName, targetReadAccessor, targetWriteAccessor )
                            .sourcePropertyName( mapping.getSourceName() )
                            .sourceReference( sourceRef )
                            .selectionParameters( mapping.getSelectionParameters() )
                            .formattingParameters( mapping.getFormattingParameters() )
                            .existingVariableNames( existingVariableNames )
                            .dependsOn( mapping.getDependsOn() )
                            .defaultValue( mapping.getDefaultValue() )
                            .defaultJavaExpression( mapping.getDefaultJavaExpression() )
                            .mirror( mapping.getMirror() )
                            .options( mapping )
                            .condition( mapping.getCondition() )
                            .build();
                        handledTargets.add( targetPropertyName );
                        unprocessedSourceParameters.remove( sourceRef.getParameter() );
                    }
                    else {
                        errorOccured = true;
                    }
                }
            }
            // remaining are the mappings without a 'source' so, 'only' a date format or qualifiers
            if ( propertyMapping != null ) {
                propertyMappings.add( propertyMapping );
            }

            return errorOccured;
        }

        /**
         * When target this mapping present, iterates over unprocessed targets.
         * <p>
         * When a target property matches its name with the (nested) source property, it is added to the list if and
         * only if it is an unprocessed target property.
         *
         * duplicates will be handled by {@link #applyPropertyNameBasedMapping(List)}
         */
        private void applyTargetThisMapping() {
            Set<String> handledTargetProperties = new HashSet<>();
            for ( MappingReference targetThis : mappingReferences.getTargetThisReferences() ) {

                // handle all prior unprocessed target properties, but let duplicates fall through
                List<SourceReference> sourceRefs = targetThis
                    .getSourceReference()
                    .push( ctx.getTypeFactory(), ctx.getMessager(), method )
                    .stream()
                    .filter( sr -> unprocessedTargetProperties.containsKey( sr.getDeepestPropertyName() )
                        || handledTargetProperties.contains( sr.getDeepestPropertyName() ) )
                    .collect( Collectors.toList() );

                // apply name based mapping
                applyPropertyNameBasedMapping( sourceRefs );

                // add handled target properties
                handledTargetProperties.addAll( sourceRefs.stream()
                    .map( SourceReference::getDeepestPropertyName )
                    .collect(
                        Collectors.toList() ) );
            }
        }

        /**
         * Iterates over all target properties and all source parameters.
         * <p>
         * When a property name match occurs, the remainder will be checked for duplicates. Matches will be removed from
         * the set of remaining target properties.
         */
        private void applyPropertyNameBasedMapping() {
            List<SourceReference> sourceReferences = new ArrayList<>();
            for ( String targetPropertyName : unprocessedTargetProperties.keySet() ) {
                for ( Parameter sourceParameter : method.getSourceParameters() ) {
                    SourceReference sourceRef = getSourceRef( sourceParameter, targetPropertyName );
                    if ( sourceRef != null ) {
                        sourceReferences.add( sourceRef );
                    }
                }
            }
            applyPropertyNameBasedMapping( sourceReferences );
        }

        /**
         * Iterates over all target properties and all source parameters.
         * <p>
         * When a property name match occurs, the remainder will be checked for duplicates. Matches will be removed from
         * the set of remaining target properties.
         */
        private void applyPropertyNameBasedMapping(List<SourceReference> sourceReferences) {

            for ( SourceReference sourceRef : sourceReferences ) {

                String targetPropertyName = sourceRef.getDeepestPropertyName();
                Accessor targetPropertyWriteAccessor = unprocessedTargetProperties.remove( targetPropertyName );
                unprocessedConstructorProperties.remove( targetPropertyName );
                if ( targetPropertyWriteAccessor == null ) {
                    // TODO improve error message
                    ctx.getMessager()
                       .printMessage( method.getExecutable(),
                           Message.BEANMAPPING_SEVERAL_POSSIBLE_SOURCES,
                           targetPropertyName
                       );
                    continue;
                }

                Accessor targetPropertyReadAccessor =
                    method.getResultType().getPropertyReadAccessors().get( targetPropertyName );
                MappingReferences mappingRefs = extractMappingReferences( targetPropertyName, false );
                PropertyMapping propertyMapping = new PropertyMappingBuilder().mappingContext( ctx )
                    .sourceMethod( method )
                    .target( targetPropertyName, targetPropertyReadAccessor, targetPropertyWriteAccessor )
                    .sourceReference( sourceRef )
                    .existingVariableNames( existingVariableNames )
                    .forgeMethodWithMappingReferences( mappingRefs )
                    .options( method.getOptions().getBeanMapping() )
                    .build();

                unprocessedSourceParameters.remove( sourceRef.getParameter() );

                if ( propertyMapping != null ) {
                    propertyMappings.add( propertyMapping );
                }
                unprocessedDefinedTargets.remove( targetPropertyName );
                unprocessedSourceProperties.remove( targetPropertyName );
            }
        }

        private void applyParameterNameBasedMapping() {

            Iterator<Entry<String, Accessor>> targetPropertyEntriesIterator =
                unprocessedTargetProperties.entrySet().iterator();

            while ( targetPropertyEntriesIterator.hasNext() ) {

                Entry<String, Accessor> targetProperty = targetPropertyEntriesIterator.next();

                Iterator<Parameter> sourceParameters = unprocessedSourceParameters.iterator();

                while ( sourceParameters.hasNext() ) {

                    Parameter sourceParameter = sourceParameters.next();
                    if ( sourceParameter.getName().equals( targetProperty.getKey() ) ) {

                        SourceReference sourceRef = new SourceReference.BuilderFromProperty()
                            .sourceParameter( sourceParameter )
                            .name( targetProperty.getKey() )
                            .build();

                        Accessor targetPropertyReadAccessor =
                            method.getResultType().getPropertyReadAccessors().get( targetProperty.getKey() );
                        MappingReferences mappingRefs = extractMappingReferences( targetProperty.getKey(), false );
                        PropertyMapping propertyMapping = new PropertyMappingBuilder()
                            .mappingContext( ctx )
                            .sourceMethod( method )
                            .target( targetProperty.getKey(), targetPropertyReadAccessor, targetProperty.getValue() )
                            .sourceReference( sourceRef )
                            .existingVariableNames( existingVariableNames )
                            .forgeMethodWithMappingReferences( mappingRefs )
                            .options( method.getOptions().getBeanMapping() )
                            .build();

                        propertyMappings.add( propertyMapping );
                        targetPropertyEntriesIterator.remove();
                        sourceParameters.remove();
                        unprocessedDefinedTargets.remove( targetProperty.getKey() );
                        unprocessedSourceProperties.remove( targetProperty.getKey() );
                    }
                }
            }
        }

        private SourceReference getSourceRef(Parameter sourceParameter, String targetPropertyName) {

            SourceReference sourceRef = null;

            if ( sourceParameter.getType().isPrimitive() || sourceParameter.getType().isArrayType() ) {
                return sourceRef;
            }

            Accessor sourceReadAccessor =
                sourceParameter.getType().getPropertyReadAccessors().get( targetPropertyName );

            Accessor sourcePresenceChecker =
                sourceParameter.getType().getPropertyPresenceCheckers().get( targetPropertyName );

            if ( sourceReadAccessor != null ) {
                DeclaredType declaredSourceType = (DeclaredType) sourceParameter.getType().getTypeMirror();
                Type returnType = ctx.getTypeFactory().getReturnType( declaredSourceType, sourceReadAccessor );
                sourceRef = new SourceReference.BuilderFromProperty().sourceParameter( sourceParameter )
                                                                     .type( returnType )
                                                                     .readAccessor( sourceReadAccessor )
                                                                     .presenceChecker( sourcePresenceChecker )
                                                                     .name( targetPropertyName )
                                                                     .build();
            }
            return sourceRef;
        }

        private MappingReferences extractMappingReferences(String targetProperty, boolean restrictToDefinedMappings) {
            if ( unprocessedDefinedTargets.containsKey( targetProperty ) ) {
                Set<MappingReference> mappings = unprocessedDefinedTargets.get( targetProperty );
                return new MappingReferences( mappings, restrictToDefinedMappings );
            }
            return null;
        }

        private ReportingPolicyGem getUnmappedTargetPolicy() {
            if ( mappingReferences.isForForgedMethods() ) {
                return ReportingPolicyGem.IGNORE;
            }
            return method.getOptions().getMapper().unmappedTargetPolicy();
        }

        private void reportErrorForUnmappedTargetPropertiesIfRequired() {

            // fetch settings from element to implement
            ReportingPolicyGem unmappedTargetPolicy = getUnmappedTargetPolicy();

            if ( method instanceof ForgedMethod && targetProperties.isEmpty() ) {
                //TODO until we solve 1140 we report this error when the target properties are empty
                ForgedMethod forgedMethod = (ForgedMethod) method;
                if ( forgedMethod.getHistory() == null ) {
                    Type sourceType = this.method.getParameters().get( 0 ).getType();
                    Type targetType = this.method.getReturnType();
                    ctx.getMessager().printMessage(
                        this.method.getExecutable(),
                        Message.PROPERTYMAPPING_FORGED_MAPPING_NOT_FOUND,
                        sourceType,
                        targetType,
                        targetType,
                        sourceType
                    );
                }
                else {
                    ForgedMethodHistory history = forgedMethod.getHistory();
                    ctx.getMessager().printMessage(
                        this.method.getExecutable(),
                        Message.PROPERTYMAPPING_FORGED_MAPPING_WITH_HISTORY_NOT_FOUND,
                        history.createSourcePropertyErrorMessage(),
                        history.getTargetType(),
                        history.createTargetPropertyName(),
                        history.getTargetType(),
                        history.getSourceType()
                    );
                }
            }
            else if ( !unprocessedTargetProperties.isEmpty() && unmappedTargetPolicy.requiresReport() ) {

                if ( !( method instanceof ForgedMethod ) ) {
                    Message msg = unmappedTargetPolicy.getDiagnosticKind() == Diagnostic.Kind.ERROR ?
                        Message.BEANMAPPING_UNMAPPED_TARGETS_ERROR : Message.BEANMAPPING_UNMAPPED_TARGETS_WARNING;
                    Object[] args = new Object[] {
                        MessageFormat.format(
                            "{0,choice,1#property|1<properties}: \"{1}\"",
                            unprocessedTargetProperties.size(),
                            Strings.join( unprocessedTargetProperties.keySet(), ", " )
                        )
                    };

                    ctx.getMessager().printMessage(
                        method.getExecutable(),
                        msg,
                        args
                    );
                }
                else if ( !ctx.isErroneous() ) {
                    Message msg = unmappedTargetPolicy.getDiagnosticKind() == Diagnostic.Kind.ERROR ?
                        Message.BEANMAPPING_UNMAPPED_FORGED_TARGETS_ERROR :
                        Message.BEANMAPPING_UNMAPPED_FORGED_TARGETS_WARNING;
                    String sourceErrorMessage = method.getParameters().get( 0 ).getType().toString();
                    String targetErrorMessage = method.getReturnType().toString();
                    if ( ( (ForgedMethod) method ).getHistory() != null ) {
                        ForgedMethodHistory history = ( (ForgedMethod) method ).getHistory();
                        sourceErrorMessage = history.createSourcePropertyErrorMessage();
                        targetErrorMessage = MessageFormat.format(
                            "\"{0} {1}\"",
                            history.getTargetType(),
                            history.createTargetPropertyName()
                        );
                    }
                    Object[] args = new Object[] {
                        MessageFormat.format(
                            "{0,choice,1#property|1<properties}: \"{1}\"",
                            unprocessedTargetProperties.size(),
                            Strings.join( unprocessedTargetProperties.keySet(), ", " )
                        ),
                        sourceErrorMessage,
                        targetErrorMessage
                    };
                    ctx.getMessager().printMessage(
                        method.getExecutable(),
                        msg,
                        args
                    );
                }

            }
        }

        private ReportingPolicyGem getUnmappedSourcePolicy() {
            if ( mappingReferences.isForForgedMethods() ) {
                return ReportingPolicyGem.IGNORE;
            }
            return method.getOptions().getMapper().unmappedSourcePolicy();
        }

        private void reportErrorForUnmappedSourcePropertiesIfRequired() {
            ReportingPolicyGem unmappedSourcePolicy = getUnmappedSourcePolicy();

            if ( !unprocessedSourceProperties.isEmpty() && unmappedSourcePolicy.requiresReport() ) {

                Message msg = unmappedSourcePolicy.getDiagnosticKind() == Diagnostic.Kind.ERROR ?
                    Message.BEANMAPPING_UNMAPPED_SOURCES_ERROR : Message.BEANMAPPING_UNMAPPED_SOURCES_WARNING;
                Object[] args = new Object[] {
                    MessageFormat.format(
                        "{0,choice,1#property|1<properties}: \"{1}\"",
                        unprocessedSourceProperties.size(),
                        Strings.join( unprocessedSourceProperties.keySet(), ", " )
                    )
                };

                ctx.getMessager().printMessage(
                    method.getExecutable(),
                    msg,
                    args
                );
            }
        }
    }

    private static class ConstructorAccessor {
        private final List<ParameterBinding> parameterBindings;
        private final Map<String, Accessor> constructorAccessors;

        private ConstructorAccessor(
            List<ParameterBinding> parameterBindings,
            Map<String, Accessor> constructorAccessors) {
            this.parameterBindings = parameterBindings;
            this.constructorAccessors = constructorAccessors;
        }
    }

    private BeanMappingMethod(Method method,
                              Collection<String> existingVariableNames,
                              List<PropertyMapping> propertyMappings,
                              MethodReference factoryMethod,
                              boolean mapNullToDefault,
                              Type returnTypeToConstruct,
                              BuilderType returnTypeBuilder,
                              List<LifecycleCallbackMethodReference> beforeMappingReferences,
                              List<LifecycleCallbackMethodReference> afterMappingReferences,
                              MethodReference finalizerMethod) {
        super(
            method,
            existingVariableNames,
            factoryMethod,
            mapNullToDefault,
            beforeMappingReferences,
            afterMappingReferences
        );

        this.propertyMappings = propertyMappings;
        this.returnTypeBuilder = returnTypeBuilder;
        this.finalizerMethod = finalizerMethod;

        // intialize constant mappings as all mappings, but take out the ones that can be contributed to a
        // parameter mapping.
        this.mappingsByParameter = new HashMap<>();
        this.constantMappings = new ArrayList<>( propertyMappings.size() );
        this.constructorMappingsByParameter = new LinkedHashMap<>();
        this.constructorConstantMappings = new ArrayList<>();
        Set<String> sourceParameterNames = getSourceParameters().stream()
            .map( Parameter::getName )
            .collect( Collectors.toSet() );
        for ( PropertyMapping mapping : propertyMappings ) {
            if ( mapping.isConstructorMapping() ) {
                if ( sourceParameterNames.contains( mapping.getSourceBeanName() ) ) {
                    constructorMappingsByParameter.computeIfAbsent(
                        mapping.getSourceBeanName(),
                        key -> new ArrayList<>()
                    ).add( mapping );
                }
                else {
                    constructorConstantMappings.add( mapping );
                }
            }
            else if ( sourceParameterNames.contains( mapping.getSourceBeanName() ) ) {
                mappingsByParameter.computeIfAbsent( mapping.getSourceBeanName(), key -> new ArrayList<>() )
                    .add( mapping );
            }
            else {
                constantMappings.add( mapping );
            }
        }
        this.returnTypeToConstruct = returnTypeToConstruct;
    }

    public List<PropertyMapping> getConstantMappings() {
        return constantMappings;
    }

    public List<PropertyMapping> getConstructorConstantMappings() {
        return constructorConstantMappings;
    }

    public List<PropertyMapping> propertyMappingsByParameter(Parameter parameter) {
        // issues: #909 and #1244. FreeMarker has problem getting values from a map when the search key is size or value
        return mappingsByParameter.getOrDefault( parameter.getName(), Collections.emptyList() );
    }

    public List<PropertyMapping> constructorPropertyMappingsByParameter(Parameter parameter) {
        // issues: #909 and #1244. FreeMarker has problem getting values from a map when the search key is size or value
        return constructorMappingsByParameter.getOrDefault( parameter.getName(), Collections.emptyList() );
    }

    public Type getReturnTypeToConstruct() {
        return returnTypeToConstruct;
    }

    public boolean hasConstructorMappings() {
        return !constructorMappingsByParameter.isEmpty() || !constructorConstantMappings.isEmpty();
    }

    public MethodReference getFinalizerMethod() {
        return finalizerMethod;
    }

    @Override
    public Set<Type> getImportTypes() {
        Set<Type> types = super.getImportTypes();

        for ( PropertyMapping propertyMapping : propertyMappings ) {
            types.addAll( propertyMapping.getImportTypes() );
        }

        if ( returnTypeToConstruct != null  ) {
            types.addAll( returnTypeToConstruct.getImportTypes() );
        }
        if ( returnTypeBuilder != null ) {
            types.add( returnTypeBuilder.getOwningType() );
        }

        return types;
    }

    public List<Parameter> getSourceParametersExcludingPrimitives() {
        return getSourceParameters().stream()
                            .filter( parameter -> !parameter.getType().isPrimitive() )
                            .collect( Collectors.toList() );
    }

    public List<Parameter> getSourcePrimitiveParameters() {
        return getSourceParameters().stream()
                            .filter( parameter -> parameter.getType().isPrimitive() )
                            .collect( Collectors.toList() );
    }

    @Override
    public int hashCode() {
        //Needed for Checkstyle, otherwise it fails due to EqualsHashCode rule
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {

        if ( this == obj ) {
            return true;
        }
        if ( obj == null || getClass() != obj.getClass() ) {
            return false;
        }

        BeanMappingMethod that = (BeanMappingMethod) obj;

        if ( !super.equals( obj ) ) {
            return false;
        }
        return Objects.equals( propertyMappings, that.propertyMappings );
    }

}
