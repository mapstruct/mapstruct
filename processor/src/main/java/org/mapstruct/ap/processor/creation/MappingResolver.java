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
package org.mapstruct.ap.processor.creation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic.Kind;

import org.mapstruct.ap.conversion.ConversionProvider;
import org.mapstruct.ap.conversion.Conversions;
import org.mapstruct.ap.model.Assignment;
import org.mapstruct.ap.model.MapperReference;
import org.mapstruct.ap.model.VirtualMappingMethod;
import org.mapstruct.ap.model.assignment.AssignmentFactory;
import org.mapstruct.ap.model.assignment.Direct;
import org.mapstruct.ap.model.common.ConversionContext;
import org.mapstruct.ap.model.common.DefaultConversionContext;
import org.mapstruct.ap.model.common.Type;
import org.mapstruct.ap.model.source.Method;
import org.mapstruct.ap.model.source.SourceMethod;
import org.mapstruct.ap.model.source.builtin.BuiltInMappingMethods;
import org.mapstruct.ap.model.source.builtin.BuiltInMethod;
import org.mapstruct.ap.model.source.selector.MethodSelectors;
import org.mapstruct.ap.util.Strings;

/**
 * Resolves class is responsible for resolving the most suitable way to resolve a mapping from source to target.
 *
 * There are 2 basic types of mappings:
 * <ul>
 * <li>conversions</li>
 * <li>methods</li>
 * </ul>
 *
 * conversions are essentially one line mappings, such as String to Integer and Integer to Long
 * methods come in some varieties:
 * <ul>
 * <li>referenced mapping methods, these are methods implemented (or referenced) by the user. Sometimes indicated
 * with the 'uses' in the mapping annotations or part of the abstract mapper class</li>
 * <li>generated mapping methods (by means of MapStruct)</li>
 * <li>built in methods</li>
 * </ul>
 *
 * @author Sjaak Derksen
 */
public class MappingResolver {

    private final Conversions conversions;
    private final BuiltInMappingMethods builtInMethods;
    private final MethodSelectors methodSelectors;
    private final MappingContext mappingContext;



    /**
     * Private methods which are not present in the original mapper interface and are added to map certain property
 types.
     * @param mappingContext
     */
    public MappingResolver( MappingContext mappingContext ) {
        this.conversions = new Conversions( mappingContext.getElementUtils(), mappingContext.getTypeFactory() );
        this.builtInMethods = new BuiltInMappingMethods( mappingContext.getTypeFactory() );
        this.methodSelectors = new MethodSelectors(
                mappingContext.getTypeUtils(),
                mappingContext.getElementUtils(),
                mappingContext.getTypeFactory()
        );
        this.mappingContext = mappingContext;
    }


    /**
     * returns a parameter assignment
     *
     * @param mappingMethod target mapping method
     * @param mappedElement used for error messages
     * @param sourceType parameter to match
     * @param targetType return type to match
     * @param targetPropertyName name of the target property
     * @param dateFormat used for formatting dates in build in methods that need context information
     * @param qualifiers used for further select the appropriate mapping method based on class and name
     * @param sourceReference call to source type as string
     *
     * @return an assignment to a method parameter, which can either be:
     * <ol>
     * <li>MethodReference</li>
     * <li>TypeConversion</li>
     * <li>Direct Assignment (empty TargetAssignment)</li>
     * <li>null, no assignment found</li>
     * </ol>
     */
    public Assignment getTargetAssignment(
            Method mappingMethod,
            String mappedElement,
            Type sourceType,
            Type targetType,
            String targetPropertyName,
            String dateFormat,
            List<TypeMirror> qualifiers,
            String sourceReference ) {

        ResolvingAttempt attempt = new ResolvingAttempt( mappingMethod,
                mappedElement,
                targetPropertyName,
                dateFormat,
                qualifiers,
                sourceReference,
                conversions,
                builtInMethods,
                methodSelectors,
                mappingContext
        );

        return attempt.getTargetAssignment( sourceType, targetType );
    }


    private static class ResolvingAttempt {

        private final Method mappingMethod;
        private final String mappedElement;
        private final List<MapperReference> mapperReferences;
        private final List<SourceMethod> methods;
        private final String targetPropertyName;
        private final String dateFormat;
        private final List<TypeMirror> qualifiers;
        private final String sourceReference;
        private final Conversions conversions;
        private final BuiltInMappingMethods builtInMethods;
        private final MethodSelectors methodSelectors;
        private final MappingContext mappingContext;

        // resolving via 2 steps creates the possibillity of wrong matches, first builtin method matches,
        // second doesn't. In that case, the first builtin method should not lead to a virtual method
        // so this set must be cleared.
        private final Set<VirtualMappingMethod> virtualMethodCandidates;

        private ResolvingAttempt( Method mappingMethod,
                String mappedElement,
                String targetPropertyName,
                String dateFormat,
                List<TypeMirror> qualifiers,
                String sourceReference,
                Conversions conversions,
                BuiltInMappingMethods builtInMethods,
                MethodSelectors methodSelectors,
                MappingContext mappingContext ) {
            this.mappingMethod = mappingMethod;
            this.mappedElement = mappedElement;
            this.mapperReferences = mappingContext.getMapperReferences();
            this.methods = mappingContext.getSourceModel();
            this.targetPropertyName = targetPropertyName;
            this.dateFormat = dateFormat;
            this.qualifiers = qualifiers;
            this.sourceReference = sourceReference;
            this.conversions = conversions;
            this.builtInMethods = builtInMethods;
            this.methodSelectors = methodSelectors;
            this.mappingContext = mappingContext;
            this.virtualMethodCandidates = new HashSet<VirtualMappingMethod>();
        }

        private Assignment getTargetAssignment( Type sourceType, Type targetType ) {

            // first simpele mapping method
            Assignment referencedMethod = resolveViaMethod( sourceType, targetType );
            if ( referencedMethod != null ) {
                referencedMethod.setAssignment( AssignmentFactory.createSimple( sourceReference ) );
                mappingContext.getUsedVirtualMappings().addAll( virtualMethodCandidates );
                return referencedMethod;
            }

            // then direct assignable
            if ( sourceType.isAssignableTo( targetType ) || isPropertyMappable( sourceType, targetType ) ) {
                Assignment simpleAssignment = AssignmentFactory.createSimple( sourceReference );
                return simpleAssignment;
            }

            // then type conversion
            Assignment conversion = resolveViaConversion( sourceType, targetType );
            if ( conversion != null ) {
                conversion.setAssignment( AssignmentFactory.createSimple( sourceReference) );
                return conversion;
            }

            // 2 step method, first: method(method(souurce))
            referencedMethod = resolveViaMethodAndMethod( sourceType, targetType );
            if ( referencedMethod != null ) {
                mappingContext.getUsedVirtualMappings().addAll( virtualMethodCandidates );
                return referencedMethod;
            }

            // 2 step method, then: method(conversion(souurce))
            referencedMethod = resolveViaConversionAndMethod( sourceType, targetType );
            if ( referencedMethod != null ) {
                mappingContext.getUsedVirtualMappings().addAll( virtualMethodCandidates );
                return referencedMethod;
            }

            // 2 step method, finally: conversion(method(souurce))
            conversion = resolveViaMethodAndConversion( sourceType, targetType );
            if ( conversion != null ) {
                mappingContext.getUsedVirtualMappings().addAll( virtualMethodCandidates );
                return conversion;
            }

            // if nothing works, alas, the result is null
            return null;
        }

        private Assignment resolveViaConversion( Type sourceType, Type targetType ) {
            ConversionProvider conversionProvider = conversions.getConversion( sourceType, targetType );

            if ( conversionProvider == null ) {
                return null;
            }

            ConversionContext ctx =
                    new DefaultConversionContext( mappingContext.getTypeFactory(), targetType, dateFormat );
            return conversionProvider.to( ctx );
        }

        /**
         * Returns a reference to a method mapping the given source type to the given target type, if such a method
         * exists.
         *
         */
        private Assignment resolveViaMethod( Type sourceType, Type targetType ) {

            // first try to find a matching source method
            SourceMethod matchingSourceMethod = getBestMatch( methods, sourceType, targetType );

            if ( matchingSourceMethod != null ) {
                return getMappingMethodReference( matchingSourceMethod, mapperReferences, targetType );
            }

            // then a matching built-in method
            BuiltInMethod matchingBuiltInMethod =
                    getBestMatch( builtInMethods.getBuiltInMethods(), sourceType, targetType );

            if ( matchingBuiltInMethod != null ) {
                virtualMethodCandidates.add( new VirtualMappingMethod( matchingBuiltInMethod ) );
                ConversionContext ctx =
                        new DefaultConversionContext( mappingContext.getTypeFactory(), targetType, dateFormat );
                Assignment methodReference =  AssignmentFactory.createMethodReference( matchingBuiltInMethod, ctx );
                methodReference.setAssignment( AssignmentFactory.createSimple( sourceReference ) );
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
        private Assignment resolveViaMethodAndMethod( Type sourceType, Type targetType ) {

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
                if ( methodYCandidate.getSourceParameters().size() == 1 ) {
                    methodRefY = resolveViaMethod( methodYCandidate.getSourceParameters().get( 0 ).getType(),
                            targetType );
                    if ( methodRefY != null ) {
                        Assignment methodRefX =  resolveViaMethod(
                                sourceType,
                                methodYCandidate.getSourceParameters().get( 0 ).getType()
                        );
                        if ( methodRefX != null ) {
                            methodRefY.setAssignment( methodRefX );
                            methodRefX.setAssignment( AssignmentFactory.createSimple( sourceReference ) );
                            break;
                        }
                        else {
                            // both should match;
                            virtualMethodCandidates.clear();
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
         */
        private Assignment resolveViaConversionAndMethod( Type sourceType, Type targetType ) {

            List<Method> methodYCandidates = new ArrayList<Method>( methods );
            methodYCandidates.addAll( builtInMethods.getBuiltInMethods() );

            Assignment methodRefY = null;

            for ( Method methodYCandidate : methodYCandidates ) {
                if ( methodYCandidate.getSourceParameters().size() == 1 ) {
                    methodRefY = resolveViaMethod(
                            methodYCandidate.getSourceParameters().get( 0 ).getType(),
                            targetType
                    );
                    if ( methodRefY != null ) {
                        Assignment conversionXRef = resolveViaConversion(
                                sourceType,
                                methodYCandidate.getSourceParameters().get( 0 ).getType()
                        );
                        if ( conversionXRef != null ) {
                            methodRefY.setAssignment( conversionXRef );
                            conversionXRef.setAssignment( new Direct( sourceReference ) );
                            break;
                        }
                        else {
                            // both should match
                            virtualMethodCandidates.clear();
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
         */
        private Assignment resolveViaMethodAndConversion( Type sourceType, Type targetType ) {

            List<Method> methodXCandidates = new ArrayList<Method>( methods );
            methodXCandidates.addAll( builtInMethods.getBuiltInMethods() );

            Assignment conversionYRef = null;

            // search the other way around
            for ( Method methodXCandidate : methodXCandidates ) {
                if ( methodXCandidate.getSourceParameters().size() == 1 ) {
                    Assignment methodRefX = resolveViaMethod(
                            sourceType,
                            methodXCandidate.getReturnType()
                    );
                    if ( methodRefX != null ) {
                        conversionYRef = resolveViaConversion( methodXCandidate.getReturnType(), targetType );
                        if ( conversionYRef != null ) {
                            conversionYRef.setAssignment( methodRefX );
                            methodRefX.setAssignment( new Direct( sourceReference ) );
                            break;
                        }
                        else {
                            // both should match;
                            virtualMethodCandidates.clear();
                            conversionYRef = null;
                        }
                    }
                }
            }
            return conversionYRef;
        }

        private <T extends Method> T getBestMatch( List<T> methods, Type sourceType, Type returnType ) {

            List<T> candidates = methodSelectors.getMatchingMethods(
                    mappingMethod,
                    methods,
                    sourceType,
                    returnType,
                    qualifiers,
                    targetPropertyName
            );

            // raise an error if more than one mapping method is suitable to map the given source type
            // into the target type
            if ( candidates.size() > 1 ) {

                String errorMsg =  String.format(
                                "Ambiguous mapping methods found for mapping " + mappedElement + " from %s to %s: %s.",
                                sourceType,
                                returnType,
                                Strings.join( candidates, ", " ) );

                mappingMethod.printMessage( mappingContext.getMessager(), Kind.ERROR, errorMsg );
            }

            if ( !candidates.isEmpty() ) {
                return candidates.get( 0 );
            }

            return null;
        }

        private Assignment getMappingMethodReference( SourceMethod method,
                List<MapperReference> mapperReferences,
                Type targetType ) {
            MapperReference mapperReference = findMapperReference( mapperReferences, method );

            return AssignmentFactory.createMethodReference(
                    method,
                    mapperReference,
                    SourceMethod.containsTargetTypeParameter( method.getParameters() ) ? targetType : null
            );
        }

        private MapperReference findMapperReference( List<MapperReference> mapperReferences, SourceMethod method ) {
            for ( MapperReference ref : mapperReferences ) {
                if ( ref.getType().equals( method.getDeclaringMapper() ) ) {
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
        private boolean isPropertyMappable( Type sourceType, Type targetType ) {
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
        private boolean collectionTypeHasCompatibleConstructor( Type sourceType, Type targetType ) {
            // note (issue #127): actually this should check for the presence of a matching constructor, with help of
            // Types#asMemberOf(); but this method seems to not work correctly in the Eclipse implementation, so instead
            // we just check whether the target type is parameterized in a way that it implicitly should have a
            // constructor which accepts the source type

            TypeMirror sourceElementType = sourceType.getTypeParameters().isEmpty()
                    ? mappingContext.getTypeFactory().getType( Object.class ).getTypeMirror()
                    : sourceType.getTypeParameters().get( 0 ).getTypeMirror();

            TypeMirror targetElementType = targetType.getTypeParameters().isEmpty()
                    ? mappingContext.getTypeFactory().getType( Object.class ).getTypeMirror()
                    : targetType.getTypeParameters().get( 0 ).getTypeMirror();

            return mappingContext.getTypeUtils().isAssignable( sourceElementType, targetElementType );
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
        private boolean mapTypeHasCompatibleConstructor( Type sourceType, Type targetType ) {
            // note (issue #127): actually this should check for the presence of a matching constructor, with help of
            // Types#asMemberOf(); but this method seems to not work correctly in the Eclipse implementation, so instead
            // we just check whether the target type is parameterized in a way that it implicitly should have a
            // constructor which accepts the source type

            TypeMirror sourceKeyType;
            TypeMirror targetKeyType;
            TypeMirror sourceValueType;
            TypeMirror targetValueType;

            if ( sourceType.getTypeParameters().isEmpty() ) {
                sourceKeyType = mappingContext.getTypeFactory().getType( Object.class ).getTypeMirror();
                sourceValueType = mappingContext.getTypeFactory().getType( Object.class ).getTypeMirror();
            }
            else {
                sourceKeyType = sourceType.getTypeParameters().get( 0 ).getTypeMirror();
                sourceValueType = sourceType.getTypeParameters().get( 1 ).getTypeMirror();
            }

            if ( targetType.getTypeParameters().isEmpty() ) {
                targetKeyType = mappingContext.getTypeFactory().getType( Object.class ).getTypeMirror();
                targetValueType = mappingContext.getTypeFactory().getType( Object.class ).getTypeMirror();
            }
            else {
                targetKeyType = targetType.getTypeParameters().get( 0 ).getTypeMirror();
                targetValueType = targetType.getTypeParameters().get( 1 ).getTypeMirror();
            }

            return mappingContext.getTypeUtils().isAssignable( sourceKeyType, targetKeyType )
                    && mappingContext.getTypeUtils().isAssignable( sourceValueType, targetValueType );
        }
    }
}
