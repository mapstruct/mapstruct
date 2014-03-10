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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Messager;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic.Kind;

import org.mapstruct.ap.conversion.ConversionProvider;
import org.mapstruct.ap.conversion.Conversions;
import org.mapstruct.ap.model.MapperReference;
import org.mapstruct.ap.model.MethodReference;
import org.mapstruct.ap.model.TypeConversion;
import org.mapstruct.ap.model.VirtualMappingMethod;
import org.mapstruct.ap.model.common.ConversionContext;
import org.mapstruct.ap.model.common.DefaultConversionContext;
import org.mapstruct.ap.model.common.Type;
import org.mapstruct.ap.model.common.TypeFactory;
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
public class MappingMethodResolver {

    private final Messager messager;
    private final TypeFactory typeFactory;
    private final Conversions conversions;
    private final BuiltInMappingMethods builtInMethods;

    private final MethodSelectors methodSelectors;
    /**
     * Private methods which are not present in the original mapper interface and are added to map certain property
     * types.
     */
    private final Set<VirtualMappingMethod> virtualMethods;

    public MappingMethodResolver(Messager messager, TypeFactory typeFactory, Elements elementUtils, Types typeUtils) {
        this.messager = messager;
        this.typeFactory = typeFactory;
        this.conversions = new Conversions( elementUtils, typeFactory );
        this.builtInMethods = new BuiltInMappingMethods( typeFactory );
        this.virtualMethods = new HashSet<VirtualMappingMethod>();
        this.methodSelectors = new MethodSelectors( typeUtils, typeFactory );
    }


    public TypeConversion getConversion(Type sourceType, Type targetType, String dateFormat, String sourceReference) {
        ConversionProvider conversionProvider = conversions.getConversion( sourceType, targetType );

        if ( conversionProvider == null ) {
            return null;
        }

        return conversionProvider.to(
            sourceReference,
            new DefaultConversionContext( typeFactory, targetType, dateFormat )
        );
    }

    /**
     * Returns a reference to a method mapping the given source type to the given target type, if such a method exists.
     *
     * @param mappingMethod target mapping method
     * @param mappedElement used for error messages
     * @param mapperReferences list of references to mapper
     * @param methods list of candidate methods
     * @param sourceType parameter to match
     * @param targetType return type to match
     * @param targetPropertyName name of the target property
     * @param dateFormat used for formatting dates in build in methods that need context information
     *
     * @return a method reference.
     */
    public MethodReference getMappingMethodReferenceBasedOnMethod(SourceMethod mappingMethod,
                                                                   String mappedElement,
                                                                   List<MapperReference> mapperReferences,
                                                                   List<SourceMethod> methods,
                                                                   Type sourceType,
                                                                   Type targetType,
                                                                   String targetPropertyName,
                                                                   String dateFormat) {
        // first try to find a matching source method
        SourceMethod matchingSourceMethod = getBestMatch(
            mappingMethod,
            mappedElement,
            methods,
            sourceType,
            targetType,
            targetPropertyName
        );

        if ( matchingSourceMethod != null ) {
            return getMappingMethodReference( matchingSourceMethod, mapperReferences, targetType );
        }

        // then a matching built-in method
        BuiltInMethod matchingBuiltInMethod = getBestMatch(
            mappingMethod,
            mappedElement,
            builtInMethods.getBuiltInMethods(),
            sourceType,
            targetType,
            targetPropertyName
        );

        return matchingBuiltInMethod != null ?
            getMappingMethodReference( matchingBuiltInMethod, targetType, dateFormat ) : null;
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
     *
     * @param mappingMethod target mapping method
     * @param mappedElement used for error messages
     * @param mapperReferences list of references to mapper
     * @param methods list of candidate methods
     * @param sourceType parameter to match
     * @param targetType return type to match
     * @param targetPropertyName name of the target property
     * @param dateFormat used for formatting dates in build in methods that need context information
     *
     * @return a method reference.
     */
    public MethodReference getMappingMethodReferenceBasedOnParameter(SourceMethod mappingMethod,
                                                                      String mappedElement,
                                                                      List<MapperReference> mapperReferences,
                                                                      List<SourceMethod> methods,
                                                                      Type sourceType,
                                                                      Type targetType,
                                                                      String targetPropertyName,
                                                                      String dateFormat) {

        List<Method> methodYCandidates = new ArrayList<Method>( methods );
        methodYCandidates.addAll( builtInMethods.getBuiltInMethods() );

        MethodReference methodRefY = null;

        // Iterate over all source methods. Check if the return type matches with the parameter that we need.
        // so assume we need a method from A to C we look for a methodX from A to B (all methods in the
        // list form such a candidate).

        // For each of the candidates, we need to look if there's  a methodY, either
        // sourceMethod or builtIn that fits the signature B to C. Only then there is a match. If we have a match
        // a nested method call can be called. so C = methodY( methodX (A) )
        for ( Method methodYCandidate : methodYCandidates ) {
            if ( methodYCandidate.getSourceParameters().size() == 1 ) {
                methodRefY = getMappingMethodReferenceBasedOnMethod(
                    mappingMethod,
                    mappedElement,
                    mapperReferences,
                    methods,
                    methodYCandidate.getSourceParameters().get( 0 ).getType(),
                    targetType,
                    targetPropertyName,
                    dateFormat
                );
                if ( methodRefY != null ) {
                    MethodReference methodRefX = getMappingMethodReferenceBasedOnMethod(
                        mappingMethod,
                        mappedElement,
                        mapperReferences,
                        methods,
                        sourceType,
                        methodYCandidate.getSourceParameters().get( 0 ).getType(),
                        targetPropertyName,
                        dateFormat
                    );
                    if ( methodRefX != null ) {
                        methodRefY.setMethodRefChild( methodRefX );
                        break;
                    }
                    else {
                        // both should match;
                        methodRefY = null;
                    }
                }
            }
        }
        return methodRefY;
    }

    private <T extends Method> T getBestMatch(SourceMethod mappingMethod,
                                              String mappedElement,
                                              List<T> methods,
                                              Type sourceType,
                                              Type returnType,
                                              String targetPropertyName) {

        List<T> candidates = methodSelectors.getMatchingMethods(
            mappingMethod,
            methods,
            sourceType,
            returnType,
            targetPropertyName
        );

        // raise an error if more than one mapping method is suitable to map the given source type into the target type
        if ( candidates.size() > 1 ) {

            messager.printMessage(
                Kind.ERROR,
                String.format(
                    "Ambiguous mapping methods found for mapping " + mappedElement + " from %s to %s: %s.",
                    sourceType,
                    returnType,
                    Strings.join( candidates, ", " )
                ),
                mappingMethod.getExecutable()
            );
        }

        if ( !candidates.isEmpty() ) {
            return candidates.get( 0 );
        }

        return null;
    }

    public Set<VirtualMappingMethod> getVirtualMethodsToCreate() {
        return virtualMethods;
    }


    private MethodReference getMappingMethodReference(SourceMethod method, List<MapperReference> mapperReferences,
                                                      Type targetType) {
        MapperReference mapperReference = findMapperReference( mapperReferences, method );

        return new MethodReference(
            method,
            mapperReference,
            SourceMethod.containsTargetTypeParameter( method.getParameters() ) ? targetType : null
        );
    }

    private MapperReference findMapperReference(List<MapperReference> mapperReferences, SourceMethod method) {
        for ( MapperReference ref : mapperReferences ) {
            if ( ref.getType().equals( method.getDeclaringMapper() ) ) {
                return ref;
            }
        }
        return null;
    }

    private MethodReference getMappingMethodReference(BuiltInMethod method, Type returnType, String dateFormat) {
        virtualMethods.add( new VirtualMappingMethod( method ) );
        ConversionContext ctx = new DefaultConversionContext( typeFactory, returnType, dateFormat );
        return new MethodReference( method, ctx );
    }


}
