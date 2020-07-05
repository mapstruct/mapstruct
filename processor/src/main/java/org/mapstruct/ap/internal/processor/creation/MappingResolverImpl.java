/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.processor.creation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import org.mapstruct.ap.internal.conversion.ConversionProvider;
import org.mapstruct.ap.internal.conversion.Conversions;
import org.mapstruct.ap.internal.gem.ReportingPolicyGem;
import org.mapstruct.ap.internal.model.Field;
import org.mapstruct.ap.internal.model.HelperMethod;
import org.mapstruct.ap.internal.model.MapperReference;
import org.mapstruct.ap.internal.model.MappingBuilderContext.MappingResolver;
import org.mapstruct.ap.internal.model.MethodReference;
import org.mapstruct.ap.internal.model.SupportingField;
import org.mapstruct.ap.internal.model.SupportingMappingMethod;
import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.ConversionContext;
import org.mapstruct.ap.internal.model.common.DefaultConversionContext;
import org.mapstruct.ap.internal.model.common.FormattingParameters;
import org.mapstruct.ap.internal.model.common.SourceRHS;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.model.source.SourceMethod;
import org.mapstruct.ap.internal.model.source.builtin.BuiltInMappingMethods;
import org.mapstruct.ap.internal.model.source.builtin.BuiltInMethod;
import org.mapstruct.ap.internal.model.source.selector.MethodSelectors;
import org.mapstruct.ap.internal.model.source.selector.SelectedMethod;
import org.mapstruct.ap.internal.model.source.selector.SelectionCriteria;
import org.mapstruct.ap.internal.util.Collections;
import org.mapstruct.ap.internal.util.FormattingMessager;
import org.mapstruct.ap.internal.util.Message;
import org.mapstruct.ap.internal.util.MessageConstants;
import org.mapstruct.ap.internal.util.NativeTypes;
import org.mapstruct.ap.internal.util.Strings;

import static java.util.Collections.singletonList;
import static org.mapstruct.ap.internal.util.Collections.first;

/**
 * The one and only implementation of {@link MappingResolver}. The class has been split into an interface an
 * implementation for the sake of avoiding package dependencies. Specifically, this implementation refers to classes
 * which should not be exposed to the {@code model} package.
 *
 * @author Sjaak Derksen
 */
public class MappingResolverImpl implements MappingResolver {

    private final FormattingMessager messager;
    private final Types typeUtils;
    private final TypeFactory typeFactory;

    private final List<Method> sourceModel;
    private final List<MapperReference> mapperReferences;

    private final Conversions conversions;
    private final BuiltInMappingMethods builtInMethods;
    private final MethodSelectors methodSelectors;

    /**
     * Private methods which are not present in the original mapper interface and are added to map certain property
     * types.
     */
    private final Set<SupportingMappingMethod> usedSupportedMappings = new HashSet<>();

    public MappingResolverImpl(FormattingMessager messager, Elements elementUtils, Types typeUtils,
                               TypeFactory typeFactory, List<Method> sourceModel,
                               List<MapperReference> mapperReferences) {
        this.messager = messager;
        this.typeUtils = typeUtils;
        this.typeFactory = typeFactory;

        this.sourceModel = sourceModel;
        this.mapperReferences = mapperReferences;

        this.conversions = new Conversions( typeFactory );
        this.builtInMethods = new BuiltInMappingMethods( typeFactory );
        this.methodSelectors = new MethodSelectors( typeUtils, elementUtils, typeFactory, messager );
    }

    @Override
    public Assignment getTargetAssignment(Method mappingMethod, Type targetType,
                                          FormattingParameters formattingParameters,
                                          SelectionCriteria criteria, SourceRHS sourceRHS,
                                          AnnotationMirror positionHint,
                                          Supplier<Assignment> forger) {

        ResolvingAttempt attempt = new ResolvingAttempt(
            sourceModel,
            mappingMethod,
            formattingParameters,
            sourceRHS,
            criteria,
            positionHint,
            forger
        );

        return attempt.getTargetAssignment( sourceRHS.getSourceTypeForMatching(), targetType );
    }

    @Override
    public Set<SupportingMappingMethod> getUsedSupportedMappings() {
        return usedSupportedMappings;
    }

    private MapperReference findMapperReference(Method method) {
        for ( MapperReference ref : mapperReferences ) {
            if ( ref.getType().equals( method.getDeclaringMapper() ) ) {
                ref.setUsed( ref.isUsed() || !method.isStatic() );
                ref.setTypeRequiresImport( true );
                return ref;
            }
        }
        return null;
    }

    private class ResolvingAttempt {

        private final Method mappingMethod;
        private final List<Method> methods;
        private final SelectionCriteria selectionCriteria;
        private final SourceRHS sourceRHS;
        private final boolean savedPreferUpdateMapping;
        private final FormattingParameters formattingParameters;
        private final AnnotationMirror positionHint;
        private final Supplier<Assignment> forger;

        // resolving via 2 steps creates the possibility of wrong matches, first builtin method matches,
        // second doesn't. In that case, the first builtin method should not lead to a supported method
        // so this set must be cleared.
        private final Set<SupportingMappingMethod> supportingMethodCandidates;

        private ResolvingAttempt(List<Method> sourceModel, Method mappingMethod,
                                 FormattingParameters formattingParameters, SourceRHS sourceRHS,
                                 SelectionCriteria criteria,
                                 AnnotationMirror positionHint,
                                 Supplier<Assignment> forger) {

            this.mappingMethod = mappingMethod;
            this.methods = filterPossibleCandidateMethods( sourceModel );
            this.formattingParameters =
                formattingParameters == null ? FormattingParameters.EMPTY : formattingParameters;
            this.sourceRHS = sourceRHS;
            this.supportingMethodCandidates = new HashSet<>();
            this.selectionCriteria = criteria;
            this.savedPreferUpdateMapping = criteria.isPreferUpdateMapping();
            this.positionHint = positionHint;
            this.forger = forger;
        }

        private <T extends Method> List<T> filterPossibleCandidateMethods(List<T> candidateMethods) {
            List<T> result = new ArrayList<>( candidateMethods.size() );
            for ( T candidate : candidateMethods ) {
                if ( isCandidateForMapping( candidate ) ) {
                    result.add( candidate );
                }
            }

            return result;
        }

        private Assignment getTargetAssignment(Type sourceType, Type targetType) {

            Assignment referencedMethod;

            // first simple mapping method
            if ( allowMappingMethod() ) {
                referencedMethod = resolveViaMethod( sourceType, targetType, false );
                if ( referencedMethod != null ) {
                    referencedMethod.setAssignment( sourceRHS );
                    return referencedMethod;
                }
            }

            // then direct assignable
            if ( !hasQualfiers() ) {
                if ( ( sourceType.isAssignableTo( targetType ) ||
                    isAssignableThroughCollectionCopyConstructor( sourceType, targetType ) )
                    && allowDirect( sourceType, targetType ) ) {
                    Assignment simpleAssignment = sourceRHS;
                    return simpleAssignment;
                }
            }
            // At this point the SourceType will either
            // 1. be a String
            // 2. or when its a primitive / wrapped type and analysis successful equal to its TargetType. But in that
            //    case it should have been direct assignable.
            // In case of 1. and the target type is still a wrapped or primitive type we must assume that the check
            // in NativeType is not successful. We don't want to go through type conversion, double mappings etc.
            // with something that we already know to be wrong.
            if ( sourceType.isLiteral()
                && "java.lang.String".equals( sourceType.getFullyQualifiedName() )
                && targetType.isNative() ) {
                return null;
            }

            // then type conversion
            if ( allowConversion() ) {
                if ( !hasQualfiers() ) {
                    ConversionAssignment conversion = resolveViaConversion( sourceType, targetType );
                    if ( conversion != null ) {
                        conversion.reportMessageWhenNarrowing( messager, this );
                        conversion.getAssignment().setAssignment( sourceRHS );
                        return conversion.getAssignment();
                    }
                }

                // check for a built-in method
                if ( !hasQualfiers() ) {
                    Assignment builtInMethod = resolveViaBuiltInMethod( sourceType, targetType );
                    if ( builtInMethod != null ) {
                        builtInMethod.setAssignment( sourceRHS );
                        usedSupportedMappings.addAll( supportingMethodCandidates );
                        return builtInMethod;
                    }
                }
            }

            if ( allow2Steps() ) {
                // 2 step method, first: method(method(source))
                referencedMethod = resolveViaMethodAndMethod( sourceType, targetType );
                if ( referencedMethod != null ) {
                    usedSupportedMappings.addAll( supportingMethodCandidates );
                    return referencedMethod;
                }

                // 2 step method, then: method(conversion(source))
                referencedMethod = resolveViaConversionAndMethod( sourceType, targetType );
                if ( referencedMethod != null ) {
                    usedSupportedMappings.addAll( supportingMethodCandidates );
                    return referencedMethod;
                }

                // stop here when looking for update methods.
                selectionCriteria.setPreferUpdateMapping( false );

                // 2 step method, finally: conversion(method(source))
                ConversionAssignment conversion = resolveViaMethodAndConversion( sourceType, targetType );
                if ( conversion != null ) {
                    usedSupportedMappings.addAll( supportingMethodCandidates );
                    return conversion.getAssignment();
                }
            }

            if ( hasQualfiers() ) {
                printQualifierMessage( selectionCriteria );
            }
            else if ( allowMappingMethod() ) {
                // only forge if we would allow mapping method
                return forger.get();
            }

            // if nothing works, alas, the result is null
            return null;
        }

        private boolean hasQualfiers() {
            return selectionCriteria != null && selectionCriteria.hasQualfiers();
        }

        private void printQualifierMessage(SelectionCriteria selectionCriteria ) {

            List<String> annotations = selectionCriteria.getQualifiers().stream()
                .filter( DeclaredType.class::isInstance )
                .map( DeclaredType.class::cast )
                .map( DeclaredType::asElement )
                .map( Element::getSimpleName )
                .map( Name::toString )
                .map( a -> "@" + a )
                .collect( Collectors.toList() );
            List<String> names = selectionCriteria.getQualifiedByNames();

            if ( !annotations.isEmpty() && !names.isEmpty() ) {
                messager.printMessage(
                    mappingMethod.getExecutable(),
                    positionHint,
                    Message.GENERAL_NO_QUALIFYING_METHOD_COMBINED,
                    Strings.join( names, MessageConstants.AND ),
                    Strings.join( annotations, MessageConstants.AND )
                );
            }
            else if ( !annotations.isEmpty() ) {
                messager.printMessage(
                    mappingMethod.getExecutable(),
                    positionHint,
                    Message.GENERAL_NO_QUALIFYING_METHOD_ANNOTATION,
                    Strings.join( annotations, MessageConstants.AND )
                );
            }
            else if ( !names.isEmpty() ) {
                messager.printMessage(
                    mappingMethod.getExecutable(),
                    positionHint,
                    Message.GENERAL_NO_QUALIFYING_METHOD_NAMED,
                    Strings.join( names, MessageConstants.AND )
                );
            }

        }

        private boolean allowDirect( Type sourceType, Type targetType ) {
            if ( selectionCriteria != null && selectionCriteria.isAllowDirect() ) {
                return true;
            }

            return allowDirect( sourceType ) || allowDirect( targetType );
        }

        private boolean allowDirect(Type type) {
            if ( type.isPrimitive() ) {
                return true;
            }

            if ( type.isArrayType() ) {
                return type.isJavaLangType();
            }

            if ( type.isIterableOrStreamType() ) {
                List<Type> typeParameters = type.getTypeParameters();
                // For iterable or stream direct mapping is enabled when:
                // - The type is raw (no type parameters)
                // - The type parameter is allowed
                return typeParameters.isEmpty() || allowDirect( Collections.first( typeParameters ) );
            }

            if ( type.isMapType() ) {
                List<Type> typeParameters = type.getTypeParameters();
                // For map type direct mapping is enabled when:
                // - The type os raw (no type parameters
                // - The key and value are direct assignable
                return typeParameters.isEmpty() ||
                    ( allowDirect( typeParameters.get( 0 ) ) && allowDirect( typeParameters.get( 1 ) ) );
            }

            return type.isJavaLangType();
        }

        private boolean allowConversion() {
            return selectionCriteria != null && selectionCriteria.isAllowConversion();
        }

        private boolean allowMappingMethod() {
            return selectionCriteria != null && selectionCriteria.isAllowMappingMethod();
        }

        private boolean allow2Steps() {
            return selectionCriteria != null && selectionCriteria.isAllow2Steps();
        }

        private ConversionAssignment resolveViaConversion(Type sourceType, Type targetType) {

            ConversionProvider conversionProvider = conversions.getConversion( sourceType, targetType );

            if ( conversionProvider == null ) {
                return null;
            }
            ConversionContext ctx = new DefaultConversionContext(
                typeFactory,
                messager,
                sourceType,
                targetType,
                formattingParameters
            );

            // add helper methods required in conversion
            for ( HelperMethod helperMethod : conversionProvider.getRequiredHelperMethods( ctx ) ) {
                usedSupportedMappings.add( new SupportingMappingMethod( helperMethod ) );
            }

            Assignment conversion = conversionProvider.to( ctx );
            if ( conversion != null ) {
                return new ConversionAssignment( sourceType, targetType, conversionProvider.to( ctx ) );
            }
            return null;
        }

        /**
         * Returns a reference to a method mapping the given source type to the given target type, if such a method
         * exists.
         */
        private Assignment resolveViaMethod(Type sourceType, Type targetType, boolean considerBuiltInMethods) {

            // first try to find a matching source method
            SelectedMethod<Method> matchingSourceMethod = getBestMatch( methods, sourceType, targetType );

            if ( matchingSourceMethod != null ) {
                return getMappingMethodReference( matchingSourceMethod );
            }

            if ( considerBuiltInMethods ) {
                return resolveViaBuiltInMethod( sourceType, targetType );
            }

            return null;
        }

        /**
         * Applies matching to given method only
         *
         * @param sourceType the source type
         * @param targetType the target type
         * @param method     the method to match
         * @return an assignment if a match, given the criteria could be found. When the method is a
         * buildIn method, all the bookkeeping is applied.
         */
        private Assignment applyMatching(Type sourceType, Type targetType, Method method) {

            if ( method instanceof SourceMethod ) {
                SelectedMethod<Method> selectedMethod =
                    getBestMatch( java.util.Collections.singletonList( method ), sourceType, targetType );
                return selectedMethod != null ? getMappingMethodReference( selectedMethod ) : null;
            }
            else if ( method instanceof BuiltInMethod ) {
                return resolveViaBuiltInMethod( sourceType, targetType );
            }
            return null;
        }

        private Assignment resolveViaBuiltInMethod(Type sourceType, Type targetType) {
            SelectedMethod<BuiltInMethod> matchingBuiltInMethod =
                getBestMatch( builtInMethods.getBuiltInMethods(), sourceType, targetType );

            if ( matchingBuiltInMethod != null ) {
                return createFromBuiltInMethod( matchingBuiltInMethod.getMethod() );
            }

            return null;
        }

        private Assignment createFromBuiltInMethod(BuiltInMethod method) {
            Set<Field> allUsedFields = new HashSet<>( mapperReferences );
            SupportingField.addAllFieldsIn( supportingMethodCandidates, allUsedFields );
            SupportingMappingMethod supportingMappingMethod = new SupportingMappingMethod( method, allUsedFields );
            supportingMethodCandidates.add( supportingMappingMethod );
            ConversionContext ctx = new DefaultConversionContext(
                typeFactory,
                messager,
                method.getSourceParameters().get( 0 ).getType(),
                method.getResultType(),
                formattingParameters
            );
            Assignment methodReference = MethodReference.forBuiltInMethod( method, ctx );
            methodReference.setAssignment( sourceRHS );
            return methodReference;
        }

        /**
         * Suppose mapping required from A to C and:
         * <ul>
         * <li>no direct referenced mapping method either built-in or referenced is available from A to C</li>
         * <li>no conversion is available</li>
         * <li>there is a method from A to B, methodX</li>
         * <li>there is a method from B to C, methodY</li>
         * </ul>
         * then this method tries to resolve this combination and make a mapping methodY( methodX ( parameter ) )
         */
        private Assignment resolveViaMethodAndMethod(Type sourceType, Type targetType) {

            List<Method> methodYCandidates = new ArrayList<>( methods );
            methodYCandidates.addAll( builtInMethods.getBuiltInMethods() );

            Assignment methodRefY = null;

            // Iterate over all source methods. Check if the return type matches with the parameter that we need.
            // so assume we need a method from A to C we look for a methodX from A to B (all methods in the
            // list form such a candidate).
            // For each of the candidates, we need to look if there's a methodY, either
            // sourceMethod or builtIn that fits the signature B to C. Only then there is a match. If we have a match
            // a nested method call can be called. so C = methodY( methodX (A) )
            for ( Method methodYCandidate : methodYCandidates ) {
                Type ySourceType = methodYCandidate.getSourceParameters().get( 0 ).getType();
                if ( Object.class.getName().equals( ySourceType.getName() ) ) {
                    //  java.lang.Object as intermediate result
                    continue;
                }

                ySourceType = ySourceType.resolveTypeVarToType( targetType, methodYCandidate.getResultType() );

                if ( ySourceType != null ) {
                    methodRefY = applyMatching( ySourceType, targetType, methodYCandidate );
                    if ( methodRefY != null ) {

                        selectionCriteria.setPreferUpdateMapping( false );
                        Assignment methodRefX = resolveViaMethod( sourceType, ySourceType, true );
                        selectionCriteria.setPreferUpdateMapping( savedPreferUpdateMapping );
                        if ( methodRefX != null ) {
                            methodRefY.setAssignment( methodRefX );
                            methodRefX.setAssignment( sourceRHS );
                            break;
                        }
                        else {
                            // both should match;
                            supportingMethodCandidates.clear();
                            methodRefY = null;
                        }
                    }
                }
            }
            return methodRefY;
        }

        /**
         * Suppose mapping required from A to C and:
         * <ul>
         * <li>there is a conversion from A to B, conversionX</li>
         * <li>there is a method from B to C, methodY</li>
         * </ul>
         * then this method tries to resolve this combination and make a mapping methodY( conversionX ( parameter ) )
         *
         * Instead of directly using a built in method candidate, all the return types as 'B' of all available built-in
         * methods are used to resolve a mapping (assignment) from result type to 'B'. If  a match is found, an attempt
         * is done to find a matching type conversion.
         */
        private Assignment resolveViaConversionAndMethod(Type sourceType, Type targetType) {

            List<Method> methodYCandidates = new ArrayList<>( methods );
            methodYCandidates.addAll( builtInMethods.getBuiltInMethods() );

            Assignment methodRefY = null;

            for ( Method methodYCandidate : methodYCandidates ) {
                Type ySourceType = methodYCandidate.getSourceParameters().get( 0 ).getType();
                if ( Object.class.getName().equals( ySourceType.getName() ) ) {
                    //  java.lang.Object as intermediate result
                    continue;
                }

                ySourceType = ySourceType.resolveTypeVarToType( targetType, methodYCandidate.getResultType() );

                if ( ySourceType != null ) {
                    methodRefY = applyMatching( ySourceType, targetType, methodYCandidate );
                    if ( methodRefY != null ) {
                        ConversionAssignment conversionXRef = resolveViaConversion( sourceType, ySourceType );
                        if ( conversionXRef != null ) {
                            methodRefY.setAssignment( conversionXRef.getAssignment() );
                            conversionXRef.getAssignment().setAssignment( sourceRHS );
                            conversionXRef.reportMessageWhenNarrowing( messager, this );
                            break;
                        }
                        else {
                            // both should match
                            supportingMethodCandidates.clear();
                            methodRefY = null;
                        }
                    }
                }
            }
            return methodRefY;
        }

        /**
         * Suppose mapping required from A to C and:
         * <ul>
         * <li>there is a method from A to B, methodX</li>
         * <li>there is a conversion from B to C, conversionY</li>
         * </ul>
         * then this method tries to resolve this combination and make a mapping conversionY( methodX ( parameter ) )
         *
         * Instead of directly using a built in method candidate, all the return types as 'B' of all available built-in
         * methods are used to resolve a mapping (assignment) from source type to 'B'. If a match is found, an attempt
         * is done to find a matching type conversion.
         */
        private ConversionAssignment resolveViaMethodAndConversion(Type sourceType, Type targetType) {

            List<Method> methodXCandidates = new ArrayList<>( methods );
            methodXCandidates.addAll( builtInMethods.getBuiltInMethods() );

            ConversionAssignment conversionYRef = null;

            // search the other way around
            for ( Method methodXCandidate : methodXCandidates ) {
                Type xTargetType = methodXCandidate.getReturnType();
                if ( methodXCandidate.isUpdateMethod() ||
                    Object.class.getName().equals( xTargetType.getFullyQualifiedName() ) ) {
                    // skip update methods || java.lang.Object as intermediate result
                    continue;
                }

                xTargetType =
                    xTargetType.resolveTypeVarToType( sourceType, methodXCandidate.getParameters().get( 0 ).getType() );

                if ( xTargetType != null ) {
                    Assignment methodRefX = applyMatching( sourceType, xTargetType, methodXCandidate );
                    if ( methodRefX != null ) {
                        conversionYRef = resolveViaConversion( xTargetType, targetType );
                        if ( conversionYRef != null ) {
                            conversionYRef.getAssignment().setAssignment( methodRefX );
                            methodRefX.setAssignment( sourceRHS );
                            conversionYRef.reportMessageWhenNarrowing( messager, this );
                            break;
                        }
                        else {
                            // both should match;
                            supportingMethodCandidates.clear();
                            conversionYRef = null;
                        }
                    }
                }
            }
            return conversionYRef;
        }

        private boolean isCandidateForMapping(Method methodCandidate) {
            return isCreateMethodForMapping( methodCandidate ) || isUpdateMethodForMapping( methodCandidate );
        }

        private boolean isCreateMethodForMapping(Method methodCandidate) {
            // a create method may not return void and has no target parameter
            return methodCandidate.getSourceParameters().size() == 1
                && !methodCandidate.getReturnType().isVoid()
                && methodCandidate.getMappingTargetParameter() == null
                && !methodCandidate.isLifecycleCallbackMethod();
        }

        private boolean isUpdateMethodForMapping(Method methodCandidate) {
            // an update method may, or may not return void and has a target parameter
            return methodCandidate.getSourceParameters().size() == 1
                && methodCandidate.getMappingTargetParameter() != null
                && !methodCandidate.isLifecycleCallbackMethod();
        }

        private <T extends Method> SelectedMethod<T> getBestMatch(List<T> methods, Type sourceType, Type returnType) {

            List<SelectedMethod<T>> candidates = methodSelectors.getMatchingMethods(
                mappingMethod,
                methods,
                singletonList( sourceType ),
                returnType,
                selectionCriteria
            );

            // raise an error if more than one mapping method is suitable to map the given source type
            // into the target type
            if ( candidates.size() > 1 ) {

                if ( sourceRHS.getSourceErrorMessagePart() != null ) {
                    messager.printMessage(
                        mappingMethod.getExecutable(),
                        positionHint,
                        Message.GENERAL_AMBIGIOUS_MAPPING_METHOD,
                        sourceRHS.getSourceErrorMessagePart(),
                        returnType,
                        Strings.join( candidates, ", " )
                    );
                }
                else {
                    messager.printMessage(
                        mappingMethod.getExecutable(),
                        positionHint,
                        Message.GENERAL_AMBIGIOUS_FACTORY_METHOD,
                        returnType,
                        Strings.join( candidates, ", " )
                    );
                }
            }

            if ( !candidates.isEmpty() ) {
                return first( candidates );
            }

            return null;
        }

        private Assignment getMappingMethodReference(SelectedMethod<Method> method) {
            MapperReference mapperReference = findMapperReference( method.getMethod() );

            return MethodReference.forMapperReference(
                method.getMethod(),
                mapperReference,
                method.getParameterBindings()
            );
        }

        /**
         * Whether the given source and target type are both a collection type or both a map type and the source value
         * can be propagated via a copy constructor.
         */
        private boolean isAssignableThroughCollectionCopyConstructor(Type sourceType, Type targetType) {
            boolean bothCollectionOrMap = false;

            if ( ( sourceType.isCollectionType() && targetType.isCollectionType() ) ||
                ( sourceType.isMapType() && targetType.isMapType() ) ) {
                bothCollectionOrMap = true;
            }

            if ( bothCollectionOrMap ) {
                return hasCompatibleCopyConstructor(
                    sourceType,
                    targetType.getImplementationType() != null ? targetType.getImplementationType() : targetType
                );
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
         * otherwise.
         */
        private boolean hasCompatibleCopyConstructor(Type sourceType, Type targetType) {
            if ( targetType.isPrimitive() ) {
                return false;
            }

            List<ExecutableElement> targetTypeConstructors = ElementFilter.constructorsIn(
                targetType.getTypeElement().getEnclosedElements() );

            for ( ExecutableElement constructor : targetTypeConstructors ) {
                if ( constructor.getParameters().size() != 1 ) {
                    continue;
                }

                // get the constructor resolved against the type arguments of specific target type
                ExecutableType typedConstructor = (ExecutableType) typeUtils.asMemberOf(
                    (DeclaredType) targetType.getTypeMirror(),
                    constructor
                );

                TypeMirror parameterType = Collections.first( typedConstructor.getParameterTypes() );
                if ( parameterType.getKind() == TypeKind.DECLARED ) {
                    // replace any possible type bounds in the type parameters of the parameter types, as in JDK super
                    // type bounds in the arguments are returned from asMemberOf with "? extends ? super XX"
                    //
                    // It might also be enough to just remove "? super" from type parameters of
                    // targetType.getTypeMirror() in case we're in JDK. And that would be something that should be
                    // handled in SpecificCompilerWorkarounds...

                    DeclaredType p = (DeclaredType) parameterType;
                    List<TypeMirror> typeArguments = new ArrayList<>( p.getTypeArguments().size() );

                    for ( TypeMirror tArg : p.getTypeArguments() ) {
                        typeArguments.add( typeFactory.getTypeBound( tArg ) );
                    }
                    parameterType = typeUtils.getDeclaredType(
                        (TypeElement) p.asElement(),
                        typeArguments.toArray( new TypeMirror[typeArguments.size()] )
                    );
                }

                if ( typeUtils.isAssignable( sourceType.getTypeMirror(), parameterType ) ) {
                    return true;
                }
            }

            return false;
        }
    }

    private static class ConversionAssignment {

        private final Type sourceType;
        private final Type targetType;
        private final Assignment assignment;

        ConversionAssignment(Type sourceType, Type targetType, Assignment assignment) {
            this.sourceType = sourceType;
            this.targetType = targetType;
            this.assignment = assignment;
        }

        Assignment getAssignment() {
            return assignment;
        }

        void reportMessageWhenNarrowing(FormattingMessager messager, ResolvingAttempt attempt) {

            if ( NativeTypes.isNarrowing( sourceType.getFullyQualifiedName(), targetType.getFullyQualifiedName() ) ) {
                ReportingPolicyGem policy = attempt.mappingMethod.getOptions().getMapper().typeConversionPolicy();
                if ( policy == ReportingPolicyGem.WARN ) {
                    report( messager, attempt, Message.CONVERSION_LOSSY_WARNING );
                }
                else if ( policy == ReportingPolicyGem.ERROR ) {
                    report( messager, attempt, Message.CONVERSION_LOSSY_ERROR );
                }
            }
        }

        private void report(FormattingMessager messager, ResolvingAttempt attempt, Message message) {

            messager.printMessage(
                attempt.mappingMethod.getExecutable(),
                attempt.positionHint,
                message,
                attempt.sourceRHS.getSourceErrorMessagePart(),
                sourceType.toString(),
                targetType.toString()
            );
        }

    }
}
