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
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
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
import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.BuilderType;
import org.mapstruct.ap.internal.model.common.FormattingParameters;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.ParameterBinding;
import org.mapstruct.ap.internal.model.common.PresenceCheck;
import org.mapstruct.ap.internal.model.common.SourceRHS;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.model.dependency.GraphAnalyzer;
import org.mapstruct.ap.internal.model.dependency.GraphAnalyzer.GraphAnalyzerBuilder;
import org.mapstruct.ap.internal.model.presence.NullPresenceCheck;
import org.mapstruct.ap.internal.model.source.BeanMappingOptions;
import org.mapstruct.ap.internal.model.source.MappingOptions;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.model.source.SelectionParameters;
import org.mapstruct.ap.internal.model.source.SourceMethod;
import org.mapstruct.ap.internal.model.source.SubclassMappingOptions;
import org.mapstruct.ap.internal.model.source.selector.SelectedMethod;
import org.mapstruct.ap.internal.model.source.selector.SelectionCriteria;
import org.mapstruct.ap.internal.util.Message;
import org.mapstruct.ap.internal.util.Strings;
import org.mapstruct.ap.internal.util.accessor.Accessor;
import org.mapstruct.ap.internal.util.accessor.AccessorType;
import org.mapstruct.ap.internal.util.accessor.ElementAccessor;
import org.mapstruct.ap.internal.util.accessor.PresenceCheckAccessor;
import org.mapstruct.ap.internal.util.accessor.ReadAccessor;

import static org.mapstruct.ap.internal.model.beanmapping.MappingReferences.forSourceMethod;
import static org.mapstruct.ap.internal.util.Collections.first;
import static org.mapstruct.ap.internal.util.Message.BEANMAPPING_ABSTRACT;
import static org.mapstruct.ap.internal.util.Message.BEANMAPPING_NOT_ASSIGNABLE;
import static org.mapstruct.ap.internal.util.Message.GENERAL_ABSTRACT_RETURN_TYPE;
import static org.mapstruct.ap.internal.util.Message.GENERAL_AMBIGUOUS_CONSTRUCTORS;
import static org.mapstruct.ap.internal.util.Message.GENERAL_CONSTRUCTOR_PROPERTIES_NOT_MATCHING_PARAMETERS;
import static org.mapstruct.ap.internal.util.Message.PROPERTYMAPPING_CANNOT_DETERMINE_SOURCE_PARAMETER_FROM_TARGET;
import static org.mapstruct.ap.internal.util.Message.PROPERTYMAPPING_CANNOT_DETERMINE_SOURCE_PROPERTY_FROM_TARGET;

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
    private final Map<String, PresenceCheck> presenceChecksByParameter;
    private final List<PropertyMapping> constantMappings;
    private final List<PropertyMapping> constructorConstantMappings;
    private final List<SubclassMapping> subclassMappings;
    private final Type returnTypeToConstruct;
    private final BuilderType returnTypeBuilder;
    private final MethodReference finalizerMethod;
    private final String finalizedResultName;
    private final List<LifecycleCallbackMethodReference> beforeMappingReferencesWithFinalizedReturnType;
    private final List<LifecycleCallbackMethodReference> afterMappingReferencesWithFinalizedReturnType;
    private final Type subclassExhaustiveException;

    private final MappingReferences mappingReferences;

    public static class Builder extends AbstractMappingMethodBuilder<Builder, BeanMappingMethod> {

        private Type userDefinedReturnType;

        /* returnType to construct can have a builder */
        private BuilderType returnTypeBuilder;
        private Map<String, Accessor> unprocessedConstructorProperties;
        private Map<String, Accessor> unprocessedTargetProperties;
        private Map<String, Accessor> unprocessedSourceProperties;
        private Set<String> missingIgnoredSourceProperties;
        private Set<String> targetProperties;
        private final List<PropertyMapping> propertyMappings = new ArrayList<>();
        private final Set<Parameter> unprocessedSourceParameters = new HashSet<>();
        private final Set<String> existingVariableNames = new HashSet<>();
        private final Map<String, Set<MappingReference>> unprocessedDefinedTargets = new LinkedHashMap<>();

        private MappingReferences mappingReferences;
        private List<MappingReference> targetThisReferences;
        private MethodReference factoryMethod;
        private boolean hasFactoryMethod;

        public Builder() {
            super( Builder.class );
        }

        @Override
        protected boolean shouldUsePropertyNamesInHistory() {
            return true;
        }

        public Builder userDefinedReturnType(Type userDefinedReturnType) {
            this.userDefinedReturnType = userDefinedReturnType;
            return this;
        }

        public Builder returnTypeBuilder( BuilderType returnTypeBuilder ) {
            this.returnTypeBuilder = returnTypeBuilder;
            return this;
        }

        public Builder sourceMethod(SourceMethod sourceMethod) {
            method( sourceMethod );
            return this;
        }

        public Builder forgedMethod(ForgedMethod forgedMethod) {
            method( forgedMethod );
            mappingReferences = forgedMethod.getMappingReferences();
            Parameter sourceParameter = first( Parameter.getSourceParameters( forgedMethod.getParameters() ) );
            for ( MappingReference mappingReference : mappingReferences.getMappingReferences() ) {
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

        @Override
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
                Type returnTypeImpl = null;
                if ( isBuilderRequired() ) {
                    // the userDefinedReturn type can also require a builder. That buildertype is already set
                    returnTypeImpl = returnTypeBuilder.getBuilder();
                    initializeFactoryMethod( returnTypeImpl, selectionParameters );
                    if ( factoryMethod != null
                        || allowsAbstractReturnTypeAndIsEitherAbstractOrCanBeConstructed( returnTypeImpl )
                        || doesNotAllowAbstractReturnTypeAndCanBeConstructed( returnTypeImpl ) ) {
                        returnTypeToConstruct = returnTypeImpl;
                    }
                    else {
                        cannotConstructReturnType = true;
                    }
                }
                else if ( userDefinedReturnType != null ) {
                    returnTypeImpl = userDefinedReturnType;
                    initializeFactoryMethod( returnTypeImpl, selectionParameters );
                    if ( factoryMethod != null || canResultTypeFromBeanMappingBeConstructed( returnTypeImpl ) ) {
                        returnTypeToConstruct = returnTypeImpl;
                    }
                    else {
                        cannotConstructReturnType = true;
                    }
                }
                else if ( !method.isUpdateMethod() ) {
                    returnTypeImpl = method.getReturnType();
                    initializeFactoryMethod( returnTypeImpl, selectionParameters );
                    if ( factoryMethod != null
                        || allowsAbstractReturnTypeAndIsEitherAbstractOrCanBeConstructed( returnTypeImpl )
                        || doesNotAllowAbstractReturnTypeAndCanBeConstructed( returnTypeImpl ) ) {
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

            boolean constructorAccessorHadError = false;
            if ( !method.isUpdateMethod() && !hasFactoryMethod ) {
                ConstructorAccessor constructorAccessor = getConstructorAccessor( resultTypeToMap );
                if ( constructorAccessor != null && !constructorAccessor.hasError ) {

                    this.unprocessedConstructorProperties = constructorAccessor.constructorAccessors;

                    factoryMethod = MethodReference.forConstructorInvocation(
                        resultTypeToMap,
                        constructorAccessor.parameterBindings
                    );

                }
                else {
                    this.unprocessedConstructorProperties = new LinkedHashMap<>();
                }
                constructorAccessorHadError = constructorAccessor != null && constructorAccessor.hasError;

                this.targetProperties.addAll( this.unprocessedConstructorProperties.keySet() );

                this.unprocessedTargetProperties.putAll( this.unprocessedConstructorProperties );
            }
            else {
                unprocessedConstructorProperties = new LinkedHashMap<>();
            }

            this.unprocessedSourceProperties = new LinkedHashMap<>();
            for ( Parameter sourceParameter : method.getSourceParameters() ) {
                unprocessedSourceParameters.add( sourceParameter );

                if ( sourceParameter.getType().isPrimitive() || sourceParameter.getType().isArrayType() ||
                    sourceParameter.getType().isMapType() ) {
                    continue;
                }

                Map<String, ReadAccessor> readAccessors = sourceParameter.getType().getPropertyReadAccessors();

                unprocessedSourceProperties.putAll( readAccessors );
            }

            // get bean mapping (when specified as annotation )
            this.missingIgnoredSourceProperties = new HashSet<>();
            if ( beanMapping != null ) {
                for ( String ignoreUnmapped : beanMapping.getIgnoreUnmappedSourceProperties() ) {
                    if ( unprocessedSourceProperties.remove( ignoreUnmapped ) == null ) {
                        missingIgnoredSourceProperties.add( ignoreUnmapped );
                    }
                }
            }

            initializeMappingReferencesIfNeeded( resultTypeToMap );

            boolean shouldHandledDefinedMappings = shouldHandledDefinedMappings( resultTypeToMap );


            if ( shouldHandledDefinedMappings ) {
                // map properties with mapping
                boolean mappingErrorOccurred = handleDefinedMappings( resultTypeToMap );
                if ( mappingErrorOccurred ) {
                    return null;
                }
            }

            // If defined mappings should not be handled then we should not apply implicit mappings
            boolean applyImplicitMappings =
                shouldHandledDefinedMappings && !mappingReferences.isRestrictToDefinedMappings();
            if ( applyImplicitMappings ) {
                applyImplicitMappings = beanMapping == null || !beanMapping.isIgnoredByDefault();
            }
            if ( applyImplicitMappings ) {

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
            if ( shouldHandledDefinedMappings ) {
                reportErrorForUnmappedTargetPropertiesIfRequired( resultTypeToMap, constructorAccessorHadError );
                reportErrorForUnmappedSourcePropertiesIfRequired();
            }
            reportErrorForMissingIgnoredSourceProperties();
            reportErrorForUnusedSourceParameters();

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

            if ( method instanceof ForgedMethod ) {
                ForgedMethod forgedMethod = (ForgedMethod) method;
                if ( factoryMethod != null ) {
                    forgedMethod.addThrownTypes( factoryMethod.getThrownTypes() );
                }
                for ( LifecycleCallbackMethodReference beforeMappingMethod : beforeMappingMethods ) {
                    forgedMethod.addThrownTypes( beforeMappingMethod.getThrownTypes() );
                }

                for ( LifecycleCallbackMethodReference afterMappingMethod : afterMappingMethods ) {
                    forgedMethod.addThrownTypes( afterMappingMethod.getThrownTypes() );
                }


                for ( PropertyMapping propertyMapping : propertyMappings ) {
                    if ( propertyMapping.getAssignment() != null ) {
                        forgedMethod.addThrownTypes( propertyMapping.getAssignment().getThrownTypes() );
                    }
                }

            }

            TypeMirror subclassExhaustiveException = method.getOptions()
                .getBeanMapping()
                .getSubclassExhaustiveException();
            Type subclassExhaustiveExceptionType = ctx.getTypeFactory().getType( subclassExhaustiveException );

            List<SubclassMapping> subclasses = new ArrayList<>();
            for ( SubclassMappingOptions subclassMappingOptions : method.getOptions().getSubclassMappings() ) {
                subclasses.add( createSubclassMapping( subclassMappingOptions ) );
            }

            MethodReference finalizeMethod = null;

            List<LifecycleCallbackMethodReference> beforeMappingReferencesWithFinalizedReturnType = new ArrayList<>();
            List<LifecycleCallbackMethodReference> afterMappingReferencesWithFinalizedReturnType = new ArrayList<>();
            if ( shouldCallFinalizerMethod( returnTypeToConstruct ) ) {
                finalizeMethod = getFinalizerMethod();

                Type actualReturnType = method.getReturnType();

                beforeMappingReferencesWithFinalizedReturnType.addAll( filterMappingTarget(
                    LifecycleMethodResolver.beforeMappingMethods(
                        method,
                        actualReturnType,
                        selectionParameters,
                        ctx,
                        existingVariableNames
                    ),
                    false
                ) );

                afterMappingReferencesWithFinalizedReturnType.addAll( LifecycleMethodResolver.afterMappingMethods(
                    method,
                    actualReturnType,
                    selectionParameters,
                    ctx,
                    existingVariableNames
                ) );

                keepMappingReferencesUsingTarget( beforeMappingReferencesWithFinalizedReturnType, actualReturnType );
                keepMappingReferencesUsingTarget( afterMappingReferencesWithFinalizedReturnType, actualReturnType );
            }

            Map<String, PresenceCheck> presenceChecksByParameter = new LinkedHashMap<>();
            for ( Parameter sourceParameter : method.getSourceParameters() ) {
                PresenceCheck parameterPresenceCheck = PresenceCheckMethodResolver.getPresenceCheckForSourceParameter(
                    method,
                    selectionParameters,
                    sourceParameter,
                    ctx
                );
                if ( parameterPresenceCheck != null ) {
                    presenceChecksByParameter.put( sourceParameter.getName(), parameterPresenceCheck );
                }
                else if ( !sourceParameter.getType().isPrimitive() ) {
                    presenceChecksByParameter.put(
                        sourceParameter.getName(),
                        new NullPresenceCheck( sourceParameter.getName() )
                    );
                }
            }


            return new BeanMappingMethod(
                method,
                getMethodAnnotations(),
                existingVariableNames,
                propertyMappings,
                factoryMethod,
                mapNullToDefault,
                returnTypeToConstruct,
                returnTypeBuilder,
                beforeMappingMethods,
                afterMappingMethods,
                beforeMappingReferencesWithFinalizedReturnType,
                afterMappingReferencesWithFinalizedReturnType,
                finalizeMethod,
                mappingReferences,
                subclasses,
                presenceChecksByParameter,
                subclassExhaustiveExceptionType
            );
        }

        private void keepMappingReferencesUsingTarget(List<LifecycleCallbackMethodReference> references, Type type) {
            references.removeIf( reference -> {
                List<ParameterBinding> bindings = reference.getParameterBindings();
                if ( bindings.isEmpty() ) {
                    return true;
                }
                for ( ParameterBinding binding : bindings ) {
                    if ( binding.isMappingTarget() ) {
                        if ( type.isAssignableTo( binding.getType() ) ) {
                            // If the mapping target matches the type then we need to keep this
                            return false;
                        }
                    }
                    else if ( binding.isTargetType() ) {
                        Type targetType = binding.getType();
                        List<Type> targetTypeTypeParameters = targetType.getTypeParameters();
                        if ( targetTypeTypeParameters.size() == 1 ) {
                            if ( type.isAssignableTo( targetTypeTypeParameters.get( 0 ) ) ) {
                                return false;
                            }
                        }
                    }
                }

                return true;
            } );
        }

        private boolean doesNotAllowAbstractReturnTypeAndCanBeConstructed(Type returnTypeImpl) {
            return !isAbstractReturnTypeAllowed()
                && canReturnTypeBeConstructed( returnTypeImpl );
        }

        private boolean allowsAbstractReturnTypeAndIsEitherAbstractOrCanBeConstructed(Type returnTypeImpl) {
            return isAbstractReturnTypeAllowed()
                && isReturnTypeAbstractOrCanBeConstructed( returnTypeImpl );
        }

        private SubclassMapping createSubclassMapping(SubclassMappingOptions subclassMappingOptions) {
            TypeFactory typeFactory = ctx.getTypeFactory();
            Type sourceType = typeFactory.getType( subclassMappingOptions.getSource() );
            Type targetType = typeFactory.getType( subclassMappingOptions.getTarget() );

            SourceRHS rightHandSide = new SourceRHS(
                "subclassMapping",
                sourceType,
                Collections.emptySet(),
                "SubclassMapping for " + sourceType.getFullyQualifiedName() );
            SelectionCriteria criteria =
                SelectionCriteria
                    .forSubclassMappingMethods(
                        subclassMappingOptions.getSelectionParameters().withSourceRHS( rightHandSide ),
                        subclassMappingOptions.getMappingControl( ctx.getElementUtils() )
                    );
            Assignment assignment = ctx
                                   .getMappingResolver()
                                   .getTargetAssignment(
                                       method,
                                       null,
                                       targetType,
                                       FormattingParameters.EMPTY,
                                       criteria,
                                       rightHandSide,
                                       subclassMappingOptions.getMirror(),
                                           () -> forgeSubclassMapping(
                                               rightHandSide,
                                               sourceType,
                                               targetType,
                                               mappingReferences ) );
            String sourceArgument = null;
            for ( Parameter parameter : method.getSourceParameters() ) {
                if ( ctx
                    .getTypeUtils()
                    .isAssignable( sourceType.getTypeMirror(), parameter.getType().getTypeMirror() ) ) {
                    sourceArgument = parameter.getName();
                    if ( assignment != null ) {
                        assignment.setSourceLocalVarName(
                            "(" + sourceType.createReferenceName() + ") " + sourceArgument );
                    }
                }
            }
            return new SubclassMapping( sourceType, sourceArgument, targetType, assignment );
        }

        private boolean isAbstractReturnTypeAllowed() {
            return !method.getOptions().getSubclassMappings().isEmpty()
                && ( method.getOptions().getBeanMapping().getSubclassExhaustiveStrategy().isAbstractReturnTypeAllowed()
                    || isCorrectlySealed() );
        }

        private boolean isCorrectlySealed() {
            Type mappingSourceType = method.getMappingSourceType();
            return isCorrectlySealed( mappingSourceType );
        }

        private boolean isCorrectlySealed(Type mappingSourceType) {
            if ( mappingSourceType.isSealed() ) {
                List<? extends TypeMirror> unusedPermittedSubclasses =
                    new ArrayList<>( mappingSourceType.getPermittedSubclasses() );
                method.getOptions().getSubclassMappings().forEach( subClassOption -> {
                    for (Iterator<? extends TypeMirror> iterator = unusedPermittedSubclasses.iterator();
                         iterator.hasNext(); ) {
                        if ( ctx.getTypeUtils().isSameType( iterator.next(), subClassOption.getSource() ) ) {
                            iterator.remove();
                        }
                    }
                } );
                for ( Iterator<? extends TypeMirror> iterator = unusedPermittedSubclasses.iterator();
                                iterator.hasNext(); ) {
                    TypeMirror typeMirror = iterator.next();
                    Type type = ctx.getTypeFactory().getType( typeMirror );
                    if ( type.isAbstract() && isCorrectlySealed( type ) ) {
                        iterator.remove();
                    }
                }
                return unusedPermittedSubclasses.isEmpty();
            }
            return false;
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

                    ReadAccessor targetPropertyReadAccessor =
                        method.getResultType().getReadAccessor( propertyName, forceUpdateMethod );
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

        private boolean canResultTypeFromBeanMappingBeConstructed(Type resultType) {

            boolean error = true;
            if ( resultType.isAbstract() ) {
                ctx.getMessager().printMessage(
                    method.getExecutable(),
                    method.getOptions().getBeanMapping().getMirror(),
                    BEANMAPPING_ABSTRACT,
                    resultType.describe(),
                    method.getResultType().describe()
                );
                error = false;
            }
            else if ( !resultType.isAssignableTo( method.getResultType() ) ) {
                ctx.getMessager().printMessage(
                    method.getExecutable(),
                    method.getOptions().getBeanMapping().getMirror(),
                    BEANMAPPING_NOT_ASSIGNABLE,
                    resultType.describe(),
                    method.getResultType().describe()
                );
                error = false;
            }
            else if ( !resultType.hasAccessibleConstructor() ) {
                ctx.getMessager().printMessage(
                    method.getExecutable(),
                    method.getOptions().getBeanMapping().getMirror(),
                    Message.GENERAL_NO_SUITABLE_CONSTRUCTOR,
                    resultType.describe()
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
                    returnType.describe()
                );
                error = false;
            }
            else if ( !returnType.hasAccessibleConstructor() ) {
                ctx.getMessager().printMessage(
                    method.getExecutable(),
                    Message.GENERAL_NO_SUITABLE_CONSTRUCTOR,
                    returnType.describe()
                );
                error = false;
            }
            return error;
        }

        private boolean isReturnTypeAbstractOrCanBeConstructed(Type returnType) {
            boolean error = true;
            if ( !returnType.isAbstract() && !returnType.hasAccessibleConstructor() ) {
                ctx
                   .getMessager()
                   .printMessage(
                       method.getExecutable(),
                       Message.GENERAL_NO_SUITABLE_CONSTRUCTOR,
                       returnType.describe() );
                error = false;
            }
            return error;
        }

        /**
         * Find a factory method for a return type or for a builder.
         * @param returnTypeImpl the return type implementation to construct
         * @param @selectionParameters
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
                    Message.GENERAL_AMBIGUOUS_FACTORY_METHOD,
                    returnTypeImpl.describe(),
                    matchingFactoryMethods.stream()
                        .map( SelectedMethod::getMethod )
                        .map( Method::describe )
                        .collect( Collectors.joining( ", " ) )
                );
                hasFactoryMethod = true;
            }
        }

        private ConstructorAccessor getConstructorAccessor(Type type) {
            if ( type.isAbstract() ) {
                // We cannot construct abstract classes.
                // Usually we won't reach here,
                // but if SubclassMapping is used with SubclassExhaustiveStrategy#RUNTIME_EXCEPTION
                // then we will still generate the code.
                // We shouldn't generate anything for those abstract types
                return null;
            }

            if ( type.isRecord() ) {

                List<ExecutableElement> constructors = ElementFilter.constructorsIn( type.getTypeElement()
                    .getEnclosedElements() );

                for ( ExecutableElement constructor : constructors ) {
                    if ( constructor.getModifiers().contains( Modifier.PRIVATE ) ) {
                        continue;
                    }

                    // prefer constructor annotated with @Default
                    if ( hasDefaultAnnotationFromAnyPackage( constructor ) ) {
                        return getConstructorAccessor( type, constructor );
                    }
                }


                // Other than that, just get the record components and use them
                List<Element> recordComponents = type.getRecordComponents();
                List<ParameterBinding> parameterBindings = new ArrayList<>( recordComponents.size() );
                Map<String, Accessor> constructorAccessors = new LinkedHashMap<>();
                for ( Element recordComponent : recordComponents ) {
                    TypeMirror recordComponentMirror = ctx.getTypeUtils()
                        .asMemberOf( (DeclaredType) type.getTypeMirror(), recordComponent );
                    String parameterName = recordComponent.getSimpleName().toString();
                    Accessor accessor = createConstructorAccessor(
                        recordComponent,
                        recordComponentMirror,
                        parameterName
                    );
                    constructorAccessors.put(
                        parameterName,
                        accessor
                    );

                    parameterBindings.add( ParameterBinding.fromTypeAndName(
                        ctx.getTypeFactory().getType( recordComponentMirror ),
                        accessor.getSimpleName()
                    ) );
                }
                return new ConstructorAccessor( parameterBindings, constructorAccessors );
            }

            List<ExecutableElement> constructors = ElementFilter.constructorsIn( type.getTypeElement()
                .getEnclosedElements() );

            // The rules for picking a constructor are the following:
            // 1. Constructor annotated with @Default (from any package) has highest precedence
            // 2. If there is a single public constructor then it would be used to construct the object
            // 3. If a parameterless constructor exists then it would be used to construct the object, and the other
            // constructors will be ignored
            ExecutableElement defaultAnnotatedConstructor = null;
            ExecutableElement parameterLessConstructor = null;
            List<ExecutableElement> accessibleConstructors = new ArrayList<>( constructors.size() );
            List<ExecutableElement> publicConstructors = new ArrayList<>( );

            for ( ExecutableElement constructor : constructors ) {
                if ( constructor.getModifiers().contains( Modifier.PRIVATE ) ) {
                    continue;
                }

                if ( hasDefaultAnnotationFromAnyPackage( constructor ) ) {
                    // We found a constructor annotated with @Default everything else is irrelevant
                    defaultAnnotatedConstructor = constructor;
                    break;
                }

                if ( constructor.getParameters().isEmpty() ) {
                    parameterLessConstructor = constructor;
                }
                else {
                    accessibleConstructors.add( constructor );
                }

                if ( constructor.getModifiers().contains( Modifier.PUBLIC ) ) {
                    publicConstructors.add( constructor );
                }
            }

            if ( defaultAnnotatedConstructor != null ) {
                // If a default annotated constructor exists it will be used, it has highest precedence
                return getConstructorAccessor( type, defaultAnnotatedConstructor );
            }

            if ( publicConstructors.size() == 1 ) {
                // If there is a single public constructor then use that one
                ExecutableElement publicConstructor = publicConstructors.get( 0 );
                if ( publicConstructor.getParameters().isEmpty() ) {
                    // The public parameterless constructor
                    return null;
                }

                return getConstructorAccessor( type, publicConstructor );
            }

            if ( parameterLessConstructor != null ) {
                // If there is a constructor without parameters use it
                return null;
            }

            if ( accessibleConstructors.isEmpty() ) {
                return null;
            }

            if ( accessibleConstructors.size() > 1 ) {

                ctx.getMessager().printMessage(
                    method.getExecutable(),
                    GENERAL_AMBIGUOUS_CONSTRUCTORS,
                    type,
                    constructors.stream()
                        .map( ExecutableElement::getParameters )
                        .map( ps -> ps.stream()
                            .map( VariableElement::asType )
                            .map( String::valueOf )
                            .collect( Collectors.joining( ", ", type.getName() + "(", ")" ) )
                        )
                        .collect( Collectors.joining( ", " ) )
                );
                return new ConstructorAccessor( true, Collections.emptyList(), Collections.emptyMap() );
            }
            else {
                return getConstructorAccessor( type, accessibleConstructors.get( 0 ) );
            }

        }

        private ConstructorAccessor getConstructorAccessor(Type type, ExecutableElement constructor) {
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
                    Accessor constructorAccessor = createConstructorAccessor(
                        parameterElement,
                        constructorParameter.getType().getTypeMirror(),
                        parameterName
                    );
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
                return new ConstructorAccessor( true, Collections.emptyList(), Collections.emptyMap() );
            }
            else {
                Map<String, Accessor> constructorAccessors = new LinkedHashMap<>();
                List<ParameterBinding> parameterBindings = new ArrayList<>( constructorProperties.size() );
                for ( int i = 0; i < constructorProperties.size(); i++ ) {
                    String parameterName = constructorProperties.get( i );
                    Parameter constructorParameter = constructorParameters.get( i );
                    Element parameterElement = constructorParameter.getElement();
                    Accessor constructorAccessor = createConstructorAccessor(
                        parameterElement,
                        constructorParameter.getType().getTypeMirror(),
                        parameterName
                    );
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

        private Accessor createConstructorAccessor(Element element, TypeMirror accessedType, String parameterName) {
            String safeParameterName = Strings.getSafeVariableName(
                parameterName,
                existingVariableNames
            );
            existingVariableNames.add( safeParameterName );
            return new ElementAccessor( element, accessedType, safeParameterName );
        }

        private boolean hasDefaultAnnotationFromAnyPackage(Element element) {
            for ( AnnotationMirror annotationMirror : element.getAnnotationMirrors() ) {
                if ( annotationMirror.getAnnotationType()
                    .asElement()
                    .getSimpleName()
                    .contentEquals( "Default" ) ) {
                    return true;
                }
            }

            return false;
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
         * Determine whether defined mappings should be handled on the result type.
         * They should be, if any of the following is true:
         * <ul>
         *     <li>The {@code resultTypeToMap} is not abstract</li>
         *     <li>There is a factory method</li>
         *     <li>The method is an update method</li>
         * </ul>
         * Otherwise, it means that we have reached this because subclass mappings are being used
         * and the chosen strategy is runtime exception.
         *
         * @param resultTypeToMap the type in which the defined target properties are defined
         * @return {@code true} if defined mappings should be handled for the result type, {@code false} otherwise
         */
        private boolean shouldHandledDefinedMappings(Type resultTypeToMap) {
            if ( !resultTypeToMap.isAbstract() ) {
                return true;
            }

            if ( hasFactoryMethod ) {
                return true;
            }

            if ( method.isUpdateMethod() ) {
                return true;
            }

            return false;
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
                    if ( target == null ) {
                        // When the shallowest property name is null then it is for @Mapping(target = ".")
                        if ( this.targetThisReferences == null ) {
                            this.targetThisReferences = new ArrayList<>();
                        }
                        this.targetThisReferences.add( mapping );
                        continue;
                    }
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
            ReadAccessor targetReadAccessor = resultTypeToMap.getReadAccessor(
                targetPropertyName,
                method.getSourceParameters().size() == 1
            );

            if ( targetWriteAccessor == null ) {
                if ( targetReadAccessor == null ) {
                    MappingOptions.InheritContext inheritContext = mapping.getInheritContext();
                    if ( inheritContext != null ) {
                        if ( inheritContext.isForwarded() &&
                            inheritContext.getTemplateMethod().isUpdateMethod() != method.isUpdateMethod() ) {
                            // When a configuration is inherited and the template method is not same type as the current
                            // method then we can safely ignore this mapping.
                            // This means that a property which is inherited might be present for a direct mapping
                            // via the Builder, but not for an update mapping (directly on the object itself),
                            // or vice versa
                            return false;
                        }
                        else if ( inheritContext.isReversed() ) {
                            // When a configuration is reverse inherited and there are no read or write accessor
                            // then we should ignore this mapping.
                            // This most likely means that we were mapping the source parameter to the target.
                            // If the error is due to something else it will be reported on the original mapping
                            return false;
                        }
                    }
                    Set<String> readAccessors = resultTypeToMap.getPropertyReadAccessors().keySet();
                    String mostSimilarProperty = Strings.getMostSimilarWord( targetPropertyName, readAccessors );

                    Message msg;
                    String[] args;

                    if ( targetRef.getPathProperties().isEmpty() ) {
                        msg = Message.BEANMAPPING_UNKNOWN_PROPERTY_IN_RESULTTYPE;
                        args = new String[] {
                            targetPropertyName,
                            resultTypeToMap.describe(),
                            mostSimilarProperty
                        };
                    }
                    else {
                        List<String> pathProperties = new ArrayList<>( targetRef.getPathProperties() );
                        pathProperties.add( mostSimilarProperty );
                        msg = Message.BEANMAPPING_UNKNOWN_PROPERTY_IN_TYPE;
                        args = new String[] {
                            targetPropertyName,
                            resultTypeToMap.describe(),
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
                            resultTypeToMap.describe()
                        };
                    }
                    else {
                        msg = Message.BEANMAPPING_PROPERTY_HAS_NO_WRITE_ACCESSOR_IN_TYPE;
                        args = new Object[] {
                            targetPropertyName,
                            resultTypeToMap.describe(),
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

            // it's a constant
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

            // it's an expression
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
            // it's a plain-old property mapping
            else  {

                SourceReference sourceRef = mappingRef.getSourceReference();
                // sourceRef is not defined, check if a source property has the same name
                if ( sourceRef == null ) {
                    // Here we follow the same rules as when we implicitly map
                    // When we implicitly map we first do property name based mapping
                    // i.e. look for matching properties in the source types
                    // and then do parameter name based mapping
                    for ( Parameter sourceParameter : method.getSourceParameters() ) {
                        SourceReference matchingSourceRef = getSourceRefByTargetName(
                            sourceParameter,
                            targetPropertyName
                        );
                        if ( matchingSourceRef != null ) {
                            if ( sourceRef != null ) {
                                errorOccured = true;
                                // This can only happen when the target property matches multiple properties
                                // within the different source parameters
                                ctx.getMessager()
                                    .printMessage(
                                        method.getExecutable(),
                                        mappingRef.getMapping().getMirror(),
                                        Message.BEANMAPPING_SEVERAL_POSSIBLE_SOURCES,
                                        targetPropertyName
                                    );
                                break;
                            }
                            // We can't break here since it is possible that the same property exists in multiple
                            // source parameters
                            sourceRef = matchingSourceRef;
                        }
                    }

                }

                if ( sourceRef == null ) {
                    // still no match. Try if one of the parameters has the same name
                    sourceRef = method.getSourceParameters()
                        .stream()
                        .filter( p -> targetPropertyName.equals( p.getName() ) )
                        .findAny()
                        .map( p -> new SourceReference.BuilderFromProperty()
                            .sourceParameter( p )
                            .name( targetPropertyName )
                            .build() )
                        .orElse( null );
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
                            .conditionJavaExpression( mapping.getConditionJavaExpression() )
                            .mirror( mapping.getMirror() )
                            .options( mapping )
                            .build();
                        handledTargets.add( targetPropertyName );
                        Parameter sourceParameter = sourceRef.getParameter();
                        unprocessedSourceParameters.remove( sourceParameter );
                        // If the source parameter was directly mapped
                        if ( sourceRef.getPropertyEntries().isEmpty() ) {
                            // Ignore all of its source properties completely
                            ignoreSourceProperties( sourceParameter );
                        }
                        else {
                            unprocessedSourceProperties.remove( sourceRef.getShallowestPropertyName() );
                        }
                    }
                    else {
                        errorOccured = true;
                    }
                }
                else {
                    errorOccured = true;

                    if ( method.getSourceParameters().size() == 1 ) {
                        ctx.getMessager()
                            .printMessage(
                                method.getExecutable(),
                                mapping.getMirror(),
                                mapping.getTargetAnnotationValue(),
                                PROPERTYMAPPING_CANNOT_DETERMINE_SOURCE_PROPERTY_FROM_TARGET,
                                method.getSourceParameters().get( 0 ).getName(),
                                targetPropertyName
                            );
                    }
                    else {
                        ctx.getMessager()
                            .printMessage(
                                method.getExecutable(),
                                mapping.getMirror(),
                                mapping.getTargetAnnotationValue(),
                                PROPERTYMAPPING_CANNOT_DETERMINE_SOURCE_PARAMETER_FROM_TARGET,
                                targetPropertyName
                            );
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
         * <p>
         * duplicates will be handled by {@link #applyPropertyNameBasedMapping(List)}
         */
        private void applyTargetThisMapping() {
            if ( this.targetThisReferences == null ) {
                return;
            }
            Set<String> handledTargetProperties = new HashSet<>();
            for ( MappingReference targetThis : this.targetThisReferences ) {

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
                    SourceReference sourceRef = getSourceRefByTargetName( sourceParameter, targetPropertyName );
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

                ReadAccessor targetPropertyReadAccessor =
                    method.getResultType()
                        .getReadAccessor( targetPropertyName, method.getSourceParameters().size() == 1 );
                MappingReferences mappingRefs = extractMappingReferences( targetPropertyName, false );
                PropertyMapping propertyMapping = new PropertyMappingBuilder().mappingContext( ctx )
                    .sourceMethod( method )
                    .sourcePropertyName( targetPropertyName )
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

                        ReadAccessor targetPropertyReadAccessor =
                            method.getResultType()
                                .getReadAccessor( targetProperty.getKey(), method.getSourceParameters().size() == 1 );
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
                        unprocessedConstructorProperties.remove( targetProperty.getKey() );
                        ignoreSourceProperties( sourceParameter );
                    }
                }
            }
        }

        private void ignoreSourceProperties(Parameter sourceParameter) {
            // The source parameter was directly mapped so ignore all of its source properties completely
            if ( !sourceParameter.getType().isPrimitive() && !sourceParameter.getType().isArrayType() ) {
                // We explicitly ignore source properties from primitives or array types
                Map<String, ReadAccessor> readAccessors = sourceParameter.getType()
                    .getPropertyReadAccessors();
                for ( String sourceProperty : readAccessors.keySet() ) {
                    unprocessedSourceProperties.remove( sourceProperty );
                }
            }
        }

        private SourceReference getSourceRefByTargetName(Parameter sourceParameter, String targetPropertyName) {

            SourceReference sourceRef = null;

            if ( sourceParameter.getType().isPrimitive() || sourceParameter.getType().isArrayType() ) {
                return sourceRef;
            }

            ReadAccessor sourceReadAccessor = sourceParameter.getType()
                .getReadAccessor( targetPropertyName, method.getSourceParameters().size() == 1 );
            if ( sourceReadAccessor != null ) {
                // property mapping
                PresenceCheckAccessor sourcePresenceChecker =
                    sourceParameter.getType().getPresenceChecker( targetPropertyName );

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
            // If we have ignoreByDefault = true, unprocessed target properties are not an issue.
            if ( method.getOptions().getBeanMapping().isIgnoredByDefault() ) {
                return ReportingPolicyGem.IGNORE;
            }
            if ( method.getOptions().getBeanMapping() != null ) {
                return method.getOptions().getBeanMapping().unmappedTargetPolicy();
            }
            return method.getOptions().getMapper().unmappedTargetPolicy();
        }

        private void reportErrorForUnmappedTargetPropertiesIfRequired(Type resultType,
                                                                      boolean constructorAccessorHadError) {

            // fetch settings from element to implement
            ReportingPolicyGem unmappedTargetPolicy = getUnmappedTargetPolicy();

            if ( targetProperties.isEmpty() ) {
                if ( method instanceof ForgedMethod ) {
                    ForgedMethod forgedMethod = (ForgedMethod) method;
                    if ( forgedMethod.getHistory() == null ) {
                        Type sourceType = this.method.getParameters().get( 0 ).getType();
                        Type targetType = this.method.getReturnType();
                        ctx.getMessager().printMessage(
                            this.method.getExecutable(),
                            Message.PROPERTYMAPPING_FORGED_MAPPING_NOT_FOUND,
                            sourceType.describe(),
                            targetType.describe(),
                            targetType.describe(),
                            sourceType.describe()
                        );
                    }
                    else {
                        ForgedMethodHistory history = forgedMethod.getHistory();
                        ctx.getMessager().printMessage(
                            this.method.getExecutable(),
                            Message.PROPERTYMAPPING_FORGED_MAPPING_WITH_HISTORY_NOT_FOUND,
                            history.createSourcePropertyErrorMessage(),
                            history.getTargetType().describe(),
                            history.createTargetPropertyName(),
                            history.getTargetType().describe(),
                            history.getSourceType().describe()
                        );
                    }
                }
                else if ( !constructorAccessorHadError ) {
                    ctx.getMessager().printMessage(
                        method.getExecutable(),
                        Message.PROPERTYMAPPING_TARGET_HAS_NO_TARGET_PROPERTIES,
                        resultType.describe()
                    );
                }
            }
            else if ( !unprocessedTargetProperties.isEmpty() && unmappedTargetPolicy.requiresReport() ) {

                Message unmappedPropertiesMsg;
                Message unmappedForgedPropertiesMsg;
                if ( unmappedTargetPolicy.getDiagnosticKind() == Diagnostic.Kind.ERROR ) {
                    unmappedPropertiesMsg = Message.BEANMAPPING_UNMAPPED_TARGETS_ERROR;
                    unmappedForgedPropertiesMsg = Message.BEANMAPPING_UNMAPPED_FORGED_TARGETS_ERROR;
                }
                else {
                    unmappedPropertiesMsg = Message.BEANMAPPING_UNMAPPED_TARGETS_WARNING;
                    unmappedForgedPropertiesMsg = Message.BEANMAPPING_UNMAPPED_FORGED_TARGETS_WARNING;
                }

                reportErrorForUnmappedProperties(
                    unprocessedTargetProperties,
                    unmappedPropertiesMsg,
                    unmappedForgedPropertiesMsg
                );

            }
        }

        private ReportingPolicyGem getUnmappedSourcePolicy() {
            if ( mappingReferences.isForForgedMethods() ) {
                return ReportingPolicyGem.IGNORE;
            }
            return method.getOptions().getBeanMapping().unmappedSourcePolicy();
        }

        private void reportErrorForUnmappedSourcePropertiesIfRequired() {
            ReportingPolicyGem unmappedSourcePolicy = getUnmappedSourcePolicy();
            if ( !unprocessedSourceProperties.isEmpty() && unmappedSourcePolicy.requiresReport() ) {
                Message unmappedPropertiesMsg;
                Message unmappedForgedPropertiesMsg;
                if ( unmappedSourcePolicy.getDiagnosticKind() == Diagnostic.Kind.ERROR ) {
                    unmappedPropertiesMsg = Message.BEANMAPPING_UNMAPPED_SOURCES_ERROR;
                    unmappedForgedPropertiesMsg = Message.BEANMAPPING_UNMAPPED_FORGED_SOURCES_ERROR;
                }
                else {
                    unmappedPropertiesMsg = Message.BEANMAPPING_UNMAPPED_SOURCES_WARNING;
                    unmappedForgedPropertiesMsg = Message.BEANMAPPING_UNMAPPED_FORGED_SOURCES_WARNING;
                }

                reportErrorForUnmappedProperties(
                    unprocessedSourceProperties,
                    unmappedPropertiesMsg,
                    unmappedForgedPropertiesMsg );
            }
        }

        private void reportErrorForUnmappedProperties(Map<String, Accessor> unmappedProperties,
                                                                Message unmappedPropertiesMsg,
                                                                Message unmappedForgedPropertiesMsg) {
            if ( !( method instanceof ForgedMethod ) ) {
                Object[] args = new Object[] {
                    MessageFormat.format(
                        "{0,choice,1#property|1<properties}: \"{1}\"",
                        unmappedProperties.size(),
                        Strings.join( unmappedProperties.keySet(), ", " )
                    )
                };

                ctx.getMessager().printMessage(
                    method.getExecutable(),
                    unmappedPropertiesMsg,
                    args
                );
            }
            else if ( !ctx.isErroneous() ) {
                String sourceErrorMessage = method.getParameters().get( 0 ).getType().describe();
                String targetErrorMessage = method.getReturnType().describe();
                if ( ( (ForgedMethod) method ).getHistory() != null ) {
                    ForgedMethodHistory history = ( (ForgedMethod) method ).getHistory();
                    sourceErrorMessage = history.createSourcePropertyErrorMessage();
                    targetErrorMessage = MessageFormat.format(
                        "\"{0} {1}\"",
                        history.getTargetType().describe(),
                        history.createTargetPropertyName()
                    );
                }
                Object[] args = new Object[] {
                    MessageFormat.format(
                        "{0,choice,1#property|1<properties}: \"{1}\"",
                        unmappedProperties.size(),
                        Strings.join( unmappedProperties.keySet(), ", " )
                    ),
                    sourceErrorMessage,
                    targetErrorMessage
                };
                ctx.getMessager().printMessage(
                    method.getExecutable(),
                    unmappedForgedPropertiesMsg,
                    args
                );
            }
        }

        private void reportErrorForMissingIgnoredSourceProperties() {
            if ( !missingIgnoredSourceProperties.isEmpty() ) {
                Object[] args = new Object[] {
                        MessageFormat.format(
                                "{0,choice,1#property|1<properties}: \"{1}\"",
                                missingIgnoredSourceProperties.size(),
                                Strings.join( missingIgnoredSourceProperties, ", " )
                        )
                };

                ctx.getMessager().printMessage(
                    method.getExecutable(),
                    Message.BEANMAPPING_MISSING_IGNORED_SOURCES_ERROR,
                    args
                );
            }
        }

        private void reportErrorForUnusedSourceParameters() {
            for ( Parameter sourceParameter : unprocessedSourceParameters ) {
                Type parameterType = sourceParameter.getType();
                if ( parameterType.isMapType() ) {
                    // We are only going to output a warning for the source parameter if it was unused
                    // i.e. the intention of the user was most likely to use it as a mapping from Bean to Map
                    List<Type> typeParameters = parameterType.getTypeParameters();
                    if ( typeParameters.size() != 2 || !typeParameters.get( 0 ).isString() ) {
                        Message message = typeParameters.isEmpty() ?
                            Message.MAPTOBEANMAPPING_RAW_MAP :
                            Message.MAPTOBEANMAPPING_WRONG_KEY_TYPE;
                        ctx.getMessager()
                            .printMessage(
                                method.getExecutable(),
                                message,
                                sourceParameter.getName(),
                                String.format(
                                    "Map<%s,%s>",
                                    !typeParameters.isEmpty() ? typeParameters.get( 0 ).describe() : "",
                                    typeParameters.size() > 1 ? typeParameters.get( 1 ).describe() : ""
                                )
                            );
                    }
                }
            }
        }
    }

    private static class ConstructorAccessor {
        private final boolean hasError;
        private final List<ParameterBinding> parameterBindings;
        private final Map<String, Accessor> constructorAccessors;

        private ConstructorAccessor(
            List<ParameterBinding> parameterBindings,
            Map<String, Accessor> constructorAccessors) {
            this( false, parameterBindings, constructorAccessors );
        }

        private ConstructorAccessor(boolean hasError, List<ParameterBinding> parameterBindings,
                                    Map<String, Accessor> constructorAccessors) {
            this.hasError = hasError;
            this.parameterBindings = parameterBindings;
            this.constructorAccessors = constructorAccessors;
        }
    }

    //CHECKSTYLE:OFF
    private BeanMappingMethod(Method method,
                              List<Annotation> annotations,
                              Collection<String> existingVariableNames,
                              List<PropertyMapping> propertyMappings,
                              MethodReference factoryMethod,
                              boolean mapNullToDefault,
                              Type returnTypeToConstruct,
                              BuilderType returnTypeBuilder,
                              List<LifecycleCallbackMethodReference> beforeMappingReferences,
                              List<LifecycleCallbackMethodReference> afterMappingReferences,
                              List<LifecycleCallbackMethodReference> beforeMappingReferencesWithFinalizedReturnType,
                              List<LifecycleCallbackMethodReference> afterMappingReferencesWithFinalizedReturnType,
                              MethodReference finalizerMethod,
                              MappingReferences mappingReferences,
                              List<SubclassMapping> subclassMappings,
                              Map<String, PresenceCheck> presenceChecksByParameter,
                              Type subclassExhaustiveException) {
        super(
            method,
            annotations,
            existingVariableNames,
            factoryMethod,
            mapNullToDefault,
            beforeMappingReferences,
            afterMappingReferences
        );
        //CHECKSTYLE:ON

        this.propertyMappings = propertyMappings;
        this.returnTypeBuilder = returnTypeBuilder;
        this.finalizerMethod = finalizerMethod;
        this.subclassExhaustiveException = subclassExhaustiveException;
        if ( this.finalizerMethod != null ) {
            this.finalizedResultName =
                Strings.getSafeVariableName( getResultName() + "Result", existingVariableNames );
            existingVariableNames.add( this.finalizedResultName );
        }
        else {
            this.finalizedResultName = null;
        }
        this.mappingReferences = mappingReferences;

        this.beforeMappingReferencesWithFinalizedReturnType = beforeMappingReferencesWithFinalizedReturnType;
        this.afterMappingReferencesWithFinalizedReturnType = afterMappingReferencesWithFinalizedReturnType;

        // initialize constant mappings as all mappings, but take out the ones that can be contributed to a
        // parameter mapping.
        this.mappingsByParameter = new HashMap<>();
        this.constantMappings = new ArrayList<>( propertyMappings.size() );
        this.presenceChecksByParameter = presenceChecksByParameter;
        this.constructorMappingsByParameter = new LinkedHashMap<>();
        this.constructorConstantMappings = new ArrayList<>();
        Set<String> sourceParameterNames = new HashSet<>();
        for ( Parameter sourceParameter : getSourceParameters() ) {
            sourceParameterNames.add( sourceParameter.getName() );
        }
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
        this.subclassMappings = subclassMappings;
    }

    public Type getSubclassExhaustiveException() {
        return subclassExhaustiveException;
    }

    public List<PropertyMapping> getConstantMappings() {
        return constantMappings;
    }

    public List<PropertyMapping> getConstructorConstantMappings() {
        return constructorConstantMappings;
    }

    public List<SubclassMapping> getSubclassMappings() {
        return subclassMappings;
    }

    public String getFinalizedResultName() {
        return finalizedResultName;
    }

    public List<LifecycleCallbackMethodReference> getBeforeMappingReferencesWithFinalizedReturnType() {
        return beforeMappingReferencesWithFinalizedReturnType;
    }

    public List<LifecycleCallbackMethodReference> getAfterMappingReferencesWithFinalizedReturnType() {
        return afterMappingReferencesWithFinalizedReturnType;
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

    public boolean hasSubclassMappings() {
        return !subclassMappings.isEmpty();
    }

    public boolean isAbstractReturnType() {
        return getFactoryMethod() == null && returnTypeToConstruct != null
            && returnTypeToConstruct.isAbstract();
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
            if ( propertyMapping.isConstructorMapping() ) {
                // We need to add the target type imports for a constructor mapper since we define its parameters
                types.addAll( propertyMapping.getTargetType().getImportTypes() );
            }
        }
        for ( SubclassMapping subclassMapping : subclassMappings ) {
            types.addAll( subclassMapping.getImportTypes() );
        }

        if ( returnTypeToConstruct != null  ) {
            types.addAll( returnTypeToConstruct.getImportTypes() );
        }
        if ( returnTypeBuilder != null ) {
            types.add( returnTypeBuilder.getOwningType() );
        }
        for ( LifecycleCallbackMethodReference reference : beforeMappingReferencesWithFinalizedReturnType ) {
            types.addAll( reference.getImportTypes() );
        }
        for ( LifecycleCallbackMethodReference reference : afterMappingReferencesWithFinalizedReturnType ) {
            types.addAll( reference.getImportTypes() );
        }

        return types;
    }

    public Collection<PresenceCheck> getSourcePresenceChecks() {
        return presenceChecksByParameter.values();
    }

    public Map<String, PresenceCheck> getPresenceChecksByParameter() {
        return presenceChecksByParameter;
    }

    public PresenceCheck getPresenceCheckByParameter(Parameter parameter) {
        return presenceChecksByParameter.get( parameter.getName() );
    }

    public List<Parameter> getSourceParametersNeedingPresenceCheck() {
        return getSourceParameters().stream()
                            .filter( this::needsPresenceCheck )
                            .collect( Collectors.toList() );
    }

    public List<Parameter> getSourceParametersNotNeedingPresenceCheck() {
        return getSourceParameters().stream()
                            .filter( parameter -> !needsPresenceCheck( parameter ) )
                            .collect( Collectors.toList() );
    }

    private boolean needsPresenceCheck(Parameter parameter) {
        if ( !presenceChecksByParameter.containsKey( parameter.getName() ) ) {
            return false;
        }

        List<PropertyMapping> mappings = propertyMappingsByParameter( parameter );
        if ( mappings.size() == 1 && doesNotNeedPresenceCheckForSourceParameter( mappings.get( 0 ) ) ) {
            return false;
        }

        mappings = constructorPropertyMappingsByParameter( parameter );

        if ( mappings.size() == 1 && doesNotNeedPresenceCheckForSourceParameter( mappings.get( 0 ) ) ) {
            return false;
        }

        return true;
    }

    private boolean doesNotNeedPresenceCheckForSourceParameter(PropertyMapping mapping) {
        if ( mapping.getAssignment().isCallingUpdateMethod() ) {
            // If the mapping assignment is calling an update method then we should do a null check
            // in the bean mapping
            return false;
        }

        return mapping.getAssignment().isSourceReferenceParameter();
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

        if ( !Objects.equals( propertyMappings, that.propertyMappings ) ) {
            return false;
        }

        if ( !Objects.equals( mappingReferences, that.mappingReferences ) ) {
            return false;
        }


        return true;
    }

}
