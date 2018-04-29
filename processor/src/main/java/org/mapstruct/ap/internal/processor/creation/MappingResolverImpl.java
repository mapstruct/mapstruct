/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.internal.processor.creation;

import static java.util.Collections.singletonList;
import static org.mapstruct.ap.internal.util.Collections.first;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
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
import org.mapstruct.ap.internal.model.HelperMethod;
import org.mapstruct.ap.internal.model.MapperReference;
import org.mapstruct.ap.internal.model.MappingBuilderContext.MappingResolver;
import org.mapstruct.ap.internal.model.MethodReference;
import org.mapstruct.ap.internal.model.VirtualMappingMethod;
import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.BuilderType;
import org.mapstruct.ap.internal.model.common.ConversionContext;
import org.mapstruct.ap.internal.model.common.DefaultConversionContext;
import org.mapstruct.ap.internal.model.common.FormattingParameters;
import org.mapstruct.ap.internal.model.common.SourceRHS;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.model.source.SelectionParameters;
import org.mapstruct.ap.internal.model.source.builtin.BuiltInMappingMethods;
import org.mapstruct.ap.internal.model.source.builtin.BuiltInMethod;
import org.mapstruct.ap.internal.model.source.selector.MethodSelectors;
import org.mapstruct.ap.internal.model.source.selector.SelectedMethod;
import org.mapstruct.ap.internal.model.source.selector.SelectionCriteria;
import org.mapstruct.ap.internal.util.Collections;
import org.mapstruct.ap.internal.util.FormattingMessager;
import org.mapstruct.ap.internal.util.Message;
import org.mapstruct.ap.internal.util.Strings;

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
    private final Set<VirtualMappingMethod> usedVirtualMappings = new HashSet<VirtualMappingMethod>();

    public MappingResolverImpl(FormattingMessager messager, Elements elementUtils, Types typeUtils,
                               TypeFactory typeFactory, List<Method> sourceModel,
                               List<MapperReference> mapperReferences) {
        this.messager = messager;
        this.typeUtils = typeUtils;
        this.typeFactory = typeFactory;

        this.sourceModel = sourceModel;
        this.mapperReferences = mapperReferences;

        this.conversions = new Conversions( elementUtils, typeFactory );
        this.builtInMethods = new BuiltInMappingMethods( typeFactory );
        this.methodSelectors = new MethodSelectors( typeUtils, elementUtils, typeFactory );
    }

    @Override
    public Assignment getTargetAssignment(Method mappingMethod, Type targetType, String targetPropertyName,
        FormattingParameters formattingParameters, SelectionParameters selectionParameters, SourceRHS sourceRHS,
        boolean preferUpdateMapping) {

        SelectionCriteria criteria =
            SelectionCriteria.forMappingMethods( selectionParameters, targetPropertyName, preferUpdateMapping );

        ResolvingAttempt attempt = new ResolvingAttempt(
            sourceModel,
            mappingMethod,
            formattingParameters,
            sourceRHS,
            criteria
        );

        return attempt.getTargetAssignment( sourceRHS.getSourceTypeForMatching(), targetType );
    }

    @Override
    public Set<VirtualMappingMethod> getUsedVirtualMappings() {
        return usedVirtualMappings;
    }

    @Override
    public MethodReference getFactoryMethod(final Method mappingMethod, Type targetType,
                                            SelectionParameters selectionParameters) {

        List<SelectedMethod<Method>> matchingFactoryMethods =
            methodSelectors.getMatchingMethods(
                mappingMethod,
                sourceModel,
                java.util.Collections.<Type> emptyList(),
                targetType.getEffectiveType(),
                SelectionCriteria.forFactoryMethods( selectionParameters ) );

        if (matchingFactoryMethods.isEmpty()) {
            return findBuilderFactoryMethod( targetType );
        }

        if ( matchingFactoryMethods.size() > 1 ) {
            messager.printMessage(
                mappingMethod.getExecutable(),
                Message.GENERAL_AMBIGIOUS_FACTORY_METHOD,
                targetType.getEffectiveType(),
                Strings.join( matchingFactoryMethods, ", " ) );

            return null;
        }

        SelectedMethod<Method> matchingFactoryMethod = first( matchingFactoryMethods );

        MapperReference ref = findMapperReference( matchingFactoryMethod.getMethod() );

        return MethodReference.forMapperReference(
            matchingFactoryMethod.getMethod(),
            ref,
            matchingFactoryMethod.getParameterBindings() );
    }

    private MethodReference findBuilderFactoryMethod(Type targetType) {
        BuilderType builder = targetType.getBuilderType();
        if ( builder == null ) {
            return null;
        }

        ExecutableElement builderCreationMethod = builder.getBuilderCreationMethod();
        if ( builderCreationMethod.getKind() == ElementKind.CONSTRUCTOR ) {
            // If the builder creation method is a constructor it would be handled properly down the line
            return null;
        }

        if ( !builder.getBuildingType().isAssignableTo( targetType ) ) {
            //TODO print error message
            return null;
        }

        return MethodReference.forStaticBuilder(
            builderCreationMethod.getSimpleName().toString(),
            builder.getOwningType()
        );
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

        // resolving via 2 steps creates the possibillity of wrong matches, first builtin method matches,
        // second doesn't. In that case, the first builtin method should not lead to a virtual method
        // so this set must be cleared.
        private final Set<VirtualMappingMethod> virtualMethodCandidates;

        private ResolvingAttempt(List<Method> sourceModel, Method mappingMethod,
            FormattingParameters formattingParameters, SourceRHS sourceRHS, SelectionCriteria criteria) {

            this.mappingMethod = mappingMethod;
            this.methods = filterPossibleCandidateMethods( sourceModel );
            this.formattingParameters =
                formattingParameters == null ? FormattingParameters.EMPTY : formattingParameters;
            this.sourceRHS = sourceRHS;
            this.virtualMethodCandidates = new HashSet<VirtualMappingMethod>();
            this.selectionCriteria = criteria;
            this.savedPreferUpdateMapping = criteria.isPreferUpdateMapping();
        }

        private <T extends Method> List<T> filterPossibleCandidateMethods(List<T> candidateMethods) {
            List<T> result = new ArrayList<T>( candidateMethods.size() );
            for ( T candidate : candidateMethods ) {
                if ( isCandidateForMapping( candidate ) ) {
                    result.add( candidate );
                }
            }

            return result;
        }

        private Assignment getTargetAssignment(Type sourceType, Type targetType) {

            // first simple mapping method
            Assignment referencedMethod = resolveViaMethod( sourceType, targetType, false );
            if ( referencedMethod != null ) {
                referencedMethod.setAssignment( sourceRHS );
                return referencedMethod;
            }

            // then direct assignable
            if ( sourceType.isAssignableTo( targetType ) ||
                    isAssignableThroughCollectionCopyConstructor( sourceType, targetType ) ) {
                Assignment simpleAssignment = sourceRHS;
                return simpleAssignment;
            }

            // At this point the SourceType will either
            // 1. be a String
            // 2. or when its a primitive / wrapped type and analysis successful equal to its TargetType. But in that
            //    case it should have been direct assignable.
            // In case of 1. and the target type is still a wrapped or primitive type we must assume that the check
            // in NativeType is not successful. We don't want to go through type conversion, double mappings etc.
            // with something that we already know to be wrong.
            if ( sourceType.isLiteral()
                && "java.lang.String".equals( sourceType.getFullyQualifiedName( ) )
                && targetType.isNative() ) {
                // TODO: convey some error message
                return null;
            }

            // then type conversion
            Assignment conversion = resolveViaConversion( sourceType, targetType );
            if ( conversion != null ) {
                conversion.setAssignment( sourceRHS );
                return conversion;
            }

            // check for a built-in method
            Assignment builtInMethod = resolveViaBuiltInMethod( sourceType, targetType );
            if ( builtInMethod != null ) {
                builtInMethod.setAssignment( sourceRHS );
                usedVirtualMappings.addAll( virtualMethodCandidates );
                return builtInMethod;
            }

            // 2 step method, first: method(method(source))
            referencedMethod = resolveViaMethodAndMethod( sourceType, targetType );
            if ( referencedMethod != null ) {
                usedVirtualMappings.addAll( virtualMethodCandidates );
                return referencedMethod;
            }

            // 2 step method, then: method(conversion(source))
            referencedMethod = resolveViaConversionAndMethod( sourceType, targetType );
            if ( referencedMethod != null ) {
                usedVirtualMappings.addAll( virtualMethodCandidates );
                return referencedMethod;
            }

            // stop here when looking for update methods.
            selectionCriteria.setPreferUpdateMapping( false );

            // 2 step method, finally: conversion(method(source))
            conversion = resolveViaMethodAndConversion( sourceType, targetType );
            if ( conversion != null ) {
                usedVirtualMappings.addAll( virtualMethodCandidates );
                return conversion;
            }

            // if nothing works, alas, the result is null
            return null;
        }

        private Assignment resolveViaConversion(Type sourceType, Type targetType) {
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
                usedVirtualMappings.add( new VirtualMappingMethod( helperMethod ) );
            }
            return conversionProvider.to( ctx );
        }

        /**
         * Returns a reference to a method mapping the given source type to the given target type, if such a method
         * exists.
         */
        private Assignment resolveViaMethod(Type sourceType, Type targetType, boolean considerBuiltInMethods) {

            // first try to find a matching source method
            SelectedMethod<Method> matchingSourceMethod = getBestMatch( methods, sourceType, targetType );

            if ( matchingSourceMethod != null ) {
                return getMappingMethodReference( matchingSourceMethod, targetType );
            }

            if ( considerBuiltInMethods ) {
                return resolveViaBuiltInMethod( sourceType, targetType );
            }

            return null;
        }

        private Assignment resolveViaBuiltInMethod(Type sourceType, Type targetType) {
            SelectedMethod<BuiltInMethod> matchingBuiltInMethod =
                getBestMatch( builtInMethods.getBuiltInMethods(), sourceType, targetType );

            if ( matchingBuiltInMethod != null ) {
                virtualMethodCandidates.add( new VirtualMappingMethod( matchingBuiltInMethod.getMethod() ) );
                ConversionContext ctx = new DefaultConversionContext(
                    typeFactory,
                    messager,
                    sourceType,
                    targetType,
                    formattingParameters
                );
                Assignment methodReference = MethodReference.forBuiltInMethod( matchingBuiltInMethod.getMethod(), ctx );
                methodReference.setAssignment( sourceRHS );
                return methodReference;
            }

            return null;
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

            List<Method> methodYCandidates = new ArrayList<Method>( methods );
            methodYCandidates.addAll( builtInMethods.getBuiltInMethods() );

            Assignment methodRefY = null;

            // Iterate over all source methods. Check if the return type matches with the parameter that we need.
            // so assume we need a method from A to C we look for a methodX from A to B (all methods in the
            // list form such a candidate).
            // For each of the candidates, we need to look if there's a methodY, either
            // sourceMethod or builtIn that fits the signature B to C. Only then there is a match. If we have a match
            // a nested method call can be called. so C = methodY( methodX (A) )
            for ( Method methodYCandidate : methodYCandidates ) {
                methodRefY =
                    resolveViaMethod( methodYCandidate.getSourceParameters().get( 0 ).getType(), targetType, true );

                if ( methodRefY != null ) {
                    selectionCriteria.setPreferUpdateMapping( false );
                    Assignment methodRefX =
                        resolveViaMethod( sourceType, methodYCandidate.getSourceParameters().get( 0 ).getType(), true );
                    selectionCriteria.setPreferUpdateMapping( savedPreferUpdateMapping );
                    if ( methodRefX != null ) {
                        methodRefY.setAssignment( methodRefX );
                        methodRefX.setAssignment( sourceRHS );
                        break;
                    }
                    else {
                        // both should match;
                        virtualMethodCandidates.clear();
                        methodRefY = null;
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
         */
        private Assignment resolveViaConversionAndMethod(Type sourceType, Type targetType) {

            List<Method> methodYCandidates = new ArrayList<Method>( methods );
            methodYCandidates.addAll( builtInMethods.getBuiltInMethods() );

            Assignment methodRefY = null;

            for ( Method methodYCandidate : methodYCandidates ) {
                methodRefY =
                    resolveViaMethod( methodYCandidate.getSourceParameters().get( 0 ).getType(), targetType, true );

                if ( methodRefY != null ) {
                    Assignment conversionXRef =
                        resolveViaConversion( sourceType, methodYCandidate.getSourceParameters().get( 0 ).getType() );
                    if ( conversionXRef != null ) {
                        methodRefY.setAssignment( conversionXRef );
                        conversionXRef.setAssignment( sourceRHS );
                        break;
                    }
                    else {
                        // both should match
                        virtualMethodCandidates.clear();
                        methodRefY = null;
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
         */
        private Assignment resolveViaMethodAndConversion(Type sourceType, Type targetType) {

            List<Method> methodXCandidates = new ArrayList<Method>( methods );
            methodXCandidates.addAll( builtInMethods.getBuiltInMethods() );

            Assignment conversionYRef = null;

            // search the other way around
            for ( Method methodXCandidate : methodXCandidates ) {
                if ( methodXCandidate.getMappingTargetParameter() != null ) {
                    continue;
                }

                Assignment methodRefX = resolveViaMethod(
                    sourceType,
                    methodXCandidate.getReturnType(),
                    true
                );
                if ( methodRefX != null ) {
                    conversionYRef = resolveViaConversion( methodXCandidate.getReturnType(), targetType );
                    if ( conversionYRef != null ) {
                        conversionYRef.setAssignment( methodRefX );
                        methodRefX.setAssignment( sourceRHS );
                        break;
                    }
                    else {
                        // both should match;
                        virtualMethodCandidates.clear();
                        conversionYRef = null;
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
                    messager.printMessage( mappingMethod.getExecutable(),
                        Message.GENERAL_AMBIGIOUS_MAPPING_METHOD,
                        sourceRHS.getSourceErrorMessagePart(),
                        returnType,
                        Strings.join( candidates, ", " )
                    );
                }
                else {
                    messager.printMessage( mappingMethod.getExecutable(),
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

        private Assignment getMappingMethodReference(SelectedMethod<Method> method,
                                                     Type targetType) {
            MapperReference mapperReference = findMapperReference( method.getMethod() );

            return MethodReference.forMapperReference(
                method.getMethod(),
                mapperReference,
                method.getParameterBindings() );
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
         * @return {@code true} if the target type has a constructor accepting the given source type, {@code false}
         *         otherwise.
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
                    constructor );

                TypeMirror parameterType = Collections.first( typedConstructor.getParameterTypes() );
                if ( parameterType.getKind() == TypeKind.DECLARED ) {
                    // replace any possible type bounds in the type parameters of the parameter types, as in JDK super
                    // type bounds in the arguments are returned from asMemberOf with "? extends ? super XX"
                    //
                    // It might also be enough to just remove "? super" from type parameters of
                    // targetType.getTypeMirror() in case we're in JDK. And that would be something that should be
                    // handled in SpecificCompilerWorkarounds...

                    DeclaredType p = (DeclaredType) parameterType;
                    List<TypeMirror> typeArguments = new ArrayList<TypeMirror>( p.getTypeArguments().size() );

                    for ( TypeMirror tArg : p.getTypeArguments() ) {
                        typeArguments.add( typeFactory.getTypeBound( tArg ) );
                    }
                    parameterType = typeUtils.getDeclaredType(
                        (TypeElement) p.asElement(),
                        typeArguments.toArray( new TypeMirror[typeArguments.size()] ) );
                }

                if ( typeUtils.isAssignable( sourceType.getTypeMirror(), parameterType ) ) {
                    return true;
                }
            }

            return false;
        }
    }
}
