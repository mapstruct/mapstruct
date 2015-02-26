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
package org.mapstruct.ap.processor.creation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.mapstruct.ap.util.FormattingMessager;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import org.mapstruct.ap.conversion.ConversionProvider;
import org.mapstruct.ap.conversion.Conversions;
import org.mapstruct.ap.model.AssignmentFactory;
import org.mapstruct.ap.model.MapperReference;
import org.mapstruct.ap.model.MappingBuilderContext.MappingResolver;
import org.mapstruct.ap.model.MethodReference;
import org.mapstruct.ap.model.VirtualMappingMethod;
import org.mapstruct.ap.model.assignment.Assignment;
import org.mapstruct.ap.model.common.ConversionContext;
import org.mapstruct.ap.model.common.DefaultConversionContext;
import org.mapstruct.ap.model.common.Type;
import org.mapstruct.ap.model.common.TypeFactory;
import org.mapstruct.ap.model.source.Method;
import org.mapstruct.ap.model.source.SourceMethod;
import org.mapstruct.ap.model.source.builtin.BuiltInMappingMethods;
import org.mapstruct.ap.model.source.builtin.BuiltInMethod;
import org.mapstruct.ap.model.source.selector.MethodSelectors;
import org.mapstruct.ap.model.source.selector.SelectionCriteria;
import org.mapstruct.ap.util.Message;
import org.mapstruct.ap.util.Strings;

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

    private final List<SourceMethod> sourceModel;
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
                               TypeFactory typeFactory, List<SourceMethod> sourceModel,
                               List<MapperReference> mapperReferences) {
        this.messager = messager;
        this.typeUtils = typeUtils;
        this.typeFactory = typeFactory;

        this.sourceModel = sourceModel;
        this.mapperReferences = mapperReferences;

        this.conversions = new Conversions( elementUtils, typeFactory );
        this.builtInMethods = new BuiltInMappingMethods( typeFactory );
        this.methodSelectors = new MethodSelectors(
            typeUtils,
            elementUtils,
            typeFactory
        );
    }

    @Override
    public Assignment getTargetAssignment(Method mappingMethod, String mappedElement, Type sourceType,
        Type targetType, String targetPropertyName, String dateFormat, List<TypeMirror> qualifiers,
        TypeMirror resultType, String sourceReference) {

        SelectionCriteria criteria = new SelectionCriteria(qualifiers, targetPropertyName, resultType );

        ResolvingAttempt attempt = new ResolvingAttempt(
            sourceModel,
            mappingMethod,
            mappedElement,
            dateFormat,
            sourceReference,
            criteria
        );

        return attempt.getTargetAssignment( sourceType, targetType );
    }

    @Override
    public Set<VirtualMappingMethod> getUsedVirtualMappings() {
        return usedVirtualMappings;
    }

    @Override
    public MethodReference getFactoryMethod( Method mappingMethod, Type targetType, List<TypeMirror> qualifiers,
        TypeMirror resultType ) {

        SelectionCriteria criteria = new SelectionCriteria(qualifiers, null, resultType );

        ResolvingAttempt attempt = new ResolvingAttempt(
            sourceModel,
            mappingMethod,
            null,
            null,
            null,
            criteria
        );

        SourceMethod matchingSourceMethod = attempt.getBestMatch( sourceModel, null, targetType );
        if ( matchingSourceMethod != null ) {
            MapperReference ref = attempt.findMapperReference( matchingSourceMethod );
            return new MethodReference( matchingSourceMethod, ref, null );
        }
        return null;

    }

    private class ResolvingAttempt {

        private final Method mappingMethod;
        private final String mappedElement;
        private final List<SourceMethod> methods;
        private final String dateFormat;
        private final SelectionCriteria selectionCriteria;
        private final String sourceReference;

        // resolving via 2 steps creates the possibillity of wrong matches, first builtin method matches,
        // second doesn't. In that case, the first builtin method should not lead to a virtual method
        // so this set must be cleared.
        private final Set<VirtualMappingMethod> virtualMethodCandidates;

        private ResolvingAttempt(List<SourceMethod> sourceModel, Method mappingMethod, String mappedElement,
            String dateFormat, String sourceReference, SelectionCriteria criteria) {

            this.mappingMethod = mappingMethod;
            this.mappedElement = mappedElement;
            this.methods = filterPossibleCandidateMethods( sourceModel );
            this.dateFormat = dateFormat;
            this.sourceReference = sourceReference;
            this.virtualMethodCandidates = new HashSet<VirtualMappingMethod>();
            this.selectionCriteria = criteria;
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
                referencedMethod.setAssignment( AssignmentFactory.createDirect( sourceReference ) );
                return referencedMethod;
            }

            // then direct assignable
            if ( sourceType.isAssignableTo( targetType ) || isPropertyMappable( sourceType, targetType ) ) {
                Assignment simpleAssignment = AssignmentFactory.createDirect( sourceReference );
                return simpleAssignment;
            }

            // then type conversion
            Assignment conversion = resolveViaConversion( sourceType, targetType );
            if ( conversion != null ) {
                conversion.setAssignment( AssignmentFactory.createDirect( sourceReference ) );
                return conversion;
            }

            // check for a built-in method
            Assignment builtInMethod = resolveViaBuiltInMethod( sourceType, targetType );
            if ( builtInMethod != null ) {
                builtInMethod.setAssignment( AssignmentFactory.createDirect( sourceReference ) );
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

            ConversionContext ctx =
                new DefaultConversionContext( typeFactory, messager, sourceType, targetType, dateFormat );
            return conversionProvider.to( ctx );
        }

        /**
         * Returns a reference to a method mapping the given source type to the given target type, if such a method
         * exists.
         */
        private Assignment resolveViaMethod(Type sourceType, Type targetType, boolean considerBuiltInMethods) {

            // first try to find a matching source method
            SourceMethod matchingSourceMethod = getBestMatch( methods, sourceType, targetType );

            if ( matchingSourceMethod != null ) {
                return getMappingMethodReference( matchingSourceMethod, targetType );
            }

            if ( considerBuiltInMethods ) {
                return resolveViaBuiltInMethod( sourceType, targetType );
            }

            return null;
        }

        private Assignment resolveViaBuiltInMethod(Type sourceType, Type targetType) {
            BuiltInMethod matchingBuiltInMethod =
                getBestMatch( builtInMethods.getBuiltInMethods(), sourceType, targetType );

            if ( matchingBuiltInMethod != null ) {
                virtualMethodCandidates.add( new VirtualMappingMethod( matchingBuiltInMethod ) );
                ConversionContext ctx = new DefaultConversionContext( typeFactory, messager,
                                                                      sourceType,
                                                                      targetType, dateFormat );
                Assignment methodReference = AssignmentFactory.createMethodReference( matchingBuiltInMethod, ctx );
                methodReference.setAssignment( AssignmentFactory.createDirect( sourceReference ) );
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
            // For each of the candidates, we need to look if there's  a methodY, either
            // sourceMethod or builtIn that fits the signature B to C. Only then there is a match. If we have a match
            // a nested method call can be called. so C = methodY( methodX (A) )
            for ( Method methodYCandidate : methodYCandidates ) {
                methodRefY =
                    resolveViaMethod( methodYCandidate.getSourceParameters().get( 0 ).getType(), targetType, true );

                if ( methodRefY != null ) {
                    Assignment methodRefX =
                        resolveViaMethod( sourceType, methodYCandidate.getSourceParameters().get( 0 ).getType(), true );
                    if ( methodRefX != null ) {
                        methodRefY.setAssignment( methodRefX );
                        methodRefX.setAssignment( AssignmentFactory.createDirect( sourceReference ) );
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
                        conversionXRef.setAssignment( AssignmentFactory.createDirect( sourceReference ) );
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
                Assignment methodRefX = resolveViaMethod(
                    sourceType,
                    methodXCandidate.getReturnType(),
                    true
                );
                if ( methodRefX != null ) {
                    conversionYRef = resolveViaConversion( methodXCandidate.getReturnType(), targetType );
                    if ( conversionYRef != null ) {
                        conversionYRef.setAssignment( methodRefX );
                        methodRefX.setAssignment( AssignmentFactory.createDirect( sourceReference ) );
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
            return methodCandidate.getSourceParameters().size() == 1 && !methodCandidate.getReturnType().isVoid()
                && methodCandidate.getMappingTargetParameter() == null;
        }

        private boolean isUpdateMethodForMapping(Method methodCandidate) {
            // an update method may, or may not return void and has a target parameter
            return methodCandidate.getSourceParameters().size() == 1
                && methodCandidate.getMappingTargetParameter() != null;
        }

        private <T extends Method> T getBestMatch(List<T> methods, Type sourceType, Type returnType) {

            List<T> candidates = methodSelectors.getMatchingMethods(
                mappingMethod,
                methods,
                sourceType,
                returnType,
                selectionCriteria
            );

            // raise an error if more than one mapping method is suitable to map the given source type
            // into the target type
            if ( candidates.size() > 1 ) {

                if ( mappedElement != null ) {
                    messager.printMessage( mappingMethod.getExecutable(),
                        Message.GENERAL_AMBIGIOUS_MAPPING_METHOD,
                        mappedElement,
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
                return candidates.get( 0 );
            }

            return null;
        }

        private Assignment getMappingMethodReference(SourceMethod method,
                                                     Type targetType) {
            MapperReference mapperReference = findMapperReference( method );

            return AssignmentFactory.createMethodReference(
                method,
                mapperReference,
                SourceMethod.containsTargetTypeParameter( method.getParameters() ) ? targetType : null
            );
        }

        private MapperReference findMapperReference(SourceMethod method) {
            for ( MapperReference ref : mapperReferences ) {
                if ( ref.getType().equals( method.getDeclaringMapper() ) ) {
                    ref.setUsed( !method.isStatic() );
                    ref.setTypeRequiresImport( true );
                    return ref;
                }
            }
            return null;
        }

        /**
         * Whether the specified property can be mapped from source to target or not. A mapping if possible if one of
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
        private boolean isPropertyMappable(Type sourceType, Type targetType) {
            boolean collectionOrMapTargetTypeHasCompatibleConstructor = false;

            if ( sourceType.isCollectionType() && targetType.isCollectionType() ) {
                collectionOrMapTargetTypeHasCompatibleConstructor = collectionTypeHasCompatibleConstructor(
                    sourceType,
                    targetType.getImplementationType() != null
                        ? targetType.getImplementationType() : targetType
                );
            }

            if ( sourceType.isMapType() && targetType.isMapType() ) {
                collectionOrMapTargetTypeHasCompatibleConstructor = mapTypeHasCompatibleConstructor(
                    sourceType,
                    targetType.getImplementationType() != null
                        ? targetType.getImplementationType() : targetType
                );
            }

            if ( ( ( targetType.isCollectionType() || targetType.isMapType() )
                && collectionOrMapTargetTypeHasCompatibleConstructor ) ) {
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
         * otherwise.
         */
        private boolean collectionTypeHasCompatibleConstructor(Type sourceType, Type targetType) {
            // note (issue #127): actually this should check for the presence of a matching constructor, with help of
            // Types#asMemberOf(); but this method seems to not work correctly in the Eclipse implementation, so instead
            // we just check whether the target type is parameterized in a way that it implicitly should have a
            // constructor which accepts the source type

            TypeMirror sourceElementType = sourceType.getTypeParameters().isEmpty()
                ? typeFactory.getType( Object.class ).getTypeMirror()
                : sourceType.getTypeParameters().get( 0 ).getTypeMirror();

            TypeMirror targetElementType = targetType.getTypeParameters().isEmpty()
                ? typeFactory.getType( Object.class ).getTypeMirror()
                : targetType.getTypeParameters().get( 0 ).getTypeMirror();

            return typeUtils.isAssignable( sourceElementType, targetElementType );
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
        private boolean mapTypeHasCompatibleConstructor(Type sourceType, Type targetType) {
            // note (issue #127): actually this should check for the presence of a matching constructor, with help of
            // Types#asMemberOf(); but this method seems to not work correctly in the Eclipse implementation, so instead
            // we just check whether the target type is parameterized in a way that it implicitly should have a
            // constructor which accepts the source type

            TypeMirror sourceKeyType;
            TypeMirror targetKeyType;
            TypeMirror sourceValueType;
            TypeMirror targetValueType;

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

            return typeUtils.isAssignable( sourceKeyType, targetKeyType )
                && typeUtils.isAssignable( sourceValueType, targetValueType );
        }
    }
}
