/**
 *  Copyright 2012-2016 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.internal.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.model.source.SelectionParameters;
import org.mapstruct.ap.internal.model.source.SourceMethod;
import org.mapstruct.ap.internal.model.source.selector.QualifierSelector;
import org.mapstruct.ap.internal.model.source.selector.SelectionCriteria;

/**
 * Factory for creating lists of appropriate {@link LifecycleCallbackMethodReference}s
 *
 * @author Andreas Gudian
 */
public final class LifecycleCallbackFactory {

    private LifecycleCallbackFactory() {
    }

    /**
     * @param method the method to obtain the beforeMapping methods for
     * @param selectionParameters method selectionParameters
     * @param ctx the builder context
     * @param existingVariableNames the existing variable names in the mapping method
     * @return all applicable {@code @BeforeMapping} methods for the given method
     */
    public static List<LifecycleCallbackMethodReference> beforeMappingMethods(Method method,
                                                                              SelectionParameters selectionParameters,
                                                                              MappingBuilderContext ctx,
                                                                              Set<String> existingVariableNames) {
        return collectLifecycleCallbackMethods(
            method,
            selectionParameters,
            filterBeforeMappingMethods( ctx.getSourceModel() ),
            ctx,
            existingVariableNames );
    }

    /**
     * @param method the method to obtain the afterMapping methods for
     * @param selectionParameters method selectionParameters
     * @param ctx the builder context
     * @param existingVariableNames list of already used variable names
     * @return all applicable {@code @AfterMapping} methods for the given method
     */
    public static List<LifecycleCallbackMethodReference> afterMappingMethods(Method method,
                                                                             SelectionParameters selectionParameters,
                                                                             MappingBuilderContext ctx,
                                                                             Set<String> existingVariableNames) {
        return collectLifecycleCallbackMethods(
            method,
            selectionParameters,
            filterAfterMappingMethods( ctx.getSourceModel() ),
            ctx,
            existingVariableNames );
    }

    private static List<LifecycleCallbackMethodReference> collectLifecycleCallbackMethods(
            Method method, SelectionParameters selectionParameters, List<SourceMethod> callbackMethods,
            MappingBuilderContext ctx, Set<String> existingVariableNames) {

        Map<SourceMethod, List<Parameter>> parameterAssignmentsForSourceMethod
            = new HashMap<SourceMethod, List<Parameter>>();

        List<SourceMethod> candidates =
            filterCandidatesByType( method, callbackMethods, parameterAssignmentsForSourceMethod, ctx );

        candidates = filterCandidatesByQualifiers( method, selectionParameters, candidates, ctx );

        return toLifecycleCallbackMethodRefs(
            method,
            candidates,
            parameterAssignmentsForSourceMethod,
            ctx,
            existingVariableNames );
    }

    private static List<SourceMethod> filterCandidatesByQualifiers(Method method,
                                                                   SelectionParameters selectionParameters,
                                                                   List<SourceMethod> candidates,
                                                                   MappingBuilderContext ctx) {
        QualifierSelector selector = new QualifierSelector( ctx.getTypeUtils(), ctx.getElementUtils() );

        return selector.getMatchingMethods( method, candidates, null, null, new SelectionCriteria(
            selectionParameters,
            null,
            false,
            false) );
    }

    private static List<LifecycleCallbackMethodReference> toLifecycleCallbackMethodRefs(Method method,
            List<SourceMethod> candidates, Map<SourceMethod, List<Parameter>> parameterAssignmentsForSourceMethod,
            MappingBuilderContext ctx, Set<String> existingVariableNames) {
        List<LifecycleCallbackMethodReference> result = new ArrayList<LifecycleCallbackMethodReference>();
        for ( SourceMethod candidate : candidates ) {
            markMapperReferenceAsUsed( ctx.getMapperReferences(), candidate );
            result.add(
                new LifecycleCallbackMethodReference(
                    candidate,
                    parameterAssignmentsForSourceMethod.get( candidate ),
                    method.getReturnType(),
                    method.getResultType(),
                    existingVariableNames ) );
        }
        return result;
    }

    private static List<SourceMethod> filterCandidatesByType(Method method,
            List<SourceMethod> callbackMethods, Map<SourceMethod, List<Parameter>> parameterAssignmentsForSourceMethod,
            MappingBuilderContext ctx) {

        List<SourceMethod> candidates = new ArrayList<SourceMethod>();

        List<Parameter> availableParams = getAvailableParameters( method, ctx );
        for ( SourceMethod callback : callbackMethods ) {
            List<Parameter> parameterAssignments =
                ParameterAssignmentUtil.getParameterAssignments( availableParams, callback.getParameters() );

            if ( isValidCandidate( callback, method, parameterAssignments ) ) {
                parameterAssignmentsForSourceMethod.put( callback, parameterAssignments );
                candidates.add( callback );
            }
        }
        return candidates;
    }

    private static boolean isValidCandidate(SourceMethod candidate, Method method,
                                            List<Parameter> parameterAssignments) {
        if ( parameterAssignments == null ) {
            return false;
        }
        if ( !candidate.matches( extractSourceTypes( parameterAssignments ), method.getResultType() ) ) {
            return false;
        }
        return ( candidate.getReturnType().isVoid() || candidate.getReturnType().isTypeVar()
            || candidate.getReturnType().isAssignableTo( method.getResultType() ) );
    }

    private static List<Parameter> getAvailableParameters(Method method, MappingBuilderContext ctx) {
        List<Parameter> availableParams = new ArrayList<Parameter>( method.getParameters() );
        if ( method.getMappingTargetParameter() == null ) {
            availableParams.add( new Parameter( null, method.getResultType(), true, false, false) );
        }

        Parameter targetTypeParameter = new Parameter(
            null,
            ctx.getTypeFactory().classTypeOf( method.getResultType() ),
            false,
            true,
            false );

        availableParams.add( targetTypeParameter );
        return availableParams;
    }

    private static void markMapperReferenceAsUsed(List<MapperReference> references, Method method) {
        for ( MapperReference ref : references ) {
            if ( ref.getType().equals( method.getDeclaringMapper() ) ) {
                if ( !ref.isUsed() && !method.isStatic() ) {
                    ref.setUsed( true );
                }
                ref.setTypeRequiresImport( true );

                return;
            }
        }
    }

    private static List<Type> extractSourceTypes(List<Parameter> parameters) {
        List<Type> result = new ArrayList<Type>( parameters.size() );

        for ( Parameter param : parameters ) {
            if ( !param.isMappingTarget() && !param.isTargetType() ) {
                result.add( param.getType() );
            }
        }

        return result;
    }

    private static List<SourceMethod> filterBeforeMappingMethods(List<SourceMethod> methods) {
        List<SourceMethod> result = new ArrayList<SourceMethod>();
        for ( SourceMethod method : methods ) {
            if ( method.isBeforeMappingMethod() ) {
                result.add( method );
            }
        }

        return result;
    }

    private static List<SourceMethod> filterAfterMappingMethods(List<SourceMethod> methods) {
        List<SourceMethod> result = new ArrayList<SourceMethod>();
        for ( SourceMethod method : methods ) {
            if ( method.isAfterMappingMethod() ) {
                result.add( method );
            }
        }

        return result;
    }
}
